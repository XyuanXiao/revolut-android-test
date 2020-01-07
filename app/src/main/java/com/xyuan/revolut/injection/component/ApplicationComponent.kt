package com.xyuan.revolut.injection.component

import android.app.Application
import com.xyuan.revolut.MyApp
import com.xyuan.revolut.injection.AppScope
import com.xyuan.revolut.injection.module.ApplicationModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

@AppScope
@Component(
  modules = [
    AndroidInjectionModule::class,
    ApplicationModule::class
  ]
)
interface ApplicationComponent : AndroidInjector<MyApp> {

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun application(application: Application): Builder
    fun build(): ApplicationComponent
  }
  override fun inject(myApp: MyApp)
  fun application(): Application
}