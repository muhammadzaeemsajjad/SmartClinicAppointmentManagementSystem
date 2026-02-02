package managerClasses;

import java.util.ArrayList;
import java.util.Collections;

import CustomExceptions.UserAlreadyExistsException;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.LocalDateTime;

import models.*;

public class DoctorManager implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    // Fields
    private ArrayList<Doctor> doctors;
    private transient FileManager fileManager;

     // Constructor
    public DoctorManager(FileManager fileManager) {
        this.fileManager = fileManager;
        this.doctors = new ArrayList<>();
       
    }

    public ArrayList<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(ArrayList<Doctor> doctors) {
        this.doctors = doctors;
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public void setFileManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }

   

    // Functions
    // === Find a doctor by ID ===
    public Doctor findDoctorById(int doctorID) {
        for (Doctor doctor : doctors) {
            if (doctor.getDocID() == doctorID) {
                return doctor;
            }
        }
        return null;
    }

    public void addDoctorWithCheck(Doctor d) throws UserAlreadyExistsException {
        if (d == null)
            return;

        // Check if a doctor with the same ID already exists
        for (Doctor existingDoctor : doctors) {
            if (existingDoctor.getDocID() == d.getDocID()) {
                throw new UserAlreadyExistsException(
                        "Doctor with ID " + d.getDocID() + " already exists!");
            }
        }

        // Add doctor if no conflict
        doctors.add(d);
        saveDoctorsToFile();
    }

  

    public void updateDoctor(int doctorID, Doctor updatedDoctor) {
        Doctor existingDoctor = findDoctorById(doctorID);

        if (existingDoctor != null) {
            int index = doctors.indexOf(existingDoctor);
            doctors.set(index, updatedDoctor);
            saveDoctorsToFile();
        } else {
            throw new IllegalArgumentException("Doctor with ID " + doctorID + " not found.");
        }
    }

    public void addAppointmentToDoctor(Doctor doctor, Appointment appointment) {
        if (doctor != null && appointment != null) {
            doctor.getDoctorAppointments().add(appointment);
            saveDoctorsToFile();
        }
    }

    public void deleteDoctor(int doctorID) {
        Doctor doctorToDelete = findDoctorById(doctorID);
        if (doctorToDelete != null) {
            doctors.remove(doctorToDelete);
            saveDoctorsToFile();
        } else {
            throw new IllegalArgumentException("Doctor with ID " + doctorID + " not found.");
        }
    }

    public Doctor getDoctorById(int doctorID) {
        return findDoctorById(doctorID);
    }

    public ArrayList<Doctor> getAllDoctors() {
        return doctors;
    }

    public ArrayList<Doctor> searchDoctorBySpecialization(String specialization) {
        ArrayList<Doctor> doctorsWithSameSpecialization = new ArrayList<>();
        for (Doctor doctor : doctors) {
            if (doctor.getSpecialization().equals(specialization)) {
                doctorsWithSameSpecialization.add(doctor);
            }
        }

        return doctorsWithSameSpecialization;
    }

    public boolean isAvailable(Doctor doctor, LocalDateTime appointmentDateTime) {

        // Extract day and time separately
        DayOfWeek day = appointmentDateTime.getDayOfWeek();
        LocalTime time = appointmentDateTime.toLocalTime();

        return doctor.getWorkingDays().contains(day) &&
                !time.isBefore(doctor.getStartTime()) &&
                !time.isAfter(doctor.getEndTime());
    }

    public void saveDoctorsToFile() {
        if (fileManager != null && doctors != null) {
            fileManager.writeToFile(doctors);
        }
    }

    public boolean login(String userName, String password) {
        for (Doctor doctor : doctors) {
            if (doctor.getUserName().equals(userName) && doctor.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public Doctor getDoctorByUsername(String username) {
        for (Doctor doctor : doctors) {
            if (doctor.getUserName().equals(username)) {
                return doctor;
            }
        }
        return null;
    }

   public void loadDoctorsFromFile() {
    ArrayList<Object> objs = fileManager.readFromFile();
    doctors = new ArrayList<>();

    if (objs != null) {
        for (Object obj : objs) {
            if (obj instanceof Doctor) {
                doctors.add((Doctor) obj);
            }
        }
    }
}


    public void sortDoctorsByName() {
        Collections.sort(doctors); // Uses the compareTo method in Doctor class
    }

    public ArrayList<Doctor> viewAvailbleDoctorsAtSpecificTime(LocalDateTime dateTime) {
        ArrayList<Doctor> availableDoctorsAtTime = new ArrayList<>();
        for (Doctor doctor : doctors) {
            if (isAvailable(doctor, dateTime)) {
                availableDoctorsAtTime.add(doctor);
            }
        }
        return availableDoctorsAtTime;
    }


}
