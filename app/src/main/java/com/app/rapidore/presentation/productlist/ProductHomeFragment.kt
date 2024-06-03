package com.app.rapidore.presentation.productlist

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.app.rapidore.R
import com.app.rapidore.common.NavigationExtensions.navigateSafe
import com.app.rapidore.common.addAsMenuHost
import com.app.rapidore.common.toast
import com.app.rapidore.databinding.FragmentProductHomeBinding
import com.app.rapidore.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProductHomeFragment : BaseFragment<FragmentProductHomeBinding>(),MenuProvider,SearchView.OnQueryTextListener {

    private var mAdapter:ProductAdapter?=null
    override fun getLayout() = R.layout.fragment_product_home
    override fun hideActionBar() = false
    override fun isBackButtonEnabled() = false
    override fun screenTitle()= getString(R.string.product_list)

    private val viewModel: ProductViewModel by viewModels ()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addAsMenuHost()
        setup()
        observe()
    }

    private fun observe() {
        viewModel.apodList.observe(viewLifecycleOwner){
            mAdapter=ProductAdapter(it) { product ->

               findNavController().navigateSafe(ProductHomeFragmentDirections.actionProductHomeFragmentToItemDesctriptionFragment(product.id?:0))
            }
            binding.list.apply {
                layoutManager =GridLayoutManager(requireContext(), 2)
                adapter = mAdapter
            }

        }
    }

    private fun setup() {
        addAsMenuHost()
          viewModel.getProducts()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        val inflater=menuInflater.inflate(R.menu.product_menu,menu)
        val searchItem = menu.findItem(R.id.app_bar_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(this)

        return inflater
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.app_bar_search -> "Serch Selected".toast(requireContext())
            else -> return false
        }
        return true
    }

    override fun onQueryTextSubmit(submit: String?): Boolean {
        submit?.let {
            mAdapter?.filter(it)
        }
        return true
    }

    override fun onQueryTextChange(change: String?): Boolean {
        change?.let {
            mAdapter?.filter(it)
        }
        return true
    }
}


