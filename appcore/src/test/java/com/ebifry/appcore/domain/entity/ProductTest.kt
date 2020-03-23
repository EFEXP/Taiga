package com.ebifry.appcore.domain.entity

import com.google.common.truth.Truth
import com.tickaroo.tikxml.TikXml
import okio.Buffer
import okio.BufferedSource
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.lang.Exception

class EntitiesTest{
    val tikXml: TikXml = TikXml.Builder().exceptionOnUnreadXml(false).build()
    @Before
    fun before(){

    }
    @Test
    fun getFeeTest(){
        val xml="""<GetMyFeesEstimateResponse xmlns="http://mws.amazonservices.com/schema/Products/2011-10-01"><GetMyFeesEstimateResult><FeesEstimateResultList><FeesEstimateResult><FeesEstimate><TimeOfFeesEstimation>2020-03-22T05:32:16.050Z</TimeOfFeesEstimation><TotalFeesEstimate><Amount>854.00</Amount><CurrencyCode>JPY</CurrencyCode></TotalFeesEstimate><FeeDetailList><FeeDetail><FeeAmount><Amount>450.00</Amount><CurrencyCode>JPY</CurrencyCode></FeeAmount><FinalFee><Amount>450.00</Amount><CurrencyCode>JPY</CurrencyCode></FinalFee><FeePromotion><Amount>0.00</Amount><CurrencyCode>JPY</CurrencyCode></FeePromotion><FeeType>ReferralFee</FeeType></FeeDetail><FeeDetail><FeeAmount><Amount>0.00</Amount><CurrencyCode>JPY</CurrencyCode></FeeAmount><FinalFee><Amount>0.00</Amount><CurrencyCode>JPY</CurrencyCode></FinalFee><FeePromotion><Amount>0.00</Amount><CurrencyCode>JPY</CurrencyCode></FeePromotion><FeeType>VariableClosingFee</FeeType></FeeDetail><FeeDetail><FeeAmount><Amount>0.00</Amount><CurrencyCode>JPY</CurrencyCode></FeeAmount><FinalFee><Amount>0.00</Amount><CurrencyCode>JPY</CurrencyCode></FinalFee><FeePromotion><Amount>0.00</Amount><CurrencyCode>JPY</CurrencyCode></FeePromotion><FeeType>PerItemFee</FeeType></FeeDetail><FeeDetail><FeeAmount><Amount>404.00</Amount><CurrencyCode>JPY</CurrencyCode></FeeAmount><FinalFee><Amount>404.00</Amount><CurrencyCode>JPY</CurrencyCode></FinalFee><FeePromotion><Amount>0.00</Amount><CurrencyCode>JPY</CurrencyCode></FeePromotion><FeeType>FBAFees</FeeType><IncludedFeeDetailList><FeeDetail><FeeAmount><Amount>404.00</Amount><CurrencyCode>JPY</CurrencyCode></FeeAmount><FinalFee><Amount>404.00</Amount><CurrencyCode>JPY</CurrencyCode></FinalFee><FeePromotion><Amount>0.00</Amount><CurrencyCode>JPY</CurrencyCode></FeePromotion><FeeType>FBAPickAndPack</FeeType></FeeDetail></IncludedFeeDetailList></FeeDetail></FeeDetailList></FeesEstimate><FeesEstimateIdentifier><MarketplaceId>A1VC38T7YXB528</MarketplaceId><IdType>ASIN</IdType><SellerId>A1YGAJBX0XV0O8</SellerId><SellerInputIdentifier>abc</SellerInputIdentifier><IsAmazonFulfilled>true</IsAmazonFulfilled><IdValue>B0765C6F63</IdValue><PriceToEstimateFees><ListingPrice><Amount>3000</Amount><CurrencyCode>JPY</CurrencyCode></ListingPrice><Shipping><Amount>0</Amount><CurrencyCode>JPY</CurrencyCode></Shipping><Points><PointsNumber>0</PointsNumber><PointsMonetaryValue><Amount>0</Amount><CurrencyCode>JPY</CurrencyCode></PointsMonetaryValue></Points></PriceToEstimateFees></FeesEstimateIdentifier><Status>Success</Status></FeesEstimateResult><FeesEstimateResult><FeesEstimate><TimeOfFeesEstimation>2020-03-22T05:32:16.050Z</TimeOfFeesEstimation><TotalFeesEstimate><Amount>450.00</Amount><CurrencyCode>JPY</CurrencyCode></TotalFeesEstimate><FeeDetailList><FeeDetail><FeeAmount><Amount>450.00</Amount><CurrencyCode>JPY</CurrencyCode></FeeAmount><FinalFee><Amount>450.00</Amount><CurrencyCode>JPY</CurrencyCode></FinalFee><FeePromotion><Amount>0.00</Amount><CurrencyCode>JPY</CurrencyCode></FeePromotion><FeeType>ReferralFee</FeeType></FeeDetail><FeeDetail><FeeAmount><Amount>0.00</Amount><CurrencyCode>JPY</CurrencyCode></FeeAmount><FinalFee><Amount>0.00</Amount><CurrencyCode>JPY</CurrencyCode></FinalFee><FeePromotion><Amount>0.00</Amount><CurrencyCode>JPY</CurrencyCode></FeePromotion><FeeType>VariableClosingFee</FeeType></FeeDetail><FeeDetail><FeeAmount><Amount>0.00</Amount><CurrencyCode>JPY</CurrencyCode></FeeAmount><FinalFee><Amount>0.00</Amount><CurrencyCode>JPY</CurrencyCode></FinalFee><FeePromotion><Amount>0.00</Amount><CurrencyCode>JPY</CurrencyCode></FeePromotion><FeeType>PerItemFee</FeeType></FeeDetail></FeeDetailList></FeesEstimate><FeesEstimateIdentifier><MarketplaceId>A1VC38T7YXB528</MarketplaceId><IdType>ASIN</IdType><SellerId>A1YGAJBX0XV0O8</SellerId><SellerInputIdentifier>sfgr</SellerInputIdentifier><IsAmazonFulfilled>false</IsAmazonFulfilled><IdValue>B07DPQP9XM</IdValue><PriceToEstimateFees><ListingPrice><Amount>3000</Amount><CurrencyCode>JPY</CurrencyCode></ListingPrice><Shipping><Amount>0</Amount><CurrencyCode>JPY</CurrencyCode></Shipping><Points><PointsNumber>0</PointsNumber><PointsMonetaryValue><Amount>0</Amount><CurrencyCode>JPY</CurrencyCode></PointsMonetaryValue></Points></PriceToEstimateFees></FeesEstimateIdentifier><Status>Success</Status></FeesEstimateResult></FeesEstimateResultList></GetMyFeesEstimateResult><ResponseMetadata><RequestId>e2d11d7c-2b32-4e26-bef9-f0851a1b3bc0</RequestId></ResponseMetadata></GetMyFeesEstimateResponse>"""
        val r=tikXml.read(sourceFrom(xml),GetMyFeesEstimateResponse::class.java)
        print(r.toString())

    }

