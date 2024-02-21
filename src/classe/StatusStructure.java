package classe;

import java.util.Set;

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
	
	// Fonction pour verifie si un segment est dans la feuille
		 public boolean segmentIsLeaft(LineSegment segment) {
		     return segmentIsLeaft(root,segment);
		 }
		
		 private boolean segmentIsLeaft(StatusNode node,LineSegment segment) {
		     if (node != null) {
		         // Parcours en ordre : gauche, nœud, droit
		    	 segmentIsLeaft(node.left,segment);
		         	if(node.isLeaf()) {
		         		return true;
		         	} 
		         	segmentIsLeaft(node.right,segment);
		         
		         
		     }
		     return false;
		 }
	 
	 //Fonction pour insérer un nouveau segment dans la structure de statut
	 public void insertSegment(LineSegment segment,boolean interior) {
	     root = insertSegmentHelper(root, segment,interior);
	 }

	private StatusNode insertSegmentHelper(StatusNode node, LineSegment segment,boolean interior) {
	     // Insérer comme dans un BST
	     if (node == null) {
	         // Créer le nœud avec le nouveau segment
	         StatusNode newNode = new StatusNode(segment);
	         //  Création du node interior
	         //if(interior)
	        	 	//newNode.left = new StatusNode(segment);
	
	         return newNode;
	     }
	
	     if (segment.compareTo(node.segment) < 0) {
	         node.left = insertSegmentHelper(node.left, segment,interior);
	     } else if (segment.compareTo(node.segment) > 0) {
	         node.right = insertSegmentHelper(node.right, segment,interior);
	     } else {
	          if(!interior && node.isLeaf())
	          {
	        	  if (segment.compareTo(node.segment) <= 0) {
	     	         node.left = insertSegmentHelper(node.left, segment,interior);
	     	     } 
	        	 else{
	     	         node.right = insertSegmentHelper(node.right, segment,interior);
	     	     }
	          }
	          
	     }
	
	     // Mettre à jour la hauteur du nœud actuel
	     updateHeight(node);
	
	     // Équilibrer l'arbre
	     int balance = getBalance(node);
	
	     // Rotation simple à droite
	     if (balance > 1 && segment.compareTo(node.left.segment) < 0) {
	         return rotateRight(node);
	     }
	
	     // Rotation simple à gauche
	     if (balance < -1 && segment.compareTo(node.right.segment) > 0) {
	         return rotateLeft(node);
	     }
	
	     // Rotation double à gauche-droite
	     if (balance > 1 && segment.compareTo(node.left.segment) > 0) {
	         node.left = rotateLeft(node.left);
	         return rotateRight(node);
	     }
	
	     // Rotation double à droite-gauche
	     if (balance < -1 && segment.compareTo(node.right.segment) < 0) {
	         node.right = rotateRight(node.right);
	         return rotateLeft(node);
	     }
	
	     return node;
	 }

	
	// Fonction pour rechercher les segments contenant le point p
    public void findSegmentsContainingP(EventPoint p, Set<LineSegment> lowerSegments, Set<LineSegment> containingSegments) {
        findSegmentsContainingP(root, p, lowerSegments, containingSegments);
    }

    private void findSegmentsContainingP(StatusNode node, EventPoint p, Set<LineSegment> lowerSegments, Set<LineSegment> containingSegments) {
        if (node != null) {
            if (node.isLeaf()) {
                // Vérifie si le segment a pour extremite  inf le point p
            	if(node.segment.getStartPoint().compareTo(p) == 0)
            	{
            		lowerSegments.add(node.segment);
            	}
            	
            } else {
            	if(node.segment.getStartPoint().compareTo(p) == 0 || node.segment.getEndPoint().compareTo(p) == 0)
            	{
            		containingSegments.add(node.segment);
            	}
                findSegmentsContainingP(node.left, p, lowerSegments, containingSegments);
                findSegmentsContainingP(node.right, p, lowerSegments, containingSegments);
            }
        }
    }
    
 // Fonction pour trouver le voisin gauche pour un PointD'Événement
    public LineSegment findLeftNeighbor(EventPoint p) {
        return findLeftNeighbor(root, p, null);
    }

    private LineSegment findLeftNeighbor(StatusNode node, EventPoint p, LineSegment voisinGauche) {
        if (node != null) {
            if (p.compareTo(node.segment.getStartPoint()) <= 0) {
                // p est à gauche du segment actuel
                return findLeftNeighbor(node.left, p, voisinGauche);
            } else {
                // p est à droite du segment actuel
                voisinGauche = node.segment;
                return findLeftNeighbor(node.right, p, voisinGauche);
            }
        }

        return voisinGauche;
    }
    
 // Fonction pour trouver le voisin droit pour un PointD'Événement
    public LineSegment findRightNeighbor(EventPoint p) {
        return findRightNeighbor(root, p, null);
    }

    private LineSegment findRightNeighbor(StatusNode node, EventPoint p, LineSegment  voisinDroit) {
        if (node != null) {
            if (p.compareTo(node.segment.getStartPoint()) <= 0) {
                // p est à gauche du segment actuel
                voisinDroit = node.segment;
                return findRightNeighbor(node.left, p, voisinDroit);
            } else {
                // p est à droite du segment actuel
                return findRightNeighbor(node.right, p, voisinDroit);
            }
        }

        return voisinDroit;
    }
    /*
    // Fonction pour supprimer un segment de la structure de statut
    public void deleteSegment(LineSegment segment) {
        root = deleteSegmentHelper(root, segment);
    }

    private StatusNode deleteSegmentHelper(StatusNode node, LineSegment segment) {
        // Chercher le nœud à supprimer comme dans un BST
        if (node == null) {
            return node;
        }

        if (segment.comparaisonSegment(node.segment) < 0) {
            node.left = deleteSegmentHelper(node.left, segment);
        } else if (segment.comparaisonSegment(node.segment) > 0) {
            node.right = deleteSegmentHelper(node.right, segment);
        } else {
            // Nœud à supprimer trouvé

            // Si le nœud a au plus un enfant
            if (node.left == null || node.right == null) {
                StatusNode temp = (node.left != null) ? node.left : node.right;

                // Cas sans enfant ou un seul enfant
                if (temp == null) {
                    temp = node;
                    node = null;
                } else {
                    // Copier le contenu du nœud non nul dans le nœud actuel
                    node = temp;
                }
            } else {
                // Nœud avec deux enfants, trouver le successeur
                StatusNode successor = getMinNode(node.right);

                // Copier les données du successeur dans ce nœud
                node.segment = successor.segment;

                // Supprimer le successeur
                node.right = deleteSegmentHelper(node.right, successor.segment);
            }
        }

        // Si le nœud avait un seul élément, pas besoin de rééquilibrer
        if (node == null) {
            return node;
        }

        // Mettre à jour la hauteur du nœud actuel
        updateHeight(node);

        // Équilibrer l'arbre
        int balance = getBalance(node);

        // Rotation simple à droite
        if (balance > 1 && getBalance(node.left) >= 0) {
            return rotateRight(node);
        }

        // Rotation simple à gauche
        if (balance < -1 && getBalance(node.right) <= 0) {
            return rotateLeft(node);
        }

        // Rotation double à gauche-droite
        if (balance > 1 && getBalance(node.left) < 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        // Rotation double à droite-gauche
        if (balance < -1 && getBalance(node.right) > 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    // Fonction pour obtenir le nœud avec la valeur minimale (successeur) dans un sous-arbre
    private StatusNode getMinNode(StatusNode node) {
        StatusNode current = node;

        // Descendre vers la gauche jusqu'à ce qu'on atteigne la feuille la plus à gauche
        while (current.left != null) {
            current = current.left;
        }

        return current;
    }
    */

	
	 
}
