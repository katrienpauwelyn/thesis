/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
tot op 5 cijfers na de komma
 */
package dataTypes;

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
    
    public void setFirst(float f){
        first = f;
    }
    
    public void setSecond(float f){
        second = f;
    }
    
    public double getFirst(){
        return (double) Math.round(first * 100000) / 100000;
    }
    
    public double getSecond(){
        return (double) Math.round(second * 100000) / 100000;
    }
    
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
    
}
