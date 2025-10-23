import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane; 
import java.awt.FlowLayout;

public class TurfHubPage extends JFrame {

    private final Color primaryBlue = new Color(0x123499); 
    private final Color darkText = new Color(50, 50, 50);
    private final Color mainWhite = Color.WHITE;
    private final Color lightGrayBackground = new Color(245, 245, 245);
    private final Color defaultButtonColor = new Color(220, 220, 220); 

    private JPanel turfDisplayPanel; 

    public TurfHubPage() {
        this("Random"); 
    }

    public TurfHubPage(String initialCategory) {
        super("Turf Booking App - " + initialCategory);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 700); 
        setLocationRelativeTo(null); 
        
        setLayout(new BorderLayout());

        add(createHubContent(), BorderLayout.CENTER); 
        
        setVisible(true);
        
        displayTurfsForCategory(initialCategory); 
    }
    
    private JScrollPane createHubContent() {
        JPanel contentContainer = new JPanel();
        contentContainer.setLayout(new BoxLayout(contentContainer, BoxLayout.Y_AXIS)); 
        contentContainer.setBackground(lightGrayBackground); 
        
        JPanel categoryButtonsWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15)); 
        categoryButtonsWrapper.setBackground(mainWhite);
        categoryButtonsWrapper.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0)); 

        JButton footballBtn = new JButton("Football");
        styleCategoryButton(footballBtn, "Football"); 
        footballBtn.addActionListener(e -> displayTurfsForCategory("Football"));

        JButton cricketBtn = new JButton("Cricket");
        styleCategoryButton(cricketBtn, "Cricket");
        cricketBtn.addActionListener(e -> displayTurfsForCategory("Cricket"));

        JButton basketballBtn = new JButton("Basketball");
        styleCategoryButton(basketballBtn, "Basketball");
        basketballBtn.addActionListener(e -> displayTurfsForCategory("Basketball"));
        
        categoryButtonsWrapper.add(footballBtn);
        categoryButtonsWrapper.add(cricketBtn);
        categoryButtonsWrapper.add(basketballBtn);
        
        contentContainer.add(categoryButtonsWrapper);
        
        turfDisplayPanel = new JPanel(); 
        turfDisplayPanel.setLayout(new BoxLayout(turfDisplayPanel, BoxLayout.Y_AXIS));
        turfDisplayPanel.setBackground(lightGrayBackground);
        turfDisplayPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));
        
        contentContainer.add(turfDisplayPanel);
        
        JScrollPane scrollPane = new JScrollPane(contentContainer);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        return scrollPane;
    }

    private void displayTurfsForCategory(String category) {
        turfDisplayPanel.removeAll(); 

        List<TurfModel> turfsToDisplay;
        if ("Random".equals(category)) {
            List<TurfModel> allTurfs = new ArrayList<>();
            allTurfs.addAll(getSimulatedTurfsByCategory("FootballTurfs"));
            allTurfs.addAll(getSimulatedTurfsByCategory("CricketTurfs"));
            allTurfs.addAll(getSimulatedTurfsByCategory("BasketballTurfs"));
            Collections.shuffle(allTurfs);
            turfsToDisplay = allTurfs.subList(0, Math.min(allTurfs.size(), 3)); 
        } else {
            turfsToDisplay = getSimulatedTurfsByCategory(category + "Turfs");
            turfsToDisplay = turfsToDisplay.subList(0, Math.min(turfsToDisplay.size(), 3)); 
        }

        if (turfsToDisplay.isEmpty()) {
            JLabel noTurfsLabel = new JLabel("No " + category + " turfs found.");
            noTurfsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14)); 
            noTurfsLabel.setForeground(Color.GRAY);
            turfDisplayPanel.add(noTurfsLabel);
        } else {
            JLabel categoryTitle = new JLabel(category + " Turfs");
            categoryTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
            categoryTitle.setForeground(darkText);
            categoryTitle.setAlignmentX(Component.LEFT_ALIGNMENT); 
            turfDisplayPanel.add(categoryTitle);
            turfDisplayPanel.add(Box.createVerticalStrut(10)); 

            for (TurfModel turf : turfsToDisplay) {
                turfDisplayPanel.add(createTurfCard(
                    turf.getName(),
                    turf.getAddress(),
                    turf.getOperatingHours(),
                    turf.getPricePerHour(),
                    turf.getImagePath() 
                ));
                turfDisplayPanel.add(Box.createVerticalStrut(15));
            }
        }

        turfDisplayPanel.revalidate();
        turfDisplayPanel.repaint();
        
        updateCategoryButtonStyles(category);
    }
    
    private JPanel createTurfCard(String name, String address, String hours, int price, String imagePath) { 
        JPanel card = new JPanel(new BorderLayout(0, 0)); 
        card.setBackground(mainWhite);
        card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1)); 
        card.setPreferredSize(new Dimension(750, 120)); 

        JLabel imageLabel = new JLabel();
        
        boolean imageLoaded = false;
        if (imagePath != null && !imagePath.isEmpty()) {
            try {
                java.net.URL imageUrl = getClass().getClassLoader().getResource(imagePath.replace("src/", ""));
                if (imageUrl != null) {
                    ImageIcon originalIcon = new ImageIcon(imageUrl);
                    Image image = originalIcon.getImage();
                    Image scaledImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    imageLabel.setIcon(new ImageIcon(scaledImage));
                    imageLoaded = true;
                }
                imageLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 0)); 
            } catch (Exception e) {
            }
        } 
        
        if (imageLoaded) {
            imageLabel.setPreferredSize(new Dimension(100, 100));
            card.add(imageLabel, BorderLayout.WEST);
        }

        JPanel detailsPanel = new JPanel(new GridLayout(3, 1));
        detailsPanel.setBackground(mainWhite);
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 0)); 
        
        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        detailsPanel.add(nameLabel);
        
        JLabel addressLabel = new JLabel(address); 
        addressLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        addressLabel.setForeground(Color.GRAY);
        detailsPanel.add(addressLabel);
        
        JLabel hoursLabel = new JLabel(hours);
        hoursLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        hoursLabel.setForeground(Color.GRAY);
        detailsPanel.add(hoursLabel);
        
        card.add(detailsPanel, BorderLayout.CENTER);

        JPanel actionPanel = new JPanel(new BorderLayout()); 
        actionPanel.setBackground(mainWhite);
        actionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 15)); 

        JLabel priceLabel = new JLabel("Price: ₹" + price + ".00/hour", javax.swing.SwingConstants.RIGHT); 
        priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        priceLabel.setForeground(primaryBlue); 
        actionPanel.add(priceLabel, BorderLayout.NORTH); 
        
        JLabel availabilityLabel = new JLabel("Available", javax.swing.SwingConstants.RIGHT); 
        availabilityLabel.setForeground(primaryBlue); 
        availabilityLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        actionPanel.add(availabilityLabel, BorderLayout.CENTER);
        
        JButton bookButton = new JButton("Book Now");
        bookButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        bookButton.setBackground(primaryBlue); 
        bookButton.setForeground(mainWhite);
        bookButton.setFocusPainted(false);
        
        bookButton.addActionListener(e -> handleBookNow()); 
        actionPanel.add(bookButton, BorderLayout.SOUTH); 
        
        card.add(actionPanel, BorderLayout.EAST);
        
        return card;
    }

    private void styleCategoryButton(JButton button, String category) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(defaultButtonColor); 
        button.setForeground(darkText);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        button.setPreferredSize(new Dimension(120, 40));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setActionCommand(category); 
        button.addActionListener(e -> displayTurfsForCategory(e.getActionCommand()));
    }
    
    private void updateCategoryButtonStyles(String selectedCategory) {
        Component firstComponent = this.getContentPane().getComponent(0);
        if (!(firstComponent instanceof JPanel)) return;
        
        JPanel contentPanel = (JPanel) firstComponent;
        if (contentPanel.getComponentCount() < 1) return;
        
        Component categoryWrapperComp = contentPanel.getComponent(0);
        if (!(categoryWrapperComp instanceof JPanel)) return;
        
        JPanel buttonWrapper = (JPanel) categoryWrapperComp;

        for (Component comp : buttonWrapper.getComponents()) {
            if (comp instanceof JButton) {
                JButton btn = (JButton) comp;
                if (btn.getActionCommand().equals(selectedCategory)) {
                    btn.setBackground(primaryBlue); 
                    btn.setForeground(mainWhite);
                } else {
                    btn.setBackground(defaultButtonColor);
                    btn.setForeground(darkText);
                }
            }
        }
    }

    private void handleBookNow() {
        if (!SessionManager.isLoggedIn()) {
             JOptionPane.showMessageDialog(this, "Please log in to book a slot.", "Session Expired", JOptionPane.ERROR_MESSAGE);
             new AuthPage();
             this.dispose();
             return;
        }
        new BookingPage(); 
    }
    
    private void handleLogout() {
        SessionManager.logout(); 
        this.dispose(); 
        new AuthPage(); 
        JOptionPane.showMessageDialog(null, "Logged out successfully.", "Logout", JOptionPane.INFORMATION_MESSAGE);
    }

    private List<TurfModel> getSimulatedTurfsByCategory(String categoryIdentifier) {
        List<TurfModel> turfs = new ArrayList<>();
        switch (categoryIdentifier) {
            case "FootballTurfs":
                turfs.add(new TurfModel(1, "Football Field A", "123 Street, City", 800, "08:00-22:00", "Football", "images/football1.jpg"));
                turfs.add(new TurfModel(2, "Soccer Dome", "456 Avenue, Town", 1000, "09:00-23:00", "Football", "images/football2.jpg"));
                turfs.add(new TurfModel(3, "Kick Off Grounds", "789 Road, Village", 750, "07:00-21:00", "Football", "images/football3.jpg"));
                break;
            case "CricketTurfs":
                turfs.add(new TurfModel(6, "Cricket Oval Prime", "303 Circle, City", 1200, "09:00-22:00", "Cricket", "images/cricket1.jpg"));
                turfs.add(new TurfModel(7, "Willow Bat Ground", "606 Square, Town", 1100, "08:00-21:00", "Cricket", "images/cricket2.jpg"));
                turfs.add(new TurfModel(8, "The Pavilion", "909 Street, Village", 1300, "10:00-23:00", "Cricket", "images/cricket3.jpg"));
                break;
            case "BasketballTurfs":
                turfs.add(new TurfModel(11, "Hoops Heaven", "404 Court, City", 600, "10:00-23:00", "Basketball", "images/basketball1.jpg"));
                turfs.add(new TurfModel(12, "Slam Dunk Zone", "707 Alley, Town", 650, "09:00-22:00", "Basketball", "images/basketball2.jpg"));
                turfs.add(new TurfModel(13, "Court Kings", "808 Street, Village", 700, "11:00-24:00", "Basketball", "images/basketball3.jpg"));
                break;
            default:
                break;
        }
        return turfs;
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> new TurfHubPage("Random"));
    }
}
