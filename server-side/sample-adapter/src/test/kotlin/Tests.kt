package com.github.marschall.kotlin.rar.implementation

import org.junit.Test
import org.junit.Before

class SampleAdapterTest {

    var adapter: SampleAdapter? = null

    Before
    fun setUp() {
        adapter = SampleAdapter()
    }

    Test
    fun lifeCycle() {
        adapter!!.start(null)
        adapter!!.stop()
    }

}