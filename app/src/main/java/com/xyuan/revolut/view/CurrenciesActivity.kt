package com.xyuan.revolut.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.xyuan.revolut.MyApp
import com.xyuan.revolut.R
import com.xyuan.revolut.injection.component.DaggerCurrenciesComponent
import com.xyuan.revolut.injection.module.CurrenciesModule
import com.xyuan.revolut.viewmodel.CurrenciesViewModel
import javax.inject.Inject

class CurrenciesActivity : AppCompatActivity() {

  @Inject
  lateinit var viewModel: CurrenciesViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

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
      Log.e("XYUAN", it.base)
    })
  }
}
