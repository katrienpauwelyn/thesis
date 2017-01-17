/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NDResults;

/**
 *
 * @author katie
 */
public class TupleInt {
    
    private int positive;
    private int negative;
    
    public TupleInt(){
        positive = 0;
        negative = 0;
    }
    
    public TupleInt(int pos, int neg){
        setPositive(pos);
        setNegative(neg);
    }
    
    public void setPositive(int pos){
        positive = pos;
    }
    
    public void setNegative(int neg){
        negative = neg;
    }
    
    public int getPositive(){
        return positive;
    }
    
    public int getNegative(){
        return negative;
    }
    
    public void incrementPositiveOne(){
        positive++;
    }
    
    public void incrementNegativeOne(){
        negative++;
    }
    
    public void incrementPositive(int pos){
        positive += pos;
    }
    
    public void incrementNegative(int neg){
        negative += neg;
    }
    
    public double getAccuracy(){
        return (double) positive/(positive+negative);
    }
    
    //increments this tuple with another tuple
    public void addTuple(TupleInt t){
        this.positive += t.positive;
        this.negative += t.negative;
    }
    
}
