/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Irrelevant;

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
    
    public static double getHammingLoss(String pathToPredFile, boolean hasKey) throws FileNotFoundException, IOException{
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
            totalXor += xor(line, nbClasses, hasKey);
            nbInstances++;
        }
        totalXor /= nbClasses;
        totalXor /= nbInstances;
        return totalXor;
    }
    
    
    //getest
    public static double xor(String line, int nbClasses, boolean hasKey){
        String[] parsed = line.split(",");
        double xor = 0.0;
        int offset = 1;
        if(hasKey){
            offset++;
        }
        for(int i = 0; i<nbClasses; i++){
            double sum = Double.parseDouble(parsed[i+offset])-Double.parseDouble(parsed[i+offset+nbClasses]);
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
    //System.out.println(getHammingLoss("/Users/katie/thesiscode/datasets/multilabel/genbase/settings.test.pred.arff", true));
    String line = "Q9Y7D2,PDOC00064@PDOC50156,"
            + "0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,"
            + "0.0,0.0,0.0,0.9999999999999999,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.9999999999999999,0.0,0.0,\"0+0+0+0+0+0+0+0+0+0\"";
    System.out.println(xor(line, 27,true));
    }
    
}
