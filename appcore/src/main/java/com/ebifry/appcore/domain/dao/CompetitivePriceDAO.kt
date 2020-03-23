package com.ebifry.appcore.domain.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ebifry.appcore.domain.entity.db.CompPrice
import com.ebifry.appcore.domain.entity.db.Ranking

@Dao
interface CompetitivePriceDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg compPrice: CompPrice)
}