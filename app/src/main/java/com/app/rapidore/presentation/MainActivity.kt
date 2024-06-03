package com.app.rapidore.presentation

import com.app.rapidore.R
import com.app.rapidore.databinding.ActivityMainBinding
import com.app.rapidore.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(){
    override fun getLayout()= R.layout.activity_main
}