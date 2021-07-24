package com.yousef.samplerestaurantlocation.ui.mainActivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.yousef.samplerestaurantlocation.BR
import com.yousef.samplerestaurantlocation.R
import com.yousef.samplerestaurantlocation.databinding.ActivityMainBinding
import com.yousef.samplerestaurantlocation.factory.ViewModelProviderFactory
import com.yousef.samplerestaurantlocation.ui.base.BaseActivity
import com.yousef.samplerestaurantlocation.utils.NetworkUtils
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject


class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel?>(), MainNavigator,
    HasSupportFragmentInjector {
    @JvmField
    @Inject
    var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>? = null


    @JvmField
    @Inject
    var factory: ViewModelProviderFactory? = null
    private var mActivityMainBinding: ActivityMainBinding? = null
    private var mMainViewModel: MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMainViewModel!!.setNavigator(this)
        mActivityMainBinding = viewDataBinding
        checkForInternetConnection()

        mMainViewModel!!.entry
    }

    override val bindingVariable: Int
        get() = BR.viewModel

    override val layoutId: Int
        get() = R.layout.activity_main

    override val viewModel: MainViewModel
        get() {
            mMainViewModel = ViewModelProvider(this, factory!!).get(MainViewModel::class.java)
            return mMainViewModel!!
        }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentDispatchingAndroidInjector!!
    }

    // we observed the internet connection here since our main activity contains all of our fragments
    // and we want to get notified across the whole app
    private fun checkForInternetConnection() {
        val snackbar =
            Snackbar.make(
                mActivityMainBinding!!.mainContent,
                R.string.internetError,
                Snackbar.LENGTH_INDEFINITE
            )
        NetworkUtils.getNetworkLiveData(this).observe(this, Observer {
            if (it) {
                snackbar.dismiss()
            } else {
                snackbar.show()
            }
        }
        )
    }

    override fun setUp() {
    }

    companion object {
        fun newIntent(context: Context?): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}
