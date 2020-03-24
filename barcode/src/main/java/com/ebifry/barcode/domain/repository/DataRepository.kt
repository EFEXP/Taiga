package com.ebifry.barcode.domain.repository

import com.ebifry.appbase.dao.ScannedItemDAO
import com.ebifry.barcode.domain.entity.CompetitivePrice
import com.ebifry.barcode.domain.entity.FeeDetail
import com.ebifry.barcode.domain.entity.Product

interface DataRepository {
    suspend fun dispatchProduct(list:List<Product>,jan:Long)
    suspend fun dispatchCompetitive(list:List<CompetitivePrice>,asin:String)
    suspend fun getScanHistory():List<ScannedItemDAO.RetrievedItem>
    suspend fun dispatchFees(list:List<FeeDetail>,asin:String)
}