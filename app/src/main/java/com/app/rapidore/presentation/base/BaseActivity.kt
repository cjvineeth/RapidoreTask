package com.app.rapidore.presentation.base

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.app.rapidore.R


abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {
    private lateinit var binding: T

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Rapidore)
        binding = DataBindingUtil.setContentView(this, getLayout())
    }

    @LayoutRes
    protected abstract fun getLayout(): Int

}