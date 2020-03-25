package com.ebifry.barcode.domain.repository

import androidx.lifecycle.LiveData
import com.ebifry.appbase.dao.ScannedItemDAO
import com.ebifry.barcode.domain.entity.CompetitivePrice
import com.ebifry.barcode.domain.entity.FeeDetail
import com.ebifry.barcode.domain.entity.Product

interface DataRepository {
    suspend fun dispatchProducts(products: List<Pair<Product,Long>>,compList:List<Pair<CompetitivePrice,String>>?,feeDetailList:List<Pair<FeeDetail,String>>?)
    fun getScanHistory(): LiveData<List<ScannedItemDAO.RetrievedItem>>
}