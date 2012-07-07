package com.github.marschall.kotlin.merchant.implementation

import java.util.List
import javax.ejb.Singleton
import javax.ejb.Remote
import java.util.ArrayList
import com.github.marschall.kotlin.merchant.api.TMerchant
import com.github.marschall.kotlin.merchant.api.Merchant
import org.jboss.security.annotation.SecurityDomain

Singleton
SecurityDomain("kotlin")
Remote(javaClass<TMerchant>())
// remember Session bean implementation class MUST be public, not abstract and not final
open class MerchantBean : TMerchant {

    public override fun activeMerchants(): List<Merchant> {
        throw UnsupportedOperationException()
    }


}
