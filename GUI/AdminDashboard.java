package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import CustomExceptions.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import managerClasses.*;
import models.*;

public class AdminDashboard {

    private JFrame frame;
    private AdminManager adminManager;
    private DoctorManager doctorManager;
    private ReceptionistManager receptionistManager;
    private PatientManager patientManager;
private AppointmentManager appointmentManager;

    private JTable doctorTable;
    private JTable receptionistTable;

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public AdminManager getAdminManager() {
        return adminManager;
    }

    public void setAdminManager(AdminManager adminManager) {
        this.adminManager = adminManager;
    }

    public DoctorManager getDoctorManager() {
        return doctorManager;
    }

    public void setDoctorManager(DoctorManager doctorManager) {
        this.doctorManager = doctorManager;
    }

    public ReceptionistManager getReceptionistManager() {
        return receptionistManager;
    }

    public void setReceptionistManager(ReceptionistManager receptionistManager) {
        this.receptionistManager = receptionistManager;
    }

    public JTable getDoctorTable() {
        return doctorTable;
    }

    public void setDoctorTable(JTable doctorTable) {
        this.doctorTable = doctorTable;
    }

    public JTable getReceptionistTable() {
        return receptionistTable;
    }

    public void setReceptionistTable(JTable receptionistTable) {
        this.receptionistTable = receptionistTable;
    }

    public AdminDashboard(AdminManager adminManager, DoctorManager doctorManager,
        ReceptionistManager receptionistManager, PatientManager patientManager, 
        AppointmentManager appointmentManager) { 
    
    this.adminManager = adminManager;
    this.doctorManager = doctorManager;
    this.receptionistManager = receptionistManager;
    this.patientManager = patientManager;         // <--- Assign them
    this.appointmentManager = appointmentManager; // <--- Assign them
    initialize();
}
    

    private void initialize() {
        frame = new JFrame("Admin Dashboard");
        frame.setSize(1100, 650);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(20, 20));

        // --- Title Section ---
        JLabel title = new JLabel("Admin Dashboard", SwingConstants.CENTER);
        title.setFont(new Font("Century", Font.BOLD, 28));
        title.setBackground(new Color(52, 152, 219));
        title.setOpaque(true);
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Add some padding to title
        frame.add(title, BorderLayout.NORTH);

