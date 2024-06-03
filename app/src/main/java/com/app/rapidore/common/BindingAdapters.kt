package com.app.rapidore.common

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapters {
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun loadImage(view: AppCompatImageView, url: String?) {
        if (url == null) return
        Glide.with(view.context).load(url).into(view)
    }

    @JvmStatic
    @BindingAdapter("goneIfNot")
    fun setNotGoneFromBoolean(view: View, visible: Boolean?) {
        view.visibility = if (visible == true) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("goneIf")
    fun setGoneFromBoolean(view: View, gone: Boolean?) {
        view.visibility = if (gone == true) View.GONE else View.VISIBLE
    }
}
