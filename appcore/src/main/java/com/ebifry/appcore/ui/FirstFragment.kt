package com.ebifry.appcore.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ebifry.appcore.R
import com.ebifry.appcore.databinding.FragmentFirstBinding
import com.ebifry.appcore.ui.adapter.JANAdapter
import com.ebifry.appcore.ui.viewmodel.MainViewModel
import uk.co.brightec.kbarcode.Barcode
import uk.co.brightec.kbarcode.BarcodeView
import uk.co.brightec.kbarcode.Options


class FirstFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentFirstBinding
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding=FragmentFirstBinding.inflate(inflater, container, false)
        binding.lifecycleOwner=this

        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycle.addObserver(binding.viewBarcode)
        binding.viewBarcode.setOptions(Options.Builder()
            .barcodeFormats(intArrayOf(Barcode.FORMAT_EAN_13))
            .minBarcodeWidth(200)
            .scaleType(BarcodeView.CENTER_CROP).build())
        binding.viewBarcode.barcodes.observe(viewLifecycleOwner, Observer { l ->
            viewModel.barcodeRead(l.mapNotNull { it.displayValue?.toLong() })
        })
        val adapter=JANAdapter(viewModel.adapterList,this.viewLifecycleOwner)
        viewModel.clearCurrentID.observe(viewLifecycleOwner, Observer {
            adapter.clear()
            viewModel.adapterList.clear()
        })
        binding.recycler.adapter=adapter
        binding.recycler.layoutManager=LinearLayoutManager(context)
        binding.sendIds.setOnClickListener {
            viewModel.startLookUpFragment()
            findNavController().navigate(R.id.codeLookUpFragment)
        }
        viewModel.untilIdListChange.observe(this.viewLifecycleOwner, Observer {
            adapter.notifyDataSetChanged()

        })
        if (savedInstanceState!=null)
            adapter.notifyDataSetChanged()
    }
}
