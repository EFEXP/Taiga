package com.ebifry.appcore.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.ebifry.appcore.R
import com.ebifry.appcore.databinding.ItemCompetitivePriceBinding
import com.ebifry.appcore.domain.entity.CompetitivePrice
import com.ebifry.appcore.domain.entity.db.CompPrice

class InnerPriceAdapter(val list: MutableList<CompPrice>,lifecycleOwner: LifecycleOwner) :BaseAdapter<CompPrice,InnerPriceAdapter.ViewHolder>(list  ,lifecycleOwner){
    class  ViewHolder(val view: ItemCompetitivePriceBinding) : RecyclerView.ViewHolder(view.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return  ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_competitive_price,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.textCondition.text=list[position].condition
    }

}