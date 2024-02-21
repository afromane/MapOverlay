package classe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class FindIntersectionsAlgorithm {
	public EventQueueAVL eventqueue;
	StatusStructure statusStructure;
    public List<LineSegment> segments = new ArrayList<>();
	

	public FindIntersectionsAlgorithm() {
		this.eventqueue = new EventQueueAVL();
	    this.statusStructure = new StatusStructure();
	}
	public  List<LineSegment> getIntersectingSegments(EventPoint sweepLinePoint) {
    List<LineSegment> intersectingSegments = new ArrayList<>();

    for (LineSegment segment : this.segments) {
        if (segment.intersectsSweepLine(sweepLinePoint)) {
            intersectingSegments.add(segment);
        }
    }
    // Trier les segments dans l'ordre croissant
        Collections.sort(intersectingSegments);

        return intersectingSegments;	
}
	

	 public  void findIntersections(Set<LineSegment> segments) {
		 // stockage des segment dans une liste
		 this.segments = new ArrayList<>(segments);
		
	       
        // Etape 1: Initialisaion de la structure  event queue
        for (LineSegment segment : segments) {       	
            this.eventqueue.insertEvent(segment.getStartPoint(), segment);
            this.eventqueue.insertEvent(segment.getEndPoint(), segment);
        }

        
       // Etape 2: Traitement des point d'evenements
        int i = 0;
       while (!this.eventqueue.isEmpty() && i<2) {
            EventPoint eventPoint = this.eventqueue.findMin();
            System.out.println("Event point "+ eventPoint.toString());
            Set<LineSegment> pSegment = this.eventqueue.findMinAllSegment();
            //Insertion du noeud interne
            LineSegment firstSegment = pSegment.stream().findFirst().orElse(null);
            //System.out.println("Segment startPoint " + firstSegment.getStartPoint().toString()+ " segments end point : "+ firstSegment.getEndPoint().toString());
            this.statusStructure.insertSegment(firstSegment, true);
         // Ensemble des segment qui coupe la ligne balayage au Event point p
            Set<LineSegment> segmentIntersects = segementIntersectsSweepLine(eventPoint);
            //Insertion de tout les segment qui intersert la ligne de balayage
            for (LineSegment segment : segmentIntersects) {
            		this.statusStructure.insertSegment(segment, false);
            }
    
            this.statusStructure.printLeavesAndInternals();
            
            
           // System.out.println("Event point" + eventPoint.toString() + " segments : "+segmentIntersects.size());
            
           
            
            
            
            this.eventqueue.deleteEvent(eventPoint); 	
            this.handleEventPoint(eventPoint);
            System.out.println("--------------------------" );
            i++;
        }
    }
 	

	
	// Méthode pour gérer un point d'événement
    public void handleEventPoint(EventPoint p) {
        // Étape 1:  U(p) -Ensemble des segments dont l'extrémité supérieure est p ou inferieur cas segments horizontaux
    	Set<LineSegment> upperSegments = getUpperSegments(p);
    	//System.out.println("Event point" + p.toString() + " segments : "+upperSegments.size());
         

        // Étape 2: Rechercher tous les segments stockés dans T qui contiennent p
        // L(p) -Ensemble segments trouvés dont l'extrémité inferieur est p et C(p) -Ensemble segments trouvés contenant p dans les noeud interne
        Set<LineSegment> lowerSegments = new HashSet<>();
        Set<LineSegment> containingSegments = new HashSet<>();
        this.statusStructure.findSegmentsContainingP(p, lowerSegments, containingSegments);
        //System.out.println("Event point" + p.toString() + " lowerSegments : "+lowerSegments.size());
        //System.out.println("Event point" + p.toString() + " containingSegments : "+containingSegments.size());

        // Étape 3: Vérifie si L(p) ∪ U(p) ∪ C(p) contient plus d'un segment
        Set<LineSegment> allSegments = new HashSet<>();
        allSegments.addAll(lowerSegments);
        allSegments.addAll(upperSegments);
        allSegments.addAll(containingSegments);

        if (allSegments.size() > 1) {
        	
            // Étape 4: Rapporter l'intersection avec L(p), U(p) et C(p)
          //  reportIntersection(p, lowerSegments, upperSegments, containingSegments);

            // Étape 5: Supprimer les segments dans L(p) ∪ C(p) de T
           // deleteSegmentsFromT(lowerSegments, containingSegments);

            // Étape 6: Insérer les segments dans U(p) ∪ C(p) dans T
          //  insertSegmentsIntoT(upperSegments, containingSegments);

            // Étape 7: (∗ Deleting and re-inserting the segments of C(p) reverses their order. ∗)
          //  deleteAndReinsertSegmentsOfCp(containingSegments);
        }
        //System.out.println("Event point" + p.toString() + " upperSegments : "+upperSegments.size());
        //System.out.println("Event point" + p.toString() + " containingSegments : "+containingSegments.size());
        
        // Étape 8: Vérifier si U(p) ∪ C(p) ≠ ∅
        if (!upperSegments.isEmpty() || !containingSegments.isEmpty()) {
            // Étape 9: Trouver les voisins sl et sr de p dans T
            LineSegment sl = this.statusStructure.findLeftNeighbor(p);
            LineSegment sr = this.statusStructure.findRightNeighbor(p);
            if( sl!=null && sr !=null) {
            	 // System.out.println("Voisin gauche " + sl.getStartPoint().toString()+ " --- "+ sl.getEndPoint().toString());
                //System.out.println("Voisin droit " + sr.getStartPoint().toString()+ " --- "+ sr.getEndPoint().toString());

                // Étape 10: Trouver de nouveaux événements avec les voisins sl et sr
                //findNewEvent(sl, sr, p);

            }
                 } 
       /*
        else {
            // Étape 11: Trouver le segment le plus à gauche de U(p) ∪ C(p) dans T
          //  LineSegment leftmostSegment = findLeftmostSegment(upperSegments, containingSegments);

            // Étape 12: Trouver le voisin de gauche sl du segment le plus à gauche
           StatusNode sl = findLeftNeighbor(leftmostSegment);

            // Étape 13: Trouver de nouveaux événements avec sl et le segment le plus à gauche
           findNewEvent(sl, leftmostSegment, p);

            // Étape 14: Trouver le segment le plus à droite de U(p) ∪ C(p) dans T
            LineSegment rightmostSegment = findRightmostSegment(upperSegments, containingSegments);

            // Étape 15: Trouver le voisin de droite sr du segment le plus à droite
            StatusNode sr = findRightNeighbor(rightmostSegment);

            // Étape 16: Trouver de nouveaux événements avec le segment le plus à droite et sr
            findNewEvent(rightmostSegment, sr, p);*/
        }
    
	// Fonction pour obtenir l'ensemble des segments dont l'extrémité supérieure est p  et inferieur pour les segments horizontaux
	private  Set<LineSegment> getUpperSegments( EventPoint p) {
		Set<LineSegment> upperSegments = new HashSet<>();
		for (LineSegment segment : this.segments) {
	         // Vérifie si le segment est horizontal
	    	if(segment.isHorizontal()) {
	    		if (segment.getStartPoint().compareTo(p) == 0) {
		            upperSegments.add(segment);
		        }
	    	}
	    	else {
	    		if (segment.getEndPoint().compareTo(p) == 0) {
		            upperSegments.add(segment);
		        }
	    	}
	        
	    }
	    return upperSegments;
	}
	
	// Rechercher l'enseblement des segments qui coupe la ligne de balayage
	
	private  Set<LineSegment> segementIntersectsSweepLine( EventPoint p) {
		Set<LineSegment> upperSegments = new HashSet<>();
		for (LineSegment segment : this.segments) {
	         // Vérifie si le segment est horizontal
	    	if(segment.intersectsSweepLine(p)) {
	    		 upperSegments.add(segment);
	    	}
	        
	    }
	    return upperSegments;
	}
}
