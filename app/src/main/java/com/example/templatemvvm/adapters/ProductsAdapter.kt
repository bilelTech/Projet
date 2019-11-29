package com.example.templatemvvm.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.templatemvvm.R
import com.example.templatemvvm.ui.dashboard.home.models.ProductVO
import kotlinx.android.synthetic.main.product_item.view.*
import org.jetbrains.anko.image

class ProductsAdapter constructor(
    private val onProductSelected:
        (ProductVO) -> Unit
) :
    RecyclerView.Adapter<ProductsAdapter.ProductCellViewHolder>() {
    private val products: MutableList<ProductVO> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductCellViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.product_item,
            parent,
            false
        )
        return ProductCellViewHolder(view)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun addProducts(products: List<ProductVO>) {
        this.products.addAll(products)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ProductCellViewHolder, position: Int) {
        val product = products[position]
        holder.bind(product, onProductSelected)
    }

    class ProductCellViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(productVO: ProductVO, listener: (ProductVO) -> Unit) = with(itemView) {
            img.image = ContextCompat.getDrawable(itemView.context, R.drawable.ic_img_bg)
            name.text = productVO.name
            description.text = productVO.description
            setOnClickListener { listener(productVO) }
        }
    }
}