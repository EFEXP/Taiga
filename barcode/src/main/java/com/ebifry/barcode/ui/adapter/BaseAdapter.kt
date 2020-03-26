package com.ebifry.barcode.ui.adapter

import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import java.util.*

abstract class BaseAdapter<T,VH:RecyclerView.ViewHolder>(private var list:MutableList<T>, private val parentLifecycleOwner: LifecycleOwner) : RecyclerView.Adapter<VH>() {

    protected var clickListener:OnItemClickListener<T>?=null
    fun setOnClickListener(l:OnItemClickListener<T>){
        clickListener=l
    }
    fun addAll(l:List<T>):List<T>{
        list.addAll(l)
        return list
    }
    fun insert(item:T,position: Int){
        list.add(position,item)
    }

    fun remove(position:Int){
        list.removeAt(position)
    }
    fun addAllMayDuplicated(l:List<T>):List<T>{
        val diff=l.toSet().minus(list.toSet())
        list.addAll(0,diff)
        return diff.toList()
    }
    fun insertAll(l:List<T>,position: Int){
        list.addAll(position,l)
    }

    fun add(l:T):List<T>{
        list.add(l)
        return list
    }
    fun clear(){
        list.clear()
    }
    fun setItems(l: MutableList<T>){
        list.clear()
        list.addAll(l)
    }
    override fun getItemCount(): Int {
        return list.size
    }



}
interface OnItemClickListener<T>{
    fun onClick(list:List<T>,position:Int)
}