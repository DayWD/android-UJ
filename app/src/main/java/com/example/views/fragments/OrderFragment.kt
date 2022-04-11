package com.example.views.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.views.R
import com.example.views.UserViewModel
import com.example.views.realm.repositories.RealmOrderInfoRepository
import com.example.views.retrofit.models.RetrofitOrderInfo
import com.example.views.retrofit.repositories.RetrofitOrderInfoRepository
import com.example.views.retrofit.repositories.RetrofitStripeRepository
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class OrderFragment : Fragment(R.layout.fragment_order) {

    private lateinit var paymentIntentClientSecret: String
    private lateinit var paymentSheet: PaymentSheet


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val payButton = view.findViewById<Button>(R.id.payButton)
        payButton.setOnClickListener { onPayClicked(view) }

        paymentSheet = PaymentSheet(this, ::onPaymentSheetResult)
    }


    private fun fetchPaymentIntent() {

        val args by navArgs<OrderFragmentArgs>()

        runBlocking {
            withContext(Dispatchers.IO) {
                paymentIntentClientSecret = RetrofitStripeRepository().getPrice(args.productId)
            }
        }
    }

    private fun showAlert(title: String, message: String? = null) {
        val builder = AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(message)
        builder.setPositiveButton("Ok", null)
        builder.create().show()
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun onPayClicked(view: View) {
        val configuration = PaymentSheet.Configuration("Example, Inc.")
        fetchPaymentIntent()

        paymentSheet.presentWithPaymentIntent(paymentIntentClientSecret, configuration)
    }

    private fun onPaymentSheetResult(paymentResult: PaymentSheetResult) {
        when (paymentResult) {
            is PaymentSheetResult.Completed -> {
                showToast("Payment complete!")
                val toProductFragment =
                    OrderFragmentDirections.actionOrderFragmentToProductsFragment()
                findNavController().navigate(toProductFragment)
                executeOrder()
            }
            is PaymentSheetResult.Canceled -> {
                Log.i("CheckoutActivity", "Payment canceled!")
            }
            is PaymentSheetResult.Failed -> {
                showAlert("Payment failed", paymentResult.error.localizedMessage)
            }
        }
    }

    private fun executeOrder() {
        val orderNameText = view?.findViewById<TextView>(R.id.editTextTextPersonName2)
        val orderAddressText = view?.findViewById<TextView>(R.id.editTextTextPersonName3)
        val phoneNumberText = view?.findViewById<TextView>(R.id.editTextPhone)
        val emailText = view?.findViewById<TextView>(R.id.editTextTextPersonName5)

        val highestOrderID: Int = RealmOrderInfoRepository().getHighestOrderId()

        RealmOrderInfoRepository().addToOrder(
            order_id = highestOrderID + 1,
            order_user_id = UserViewModel.userId!!,
            order_name = orderNameText?.text.toString(),
            order_address = orderAddressText?.text.toString(),
            order_phone = phoneNumberText?.text.toString().toInt(),
            order_mail = emailText?.text.toString()
        )
        val orderModel = RetrofitOrderInfo(
            order_id = highestOrderID + 1,
            order_user_id = UserViewModel.userId!!,
            order_name = orderNameText?.text.toString(),
            order_address = orderAddressText?.text.toString(),
            order_phone = phoneNumberText?.text.toString().toInt(),
            order_mail = emailText?.text.toString()
        )
        runBlocking {
            withContext(Dispatchers.IO) {
                RetrofitOrderInfoRepository().createOrder(orderModel)
            }
        }
    }
}