package com.yousef.samplerestaurantlocation.data

import androidx.lifecycle.MediatorLiveData
import com.yousef.samplerestaurantlocation.data.local.db.DbHelper
import com.yousef.samplerestaurantlocation.data.local.prefs.PreferencesHelper
import com.yousef.samplerestaurantlocation.data.remote.ApiHelper

interface DataManager : DbHelper, PreferencesHelper, ApiHelper {

    companion object {
        val mData = MediatorLiveData<String>()
    }
}