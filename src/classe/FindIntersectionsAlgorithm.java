package classe;

import java.util.List;
import java.util.Set;

public class FindIntersectionsAlgorithm {
	EventEnqueue eventEnqueue;
	StatusStructure statusStructure;
	public FindIntersectionsAlgorithm() {
		this.eventEnqueue = new EventEnqueue();
        this.statusStructure = new StatusStructure();
	}
	
	
	 public  void findIntersections(Set<LineSegment> segments) {
	       
	        // Step 1: Initialize event queue
	        for (LineSegment segment : segments) {
	            EventPoint startEvent = new EventPoint(segment.getStartPoint().getX(),segment.getStartPoint().getY());
	           EventPoint endEvent = new EventPoint(segment.getEndPoint().getX(),segment.getEndPoint().getY());
	            
	            this.eventEnqueue.insertEvent(startEvent, segment);
	           this.eventEnqueue.insertEvent(endEvent, segment);
	        }

	      /*  // Step 2: Process events
	        while (!this.eventEnqueue.isEmpty()) {
	            EventPoint eventPoint = this.eventEnqueue.findMin();
	            this.handleEventPoint(eventPoint, this.eventEnqueue,this.statusStructure );
	        }*/
	    }


	private void handleEventPoint(EventPoint eventPoint, EventEnqueue eventEnqueue, StatusStructure statusStructure) {
		
		
	}

	 	
}
