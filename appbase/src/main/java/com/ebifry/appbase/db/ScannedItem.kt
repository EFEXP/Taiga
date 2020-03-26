package com.ebifry.appbase.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "scanned_items")
data class ScannedItem(
    val date: Date,
    @PrimaryKey
    val asin: String,
    val origin:Long,
    val name: String,
    val url: String,
    val restrictedMaker:Boolean,
    val restrictedCategory: Boolean,
    val category:String
)
