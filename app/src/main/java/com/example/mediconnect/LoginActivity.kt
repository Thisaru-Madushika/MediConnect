package com.example.mediconnect

import android.accounts.AccountManager
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomsheet.BottomSheetDialog

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val etEmail = findViewById<EditText>(R.id.email_input)
        val etPassword = findViewById<EditText>(R.id.password_input)
        val btnSignIn = findViewById<Button>(R.id.btn_sign_in)

        btnSignIn.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            // Check if fields are empty
            if (email.isEmpty()) {
                etEmail.error = "Please enter email"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                etPassword.error = "Please enter password"
                return@setOnClickListener
            }

            // Check if any account has been created yet
            if (!UserRepo.hasAccount()) {
                Toast.makeText(this, "No account found. Please create an account first.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            // Validate login credentials
            if (UserRepo.validateLogin(email, password)) {
                // Successful login
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                // Wrong credentials
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
            }
        }

        val btnGoogle = findViewById<ConstraintLayout>(R.id.btn_google)
        btnGoogle.setOnClickListener {
            showGoogleSignInBottomSheet()
        }

        val forgotPassword = findViewById<TextView>(R.id.forgot_password)
        forgotPassword.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

        val createNewAccount = findViewById<TextView>(R.id.create_new_account)
        createNewAccount.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showGoogleSignInBottomSheet() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottom_sheet_google_signin, null)
        bottomSheetDialog.setContentView(view)

        val accountsContainer = view.findViewById<LinearLayout>(R.id.accounts_container)
        val btnUseAnother = view.findViewById<LinearLayout>(R.id.btn_use_another_account)

        // Get Google Accounts from the device
        val accountManager = AccountManager.get(this)
        val accounts = accountManager.getAccountsByType("com.google")

        if (accounts.isEmpty()) {
            // Show a demo account if no accounts are found
            addAccountItem(accountsContainer, "User", "user@gmail.com") {
                navigateToHome(bottomSheetDialog, "User")
            }
        } else {
            accounts.forEach { account ->
                val name = account.name.substringBefore("@").replaceFirstChar { it.uppercase() }
                addAccountItem(accountsContainer, name, account.name) {
                    navigateToHome(bottomSheetDialog, name)
                }
            }
        }

        btnUseAnother.setOnClickListener {
            navigateToHome(bottomSheetDialog, "User")
        }

        bottomSheetDialog.show()
    }

    private fun addAccountItem(container: LinearLayout, name: String, email: String, onClick: () -> Unit) {
        val itemView = LayoutInflater.from(this).inflate(R.layout.item_google_account, container, false)
        val initialsTv = itemView.findViewById<TextView>(R.id.account_initials)
        val nameTv = itemView.findViewById<TextView>(R.id.account_name)
        val emailTv = itemView.findViewById<TextView>(R.id.account_email)

        nameTv.text = name
        emailTv.text = email
        initialsTv.text = if (name.isNotEmpty()) name[0].uppercase().toString() else "G"

        itemView.setOnClickListener { onClick() }
        container.addView(itemView)
    }

    private fun navigateToHome(dialog: BottomSheetDialog, name: String) {
        // Store the user name in Repo to persist across screens
        UserRepo.setUserName(name)
        
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
        dialog.dismiss()
    }
}