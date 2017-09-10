/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package balancedKMeans;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import statics.Path;

/**
 *
 * @author katie
 * Hetzelfde als BalancedKMeanMaker maar dan voor 'one' (niet voor ensemble)
 * code is basically the same
 */
public class OneBalancedKMeanMaker {
    
     public static void makeAllhierarchies() throws IOException{
        PrintStream stream = new PrintStream(new File(Path.pathPinac+"timeHierKMeansOne.txt"));
        stream.println("tijd om one balanced k means hierarchie te maken");
        for(String dataset: Path.datasets){
            
            System.out.println(dataset );
            long startTime = System.nanoTime();
            
           
              makeHierarchy(dataset, Path.pathPinac+dataset+"/"+dataset+"train.arff",
                    Path.pathPinac+dataset+"/one/kmeansHierOne.txt", Path.pathPinac+dataset+"/"+dataset+"trainFlat.arff");     
            long endTime = System.nanoTime();
            long durationMs = (endTime - startTime)/1000000;
            stream.println(dataset+": totale tijd"+durationMs+" ms");
           
        }
        stream.close();
    }
    
    public static void makeHierarchy(String dataset, String fromFile, String outputFile, String fileLabels) throws IOException{
        ArrayList<int[]> labelData = LabelDataReader.getLabelData(dataset, fileLabels);
        Cluster bigCluster = new Cluster(labelData);
        PrintStream stream = new PrintStream(new File(outputFile));
        int nbLabels = labelData.get(0).length;
        for(int i = 0; i<nbLabels; i++){
            bigCluster.labels.add(i);
        }
        int maxLabels =(int) Math.ceil((double) labelData.get(0).length/(double) Path.getNbClusterCentra(dataset));
        ArrayList<Cluster> todoClusters = new ArrayList();
        todoClusters.add(bigCluster);
        
        HashMap<Integer, String> labelMappings = LabelDataReader.getIndicesLabel(fileLabels);
        
       
        while(!todoClusters.isEmpty()){
            Cluster first = todoClusters.remove(0);
            String basicString = indicesToString(first.labels, labelMappings)+",";
            maxLabels =(int) Math.ceil((double) first.labels.size()/(double) Path.getNbClusterCentra(dataset));
      
            ArrayList<Cluster> subClusters = splitCluster(first, 
                Path.getNbClusterCentra(dataset), maxLabels, Path.nbIterations, labelData);
            for(Cluster c: subClusters){
                //afdrukken
                                //geen lege clusters?
                if(c.labels.size()==0){
                    System.out.println("size is 0");
                    for(Cluster c2: subClusters){
                        System.out.println("other cluster sizes are: "+c2.labels.size());
                    }
                } else {
                    stream.println(basicString+indicesToString(c.labels, labelMappings));
                }
                

                //cluster groter dan 1 => recursief
                if(c.labels.size()>1){
                    todoClusters.add(c);
                }
                
            }
        }
     
        stream.close();
    }
    
    
    private static String indicesToString(ArrayList<Integer> indices, HashMap<Integer, String> mappings){
         String basicString = "";
            for(int s: indices){
                basicString += mappings.get(s)+"-";
            }
            return basicString.substring(0, basicString.length()-1);
    }

    
    public static ArrayList<Cluster> splitCluster(Cluster cluster, int nbClusters, int maxLabels, 
            int nbIterations, ArrayList<int[]> labelData){
        ArrayList<Cluster> subClusters = new ArrayList();
        if(cluster.labels.size()<=nbClusters){
            for(int label: cluster.labels){
                Cluster toAdd = new Cluster(labelData);
                toAdd.labels.add(label);
                subClusters.add(toAdd);
            }
            return subClusters;
        } 
        
        //initializatie
        RandomInt random = new RandomInt();
        ArrayList<Integer> assignedInit = new ArrayList<>();
       
        while(assignedInit.size()!=nbClusters){
            int newInt = RandomInt.randInt(0, cluster.getNbLabels()-1);
            if(!assignedInit.contains(newInt)){
                subClusters.add(new Cluster(cluster.getDataLabelNr(newInt),labelData ));//initiele subclusters
                assignedInit.add(newInt);
            }
        }
        //een aantal iteraties
        for(int iteration = 0; iteration < nbIterations; iteration++){  
         //   System.out.println("begin iteration "+iteration);
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
        makeAllhierarchies();
    }
}