        // --- Center Panel (Holds the two tables) ---
        JPanel center = new JPanel(new GridLayout(1, 2, 20, 20));
        center.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // ================== DOCTORS PANEL ==================
        JPanel doctorsPanel = new JPanel(new BorderLayout(5, 5));
        doctorsPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219), 1), " Doctors Management "));

        doctorTable = new JTable();
        refreshDoctorTable();
        JScrollPane docScroll = new JScrollPane(doctorTable);
        doctorsPanel.add(docScroll, BorderLayout.CENTER);

        // -- Doctor Buttons Panel --
        // Uses FlowLayout to center buttons neatly. 
        // 5 buttons will likely wrap: 3 on top, 2 on bottom.
        JPanel docButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        docButtons.setPreferredSize(new Dimension(500, 100)); // Height accommodates 2 rows of buttons

        // Create Buttons
        JButton addDoctorBtn = createBlueButton("Add Doctor");
        JButton removeDoctorBtn = createBlueButton("Remove"); // Shortened text for better fit
        JButton updateDocButton = createBlueButton("Update"); // Shortened text
        JButton sortDocBtn = createBlueButton("Sort by Name");

        // UI Styling: Smaller, sleeker buttons
        Dimension btnSize = new Dimension(135, 35); // Sleek size
        Font btnFont = new Font("Century", Font.BOLD, 12); // Slightly smaller font

        applyButtonStyle(addDoctorBtn, btnSize, btnFont);
        applyButtonStyle(removeDoctorBtn, btnSize, btnFont);
        applyButtonStyle(updateDocButton, btnSize, btnFont);
        applyButtonStyle(sortDocBtn, btnSize, btnFont);

        // Add Listeners
        addDoctorBtn.addActionListener(e -> showAddDoctorDialog());
        removeDoctorBtn.addActionListener(e -> removeSelectedDoctor());
        updateDocButton.addActionListener(e -> updateDoctor());
        sortDocBtn.addActionListener(e -> {
            doctorManager.sortDoctorsByName();
            refreshDoctorTable();
        });

        // Add to Panel
        docButtons.add(addDoctorBtn);
        docButtons.add(updateDocButton);
        docButtons.add(removeDoctorBtn);
        // -- Force a visual break or just let them wrap --
        docButtons.add(sortDocBtn);

        doctorsPanel.add(docButtons, BorderLayout.SOUTH);

        // ================== RECEPTIONISTS PANEL ==================
        JPanel recPanel = new JPanel(new BorderLayout(5, 5));
        recPanel.setBorder(BorderFactory.createTitledBorder(
             BorderFactory.createLineBorder(new Color(52, 152, 219), 1), " Receptionists Management "));

        receptionistTable = new JTable();
        refreshReceptionistTable();
        JScrollPane recScroll = new JScrollPane(receptionistTable);
        recPanel.add(recScroll, BorderLayout.CENTER);

        // -- Receptionist Buttons Panel --
        JPanel recButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        recButtons.setPreferredSize(new Dimension(500, 100));

        // Create Buttons
        JButton addRecBtn = createBlueButton("Add Receptionist");
        JButton removeRecBtn = createBlueButton("Remove");
        JButton updateRecButton = createBlueButton("Update");
        JButton sortRecBtn = createBlueButton("Sort by Name");

        applyButtonStyle(addRecBtn, btnSize, btnFont);
        applyButtonStyle(removeRecBtn, btnSize, btnFont);
        applyButtonStyle(updateRecButton, btnSize, btnFont);
        applyButtonStyle(sortRecBtn, btnSize, btnFont);

        // Add Listeners
        addRecBtn.addActionListener(e -> showAddReceptionistDialog());
        removeRecBtn.addActionListener(e -> removeSelectedReceptionist());
        updateRecButton.addActionListener(e -> updateReceptionist());
        sortRecBtn.addActionListener(e -> {
            receptionistManager.sortReceptionistsByName();
            refreshReceptionistTable();
        });
    

        // Add to Panel
        recButtons.add(addRecBtn);
        recButtons.add(updateRecButton);
        recButtons.add(removeRecBtn);
        recButtons.add(sortRecBtn);

        recPanel.add(recButtons, BorderLayout.SOUTH);

        // Add panels to center
        center.add(doctorsPanel);
        center.add(recPanel);
        frame.add(center, BorderLayout.CENTER);

        // --- Bottom Section (Logout) ---
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10)); // Add padding
        JButton exitBtn = createBlueButton("Logout");
        applyButtonStyle(exitBtn, btnSize, btnFont);
        
        exitBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(frame,
                    "Are you sure you want to logout?",
                    "Logout Confirmation",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                frame.dispose();
                new MainLoginGui(adminManager, doctorManager, receptionistManager, patientManager, appointmentManager);
            }
        });
        bottom.add(exitBtn);

        frame.add(bottom, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    // --- Helper Method to reduce code repetition ---
    private void applyButtonStyle(JButton btn, Dimension size, Font font) {
        btn.setPreferredSize(size);
        btn.setFont(font);
    }

    // ---- helpers ----
    private void refreshDoctorTable() {
        ArrayList<Doctor> doctors = doctorManager.getAllDoctors();
        String[] cols = { "Username", "DocID", "Specialization", "Experience" }; // removed Available
        DefaultTableModel model = new DefaultTableModel(cols, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // make cells non-editable
            }
        };
        if (doctors != null) {
            for (Doctor d : doctors) {
                model.addRow(new Object[] {
                        d.getUserName(),
                        d.getDocID(),
                        d.getSpecialization(),
                        d.getExperience()
                });
            }
        }
        doctorTable.setModel(model);
        doctorTable.getTableHeader().setBackground(new Color(52, 152, 219)); // light blue
        doctorTable.getTableHeader().setForeground(Color.WHITE);
        doctorTable.getTableHeader().setFont(new Font("Century", Font.BOLD, 16));
        doctorTable.setFont(new Font("Century", Font.PLAIN, 14)); // change font style and size
        doctorTable.setRowHeight(25); // optional, to make table more readable
    }

    private void updateReceptionist() {
    try {
        String inputId = JOptionPane.showInputDialog(frame, "Enter Receptionist ID to update:");
        if (inputId == null) return; // User clicked Cancel
        
        int id = Integer.parseInt(inputId);

        // 1. Find the Receptionist
        // (Assuming you have this method in your manager, otherwise use a loop to find it)
        Receptionist rec = receptionistManager.findReceptionistById(id);
        
        if (rec == null) {
            JOptionPane.showMessageDialog(frame, "Receptionist not found.");
            return;
        }

        // 2. Setup Fields (Pre-fill with existing data)
        JTextField idF = new JTextField(String.valueOf(rec.getReceptionistId()));
        idF.setEditable(false); // ID cannot be changed

        JTextField nameF = new JTextField(rec.getName());
        JTextField ageF = new JTextField(String.valueOf(rec.getAge()));
        JTextField cnicF = new JTextField(rec.getCNIC());
        JTextField phoneF = new JTextField(String.valueOf(rec.getPhoneNumber()));
        JTextField emailF = new JTextField(rec.getEmail());
        JTextField addressF = new JTextField(rec.getAddress());
        JTextField usernameF = new JTextField(rec.getUserName());
        JPasswordField passF = new JPasswordField(rec.getPassword()); // Be careful showing passwords

        // Gender Radio Buttons
        JRadioButton maleRB = new JRadioButton("Male");
        JRadioButton femaleRB = new JRadioButton("Female");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleRB);
        genderGroup.add(femaleRB);

        if ("Male".equalsIgnoreCase(rec.getGender()))
            maleRB.setSelected(true);
        else if ("Female".equalsIgnoreCase(rec.getGender()))
            femaleRB.setSelected(true);

        boolean validInput = false;

        // 3. The Update Loop
        while (!validInput) {
            JPanel p = new JPanel(new GridLayout(0, 2, 5, 5));

            p.add(new JLabel("Rec ID:"));
            p.add(idF);
            p.add(new JLabel("Name:"));
            p.add(nameF);
            p.add(new JLabel("Age:"));
            p.add(ageF);
            p.add(new JLabel("CNIC (no dashes):"));
            p.add(cnicF);

            p.add(new JLabel("Gender:"));
            JPanel genderPanel = new JPanel();
            genderPanel.add(maleRB);
            genderPanel.add(femaleRB);
            p.add(genderPanel);

            p.add(new JLabel("Phone:"));
            p.add(phoneF);
            p.add(new JLabel("Email:"));
            p.add(emailF);
            p.add(new JLabel("Address:"));
            p.add(addressF);
            p.add(new JLabel("Username:"));
            p.add(usernameF);
            p.add(new JLabel("Password:"));
            p.add(passF);

            p.setPreferredSize(new Dimension(400, 450));

            int res = JOptionPane.showConfirmDialog(
                    frame, p, "Update Receptionist",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (res != JOptionPane.OK_OPTION)
                return;

            try {
                // 4. Validate Inputs (Same logic as Add Receptionist)
                String name = ValidationUtil.validateName(nameF.getText());
                int age = ValidationUtil.validateAge(ageF.getText());
                String cnic = ValidationUtil.validateCNIC(cnicF.getText());
                long phone = Long.parseLong(ValidationUtil.validatePhone(phoneF.getText()));
                String email = ValidationUtil.validateEmail(emailF.getText());
                String address = ValidationUtil.validateAddress(addressF.getText());
                String username = usernameF.getText().trim();
                String password = new String(passF.getPassword()).trim();

                if (username.isEmpty() || password.isEmpty()) {
                    throw new IllegalArgumentException("Username/Password cannot be empty.");
                }

                String gender;
                if (maleRB.isSelected())
                    gender = "Male";
                else if (femaleRB.isSelected())
                    gender = "Female";
                else
                    throw new IllegalArgumentException("Select a gender.");

                // 5. Update the Object
                rec.setName(name);
                rec.setAge(age);
                rec.setCNIC(cnic);
                rec.setGender(gender);
                rec.setPhoneNumber(phone);
                rec.setEmail(email);
                rec.setAddress(address);
                rec.setUserName(username);
                rec.setPassword(password);

                // 6. Save Changes
                // Ensure your manager has this method implemented!
                receptionistManager.updateReceptionist(rec); 
                refreshReceptionistTable(); // Update the GUI table immediately

                JOptionPane.showMessageDialog(frame, "Receptionist updated successfully!");
                validInput = true;

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Age or Phone must be numeric.");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(frame, "Input Error: " + ex.getMessage());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error updating receptionist: " + ex.getMessage());
            }
        }

    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(frame, "Invalid Receptionist ID.");
    }
}

    private void updateDoctor() {
    try {
        String inputId = JOptionPane.showInputDialog(frame, "Enter Doctor ID to update:");
        if (inputId == null) return; // User clicked Cancel

        int id = Integer.parseInt(inputId);

        // 1. Find the Doctor
        // (Ensure your DoctorManager has this method!)
        Doctor doc = doctorManager.getDoctorById(id);
        
        if (doc == null) {
            JOptionPane.showMessageDialog(frame, "Doctor not found.");
            return;
        }

        // 2. Setup Fields (Pre-fill with existing data)
        JTextField idF = new JTextField(String.valueOf(doc.getDocID()));
        idF.setEditable(false); // ID cannot be changed

        JTextField nameF = new JTextField(doc.getName());
        JTextField ageF = new JTextField(String.valueOf(doc.getAge()));
        JTextField cnicF = new JTextField(doc.getCNIC());
        JTextField phoneF = new JTextField(String.valueOf(doc.getPhoneNumber()));
        JTextField emailF = new JTextField(doc.getEmail());
        JTextField addressF = new JTextField(doc.getAddress());
        JTextField usernameF = new JTextField(doc.getUserName());
        JPasswordField passF = new JPasswordField(doc.getPassword()); 
        JTextField expF = new JTextField(String.valueOf(doc.getExperience()));
        
        // Time Fields (Convert LocalTime to String)
        JTextField startTimeF = new JTextField(doc.getStartTime().toString());
        JTextField endTimeF = new JTextField(doc.getEndTime().toString());

        // Specialization Dropdown (Set selected item)
        String[] specializations = { "General Physician", "ENT","Orthopedic", "Cardiologist", "Dermatologist", "Neurologist", "Pediatrician" };
        JComboBox<String> specCB = new JComboBox<>(specializations);
        specCB.setSelectedItem(doc.getSpecialization());

        // Gender Radio Buttons
        JRadioButton maleRB = new JRadioButton("Male");
        JRadioButton femaleRB = new JRadioButton("Female");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleRB);
        genderGroup.add(femaleRB);

        if ("Male".equalsIgnoreCase(doc.getGender()))
            maleRB.setSelected(true);
        else if ("Female".equalsIgnoreCase(doc.getGender()))
            femaleRB.setSelected(true);

        // Days Checkboxes (Check existing days)
        JCheckBox monday = new JCheckBox("Mon");
        JCheckBox tuesday = new JCheckBox("Tue");
        JCheckBox wednesday = new JCheckBox("Wed");
        JCheckBox thursday = new JCheckBox("Thu");
        JCheckBox friday = new JCheckBox("Fri");
        JCheckBox saturday = new JCheckBox("Sat");

        Set<java.time.DayOfWeek> currentDays = doc.getWorkingDays();
        if(currentDays.contains(java.time.DayOfWeek.MONDAY)) monday.setSelected(true);
        if(currentDays.contains(java.time.DayOfWeek.TUESDAY)) tuesday.setSelected(true);
        if(currentDays.contains(java.time.DayOfWeek.WEDNESDAY)) wednesday.setSelected(true);
        if(currentDays.contains(java.time.DayOfWeek.THURSDAY)) thursday.setSelected(true);
        if(currentDays.contains(java.time.DayOfWeek.FRIDAY)) friday.setSelected(true);
        if(currentDays.contains(java.time.DayOfWeek.SATURDAY)) saturday.setSelected(true);

        boolean validInput = false;

        // 3. The Update Loop
        while (!validInput) {
            JPanel p = new JPanel(new GridLayout(0, 2, 5, 5));

            p.add(new JLabel("Doc ID:"));
            p.add(idF);
            p.add(new JLabel("Name:"));
            p.add(nameF);
            p.add(new JLabel("Age:"));
            p.add(ageF);
            p.add(new JLabel("CNIC (no dashes):"));
            p.add(cnicF);

            p.add(new JLabel("Gender:"));
            JPanel genderPanel = new JPanel();
            genderPanel.add(maleRB);
            genderPanel.add(femaleRB);
            p.add(genderPanel);

            p.add(new JLabel("Phone:"));
            p.add(phoneF);
            p.add(new JLabel("Username:"));
            p.add(usernameF);
            p.add(new JLabel("Password:"));
            p.add(passF);
            p.add(new JLabel("Email:"));
            p.add(emailF);
            p.add(new JLabel("Address:"));
            p.add(addressF);
            p.add(new JLabel("Experience (Years):"));
            p.add(expF);
            
            p.add(new JLabel("Specialization:"));
            p.add(specCB);
            
            p.add(new JLabel("Start Time (HH:mm):"));
            p.add(startTimeF);
            p.add(new JLabel("End Time (HH:mm):"));
            p.add(endTimeF);

            JPanel daysPanel = new JPanel(new GridLayout(2, 3));
            daysPanel.add(monday);
            daysPanel.add(tuesday);
            daysPanel.add(wednesday);
            daysPanel.add(thursday);
            daysPanel.add(friday);
            daysPanel.add(saturday);
            p.add(new JLabel("Working Days:"));
            p.add(daysPanel);

            p.setPreferredSize(new Dimension(400, 600)); // Taller for more fields

            int res = JOptionPane.showConfirmDialog(
                    frame, p, "Update Doctor",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (res != JOptionPane.OK_OPTION)
                return;

            try {
                // 4. Validate Inputs
                String name = ValidationUtil.validateName(nameF.getText());
                int age = ValidationUtil.validateAge(ageF.getText());
                String cnic = ValidationUtil.validateCNIC(cnicF.getText());
                long phone = Long.parseLong(ValidationUtil.validatePhone(phoneF.getText()));
                String email = ValidationUtil.validateEmail(emailF.getText());
                String address = ValidationUtil.validateAddress(addressF.getText());
                int experience = ValidationUtil.validateExperienceYears(expF.getText());
                
                String username = usernameF.getText().trim();
                String password = new String(passF.getPassword()).trim();
                
                if (username.isEmpty() || password.isEmpty()) {
                    throw new IllegalArgumentException("Username/Password cannot be empty.");
                }

                String gender;
                if (maleRB.isSelected()) gender = "Male";
                else if (femaleRB.isSelected()) gender = "Female";
                else throw new IllegalArgumentException("Select a gender.");

                String specialization = (String) specCB.getSelectedItem();

                // Validate Times
                java.time.LocalTime startTime = java.time.LocalTime.parse(startTimeF.getText().trim());
                java.time.LocalTime endTime = java.time.LocalTime.parse(endTimeF.getText().trim());
                
                if(!endTime.isAfter(startTime)) {
                     throw new IllegalArgumentException("End Time must be after Start Time.");
                }

                // Validate Days
                Set<java.time.DayOfWeek> newWorkingDays = new HashSet<>();
                if (monday.isSelected()) newWorkingDays.add(java.time.DayOfWeek.MONDAY);
                if (tuesday.isSelected()) newWorkingDays.add(java.time.DayOfWeek.TUESDAY);
                if (wednesday.isSelected()) newWorkingDays.add(java.time.DayOfWeek.WEDNESDAY);
                if (thursday.isSelected()) newWorkingDays.add(java.time.DayOfWeek.THURSDAY);
                if (friday.isSelected()) newWorkingDays.add(java.time.DayOfWeek.FRIDAY);
                if (saturday.isSelected()) newWorkingDays.add(java.time.DayOfWeek.SATURDAY);
                
                if (newWorkingDays.isEmpty()) {
                    throw new IllegalArgumentException("Select at least one working day.");
                }

                // 5. Update the Object
                doc.setName(name);
                doc.setAge(age);
                doc.setCNIC(cnic);
                doc.setGender(gender);
                doc.setPhoneNumber(phone);
                doc.setUserName(username);
                doc.setPassword(password);
                doc.setEmail(email);
                doc.setAddress(address);
                doc.setExperience(experience);
                doc.setSpecialization(specialization);
                doc.setStartTime(startTime);
                doc.setEndTime(endTime);
                doc.setWorkingDays(newWorkingDays);

                // 6. Save Changes
                doctorManager.updateDoctor(id, doc); // Ensures file is updated
                refreshDoctorTable(); 

                JOptionPane.showMessageDialog(frame, "Doctor updated successfully!");
                validInput = true;

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Age, Phone, or Experience must be numeric.");
            } catch (java.time.format.DateTimeParseException ex) {
                JOptionPane.showMessageDialog(frame, "Time must be in HH:mm format (e.g., 09:30).");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(frame, "Input Error: " + ex.getMessage());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error updating doctor: " + ex.getMessage());
            }
        }

    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(frame, "Invalid Doctor ID.");
    }
}

    private void refreshReceptionistTable() {
        ArrayList<Receptionist> recs = receptionistManager.getReceptionists();
        String[] cols = { "Username", "RecID", "Email", "Phone" };
        DefaultTableModel model = new DefaultTableModel(cols, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // make cells non-editable
            }
        };
        if (recs != null) {
            for (Receptionist r : recs) {
                model.addRow(new Object[] { r.getUserName(), r.getReceptionistId(), r.getEmail(), r.getPhoneNumber() });
            }
        }
        receptionistTable.setModel(model);
        receptionistTable.getTableHeader().setBackground(new Color(52, 152, 219));
        receptionistTable.getTableHeader().setForeground(Color.WHITE);
        receptionistTable.getTableHeader().setFont(new Font("Century", Font.BOLD, 16));
        receptionistTable.setFont(new Font("Century", Font.PLAIN, 14)); // change font style and size
        receptionistTable.setRowHeight(25); // optional, to make table more readable

    }
    



    private void showAddDoctorDialog() {
    // Fields
    JTextField nameF = new JTextField();
    JTextField ageF = new JTextField();
    JTextField cnicF = new JTextField();
    JTextField phoneF = new JTextField();
    JTextField usernameF = new JTextField();
    JPasswordField passF = new JPasswordField();
    JTextField emailF = new JTextField();
    JTextField addressF = new JTextField();
    JTextField docIdF = new JTextField();
    JTextField expF = new JTextField();

    String[] specializations = { "General Physician", "ENT","Orthopedic", "Cardiologist", "Dermatologist", "Neurologist", "Pediatrician" };
    JComboBox<String> specCB = new JComboBox<>(specializations);

    JTextField startTimeF = new JTextField("08:30");
    JTextField endTimeF = new JTextField("14:30");

    // Gender radio buttons
    JRadioButton maleRB = new JRadioButton("Male");
    JRadioButton femaleRB = new JRadioButton("Female");
    ButtonGroup genderGroup = new ButtonGroup();
    genderGroup.add(maleRB);
    genderGroup.add(femaleRB);

    // Days checkboxes
    JCheckBox monday = new JCheckBox("Mon");
    JCheckBox tuesday = new JCheckBox("Tue");
    JCheckBox wednesday = new JCheckBox("Wed");
    JCheckBox thursday = new JCheckBox("Thu");
    JCheckBox friday = new JCheckBox("Fri");
    JCheckBox saturday = new JCheckBox("Sat");

    boolean validInput = false;

    while (!validInput) {
        JPanel p = new JPanel(new GridLayout(0, 2, 5, 5));
        p.add(new JLabel("Name:"));
        p.add(nameF);
        p.add(new JLabel("Age:"));
        p.add(ageF);
        p.add(new JLabel("CNIC(without dashes):"));
        p.add(cnicF);

        p.add(new JLabel("Gender:"));
        JPanel genderPanel = new JPanel();
        genderPanel.add(maleRB);
        genderPanel.add(femaleRB);
        p.add(genderPanel);

        p.add(new JLabel("Phone:"));
        p.add(phoneF);
        p.add(new JLabel("Username:"));
        p.add(usernameF);
        p.add(new JLabel("Password:"));
        p.add(passF);
        p.add(new JLabel("Email:"));
        p.add(emailF);
        p.add(new JLabel("Address:"));
        p.add(addressF);
        p.add(new JLabel("Doc ID:"));
        p.add(docIdF);
        p.add(new JLabel("Experience:"));
        p.add(expF);
        p.add(new JLabel("Degree:"));
        p.add(new JLabel("MBBS"));
        p.add(new JLabel("Specialization:"));
        p.add(specCB);
        p.add(new JLabel("Start Time (HH:mm):"));
        p.add(startTimeF);
        p.add(new JLabel("End Time (HH:mm):"));
        p.add(endTimeF);

        JPanel daysPanel = new JPanel(new GridLayout(2, 3));
        daysPanel.add(monday);
        daysPanel.add(tuesday);
        daysPanel.add(wednesday);
        daysPanel.add(thursday);
        daysPanel.add(friday);
        daysPanel.add(saturday);
        p.add(new JLabel("Working Days:"));
        p.add(daysPanel);

        p.setPreferredSize(new Dimension(400, 550));

        int res = JOptionPane.showConfirmDialog(
                frame, p, "Add Doctor",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );
        if (res != JOptionPane.OK_OPTION)
            return;

        boolean errorOccurred = false;

        try {
            String name = ValidationUtil.validateName(nameF.getText());
            int age = ValidationUtil.validateAge(ageF.getText());
            String cnic = ValidationUtil.validateCNIC(cnicF.getText());
            long phone = Long.parseLong(ValidationUtil.validatePhone(phoneF.getText()));
            String email = ValidationUtil.validateEmail(emailF.getText());
            String address = ValidationUtil.validateAddress(addressF.getText());
            int docId = ValidationUtil.validateID(docIdF.getText());
            int experience = ValidationUtil.validateExperienceYears(expF.getText());

            String gender = null;
            if (maleRB.isSelected())
                gender = "Male";
            else if (femaleRB.isSelected())
                gender = "Female";
            else
                throw new IllegalArgumentException("Select a gender.");

            String username = usernameF.getText().trim();
            String password = new String(passF.getPassword()).trim();

            String specialization = (String) specCB.getSelectedItem();

            java.time.LocalTime startTime = java.time.LocalTime.parse(startTimeF.getText().trim());
            java.time.LocalTime endTime = java.time.LocalTime.parse(endTimeF.getText().trim());

            Set<java.time.DayOfWeek> workingDays = new HashSet<>();
            if (monday.isSelected())
                workingDays.add(java.time.DayOfWeek.MONDAY);
            if (tuesday.isSelected())
                workingDays.add(java.time.DayOfWeek.TUESDAY);
            if (wednesday.isSelected())
                workingDays.add(java.time.DayOfWeek.WEDNESDAY);
            if (thursday.isSelected())
                workingDays.add(java.time.DayOfWeek.THURSDAY);
            if (friday.isSelected())
                workingDays.add(java.time.DayOfWeek.FRIDAY);
            if (saturday.isSelected())
                workingDays.add(java.time.DayOfWeek.SATURDAY);
            if (workingDays.isEmpty())
                throw new IllegalArgumentException("Select at least one working day");

            Doctor newDoc = new Doctor(
                    name, age, cnic, gender, phone, username, password,
                    email, address, docId, experience,
                    specialization, startTime, endTime, workingDays
            );

            doctorManager.addDoctorWithCheck(newDoc);
            refreshDoctorTable();
            JOptionPane.showMessageDialog(frame, "Doctor added successfully!");
            validInput = true;

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Doc ID, Age, Phone, or Experience must be numeric.");
      
            errorOccurred = true;
        } catch (java.time.format.DateTimeParseException ex) {
            JOptionPane.showMessageDialog(frame, "Start/End time must be in HH:mm format.");
            startTimeF.setText("");
            endTimeF.setText("");
            errorOccurred = true;
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(frame, "Input Error: " + ex.getMessage());
            errorOccurred = true;
        } catch (UserAlreadyExistsException ex) {
            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
            errorOccurred = true;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error adding doctor: " + ex.getMessage());
            errorOccurred = true;
        }

        if (!errorOccurred)
            validInput = true;
    }
}



    private void removeSelectedDoctor() {
        int row = doctorTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(frame, "Select a doctor row first.");
            return;
        }
        int docId = (int) doctorTable.getModel().getValueAt(row, 1);
        int confirm = JOptionPane.showConfirmDialog(frame, "Delete doctor with ID " + docId + " ?", "Confirm",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            doctorManager.deleteDoctor(docId);
            refreshDoctorTable();
        }
    }

    private void showAddReceptionistDialog() {
        JTextField nameF = new JTextField();
        JTextField ageF = new JTextField();
        JTextField cnicF = new JTextField();
        JTextField phoneF = new JTextField();
        JTextField usernameF = new JTextField();
        JPasswordField passF = new JPasswordField();
        JTextField emailF = new JTextField();
        JTextField addressF = new JTextField();
        JTextField recIdF = new JTextField();

        // Gender radio buttons
        JRadioButton maleRB = new JRadioButton("Male");
        JRadioButton femaleRB = new JRadioButton("Female");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleRB);
        genderGroup.add(femaleRB);

        boolean validInput = false;

        while (!validInput) {
            JPanel p = new JPanel(new GridLayout(0, 2, 5, 5));
            p.add(new JLabel("Name:"));
            p.add(nameF);
            p.add(new JLabel("Age:"));
            p.add(ageF);
            p.add(new JLabel("CNIC(without dashes):"));
            p.add(cnicF);

            p.add(new JLabel("Gender:"));
            JPanel genderPanel = new JPanel();
            genderPanel.add(maleRB);
            genderPanel.add(femaleRB);
            p.add(genderPanel);

            p.add(new JLabel("Phone:"));
            p.add(phoneF);
            p.add(new JLabel("Username:"));
            p.add(usernameF);
            p.add(new JLabel("Password:"));
            p.add(passF);
            p.add(new JLabel("Email:"));
            p.add(emailF);
            p.add(new JLabel("Address:"));
            p.add(addressF);
            p.add(new JLabel("Receptionist ID:"));
            p.add(recIdF);

            p.setPreferredSize(new Dimension(400, 400));

            int res = JOptionPane.showConfirmDialog(frame, p, "Add Receptionist", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);
            if (res != JOptionPane.OK_OPTION)
                return;

            boolean errorOccurred = false;

            try {
                String name = ValidationUtil.validateName(nameF.getText());
                int age = ValidationUtil.validateAge(ageF.getText());
                String cnic = ValidationUtil.validateCNIC(cnicF.getText());
                long phone = Long.parseLong(ValidationUtil.validatePhone(phoneF.getText()));
                String email = ValidationUtil.validateEmail(emailF.getText());
                String address = ValidationUtil.validateAddress(addressF.getText());
                int recId = ValidationUtil.validateID(recIdF.getText());

                String gender = null;
                if (maleRB.isSelected())
                    gender = "Male";
                else if (femaleRB.isSelected())
                    gender = "Female";
                else
                    throw new IllegalArgumentException("Select a gender.");

                String username = usernameF.getText().trim();
                String password = new String(passF.getPassword()).trim();

                Receptionist r = new Receptionist(name, age, cnic, gender, phone, username, password, email, address,
                        recId);

                receptionistManager.addReceptionist(r);
                refreshReceptionistTable();
                JOptionPane.showMessageDialog(frame, "Receptionist added successfully!");
                validInput = true;

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "ID, Age, or Phone must be numeric.");
               
                errorOccurred = true;
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(frame, "Input Error: " + ex.getMessage());
                errorOccurred = true;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error adding receptionist: " + ex.getMessage());
                errorOccurred = true;
            }

            if (!errorOccurred)
                validInput = true;
        }
    }

    private void removeSelectedReceptionist() {
        int row = receptionistTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(frame, "Select a receptionist row first.");
            return;
        }
        int recId = (int) receptionistTable.getModel().getValueAt(row, 1);
        int confirm = JOptionPane.showConfirmDialog(frame, "Delete receptionist with ID " + recId + " ?", "Confirm",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            receptionistManager.deleteReceptionist(recId);
            refreshReceptionistTable();
        }
    }
    // =================== Button UI Helpers =======================

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

}
