package com.carrot.domain.contests

import com.carrot.domain.util.Constants.DEFAULT_LONG_VALUE
import com.carrot.domain.util.Constants.DEFAULT_STRING_VALUE
import com.carrot.domain.util.Constants.INTERNAL_SERVER_ERROR
import com.carrot.domain.util.Constants.SUCCESS_RESPONSE
import com.carrot.domain.util.Resource
import com.carrot.trucoder2.data.model.BackendContestContainer
import com.carrot.trucoder2.model.Contest
import com.carrot.trucoder2.model.ContestDatabase
import com.carrot.trucoder2.model.ContestPlatform.CONTESTS_PLATFORM_UNSPECIFIED_VALUE
import javax.inject.Inject

class ContestsDataRetriever @Inject constructor() {

    /** Loads the  contest data received from the server. */
    fun loadContestsData(responseBody: BackendContestContainer?): Resource<ContestDatabase> {
        if (responseBody == null) {
            return Resource.UncaughtError(
                ContestDatabase.getDefaultInstance(),
                "response body was null."
            )
        }
        if (responseBody.status == null || responseBody.contest == null) {
            return Resource.UncaughtError(
                ContestDatabase.getDefaultInstance(),
                "Either status or contests list was null."
            )
        }
        return when (responseBody.status) {
            SUCCESS_RESPONSE -> {
                val contestsDatabaseBuilder = ContestDatabase.newBuilder()
                responseBody.contest!!.forEach {
                    contestsDatabaseBuilder.addContest(
                        Contest.newBuilder()
                            .setContestPlatformValue(it.id ?: CONTESTS_PLATFORM_UNSPECIFIED_VALUE)
                            .setDuration(it.duration ?: DEFAULT_LONG_VALUE)
                            .setStartTime(it.statTime ?: DEFAULT_LONG_VALUE)
                            .setEndTime(it.endTime ?: DEFAULT_LONG_VALUE)
                            .setEvent(it.event ?: DEFAULT_STRING_VALUE)
                    )
                }
                Resource.Success(contestsDatabaseBuilder.build())
            }
            INTERNAL_SERVER_ERROR -> {
                Resource.InternalServerError(
                    ContestDatabase.getDefaultInstance(),
                    "Internal server error."
                )
            }
            else -> {
                Resource.UncaughtError(
                    ContestDatabase.getDefaultInstance(),
                    "Uncaught exception occurred in ContestsDataRetriever"
                )
            }
        }
    }
}