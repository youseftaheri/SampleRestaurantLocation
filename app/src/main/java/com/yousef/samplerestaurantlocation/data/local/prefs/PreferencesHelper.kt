package com.yousef.samplerestaurantlocation.data.local.prefs

import com.google.android.gms.maps.model.LatLng
import com.yousef.samplerestaurantlocation.data.model.db.Restaurant

interface PreferencesHelper {
    fun clear()
    var lastLatLng: LatLng?
    var selectedRestaurant: Restaurant?
}