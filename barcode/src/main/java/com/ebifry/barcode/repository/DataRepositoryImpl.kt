package com.ebifry.barcode.repository

import androidx.lifecycle.LiveData
import com.ebifry.appbase.AppDatabase
import com.ebifry.appbase.dao.ScannedItemDAO
import com.ebifry.appbase.db.CompPrice
import com.ebifry.appbase.db.DBFeeDetail
import com.ebifry.appbase.db.Ranking
import com.ebifry.appbase.db.ScannedItem
import com.ebifry.barcode.domain.entity.CompetitivePrice
import com.ebifry.barcode.domain.entity.FeeDetail
import com.ebifry.barcode.domain.entity.Product
import com.ebifry.barcode.domain.repository.DataRepository
import java.util.*
import javax.inject.Inject
import kotlin.math.roundToInt

class DataRepositoryImpl @Inject constructor(private val db: AppDatabase):DataRepository {


    override suspend fun dispatchProducts(
        products: List<Pair<Product, Long>>,
        compList: List<Pair<CompetitivePrice, String>>?,
        feeDetailList: List<Pair<FeeDetail, String>>?
    ) {
        val dbCompList=compList?.map {
            CompPrice(
                it.first.id,
                it.first.condition,
                it.first.subcondition,
                it.second,
                it.first.price.listing,
                it.first.price.landed,
                it.first.price.shipping
            )
        }
        val dbFeesList=feeDetailList?.map {
            DBFeeDetail(
                Date(),
                it.first.totalAmount.roundToInt(),
                it.first.feeType,
                it.second
            )
        }


        val scannedItems= products.map {
            val today=Date()
            ScannedItem(
                today,
                it.first.asin,
                it.second,
                it.first.name ?: "不明",
                it.first.imageURL?.replace("._SL75_", "._SL350_") ?: "",
                restricted.contains(it.first.manufacturer),
                restrictedCategory.contains(it.first.category),
                categoryConst[it.first.category] ?: "UNKNOWN"
            )
        }

        val a=arrayListOf<Ranking>()
        products.filter { it.first.salesRankings!=null }.forEach {
                product->
            a.addAll(product.first.salesRankings!!.map {
                Ranking(
                    id = null,
                    asin = product.first.asin,
                    category = it.productCategory,
                    ranking = it.rank
                )
            })
        }
        db.scannedItemDao().insert(scannedItems,a,dbFeesList,dbCompList)

    }


    override fun getScanHistory(): LiveData<List<ScannedItemDAO.RetrievedItem>> {
        return db.scannedItemDao().getScanHistory()
    }
    

}