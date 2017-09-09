/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Irrelevant;

/**
 *
 * @author katie
 */
public class RankTuple implements Comparable<RankTuple> {
    public int label;
    public double prediction;
    
    public RankTuple(int l, double p){
        label = l;
        prediction = p;
    }

    @Override
    //van hoge predictie naar lage predictie sorteren
    public int compareTo(RankTuple o) {
        if(o.prediction == prediction){
            return 0;
        }
        if(o.prediction > prediction){
            return 1;
        }
        return -1;
    }
    
    public String toString(){
        return "label "+label+" | prediction "+prediction;
    }
    
    
    
}
