package src;

import java.util.*;

/**
 *
 * AVLTree
 *
 * An implementation of aמ AVL Tree with
 * distinct integer keys and info.
 *
 */

public class AVLTree implements Iterable<AVLTree.IAVLNode> {

	private AVLNode root, min, max;
	private int size;

	// COMPLEXITY O(1)
	public AVLTree() { // making an empty tree
		this.size = 0;
		this.root = null;
	}

	// This one is for split(), not for public use
	// COMPLEXITY O(1)
	private AVLTree(AVLNode node){
		if (node.getHeight() == -1){ root = null; size = 0; return; }
		root = node;
		size = node.size;
		updateMinMax();
	}

	/**
	 * public boolean empty()
	 * <p>
	 * Returns true if and only if the tree is empty.
	 */
	// COMPLEXITY O(1)
	public boolean empty() {
		return this.size == 0;
	}

	/**
	 * public String search(int k)
	 * Wrote by Asaf
	 * Returns the info of an item with key k if it exists in the tree.
	 * otherwise, returns null.
	 */
	// COMPLEXITY O(log n)
	public String search(int k) {
		AVLNode a = (AVLNode) getNode(k); // get the node with the key k or null if it doesn't exist
		return a != null ? a.info : null;
	}

	// COMPLEXITY O(1)
	public IAVLNode rotateLeft(IAVLNode node) {
		if (node == null) { // sanity check
			return null;
		}

		// Doing rotation
		IAVLNode newRoot = node.getRight();
		newRoot.setParent(node.getParent());
		node.setParent(newRoot);
		node.setRight(newRoot.getLeft());
		node.getRight().setParent(node);
		newRoot.setLeft(node);

		// update size
		newRoot.getLeft().updateSize();
		newRoot.updateSize();

		// update parent's sons:

		if (newRoot.position() == -1) { // If we rotated the root, need to change it in the tree field
			this.root = (AVLNode) newRoot;
		} else if (newRoot.position() == 1) { // It's a right son
			newRoot.getParent().setRight(newRoot);
		} else { // It's a left son
			newRoot.getParent().setLeft(newRoot);
		}

		return newRoot;
	}

	// COMPLEXITY O(1)
	public IAVLNode rotateRight(IAVLNode node) {
		if (node == null) {
			return null;
		} // sanity check

		// Doing rotation
		IAVLNode newRoot = node.getLeft();
		newRoot.setParent(node.getParent());
		node.setParent(newRoot);
		node.setLeft(newRoot.getRight());
		node.getLeft().setParent(node);
		newRoot.setRight(node);

		// update size
		newRoot.getRight().updateSize();
		newRoot.updateSize();

		// update parent's sons:

		if (newRoot.position() == -1) { // If we rotated the root, need to change it in the tree field
			this.root = (AVLNode) newRoot;
		} else if (newRoot.position() == 1) { // It's a right son
			newRoot.getParent().setRight(newRoot);
		} else { // It's a left son
			newRoot.getParent().setLeft(newRoot);
		}

		return newRoot;
	}

