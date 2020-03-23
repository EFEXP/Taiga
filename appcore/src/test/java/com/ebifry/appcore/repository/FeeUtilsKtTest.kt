package com.ebifry.appcore.repository

import com.ebifry.appcore.domain.entity.PackageDimensions
import org.junit.Test

import org.junit.Assert.*
import java.util.*

class FeeUtilsKtTest {

    @Test
    fun calculateStorageCharge() {
       val f=calculateStorageCharge(Date(), PackageDimensions(1.0,1.0,1.0,1.0))
        println(f)
    }
}