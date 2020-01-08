package com.xyuan.revolut.injection.module

import com.xyuan.revolut.injection.ActivityScope
import com.xyuan.revolut.service.CurrenciesRepository
import com.xyuan.revolut.service.WebService
import com.xyuan.revolut.view.CurrenciesActivity
import com.xyuan.revolut.viewmodel.CurrenciesViewModel
import dagger.Module
import dagger.Provides

@Module
class CurrenciesModule(
	var currenciesActivity: CurrenciesActivity
) {

	@Provides
	@ActivityScope
	fun providesContext(): CurrenciesActivity = currenciesActivity

	@Provides
	@ActivityScope
	fun providesCurrenciesViewModel(
		currenciesRepository: CurrenciesRepository
	): CurrenciesViewModel = CurrenciesViewModel(currenciesRepository)

	@Provides
	@ActivityScope
	fun providesCurrenciesRepository(): CurrenciesRepository = CurrenciesRepository()
}