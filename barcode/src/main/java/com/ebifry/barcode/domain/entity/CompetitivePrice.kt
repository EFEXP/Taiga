package com.ebifry.barcode.domain.entity

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
    val prices:List<CompetitivePrice>,
    @Path("Product/CompetitivePricing/NumberOfOfferListings")
    @Element
    val numberOfOfferListings:List<OfferListingCount>
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


@Xml(name="OfferListingCount")
data class OfferListingCount(
    @Attribute
    val condition:String,
    @TextContent
    val amount:String)

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
