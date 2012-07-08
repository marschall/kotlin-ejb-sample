package com.github.marschall.kotlin.merchant.implementation

import java.util.List
import javax.ejb.Singleton
import javax.ejb.Remote
import java.util.ArrayList
import com.github.marschall.kotlin.merchant.api.TMerchant
import com.github.marschall.kotlin.merchant.api.Merchant
import org.jboss.security.annotation.SecurityDomain
import com.github.marschall.kotlin.tenant.api.Tenant

Singleton
SecurityDomain("kotlin")
Remote(javaClass<TMerchant>())
// remember Session bean implementation class MUST be public, not abstract and not final
open class MerchantBean : TMerchant {

    public override fun activeMerchants(tenant: Tenant): List<Merchant> {
        when (tenant.id) {
            1.toLong() -> {
                val merchants = ArrayList<Merchant>(2)
                merchants.add(Merchant(1, "Big Corp"))
                merchants.add(Merchant(2, "Big Retailer"))
                return merchants
            }
            2.toLong() -> {
                val merchants = ArrayList<Merchant>(1)
                merchants.add(Merchant(3, "Mom and Pop Store"))
                return merchants
            }
            else -> return ArrayList(0)
        }
    }


}
