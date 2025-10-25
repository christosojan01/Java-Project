package src;
import javax.swing.*;

import java.awt.*;

import java.sql.*;

public class LoginSignup extends JFrame {
    private JTextField usernameField, emailField;
    private JPasswordField passwordField;
    private JButton loginBtn, signupBtn, switchBtn;
    private JComboBox<String> roleBox;
    private boolean isLoginMode = true;
    

    public LoginSignup() {
    setTitle("Turf Booking - Login / Signup");
    setSize(420, 420);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.fill = GridBagConstraints.HORIZONTAL;
    panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

    panel.setBackground(new Color(34, 40, 49)); // deep grey background

    JLabel title = new JLabel("Turf Booking System", SwingConstants.CENTER);
    title.setFont(new Font("Segoe UI", Font.BOLD, 22));
    title.setForeground(new Color(0, 173, 181)); // teal accent

    JLabel usernameLabel = new JLabel("Username:");
    JLabel emailLabel = new JLabel("Email:");
    JLabel passwordLabel = new JLabel("Password:");
    JLabel roleLabel = new JLabel("Role:");

    usernameLabel.setForeground(Color.WHITE);
    emailLabel.setForeground(Color.WHITE);
    passwordLabel.setForeground(Color.WHITE);
    roleLabel.setForeground(Color.WHITE);

    usernameField = new JTextField();
    emailField = new JTextField();
    passwordField = new JPasswordField();
    roleBox = new JComboBox<>(new String[] {"User", "Turf Owner"});

    loginBtn = new JButton("Login");
    signupBtn = new JButton("Sign Up");
    switchBtn = new JButton("Switch to Signup");

    Color accent = new Color(0, 173, 181);
    Color dark = new Color(57, 62, 70);

    JButton[] buttons = {loginBtn, signupBtn, switchBtn};
    for (JButton b : buttons) {
        b.setBackground(accent);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createLineBorder(dark, 2));
        b.setFont(new Font("Segoe UI", Font.BOLD, 14));
    }

    gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; panel.add(title, gbc);
    gbc.gridwidth = 1;

    gbc.gridx = 0; gbc.gridy = 1; panel.add(usernameLabel, gbc);
    gbc.gridx = 1; gbc.gridy = 1; panel.add(usernameField, gbc);

    gbc.gridx = 0; gbc.gridy = 2; panel.add(emailLabel, gbc);
    gbc.gridx = 1; gbc.gridy = 2; panel.add(emailField, gbc);

    gbc.gridx = 0; gbc.gridy = 3; panel.add(passwordLabel, gbc);
    gbc.gridx = 1; gbc.gridy = 3; panel.add(passwordField, gbc);

    gbc.gridx = 0; gbc.gridy = 4; panel.add(roleLabel, gbc);
    gbc.gridx = 1; gbc.gridy = 4; panel.add(roleBox, gbc);

    gbc.gridx = 0; gbc.gridy = 5; panel.add(loginBtn, gbc);
    gbc.gridx = 1; gbc.gridy = 5; panel.add(signupBtn, gbc);

    gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2; panel.add(switchBtn, gbc);

    add(panel);

    emailLabel.setVisible(false);
    emailField.setVisible(false);
    signupBtn.setVisible(false);
    roleBox.setVisible(false);
    roleLabel.setVisible(false);

    switchBtn.addActionListener(e -> {
        isLoginMode = !isLoginMode;
        emailLabel.setVisible(!isLoginMode);
        emailField.setVisible(!isLoginMode);
        roleBox.setVisible(!isLoginMode);
        roleLabel.setVisible(!isLoginMode);
        loginBtn.setVisible(isLoginMode);
        signupBtn.setVisible(!isLoginMode);
        switchBtn.setText(isLoginMode ? "Switch to Signup" : "Switch to Login");
    });

    loginBtn.addActionListener(e -> loginUser());
    signupBtn.addActionListener(e -> signupUser());

    setVisible(true);
}




    private void toggleMode(JLabel emailLabel, JTextField emailField) 
    {
        isLoginMode = !isLoginMode;
        emailLabel.setVisible(!isLoginMode);
        emailField.setVisible(!isLoginMode);
        roleBox.setVisible(!isLoginMode);
        loginBtn.setVisible(isLoginMode);
        signupBtn.setVisible(!isLoginMode);
        switchBtn.setText(isLoginMode ? "Switch to Signup" : "Switch to Login");
    }


    private void loginUser() {
    String username = usernameField.getText();
    String password = new String(passwordField.getPassword());

    if (username.isEmpty() || password.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter both username and password!");
        return;
    }

    try (Connection conn = DBConnection.getConnection()) {
        String query = "SELECT * FROM users WHERE username=? AND password=?";
        PreparedStatement pst = conn.prepareStatement(query);
        pst.setString(1, username);
        pst.setString(2, password);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            JOptionPane.showMessageDialog(this, "Login Successful! Welcome " + username);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials!");
        }
    } catch (Exception ex) {
        ex.printStackTrace();
    }
}



   private void signupUser() {
    String username = usernameField.getText();
    String email = emailField.getText();
    String password = new String(passwordField.getPassword());
    String role = (String) roleBox.getSelectedItem();

    if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
        JOptionPane.showMessageDialog(this, "All fields (Username, Email, Password) are required!");
        return;
    }

    try (Connection conn = DBConnection.getConnection()) {
        String query = "INSERT INTO users(username, email, password, role) VALUES (?, ?, ?, ?)";
        PreparedStatement pst = conn.prepareStatement(query);
        pst.setString(1, username);
        pst.setString(2, email);
        pst.setString(3, password);
        pst.setString(4, role);
        pst.executeUpdate();

        JOptionPane.showMessageDialog(this, "Signup Successful! You can now login.");
        toggleMode(new JLabel(), new JTextField());
    } catch (SQLIntegrityConstraintViolationException e) {
        JOptionPane.showMessageDialog(this, "Username already exists!");
    } catch (Exception ex) {
        ex.printStackTrace();
    }
}



    public static void main(String[] args) {
        new LoginSignup();
    }
}
