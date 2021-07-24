package com.yousef.samplerestaurantlocation.utils

import android.text.TextWatcher
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


object BindingUtils {

    @JvmStatic
    @BindingAdapter("srcCompat")
    fun bindSrcCompat(imageView: ImageView, drawable: Int) {
        imageView.setImageResource(drawable)
        // Your setter code goes here, like setDrawable or similar
    }

    @BindingAdapter("text")
    fun bindText(tv: TextView, value: Int) {
        tv.text = value.toString()
    }

    @JvmStatic
    @BindingAdapter("android:imageUrl")
    fun setImageUrl(imageView: ImageView, url: String?) {
        val context = imageView.context
        Glide.with(context).load(url).dontAnimate().into(imageView)

    }

    @JvmStatic
    @BindingAdapter("android:background")
    fun setBackground(textView: TextView, drawable: Int) {
        textView.setBackgroundResource(drawable)
    }

    @JvmStatic
    @BindingAdapter("textChangedListener")
    fun bindTextWatcher(editText: EditText, textWatcher: TextWatcher?) {
        editText.addTextChangedListener(textWatcher)
    }

    @JvmStatic
    @BindingAdapter("android:background")
    fun setBackground(constraintLayout: ConstraintLayout, drawable: Int) {
        constraintLayout.setBackgroundResource(drawable)
    }

    @JvmStatic
    @BindingAdapter("android:background")
    fun setBackground(button: Button, drawable: Int) {
        button.setBackgroundResource(drawable)
    }

    @JvmStatic
    @BindingAdapter("android:visibility")
    fun setVisibility(view: View, value: Boolean) {
        view.visibility = if(value) VISIBLE else GONE
    }

    @JvmStatic
    @BindingAdapter("android:visibility")
    fun setVisibility(view: View, value: Int) {
        view.visibility = if(value == 1) VISIBLE else GONE
    }

    @JvmStatic
    @BindingAdapter("android:visibility")
    fun setCardVisibility(view: CardView, value: Boolean) {
        view.visibility = if(value) VISIBLE else GONE
    }

    @JvmStatic
    @BindingAdapter("android:enabled")
    fun setEnabled(view: View, value: Boolean) {
        view.isEnabled = value;
    }

}