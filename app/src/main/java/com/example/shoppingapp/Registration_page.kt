package com.example.shoppingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern

class Registration_page : AppCompatActivity() {

    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_page)
        firebaseAuth = FirebaseAuth.getInstance()
        val etUserName = findViewById<EditText>(R.id.edtusername)
        val etEmailAddress = findViewById<EditText>(R.id.edtuseremail)
        val etUserPassword = findViewById<EditText>(R.id.edtpass)


        val logintext = findViewById<TextView>(R.id.text_login)
        logintext.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()

        }

       val btnSignup = findViewById<Button>(R.id.btn_signup)
        btnSignup.setOnClickListener {
            if (etEmailAddress.text.toString().isEmpty()) {
                etEmailAddress.error = "Please Enter Email"
                etEmailAddress.requestFocus()
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(etEmailAddress.text.toString()).matches()){
                etEmailAddress.error = "Please Enter Valid Email Address"
            etEmailAddress.requestFocus()
                return@setOnClickListener
        }
            if (etUserPassword.text.toString().isEmpty()) {
                etUserPassword.error = "Please Enter Password"
                etUserPassword.requestFocus()
                return@setOnClickListener
            }
            firebaseAuth.createUserWithEmailAndPassword(etEmailAddress.text.toString(),etUserPassword.text.toString())
                .addOnCompleteListener {
                    if (it.isSuccessful){
                        val user = firebaseAuth.currentUser
                        user?.sendEmailVerification()
                            ?.addOnCompleteListener {
                                if(it.isSuccessful){
                                    startActivity(Intent(this,LoginActivity::class.java))
                                    finish()
                                }
                            }
                    }
                    else{
                        Toast.makeText(this,"Error Creating User",Toast.LENGTH_SHORT).show()
                    }
                }

        }

    }






    }
