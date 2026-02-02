package models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Appointment implements Serializable {
    private static final long serialVersionUID = 5L;

    // === Fields ===
    private int appointmentId;
    private int patientId;
    private int doctorId;
    private LocalDateTime dateTime; // combined date + time
    private String status;          // "Scheduled", "Completed", "Cancelled"

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    // === Constructor ===
    public Appointment(int appointmentId, int patientId, int doctorId, LocalDateTime dateTime, String status) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.dateTime = dateTime;
        this.status = status;
    }

    // === Getters ===
    public int getAppointmentId() {
        return appointmentId;
    }

    public int getPatientId() {
        return patientId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getStatus() {
        return status;
    }

    // === Setters ===
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

  @Override
    public String toString() {
        return "Appointment [appointmentId=" + appointmentId + ", patientId=" + patientId + ", doctorId=" + doctorId
                + ", dateTime=" + dateTime.format(formatter) + ", status=" + status + "]";
    }   

   
}
