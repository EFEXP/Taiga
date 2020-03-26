package com.ebifry.appbase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ebifry.appbase.dao.*
import com.ebifry.appbase.db.*

@Database(entities = [ScannedItem::class, Ranking::class, CompPrice::class, DBFeeDetail::class,Offers::class], version = 1 ,exportSchema = false)
@TypeConverters(RoomTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun scannedItemDao(): ScannedItemDAO
}
