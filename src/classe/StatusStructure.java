package classe;

import java.util.HashSet;
import java.util.Set;

class StatusNode {
    LineSegment segment;
    StatusNode left, right;
    int height;
    LineSegment rightmostLeafSegment;

    public StatusNode(LineSegment segment) {
        this.segment = segment;
        this.height = 1;
        this.rightmostLeafSegment = segment;
    }
}

public class StatusStructure {
    private StatusNode root;

    private int height(StatusNode node) {
        return (node == null) ? 0 : node.height;
    }

    private int balanceFactor(StatusNode node) {
        return (node == null) ? 0 : height(node.left) - height(node.right);
    }

    private void updateHeight(StatusNode node) {
        if (node != null) {
            node.height = 1 + Math.max(height(node.left), height(node.right));
        }
    }

    private StatusNode rightRotate(StatusNode y) {
	    if (y == null) {
	        return null; 
	    }
	    StatusNode x = y.left;
	    if (x == null) {
	        return y;
	    }
	
	    StatusNode T2 = x.right;
	
	    x.right = y;
	    y.left = T2;
	
	    updateHeight(y);
	    updateHeight(x);
	
	    return x;
	}

    private StatusNode leftRotate(StatusNode x) {
        if (x == null) {
            return null; 
        }
        StatusNode y = x.right;
        if (y == null) {
            return x;
        }
        StatusNode T2 = y.left;
        y.left = x;
        x.right = T2;
        updateHeight(x);
        updateHeight(y);

        return y;
    }

    private StatusNode insert(StatusNode root, LineSegment segment) {
        if (root == null) {
            return new StatusNode(segment);
        }

        int comparisonResult = segment.compare(root.segment);

        if (comparisonResult < 0) {
            root.left = insert(root.left, segment);
        } else {
            root.right = insert(root.right, segment);
        }

        updateHeight(root);

        int balance = balanceFactor(root);

        // Left Heavy
        if (balance > 1) {
            comparisonResult = segment.compare(root.left.segment);
            if (comparisonResult < 0) {
                return rightRotate(root);
            } else {
                root.left = leftRotate(root.left);
                return rightRotate(root);
            }
        }

        // Right Heavy
        if (balance < -1) {
            comparisonResult = segment.compare(root.right.segment);
            if (comparisonResult > 0) {
                return leftRotate(root);
            } else {
                root.right = rightRotate(root.right);
                return leftRotate(root);
            }
        }

        updateInternalNode(root);

        return root;
    }

    public void insert(LineSegment segment) {
        root = insert(root, segment);
    }

    private LineSegment findRightmostLeafOfLeftSubtree(StatusNode root) {
        if (root == null) {
            return null;
        }

        while (root.right != null) {
            root = root.right;
        }

        return root.segment;
    }

    private void updateInternalNode(StatusNode root) {
        if (root != null) {
            root.rightmostLeafSegment = findRightmostLeafOfLeftSubtree(root.left);
        }
    }
    
    public void delete(LineSegment segment) {
        root = deleteNode(root, segment);
        updateRightmostLeafSegments(root);
    }
    private StatusNode deleteNode(StatusNode root, LineSegment segment) {
        if (root == null) {
            return null;
        }

        int comparisonResult = segment.compare(root.segment);

        if (comparisonResult < 0) {
            root.left = deleteNode(root.left, segment);
        } else if (comparisonResult > 0) {
            root.right = deleteNode(root.right, segment);
        } else {
            // Node to be deleted found

            if (root.left == null || root.right == null) {
                // One or no child
                StatusNode nonNullChild = (root.left != null) ? root.left : root.right;

                if (nonNullChild == null) {
                    root = null;
                } else {
                    root = nonNullChild;
                }
            } else {
                StatusNode successor = findSuccessor(root.right);
                root.segment = successor.segment;
                root.right = deleteNode(root.right, successor.segment);
            }
        }

        if (root != null) {
            updateHeight(root);
            updateInternalNode(root);
        }

        return balance(root);
    }

    public void updateRightmostLeafSegments() {
        updateRightmostLeafSegments(root);
    }

    private void updateRightmostLeafSegments(StatusNode root) {
        if (root != null) {
            // Mettre à jour les enfants d'abord
            updateRightmostLeafSegments(root.left);
            updateRightmostLeafSegments(root.right);

            // Mettre à jour le rightmostLeafSegment du nœud actuel
            if (root.left != null) {
                root.rightmostLeafSegment = root.left.rightmostLeafSegment;
            } else {
                root.rightmostLeafSegment = root.segment;
            }
        }
    }
	private StatusNode findSuccessor(StatusNode root) {
	    StatusNode current = root;
	    while (current.left != null) {
	        current = current.left;
	    }
	    return current;
	}
	 
