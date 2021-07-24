package com.yousef.samplerestaurantlocation.ui.splashFragment

import androidx.recyclerview.widget.LinearLayoutManager
import dagger.Module
import dagger.Provides
import java.util.*

@Module
class SplashFragmentModule {
    @Provides
    fun provideLinearLayoutManager(fragment: SplashFragment): LinearLayoutManager {
        return LinearLayoutManager(fragment.activity)
    }
}
