package com.example.shoppingapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class Home : AppCompatActivity() {
    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        val logout = findViewById<Button>(R.id.logout)

        logout.setOnClickListener {



            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }


    }
   
}