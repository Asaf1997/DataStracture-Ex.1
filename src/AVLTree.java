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

	public AVLNode root;
	private int size;

	public AVLTree() { // making an empty tree
		this.size = 0;
		this.root = null;
	}

	/**
	 * public boolean empty()
	 * <p>
	 * Returns true if and only if the tree is empty.
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
		AVLNode a = (AVLNode) getNode(k); // get the node with the key k or null if it doesn't exist
		return a != null ? a.info : null;
	}

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
	public int insert(int k, String i) {
		if (empty()) {
			this.root = new AVLNode(k, i);
			size++;
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

	/**
	 * Ido wrote this function for delete
	 * find the successor of node
	 */
	public IAVLNode successor(IAVLNode node) {
		if (node.getRight().getHeight() != -1) {
			IAVLNode left_subtree = node.getRight();
			while (left_subtree.getLeft().getHeight() != -1) {
				left_subtree = left_subtree.getLeft();
			}
			return left_subtree;
		}
		else {
			IAVLNode parent = node.getParent();
			while (parent != null && node == parent.getRight()) { // if parent is not empty node, and we can continue to search
				node = parent;
				parent = node.getParent();
			}
			return parent;
		}
	}

	private IAVLNode getNode(int k) {
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
	public int delete(int k) {
		IAVLNode node = getNode(k);
		if (node == null) // Key does not exist
			return -1;

		this.size--;

		IAVLNode parent = node.getParent();

		if (node.isLeaf()) {
			if (this.size == 0) { // If it's an only node
				this.root = null;
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
			else { // Is right son
				parent.setRight(suc_node);
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
	public String min() {
		if (this.size == 0) {
			return null;
		}
		AVLNode curr = this.root;
		while (curr.left.rank != -1) {
			curr = curr.left;
		}
		return curr.info;
	}

	/**
	 * public String max()
	 * Wrote by Asaf
	 * <p>
	 * Returns the info of the item with the largest key in the tree,
	 * or null if the tree is empty.
	 */
	public String max() {
		if (this.size == 0) {
			return null;
		}
		AVLNode curr = this.root;
		while (curr.right.rank != -1) {
			curr = curr.right;
		}
		return curr.info;
	}

	/**
	 * public int[] keysToArray()
	 * <p>
	 * Returns a sorted array which contains all keys in the tree,
	 * or an empty array if the tree is empty.
	 */
	public int[] keysToArray() {
		int[] keys = new int[this.size];
		int i = 0;
		for (IAVLNode node : this) {
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
	public String[] infoToArray() {
		if (this.size == 0) {
			return new String[0];
		}
		String[] info = new String[this.size];
		int i = 0;
		for (IAVLNode node : this) {
			info[i++] = node.getValue();
		}
		return info;
	}

	/**
	 * public int size()
	 * <p>
	 * Returns the number of nodes in the tree.
	 */
	public int size() {
		return this.size;
	}

	/**
	 * public int getRoot()
	 * <p>
	 * Returns the root AVL node, or null if the tree is empty
	 */
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
	public AVLTree[] split(int x) {
		AVLTree[] splitted = new AVLTree[]{new AVLTree(), new AVLTree()};
		Stack<AVLTree> big_trees = new Stack<>();
		Stack<AVLTree> big_nodes = new Stack<>();
		Stack<AVLTree> small_trees = new Stack<>();
		Stack<AVLTree> small_nodes = new Stack<>();
		AVLNode t = this.root;
		while (t.getKey() != x && t.getHeight() != -1) {
			if (t.getKey() < x) {

			}
		}
		return null;
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
	public int join(IAVLNode x, AVLTree t) {
		if(t.size == 0 && this.size == 0){
			this.insert(x.getKey(), x.getValue());
			return 1;
		}
		if(t.size == 0){
			this.insert(x.getKey(), x.getValue());
			return Math.abs(this.root.rank - (-1)) + 1;
		}
		if (this.size == 0){
			t.insert(x.getKey(), x.getValue());
			this.root = t.root;
			return Math.abs((-1) - t.root.rank) + 1;
		}
		// tree and t are not empty
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
		}
		else if(k1 < k2){
			this.root = (AVLNode) biggerKeyTree;
			IAVLNode node = biggerKeyTree;
			while(node.getHeight() > k1)
				node = node.getLeft();
			// now node.getHeight() == k1/k1 -1
			AVLNode parent = (AVLNode) node.getParent();
			x.setLeft(smallerKeyTree);
			x.setRight(node);
			x.setHeight(k1 + 1);
			x.setParent(parent);
			parent.setLeft(x);
			reBalance(x);
		}
		else{
			this.root = (AVLNode) smallerKeyTree;
			IAVLNode node = smallerKeyTree;
			while(node.getHeight() > k2)
				node = node.getRight();
			// now node.getHeight() == k2/k2 -1
			AVLNode parent = (AVLNode) node.getParent();
			x.setLeft(node);
			x.setRight(biggerKeyTree);
			x.setHeight(k2 + 1);
			x.setParent(parent);
			parent.setRight(x);
			reBalance(x);
		}
		return Math.abs(k1 - k2) + 1;
	}

	//prints the tree level by level until the last virtual node
	// V - virtual node
	//N - null
	public void treePrinter() {
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

			for (IAVLNode node : currList) {
				if (node != null && node.isRealNode()) {
					System.out.print(node.getValue() + space);
					childrenList.add(node.getLeft());
					childrenList.add(node.getRight());
				} else if (node != null) {
					System.out.print("V");
					System.out.print(space);
					childrenList.add(null);
					childrenList.add(null);

				} else { //node == null
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

	public void print() {
		this.treePrinter();
		System.out.println();
		System.out.println();
		System.out.println("tree size: " + this.size);
		System.out.println();
		for (IAVLNode node : this) {
			System.out.print("| " + node + ", rank: " + node.getHeight() + " |");
		}
		System.out.println();
		System.out.println();
		System.out.println();
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

		private AVLNode(AVLNode parent) {
			this.key = -1;
			this.info = null;
			this.rank = -1;
			this.parent = parent;
			size = 0;
		}

		public AVLNode(int key, String info) {
			this.info = info;
			this.key = key;
			this.rank = 0;
			this.size = 1;
			this.left = new AVLNode(this);
			this.right = new AVLNode(this);
		}

		public int getKey() {
			return this.key;
		}

		public String getValue() {
			return this.info;
		}

		public void setLeft(IAVLNode node) // this also changes this parent of node
		{
			this.left = (AVLNode) node;
			((AVLNode) node).parent = this;
		}

		public IAVLNode getLeft() {
			return this.left;
		}

		public void setRight(IAVLNode node) // this also changes this parent of node
		{
			this.right = (AVLNode) node;
			((AVLNode) node).parent = this;
		}

		public IAVLNode getRight() {
			return this.right;
		}

		public void setParent(IAVLNode node) {
			this.parent = (AVLNode) node;
		}

		public IAVLNode getParent() {
			return this.parent;
		}

		public boolean isRealNode() {
			return this.rank != -1;
		}

		public void setHeight(int height) {
			this.rank = height;
		}

		public int getHeight() {
			return this.rank;
		}

		public int getLeftDiff() {
			return this.rank - this.left.rank;
		}

		public int getRightDiff() {
			return this.rank - this.right.rank;
		}

		public boolean isLeaf() {
			return this.left.rank == -1 && this.right.rank == -1;
		}

		public int isUnary() { // 0 - left subtree, 1 - right subtree, else - (-1)
			if (this.left.rank == -1 && this.right.rank != -1) {
				return 0;
			}
			if (this.left.rank != -1 && this.right.rank == -1) {
				return 1;
			} else return -1;
		}

		public void promote() {
			this.rank += 1;
		}

		public void demote() {
			this.rank -= 1;
		}

		public String toString() {
			return "key: " + this.key;
		}

		public int position() {
			if (this.parent == null)
				return -1;
			if (this.parent.key < this.key)
				return 1;
			return 0;
		}

		@Override
		public int getSize() {
			return size;
		}

		@Override
		public int updateSize() {
			this.size = 1 + left.size + right.size;
			return this.size;
		}
	}

	public class TreeIterator implements Iterator<IAVLNode> {

		private IAVLNode curr;

		public TreeIterator(AVLTree tree) {
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
  
