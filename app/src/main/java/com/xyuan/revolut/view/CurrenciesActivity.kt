package com.xyuan.revolut.view

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.xyuan.revolut.MyApp
import com.xyuan.revolut.R
import com.xyuan.revolut.injection.component.DaggerCurrenciesComponent
import com.xyuan.revolut.injection.module.CurrenciesModule
import com.xyuan.revolut.model.RateItem
import com.xyuan.revolut.viewmodel.CurrenciesViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class CurrenciesActivity : AppCompatActivity(), OnItemClickListener, OnItemValueChangedListener {

  @Inject
  lateinit var viewModel: CurrenciesViewModel

  private lateinit var linearLayoutManager: LinearLayoutManager
	private lateinit var adapter: RecyclerAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    linearLayoutManager = LinearLayoutManager(this)
    rates_list.layoutManager = linearLayoutManager

		initAdapter(ArrayList())
    initInjection()
    initViewModel()
  }

	private fun initAdapter(list: ArrayList<RateItem>) {
		adapter = RecyclerAdapter(list, this, this)
		rates_list.adapter = adapter
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

	fun updateRates(rates: ArrayList<RateItem>) {
		adapter.updateRates(rates)
	}

	override fun onItemClicked(position: Int) {
		viewModel.onItemClicked(position)
	}

	override fun onValueChanged(abbreviation: String, value: Float, editText: EditText) {
		if (currentFocus == editText) {
			viewModel.onItemValueChanged(abbreviation, value)
		}
	}
}
