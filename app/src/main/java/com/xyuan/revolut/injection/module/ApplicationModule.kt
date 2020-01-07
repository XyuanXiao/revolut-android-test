package com.xyuan.revolut.injection.module

import android.app.Application
import android.content.Context
import com.xyuan.revolut.injection.AppScope
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule {

  @Provides
  @AppScope
  fun providesApplication(app: Application): Context = app
}