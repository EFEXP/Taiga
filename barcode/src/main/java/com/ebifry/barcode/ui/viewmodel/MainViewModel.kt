package com.ebifry.barcode.ui.viewmodel


import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.*
import com.ebifry.appbase.Taiga
import com.ebifry.barcode.di.MainViewModule
import com.ebifry.appbase.dao.ScannedItemDAO
import com.ebifry.barcode.di.AppModule
import com.ebifry.barcode.di.DaggerApplicationComponent
import com.ebifry.barcode.domain.usecase.ScanApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uk.co.brightec.kbarcode.Barcode
import javax.inject.Inject
import kotlin.collections.ArrayList


class MainViewModel(application: Application) : AndroidViewModel(application) {
    @Inject
    lateinit var scanApp: ScanApp
    @Inject
    lateinit var sharedPreferences: SharedPreferences

    val errorOccurs = MutableLiveData<String>()
    val clearCurrentID = MutableLiveData<Unit>()
    var adapterList: ArrayList<Long> = arrayListOf()
    val goToLookUp=MutableLiveData<Unit>()
    private val idList = MutableLiveData<List<Long>>()
    val untilIdListChange = Transformations.distinctUntilChanged(idList)
    val historyData=MutableLiveData<List<ScannedItemDAO.RetrievedItem>>()

    init {
        val c = getApplication<Taiga>().provideCoreComponent()
        val co =
            DaggerApplicationComponent.builder().coreComponent(c).appModule(AppModule()).build()
        co.plus(this)
    }

    fun barcodeRead(list: List<Barcode>) {
        val barcodeNumbers= list.mapNotNull { it.rawValue?.toLong() }
        val c = adapterList
        val set = (barcodeNumbers.filter { isJANCode(it.toString()) or isISBNCode(it.toString()) } - c)
        adapterList.addAll(set.toList())
        idList.postValue(set.toList())
    }

    fun clickSendButton(){
        val isFBA =sharedPreferences.getBoolean("is_fba",true)
        val isMinorSeller =sharedPreferences.getBoolean("is_minor_seller",false)
        CoroutineScope(viewModelScope.coroutineContext).launch {
            runCatching {
                scanApp.scanBarcode(adapterList,isFBA,isMinorSeller)
                }.
            onSuccess {
                adapterList.clear()
                idList.postValue(emptyList<Long>()) }.
            onFailure { ex: Throwable ->
                Log.e("ERROR", ex.localizedMessage!!)
                errorOccurs.postValue(ex.localizedMessage ?: "UNKNOWN:" + ex.message)
                //throw ex
            }
        }
    }

    fun startedLookUpFragment(){
        CoroutineScope(viewModelScope.coroutineContext).launch {
            historyData.postValue(scanApp.historyView())
        }
    }

    fun startLookUpFragment() {
        goToLookUp.postValue(Unit)
    }
    private fun isJANCode(targetCode: String) =
        !(targetCode.length != 13 || !targetCode.startsWith("45") && !targetCode.startsWith("49"))

    private fun isISBNCode(targetCode: String) =
        (targetCode.length == 10 || targetCode.length == 13) && (targetCode.startsWith("978") || targetCode.startsWith(
            "979"
        ))

}