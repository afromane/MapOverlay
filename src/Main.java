
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import classe.EventPoint;
import classe.EventQueueAVL;
import classe.FindIntersectionsAlgorithm;
import classe.LineSegment;
import classe.StatusStructure;
import ui.MainScreen;
public class Main {


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainScreen frame = new MainScreen();

	              				 //frame.pack();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		/*Set<LineSegment> segments =  loadSegments("C:\\Users\\Manel Issa\\Desktop\\MapOverlay\\cartes\\fichier2.txt");
		
		FindIntersectionsAlgorithm  findIntersect = new FindIntersectionsAlgorithm();
		
		findIntersect.findIntersections(segments); 
		findIntersect.reportIntersection.inOrderTraversal();*/
		
	}
	public static Set<LineSegment> loadSegments(String filePath) {
		Set<LineSegment> segments = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] coordinates = line.split(" ");
                if (coordinates.length == 4) {

                    double x1 = Double.parseDouble(coordinates[0]);
                    double y1 = Double.parseDouble(coordinates[1]);
                    double x2 = Double.parseDouble(coordinates[2]);
                    double y2 = Double.parseDouble(coordinates[3]);
                    EventPoint startPoint = new EventPoint(x1,y1) ;
                    EventPoint endPoint = new EventPoint(x2,y2)  ;
                    segments.add(new LineSegment(startPoint, endPoint));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return segments;
    }
	
	
	
	

}
