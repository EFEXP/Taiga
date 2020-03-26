package com.ebifry.barcode.ui

import android.content.Context
import android.graphics.*
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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
import com.google.android.material.snackbar.BaseTransientBottomBar
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
            val holder=onDrawSurface.holder
            holder.setFormat(PixelFormat.TRANSPARENT)




            viewBarcode.barcodes.observe(viewLifecycleOwner, Observer { l ->
                l.forEach {it.boundingBox?.let {rect->
                    val canvas=holder.lockCanvas().apply {
                        drawColor(0, PorterDuff.Mode.CLEAR)
                        val paint=Paint().apply {
                            style=Paint.Style.STROKE
                            color=ContextCompat.getColor(context!!,R.color.colorAccent)
                            strokeWidth=5F
                        }
                        drawRect(rect.left.toFloat(),rect.top.toFloat(),rect.right.toFloat(),rect.bottom.toFloat(),paint)
                    }
                    holder.unlockCanvasAndPost(canvas)
                }

                }
                viewModel.barcodeRead(l)
            })

            onDrawSurface.setOnClickListener {
                val previewEnabled = it.tag as? Boolean ?: true
                if (previewEnabled) {
                    viewBarcode.release()
                    onDrawSurface.foreground=ContextCompat.getDrawable(context!!,R.color.stop_black)
                } else {
                    viewBarcode.start()
                    onDrawSurface.foreground=null
                }
                it.tag = previewEnabled.not()
            }

            recycler.adapter = adapter
            recycler.layoutManager = LinearLayoutManager(context)
            recycler.itemAnimator = SlideInLeftAnimator()
            adapter.setOnClickListener(object : OnItemClickListener<Long> {

                override fun onClick(list: List<Long>, position: Int) {
                    val item = list[position]
                    adapter.remove(position)
                    adapter.notifyItemRemoved(position)
                    adapter.notifyItemRangeChanged(position, 1)
                    Snackbar.make(coordinator, "削除しました。", Snackbar.LENGTH_LONG)
                        .setAction("取り消す") {
                            adapter.insert(item, position)
                            adapter.notifyItemInserted(position)
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
