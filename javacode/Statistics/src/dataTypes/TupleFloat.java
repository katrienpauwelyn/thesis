/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataTypes;

/**
 *
 * @author katie
 */
public class TupleFloat {
    private float first;
    private float second;
    
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
    
    public float getFirst(){
        return first;
    }
    
    public float getSecond(){
        return second;
    }
    
    public void incrementFirstWith(float f){
        first += f;
    }
    
    public void incrementSecondWith(float f){
        second += f;
    }
    
    public void divideBothWith(float f){
        first /= f;
        second /=f;
    }
    
}
