import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VacuumCleanerLauncher extends JFrame implements ActionListener {

    private VacuumCleaner vacuumCleaner;
    private JLabel statusLabel;
    private JButton upButton, downButton, useButton, emptyButton, chargeButton;

    public VacuumCleanerLauncher() {
        vacuumCleaner = new VacuumCleaner();

        // 1. Setup the Frame (Window)
        setTitle("Vacuum Cleaner Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // 2. Create the Status Display Label
        statusLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Monospaced", Font.BOLD, 14));
        updateStatusDisplay();
        add(statusLabel, BorderLayout.CENTER);

        // 3. Create the Control Panel (for buttons)
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(3, 2, 10, 10));

        // 4. Initialize Buttons
        upButton    = new JButton("Strength UP (MAX 3)");
        downButton  = new JButton("Strength DOWN (MIN 0)");
        useButton   = new JButton("USE (1 Cycle)");
        emptyButton = new JButton("EMPTY DUST");
        chargeButton= new JButton("CHARGE (30s Wait)");

        // 5. Add Action Listeners
        upButton.addActionListener(this);
        downButton.addActionListener(this);
        useButton.addActionListener(this);
        emptyButton.addActionListener(this);
        chargeButton.addActionListener(this);

        // 6. Add Buttons to the Control Panel
        controlPanel.add(upButton);
        controlPanel.add(downButton);
        controlPanel.add(useButton);
        controlPanel.add(emptyButton);
        controlPanel.add(chargeButton);
        controlPanel.add(new JLabel("")); // Empty space

        add(controlPanel, BorderLayout.SOUTH);

        pack();
        setSize(450, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void updateStatusDisplay() {
        String powerStatus = vacuumCleaner.isCharging()
                ? "CHARGING"
                : (vacuumCleaner.getPowerState() ? "ON" : "OFF");

        String statusText = String.format(
                "<html><div style='text-align: center;'>" +
                        "<h2>VACUUM STATUS</h2>" +
                        "🔋 Battery: <b>%d%%</b><br>" +
                        "🌪️ Strength: <b>%d</b><br>" +
                        "🗑️ Dirt Level: <b>%d / %d</b> (Max)<br>" +
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
            case "Strength UP (MAX 3)":
                // **FIXED:** Calling your method without the int argument
                vacuumCleaner.addSuctionLevel();
                break;
            case "Strength DOWN (MIN 0)":
                // **FIXED:** Calling your method without the int argument
                vacuumCleaner.minusSuctionLevel();
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
                "Charging started. (Simulating 30 seconds wait).\n" +
                        "Click OK to instantly complete the charge.",
                "Charging in Progress...", JOptionPane.WARNING_MESSAGE);

        vacuumCleaner.completeCharge();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VacuumCleanerLauncher());
    }
}