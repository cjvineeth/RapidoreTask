package com.app.rapidore.presentation.item

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.app.rapidore.R
import com.app.rapidore.common.NavigationExtensions.navigateSafe
import com.app.rapidore.common.addAsMenuHost
import com.app.rapidore.databinding.FragmentItemDesctriptionBinding
import com.app.rapidore.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItemDescriptionFragment : BaseFragment<FragmentItemDesctriptionBinding>(),MenuProvider {

    override fun getLayout() = R.layout.fragment_item_desctription

    override fun hideActionBar() = false

    override fun isBackButtonEnabled() = true

    override fun screenTitle() = getString(R.string.item_description)


    private val viewModel: ItemDescriptionViewModel by viewModels()
    private val args: ItemDescriptionFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addAsMenuHost()
        binding.viewModel=viewModel
        setUp()
        observe()
    }

    private fun observe() {
       viewModel.addedToCart.observe(viewLifecycleOwner){
           it?.let {
               if (it){
                   findNavController().navigateSafe(ItemDescriptionFragmentDirections.actionItemDesctriptionFragmentToFragmentCart())
               }
           }
       }
    }

    private fun setUp() {
        viewModel.getProducts(args.productId)
        binding.addToCartButton.setOnClickListener {
            viewModel.addToCart()
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        return menuInflater.inflate(R.menu.details_menu,menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.action_cart -> {
                findNavController().navigateSafe(ItemDescriptionFragmentDirections.actionItemDesctriptionFragmentToFragmentCart())
                return true
            }
            else -> return false
        }
        return true
    }

}