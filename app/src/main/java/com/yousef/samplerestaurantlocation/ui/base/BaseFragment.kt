package com.yousef.samplerestaurantlocation.ui.base

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.yousef.samplerestaurantlocation.R
import com.yousef.samplerestaurantlocation.utils.CommonUtils
import com.yousef.samplerestaurantlocation.utils.CommonUtils.showLoadingDialog
import com.yousef.samplerestaurantlocation.utils.CustomProgressDialog
import com.yousef.samplerestaurantlocation.utils.MyToast
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel<*>?> : DaggerFragment() {
    var baseActivity: BaseActivity<*, *>? = null
        private set
    private var mRootView: View? = null
    var viewDataBinding: T? = null
        private set
    private var mViewModel: V? = null

//    @JvmField
//    @Inject
//    var mProgressDialog: CustomProgressDialog? = null
    private var mProgressDialog: ProgressDialog? = null

    @JvmField
    @Inject
    var utils: CommonUtils? = null

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
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*, *>) {
            val activity = context
            baseActivity = activity
            activity.onFragmentAttached()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        performDependencyInjection()
        super.onCreate(savedInstanceState)
        mViewModel = viewModel
        setHasOptionsMenu(false)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        mRootView = viewDataBinding!!.getRoot()
        return mRootView
    }

    override fun onDetach() {
        baseActivity = null
        super.onDetach()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding!!.setVariable(bindingVariable, mViewModel)
        viewDataBinding!!.lifecycleOwner = this
        viewDataBinding!!.executePendingBindings()
    }

    fun handleError(exception: String?) {
        if(exception!!.length >= 23){
            if(exception.substring(0,22) == "Unable to resolve host"){
                MyToast.show(activity, getString(R.string.internetError), true)
            }else {
                MyToast.show(activity, exception, true)
            }
        }else {
            MyToast.show(activity, exception, true)
        }
    }

    fun hideKeyboard() {
        baseActivity?.hideKeyboard()
    }


    private fun performDependencyInjection() {
        AndroidSupportInjection.inject(this)
    }

    interface Callback {
        fun onFragmentAttached()
        fun onFragmentDetached(tag: String?)
    }

    fun showLoading() {
        hideLoading()
//                mProgressDialog!!.show();
        mProgressDialog = showLoadingDialog(context)

    }

    fun hideLoading() {
        if (mProgressDialog != null && mProgressDialog!!.isShowing) {
            //   mProgressDialog.cancel();
            mProgressDialog!!.dismiss()
        }
    }

    fun animateViewGroupChildren(parent: ViewGroup, animId: Int) {
        val animZoomIn: Animation = AnimationUtils.loadAnimation(activity, animId)
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
        val animZoomIn: Animation = AnimationUtils.loadAnimation(activity, animId)
        view.startAnimation(animZoomIn)
    }
}