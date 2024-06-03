package com.app.rapidore.presentation.cart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.rapidore.R
import com.app.rapidore.databinding.FragmentCartBinding
import com.app.rapidore.presentation.base.BaseFragment
import com.app.rapidore.presentation.cart.adapter.CartAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentCart : BaseFragment<FragmentCartBinding>() {

       private var totalSum:Double=0.0

    private var mAdapter: CartAdapter?=null
    private val viewModel: CartViewModel by viewModels()
    override fun getLayout() = R.layout.fragment_cart
    override fun hideActionBar() = false
    override fun isBackButtonEnabled() = true
    override fun screenTitle() = getString(R.string.added_products)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
        observe()
    }

    private fun observe() {
        viewModel.cartList.observe(viewLifecycleOwner) { list ->
            totalSum=list.sumOf { it.price.toDouble() }

            list.sumOf { it.price.toDouble() }
            binding.tvTotalPrice.text="₹ "+list.sumOf { it.price.toDouble() }.toString()
            mAdapter = CartAdapter(list,totalSum,object :CartAdapter.OnSumUpDateListener{
                override fun onInCrease(sum: Double) {

                }

                override fun onDecrease(sum: Double) {
                }

            })
            binding.rvCartItems.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = mAdapter
            }
        }
    }

    private fun setup() {
        viewModel.getCarts()


    }


}