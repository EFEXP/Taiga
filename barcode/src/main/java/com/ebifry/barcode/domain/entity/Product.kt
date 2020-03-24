package com.ebifry.barcode.domain.entity

import com.tickaroo.tikxml.annotation.*

//Xml for parser.
@Xml(name = "Product")
data class Product(
    @Path("Identifiers/MarketplaceASIN")
    @PropertyElement(name = "ASIN")
    val asin: String,
    @Path("AttributeSets/ns2:ItemAttributes")
    @PropertyElement(name = "ns2:Title")
    val name: String?,
    @Path("AttributeSets/ns2:ItemAttributes/ns2:SmallImage")
    @PropertyElement(name = "ns2:URL")
    val imageURL: String?,
    @Path("SalesRankings")
    @Element
    val salesRankings: List<SalesRank>?,
    @Path("AttributeSets/ns2:ItemAttributes")
    @Element
    val packageDimensions: PackageDimensions?,
    @Path("AttributeSets/ns2:ItemAttributes")
    @PropertyElement(name = "ns2:Manufacturer")
    val manufacturer: String?,
    @Path("AttributeSets/ns2:ItemAttributes")
    @PropertyElement(name = "ns2:ProductGroup")
    val category: String?,
    @Path( "Relationships/VariationParent/Identifiers")
    @PropertyElement(name = "ASIN")
    val parentAsin:String?
)

@Xml(name = "ns2:PackageDimensions")
data class PackageDimensions(
    @PropertyElement(name = "ns2:Height")
    val height: Double,
    @PropertyElement(name = "ns2:Length")
    val length: Double,
    @PropertyElement(name = "ns2:Width")
    val width: Double,
    @PropertyElement(name = "ns2:Weight")
    val weight: Double
)

@Xml(name = "SalesRank")
data class SalesRank(
    @PropertyElement(name = "ProductCategoryId")
    val productCategory: String,
    @PropertyElement(name = "Rank")
    val rank: Long
)

@Xml(name = "GetMatchingProductForIdResponse")
data class GetMatchingProductForIdResponse(
    @Element
    val matchingProductForIdResult: List<GetMatchingProductForIdResult>
)
@Xml(name = "GetMatchingProductForIdResult")
data class GetMatchingProductForIdResult(
    @Attribute
    val status: String,
    @Attribute(name = "Id")
    val sentId:Long,
    @Attribute(name = "IdType")
    val idType:String,
    @Path( "Products")
    @Element
    val products:List<Product>
)


