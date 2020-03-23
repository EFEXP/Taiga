package com.ebifry.appcore.domain

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ebifry.appcore.domain.dao.*
import com.ebifry.appcore.domain.entity.db.CompPrice
import com.ebifry.appcore.domain.entity.db.DBFeeDetail
import com.ebifry.appcore.domain.entity.db.Ranking
import com.ebifry.appcore.domain.entity.db.ScannedItem

@Database(entities = [ScannedItem::class, Ranking::class, CompPrice::class,DBFeeDetail::class], version = 1 ,exportSchema = false)
@TypeConverters(RoomTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun scannedItemDao(): ScannedItemDAO
    abstract fun rankingDao():RankingDAO
    abstract fun competitiveDao():CompetitivePriceDAO
    abstract fun feeDao():FeeDAO
}
