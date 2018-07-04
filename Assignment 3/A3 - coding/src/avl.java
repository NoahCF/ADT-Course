//Robert Nguyen (ID# 21697048)
//Noah Hayek (ID# 27080409)
//Programming Question #2

class avl {
    
    Node root;

    private class Node{
    	
    	/*These attributes of the Node class will not be sufficient for part 2 of the programming question: AVL .*/
    	/*You are free to add extra attributes as you see fit, but do not remove attributes given as it will mess with help code.*/

        String keyword;
        Record record;
        int size;
        int height;
        Node l;
        Node r;
        Node parent;

        private Node(String k){
        	keyword = k;
            record = null;
            l = null;
            r = null;
            parent = null;
            height = 0;
        }

        private void update(Record r){
        	/*TODO Adds the Record r to the linked list of records. You do not have to check if the record is already in the list.*/
        	/*HINT: Add the Record r to the front of your linked list.*/
            r.next = record;
            record = r;
        }

        public boolean hasLeftChild() {
            return (!(this.l == null));
        }

        public boolean hasRightChild() {
            return (!(this.r == null));
        }

        public boolean isNotRoot() {
            return (!(this.parent == null));
        }

       
    }

    public avl(){
        this.root = null;
    }
      
    public void insert(String keyword, FileData fd){
        Record recordToAdd = new Record(fd.id, fd.title, fd.author, null);
        /*TODO Write a recursive insertion that adds recordToAdd to the list of records for the node associated*/
        /*with keyword. If there is no node, this code should add the node.*/
        insertToNode(root, keyword, recordToAdd);
        
        computeHeights(root);
        rebalanceTree(root);
    }

    //helper method for insert()
    private void insertToNode(Node node, String keyword, Record recordToAdd) {
    	
    	if (root==null) {
    		Node newNode = new Node(keyword);
    		newNode.update(recordToAdd);
            //newNode.keyword = keyword;
    		root = newNode;
    		return;
    	}
        if (keyword.compareToIgnoreCase(node.keyword) == 0) {
            node.update(recordToAdd);
            return;
        }
        else if (keyword.compareToIgnoreCase(node.keyword) < 0) {
            if (node.hasLeftChild()) {
                insertToNode(node.l, keyword, recordToAdd);
                return;
            }
            else {
                Node newNode = new Node(keyword);
                newNode.update(recordToAdd);
                //newNode.keyword = keyword;
                newNode.parent = node;
                node.l = newNode;
                return;
            }
        }
        else if (node.hasRightChild()) {
            insertToNode(node.r, keyword, recordToAdd);
            return;
        }
        else {
            Node newNode = new Node(keyword);
            newNode.update(recordToAdd);
            //newNode.keyword = keyword;
            newNode.parent = node;
            node.r = newNode;
            return;
        }
    }
    
    public boolean contains(String keyword){
    	/*TODO Write a recursive function which returns true if a particular keyword exists in the bst*/
        Node temp = getNode(root, keyword);
        if (temp == null)
           	return false;
        else return true;
    }

    public Record get_records(String keyword){
        /* TODO Returns the first record for a particular keyword. This record will link to other records*/
    	//If the keyword is not in the bst, it should return null.
        Node temp = getNode(root, keyword);
        if (!(temp == null)) {
            return temp.record;
        }
        else return null;
    }

