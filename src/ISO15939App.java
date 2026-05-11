import javax.swing.*;
import java.awt.*;

public class ISO15939App extends JFrame {

    private final CardLayout cardLayout;
    private final JPanel mainPanel;
    private int currentStepIndex = 0;
    private final String[] STEP_NAMES = {"Profile", "Define", "Plan", "Collect", "Analyse"};
    private JPanel stepIndicatorPanel;

    private Scenario activeScenario;

    private ProfilePanel profilePanel;
    private DefinePanel definePanel;
    private PlanPanel planPanel;
    private CollectPanel collectPanel;
    private AnalysePanel analysePanel;

    public ISO15939App() {
        setTitle("ISO 15939 Measurement Process Simulator - Kira Edition");
        setSize(900, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        activeScenario = ScenarioDataRepository.createDefaultScenario();

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        profilePanel = new ProfilePanel();
        definePanel = new DefinePanel(activeScenario);
        planPanel = new PlanPanel();
        collectPanel = new CollectPanel();
        analysePanel = new AnalysePanel();

        mainPanel.add(profilePanel, STEP_NAMES[0]);
        mainPanel.add(definePanel, STEP_NAMES[1]);
        mainPanel.add(planPanel, STEP_NAMES[2]);
        mainPanel.add(collectPanel, STEP_NAMES[3]);
        mainPanel.add(analysePanel, STEP_NAMES[4]);

        add(createStepIndicator(), BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        add(createNavigationPanel(), BorderLayout.SOUTH);

        updateStepIndicator();
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
                if (!profilePanel.isComplete()) {
                    JOptionPane.showMessageDialog(this, "Lütfen ilerlemek için profili tamamlayın.", "Hata", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }
            if (currentStepIndex < STEP_NAMES.length - 1) { currentStepIndex++; updateView(); }
        });

        panel.add(btnPrev); panel.add(btnNext);
        return panel;
    }

    private void updateView() {
        if(currentStepIndex == 2) planPanel.refreshData(activeScenario);
        if(currentStepIndex == 3) collectPanel.refreshData(activeScenario);
        if(currentStepIndex == 4) analysePanel.updateAnalyseData(activeScenario);

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
                lbl.setText(STEP_NAMES[i] + " \u2713");
                lbl.setForeground(new Color(0, 150, 0));
            }
            stepIndicatorPanel.add(lbl);
            if (i < STEP_NAMES.length - 1) stepIndicatorPanel.add(new JLabel(" -> "));
        }
        stepIndicatorPanel.revalidate();
        stepIndicatorPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ISO15939App().setVisible(true);
        });
    }
}