package com.yousef.samplerestaurantlocation.data.remote

import android.annotation.SuppressLint

@SuppressLint("NewApi")
class Events {
    class PermissionCheckerResult(var result: Boolean)
    class NetworkStatus(var status: Boolean)
}