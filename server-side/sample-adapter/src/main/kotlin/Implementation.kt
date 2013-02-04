package com.github.marschall.kotlin.rar.implementation

import java.util.logging.Logger
import javax.naming.InitialContext
import javax.resource.spi.ActivationSpec
import javax.resource.spi.BootstrapContext
import javax.resource.spi.Connector
import javax.resource.spi.ResourceAdapter
import javax.resource.spi.TransactionSupport
import javax.resource.spi.endpoint.MessageEndpointFactory
import javax.transaction.xa.XAResource
import javax.resource.spi.ConfigProperty
import java.util.Objects


Connector
class SampleAdapter : ResourceAdapter {

    ConfigProperty(defaultValue = "dev_null")
    var profile: String? = null

    //https://docs.jboss.org/author/display/AS71/JNDI+Reference

    public override fun start(ctx: BootstrapContext?) {
        // https://docs.jboss.org/author/display/AS71/Command+line+parameters
        LOG.info(System.getProperty("jboss.server.config.dir"))
        val ic = InitialContext()
        ic.rebind(NAME, VALUE)
        LOG.info("started")
    }

    public override fun stop() {
        val ic = InitialContext()
        ic.unbind(NAME)
        LOG.info("stopped")
    }

    public override fun endpointActivation(mef: MessageEndpointFactory?, spec: ActivationSpec? ) {

    }

    public override fun endpointDeactivation(mef: MessageEndpointFactory? , spec: ActivationSpec?) {

    }

    public override fun getXAResources(specs: Array<out ActivationSpec>?): Array<XAResource>? {
        return null
    }


    // JBoss AS 7.1.1 requires this method to be present
    public override fun hashCode(): Int {
        return Objects.hashCode(profile)
    }

    // JBoss AS 7.1.1 requires this method to be present
    public override fun equals(obj: Any?): Boolean {
        if (this.identityEquals(obj)) {
            return true
        }
        if (!(obj is SampleAdapter)) {
            return false
        }
        return Objects.equals(profile, obj.profile)
    }
}

val NAME: String  = "java:global/env/foo"
val VALUE: String = "FOO"
val LOG: Logger = Logger.getLogger("sample-adapter")!!
