package com.yousef.samplerestaurantlocation.ui.mapFragment

import com.yousef.samplerestaurantlocation.data.model.LocationsPOJO
import com.yousef.samplerestaurantlocation.data.model.db.Restaurant

interface MapNavigator {
    fun handleError(exception: String?)
    fun showLoading()
    fun hideLoading()
    fun setMarkers(venues: List<Restaurant>?)
}