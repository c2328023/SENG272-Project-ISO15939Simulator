import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PlanPanel extends JPanel {
    private JTable table;

    public PlanPanel() {
        setLayout(new BorderLayout());
        String[] columns = {"Dimension", "Metric", "Coeff", "Direction", "Range", "Unit"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        table.setEnabled(false);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public void refreshData(Scenario activeScenario) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        for (Dimension d : activeScenario.getDimensions()) {
            for (Metric m : d.getMetrics()) {
                model.addRow(new Object[]{ d.getName() + " (" + d.getCoefficient() + ")", m.getName(), m.getCoefficient(), m.getDirection(), m.getMin() + "-" + m.getMax(), m.getUnit() });
            }
        }
    }
}