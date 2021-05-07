package com.carrot.domain.contests


import androidx.lifecycle.MutableLiveData
import com.carrot.trucoder2.data.api.ContestsService
import com.carrot.trucoder2.data.model.BackendContestContainer
import com.carrot.trucoder2.model.Contest
import com.carrot.trucoder2.model.ContestDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton


/** Responsible for handling all data associated with contests. */
@Singleton
class ContestsDataController @Inject constructor(
    private val contestsService: ContestsService,
    private val contestsDataRetriever: ContestsDataRetriever
) {

    private val contestDatabaseLiveData = MutableLiveData<Resource<ContestDatabase>>()

    /** returns [ContestDatabase] containing the contests of only a specified id.*/
    fun filtersContestsById(contestDatabase: ContestDatabase, platformID: Int): List<Contest> {
        return contestDatabase.contestList.filter { it.contestPlatformValue == platformID }
    }

    /** updates the value of [contestDatabaseLiveData] to loading and then
     * calls [getContestsFromBackend] to perform network request.*/
    fun executeNetworkRequestToGetContests() {
        contestDatabaseLiveData.postValue(Resource.Loading())
        getContestsFromBackend()
    }

    /** Perform network requests to the contests endpoint and
     * returns a protobuf generated from the JSON.
     */
    private fun getContestsFromBackend() {
        val call = contestsService.getContest()
        call.enqueue(object : Callback<BackendContestContainer> {
            override fun onResponse(
                call: Call<BackendContestContainer>,
                response: Response<BackendContestContainer>
            ) {
                contestDatabaseLiveData.postValue(
                    if (response.isSuccessful) {
                        contestsDataRetriever.loadContestsData(response.body())
                    } else {
                        Resource.UncaughtError(
                            ContestDatabase.getDefaultInstance(),
                            "Retrofit response not within 200 to 299 range."
                        )
                    }
                )
            }

            override fun onFailure(call: Call<BackendContestContainer>, t: Throwable) {
                contestDatabaseLiveData.postValue(
                    Resource.UncaughtError(
                        ContestDatabase.getDefaultInstance(),
                        t.message ?: "Uncaught exception occurred in ContestsDataController."
                    )
                )
            }
        })
    }

    /** returns [contestDatabaseLiveData]. */
    fun getContestLiveData() = contestDatabaseLiveData

}