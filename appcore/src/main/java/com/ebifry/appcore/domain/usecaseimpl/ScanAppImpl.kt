package com.ebifry.appcore.domain.usecaseimpl

import android.accounts.NetworkErrorException
import android.util.Log
import com.ebifry.appcore.domain.AppDatabase
import com.ebifry.appcore.domain.dao.ScannedItemDAO
import com.ebifry.appcore.domain.repository.AmazonRepository
import com.ebifry.appcore.domain.repository.DataRepository
import com.ebifry.appcore.domain.usecase.ScanApp
import java.lang.Exception
import javax.inject.Inject

class ScanAppImpl @Inject constructor(
    private val amazonRepository: AmazonRepository,
    private val dataRepository: DataRepository
) : ScanApp {

    override suspend fun scanBarcode(ids: List<Long>): List<ScannedItemDAO.RetrievedItem> {
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
            val feeResult = amazonRepository.getMyFee(arrayListOf(it.asin), true, arrayListOf(it.prices.first().price.listing))
            feeResult.feesEstimateResultList.filter {w-> w.status == "Success" }.forEach {w->
                dataRepository.dispatchFees(w.feeDetailList, it.asin)
            }}
            catch (ex:Exception){
                Log.e("Hello",ex.localizedMessage!!)
            }
        }
        return dataRepository.getScanHistory()
    }




}
