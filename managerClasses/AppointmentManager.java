package managerClasses;

import java.util.ArrayList;
import java.time.LocalDateTime;
import models.*;

public class AppointmentManager implements java.io.Serializable {
    private static final long serialVersionUID = 4L;

    // ------------------ FIELDS ------------------
    private ArrayList<Appointment> appointments;
    private transient FileManager fileManagerAppointments;
    private transient DoctorManager doctorManager;
    public static final int APPOINTMENT_DURATION_MINUTES = 20;

    // ------------------ CONSTRUCTOR ------------------
    public AppointmentManager(FileManager fileManager, DoctorManager doctorManager) {
        this.fileManagerAppointments = fileManager;
        this.doctorManager = doctorManager;
        this.appointments = new ArrayList<>();
    }

    // ------------------ GETTERS & SETTERS ------------------
    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    public ArrayList<Appointment> getAppointmentsByDoctor(int doctorID) {
        ArrayList<Appointment> list = new ArrayList<>();
        for (Appointment a : appointments) {
            if (a.getDoctorId() == doctorID) {
                list.add(a);
            }
        }
        return list;
    }

    public ArrayList<Appointment> getAppointmentsByPatient(int patientID) {
        ArrayList<Appointment> list = new ArrayList<>();
        for (Appointment a : appointments) {
            if (a.getPatientId() == patientID) {
                list.add(a);
            }
        }
        return list;
    }

    // ------------------ BOOK APPOINTMENT ------------------
    public boolean bookAppointment(Patient p, Doctor d, LocalDateTime dateTime) {
        if (p == null || d == null || dateTime == null)
            return false;
        check(dateTime);

        if (!isDoctorFree(d.getDocID(), dateTime)) {
            return false;
        }

        int newId = appointments.size() + 1;
        Appointment appt = new Appointment(newId, p.getPatientId(), d.getDocID(), dateTime, "Scheduled");

        appointments.add(appt);
        saveAppointmentsToFile();

        // Add appointment to doctor and save doctor data
        doctorManager.addAppointmentToDoctor(d, appt);
        doctorManager.saveDoctorsToFile();

        return true;
    }

    // ------------------ FIND, DELETE, UPDATE, RESCHEDULE ------------------
    public Appointment findAppointmentById(int appointmentID) {
        for (Appointment a : appointments) {
            if (a.getAppointmentId() == appointmentID)
                return a;
        }
        return null;
    }

    public boolean deleteAppointment(int appointmentID) {
        Appointment appt = findAppointmentById(appointmentID);
        if (appt != null) {
            appointments.remove(appt);
            saveAppointmentsToFile();
            return true;
        } else {
            return false;
        }
    }
    private void check(LocalDateTime dateTime) {
        if (dateTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Invalid Time: Cannot schedule in the past.");
        }
    }

    public boolean rescheduleAppointment(int appointmentID, LocalDateTime newDateTime) {
        Appointment appt = findAppointmentById(appointmentID);
        if (appt == null)
            return false;
        check(newDateTime);

        Doctor d = doctorManager.getDoctorById(appt.getDoctorId());
        if (!isDoctorFree(d.getDocID(), newDateTime)) {
            return false;
        }

        appt.setDateTime(newDateTime);
        saveAppointmentsToFile();
        return true;
    }

    public void updateAppointment(int appointmentID, Appointment updatedAppointment) {
        Appointment appt = findAppointmentById(appointmentID);
        if (appt != null) {
            check(updatedAppointment.getDateTime());

            appt.setPatientId(updatedAppointment.getPatientId());
            appt.setDoctorId(updatedAppointment.getDoctorId());
            appt.setDateTime(updatedAppointment.getDateTime());
            appt.setStatus(updatedAppointment.getStatus());
            saveAppointmentsToFile();
        } else {
            throw new IllegalArgumentException("Appointment with ID " + appointmentID + " not found.");
        }
    }

    public boolean isDoctorFree(int doctorId, LocalDateTime newStartTime) {
        LocalDateTime newEndTime = newStartTime.plusMinutes(APPOINTMENT_DURATION_MINUTES);

        for (Appointment a : appointments) {
            if (a.getDoctorId() == doctorId) {

                LocalDateTime existingStart = a.getDateTime();
                LocalDateTime existingEnd = existingStart.plusMinutes(APPOINTMENT_DURATION_MINUTES);

                // TIME OVERLAP CONDITION:
                boolean overlap = newStartTime.isBefore(existingEnd) &&
                        existingStart.isBefore(newEndTime);

                if (overlap) {
                    return false; // doctor is busy at this time
                }
            }
        }
        return true; // doctor is free
    }

    // ------------------ SAVE & LOAD ------------------
    public void saveAppointmentsToFile() {
        if (fileManagerAppointments!=null && appointments!=null) {
            fileManagerAppointments.writeToFile(appointments);
        }
    }

    public void loadAppointmentsFromFile() {
        ArrayList<Object> objs = fileManagerAppointments.readFromFile();
        if (objs != null) {
            appointments = new ArrayList<>(); // reset current list
            for (Object obj : objs) {
                if (obj instanceof Appointment) {
                    appointments.add((Appointment) obj);
                }
            }
        } 
    }

}
