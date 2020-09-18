package com.carrot.trucoder2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carrot.trucoder2.repository.CodeRespository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.net.HttpURLConnection
import java.net.URL

class DetailsViewModel() :ViewModel() {

    var result :MutableLiveData<Int> = MutableLiveData()


    fun checkHandle(url: URL) = CoroutineScope(IO).launch {
            try {
                HttpURLConnection.setFollowRedirects(false)
                val con: HttpURLConnection = url.openConnection() as HttpURLConnection
                con.requestMethod = "HEAD"
                if(con.responseCode == 200)
                    result.postValue(1)
                else
                    result.postValue(0)
                println("value of it is 0 or 1")
            } catch (e: Exception) {
                result.postValue(-1)
                println("value of it is -1")
                e.printStackTrace()
            }
    }

}