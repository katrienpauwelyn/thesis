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
public class TupleDouble {
    
    private double mean;
    private double weightedMean;
    
    public TupleDouble(){
        setMean(0);
        setWeighted(0);
    }
    
    public void setMean(double d){
        mean =d;
    }
    
    public void setWeighted(double d){
        weightedMean = d;
    }
    
    public void incrementMean(double d){
        mean += d;
    }
    
    public void incrementWeighted(double d){
        weightedMean += d;
    }
    
    public double getMean(){
        return (double) Math.round(mean * 100000) / 100000;
    }
    
    public double getWeighted(){
        return (double) Math.round(weightedMean * 100000) / 100000;
    }
    
}
