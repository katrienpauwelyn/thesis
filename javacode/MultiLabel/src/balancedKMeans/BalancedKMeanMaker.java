/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package balancedKMeans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import statics.Path;

/**
 *
 * @author katie
 */
public class BalancedKMeanMaker {
    
    public static void doDataset(String dataset) throws IOException{
        ArrayList<int[]> labelData = LabelDataReader.getLabelData(dataset);
        Cluster bigCluster = new Cluster(labelData);
        int nbLabels = labelData.get(0).length;
        for(int i = 0; i<nbLabels; i++){
            bigCluster.labels.add(i);
        }
        int maxLabels = labelData.get(0).length/Path.getNbClusterCentra(dataset);
        ArrayList<Cluster> subClusters = splitCluster(bigCluster, 
                Path.getNbClusterCentra(dataset), maxLabels, Path.nbIterations, labelData);
        System.out.println("end");
        for(Cluster c: subClusters){
            c.printCluster();
        }
    }
    

    
    public static ArrayList<Cluster> splitCluster(Cluster cluster, int nbClusters, int maxLabels, 
            int nbIterations, ArrayList<int[]> labelData){
        //initializatie
        RandomInt random = new RandomInt();
        ArrayList<Integer> assignedInit = new ArrayList<>();
        ArrayList<Cluster> subClusters = new ArrayList();
        while(assignedInit.size()!=nbClusters){//HIER
            int newInt = RandomInt.randInt(0, cluster.getNbLabels()-1);
            if(!assignedInit.contains(newInt)){
                System.out.println("initialisatie "+newInt);
                subClusters.add(new Cluster(cluster.getDataLabelNr(newInt),labelData ));//initiele subclusters
                assignedInit.add(newInt);
            }
        }
        //een aantal iteraties
        for(int iteration = 0; iteration < nbIterations; iteration++){  
            System.out.println("begin iteration "+iteration);
            for(Cluster sub: subClusters){
                sub.distances.clear();
            }
            for(int label: cluster.labels){
                //afstanden berekenen voor een label
                ArrayList<DistanceToCluster> distances = new ArrayList();
                for(int nbCluster = 0; nbCluster<nbClusters; nbCluster++){
                    distances.add(new DistanceToCluster(subClusters.get(nbCluster).distance(label), 
                            subClusters.get(nbCluster), label));
                }
                Collections.sort(distances);
                boolean finished = false;

                while(!finished){
                    //label bij de dichtst bijzijnde cluster steken
                    distances = distances.get(0).cluster.addDistance(distances, maxLabels);
                    if(distances==null){
                        finished=true;
                    }
                }
            }
            //herbereken de centra    //resetten op true, dan && doen, als er 1 false is gaat het false zijn
            boolean allsame = true;
            for(Cluster c: subClusters){
                allsame = allsame && c.recalculateCentra();
            }
            if(allsame){//stopconditie
                iteration = nbIterations;
            }
        }
        for(Cluster sub: subClusters){
            sub.makeLabelsFinal();
        }
        
        return subClusters;
    }
    
    
    public static void main(String[] args) throws IOException{
        doDataset("emotions");
    }
}
