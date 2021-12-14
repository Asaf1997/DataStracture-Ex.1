package src;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

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

    public static int numOf(Integer[] a){
        int counter = 0;
        for (int i = 0 ; i < a.length ; i++){
            for (int j = i + 1 ; j < a.length ; j++){
                if (a[i] > a[j]){
                    counter ++;
                }
            }
        }
        return counter;
    }

    public static int insertThisShitToATree(Integer[] a){
        int counter = 0;
        FingerTree t = new FingerTree();
        for (int i = 0 ; i < a.length ; i++){
            counter += t.insert(a[i], "");
        }
        return counter;
    }

    public static void asafTest(){

/**
        AVLTree t1 = new AVLTree();
        for (int i=1 ; i <= 10 ; i++){
            t1.insert(i, "");
        }
        t1.print();

        AVLTree[] aaa = t1.split(5);

        aaa[0].print();
        aaa[1].print();
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

/**
        for (int i = 1 ; i <= 5 ; i++){
            int c = (int) (1000 * Math.pow(2,i));
            System.out.println("i = "+ i + ", c = "+ c);
            Set<Integer> s = new HashSet<>();
            Random r = new Random();
            while (s.size() < c){
                s.add(r.nextInt(10*c)+1);
            }
            Integer[] a = s.toArray(new Integer[s.size()]);
            System.out.println("num of shit = " + numOf(a));
            System.out.println("cost of shit = " + insertThisShitToATree(a));

            System.out.println();
            System.out.println();
        }
**/
/**
        for (int i = 1 ; i <= 5 ; i++){
            int c = (int) (1000 * Math.pow(2,i));
            System.out.println("i = "+ i + ", c = "+ c);
            Integer[] a = new Integer[c];
            for (int p=0 ; p < c ; p++){
                a[p] = c - p;
            }

            System.out.println("num of shit = " + numOf(a));
            System.out.println("cost of shit = " + insertThisShitToATree(a));

            System.out.println();
            System.out.println();
        }**/
    }
}

