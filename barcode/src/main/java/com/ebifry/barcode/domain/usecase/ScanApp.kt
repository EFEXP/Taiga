package com.ebifry.barcode.domain.usecase

import com.ebifry.appbase.dao.ScannedItemDAO


interface ScanApp {
    suspend fun scanBarcode(ids:List<Long>,isFBA:Boolean,isMinorSeller:Boolean)
    suspend fun historyView(): List<ScannedItemDAO.RetrievedItem>
}