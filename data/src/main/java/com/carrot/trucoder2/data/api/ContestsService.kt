package com.carrot.trucoder2.data.api

import com.carrot.trucoder2.data.model.BackendContestContainer
import retrofit2.Call
import retrofit2.http.GET

/** Service that provides access to contests endpoint. */
interface ContestsService {
    @GET("contests")
    fun getContest(): Call<BackendContestContainer>
}