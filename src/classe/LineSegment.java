package classe;

//Classe représentant un segment de ligne avec des points d'événement
public class LineSegment   {
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
 
 
//Method to compare line segments
 public int compare(LineSegment other) {
     // Your comparison logic here
     // You might want to compare based on the start and end points of the line segments
     // Adjust this according to your requirements
     if (this.getStartPoint().getY() > other.getStartPoint().getY() || (this.getStartPoint().getY() == other.getStartPoint().getY() && this.getStartPoint().getX() < other.getStartPoint().getX())) {
         return -1; // this ≺ other
     } else if (this.getStartPoint().getY() == other.getStartPoint().getY() && this.getStartPoint().getX() == other.getStartPoint().getX() && this.getEndPoint().getY() == other.getEndPoint().getY() && this.getEndPoint().getX() == other.getEndPoint().getX()) {
         return 0; // this and other are equal
     } else {
         return 1; // this ≻ other
     }
 }
 // Fonction qui vérifie si le segment intersecte la ligne de balayage au point donné
 public boolean intersectsSweepLine(EventPoint p) {
    
     return (startPoint.getY() <= p.getY() && endPoint.getY() >= p.getY()) ||
            (endPoint.getY() <= p.getY() && startPoint.getY() >= p.getY());
 }

//Méthode pour vérifier si le segment intersecte en dessous de la ligne de balayage au point donné
 public boolean intersectsBelow(EventPoint p) {
	 // Exclure les segments horizontaux
	    if (startPoint.getY() == endPoint.getY()) {
	        return false;
	    }

	    // Vérifier si le segment intersecte en dessous du point d'événement
	    return startPoint.getY() <= p.getY() && endPoint.getY() <= p.getY();
 }

 // Méthode pour vérifier si le segment intersecte sur la ligne et à droite du point donné
 public boolean intersectsOnLineToRight(EventPoint p) {
	// Vérifier si le segment est horizontal
	    if (startPoint.getY() == endPoint.getY()) {
	        // Gérer le cas des segments horizontaux
	        return startPoint.getY() == p.getY() && endPoint.getX() >= p.getX();
	    }

	    // Vérifier si le segment intersecte la ligne de balayage et se trouve à droite du point d'événement
	    return intersectsSweepLine(p) && endPoint.getX() >= p.getX();
 }
}
