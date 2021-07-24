package com.yousef.samplerestaurantlocation.utils

object Const {
    private const val MAIN_BASE = "https://api.foursquare.com/"
    var BASE_URL = MAIN_BASE + "v2/"

    const val PREF_NAME = "sample_restaurant_pref"
    const val DB_NAME = "sample_restaurant_database.db"
    const val RESTAURANT_CATEGORY_ID = "4d4b7105d754a06374d81259"
    const val DEFAULT_ZOOM = 15f
    const val ERROR_DIALOG_REQUEST = 5001
    const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 5003
    const val PERMISSIONS_REQUEST_ENABLE_GPS = 5002
    const val LOCATION_PERMISSION_REQUEST_CODE = 1234
    @JvmField
    var TWO_HUNDRED = 200
    @JvmField
    var SPLASH_TIME = 2000L
}