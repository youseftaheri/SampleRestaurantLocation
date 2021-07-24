package com.yousef.samplerestaurantlocation.ui.mainActivity

import com.yousef.samplerestaurantlocation.data.DataManager
import com.yousef.samplerestaurantlocation.ui.base.BaseViewModel
import com.yousef.samplerestaurantlocation.utils.rx.SchedulerProvider

class MainViewModel(dataManager: DataManager?, schedulerProvider: SchedulerProvider?) : BaseViewModel<MainNavigator?>(dataManager!!, schedulerProvider!!) {

    val entry: Unit
        get() {
            navigator!!.setUp()
        }
}
