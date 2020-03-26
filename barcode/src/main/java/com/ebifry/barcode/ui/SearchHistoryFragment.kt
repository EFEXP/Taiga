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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import kotlinx.android.synthetic.main.fragment_search_history.*
import kotlinx.android.synthetic.main.fragment_search_history.view.*
import kotlinx.android.synthetic.main.fragment_search_history.view.progressBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.internal.notify


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
        val adapter = ScanHistoryAdapter(arrayListOf(), this.viewLifecycleOwner).apply {
            setHasStableIds(true)
        }
        view.recycler.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(activity)
            itemAnimator=SlideInLeftAnimator()
        }

        viewModel.historyData.observe(this.viewLifecycleOwner, Observer {
            if (!initialized) {
                adapter.addAll(it)
                adapter.notifyItemRangeInserted(0,it.size)
                initialized=true
                if (viewModel.adapterList.isEmpty())
                {
                    progressBar.visibility = View.GONE
                }
            }
            else{
                progressBar.visibility = View.GONE
                adapter.clear()
                adapter.addAll(it)
                adapter.notifyDataSetChanged()
                //val removedPositions =adapter.updateMayDuplicated(it)
                //if ((removedPositions.size)>0){
                //removedPositions.forEach {
                //    adapter.notifyItemMoved(it,0)
                //}}

                //val r=adapter.addAllMayDuplicated(it)
                //if ((r.size+removedPositions.size)>0){

                //adapter.notifyItemRangeInserted(0,r.size)
                //adapter.notifyItemRangeChanged(0,r.size)
                //view.recycler.smoothScrollToPosition(0)}
            }
        })
        adapter.setOnClickListener(object : OnItemClickListener<ScannedItemDAO.RetrievedItem> {
            override fun onClick(list: List<ScannedItemDAO.RetrievedItem>, position: Int) {
                val item = list[position]
                MaterialAlertDialogBuilder(activity,R.style.ThemeOverlay_MaterialComponents)
                    .setTitle("なにをしますか？")
                    .setItems(arrayOf("Amazonで見る","出品制限を見る", "モノレートで見る", "最安値.comで見る",view.context.getString(R.string.action_copy_asin),view.context.getString(R.string.action_copy_jan))) { _, which ->
                        if (which<3){
                            val items = arrayOf(
                                String.format(view.context.getString(R.string.amazon_product_url),item.scannedItem.asin),
                                String.format(view.context.getString(R.string.amazon_seller_url),item.scannedItem.asin),
                                String.format(view.context.getString(R.string.monorate_url),item.scannedItem.asin),
                                String.format(view.context.getString(R.string.saiyasune_url),item.scannedItem.name)
                            )
                            CustomTabsIntent.Builder().build()
                                .launchUrl(context!!, Uri.parse(items[which]))
                        }
                        else{
                            when(which){
                                4->{
                                   val service= view.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                    service.setPrimaryClip(ClipData.newPlainText("",item.scannedItem.asin))
                                    Snackbar.make(view.coodinator_scanhistory, "コピーしました。", Snackbar.LENGTH_LONG).show()
                                }
                                5->{
                                    val service= view.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                    service.setPrimaryClip(ClipData.newPlainText("",item.scannedItem.origin.toString()))
                                    Snackbar.make(view.coodinator_scanhistory, "コピーしました。", Snackbar.LENGTH_LONG).show()
                                }
                            }
                        }


                    }
                    .show()
            }

        })


    }

}
