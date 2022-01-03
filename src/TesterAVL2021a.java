package src;

import java.util.Arrays;

public class TesterAVL2021a {
	public static int NUMBER_OF_TESTS = 16;
	public static enum SuccessStatus { FAIL, PASS, EXCEPTION }
    
    public static void main(String[] args) {
        // initialize success status array to false.
        final SuccessStatus[] success = new SuccessStatus[NUMBER_OF_TESTS];
        
        final SimpleTests first_tester = new SimpleTests();
        final ComplexTests second_tester = new ComplexTests();
        
        // generates Q1 and Q2 measurements. Comment out if you only need the test-results:
        // final Experiments experiments = new Experiments();
        // experiments.generate_output();
        
        for (int test_index = 0; test_index < NUMBER_OF_TESTS; test_index ++) { 
        	final int test_index_final = test_index;  // using 'test_index' directly results in a warning inside the 'try' clause. 
        	runWithInterrupt(success,
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                            	boolean result;
                            	switch (test_index_final) {
                            	case 0:  result = first_tester.testEmpty(); break;
                            	case 1:  result = first_tester.testSearch(); break;
                            	case 2:  result = first_tester.testSelect(); break;
                            	case 3:  result = first_tester.testInsert(); break;
                            	case 4:  result = first_tester.testDelete(); break;
                            	case 5:  result = first_tester.testMin();break;
                            	case 6:  result = first_tester.testMax(); break;
                            	case 7:  result = first_tester.testKeysToArray(); break;
                            	case 8:  result = first_tester.testSize(); break;
                            	case 9: result = first_tester.testSplit(); break;
                            	case 10: result = first_tester.testJoin(); break;
                            	case 11: result = first_tester.testIsBalanced(); break;
                            	case 12: result = first_tester.testAvlNodeFuncsImplemented(); break;
                            	case 13: result = second_tester.testInsertComplexExtra(); break;
                            	case 14: result = second_tester.testDeleteComplex(); break;
                            	case 15: result = second_tester.testMinMaxComplex(); break;
                            	
                            	default:
                            		System.out.println("Test-case " + test_index_final + " not accounted for!");
                            		throw new Exception("Test-case " + test_index_final + " not accounted for!");
                            	}
                                success[test_index_final] = result ? SuccessStatus.PASS : SuccessStatus.FAIL;
                            } catch (Throwable e) {
                                success[test_index_final] = SuccessStatus.EXCEPTION;
                            }
                        }
                    }), test_index_final);
        }
        
        int final_score = printStatus(success, second_tester.avlOperations, /* elaborate= */ true);
        System.exit(final_score);
    }

    private static void runWithInterrupt(SuccessStatus[] success, Thread thread, int idx) {
        thread.start();

        for (int i = 0; i < 20; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!thread.isAlive())
                break;
        }
        if (thread.isAlive()) {
            thread.stop();
            success[idx] = SuccessStatus.EXCEPTION;
        }
    }
    
    public static int printStatus(SuccessStatus[] success, int wavlOperations, boolean elaborate) {
        int passed = 0;
        int exceptions = 0;
        String exceptionList = "";
        String passedList = "";
        String failedList = "";

        for (int i = 0; i < success.length; i++) {
            SuccessStatus succes = success[i];
            passed += succes == SuccessStatus.PASS ? 1 : 0;
            exceptions += succes == SuccessStatus.EXCEPTION ? 1 : 0;
            exceptionList += succes == SuccessStatus.EXCEPTION ? " " + i : "";
            passedList += succes == SuccessStatus.PASS ? " " + i : "";
            failedList += succes == SuccessStatus.FAIL ? " " + i : "";
        }

        System.out.println("============================================");
        System.out.println("==              Run Results:              ==");
        System.out.println("==  ==  ==  ==  ==  ==  ==  ==  ==  ==  ====");
        System.out.println("== Total Runs: " + success.length);
        System.out.println("== Passed Runs: " + passed);
        System.out.println("== Failed Runs: " + (success.length - passed));
        System.out.println("== Of which are exceptions: " + exceptions);
        System.out.println("== Exceptions occurred in cases: " + exceptionList);
        if (elaborate) {
            System.out.println("== The following cases passed: " + passedList);
            System.out.println("== The following cases failed: " + failedList);
        }
        int final_score = 4*passed;
        System.out.println("The final score is: " + final_score + " (out of " + 4*success.length + ").");
		return final_score;

    }
    
    
    /** 'Unit Tests', i.e. simple tests. */
    public static class SimpleTests {

        public boolean testEmpty() {
            AVLTree avlTree = new AVLTree();
            if (!avlTree.empty()) {
                return false;
            }
            avlTree.insert(1, "hello");
            return (!avlTree.empty());
        }

        public boolean testSearch() {
            AVLTree avlTree = new AVLTree();
            if (avlTree.search(1) != null) {
                return false;
            }
            avlTree.insert(1, "hello");
            if (avlTree.search(1).equals("hello")) {
                return true;
            }
            return false;
        }

        public boolean testInsert() {
        	int a=123,b=456,c=789;
        	AVLTree avlTree = new AVLTree();
        	// Insert an item twice.
        	if (avlTree.insert(a, "a") != 0) return false; // no re-balancing on the first item.
        	if (avlTree.insert(a, "aaa") != -1) return false;
        	
        	// Next, we check rotation counts in simple trees.
        	// We relax the test to ignore promotion/demotion counts.
        	
        	// case1: single rotation.
        	AVLTree avlTree2 = new AVLTree();
        	if (avlTree2.insert(a, "a") != 0) return false; // no re-balancing on the first item.
        	avlTree2.insert(b, "b");
        	if (avlTree2.insert(c, "c") < 1) return false; // at least one rotation over the edge (a,b).
        	
        	// case2: double rotation.
        	AVLTree avlTree3 = new AVLTree();
        	if (avlTree3.insert(a, "a") != 0) return false; // no re-balancing on the first item.
        	avlTree3.insert(c, "c");
        	if (avlTree3.insert(b, "b") < 2) return false; // at least two rotations over the edges (b,c) and then (a,b).
        	
        	return true;
        }

        public boolean testDelete() {
        	// Check no-deletion when the tree is empty.
            AVLTree avlTree = new AVLTree();
            if (avlTree.delete(0) != -1) { return false; }
            if (avlTree.delete(1) != -1) { return false; }
        	// Now fill the tree and expect to delete successfully.
            for (int i = 0; i < 100; i++) {
                avlTree.insert(i, "num" + i);
            }
            if (avlTree.delete(0) < 0) { return false; }
            if (avlTree.delete(1) < 0) { return false; }
            // Finally, test re-deletion.
            if (avlTree.delete(0) != -1) { return false; }
            if (avlTree.delete(1) != -1) { return false; }
            
            return true;
        }

        public boolean testMin() {
        	// Tests the min function in two phases between which it updates.
        	// We don't use deletion, to reduce logic-dependencies. 
            AVLTree avlTree = new AVLTree();
            if (avlTree.min() != null) {
                return false;
            }
            for (int i = 22; i < 33; i++) {
                    avlTree.insert(i, "num" + i);
            }
            if (!avlTree.min().equals("num22")) {
            	return false;
            }
            
            for (int i = 11; i < 22; i++) {
                avlTree.insert(i, "num" + i);
            }

            return (avlTree.min().equals("num11"));
        }

        public boolean testMax() {
        	// Tests the max function in two phases between which it updates.
        	// We don't use deletion, to reduce logic-dependencies.
            AVLTree avlTree = new AVLTree();
            if (avlTree.max() != null) {
                return false;
            }
            for (int i = 44; i <= 55; i++) {
                avlTree.insert(i, "num" + i);
            }
            if (!avlTree.max().equals("num55")) {
            	return false;
            }
            for (int i = 77; i > 66; i--) {
                avlTree.insert(i, "num" + i);
            }
            return (avlTree.max().equals("num77"));
        }

        
        public boolean testKeysToArray() {
        	// We test here only for non-empty trees.
        	// A full test would also consider an empty tree, but we neglect it here.
        	int total_size = 100;
            AVLTree avlTree = new AVLTree();
            String infoarray[];
            int[] keysarray;
            for (int i = 0; i < total_size; i++) {
                avlTree.insert(i, "num" + i);
            }
            keysarray = avlTree.keysToArray();
            infoarray = avlTree.infoToArray();
            for (int i = 0; i < total_size; i++) {
                if (!(infoarray[i].equals("num" + i) && keysarray[i] == i)) {
                    return false;
                }
            }
            return true;

        }

        public boolean testSize() {
            AVLTree avlTree = new AVLTree();
            for (int i = 0; i < 100; i++) {
                avlTree.insert(i, "num" + i);
            }
            if (avlTree.size() != 100) return false;
            for (int i = 0; i < 50; i++) {
                avlTree.delete(i);
            }
            if (avlTree.size() != 50) return false;
            for (int i = 0; i < 25; i++) {
                avlTree.insert(i, "num" + i);
            }
            if (avlTree.size() != 75) return false;
            return true;
        }

        public boolean testJoin() {
        	// We test a join of two non-empty trees with same/different heights, in 4 scenarios:
        	// #1,#2 join-left ('this' has smaller keys), #3,#4 join-right (larger keys).
        	// #1,#3 has same-height trees, #2,#4 has different height.
        	// A full test would also check handling empty trees, but we neglect it here.
        	AVLTree tree_main,tree_other;
        	 
        	for (int scenario=1; scenario<=4; scenario++) {
        		int merged_size = 5;
        		AVLTree tree1 = new AVLTree();
            	tree1.insert(1, "numMin");
            	if (scenario % 2 == 1) {  // scenarios 1,3: this makes both trees of the same height.
            		tree1.insert(2, "num2");
            		merged_size += 1;
            	}
            	AVLTree tree_temp = new AVLTree(); // used to generate the joining IAVLNode.
            	tree_temp.insert(3, "numA");
            	AVLTree tree2 = new AVLTree();
            	tree2.insert(4, "numB");
            	tree2.insert(5, "numC");
            	tree2.insert(6, "numMax");
            	
            	if (scenario <= 2) { // scenarios 1,2
            		tree_main = tree1;
            		tree_other = tree2;
            	} else {  // scenarios 3,4
            		tree_main = tree2;
            		tree_other = tree1;
            	}
            	
            	if (tree_main.join(tree_temp.getRoot(), tree_other) < 0) {
            		return false;
            	}
            	if (tree_main.size() != merged_size ||
            			!tree_main.min().equals("numMin") ||
            			!tree_main.max().equals("numMax")
            			) {
            		return false;
            		}
        	}
        	return true;
        }
        
        public boolean testSplit() {
        	// First we split in the middle, then we split such that one tree is empty.
        	int total_size = 100;
        	int split_value = 66;
        	int next_split = split_value - 1;
            AVLTree avlTree = new AVLTree();
            AVLTree[] trees;
            for (int i = 0; i < total_size; i++) {
                avlTree.insert(i, "num" + i);
            }
            // Test a "middle split", and verify the location of every item of the tree.
            trees = avlTree.split(split_value);
            if (trees.length != 2) { return false; }
            if (trees[0].size() + trees[1].size() != total_size-1) { return false; }
            
            for (int i=0; i<split_value; i++) {
            	if(trees[0].search(i) == null || trees[1].search(i) != null) {
            		return false;
            	}
            }
            for (int i=split_value+1; i<total_size; i++) {
            	if(trees[0].search(i) != null || trees[1].search(i) == null) {
            		return false;
            	}
            }
            if(trees[0].search(split_value) != null || trees[1].search(split_value) != null) {
        		return false;
        	}
            
            // Test a "side split" of trees[0] to verify that the second output tree is empty.
            int original_size = trees[0].size();
            trees = trees[0].split(next_split);
            if (trees.length != 2) return false;
            if (!trees[1].empty()) return false;
            if (trees[0].size() + trees[1].size() != original_size-1) { return false; }

            // Finally, test a "middle split" that results in two empty trees.
            AVLTree avlTreeOneItem = new AVLTree();
            avlTreeOneItem.insert(123, "root only");
            trees = avlTreeOneItem.split(123);
            if (!trees[0].empty() || !trees[1].empty()) return false;
            
            return true;
        }

        public boolean testSelect() {
            AVLTree avlTree = new AVLTree();
            for (int i = 10; i < 1000; i++) {
    			avlTree.insert(i, "num" + i);
            }
            if (avlTree.search(0) != null) return false;
            if (!avlTree.search(500).equals("num" + 500)) return false;
            if (!avlTree.search(999).equals("num" + 999)) return false;
            if (avlTree.search(1111) != null) return false;
            return true;
        }

        public boolean testAvlNodeFuncsImplemented() {
            AVLTree avlTree = new AVLTree();
            avlTree.insert(101, "n101");
            AVLTree.IAVLNode avlNode = avlTree.getRoot();
            
            // avlNode.isRealNode(); // technically should be 'true' since the root is still 'legally' in the tree, but we neglect this check.
            if (avlNode.getKey() != 101) {return false;}
            if (avlNode.getValue() != "n101") {return false;}
            avlNode.setLeft(avlNode); if (avlNode.getLeft() != avlNode) {return false;}
            avlNode.setRight(avlNode); if (avlNode.getRight() != avlNode) {return false;}
            avlNode.setParent(avlNode); if (avlNode.getParent() != avlNode) {return false;}
            avlNode.setHeight(456); if (avlNode.getHeight() != 456) {return false;}
            return true;
        }

        boolean testIsBalanced() {
        	// Test twice: once insertion in order, once in "random" order.
        	AVLTree avlTree = new AVLTree();
        	AVLTree avlTree2 = new AVLTree();
        	int[] values_unordered = new int[]{17, 6, 1, 19, 18, 3, 2, 10, 13, 12,
                    20, 15, 4, 11, 7, 16, 9, 5, 8, 14, 28};
            for (int v=1; v<2000;v++) {
                avlTree.insert(v, "" + v);
            }
            for (int v : values_unordered) {
                avlTree2.insert(v, "" + v);
            }
            int height1 = getHeightIfBalancedRecursively(avlTree.getRoot());
            int height2 = getHeightIfBalancedRecursively(avlTree.getRoot());
            
            // We don't use explicitly 'isBalancedRecursively' because an empty implementation
            // that doesn't insert anything remains balanced, and this misses the point.
            // Rather than testing 'isEmpty()', we just require some minimal depth.
            return (height1 >= 2 && height2 >= 2);
        }

        public boolean isBalancedRecursively(AVLTree.IAVLNode current) {
        	return getHeightIfBalancedRecursively(current) >= 0;
        }
        
        /* Returns the height of a tree if it is balanced (h >= 0), else returns -1. */
        int getHeightIfBalancedRecursively(AVLTree.IAVLNode node) {
        	/* base case tree is empty */
            if (node == null || node.isRealNode() == false)
                return 0;
            
            /* Get the height of left and right sub trees */
            int lh = getHeightIfBalancedRecursively(node.getLeft());
            int rh = getHeightIfBalancedRecursively(node.getRight());
            
            if (lh == -1 || rh == -1)
            	return -1; // Imbalance in subtrees.
            if (Math.abs(lh - rh) > 1)
                return -1; // Imbalance in root.
            return 1 + Math.max(lh, rh); // Height of the root.
        }
    }
    
    /** Logic-heavy (relatively speaking) tests. */
    public static class ComplexTests {
    	public static int INFINITY_CONST = 999999999;
    	
        AVLTree avlTree;
        int avlOperations;
        SimpleTests tester_util;
        
        public ComplexTests() {
            avlTree = null;
            avlOperations = 0;
            tester_util = new SimpleTests(); // Used for its balance-check function.
        }

        /** Inserts a bunch of keys at arbitrary order. Then deletes them in some nice order and
         * tracks the min/max values. */
        public boolean testMinMaxComplex() {
        	
            AVLTree tree = new AVLTree();
            if (!tree.empty()) { return false; }
            int[] insert_values = new int[]{1,2,3,4,5,6,7,8,9,10};
            int[] delete_values = new int[]{1,10,2,9,3,8,4,7,5,6};
            int[] max_values = new int[]{10,10,9,9,8,8,7,7,6,6};
            int[] min_values = new int[]{1,2,2,3,3,4,4,5,5,6};
            
            for (int val : insert_values) {
                tree.insert(val, "" + val);
            }
            for (int i=0; i<10; i++) {
            	if (!tree.min().equals(""+min_values[i])
            			|| !tree.max().equals(""+max_values[i])) { return false; }
            	if (!tester_util.isBalancedRecursively(tree.getRoot())) {return false;}
            	tree.delete(delete_values[i]);
            }
            if (tree.min() != null || tree.max() != null) {return false;}
            return true;
        }
        
        public boolean testInsertComplexExtra() {  // legacy name, there was 'testInsertComplex' function.
            avlTree = new AVLTree();
            int[] values_unordered = new int[]{17, 6, 1, 19, 18, 3, 2, 10, 13, 12,
                    20, 15, 4, 11, 7, 16, 9, 5, 8, 14, 28};
            
            // Tests more thoroughly that inserting an existing key returns -1.
            for (int val : values_unordered) {
                avlOperations += avlTree.insert(val, "" + val);
                int cont = avlTree.insert(val, "" + (-1));  // Garbage string, val is already in the tree.
                if (cont != -1) {
                    System.out.println("Re-inserted key did not return -1 (ret_val = " + cont + ").");
                    return false;
                }
            }
            // Tests that the minimum/maximum are correct.
            if (!avlTree.max().equals(""+Arrays.stream(values_unordered).max().getAsInt()) ||
                    !avlTree.min().equals(""+Arrays.stream(values_unordered).min().getAsInt())) {
                return false;
            }
            // Tests that every key belongs in the tree. Twice.
            for (int iter_loop=1; iter_loop<=2; iter_loop++) {
    	        for (int val : values_unordered) {
    	            if ((intValue(avlTree.search(val)) != val)) {
    	                System.out.println("It is loop #" + iter_loop + ".");
    	                return false;
    	            }
    	        }
            }
            return true;
        }
        // This is a legacy function used only in 'testInsertComplexExtra'.
        public static int intValue(String str){
            if(str == null) return -1;
            else return Integer.parseInt(str);
        }
        
        /** Insert a bunch of keys at arbitrary order. Then delete all of them in a different order.
         * Checks a few invariants throughout the deletion (balance, etc.). Finally, verifies an empty tree. */
        public boolean testDeleteComplex() {
            AVLTree tree = new AVLTree();
            if (!tree.empty()) { return false; }
            int[] insert_values = new int[]{16, 24, 36, 19, 44, 28, 61, 74, 83, 64, 52, 65, 86, 93, 88};
            int[] delete_values = new int[]{88, 19, 16, 28, 24, 36, 52, 93, 86, 83, 44, 61, 74, 64, 65};
            
            for (int val : insert_values) {
                tree.insert(val, "" + val);
            }
            int expected_size = insert_values.length;
            if (tree.size() != expected_size) { return false; }
            
            if (!tree.min().equals(""+Arrays.stream(insert_values).min().getAsInt()) ||
            		!tree.max().equals(""+Arrays.stream(insert_values).max().getAsInt()) ||
            		!tester_util.isBalancedRecursively(tree.getRoot()) ||
            		!checkBstProperty(tree.getRoot())) {
                return false;
            }

            for (int v : delete_values) {
            	tree.delete(v);
            	expected_size -= 1;
            	if (tree.size() != expected_size) { return false; }
                if (!tester_util.isBalancedRecursively(tree.getRoot())) { return false; }
                if (!checkBstProperty(tree.getRoot())) { return false; }
                if (tree.search(v) != null) { return false; }
            }
            if (!tree.empty()) { return false; }
            return true;
        }
        
        
        public boolean checkBstPropertyInBounds(AVLTree.IAVLNode current, int lower_bound, int upper_bound) {
        	if (current == null || !current.isRealNode()) return true;
        	int key = current.getKey();
        	if (key > upper_bound || key < lower_bound) return false; 
        	return  checkBstPropertyInBounds(current.getLeft(), lower_bound, key) &&
        			checkBstPropertyInBounds(current.getRight(), key, upper_bound);
        }
        
        public boolean checkBstProperty(AVLTree.IAVLNode root_of_scan) {
        	return checkBstPropertyInBounds(root_of_scan, -INFINITY_CONST, INFINITY_CONST);
        }
    }
}