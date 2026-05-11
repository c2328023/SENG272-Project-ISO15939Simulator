import javax.swing.*;
import java.awt.*;

public class DefinePanel extends JPanel {
    public DefinePanel(Scenario activeScenario) {
        setLayout(new GridLayout(3, 1, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

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

        add(typePanel); add(modePanel); add(scenarioPanel);
    }
}