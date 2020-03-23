package com.ebifry.appcore.ui

import android.app.AlertDialog
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
import com.ebifry.appcore.databinding.FragmentCodeLookupBinding
import com.ebifry.appcore.domain.dao.ScannedItemDAO
import com.ebifry.appcore.ui.adapter.ScanHistoryAdapter
import com.ebifry.appcore.ui.adapter.OnItemClickListener
import com.ebifry.appcore.ui.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_code_lookup.view.*


class CodeLookUpFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentCodeLookupBinding.inflate(inflater, container, false).root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ScanHistoryAdapter(arrayListOf(), this.viewLifecycleOwner)
        view.recycler.adapter = adapter
        view.recycler.layoutManager = LinearLayoutManager(activity)
        viewModel.loaded.observe(this.viewLifecycleOwner, Observer {
            adapter.setItems(it.toMutableList())
        })
        adapter.setOnClickListener(object : OnItemClickListener<ScannedItemDAO.RetrievedItem> {
            override fun onClick(list: List<ScannedItemDAO.RetrievedItem>, int: Int) {
                val item = list[int]
                AlertDialog.Builder(activity)
                    .setTitle("Selector")
                    .setItems(arrayOf("Amazon", "モノレート", "最安値.com")) { _, which ->
                        val items = arrayOf(
                            "https://www.amazon.co.jp/o/ASIN/${item.scannedItem.asin}",
                            "http://mnrate.com/s/past.php?kwd=${item.scannedItem.asin}",
                            "https://www.saiyasune.com/I1W${item.scannedItem.name}.html"
                        )
                        CustomTabsIntent.Builder().build()
                            .launchUrl(context!!, Uri.parse(items[which]))
                    }
                    .show()
            }

        })


    }

}
