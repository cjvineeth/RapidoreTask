
package com.app.rapidore.presentation.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.LayoutRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.app.rapidore.connectivity.base.ConnectivityProvider


abstract class BaseFragment<T : ViewDataBinding> : Fragment(),
    ConnectivityProvider.ConnectivityStateListener {
    private var actionbar: ActionBar? = null
    private lateinit var _activityResult: ActivityResultLauncher<Intent>
    val activityResult: ActivityResultLauncher<Intent>
        get() = _activityResult
    private lateinit var _binding: T
    val binding: T
        get() = _binding
    private lateinit var result: (ActivityResult) -> Unit

    private val _provider: ConnectivityProvider by lazy {
        ConnectivityProvider.createProvider(
            requireActivity()
        )
    }
    val provider: ConnectivityProvider
        get() = _provider


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(
            inflater, getLayout(), container, false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        return _binding.root
    }

    override fun onStart() {
        super.onStart()


        _provider.addListener(this)
        if (hideActionBar()) {
            setHasOptionsMenu(false)
            actionbar?.hide()

        } else {
            actionbar?.show()
            setHasOptionsMenu(true)
        }
        actionbar?.apply {
            title=screenTitle()
        }
        if (isBackButtonEnabled()) {
            actionbar?.setDisplayHomeAsUpEnabled(true)
        } else {
            actionbar?.setDisplayHomeAsUpEnabled(false);
        }

    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                findNavController().popBackStack()
                true
            }

            else -> {
                false
            }
        }
    }

    override fun onStop() {
        super.onStop()
        _provider.removeListener(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionbar = (activity as AppCompatActivity?)?.supportActionBar
        _activityResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            result.invoke(it)
        }

    }

    fun getActivityResult(onActivityResult: (ActivityResult) -> Unit) {
        this.result = onActivityResult
    }

    @LayoutRes
    protected abstract fun getLayout(): Int
    protected abstract fun hideActionBar(): Boolean

    protected abstract fun isBackButtonEnabled(): Boolean
    protected abstract fun screenTitle(): String

    protected abstract fun onConnectionStateChange(state: ConnectivityProvider.NetworkState)

    override fun onStateChange(state: ConnectivityProvider.NetworkState) {
        onConnectionStateChange(state)

    }

}