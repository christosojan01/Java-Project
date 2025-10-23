
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class bookingConfirmationPage extends JFrame {

    
    private final Color primaryBlue = new Color(0x123499);
    private final Color darkText = new Color(50, 50, 50);
    private final Color mainWhite = Color.WHITE;
    private final Color successGreen = new Color(46, 139, 87);

   
    public bookingConfirmationPage(int userId, String turfName, String date, String timeSlot) {
        super("Booking Confirmed!");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 350); 
        setLocationRelativeTo(null); 

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(mainWhite);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

       
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(mainWhite);
        
        JLabel titleLabel = new JLabel("BOOKING SUCCESS!", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(successGreen);

       
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); 

       
        JPanel detailPanel = new JPanel(new GridLayout(4, 2, 10, 10)); 
        detailPanel.setBackground(mainWhite);
        detailPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10)); 

       
        long confirmationNumber = System.currentTimeMillis() % 10000;

       
        addDetailRow(detailPanel, "Confirmation ID:", "#" + confirmationNumber, darkText);
        addDetailRow(detailPanel, "Turf:", turfName, primaryBlue);
        addDetailRow(detailPanel, "Date & Time:", date + " @ " + timeSlot, darkText);
        addDetailRow(detailPanel, "Logged in User ID:", String.valueOf(userId), darkText);

        
        JButton okButton = new JButton("OK / View Bookings");
        okButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        okButton.setBackground(primaryBlue);
        okButton.setForeground(mainWhite);
        okButton.setFocusPainted(false);
        okButton.setPreferredSize(new Dimension(250, 40));
        
        okButton.addActionListener(e -> {
            this.dispose(); 
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(mainWhite);
        buttonPanel.add(okButton);
        
      
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(detailPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }
    
    private void addDetailRow(JPanel parent, String label, String value, Color valueColor) {
        JLabel labelComp = new JLabel(label);
        labelComp.setFont(new Font("Segoe UI", Font.BOLD, 14));
        labelComp.setForeground(darkText);
        parent.add(labelComp);
        
        JLabel valueComp = new JLabel(value);
        valueComp.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        valueComp.setForeground(valueColor);
        parent.add(valueComp);
    }

    public static void main(String[] args) {
      
        javax.swing.SwingUtilities.invokeLater(() -> {
            new bookingConfirmationPage(10, "Green Fields Turf", "2025-10-10", "09:00 - 10:00 (9 AM)");
        });
    }
}