    private StatusNode balance(StatusNode root) {
    if (root == null) {
        return null;
    }

    updateHeight(root);

    int balance = balanceFactor(root);

    // Left unbalanced
    if (balance > 1) {
        if (balanceFactor(root.left) >= 0) {
            return rightRotate(root);
        } else {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }
    }

    // Right unbalanced
        if (balance < -1) {
            if (balanceFactor(root.right) <= 0) {
                return leftRotate(root);
            } else {
                root.right = rightRotate(root.right);
                return leftRotate(root);
            }
        }

        return root;
    }

    public Set<LineSegment> findSegmentsContainingPoint(EventPoint p) {
        Set<LineSegment> result = new HashSet<>();
        findSegmentsContainingPoint(root, p, result);
        return result;
    }

    private void findSegmentsContainingPoint(StatusNode root, EventPoint p, Set<LineSegment> result) {
        if (root == null) {
            return;
        }

        // Vérifier si rightmostLeafSegment est défini
        if (root.rightmostLeafSegment != null) {
            int comparisonResultStart = root.rightmostLeafSegment.getStartPoint().compareTo(p);
            int comparisonResultEnd = root.rightmostLeafSegment.getEndPoint().compareTo(p);

            // Si p est à l'intérieur du segment
            if (comparisonResultStart == 0 || comparisonResultEnd == 0) {
                // Un même segment ne peut pas avoir son propre rightmostLeafSegment comme valeur
                if (root.segment.getStartPoint().compareTo(root.rightmostLeafSegment.getStartPoint()) != 0
                        && root.segment.getEndPoint().compareTo(root.rightmostLeafSegment.getEndPoint()) != 0) {
                    result.add(root.segment);
                }
            }

            // Si p est à gauche, rechercher dans le sous-arbre droit
            if (comparisonResultEnd < 0) {
                findSegmentsContainingPoint(root.right, p, result);
            }

            // Si p est à droite, rechercher dans le sous-arbre gauche
            if (comparisonResultStart > 0) {
                findSegmentsContainingPoint(root.left, p, result);
            }
        }
    }
    
    public void printTree() {
	        inOrderTraversal(root);
	    }

	    private void inOrderTraversal(StatusNode root) {
	        if (root != null) {
	            inOrderTraversal(root.left);
	            System.out.println("Segment: (" + root.segment.getStartPoint().toString() + ", " + root.segment.getEndPoint().toString() + ")");
	         System.out.println("Rigt: (" + root.rightmostLeafSegment.getStartPoint().toString() + ", " + root.rightmostLeafSegment.getEndPoint().toString() + ")");
	            
	            inOrderTraversal(root.right);
	        }
	    }
    
    public Set<LineSegment> findSegmentsWithLowerEndpoint( EventPoint p) {
    	Set<LineSegment> result = new HashSet<>();
        findSegmentsWithLowerEndpoint(root, p, result);
        return result;
    }

    private void findSegmentsWithLowerEndpoint(StatusNode root, EventPoint p, Set<LineSegment> result) {
        if (root == null) {
            return;
        }

        int comparisonResultStart = p.compareTo(root.segment.getStartPoint());
        if (comparisonResultStart == 0) {
            result.add(root.segment);
        }

        // If p is to the right, search in the left subtree
       // if (comparisonResultStart > 0) {
            findSegmentsWithLowerEndpoint(root.left, p, result);
       // }

        // If p is to the left, search in the right subtree
        findSegmentsWithLowerEndpoint(root.right, p, result);
    }

    public void findNeighbors(EventPoint p, LineSegment sl, LineSegment sr) {
    	findNeighbors(root, p, sl, sr);
    }

	private void findNeighbors(StatusNode root, EventPoint p, LineSegment sl, LineSegment sr) {
	    while (root != null) {
	        int comparisonResultStart = p.compareTo(root.segment.getStartPoint());
	        int comparisonResultEnd = p.compareTo(root.segment.getEndPoint());
	
	        if (comparisonResultStart < 0) {
	            sr = root.segment; // root pourrait être sr
	            root = root.left;
	        } else if (comparisonResultEnd > 0) {
	            sl = root.segment; // root pourrait être sl
	            root = root.right;
	        } else {
	            // Vérifier si le nœud le plus à droite et le plus à gauche ne sont pas nuls
	            StatusNode rightmostNode = findRightmostNode(root.left);
	            StatusNode leftmostNode = findLeftmostNode(root.right);
	
	            if (rightmostNode != null) {
	                sr = rightmostNode.segment;
	            }
	
	            if (leftmostNode != null) {
	                sl = leftmostNode.segment;
	            }
	
	            return; // Nous avons trouvé le segment, pas besoin de continuer la recherche
	        }
	    }
	}

