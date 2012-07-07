package com.github.marschall.kotlin.merchant.api

import java.io.Serializable
import java.util.List
import javax.ejb.Remote

Remote
public trait TMerchant {
    public fun activeMerchants() : List<Merchant>
}

public class Merchant(val id:Long, val name: String) : Serializable {

}