	/**
	 * the function rebalances the tree, updates node's size and returns the count of operations made
	 */
	// COMPLEXITY O(log n)
	public int reBalance(IAVLNode t) {
		int counter = 0; // counts the number of operations
		while (t != null) {

			int leftDiff = t.getLeftDiff();
			int rightDiff = t.getRightDiff();

			/** ------------- insert and join cases ------------- **/

			// case 1
			if (leftDiff == 0 && rightDiff == 0 || leftDiff == 0 && rightDiff == 1 || leftDiff == 1 && rightDiff == 0) {
				counter++;
				t.promote();
				t.updateSize();
			}

			// cases 2 & 3 (left side)
			else if (leftDiff == 0 && rightDiff == 2) {

				// checking left son's ranks
				int leftSon_leftDiff = t.getLeft().getLeftDiff();
				int leftSon_rightDiff = t.getLeft().getRightDiff();

				if (leftSon_leftDiff == 1 && leftSon_rightDiff == 2) { // case 2
					counter += 2;
					t.demote();
					rotateRight(t);
				} else if (leftSon_leftDiff == 2 && leftSon_rightDiff == 1) { // case 3
					counter += 5;
					t.demote();
					t.getLeft().demote();
					t.getLeft().getRight().promote();
					rotateLeft(t.getLeft());
					rotateRight(t);
				} else if (leftSon_leftDiff == 1 && leftSon_rightDiff == 1) { // can happen only in Join()
					counter += 2;
					t.getLeft().promote();
					rotateRight(t);
				}
			}

			// cases 2 & 3 (right side)
			else if (leftDiff == 2 && rightDiff == 0) {

				// checking right son's ranks
				int rightSon_leftDiff = t.getRight().getLeftDiff();
				int rightSon_rightDiff = t.getRight().getRightDiff();

				if (rightSon_leftDiff == 2 && rightSon_rightDiff == 1) { // case 2
					counter += 2;
					t.demote();
					rotateLeft(t);
				} else if (rightSon_leftDiff == 1 && rightSon_rightDiff == 2) { // case 3
					counter += 5;
					t.demote();
					t.getRight().demote();
					t.getRight().getLeft().promote();
					rotateRight(t.getRight());
					rotateLeft(t);
				} else if (rightSon_leftDiff == 1 && rightSon_rightDiff == 1) { // can happen only in Join()
					counter += 2;
					t.getRight().promote();
					rotateLeft(t);
				}
			}

			/** ------------- delete cases ------------- **/

			if (leftDiff == 2 && rightDiff == 2) { // case 1
				counter++;
				t.demote();
				t.updateSize();
			}

			// cases 2 - 4 (left side)
			else if (leftDiff == 3 && rightDiff == 1) {

				int rightSon_leftDiff = t.getRight().getLeftDiff();
				int rightSon_rightDiff = t.getRight().getRightDiff();

				if (rightSon_leftDiff == 1 && rightSon_rightDiff == 1) { // case 2
					counter += 3;
					t.demote();
					t.getRight().promote();
					rotateLeft(t);
				} else if (rightSon_leftDiff == 2 && rightSon_rightDiff == 1) { // case 3
					counter += 3;
					t.demote();
					t.demote();
					rotateLeft(t);
				} else if (rightSon_leftDiff == 1 && rightSon_rightDiff == 2) { // case 4
					counter += 6;
					t.demote();
					t.demote();
					t.getRight().demote();
					t.getRight().getLeft().promote();
					rotateRight(t.getRight());
					rotateLeft(t);
				}
			}

			// cases 2 - 4 (right side)
			else if (leftDiff == 1 && rightDiff == 3) {

				int leftSon_leftDiff = t.getLeft().getLeftDiff();
				int leftSon_rightDiff = t.getLeft().getRightDiff();

				if (leftSon_leftDiff == 1 && leftSon_rightDiff == 1) { // case 2
					counter += 3;
					t.demote();
					t.getLeft().promote();
					rotateRight(t);
				} else if (leftSon_leftDiff == 1 && leftSon_rightDiff == 2) { // case 3
					counter += 3;
					t.demote();
					t.demote();
					rotateRight(t);
				} else if (leftSon_leftDiff == 2 && leftSon_rightDiff == 1) { // case 4
					counter += 6;
					t.demote();
					t.demote();
					t.getLeft().demote();
					t.getLeft().getRight().promote();
					rotateLeft(t.getLeft());
					rotateRight(t);
				}
			}
			t.updateSize();
			t = t.getParent(); // for while loop
		}
		updateMinMax();
		return counter;
	}

	/**
	 * public int insert(int k, String i)
	 * <p>
	 * Inserts an item with key k and info i to the AVL tree.
	 * The tree must remain valid, i.e. keep its invariants.
	 * Returns the number of re-balancing operations, or 0 if no re-balancing operations were necessary.
	 * A promotion/rotation counts as one re-balance operation, double-rotation is counted as 2.
	 * Returns -1 if an item with key k already exists in the tree.
	 */
	// COMPLEXITY O(log n)
	public int insert(int k, String i) {
		if (empty()) {
			this.root = new AVLNode(k, i);
			size++;
			this.min = this.root;
			this.max = this.root;
			return 0;
		}

		IAVLNode t = this.root;
		while (t.getKey() != -1) {
			if (t.getKey() == k) {
				return -1;
			}
			t = t.getKey() > k ? t.getLeft() : t.getRight();
		}

		t = t.getParent();

		// adding the new node as a leaf
		if (t.getKey() > k) {
			t.setLeft(new AVLNode(k, i));
		}
		else {
			t.setRight(new AVLNode(k, i));
		}

		t.updateSize();
		this.size++;
		return reBalance(t);
	}

