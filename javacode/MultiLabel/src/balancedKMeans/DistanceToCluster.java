/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package balancedKMeans;

/**
 *
 * @author katie
 */
public class DistanceToCluster implements Comparable<DistanceToCluster>{
    
    public double distance;
    public Cluster cluster;
    public int label;
    
    public DistanceToCluster(double distance, Cluster c, int label){
        this.distance = distance;
        cluster = c;
        this.label = label;
    }

    //Compares this object with the specified object for order. Returns a negative integer, zero, 
    //or a positive integer as this object is less than, equal to, or greater than the specified object.
    @Override
    public int compareTo(DistanceToCluster to) {
       if(this.distance<to.distance){
           return -1;
       }
       if(this.distance>to.distance){
           return 1;
       }
       return 0;
    }
    
    
    
}
