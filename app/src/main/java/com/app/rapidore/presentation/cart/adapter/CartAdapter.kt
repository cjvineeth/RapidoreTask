package com.app.rapidore.presentation.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.rapidore.R
import com.app.rapidore.data.remote.dto.Product
import com.app.rapidore.databinding.ItemCartBinding
import com.app.rapidore.databinding.ItemProductBinding
import com.app.rapidore.domain.model.CartModel
import com.app.rapidore.domain.model.ProductModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class CartAdapter(

    private val listener: ItemClickListener,
    private var apodList: CartModel
) : RecyclerView.Adapter<CartAdapter.ApodViewHolder>() {

    interface ItemClickListener
    {
        fun getItem(id:Int?): ProductModel
    }

    private var filteredList: MutableList<Product> = mutableListOf()

    init {
        filteredList.addAll(apodList.products.toMutableList())
    }

    inner class ApodViewHolder(val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProductModel) {



        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApodViewHolder {
        val listItemBinding = ItemCartBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ApodViewHolder(listItemBinding)
    }

    override fun onBindViewHolder(holder: ApodViewHolder, position: Int) {
        val item = filteredList[position]


        holder.bind(listener.getItem(item.productId))
    }
    override fun getItemCount(): Int {
        return filteredList.size
    }

}
