package GUI;

import javax.swing.*;
import java.awt.*;

import managerClasses.*;
import models.*;

public class MainLoginGui {

    private JFrame frame;

    // Managers (passed from main)
    private AdminManager adminManager;
    private DoctorManager doctorManager;
    private ReceptionistManager receptionistManager;
    private PatientManager patientManager;
    private AppointmentManager appointmentManager;

    // ---------------- CONSTRUCTOR ----------------
    public MainLoginGui(AdminManager adminManager,
            DoctorManager doctorManager,
            ReceptionistManager receptionistManager,
            PatientManager patientManager,
            AppointmentManager appointmentManager) {

        this.adminManager = adminManager;
        this.doctorManager = doctorManager;
        this.receptionistManager = receptionistManager;
        this.patientManager = patientManager;
        this.appointmentManager = appointmentManager;
        showRoleSelectionScreen();
    }

    // ---------------- ROLE SELECTION SCREEN ----------------

    private void showRoleSelectionScreen() {
    // 1. Initialize frame only if it doesn't exist
    if (frame == null) {
        frame = new JFrame("Smart Clinic Management - Select Role");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    } else {
        // 2. If returning (Back button), clear the previous login form
        frame.getContentPane().removeAll();
        frame.repaint();
    }

    // 3. Reset Layout (Important because Login screen uses BorderLayout)
    frame.setLayout(new GridLayout(4, 1, 10, 10));

    JLabel title = new JLabel("Select Your Role", SwingConstants.CENTER);
    title.setFont(new Font("Century", Font.BOLD, 20));

    // Define the custom GradientButton class locally (as in your original code)
    class GradientButton extends JButton {
        private Color color1;
        private Color color2;

        public GradientButton(String text, Color c1, Color c2) {
            super(text);
            this.color1 = c1;
            this.color2 = c2;
            setContentAreaFilled(false);
            setForeground(Color.WHITE);
            setFocusPainted(false);
            setFont(new Font("Century", Font.BOLD, 16));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            GradientPaint gp = new GradientPaint(
                    0, 0, color1,
                    0, getHeight(), color2);

            g2.setPaint(gp);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);

            super.paintComponent(g);
        }
    }

    // Create the buttons
    JButton adminBtn = new GradientButton("Admin",
            new Color(52, 152, 219), // Light Blue
            new Color(41, 128, 185)); // Darker Blue

    JButton doctorBtn = new GradientButton("Doctor",
            new Color(52, 152, 219),
            new Color(41, 128, 185));

    JButton recBtn = new GradientButton("Receptionist",
            new Color(52, 152, 219),
            new Color(41, 128, 185));

    adminBtn.setFont(new Font("Century", Font.BOLD, 18));
    doctorBtn.setFont(new Font("Century", Font.BOLD, 18));
    recBtn.setFont(new Font("Century", Font.BOLD, 18));

    // Add Action Listeners
    adminBtn.addActionListener(e -> showLoginScreen("Admin"));
    doctorBtn.addActionListener(e -> showLoginScreen("Doctor"));
    recBtn.addActionListener(e -> showLoginScreen("Receptionist"));

    // 4. Add components back to frame
    frame.add(title);
    frame.add(adminBtn);
    frame.add(doctorBtn);
    frame.add(recBtn);

    // 5. Refresh the frame to show changes
    frame.revalidate();
    frame.setVisible(true);
}

    // ---------------- LOGIN SCREEN ----------------

