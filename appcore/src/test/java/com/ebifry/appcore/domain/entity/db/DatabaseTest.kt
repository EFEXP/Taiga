package com.ebifry.appcore.domain.entity.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ebifry.appcore.domain.AppDatabase
import com.ebifry.appcore.domain.dao.RankingDAO
import com.ebifry.appcore.domain.dao.ScannedItemDAO
import com.ebifry.appcore.domain.entity.*
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
        db=Room.inMemoryDatabaseBuilder(context,AppDatabase::class.java).build()
        rankDao=db.rankingDao()
        scannedItemDAO=db.scannedItemDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeUserAndReadInList() {
        val p= arrayListOf(Product("asinx1","Jes1","hfweaf1", arrayListOf(SalesRank("cate1-1",100),SalesRank("cate1-2",20)),
            PackageDimensions(1.0,1.0,1.0,29.0),"","",0.0))
        val s= p.map {
            ScannedItem(
                date = Date(),
                shipmentCharge = 0,
                storageChargePerMonth = 0.0,
                asin = it.asin,
                name = it.name,
                url = it.imageURL,
                origin = 0L,
                restrictedCategory= false,
                restrictedMaker= false,
                category = ""
            )
        }
        val a= arrayListOf<Ranking>()
        p.forEach{a.addAll(it.salesRankings.map {salesRank ->
            Ranking(
                null,
                salesRank.productCategory,
                salesRank.rank,
                it.asin
            )
        } )}

        CoroutineScope(Dispatchers.IO).launch {
            scannedItemDAO.insertAll(*s.toTypedArray())
            rankDao.insertAll(*a.toTypedArray())
            val result= scannedItemDAO.load()
            result.forEachIndexed {i,item->
                println(item)
                Truth.assertThat(item).isEqualTo(a[i])
            }
        }


    }
}