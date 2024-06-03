package com.app.rapidore.presentation.productlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.rapidore.R
import com.app.rapidore.databinding.ItemProductBinding
import com.app.rapidore.domain.model.ProductModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import java.util.Locale

class ProductAdapter (
    private var apodList: MutableList<ProductModel>,
    private var callback: ((ProductModel) -> Unit)? = null
) : RecyclerView.Adapter<ProductAdapter.ApodViewHolder>() {

    private var filteredList: MutableList<ProductModel> = mutableListOf()

    init {
        filteredList.addAll(apodList)
    }

    inner class ApodViewHolder(val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProductModel) {
            binding.title.text = item.title
            binding.price.text = "₹ ${item.price}"
            binding.ratingText.text="(${item.rating.rate})"
            binding.ratingBar.numStars=5
            binding.ratingBar.rating = item.rating.rate.toFloat()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApodViewHolder {
        val listItemBinding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ApodViewHolder(listItemBinding)
    }

    override fun onBindViewHolder(holder: ApodViewHolder, position: Int) {
        val item = filteredList[position]
        Glide.with(holder.binding.root.context)
            .load(item.image)
            .placeholder(R.mipmap.ic_launcher)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(holder.binding.productImage)

        holder.binding.root.setOnClickListener {
            callback?.invoke(item)
        }
        holder.bind(item)
    }
    override fun getItemCount(): Int {
        return filteredList.size
    }

    fun filter(query: String) {
        filteredList.clear()
        if (query.isEmpty()) {
            filteredList.addAll(apodList)
        } else {
            for (item in apodList) {
                if (item.title.lowercase(Locale.getDefault()).contains(query.lowercase(Locale.getDefault()))) {
                    filteredList.add(item)
                }
            }
        }
        notifyDataSetChanged()
    }
}
