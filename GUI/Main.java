package GUI;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.JOptionPane;

import CustomExceptions.*;
import managerClasses.*;
import models.*;

public class Main {
    public static void main(String[] args) {

        // ---------------- FILE MANAGERS ----------------
        FileManager adminFM = new FileManager(
                "C:\\Users\\ZAEEM\\Desktop\\Semester-project\\Files\\AdminData.dat");
        FileManager doctorFM = new FileManager(
                "C:\\Users\\ZAEEM\\Desktop\\Semester-project\\Files\\DoctorData.dat");
        FileManager receptionistFM = new FileManager(
                "C:\\Users\\ZAEEM\\Desktop\\Semester-project\\Files\\ReceptionistData.dat");
        FileManager patientFM = new FileManager(
                "C:\\Users\\ZAEEM\\Desktop\\Semester-project\\Files\\PatientData.dat");
        FileManager appointmentFM = new FileManager(
                "C:\\Users\\ZAEEM\\Desktop\\Semester-project\\Files\\AppointmentsData.dat");
        // ---------------- MANAGERS ----------------
        AdminManager adminManager = new AdminManager(adminFM);
        DoctorManager doctorManager = new DoctorManager(doctorFM);
        ReceptionistManager receptionistManager = new ReceptionistManager(receptionistFM);
        PatientManager patientManager = new PatientManager(patientFM);
        AppointmentManager appointmentManager = new AppointmentManager(appointmentFM, doctorManager);

        // ---------------- LOAD DATA FROM FILES ----------------
        try {
            adminManager.loadAdminsFromFile();
            doctorManager.loadDoctorsFromFile();
            receptionistManager.loadReceptionistsFromFile();
            patientManager.loadPatientsFromFile();
            appointmentManager.loadAppointmentsFromFile();

            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error loading data from files: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);

        }

        // ---------------- LINK APPOINTMENTS TO DOCTORS ----------------
        for (Appointment a : appointmentManager.getAppointments()) {
            Doctor d = doctorManager.getDoctorById(a.getDoctorId());
            if (d != null) {
                if (!appointmentManager.isDoctorFree(d.getDocID(), a.getDateTime())) {
                    doctorManager.addAppointmentToDoctor(d, a);
                } else {
                    JOptionPane.showMessageDialog(null, "Conflict detected: Doctor " + d.getUserName() +
                            " already has an appointment at " + a.getDateTime(), "Conflict",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        }

        // ---------------- ADD DEFAULT DATA IF FILES EMPTY ----------------
        if (doctorManager.getDoctors().isEmpty()) {
            Set<DayOfWeek> days1 = Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY);
            Set<DayOfWeek> days2 = Set.of(DayOfWeek.THURSDAY, DayOfWeek.FRIDAY);
            Set<DayOfWeek> days3 = Set.of(DayOfWeek.MONDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY);

            Doctor d1 = new Doctor("Ahmed Khan", 40, "4210112345671", "Male", 30012345671L, "dr_ahmed", "pass123",
                    "ahmed@example.com", "Karachi, Pakistan", 101, 15, "Cardiologist",
                    LocalTime.of(9, 0), LocalTime.of(17, 0), days1);

            Doctor d2 = new Doctor("Sara Ali", 35, "3520276543219", "Female", 31112345671L, "dr_sara", "sara789",
                    "sara@example.com", "Lahore, Pakistan", 102, 10, "Dermatologist",
                    LocalTime.of(10, 0), LocalTime.of(16, 0), days2);

            Doctor d3 = new Doctor("Bilal Khan", 50, "6110198765432", "Male", 32112345671L, "dr_bilal", "bilal456",
                    "bilal@example.com", "Islamabad, Pakistan", 103, 25, "Orthopedic Surgeon",
                    LocalTime.of(8, 30), LocalTime.of(12, 30), days3);

            try {
                doctorManager.addDoctorWithCheck(d1);
                doctorManager.addDoctorWithCheck(d2);
                doctorManager.addDoctorWithCheck(d3);
            } catch (UserAlreadyExistsException e) {
                JOptionPane.showMessageDialog(null, "Error adding default doctors: " + e.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        if (adminManager.getAdmins().isEmpty()) {
            Admin admin1 = new Admin(
                    "Admin Name", // name
                    45, // age
                    "6110144556678", // CNIC
                    "Male", // gender
                    30055566771L, // phoneNumber
                    "admin123", // userName
                    "adminpass", // password
                    "admin@example.com", // email
                    "Islamabad, Pakistan" // address
            );
            adminManager.getAdmins().add(admin1);
            adminManager.saveAdminsToFile();
        }
        

        if (receptionistManager.getReceptionists().isEmpty()) {
            Receptionist rec1 = new Receptionist("Sana", 28, "3740611223345", "Female", 31700112231L,
                    "rec_sana", "sana123", "sana.recep@example.com", "Rawalpindi, Pakistan", 201);
            try {
                receptionistManager.addReceptionist(rec1);
            } catch (UserAlreadyExistsException e) {
                JOptionPane.showMessageDialog(null, "Error adding default receptionists: " + e.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            receptionistManager.saveReceptionistsToFile();
        }

        if (patientManager.getPatients().isEmpty()) {
            ArrayList<String> history1 = new ArrayList<>();
            history1.add("High Blood Pressure");
            history1.add("Allergy: Penicillin");
            ArrayList<String> history2 = new ArrayList<>();
            history2.add("Diabetes Type II");
            ArrayList<String> history3 = new ArrayList<>();
            history3.add("Asthma");
            history3.add("Appendix Surgery (2021)");
            ArrayList<String> history4 = new ArrayList<>();
            history4.add("Migraine");
            ArrayList<String> history5 = new ArrayList<>();
            history5.add("Fractured Arm (2023)");

            Patient p1 = new Patient(101, "Ali Khan", 40, "4210111223345", "Male", 30011223341L, history1);
            Patient p2 = new Patient(102, "Fatima Ali", 35, "3520122334456", "Female", 31122334451L, history2);
            Patient p3 = new Patient(103, "Omar Farooq", 28, "6110133445567", "Male", 32133445561L, history3);
            Patient p4 = new Patient(104, "Ayesha Siddiqui", 30, "3740644556678", "Female", 31744556671L, history4);
            Patient p5 = new Patient(105, "Zain Malik", 25, "4210155667789", "Male", 30055667781L, history5);

            try {
                patientManager.addPatient(p1);
                patientManager.addPatient(p2);
                patientManager.addPatient(p3);
                patientManager.addPatient(p4);
                patientManager.addPatient(p5);
            } catch (UserAlreadyExistsException e) {
                JOptionPane.showMessageDialog(null, "Error adding default patients: " + e.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            patientManager.savePatientsToFile();
        }

        // ---------------- START GUI ----------------
        new MainLoginGui(adminManager, doctorManager, receptionistManager, patientManager, appointmentManager);
    }
}
