import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory; 
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SportSelectionPage extends JFrame {

    private final Color mainWhite = Color.WHITE; 
    private final Color primaryBlue = new Color(0x123499); 
    private final Color darkText = new Color(50, 50, 50); 
    private final Color buttonBorderGray = new Color(200, 200, 200); 

    public SportSelectionPage() {
        super("Select a Sport");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500); 
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridBagLayout()); 
        mainPanel.setBackground(mainWhite); 

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS)); 
        contentPanel.setBackground(mainWhite); 
        contentPanel.setBorder(BorderFactory.createEmptyBorder(80, 80, 80, 80)); 

        JLabel titleLabel = new JLabel("Which Sport?", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(primaryBlue); 
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); 
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(30)); 

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0));
        buttonRow.setBackground(mainWhite); 

        JButton footballBtn = createSportButton("Football");
        JButton cricketBtn = createSportButton("Cricket");
        JButton basketballBtn = createSportButton("Basketball");
        
        footballBtn.addActionListener(e -> {
            new TurfHubPage("Football").setVisible(true); 
            this.dispose(); 
        });
        cricketBtn.addActionListener(e -> {
            new TurfHubPage("Cricket").setVisible(true); 
            this.dispose(); 
        });
        basketballBtn.addActionListener(e -> {
            new TurfHubPage("Basketball").setVisible(true); 
            this.dispose(); 
        });

        buttonRow.add(footballBtn);
        buttonRow.add(cricketBtn);
        buttonRow.add(basketballBtn);

        contentPanel.add(buttonRow);
        
        mainPanel.add(contentPanel); 

        add(mainPanel);
        setVisible(true);
    }

    private JButton createSportButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(150, 100));
        button.setBackground(mainWhite); 
        button.setForeground(darkText); 
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(buttonBorderGray, 1)); 
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBorder(BorderFactory.createLineBorder(primaryBlue, 2)); 
                button.setForeground(primaryBlue); 
            }
            public void mouseExited(MouseEvent evt) {
                button.setBorder(BorderFactory.createLineBorder(buttonBorderGray, 1)); 
                button.setForeground(darkText); 
            }
        });
        return button;
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(SportSelectionPage::new);
    }
}
