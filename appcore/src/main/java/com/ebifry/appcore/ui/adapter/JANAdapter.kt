package com.ebifry.appcore.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.ebifry.appcore.R
import com.ebifry.appcore.databinding.ItemJanBinding

class JANAdapter(val list: MutableList<Long>,lifecycleOwner: LifecycleOwner):BaseAdapter<Long,JANAdapter.JANViewHolder>(list  ,lifecycleOwner){
    class  JANViewHolder(val view: ItemJanBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):  JANViewHolder {
        return  JANViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_jan,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder:  JANViewHolder, position: Int) {
        holder.view.jan=list[position]
        holder.view.root.setOnClickListener {
            clickListener?.onClick(list,position)
        }
        holder.view.executePendingBindings()
    }

}