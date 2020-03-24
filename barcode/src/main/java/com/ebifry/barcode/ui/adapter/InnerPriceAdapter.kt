package com.ebifry.barcode.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.ebifry.barcode.R
import com.ebifry.barcode.databinding.ItemCompetitivePriceBinding
import com.ebifry.barcode.databinding.ItemFeeBinding
import com.ebifry.appbase.db.DBFeeDetail

class FeeAdapter(val list: MutableList<DBFeeDetail>, lifecycleOwner: LifecycleOwner) :BaseAdapter<DBFeeDetail,FeeAdapter.ViewHolder>(list  ,lifecycleOwner){
    class  ViewHolder(val view: ItemFeeBinding) : RecyclerView.ViewHolder(view.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return  ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_fee,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item=list[position]

        holder.view.apply {
            fee=item
            when (item.feeType) {
                "カート価格" -> {
                    feeIcon.setImageResource(R.drawable.ic_shopping_cart_black_24dp)
                }
                "損益分岐価格" -> {
                    feeIcon.setImageResource(R.drawable.ic_attach_money_black_24dp)
                }
                else -> {
                    feeAmount.setTextColor(ContextCompat.getColor(root.context,R.color.red))
                }
            }
            feeTitle.text=when(item.feeType){
                "FBAFees"->"FBA手数料"
                "PerItemFee"->"基本成約料"
                "VariableClosingFee"->"カテゴリー成約料"
                "ReferralFee"->"販売手数料"
                else ->item.feeType
            }
            executePendingBindings()
        }

    }

}