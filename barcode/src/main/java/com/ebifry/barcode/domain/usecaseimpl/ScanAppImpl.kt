package com.ebifry.barcode.domain.usecaseimpl

import android.util.Log
import com.ebifry.appbase.dao.ScannedItemDAO
import com.ebifry.barcode.domain.repository.AmazonRepository
import com.ebifry.barcode.domain.repository.DataRepository
import com.ebifry.barcode.domain.usecase.ScanApp
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class ScanAppImpl @Inject constructor(
    private val amazonRepository: AmazonRepository,
    private val dataRepository: DataRepository
) : ScanApp {
    override suspend fun scanBarcode(ids: List<Long>,isFBA:Boolean,isMinorSeller:Boolean) {
        val result = amazonRepository.getMatchingProductForId(ids)
        val successQuery = result.matchingProductForIdResult.filter { it.status == "Success" }
        val hasListPriceProducts = result.matchingProductForIdResult.map { it.products }.flatten()
        successQuery.forEach {
            dataRepository.dispatchProduct(it.products, it.sentId)
        }
        val competitiveResult =
            amazonRepository.competitivePriceResponse(hasListPriceProducts.map { it.asin })
        val r = competitiveResult.competitivePricingForASINResult.filter { it.status == "Success" }
        r.forEach {
            dataRepository.dispatchCompetitive(it.prices, it.asin)
        }
        r.forEach {
            try {
            val feeResult = amazonRepository.getMyFee(arrayListOf(it.asin), isFBA, arrayListOf(it.prices.first().price.listing))
            feeResult.feesEstimateResultList.filter {w-> w.status == "Success" }.forEach {w->
                dataRepository.dispatchFees(w.feeDetailList, it.asin)
            }}
            catch (ex:Exception){
                Log.e("Hello",ex.localizedMessage!!)
            }
        }

    }

    override suspend fun historyView(): List<ScannedItemDAO.RetrievedItem> {
       return dataRepository.getScanHistory()
    }


}
