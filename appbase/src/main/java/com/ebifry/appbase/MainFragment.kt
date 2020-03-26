package com.ebifry.appbase

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.ebifry.appbase.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?  {
        val binding=FragmentMainBinding.inflate(inflater, container, false)
        binding.lifecycleOwner=this
        binding.buttonGo.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToBarcodeScanFragment())
        }
        binding.buttonBarcode.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToSettingsFragment())
        }
        return  binding.root
    }

}
