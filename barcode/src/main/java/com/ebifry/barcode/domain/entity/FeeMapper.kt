package com.ebifry.barcode.domain.entity

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Path
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name="GetMyFeesEstimateResponse")
data class GetMyFeesEstimateResponse(
    @Path("GetMyFeesEstimateResult/FeesEstimateResultList")
    @Element
    val feesEstimateResultList:List<FeesEstimateResult>
)
@Xml(name = "FeesEstimateResult")
data class FeesEstimateResult(
    @Path("FeesEstimateIdentifier")
    @PropertyElement(name="IdValue")
    val asin:String,
    @Path("FeesEstimate/TotalFeesEstimate")
    @PropertyElement(name="Amount")
    val totalFeesEstimate:Double,
    @Path("FeesEstimate/FeeDetailList")
    @Element
    val feeDetailList:List<FeeDetail>,
    @PropertyElement(name="Status")
    val status:String)

@Xml(name="FeeDetail")
data class FeeDetail(
    @Path("FeeAmount")
    @PropertyElement(name="Amount")
    val totalAmount:Double,
    @PropertyElement(name="FeeType")
    val feeType:String)

