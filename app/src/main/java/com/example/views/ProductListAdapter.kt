package com.example.views

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.views.fragments.ProductsFragmentDirections
import com.example.views.realm.models.Product

class ProductListAdapter(private val products: List<Product>) :
    RecyclerView.Adapter<ProductListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val productRow = layoutInflater.inflate(R.layout.product_row, parent, false)
        return ProductListViewHolder(productRow)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
        val productTextView = holder.view.findViewById<TextView>(R.id.product_name_text)
        val productPriceTextView = holder.view.findViewById<TextView>(R.id.product_price_textView)
        val buyButton = holder.view.findViewById<TextView>(R.id.buy_button)

        productTextView.text = products[position].product_name
        val price = products[position].product_price.toString()
        productPriceTextView.text = "Price: $price zł"

        val toCartPageFragment =
            ProductsFragmentDirections.actionProductsFragmentToCartFragment2(productId = products[position].product_id)
        buyButton?.setOnClickListener { it.findNavController().navigate(toCartPageFragment) }

    }

    override fun getItemCount(): Int {
        return products.size
    }
}

class ProductListViewHolder(val view: View) : RecyclerView.ViewHolder(view)