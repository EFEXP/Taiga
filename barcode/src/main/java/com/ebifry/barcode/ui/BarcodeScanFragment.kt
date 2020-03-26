package com.ebifry.barcode.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ebifry.barcode.R
import com.ebifry.barcode.databinding.FragmentBarcodeScanBinding
import com.ebifry.barcode.ui.adapter.JANAdapter
import com.ebifry.barcode.ui.adapter.OnItemClickListener
import com.ebifry.barcode.ui.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import uk.co.brightec.kbarcode.Barcode
import uk.co.brightec.kbarcode.BarcodeView
import uk.co.brightec.kbarcode.Options


class BarcodeScanFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentBarcodeScanBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBarcodeScanBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycle.addObserver(binding.viewBarcode)
        val adapter = JANAdapter(viewModel.adapterList, this.viewLifecycleOwner)
        binding.apply {
            viewBarcode.setOptions(
                Options.Builder()
                    .barcodeFormats(intArrayOf(Barcode.FORMAT_EAN_13))
                    .minBarcodeWidth(500)
                    .scaleType(BarcodeView.CENTER_CROP).build()
            )
            viewBarcode.barcodes.observe(viewLifecycleOwner, Observer { l ->
                viewModel.barcodeRead(l)
            })
            viewBarcode.setOnClickListener {
                val previewEnabled = it.tag as? Boolean ?: true
                if (previewEnabled) {
                    viewBarcode.release()
                }
                else{
                    viewBarcode.start()
                }
                it.tag = previewEnabled.not()

            }
            recycler.adapter = adapter
            recycler.layoutManager = LinearLayoutManager(context)
            recycler.itemAnimator = SlideInLeftAnimator()
            adapter.setOnClickListener(object : OnItemClickListener<Long> {
                override fun onClick(list: List<Long>, int: Int) {
                    val item = list[int]
                    adapter.remove(int)
                    adapter.notifyItemRemoved(int)
                    Snackbar.make(coordinator, "削除しました。", Snackbar.LENGTH_LONG).setAction("取り消す") {
                        adapter.insert(item, int)
                        adapter.notifyItemInserted(int)
                    }.show()
                }
            })


            sendIds.setOnClickListener {
                viewModel.clickSendButton()
                findNavController().navigate(BarcodeScanFragmentDirections.actionBarcodeScanFragmentToScanHistoryFragment())
            }
        }

        viewModel.apply {
            clearCurrentID.observe(viewLifecycleOwner, Observer {
                adapter.clear()
                viewModel.adapterList.clear()
            })

            untilIdListChange.observe(viewLifecycleOwner, Observer {
                adapter.notifyItemRangeInserted(it.second, it.first)
            })
            goToLookUp.observe(viewLifecycleOwner, Observer {
                findNavController().navigate(R.id.searchHistoryFragment)
            })
        }



        if (savedInstanceState != null)
            adapter.notifyDataSetChanged()
    }
}
