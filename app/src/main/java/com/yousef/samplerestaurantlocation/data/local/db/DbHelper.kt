package com.yousef.samplerestaurantlocation.data.local.db

import com.yousef.samplerestaurantlocation.data.model.db.Restaurant

interface DbHelper {
    suspend fun deleteAll()
    suspend fun allRestaurants(): List<Restaurant>?
    suspend fun insertRestaurant(restaurant: Restaurant?)
    suspend fun findRestaurantById(id: String?)
}