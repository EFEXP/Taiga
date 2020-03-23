package com.ebifry.appcore.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.findNavController
import com.ebifry.appcore.R
import com.ebifry.appcore.databinding.ActivityMainBinding
import com.ebifry.appcore.ui.viewmodel.MainViewModel
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.android.HasAndroidInjector

import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() ,ViewModelStoreOwner {

    private val viewModel: MainViewModel by viewModels()
    private val PERMISSION_REQUEST=100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED)
        {
           setFragment()
        }
        else{
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),PERMISSION_REQUEST)
        }

    }
    private fun setFragment(){
        val binding=DataBindingUtil.setContentView<ActivityMainBinding>(this,R.layout.activity_main)
        setSupportActionBar(toolbar)
        binding.lifecycleOwner=this
        viewModel.errorOccur.observe(this, Observer {
            Toast.makeText(this,it,Toast.LENGTH_LONG).show()
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==PERMISSION_REQUEST){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                setFragment()
            }

        }

    }


}
