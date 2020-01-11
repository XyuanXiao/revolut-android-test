package com.xyuan.revolut.viewmodel

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xyuan.revolut.model.CurrenciesResponse
import com.xyuan.revolut.model.RateItem
import com.xyuan.revolut.service.CurrenciesRepository
import com.xyuan.revolut.view.CurrenciesActivity
import javax.inject.Inject


class CurrenciesViewModel @Inject constructor(
	private var currenciesRepository: CurrenciesRepository
) : ViewModel() {

	private lateinit var view: CurrenciesActivity
	lateinit var currenciesList: MutableLiveData<CurrenciesResponse>
	private lateinit var ratesList: ArrayList<RateItem>
	private var baseCurrency: String = "EUR"
	private var ratesMultiplier: Float = 1f

	fun bindView(view: CurrenciesActivity) {
		this.view = view
	}

	fun onViewCreated() {
		updateCurrenciesList()
	}

	private fun updateCurrenciesList() {
		currenciesList = currenciesRepository.getCurrencies(baseCurrency)
	}

	private fun moveItemToTop(position: Int) {
		ratesList.removeAt(position).let { item ->
			baseCurrency = item.abbreviation
			ratesMultiplier = item.value
			ratesList.add(0, item)
		}
	}

	fun onItemClicked(position: Int) {
		moveItemToTop(position)
		view.updateRates(ratesList)
	}

	fun onItemValueChanged(abbreviation: String, value: Float) {
		var position = 0
		ratesList.forEachIndexed { index, item ->
			if (item.abbreviation == abbreviation) {
				ratesMultiplier = value / item.value
				position = index
			}
		}
		ratesList.forEach {
			it.value = it.value * ratesMultiplier
		}
		moveItemToTop(position)

		view.updateRates(ratesList)
	}

	fun onCurrenciesReceived(currencies: CurrenciesResponse) {
		ratesList = getRatesList(currencies)
		view.updateRates(ratesList)
		getCurrenciesAgain()
	}

	private fun getCurrenciesAgain() {
		val handler = Handler()
		handler.postDelayed({
			updateCurrenciesList()
		}, 5000) //I made it request each 5 seconds because 1 second was too frequent
	}

	private fun getRatesList(response: CurrenciesResponse): ArrayList<RateItem> {
		val rates = ArrayList<RateItem>()

		rates.add(RateItem("AUD", "Australian Dollar", response.rates.AUD?.times(ratesMultiplier) ?: ratesMultiplier))
		rates.add(RateItem("BGN", "Bulgarian Lev", response.rates.BGN?.times(ratesMultiplier) ?: ratesMultiplier))
		rates.add(RateItem("BRL", "Brazilian Real", response.rates.BRL?.times(ratesMultiplier) ?: ratesMultiplier))
		rates.add(RateItem("CAD", "Canadian Dollar", response.rates.CAD?.times(ratesMultiplier) ?: ratesMultiplier))
		rates.add(RateItem("CHF", "Swiss Franc", response.rates.CHF?.times(ratesMultiplier) ?: ratesMultiplier))
		rates.add(RateItem("CNY", "Chinese Yuan", response.rates.CNY?.times(ratesMultiplier) ?: ratesMultiplier))
		rates.add(RateItem("CZK", "Czech Koruna", response.rates.CZK?.times(ratesMultiplier) ?: ratesMultiplier))
		rates.add(RateItem("DKK", "Danish Krone", response.rates.DKK?.times(ratesMultiplier) ?: ratesMultiplier))
		rates.add(RateItem("EUR", "Euro", response.rates.EUR?.times(ratesMultiplier) ?: ratesMultiplier))
		rates.add(RateItem("GBP", "British Pound", response.rates.GBP?.times(ratesMultiplier) ?: ratesMultiplier))
		rates.add(RateItem("HKD", "Hong Kong Dollar", response.rates.HKD?.times(ratesMultiplier) ?: ratesMultiplier))
		rates.add(RateItem("HRK", "Croatian Kuna", response.rates.HRK?.times(ratesMultiplier) ?: ratesMultiplier))
		rates.add(RateItem("HUF", "Hungarian Forint", response.rates.HUF?.times(ratesMultiplier) ?: ratesMultiplier))
		rates.add(RateItem("IDR", "Indonesian Rupiah", response.rates.IDR?.times(ratesMultiplier) ?: ratesMultiplier))
		rates.add(RateItem("ILS", "Israeli Shekel", response.rates.ILS?.times(ratesMultiplier) ?: ratesMultiplier))
		rates.add(RateItem("JPY", "Japonese Yen", response.rates.JPY?.times(ratesMultiplier) ?: ratesMultiplier))
		rates.add(RateItem("KRW", "South Korean Won", response.rates.KRW?.times(ratesMultiplier) ?: ratesMultiplier))
		rates.add(RateItem("MXN", "Mexican Peso", response.rates.MXN?.times(ratesMultiplier) ?: ratesMultiplier))
		rates.add(RateItem("MYR", "Malaysian Ringgit", response.rates.MYR?.times(ratesMultiplier) ?: ratesMultiplier))
		rates.add(RateItem("NOK", "Norwegian Krone", response.rates.NOK?.times(ratesMultiplier) ?: ratesMultiplier))
		rates.add(RateItem("NZD", "New Zealand Dollar", response.rates.NZD?.times(ratesMultiplier) ?: ratesMultiplier))
		rates.add(RateItem("PHP", "Philippine Peso", response.rates.PHP?.times(ratesMultiplier) ?: ratesMultiplier))
		rates.add(RateItem("PLN", "Polish Zloty", response.rates.PLN?.times(ratesMultiplier) ?: ratesMultiplier))
		rates.add(RateItem("RON", "Romanian Leu", response.rates.RON?.times(ratesMultiplier) ?: ratesMultiplier))
		rates.add(RateItem("RUB", "Russian Ruble", response.rates.RUB?.times(ratesMultiplier) ?: ratesMultiplier))
		rates.add(RateItem("SEK", "Swedish Krona", response.rates.SEK?.times(ratesMultiplier) ?: ratesMultiplier))
		rates.add(RateItem("SGD", "Singapore Dollar", response.rates.SGD?.times(ratesMultiplier) ?: ratesMultiplier))
		rates.add(RateItem("THB", "Thai Baht", response.rates.THB?.times(ratesMultiplier) ?: ratesMultiplier))
		rates.add(RateItem("TRY", "Turkish Lire", response.rates.TRY?.times(ratesMultiplier) ?: ratesMultiplier))
		rates.add(RateItem("USD", "United States Dollar", response.rates.USD?.times(ratesMultiplier) ?: ratesMultiplier))
		rates.add(RateItem("ZAR", "South African Rand", response.rates.ZAR?.times(ratesMultiplier) ?: ratesMultiplier))

		for (i in 0..rates.size) {
			if (rates[i].abbreviation == baseCurrency) {
				rates.add(0, rates.removeAt(i))
				break
			}
		}

		return rates
	}
}