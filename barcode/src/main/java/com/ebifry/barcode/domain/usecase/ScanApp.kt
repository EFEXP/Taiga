package com.ebifry.barcode.domain.usecase

import androidx.lifecycle.LiveData
import com.ebifry.appbase.dao.ScannedItemDAO


interface ScanApp {
    suspend fun scanBarcode(ids:List<Long>,isFBA:Boolean,isMinorSeller:Boolean)
    fun historyView(): LiveData<List<ScannedItemDAO.RetrievedItem>>
}