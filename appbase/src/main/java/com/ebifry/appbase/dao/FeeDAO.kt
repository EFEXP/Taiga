package com.ebifry.appbase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.ebifry.appbase.db.DBFeeDetail

@Dao
interface FeeDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg fee: DBFeeDetail)
}