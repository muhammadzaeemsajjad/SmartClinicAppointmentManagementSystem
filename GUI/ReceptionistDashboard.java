package GUI;

import managerClasses.*;
import models.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import CustomExceptions.*;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ReceptionistDashboard {

    private JFrame frame;
    private Receptionist receptionist;
    private PatientManager patientManager;
    private AppointmentManager appointmentManager;
    private DoctorManager doctorManager;
    private AdminManager adminManager;
private ReceptionistManager receptionistManager;

    public ReceptionistDashboard(Receptionist receptionist,
            PatientManager patientManager,
            AppointmentManager appointmentManager,
            DoctorManager doctorManager,
            AdminManager adminManager,
            ReceptionistManager receptionistManager) {
        this.receptionist = receptionist;
        this.patientManager = patientManager;
        this.appointmentManager = appointmentManager;
        this.doctorManager = doctorManager;
        this.adminManager = adminManager;
        this.receptionistManager = receptionistManager;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Receptionist Dashboard - " + receptionist.getUserName());
        frame.setSize(700, 550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // center on screen
        frame.setLayout(new BorderLayout(20, 20));

        JButton addPatientBtn = createBlueButton("Add Patient");
        JButton removePatientBtn = createBlueButton("Remove Patient");
        JButton updatePatientBtn = createBlueButton("Update Patient");
        JButton bookAppointmentBtn = createBlueButton("Book Appointment");
        JButton deleteAppointmentBtn = createBlueButton("Delete Appointment");
        JButton rescheduleAppointmentBtn = createBlueButton("Reschedule Appointment");
        JButton viewPatientsbtn = createBlueButton("View Patients");
        JButton viewAvailableDoctorsBtn = createBlueButton("View Available Doctors");
        JButton exitBtn = createBlueButton("Exit");

        // Set button size and font so labels are fully visible
        Dimension btnSize = new Dimension(300, 48);
        java.awt.Font btnFont = addPatientBtn.getFont().deriveFont(16f);
        addPatientBtn.setPreferredSize(btnSize);
        addPatientBtn.setFont(btnFont);
        removePatientBtn.setPreferredSize(btnSize);
        removePatientBtn.setFont(btnFont);
        updatePatientBtn.setPreferredSize(btnSize);
        updatePatientBtn.setFont(btnFont);
        bookAppointmentBtn.setPreferredSize(btnSize);
        bookAppointmentBtn.setFont(btnFont);
        deleteAppointmentBtn.setPreferredSize(btnSize);
        deleteAppointmentBtn.setFont(btnFont);
        rescheduleAppointmentBtn.setPreferredSize(btnSize);
        rescheduleAppointmentBtn.setFont(btnFont);
        viewPatientsbtn.setPreferredSize(btnSize);
        viewPatientsbtn.setFont(btnFont);
        viewAvailableDoctorsBtn.setPreferredSize(btnSize);
        viewAvailableDoctorsBtn.setFont(btnFont);

        exitBtn.setPreferredSize(btnSize);
        exitBtn.setFont(btnFont);

        // BUTTON FUNCTIONALITY
        addPatientBtn.addActionListener(e -> addPatient());
        removePatientBtn.addActionListener(e -> removePatient());
        updatePatientBtn.addActionListener(e -> updatePatient());
        bookAppointmentBtn.addActionListener(e -> bookAppointment());
        deleteAppointmentBtn.addActionListener(e -> deleteAppointment());
        rescheduleAppointmentBtn.addActionListener(e -> rescheduleAppointment());
        viewAvailableDoctorsBtn.addActionListener(e -> viewAvailableDoctors());
        viewPatientsbtn.addActionListener(e -> viewPatients());

        exitBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(frame,
                    "Are you sure you want to exit?",
                    "Exit Confirmation",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                frame.dispose();
                new MainLoginGui(adminManager, doctorManager, receptionistManager, patientManager, appointmentManager);
            }
        });

        // Put buttons into a centered panel so preferred sizes are respected
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.add(addPatientBtn);
        buttonPanel.add(removePatientBtn);
        buttonPanel.add(updatePatientBtn);
        buttonPanel.add(bookAppointmentBtn);
        buttonPanel.add(deleteAppointmentBtn);
        buttonPanel.add(rescheduleAppointmentBtn);
        buttonPanel.add(viewPatientsbtn);
        buttonPanel.add(viewAvailableDoctorsBtn);
        buttonPanel.add(exitBtn);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        frame.add(buttonPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private void addPatient() {
        JTextField idF = new JTextField();
        JTextField nameF = new JTextField();
        JTextField ageF = new JTextField();
        JTextField cnicF = new JTextField();
        JTextField phoneF = new JTextField();
        JTextField medicalF = new JTextField();

        JRadioButton maleRB = new JRadioButton("Male");
        JRadioButton femaleRB = new JRadioButton("Female");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleRB);
        genderGroup.add(femaleRB);

        boolean validInput = false;

        while (!validInput) {
            JPanel p = new JPanel(new GridLayout(0, 2, 5, 5));
            p.add(createLabel("Patient ID:"));
            p.add(idF);
            p.add(createLabel("Name:"));
            p.add(nameF);
            p.add(createLabel("Age:"));
            p.add(ageF);

            p.add(createLabel("CNIC(without dashes):"));
            p.add(cnicF);

            p.add(createLabel("Gender:"));
            JPanel genderPanel = new JPanel();
            genderPanel.add(maleRB);
            genderPanel.add(femaleRB);
            p.add(genderPanel);

            p.add(createLabel("Phone Number:"));
            p.add(phoneF);
            p.add(createLabel("Medical History (comma separated):"));
            p.add(medicalF);

            p.setPreferredSize(new Dimension(400, 350));

            int res = JOptionPane.showConfirmDialog(frame, p, "Add Patient", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);
            if (res != JOptionPane.OK_OPTION)
                return;

            boolean errorOccurred = false;

            try {
                int id = ValidationUtil.validateID(idF.getText());
                String name = ValidationUtil.validateName(nameF.getText());
                int age = ValidationUtil.validateAge(ageF.getText());
                String cnic = ValidationUtil.validateCNIC(cnicF.getText());
                long phone = Long.parseLong(ValidationUtil.validatePhone(phoneF.getText()));
                

                String gender = null;
                if (maleRB.isSelected())
                    gender = "Male";
                else if (femaleRB.isSelected())
                    gender = "Female";
                else
                    throw new IllegalArgumentException("Select a gender.");

                ArrayList<String> medicalHistory = new ArrayList<>();
                String historyText = medicalF.getText().trim();
                if (!historyText.isEmpty()) {
                    for (String s : historyText.split(","))
                        medicalHistory.add(s.trim());
                }

                Patient patient = new Patient(id, name, age, cnic, gender, phone, medicalHistory);
                patientManager.addPatient(patient);
                JOptionPane.showMessageDialog(frame, "Patient added successfully!");
                validInput = true;

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "ID, Age, or Phone must be numeric. Check and try again.");
               
                errorOccurred = true;
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(frame, "Input Error: " + ex.getMessage());
                errorOccurred = true;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error adding patient: " + ex.getMessage());
                errorOccurred = true;
            }

            if (!errorOccurred)
                validInput = true;
        }
    }

    private void viewPatients() {
        ArrayList<Patient> patients = patientManager.getPatients();
        if (patients == null || patients.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No patients found.");
            return;
        }

        // Table columns
        String[] columns = { "ID", "Name", "Gender", "Medical History" };

        // Model
        DefaultTableModel model = new DefaultTableModel(columns, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // make cells non-editable
            }
        };

        for (Patient p : patients) {

            // Convert medical history list to a clean string
            String history = "";
            if (p.getMedicalHistory() != null) {
                history = String.join(", ", p.getMedicalHistory());
            }

            Object[] row = {
                    p.getPatientId(),
                    p.getName(),
                    p.getGender(),
                    history
            };
            model.addRow(row);
        }

        JTable table = new JTable(model);
        table.setFont(new Font("Century", Font.PLAIN, 14)); 
        table.setRowHeight(28); 
        table.setEnabled(false);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(700, 400));

        // Sort button
        JButton sortBtn = createBlueButton("Sort by Name");

        sortBtn.addActionListener(e -> {
            patientManager.SortPatientsByName();

            DefaultTableModel newModel = new DefaultTableModel(columns, 0);
            for (Patient p : patientManager.getPatients()) {

                String history = "";
                if (p.getMedicalHistory() != null) {
                    history = String.join(", ", p.getMedicalHistory());
                }

                Object[] row = {
                        p.getPatientId(),
                        p.getName(),
                        p.getGender(),
                        history
                };
                newModel.addRow(row);
            }
            table.setModel(newModel);
        });

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.add(sortBtn);

        JPanel content = new JPanel(new BorderLayout());
        content.add(topPanel, BorderLayout.NORTH);
        content.add(scrollPane, BorderLayout.CENTER);

        JButton okBtn = createBlueButton("Close");
        okBtn.setPreferredSize(new Dimension(120, 40));

        JDialog dialog = new JDialog(frame, "All Patients", true);
        dialog.setLayout(new BorderLayout(10, 10));

        dialog.add(content, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(okBtn);
        dialog.add(bottomPanel, BorderLayout.SOUTH);

        okBtn.addActionListener(e -> dialog.dispose());

        dialog.pack();
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);

    }

    private void removePatient() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter Patient ID to remove:"));
            if (patientManager.deletePatient(id)) {

                JOptionPane.showMessageDialog(frame, "Patient removed successfully!");
            } else {
                JOptionPane.showMessageDialog(frame, "Patient not found.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error removing patient: " + ex.getMessage());
        }
    }

   

    private void updatePatient() {
    try {
        int id = Integer.parseInt(
                JOptionPane.showInputDialog(frame, "Enter Patient ID to update:")
        );

        Patient patient = patientManager.getPatientById(id);
        if (patient == null) {
            JOptionPane.showMessageDialog(frame, "Patient not found.");
            return;
        }

        // Fields (pre-filled)
        JTextField idF = new JTextField(String.valueOf(patient.getPatientId()));
        idF.setEditable(false); // ID should not change

        JTextField nameF = new JTextField(patient.getName());
        JTextField ageF = new JTextField(String.valueOf(patient.getAge()));
        JTextField cnicF = new JTextField(patient.getCNIC());
        JTextField phoneF = new JTextField(String.valueOf(patient.getPhoneNumber()));
        JTextField medicalF = new JTextField(
                String.join(",", patient.getMedicalHistory())
        );

        // Gender radio buttons
        JRadioButton maleRB = new JRadioButton("Male");
        JRadioButton femaleRB = new JRadioButton("Female");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleRB);
        genderGroup.add(femaleRB);

        if ("Male".equalsIgnoreCase(patient.getGender()))
            maleRB.setSelected(true);
        else if ("Female".equalsIgnoreCase(patient.getGender()))
            femaleRB.setSelected(true);

        boolean validInput = false;

        while (!validInput) {
            JPanel p = new JPanel(new GridLayout(0, 2, 5, 5));

            p.add(createLabel("Patient ID:"));
            p.add(idF);
            p.add(createLabel("Name:"));
            p.add(nameF);
            p.add(createLabel("Age:"));
            p.add(ageF);
            p.add(createLabel("CNIC (without dashes):"));
            p.add(cnicF);

            p.add(createLabel("Gender:"));
            JPanel genderPanel = new JPanel();
            genderPanel.add(maleRB);
            genderPanel.add(femaleRB);
            p.add(genderPanel);

            p.add(createLabel("Phone Number:"));
            p.add(phoneF);
            p.add(createLabel("Medical History (comma separated):"));
            p.add(medicalF);

            p.setPreferredSize(new Dimension(400, 350));

            int res = JOptionPane.showConfirmDialog(
                    frame, p, "Update Patient",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (res != JOptionPane.OK_OPTION)
                return;

            try {
                String name = ValidationUtil.validateName(nameF.getText());
                int age = ValidationUtil.validateAge(ageF.getText());
                String cnic = ValidationUtil.validateCNIC(cnicF.getText());
                long phone = Long.parseLong(
                        ValidationUtil.validatePhone(phoneF.getText())
                );

                String gender;
                if (maleRB.isSelected())
                    gender = "Male";
                else if (femaleRB.isSelected())
                    gender = "Female";
                else
                    throw new IllegalArgumentException("Select a gender.");

                ArrayList<String> medicalHistory = new ArrayList<>();
                String historyText = medicalF.getText().trim();
                if (!historyText.isEmpty()) {
                    for (String s : historyText.split(","))
                        medicalHistory.add(s.trim());
                }

                // Update patient object
                patient.setName(name);
                patient.setAge(age);
                patient.setCNIC(cnic);
                patient.setGender(gender);
                patient.setPhoneNumber(phone);
                patient.setMedicalHistory(medicalHistory);

                patientManager.updatePatient(id, patient);

                JOptionPane.showMessageDialog(frame, "Patient updated successfully!");
                validInput = true;

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Age or Phone must be numeric.");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(frame, "Input Error: " + ex.getMessage());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error updating patient: " + ex.getMessage());
            }
        }

    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(frame, "Invalid Patient ID.");
    }
}


 private void bookAppointment() {

    JTextField patientIdF = new JTextField(10);
    JTextField doctorIdF = new JTextField(10);
    JTextField dateTimeF = new JTextField("2025-01-01T10:30"); // hint

    boolean validInput = false;

    while (!validInput) {
        JPanel p = new JPanel(new GridLayout(0, 2, 3, 3));
        // p.setBackground(Color.);
        // p.setPreferredSize(new Dimension(900,100));

        p.add(createLabel("Patient ID:"));
        p.add(patientIdF);

        p.add(createLabel("Doctor ID:"));
        p.add(doctorIdF);

        p.add(createLabel("Date & Time (yyyy-MM-ddTHH:mm):"));
        p.add(dateTimeF);

        p.setPreferredSize(new Dimension(450, 150));

        int res = JOptionPane.showConfirmDialog(
                frame,
                p,
                "Book Appointment",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (res != JOptionPane.OK_OPTION)
            return;

        boolean errorOccurred = false;

        try {
            int patientId = ValidationUtil.validateID(patientIdF.getText());
            int doctorId = ValidationUtil.validateID(doctorIdF.getText());

            LocalDateTime dateTime;
            try {
                dateTime = LocalDateTime.parse(dateTimeF.getText().trim());
            } catch (Exception e) {
                dateTimeF.setText("");
                throw new IllegalArgumentException("Date & Time must be in yyyy-MM-ddTHH:mm format.");
            }

            Patient patient = patientManager.getPatientById(patientId);
            if (patient == null)
                throw new IllegalArgumentException("Patient not found.");

            Doctor doctor = doctorManager.getDoctorById(doctorId);
            if (doctor == null)
                throw new IllegalArgumentException("Doctor not found.");

            boolean booked = appointmentManager.bookAppointment(patient, doctor, dateTime);

            if (!booked)
                throw new IllegalArgumentException("Doctor not available at that time.");

            appointmentManager.saveAppointmentsToFile();
            JOptionPane.showMessageDialog(frame, "Appointment booked successfully!");
            validInput = true;

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(frame, "Input Error: " + ex.getMessage());
            errorOccurred = true;

            // clear only related fields
            if (ex.getMessage().toLowerCase().contains("patient"))
                patientIdF.setText("");
            if (ex.getMessage().toLowerCase().contains("doctor"))
                doctorIdF.setText("");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error booking appointment: " + ex.getMessage());
            errorOccurred = true;
        }

        if (!errorOccurred)
            validInput = true;
    }
}
private void viewAvailableDoctors() {
    LocalDateTime time = null;

    while (true) {
        String timeStr = JOptionPane.showInputDialog(
                frame,
                "Enter time (yyyy-MM-ddTHH:mm):"
        );

        if (timeStr == null) {
            // User pressed cancel
            return;
        }

        try {
            time = LocalDateTime.parse(timeStr);
            break; // valid input, exit loop
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    frame,
                    "Invalid time format. Use yyyy-MM-ddTHH:mm",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            // loop continues and asks again
        }
    }

    // Now time is valid
    ArrayList<Doctor> list = doctorManager.viewAvailbleDoctorsAtSpecificTime(time);

    if (list.isEmpty()) {
        JOptionPane.showMessageDialog(frame, "No doctors available at this time.");
        return;
    }

    // Table columns
    String[] columns = {
            "Doctor ID", "Name", "Specialization",
            "Start Time", "End Time", "Experience"
    };

    // Table model
    DefaultTableModel model = new DefaultTableModel(columns, 0);

    for (Doctor d : list) {
        Object[] row = {
                d.getDocID(),
                d.getName(),
                d.getSpecialization(),
                d.getStartTime(),
                d.getEndTime(),
                d.getExperience()
        };
        model.addRow(row);
    }

    JTable table = new JTable(model);
    table.setEnabled(false); // view-only

    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setPreferredSize(new Dimension(600, 300));

    JOptionPane.showMessageDialog(
            frame,
            scrollPane,
            "Available Doctors",
            JOptionPane.PLAIN_MESSAGE
    );
}





    private void deleteAppointment() {
        try {
            int appointmentId = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter Appointment ID to delete:"));
            if (appointmentManager.deleteAppointment(appointmentId)) {
                JOptionPane.showMessageDialog(frame, "Appointment deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(frame, "Appointment not found.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error deleting appointment: " + ex.getMessage());
        }
    }

    private void rescheduleAppointment() {
        try {
            int appointmentId = Integer
                    .parseInt(JOptionPane.showInputDialog(frame, "Enter Appointment ID to reschedule:"));
            Appointment appointment = appointmentManager.findAppointmentById(appointmentId);
            if (appointment != null) {
                String newDateTimeStr = JOptionPane.showInputDialog(frame, "Enter new date & time (yyyy-MM-ddTHH:mm):");
                LocalDateTime newDateTime = LocalDateTime.parse(newDateTimeStr);
                if (appointmentManager.rescheduleAppointment(appointmentId, newDateTime)) {
                    appointmentManager.saveAppointmentsToFile();
                    JOptionPane.showMessageDialog(frame, "Appointment rescheduled successfully!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Doctor not available at the new time!");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Appointment not found.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error rescheduling appointment: " + ex.getMessage());
        }
    }
    // ================== Smart Clinic Gradient Button Helpers ==================

    private JButton createBlueButton(String text) {
        return createGradientButton(text,
                new Color(52, 152, 219), new Color(41, 128, 185));
    }

    private JButton createGradientButton(String text, Color c1, Color c2) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, c1, 0, getHeight(), c2);
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                super.paintComponent(g);
            }
        };
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Century", Font.BOLD, 14));
        return btn;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Century", Font.BOLD, 12));
        return label;
    }

}