	// insert a given node
	// COMPLEXITY O(log n)
	public int insertNode(IAVLNode x){
		if (empty()) {
			this.root = (AVLNode) x;
			size++;
			this.min = this.root;
			this.max = this.root;
			return 0;
		}

		IAVLNode t = this.root;
		while (t.getKey() != -1) {
			if (t.getKey() == x.getKey()) {
				return -1;
			}
			t = t.getKey() > x.getKey() ? t.getLeft() : t.getRight();
		}

		t = t.getParent();

		// adding the new node as a leaf
		if (t.getKey() > x.getKey()) {
			t.setLeft(x);
		}
		else {
			t.setRight(x);
		}

		t.updateSize();
		this.size++;
		return reBalance(t);
	}

	/**
	 * return the successor of node (next key in size)
	 */
	// COMPLEXITY O(log n)
	public IAVLNode successor(IAVLNode node) {
		if (node.getRight().getHeight() != -1) { // If it has a right sub-tree : go to the smallest node there
			IAVLNode left_subtree = node.getRight();
			while (left_subtree.getLeft().getHeight() != -1) {
				left_subtree = left_subtree.getLeft();
			}
			return left_subtree;
		}

		else {
			IAVLNode parent = node.getParent();

			// If parent is not empty node, and we can continue to search
			while (parent != null && node == parent.getRight()) {
				node = parent;
				parent = node.getParent();
			}
			return parent;
		}
	}

	/**
	 * Returns the node with the key k. If it doesn't exist - return null
	 */
	// COMPLEXITY O(log n)
	public IAVLNode getNode(int k) {
		if (empty()){ return null; }
		IAVLNode curr = this.root;
		while (curr.getHeight() != -1) {
			if (curr.getKey() == k) {
				return curr;
			}
			curr = curr.getKey() < k ? curr.getRight() : curr.getLeft();
		}
		return null;
	}

	/**
	 * public int delete(int k)
	 * <p>
	 * Deletes an item with key k from the binary tree, if it is there.
	 * The tree must remain valid, i.e. keep its invariants.
	 * Returns the number of re-balancing operations, or 0 if no re-balancing operations were necessary.
	 * A promotion/rotation counts as one re-balance operation, double-rotation is counted as 2.
	 * Returns -1 if an item with key k was not found in the tree.
	 */
	// COMPLEXITY O(log n)
	public int delete(int k) {
		IAVLNode node = getNode(k);
		if (node == null) // Key does not exist
			return -1;

		this.size--;

		IAVLNode parent = node.getParent();

		if (node.isLeaf()) {
			if (this.size == 0) { // If it's an only node
				this.root = null;
				this.max = null;
				this.min = null;
			}
			else {
				IAVLNode vir_node = new AVLNode((AVLNode) parent);
				if (node.position() == 0)
					parent.setLeft(vir_node);
				else
					parent.setRight(vir_node);
				parent.updateSize();
				return reBalance(parent);
			}
		}

		else if (node.isUnary() == 0) { // subtree left
			if (parent == null) {
				this.root = (AVLNode) node.getLeft();
			}
			else if (parent.getKey() < node.getKey()) {
				parent.setRight(node.getLeft());
			}
			else {
				parent.setLeft(node.getLeft());
			}
			node.getLeft().setParent(parent);
			parent.updateSize();
			return reBalance(parent);
		}

		else if (node.isUnary() == 1) { // subtree right
			if (parent == null) {
				this.root = (AVLNode) node.getRight();
			}
			if (parent.getKey() < node.getKey()) {
				parent.setRight(node.getRight());
			} else {
				parent.setLeft(node.getRight());
			}
			node.getRight().setParent(parent);
			parent.updateSize();
			return reBalance(parent);
		}

		/**
		 * here we can assume that the successor is not the root of the tree.
		 */

		else {
			// take the successor
			IAVLNode suc_node = successor(node);
			IAVLNode suc_parent = suc_node.getParent(); // this can't be null because suc_node is leaf or unary
			if (suc_node.isLeaf()) {
				if (suc_node.position() == 0)
					suc_parent.setLeft(new AVLNode((AVLNode) suc_parent));
				else
					suc_parent.setRight(new AVLNode((AVLNode) suc_parent));
			}
			else { // suc node is unary with right son
				if (suc_node.position() == 0)
					suc_parent.setLeft(suc_node.getRight());
				else
					suc_parent.setRight(suc_node.getRight());
			}

			// update size after the changes
			suc_parent.updateSize();

			// replace node with suc_node
			suc_node.setParent(parent);

			if (suc_node.position() == 0) { // Is left son
				parent.setLeft(suc_node);
			}
			else if (suc_node.position() == 1){ // Is right son
				parent.setRight(suc_node);
			}
			else { // Is a new root
				this.root = (AVLNode) suc_node;
			}

			suc_node.setLeft(node.getLeft());
			suc_node.setRight(node.getRight());
			suc_node.setHeight(node.getHeight());
			suc_node.updateSize();

			return reBalance(suc_parent);
		}
		return 0;
	}

