package com.github.marschall.kotlin.merchant.api

import com.github.marschall.kotlin.tenant.api.Tenant
import java.io.Serializable
import java.util.List

public trait TMerchant {
    public fun activeMerchants(tenant: Tenant): List<Merchant>
    public fun userName(): String?
}

public class Merchant(val id:Long, val name: String): Serializable {

}
