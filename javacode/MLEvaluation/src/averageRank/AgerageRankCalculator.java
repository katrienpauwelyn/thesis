/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package averageRank;

import java.util.Arrays;

/**
 *
 * @author katie
 */
public class AgerageRankCalculator {
    
    //per rij een classifier
    //dezelfde volgorde van classifiers aanhouden voor input en output
    //stablesort mag hier omdat enkel OneError dubbele waarden heeft (en deze verschilt niet significant)
    public static double[] getAverageRanks(double[][] results, boolean lowerIsBetter){
        int nbDatasets = results[0].length;
        int nbClassifiers = results.length;
        double[] ranks = new double[nbClassifiers];
        for(int dataset= 0; dataset<nbDatasets; dataset++){
            double column[] = new double[nbClassifiers];
            for(int classifier=0; classifier<nbClassifiers ; classifier++){
                column[classifier] = results[classifier][dataset];
            }
                int[] temp = weka.core.Utils.stableSort(column);
                int[] ranksDataset = convertWekaToRanks(temp, lowerIsBetter);
                for(int i = 0; i<ranksDataset.length; i++){
                    ranks[i]+=ranksDataset[i];
                }
            
        }
        for(int i = 0; i<nbClassifiers; i++){
            ranks[i]/=nbDatasets;
        }
        return ranks;
    }
    
    
    public static int[] convertWekaToRanks(int[] temp, boolean lowerIsBetter){
        int[] output = new int[temp.length];
        for(int i = 0; i<output.length; i++){
            if(lowerIsBetter){
                output[temp[i]] = i+1;
            }
            else {
                output[temp[i]] = temp.length-i;
            }
        }
        return output;
    }
    
    
    public static void main(String[] args){
      /*  double[] a = {0.5, 3, 1};
        double[] b = {2,2,2};
        double[] d = {1,7,2,5,1,1};
        double[] e = {3,1,3,1,2,3};
        double[]f = {0,0,0,0,0,0};
        int[] c = {1, 0, 2};
        double[][] matrix = {d,e,f};
      //  System.out.println(Arrays.toString(convertWekaToRanks(c, true)));
        System.out.println(Arrays.toString(getAverageRanks(matrix, true)));*/
      System.out.println("rankingLoss");
      double[][] matrixRankingLoss = {Data.rankingLossFlat, Data.rankingLossHier, Data.rankingLossKMeans, Data.rankingLossKMeansDiffHiers};
      System.out.println(Arrays.toString(getAverageRanks(matrixRankingLoss, true)));
      System.out.println();
      System.out.println("microRoC");
      double[][] matrixMicroROC = {Data.microROCFlat, Data.microROCHier, Data.microROCKMeans, Data.microROCKMeansDiffHiers};
      System.out.println(Arrays.toString(getAverageRanks(matrixMicroROC, false)));
      System.out.println(Data.microROCFlat.length);
      
      
      System.out.println((double)2.569*Math.sqrt((double)20/54));
        
    }
    
    
    
}
