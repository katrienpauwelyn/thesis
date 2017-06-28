/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package balancedKMeans;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author katie
 * 
 * cluster heeft een centrum en toegewezen labels
 */
public class Cluster {
    public ArrayList<Integer> labels = new ArrayList();
    public double[] clusterCentrum;
    public ArrayList<int[]> labelData;
    
    //houdt de voorlopige lijst bij van labels en hun afstanden tot alle cluster gemiddelden
    //voor iedere arraylist in distances geldt dat .get(0) het label is dat toegevoegd is 
    //(dit is deze met kleinste afstand)
    public ArrayList<ArrayList<DistanceToCluster>> distances=new ArrayList();

    //enkel te gebruiken bij de initiele cluster (= ganse data)
    public Cluster(ArrayList<int[]> labelD){
        labelData = labelD;
    }
    
    public Cluster(double[] clusterC, ArrayList<int[]> labelData){
        this.labelData = labelData;
        clusterCentrum = clusterC;
    }
    
    public Cluster(int[] clusterC, ArrayList<int[]> labelData){
        clusterCentrum = new double[clusterC.length];
        for(int i = 0; i<clusterC.length; i++){
            clusterCentrum[i] = (double)clusterC[i];
        }
        this.labelData = labelData;
    }
    
    
    public void assignLabels(ArrayList<Integer> la){
        labels = la;
    }
    
    public ArrayList<Integer> resetLabels(){
        labels = null;
        return labels;
    }
    
      public int getNbLabels(){
        return labels.size();
    }
    /**
     *vb: labels is 1,3
     * data: {1,2,3,10}
     *      {4,5,6,11}
     *      {7,8,9,12}
     * getDataLabelNr(0) is dan:{2,5,8}
     */
    public int[] getDataLabelNr(int nr){
        int label = labels.get(nr);
        return getLabelData(label);
    }
    
    public int[] getLabelData(int label){
        int[] out = new int[labelData.size()];
        for(int i = 0; i<labelData.size(); i++){
            out[i] = labelData.get(i)[label];
        }
        return out;
    }
    
     /**
     * Berekent de afstand tussen een clusterCentrum en een label (met index indexLabel)
     */
    public double distance(int indexLabel){
        double sum = 0;
        for(int i = 0; i<clusterCentrum.length; i++){
            sum += Math.pow(clusterCentrum[i]-labelData.get(i)[indexLabel], 2);
        }
        return Math.sqrt(sum);
    }
    
    //voegt een label en zijn afstanden toe
    //als er te veel labels zijn, verwijder de verste en return deze. Als er niet te veel
    //zijn, return null
    public ArrayList<DistanceToCluster> addDistance(ArrayList<DistanceToCluster> list, int maxLabels){
        distances.add(list);
        if(distances.size()>maxLabels){
            System.out.println(distances.size() +" "+maxLabels);
            return removeDistance();
        }
        return null;
    }
    
    //verwijder het label met de grootste afstand tot het centrum van deze cluster
    //zet de afstand van dit label tot deze cluster op oneindig
    //return dit label en zijn afstanden
    public ArrayList<DistanceToCluster> removeDistance(){
        ArrayList<DistanceToCluster> furthestCluster = distances.get(0);
        double furthestDistance = furthestCluster.get(0).distance;
        for(int i = 1; i<distances.size(); i++){
            if(distances.get(i).get(0).distance>furthestDistance){
                furthestDistance = distances.get(i).get(0).distance;
                furthestCluster = distances.get(i);
            }
        }
        furthestCluster.get(0).distance = Double.MAX_VALUE;
        distances.remove(furthestCluster);
        Collections.sort(furthestCluster);
        return furthestCluster;
    }
    
    //herberekent het centrum aan de hand van 'distances'
    public void recalculateCentra(){
        int nbLabels = distances.size();
        for(int instance = 0; instance<clusterCentrum.length; instance++){
            double newValue = 0.0;
            for(ArrayList<DistanceToCluster> distance: distances){
                newValue+=labelData.get(instance)[distance.get(0).label];
            }
            clusterCentrum[instance] = newValue/nbLabels;
        }
    }
    
    //verander de tijdelijke labels (distance) in permanente labels (labels)
    public void makeLabelsFinal(){
        for(ArrayList<DistanceToCluster> list: distances){
            labels.add(list.get(0).label);
        }
    }
    
    public void printCluster(){
        String s = "";
        for(int i: labels){
            s+=i+" ";
        }
        System.out.println(s);
    }
    
    
}
