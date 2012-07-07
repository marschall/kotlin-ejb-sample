package com.github.marschall.kotlin.client

import com.github.marschall.kotlin.tenant.api.TTenant
import java.security.Security
import java.util.Hashtable
import javax.naming.Context
import javax.naming.InitialContext
import org.jboss.sasl.JBossSaslProvider

class EjbClient {

    fun run() {
        Security.addProvider(JBossSaslProvider())
        val context = createInitialContext()
        val tenant = lookUp(context, "tenant", "TenantBean", javaClass<TTenant>())
        println(tenant.activeTenants())
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

fun createInitialContext(): Context {
    val jndiProperties = Hashtable<Any, Any>()
    jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming")
    jndiProperties.put(Context.PROVIDER_URL, "remote://127.0.0.1:4447")
    jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory")
    jndiProperties.put("jboss.naming.client.ejb.context", true)
    return InitialContext(jndiProperties)
}

fun main(args : Array<String>) {
    val client = EjbClient()
    client.run()
}
