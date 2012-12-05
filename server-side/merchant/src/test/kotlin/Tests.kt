package com.github.marschall.kotlin.merchant.implementation

import com.github.marschall.kotlin.merchant.api.TMerchant
import com.github.marschall.kotlin.tenant.api.Tenant
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import org.junit.Before
import org.junit.Test

class MerchantBeanTest {

    var merchant: TMerchant? = null

    Before
    fun setUp() {
        merchant = MerchantBean()
    }

    Test
    fun activeTenants() {
        val activeMerchants = merchant!!.activeMerchants(Tenant(1, "1", "1"))
        assertNotNull(activeMerchants)
        assertFalse(activeMerchants.isEmpty())
        // assertThat(activeTenants, not(empty()))
    }

}
