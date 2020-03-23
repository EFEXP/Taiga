package com.ebifry.appcore.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ebifry.appcore.R
import com.ebifry.appcore.databinding.ItemMerchandiseBinding
import com.ebifry.appcore.domain.dao.ScannedItemDAO
import com.ebifry.appcore.domain.entity.db.DBFeeDetail


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
                    "カート価格",
                    i.scannedItem.asin
                )
            )
            feeList.addAll(i.fees.filterNot { it.totalAmount==0 })
            feeList.addAll(
                arrayOf(
                DBFeeDetail(
                    i.scannedItem.date,
                    i.comp[0].listing.toInt()-i.fees.map { it.totalAmount }.reduce { acc, i -> acc + i },
                    "損益分岐価格",
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
            root.setOnClickListener {
                clickListener?.onClick(list, position)
            }
            executePendingBindings()
        }


    }

}

