/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statistics;

import java.util.ArrayList;

/**
 * Klasse om de standaard deviatie en het gemiddelde te berekenen.
 * geeft tot 5 cijfers na komma terug
 * @author katie
 */
public class CollectionMeanStdev {
    private ArrayList<Double> numbers;
    
    public CollectionMeanStdev(){
        numbers = new ArrayList<Double>();
    }
    
    public void addNumber(double d){
        numbers.add(d);
    }
    
    public double getMean(){
        double acc=0;
        for(double d: numbers){
            acc+=d;
        }
        acc/=numbers.size();
        return Math.floor(acc*100000)/100000;
    }
    
    public double getStandardDeviation(){
        double somKwadraat = 0;
        double mean = getMean();
        double som;
        for(double d: numbers){
            som = d - mean;
            somKwadraat+=som*som;
        }
        somKwadraat /= numbers.size();
        return Math.floor(Math.sqrt(somKwadraat)*100000)/100000;
    }
    
}