    public void delete(String keyword){
    	/*TODO Write a recursive function which removes the Node with keyword from the binary search tree.*/
    	/*You may not use lazy deletion and if the keyword is not in the bst, the function should do nothing.*/
        Node deletedNode = getNode(root, keyword);
        Node replacement = null;

        //retrieving replacement node, if needed
        if (!(deletedNode == null)) {
        	System.out.println("Node " + deletedNode.keyword + " found...");        	
        	
            if (deletedNode.hasLeftChild() && !deletedNode.hasRightChild()) {
                replacement = deletedNode.l;
            }
            else if (!deletedNode.hasLeftChild() && deletedNode.hasRightChild()) {
                replacement = deletedNode.r;
            }
            else if (deletedNode.hasLeftChild() && deletedNode.hasRightChild()) {
                Node largestOfLeftSubtreeTemp = deletedNode.l;
                while (largestOfLeftSubtreeTemp.hasRightChild()) {
                    largestOfLeftSubtreeTemp = largestOfLeftSubtreeTemp.r;
                }
                replacement = largestOfLeftSubtreeTemp;
            }
            //node is a leaf and needs no replacement
            else if (!deletedNode.hasLeftChild() && !deletedNode.hasRightChild()) {
            	System.out.println("Node is a leaf. Deleting node...");
            	if (deletedNode.parent.l == deletedNode)
            		deletedNode.parent.l = null;
            	else deletedNode.parent.r = null;
            }
        }

        //node re-linking
        if (!(replacement == null)) {
        	System.out.println("Deleting " + keyword + " node and swapping with replacement...");
        	
            //detaching replacement from its original parent
            if (replacement.parent.r == replacement)
                replacement.parent.r = null;
            else
                replacement.parent.l = null;
            //setting replacement's new parent
            replacement.parent = deletedNode.parent;

            //setting replacement's new parent (if any), to set replacement as its new child
            if (deletedNode.isNotRoot()) {
                if (deletedNode == deletedNode.parent.l)
                    deletedNode.parent.l = replacement;
                else deletedNode.parent.r = replacement;
            }
            else root = replacement;

            //retrieve replacement's leftmost child, if any
            Node replacementLeftmostChildTemp = replacement;
            while(replacementLeftmostChildTemp.hasLeftChild()) {
                replacementLeftmostChildTemp = replacementLeftmostChildTemp.l;
            }
            //set said leftmost child to set deletedNode's left child as its left child
            replacementLeftmostChildTemp.l = deletedNode.l;
            //deletedNode's left child sets replacementLeftmostChildTemp as its parent
            if (deletedNode.hasLeftChild())
                deletedNode.l.parent = replacementLeftmostChildTemp;

            //replacement sets deletedNode's right child as its right child
            replacement.r = deletedNode.r;
            //deletedNode's right child sets replacement as its parent
            if (deletedNode.hasRightChild())
                deletedNode.r.parent = replacement;
        }
        
        computeHeights(root);
        rebalanceTree(root);
    }

    //helper method, retrieves Node with keyword (if exists, otherwise returns null)
    //node is assumed to be initialized at root
    private Node getNode(Node node, String key) {
        if (key.compareToIgnoreCase(node.keyword) == 0)
            return node;
        else if (key.compareToIgnoreCase(node.keyword) < 0) {
            if (node.hasLeftChild())
                return getNode(node.l, key);
            else return null;
        }
        else if (node.hasRightChild())
            return getNode(node.r, key);
        else return null;
    }
    
    //tester method to see if the tree is a binary search tree
    public void isABST() {
    	if (isABST(root))
    		System.out.println("Yes, this is a binary search tree.");
    	else 
    		System.out.println("No, this is not a binary search tree.");
    }
    private boolean isABST(Node node) {
    	if (node.hasLeftChild()) {
    		//checks if left child comes before parent lexicographically
    		if (node.l.keyword.compareTo(node.keyword) >= 0)
    			return false;
    		//recursive call down the tree to continue checking
    		else return isABST(node.l);
    	}
    	if (node.hasRightChild()) {
    		//checks if right child comes after parent lexicographically
    		if (node.r.keyword.compareTo(node.keyword) <= 0)
    			return false;
    		//recursive call down the tree to continue checking
    		else return isABST(node.r);
    	}
    	return true;
    }

    public void print(){
        print(root);
    }

    private void print(Node t){
        if (t!=null){
            print(t.l);
            System.out.println(t.keyword);
            Record r = t.record;
            while(r != null){
                System.out.printf("\t%s\n",r.title);
                r = r.next;
            }
            print(t.r);
        } 
    }
    

    //methods for AVL tree
    
    //to test out in the main method as to whether the tree is balanced or not
    public void isTreeBalanced() {    	
    	System.out.println("Height of left child of root:" + root.l.height + " vs height of right child of root:" + root.r.height);
    	System.out.println("Is the root node left-heavy? " + isLeftHeavy(root));
    	System.out.println("Is the root node right-heavy? " + isRightHeavy(root));
    	System.out.println("Is the tree balanced? " + isTreeBalanced(root));
    }
    //helper method for above tester method, if any of the tree's nodes are not balanced, will return false
    private boolean isTreeBalanced(Node node) {
    	boolean isLeftBalanced = true;
    	boolean isRightBalanced = true;
    	if ((Math.abs((root.l.height - root.r.height)) > 1))
    		return false;
    	if (node.hasLeftChild())
    		isLeftBalanced = isTreeBalanced(node.l);
    	if (node.hasRightChild())
    		isRightBalanced = isTreeBalanced(node.r);
    	return (isLeftBalanced && isRightBalanced);
    }
    
