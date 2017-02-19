/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hsc;

/**
 *
 * @author katie
 */
public class AccTuple {
    
    private int nb;
    private double sumOfAcc;
    
    public AccTuple(){
        nb = 0;
        sumOfAcc = 0.0;
    }
    
    public void add(double acc){
        nb ++;
        sumOfAcc+=acc;
    }
    
    public double meanAcc(){
        return sumOfAcc/nb;
    }
    
    public double meanAccPercent(){
        return Math.floor(sumOfAcc*100000/nb)/100000;
    }
}
