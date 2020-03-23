package com.ebifry.appcore.ui.viewmodel


import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.ebifry.appcore.Taiga
import com.ebifry.appcore.di.MainViewModule
import com.ebifry.appcore.domain.dao.ScannedItemDAO
import com.ebifry.appcore.domain.usecase.ScanApp
import javax.inject.Inject
import kotlin.collections.ArrayList


class MainViewModel(application:Application): AndroidViewModel(application) {
    @Inject
    lateinit var scanApp: ScanApp
    val errorOccur=MutableLiveData<String>()
    val clearCurrentID=MutableLiveData<Unit>()
    var adapterList:ArrayList<Long> = arrayListOf()
    private val idList= MutableLiveData<List<Long>>()
    val untilIdListChange=Transformations.distinctUntilChanged(idList)
    private val sendClicked=MutableLiveData<List<Long>>()
    val loaded=sendClicked.switchMap {
        list->
        liveData(context = viewModelScope.coroutineContext) {
            runCatching { scanApp.scanBarcode(list) }.
            onSuccess {res:List<ScannedItemDAO.RetrievedItem>-> emit(res)
            adapterList.clear()
                idList.postValue(emptyList<Long>())
            }.
            onFailure{
                ex:Throwable->
                Log.e("ERROR",ex.localizedMessage!!)
                errorOccur.postValue(ex.localizedMessage?:"UNKNOWN:"+ex.message)
                //throw ex

            }
        }

    }


init {
    getApplication<Taiga>().applicationComponent.plus(MainViewModule()).inject(this)
}

    fun barcodeRead(list: List<Long>){
        val c=adapterList
        val set =(list.filter { isJANCode(it.toString()) or isISBNCode(it.toString())}-c)
        adapterList.addAll(set.toList())
        idList.postValue(set.toList())
    }

    private fun isJANCode(targetCode: String)=!(targetCode.length != 13 || !targetCode.startsWith("45") && !targetCode.startsWith("49"))
    private fun isISBNCode(targetCode: String)= (targetCode.length == 10 || targetCode.length == 13) && (targetCode.startsWith("978") || targetCode.startsWith("979"))

    fun startLookUpFragment(){
        sendClicked.postValue(adapterList)
    }





}