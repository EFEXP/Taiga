package com.ebifry.appcore.domain.entity.db

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bumptech.glide.Glide
import java.util.*

@Entity
data class ScannedItem(
    val date: Date,
    @PrimaryKey
    val asin: String,
    val origin:Long,
    val name: String,
    val url: String,
    val restrictedMaker:Boolean,
    val restrictedCategory: Boolean,
    val category:String
) {

    companion object {
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun loadImage(view: ImageView, url: String?) {
            if (!url.isNullOrEmpty()) {
                Glide.with(view.context).load(url).into(view)
            }
        }
    }
}
enum class SizeCategory(val shippingFbaCharge:Int){
    SMALL(282),
    MEDIUM_1(381),
    MEDIUM_2(421),
    MEDIUM_3(467),
    MEDIUM_4(548),
    LARGE_1(589),
    LARGE_2(712),
    LARGE_3(815),
    LARGE_4(975),
    LARGE_5(1020),
    LARGE_6(1100),
    LARGE_7(1532),
    LARGE_8(1756),
    EXTRA_LARGE_1(3061),
    EXTRA_LARGE_2(3970),
    EXTRA_LARGE_3(4995),
    EXTRA_LARGE_4(6250),
}
enum class ReturnCharge(val returnCharge: Int){
    SMALL(30),
    MEDIUM_1(45),
    MEDIUM_2(60),
    MEDIUM_3(100){
        fun getCharge(weightGram:Double):Int{
            return 100+((weightGram-1000)*40/1000).toInt()
        }
    },
    LARGE_1(80),
    LARGE_2(110),
    LARGE_3(140),
    LARGE_4(200),
    LARGE_5(350){
        fun getCharge(weightGram:Double):Int{
            return 350+((weightGram-5000)*40/1000).toInt()
        }
    },
}