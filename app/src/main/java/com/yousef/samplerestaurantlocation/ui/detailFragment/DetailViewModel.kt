package com.yousef.samplerestaurantlocation.ui.detailFragment

import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.yousef.samplerestaurantlocation.data.DataManager
import com.yousef.samplerestaurantlocation.data.model.ContactPOJO
import com.yousef.samplerestaurantlocation.ui.base.BaseViewModel
import com.yousef.samplerestaurantlocation.utils.CommonUtils.getIfExists
import com.yousef.samplerestaurantlocation.utils.Const
import com.yousef.samplerestaurantlocation.utils.rx.SchedulerProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DetailViewModel(dataManager: DataManager?, schedulerProvider: SchedulerProvider?) : BaseViewModel<DetailNavigator?>(dataManager!!, schedulerProvider!!) {

    @JvmField
    val name: ObservableField<String> = ObservableField("")
    @JvmField
    val location: ObservableField<String> = ObservableField("")
    @JvmField
    var address: ObservableField<String> = ObservableField("")
    @JvmField
    var distance: ObservableField<String> = ObservableField("")
    @JvmField
    val phone: ObservableField<String> = ObservableField("")
    @JvmField
    var facebook: ObservableField<String> = ObservableField("")
    @JvmField
    var twitter: ObservableField<String> = ObservableField("")

    init {
        name.set(dataManager!!.selectedRestaurant?.name)
        location.set(dataManager.selectedRestaurant?.city + " , " +
                dataManager.selectedRestaurant?.state + " , " +
                dataManager.selectedRestaurant?.country)
        address.set(dataManager.selectedRestaurant?.address + " , " +
                dataManager.selectedRestaurant?.crossStreet)
        distance.set(dataManager.selectedRestaurant?.distance.toString())
    }

    fun back() {
        navigator!!.back()
    }


    fun showDetails(restaurantId: String) {
        navigator?.showLoading()
        viewModelScope.launch (Dispatchers.Main) { // launches coroutine in main thread
            try {
                val contactPOJO = dataManager.getContactResults(restaurantId)
                navigator?.hideLoading()
                if (contactPOJO != null)
                    if (contactPOJO.meta!!.code == Const.TWO_HUNDRED) {
                        val contact: ContactPOJO.Contact? = contactPOJO.response!!.venue!!.contact
                        phone.set(getIfExists(contact?.formattedPhone))
                        facebook.set(getIfExists(contact?.facebook))
                        twitter.set(getIfExists(contact?.twitter))
                    }
                    else navigator?.handleError(contactPOJO.meta!!.errorDetail)
            } catch (e: Exception) {
                navigator!!.hideLoading()
                navigator!!.handleError(e.message)
            }
        }
    }


}