    //traverses through the tree's nodes and calls the rebalancing method below
    private void rebalanceTree(Node node) {
    	if (node.hasLeftChild())
    		rebalanceTree(node.l);
    	if (node.hasRightChild())
    		rebalanceTree(node.r);
    	rebalanceSubtree(node);
    	computeHeights(node);
    }
    
    //detects any imbalances around the Node and calls for the appropriate rotation
    private void rebalanceSubtree(Node node) {
    	if (isLeftHeavy(node)) {
    		if (isRightHeavy(node.l))
    			doubleRightRotation(node);
    		else singleRightRotation(node);
    	}    	
    	if (isRightHeavy(node)) {
    		if (isLeftHeavy(node.r))
    			doubleLeftRotation(node);
    		else singleLeftRotation(node);
    	}
    }

    //if the Node's left child has a greater height than the right
    private boolean isLeftHeavy(Node node) {
    	return (balanceFactor(node) > 1);
    }

    //if the Node's right child has a greater height than the left
    private boolean isRightHeavy(Node node) {
    	return (balanceFactor(node) < -1);
    }

    //basically does this...
    //		A				      B
    //	   / \				    /   \
    //	A.l	  B				   A     C	
    //		/  \			  / \    / \			
    //	  B.l	C			A.l B.l C.l	C.r		
    //		   / \					
    //		 C.l  C.r
    private void singleLeftRotation(Node nodeA) {
    	Node parentOfNodeA = nodeA.parent;
    	Node nodeB = nodeA.r;
    	Node nodeB_left = nodeB.l;
    	Node nodeC = nodeB.r;

    	nodeB.parent = parentOfNodeA;
    	if (nodeA.isNotRoot()) {
    		if (parentOfNodeA.l == nodeA)
    			parentOfNodeA.l = nodeB;
    		else parentOfNodeA.r = nodeB;
    	}
    	else root = nodeB;
    	
    	if (!(nodeB == null))
    		nodeB.l = nodeA;
    	nodeA.parent = nodeB;
    	
    	if (!(nodeB == null))
    		nodeB.r = nodeC;
    	if (!(nodeC == null))
    		nodeC.parent = nodeB;
    	
    	nodeA.r = nodeB_left;
    	if (!(nodeB_left == null))
    		nodeB_left.parent = nodeA;
    }


    //basically does this...
    //		A					 A						 C
    //	   / \				   /  \					   /	\
    //	 A.l  B				  A.l  C				  A		 B
    //		 / \		->		  / \ 		->		 / \	/  \
    //		C  B.r				C.l  B				A.l C.l C.r B.r
    //	   / \			    		/ \				
    //	 C.l  C.r				  C.r  B.r	
    private void doubleLeftRotation(Node nodeA) {
    	Node parentOfNodeA = nodeA.parent;
    	Node nodeB = nodeA.r;
    	Node nodeC = nodeB.l;
    	Node nodeC_left = nodeC.l;
    	Node nodeC_right = nodeC.r;
    	
    	if (!(nodeC == null))
    		nodeC.parent = parentOfNodeA;
    	if (nodeA.isNotRoot()) {
    		if (parentOfNodeA.l == nodeA)
    			parentOfNodeA.l = nodeC;
    		else parentOfNodeA.r = nodeC;
    	}
    	else root = nodeC;
    	
    	if (!(nodeC == null))
    		nodeC.l = nodeA;
    	nodeA.parent = nodeC;
    	
    	if (!(nodeC == null))
    		nodeC.r = nodeB;
    	if (!(nodeB == null))
    		nodeB.parent = nodeC;
    	
    	nodeA.r = nodeC_left;
    	if (!(nodeC_left == null))
    		nodeC_left.parent = nodeA;
    	
    	if (!(nodeB == null))
    		nodeB.l = nodeC_right;
    	if (!(nodeC_right == null))
    		nodeC_right.parent = nodeB;    	
    }


