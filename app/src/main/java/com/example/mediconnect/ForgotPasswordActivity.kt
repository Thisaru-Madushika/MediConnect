package com.example.mediconnect

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ForgotPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_forgot_password)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<ImageView>(R.id.btn_back).setOnClickListener {
            finish()
        }

        val emailInput = findViewById<EditText>(R.id.email_input)
        val btnSendResetLink = findViewById<Button>(R.id.btn_send_reset_link)

        btnSendResetLink.setOnClickListener {
            val email = emailInput.text.toString().trim()

            if (email.isEmpty()) {
                emailInput.error = "Please enter your email address"
                emailInput.requestFocus()
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailInput.error = "Please enter a valid email address"
                emailInput.requestFocus()
            } else {
                val intent = Intent(this, ResetPasswordActivity::class.java)
                startActivity(intent)
            }
        }
    }
}