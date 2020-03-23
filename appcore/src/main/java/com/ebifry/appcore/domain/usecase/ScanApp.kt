package com.ebifry.appcore.domain.usecase

import com.ebifry.appcore.domain.dao.ScannedItemDAO


interface ScanApp {
    suspend fun scanBarcode(ids:List<Long>): List<ScannedItemDAO.RetrievedItem>
}