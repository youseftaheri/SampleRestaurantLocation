package com.yousef.samplerestaurantlocation.data.local.db

import com.yousef.samplerestaurantlocation.data.model.db.Restaurant
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDbHelper @Inject constructor(private val mAppDatabase: AppDatabase) : DbHelper {

    override suspend fun deleteAll() {
        mAppDatabase.restaurantDao()!!.deleteAll()
    }

    override suspend fun allRestaurants(): List<Restaurant>?{
        return mAppDatabase.restaurantDao()!!.loadAll()
    }

    override suspend fun insertRestaurant(restaurant: Restaurant?) {
        mAppDatabase.restaurantDao()!!.insert(restaurant)
    }

    override suspend fun findRestaurantById(id: String?) {
        mAppDatabase.restaurantDao()!!.findById(id)
    }
}