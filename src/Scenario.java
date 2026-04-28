import java.util.ArrayList;
import java.util.List;

public class Scenario {
    private String name;
    private List<Dimension> dimensions;

    public Scenario(String name) {
        this.name = name;
        this.dimensions = new ArrayList<>();
    }

    public void addDimension(Dimension d) { dimensions.add(d); }
    public List<Dimension> getDimensions() { return dimensions; }
    public String getName() { return name; }

    @Override
    public String toString() { return name; }
}