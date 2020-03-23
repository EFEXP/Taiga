package com.ebifry.appcore.repository

import com.ebifry.appcore.di.AmazonAuthModule
import com.ebifry.appcore.domain.entity.CompetitivePriceResponse
import com.ebifry.appcore.domain.entity.GetMatchingProductForIdResponse
import com.ebifry.appcore.domain.entity.GetMyFeesEstimateResponse
import com.ebifry.appcore.domain.repository.AmazonRepository
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class AmazonRepositoryImpl @Inject constructor(private val service: AmazonService) : AmazonRepository {
    private val json: MediaType = "application/x-www-form-urlencoded".toMediaType()


    override suspend fun getMatchingProductForId(list: List<Long>): GetMatchingProductForIdResponse {
        val r=RequestBuilder("GetMatchingProductForId",AmazonAuthModule())
            .addIdList(list,"JAN")
            .add("MarketplaceId","A1VC38T7YXB528")
            .build()
        return service.getMatchingProductForId(r.toRequestBody(json))
    }

    override suspend fun competitivePriceResponse(list: List<String>): CompetitivePriceResponse {
        val r=RequestBuilder("GetCompetitivePricingForASIN",AmazonAuthModule())
            .addASINList(list)
            .add("MarketplaceId","A1VC38T7YXB528")
            .build()
        return service.getCompetitivePricingForASIN(r.toRequestBody(json))
    }

    override suspend fun getMyFee(list: List<String>,isFBA:Boolean,price:List<Double>): GetMyFeesEstimateResponse {
        val r=RequestBuilder("GetMyFeesEstimate",AmazonAuthModule())
            .addFeeParams(list,price,isFBA)
            .build()
        return service.getMyFeeEstimate(r.toRequestBody(json))
    }


}