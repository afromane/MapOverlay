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
    
    public  EventQueueAVL reportIntersection;
    
	

	public FindIntersectionsAlgorithm() {
		this.eventqueue = new EventQueueAVL();
		this.reportIntersection = new EventQueueAVL();
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
        //Collections.sort(intersectingSegments);

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
       while (!this.eventqueue.isEmpty()  ) {
            EventPoint eventPoint = this.eventqueue.findMin();
            this.eventqueue.deleteEvent(eventPoint); 	
            this.handleEventPoint(eventPoint);
            System.out.println("--------------------------" );
        }
    }
 	

	
    public void handleEventPoint(EventPoint p) {
        // Étape 1:  U(p) -Ensemble des segments dont l'extrémité supérieure est p ou inferieur cas segments horizontaux
    	Set<LineSegment> upperSegments = getUpperSegments(p);
        // Étape 2: Rechercher tous les segments stockés dans T qui contiennent p
        // L(p) -Ensemble segments trouvés dont l'extrémité inferieur est p et C(p) -Ensemble segments trouvés contenant p dans les noeud interne
        Set<LineSegment> lowerSegments = this.statusStructure.findSegmentsWithLowerEndpoint(p);
        Set<LineSegment> containingSegments = this.statusStructure.findSegmentsContainingPoint(p);

        // Étape 3: Vérifie si L(p) ∪ U(p) ∪ C(p) contient plus d'un segment
        Set<LineSegment> allSegments = new HashSet<>();
        allSegments.addAll(lowerSegments);
        allSegments.addAll(upperSegments);
        allSegments.addAll(containingSegments);
        // Étape 4: Rapporter l'intersection avec L(p), U(p) et C(p)
        if (allSegments.size() > 1) {
        	//System.out.println("report : "+allSegments.size());
        	 for(LineSegment segment : allSegments)
             	this.reportIntersection.insertEvent(p, segment);
        }
            
        // Étape 5: Supprimer les segments dans L(p) ∪ C(p) de T
        Set<LineSegment> containingSegmentsAndlowerSegments = new HashSet<>();
        containingSegmentsAndlowerSegments.addAll(lowerSegments);
        containingSegmentsAndlowerSegments.addAll(containingSegments);
        for(LineSegment segment : containingSegmentsAndlowerSegments)
        {
        	this.statusStructure.delete(segment);
        }
        // Étape 6: Insérer les segments dans U(p) ∪ C(p) dans T
        Set<LineSegment> containingSegmentsAndupperSegments = new HashSet<>();
        containingSegmentsAndupperSegments.addAll(upperSegments);
        containingSegmentsAndupperSegments.addAll(containingSegments);
        for(LineSegment segment : containingSegmentsAndupperSegments)
        {
        	this.statusStructure.insert(segment);
        }

        // Étape 7: (∗ Deleting and re-inserting the segments of C(p) reverses their order. ∗)
        containingSegments.clear();
        containingSegments = this.statusStructure.findSegmentsContainingPoint(p);
        
        // Étape 8: Vérifie si U(p) ∪ C(p) ≠ ∅
        if (!upperSegments.isEmpty() || !containingSegments.isEmpty()) {
            // Étape 9: Trouver les voisins sl et sr de p dans T
            LineSegment sl = null;
            LineSegment sr = null;
            this.statusStructure.findNeighbors(p, sl, sr);
        } 
       
        else {
            // Étape 11: Trouver le segment le plus à gauche de U(p) ∪ C(p) dans T
            LineSegment leftmostSegment = this.statusStructure.findLeftmostSegment(p);
            // Étape 12: Trouver le voisin de gauche sl du segment le plus à gauche
           LineSegment sl = this.statusStructure.findLeftNeighborOfLeftmostSegment(p);
            // Étape 13: Trouver de nouveaux événements avec sl et le segment le plus à gauche
           findNewEvent(sl, leftmostSegment, p);
            // Étape 14: Trouver le segment le plus à droite de U(p) ∪ C(p) dans T
           LineSegment rightmostSegment = this.statusStructure.findRightmostSegment(p);

            // Étape 15: Trouver le voisin de droite sr du segment le plus à droite
            LineSegment sr = this.statusStructure.findRightNeighborOfRightmostSegment(p);
            // Étape 16: Trouver de nouveaux événements avec le segment le plus à droite et sr
            findNewEvent(rightmostSegment, sr, p);
        }
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
	
	
	private  Set<LineSegment> segementIntersectsSweepLine( EventPoint p) {
		Set<LineSegment> upperSegments = new HashSet<>();
		for (LineSegment segment : this.segments) {
	    	if(segment.intersectsSweepLine(p)) {
	    		 upperSegments.add(segment);
	    	}
	        
	    }
	    return upperSegments;
	}
	public void findNewEvent(LineSegment sl, LineSegment sr, EventPoint p) {
        if(sl!= null && sr!=null )
        	if ((sl.intersectsBelow(p) || sl.intersectsOnLineToRight(p)) && !isIntersectionEventPresent(sl, sr, p)) {
                insertIntersectionEvent(sl, sr, p);
            }
    }
	public boolean isIntersectionEventPresent(LineSegment sl, LineSegment sr, EventPoint p) {
	    Set<LineSegment> segmentsAtEvent = this.eventqueue.findSegmentsAtEvent(p);
	    return segmentsAtEvent.contains(sl) && segmentsAtEvent.contains(sr);
	}
	public void insertIntersectionEvent(LineSegment sl, LineSegment sr, EventPoint p) {
	    if (!isIntersectionEventPresent(sl, sr, p)) {
	       this.eventqueue.insertEvent(p, sl);
	       this.eventqueue.insertEvent(p, sr);
	    }
	}
}
