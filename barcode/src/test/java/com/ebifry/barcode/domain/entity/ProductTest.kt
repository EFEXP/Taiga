package com.ebifry.barcode.domain.entity

import com.google.common.truth.Truth
import com.tickaroo.tikxml.TikXml
import okio.Buffer
import okio.BufferedSource
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
        val xml="""<GetMyFeesEstimateResponse xmlns="http://mws.amazonservices.com/schema/Products/2011-10-01">
  <GetMyFeesEstimateResult>
    <FeesEstimateResultList>
      <FeesEstimateResult>
        <FeesEstimate>
          <TimeOfFeesEstimation>2020-03-23T13:20:21.582Z</TimeOfFeesEstimation>
          <TotalFeesEstimate>
            <Amount>3319.00</Amount>
            <CurrencyCode>JPY</CurrencyCode>
          </TotalFeesEstimate>
          <FeeDetailList>
            <FeeDetail>
              <FeeAmount>
                <Amount>2915.00</Amount>
                <CurrencyCode>JPY</CurrencyCode>
              </FeeAmount>
              <FinalFee>
                <Amount>2915.00</Amount>
                <CurrencyCode>JPY</CurrencyCode>
              </FinalFee>
              <FeePromotion>
                <Amount>0.00</Amount>
                <CurrencyCode>JPY</CurrencyCode>
              </FeePromotion>
              <FeeType>ReferralFee</FeeType>
            </FeeDetail>
            <FeeDetail>
              <FeeAmount>
                <Amount>0.00</Amount>
                <CurrencyCode>JPY</CurrencyCode>
              </FeeAmount>
              <FinalFee>
                <Amount>0.00</Amount>
                <CurrencyCode>JPY</CurrencyCode>
              </FinalFee>
              <FeePromotion>
                <Amount>0.00</Amount>
                <CurrencyCode>JPY</CurrencyCode>
              </FeePromotion>
              <FeeType>VariableClosingFee</FeeType>
            </FeeDetail>
            <FeeDetail>
              <FeeAmount>
                <Amount>0.00</Amount>
                <CurrencyCode>JPY</CurrencyCode>
              </FeeAmount>
              <FinalFee>
                <Amount>0.00</Amount>
                <CurrencyCode>JPY</CurrencyCode>
              </FinalFee>
              <FeePromotion>
                <Amount>0.00</Amount>
                <CurrencyCode>JPY</CurrencyCode>
              </FeePromotion>
              <FeeType>PerItemFee</FeeType>
            </FeeDetail>
            <FeeDetail>
              <FeeAmount>
                <Amount>404.00</Amount>
                <CurrencyCode>JPY</CurrencyCode>
              </FeeAmount>
              <FinalFee>
                <Amount>404.00</Amount>
                <CurrencyCode>JPY</CurrencyCode>
              </FinalFee>
              <FeePromotion>
                <Amount>0.00</Amount>
                <CurrencyCode>JPY</CurrencyCode>
              </FeePromotion>
              <FeeType>FBAFees</FeeType>
              <IncludedFeeDetailList>
                <FeeDetail>
                  <FeeAmount>
                    <Amount>404.00</Amount>
                    <CurrencyCode>JPY</CurrencyCode>
                  </FeeAmount>
                  <FinalFee>
                    <Amount>404.00</Amount>
                    <CurrencyCode>JPY</CurrencyCode>
                  </FinalFee>
                  <FeePromotion>
                    <Amount>0.00</Amount>
                    <CurrencyCode>JPY</CurrencyCode>
                  </FeePromotion>
                  <FeeType>FBAPickAndPack</FeeType>
                </FeeDetail>
              </IncludedFeeDetailList>
            </FeeDetail>
          </FeeDetailList>
        </FeesEstimate>
        <FeesEstimateIdentifier>
          <MarketplaceId>A1VC38T7YXB528</MarketplaceId>
          <IdType>ASIN</IdType>
          <SellerId>A1YGAJBX0XV0O8</SellerId>
          <SellerInputIdentifier>da</SellerInputIdentifier>
          <IsAmazonFulfilled>true</IsAmazonFulfilled>
          <IdValue>B07XL7VGKH</IdValue>
          <PriceToEstimateFees>
            <ListingPrice>
              <Amount>36440</Amount>
              <CurrencyCode>JPY</CurrencyCode>
            </ListingPrice>
            <Shipping>
              <Amount>0</Amount>
              <CurrencyCode>JPY</CurrencyCode>
            </Shipping>
            <Points>
              <PointsNumber>0</PointsNumber>
              <PointsMonetaryValue>
                <Amount>0</Amount>
                <CurrencyCode>JPY</CurrencyCode>
              </PointsMonetaryValue>
            </Points>
          </PriceToEstimateFees>
        </FeesEstimateIdentifier>
        <Status>Success</Status>
      </FeesEstimateResult>
    </FeesEstimateResultList>
  </GetMyFeesEstimateResult>
  <ResponseMetadata>
    <RequestId>c3e2c4fe-896d-4153-9bb0-b99522923c32</RequestId>
  </ResponseMetadata>
</GetMyFeesEstimateResponse>"""
        val r=tikXml.read(sourceFrom(xml),GetMyFeesEstimateResponse::class.java)
        print(r.toString())
    }

    @Test
    fun newCompetitivePrice() {
        val xml="""<?xml version="1.0"?><GetCompetitivePricingForASINResponse xmlns="http://mws.amazonservices.com/schema/Products/2011-10-01"><GetCompetitivePricingForASINResult ASIN="4800720729" status="Success"><Product xmlns:ns2="http://mws.amazonservices.com/schema/Products/2011-10-01/default.xsd"><Identifiers><MarketplaceASIN><MarketplaceId>A1VC38T7YXB528</MarketplaceId><ASIN>4800720729</ASIN></MarketplaceASIN></Identifiers><CompetitivePricing><CompetitivePrices><CompetitivePrice belongsToRequester="false" condition="New" subcondition="New"><CompetitivePriceId>1</CompetitivePriceId><Price><LandedPrice><CurrencyCode>JPY</CurrencyCode><Amount>1708.00</Amount></LandedPrice><ListingPrice><CurrencyCode>JPY</CurrencyCode><Amount>1738.00</Amount></ListingPrice><Shipping><CurrencyCode>JPY</CurrencyCode><Amount>0.00</Amount></Shipping><Points><PointsNumber>30</PointsNumber><PointsMonetaryValue><CurrencyCode>JPY</CurrencyCode><Amount>30.00</Amount></PointsMonetaryValue></Points></Price></CompetitivePrice></CompetitivePrices><NumberOfOfferListings><OfferListingCount condition="New">11</OfferListingCount><OfferListingCount condition="Used">14</OfferListingCount><OfferListingCount condition="Any">25</OfferListingCount></NumberOfOfferListings></CompetitivePricing><SalesRankings><SalesRank><ProductCategoryId>book_display_on_website</ProductCategoryId><Rank>8171</Rank></SalesRank><SalesRank><ProductCategoryId>516238</ProductCategoryId><Rank>11</Rank></SalesRank></SalesRankings></Product></GetCompetitivePricingForASINResult><ResponseMetadata><RequestId>67dc0fff-d48a-49ac-b466-c35cdc8e360e</RequestId></ResponseMetadata></GetCompetitivePricingForASINResponse>"""
        try {
            val r=tikXml.read(sourceFrom(xml),CompetitivePriceResponse::class.java)
            print(r.toString())
            Truth.assertThat(r).isNotNull()
        }
        catch (ex:Exception){
            ex.printStackTrace()
            Truth.assertThat(ex).hasMessageThat().startsWith("Hello")
        }
    }
    private fun sourceFrom(xml: String): BufferedSource? {
        return Buffer().writeUtf8(xml)
    }
}