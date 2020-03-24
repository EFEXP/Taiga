package com.ebifry.appbase.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import java.util.*

@Entity(
    tableName = "fee",
    primaryKeys = arrayOf("asin","feeType","date"),
    foreignKeys = [ForeignKey(entity = ScannedItem::class,parentColumns = arrayOf("asin"),childColumns = arrayOf("asin"), onDelete = ForeignKey.CASCADE)],
    indices = [Index(value = ["asin"], unique = false)]
)
data class DBFeeDetail(
    val date:Date,
    val totalAmount:Int,
    val feeType:String,
    val asin:String
)

