import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class BookingPage extends JFrame {

    private JComboBox<String> turfNameBox; 
    private JComboBox<String> dateBox;
    private JComboBox<String> timeSlotBox;
    private JButton confirmButton;
    private JButton backButton;

    private final Color mainWhite = Color.WHITE;
    private final Color primaryBlue = new Color(0x123499); 
    private final Color darkText = new Color(50, 50, 50);

    public BookingPage() {
        super("Book Slot");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(450, 500); 
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(245, 245, 245));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(mainWhite); 
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40)); 
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 0, 8, 0); 
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.gridwidth = 2; 

        JLabel titleLabel = new JLabel("Confirm Your Booking");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26)); 
        titleLabel.setForeground(primaryBlue);
        gbc.gridy = 0; gbc.insets = new Insets(0,0,10,0);
        formPanel.add(titleLabel, gbc);

        JLabel subTitleLabel = new JLabel("Please select your turf, date, and time.");
        subTitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subTitleLabel.setForeground(darkText);
        gbc.gridy = 1; gbc.insets = new Insets(0,0,25,0);
        formPanel.add(subTitleLabel, gbc);
        
        gbc.gridy = 2; gbc.insets = new Insets(0,0,5,0); formPanel.add(createLabel("Select Turf Name", darkText), gbc);
        
        String[] turfs = {
            "Green Fields Turf", "City Sports Arena", "Turf Central", 
            "Star Turf Club", "The Pitch", "Rooftop Field", "Corner Kick Turf"
        };
        turfNameBox = createDropdown(turfs, mainWhite, darkText);
        gbc.gridy = 3; gbc.insets = new Insets(0,0,15,0); formPanel.add(turfNameBox, gbc);
        
        gbc.gridy = 4; gbc.insets = new Insets(0,0,5,0); formPanel.add(createLabel("Select Booking Date", darkText), gbc);
        String[] dates = {"2025-10-08 (Today)", "2025-10-09", "2025-10-10", "2025-10-11"};
        dateBox = createDropdown(dates, mainWhite, darkText);
        gbc.gridy = 5; gbc.insets = new Insets(0,0,15,0); formPanel.add(dateBox, gbc);

        gbc.gridy = 6; gbc.insets = new Insets(0,0,5,0); formPanel.add(createLabel("Select Time Slot", darkText), gbc);
        
        String[] slots = {
            "09:00 - 10:00 (9 AM)", "10:00 - 11:00 (10 AM)", "11:00 - 12:00 (11 AM)", 
            "12:00 - 13:00 (12 PM - Noon)", "13:00 - 14:00 (1 PM)", "14:00 - 15:00 (2 PM)",
            "15:00 - 16:00 (3 PM)", "16:00 - 17:00 (4 PM)", "17:00 - 18:00 (5 PM)",
            "18:00 - 19:00 (6 PM)", "19:00 - 20:00 (7 PM)", "20:00 - 21:00 (8 PM)",
            "21:00 - 22:00 (9 PM)", "22:00 - 23:00 (10 PM)", "23:00 - 24:00 (11 PM - Midnight)"
        };
        timeSlotBox = createDropdown(slots, mainWhite, darkText);
        gbc.gridy = 7; gbc.insets = new Insets(0,0,25,0); formPanel.add(timeSlotBox, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setBackground(mainWhite);
        
        backButton = new JButton("Cancel");
        styleButton(backButton, Color.GRAY, mainWhite);
        backButton.addActionListener(e -> this.dispose());
        buttonPanel.add(backButton);
        
        confirmButton = new JButton("Confirm Booking");
        styleButton(confirmButton, primaryBlue, mainWhite);
        confirmButton.addActionListener(e -> handleConfirmBooking());
        buttonPanel.add(confirmButton);
        
        gbc.gridy = 8; gbc.insets = new Insets(10, 0, 0, 0); 
        formPanel.add(buttonPanel, gbc);

        mainPanel.add(formPanel);
        add(mainPanel);
        setVisible(true);
    }
    
    private void handleConfirmBooking() {
        if (!SessionManager.isLoggedIn()) {
            JOptionPane.showMessageDialog(this, "Error: You must be logged in to book a slot.", "Session Required", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int currentUserId = SessionManager.getCurrentUser().getUserId();

        String turfName = (String) turfNameBox.getSelectedItem();
        String date = (String) dateBox.getSelectedItem();
        String timeSlot = (String) timeSlotBox.getSelectedItem();
        
        TurfDAO turfDAO = new TurfDAO();
        int turfId = turfDAO.getTurfIdByName(turfName); 
        
        if (turfId == -1) {
            JOptionPane.showMessageDialog(this, "Error: Could not find turf details in the database.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Booking newBooking = new Booking(currentUserId, turfId, date, timeSlot);
        
        BookingDAO bookingDAO = new BookingDAO();
        boolean success = bookingDAO.saveBooking(newBooking);

        if (success) {
            this.dispose(); 
            
            new BookingConfirmationPage(currentUserId, turfName, date, timeSlot).setVisible(true);
            
        } else {
            JOptionPane.showMessageDialog(this, 
                "Booking failed! Check if your database connection is active.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private static JLabel createLabel(String text, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        label.setForeground(color);
        return label;
    }
    
    private static JComboBox<String> createDropdown(String[] items, Color bg, Color fg) {
        JComboBox<String> box = new JComboBox<>(items);
        box.setBackground(bg);
        box.setForeground(fg);
        box.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        return box;
    }
    
    private static void styleButton(JButton button, Color bg, Color fg) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(bg);
        button.setForeground(fg);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> new BookingPage());
    }
}
