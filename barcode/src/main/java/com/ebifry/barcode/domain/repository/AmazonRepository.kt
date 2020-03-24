package com.ebifry.barcode.domain.repository

import com.ebifry.barcode.domain.entity.CompetitivePriceResponse
import com.ebifry.barcode.domain.entity.GetMatchingProductForIdResponse
import com.ebifry.barcode.domain.entity.GetMyFeesEstimateResponse


interface AmazonRepository {
    suspend fun getMatchingProductForId(list: List<Long>): GetMatchingProductForIdResponse
    suspend fun competitivePriceResponse(list: List<String>): CompetitivePriceResponse
    suspend fun getMyFee(list: List<String>,isFBA:Boolean,price:List<Double>): GetMyFeesEstimateResponse

}