package src;

import java.util.Arrays;

public class tester {
    public static void main(String[] args) {
        asafTest();
        //idoTest();
    }

    public static void idoTest(){
        /**
        AVLTree t2 = new AVLTree();
        AVLTree.AVLNode a1 = new AVLTree.AVLNode(1, "1");
        AVLTree.AVLNode a2 = new AVLTree.AVLNode(2, "2");
        AVLTree.AVLNode a3 = new AVLTree.AVLNode(3, "3");
        t2.insert(a1.getKey(), a1.getValue());
        t2.insert(a2.getKey(), a2.getValue());
        System.out.println(t2.root.getHeight());
        t2.delete(a2.getKey());
        System.out.println(t2.root.getHeight());
         **/
    }

    public static void asafTest(){
        /**
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
**/
/**
        AVLTree t2 = new AVLTree();
        t2.insert(14,"14");
        t2.insert(12,"12");
        t2.insert(11,"11");
        t2.insert(13,"13");
        t2.insert(15,"15");
        t2.insert(1,"1");
        t2.insert(2,"2");
        t2.insert(3,"3");
        t2.insert(4,"4");
        t2.print();

        System.out.println(t2.delete(12));
        System.out.println(t2.delete(11));
        System.out.println(t2.delete(3));
        System.out.println(t2.delete(1));
        System.out.println(t2.delete(4));
        System.out.println(t2.delete(2));
        t2.print();

        AVLTree[] aa = t2.split(11);
        aa[0].print();
        aa[1].print();
       **/

    }

}
