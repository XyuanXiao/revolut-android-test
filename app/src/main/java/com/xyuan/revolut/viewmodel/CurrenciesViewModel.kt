package com.xyuan.revolut.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.xyuan.revolut.model.CurrenciesResponse
import com.xyuan.revolut.service.CurrenciesRepository
import com.xyuan.revolut.view.CurrenciesActivity
import javax.inject.Inject


class CurrenciesViewModel @Inject constructor(
    private var currenciesRepository: CurrenciesRepository
) : ViewModel() {

    private lateinit var view: CurrenciesActivity
    lateinit var currenciesList: LiveData<CurrenciesResponse>

    fun bindView(view: CurrenciesActivity) {
        this.view = view
    }

    fun onViewCreated() {
        updateCurrenciesList("EUR")
    }

    private fun updateCurrenciesList(base: String) {
        currenciesList = currenciesRepository.getCurrencies(base)
    }
}