package ui;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class LeftPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private MainScreen mainScreen;  // Reference to the main screen
	 private static final String PROJECT_DIRECTORY = System.getProperty("user.dir");


	/**
	 * Create the panel.
	 */
	public LeftPanel(MainScreen mainScreen) {
		
		
		 this.mainScreen = mainScreen; 
		setLayout(null);
		
		JButton btnCharger = new JButton("Charger une carte");
		btnCharger.setFont(new Font("Viner Hand ITC", Font.PLAIN, 20));
		btnCharger.setBounds(10, 209, 258, 41);
		btnCharger.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                showFileChooser();
	            }
	        });
		add(btnCharger);
		
		JButton btnSaisie = new JButton("Saisie  nouvelle carte");
		btnSaisie.setFont(new Font("Viner Hand ITC", Font.PLAIN, 20));
		btnSaisie.setBounds(10, 261, 258, 41);
		
		btnSaisie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	SaveSegment blockingDialog = new SaveSegment();
                blockingDialog.setLocationRelativeTo(null);
                blockingDialog.setVisible(true);
            }
        });
		add(btnSaisie);
		
		JButton btnAfficherUneCarte = new JButton("Afficher une carte");
		btnAfficherUneCarte.setToolTipText("Double clique");
		btnAfficherUneCarte.setFont(new Font("Viner Hand ITC", Font.PLAIN, 20));
		btnAfficherUneCarte.setBounds(10, 313, 258, 41);
		
		
		btnAfficherUneCarte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	MapsList blockingDialog  =new MapsList();
                blockingDialog.setLocationRelativeTo(null);
                blockingDialog.setVisible(true);
               // showFileChooser();
            	
            	/* SwingUtilities.invokeLater(new Runnable() {
                     @Override
                     public void run() {
                    	 JPanel newMapPanel = new MapsList();
                         
                     	mainScreen.replaceRightPanel(newMapPanel, "MapsList");                    
                     }
                 });*/
            }
        });
		add(btnAfficherUneCarte);
		
		JLabel lblNewLabel = new JLabel("Menu");
		lblNewLabel.setFont(new Font("Viner Hand ITC", Font.BOLD, 30));
		lblNewLabel.setBounds(80, 120, 114, 41);
		add(lblNewLabel);
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{btnCharger, btnSaisie, btnAfficherUneCarte, lblNewLabel}));

	}
	private void showFileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
           // filePathField.setText(selectedFile.getAbsolutePath());
            System.out.println(selectedFile.getAbsolutePath());
            this.upload(selectedFile.getAbsoluteFile());
        }
    }
	private void upload(File fileToCopy) {
        // Specify the destination folder within your local project directory
        String destinationFolderPath = PROJECT_DIRECTORY + File.separator + "maps";

        try {
            Path sourcePath = fileToCopy.toPath();
            Path destinationPath = Paths.get(destinationFolderPath, fileToCopy.getName());

            // Create the destination folder if it doesn't exist
            Files.createDirectories(destinationPath.getParent());

            // Copy the file to the specified destination folder
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("File copied successfully to: " + destinationPath);
            JOptionPane.showMessageDialog(this, "Carte chargé"+ fileToCopy.getName() +" avec succès dans ", "Succès", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement de la carte.", "Erreur", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error copying file: " + e.getMessage());
        }
    }

}
