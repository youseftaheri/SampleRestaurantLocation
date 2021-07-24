package com.yousef.samplerestaurantlocation.data.remote

import com.yousef.samplerestaurantlocation.data.model.*
import io.reactivex.Single

interface ApiHelper {
    suspend fun getLocationResults(ll: String): LocationsPOJO?
    suspend fun getContactResults(venueId: String): ContactPOJO?
}