package com.yousef.samplerestaurantlocation.data.local.prefs

import android.content.Context
import android.content.SharedPreferences
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.yousef.samplerestaurantlocation.data.model.db.Restaurant
import com.yousef.samplerestaurantlocation.di.PreferenceInfo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPreferencesHelper @Inject constructor(
    context: Context,
    @PreferenceInfo prefFileName: String?) : PreferencesHelper {
    private val mPrefs: SharedPreferences

    companion object {
        private const val LAST_LATLNG = "LAST_LATLNG"
        private const val SELECTED_RESTAURANT = "SELECTED_RESTAURANT"
    }

    override fun clear() {
        mPrefs.edit().clear().apply()
    }

    init {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)
    }

    //<------------------------------------------------------------------------------->\\
    //Store & retrieve Last LatLng
    //<-------------------------------------------------------------------------------->\\
    override var lastLatLng: LatLng?
        get() = Gson().fromJson(mPrefs.getString(LAST_LATLNG, null), LatLng::class.java)
        set(data) {
            mPrefs.edit().putString(LAST_LATLNG, Gson().toJson(data)).apply()
        }


    //<------------------------------------------------------------------------------->\\
    //Store & retrieve selected restaurant
    //<-------------------------------------------------------------------------------->\\
    override var selectedRestaurant: Restaurant?
        get() = Gson().fromJson(mPrefs.getString(SELECTED_RESTAURANT, null), Restaurant::class.java)
        set(data) {
            mPrefs.edit().putString(SELECTED_RESTAURANT, Gson().toJson(data)).apply()
        }

}