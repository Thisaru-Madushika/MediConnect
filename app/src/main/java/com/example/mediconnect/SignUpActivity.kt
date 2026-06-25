package com.example.mediconnect

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val fullNameInput = findViewById<EditText>(R.id.full_name_input)
        val emailInput = findViewById<EditText>(R.id.email_input)
        val mobileInput = findViewById<EditText>(R.id.mobile_input)
        val passwordInput = findViewById<EditText>(R.id.password_input)
        val btnSignUp = findViewById<Button>(R.id.btn_signup)
        val loginLink = findViewById<TextView>(R.id.login_link)

        // Go back to Login screen
        loginLink.setOnClickListener {
            finish()
        }

        btnSignUp.setOnClickListener {
            val name = fullNameInput.text.toString().trim()
            val email = emailInput.text.toString().trim()
            val mobile = mobileInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            // Basic validation for input fields
            if (name.isEmpty()) {
                fullNameInput.error = "Please enter your full name"
                fullNameInput.requestFocus()
            } else if (email.isEmpty()) {
                emailInput.error = "Please enter your email address"
                emailInput.requestFocus()
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailInput.error = "Please enter a valid email address"
                emailInput.requestFocus()
            } else if (mobile.isEmpty()) {
                mobileInput.error = "Please enter your mobile number"
                mobileInput.requestFocus()
            } else if (password.isEmpty()) {
                passwordInput.error = "Please enter a password"
                passwordInput.requestFocus()
            } else if (password.length < 8) {
                passwordInput.error = "Password must be at least 8 characters"
                passwordInput.requestFocus()
            } else {
                // Store user details in the temporary repository
                UserRepo.registerUser(name, email, password)

                // Navigate to Home screen and pass user name
                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("USER_NAME", name)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
        }
    }
}