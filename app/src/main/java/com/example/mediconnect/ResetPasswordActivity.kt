package com.example.mediconnect

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ResetPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_reset_password)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<ImageView>(R.id.btn_back).setOnClickListener {
            finish()
        }

        val newPasswordInput = findViewById<EditText>(R.id.new_password_input)
        val confirmPasswordInput = findViewById<EditText>(R.id.confirm_password_input)
        val btnUpdatePassword = findViewById<Button>(R.id.btn_update_password)

        btnUpdatePassword.setOnClickListener {
            val newPassword = newPasswordInput.text.toString().trim()
            val confirmPassword = confirmPasswordInput.text.toString().trim()

            if (newPassword.isEmpty()) {
                newPasswordInput.error = "Please enter a new password"
                newPasswordInput.requestFocus()
            } else if (newPassword.length < 8) {
                newPasswordInput.error = "Password must be at least 8 characters"
                newPasswordInput.requestFocus()
            } else if (confirmPassword.isEmpty()) {
                confirmPasswordInput.error = "Please confirm your password"
                confirmPasswordInput.requestFocus()
            } else if (newPassword != confirmPassword) {
                confirmPasswordInput.error = "Passwords do not match"
                confirmPasswordInput.requestFocus()
            } else {
                val intent = Intent(this, SuccessActivity::class.java)
                startActivity(intent)
            }
        }
    }
}