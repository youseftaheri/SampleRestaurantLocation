package com.yousef.samplerestaurantlocation.ui.base

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import com.yousef.samplerestaurantlocation.data.DataManager
import com.yousef.samplerestaurantlocation.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference

abstract class BaseViewModel<N>(val dataManager: DataManager,
                                val schedulerProvider: SchedulerProvider) : ViewModel() {
    val isLoading = ObservableBoolean()
    val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var mNavigator: WeakReference<N>? = null
    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    val navigator: N?
        get() = mNavigator!!.get()

    fun setNavigator(navigator: N) {
        mNavigator = WeakReference(navigator)
    }
}