package com.carrot.trucoder2.data

import com.carrot.trucoder2.data.api.BasicDetailsService
import com.carrot.trucoder2.data.api.ContestsService
import com.carrot.trucoder2.data.model.TruCoderRetrofit
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {
    /**
     * Provides the Retrofit object.
     *
     * @return the Retrofit object
     */
    @TruCoderRetrofit
    @Provides
    @Singleton
    fun providesRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(NetworkSettings.getBaseUrl())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    /**
     * Provides the Classroom service implementation.
     *
     * @param retrofit the Retrofit object used to instantiate the service
     * @return the basic-details service implementation.
     */
    @Provides
    @Singleton
    fun providesBasicDetailsService(@TruCoderRetrofit retrofit: Retrofit): BasicDetailsService {
        return retrofit.create(BasicDetailsService::class.java)
    }

    /**
     * Provides the Classroom service implementation.
     *
     * @param retrofit the Retrofit object used to instantiate the service
     * @return the contests service implementation.
     */
    @Provides
    @Singleton
    fun providersContestsService(@TruCoderRetrofit retrofit: Retrofit): ContestsService {
        return retrofit.create(ContestsService::class.java)
    }
}