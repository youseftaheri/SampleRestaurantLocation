package com.yousef.samplerestaurantlocation.di.module

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.yousef.samplerestaurantlocation.data.AppDataManager
import com.yousef.samplerestaurantlocation.data.DataManager
import com.yousef.samplerestaurantlocation.data.local.db.AppDatabase
import com.yousef.samplerestaurantlocation.data.local.db.AppDbHelper
import com.yousef.samplerestaurantlocation.data.local.db.DbHelper
import com.yousef.samplerestaurantlocation.data.local.prefs.AppPreferencesHelper
import com.yousef.samplerestaurantlocation.data.local.prefs.PreferencesHelper
import com.yousef.samplerestaurantlocation.data.remote.ApiHelper
import com.yousef.samplerestaurantlocation.data.remote.Apis
import com.yousef.samplerestaurantlocation.data.remote.AppApiHelper
import com.yousef.samplerestaurantlocation.di.DatabaseInfo
import com.yousef.samplerestaurantlocation.di.PreferenceInfo
import com.yousef.samplerestaurantlocation.utils.CommonUtils
import com.yousef.samplerestaurantlocation.utils.Const
import com.yousef.samplerestaurantlocation.utils.rx.AppSchedulerProvider
import com.yousef.samplerestaurantlocation.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.annotations.Nullable
import retrofit2.Retrofit
import javax.inject.Singleton


@Module(includes = [ViewModelModule::class])
class AppModule {
    @Provides
    @Singleton
    fun provideApiHelper(appApiHelper: AppApiHelper): ApiHelper {
        return appApiHelper
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): Apis {
        return retrofit.create(Apis::class.java)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@DatabaseInfo dbName: String?, context: Context?): AppDatabase {
        return Room.databaseBuilder(context!!, AppDatabase::class.java, dbName!!)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideDbHelper(appDbHelper: AppDbHelper): DbHelper {
        return appDbHelper
    }

    @Provides
    @DatabaseInfo
    fun provideDatabaseName(): String? {
        return Const.DB_NAME
    }

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideDataManager(appDataManager: AppDataManager): DataManager {
        return appDataManager
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
    }

    @Provides
    @Singleton
    fun providePreferencesHelper(appPreferencesHelper: AppPreferencesHelper): PreferencesHelper {
        return appPreferencesHelper
    }

    @Provides
    @PreferenceInfo
    fun providePreferenceName(): String {
        return Const.PREF_NAME
    }

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider {
        return AppSchedulerProvider()
    }

    @Singleton
    @Provides
    fun provideUtils(context: Context?): CommonUtils {
        return CommonUtils
    }
}