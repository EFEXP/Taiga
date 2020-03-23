package com.ebifry.appcore.repository

import com.ebifry.appcore.domain.AppDatabase
import com.ebifry.appcore.domain.dao.ScannedItemDAO
import com.ebifry.appcore.domain.entity.CompetitivePrice
import com.ebifry.appcore.domain.entity.FeeDetail
import com.ebifry.appcore.domain.entity.Product
import com.ebifry.appcore.domain.entity.db.*
import com.ebifry.appcore.domain.repository.DataRepository
import java.util.*
import javax.inject.Inject
import kotlin.math.roundToInt

class DataRepositoryImpl @Inject constructor(private val db:AppDatabase):DataRepository {
    override suspend fun dispatchProduct(products: List<Product>,jan:Long) {
        val scannedItems= products.mapIndexed {i,it->
            val today=Date()
            ScannedItem(
                today,
                it.asin,
                jan,
                it.name?:"不明",
                it.imageURL?.replace("._SL75_","._SL350_")?:"",
                restricted.contains(it.manufacturer),
                restrictedCategory.contains(it.category),
                categoryConst[it.category]?:"UNKNOWN"
            )
        }.toTypedArray()

        db.scannedItemDao().insertAll(*scannedItems)

        val a=arrayListOf<Ranking>()
        products.filter { it.salesRankings!=null }.forEach {
                product->
            a.addAll(product.salesRankings!!.map {
                Ranking(
                    id = null,
                    asin = product.asin,
                    category = it.productCategory,
                    ranking = it.rank
                )
            })

        }
        db.rankingDao().insertAll(*a.toTypedArray())
    }
    override suspend fun dispatchCompetitive(list:List<CompetitivePrice>,asin:String){
        val l=list.map { CompPrice(it.id,it.condition,it.subcondition,asin,it.price.listing,it.price.landed,it.price.shipping) }.toTypedArray()
        db.competitiveDao().insertAll(*l)
    }

    override suspend fun dispatchFees(list:List<FeeDetail>,asin:String){
        val l=list.map { DBFeeDetail(Date(),it.totalAmount.roundToInt(),it.feeType,asin) }.toTypedArray()
        db.feeDao().insertAll(*l)
    }


    override suspend fun getScanHistory():List<ScannedItemDAO.RetrievedItem> {
        return db.scannedItemDao().load()
    }
    

}