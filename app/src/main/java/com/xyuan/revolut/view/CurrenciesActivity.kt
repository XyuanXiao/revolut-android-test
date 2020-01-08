package com.xyuan.revolut.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.xyuan.revolut.MyApp
import com.xyuan.revolut.R
import com.xyuan.revolut.injection.component.DaggerCurrenciesComponent
import com.xyuan.revolut.injection.module.CurrenciesModule
import com.xyuan.revolut.model.RateItem
import com.xyuan.revolut.viewmodel.CurrenciesViewModel
import javax.inject.Inject
import  kotlinx.android.synthetic.main.activity_main.*

class CurrenciesActivity : AppCompatActivity() {

  @Inject
  lateinit var viewModel: CurrenciesViewModel

  private lateinit var linearLayoutManager: LinearLayoutManager
	private lateinit var adapter: RecyclerAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    linearLayoutManager = LinearLayoutManager(this)
    rates_list.layoutManager = linearLayoutManager

    initInjection()
    initViewModel()
  }

  private fun initInjection() {
    DaggerCurrenciesComponent
      .builder().applicationComponent((application as MyApp).getApplicationComponent())
      .currenciesModule(CurrenciesModule(this))
      .build()
      .inject(this)
  }

	private fun initViewModel() {
		viewModel.onViewCreated()
		viewModel.bindView(this)
		viewModel.currenciesList.observe(this, Observer {
			viewModel.onCurrenciesReceived(it)
		})
	}

	fun onRatesUpdated(rates: ArrayList<RateItem>) {
		adapter = RecyclerAdapter(rates)
		rates_list.adapter = adapter
	}
}
