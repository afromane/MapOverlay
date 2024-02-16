package ui;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileReader; 
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapPanel extends JPanel {
    private List<Segment> segments;
    private int scale;
    private Color color ;
    private static final long serialVersionUID = 1L;
    public MapPanel(String filePath, int scale, Color color) {
        this.scale = scale;
        loadSegments(filePath);
        int width = calculateWidth();
        int height = calculateHeight();
        setPreferredSize(new Dimension(width, height));
        this.color = color;
        
    }

    private void loadSegments(String filePath) {
        segments = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] coordinates = line.split(" ");
                if (coordinates.length == 4) {
                    double x1 = Double.parseDouble(coordinates[0]);
                    double y1 = Double.parseDouble(coordinates[1]);
                    double x2 = Double.parseDouble(coordinates[2]);
                    double y2 = Double.parseDouble(coordinates[3]);
                    segments.add(new Segment(x1, y1, x2, y2));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int calculateWidth() {
        int maxWidth = 0;
        for (Segment segment : segments) {
            int segmentWidth = (int) Math.max(segment.getX1(), segment.getX2());
            if (segmentWidth > maxWidth) {
                maxWidth = segmentWidth;
            }
        }
        return (int) (maxWidth * scale) + 1;
    }

    private int calculateHeight() {
        int maxHeight = 0;
        for (Segment segment : segments) {
            int segmentHeight = (int) Math.max(segment.getY1(), segment.getY2());
            if (segmentHeight > maxHeight) {
                maxHeight = segmentHeight;
            }
        }
        return (int) (maxHeight * scale) + 1;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(this.color); // Charger la couleur specifique du segment

        for (Segment segment : segments) {
            int x1 = (int) (segment.getX1() * scale);
            int y1 = (int) (segment.getY1() * scale);
            int x2 = (int) (segment.getX2() * scale);
            int y2 = (int) (segment.getY2() * scale);

            g.drawLine(x1, y1, x2, y2);
        }
    }

    private static class Segment {
        private double x1, y1, x2, y2;

        public Segment(double x1, double y1, double x2, double y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }

        public double getX1() {
            return x1;
        }

        public double getY1() {
            return y1;
        }

        public double getX2() {
            return x2;
        }

        public double getY2() {
            return y2;
        }
    }
}
