package com.ebifry.appcore.domain.dao

import androidx.room.*
import com.ebifry.appcore.domain.entity.db.CompPrice
import com.ebifry.appcore.domain.entity.db.DBFeeDetail
import com.ebifry.appcore.domain.entity.db.Ranking
import com.ebifry.appcore.domain.entity.db.ScannedItem

@Dao
interface ScannedItemDAO{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg scannedItem: ScannedItem)

    @Transaction
    @Query("SELECT * FROM scanneditem")
    suspend fun load():List<RetrievedItem>

    data class RetrievedItem(
        @Embedded
        val scannedItem: ScannedItem,
        @Relation(parentColumn = "asin",entityColumn = "asin")
        val ranks:List<Ranking>,
        @Relation(parentColumn = "asin",entityColumn = "asin")
        val comp:List<CompPrice>,
        @Relation(parentColumn = "asin",entityColumn = "asin")
        val fees:List<DBFeeDetail>
    )
}