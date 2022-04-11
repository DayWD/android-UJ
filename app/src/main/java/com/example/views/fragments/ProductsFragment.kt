package com.example.views.fragments

import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.views.ProductListAdapter
import com.example.views.R
import com.example.views.realm.models.Product
import io.realm.Realm

class ProductsFragment : Fragment(R.layout.fragment_products) {

    private lateinit var recyclerView: RecyclerView


    override fun onStart() {
        super.onStart()

        val config = com.example.views.RealmConfiguration.realmConfig()
        val realm = Realm.getInstance(config)

        val products = realm.where(Product::class.java)
            .findAll()
            .toList()


        recyclerView = view?.findViewById(R.id.product_recycle_view)!!
        val adapter = ProductListAdapter(products)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val aboutButton = view?.findViewById<Button>(R.id.prodAboutButton)
        val toAboutFragment = ProductsFragmentDirections.actionProductsFragmentToAboutFragment()
        aboutButton?.setOnClickListener { findNavController().navigate(toAboutFragment) }

    }

}