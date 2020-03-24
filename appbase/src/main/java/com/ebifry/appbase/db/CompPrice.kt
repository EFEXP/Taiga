package com.ebifry.appbase.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    primaryKeys = ["id", "asin"],
    tableName = "comp_price",
    foreignKeys = [ForeignKey(entity = ScannedItem::class,parentColumns = arrayOf("asin"),childColumns = arrayOf("asin"), onDelete = ForeignKey.CASCADE)],
    indices = [Index(value = ["asin"], unique = false)]
)
data class CompPrice(
    val id:Int,
    val condition:String,
    val subcondition: String?,
    val asin :String,
    val listing:Double,
    val landed:Double,
    val shipping:Double
    )

