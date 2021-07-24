package com.yousef.samplerestaurantlocation.utils

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.maps.model.LatLng
import com.yousef.samplerestaurantlocation.R
import java.util.*


object CommonUtils {

    /**
     * Show toast message
     *
     * @param mContext Context of activity or fragment
     * @param message  Message that show into the Toast
     */
    @JvmStatic
    fun showToast(mContext: Context?, message: String?) {
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show()
    }

    /**
     * Show Log
     *
     * @param message Message that want to show into Log
     */
    fun showLog(message: String) {
        Log.e("Log Message", "" + message)
    }

    /**
     * Hide Soft Keyboard
     *
     * @param mContext Context of the Activity or Fragment.
     * @param view     Current focus of View
     */
    @JvmStatic
    fun hideKeyboard(mContext: Context, view: View?) {
        if (view != null) {
            val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    /**
     * Load image from url into imageview
     *
     * @param mContext    Context of Activity or Fragment
     * @param url         Url that want to load into Imageview
     * @param imageView   ImageView in which url loads
     * @param placeholder Drawable image while loading image from Url
     */
    @JvmStatic
    fun loadImage(mContext: Context?, url: String?, imageView: ImageView?, placeholder: Int) {
        Glide.with(mContext!!).load(url).apply(RequestOptions().placeholder(placeholder)).diskCacheStrategy(
            DiskCacheStrategy.RESOURCE
        ).into(imageView!!)
    }

    @JvmStatic
    fun isExist(data: String?): Boolean {
        return data != null && !data.isEmpty()
    }

    @JvmStatic
    fun getIfExists(data: String?): String? {
        return if (isExist(data)) data else "-"
    }

    @JvmStatic
    fun showLoadingDialog(context: Context?): ProgressDialog {
        val progressDialog = ProgressDialog(context)
        progressDialog.show()
        if (progressDialog.window != null) {
            progressDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val wlmp = Objects.requireNonNull(progressDialog.window)?.attributes
            if (wlmp != null) {
                wlmp.gravity = Gravity.CENTER_HORIZONTAL
            }
            progressDialog.window!!.attributes = wlmp
        }
        progressDialog.setContentView(R.layout.progress_spinner)
        progressDialog.isIndeterminate = true
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)
        return progressDialog
    }

    fun distanceBetween(first: LatLng, second: LatLng): Float {
        val distance = FloatArray(1)
        Location.distanceBetween(
            first.latitude,
            first.longitude,
            second.latitude,
            second.longitude,
            distance
        )
        return distance[0]
    }

}