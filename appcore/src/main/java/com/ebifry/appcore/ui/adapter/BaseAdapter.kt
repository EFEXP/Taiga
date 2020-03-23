package com.ebifry.appcore.ui.adapter

import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T,VH:RecyclerView.ViewHolder>(private var list:MutableList<T>, private val parentLifecycleOwner: LifecycleOwner) : RecyclerView.Adapter<VH>() {

    protected var clickListener:OnItemClickListener<T>?=null
    fun setOnClickListener(l:OnItemClickListener<T>){
        clickListener=l
    }
    fun addAll(l:List<T>):List<T>{
        list.addAll(l)
        notifyDataSetChanged()
        return list
    }
    fun add(l:T):List<T>{
        list.add(l)
        notifyItemInserted(list.size)
        return list
    }
    fun clear(){
        list.clear()
        notifyDataSetChanged()
    }
    fun setItems(l: MutableList<T>){
        list.clear()
        list.addAll(l)
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return list.size
    }



}
interface OnItemClickListener<T>{
    fun onClick(list:List<T>,int:Int)
}