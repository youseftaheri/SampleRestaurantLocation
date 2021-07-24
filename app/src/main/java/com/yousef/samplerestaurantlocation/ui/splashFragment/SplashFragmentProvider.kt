package com.yousef.samplerestaurantlocation.ui.splashFragment

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SplashFragmentProvider {
    @ContributesAndroidInjector(modules = [SplashFragmentModule::class])
    abstract fun provideSplashFragmentFactory(): SplashFragment?
}
