package com.github.marschall.kotlin.tenant.implementation

import com.github.marschall.kotlin.tenant.api.TTenant
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class TenantBeanTest {

    var tenant: TTenant? = null

    Before
    fun setUp() {
        tenant = TenantBean()
    }

    Test
    fun activeTenants() {
        val activeTenants = tenant!!.activeTenants()
        assertNotNull(activeTenants)
        assertFalse(activeTenants.isEmpty())
        // assertThat(activeTenants, not(empty()))
    }

}
