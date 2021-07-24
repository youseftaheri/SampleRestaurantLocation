package com.yousef.samplerestaurantlocation.utils

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import com.yousef.samplerestaurantlocation.data.remote.Events
import org.greenrobot.eventbus.EventBus

class InternetReceiver : BroadcastReceiver() {
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context, intent: Intent) {
        try {
            val connectivityManager = context
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager
                    .activeNetworkInfo
            if (networkInfo != null && networkInfo.isConnected) {
                EventBus.getDefault().post(Events.NetworkStatus(true))
            } else {
                EventBus.getDefault().post(Events.NetworkStatus(false))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            EventBus.getDefault().post(Events.NetworkStatus(false))
        }
    }
}