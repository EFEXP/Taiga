package com.ebifry.appcore.domain.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.ebifry.appcore.domain.entity.db.DBFeeDetail
import com.ebifry.appcore.domain.entity.db.Ranking
@Dao
interface FeeDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg fee: DBFeeDetail)
}