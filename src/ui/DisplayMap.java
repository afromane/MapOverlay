package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class DisplayMap extends JDialog {
    private static final long serialVersionUID = 1L;

    public DisplayMap(List<String> selectedMaps) {

        setTitle("Visualizer Dialog");
        setModal(true);
        setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea();
        for (String map : selectedMaps) {
            textArea.append(map + "\n");
        }

        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        JButton closeButton = new JButton("Fermer");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);
        
        
        List<Color>  colors = new ArrayList<>();
        for(String map : selectedMaps)
        {
        	colors.add(generateRandomColor() );
        }
        
		MapPanel mapPanel = new MapPanel(selectedMaps,2,colors);
        add(mapPanel, BorderLayout.CENTER);

        // Définir la taille de la fenêtre modale
        setPreferredSize(new Dimension(700, 300));

        pack();
    }
    
    
    /*
	 * Generation des couleurs
	 * 
	 * */
	private static Color generateRandomColor() {
        int r = (int) (Math.random() * 256);
        int g = (int) (Math.random() * 256);
        int b = (int) (Math.random() * 256);
        return new Color(r, g, b);
    }
}
