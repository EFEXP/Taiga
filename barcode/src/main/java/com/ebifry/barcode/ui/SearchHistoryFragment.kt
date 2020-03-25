package com.ebifry.barcode.ui

import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ebifry.appbase.dao.ScannedItemDAO
import com.ebifry.barcode.R
import com.ebifry.barcode.databinding.FragmentSearchHistoryBinding
import com.ebifry.barcode.ui.adapter.ScanHistoryAdapter
import com.ebifry.barcode.ui.adapter.OnItemClickListener
import com.ebifry.barcode.ui.viewmodel.MainViewModel
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import kotlinx.android.synthetic.main.fragment_search_history.*
import kotlinx.android.synthetic.main.fragment_search_history.view.*
import kotlinx.android.synthetic.main.fragment_search_history.view.progressBar


class SearchHistoryFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentSearchHistoryBinding.inflate(inflater, container, false).root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var initialized=false
        val adapter = ScanHistoryAdapter(arrayListOf(), this.viewLifecycleOwner)
        view.recycler.adapter = adapter
        view.recycler.layoutManager = LinearLayoutManager(activity)
        view.recycler.itemAnimator=SlideInLeftAnimator()
        viewModel.historyData.observe(this.viewLifecycleOwner, Observer {
            if (!initialized) {
                adapter.setItems(it.toMutableList())
                progressBar.visibility = View.GONE
                initialized=true
            }
            else{
                adapter.addAllMayDuplicated(it)
            }
        })
        viewModel.startedLookUpFragment()
        adapter.setOnClickListener(object : OnItemClickListener<ScannedItemDAO.RetrievedItem> {
            override fun onClick(list: List<ScannedItemDAO.RetrievedItem>, int: Int) {
                val item = list[int]
                AlertDialog.Builder(activity)
                    .setTitle("Selector")
                    .setItems(arrayOf("Amazon", "モノレート", "最安値.com",view.context.getString(R.string.action_copy_asin),view.context.getString(R.string.action_copy_jan))) { _, which ->
                        if (which<2){
                            val items = arrayOf(
                                String.format(view.context.getString(R.string.amazon_product_url),item.scannedItem.asin),
                                String.format(view.context.getString(R.string.monorate_url),item.scannedItem.asin),
                                String.format(view.context.getString(R.string.saiyasune_url),item.scannedItem.name)
                            )
                            CustomTabsIntent.Builder().build()
                                .launchUrl(context!!, Uri.parse(items[which]))
                        }
                        else{
                            when(which){
                                3->{
                                   val service= view.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                    service.setPrimaryClip(ClipData.newPlainText("",item.scannedItem.asin))
                                }
                                4->{
                                    val service= view.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                    service.setPrimaryClip(ClipData.newPlainText("",item.scannedItem.origin.toString()))
                                }
                            }
                        }


                    }
                    .show()
            }

        })


    }

}
