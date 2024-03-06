package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MapPanel extends JPanel {
    private List<List<Segment>> segmentsList;
    private int scale;
    private List<Color> colors;
    private List<String> filePaths;
    private List<JCheckBox> checkboxList;

    private static final long serialVersionUID = 1L;

    public MapPanel(List<String> filePaths, int scale, List<Color> colors) {
        this.scale = scale;
        this.colors = colors;
        this.checkboxList = new ArrayList<>();
        loadSegments(filePaths);
        int width = calculateWidth();
        int height = calculateHeight();
        setPreferredSize(new Dimension(width, height));
        setLayout(new BorderLayout()); // Use BorderLayout to split the panel into two parts: map and legend
        add(createMapPanel(), BorderLayout.CENTER);
        add(createLegendPanel(), BorderLayout.WEST);
    }

    private void loadSegments(List<String> filePaths) {
        this.filePaths = filePaths;
        segmentsList = new ArrayList<>();

        for (String filePath : filePaths) {
            List<Segment> segments = new ArrayList<>();
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
            segmentsList.add(segments);
        }
    }

    private int calculateWidth() {
        int maxWidth = 0;
        for (List<Segment> segments : segmentsList) {
            for (Segment segment : segments) {
                int segmentWidth = (int) Math.max(segment.getX1(), segment.getX2());
                if (segmentWidth > maxWidth) {
                    maxWidth = segmentWidth;
                }
            }
        }
        return (int) (maxWidth * scale) + 1;
    }

    private int calculateHeight() {
        int maxHeight = 0;
        for (List<Segment> segments : segmentsList) {
            for (Segment segment : segments) {
                int segmentHeight = (int) Math.max(segment.getY1(), segment.getY2());
                if (segmentHeight > maxHeight) {
                    maxHeight = segmentHeight;
                }
            }
        }
        return (int) (maxHeight * scale) + 1;
    }

    private JPanel createMapPanel() {
        return new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                int colorIndex = 0;
                for (int i = 0; i < segmentsList.size(); i++) {
                    if (checkboxList.get(i).isSelected()) {
                        g.setColor(colors.get(colorIndex));

                        for (Segment segment : segmentsList.get(i)) {
                            int x1 = (int) (segment.getX1() * scale);
                            int y1 = (int) (segment.getY1() * scale);
                            int x2 = (int) (segment.getX2() * scale);
                            int y2 = (int) (segment.getY2() * scale);

                            g.drawLine(x1, y1, x2, y2);
                        }
                        colorIndex = (colorIndex + 1) % colors.size();
                    }
                }
            }
        };
    }

    private JPanel createLegendPanel() {
        JPanel legendPanel = new JPanel();
        legendPanel.setLayout(new GridLayout(colors.size(), 2));
        legendPanel.setPreferredSize(new Dimension(150, getHeight())); // Set the width of the legend panel

        for (int i = 0; i < colors.size(); i++) {
            JCheckBox checkBox = new JCheckBox();
            checkboxList.add(checkBox);
            checkBox.setSelected(true);

            String[] parts = filePaths.get(i).split("/");
            JLabel legendLabel = new JLabel(parts[parts.length - 1]);
            legendLabel.setForeground(colors.get(i));

            checkBox.addActionListener(new CheckboxActionListener());
            legendPanel.add(checkBox);
            legendPanel.add(legendLabel);
        }

        return legendPanel;
    }

    private class CheckboxActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            repaint(); // Repaint the map when a checkbox state changes
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
