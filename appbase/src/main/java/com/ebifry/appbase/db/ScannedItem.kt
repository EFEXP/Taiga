package com.ebifry.appbase.db

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
