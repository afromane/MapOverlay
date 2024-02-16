package classe;

//Class representing an event point
public class EventPoint implements Comparable<EventPoint> {
 private double x, y;

 public EventPoint(double x, double y) {
     this.x = x;
     this.y = y;
 }

 public double getX() {
     return x;
 }

 public double getY() {
     return y;
 }
 public String toString() {
	 return "("+this.getX()+";"+this.getY() +")";
 }

 @Override
 public int compareTo(EventPoint other) {
	 if ((this.getY() > other.getY())  || (( this.getY() == other.getY()) && this.getX() < other.getX())) {
         return -1; // this ≺ other
     } else if (this.getY() == other.getY() && this.getX() == other.getX()) {
         return 0; // this and other are equal
     } else {
         return 1; // this ≻ other
     }
 }
}
