package com.github.marschall.kotlin.rar.implementation

import java.util.logging.Logger
import javax.resource.spi.ActivationSpec
import javax.resource.spi.BootstrapContext
import javax.resource.spi.Connector
import javax.resource.spi.ResourceAdapter
import javax.resource.spi.TransactionSupport.TransactionSupportLevel
import javax.resource.spi.endpoint.MessageEndpointFactory
import javax.transaction.xa.XAResource

Connector(reauthenticationSupport = false, transactionSupport = TransactionSupportLevel.NoTransaction)
class SampleAdapter : ResourceAdapter {

    public override fun start(ctx: BootstrapContext?) {
        LOG.info("start")
    }

    public override fun stop() {
        LOG.info("stop")
    }

    public override fun endpointActivation(mef: MessageEndpointFactory?, spec: ActivationSpec? ) {

    }

    public override fun endpointDeactivation(mef: MessageEndpointFactory? , spec: ActivationSpec?) {

    }

    public override fun getXAResources(specs: Array<ActivationSpec?>?): Array<XAResource?>? {
        return null;
    }


    // JBoss AS 7.1.1 requires this method to be present
    public override fun hashCode(): Int {
        return super<ResourceAdapter>.hashCode()
    }

    // JBoss AS 7.1.1 requires this method to be present
    public override fun equals(obj: Any?): Boolean {
        return obj == this
    }
}

val LOG: Logger = Logger.getLogger("sample-adapter")!!
