
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
		/*EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainScreen frame = new MainScreen();

	              				 //frame.pack();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});*/
		
		/*Set<LineSegment> segments =  loadSegments("C:\\Users\\Manel Issa\\Desktop\\MapOverlay\\cartes\\fichier2.txt");
		
		/*for(LineSegment segment: segments) {
			System.out.printf("Start point" + segment.getStartPoint().toString() + " End point" + segment.getEndPoint().toString());
		}*/
		/*
		FindIntersectionsAlgorithm  findIntersect = new FindIntersectionsAlgorithm();
		findIntersect.findIntersections(segments);
		EventPoint pvt = findIntersect.eventqueue.findMin();
		System.out.printf(" point" + pvt.toString());*/
		
		/*LineSegment segment1 =  new LineSegment(new EventPoint(2,4),new EventPoint(3.5,6.0));
		LineSegment segment2 =  new LineSegment(new EventPoint(6.5,4.0), new EventPoint(5.0,7));
		LineSegment segment3 =  new LineSegment(new EventPoint(4.5,4),new EventPoint(3,1.5));
		LineSegment segment4 =  new LineSegment(new EventPoint(3,3),new EventPoint(2,1));*/
		
		LineSegment segment1 =  new LineSegment(new EventPoint(4.5,9),new EventPoint(3,6.5));
		LineSegment segment2 =  new LineSegment(new EventPoint(5.5,9.0), new EventPoint(4.5,6.5));
		LineSegment segment3 =  new LineSegment(new EventPoint(6.5,9),new EventPoint(8,6));
		LineSegment segment4 =  new LineSegment(new EventPoint(6,6),new EventPoint(4,3.5));
		LineSegment segment5 =  new LineSegment(new EventPoint(2.5,3.5),new EventPoint(4.5,9));
		LineSegment segment6 =  new LineSegment(new EventPoint(4.5,10),new EventPoint(5.5,9));
		
		Set<LineSegment> segments = new HashSet<LineSegment>();
		segments.add(segment1);
		segments.add(segment2);
		segments.add(segment3);
		segments.add(segment4);
		segments.add(segment5);
		segments.add(segment6);
		
		FindIntersectionsAlgorithm  findIntersect = new FindIntersectionsAlgorithm();
		
		findIntersect.findIntersections(segments); 
		findIntersect.reportIntersection.inOrderTraversal();
		
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
