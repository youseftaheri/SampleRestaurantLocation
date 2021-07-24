package com.yousef.samplerestaurantlocation.data.local.db.dao

import androidx.room.*
import com.yousef.samplerestaurantlocation.data.model.db.Restaurant

@Dao
interface RestaurantDao {
    @Query("DELETE FROM restaurants")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(restaurant: Restaurant?)

    @Query("SELECT * FROM restaurants WHERE id LIKE :id LIMIT 1")
    suspend fun findById(id: String?): Restaurant?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(restaurant: Restaurant?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(restaurants: List<Restaurant?>?)

    @Query("SELECT * FROM restaurants")
    suspend fun loadAll(): List<Restaurant>?

    @Query("SELECT * FROM restaurants WHERE id IN (:restaurantIds)")
    suspend fun loadAllByIds(restaurantIds: List<Int?>?): List<Restaurant?>?
}