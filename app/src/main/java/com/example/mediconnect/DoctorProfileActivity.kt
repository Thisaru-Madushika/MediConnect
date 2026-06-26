package com.example.mediconnect

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DoctorProfileActivity : AppCompatActivity() {

    private var selectedDateLayout: LinearLayout? = null
    private var selectedSlotText: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_doctor_profile)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Back button
        findViewById<ImageView>(R.id.iv_back).setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Date selection setup
        val dateIds = listOf(R.id.date_mon, R.id.date_tue, R.id.date_wed, R.id.date_thu, R.id.date_fri, R.id.date_sat)
        dateIds.forEach { id ->
            findViewById<LinearLayout>(id)?.setOnClickListener { view ->
                selectDate(view as LinearLayout)
            }
        }

        // Slot selection setup
        val slotIds = listOf(R.id.slot_0900, R.id.slot_1000, R.id.slot_1030, R.id.slot_0100, R.id.slot_0230, R.id.slot_0400)
        slotIds.forEach { id ->
            findViewById<TextView>(id)?.setOnClickListener { view ->
                selectSlot(view as TextView)
            }
        }

        // Confirm Selection button
        val btnConfirm = findViewById<Button>(R.id.btn_confirm_selection)
        btnConfirm.setOnClickListener {
            if (selectedDateLayout != null && selectedSlotText != null) {
                // Get selected date and time strings
                val day = (selectedDateLayout?.getChildAt(0) as? TextView)?.text.toString()
                val date = (selectedDateLayout?.getChildAt(1) as? TextView)?.text.toString()
                val fullDate = "$day, $date Feb 2026"
                val selectedTime = selectedSlotText?.text.toString()

                // Navigate to BookingDetailsActivity with data
                val intent = Intent(this, BookingDetailsActivity::class.java)
                intent.putExtra("SELECTED_DATE", fullDate)
                intent.putExtra("SELECTED_TIME", selectedTime)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please select both a date and a time slot", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun selectDate(layout: LinearLayout) {
        // Reset previous selection
        selectedDateLayout?.let {
            it.backgroundTintList = ColorStateList.valueOf(Color.WHITE)
            if (it.childCount >= 2) {
                (it.getChildAt(0) as? TextView)?.setTextColor(Color.parseColor("#6B7280"))
                (it.getChildAt(1) as? TextView)?.setTextColor(Color.BLACK)
            }
        }

        // Set new selection
        layout.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.blue_primary))
        if (layout.childCount >= 2) {
            (layout.getChildAt(0) as? TextView)?.setTextColor(Color.WHITE)
            (layout.getChildAt(1) as? TextView)?.setTextColor(Color.WHITE)
        }
        
        selectedDateLayout = layout
    }

    private fun selectSlot(textView: TextView) {
        // Reset previous selection
        selectedSlotText?.let {
            it.backgroundTintList = ColorStateList.valueOf(Color.WHITE)
            it.setTextColor(Color.BLACK)
        }

        // Set new selection
        textView.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.blue_primary))
        textView.setTextColor(Color.WHITE)

        selectedSlotText = textView
    }
}