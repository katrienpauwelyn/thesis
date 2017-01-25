/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wilcoxon;

import java.util.Set;
import java.util.TreeMap;

/**
 *
 * @author katie
 */
public class WilcoxonMap {
    TreeMap<Double, WilcoxonTuple> treeMap;
    
    
    public WilcoxonMap(){
        treeMap  = new TreeMap<Double, WilcoxonTuple>();
    }
    
    public void put(double d, WilcoxonTuple tuple){
        treeMap.put(d, tuple);
    }
    
    public double calculateWilcoxonScore(){
        double ndScore = 0;
        double clusScore = 0;
        treeMap.remove(0.0);
        double[] ranks = getRanks();
        for(int i = 0; i<ranks.length; i++){
            if(ranks[i]>0){
                ndScore+= ranks[i];
            } else {
                clusScore+=ranks[i];
            }
        }
         return ndScore;
    }
    
    
    public double[] getRanks(){
        double[] ranks = new double[treeMap.size()];
        for(int i = 0; i<treeMap.size(); i++){
            ranks[i] = i+1;
        }
        
        for(int i = 0; i<treeMap.size(); i++){
            Object[] keys = treeMap.keySet().toArray();
            double d = (double) keys[i];
            WilcoxonTuple t = treeMap.get(d);
            int nbSame = 1;
              double sumOfRanks = ranks[i];
            if(i!=treeMap.size()-1){
                double next = (double) keys[i+1];
          
            
            
            while(d==next){
                sumOfRanks+=ranks[i+nbSame];
                nbSame ++;
                next = (double) keys[i+nbSame];
            }
            }
            
            if(nbSame > 1){
               for(int nb = 0; nb < nbSame; nb++){
                   ranks[i+nb] = sumOfRanks / nbSame;
               }
            }
            
            i+=nbSame-1;
            
        }
        
        for(int i = 0; i<treeMap.size(); i++){
            Object[] keys = treeMap.keySet().toArray();
            if(!treeMap.get(keys[i]).ndBetter()){
                ranks[i]*=(-1.0);
            }
        }
        return ranks;
    }
    
    
    public int getSize(){
        return treeMap.size();
    }
    
    public static void main(String[] args){
        TreeMap<Double, String> test = new TreeMap<Double, String>();
        test.put(2.0, "a");
        test.put(2.0,"b");
 
    }
}
