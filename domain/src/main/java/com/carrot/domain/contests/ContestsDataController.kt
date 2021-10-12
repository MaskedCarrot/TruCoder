package com.carrot.domain.contests


import android.icu.util.Calendar
import androidx.lifecycle.MutableLiveData
import com.carrot.domain.util.Resource
import com.carrot.trucoder2.data.api.ContestsService
import com.carrot.trucoder2.data.model.BackendContestContainer
import com.carrot.trucoder2.model.Contest
import com.carrot.trucoder2.model.ContestDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton


/** Responsible for handling all data associated with contests. */
@Singleton
class ContestsDataController @Inject constructor(
    private val contestsService: ContestsService,
    private val contestsDataRetriever: ContestsDataRetriever
) {
    private val contestDatabaseLiveData = MutableLiveData<Resource<ContestDatabase>>()

    /** Returns [ContestDatabase] containing the contests of only a specified id.*/
    fun filtersContestsById(
        contestDatabase: ContestDatabase,
        platformIdList: List<Int>
    ): List<Contest> {
        return contestDatabase.contestList.filter {
            platformIdList.contains(it.contestPlatformValue)
        }
    }

    /**
     * Returns [ContestDatabase] filtered by the platformId till a specific number of days in
     * advance. */
    fun filterUpcomingContestByTime(
        contestDatabase: ContestDatabase,
        platformIdList: ArrayList<Int>,
        daysInFuture: Int
    ): List<Contest> {
        val currentTIme = Calendar.getInstance().timeInMillis

        // Filter list by checking the following three conditions:
        // 1. The platformId should exist in the specified list of platform ids
        // 2. the difference between the start time of the contest and the current time should be
        // less than or equal to the specified number of days.
        // 3. The contest should be yet to start, i.e. the start time of the contest should be less
        // than the current time.
        return contestDatabase.contestList.filter {
            platformIdList.contains(it.contestPlatformValue) &&
                    (TimeUnit.MILLISECONDS.toDays(it.startTime - currentTIme) <=
                            daysInFuture.toLong())
                    && (currentTIme < it.startTime)
        }
    }

    /**
     * Updates the value of [contestDatabaseLiveData] to loading and then calls
     * [getContestsFromBackend] to perform network request. Result of this function should be
     * retrieved using the function [getContestLiveData].
     */
    fun executeNetworkRequestToGetContests() {
        contestDatabaseLiveData.postValue(Resource.Loading())
        getContestsFromBackend()
    }


    /** returns [contestDatabaseLiveData]. */
    fun getContestLiveData() = contestDatabaseLiveData

    /**
     * Perform network requests to the contests endpoint and
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
}