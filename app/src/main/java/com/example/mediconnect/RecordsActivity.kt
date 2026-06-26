package com.example.mediconnect

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecordsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_records)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.rv_appointments)
        recyclerView.layoutManager = LinearLayoutManager(this)
        
        // Set up the adapter with data from AppointmentRepo
        val adapter = AppointmentAdapter(
            AppointmentRepo.appointmentList,
            onCancelClick = { appointment ->
                // Navigate to Cancel Confirmation
                val intent = Intent(this, CancelConfirmationActivity::class.java)
                startActivity(intent)
            },
            onRescheduleClick = { appointment ->
                // Navigate to Reschedule screen
                val intent = Intent(this, RescheduleActivity::class.java)
                startActivity(intent)
            }
        )
        recyclerView.adapter = adapter

        // Bottom Navigation - Home
        findViewById<LinearLayout>(R.id.nav_home).setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}