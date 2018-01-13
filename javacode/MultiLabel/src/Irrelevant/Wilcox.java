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
public class Wilcox implements Comparable<Wilcox>{
    
    double difference;
    
    public Wilcox(double a, double b){
        difference = b-a;
        if(difference==0){
            throw new Error("gelijk aan nul");
        }
    }
    
    public int getSign(){
        if(difference<0){
            return -1;
        } else {
            return 1;
        }
    }
    
    @Override
    //this is kleiner dan o => return -1
    public int compareTo(Wilcox o) {
        if(Math.abs(difference)<Math.abs(o.difference)){
            return -1;
        }
        if(Math.abs(difference)>Math.abs(o.difference)){
            return 1;
        }
        throw new Error("twee gelijke rankings");
       // return 0;
    }
    
    
    
}
