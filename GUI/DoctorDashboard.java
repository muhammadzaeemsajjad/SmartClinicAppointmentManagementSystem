package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import managerClasses.*;
import models.*;
import java.util.ArrayList;

public class DoctorDashboard {

    private JFrame frame;
    private DoctorManager doctorManager;
    private AppointmentManager appointmentManager;
    private Doctor doctor; // logged-in doctor
    private AdminManager adminManager;
    private ReceptionistManager receptionistManager;
    private PatientManager patientManager;

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public DoctorManager getDoctorManager() {
        return doctorManager;
    }

    public void setDoctorManager(DoctorManager doctorManager) {
        this.doctorManager = doctorManager;
    }

    public AppointmentManager getAppointmentManager() {
        return appointmentManager;
    }

    public void setAppointmentManager(AppointmentManager appointmentManager) {
        this.appointmentManager = appointmentManager;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public DoctorDashboard(DoctorManager doctorManager, Doctor doctor, AppointmentManager appointmentManager,
            AdminManager adminManager, ReceptionistManager receptionistManager, PatientManager patientManager) {
        this.doctorManager = doctorManager;
        this.doctor = doctor;
        this.appointmentManager = appointmentManager;

        // Assign the new ones
        this.adminManager = adminManager;
        this.receptionistManager = receptionistManager;
        this.patientManager = patientManager;

        initialize();
    }

    private void initialize() {
        frame = new JFrame("Doctor Dashboard - " + doctor.getUserName());
        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("Doctor Dashboard: " + doctor.getUserName(), SwingConstants.CENTER);
        title.setFont(new Font("Century", Font.BOLD, 24));
        title.setBackground(new Color(52, 152, 219)); // Medical Blue
        title.setOpaque(true);
        title.setForeground(Color.WHITE);
        frame.add(title, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(2, 1, 10, 10));

        // ------------------- PROFILE PANEL -------------------
        JPanel profile = new JPanel(new GridLayout(0, 2, 5, 5));
        profile.setBorder(BorderFactory.createTitledBorder("Profile"));
        profile.add(createProfileLabel("Username:"));
        profile.add(createProfileValue(doctor.getUserName()));

        profile.add(createProfileLabel("Doc ID:"));
        profile.add(createProfileValue(String.valueOf(doctor.getDocID())));

        profile.add(createProfileLabel("Specialization:"));
        profile.add(createProfileValue(doctor.getSpecialization()));

        profile.add(createProfileLabel("Experience:"));
        profile.add(createProfileValue(String.valueOf(doctor.getExperience())));

        profile.add(createProfileLabel("Email:"));
        profile.add(createProfileValue(doctor.getEmail()));

        profile.add(createProfileLabel("Phone:"));
        profile.add(createProfileValue(String.valueOf(doctor.getPhoneNumber())));
        center.add(profile);

        // ------------------- APPOINTMENT TABLE -------------------
        JPanel appt = new JPanel(new BorderLayout());
        appt.setBorder(BorderFactory.createTitledBorder("Appointments"));

        String[] cols = { "ApptID", "PatientID", "DateTime", "Status" };
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        JTable apptTable = new JTable(model);
        apptTable.getTableHeader().setFont(new Font("Century", Font.BOLD, 13));
        apptTable.getTableHeader().setBackground(new Color(52, 152, 219));
        apptTable.getTableHeader().setForeground(Color.WHITE);

        apptTable.setFont(new Font("Century", Font.PLAIN, 13));
        apptTable.setRowHeight(22);

        loadDoctorAppointments(model); // load data

        appt.add(new JScrollPane(apptTable), BorderLayout.CENTER);
        center.add(appt);

        frame.add(center, BorderLayout.CENTER);

        // ------------------- BOTTOM BUTTONS -------------------
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton logoutBtn = createBlueButton("Logout");

     

        logoutBtn.addActionListener(e -> {

            frame.dispose();
            new MainLoginGui(adminManager, doctorManager, receptionistManager, patientManager, appointmentManager);
        });

        // bottom.add(refreshBtn);
        bottom.add(logoutBtn);

        frame.add(bottom, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    // ---------------- LOAD APPOINTMENTS FOR THIS DOCTOR ----------------
    private void loadDoctorAppointments(DefaultTableModel model) {
        ArrayList<Appointment> list = appointmentManager.getAppointmentsByDoctor(doctor.getDocID());

        for (Appointment a : list) {
            model.addRow(new Object[] {
                    a.getAppointmentId(),
                    a.getPatientId(),
                    a.getDateTime(),
                    a.getStatus()
            });
        }
    }

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

    private JLabel createProfileLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Century", Font.BOLD, 14)); // 👈 change font here
        label.setForeground(new Color(52, 73, 94)); // optional color
        return label;
    }

    private JLabel createProfileValue(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Century", Font.PLAIN, 13));
        label.setForeground(Color.DARK_GRAY);
        return label;
    }

}
