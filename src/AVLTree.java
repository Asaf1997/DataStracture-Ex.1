package src;

/**
 *
 * AVLTree
 *
 * An implementation of aמ AVL Tree with
 * distinct integer keys and info.
 *
 */

public class AVLTree {

	private AVLNode root;
	private int size;

	public AVLTree(){
		this.size = 0;
		this.root = null;
	}

  /**
   * public boolean empty()
   *
   * Returns true if and only if the tree is empty.
   *
   */
  public boolean empty() {
	  return this.size == 0;
  }

 /**
   * public String search(int k)
   * Wrote by Asaf
   * Returns the info of an item with key k if it exists in the tree.
   * otherwise, returns null.
   */
  public String search(int k) {
	  AVLNode a = getNode(k);
	  return a != null ? a.info : null;
  }

	/**
	 * Ido wrote this function for insert and delete
	 * the function rebalances the tree and returns the count of operations
	 */
	public int reBalance(AVLNode t){

		return 0;
	}

	/**
   * public int insert(int k, String i)
   *
   * Inserts an item with key k and info i to the AVL tree.
   * The tree must remain valid, i.e. keep its invariants.
   * Returns the number of re-balancing operations, or 0 if no re-balancing operations were necessary.
   * A promotion/rotation counts as one re-balance operation, double-rotation is counted as 2.
   * Returns -1 if an item with key k already exists in the tree.
   */
   public int insert(int k, String i) {

	   //ASAF

	   return 420;	// to be replaced by student code
   }
	/**
	 * Ido wrote this function for delete
	 * find the successor of node
	 */
	public IAVLNode successor(IAVLNode node){
		if(node.getRight() != null){
			IAVLNode left_subtree = node.getRight();
			while(left_subtree.getLeft() != null){
				left_subtree = left_subtree.getLeft();
			}
			return left_subtree;
		}
		else{
			IAVLNode parent = node.getParent();
			while(parent != null && node == parent.getRight()){ // if parent is not empty node, and we can continue to search
				node = parent;
				parent = node.getParent();
			}
			return parent;
		}
	}

	// By asaf
	public boolean isLeaf(IAVLNode node){
		if(node.getLeft().getKey() == -1 && node.getRight().getKey() == -1){return true;}
		return false;
	}

	public int difference(IAVLNode node1, IAVLNode node2){
		return node1.getHeight() - node2.getHeight();
	}

	private AVLNode getNode(int k){
		AVLNode curr = this.root;
		while (curr.key != -1){
			if (curr.key == k){ return curr; }
			curr = curr.key < k ? curr.right : curr.left ;
		}
		return null;
	}

  /**
   * public int delete(int k)
   *
   * Deletes an item with key k from the binary tree, if it is there.
   * The tree must remain valid, i.e. keep its invariants.
   * Returns the number of re-balancing operations, or 0 if no re-balancing operations were necessary.
   * A promotion/rotation counts as one re-balance operation, double-rotation is counted as 2.
   * Returns -1 if an item with key k was not found in the tree.
   */
   public int delete(int k) {
	   if(this.search(k) == null || k < 0)
		   return -1;
	   IAVLNode del_node = search(k);
	   if(isLeaf(del_node)){ // leaf
		   IAVLNode parent = del_node.getParent();
		   if(difference(parent, parent.getLeft()) == 1 && difference(parent, parent.getRight()) == 1) // difference of rank: 1 1
			   del_node.setNoRealNode();
		   else{ // difference of rank: 1 2 || 2 1

		   }

	   }


	   return 421;	// to be replaced by student code
   }

   /**
    * public String min()
    *
    * Returns the info of the item with the smallest key in the tree,
    * or null if the tree is empty.
    */
   public String min() {

	   // IDO


	   return "minDefaultString"; // to be replaced by student code
   }

   /**
    * public String max()
    * Wrote by Asaf
	*
    * Returns the info of the item with the largest key in the tree,
    * or null if the tree is empty.
    */
   public String max() {
	   if(this.size == 0){ return null; }
	   AVLNode curr = this.root;
	   while (curr.right.key != -1){
			curr = curr.right;
	   }
	   return curr.info;
   }

  /**
   * public int[] keysToArray()
   *
   * Returns a sorted array which contains all keys in the tree,
   * or an empty array if the tree is empty.
   */
  public int[] keysToArray() {
	  int[] keys = new int[this.size];

	  return null;
  }

