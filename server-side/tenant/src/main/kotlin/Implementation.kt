package com.github.marschall.kotlin.tenant.implementation

import com.github.marschall.kotlin.tenant.api.TTenant
import com.github.marschall.kotlin.tenant.api.Tenant
import java.util.List
import javax.ejb.ConcurrencyManagement
import javax.ejb.ConcurrencyManagementType
import javax.ejb.Remote
import javax.ejb.Singleton
import javax.naming.InitialContext
import javax.ejb.EJBContext
import javax.annotation.Resource

import java.util.ArrayList

Singleton
ConcurrencyManagement(ConcurrencyManagementType.BEAN)
Remote(javaClass<TTenant>())
// remember Session bean implementation class MUST be public, not abstract and not final
open class TenantBean : TTenant {

    Resource
    private var ejbContext: EJBContext? = null

    public override fun userName(): String {
        return ejbContext!!.getCallerPrincipal().getName()!!
    }

    public override fun activeTenants(): List<Tenant> {
        //return listOf(Tenant(1, "te1", "First Tentant"), Tenant(2, "tex", "Second Tentant")) as List<Tenant>

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
