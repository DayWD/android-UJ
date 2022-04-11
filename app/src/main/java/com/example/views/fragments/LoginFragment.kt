package com.example.views.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.views.R
import com.example.views.UserViewModel
import com.example.views.realm.repositories.RealmUserRepository
import com.example.views.retrofit.models.RetrofitUser
import com.example.views.retrofit.repositories.RetrofitUserRepository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onStart() {
        super.onStart()

        val registerButton = view?.findViewById<Button>(R.id.buttonRegister)
        val loginButton = view?.findViewById<Button>(R.id.buttonLogin)
        val mapButton = view?.findViewById<Button>(R.id.buttonMap)
        val googleButton =
            view?.findViewById<com.google.android.gms.common.SignInButton>(R.id.buttonGoogle)

        val toRegisterPageFragment =
            LoginFragmentDirections.actionLoginFragmentToRegisterPageFragment()
        val toMapPageFragment = LoginFragmentDirections.actionLoginFragmentToMapsFragment()
        registerButton?.setOnClickListener { findNavController().navigate(toRegisterPageFragment) }
        loginButton?.setOnClickListener { loginButtonHandler() }
        mapButton?.setOnClickListener { findNavController().navigate(toMapPageFragment) }

        // Google SignIn code below
        val account = GoogleSignIn.getLastSignedInAccount(requireContext())
        /*  updateUI(account)*/


        //    googleButton?.isEnabled = UserViewModel.userLogin.isNullOrEmpty()

    }

    private fun loginButtonHandler() {
        val loginText = view?.findViewById<TextView>(R.id.editTextLogin)
        val passwordText = view?.findViewById<TextView>(R.id.editTextPassword)

        val users = RealmUserRepository().getUsers()

        if (isNotEmpty(loginText) && isNotEmpty(passwordText)) {
            for (user in users) {
                if (user.login == loginText?.text.toString() && user.password == passwordText?.text.toString()) {
                    UserViewModel.userId = user.user_id
                    UserViewModel.userLogin = user.login

                    val toProductsPageFragment =
                        LoginFragmentDirections.actionLoginFragmentToProductsFragment()
                    findNavController().navigate(toProductsPageFragment)
                    Toast.makeText(
                        requireContext(),
                        "You are already logged in!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            if (UserViewModel.userId == null)
                Toast.makeText(requireContext(), "Invalid login or password!", Toast.LENGTH_LONG)
                    .show()
        } else {
            Toast.makeText(requireContext(), "Not all text boxes are filled!", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun isNotEmpty(textBox: TextView?): Boolean {
        return textBox?.text?.length != 0
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
        val googleSignInButton =
            view.findViewById<com.google.android.gms.common.SignInButton>(R.id.buttonGoogle)
        googleSignInButton?.setOnClickListener { signIn() }

        googleSignInClient.signOut()
        UserViewModel.userLogin = null
        UserViewModel.userId = null
    }

    private fun signIn() {
        val signInIntent: Intent = googleSignInClient.signInIntent
        resultLauncher.launch(signInIntent)
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val task: Task<GoogleSignInAccount> =
                    GoogleSignIn.getSignedInAccountFromIntent(data)

                val toProductsPageFragment =
                    LoginFragmentDirections.actionLoginFragmentToProductsFragment()
                findNavController().navigate(toProductsPageFragment)

                handleSignInResult(task)
            }
        }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            addOAuthAccountToDataBase(account.id!!, account.email!!)
            UserViewModel.userLogin = account.id
            val users = RealmUserRepository().getUsers()

            for (user in users) {
                if (user.login == account.id)
                    UserViewModel.userId = user.user_id
            }

            Toast.makeText(requireContext(), "You are already logged in!", Toast.LENGTH_LONG).show()
        } catch (e: ApiException) {
            Toast.makeText(requireContext(), "Google authentication failed", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun addOAuthAccountToDataBase(loginOAuth: String, emailOAuth: String) {
        val highestUserID: Int = RealmUserRepository().getHighestUserId()

        val users = RealmUserRepository().getUsers()

        for (user in users) {
            if (user.login == loginOAuth) {
                return
            }
        }

        RealmUserRepository().addToUser(
            user_id = highestUserID + 1,
            login = loginOAuth,
            email = emailOAuth,
            password = "OAuth"
        )
        val userModel = RetrofitUser(
            login = loginOAuth,
            email = emailOAuth,
            password = "OAuth"
        )
        runBlocking {
            withContext(Dispatchers.IO) {
                RetrofitUserRepository().createUser(userModel)
            }
        }
    }

}