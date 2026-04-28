public class Metric {
    private String name;
    private double coefficient;
    private String direction; // "Higher" veya "Lower"
    private double min, max;
    private String unit;
    private double rawValue;

    public Metric(String name, double coefficient, String direction, double min, double max, String unit, double rawValue) {
        this.name = name;
        this.coefficient = coefficient;
        this.direction = direction;
        this.min = min;
        this.max = max;
        this.unit = unit;
        this.rawValue = rawValue;
    }

    public double calculateScore() {
        double score = 0;
        if (direction.equals("Higher")) {
            // [cite: 63]
            score = 1 + ((rawValue - min) / (max - min)) * 4;
        } else {
            score = 5 - (((rawValue - min) / (max - min)) * 4);
        }
        return Math.round(score * 2) / 2.0;
    }

    public String getName() { return name; }
    public double getCoefficient() { return coefficient; }
    public String getDirection() { return direction; }
    public double getMin() { return min; }
    public double getMax() { return max; }
    public String getUnit() { return unit; }
    public double getRawValue() { return rawValue; }
}