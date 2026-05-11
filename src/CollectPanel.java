import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CollectPanel extends JPanel {
    private JTable table;

    public CollectPanel() {
        setLayout(new BorderLayout());
        String[] columns = {"Metric", "Direction", "Range", "Value", "Score (1-5)", "Coeff / Unit"};
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
                model.addRow(new Object[]{ m.getName(), m.getDirection(), m.getMin() + "-" + m.getMax(), m.getRawValue(), m.calculateScore(), m.getCoefficient() + "/" + m.getUnit() });
            }
        }
    }
}