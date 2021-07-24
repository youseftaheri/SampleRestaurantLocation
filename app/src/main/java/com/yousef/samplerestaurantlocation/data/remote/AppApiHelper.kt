package com.yousef.samplerestaurantlocation.data.remote

import android.content.Context
import com.yousef.samplerestaurantlocation.data.local.prefs.PreferencesHelper
import com.yousef.samplerestaurantlocation.data.model.*
import com.yousef.samplerestaurantlocation.utils.CommonUtils
import com.yousef.samplerestaurantlocation.utils.Const
import io.reactivex.Single
import okhttp3.RequestBody
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppApiHelper
@Inject constructor(
    private val apis: Apis,
    private val prefrenceHepler: PreferencesHelper,
    private val mContext: Context,
    private val commonUtils: CommonUtils
) : ApiHelper {

    override suspend fun getLocationResults(ll: String): LocationsPOJO? {
        return apis.getLocationResults(ll, Const.RESTAURANT_CATEGORY_ID, "100000")
    }

    override suspend fun getContactResults(venueId: String): ContactPOJO? {
        return apis.getContactResults(venueId)
    }
}