package com.github.marschall.kotlin.merchant.implementation

import org.junit.Before
import com.github.marschall.kotlin.merchant.api.TMerchant
import org.junit.Test
import com.github.marschall.kotlin.tenant.api.Tenant
import kotlin.test.assertNotNull
import kotlin.test.assertFalse

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
