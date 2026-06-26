package com.example.mediconnect

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Fetch user name from UserRepo to ensure it persists throughout the session
        findViewById<TextView>(R.id.user_name).text = UserRepo.getUserName()

        // Book Doctor Click
        findViewById<View>(R.id.service_book).setOnClickListener {
            val intent = Intent(this, BookDoctorActivity::class.java)
            startActivity(intent)
        }

        // Bottom Navigation - Records Click
        findViewById<View>(R.id.nav_records).setOnClickListener {
            val intent = Intent(this, RecordsActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        // Refresh the name when returning to the home screen
        findViewById<TextView>(R.id.user_name).text = UserRepo.getUserName()
    }
}