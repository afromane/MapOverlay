package ui;


import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class MainScreen extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel leftPanel;
    private JPanel rightPanel;
    private CardLayout cardLayout;
    private JSplitPane splitPane;
	JFileChooser fileChooser = new JFileChooser();
	
	ArrayList<Double>  arrListOfX1 = new ArrayList<Double>();  // String[] arrOfStr = str.split("@", 2);
	ArrayList<Double>  arrListOfX2 = new ArrayList<Double>() ; 
	ArrayList<Double>  arrListOfY1 = new ArrayList<Double>(); 
	ArrayList<Double>  arrListOfY2 = new ArrayList<Double>() ; 
	 File selectedFile;
	 String selectedFilePath = "C:\\Users\\Manel Issa\\Desktop\\MapOverlay\\cartes\\fichier2.txt";

	/**
	 * Create the frame.
	 */
	public MainScreen() {
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		this.setTitle("Map overlay");
		this.setSize(1000,800);
		this.setLocationRelativeTo(null);
		// Create components for the panes
         leftPanel = new LeftPanel(this);
         rightPanel = new RightPanel();
         
         cardLayout = new CardLayout();
         rightPanel.setLayout(cardLayout);
         splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        

        //  width of the left component
        splitPane.setDividerLocation(300);
        splitPane.setDividerSize(0);

        splitPane.setEnabled(false);

        // Add the split pane to the frame
        this.getContentPane().add(splitPane);
		
		
	}
	
	 public void replaceRightPanel(JPanel newPanel, String panelName) {
        rightPanel.add(newPanel, panelName);
        cardLayout.show(rightPanel, panelName);
    }
	
	
	
}
