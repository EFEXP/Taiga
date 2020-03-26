package com.ebifry.barcode.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ebifry.appbase.dao.ScannedItemDAO
import com.ebifry.barcode.R
import com.ebifry.barcode.databinding.ItemMerchandiseBinding
import com.ebifry.appbase.db.DBFeeDetail
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class ScanHistoryAdapter(
    private val list: MutableList<ScannedItemDAO.RetrievedItem>,
    private val lOwner: LifecycleOwner
) : BaseAdapter<ScannedItemDAO.RetrievedItem, ScanHistoryAdapter.MyViewHolder>(list, lOwner) {
    class MyViewHolder(val view: ItemMerchandiseBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_merchandise,
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val i = list[position]

        if (!i.fees.isNullOrEmpty()) {
            val feeList = arrayListOf(
                DBFeeDetail(
                    i.scannedItem.date,
                    i.comp[0].listing.toInt(),
                    holder.view.root.context.getString(R.string.cart_price),
                    i.scannedItem.asin
                )
            )
            feeList.addAll(i.fees.filterNot { it.totalAmount==0 })
            feeList.addAll(
                arrayOf(
                    DBFeeDetail(
                        i.scannedItem.date,
                        i.comp[0].listing.toInt() - i.fees.map { it.totalAmount }
                            .reduce { acc, fee -> acc + fee },
                        holder.view.root.context.getString(R.string.breakeven_point),
                        i.scannedItem.asin
                    )
                )

            )
            val adapter = FeeAdapter(feeList, lOwner)
            holder.view.apply {
                feeRecycler.layoutManager = LinearLayoutManager(root.context)
                feeRecycler.adapter = adapter
                separateFeeFromSum.visibility=View.VISIBLE
                separateInfoFromFee.visibility=View.VISIBLE
            }
        }
        else{
            holder.view.apply {
                separateFeeFromSum.visibility=View.GONE
                separateInfoFromFee.visibility=View.GONE
            }
        }
        holder.view.apply {
            item = i
            Glide.with(root.context).load(i.scannedItem.url).into(imagePreview)
            root.setOnClickListener {
                clickListener?.onClick(list, position)
            }
            feeRecyclerMask.setOnClickListener {
                clickListener?.onClick(list, position)
            }
            textTimestamp.text= getRelativeTime(i.scannedItem.date)



            executePendingBindings()
        }


    }

    fun updateMayDuplicated(l:List<ScannedItemDAO.RetrievedItem>):List<Int>{
        val A=list
        val B=l
        val diff=l.toSet().minus(list.toSet())
        //アップデートsareruアイテム
        val updated=(B-diff).find {bElement->A.map { it.scannedItem.date }.contains(bElement.scannedItem.date)}
        val indices=(A-(B-updated)).map { list.indexOf(it) }
        list.removeAll((A-(B-updated)))

        updated?.let {
            insertAll(arrayListOf(it),0)
        }

        //削除されたアイテムのポジション
        return indices

    }
    private fun getRelativeTime(create: Date): String {
        val datetime1 = System.currentTimeMillis()
        val datetime2 = create.time
        val difference = datetime1 - datetime2
        return when {
            difference < 10000L -> "いま".format(TimeUnit.MILLISECONDS.toSeconds(difference))
            difference < 60000L -> "%d秒前".format(TimeUnit.MILLISECONDS.toSeconds(difference))
            difference < 3600000L -> "%d分前".format(TimeUnit.MILLISECONDS.toMinutes(difference))
            difference < 86400000L -> "%d時間前".format(TimeUnit.MILLISECONDS.toHours(difference))
            else -> "%d日前".format(TimeUnit.MILLISECONDS.toDays(difference))
        }
    }

}

