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
        AVLTree.AVLNode a1 = new AVLTree.AVLNode(1, "1");
        AVLTree.AVLNode a2 = new AVLTree.AVLNode(2, "2");
        AVLTree.AVLNode a3 = new AVLTree.AVLNode(3, "3");
        AVLTree.AVLNode a4 = new AVLTree.AVLNode(4, "4");
        AVLTree.AVLNode a5 = new AVLTree.AVLNode(5, "5");
        AVLTree.AVLNode a6 = new AVLTree.AVLNode(6, "6");
        AVLTree.AVLNode a7 = new AVLTree.AVLNode(7, "7");

        a2.setLeft(a1);
        a2.setRight(a3);
        a6.setLeft(a5);
        a6.setRight(a7);
        a4.setLeft(a2);
        a4.setRight(a6);

        t1.root = a4;
        t1.print();

        t1.rotateLeft(t1.root);
        t1.print();

    }

}
