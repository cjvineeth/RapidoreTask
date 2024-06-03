package com.app.rapidore.presentation.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.rapidore.databinding.ItemCartBinding
import com.app.rapidore.domain.model.ProductModel

class CartAdapter(
    private var apodList: MutableList<ProductModel>,var sum:Double?,val listener: OnSumUpDateListener
) : RecyclerView.Adapter<CartAdapter.ApodViewHolder>() {


    private var filteredList: MutableList<ProductModel> = mutableListOf()

    interface OnSumUpDateListener {
        fun onInCrease(sum: Double)

        fun onDecrease(sum: Double)
    }

    init {
        filteredList.addAll(apodList)
    }

    inner class ApodViewHolder(val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProductModel) {
             binding.apply {
                 model=item
             }
            binding.btnIncreaseQuantity.setOnClickListener {
                binding.tvQuantity.text =
                    binding.tvQuantity.text.toString().toInt().plus(1).toString()
                listener.onInCrease(sum?.plus(item.price.toDouble())?:0.0)
            }
            binding.btnDecreaseQuantity.setOnClickListener{
                if (binding.tvQuantity.text.toString().toInt() > 1) {
                    binding.tvQuantity.text =
                        binding.tvQuantity.text.toString().toInt().minus(1).toString()
                    listener.onDecrease(sum?.minus(item.price.toDouble())?:0.0)
                }
            }
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
        holder.bind(item)
    }
    override fun getItemCount(): Int {
        return filteredList.size
    }

    fun setTotalSum(totalSum: Double, isIncrease: Boolean) {
        sum = if(isIncrease) {
            sum?.plus(totalSum)
        } else{
            sum?.minus(totalSum)
        }
    }

}
