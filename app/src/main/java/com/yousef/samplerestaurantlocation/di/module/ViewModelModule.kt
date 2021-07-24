package com.yousef.samplerestaurantlocation.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yousef.samplerestaurantlocation.di.ViewModelKey
import com.yousef.samplerestaurantlocation.factory.ViewModelProviderFactory
import com.yousef.samplerestaurantlocation.ui.detailFragment.DetailViewModel
import com.yousef.samplerestaurantlocation.ui.mainActivity.MainViewModel
import com.yousef.samplerestaurantlocation.ui.mapFragment.MapViewModel
import com.yousef.samplerestaurantlocation.ui.splashFragment.SplashViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelProviderFactory?): ViewModelProvider.Factory?

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel?): ViewModel?
    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindSplashViewModel(splashViewModel: SplashViewModel?): ViewModel?
    @Binds
    @IntoMap
    @ViewModelKey(MapViewModel::class)
    abstract fun bindMapViewModel(mapViewModel: MapViewModel?): ViewModel?
    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    abstract fun bindDetailViewModel(detailViewModel: DetailViewModel?): ViewModel?
}