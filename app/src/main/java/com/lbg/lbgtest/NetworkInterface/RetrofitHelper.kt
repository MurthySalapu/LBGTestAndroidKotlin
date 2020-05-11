package com.lbg.lbgtest.NetworkInterface

import com.lbg.lbgtest.common.AppSettings
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Name - RetrofitHelper
 * Purpose - Retrofit Helper to create service for requested class
 */
open class RetrofitHelper {

    private var sInstance: Retrofit? = null

    fun <S> createService(serviceClass: Class<S>): S {
        return getInstance().create(serviceClass)
    }

    /**
     * Singleton retrofit instance
     * @return - retrofit
     */
    /**
     * Singleton retrofit instance
     * @return - retrofit
     */
    private fun getInstance(): Retrofit {

        if (sInstance == null) {
            sInstance = retrofit
        }
        return sInstance as Retrofit
    }

    /**
     * Get retrofit instance
     * @return - retrofit object
     */
    private val retrofit: Retrofit
        get() = Retrofit.Builder()
            .baseUrl(AppSettings.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}
