/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statistics;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author katie
 */
public class HammingLoss {
    
    
    //TODO genbase heeft een key: iets anders voor de parsing (key staat voor alles)
    
    public static double getHammingLoss(String pathToPredFile) throws FileNotFoundException, IOException{
        BufferedReader reader = new BufferedReader(new FileReader(pathToPredFile));
        String line;
        int nbClasses = 1;
        while(!reader.readLine().contains("Original-p-")){}
        while(reader.readLine().contains("Original-p-")){nbClasses++;}
        System.out.println(nbClasses + " nbClasses");
        while(!reader.readLine().contains("@DATA")){}
        double totalXor = 0.0;
        int nbInstances = 0;
        while((line=reader.readLine())!=null && !line.isEmpty()){
            totalXor += xor(line, nbClasses);
            nbInstances++;
        }
        totalXor /= nbClasses;
        totalXor /= nbInstances;
        return totalXor;
    }
    
    
    //getest
    public static double xor(String line, int nbClasses){
        String[] parsed = line.split(",");
        double xor = 0.0;
        for(int i = 0; i<nbClasses; i++){
            double sum = Double.parseDouble(parsed[i+1])-Double.parseDouble(parsed[i+1+nbClasses]);
            xor += Math.abs(sum);
        }
        return xor;
    }
    
   /** public static double xorThreshold(String line, int nbClasses, int threshold){
         String[] parsed = line.split(",");
        double xor = 0.0;
        for(int i = 0; i<nbClasses; i++){
            double sum = Double.parseDouble(parsed[i+1])-Double.parseDouble(parsed[i+1+nbClasses]);
            xor += Math.abs(sum);
        }
        return xor;
    }*/
    
    
    public static void main(String[] args) throws FileNotFoundException, IOException{
    System.out.println(getHammingLoss("/Users/katie/thesiscode/datasets/multilabel/emotions/settings.test.pred.arff"));
    }
    
}