  /**
   * public String[] infoToArray()
   *
   * Returns an array which contains all info in the tree,
   * sorted by their respective keys,
   * or an empty array if the tree is empty.
   */
  public String[] infoToArray() {

	  // IDO

	  return new String[55]; // to be replaced by student code
  }

   /**
    * public int size()
    *
    * Returns the number of nodes in the tree.
    */
   public int size()
   {
	   return this.size;
   }
   
   /**
    * public int getRoot()
    *
    * Returns the root AVL node, or null if the tree is empty
    */
   public IAVLNode getRoot()
   {
	   return this.root;
   }
   
   /**
    * public AVLTree[] split(int x)
    *
    * splits the tree into 2 trees according to the key x. 
    * Returns an array [t1, t2] with two AVL trees. keys(t1) < x < keys(t2).
    * 
	* precondition: search(x) != null (i.e. you can also assume that the tree is not empty)
    * postcondition: none
    */   
   public AVLTree[] split(int x)
   {
	   // ASAF


	   return null; 
   }
   
   /**
    * public int join(IAVLNode x, AVLTree t)
    *
    * joins t and x with the tree. 	
    * Returns the complexity of the operation (|tree.rank - t.rank| + 1).
	*
	* precondition: keys(t) < x < keys() or keys(t) > x > keys(). t/tree might be empty (rank = -1).
    * postcondition: none
    */   
   public int join(IAVLNode x, AVLTree t)
   {
	   // IDO

	   return -1;
   }

	/** 
	 * public interface IAVLNode
	 * ! Do not delete or modify this - otherwise all tests will fail !
	 */
	public interface IAVLNode{	
		public int getKey(); // Returns node's key (for virtual node return -1).
		public String getValue(); // Returns node's value [info], for virtual node returns null.
		public void setLeft(IAVLNode node); // Sets left child.
		public IAVLNode getLeft(); // Returns left child, if there is no left child returns null.
		public void setRight(IAVLNode node); // Sets right child.
		public IAVLNode getRight(); // Returns right child, if there is no right child return null.
		public void setParent(IAVLNode node); // Sets parent.
		public IAVLNode getParent(); // Returns the parent, if there is no parent return null.
		public boolean isRealNode(); // Returns True if this is a non-virtual AVL node.
    	public void setHeight(int height); // Sets the height of the node.
    	public int getHeight(); // Returns the height of the node (-1 for virtual nodes).
	}

   /** 
    * public class AVLNode
    *
    * If you wish to implement classes other than AVLTree
    * (for example AVLNode), do it in this file, not in another file. 
    * 
    * This class can and MUST be modified (It must implement IAVLNode).
    */
  public class AVLNode implements IAVLNode{
	  private int key, rank;
	  private String info;
	  private AVLNode left, parent, right;

	  	private AVLNode(){
			  this.key = -1;
			  this.info = null;
		}

	  	public AVLNode(int key, String info){
			  this.info = info;
			  this.key = key;
			  this.left = new AVLLeafSons();
			  this.right = new AVLLeafSons();
		}

		public int getKey()
		{
			return this.key; // to be replaced by student code
		}
		public String getValue()
		{
			return this.info; // to be replaced by student code
		}
		public void setLeft(IAVLNode node)
		{
			this.left = (AVLNode) node; // to be replaced by student code
		}
		public IAVLNode getLeft()
		{
			return this.left; // to be replaced by student code
		}
		public void setRight(IAVLNode node)
		{
			this.right = (AVLNode) node; // to be replaced by student code
		}
		public IAVLNode getRight()
		{
			return this.right; // to be replaced by student code
		}
		public void setParent(IAVLNode node)
		{
			this.parent = (AVLNode) node;  // to be replaced by student code
		}
		public IAVLNode getParent()
		{
			return this.parent; // to be replaced by student code
		}
		public boolean isRealNode()
		{
			return true; // to be replaced by student code
		}
	    public void setHeight(int height)
	    {
	      this.rank = height; // to be replaced by student code
	    }
	    public int getHeight()
	    {
	      return this.rank; // to be replaced by student code
	    }
  }
	private class AVLLeafSons extends AVLNode{
		public AVLLeafSons() {
			super();
		}
	}


}
  
