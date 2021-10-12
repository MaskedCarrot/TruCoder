package com.carrot.domain.basicdetails

import androidx.lifecycle.MutableLiveData
import com.carrot.domain.util.Resource
import com.carrot.trucoder2.data.api.BasicDetailsService
import com.carrot.trucoder2.data.model.BackendBasicDetailsContainer
import com.carrot.trucoder2.model.BasicDetails
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class BasicDetailsController @Inject constructor(
    private val basicDetailsService: BasicDetailsService,
    private val basicDetailsRetriever: BasicDetailsRetriever
) {

    private val basicDetailsLiveData = MutableLiveData<Resource<BasicDetails>>()

    /**
     * Updates the value of [basicDetailsLiveData] to loading and then calls
     * the function [getBasicDetailsFromBackend] to perform network request. Result of this function
     * should be retrieved using the the function [getBasicDetailsLiveData].
     */
    fun executeNetworkRequestToGetBasicDetails(handle: String) {
        basicDetailsLiveData.postValue(Resource.Loading())
        getBasicDetailsFromBackend(handle)
    }

    /** Returns [basicDetailsLiveData]. */
    fun getBasicDetailsLiveData() = basicDetailsLiveData

    private fun getBasicDetailsFromBackend(handle: String) {
        val call = basicDetailsService.getBasicDetails(handle)
        call.enqueue(object : Callback<BackendBasicDetailsContainer> {
            override fun onResponse(
                call: Call<BackendBasicDetailsContainer>,
                response: Response<BackendBasicDetailsContainer>
            ) {
                basicDetailsLiveData.postValue(
                    if (response.isSuccessful) {
                        basicDetailsRetriever.loadBasicDetailsData(response.body())
                    } else {
                        Resource.UncaughtError(
                            BasicDetails.getDefaultInstance(),
                            "Retrofit response not within 200 to 299 range."
                        )
                    }
                )
            }

            override fun onFailure(call: Call<BackendBasicDetailsContainer>, t: Throwable) {
                Resource.UncaughtError(
                    BasicDetails.getDefaultInstance(),
                    t.message ?: "Uncaught exception occurred in BasicDetailsController."
                )
            }
        })
    }
}