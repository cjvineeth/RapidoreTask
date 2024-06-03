package com.app.rapidore.common

import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle


    fun Fragment.addAsMenuHost(remove: Boolean = false) {
        if (this is MenuProvider) {
            val menuHost: MenuHost = requireActivity()
            if (remove) {
                menuHost.removeMenuProvider(this)
            }
            menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        }
    }
