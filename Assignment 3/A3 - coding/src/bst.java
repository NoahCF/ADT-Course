//Robert Nguyen (ID# 21697048)
//Noah Hayek (ID# 27080409)
//Programming Question #1

class bst{
    
    Node root;

    private class Node{
    	
    	/*These attributes of the Node class will not be sufficient for part 2 of the programming question: AVL .*/
    	/*You are free to add extra attributes as you see fit, but do not remove attributes given as it will mess with help code.*/

        String keyword;
        Record record;
        int size;
        Node l;
        Node r;
        Node parent;

        private Node(String k){
        	keyword = k;
            record = null;
            l = null;
            r = null;
            parent = null;
        }

        private void update(Record r){
        	/*TODO Adds the Record r to the linked list of records. You do not have to check if the record is already in the list.*/
        	/*HINT: Add the Record r to the front of your linked list.*/
            r.next = record;
            record = r;
        }

        private boolean hasLeftChild() {
            return (!(this.l == null));
        }

        private boolean hasRightChild() {
            return (!(this.r == null));
        }

        private boolean isNotRoot() {
            return (!(this.parent == null));
        }

       
    }

    public bst(){
        this.root = null;
    }
      
    public void insert(String keyword, FileData fd){
        Record recordToAdd = new Record(fd.id, fd.title, fd.author, null);
        /*TODO Write a recursive insertion that adds recordToAdd to the list of records for the node associated*/
        /*with keyword. If there is no node, this code should add the node.*/
        insertToNode(root, keyword, recordToAdd);
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
    	return null;
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
        	System.out.println("Deleting " + keyword + " node and swapping with replacement named " + replacement.keyword);
        	
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
    

}
