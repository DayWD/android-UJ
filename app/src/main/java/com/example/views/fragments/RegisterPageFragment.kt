package com.example.views.fragments

import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.views.R
import com.example.views.realm.repositories.RealmUserRepository
import com.example.views.retrofit.models.RetrofitUser
import com.example.views.retrofit.repositories.RetrofitUserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class RegisterPageFragment : Fragment(R.layout.fragment_register_page) {

    override fun onStart() {
        super.onStart()
        val registerButton = view?.findViewById<Button>(R.id.regRegisterNow)

        registerButton?.setOnClickListener { checkRegisterParameters() }

    }

    private fun checkRegisterParameters() {
        val loginText = view?.findViewById<TextView>(R.id.editTextRegLogin)
        val emailText = view?.findViewById<TextView>(R.id.editTextRegEmail)
        val passwordText = view?.findViewById<TextView>(R.id.editTextRegPassword)
        val passwordRepeatText = view?.findViewById<TextView>(R.id.editTextRegRepeatPassword)
        val toLoginPageFragment =
            RegisterPageFragmentDirections.actionRegisterPageFragmentToLoginFragment()


        if (isNotEmpty(loginText) && isNotEmpty(emailText) && isNotEmpty(passwordText) && isNotEmpty(
                passwordRepeatText
            )
        ) {
            if (loginIsNotTaken(loginText?.text.toString())) {
                if (passwordText?.text.toString() == passwordRepeatText?.text.toString()) {
                    val highestUserID: Int = RealmUserRepository().getHighestUserId()

                    RealmUserRepository().addToUser(
                        user_id = highestUserID + 1,
                        login = loginText?.text.toString(),
                        email = emailText?.text.toString(),
                        password = passwordText?.text.toString()
                    )
                    val userModel = RetrofitUser(
                        login = loginText?.text.toString(),
                        email = emailText?.text.toString(),
                        password = passwordText?.text.toString()
                    )
                    runBlocking {
                        withContext(Dispatchers.IO) {
                            RetrofitUserRepository().createUser(userModel)
                        }
                    }
                    findNavController().navigate(toLoginPageFragment)

                } else {
                    Toast.makeText(
                        requireContext(),
                        "Passwords are not the same!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "This login has already been taken!",
                    Toast.LENGTH_LONG
                ).show()
            }
        } else {
            Toast.makeText(requireContext(), "Not all text boxes are filled!", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun loginIsNotTaken(userLogin: String): Boolean {
        val users = RealmUserRepository().getUsers()
        for (user in users) {
            if (user.login == userLogin)
                return false
        }
        return true
    }

    private fun isNotEmpty(textBox: TextView?): Boolean {
        return textBox?.text?.length != 0
    }
}