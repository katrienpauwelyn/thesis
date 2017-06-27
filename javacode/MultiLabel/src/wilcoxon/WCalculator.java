/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wilcoxon;

import java.util.Arrays;

/**
 *
 * @author katie
 */
public class WCalculator {
    
    public double getW(double[] array1, double[] array2){
        if(array1.length!=array2.length){
            throw new Error("de twee arrays hebben geen gelijke lengte");
        }
        Wilcox[] tuples = new Wilcox[array1.length];
        for(int i = 0; i<tuples.length; i++){
            tuples[i] = new Wilcox(array1[i], array2[i]);
        }
        Arrays.sort(tuples); //van klein naar groot gesorteerd
        double w = 0;
        for(int i = 0; i<tuples.length; i++){
            int rank = i+1;
            w += rank*tuples[i].getSign();
        }
        return w;
    }
    
    public static void main(String[] args){
        WCalculator calc = new WCalculator();
 /*    System.out.println("oneError wilcoxon: "+calc.getW(Data.oneErrorHier, Data.oneErrorFlat));
     System.out.println("coverage wilcoxon: "+calc.getW(Data.coverateHier, Data.coverageFlat));
     System.out.println("rankingLoss wilcoxon: "+calc.getW(Data.rankingLossHier, Data.rankingLossFlat));
     System.out.println("average precision wilcoxon: "+calc.getW(Data.averagePrecisionHier, Data.averagePrecisionFlat));*/
 
 System.out.println("microPR wilcoxon "+calc.getW(Data.microPRHier, Data.microPRFlat));
 System.out.println("microROC wilcoxon "+calc.getW(Data.microROCHier, Data.microROCFlat));
 System.out.println("macroPR wilcoxon "+calc.getW(Data.macroPRHier, Data.macroPRFlat));
 System.out.println("macroROC wilcoxon "+calc.getW(Data.macroROCHier, Data.macroROCFlat));
    }
}
