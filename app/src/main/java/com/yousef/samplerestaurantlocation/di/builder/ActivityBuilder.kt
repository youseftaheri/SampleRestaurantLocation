package com.yousef.samplerestaurantlocation.di.builder

import com.yousef.samplerestaurantlocation.ui.detailFragment.DetailFragmentProvider
import com.yousef.samplerestaurantlocation.ui.mainActivity.MainActivity
import com.yousef.samplerestaurantlocation.ui.mainActivity.MainModule
import com.yousef.samplerestaurantlocation.ui.mapFragment.MapFragmentProvider
import com.yousef.samplerestaurantlocation.ui.splashFragment.SplashFragmentProvider
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [MainModule::class,
        SplashFragmentProvider::class,
        MapFragmentProvider::class,
        DetailFragmentProvider::class])
    abstract fun bindMainActivity(): MainActivity?
}