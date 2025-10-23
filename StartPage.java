import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane; 
import java.awt.Color;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.GridLayout; 
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StartPage extends JFrame {

    private final Color primaryBackground = new Color(30, 30, 30); 
    private final Color buttonBackground = new Color(50, 50, 50);
    private final Color buttonHoverBorder = new Color(0, 180, 120); 
    private final Color textColor = Color.WHITE;

    public StartPage() { 
        super("Select Your Sport");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null); 

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(primaryBackground);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JLabel titleLabel = new JLabel("Which Sport?", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(textColor);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel outerButtonContainer = new JPanel(new GridLayout(1, 1));
        outerButtonContainer.setBackground(primaryBackground);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 30)); 
        buttonPanel.setBackground(primaryBackground);

        JButton footballBtn = createSportButton("Football");
        JButton cricketBtn = createSportButton("Cricket");
        JButton basketballBtn = createSportButton("Basketball");

        footballBtn.addActionListener(e -> navigateToTurfHub("Football"));
        cricketBtn.addActionListener(e -> navigateToTurfHub("Cricket"));
        basketballBtn.addActionListener(e -> navigateToTurfHub("Basketball"));

        buttonPanel.add(footballBtn);
        buttonPanel.add(cricketBtn);
        buttonPanel.add(basketballBtn);

        outerButtonContainer.add(buttonPanel);
        mainPanel.add(outerButtonContainer, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }

    private JButton createSportButton(String text) { 
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(150, 100));
        button.setBackground(buttonBackground);
        button.setForeground(textColor);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(buttonBackground, 2)); 
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBorder(BorderFactory.createLineBorder(buttonHoverBorder, 2));
            }
            public void mouseExited(MouseEvent evt) {
                button.setBorder(BorderFactory.createLineBorder(buttonBackground, 2));
            }
        });
        return button;
    }
    
    private void navigateToTurfHub(String category) {
        new TurfHubPage(category).setVisible(true); 
        this.dispose(); 
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(StartPage::new); 
    }
}
