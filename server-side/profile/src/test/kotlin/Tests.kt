package com.github.marschall.kotlin.profile.implementation

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test


class ProfileBeanTest {

    var profile: ProfileBean? = null

    Before
    fun setUp() {
        profile = ProfileBean()
    }

    Test
    fun activeTenants() {
        val value = profile!!.identity("kotlin")
        assertEquals("kotlin", value)
    }

}