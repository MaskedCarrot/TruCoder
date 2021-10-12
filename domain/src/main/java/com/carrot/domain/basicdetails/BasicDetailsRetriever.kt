package com.carrot.domain.basicdetails

import com.carrot.domain.util.Constants
import com.carrot.domain.util.Constants.DEFAULT_INT_VALUE
import com.carrot.domain.util.Constants.DEFAULT_LONG_VALUE
import com.carrot.domain.util.Constants.DEFAULT_STRING_VALUE
import com.carrot.domain.util.Constants.SUCCESS_RESPONSE
import com.carrot.domain.util.Resource
import com.carrot.trucoder2.data.model.BackendBasicDetailsContainer
import com.carrot.trucoder2.data.model.BackendContestContainer
import com.carrot.trucoder2.model.*
import javax.inject.Inject

class BasicDetailsRetriever @Inject constructor() {

    /** Loads the  basic details data received from the server. */
    fun loadBasicDetailsData(responseBody: BackendBasicDetailsContainer?): Resource<BasicDetails> {
        if (responseBody == null) {
            return Resource.UncaughtError(
                BasicDetails.getDefaultInstance(),
                "response body was null."
            )
        }

        if (responseBody.status == null || responseBody.basicDetails == null) {
            return Resource.UncaughtError(
                BasicDetails.getDefaultInstance(),
                "Either status or basic details was null."
            )
        }

        return when (responseBody.status) {
            SUCCESS_RESPONSE -> {
                val basicDetailsBuilder = BasicDetails.newBuilder().apply {
                    handle = responseBody.basicDetails!!.handle?: DEFAULT_STRING_VALUE
                    avatar = responseBody.basicDetails!!.avatar?: DEFAULT_STRING_VALUE
                    level = responseBody.basicDetails!!.level?: DEFAULT_STRING_VALUE
                    rating = responseBody.basicDetails!!.rating?: DEFAULT_INT_VALUE
                }
                Resource.Success(basicDetailsBuilder.build())
            }

            Constants.INTERNAL_SERVER_ERROR -> {
                Resource.InternalServerError(
                    BasicDetails.getDefaultInstance(),
                    "Internal server error."
                )
            }
            else -> {
                Resource.UncaughtError(
                    BasicDetails.getDefaultInstance(),
                    "Uncaught exception occurred in BasicDetailsRetriever."
                )
            }
        }
    }
}