package com.yousef.samplerestaurantlocation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yousef.samplerestaurantlocation.data.DataManager
import com.yousef.samplerestaurantlocation.ui.detailFragment.DetailViewModel
import com.yousef.samplerestaurantlocation.ui.mainActivity.MainViewModel
import com.yousef.samplerestaurantlocation.ui.mapFragment.MapViewModel
import com.yousef.samplerestaurantlocation.ui.splashFragment.SplashViewModel
import com.yousef.samplerestaurantlocation.utils.rx.SchedulerProvider
import javax.inject.Inject
import javax.inject.Singleton

@Suppress("UNCHECKED_CAST")
@Singleton
class ViewModelProviderFactory
@Inject
constructor(private val dataManager: DataManager, private val schedulerProvider: SchedulerProvider) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                return MainViewModel(dataManager, schedulerProvider) as T
            }
            modelClass.isAssignableFrom(SplashViewModel::class.java) -> {
                return SplashViewModel(dataManager, schedulerProvider) as T
            }
            modelClass.isAssignableFrom(MapViewModel::class.java) -> {
                return MapViewModel(dataManager, schedulerProvider) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                return DetailViewModel(dataManager, schedulerProvider) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}