package com.ebifry.appcore.domain.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.ebifry.appcore.domain.entity.db.ScannedItem
import com.tickaroo.tikxml.annotation.*

@Xml(name = "GetCompetitivePricingForASINResponse")
data class CompetitivePriceResponse(
    @Element
    val competitivePricingForASINResult:List<GetCompetitivePricingForASINResult>
)

@Xml(name = "GetCompetitivePricingForASINResult")
data class GetCompetitivePricingForASINResult(
    @Attribute
    val status:String,
    @Attribute(name="ASIN")
    val asin:String,
    @Path("Product/CompetitivePricing/CompetitivePrices")
    @Element
    val prices:List<CompetitivePrice>
)

@Xml(name="CompetitivePrice")
data class CompetitivePrice(
    @Attribute(name="condition")
    val condition:String,
    @Attribute(name="subcondition")
    val subcondition:String,
    @PropertyElement(name = "CompetitivePriceId")
    val id:Int,
    @Element
    val price:Price
)

@Xml(name="Price")
data class Price(
    @Path("ListingPrice")
    @PropertyElement(name="Amount")
    val listing:Double,
    @Path("LandedPrice")
    @PropertyElement(name="Amount")
    val landed:Double,
    @Path("Shipping")
    @PropertyElement(name = "Amount")
    val shipping:Double
)
