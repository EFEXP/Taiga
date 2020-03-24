package com.ebifry.appbase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.ebifry.appbase.db.CompPrice

@Dao
interface CompetitivePriceDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg compPrice: CompPrice)
}