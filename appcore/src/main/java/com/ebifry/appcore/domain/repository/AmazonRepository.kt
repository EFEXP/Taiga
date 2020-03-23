package com.ebifry.appcore.domain.repository

import com.ebifry.appcore.domain.entity.CompetitivePriceResponse
import com.ebifry.appcore.domain.entity.GetMatchingProductForIdResponse
import com.ebifry.appcore.domain.entity.GetMyFeesEstimateResponse


interface AmazonRepository {
    suspend fun getMatchingProductForId(list: List<Long>): GetMatchingProductForIdResponse
    suspend fun competitivePriceResponse(list: List<String>): CompetitivePriceResponse
    suspend fun getMyFee(list: List<String>,isFBA:Boolean,price:List<Double>): GetMyFeesEstimateResponse

}