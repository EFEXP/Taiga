package com.ebifry.appbase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ebifry.appbase.dao.*
import com.ebifry.appbase.db.CompPrice
import com.ebifry.appbase.db.DBFeeDetail
import com.ebifry.appbase.db.Ranking
import com.ebifry.appbase.db.ScannedItem

@Database(entities = [ScannedItem::class, Ranking::class, CompPrice::class, DBFeeDetail::class], version = 1 ,exportSchema = false)
@TypeConverters(RoomTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun scannedItemDao(): ScannedItemDAO
}
