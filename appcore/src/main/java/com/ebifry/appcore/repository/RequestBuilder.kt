package com.ebifry.appcore.repository


import android.util.Log
import com.ebifry.appcore.di.AmazonAuthModule
import com.ebifry.appcore.di.DaggerAmazonAuthComponent
import com.ebifry.appcore.domain.entity.AuthParams
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import javax.inject.Inject
import kotlin.math.roundToInt

open class RequestBuilder (action:String,amazonAuthModule: AmazonAuthModule){
    @Inject
    lateinit var authParams: AuthParams
    init {
            DaggerAmazonAuthComponent.builder()
            .amazonAuthModule(amazonAuthModule)
            .build()
            .inject(this)
    }
    private val map= mutableMapOf(
        "AWSAccessKeyId" to authParams.AWSAccessKeyId
        ,"Action" to action
        ,"MWSAuthToken" to  authParams.MWSAuthToken
        ,"SellerId" to authParams.SellerId
        ,"SignatureMethod" to "HmacSHA256"
        ,"SignatureVersion" to "2"
        ,"Version" to "2011-10-01"
    )

    fun authChange(authParams: AuthParams){
        map["AWSAccessKeyId"] = authParams.AWSAccessKeyId
        map["MWSAuthToken"]=  authParams.MWSAuthToken
        map["SellerId"] = authParams.SellerId
    }

    fun addFeeParams(list: List<String>,prices:List<Double>,isFBA:Boolean):RequestBuilder{
        val c="FeesEstimateRequestList.FeesEstimateRequest."
        val intPrices=prices.map { it.roundToInt() }
        list.forEachIndexed {index, i ->
            map.putAll(mapOf( "$c${index+1}.MarketplaceId" to "A1VC38T7YXB528",
                "$c${index+1}.IdType" to "ASIN",
                "$c${index+1}.IdValue" to i,
                "$c${index+1}.IsAmazonFulfilled" to isFBA.toString(),
                "$c${index+1}.Identifier" to UUID.randomUUID().toString(),
                "$c${index+1}.PriceToEstimateFees.ListingPrice.Amount" to intPrices[index].toString(),
                "$c${index+1}.PriceToEstimateFees.ListingPrice.CurrencyCode" to "JPY",
                "$c${index+1}.PriceToEstimateFees.Shipping.Amount" to 0.toString(),
                "$c${index+1}.PriceToEstimateFees.Shipping.CurrencyCode" to "JPY",
                "$c${index+1}.PriceToEstimateFees.Points.PointsNumber" to 0.toString(),
                "$c${index+1}.PriceToEstimateFees.Points.PointsMonetaryValue.Amount" to 0.toString(),
                "$c${index+1}.PriceToEstimateFees.Points.PointsMonetaryValue.CurrencyCode" to "JPY"))
        }
        return this

    }

    fun addIdList(list: List<Long>,queryType:String):RequestBuilder{
        map.putAll(list.mapIndexed {index, i ->  "IdList.Id.${index+1}" to i.toString() })
        map["IdType"] = queryType
        return this
    }
    fun addASINList(list: List<String>):RequestBuilder{
        map.putAll(list.mapIndexed {index, i ->  "ASINList.ASIN.${index+1}" to i })
        return this
    }

    fun add(k:String,v:String):RequestBuilder{
        map[k] = v
        return this
    }

    fun build():String{
        map["Timestamp"]=getISO8601Timestamp()
        val q=map.toSortedMap().entries.map { "&${urlEncode(it.key)}=${urlEncode(it.value)}" }.reduce {acc, mutableEntry ->acc+mutableEntry }.drop(1)

        val s=urlEncode(createHmac(q))
        return "$q&Signature=$s"
    }

    private fun createHmac(text: String): String{
        val algorithm = "HmacSHA256"
        val c="POST\nmws.amazonservices.jp\n/Products/2011-10-01\n${text}".trim()
        val key = authParams.secretKey
        val keySpec = SecretKeySpec(key.toByteArray(), algorithm)
        val mac = Mac.getInstance(algorithm)
        mac.init(keySpec)
        val sign = mac.doFinal(c.toByteArray())
        return base64Encode(sign)
    }


    open fun getCalender():Calendar{
        return Calendar.getInstance()
    }
    private fun getISO8601Timestamp(): String {
        val cal: Calendar = getCalender()
        val dfm: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        dfm.timeZone = TimeZone.getTimeZone("GMT")
        return dfm.format(cal.time)
    }
    private fun urlEncode(rawValue: String): String {
        val value: String = rawValue.replace("\n","")
        val u=URLEncoder.encode(value, "UTF-8").replace("%7E", "~").replace(" ", "%20")
            .replace("+", "%20")
            .replace("*", "%2A")
        return u
    }

    private fun base64Encode(text: String): String {
        val bytes: ByteArray
        bytes = try {
            text.toByteArray(charset("ASCII"))
        } catch (e: UnsupportedEncodingException) {
            throw RuntimeException(e)
        }
        return base64Encode(bytes)
    }
    private fun base64Encode(bytes: ByteArray): String {
        val bitPattern = StringBuffer()
        for (i in bytes.indices) {
            var b = bytes[i].toInt()
            if (b < 0) {
                b += 256
            }
            var tmp = Integer.toBinaryString(b)
            while (tmp.length < 8) {
                tmp = "0$tmp"
            }
            bitPattern.append(tmp)
        }

        while (bitPattern.length % 6 != 0) {
            bitPattern.append("0")
        }

        val table = arrayOf(
            "A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P",
            "Q", "R", "S", "T", "U", "V", "W", "X",
            "Y", "Z", "a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n",
            "o", "p", "q", "r", "s", "t", "u", "v",
            "w", "x", "y", "z", "0", "1", "2", "3",
            "4", "5", "6", "7", "8", "9", "+", "/"
        )
        val encoded = StringBuffer()
        var i = 0
        while (i < bitPattern.length) {
            val tmp = bitPattern.substring(i, i + 6)
            val index = tmp.toInt(2)
            encoded.append(table[index])
            i += 6
        }

        while (encoded.length % 4 != 0) {
            encoded.append("=")
        }
        return encoded.toString()
    }


}