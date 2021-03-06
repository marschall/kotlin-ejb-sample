package com.github.marschall.kotlin.tenant.api

import java.io.Serializable
import java.util.List
import javax.ejb.Remote

public trait TTenant {

    public fun activeTenants(): List<Tenant>

    public fun lookUpJndiValue(): String

    public fun userName(): String

}

public class Tenant(val id:Long, val acronym: String, val name: String) : Serializable {

    override fun toString(): String {
        return "TTenant($acronym): $name"
    }

}