package com.ebifry.appcore.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.ebifry.appcore.R
import com.ebifry.appcore.databinding.ItemLinkBinding

class LinkAdapter(val list: MutableList<String>,lifecycleOwner: LifecycleOwner):BaseAdapter<String,LinkAdapter. LinkViewHolder>(list  ,lifecycleOwner){
    class  LinkViewHolder(val view: ItemLinkBinding) :RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):  LinkViewHolder {
        return  LinkViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_link,
                parent,
                false
            )
        )


    }

    override fun onBindViewHolder(holder:  LinkViewHolder, position: Int) {
        holder.view.asin=list[position]

    }

}