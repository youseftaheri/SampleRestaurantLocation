package com.yousef.samplerestaurantlocation.ui.mapFragment

import androidx.recyclerview.widget.LinearLayoutManager
import dagger.Module
import dagger.Provides

@Module
class MapFragmentModule {
    @Provides
    fun provideLinearLayoutManager(fragment: MapFragment): LinearLayoutManager {
        return LinearLayoutManager(fragment.activity)
    }
}
