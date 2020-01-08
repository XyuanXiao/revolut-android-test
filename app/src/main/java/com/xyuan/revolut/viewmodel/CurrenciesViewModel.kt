package com.xyuan.revolut.viewmodel

import androidx.lifecycle.LiveData
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
	lateinit var currenciesList: LiveData<CurrenciesResponse>
	private lateinit var ratesList: ArrayList<RateItem>
	private var baseCurrency: String = "EUR"
	private var ratesMultiplier: Float = 1f

	fun bindView(view: CurrenciesActivity) {
		this.view = view
	}

	fun onViewCreated() {
		updateCurrenciesList(baseCurrency)
	}

	private fun updateCurrenciesList(base: String) {
		currenciesList = currenciesRepository.getCurrencies(base)
	}

	fun onCurrenciesReceived(currencies: CurrenciesResponse) {
		ratesList = getRatesList(currencies)
		view.onRatesUpdated(ratesList)
	}

	fun onItemClicked(position: Int) {
		ratesList.removeAt(position).let { item ->
			baseCurrency = item.abbreviation
			ratesMultiplier = item.value
			ratesList.add(0, item)
		}
		view.onRatesUpdated(ratesList)
	}

	private fun getRatesList(response: CurrenciesResponse): ArrayList<RateItem> {
		val rates = ArrayList<RateItem>()

		rates.add(RateItem("AUD", "Australian Dollar", response.rates.AUD ?: 1f))
		rates.add(RateItem("BGN", "Bulgarian Lev", response.rates.BGN ?: 1f))
		rates.add(RateItem("BRL", "Brazilian Real", response.rates.BRL ?: 1f))
		rates.add(RateItem("CAD", "Canadian Dollar", response.rates.CAD ?: 1f))
		rates.add(RateItem("CHF", "Swiss Franc", response.rates.CHF ?: 1f))
		rates.add(RateItem("CNY", "Chinese Yuan", response.rates.CNY ?: 1f))
		rates.add(RateItem("CZK", "Czech Koruna", response.rates.CZK ?: 1f))
		rates.add(RateItem("DKK", "Danish Krone", response.rates.DKK ?: 1f))
		rates.add(RateItem("EUR", "Euro", response.rates.EUR ?: 1f))
		rates.add(RateItem("GBP", "British Pound", response.rates.GBP ?: 1f))
		rates.add(RateItem("HKD", "Hong Kong Dollar", response.rates.HKD ?: 1f))
		rates.add(RateItem("HRK", "Croatian Kuna", response.rates.HRK ?: 1f))
		rates.add(RateItem("HUF", "Hungarian Forint", response.rates.HUF ?: 1f))
		rates.add(RateItem("IDR", "Indonesian Rupiah", response.rates.IDR ?: 1f))
		rates.add(RateItem("ILS", "Israeli Shekel", response.rates.ILS ?: 1f))
		rates.add(RateItem("JPY", "Japonese Yen", response.rates.JPY ?: 1f))
		rates.add(RateItem("KRW", "South Korean Won", response.rates.KRW ?: 1f))
		rates.add(RateItem("MXN", "Mexican Peso", response.rates.MXN ?: 1f))
		rates.add(RateItem("MYR", "Malaysian Ringgit", response.rates.MYR ?: 1f))
		rates.add(RateItem("NOK", "Norwegian Krone", response.rates.NOK ?: 1f))
		rates.add(RateItem("NZD", "New Zealand Dollar", response.rates.NZD ?: 1f))
		rates.add(RateItem("PHP", "Philippine Peso", response.rates.PHP ?: 1f))
		rates.add(RateItem("PLN", "Polish Zloty", response.rates.PLN ?: 1f))
		rates.add(RateItem("RON", "Romanian Leu", response.rates.RON ?: 1f))
		rates.add(RateItem("RUB", "Russian Ruble", response.rates.RUB ?: 1f))
		rates.add(RateItem("SEK", "Swedish Krona", response.rates.SEK ?: 1f))
		rates.add(RateItem("SGD", "Singapore Dollar", response.rates.SGD ?: 1f))
		rates.add(RateItem("THB", "Thai Baht", response.rates.THB ?: 1f))
		rates.add(RateItem("TRY", "Turkish Lire", response.rates.TRY ?: 1f))
		rates.add(RateItem("USD", "United States Dollar", response.rates.USD ?: 1f))
		rates.add(RateItem("ZAR", "South African Rand", response.rates.ZAR ?: 1f))

		return rates
	}
}