package com.github.marschall.kotlin.tenant.implementation

import org.junit.Before
import com.github.marschall.kotlin.tenant.api.TTenant
import org.junit.Test
import org.junit.Assert.*
import org.hamcrest.Matchers.*

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
