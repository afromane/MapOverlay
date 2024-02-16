package classe;


//Classe pour représenter un nœud AVL qui stocke des segments
class StatusNode {
	 LineSegment segment;
	 StatusNode left, right;
	 int height;

	 public StatusNode(LineSegment segment) {
	     this.segment = segment;
	     this.height = 1;
	 }
	
	 public boolean isLeaf() {
	     return (left == null && right == null);
	 }
	}

	//Classe principale pour l'arbre AVL utilisé comme "Structure de statut"
	public class StatusStructure {
	 private StatusNode root;
	
	 // Fonction pour obtenir la hauteur d'un nœud (gère les cas null)
	 private int height(StatusNode node) {
	     return (node == null) ? 0 : node.height;
	 }
	
	 // Fonction pour effectuer une rotation simple à droite
	 private StatusNode rotateRight(StatusNode y) {
	     StatusNode x = y.left;
	     StatusNode T2 = x.right;
	
	     x.right = y;
	     y.left = T2;
	
	     updateHeight(y);
	     updateHeight(x);
	
	     return x;
	 }

	 // Fonction pour effectuer une rotation simple à gauche
	 private StatusNode rotateLeft(StatusNode x) {
	     StatusNode y = x.right;
	     StatusNode T2 = y.left;
	
	     y.left = x;
	     x.right = T2;
	
	     updateHeight(x);
	     updateHeight(y);
	
	     return y;
	 }
	
	 // Fonction pour obtenir le facteur d'équilibre d'un nœud
	 private int getBalance(StatusNode node) {
	     return (node == null) ? 0 : height(node.left) - height(node.right);
	 }
	
	 // Fonction pour mettre à jour la hauteur d'un nœud
	 private void updateHeight(StatusNode node) {
	     if (node != null) {
	         node.height = 1 + Math.max(height(node.left), height(node.right));
	     }
	 }
	
	 // Fonction pour afficher les nœuds par inorder
	 public void printLeavesAndInternals() {
	     printLeavesAndInternals(root);
	 }
	
	 private void printLeavesAndInternals(StatusNode node) {
	     if (node != null) {
	         // Parcours en ordre : gauche, nœud, droit
	         printLeavesAndInternals(node.left);
	         	if(node.isLeaf()) {
	         		System.out.println("feuille : " + node.segment.getStartPoint().toString() + "-" + node.segment.getEndPoint().toString());
	         	}
	         	else
	         	{
	         		System.out.println("internal node : " + node.segment.getStartPoint().toString() + "-" + node.segment.getEndPoint().toString());
	         	}
	         		 
	         printLeavesAndInternals(node.right);
	     }
	 }

	 // Fonction pour insérer un nouveau segment dans la structure de statut
	 public void insertSegment(LineSegment newSegment) {
	     root = insertSegmentHelper(root, newSegment);
	 }

	 private StatusNode insertSegmentHelper(StatusNode node, LineSegment newSegment) {
	     // Insérer comme dans un BST
	     if (node == null) {
	         // Créer le nœud avec le nouveau segment
	         StatusNode newNode = new StatusNode(newSegment);
	
	         // Créer le nœud fils gauche avec la même valeur que le nœud parent
	         newNode.left = new StatusNode(newSegment);
	
	         return newNode;
	     }
	
	     if (newSegment.comparaisonSegment(node.segment) < 0) {
	         node.left = insertSegmentHelper(node.left, newSegment);
	     } else if (newSegment.comparaisonSegment(node.segment) > 0) {
	         node.right = insertSegmentHelper(node.right, newSegment);
	     } else {
	         // Les segments sont égaux, gestion facultative selon les besoins
	     }
	
	     // Mettre à jour la hauteur du nœud actuel
	     updateHeight(node);
	
	     // Équilibrer l'arbre
	     int balance = getBalance(node);
	
	     // Rotation simple à droite
	     if (balance > 1 && newSegment.comparaisonSegment(node.left.segment) < 0) {
	         return rotateRight(node);
	     }
	
	     // Rotation simple à gauche
	     if (balance < -1 && newSegment.comparaisonSegment(node.right.segment) > 0) {
	         return rotateLeft(node);
	     }
	
	     // Rotation double à gauche-droite
	     if (balance > 1 && newSegment.comparaisonSegment(node.left.segment) > 0) {
	         node.left = rotateLeft(node.left);
	         return rotateRight(node);
	     }
	
	     // Rotation double à droite-gauche
	     if (balance < -1 && newSegment.comparaisonSegment(node.right.segment) < 0) {
	         node.right = rotateRight(node.right);
	         return rotateLeft(node);
	     }
	
	     return node;
	 }
}
