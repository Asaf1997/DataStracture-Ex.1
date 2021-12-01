package src;

public class tester {
    public static void main(String[] args) {
        asafTest();
        idoTest();
    }

    public static void idoTest(){

    }

    public static void asafTest(){
        AVLTree t1 = new AVLTree();
        AVLTree.AVLNode a1 = new AVLTree.AVLNode(1, "a");
        AVLTree.AVLNode a2 = new AVLTree.AVLNode(2, "b");
        AVLTree.AVLNode a3 = new AVLTree.AVLNode(3, "c");
        AVLTree.AVLNode a4 = new AVLTree.AVLNode(4, "d");
        AVLTree.AVLNode a5 = new AVLTree.AVLNode(5, "e");

        a2.setLeft(a1);
        a2.setRight(a3);
        a4.setLeft(a2);
        a4.setRight(a5);

        t1.root = a4;
        t1.treePrinter();
    }

}
