package com.ebifry.appcore.domain.entity.db

import androidx.room.*

@Entity(
    tableName = "ranking",
    foreignKeys = [ForeignKey(entity = ScannedItem::class,parentColumns = arrayOf("asin"),childColumns = arrayOf("asin"), onDelete = ForeignKey.CASCADE)],
    indices = [Index(value = ["asin"], unique = false)]
)
data class Ranking(
    @PrimaryKey(autoGenerate = true)
    val id:Long?,
    val category:String,
    val ranking: Long,
    val asin :String){
    override fun equals(other: Any?): Boolean {
        if (other ==null)
            return false
        return if (other is Ranking) {
            (category == other.category && ranking == other.ranking && asin == other.asin)
        } else{
            false
        }
    }
}