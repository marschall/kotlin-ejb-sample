package com.github.marschall.kotlin.merchant.api

import java.io.Serializable
import java.util.List
import javax.ejb.Remote
import com.github.marschall.kotlin.tenant.api.Tenant

Remote
public trait TMerchant {
    public fun activeMerchants(tenant: Tenant) : List<Merchant>
}

public class Merchant(val id:Long, val name: String) : Serializable {

}
