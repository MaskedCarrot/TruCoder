package com.carrot.trucoder2.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carrot.trucoder2.model.*
import com.carrot.trucoder2.repository.CodeRepository
import com.carrot.trucoder2.utils.Resource
import kotlinx.coroutines.launch
import timber.log.Timber

class MainActivityViewModel @ViewModelInject constructor(private val repository: CodeRepository) :
    ViewModel() {

    val codeforcesUserLD: MutableLiveData<Resource<ResponseCodforces>> = MutableLiveData()
    val codechefUserLD: MutableLiveData<Resource<ResponseCodechef>> = MutableLiveData()
    val contestLD: MutableLiveData<Resource<List<ResultContest>>> = MutableLiveData()
    val codechefFriendsLD: MutableLiveData<Resource<ResponseLeaderboard>> = MutableLiveData()
    val codeforcesFriendsLD: MutableLiveData<Resource<ResponseLeaderboard>> = MutableLiveData()


    fun RefreshCCFriends(handles: String) = viewModelScope.launch {
        val list = repository.RefreshCC()
        var str = ""
        for (i in list)
            str = "$str$i;"
        if (list.isEmpty())
            str = "$handles;"
        str = str.substring(0, str.length - 1)
        val response = repository.fetchFriendListCC(str)
        if(response.isSuccessful){
            response.body()?.let {
                repository.InsertFriends(it)
            }
        }
        else{
            Timber.e("Could not refresh Codechef friends list")
        }
    }

    fun RefreshCFFriends(handles: String) = viewModelScope.launch {
        val list = repository.RefreshCF()
        var str = ""
        for(i in list)
            str = "$str$i;"
        if(list.isEmpty())
            str = "$handles;"
        str = str.substring(0 , str.length-1)

        val response = repository.fetchFriendListCF(str)
        if(response.isSuccessful){
            response.body()?.let {
                repository.InsertFriends(it)
            }
        }
        else{
            Timber.e("Could not refresh Codeforces friends list")
        }
    }

    fun getCodeforcesUser(handle : String) = viewModelScope.launch {
        try{
            codeforcesUserLD.postValue(Resource.Loading())
            val response = repository.fetchCodeforcesUser(handle)
            if(response.isSuccessful) {
                response.body()?.let { result ->
                    if(result.status == "Success")
                        codeforcesUserLD.postValue(Resource.Success(result))
                    else
                        codechefUserLD.postValue(Resource.Error(null, response.message()))
                }
            }
            else{
                codeforcesUserLD.postValue(Resource.Error(null, response.message()))
            }
        }catch (e:java.lang.Exception){
            codeforcesUserLD.postValue(Resource.Error(null, "Timeout"))
        }
    }

    fun getCodeChefUser(handle:String) = viewModelScope.launch {
        try{
            codechefUserLD.postValue(Resource.Loading())
            val response = repository.fetchCodechefUser(handle)
            if(response.isSuccessful) {
                response.body()?.let { result ->
                    if(result.status == "Success")
                        codechefUserLD.postValue(Resource.Success(result))
                    else
                        codechefUserLD.postValue(Resource.Error(null, response.message()))
                }
            }
            else{
                codechefUserLD.postValue(Resource.Error(null, response.message()))
            }
        }catch (e:Exception){
            codechefUserLD.postValue(Resource.Error(null, "Timeout"))
        }
    }

    fun getContestData() = viewModelScope.launch {
        try {
            contestLD.postValue(Resource.Loading())
            val response = repository.fetchContests()
            if (response.isSuccessful) {
                response.body()?.let {
                    if (response.body()!!.status == "success")
                        repository.NukeContests()
                    repository.InsertContests(it.resultContest)
                    }
            } else {
                contestLD.postValue(Resource.Error(null, response.message()))
            }
        }catch (e:Exception){
            contestLD.postValue(Resource.Error(null, "Timeout"))
        }
    }

    fun getCodechefFriends(handles :String) = viewModelScope.launch {
        codechefFriendsLD.postValue(Resource.Loading())
        val response = repository.fetchFriendListCC(handles)
        if(response.isSuccessful){
            response.body()?.let {
                repository.InsertFriends(it)
                codechefFriendsLD.postValue(Resource.Success(it))
            }
        }
        else{
            codechefFriendsLD.postValue(Resource.Error(null , response.message()))
        }
    }

    fun getCodeforcesFriends(handles : String) = viewModelScope.launch {
        codeforcesUserLD.postValue(Resource.Loading())
        val response = repository.fetchFriendListCF(handles)
        if(response.isSuccessful){
            response.body()?.let {
                repository.InsertFriends(it)
                codeforcesFriendsLD.postValue(Resource.Success(it))
            }
        }
        else{
            codeforcesFriendsLD.postValue(Resource.Error(null , response.message()))
        }
    }

    fun getCodechefContests() =
        repository.getCodechefContests()

    fun getAtCoderContests() =
        repository.getAtCoderContests()

    fun getCodeforcesContest() =
        repository.getCodeforcesContests()

    fun getGoogleContests() =
        repository.getGoogleContests()

    fun getAllContests() =
        repository.getAllContests()

    fun getTopCoderContests()=
        repository.getTopcoderContest()

    fun getAllCCFriends() =
        repository.getAllCCFriends()

    fun getAllCFFriends() =
        repository.getAllCFFriends()

    fun getAllRunningCCContests()=
        repository.getAllRunningCCContest()

    fun getAllRunningACContests()=
        repository.getAllRunningACContest()

    fun getAllRunningCFContests()=
        repository.getAllRunningCFContest()

    fun getAllRunningGContests()=
        repository.getAllRunningGContest()

    fun getAllRunningTContests()=
        repository.getAllRunningTContest()

    fun getAllRunningContests()=
        repository.getAllRunningContest()

    fun InsertFriend(leaderboard: ResponseLeaderboard)= viewModelScope.launch {
        repository.InsertFriends(leaderboard)
    }


    fun DeleteFriends(leaderboard: Leaderboard) = viewModelScope.launch {
        repository.DeleteFriends(leaderboard)
    }



    fun afterMathsCodechef() = viewModelScope.launch {
        repository.deleteAllCC()
    }
    fun afterMathsCodeforces() = viewModelScope.launch {
        repository.deleteAllCF()
    }





}