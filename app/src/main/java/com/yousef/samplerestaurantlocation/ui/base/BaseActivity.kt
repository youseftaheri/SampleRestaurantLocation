package com.yousef.samplerestaurantlocation.ui.base

import android.annotation.TargetApi
import android.app.ProgressDialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.multidex.MultiDex
import com.yousef.samplerestaurantlocation.MyApplication
import com.yousef.samplerestaurantlocation.R
import com.yousef.samplerestaurantlocation.utils.*
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import dagger.android.support.HasSupportFragmentInjector
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import javax.inject.Inject

abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel<*>?> : DaggerAppCompatActivity(),
    BaseFragment.Callback, HasSupportFragmentInjector {
    // TODO
    // this can probably depend on isLoading variable of BaseViewModel,
    // since its going to be common for all the activities
//    @JvmField
//    @Inject
//    var mProgressDialog: CustomProgressDialog? = null
//    @JvmField
//    @Inject
//    var mProgressDialog: CustomProgressDialog? = null
    private var mProgressDialog: ProgressDialog? = null

    @JvmField
    @Inject
    var utils: CommonUtils? = null
    var viewDataBinding: T? = null
        private set
    private var mViewModel: V? = null
    @JvmField
    var mContext: Context? = null

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    abstract val bindingVariable: Int

    /**
     * @return layout resource id
     */
    @get:LayoutRes
    abstract val layoutId: Int

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    abstract val viewModel: V

    override fun onFragmentAttached() {}
    override fun onFragmentDetached(tag: String?) {}
    override fun attachBaseContext(newBase: Context) {
        val context = (newBase.applicationContext as MyApplication)
//            .setLanguage(newBase, "en")
        super.attachBaseContext(ViewPumpContextWrapper.wrap(context))
        MultiDex.install(this)
        //ACRA.init(this);
    }

    companion object {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        performDependencyInjection()
        super.onCreate(savedInstanceState)
        ViewPump.init(
            ViewPump.builder()
                .addInterceptor(
                    CalligraphyInterceptor(
                        CalligraphyConfig.Builder()
//                            .setDefaultFontPath(Const.FONT_FILE)
//                            .setFontAttrId(R.attr.fontPath)
                            .build())
                )
                .build())
        mContext = this@BaseActivity
        performDataBinding()
    }


    fun handleError(exception: String?) {
        if(exception!!.length >= 23) {
            if (exception.substring(0, 22) == "Unable to resolve host") {
                MyToast.show(this, getString(R.string.internetError), true)
            } else {
                MyToast.show(this, exception, true)
            }
        }else {
            MyToast.show(this, exception, true)
        }
    }

    fun hideKeyboard() {
        // Check if no view has focus:
        val view = this.currentFocus
        view?.let { v ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }

    fun hideLoading() {
        if (mProgressDialog != null && mProgressDialog!!.isShowing) {
            //   mProgressDialog.cancel();
            mProgressDialog!!.dismiss()
        }
    }


    fun performDependencyInjection() {
        AndroidInjection.inject(this)
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionsSafely(permissions: Array<String?>?, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions!!, requestCode)
        }
    }

    fun showLoading() {
        hideLoading()
                mProgressDialog!!.show()
    }

    private fun performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, layoutId)
        mViewModel = if (mViewModel == null) viewModel else mViewModel
        viewDataBinding!!.setVariable(bindingVariable, mViewModel)
        viewDataBinding!!.executePendingBindings()
    }

    fun animateViewGroupChildren(parent: ViewGroup, animId: Int) {
        val animZoomIn: Animation = AnimationUtils.loadAnimation(this, animId)
        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            if (child is ViewGroup) {
                animateViewGroupChildren(child, animId)
                // DO SOMETHING WITH VIEWGROUP, AFTER CHILDREN HAS BEEN LOOPED
            } else {
                child.startAnimation(animZoomIn)
            }
        }
    }

    fun animateView(view: View, animId: Int) {
        val animZoomIn: Animation = AnimationUtils.loadAnimation(this, animId)
                view.startAnimation(animZoomIn)
    }


}