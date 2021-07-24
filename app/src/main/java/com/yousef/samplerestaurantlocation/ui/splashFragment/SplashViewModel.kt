package com.yousef.samplerestaurantlocation.ui.splashFragment

import android.annotation.SuppressLint
import androidx.lifecycle.viewModelScope
import com.yousef.samplerestaurantlocation.data.DataManager
import com.yousef.samplerestaurantlocation.ui.base.BaseViewModel
import com.yousef.samplerestaurantlocation.utils.rx.SchedulerProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashViewModel(dataManager: DataManager?, schedulerProvider: SchedulerProvider?) : BaseViewModel<SplashNavigator?>(dataManager!!, schedulerProvider!!) {
    @SuppressLint("CheckResult")
    fun fetchSplash() {
        dataManager.clear()
        viewModelScope.launch (Dispatchers.Main) { // launches coroutine in main thread
            try {
                dataManager.deleteAll()
                    }catch (e: Exception) {
                navigator!!.handleError(e.message)
                    }
        }
    }
}