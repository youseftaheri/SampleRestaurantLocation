package com.yousef.samplerestaurantlocation.ui.detailFragment

interface DetailNavigator {
    fun handleError(exception: String?)
    fun showLoading()
    fun hideLoading()
    fun back()
}