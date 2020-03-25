package com.ebifry.appbase

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ebifry.appbase.dao.RankingDAO
import com.ebifry.appbase.dao.ScannedItemDAO
import com.ebifry.appbase.db.Ranking
import com.ebifry.appbase.db.ScannedItem
import com.google.common.truth.Truth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.*

@RunWith(AndroidJUnit4::class)
class DatabaseTest {
    private lateinit var rankDao: RankingDAO
    private lateinit var db: AppDatabase
    private lateinit var scannedItemDAO: ScannedItemDAO
    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db=Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        rankDao=db.rankingDao()
        scannedItemDAO=db.scannedItemDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun scannedItemAutoEncoder() {
        val scannedItems= arrayListOf(
            ScannedItem(Date(),"abc",4732819,"abc","fkj",false,false,"BOOK")
            ,ScannedItem(Date(),"abcd",4732819,"abc","fkj",false,false,"BOOK")
        )

        CoroutineScope(Dispatchers.IO).launch {
            db.scannedItemDao().insertAll(*scannedItems.toTypedArray())
            val actual=db.scannedItemDao().load()
            Truth.assertThat(actual).isEqualTo(scannedItems)
        }

    }
}