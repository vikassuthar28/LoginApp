package com.example.shoppingapp

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {
    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        firebaseAuth = FirebaseAuth.getInstance()

        val loginbutton = findViewById<Button>(R.id.button)
        val signuptext = findViewById<TextView>(R.id.text_signup)
        val forgetpassword = findViewById<TextView>(R.id.text_forget)

        signuptext.setOnClickListener {

            val intent = Intent(this, Registration_page::class.java)
            startActivity(intent)
            finish()
        }

        loginbutton.setOnClickListener {
            loginUser()

        }
        forgetpassword.setOnClickListener {

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Forget Password")
            val view = layoutInflater.inflate(R.layout.dialog_forget_password, null)
            val username = view.findViewById<EditText>(R.id.et_username)
            builder.setView(view)
            builder.setPositiveButton("Rest", DialogInterface.OnClickListener { _, _ ->
                foregetPassword(username)
            })
            builder.setNegativeButton("Close", DialogInterface.OnClickListener { _, _ -> })
            builder.show()

        }

    }

    private fun foregetPassword(username: EditText) {

        if (username.text.toString().isEmpty()) {
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(username.text.toString()).matches()) {
            return

        }
        firebaseAuth.sendPasswordResetEmail(username.text.toString())
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Email Sent", Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun loginUser() {
        val etEmailAddress = findViewById<EditText>(R.id.edtuser)
        val etUserPassword = findViewById<EditText>(R.id.edtpass)

        if (etEmailAddress.text.toString().isEmpty()) {
            etEmailAddress.error = "Please Enter Email"
            etEmailAddress.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(etEmailAddress.text.toString()).matches()) {
            etEmailAddress.error = "Please Enter Valid Email Address"
            etEmailAddress.requestFocus()
            return
        }
        if (etUserPassword.text.toString().isEmpty()) {
            etUserPassword.error = "Please Enter Password"
            etUserPassword.requestFocus()
            return
        }

        firebaseAuth.signInWithEmailAndPassword(
            etEmailAddress.text.toString(),
            etUserPassword.text.toString()
        )
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    updateUI(user)
                    Toast.makeText(this, "Sucess", Toast.LENGTH_SHORT).show()
                } else {
                    updateUI(null)
                    Toast.makeText(this, "Error on Authentication", Toast.LENGTH_SHORT).show()
                }
            }

    }

    public override fun onStart() {
        super.onStart()
        val currentUser = firebaseAuth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            if (currentUser.isEmailVerified) {
                startActivity(Intent(this, Home::class.java))
                finish()
            } else {
                Toast.makeText(baseContext, "Check your email address", Toast.LENGTH_SHORT).show()

            }
        } else {
            Toast.makeText(baseContext, "Login failed", Toast.LENGTH_SHORT).show()
        }
    }
}