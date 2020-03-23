package com.ebifry.appcore.domain.usecaseimpl

import android.accounts.NetworkErrorException
import com.ebifry.appcore.domain.AppDatabase
import com.ebifry.appcore.domain.dao.ScannedItemDAO
import com.ebifry.appcore.domain.repository.AmazonRepository
import com.ebifry.appcore.domain.repository.DataRepository
import com.ebifry.appcore.domain.usecase.ScanApp
import javax.inject.Inject

class ScanAppImpl @Inject constructor(private val amazonRepository: AmazonRepository,private val dataRepository: DataRepository) : ScanApp{

    override suspend fun scanBarcode(ids: List<Long>): List<ScannedItemDAO.RetrievedItem> {
        val result=amazonRepository.getMatchingProductForId(ids)
        if (result.status=="Success")
        {
            dataRepository.dispatchProduct(result.Products,ids)
            val feeResult=amazonRepository.getMyFee(result.Products.map { it.asin },true,result.Products.map { it.listPrice })
            feeResult.feesEstimateResultList.forEach {
                if(it.status=="Success"){
                    dataRepository.dispatchFees(it.feeDetailList,it.asin)
                }
                else{
                    throw NetworkErrorException("ID must not be null.")
                }
            }

            val c= amazonRepository.competitivePriceResponse(result.Products.map { it.asin })
            c.competitivePricingForASINResult.forEach {
                if(it.status=="Success"){
                    dataRepository.dispatchCompetitive(it.prices,it.asin)
                }
                else{
                    throw NetworkErrorException("ID must not be null.")
                }
            }
            return dataRepository.getScanHistory()

        }
        else{
            throw NetworkErrorException("ID must not be null.")
        }

    }






}