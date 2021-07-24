package com.yousef.samplerestaurantlocation.data.remote

import com.yousef.samplerestaurantlocation.data.model.*
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface Apis {
    companion object {
        private const val CLIENT_ID = "QYGHM0MO2CQET011VOVO54U15OWQSDI2QCFGRIVOB1QH5FRA"
        private const val CLIENT_SECRET = "TPKU1R10SXAS2WKXJ44XRALKTQJYLAP5G4XMLLDCDOIHB4UU"
        private const val VERSION = "20210722"
        private const val COMMON_PARAMS = "&client_id=$CLIENT_ID&client_secret=$CLIENT_SECRET&v=$VERSION"

    }

    @GET("venues/search?limit=30$COMMON_PARAMS")
    suspend fun getLocationResults(@Query("ll") ll: String,
                                   @Query("categoryId") categoryId: String,
                                   @Query("radius") radius: String): LocationsPOJO?

    @GET("venues/{venue_id}/?$COMMON_PARAMS")
    suspend fun getContactResults(@Path("venue_id") venueId: String): ContactPOJO?
}