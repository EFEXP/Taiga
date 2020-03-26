package com.ebifry.appbase.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
@Entity(
    tableName = "offers",
    primaryKeys = ["asin"],
    foreignKeys = [ForeignKey(entity = ScannedItem::class,parentColumns = arrayOf("asin"),childColumns = arrayOf("asin"), onDelete = ForeignKey.CASCADE)],
    indices = [Index(value = ["asin"], unique = false)]
)
data class Offers(
    val asin:String,
    val newOffer:Int,
    val usedOffer:Int,
    val anyOffer: Int
)
