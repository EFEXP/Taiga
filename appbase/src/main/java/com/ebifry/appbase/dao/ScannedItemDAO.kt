package com.ebifry.appbase.dao

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.room.*
import  com.ebifry.appbase.db.CompPrice
import  com.ebifry.appbase.db.DBFeeDetail
import  com.ebifry.appbase.db.Ranking
import  com.ebifry.appbase.db.ScannedItem

@Dao
abstract class ScannedItemDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract suspend fun insertAll(vararg scannedItem: ScannedItem)

    @WorkerThread
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract suspend fun insertAll(vararg ranking: Ranking)

    @WorkerThread
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract suspend fun insertAll(vararg fee: DBFeeDetail)

    @Transaction
    @Query("SELECT * FROM scanned_items ORDER BY scanned_items.date DESC")
    protected abstract fun load(): LiveData<List<RetrievedItem>>

    @WorkerThread
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract  suspend fun insertAll(vararg compPrice: CompPrice)

    @Transaction
    open suspend fun insert(
        scannedItems: List<ScannedItem>?,
        rankings: List<Ranking>?,
        fees: List<DBFeeDetail>?,
        compPrices: List<CompPrice>?
    ) {
        scannedItems?.let {
            insertAll(scannedItem = *scannedItems.toTypedArray())
        }
        rankings?.let {
            insertAll(ranking = *rankings.toTypedArray())
        }
        fees?.let {
            insertAll(fee = *fees.toTypedArray())
        }
        compPrices?.let {
            insertAll(*compPrices.toTypedArray())
        }

    }

    fun getScanHistory() = load()

    data class RetrievedItem(
        @Embedded
        val scannedItem: ScannedItem,
        @Relation(parentColumn = "asin", entityColumn = "asin")
        val ranks: List<Ranking>,
        @Relation(parentColumn = "asin", entityColumn = "asin")
        val comp: List<CompPrice>,
        @Relation(parentColumn = "asin", entityColumn = "asin")
        val fees: List<DBFeeDetail>
    ){
        override fun equals(other: Any?): Boolean {
            return when (other) {
                null -> false
                !is RetrievedItem -> {
                    false
                }
                else -> {
                    this.scannedItem.asin==other.scannedItem.asin
                }
            }

        }

        override fun hashCode(): Int {
            return scannedItem.asin.hashCode()
        }
    }



}