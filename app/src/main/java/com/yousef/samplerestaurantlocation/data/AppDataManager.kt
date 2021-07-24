package com.yousef.samplerestaurantlocation.data

import android.content.Context
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.yousef.samplerestaurantlocation.data.local.db.DbHelper
import com.yousef.samplerestaurantlocation.data.local.prefs.PreferencesHelper
import com.yousef.samplerestaurantlocation.data.model.*
import com.yousef.samplerestaurantlocation.data.model.db.Restaurant
import com.yousef.samplerestaurantlocation.data.remote.ApiHelper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDataManager
@Inject constructor(
    private val mContext: Context,
    private val mPreferencesHelper: PreferencesHelper,
    private val mApiHelper: ApiHelper,
    private val mDbHelper: DbHelper,
    private val mGson: Gson) : DataManager {

    companion object {
        private const val TAG = "AppDataManager"
    }

    override fun clear() {
        mPreferencesHelper.clear()
    }

    override var lastLatLng: LatLng?
        get() = mPreferencesHelper.lastLatLng
        set(data) {
            mPreferencesHelper.lastLatLng = data
        }

    override var selectedRestaurant: Restaurant?
        get() = mPreferencesHelper.selectedRestaurant
        set(data) {
            mPreferencesHelper.selectedRestaurant = data
        }

    override suspend fun deleteAll() {
        mDbHelper.deleteAll()
    }

    override suspend fun getLocationResults(ll: String): LocationsPOJO? {
        return mApiHelper.getLocationResults(ll)
    }

    override suspend fun getContactResults(venueId: String): ContactPOJO? {
        return mApiHelper.getContactResults(venueId)
    }

    override suspend fun allRestaurants(): List<Restaurant>?{
        return mDbHelper.allRestaurants()
    }

    override suspend fun insertRestaurant(restaurant: Restaurant?) {
        mDbHelper.insertRestaurant(restaurant)
    }

    override suspend fun findRestaurantById(id: String?) {
        mDbHelper.findRestaurantById(id)
    }
}