	private StatusNode findRightmostNode(StatusNode node) {
	    while (node != null && node.right != null) {
	        node = node.right;
	    }
	    return node;
	}
	
	private StatusNode findLeftmostNode(StatusNode node) {
	    while (node != null && node.left != null) {
	        node = node.left;
	    }
	    return node;
	}
	
	public LineSegment findLeftmostSegment(EventPoint p) {
	    return findLeftmostSegment(root, p);
	}

	private LineSegment findLeftmostSegment(StatusNode root, EventPoint p) {
	    if (root == null) {
	        return null;
	    }

	    // Vérifier si rightmostLeafSegment est défini
	    if (root.rightmostLeafSegment != null) {
	        int comparisonResultStart = root.rightmostLeafSegment.getStartPoint().compareTo(p);
	        int comparisonResultEnd = root.rightmostLeafSegment.getEndPoint().compareTo(p);

	        // Si p est à l'intérieur du segment ou à gauche, rechercher dans le sous-arbre gauche
	        if (comparisonResultStart == 0 || comparisonResultEnd == 0 || comparisonResultEnd < 0) {
	            LineSegment leftmostSegmentInRightSubtree = findLeftmostSegment(root.right, p);

	            // Si un segment est trouvé dans le sous-arbre droit, retourner-le
	            if (leftmostSegmentInRightSubtree != null) {
	                return leftmostSegmentInRightSubtree;
	            }
	        }

	        // Si p est à droite, rechercher dans le sous-arbre gauche
	        if (comparisonResultStart > 0) {
	            return findLeftmostSegment(root.left, p);
	        }

	        // Si p est à gauche, retourner le segment actuel
	        return root.segment;
	    }

	    return null;
	}

	public LineSegment findLeftNeighborOfLeftmostSegment(EventPoint p) {
	    LineSegment leftmostSegment = findLeftmostSegment(p);
	    
	    if (leftmostSegment != null) {
	        return findLeftNeighbor(root, leftmostSegment);
	    }
	    
	    return null;
	}

	private LineSegment findLeftNeighbor(StatusNode root, LineSegment targetSegment) {
	    LineSegment leftNeighbor = null;

	    while (root != null) {
	        int comparisonResult = targetSegment.compare(root.segment);

	        if (comparisonResult > 0) {
	            // Le segment cible est à gauche, mettre à jour leftNeighbor et rechercher dans le sous-arbre gauche
	            leftNeighbor = root.segment;
	            root = root.left;
	        } else {
	            // Le segment cible est à droite, recherche dans le sous-arbre droit
	            root = root.right;
	        }
	    }

	    return leftNeighbor;
	}
	public LineSegment findRightmostSegment(EventPoint p) {
	    StatusNode rightmostNode = findRightmostNode(root, p);

	    if (rightmostNode != null) {
	        return rightmostNode.segment;
	    }

	    return null;
	}

	private StatusNode findRightmostNode(StatusNode root, EventPoint p) {
	    StatusNode rightmostNode = null;

	    while (root != null) {
	        int comparisonResult = p.compareTo(root.segment.getStartPoint());

	        if (comparisonResult > 0) {
	            // p est à droite du segment actuel, mettre à jour rightmostNode et rechercher dans le sous-arbre droit
	            rightmostNode = root;
	            root = root.right;
	        } else {
	            // p est à gauche du segment actuel, rechercher dans le sous-arbre gauche
	            root = root.left;
	        }
	    }

	    return rightmostNode;
	}
	public LineSegment findRightNeighborOfRightmostSegment(EventPoint p) {
	    StatusNode rightmostNode = findRightmostNode(root, p);
	    
	    if (rightmostNode != null) {
	        // Si le nœud le plus à droite a un sous-arbre droit, trouvez le nœud le plus à gauche de ce sous-arbre
	        if (rightmostNode.right != null) {
	            return findLeftmostNode(rightmostNode.right).segment;
	        }
	    }

	    return null;
	}

}