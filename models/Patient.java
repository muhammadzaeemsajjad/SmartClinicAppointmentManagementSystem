package models;

import java.util.ArrayList;

public class Patient extends Person implements Comparable<Patient> {
    private static final long serialVersionUID = 3L;

    private int patientId;
    private ArrayList<String> medicalHistory;

    public Patient(int patientId, String name, int age, String CNIC,
                   String gender, long phoneNumber,
                   ArrayList<String> medicalHistory) {

        super(name, age, CNIC, gender, phoneNumber);

        this.patientId = patientId;
        this.medicalHistory = medicalHistory;
    }

    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }

    public ArrayList<String> getMedicalHistory() { return medicalHistory; }
    public void setMedicalHistory(ArrayList<String> medicalHistory) { this.medicalHistory = medicalHistory; }

    @Override
    public int compareTo(Patient other) {
        return this.name.compareToIgnoreCase(other.name);
    }

    @Override
    public String toString() {
        return "Patient [name=" + name +
                ", gender=" + gender +
                ", medicalHistory=" + medicalHistory +
                ", patientId=" + patientId + "]";
    }
}
