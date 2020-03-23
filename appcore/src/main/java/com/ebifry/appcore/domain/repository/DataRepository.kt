package com.ebifry.appcore.domain.repository

import com.ebifry.appcore.domain.dao.ScannedItemDAO
import com.ebifry.appcore.domain.entity.CompetitivePrice
import com.ebifry.appcore.domain.entity.FeeDetail
import com.ebifry.appcore.domain.entity.Product

interface DataRepository {
    suspend fun dispatchProduct(list:List<Product>,jan:Long)
    suspend fun dispatchCompetitive(list:List<CompetitivePrice>,asin:String)
    suspend fun getScanHistory():List<ScannedItemDAO.RetrievedItem>
    suspend fun dispatchFees(list:List<FeeDetail>,asin:String)
}