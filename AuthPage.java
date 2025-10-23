import java.awt.CardLayout; 
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class AuthPage extends JFrame {

    private JPanel cardPanel; 
    private CardLayout cardLayout = new CardLayout();
    
    private JTextField loginUsernameField;
    private JPasswordField loginPasswordField;
    
    private JTextField regNameField; 
    private JTextField regEmailField;
    private JTextField regUsernameField;
    private JPasswordField regPasswordField;

    private final Color mainWhite = Color.WHITE;
    private final Color primaryBlue = new Color(0x123499); 
    private final Color linkColor = new Color(50, 50, 50);   
    private final Color darkText = new Color(50, 50, 50);     
    private final Color lightGrayBackground = new Color(245, 245, 245); 

    public AuthPage() {
        super("TurfZone | Access");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 600); 
        setLocationRelativeTo(null); 
        
        JPanel mainContainer = new JPanel(new GridBagLayout());
        mainContainer.setBackground(lightGrayBackground);
        
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(mainWhite);
        
        JPanel loginPanel = createLoginPagePanel();
        JPanel registerPanel = createRegistrationPagePanel();
        
        cardPanel.add(loginPanel, "Login");
        cardPanel.add(registerPanel, "Register");
        
        mainContainer.add(cardPanel);
        
        add(mainContainer);
        setVisible(true);
    }
    
    private JPanel createLoginPagePanel() {
        JPanel formPanel = new JPanel(new GridBagLayout()); formPanel.setBackground(mainWhite); formPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        GridBagConstraints gbc = getBaseGBC();
        
        JLabel appLogoLabel = new JLabel("TurfZone"); appLogoLabel.setFont(new Font("Segoe UI", Font.BOLD, 36)); appLogoLabel.setForeground(primaryBlue); gbc.gridy = 0; gbc.insets = new Insets(0,0,5,0); formPanel.add(appLogoLabel, gbc);
        JLabel subTitleLabel = new JLabel("Have Fun with Friends!"); subTitleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18)); subTitleLabel.setForeground(darkText); gbc.gridy = 1; gbc.insets = new Insets(0, 0, 30, 0); formPanel.add(subTitleLabel, gbc);

        loginUsernameField = createStyledField("  Username"); gbc.gridy = 2; gbc.insets = new Insets(10, 0, 10, 0); formPanel.add(loginUsernameField, gbc);
        loginPasswordField = createStyledPasswordField("  Password"); gbc.gridy = 3; gbc.insets = new Insets(10, 0, 10, 0); formPanel.add(loginPasswordField, gbc);

        JLabel forgotPasswordLabel = createLabel("Forgot Password?", primaryBlue); forgotPasswordLabel.setCursor(new Cursor(Cursor.HAND_CURSOR)); gbc.gridy = 4; gbc.anchor = GridBagConstraints.WEST; gbc.insets = new Insets(5, 0, 20, 0); formPanel.add(forgotPasswordLabel, gbc);
        
        JButton signInButton = new JButton("Login"); styleButton(signInButton, primaryBlue, mainWhite); signInButton.setPreferredSize(new Dimension(300, 50)); gbc.gridy = 5; gbc.insets = new Insets(10, 0, 10, 0); gbc.anchor = GridBagConstraints.CENTER; formPanel.add(signInButton, gbc); signInButton.addActionListener(e -> handleSignIn());
        
        JPanel signUpPanel = createLinkPanel("Didn't Have Account? ", "Sign Up", darkText, primaryBlue);
        signUpPanel.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) { cardLayout.show(cardPanel, "Register"); } });
        gbc.gridy = 6; gbc.insets = new Insets(10, 0, 10, 0); formPanel.add(signUpPanel, gbc);
        
        return formPanel;
    }

    private JPanel createRegistrationPagePanel() {
        JPanel formPanel = new JPanel(new GridBagLayout()); formPanel.setBackground(mainWhite); formPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40)); 
        GridBagConstraints gbc = getBaseGBC();

        JLabel titleLabel = new JLabel("Sign - Up"); titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 30)); titleLabel.setForeground(primaryBlue); gbc.gridy = 0; gbc.insets = new Insets(0, 0, 30, 0); formPanel.add(titleLabel, gbc);

        regUsernameField = createStyledField("  Username"); gbc.gridy = 1; gbc.insets = new Insets(10, 0, 10, 0); formPanel.add(regUsernameField, gbc);
        regEmailField = createStyledField("  Email Id"); gbc.gridy = 2; gbc.insets = new Insets(10, 0, 10, 0); formPanel.add(regEmailField, gbc);
        
        JTextField contactField = createStyledField("  Contact Number"); gbc.gridy = 3; gbc.insets = new Insets(10, 0, 10, 0); formPanel.add(contactField, gbc);
        
        regPasswordField = createStyledPasswordField("  New Password"); gbc.gridy = 4; gbc.insets = new Insets(10, 0, 10, 0); formPanel.add(regPasswordField, gbc);
        
        JPasswordField confirmPasswordField = createStyledPasswordField("  Confirm Password"); gbc.gridy = 5; gbc.insets = new Insets(10, 0, 30, 0); formPanel.add(confirmPasswordField, gbc);

        JButton signUpButton = new JButton("Sign-Up"); styleButton(signUpButton, primaryBlue, mainWhite); signUpButton.setPreferredSize(new Dimension(300, 50)); gbc.gridy = 6; gbc.insets = new Insets(10, 0, 10, 0); formPanel.add(signUpButton, gbc); signUpButton.addActionListener(e -> handleRegistration());

        JPanel signInPanel = createLinkPanel("Didn't Have Account? ", "Sign In", darkText, primaryBlue);
        signInPanel.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) { cardLayout.show(cardPanel, "Login"); } });
        gbc.gridy = 7; gbc.insets = new Insets(10, 0, 10, 0); formPanel.add(signInPanel, gbc);
        
        return formPanel;
    }
    
    private void handleSignIn() {
        String username = loginUsernameField.getText().trim();
        String password = new String(loginPasswordField.getPassword());
        
        UserDAO userDAO = new UserDAO();
        User authenticatedUser = userDAO.loginUser(username, password);

        if (authenticatedUser != null) {
            SessionManager.setCurrentUser(authenticatedUser); 
            
            JOptionPane.showMessageDialog(this, "Welcome, " + authenticatedUser.getName() + "!", "Login Success", JOptionPane.INFORMATION_MESSAGE);
            
            this.dispose(); 
            new SportSelectionPage().setVisible(true); 
            
        } else {
            JOptionPane.showMessageDialog(this, 
                                          "Incorrect Username or Password.", 
                                          "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handleRegistration() {
        String name = regUsernameField.getText().trim(); 
        String email = regEmailField.getText().trim();
        String username = regUsernameField.getText().trim();
        String password = new String(regPasswordField.getPassword());
        
        if (email.isEmpty() || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username, Email, and Password are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        User newUser = new User(name, username, password, email, "N/A"); 
        
        UserDAO userDAO = new UserDAO();
        boolean success = userDAO.registerUser(newUser);

        if (success) {
            JOptionPane.showMessageDialog(this, "Registration Successful! You can now sign in.", "Success", JOptionPane.INFORMATION_MESSAGE);
            regNameField.setText(""); regEmailField.setText(""); regUsernameField.setText(""); regPasswordField.setText("");
            cardLayout.show(cardPanel, "Login");
            
        } else {
            JOptionPane.showMessageDialog(this, "Registration Failed! Username or Email may already exist.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private static GridBagConstraints getBaseGBC() { GridBagConstraints gbc = new GridBagConstraints(); gbc.gridx = 0; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL; return gbc; }
    private static JLabel createLabel(String text, Color color) { JLabel label = new JLabel(text); label.setFont(new Font("Segoe UI", Font.PLAIN, 13)); label.setForeground(color); return label; }
    private static JTextField createTextField(Color bg, Color fg) { JTextField field = new JTextField(20); field.setBackground(bg); field.setForeground(fg); field.setCaretColor(fg); field.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY)); return field; }
    private static JPasswordField createPasswordField(Color bg, Color fg) { JPasswordField field = new JPasswordField(20); field.setBackground(bg); field.setForeground(fg); field.setCaretColor(fg); field.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY)); return field; }
    private static void styleButton(JButton button, Color bg, Color fg) { button.setFont(new Font("Segoe UI", Font.BOLD, 14)); button.setBackground(bg); button.setForeground(fg); button.setFocusPainted(false); button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); }
    private JPanel createLinkPanel(String text, String linkText, Color textColor, Color linkColor) { JPanel linkPanel = new JPanel(new GridBagLayout()); linkPanel.setBackground(mainWhite); JLabel textLabel = new JLabel(text); textLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12)); linkPanel.add(textLabel); JLabel linkLabel = new JLabel(linkText); linkLabel.setFont(new Font("Segoe UI", Font.BOLD, 12)); linkLabel.setForeground(linkColor); linkPanel.add(linkLabel); return linkPanel; }

    private JTextField createStyledField(String placeholder) {
        JTextField field = new JTextField(placeholder);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        field.setForeground(new Color(50, 50, 50));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(primaryBlue, 1), 
            BorderFactory.createEmptyBorder(8, 10, 8, 10) 
        ));
        field.putClientProperty("JTextField.placeholderText", placeholder); 
        return field;
    }

    private JPasswordField createStyledPasswordField(String placeholder) {
        JPasswordField field = new JPasswordField(placeholder);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        field.setForeground(new Color(50, 50, 50));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(primaryBlue, 1), 
            BorderFactory.createEmptyBorder(8, 10, 8, 10) 
        ));
        return field;
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(AuthPage::new);
    }
}
