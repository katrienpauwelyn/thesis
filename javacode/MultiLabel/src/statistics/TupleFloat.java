/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
tot op 5 cijfers na de komma
 */
package statistics;

/**
 *
 * @author katie
 */
public class TupleFloat {
    private double first;
    private double second;
    
    public TupleFloat(){
        first = 0;
        second = 0;
    }
    
    //Auprc
    public void setFirst(float f){
        first = f;
    }
    
    //auroc
    public void setSecond(float f){
        second = f;
    }
    
    //Auprc
    public double getFirst(){
        return first;
    }
    
    //auroc
    public double getSecond(){
        return second;
    }
    
 /**   public double getFirstRounded(){
        return (double) Math.round(first * 100000) / 1000;
    }
    
    public double getSecondRounded(){
        return (double) Math.round(second * 100000) / 1000;
    }*/
    
    public void incrementFirstWith(double f){
        first += f;
    }
    
    public void incrementSecondWith(double f){
        second += f;
    }
    
    public void divideBothWith(float f){
        first /= f;
        second /=f;
    }
    
    public String print(){
        return "first: "+first+" second: "+second;
    }
    
}
