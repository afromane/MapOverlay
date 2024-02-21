package classe;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Classe principale pour représenter un nœud AVL qui stocke des points d'événements
class EventPointNode {
    EventPoint eventPoint;
    Set<LineSegment> segments;
    EventPointNode left, right;
    int height;

    public EventPointNode(EventPoint eventPoint, LineSegment segment) {
        this.eventPoint = eventPoint;
        this.segments = new HashSet<>();
        this.segments.add(segment);
        this.height = 1;
    }
}

// Classe principale pour la gestion des points d'événements et de la structure AVL associée
public class EventQueueAVL {
    private EventPointNode root;

    // Constructeur de la classe
    public EventQueueAVL() {
        this.root = null;
    }

    // Fonction pour obtenir la hauteur d'un nœud (gère les cas null)
    private int height(EventPointNode node) {
        return (node == null) ? 0 : node.height;
    }

  
    // Fonction pour obtenir le facteur d'équilibre d'un nœud
    private int getBalance(EventPointNode node) {
        return (node == null) ? 0 : height(node.left) - height(node.right);
    }

    // Fonction pour mettre à jour la hauteur d'un nœud
    private void updateHeight(EventPointNode node) {
        if (node != null) {
            node.height = 1 + Math.max(height(node.left), height(node.right));
            updateHeight(node.left);
            updateHeight(node.right);
        }
    }

    // Fonction pour insérer un nouveau point d'événement et associer un segment
    public void insertEvent(EventPoint eventPoint, LineSegment segment) {
        root = insert(root, eventPoint, segment);
        updateHeight(root);
    }

    // Fonction auxiliaire récursive pour insérer un point d'événement et associer un segment
    private EventPointNode insert(EventPointNode root, EventPoint eventPoint, LineSegment segment) {
        if (root == null) {
            return new EventPointNode(eventPoint, segment);
        }

        if (eventPoint.compareTo(root.eventPoint) < 0) {
            root.left = insert(root.left, eventPoint, segment);
        } else if (eventPoint.compareTo(root.eventPoint) > 0) {
            root.right = insert(root.right, eventPoint, segment);
        } else {
            // Éviter l'insertion de points d'événements identiques et insérer le segment
            root.segments.add(segment);
            return root;
        }

        // Mise à jour de la hauteur du nœud actuel
        root.height = 1 + Math.max(height(root.left), height(root.right));

        // Équilibrer le nœud
        return balance(root);
    }

    // Fonction pour trouver le point d'événement le plus petit
    public EventPoint findMin() {
        return findMin(this.root);
    }
    // Fonction  auxiliaire pour trouver le point d'événement le plus petit
    private EventPoint findMin(EventPointNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node.eventPoint;
    }
   
    // Fonction pour trouver  l'ensemble des segment du point d'événement le plus petit
    public Set<LineSegment> findMinAllSegment() {
        return findMinAllSegment(this.root);
    }
    // Fonction  auxiliaire pour trouver le point d'événement le plus petit
    private Set<LineSegment> findMinAllSegment(EventPointNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node.segments;
    }
    
    //
    // Fonction pour supprimer un point d'événement et son segment associé de la file d'événements
    public void deleteEvent(EventPoint eventPoint) {
        if (root != null) {
            root = deleteEvent(root, eventPoint);
        }
        if (root != null) {
            updateHeight(root);
        }
    }

    // Fonction auxiliaire récursive pour supprimer un point d'événement et son segment associé
    private EventPointNode deleteEvent(EventPointNode root, EventPoint eventPoint) {
        if (root == null) {
            return root;
        }

        if (eventPoint.compareTo(root.eventPoint) < 0) {
            root.left = deleteEvent(root.left, eventPoint);
        } else if (eventPoint.compareTo(root.eventPoint) > 0) {
            root.right = deleteEvent(root.right, eventPoint);
        } else {
            // Point d'événement trouvé

            // Supprimer le nœud
            root = deleteNode(root);
        }

        if (root != null) {
            // Mise à jour de la hauteur du nœud actuel
            root.height = 1 + Math.max(height(root.left), height(root.right));

            // Équilibrer l'arbre
            return balance(root);
        } else {
            // L'arbre est vide ou le nœud a été supprimé, retourner null
            return null;
        }
    }

    // Fonction auxiliaire pour supprimer un nœud
    private EventPointNode deleteNode(EventPointNode node) {
        if (node.left == null) {
            return node.right;
        } else if (node.right == null) {
            return node.left;
        }

        // Le nœud a deux enfants, trouver le successeur
        EventPointNode successor = getSuccessor(node.right);

        // Copier les données du successeur dans ce nœud
        node.eventPoint = successor.eventPoint;
        node.segments = successor.segments;

        // Supprimer le nœud successeur
        node.right = deleteNode(node.right);

        // Mise à jour de la hauteur du nœud actuel
        node.height = 1 + Math.max(height(node.left), height(node.right));

        // Équilibrer l'arbre
        return balance(node);
    }

    // Fonction auxiliaire pour obtenir le successeur d'un nœud
    private EventPointNode getSuccessor(EventPointNode root) {
        while (root.left != null) {
            root = root.left;
        }
        return root;
    }

    // Fonction pour équilibrer un nœud AVL
    private EventPointNode balance(EventPointNode node) {
        int balance = getBalance(node);

        // Gauche lourde
        if (balance > 1) {
            if (getBalance(node.left) >= 0) {
                return rotateRight(node);
            } else {
                node.left = rotateLeft(node.left);
                return rotateRight(node);
            }
        }

        // Droite lourde
        if (balance < -1) {
            if (getBalance(node.right) <= 0) {
                return rotateLeft(node);
            } else {
                node.right = rotateRight(node.right);
                return rotateLeft(node);
            }
        }

        return node;
    }

    // Fonction pour effectuer une rotation simple à droite
    private EventPointNode rotateRight(EventPointNode y) {
        EventPointNode x = y.left;
        EventPointNode T2 = x.right;

        // Rotation
        x.right = y;
        y.left = T2;

        // Mise à jour des hauteurs
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    // Fonction pour effectuer une rotation simple à gauche
    private EventPointNode rotateLeft(EventPointNode x) {
        EventPointNode y = x.right;
        EventPointNode T2 = y.left;

        // Rotation
        y.left = x;
        x.right = T2;

        // Mise à jour des hauteurs
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }
    // Fonction auxiliaire pour afficher les points d'événements par parcours inordre
    public void inOrderTraversal() {
    	inOrderTraversal(this.root);
    }

    // Fonction auxiliaire pour afficher les points d'événements par parcours inordre
    private void inOrderTraversal(EventPointNode node) {
        if (node != null) {
            inOrderTraversal(node.left);
            System.out.println("Point d'événement : " + node.eventPoint.getX() + ", " + node.eventPoint.getY());
           /* System.out.println("Segments associés :");
            for (LineSegment segment : node.segments) {
                System.out.println(segment.getStartPoint().toString() + " - " + segment.getEndPoint().toString());
            }
            System.out.println();*/
            inOrderTraversal(node.right);
        }
    }
    
    // FOnction permetant de verifier si l'arbre est vide
    
    public boolean isEmpty(){
    	return this.root == null ? true : false;
    }
}
