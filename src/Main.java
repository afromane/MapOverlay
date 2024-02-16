
import java.awt.EventQueue;

import classe.EventEnqueue;
import classe.EventPoint;
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
		
				
		EventEnqueue eventEnqueue = new EventEnqueue();

	        EventPoint event1 = new EventPoint(2.0, 5.0);
	        EventPoint event2 = new EventPoint(6.0, 4.0);
	        
	        EventPoint event3 = new EventPoint(7.0, 8.0);
	        EventPoint event4 = new EventPoint(4.0, 5.0);
	        
	        EventPoint event5 = new EventPoint(7.0, 4.0);
	        EventPoint event6 = new EventPoint(2.0, 5.0);
	        
	        EventPoint event7 = new EventPoint(6.0, 4.0);
	        EventPoint event8 = new EventPoint(7.0, 8.0);
	        
	        EventPoint event9 = new EventPoint(4.0, 5.0);
	        EventPoint event10 = new EventPoint(7.0, 4.0);
	        EventPoint event11 = new EventPoint(7.0, 4.0);
	        
	        LineSegment segment1 = new LineSegment(event1,event2);
	        LineSegment segment2 = new LineSegment(event3,event4);
	        LineSegment segment3 = new LineSegment(event5,event6);
	        LineSegment segment4 = new LineSegment(event7,event8);
	        LineSegment segment5 = new LineSegment(event9,event10);
	        LineSegment segment6 = new LineSegment(event10,event11);
	       

	        eventEnqueue.insertEvent(event1, segment1);
	        eventEnqueue.insertEvent(event2, segment1);
	        eventEnqueue.insertEvent(event3, segment2);
	        eventEnqueue.insertEvent(event4, segment2);
	        eventEnqueue.insertEvent(event5, segment3);
	        
	        


	        System.out.println("Event Points in Order:");
	        eventEnqueue.inOrderTraversal();
	        
	        EventPoint pointEvt = eventEnqueue.findMin();
	        System.out.println("\nNext Event: (" + pointEvt.getX() + ", " + pointEvt.getY() + ")");
	         System.out.println("Removing a node:");
	        eventEnqueue.deleteEvent(pointEvt);
	        System.out.println("New Event Points in Order:");
	        eventEnqueue.inOrderTraversal();
		/*   
		// Création d'une instance de la structure de statut AVL
        StatusStructure statusStructure = new StatusStructure();

     	Création de quelques segments pour l'exemple
        LineSegment segment1 = new LineSegment(new EventPoint(1, 2), new EventPoint(3, 4));
        LineSegment segment2 = new LineSegment(new EventPoint(2, 3), new EventPoint(5, 6));
        LineSegment segment3 = new LineSegment(new EventPoint(4, 5), new EventPoint(6, 7));
        LineSegment segment4 = new LineSegment(new EventPoint(0, 1), new EventPoint(2, 3));

        // Insertion des segments dans la structure AVL
        statusStructure.insertSegment(segment1);
        statusStructure.insertSegment(segment2);
        //statusStructure.insertSegment(segment3);
        //statusStructure.insertSegment(segment4);

        // Affichage des feuilles et des nœuds internes en parcours in-order
        System.out.println("Affichage des feuilles et des noeuds internes :");
        statusStructure.printLeavesAndInternals();*/
	        
	       
	        
	}

}
