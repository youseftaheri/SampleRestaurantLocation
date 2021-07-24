package com.yousef.samplerestaurantlocation.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yousef.samplerestaurantlocation.data.local.db.dao.RestaurantDao
import com.yousef.samplerestaurantlocation.data.model.db.Restaurant

@Database(entities = [Restaurant::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun restaurantDao(): RestaurantDao?
}