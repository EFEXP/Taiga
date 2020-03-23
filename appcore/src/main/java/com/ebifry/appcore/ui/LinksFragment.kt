package com.ebifry.appcore.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ebifry.appcore.R
import com.ebifry.appcore.ui.adapter.LinkAdapter
import kotlinx.android.synthetic.main.a_recycler_fragment.*
import kotlinx.android.synthetic.main.a_recycler_fragment.view.*
import java.lang.IllegalArgumentException

class LinksFragment(): AppCompatDialogFragment(){
    private val ARGS_KEY="100"
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.a_recycler_fragment, container, false)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val asin=arguments!!.get(ARGS_KEY)!!

        val adapter=LinkAdapter(arrayListOf(
            "https://sellercentral-japan.amazon.com/product-search/search?q=${asin}"
            ,"https://mnrate.com/item/aid/${asin}"
            ,""
            ,""),this)
        val r=view.findViewById<RecyclerView>(R.id.recycler)
        r.recycler.adapter=adapter
        r.recycler.layoutManager=LinearLayoutManager(activity)
    }
    companion object {
        fun getInstance(v:String): LinksFragment {
            return LinksFragment().apply {
                arguments=Bundle().apply {
                    putString(ARGS_KEY,v)
                }

            }
        }
    }
}