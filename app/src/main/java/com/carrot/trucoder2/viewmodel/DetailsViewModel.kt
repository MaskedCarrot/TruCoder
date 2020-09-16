package com.carrot.trucoder2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carrot.trucoder2.repository.CodeRespository
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import java.net.URL

class DetailsViewModel() :ViewModel() {

    var result :MutableLiveData<Int> = MutableLiveData()


    fun checkHandle(url: URL) = viewModelScope.launch {
        try {
            result = MutableLiveData()
            HttpURLConnection.setFollowRedirects(false)
            val con: HttpURLConnection = url.openConnection()  as HttpURLConnection
            con.requestMethod = "HEAD"
            if(con.responseCode == HttpURLConnection.HTTP_OK)
                result.postValue(1)
            else
                result.postValue(0)
        } catch (e: Exception) {
            result.postValue(-1)
        }
    }

}