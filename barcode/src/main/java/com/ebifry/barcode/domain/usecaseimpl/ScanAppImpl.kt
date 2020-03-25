package com.ebifry.barcode.domain.usecaseimpl

import android.util.Log
import androidx.lifecycle.LiveData
import com.ebifry.appbase.dao.ScannedItemDAO
import com.ebifry.barcode.domain.entity.CompetitivePrice
import com.ebifry.barcode.domain.entity.FeeDetail
import com.ebifry.barcode.domain.entity.Product
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
        val productList:MutableList<Pair<Product,Long>> = arrayListOf()
        successQuery.map {Pair(it.products,it.sentId) }.forEach {
            it.first.forEach {product->
                productList.add(Pair(product,it.second))
            }
        }
        val competitivePriceList:MutableList<Pair<CompetitivePrice,String>> = arrayListOf()
        val competitiveResult =
            amazonRepository.competitivePriceResponse(hasListPriceProducts.map { it.asin })
        val r = competitiveResult.competitivePricingForASINResult.filter { it.status == "Success" }
        r.map { Pair(it.prices,it.asin) }.forEach {
            it.first.forEach {price->
                competitivePriceList.add(Pair(price,it.second))
            }
        }
        val feesList:MutableList<Pair<FeeDetail,String>> = arrayListOf()
        r.forEach {
            try {
            val feeResult = amazonRepository.getMyFee(arrayListOf(it.asin), isFBA, arrayListOf(it.prices.first().price.listing))
            feeResult.feesEstimateResultList.filter {w-> w.status == "Success" }.map {fee->
                Pair(fee.feeDetailList,fee.asin)
            }.forEach {pair->
                pair.first.forEach{d->
                    feesList.add(Pair(d,pair.second))
                }
            }
            }
            catch (ex:Exception){
                Log.e("Hello",ex.localizedMessage!!)
            }
        }
        dataRepository.dispatchProducts(productList,competitivePriceList,feesList)

    }

    override  fun historyView(): LiveData<List<ScannedItemDAO.RetrievedItem>> {
       return dataRepository.getScanHistory()
    }


}
