package classe;

//Classe représentant un segment de ligne avec des points d'événement
public class LineSegment  {
 private EventPoint startPoint, endPoint;

 public LineSegment(EventPoint startPoint, EventPoint endPoint) {
     this.startPoint = startPoint;
     this.endPoint = endPoint;
 }

 public EventPoint getStartPoint() {
     return startPoint;
 }

 public EventPoint getEndPoint() {
     return endPoint;
 }
 public boolean EstHorizontal() {
	 return startPoint.getY() == endPoint.getY() ? true :  false ;
 }
 public  int  comparaisonSegment(LineSegment other)
 {
	 if(this.startPoint.getX() <= other.startPoint.getX())
		 return -1;
	 else 
		 return 1;
 }

}
