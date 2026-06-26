package com.example.mediconnect

data class Appointment(
    val drName: String,
    val specialty: String,
    val date: String,
    val time: String,
    val status: String = "CONFIRMED"
)

object AppointmentRepo {
    val appointmentList = mutableListOf<Appointment>(
        // Previous examples
        Appointment("Dr. Dhammika Weerakoon", "Cardiologist", "Tue, 07 Feb 2026", "10:30 AM", "CONFIRMED"),
        Appointment("Dr. Disna Athukorala", "Neurologist", "Fri, 10 Feb 2026", "02:30 PM", "PENDING")
    )

    fun addAppointment(appointment: Appointment) {
        // Add new appointment to the top of the list
        appointmentList.add(0, appointment)
    }
}