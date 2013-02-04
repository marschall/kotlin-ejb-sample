package com.github.marschall.kotlin.client

import com.github.marschall.kotlin.tenant.api.TTenant
import java.security.Security
import java.util.Hashtable
import javax.naming.Context
import javax.naming.InitialContext
import javax.security.auth.callback.Callback
import javax.security.auth.callback.CallbackHandler
import javax.security.auth.callback.NameCallback
import javax.security.auth.callback.PasswordCallback
import org.jboss.sasl.JBossSaslProvider
import com.github.marschall.kotlin.merchant.api.TMerchant

class EjbClient {

    static {
        Security.addProvider(JBossSaslProvider())
    }

    fun run() {

        // do unauthenticated calls
        var context = createInitialContext()

        val tenantBean = lookUp(context, "tenant", "TenantBean", javaClass<TTenant>())
        System.out.println(tenantBean.userName())
        val activeTenants = tenantBean.activeTenants()
        val tenantIterator1 = activeTenants.iterator()
        while (tenantIterator1!!.hasNext()) {
            var tenant = tenantIterator1.next()
            System.out.println(tenant)
        }

        // login
        context = createInitialContextForUser("admin", "admin")
        val merchantBean = lookUp(context, "merchant", "MerchantBean", javaClass<TMerchant>())
        //context = createInitialContextForUser("admin", "admin")
        System.out.println(merchantBean.userName())
        val tenantIterator2 = activeTenants.iterator()
        while (tenantIterator2!!.hasNext()) {
            var tenant = tenantIterator2.next()
            val merchantIterator = merchantBean.activeMerchants(tenant).iterator()
            while (merchantIterator!!.hasNext()) {
                val merchant = merchantIterator.next()
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
    // val proxy = context.lookup("ejb:" + appName + "/" + moduleName + "/" + distinctName + "/" + beanName + "!" + interfaceClass.getName());
    // val remoteName = "ejb:" + appName + "/" + moduleName + "/" + distinctName + "/" + beanName + "!" + interfaceClass.getName()
    val remoteName = "ejb:" + appName + "/" + moduleName + "/" + distinctName + "/" + beanName + "!" + interfaceClass.getName()
    println(remoteName)
    val proxy = context.lookup(remoteName)
    // val proxy = context.lookup("ejb:/" + appName + "/" + moduleName + "/" + beanName + "!" + interfaceClass.getName());
    return interfaceClass.cast(proxy)!!
}

fun createConfigurationHashTable(): Hashtable<Any, Any> {
    // https://issues.jboss.org/browse/EJBCLIENT-34
    val jndiProperties = Hashtable<Any, Any>()

    // https://community.jboss.org/thread/196054
    // https://community.jboss.org/thread/199165?tstart=0
    // java.naming.provider.url=remote://localhost:4447
    // java.naming.factory.initial=org.jboss.naming.remote.client.InitialContextFactory
    // java.naming.factory.url.pkgs=org.jboss.ejb.client.naming

    // https://community.jboss.org/thread/196943
    // https://community.jboss.org/wiki/JBossAS7RemoteEJBAuthenticationHowto
    // https://community.jboss.org/message/732309#732309#732309
    // https://community.jboss.org/thread/176963
    jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");

    // needs updated client
    /*
    jndiProperties.put("endpoint.name", "client-endpoint")
    jndiProperties.put("remote.connectionprovider.create.options.org.xnio.Options.SSL_ENABLED", false)

    jndiProperties.put("remote.connections", "default")

    jndiProperties.put("remote.connection.default.host", "127.0.0.1")
    jndiProperties.put("remote.connection.default.port", 4447)
    jndiProperties.put("remote.connection.default.connect.options.org.xnio.Options.SASL_POLICY_NOANONYMOUS", false);
    */

    // jndiProperties.put("remote.connection.default.connect.options.org.xnio.Options.SASL_DISALLOWED_MECHANISMS", "JBOSS-LOCAL-USER");

    return jndiProperties
}

fun createInitialContext(): Context {
    val jndiProperties = createConfigurationHashTable()
    return InitialContext(jndiProperties)
}

fun createInitialContextForUser(username: String, password: String): Context {
    val jndiProperties = createConfigurationHashTable()
    // https://community.jboss.org/message/731189
    // https://community.jboss.org/message/613128#613128
    // https://community.jboss.org/wiki/ManagementAPISecurityPossibleConfigurationSample
    // https://community.jboss.org/message/640619#640619#640619
    jndiProperties.put("remote.connection.default.username", username)
    jndiProperties.put("remote.connection.default.password", password)
    jndiProperties.put(Context.SECURITY_PRINCIPAL, username);
    jndiProperties.put(Context.SECURITY_CREDENTIALS, password)
    return InitialContext(jndiProperties)
}

fun main(args : Array<String>) {
    val client = EjbClient()
    client.run()
}