private void showLoginScreen(String role) {
    // Clear the frame to remove the role selection buttons
    frame.getContentPane().removeAll();
    frame.repaint();
    frame.setLayout(new BorderLayout());

    // --- 1. TITLE PANEL ---
    JLabel title = new JLabel(role + " Login", SwingConstants.CENTER);
    title.setFont(new Font("Century", Font.BOLD, 18));
    JPanel titlePanel = new JPanel(new BorderLayout());
    titlePanel.add(title, BorderLayout.CENTER);
    titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

    // --- 2. CENTER PANEL (FORM) ---
    JPanel centerPanel = new JPanel();
    centerPanel.setLayout(new GridBagLayout());
    centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(8, 8, 8, 8);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    // Username Label & Field
    JLabel usernameLabel = new JLabel("Username:");
    usernameLabel.setFont(new Font("Century", Font.PLAIN, 15));
    JTextField usernameField = new JTextField(15);
    usernameField.setFont(new Font("Century", Font.PLAIN, 14));

    // Password Label & Field
    JLabel passwordLabel = new JLabel("Password:");
    passwordLabel.setFont(new Font("Century", Font.PLAIN, 15));
    JPasswordField passwordField = new JPasswordField(15);
    passwordField.setFont(new Font("Century", Font.PLAIN, 14));

    // Add to GridBagLayout
    gbc.gridx = 0;
    gbc.gridy = 0;
    centerPanel.add(usernameLabel, gbc);
    gbc.gridx = 1;
    centerPanel.add(usernameField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 1;
    centerPanel.add(passwordLabel, gbc);
    gbc.gridx = 1;
    centerPanel.add(passwordField, gbc);

    // --- 3. BOTTOM PANEL (BUTTONS) ---
    JPanel southPanel = new JPanel();
    // Use FlowLayout to place Back and Login buttons side-by-side
    southPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10)); 
    southPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));

 
   

    // B. Login Button (Your original gradient style)
    JButton loginBtn = new JButton("Login") {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            GradientPaint gp = new GradientPaint(
                    0, 0, new Color(52, 152, 219),
                    0, getHeight(), new Color(41, 128, 185)
            );
            g2.setPaint(gp);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
            super.paintComponent(g);
        }
    };
    JButton backBtn = new JButton("Back") {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            GradientPaint gp = new GradientPaint(
                    0, 0, new Color(52, 152, 219),
                    0, getHeight(), new Color(41, 128, 185)
            );
            g2.setPaint(gp);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
            super.paintComponent(g);
        }
    };
    loginBtn.setForeground(Color.WHITE);
    loginBtn.setFont(new Font("Century", Font.BOLD, 16));
    loginBtn.setBorderPainted(false);
    loginBtn.setFocusPainted(false);
    loginBtn.setContentAreaFilled(false);
    loginBtn.setPreferredSize(new Dimension(120, 35));
    backBtn.setForeground(Color.WHITE);
    backBtn.setFont(new Font("Century", Font.BOLD, 16));
    backBtn.setBorderPainted(false);
    backBtn.setFocusPainted(false);
    backBtn.setContentAreaFilled(false);
    backBtn.setPreferredSize(new Dimension(120, 35));
     backBtn.addActionListener(e -> {
        showRoleSelectionScreen(); 
    });

    loginBtn.addActionListener(e -> {
        String user = usernameField.getText();
        String pass = new String(passwordField.getPassword());
        handleLogin(role, user, pass);
    });

    // Add buttons to South Panel
    southPanel.add(backBtn);
    southPanel.add(loginBtn);

    // --- 4. ASSEMBLE FRAME ---
    frame.add(titlePanel, BorderLayout.NORTH);
    frame.add(centerPanel, BorderLayout.CENTER);
    frame.add(southPanel, BorderLayout.SOUTH);

    frame.pack(); // compact size
    frame.setLocationRelativeTo(null); // center on screen
    frame.revalidate(); // Refresh layout
    frame.repaint();
}


    // ---------------- HANDLE LOGIN ----------------
    private void handleLogin(String role, String username, String password) {

        boolean success = false;

        switch (role) {
            case "Admin":
                success = adminManager.login(username, password);
                break;
            case "Doctor":
                success = doctorManager.login(username, password);
                break;
            case "Receptionist":
                success = receptionistManager.login(username, password);
                break;
        }

        // IN MainLoginGui.java -> inside handleLogin() method

if (success) {
    JOptionPane.showMessageDialog(frame, "Login Successful!");
    frame.dispose();
    
    switch (role) {
        case "Admin":
            // PASS ALL MANAGERS
            new AdminDashboard(adminManager, doctorManager, receptionistManager, patientManager, appointmentManager);
            break;
            
        case "Doctor":
            Doctor loggedDoc = doctorManager.getDoctorByUsername(username);
            // PASS ALL MANAGERS
            new DoctorDashboard(doctorManager, loggedDoc, appointmentManager, 
                                adminManager, receptionistManager, patientManager);
            break;
            
        case "Receptionist":
            Receptionist loggedRec = receptionistManager.findReceptionistByUsername(username);
            // PASS ALL MANAGERS
            new ReceptionistDashboard(loggedRec, patientManager, appointmentManager, doctorManager, 
                                      adminManager, receptionistManager);
            break;
    }
}else {
            JOptionPane.showMessageDialog(frame, "Invalid Username or Password!");
        }
    }


}