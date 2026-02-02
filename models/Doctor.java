package models;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Set;

public class Doctor extends User implements Comparable<Doctor> {
    private static final long serialVersionUID = 5L;

    private int docID;
    private int experience;
    private final String degree;
    private String specialization;
    private LocalTime startTime;
    private LocalTime endTime;
    private Set<DayOfWeek> workingDays;

    private ArrayList<Appointment> doctorAppointments;

    public Doctor(String name, int age, String CNIC, String gender, long phoneNumber,
                  String userName, String password, String email, String address,
                  int docID, int experience, String specialization,
                  LocalTime startTime, LocalTime endTime, Set<DayOfWeek> workingDays) {

        super(name, age, CNIC, gender, phoneNumber, userName, password, email, address);

        this.docID = docID;
        this.experience = experience;
        this.degree = "MBBS";
        this.specialization = specialization;
        this.startTime = startTime;
        this.endTime = endTime;
        this.workingDays = workingDays;

        this.doctorAppointments = new ArrayList<>();
    }

    // Getters & Setters
    public int getDocID() {
        return docID;
    }

    public void setDocID(int docID) {
        this.docID = docID;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getDegree() {
        return degree;
    }


    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Set<DayOfWeek> getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(Set<DayOfWeek> workingDays) {
        this.workingDays = workingDays;
    }

    public ArrayList<Appointment> getDoctorAppointments() {
        return doctorAppointments;
    }

    public void setDoctorAppointments(ArrayList<Appointment> doctorAppointments) {
        this.doctorAppointments = doctorAppointments;
    }

    public int compareTo(Doctor other) {
        return this.userName.compareToIgnoreCase(other.userName);
    }

    @Override
    public String toString() {
        return super.toString() +
                " Doctor [docID=" + docID +
                ", experience=" + experience +
                ", degree=" + degree +
                ", specialization=" + specialization +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", workingDays=" + workingDays + "]";
    }
}
