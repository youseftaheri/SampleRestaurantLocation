package com.yousef.samplerestaurantlocation.di.component

import android.app.Application
import com.yousef.samplerestaurantlocation.MyApplication
import com.yousef.samplerestaurantlocation.di.builder.ActivityBuilder
import com.yousef.samplerestaurantlocation.di.module.ApiModule
import com.yousef.samplerestaurantlocation.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, AppModule::class, ApiModule::class, ActivityBuilder::class])
interface AppComponent : AndroidInjector<DaggerApplication?> {
    fun inject(app: MyApplication?)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder?
        fun build(): AppComponent?
    }
}