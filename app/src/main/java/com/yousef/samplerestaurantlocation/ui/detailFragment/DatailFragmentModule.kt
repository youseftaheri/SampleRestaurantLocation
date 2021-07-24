package com.yousef.samplerestaurantlocation.ui.detailFragment

import androidx.recyclerview.widget.LinearLayoutManager
import dagger.Module
import dagger.Provides

@Module
class DatailFragmentModule {
    @Provides
    fun provideLinearLayoutManager(fragment: DetailFragment): LinearLayoutManager {
        return LinearLayoutManager(fragment.activity)
    }
}