	/**
	 * public String min()
	 * <p>
	 * Returns the info of the item with the smallest key in the tree,
	 * or null if the tree is empty.
	 */
	// COMPLEXITY O(1)
	public String min() {
		return min.info;
	}

	/**
	 * public String max()
	 *
	 * Returns the info of the item with the largest key in the tree,
	 * or null if the tree is empty.
	 */
	// COMPLEXITY O(1)
	public String max() {
		return max.info;
	}

	/**
	 * public int[] keysToArray()
	 * <p>
	 * Returns a sorted array which contains all keys in the tree,
	 * or an empty array if the tree is empty.
	 */
	// COMPLEXITY O(n)
	public int[] keysToArray() {
		int[] keys = new int[this.size];
		int i = 0;
		for (IAVLNode node : this) { // In-order iteration
			keys[i++] = node.getKey();
		}
		return keys;
	}

	/**
	 * public String[] infoToArray()
	 * <p>
	 * Returns an array which contains all info in the tree,
	 * sorted by their respective keys,
	 * or an empty array if the tree is empty.
	 */
	// COMPLEXITY O(n)
	public String[] infoToArray() {
		if (this.size == 0) {
			return new String[0];
		}
		String[] info = new String[this.size];
		int i = 0;
		for (IAVLNode node : this) { // In-order iteration
			info[i++] = node.getValue();
		}
		return info;
	}

	/**
	 * public int size()
	 * <p>
	 * Returns the number of nodes in the tree.
	 */
	// COMPLEXITY O(1)
	public int size() {
		return this.size;
	}

	/**
	 * public int getRoot()
	 * <p>
	 * Returns the root AVL node, or null if the tree is empty
	 */
	// COMPLEXITY O(1)
	public IAVLNode getRoot() {
		return this.root;
	}

	/**
	 * public AVLTree[] split(int x)
	 * <p>
	 * splits the tree into 2 trees according to the key x.
	 * Returns an array [t1, t2] with two AVL trees. keys(t1) < x < keys(t2).
	 * <p>
	 * precondition: search(x) != null (i.e. you can also assume that the tree is not empty)
	 * postcondition: none
	 */
	// COMPLEXITY O(log n)
	public AVLTree[] split(int x) {

		AVLTree[] splitted = new AVLTree[2];

		int big = -1; int small = -1; // Index of the last object that is not null for each array
		AVLTree[] bigger_key_trees = new AVLTree[this.size];
		AVLTree[] smaller_key_trees = new AVLTree[this.size];
		IAVLNode[] bigger_key_nodes = new IAVLNode[this.size];
		IAVLNode[] smaller_key_nodes = new IAVLNode[this.size];

		IAVLNode t = this.root;
		while (t.getKey() != x && t.getHeight() != -1) { // searching for node with key x
			if (t.getKey() > x) { // insert the node and his right sub_tree to big keys array
				big++;
				bigger_key_nodes[big] = t;
				bigger_key_nodes[big].setHeight(0);
				bigger_key_trees[big] = new AVLTree((AVLNode) t.disconnectRight());
				t = t.disconnectLeft(); // disconnect the connection between the node and it's sons
				bigger_key_nodes[big].setHeight(0);
			}
			else { // insert the node and his left sub_tree to small keys array
				small++;
				smaller_key_nodes[small] = t;
				smaller_key_nodes[small].setHeight(0);
				smaller_key_trees[small] = new AVLTree((AVLNode) t.disconnectLeft());
				t = t.disconnectRight(); // disconnect the connection between the node and it's sons
			}
		}

		// t is now the node we split (with key x)
		splitted[0] = new AVLTree((AVLNode) t.disconnectLeft());
		splitted[1] = new AVLTree((AVLNode) t.disconnectRight());

		// Build the new trees
		while (small >= 0 || big >= 0){
			if (big >= 0) {
				splitted[1].join(bigger_key_nodes[big], bigger_key_trees[big]);
				big--;
			}
			if (small >= 0){
				splitted[0].join(smaller_key_nodes[small], smaller_key_trees[small]);
				small--;
			}
		}
		return splitted;
	}

