package com.yousef.samplerestaurantlocation.ui.detailFragment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.yousef.samplerestaurantlocation.data.DataManager
import com.yousef.samplerestaurantlocation.data.model.ContactPOJO
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
import org.mockito.Mock
import org.mockito.Mockito.atLeastOnce
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DetailFragmentViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @Mock
    var mSchedulesCallback: DetailNavigator? = null

    @Mock
    var mMockDataManager: DataManager? = null
    private var mSchedulesViewModel: DetailViewModel? = null
    private var mTestScheduler: TestScheduler? = null

    @Before
    @Throws(java.lang.Exception::class)
    open fun setUp(): Unit {
        mTestScheduler = TestScheduler()
        val testSchedulerProvider = TestSchedulerProvider(mTestScheduler!!)
        mSchedulesViewModel = DetailViewModel(mMockDataManager, testSchedulerProvider)
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
    fun testShowDetailsError(){
        testCoroutineRule.runBlockingTest {
            val contactResponse = ContactPOJO(402, "test")
            doReturn(contactResponse)
                .`when`(mMockDataManager)
                ?.getContactResults("1111111111")

            mSchedulesViewModel?.showDetails("1111111111")
            verify(mSchedulesCallback)?.showLoading()
            verify(mSchedulesCallback)?.hideLoading()
            verify(mSchedulesCallback, atLeastOnce())!!.handleError(contactResponse.meta!!.errorDetail)
        }
    }

    @Test
    fun testShowDetailsApi(){
        testCoroutineRule.runBlockingTest {
            val contactResponse = ContactPOJO(Const.TWO_HUNDRED, "test")
            doReturn(contactResponse)
                .`when`(mMockDataManager)
                ?.getContactResults("1111111111")

            mSchedulesViewModel?.showDetails("1111111111")
            verify(mSchedulesCallback)?.showLoading()
            verify(mSchedulesCallback, atLeastOnce())?.hideLoading()
        }
    }
}