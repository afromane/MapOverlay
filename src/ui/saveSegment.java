package ui;

import javax.swing.*;
import javax.swing.text.NumberFormatter;

import classe.EventPoint;
import classe.LineSegment;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashSet;
import java.util.Set;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
public class SaveSegment extends JDialog {

    private static final long serialVersionUID = 1L;
    private JTextField y1;
    private JTextField x1;
    private JTextField x2;
    private JTextField y2;
    private Set<LineSegment> segments = new HashSet<>();
    private JLabel lblCount = new JLabel("");

	 private static final String PROJECT_DIRECTORY = System.getProperty("user.dir");
	 private String destinationFolderPath = PROJECT_DIRECTORY + File.separator + "maps";
    public SaveSegment() {
        setTitle("Enregistrer Nouvelle carte");
        setModal(true);
        getContentPane().setLayout(null);

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        inputPanel.setBounds(0, 0, 384, 178);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        getContentPane().add(inputPanel);

        x1 = new JTextField();
        inputPanel.add(new JLabel("x1"));
        inputPanel.add(x1);

        y1 = createFormattedTextField();
        inputPanel.add(new JLabel("y1"));
        inputPanel.add(y1);

        x2 = new JTextField();
        inputPanel.add(new JLabel("x2"));
        inputPanel.add(x2);

        y2 = new JTextField();
        inputPanel.add(new JLabel("y2"));
        inputPanel.add(y2);
                inputPanel.add(lblCount);
        
                lblCount.setFont(new Font("Tahoma", Font.PLAIN, 16));
                lblCount.setText("Total segment : " + segments.size());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(0, 178, 384, 33);
        getContentPane().add(buttonPanel);

        JButton btnAddSegment = new JButton("Ajout Segment");
        btnAddSegment.setBounds(26, 5, 129, 23);
        btnAddSegment.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String _x1 = x1.getText();
                String _y1 = y1.getText();
                String _x2 = x2.getText();
                String _y2 = y2.getText();

                try {
                    double x1Value = Double.parseDouble(_x1);
                    double y1Value = Double.parseDouble(_y1);
                    double x2Value = Double.parseDouble(_x2);
                    double y2Value = Double.parseDouble(_y2);

                    segments.add(new LineSegment(new EventPoint(x1Value, y1Value), new EventPoint(x2Value, y2Value)));
                    lblCount.setText("Total segment : " + segments.size());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(SaveSegment.this, "Veuillez saisir des nombres valides.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonPanel.setLayout(null);
        buttonPanel.add(btnAddSegment);
        
        JButton btnEnregistrerCarte = new JButton("Enregistrer carte");
        btnEnregistrerCarte.setBounds(165, 5, 181, 23);
        btnEnregistrerCarte.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                enregistrerCarte();
            }
        });
        buttonPanel.add(btnEnregistrerCarte);

        setPreferredSize(new Dimension(400, 250));
        pack();
        setLocationRelativeTo(null);
    }

    private JTextField createFormattedTextField() {
        NumberFormat doubleFormat = DecimalFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(doubleFormat) {
            @Override
            public Object stringToValue(String text) throws java.text.ParseException {
                if (text.isEmpty()) {
                    return null;
                }
                return super.stringToValue(text);
            }
        };
        formatter.setValueClass(Double.class);
        formatter.setMinimum(0.0);
        formatter.setMaximum(Double.MAX_VALUE);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);
        JFormattedTextField textField = new JFormattedTextField(formatter);
        textField.setColumns(10);
        textField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == ',') {
                    e.setKeyChar('.');
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        return textField;
    }
    private void enregistrerCarte() {
        String carteName = JOptionPane.showInputDialog(this, "Entrez le nom de la carte:");
        if (carteName == null || carteName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez saisir un nom de carte valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (FileWriter writer = new FileWriter(destinationFolderPath+"/"+carteName + ".txt",true)) {
            for (LineSegment segment : segments) {
                writer.write(segment.getStartPoint().getX() +" "+segment.getStartPoint().getY()+" "+segment.getEndPoint().getX() +" "+segment.getEndPoint().getY() +"\n");
            }
             x1.setText("");
            y1.setText("");
            x2.setText("");
             y2.setText("");
             this.segments.clear();
            JOptionPane.showMessageDialog(this, "Segments enregistrés avec succès dans " + carteName + ".txt", "Succès", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de l'enregistrement des segments.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
   
}
