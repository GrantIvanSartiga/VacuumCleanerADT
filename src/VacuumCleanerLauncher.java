import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class VacuumCleanerLauncher extends JFrame implements ActionListener {

    private VacuumCleaner vacuumCleaner;
    private JLabel statusLabel;

    private JButton leftButton, rightButton, useButton, emptyButton, chargeButton;

    private JLabel gifLabel;
    private ImageIcon vacuumGifSlow, vacuumGifMedium, vacuumGifFast, vacuumPaused;

    private ImageIcon leftIcon, rightIcon, useIcon, emptyIcon, chargeIcon;

    // --- Custom Colors ---
    private final Color LIGHT_BACKGROUND = new Color(0xFCF0AF); // Cream/Yellow
    private final Color DARK_ACCENT = new Color(0x192B7E);      // Dark Navy/Indigo


    public VacuumCleanerLauncher() {
        vacuumCleaner = new VacuumCleaner();

        // 1. Setup the Frame (Window)
        setTitle("Vacuum Cleaner Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setSize(500, 600);
        setLocationRelativeTo(null);

        // APPLY COLOR 1: Main background
        getContentPane().setBackground(LIGHT_BACKGROUND);
        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 2. Create Top Panel (GIF + Status)
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setPreferredSize(new Dimension(500, 300));

        // APPLY COLOR 2: Top panel background
        topPanel.setBackground(LIGHT_BACKGROUND);

        // --- Load GIFs and Control Icons ---

        int iconSize = 60;
        try {
            leftIcon = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("left_icon.png")))
                    .getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
            rightIcon = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("right_icon.png")))
                    .getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));

            useIcon = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("use_icon.png")))
                    .getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
            emptyIcon = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("empty_icon.png")))
                    .getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
            chargeIcon = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("charge_icon.png")))
                    .getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
        } catch (Exception e) {
            System.err.println("Error loading control icons. Ensure icon files are in the resources folder: " + e.getMessage());
        }

        // GIF Loading (Resized)
        vacuumGifSlow = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("vacuumGifSlow.gif"))).getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT));
        vacuumGifMedium = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("vacuumGifMedium.gif"))).getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT));
        vacuumGifFast = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("vacuumGifFast.gif"))).getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT));
        vacuumPaused = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("vacuumPaused.png"))).getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT));

        gifLabel = new JLabel(vacuumPaused);
        gifLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gifLabel.setVerticalAlignment(SwingConstants.CENTER);

        topPanel.add(gifLabel, BorderLayout.CENTER);

        // Create Status Label
        statusLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Monospaced", Font.BOLD, 12));
        statusLabel.setPreferredSize(new Dimension(500, 150));

        // APPLY COLOR 3: Set Status Label text color
        statusLabel.setForeground(DARK_ACCENT);

        topPanel.add(statusLabel, BorderLayout.NORTH);
        add(topPanel, BorderLayout.NORTH);

        // 3. Create the Control Panel (Main Container)

        // Use BorderLayout for the main control area
        JPanel controlPanel = new JPanel(new BorderLayout(10, 10));

        // APPLY COLOR 4: Control panel background and reinforced border style
        controlPanel.setBackground(LIGHT_BACKGROUND);

        controlPanel.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(DARK_ACCENT, 3),
                        "Controls",
                        javax.swing.border.TitledBorder.CENTER,
                        javax.swing.border.TitledBorder.TOP,
                        new Font("Arial", Font.BOLD, 14),
                        DARK_ACCENT
                )
        );

        // 4. Initialize Buttons (Icon-Only and Borderless)

        // LEFT BUTTON (Decrease Strength)
        leftButton = new JButton(leftIcon);
        leftButton.setActionCommand("Strength LEFT (MIN 0)");
        leftButton.setBorderPainted(false);
        leftButton.setContentAreaFilled(false);
        leftButton.setFocusPainted(false);
        leftButton.setOpaque(false);

        // RIGHT BUTTON (Increase Strength)
        rightButton = new JButton(rightIcon);
        rightButton.setActionCommand("Strength RIGHT (MAX 3)");
        rightButton.setBorderPainted(false);
        rightButton.setContentAreaFilled(false);
        rightButton.setFocusPainted(false);
        rightButton.setOpaque(false);

        useButton = new JButton(useIcon);
        useButton.setActionCommand("USE (1 Cycle)");
        useButton.setBorderPainted(false);
        useButton.setContentAreaFilled(false);
        useButton.setFocusPainted(false);
        useButton.setOpaque(false);

        emptyButton = new JButton(emptyIcon);
        emptyButton.setActionCommand("EMPTY DUST");
        emptyButton.setBorderPainted(false);
        emptyButton.setContentAreaFilled(false);
        emptyButton.setFocusPainted(false);
        emptyButton.setOpaque(false);

        chargeButton = new JButton(chargeIcon);
        chargeButton.setActionCommand("CHARGE (30s Wait)");
        chargeButton.setBorderPainted(false);
        chargeButton.setContentAreaFilled(false);
        chargeButton.setFocusPainted(false);
        chargeButton.setOpaque(false);

        // 5. Add Action Listeners
        leftButton.addActionListener(this);
        rightButton.addActionListener(this);
        useButton.addActionListener(this);
        emptyButton.addActionListener(this);
        chargeButton.addActionListener(this);

        // 6. Reworked Button Layout using Nested Panels for Asymmetric Grid

        // Panel for Row 1: LEFT | USE | RIGHT (3 Columns)
        JPanel row1Panel = new JPanel(new GridLayout(1, 3, 10, 10));
        row1Panel.setBackground(LIGHT_BACKGROUND);

        row1Panel.add(leftButton);
        row1Panel.add(useButton);
        row1Panel.add(rightButton);

        // Panel for Row 2: CHARGE | EMPTY (2 Columns)
        JPanel row2Panel = new JPanel(new GridLayout(1, 2, 10, 10));
        row2Panel.setBackground(LIGHT_BACKGROUND);

        row2Panel.add(chargeButton);
        row2Panel.add(emptyButton);

        // Stack the two rows vertically
        JPanel buttonStack = new JPanel();
        buttonStack.setLayout(new BoxLayout(buttonStack, BoxLayout.Y_AXIS));
        buttonStack.setBackground(LIGHT_BACKGROUND);

        buttonStack.add(row1Panel);
        buttonStack.add(Box.createRigidArea(new Dimension(0, 10))); // Vertical Spacer
        buttonStack.add(row2Panel);

        controlPanel.add(buttonStack, BorderLayout.NORTH); // Put button stack in the NORTH

        add(controlPanel, BorderLayout.SOUTH);

        updateStatusDisplay();
        setVisible(true);
    }

    // ... (updateStatusDisplay, actionPerformed, handleUseCycle, handleCharging, and main methods remain the same) ...

    private void updateStatusDisplay() {
        // GIF Logic: Display the appropriate GIF or Paused image based on state
        if (vacuumCleaner.getPowerState() && !vacuumCleaner.isCharging()) {
            int strength = vacuumCleaner.getSuctionLevel();
            if (strength == 1) {
                gifLabel.setIcon(vacuumGifSlow);
            } else if (strength == 2) {
                gifLabel.setIcon(vacuumGifMedium);
            } else if (strength >= 3) {
                gifLabel.setIcon(vacuumGifFast);
            }
        } else {
            gifLabel.setIcon(vacuumPaused);
        }

        String powerStatus = vacuumCleaner.isCharging()
                ? "CHARGING"
                : (vacuumCleaner.getPowerState() ? "ON" : "OFF");

        // APPLY COLOR 5: Ensure HTML text uses the dark accent color
        String statusText = String.format(
                "<html><div style='text-align: center; color: #192B7E;'>" +
                        "<h2>VACUUM BOT STATUS</h2>" +
                        "🔋 Battery: <b>%d%%</b><br>" +
                        "🌪️ Strength: <b>%d</b><br>" +
                        "🗑️ Dirt Level: <b>%d/%d</b><br>" +
                        "🔌 State: <b>%s</b>" +
                        "</div></html>",
                vacuumCleaner.getBatteryLevel(),
                vacuumCleaner.getSuctionLevel(),
                vacuumCleaner.getDirtLevel(),
                VacuumCleaner.MAX_DIRT,
                powerStatus
        );
        statusLabel.setText(statusText);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            // Mapped LEFT command to decrease strength (minusSuctionLevel())
            case "Strength LEFT (MIN 0)":
                vacuumCleaner.minusSuctionLevel();
                break;
            // Mapped RIGHT command to increase strength (addSuctionLevel())
            case "Strength RIGHT (MAX 3)":
                vacuumCleaner.addSuctionLevel();
                break;
            case "USE (1 Cycle)":
                handleUseCycle();
                break;
            case "EMPTY DUST":
                vacuumCleaner.emptyDust();
                break;
            case "CHARGE (30s Wait)":
                handleCharging();
                break;
        }
        updateStatusDisplay();
    }

    private void handleUseCycle() {
        if (!vacuumCleaner.useVacuum()) {
            String errorMsg = "Cannot use the vacuum.\n";
            if (vacuumCleaner.getSuctionLevel() == 0) {
                errorMsg += "- Strength is set to 0 (OFF).";
            } else if (vacuumCleaner.isCharging()) {
                errorMsg += "- Vacuum is currently charging.";
            } else if (vacuumCleaner.getBatteryLevel() <= 0) {
                errorMsg += "- Battery is 0%. Please charge.";
            } else if (vacuumCleaner.getDirtLevel() >= vacuumCleaner.getMaxDirt()) {
                errorMsg += "- Dust level is FULL! Please empty.";
            }
            JOptionPane.showMessageDialog(this, errorMsg, "Use Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleCharging() {
        if (vacuumCleaner.isCharging()) {
            JOptionPane.showMessageDialog(this, "Already charging!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } else if (vacuumCleaner.getBatteryLevel() == 100) {
            JOptionPane.showMessageDialog(this, "Battery is already full (100%).", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        vacuumCleaner.startCharging();
        updateStatusDisplay();

        // SIMULATE THE 30-SECOND WAIT TIME
        JOptionPane.showMessageDialog(this,
                "Charging started..\n" +
                        "Click OK to instantly complete the charge.",
                "Charging in Progress...", JOptionPane.WARNING_MESSAGE);

        vacuumCleaner.completeCharge();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VacuumCleanerLauncher());
    }
}