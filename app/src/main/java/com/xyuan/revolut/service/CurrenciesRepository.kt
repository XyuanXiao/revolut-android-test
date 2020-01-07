package com.xyuan.revolut.service

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.xyuan.revolut.model.CurrenciesResponse
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Singleton
class CurrenciesRepository {

    private val BASE_URL = "https://revolut.duckdns.org/"

    private val webService = Retrofit
        .Builder()
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .client(OkHttpClient().newBuilder().build())
        .baseUrl(BASE_URL)
        .build()
        .create(WebService::class.java)

    fun getCurrencies(base: String): LiveData<CurrenciesResponse> {
        val data = MutableLiveData<CurrenciesResponse>()

        webService.getCurrencies(base).enqueue(object : Callback<CurrenciesResponse> {
            override fun onResponse(
                call: Call<CurrenciesResponse>,
                response: Response<CurrenciesResponse>
            ) {
                data.value = response.body()
            }

            override fun onFailure(call: Call<CurrenciesResponse>, t: Throwable) {
                //TODO
            }
        })
        return data
    }
}