    //basically does this...
    //		A				      B
    //	   / \				    /   \
    //	  B   A.r			  C	      A
    //	 / \		->		 / \     / \
    //	C	B.r				C.l C.r B.r A.r
    //  / \						
    // C.l C.r
    private void singleRightRotation(Node nodeA) {
    	Node parentOfNodeA = nodeA.parent;
    	Node nodeB = nodeA.l;
    	Node nodeC = nodeB.l;
    	Node nodeB_right = nodeB.r;

    	nodeB.parent = parentOfNodeA;
    	if (nodeA.isNotRoot()) {
    		if (parentOfNodeA.l == nodeA)
    			parentOfNodeA.l = nodeB;
    		else parentOfNodeA.r = nodeB;
    	}
    	else root = nodeB;
    	
    	if (!(nodeB == null))
    		nodeB.l = nodeC;
    	if (!(nodeC == null))
    		nodeC.parent = nodeB;

    	if (!(nodeB == null))
    		nodeB.r = nodeA;
    	nodeA.parent = nodeB;
    	
    	nodeA.l = nodeB_right;
    	if (!(nodeB_right == null))
    		nodeB_right.parent = nodeA;
    }
    
    //double-right rotation
    //basically does this...
    //	    A						A					   C
    //	   / \					  /  \				     /    \
    //	  B   A.r				 C    A.r			   B       A
    //	 / \			->		/ \			->		 / \     /   \
    //	B.l	C				   B   C.r		        B.l C.l C.r A.r
    //	   / \				  / \					
    //	 C.l C.r			B.l	C.l		
    private void doubleRightRotation(Node nodeA) {
    	Node parentOfNodeA = nodeA.parent;
    	Node nodeB = nodeA.l;
    	Node nodeC = nodeB.r;
    	Node nodeC_left = nodeC.l;
    	Node nodeC_right = nodeC.r;
    	
    	nodeC.parent = parentOfNodeA;
    	if (nodeA.isNotRoot()) {
    		if (parentOfNodeA.l == nodeA)
    			parentOfNodeA.l = nodeC;
    		else parentOfNodeA.r = nodeC;
    	}
    	else root = nodeC;
    	
    	if (!(nodeC == null))
    		nodeC.l = nodeB;
    	if (!(nodeB == null))
    		nodeB.parent = nodeC;
    	
    	if (!(nodeC == null))
    		nodeC.r = nodeA;
    	nodeA.parent = nodeC;
    	
    	if (!(nodeB == null))
    		nodeB.r = nodeC_left;
    	if (!(nodeC_left == null))
    		nodeC_left.parent = nodeB;
    	
    	nodeA.l = nodeC_right;
    	if (!(nodeC_right == null))
    		nodeC_right.parent = nodeA;
    }

    //returns balance factor of a Node
    //if greater than 1, then the Node is left-heavy
    //if lower than -1, then the Node is right-heavy
    private int balanceFactor(Node node) {
    	Integer leftChildHeight;
    	Integer rightChildHeight;
    	
    	if (node.hasLeftChild())
    		leftChildHeight = node.l.height;
    	else leftChildHeight = 0;
    	
    	if (node.hasRightChild())
    		rightChildHeight = node.r.height;
    	else rightChildHeight = 0;
    	
    	//below will print out the heights of the Node's left child and right child, for testing purposes
    	//System.out.println("LC:" + leftChildHeight + " vs RC:" + rightChildHeight);
    	return (leftChildHeight - rightChildHeight);	
    }

    //Recursive method for setting the heights of the tree's nodes, Node argument is assumed to be initialized at the root Node
    private int computeHeights(Node node) {    	
    	int heightOfNode_L = 1;
    	int heightOfNode_R = 1;
    	
    	if (node.hasLeftChild())
    		heightOfNode_L = computeHeights(node.l) + 1;
    	if (node.hasRightChild())
    		heightOfNode_R = computeHeights(node.r) + 1;
    	
    	//will get the greater of the heights of the Node's children
    	node.height = Math.max(heightOfNode_L, heightOfNode_R);
    	
    	return node.height;
    }
}
