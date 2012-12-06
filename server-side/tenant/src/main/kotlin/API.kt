package com.github.marschall.kotlin.tenant.api

import java.io.Serializable
import java.util.List

public trait TTenant {
    public fun activeTenants(): List<Tenant>

    public fun lookUpJndiValue(): String

}

public class Tenant(val id:Long, val acronym: String, val name: String) : Serializable {

}