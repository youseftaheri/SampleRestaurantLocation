package com.yousef.samplerestaurantlocation.ui.splashFragment

interface SplashNavigator {
    fun handleError(exception: String?)
    fun showLoading()
    fun hideLoading()
}