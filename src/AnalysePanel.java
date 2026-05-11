import javax.swing.*;
import java.awt.*;

public class AnalysePanel extends JPanel {
    public AnalysePanel() {
        setLayout(new BorderLayout());
    }

    public void updateAnalyseData(Scenario activeScenario) {
        removeAll();
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
        add(progressPanel, BorderLayout.NORTH);
        add(radarPanel, BorderLayout.CENTER);
        add(gapPanel, BorderLayout.SOUTH);
        revalidate();
        repaint();
    }
}