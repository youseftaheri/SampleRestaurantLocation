package com.yousef.samplerestaurantlocation.ui.mapFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.yousef.samplerestaurantlocation.data.DataManager
import com.yousef.samplerestaurantlocation.data.model.LocationsPOJO
import com.yousef.samplerestaurantlocation.data.model.db.Restaurant
import com.yousef.samplerestaurantlocation.ui.base.BaseViewModel
import com.yousef.samplerestaurantlocation.utils.CommonUtils.distanceBetween
import com.yousef.samplerestaurantlocation.utils.Const
import com.yousef.samplerestaurantlocation.utils.rx.SchedulerProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MapViewModel(dataManager: DataManager?, schedulerProvider: SchedulerProvider?) : BaseViewModel<MapNavigator?>(dataManager!!, schedulerProvider!!) {
    companion object {
        const val TAG = "MapViewModel"
    }

//    private var searchResult: MutableLiveData<List<LocationsPOJO.Venue>> = MutableLiveData()

    fun searchByCategory(centerLatLng: LatLng, currentLatLng: LatLng){//}: LiveData<List<LocationsPOJO.Venue>> {
        val lastLatLng = dataManager.lastLatLng
        if(lastLatLng == null){
            dataManager.lastLatLng = currentLatLng
            callLocationApi(currentLatLng, true)
        }else if(distanceBetween(currentLatLng,lastLatLng)>200F) {
            dataManager.lastLatLng = currentLatLng
            callLocationApi(currentLatLng, true)
        } else if(distanceBetween(centerLatLng,lastLatLng)>500F) {
            callLocationApi(centerLatLng, false)
        }else{
            getLocationsFromDB()
        }
    }

    fun callLocationApi(latLng: LatLng, updateDB: Boolean) {
        navigator!!.showLoading()
        val latLngStr = latLng.latitude.toString() + "," + latLng.longitude.toString()
        viewModelScope.launch (Dispatchers.Main) { // launches coroutine in main thread
            try {
                dataManager.deleteAll()
                val locationsPOJO = dataManager.getLocationResults(latLngStr)
                if (locationsPOJO != null)
                    if (locationsPOJO.meta!!.code == Const.TWO_HUNDRED) {
                        val restaurants: MutableList<Restaurant> = ArrayList()
                        for (i in locationsPOJO.response!!.venues!!.indices) {
                            val restaurant = Restaurant()
                            restaurant.id = locationsPOJO.response!!.venues!![i].id
                            restaurant.name = locationsPOJO.response!!.venues!![i].name
                            restaurant.lat = locationsPOJO.response!!.venues!![i].location!!.lat
                            restaurant.lng = locationsPOJO.response!!.venues!![i].location!!.lng
                            restaurant.address = locationsPOJO.response!!.venues!![i].location!!.address
                            restaurant.crossStreet = locationsPOJO.response!!.venues!![i].location!!.crossStreet
                            restaurant.country = locationsPOJO.response!!.venues!![i].location!!.country
                            restaurant.state = locationsPOJO.response!!.venues!![i].location!!.state
                            restaurant.city = locationsPOJO.response!!.venues!![i].location!!.city
                            restaurant.distance = locationsPOJO.response!!.venues!![i].location!!.distance
                            restaurant.postalCode = locationsPOJO.response!!.venues!![i].location!!.postalCode
                            restaurants.add(restaurant)
                            if(updateDB) dataManager.insertRestaurant(restaurant)
                        }
//                        searchResult.postValue(locationsPOJO.response!!.venues)
                        navigator!!.setMarkers(restaurants)
                    }
                    else navigator!!.handleError(locationsPOJO.meta!!.errorDetail)
            } catch (e: Exception) {
                navigator!!.hideLoading()
                navigator!!.handleError(e.message)
            }
        }
        navigator!!.hideLoading()
    }

    private fun getLocationsFromDB() {
        viewModelScope.launch (Dispatchers.Main) { // launches coroutine in main thread
            try {
                val restaurants : List<Restaurant>? = dataManager.allRestaurants()
                navigator!!.setMarkers(restaurants)
            }catch (e: Exception) {
                navigator!!.handleError(e.message)
            }
        }
    }

    fun storeSelectedRestaurant(selectedRestaurant: Restaurant?){
        dataManager.selectedRestaurant = selectedRestaurant
    }
}