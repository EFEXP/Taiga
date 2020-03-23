package com.ebifry.appcore.repository

import com.ebifry.appcore.domain.entity.PackageDimensions
import com.ebifry.appcore.domain.entity.Product
import org.bouncycastle.util.Pack
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class DataRepositoryImplTest {

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun calculateShippingCharge() {
        val h= arrayOf(24,32,33,40,40)
        val l= arrayOf(13,21,24,20,32)
        val w= arrayOf(1,2,2,1,1)
        val we= arrayOf(10,900,1900,3900,8000,1000)
        val r=h.mapIndexed{i,_->
            createProductFromCm(h[i],l[i],w[i],we[i])
        }
        r.forEach { println(calculateShippingCharge(it)) }
    }
    fun createProductFromCm(h:Int,l:Int,w:Int,we:Int):Product{
        return Product("","","", arrayListOf(), PackageDimensions(h/2.54,l/2.54,w/2.54,we.toDouble()/2204.62),"","",0.0)
    }
}