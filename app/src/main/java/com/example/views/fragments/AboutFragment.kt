package com.example.views.fragments

import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.views.R

class AboutFragment : Fragment(R.layout.fragment_about) {

    override fun onStart() {
        super.onStart()

        val backButton = view?.findViewById<Button>(R.id.abBackButton)

        val toProductsFragment = AboutFragmentDirections.actionAboutFragmentToProductsFragment()
        backButton?.setOnClickListener { it.findNavController().navigate(toProductsFragment) }
    }

}