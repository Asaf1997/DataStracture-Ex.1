package src;

import java.util.ArrayList;

/**
 *
 * AVLTree
 *
 * An implementation of aמ AVL Tree with
 * distinct integer keys and info.
 *
 */

public class AVLTree {

	public AVLNode root;
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
	  AVLNode a = (AVLNode) getNode(k);
	  return a != null ? a.info : null;
  }

	public IAVLNode rotateLeft(IAVLNode t){
		IAVLNode A = t.getLeft();
		IAVLNode B = t.getRight().getLeft();
		IAVLNode newroot = t.getRight();
		newroot.setParent(t.getParent());
		t.setParent(newroot);
		t.setLeft(A);
		t.setRight(B);
		A.setParent(t);
		B.setParent(t);
		if(newroot.getParent().getKey() < newroot.getKey()) { newroot.getParent().setRight(newroot); }
		else { newroot.getParent().setLeft(newroot); }
		return newroot;
	}

	public IAVLNode rotateRight(IAVLNode node){
		IAVLNode newRoot = node.getLeft();
		newRoot.setParent(node.getParent());
		node.setParent(newRoot);
		node.setLeft(newRoot.getRight());
		node.getLeft().setParent(node);
		newRoot.setRight(node);

		if (newRoot.getParent().getKey() < newRoot.getKey()){ newRoot.getParent().setRight(newRoot); }
		else { newRoot.getParent().setLeft(newRoot); }

		return newRoot;
	}

	/**
	 * Ido wrote this function for insert and delete
	 * the function rebalances the tree and returns the count of operations
	 */
	public int reBalance(AVLNode t){
		int counter = 0;
		while (t != null){
			int leftDiff = t.getLeftDiff();
			int rightDiff = t.getRightDiff();
			if (leftDiff == 0 && rightDiff == 0 || leftDiff == 0 && rightDiff == 1 || leftDiff == 1 && rightDiff == 0){
				t.promote();
			}
			else if (leftDiff == 0 && rightDiff == 2){
				counter ++;
				
			}

		}
		return  0 ;
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

	public int difference(IAVLNode node1, IAVLNode node2){
		return node1.getHeight() - node2.getHeight();
	}

	private IAVLNode getNode(int k){
		IAVLNode curr = this.root;
		while (curr.getKey() != -1){
			if (curr.getKey() == k){ return curr; }
			curr = curr.getKey() < k ? curr.getRight() : curr.getLeft() ;
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
	   IAVLNode node = getNode(k);
	   IAVLNode parent = node.getParent();
	   if(node.isLeaf()){
		   node = new AVLNode((AVLNode)parent);
		   return 0;
	   }
	   else if (node.isUnary() == 0){
		   if(parent.getKey() < node.getKey()) { parent.setRight(node.getLeft()); }
		   else { parent.setLeft(node.getLeft()); }
		   node.getLeft().setParent(parent);
		   node.setLeft(null);
		   reBalance((AVLNode) parent);
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
	   while (curr.right.rank != -1){
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

	//prints the tree level by level until the last virtual node
	// V - virtual node
	//N - null
	public void treePrinter(){
		ArrayList<IAVLNode> currList = new ArrayList<>();
		currList.add(root);
		int level = root.getHeight();

		while (currList.size() > 0) {

			String space = "  ";

			for (int i = 0; i < level; i++) {
				space = space + space;
			}
			level--;
			System.out.print(space);


			ArrayList<IAVLNode> childrenList = new ArrayList<>();

			for (IAVLNode node: currList) {
				if (node != null && node.isRealNode()) {
					System.out.print(node.getValue() + space);
					childrenList.add(node.getLeft());
					childrenList.add(node.getRight());
				}
				else if (node != null) {
					System.out.print("V");
					System.out.print(space);
					childrenList.add(null);
					childrenList.add(null);

				}
				else { //node == null
					System.out.print("N");
					System.out.print(space);

					childrenList.add(null);
					childrenList.add(null);
				}

			}
			boolean onlyNull = true;

			for (int i = 0; i < childrenList.size(); i++) {
				if (childrenList.get(i) != null) {
					onlyNull = false;
					break;
				}
			}
			if (onlyNull) {
				break;
			}
			currList = childrenList;
			childrenList = new ArrayList<>();

			System.out.println();
			System.out.println();
		}
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
		public int getLeftDiff();
		public int getRightDiff();
		public boolean isLeaf();
		public int isUnary();
		public void promote();
		public void demote();
	}

   /** 
    * public class AVLNode
    *
    * If you wish to implement classes other than AVLTree
    * (for example AVLNode), do it in this file, not in another file. 
    * 
    * This class can and MUST be modified (It must implement IAVLNode).
    */
  public static class AVLNode implements IAVLNode{
	  private int key, rank;
	  private String info;
	  private AVLNode left, parent, right;

	  	private AVLNode(AVLNode parent){
			  this.key = -1;
			  this.info = null;
			  this.rank = -1;
			  this.parent = parent;
		}

	  	public AVLNode(int key, String info){
			  this.info = info;
			  this.key = key;
			  this.rank = 0;
			  this.left = new AVLNode(this);
			  this.right = new AVLNode(this);
		}

		public int getKey()
		{
			return this.key;
		}
		public String getValue()
		{
			return this.info;
		}
		public void setLeft(IAVLNode node) // this also changes this parent of node
		{
			this.left = (AVLNode) node;
			((AVLNode) node).parent = this;
		}
		public IAVLNode getLeft()
		{
			return this.left;
		}
		public void setRight(IAVLNode node) // this also changes this parent of node
		{
			this.right = (AVLNode) node;
			((AVLNode) node).parent = this;
		}
		public IAVLNode getRight()
		{
			return this.right;
		}
		public void setParent(IAVLNode node)
		{
			this.parent = (AVLNode) node;
		}
		public IAVLNode getParent()
		{
			return this.parent;
		}
		public boolean isRealNode()
		{
			return this.rank != -1;
		}
	    public void setHeight(int height)
	    {
	      this.rank = height;
	    }
	    public int getHeight()
	    {
	      return this.rank;
	    }
		public int getLeftDiff(){
			  return this.rank - this.left.rank;
		}

	   public int getRightDiff(){
		   return this.rank - this.left.rank;
	   }

	   public boolean isLeaf(){
			  return this.left.rank == -1 && this.right.rank == -1;
	   }

	   public int isUnary(){ // 0 - left subtree, 1 - right subtree, else - (-1)
			  if (this.left.rank == -1 && this.right.rank != -1) { return 0; }
			  if (this.left.rank != -1 && this.right.rank == -1) { return 1; }
			  else return -1;
	   }

	   public void promote(){
			  this.rank += 1;
	   }

	   public void demote(){
			  this.rank -= 1;
	   }
  }
}
  
