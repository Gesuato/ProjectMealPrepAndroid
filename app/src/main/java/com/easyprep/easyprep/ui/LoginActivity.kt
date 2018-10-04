package com.easyprep.easyprep.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.easyprep.easyprep.R
import com.easyprep.easyprep.data.model.login.User
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    private lateinit var user: User
    private  var mAuth: FirebaseAuth? = null
    private  var mAuthListener: FirebaseAuth.AuthStateListener? = null
    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()
        mAuthListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val userAuth = firebaseAuth.currentUser
            if (userAuth != null) {
                user = User(userAuth.uid)
                // User is signed in
                Log.d("LoginAuth", "onAuthStateChanged:signed_in:" + user.userId)
            } else {
                // User is signed out
                Log.d("LoginAuth", "onAuthStateChanged:signed_out")
            }
        }

        btnLogin.setOnClickListener {
            email = editTextEmail.text.toString()
            password = editTextPassword.text.toString()
            print(email)

            mAuth!!.signInWithEmailAndPassword("email@gmail.com", "123456")
                .addOnCompleteListener(
                    this
                ) { task ->
                    Log.d("LoginSucess", "signInWithEmail:onComplete:" + task.isSuccessful)

                    if (!task.isSuccessful) {
                        Log.w("LoginFailed", "signInWithEmail:failed", task.exception)
                        Toast.makeText(
                            this, "Failed",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }

    override fun onStart() {
        super.onStart()
        mAuth!!.addAuthStateListener(mAuthListener!!)
    }

    override fun onStop() {
        super.onStop()
        if (mAuthListener != null) {
            mAuth!!.removeAuthStateListener(mAuthListener!!)
        }
    }
}
