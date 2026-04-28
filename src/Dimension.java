import java.util.ArrayList;
import java.util.List;

public class Dimension {
    private String name;
    private double coefficient;
    private List<Metric> metrics;

    public Dimension(String name, double coefficient) {
        this.name = name;
        this.coefficient = coefficient;
        this.metrics = new ArrayList<>();
    }

    public void addMetric(Metric m) { metrics.add(m); }
    public List<Metric> getMetrics() { return metrics; }
    public String getName() { return name; }
    public double getCoefficient() { return coefficient; }

    public double calculateDimensionScore() {
        double totalWeightedScore = 0;
        double totalCoeff = 0;
        for (Metric m : metrics) {
            totalWeightedScore += (m.calculateScore() * m.getCoefficient());
            totalCoeff += m.getCoefficient();
        }
        return totalCoeff == 0 ? 0 : (totalWeightedScore / totalCoeff);
    }
}