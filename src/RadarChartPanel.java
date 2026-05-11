import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RadarChartPanel extends JPanel {
    private final List<Dimension> dims;

    public RadarChartPanel(List<Dimension> dims) {
        this.dims = dims;
    }

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