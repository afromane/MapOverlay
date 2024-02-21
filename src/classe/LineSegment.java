package classe;

//Classe représentant un segment de ligne avec des points d'événement
public class LineSegment implements Comparable<LineSegment>  {
 private EventPoint startPoint, endPoint;

 public LineSegment(EventPoint startPoint, EventPoint endPoint) {
     this.startPoint = startPoint;
     this.endPoint = endPoint;
 }

 public EventPoint getStartPoint() {
     return startPoint;
 }
 public void setStartPoint(EventPoint startPoint) {
      this.startPoint =startPoint ;
 }
 

 public EventPoint getEndPoint() {
     return endPoint;
 }
 public void setEndPoint(EventPoint endPoint) {
     this.endPoint = endPoint ;
}
 public boolean isHorizontal() {
	 return startPoint.getY() == endPoint.getY() ? true :  false ;
 }
 
 @Override
 public int compareTo(LineSegment other) {
	  // Comparaison basée sur les coordonnées x du point de d'arrive
	 
     return Double.compare(this.endPoint.getX(), other.endPoint.getX());
 }
 // Fonction qui vérifie si le segment intersecte la ligne de balayage au point donné
 public boolean intersectsSweepLine(EventPoint p) {
    
     return (startPoint.getY() <= p.getY() && endPoint.getY() >= p.getY()) ||
            (endPoint.getY() <= p.getY() && startPoint.getY() >= p.getY());
 }

}
