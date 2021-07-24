package com.yousef.samplerestaurantlocation.ui.detailFragment

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yousef.samplerestaurantlocation.BR
import com.yousef.samplerestaurantlocation.R
import com.yousef.samplerestaurantlocation.databinding.FragmentDetailBinding
import com.yousef.samplerestaurantlocation.factory.ViewModelProviderFactory
import com.yousef.samplerestaurantlocation.ui.base.BaseFragment
import javax.inject.Inject


class DetailFragment : BaseFragment<FragmentDetailBinding, DetailViewModel?>(), DetailNavigator {
    var mFragmentDetailBinding: FragmentDetailBinding? = null

    @JvmField
    @Inject
    var mLayoutManager: LinearLayoutManager? = null

    @JvmField
    @Inject
    var factory: ViewModelProviderFactory? = null
    private var mDetailViewModel: DetailViewModel? = null
    override val bindingVariable: Int
        get() = BR.viewModel

    override val layoutId: Int
        get() = R.layout.fragment_detail

    override val viewModel: DetailViewModel
        get() {
            mDetailViewModel =
                ViewModelProvider(this, factory!!).get(DetailViewModel::class.java)
            return mDetailViewModel!!
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDetailViewModel!!.setNavigator(this)
        //      mDetailAdapter.setListener(this);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mFragmentDetailBinding = viewDataBinding
        val args: DetailFragmentArgs? = arguments?.let{
            DetailFragmentArgs.fromBundle(it)
        }
        val restaurantId: String = args!!.restaurantId
        mDetailViewModel!!.showDetails(restaurantId)
    }

    override fun back() {
        requireActivity().onBackPressed()
    }

    companion object {
        fun newInstance(): DetailFragment {
            val args = Bundle()
            val fragment = DetailFragment()
            fragment.arguments = args
            return fragment
        }
    }
}