    @Test
    fun newCompetitivePrice() {
        val xml="""<?xml version="1.0"?><GetCompetitivePricingForASINResponse xmlns="http://mws.amazonservices.com/schema/Products/2011-10-01"><GetCompetitivePricingForASINResult ASIN="4860642880" status="Success"><Product xmlns:ns2="http://mws.amazonservices.com/schema/Products/2011-10-01/default.xsd"><Identifiers><MarketplaceASIN><MarketplaceId>A1VC38T7YXB528</MarketplaceId><ASIN>4860642880</ASIN></MarketplaceASIN></Identifiers><CompetitivePricing><CompetitivePrices><CompetitivePrice belongsToRequester="false" condition="New" subcondition="New"><CompetitivePriceId>1</CompetitivePriceId><Price><LandedPrice><CurrencyCode>JPY</CurrencyCode><Amount>2160.00</Amount></LandedPrice><ListingPrice><CurrencyCode>JPY</CurrencyCode><Amount>2200.00</Amount></ListingPrice><Shipping><CurrencyCode>JPY</CurrencyCode><Amount>0.00</Amount></Shipping><Points><PointsNumber>40</PointsNumber><PointsMonetaryValue><CurrencyCode>JPY</CurrencyCode><Amount>40.00</Amount></PointsMonetaryValue></Points></Price></CompetitivePrice></CompetitivePrices><NumberOfOfferListings><OfferListingCount condition="New">3</OfferListingCount><OfferListingCount condition="Used">43</OfferListingCount><OfferListingCount condition="Any">46</OfferListingCount></NumberOfOfferListings></CompetitivePricing><SalesRankings><SalesRank><ProductCategoryId>book_display_on_website</ProductCategoryId><Rank>26499</Rank></SalesRank><SalesRank><ProductCategoryId>500904</ProductCategoryId><Rank>71</Rank></SalesRank></SalesRankings></Product></GetCompetitivePricingForASINResult><GetCompetitivePricingForASINResult ASIN="477415377X" status="Success"><Product xmlns:ns2="http://mws.amazonservices.com/schema/Products/2011-10-01/default.xsd"><Identifiers><MarketplaceASIN><MarketplaceId>A1VC38T7YXB528</MarketplaceId><ASIN>477415377X</ASIN></MarketplaceASIN></Identifiers><CompetitivePricing><CompetitivePrices><CompetitivePrice belongsToRequester="false" condition="New" subcondition="New"><CompetitivePriceId>1</CompetitivePriceId><Price><LandedPrice><CurrencyCode>JPY</CurrencyCode><Amount>3465.00</Amount></LandedPrice><ListingPrice><CurrencyCode>JPY</CurrencyCode><Amount>3630.00</Amount></ListingPrice><Shipping><CurrencyCode>JPY</CurrencyCode><Amount>0.00</Amount></Shipping><Points><PointsNumber>165</PointsNumber><PointsMonetaryValue><CurrencyCode>JPY</CurrencyCode><Amount>165.00</Amount></PointsMonetaryValue></Points></Price></CompetitivePrice></CompetitivePrices><NumberOfOfferListings><OfferListingCount condition="New">3</OfferListingCount><OfferListingCount condition="Used">26</OfferListingCount><OfferListingCount condition="Any">29</OfferListingCount></NumberOfOfferListings></CompetitivePricing><SalesRankings><SalesRank><ProductCategoryId>book_display_on_website</ProductCategoryId><Rank>94610</Rank></SalesRank><SalesRank><ProductCategoryId>542584</ProductCategoryId><Rank>110</Rank></SalesRank><SalesRank><ProductCategoryId>502792</ProductCategoryId><Rank>135</Rank></SalesRank><SalesRank><ProductCategoryId>3229704051</ProductCategoryId><Rank>572</Rank></SalesRank></SalesRankings></Product></GetCompetitivePricingForASINResult><ResponseMetadata><RequestId>ab69fa4d-bb0c-4c2d-850a-704e1e658877</RequestId></ResponseMetadata></GetCompetitivePricingForASINResponse>""";
        val r=tikXml.read(sourceFrom(xml),CompetitivePriceResponse::class.java)
        print(r.toString())


    }
    @Test
    fun newProductObject(){
        val xml="""<?xml version="1.0"?>
<GetMatchingProductForIdResponse xmlns="http://mws.amazonservices.com/schema/Products/2011-10-01">
    <GetMatchingProductForIdResult Id="9784774153773" IdType="JAN" status="Success">
        <Products xmlns:ns2="http://mws.amazonservices.com/schema/Products/2011-10-01/default.xsd">
            <Product>
                <Identifiers>
                    <MarketplaceASIN>
                        <MarketplaceId>A1VC38T7YXB528</MarketplaceId>
                        <ASIN>477415377X</ASIN>
                    </MarketplaceASIN>
                </Identifiers>
                <AttributeSets>
                    <ns2:ItemAttributes xml:lang="ja-JP">
                        <ns2:Binding>単行本（ソフトカバー）</ns2:Binding>
                        <ns2:Creator Role="著">渡辺 修司</ns2:Creator>
                        <ns2:ItemDimensions>
                            <ns2:Weight Units="pounds">1.3227735720000</ns2:Weight>
                        </ns2:ItemDimensions>
                        <ns2:IsAdultProduct>false</ns2:IsAdultProduct>
                        <ns2:Label>技術評論社</ns2:Label>
                        <ns2:Languages>
                            <ns2:Language>
                                <ns2:Name>japanese</ns2:Name>
                                <ns2:Type>発行済み</ns2:Type>
                            </ns2:Language>
                            <ns2:Language>
                                <ns2:Name>japanese</ns2:Name>
                                <ns2:Type>不明</ns2:Type>
                            </ns2:Language>
                        </ns2:Languages>
                        <ns2:ListPrice>
                            <ns2:Amount>3630.00</ns2:Amount>
                            <ns2:CurrencyCode>JPY</ns2:CurrencyCode>
                        </ns2:ListPrice>
                        <ns2:Manufacturer>技術評論社</ns2:Manufacturer>
                        <ns2:NumberOfPages>480</ns2:NumberOfPages>
                        <ns2:PackageDimensions>
                            <ns2:Height Units="inches">1.1023622036</ns2:Height>
                            <ns2:Length Units="inches">8.2677165270</ns2:Length>
                            <ns2:Width Units="inches">5.9055118050</ns2:Width>
                            <ns2:Weight Units="pounds">1.46</ns2:Weight>
                        </ns2:PackageDimensions>
                        <ns2:ProductGroup>Book</ns2:ProductGroup>
                        <ns2:ProductTypeName>ABIS_BOOK</ns2:ProductTypeName>
                        <ns2:PublicationDate>2012-11-21</ns2:PublicationDate>
                        <ns2:Publisher>技術評論社</ns2:Publisher>
                        <ns2:SmallImage>
                            <ns2:URL>http://ecx.images-amazon.com/images/I/51TaOch1LJL._SL75_.jpg</ns2:URL>
                            <ns2:Height Units="pixels">75</ns2:Height>
                            <ns2:Width Units="pixels">53</ns2:Width>
                        </ns2:SmallImage>
                        <ns2:Studio>技術評論社</ns2:Studio>
                        <ns2:Title>JUnit実践入門 ~体系的に学ぶユニットテストの技法 (WEB+DB PRESS plus)</ns2:Title>
                    </ns2:ItemAttributes>
                </AttributeSets>
                <Relationships />
                <SalesRankings>
                    <SalesRank>
                        <ProductCategoryId>book_display_on_website</ProductCategoryId>
                        <Rank>54627</Rank>
                    </SalesRank>
                    <SalesRank>
                        <ProductCategoryId>502792</ProductCategoryId>
                        <Rank>92</Rank>
                    </SalesRank>
                    <SalesRank>
                        <ProductCategoryId>542584</ProductCategoryId>
                        <Rank>94</Rank>
                    </SalesRank>
                    <SalesRank>
                        <ProductCategoryId>3229704051</ProductCategoryId>
                        <Rank>424</Rank>
                    </SalesRank>
                </SalesRankings>
            </Product>
        </Products>
    </GetMatchingProductForIdResult>
    <ResponseMetadata>
        <RequestId>30508b82-6a32-4103-9f51-0a145848fe10</RequestId>
    </ResponseMetadata>
</GetMatchingProductForIdResponse>"""
        try {
            val r=tikXml.read(sourceFrom(xml),GetMatchingProductForIdResponse::class.java)
            print(r.toString())
            Truth.assertThat(r).isNotNull()
        }
        catch (ex:Exception){
            Truth.assertThat(ex).hasMessageThat().startsWith("Hello")
        }
    }
    private fun sourceFrom(xml: String): BufferedSource? {
        return Buffer().writeUtf8(xml)
    }
}