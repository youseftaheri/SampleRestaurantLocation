package com.yousef.samplerestaurantlocation.ui.mapFragment

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MapFragmentProvider {
    @ContributesAndroidInjector(modules = [MapFragmentModule::class])
    abstract fun provideMapFragmentFactory(): MapFragment?
}
