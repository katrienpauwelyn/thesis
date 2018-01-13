/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Irrelevant;

import java.util.Arrays;

/**
 * @author katie
 */
public class WCalculator {
    
    public double getW(double[] array1, double[] array2){
        if(array1.length!=array2.length){
            throw new Error("de twee arrays hebben geen gelijke lengte");
        }
        int nbSame = nbSame(array1, array2);
        
        Wilcox[] tuples = new Wilcox[array1.length-nbSame];
        System.out.println("aantal vergelijkingspunten: "+tuples.length);
        int nb = 0;
        for(int i = 0; i<array1.length; i++){
            if(array1[i]!=array2[i]){
                tuples[nb] = new Wilcox(array1[i], array2[i]);
                nb++;
            }  
        }
        Arrays.sort(tuples); //van klein naar groot gesorteerd
        checkAllDifferent(tuples);
        double w = 0;
        for(int i = 0; i<tuples.length; i++){
            int rank = i+1;
            w += rank*tuples[i].getSign();
        }
        return w;
    }
    
    public static void main(String[] args){
        WCalculator calc = new WCalculator();
     System.out.println("oneError wilcoxon KMeans-Flat: "+calc.getW(Data.oneErrorFlat, Data.oneErrorKMeans));
     System.out.println("coverage wilcoxon KMeans-Flat: "+calc.getW(Data.coverageFlat, Data.coverageKMeans));
     System.out.println("rankingLoss wilcoxon KMeans-Flat: "+calc.getW(Data.rankingLossFlat, Data.rankingLossKMeans));
     System.out.println("average precision wilcoxon KMeans-Flat: "+calc.getW(Data.averagePrecisionFlat, Data.averagePrecisionKMeans));
 
 System.out.println("microPR wilcoxon KMeans-Flat "+calc.getW(Data.microPRFlat, Data.microPRKMeans));
 System.out.println("microROC wilcoxon KMeans-Flat "+calc.getW(Data.microROCFlat, Data.microROCKMeans));
 System.out.println("macroPR wilcoxon KMeans-Flat "+calc.getW(Data.macroPRFlat, Data.macroPRKMeans));
 System.out.println("macroROC wilcoxon KMeans-Flat "+calc.getW(Data.macroROCFlat, Data.macroROCKMeans));
    }

    
    //check hoe vaak array1[i]-array2[i] nul is
    private int nbSame(double[] array1, double[] array2) {
        int nbSame = 0;
        for(int i = 0; i<array1.length; i++){
            if(array1[i]==array2[i]){
                nbSame++;
            }
        }
        return nbSame;
    }

    //check of alle tuples een andere waarde hebben
    private void checkAllDifferent(Wilcox[] tuples) {
        for(int i = 0; i<tuples.length-1; i++){
            if(tuples[i].difference==tuples[i+1].difference){
                throw new Error("twee tuples hebben dezelfde difference");
            }
        }
    }
    
}
