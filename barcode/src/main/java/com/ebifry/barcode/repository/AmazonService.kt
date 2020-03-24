package com.ebifry.barcode.repository

import com.ebifry.barcode.domain.entity.CompetitivePriceResponse
import com.ebifry.barcode.domain.entity.GetMatchingProductForIdResponse
import com.ebifry.barcode.domain.entity.GetMyFeesEstimateResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


interface AmazonService {

    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("Products/2011-10-01")
    suspend fun getMatchingProductForId(@Body body: RequestBody): GetMatchingProductForIdResponse

    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("Products/2011-10-01")
    suspend fun getCompetitivePricingForASIN(@Body body: RequestBody):CompetitivePriceResponse

    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("Products/2011-10-01")
    suspend fun getMyFeeEstimate(@Body body: RequestBody):GetMyFeesEstimateResponse
}