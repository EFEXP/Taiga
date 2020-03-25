package com.ebifry.barcode.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ebifry.appbase.MainActivity
import com.ebifry.barcode.R
import com.ebifry.barcode.databinding.FragmentBarcodeScanBinding
import com.ebifry.barcode.ui.adapter.JANAdapter
import com.ebifry.barcode.ui.adapter.OnItemClickListener
import com.ebifry.barcode.ui.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import kotlinx.android.synthetic.main.fragment_barcode_scan.view.*
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
        binding=FragmentBarcodeScanBinding.inflate(inflater, container, false)
        binding.lifecycleOwner=this
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycle.addObserver(binding.viewBarcode)
        val adapter=JANAdapter(viewModel.adapterList,this.viewLifecycleOwner)
        binding.apply {
            viewBarcode.setOptions(Options.Builder()
                .barcodeFormats(intArrayOf(Barcode.FORMAT_EAN_13))
                .minBarcodeWidth(200)
                .scaleType(BarcodeView.CENTER_CROP).build())
            viewBarcode.barcodes.observe(viewLifecycleOwner, Observer { l ->
                viewModel.barcodeRead(l)
            })
            recycler.adapter=adapter
            recycler.layoutManager=LinearLayoutManager(context)
            recycler.itemAnimator=SlideInLeftAnimator()
            adapter.setOnClickListener(object : OnItemClickListener<Long> {
                override fun onClick(list: List<Long>, int: Int) {
                    val item=list[int]
                    adapter.remove(int)
                    adapter.notifyItemRemoved(int)
                    Snackbar.make((activity as MainActivity).findViewById(android.R.id.content),"削除しました。",Snackbar.LENGTH_LONG).setAction("取り消す") {
                        adapter.insert(item,int)
                        adapter.notifyItemInserted(int)
                    }.show()
                }
            })


            sendIds.setOnClickListener {
                viewModel.clickSendButton()
                findNavController().navigate(R.id.searchHistoryFragment)
            }
        }

        viewModel.apply {
            clearCurrentID.observe(viewLifecycleOwner, Observer {
                adapter.clear()
                viewModel.adapterList.clear()
            })

            untilIdListChange.observe(viewLifecycleOwner, Observer {
                adapter.notifyItemRangeInserted(it.second,it.first)
            })
            goToLookUp.observe(viewLifecycleOwner, Observer {
                findNavController().navigate(R.id.searchHistoryFragment)
            })
        }



        if (savedInstanceState!=null)
            adapter.notifyDataSetChanged()
    }
}
