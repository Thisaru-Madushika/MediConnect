package com.example.mediconnect

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class BookingDetailsActivity : AppCompatActivity() {

    private var selectedGender: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_booking_details)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Get selected date and time from the intent
        val selectedDate = intent.getStringExtra("SELECTED_DATE") ?: "Tue, 07 Feb 2026"
        val selectedTime = intent.getStringExtra("SELECTED_TIME") ?: "10:30 AM"

        // Update UI with selected date and time
        findViewById<TextView>(R.id.summary_date).text = selectedDate
        findViewById<TextView>(R.id.summary_time).text = selectedTime

        val etFullName = findViewById<EditText>(R.id.et_full_name)
        val etAge = findViewById<EditText>(R.id.et_age)
        val btnConfirm = findViewById<Button>(R.id.btn_confirm)
        
        val genderButtons = mapOf(
            R.id.male_btn to "Male",
            R.id.female_btn to "Female",
            R.id.other_btn to "Other"
        )

        // Setup gender selection logic
        genderButtons.forEach { (id, gender) ->
            findViewById<TextView>(id).setOnClickListener { view ->
                resetGenderButtons()
                (view as TextView).apply {
                    backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this@BookingDetailsActivity, R.color.blue_primary))
                    setTextColor(Color.WHITE)
                }
                selectedGender = gender
            }
        }

        findViewById<LinearLayout>(R.id.left_3)?.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        btnConfirm.setOnClickListener {
            val name = etFullName.text.toString().trim()
            val age = etAge.text.toString().trim()

            if (name.isEmpty() || age.isEmpty() || selectedGender == null) {
                Toast.makeText(this, "Please fill in all details", Toast.LENGTH_SHORT).show()
            } else {
                // Create and add new appointment
                val newAppointment = Appointment(
                    drName = "Dr. Dhammika Weerakoon",
                    specialty = "Cardiologist",
                    date = selectedDate,
                    time = selectedTime,
                    status = "CONFIRMED"
                )
                AppointmentRepo.addAppointment(newAppointment)

                // Navigate to Records screen
                val intent = Intent(this, RecordsActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun resetGenderButtons() {
        val ids = listOf(R.id.male_btn, R.id.female_btn, R.id.other_btn)
        ids.forEach { id ->
            findViewById<TextView>(id).apply {
                backgroundTintList = ColorStateList.valueOf(Color.WHITE)
                setTextColor(Color.parseColor("#666666"))
            }
        }
    }
}