	/**
	 * public int join(IAVLNode x, AVLTree t)
	 * <p>
	 * joins t and x with the tree.
	 * Returns the complexity of the operation (|tree.rank - t.rank| + 1).
	 * <p>
	 * precondition: keys(t) < x < keys() or keys(t) > x > keys(). t/tree might be empty (rank = -1).
	 * postcondition: none
	 */
	// COMPLEXITY O(log n)
	public int join(IAVLNode x, AVLTree t) {
		if (x == null){ // sanity check
			return -1;
		}

		// If one of the trees is empty
		if(t.size == 0 && this.size == 0){
			insertNode(x);
			return 1;
		}
		if(t.size == 0){
			this.insertNode(x);
			return Math.abs(this.root.rank - (-1)) + 1;
		}
		if (this.size == 0){
			t.insertNode(x);
			this.root = t.root;
			this.size = t.size;
			this.max = t.max;
			this.min = t.min;
			return Math.abs((-1) - t.root.rank) + 1;
		}

		// this and t are not empty
		IAVLNode biggerKeyTree;
		IAVLNode smallerKeyTree;
		if(t.root.key < this.root.key){ // keys(t) < x < keys()
			biggerKeyTree = this.root;
			smallerKeyTree = t.root;
		}
		else{
			biggerKeyTree = t.root;
			smallerKeyTree = this.root;
		}


		int k1 = smallerKeyTree.getHeight();
		int k2 = biggerKeyTree.getHeight();
		if(k1 == k2){ // same rank
			this.root = (AVLNode) x;
			this.root.setLeft(smallerKeyTree);
			this.root.setRight(biggerKeyTree);
			this.root.setHeight(k1 + 1);
			this.root.updateSize();
			this.updateMinMax();
		}

		else if(k1 < k2){
			this.root = (AVLNode) biggerKeyTree;
			IAVLNode node = biggerKeyTree;
			while(node.getHeight() > k1)
				node = node.getLeft();
			// Now node's height is k1 or k1 -1

			AVLNode parent = (AVLNode) node.getParent();
			x.setLeft(smallerKeyTree);
			x.setRight(node);
			x.setHeight(k1 + 1);
			x.setParent(parent);
			parent.setLeft(x);
			x.updateSize();
			parent.updateSize();
			reBalance(x);
		}

		else{
			this.root = (AVLNode) smallerKeyTree;
			IAVLNode node = smallerKeyTree;
			while(node.getHeight() > k2)
				node = node.getRight();
			// Now node's height is k2 or k2 -1

			AVLNode parent = (AVLNode) node.getParent();
			x.setRight(biggerKeyTree);
			x.setLeft(node);
			x.setHeight(k2 + 1);
			x.setParent(parent);
			parent.setRight(x);
			x.updateSize();
			parent.updateSize();
			reBalance(x);
		}

		this.size += t.size + 1;
		return Math.abs(k1 - k2) + 1;
	}

	// COMPLEXITY O(log n)
	public void updateMinMax(){
		AVLNode minNode = root;
		AVLNode maxNode = root;
		while (minNode.getLeft().getHeight() != -1 || maxNode.getRight().getHeight() != -1){
			minNode = minNode.getLeft().getHeight() != -1 ? (AVLNode) minNode.getLeft() : minNode;
			maxNode = maxNode.getRight().getHeight() != -1 ? (AVLNode) maxNode.getRight() : maxNode;
		}
		min = minNode;
		max = maxNode;
	}

	// COMPLEXITY O(1)
	public AVLNode getMax(){
		return max;
	}

	// COMPLEXITY O(1)
	public AVLNode getMin(){
		return min;
	}

	@Override
	public Iterator iterator() {
		return new TreeIterator(this);
	}

	/**
	 * public interface IAVLNode
	 * ! Do not delete or modify this - otherwise all tests will fail !
	 */
	public interface IAVLNode {
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
		public int position();
		public int getSize();
		public int updateSize();
		public IAVLNode disconnectRight();
		public IAVLNode disconnectLeft();
	}

	/**
	 * public class AVLNode
	 * <p>
	 * If you wish to implement classes other than AVLTree
	 * (for example AVLNode), do it in this file, not in another file.
	 * <p>
	 * This class can and MUST be modified (It must implement IAVLNode).
	 */
	public static class AVLNode implements IAVLNode {
		private int key, rank, size;
		private String info;
		private AVLNode left, parent, right;

