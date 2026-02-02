package managerClasses;


import models.Patient;

import java.util.ArrayList;
import java.util.Collections;

import CustomExceptions.UserAlreadyExistsException;

public class PatientManager implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    // Fields
    private ArrayList<Patient> patients;
    private FileManager fileManager;

    public PatientManager(FileManager fileManager) {
        this.fileManager = fileManager;
        this.patients = new ArrayList<>();
        
    }

     // Getters and Setters

    public ArrayList<Patient> getPatients() {
        return patients;
    }

    public void SortPatientsByName() {
        Collections.sort(patients); // Uses the compareTo method in Patient class
    }

    public void setPatients(ArrayList<Patient> patients) {
        this.patients = patients;
        savePatientsToFile();
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public void setFileManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    
    
    

    public void addPatient(Patient p) throws UserAlreadyExistsException {
        if (p == null)
            return;

        // Check if a patient with the same ID already exists
        for (Patient existingPatient : patients) {
            if (existingPatient.getPatientId() == p.getPatientId()) {
                throw new UserAlreadyExistsException(
                        "Patient with ID " + p.getPatientId() + " already exists!");
            }
        }

        // Add patient if no conflict
        patients.add(p);
        savePatientsToFile();
    }

    public void updatePatient(int patientID, Patient updatedPatient) {
        Patient existingPatient = getPatientById(patientID);

        if (existingPatient != null) {
            int index = patients.indexOf(existingPatient);
            patients.set(index, updatedPatient);
            savePatientsToFile();
        } else {
            throw new IllegalArgumentException("Patient with ID " + patientID + " not found.");
        }
    }

    public boolean deletePatient(int patientID) {
        Patient toDelete = getPatientById(patientID);
       
        if (toDelete != null) {
            patients.remove(toDelete);
            savePatientsToFile();
            return true;
        } else {
            return false;
        }
    }

    public Patient getPatientById(int patientID) {
        Patient searchedPatient = null;
        for (Patient p : patients) {
            if (p.getPatientId() == patientID) {
                searchedPatient = p;
                break;
            }
        }
        
        if (searchedPatient != null) {
            return searchedPatient;
        }
        return null;

    }


    public void savePatientsToFile() {
        if (patients!=null && fileManager != null) {
            fileManager.writeToFile(patients);
        }
    }

    public void loadPatientsFromFile() {
    ArrayList<Object> objs = fileManager.readFromFile(); // read raw objects from file
    patients = new ArrayList<>(); // always initialize
    if (objs != null) {
        for (Object obj : objs) {
            if (obj instanceof Patient) {
                patients.add((Patient) obj);
            }
        }
    }
}

}
