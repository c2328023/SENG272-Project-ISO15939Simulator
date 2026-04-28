import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ISO15939App extends JFrame {

    private final CardLayout cardLayout;
    private final JPanel mainPanel;
    private int currentStepIndex = 0;
    private final String[] STEP_NAMES = {"Profile", "Define", "Plan", "Collect", "Analyse"};
    private JPanel stepIndicatorPanel;

    private JTextField txtUsername, txtSchool, txtSession;
    private JTable planTable, collectTable;
    private JPanel analyseContentPanel;
    private Scenario activeScenario;

    public ISO15939App() {
        setTitle("ISO 15939 Measurement Process Simulator");
        setSize(900, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        setupData();

        mainPanel.add(createProfilePanel(), STEP_NAMES[0]);
        mainPanel.add(createDefinePanel(), STEP_NAMES[1]);
        mainPanel.add(createPlanPanel(), STEP_NAMES[2]);
        mainPanel.add(createCollectPanel(), STEP_NAMES[3]);
        mainPanel.add(createAnalysePanel(), STEP_NAMES[4]);

        add(createStepIndicator(), BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        add(createNavigationPanel(), BorderLayout.SOUTH);

        updateStepIndicator();
    }

    private void setupData() {
        activeScenario = new Scenario("Scenario C - Team Alpha");

        Dimension usability = new Dimension("Usability", 25);
        usability.addMetric(new Metric("SUS score", 50, "Higher", 0, 100, "points", 89));
        usability.addMetric(new Metric("Onboarding time", 50, "Lower", 0, 60, "min", 5));

        Dimension performance = new Dimension("Perf. Efficiency", 20);
        performance.addMetric(new Metric("Video start time", 50, "Lower", 0, 15, "sec", 3));
        performance.addMetric(new Metric("Concurrent exams", 50, "Higher", 0, 600, "users", 450));

        Dimension reliability = new Dimension("Reliability", 20);
        reliability.addMetric(new Metric("Uptime", 50, "Higher", 95, 100, "%", 99));
        reliability.addMetric(new Metric("MTTR", 50, "Lower", 0, 120, "min", 30));

        activeScenario.addDimension(usability);
        activeScenario.addDimension(performance);
        activeScenario.addDimension(reliability);
    }

    private JPanel createProfilePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        txtUsername = new JTextField(20);
        txtSchool = new JTextField(20);
        txtSession = new JTextField(20);

        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1; panel.add(txtUsername, gbc);
        gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("School:"), gbc);
        gbc.gridx = 1; panel.add(txtSchool, gbc);
        gbc.gridx = 0; gbc.gridy = 2; panel.add(new JLabel("Session Name:"), gbc);
        gbc.gridx = 1; panel.add(txtSession, gbc);

        return panel;
    }

    private JPanel createDefinePanel() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JPanel typePanel = new JPanel();
        typePanel.setBorder(BorderFactory.createTitledBorder("2a. Quality Type"));
        ButtonGroup typeGroup = new ButtonGroup();
        JRadioButton rbProduct = new JRadioButton("Product Quality", true);
        JRadioButton rbProcess = new JRadioButton("Process Quality");
        typeGroup.add(rbProduct); typeGroup.add(rbProcess);
        typePanel.add(rbProduct); typePanel.add(rbProcess);

        JPanel modePanel = new JPanel();
        modePanel.setBorder(BorderFactory.createTitledBorder("2b. Mode"));
        ButtonGroup modeGroup = new ButtonGroup();
        JRadioButton rbCustom = new JRadioButton("Custom");
        JRadioButton rbHealth = new JRadioButton("Health");
        JRadioButton rbEdu = new JRadioButton("Education", true);
        modeGroup.add(rbCustom); modeGroup.add(rbHealth); modeGroup.add(rbEdu);
        modePanel.add(rbCustom); modePanel.add(rbHealth); modePanel.add(rbEdu);

        JPanel scenarioPanel = new JPanel();
        scenarioPanel.setBorder(BorderFactory.createTitledBorder("2c. Scenario"));
        JComboBox<Scenario> comboScenarios = new JComboBox<>();
        comboScenarios.addItem(activeScenario);
        comboScenarios.addItem(new Scenario("Scenario D - Team Beta"));
        scenarioPanel.add(comboScenarios);

        panel.add(typePanel); panel.add(modePanel); panel.add(scenarioPanel);
        return panel;
    }

    private JPanel createPlanPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = {"Dimension", "Metric", "Coeff", "Direction", "Range", "Unit"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        planTable = new JTable(model);
        planTable.setEnabled(false);
        panel.add(new JScrollPane(planTable), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createCollectPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = {"Metric", "Direction", "Range", "Value", "Score (1-5)", "Coeff / Unit"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        collectTable = new JTable(model);
        collectTable.setEnabled(false);
        panel.add(new JScrollPane(collectTable), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createAnalysePanel() {
        analyseContentPanel = new JPanel(new BorderLayout());
        return analyseContentPanel;
    }

    private void updateAnalyseData() {
        analyseContentPanel.removeAll();
        JPanel progressPanel = new JPanel(new GridLayout(0, 1, 5, 5));

        Dimension lowestDim = null;
        double lowestScore = 5.0;

        for (Dimension d : activeScenario.getDimensions()) {
            double score = d.calculateDimensionScore();
            if(score < lowestScore) { lowestScore = score; lowestDim = d; }

            JPanel row = new JPanel(new BorderLayout(10, 0));
            row.add(new JLabel(d.getName() + " (" + String.format("%.2f", score) + ")"), BorderLayout.WEST);
            JProgressBar pb = new JProgressBar(0, 500);
            pb.setValue((int)(score * 100));
            pb.setStringPainted(true);
            pb.setString(String.format("%.2f", score) + " / 5.0");
            pb.setForeground(new Color(0, 153, 76));
            row.add(pb, BorderLayout.CENTER);
            progressPanel.add(row);
        }

        JPanel gapPanel = new JPanel(new GridLayout(4, 1));
        gapPanel.setBorder(BorderFactory.createTitledBorder("Gap Analysis"));
        if(lowestDim != null) {
            double gap = 5.0 - lowestScore;
            String label = lowestScore >= 4 ? "Excellent" : lowestScore >= 3 ? "Good" : lowestScore >= 2 ? "Needs Improvement" : "Poor";
            gapPanel.add(new JLabel("Lowest Dimension: " + lowestDim.getName() + " (Score: " + String.format("%.2f", lowestScore) + ")"));
            gapPanel.add(new JLabel("Gap Value: " + String.format("%.2f", gap)));
            gapPanel.add(new JLabel("Quality Label: " + label));
            gapPanel.add(new JLabel("<html><i>This dimension has the lowest score and requires the most improvement.</i></html>"));
        }

        RadarChartPanel radarPanel = new RadarChartPanel(activeScenario.getDimensions());
        radarPanel.setPreferredSize(new java.awt.Dimension(300, 300));
        analyseContentPanel.add(progressPanel, BorderLayout.NORTH);
        analyseContentPanel.add(radarPanel, BorderLayout.CENTER);
        analyseContentPanel.add(gapPanel, BorderLayout.SOUTH);
        analyseContentPanel.revalidate();
        analyseContentPanel.repaint();
    }

    private void refreshTables() {
        DefaultTableModel planModel = (DefaultTableModel) planTable.getModel();
        DefaultTableModel collectModel = (DefaultTableModel) collectTable.getModel();
        planModel.setRowCount(0); collectModel.setRowCount(0);

        for (Dimension d : activeScenario.getDimensions()) {
            for (Metric m : d.getMetrics()) {
                planModel.addRow(new Object[]{ d.getName() + " (" + d.getCoefficient() + ")", m.getName(), m.getCoefficient(), m.getDirection(), m.getMin() + "-" + m.getMax(), m.getUnit() });
                collectModel.addRow(new Object[]{ m.getName(), m.getDirection(), m.getMin() + "-" + m.getMax(), m.getRawValue(), m.calculateScore(), m.getCoefficient() + "/" + m.getUnit() });
            }
        }
    }

    private JPanel createNavigationPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnPrev = new JButton("Previous");
        JButton btnNext = new JButton("Next");

        btnPrev.addActionListener(e -> {
            if (currentStepIndex > 0) { currentStepIndex--; updateView(); }
        });

        btnNext.addActionListener(e -> {
            if (currentStepIndex == 0) {
                if (txtUsername.getText().trim().isEmpty() || txtSchool.getText().trim().isEmpty() || txtSession.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill in all profile fields (Username, School, Session) to proceed.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }
            if (currentStepIndex < STEP_NAMES.length - 1) { currentStepIndex++; updateView(); }
        });

        panel.add(btnPrev); panel.add(btnNext);
        return panel;
    }

    private void updateView() {
        if(currentStepIndex == 2 || currentStepIndex == 3) refreshTables();
        if(currentStepIndex == 4) updateAnalyseData();

        cardLayout.show(mainPanel, STEP_NAMES[currentStepIndex]);
        updateStepIndicator();
    }

    private JPanel createStepIndicator() {
        stepIndicatorPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        return stepIndicatorPanel;
    }

    private void updateStepIndicator() {
        stepIndicatorPanel.removeAll();
        for (int i = 0; i < STEP_NAMES.length; i++) {
            JLabel lbl = new JLabel(STEP_NAMES[i]);
            if (i == currentStepIndex) {
                lbl.setFont(new Font("Arial", Font.BOLD, 14));
                lbl.setForeground(Color.BLUE);
            } else if (i < currentStepIndex) {
                lbl.setText(STEP_NAMES[i] + " ✓"); // Unicode uyarısı düzeltildi
                lbl.setForeground(new Color(0, 150, 0));
            }
            stepIndicatorPanel.add(lbl);
            if (i < STEP_NAMES.length - 1) stepIndicatorPanel.add(new JLabel(" -> "));
        }
        stepIndicatorPanel.revalidate();
        stepIndicatorPanel.repaint();
    }

    // Static uyarılarına takılmamak için düzenlenmiş bağımsız grafik sınıfı
    static class RadarChartPanel extends JPanel {
        private final List<Dimension> dims;
        public RadarChartPanel(List<Dimension> dims) { this.dims = dims; }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if(dims == null || dims.isEmpty()) return;
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int cx = getWidth() / 2, cy = getHeight() / 2;
            int radius = Math.min(cx, cy) - 40;
            int n = dims.size();

            g2d.setColor(Color.LIGHT_GRAY);
            Polygon bgPoly = new Polygon();
            for (int i = 0; i < n; i++) {
                double angle = 2 * Math.PI * i / n - Math.PI / 2;
                bgPoly.addPoint((int)(cx + radius * Math.cos(angle)), (int)(cy + radius * Math.sin(angle)));
            }
            g2d.drawPolygon(bgPoly);

            Polygon dataPoly = new Polygon();
            for (int i = 0; i < n; i++) {
                double score = dims.get(i).calculateDimensionScore();
                double scaledRadius = radius * (score / 5.0);
                double angle = 2 * Math.PI * i / n - Math.PI / 2;
                int x = (int)(cx + scaledRadius * Math.cos(angle));
                int y = (int)(cy + scaledRadius * Math.sin(angle));
                dataPoly.addPoint(x, y);

                g2d.setColor(Color.BLACK);
                g2d.drawString(dims.get(i).getName(), (int)(cx + (radius+15) * Math.cos(angle)) - 20, (int)(cy + (radius+15) * Math.sin(angle)));
            }
            g2d.setColor(new Color(0, 0, 255, 100));
            g2d.fillPolygon(dataPoly);
            g2d.setColor(Color.BLUE);
            g2d.drawPolygon(dataPoly);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ISO15939App().setVisible(true);
        });
    }
}