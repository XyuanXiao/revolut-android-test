package com.xyuan.revolut.injection.component

import com.xyuan.revolut.injection.ActivityScope
import com.xyuan.revolut.injection.module.CurrenciesModule
import com.xyuan.revolut.view.CurrenciesActivity
import dagger.Component
import dagger.android.ContributesAndroidInjector

@ActivityScope
@Component (
	dependencies = [ApplicationComponent::class],
	modules = [CurrenciesModule::class]
)
interface CurrenciesComponent {
	@ContributesAndroidInjector
	fun inject(activity: CurrenciesActivity)
}