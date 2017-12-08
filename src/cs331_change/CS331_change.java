/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs331_change;

/**
 *
 * @author Austin
 */
public class CS331_change {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int[] denom = {1, 5, 10, 25};
        
        long startTime = System.nanoTime();
        for (int i = 0; i < 100; i++) {
            int amount = (int) (Math.random() * 99 + 1);
            int[] change = makeChange(amount, denom);
            //System.out.println(change.toString());
            
        }
        double greedyTime = System.nanoTime() - startTime;
        greedyTime /= 1000000;
        
        startTime = System.nanoTime();
        for (int i = 0; i < 100; i++) {
            int amount = (int) (Math.random() * 99 + 1);
            int[][]change = makeChangeDynProg(amount, denom);
        }
        double dynamicTime = System.nanoTime() - startTime;
        dynamicTime /= 1000000;
        
        System.out.println("Greedy time:  " + greedyTime + " ms");
        System.out.println("Dynamic time: " + dynamicTime + " ms");
        
        //int[][] change = makeChangeDynProg(100, denom);
        
//        if (change == null) {
//            System.out.println("No Solution");
//            System.exit(0);
//        }
        
//        for (int i = 0; i < change.length; i++) {
//            System.out.println(change[i]);
//        }
    }
    
    public static int[] makeChange(int n, int[] denom) {
        int s = 0;
        int[] change = new int[denom.length];
        
        while (s < n) {
            int i = findNextCoin(denom, n, s);
            
            if (i == -1) {
                return null; //No Solution
            }
            
            change[i] = change[i] + 1;
            s = s + denom[i];
        }
        
        return change;
    }
    
    public static int[][] makeChangeDynProg(int n, int[] denom) {
        n += 1;
        int[][] change = new int[denom.length][n];
        
        for (int i = 0; i < n; i++) {
            change[0][i] = 0;
        }
        
        for (int i = 0; i < denom.length; i++) {
            for (int j = 0; j < n; j++) {
                int val;
                if (i == 0 && j < denom[i]) {
                    val = 0; //INFINITY
                } else if (i == 0) {
                    //val = 1 + change[0][j - denom[0]];
                    val = 1 + change[0][j - denom[0]];
                } else if (j < denom[i]) {
                    val = change[i - 1][j];
                } else {
                    val = Math.min(change[i - 1][j], 1 + change[i][j - denom[i]]);
                }
                
                change[i][j] = val;
            }
        }
        return change;
    }
    
    public static int findNextCoin(int[] denom, int n, int s) {
        for (int i = denom.length - 1; i >= 0; i--) {
            if (s + denom[i] <= n) {
                return i;
            }
        }
        return -1;
    }
    
}
