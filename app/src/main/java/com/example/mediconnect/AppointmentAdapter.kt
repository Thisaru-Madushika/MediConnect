package com.example.mediconnect

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AppointmentAdapter(
    private val appointments: List<Appointment>,
    private val onCancelClick: (Appointment) -> Unit,
    private val onRescheduleClick: (Appointment) -> Unit
) : RecyclerView.Adapter<AppointmentAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val drName: TextView = view.findViewById(R.id.tv_dr_name)
        val specialty: TextView = view.findViewById(R.id.tv_specialty)
        val status: TextView = view.findViewById(R.id.tv_status)
        val dateTime: TextView = view.findViewById(R.id.tv_date_time)
        val btnCancel: TextView = view.findViewById(R.id.btn_cancel)
        val btnReschedule: TextView = view.findViewById(R.id.btn_reschedule)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_appointment, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val appointment = appointments[position]
        holder.drName.text = appointment.drName
        holder.specialty.text = appointment.specialty
        holder.status.text = appointment.status
        holder.dateTime.text = "${appointment.date}   |   ${appointment.time}"

        holder.btnCancel.setOnClickListener { onCancelClick(appointment) }
        holder.btnReschedule.setOnClickListener { onRescheduleClick(appointment) }
    }

    override fun getItemCount() = appointments.size
}