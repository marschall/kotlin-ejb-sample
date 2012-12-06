package com.github.marschall.kotlin.tenant.implementation

import com.github.marschall.kotlin.tenant.api.TTenant
import com.github.marschall.kotlin.tenant.api.Tenant
import java.util.ArrayList
import java.util.List
import javax.ejb.Remote
import javax.ejb.Singleton
import javax.naming.InitialContext
import javax.ejb.ConcurrencyManagementType
import javax.ejb.ConcurrencyManagement

Singleton
ConcurrencyManagement(ConcurrencyManagementType.BEAN)
Remote(javaClass<TTenant>())
// remember Session bean implementation class MUST be public, not abstract and not final
open class TenantBean : TTenant {

    public override fun activeTenants(): List<Tenant> {
        //return list(Tenant(1, "te1", "First Tentant"), Tenant(2, "tex", "Second Tentant"))
        val tenants = ArrayList<Tenant>()
        tenants.add(Tenant(1, "te1", "First Tentant"))
        tenants.add(Tenant(2, "tex", "Second Tentant"))

        // return tenants
        return tenants as List<Tenant>
    }

    public override fun lookUpJndiValue(): String {
        val ic = InitialContext()
        return ic.lookup(NAME) as String
    }

}

val NAME: String  = "java:global/env/foo"
