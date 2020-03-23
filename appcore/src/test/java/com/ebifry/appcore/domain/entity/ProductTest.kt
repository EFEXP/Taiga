package com.ebifry.appcore.domain.entity

import com.ebifry.appcore.domain.entity.db.DBFeeDetail
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
        val xml="""<?xml version="1.0"?>
<GetMatchingProductForIdResponse xmlns="http://mws.amazonservices.com/schema/Products/2011-10-01">
  <GetMatchingProductForIdResult Id="9784806141150" IdType="JAN" status="Success">
    <Products xmlns:ns2="http://mws.amazonservices.com/schema/Products/2011-10-01/default.xsd">
      <Product>
        <Identifiers>
          <MarketplaceASIN>
            <MarketplaceId>A1VC38T7YXB528</MarketplaceId>
            <ASIN>4806141151</ASIN>
          </MarketplaceASIN>
        </Identifiers>
        <AttributeSets>
          <ns2:ItemAttributes xml:lang="ja-JP">
            <ns2:Binding>単行本（ソフトカバー）</ns2:Binding>
            <ns2:Creator Role="著">中山 マコト</ns2:Creator>
            <ns2:ItemDimensions>
              <ns2:Weight Units="pounds">0.7054792384000</ns2:Weight>
            </ns2:ItemDimensions>
            <ns2:IsAdultProduct>false</ns2:IsAdultProduct>
            <ns2:Label>中経出版</ns2:Label>
            <ns2:Languages>
              <ns2:Language>
                <ns2:Name>japanese</ns2:Name>
                <ns2:Type>発行済み</ns2:Type>
              </ns2:Language>
            </ns2:Languages>
            <ns2:ListPrice>
              <ns2:Amount>1650.00</ns2:Amount>
              <ns2:CurrencyCode>JPY</ns2:CurrencyCode>
            </ns2:ListPrice>
            <ns2:Manufacturer>中経出版</ns2:Manufacturer>
            <ns2:NumberOfPages>255</ns2:NumberOfPages>
            <ns2:PackageDimensions>
              <ns2:Height Units="inches">0.7086614166</ns2:Height>
              <ns2:Length Units="inches">7.4803149530</ns2:Length>
              <ns2:Width Units="inches">5.0393700736</ns2:Width>
              <ns2:Weight Units="pounds">0.705472037362793485962466</ns2:Weight>
            </ns2:PackageDimensions>
            <ns2:PackageQuantity>1</ns2:PackageQuantity>
            <ns2:ProductGroup>Book</ns2:ProductGroup>
            <ns2:ProductTypeName>ABIS_BOOK</ns2:ProductTypeName>
            <ns2:PublicationDate>2011-07-16</ns2:PublicationDate>
            <ns2:Publisher>中経出版</ns2:Publisher>
            <ns2:SmallImage>
              <ns2:URL>http://ecx.images-amazon.com/images/I/51S3b6MecLL._SL75_.jpg</ns2:URL>
              <ns2:Height Units="pixels">75</ns2:Height>
              <ns2:Width Units="pixels">50</ns2:Width>
            </ns2:SmallImage>
            <ns2:Studio>中経出版</ns2:Studio>
            <ns2:Title>「バカ売れ」タイトルが面白いほど書ける本</ns2:Title>
          </ns2:ItemAttributes>
        </AttributeSets>
        <Relationships/>
        <SalesRankings>
          <SalesRank>
            <ProductCategoryId>book_display_on_website</ProductCategoryId>
            <Rank>377851</Rank>
          </SalesRank>
          <SalesRank>
            <ProductCategoryId>505418</ProductCategoryId>
            <Rank>560</Rank>
          </SalesRank>
        </SalesRankings>
      </Product>
    </Products>
  </GetMatchingProductForIdResult>
  <ResponseMetadata>
    <RequestId>a7018950-c57e-48ec-80e0-de8f809bf2c1</RequestId>
  </ResponseMetadata>
</GetMatchingProductForIdResponse>"""
        try {
            val r=tikXml.read(sourceFrom(xml),GetMatchingProductForIdResponse::class.java)
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