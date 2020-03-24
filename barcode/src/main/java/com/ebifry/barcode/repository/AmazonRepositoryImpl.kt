package com.ebifry.barcode.repository

import com.ebifry.barcode.di.AmazonAuthModule
import com.ebifry.barcode.domain.entity.CompetitivePriceResponse
import com.ebifry.barcode.domain.entity.GetMatchingProductForIdResponse
import com.ebifry.barcode.domain.entity.GetMyFeesEstimateResponse
import com.ebifry.barcode.domain.repository.AmazonRepository
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class AmazonRepositoryImpl @Inject constructor(private val service: AmazonService) : AmazonRepository {
    private val json: MediaType = "application/x-www-form-urlencoded".toMediaType()


    override suspend fun getMatchingProductForId(list: List<Long>): GetMatchingProductForIdResponse {
        val r=RequestBuilder(AmazonAuthModule())
            .addIdList(list,"JAN")
            .add("MarketplaceId","A1VC38T7YXB528")
            .add("Action","GetMatchingProductForId")
            .build()
        return service.getMatchingProductForId(r.toRequestBody(json))
    }

    override suspend fun competitivePriceResponse(list: List<String>): CompetitivePriceResponse {
        val r=RequestBuilder(AmazonAuthModule())
            .addASINList(list)
            .add("Action","GetCompetitivePricingForASIN")
            .add("MarketplaceId","A1VC38T7YXB528")
            .build()
        return service.getCompetitivePricingForASIN(r.toRequestBody(json))
    }

    override suspend fun getMyFee(list: List<String>,isFBA:Boolean,price:List<Double>): GetMyFeesEstimateResponse {
        val r=RequestBuilder(AmazonAuthModule())
            .addFeeParams(list,price,isFBA)
            .add("Action","GetMyFeesEstimate")
            .build()
        return service.getMyFeeEstimate(r.toRequestBody(json))
    }


}