		// Virtual Node Builder. COMPLEXITY O(1)
		private AVLNode(AVLNode parent) {
			this.key = -1;
			this.info = null;
			this.rank = -1;
			this.parent = parent;
			size = 0;
		}

		// COMPLEXITY O(1)
		public AVLNode(int key, String info) {
			this.info = info;
			this.key = key;
			this.rank = 0;
			this.size = 1;
			this.left = new AVLNode(this);
			this.right = new AVLNode(this);
		}

		// COMPLEXITY O(1)
		public int getKey() {
			return this.key;
		}

		// COMPLEXITY O(1)
		public String getValue() {
			return this.info;
		}

		// COMPLEXITY O(1)
		public void setLeft(IAVLNode node) // this also changes this parent of node
		{
			this.left = (AVLNode) node;
			((AVLNode) node).parent = this;
		}

		// COMPLEXITY O(1)
		public IAVLNode getLeft() {
			return this.left;
		}

		// COMPLEXITY O(1)
		public void setRight(IAVLNode node) // this also changes this parent of node
		{
			this.right = (AVLNode) node;
			((AVLNode) node).parent = this;
		}

		// COMPLEXITY O(1)
		public IAVLNode getRight() {
			return this.right;
		}

		// COMPLEXITY O(1)
		public void setParent(IAVLNode node) {
			this.parent = (AVLNode) node;
		}

		// COMPLEXITY O(1)
		public IAVLNode getParent() {
			return this.parent;
		}

		// COMPLEXITY O(1)
		public boolean isRealNode() {
			return this.rank != -1;
		}

		// COMPLEXITY O(1)
		public void setHeight(int height) {
			this.rank = height;
		}

		// COMPLEXITY O(1)
		public int getHeight() {
			return this.rank;
		}

		// COMPLEXITY O(1)
		public int getLeftDiff() {
			return this.rank - this.left.rank;
		}

		// COMPLEXITY O(1)
		public int getRightDiff() {
			return this.rank - this.right.rank;
		}

		// COMPLEXITY O(1)
		public boolean isLeaf() {
			return this.left.rank == -1 && this.right.rank == -1;
		}

		// COMPLEXITY O(1)
		public int isUnary() { // 0 - left subtree, 1 - right subtree, else - (-1)
			if (this.left.rank == -1 && this.right.rank != -1) {
				return 1;
			}
			if (this.left.rank != -1 && this.right.rank == -1) {
				return 0;
			} else return -1;
		}

		// COMPLEXITY O(1)
		public void promote() {
			this.rank += 1;
		}

		// COMPLEXITY O(1)
		public void demote() {
			this.rank -= 1;
		}

		// COMPLEXITY O(1)
		public String toString() {
			return "key: " + this.key;
		}

		// COMPLEXITY O(1)
		// return -1 if it's a root, 0 if it's a left son, 1 if right son
		public int position() {
			if (this.parent == null)
				return -1;
			if (this.parent.key < this.key)
				return 1;
			return 0;
		}

		// COMPLEXITY O(1)
		@Override
		public int getSize() {
			return size;
		}

		// COMPLEXITY O(1)
		@Override
		public int updateSize() {
			this.size = 1 + left.size + right.size;
			return this.size;
		}

		/**
		 * disconnect between node and his right son and return the right son
		 * @return @pre this.right
		 */
		// COMPLEXITY O(1)
		public IAVLNode disconnectRight(){
			IAVLNode temp = this.right;
			this.right = new AVLNode(this);
			temp.setParent(null);
			this.updateSize();
			return temp;
		}

		/**
		 * disconnect between node and his left son and return the left son
		 * @return @pre this.left
		 */
		// COMPLEXITY O(1)
		public IAVLNode disconnectLeft(){
			IAVLNode temp = this.left;
			this.left = new AVLNode(this);
			temp.setParent(null);
			this.updateSize();
			return temp;
		}
	}

	public class TreeIterator implements Iterator<IAVLNode> {

		private IAVLNode curr;

		public TreeIterator(AVLTree tree) {
			if (tree.size == 0){
				curr = null;
				return;
			}
			curr = tree.root;
			while (curr.getLeft().getHeight() != -1 && curr != null) {
				curr = curr.getLeft();
			}
		}

		@Override
		public boolean hasNext() {
			return curr != null;
		}

		@Override
		public IAVLNode next() {
			IAVLNode temp = curr;
			curr = successor(curr);
			return temp;
		}
	}
}
  
