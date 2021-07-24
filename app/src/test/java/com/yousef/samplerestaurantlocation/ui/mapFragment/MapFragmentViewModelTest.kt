package com.yousef.samplerestaurantlocation.ui.mapFragment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.android.gms.maps.model.LatLng
import com.yousef.samplerestaurantlocation.data.DataManager
import com.yousef.samplerestaurantlocation.data.model.LocationsPOJO
import com.yousef.samplerestaurantlocation.utils.Const
import com.yousef.samplerestaurantlocation.utils.CoroutineTestRule
import com.yousef.samplerestaurantlocation.utils.TestCoroutineRule
import com.yousef.samplerestaurantlocation.utils.rx.TestSchedulerProvider
import io.reactivex.schedulers.TestScheduler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MapFragmentViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @Mock
    var mSchedulesCallback: MapNavigator? = null

    @Mock
    var mMockDataManager: DataManager? = null
    private var mSchedulesViewModel: MapViewModel? = null
    private var mTestScheduler: TestScheduler? = null

    @Before
    @Throws(java.lang.Exception::class)
    open fun setUp(): Unit {
        mTestScheduler = TestScheduler()
        val testSchedulerProvider = TestSchedulerProvider(mTestScheduler!!)
        mSchedulesViewModel = MapViewModel(mMockDataManager, testSchedulerProvider)
        mSchedulesViewModel!!.setNavigator(mSchedulesCallback)
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        mTestScheduler = null
        mSchedulesViewModel = null
        mSchedulesCallback = null
    }

    @Test
    fun testCallLocationApiError(){
        testCoroutineRule.runBlockingTest {
            val locationsResponse = LocationsPOJO(402, "test")
            doReturn(locationsResponse)
                .`when`(mMockDataManager)
                ?.getLocationResults("1.0,2.0")

            mSchedulesViewModel?.callLocationApi(
                LatLng(1.0, 2.0), false)
            verify(mSchedulesCallback, atLeastOnce())?.showLoading()
            verify(mMockDataManager)?.deleteAll()
            verify(mSchedulesCallback, atLeastOnce())?.hideLoading()
            verify(mSchedulesCallback)?.handleError(locationsResponse.meta!!.errorDetail)
        }
    }

    @Test
    fun testCallLocationApi(){
        testCoroutineRule.runBlockingTest {
            val locationsResponse = LocationsPOJO(Const.TWO_HUNDRED, "test")
            doReturn(locationsResponse)
                .`when`(mMockDataManager)
                ?.getLocationResults("1.0,2.0")
            mSchedulesViewModel?.callLocationApi(
                LatLng(1.0, 2.0), false)
            verify(mSchedulesCallback, atLeastOnce())?.showLoading()
            verify(mMockDataManager)?.deleteAll()
            verify(mSchedulesCallback)?.setMarkers(ArgumentMatchers.any())
        }
    }

    @Test
    fun testSearchByCategory(){
        testCoroutineRule.runBlockingTest {
            val locationsResponse = LocationsPOJO(Const.TWO_HUNDRED, "test")
            doReturn(locationsResponse)
                .`when`(mMockDataManager)
                ?.getLocationResults("3.0,4.0")

            mSchedulesViewModel?.searchByCategory(
                LatLng(1.0, 2.0),
                LatLng(3.0, 4.0))

            verify(mMockDataManager)!!.lastLatLng =  LatLng(3.0, 4.0)
            verify(mSchedulesCallback, atLeastOnce())?.showLoading()
            verify(mMockDataManager)?.deleteAll()
            verify(mMockDataManager)!!.insertRestaurant(ArgumentMatchers.any())
            verify(mSchedulesCallback)?.setMarkers(ArgumentMatchers.any())
        }
    }
}