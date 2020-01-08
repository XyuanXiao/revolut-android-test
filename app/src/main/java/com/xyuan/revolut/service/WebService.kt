package com.xyuan.revolut.service

import com.xyuan.revolut.model.CurrenciesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {

	@GET("/latest")
	fun getCurrencies(
		@Query("base") name: String
	): Call<CurrenciesResponse>
}