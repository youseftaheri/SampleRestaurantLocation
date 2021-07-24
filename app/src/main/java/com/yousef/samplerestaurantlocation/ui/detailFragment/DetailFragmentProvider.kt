package com.yousef.samplerestaurantlocation.ui.detailFragment

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class DetailFragmentProvider {
    @ContributesAndroidInjector(modules = [DatailFragmentModule::class])
    abstract fun provideDetailFragmentFactory(): DetailFragment?
}
