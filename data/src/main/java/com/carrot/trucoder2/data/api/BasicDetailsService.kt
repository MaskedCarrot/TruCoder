package com.carrot.trucoder2.data.api

import com.carrot.trucoder2.data.model.BackendBasicDetailsContainer
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/** Service that provides access to basic-details endpoint. */
interface BasicDetailsService {
    @GET("basic_details/{handle}")
    fun getBasicDetails(
        @Query("handle") handle: String
    ): Call<BackendBasicDetailsContainer>
}
