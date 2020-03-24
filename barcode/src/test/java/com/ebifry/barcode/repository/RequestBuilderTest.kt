package com.ebifry.barcode.repository


import com.ebifry.barcode.di.AmazonAuthModule
import com.ebifry.barcode.domain.entity.AuthParams
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.*

@RunWith(JUnit4::class)
class RequestBuilderTest {

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun build() {

        val r=object : RequestBuilder("TESTING",
            AmazonAuthModule()
        ) {
            @Override
            override fun getCalender(): Calendar {
                val cl = Calendar.getInstance()
                cl[2020, 3, 7, 11, 10] = 59
                return cl
            }
        }
        r.authChange(AuthParams("Key","a.-","SELLER","SECRREt"))
        val actual=r.build()
        print(actual)
        assertThat(actual).isEqualTo("AWSAccessKeyId=Key&Action=TESTING&MWSAuthToken=a.-&SellerId=SELLER&SignatureMethod=HmacSHA256&SignatureVersion=2&Timestamp=2020-04-07T02%3A10%3A59Z&Version=2011-10-01&Signature=Pbflb%2Bmko%2FfewCwQ7ik8GWOAcshFxQgiUZUtrPVt0Us%3D")

    }
}