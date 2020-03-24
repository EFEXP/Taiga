package com.ebifry.appbase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import  com.ebifry.appbase.db.Ranking

@Dao
interface RankingDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg ranking: Ranking)
}