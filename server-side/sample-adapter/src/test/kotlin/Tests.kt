package com.github.marschall.kotlin.rar.implementation

import org.junit.Test
import org.junit.Before
import org.junit.Assert.assertEquals

class SampleAdapterTest {

    var adapter: SampleAdapter? = null

    Before
    fun setUp() {
        adapter = SampleAdapter()
    }

    Test
    fun equals() {
        assertEquals(adapter, adapter)
    }

}