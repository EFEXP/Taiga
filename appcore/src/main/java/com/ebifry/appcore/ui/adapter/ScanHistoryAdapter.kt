package com.ebifry.appcore.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ebifry.appcore.R
import com.ebifry.appcore.databinding.ItemCompetitivePriceBinding
import com.ebifry.appcore.databinding.ItemMerchandiseBinding
import com.ebifry.appcore.domain.dao.ScannedItemDAO


class ScanHistoryAdapter(private val list:MutableList<ScannedItemDAO.RetrievedItem>, private val lifecycleOwner: LifecycleOwner): BaseAdapter<ScannedItemDAO.RetrievedItem,ScanHistoryAdapter.MyViewHolder>(list,lifecycleOwner){
    class MyViewHolder(val view: ItemMerchandiseBinding): RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_merchandise,parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val i=list[position]
        val s:List<View> =i.comp.map {
            val bind=ItemCompetitivePriceBinding.inflate(LayoutInflater.from(holder.view.innerLinearLayout.context),holder.view.innerLinearLayout,false)
                bind.price=it
                bind.root }

        holder.view.apply {
            item=i
            s.forEach { innerLinearLayout.addView(it) }
            root.setOnClickListener {
                clickListener?.onClick(list,position)
            }
            executePendingBindings()
        }


    }

}

