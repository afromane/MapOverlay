package ui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;

public class MapsList extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final String PROJECT_DIRECTORY = System.getProperty("user.dir");
    DefaultListModel<FileListItem> listModel = new DefaultListModel<>();
    JList<FileListItem> list;

    /**
     * Create the panel.
     */
    public MapsList() {
        setLayout(new BorderLayout());

        list = new JList<>(listModel);
        list.setLayoutOrientation(JList.VERTICAL_WRAP);
        list.setCellRenderer(new FileListRenderer());
        list.setFont(new Font("Viner Hand ITC", Font.PLAIN, 17));
        // Allow multiple selections
        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        // Add a margin to the JList
        int margin = 30;
        list.setBorder(BorderFactory.createEmptyBorder(margin, margin, margin, margin));

        // Create a JScrollPane to provide scrolling if needed
        JScrollPane scrollPane = new JScrollPane(list);
        add(scrollPane, BorderLayout.CENTER);

        JLabel lblNewLabel = new JLabel("LISTE DE CARTE");
        lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        labelPanel.setBackground(new Color(255, 255, 255));
        labelPanel.add(lblNewLabel);
        labelPanel.setOpaque(false);
        scrollPane.setColumnHeaderView(labelPanel);

        // Add a ListSelectionListener to handle selection events
        list.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                // Handle the selection event here
                List<FileListItem> selectedFiles = list.getSelectedValuesList();
                if (!selectedFiles.isEmpty()) {
                    // Do something with the selected files
                    System.out.println("Selected Files:");
                    for (FileListItem selectedFile : selectedFiles) {
                        System.out.println(selectedFile.getFileName());
                    }
                }
            }
        });

        // Footer components
        JButton visualizerButton = new JButton("Visualizer");
        JButton mapOverlayButton = new JButton("Editer");

        JButton editerButton = new JButton("Map Overlay");

        // Create a JPanel to hold the footer components
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.add(visualizerButton);
        footerPanel.add(editerButton);
        footerPanel.add(mapOverlayButton);

        // Create a JPanel to act as a container for the footer
        JPanel footerContainer = new JPanel(new BorderLayout());
        footerContainer.add(footerPanel, BorderLayout.SOUTH);

        // Add the footer container to the BorderLayout.PAGE_END of the JScrollPane
        add(footerContainer, BorderLayout.PAGE_END);

        File directory = new File(PROJECT_DIRECTORY + "/maps/");

        if (directory.isDirectory()) {
            // Get the list of files in the directory
            File[] files = directory.listFiles();

            // Add file names and icons to the DefaultListModel
            if (files != null) {
                for (File file : files) {
                    if (!file.isDirectory()) {
                        // Resize the icon and add it to the list model
                        ImageIcon icon = new ImageIcon(new ImageIcon(PROJECT_DIRECTORY + "/src/images/icon.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
                        listModel.addElement(new FileListItem(file.getName(), icon));
                    }
                }
            }
        }
    }

    private static class FileListItem {
        private final String fileName;
        private final ImageIcon icon;

        public FileListItem(String fileName, ImageIcon icon) {
            this.fileName = fileName;
            this.icon = icon;
        }

        public String getFileName() {
            return fileName;
        }

        public ImageIcon getIcon() {
            return icon;
        }

        @Override
        public String toString() {
            return fileName;
        }
    }

    private static class FileListRenderer extends JPanel implements ListCellRenderer<FileListItem> {
        private final JLabel iconLabel;
        private final JLabel nameLabel;

        private static final int MARGIN = 10;

        public FileListRenderer() {
            setLayout(new BorderLayout());
            iconLabel = new JLabel();
            nameLabel = new JLabel();
            nameLabel.setHorizontalAlignment(JLabel.CENTER);
            add(iconLabel, BorderLayout.CENTER);
            add(nameLabel, BorderLayout.SOUTH);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends FileListItem> list, FileListItem value,
                                                      int index, boolean isSelected, boolean cellHasFocus) {
            iconLabel.setIcon(value.getIcon());
            nameLabel.setText(value.getFileName());
            nameLabel.setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
            setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());

            // Add margin around each item
            setBorder(BorderFactory.createEmptyBorder(MARGIN, MARGIN, MARGIN, MARGIN));

            return this;
        }
    }

   
}
