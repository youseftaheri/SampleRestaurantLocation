package com.yousef.samplerestaurantlocation.ui.splashFragment

import android.animation.ValueAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.yousef.samplerestaurantlocation.BR
import com.yousef.samplerestaurantlocation.R
import com.yousef.samplerestaurantlocation.databinding.FragmentSplashBinding
import com.yousef.samplerestaurantlocation.factory.ViewModelProviderFactory
import com.yousef.samplerestaurantlocation.ui.base.BaseFragment
import com.yousef.samplerestaurantlocation.utils.Const
import javax.inject.Inject

class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel?>(), SplashNavigator {
    var mFragmentSplashBinding: FragmentSplashBinding? = null

    @JvmField
    @Inject
    var mLayoutManager: LinearLayoutManager? = null

    @JvmField
    @Inject
    var factory: ViewModelProviderFactory? = null
    private var mSplashViewModel: SplashViewModel? = null
    override val bindingVariable: Int
        get() = BR.viewModel

    override val layoutId: Int
        get() = R.layout.fragment_splash

    override val viewModel: SplashViewModel
        get() {
            mSplashViewModel =
                ViewModelProvider(this, factory!!).get(SplashViewModel::class.java)
            return mSplashViewModel!!
        }
    private val runnable = Runnable {
        findNavController().navigate(R.id.mapFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSplashViewModel!!.setNavigator(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mFragmentSplashBinding = viewDataBinding
        setAnimation()
        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(R.id.mapFragment) }, Const.SPLASH_TIME)
        mSplashViewModel!!.fetchSplash()
    }
    
    private fun setAnimation() {
        //to prevent animation lag
        mFragmentSplashBinding!!.backgroundOne.setLayerType(View.LAYER_TYPE_HARDWARE, null)
        mFragmentSplashBinding!!.backgroundTwo.setLayerType(View.LAYER_TYPE_HARDWARE, null)

        //define and start the background animation
        val animator = ValueAnimator.ofFloat(1.00f, 0.00f)
        animator.repeatCount = ValueAnimator.INFINITE
        animator.interpolator = LinearInterpolator()
        animator.duration = 7500L
        animator.addUpdateListener { animation: ValueAnimator ->
            val progress = animation.animatedValue as Float
            val height = mFragmentSplashBinding!!.backgroundOne.height.toFloat()
            val translationY = height * progress
            mFragmentSplashBinding!!.backgroundOne.translationY = translationY
            mFragmentSplashBinding!!.backgroundTwo.translationY = translationY - height
        }
        animator.start()
    }

    companion object {
        fun newInstance(): SplashFragment {
            val args = Bundle()
            val fragment = SplashFragment()
            fragment.arguments = args
            return fragment
        }
    }
}