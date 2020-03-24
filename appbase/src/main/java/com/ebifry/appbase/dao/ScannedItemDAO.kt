package com.ebifry.appbase.dao

import androidx.room.*
import  com.ebifry.appbase.db.CompPrice
import  com.ebifry.appbase.db.DBFeeDetail
import  com.ebifry.appbase.db.Ranking
import  com.ebifry.appbase.db.ScannedItem

@Dao
interface ScannedItemDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg scannedItem: ScannedItem)

    @Transaction
    @Query("SELECT * FROM scanneditem")
    suspend fun load(): List<RetrievedItem>

    data class RetrievedItem(
        @Embedded
        val scannedItem: ScannedItem,
        @Relation(parentColumn = "asin", entityColumn = "asin")
        val ranks: List<Ranking>,
        @Relation(parentColumn = "asin", entityColumn = "asin")
        val comp: List<CompPrice>,
        @Relation(parentColumn = "asin", entityColumn = "asin")
        val fees: List<DBFeeDetail>
    )
}