package com.github.marschall.kotlin.client

import com.github.marschall.kotlin.tenant.api.TTenant
import java.security.Security
import java.util.Hashtable
import javax.naming.Context
import javax.naming.InitialContext
import org.jboss.sasl.JBossSaslProvider
import com.github.marschall.kotlin.tenant.api.Tenant
import java.util.List
import java.util.Collections
import java.util.ArrayList
import javax.security.auth.callback.CallbackHandler
import javax.security.auth.callback.Callback
import javax.security.auth.callback.NameCallback
import javax.security.auth.callback.PasswordCallback
import javax.security.auth.login.LoginContext
import com.github.marschall.kotlin.merchant.api.TMerchant

class EjbClient {

    static {
        Security.addProvider(JBossSaslProvider())
    }

    class object {
        /** see jaas.config */
        val LOGIN_CONTEXT_NAME: String = "kotlin"

        /** JAAS configuration file name */
        val LOGIN_CONFIG_FILENAME: String  = "jaas.config"

        /**
         * JAAS login config system property. Will point to location of
         * {@link #LOGIN_CONFIG_FILENAME}
         */
        val LOGIN_CONFIG_PROPERTY: String  = "java.security.auth.login.config"
    }

    fun run() {
        // set up JAAS

        val url = javaClass<EjbClient>().getClassLoader()!!.getResource(LOGIN_CONFIG_FILENAME)
        if (url != null) {
            System.setProperty(LOGIN_CONFIG_PROPERTY, url.toExternalForm())
        } else {
            throw RuntimeException("could not find " + LOGIN_CONFIG_FILENAME);
        }

        // do unauthenticated calls
        var context = createInitialContext()
        val tenantBean = lookUp(context, "tenant", "TenantBean", javaClass<TTenant>())
        for (tenant in tenantBean.activeTenants()) {
            System.out.println(tenant)
        }

        // login
        //val loginContext = LoginContext(LOGIN_CONTEXT_NAME, KotlinLoginHandler())
        //loginContext.login()

        // unauthenticated calls
        //val merchantBean = lookUp(context, "merchant", "MerchantBean", javaClass<TMerchant>())
        context = createInitialContextForUser("admin", "admin")
        val merchantBean = lookUp(context, "merchant", "MerchantBean", javaClass<TMerchant>())
        System.out.println(merchantBean.userName())
        for (tenant in tenantBean.activeTenants()) {
            for (merchant in merchantBean.activeMerchants(tenant)) {
                System.out.println(merchant)
            }
        }

    }
}

fun <T> lookUp(context: Context, moduleName: String, beanName: String, interfaceClass: Class<T>): T {
    // The app name is the application name of the deployed EJBs. This is typically the ear name
    // without the .ear suffix. However, the application name could be overridden in the application.xml of the
    // EJB deployment on the server.
    // Since we haven't deployed the application as a .ear, the app name for us will be an empty string
    val appName = "sample-application"
    // This is the module name of the deployed EJBs on the server. This is typically the jar name of the
    // EJB deployment, without the .jar suffix, but can be overridden via the ejb-jar.xml
    // In this example, we have deployed the EJBs in a jboss-as-ejb-remote-app.jar, so the module name is
    // jboss-as-ejb-remote-app
    // val moduleName = "";
    // AS7 allows each deployment to have an (optional) distinct name. We haven't specified a distinct name for
    // our EJB deployment, so this is an empty string
    val distinctName = ""
    // The EJB name which by default is the simple class name of the bean implementation class
    //    final String beanName = "AS7Bean";
    // the remote view fully qualified class name
    // let's do the lookup
    //    Object proxy = context.lookup("ejb:" + appName + "/" + moduleName + "/" + distinctName + "/" + beanName + "!" + interfaceClass.getName());
    //use JNDI instead of ejb
    val proxy = context.lookup(appName + "/" + moduleName + "/" + distinctName + "/" + beanName + "!" + interfaceClass.getName())
    return interfaceClass.cast(proxy)!!
}

class KotlinLoginHandler : CallbackHandler {

    public override fun handle(callbacks: Array<Callback?>?) {
        for (callback: Callback? in callbacks) {
            when (callback) {
                is NameCallback -> callback.setName("admin")
                is PasswordCallback -> callback.setPassword("admin".toCharArray())
                else -> throw IllegalStateException("unknown callback" + callback)
            }
        }
    }

}

fun createInitialContext(): Context {
    val jndiProperties = Hashtable<Any, Any>()
    jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory")
    jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming")
    jndiProperties.put(Context.PROVIDER_URL, "remote://127.0.0.1:4447")
    jndiProperties.put("remote.connection.default.connect.options.org.xnio.Options.SASL_POLICY_NOANONYMOUS", false)
    return InitialContext(jndiProperties)
}

fun createInitialContextForUser(userName: String, password: String): Context {
    val jndiProperties = Hashtable<Any, Any>()
    jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory")
    jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming")
    jndiProperties.put(Context.PROVIDER_URL, "remote://127.0.0.1:4447")
    jndiProperties.put("remote.connection.default.connect.options.org.xnio.Options.SASL_POLICY_NOANONYMOUS", true)
    jndiProperties.put("remote.connection.default.username", userName)
    jndiProperties.put("remote.connection.default.password", password)
    return InitialContext(jndiProperties)
}

fun main(args : Array<String>) {
    val client = EjbClient()
    client.run()
}
