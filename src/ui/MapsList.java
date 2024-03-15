

package ui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
public class MapsList extends JDialog {

    private static final long serialVersionUID = 1L;
    private static final String PROJECT_DIRECTORY = System.getProperty("user.dir");
    private static final int MARGIN = 10;

    private DefaultListModel<FileListItem> listModel = new DefaultListModel<>();
    private JList<FileListItem> list;
    private List<String> selectedMaps;

    public MapsList() {
        setModal(true);
        setTitle("Liste des cartes");
        setLayout(new BorderLayout());
        selectedMaps = new ArrayList<>();

        initializeList();

        add(createScrollPane(), BorderLayout.CENTER);
        add(createFooterPanel(), BorderLayout.PAGE_END);

        setPreferredSize(new Dimension(700, 500));
        pack();
        setLocationRelativeTo(null);
    }

    private void initializeList() {
        File directory = new File(PROJECT_DIRECTORY + File.separator + "maps");

        if (directory.isDirectory()) {
            File[] files = directory.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (!file.isDirectory()) {
                        ImageIcon icon = new ImageIcon(new ImageIcon(PROJECT_DIRECTORY + File.separator + "src/images/icon.png")
                                .getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
                        listModel.addElement(new FileListItem(file.getName(), icon));
                    }
                }
            }
        }
    }

    private JScrollPane createScrollPane() {
        list = new JList<>(listModel);
        list.setLayoutOrientation(JList.VERTICAL_WRAP);
        list.setCellRenderer(new FileListRenderer());
        list.setFont(new Font("Viner Hand ITC", Font.PLAIN, 17));
        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        
        list.setBorder(BorderFactory.createEmptyBorder(MARGIN, MARGIN, MARGIN, MARGIN));
        // Use a DefaultListSelectionModel that supports checking
        list.setSelectionModel(new DefaultListSelectionModel() {
            @Override
            public void setSelectionInterval(int index0, int index1) {
                if (super.isSelectedIndex(index0)) {
                    super.removeSelectionInterval(index0, index1);
                } else {
                    super.addSelectionInterval(index0, index1);
                }
            }
        });

        list.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                List<FileListItem> selectedFiles = list.getSelectedValuesList();
                selectedMaps.clear();
                for (FileListItem selectedFile : selectedFiles) {
                    selectedMaps.add(PROJECT_DIRECTORY + File.separator + "maps" + File.separator + selectedFile.getFileName());
                }
            }
        });

        return new JScrollPane(list);
    }

    private JPanel createFooterPanel() {
        JButton visualizerButton = createButton("Visualizer", e -> {
            if (!selectedMaps.isEmpty()) {
                System.out.println("Selected Maps:");
                showBlockingDialog(selectedMaps);
            } else {
                System.out.println("Aucune carte sélectionnée.");
            }
        });

        JButton editerButton = createButton("Editer", e -> {
        	
        	for(String file : this.selectedMaps) {
        		File fichier = new File(file);
                
                if (fichier.exists() && Desktop.isDesktopSupported()) {
                    try {
                        Desktop.getDesktop().edit(fichier);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {

                    JOptionPane.showMessageDialog(this, "Le fichier n'existe pas ou Desktop n'est pas pris en charge.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    System.out.println("Le fichier n'existe pas ou Desktop n'est pas pris en charge.");
                }
        	}
            
        });

        JButton mapOverlayButton = createButton("Map Overlay", e -> {
            // Handle Map Overlay button action
        });

        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.add(visualizerButton);
        footerPanel.add(editerButton);
        footerPanel.add(mapOverlayButton);

        JPanel footerContainer = new JPanel(new BorderLayout());
        footerContainer.add(footerPanel, BorderLayout.SOUTH);

        return footerContainer;
    }

    private JButton createButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.addActionListener(actionListener);
        return button;
    }

    private void showBlockingDialog(List<String> selectedMaps) {
        DisplayMap blockingDialog = new DisplayMap(selectedMaps);
        blockingDialog.setLocationRelativeTo(this);
        blockingDialog.setVisible(true);
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
        private final JCheckBox checkBox;
        private final JLabel iconLabel;
        private final JLabel nameLabel;

        public FileListRenderer() {
            setLayout(new BorderLayout());
            checkBox = new JCheckBox();
            checkBox.setOpaque(false);
            iconLabel = new JLabel();
            nameLabel = new JLabel();
            nameLabel.setHorizontalAlignment(JLabel.CENTER);
            add(checkBox, BorderLayout.WEST);
            add(iconLabel, BorderLayout.CENTER);
            add(nameLabel, BorderLayout.SOUTH);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends FileListItem> list, FileListItem value,
                                                      int index, boolean isSelected, boolean cellHasFocus) {
            checkBox.setSelected(list.isSelectedIndex(index));
            iconLabel.setIcon(value.getIcon());
            nameLabel.setText(value.getFileName());
            nameLabel.setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
            setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());

            setBorder(BorderFactory.createEmptyBorder(MARGIN, MARGIN, MARGIN, MARGIN));

            return this;
        }
    }

   
}
