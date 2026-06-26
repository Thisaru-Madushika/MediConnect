package com.example.mediconnect

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RescheduleSuccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_reschedule_success)

        // Set up insets for the header layout
        val headerLayout = findViewById<View>(R.id.header_layout)
        if (headerLayout != null) {
            ViewCompat.setOnApplyWindowInsetsListener(headerLayout) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
                insets
            }
        }

        // Back button click listener
        findViewById<View>(R.id.btn_back)?.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Go to Home button click listener
        findViewById<Button>(R.id.btn_go_home)?.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        // View Appointments button click listener
        findViewById<Button>(R.id.btn_view_appointments)?.setOnClickListener {
            // Close this activity for now as there is no other screen
            finish()
        }
    }
}