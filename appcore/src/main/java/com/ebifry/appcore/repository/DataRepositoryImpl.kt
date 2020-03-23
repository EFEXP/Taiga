package com.ebifry.appcore.repository

import com.ebifry.appcore.domain.AppDatabase
import com.ebifry.appcore.domain.dao.ScannedItemDAO
import com.ebifry.appcore.domain.entity.CompetitivePrice
import com.ebifry.appcore.domain.entity.FeeDetail
import com.ebifry.appcore.domain.entity.Price
import com.ebifry.appcore.domain.entity.Product
import com.ebifry.appcore.domain.entity.db.CompPrice
import com.ebifry.appcore.domain.entity.db.DBFeeDetail
import com.ebifry.appcore.domain.entity.db.Ranking
import com.ebifry.appcore.domain.entity.db.ScannedItem
import com.ebifry.appcore.domain.repository.DataRepository
import java.lang.IllegalStateException
import java.util.*
import javax.inject.Inject

class DataRepositoryImpl @Inject constructor(private val db:AppDatabase):DataRepository {
    val restricted= arrayOf("Apple","アップル","Beats","ビーツ","BenQ Japan","ベンキュージャパン","Brother","ブラザー","Canon","キャノン","Casio","カシオ","DJI","ディージェーアイ","ELECOM","エレコム","EPSON","エプソン","Fhilips","フィリップス","FUJIFILM","フジフィルム","GoPro","ゴープロ","Microsoft","マイクロソフト","NIKON","ニコン","OLYMPUS","オリンパス","Panasonic","パナソニック","PENTAX","ペンタックス","RICOH","リコー","THERMOS","サーモス","SanDisc","サンディスク","SIGMA","シグマ","SONY","ソニー","Shop Japan","ショップジャパン","TAMRON","タムロン","YAMAHA","ヤマハ","Tanita","タニタ","象印","ゾウジルシ","Bandai","バンダイ","Ergobaby","エルゴベビー","GOOD SMILE COMPANY","グッドスマイルカンパニー","Hoppetta","ホッペタ","LEGO","レゴ","Peeple","ピープル","SEGA TOYS","セガトイズ","STUDIO GHIBLI","スタジオジブリ","TAKARA TOMY","タカラトミー","A BATHING APE","ア・ベイシング・エイプ","Abercrombie & Fitch","アバクロンビー＆フィッチ","adidas","アディダス","BOTTEGA VENETA","ボッテガ・ヴェネタ","BURBERRY","バーバリー","CALVIN KLEIN","カルバンクライン","CANADA GOOSE カナダグース","CHAN LUU","チャン・ルー","Chloe","クロエ","Christian Louboutin","クリスチャン・ルブタン","COACH","コーチ","Daniel Wellington","ダニエル・ウェリントン","Dior","ディオール","Dunhill","ダンヒル","Ed Hardy","エド・ハーディ","emu","エミュー","FENDI","フェンディ","FJALL RAVEN","","フェールラーベン","Giorgio Armani","","ジョルジオ・アルマーニ","GOYARD","ゴヤール","GUCCI","グッチ","GUESSS","ゲス","HUNTER","ハンター","IL BISONTE","イル・ビソンテ","LeSportsac","","レスポートサック","LONGCHAMP","ロンシャン","LOUIS VUITTON","ルイ・ヴィトン","MARC BY MARC JACOBS","マーク・ジェイコブス","Mila schon","ミラ・ショーン","MINNETONKA","ミネトンカ","MONCLER","モンクレール","NEW BALANCE","ニューバランス","NIKE","ナイキ","Orobianco","オロビアンコ","PANERAI","パネライ","Paul Smith","ポール・スミス","Polo Ralph Lauren","","ポロ・ラルフローレン","RAY-BAN","レイバン","Salvatore Ferragamo","","サルヴァトーレ・フェラガモ","TATRAS","タトラス","TIFFANY","ティファニー","TOD’S","トッズ","TOMS SHOES","","トムス・シューズ","TORY BURCH","トリー・バーチ","Vivienne Westwood","ヴィヴィアン・ウエストウッド","VANS","バンズ","Ergobaby","エルゴベビー","Hoppetta","ホッペッタ","Coleman","コールマン","ブランド名","フリガナ","Celvoke","セルヴォーク","ecostore","エコストア","Essie","エッシー","F organics","エッフェオーガニック","giovanni","ジョヴァンニ","HUMBLE BRUSH","ハンブルブラッシュ","LAB SERIES","ラボシリーズ","L’Artisan Parfumeur","ラルチザンパフューム","MDNA SKIN","エムディーエヌエースキン","NYX","ニックス","PENHALIGON’S","ペンハリガン","RANCÉ","ランセ","to/one","トーン","ウォルト・ディズニー・ジャパン株式会社","ウォルト・ディズニー・ジャパン・カブシキガイシャ","COLUMBIA MUSIC ENTERTAINMENT","コロムビアミュージックエンターテイメント","J STORM","ジェーストーム","東宝","トーホウ")
    val restrictedCategory= arrayOf("Watch","Jewelry","Grocery","Beauty")
    val const= mapOf(Pair("BISS Basic", "産業・研究開発"),
        Pair("Speakers", "楽器"),
        Pair("Musical Instruments", "楽器"),
        Pair("Pantry Alcohol", "食品・飲料・お酒"),
        Pair("Pantry", "食品・飲料・お酒"),
        Pair("Alcoholic Beverage", "食品・飲料・お酒"),
        Pair("Grocery", "食品・飲料・お酒"),
        Pair("Pharmacy", "薬"),
        Pair("Automotive Parts and Accessories", "カー＆バイク用品"),
        Pair("Shoes", "シューズ＆バッグ"),
        Pair("Pet Products", "ペット用品"),
        Pair("Apparel", "服＆ファッション小物"),
        Pair("Baby Product", "ベビー&マタニティ"),
        Pair("Jewelry", "ジュエリー"),
        Pair("Watch", "時計"),
        Pair("Beauty", "コスメ"),
        Pair("Health and Beauty", "ヘルス&ビューティー"),
        Pair("Sports", "スポーツ&アウトドア"),
        Pair("Hobby", "ホビー"),
        Pair("Toy", "おもちゃ"),
        Pair("Digital Video Games", "TVゲーム"),
        Pair("Video Games", "TVゲーム"),
        Pair("Software", "PCソフト"),
        Pair("Video", "ビデオ"),
        Pair("DVD", "DVD"),
        Pair("Movie", "Amazonビデオ"),
        Pair("Music", "ミュージック"),
        Pair("Home Improvement", "DIY・工具"),
        Pair("Home", "ホーム&キッチン"),
        Pair("Kitchen", "ホーム&キッチン"),
        Pair("Office Product", "文房具・オフィス用品"),
        Pair("Major Appliances", "大型家電"),
        Pair("Photography", "家電&カメラ"),
        Pair("Home Theater", "家電&カメラ"),
        Pair("Personal Computer", "パソコン・周辺機器"),
        Pair("CE", "家電&カメラ"),
        Pair("eBooks", "Kindle"),
        Pair("Book", "本")
    )

    override suspend fun dispatchProduct(products: List<Product>,jans:List<Long>) {
        val sizeCategories= products.map { calculateShippingCharge(it) }

        if (sizeCategories.size!=products.size)
            throw IllegalStateException("The product may not be able to ship from Amazon.co.jp")

        val scannedItems= products.zip(sizeCategories).mapIndexed {i,it->
            val today=Date()
            ScannedItem(
                today,
                it.second.shippingFbaCharge,
                calculateStorageCharge(today, it.first.packageDimensions),
                it.first.asin,
                jans[i],
                it.first.name,
                it.first.imageURL,
                restricted.contains(it.first.manufacturer),
                restrictedCategory.contains(it.first.category),
                const[it.first.category]?:"UNKNOWN"
            )
        }.toTypedArray()

        db.scannedItemDao().insertAll(*scannedItems)
        val a=arrayListOf<Ranking>()
        products.forEach{ product->
            a.addAll(product.salesRankings.map {
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
        val l=list.map { DBFeeDetail(Date(),it.totalAmount,it.feeType,asin) }.toTypedArray()
        db.feeDao().insertAll(*l)
    }

    override suspend fun getScanHistory():List<ScannedItemDAO.RetrievedItem> {
        return db.scannedItemDao().load()
    }
    

}