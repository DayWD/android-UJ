package com.example.views.fragments

import android.annotation.SuppressLint
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.views.R
import com.example.views.realm.repositories.RealmCategoryRepository
import com.example.views.realm.repositories.RealmProductRepository

class CartFragment : Fragment(R.layout.fragment_cart) {


    /* private lateinit var paymentIntentClientSecret: String
     private lateinit var paymentSheet: PaymentSheet*/

    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()

        val args by navArgs<CartFragmentArgs>()
        val product = RealmProductRepository().getProduct(product_id = args.productId)

        val textCartSelected = view?.findViewById<TextView>(R.id.textCartSelected)
        val textCartProduct = view?.findViewById<TextView>(R.id.textCartProduct)
        val textCartCategory = view?.findViewById<TextView>(R.id.textSelectedCategory)
        val buttonCartBuy = view?.findViewById<Button>(R.id.buttonCartBuy)

        val cartCategory = RealmCategoryRepository().getCart(product?.product_category_id!!)
        textCartSelected?.text = product.product_name
        textCartProduct?.text = product.description
        textCartCategory?.text = cartCategory?.category_name
        val price = product.product_price
        buttonCartBuy?.text = "Order for $price zł"


        val toOrderPageFragment =
            CartFragmentDirections.actionCartFragment2ToOrderFragment(productId = args.productId)
        buttonCartBuy?.setOnClickListener { it.findNavController().navigate(toOrderPageFragment) }
    }

    /*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonCartBuy = view.findViewById<Button>(R.id.buttonCartBuy)
        buttonCartBuy.setOnClickListener{ onPayClicked(view) }

        paymentSheet = PaymentSheet(this, ::onPaymentSheetResult)

//        fetchPaymentIntent()

    }


    private fun fetchPaymentIntent() {

        val args by navArgs<CartFragmentArgs>()

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
            Toast.makeText(requireContext(),  message, Toast.LENGTH_LONG).show()
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
            }
            is PaymentSheetResult.Canceled -> {
                Log.i("CheckoutActivity", "Payment canceled!")
            }
            is PaymentSheetResult.Failed -> {
                showAlert("Payment failed", paymentResult.error.localizedMessage)
            }
        }
    }*/

}