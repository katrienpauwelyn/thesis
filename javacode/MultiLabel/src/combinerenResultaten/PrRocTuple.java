/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package combinerenResultaten;

/**
 *
 * @author katie
 */
public class PrRocTuple {
    
    public double pr;
    public double roc;

    public PrRocTuple(double prNew, double rocNew) {
        pr = prNew;
        roc = rocNew;
    }
    
    public void print(){
        System.out.println("pr: "+pr+" roc: "+roc);
    }
    
    public void add(PrRocTuple tuple){
        this.pr += tuple.pr;
        this.roc += tuple.roc;
    }
    
    public void divide(int nb){
        pr = (double) pr/nb;
        roc = (double) roc/nb;
        
    }
    
    
}
