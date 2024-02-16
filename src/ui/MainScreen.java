package ui;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.BorderLayout;
import java.awt.Color;

public class MainScreen extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	JFileChooser fileChooser = new JFileChooser();
	
	ArrayList<Double>  arrListOfX1 = new ArrayList<Double>();  // String[] arrOfStr = str.split("@", 2);
	ArrayList<Double>  arrListOfX2 = new ArrayList<Double>() ; 
	ArrayList<Double>  arrListOfY1 = new ArrayList<Double>(); 
	ArrayList<Double>  arrListOfY2 = new ArrayList<Double>() ; 
	 File selectedFile;
	 String selectedFilePath = "C:\\Users\\Manel Issa\\Desktop\\del\\MapOverlay\\cartes\\fichier2.txt";

	/**
	 * Create the frame.
	 */
	public MainScreen() {
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		this.setTitle("Map overlay");
		this.setSize(1000,800);
		this.setLocationRelativeTo(null);
		
		//setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(20, 0, 59, 22);
		contentPane.add(menuBar);
		
		JMenu menuCarte = new JMenu("Carte");
		menuBar.add(menuCarte);
		
		JMenuItem menuImporter = new JMenuItem("Importer une carte");
		
		
		menuImporter.addActionListener( new ActionListener() {

			@Override
			 public void actionPerformed(ActionEvent e) {
		        int result = fileChooser.showOpenDialog(null);
		        if (result == JFileChooser.APPROVE_OPTION) {
		           // File selectedFile = fileChooser.getSelectedFile();
		        	 selectedFile = fileChooser.getSelectedFile();
		        	 selectedFilePath =  fileChooser.getSelectedFile().getAbsolutePath();
		        	 System.out.print(selectedFilePath);
		            // Print chemin d'acces du ficier
		           // selectedFilePath = selectedFile;
		           
		        }
		    }

		});
		menuCarte.add(menuImporter);
		menuCarte.addSeparator();
		JMenuItem menuView = new JMenuItem("Visualiser carte");
		menuCarte.add(menuView);
		
		//Carte carte = new Carte();
		//carte.setLocation(300, 300);
		//carte.setSize(500, 500);
		//contentPane.add(carte);
        //  this.getContentPane().add(carte, BorderLayout.CENTER);
			/*if(selectedFilePath != null) {
				
			
				
				
			}*/
		Color  color = generateRandomColor();
		MapPanel mapPanel = new MapPanel(selectedFilePath,2,color);
		mapPanel.setLocation(300, 100);
		mapPanel.setSize(500,700);
		
		System.out.print(selectedFilePath);
		contentPane.add(mapPanel);
		  this.getContentPane().add(mapPanel, BorderLayout.CENTER);
	
		
		
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
