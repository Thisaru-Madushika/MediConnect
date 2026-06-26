package com.example.mediconnect

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RescheduleActivity : AppCompatActivity() {

    private var selectedDateView: View? = null
    private var selectedTimeView: TextView? = null

    private var selectedDate: String? = null
    private var selectedTime: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_reschedule)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Back Button
        findViewById<View>(R.id.left_4)?.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        setupDateSelection()
        setupTimeSelection()

        // Update Appointment Button Click
        findViewById<Button>(R.id.btn_update_appointment)?.setOnClickListener {
            if (selectedDate == null || selectedTime == null) {
                Toast.makeText(this, "Please select both a date and a time slot.", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, RescheduleSuccessActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun setupDateSelection() {
        val dateIds = listOf(
            R.id.res_date_tue, R.id.res_date_wed, R.id.res_date_thu,
            R.id.res_date_fri, R.id.res_date_sat
        )

        dateIds.forEach { id ->
            val view = findViewById<LinearLayout>(id)
            view.setOnClickListener {
                selectDate(view)
            }
        }
    }

    private fun selectDate(view: LinearLayout) {
        // Reset previous selection
        selectedDateView?.let { prevView ->
            prevView.backgroundTintList = ContextCompat.getColorStateList(this, android.R.color.white)
            if (prevView is LinearLayout) {
                for (i in 0 until prevView.childCount) {
                    val child = prevView.getChildAt(i)
                    if (child is TextView) {
                        if (i == 0) child.setTextColor(Color.parseColor("#666666"))
                        else child.setTextColor(Color.BLACK)
                    }
                }
            }
        }

        // Apply new selection
        selectedDateView = view
        view.backgroundTintList = ContextCompat.getColorStateList(this, R.color.blue_primary) // Assuming a blue color exists or use a hex
        // Since I don't know the exact color resource name, I'll use a direct color if needed, 
        // but let's try to match the UI style. The XML used #007BFF.
        view.backgroundTintList = android.content.res.ColorStateList.valueOf(Color.parseColor("#007BFF"))
        
        for (i in 0 until view.childCount) {
            val child = view.getChildAt(i)
            if (child is TextView) {
                child.setTextColor(Color.WHITE)
                if (i == 1) selectedDate = child.text.toString() // Store day number
            }
        }
    }

    private fun setupTimeSelection() {
        val timeIds = listOf(
            R.id.slot1, R.id.slot2, R.id.slot3,
            R.id.slot4, R.id.slot5, R.id.slot6
        )

        timeIds.forEach { id ->
            val textView = findViewById<TextView>(id)
            textView.setOnClickListener {
                selectTime(textView)
            }
        }
    }

    private fun selectTime(textView: TextView) {
        // Reset previous selection
        selectedTimeView?.let { prevView ->
            prevView.backgroundTintList = android.content.res.ColorStateList.valueOf(Color.WHITE)
            prevView.setTextColor(Color.parseColor("#666666"))
        }

        // Apply new selection
        selectedTimeView = textView
        textView.backgroundTintList = android.content.res.ColorStateList.valueOf(Color.parseColor("#007BFF"))
        textView.setTextColor(Color.WHITE)
        selectedTime = textView.text.toString()
    }
}