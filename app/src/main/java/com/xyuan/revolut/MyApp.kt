package com.xyuan.revolut

import android.app.Activity
import android.app.Application
import android.content.Context
import com.xyuan.revolut.injection.component.ApplicationComponent
import com.xyuan.revolut.injection.component.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class MyApp : Application(), HasActivityInjector {

	@Inject
	lateinit var activityInjector: DispatchingAndroidInjector<Activity>

	private lateinit var applicationComponent: ApplicationComponent

	override fun onCreate() {
		super.onCreate()
		applicationComponent = DaggerApplicationComponent
			.builder()
			.application(this)
			.build()
	}

	fun get(context: Context) = context.applicationContext as MyApp

	fun getApplicationComponent(): ApplicationComponent = applicationComponent

	override fun activityInjector(): AndroidInjector<Activity> {
		return activityInjector
	}
}