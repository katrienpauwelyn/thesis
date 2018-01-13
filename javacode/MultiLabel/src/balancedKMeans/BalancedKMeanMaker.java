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
 * Maakt de Gebalanceerde KMeans hierarchieen
 * 
 * makeAllhierarchies() maakt 1 hierarchie per bag (dus 50 per dataset)
 * makeOneHierarchyForWholeDataset() maakt 1 hierarchie voor de volledige dataset
 */
public class BalancedKMeanMaker {
    
    //maak alle balanced k means hierarchieen (en sla de tijd op)
    //maakt dus 1 hierarchie per bag (50 hierarchieen per dataset)
    public static void makeAllhierarchies() throws IOException{
        PrintStream stream = new PrintStream(new File(Path.pathPinac+"timeHierKMeans.txt"));
        stream.println("tijd om "+Path.nbBags+" balanced k means hierarchieën te maken");
        
        for(String dataset: Path.datasets){
            
            System.out.println(dataset );
            //long startTime = System.nanoTime();
            long totalTime = 0;
            for(int i = 0; i<Path.nbBags; i++){
                System.out.println(i);
                 long startTime = System.nanoTime();
              makeHierarchy(dataset, Path.pathPinac+dataset+"/"+"settings-bag-"+(i+1)+".arff",
                    Path.pathPinac+dataset+"/kmeansHierBag+"+i+".txt", Path.pathPinac+dataset+"/"+dataset+"trainFlat.arff");     
              long endTime = System.nanoTime();
            long durationMs = (endTime - startTime)/1000000;
            totalTime+=durationMs;
             stream.println(dataset+":  tijd"+durationMs+" ms");
            }
           // long endTime = System.nanoTime();
           // long durationMs = (endTime - startTime)/1000000;
            stream.println(dataset+": totale tijd"+totalTime+" ms; gemiddelde tijd per bag: "+totalTime/Path.nbBags+" ms");
        }
        stream.close();
    }
  
    
    /**
     * Maakt en schrijft een hierarchie uit
     * @param dataset de naam van de dataset
     * @param fromFile: pad naar de input file
     * @param outputFile: pad naar de output file
     * @param fileLabels de klassen die in fromFile voorkomen
     * 
     *
     */
    public static void makeHierarchy(String dataset, String fromFile, String outputFile, String fileLabels) throws IOException{
        ArrayList<int[]> labelData = LabelDataReader.getLabelData(dataset, fileLabels);
        Cluster bigCluster = new Cluster(labelData);
        PrintStream stream = new PrintStream(new File(outputFile));
        int nbLabels = labelData.get(0).length;
        for(int i = 0; i<nbLabels; i++){
            bigCluster.labels.add(i);
        }
        int maxNbOfLabels =(int) Math.ceil((double) labelData.get(0).length/(double) Path.getNbClusterCentraNieuweDatasets(dataset));
        ArrayList<Cluster> todoClusters = new ArrayList();
        todoClusters.add(bigCluster);
        
        HashMap<Integer, String> labelMappings = LabelDataReader.getIndicesLabel(fileLabels);
        
       
        while(!todoClusters.isEmpty()){
            Cluster first = todoClusters.remove(0);
            String basicString = indicesToString(first.labels, labelMappings)+",";
            maxNbOfLabels =(int) Math.ceil((double) first.labels.size()/(double) Path.getNbClusterCentraNieuweDatasets(dataset));
      
            ArrayList<Cluster> subClusters = splitCluster(first, 
                Path.getNbClusterCentraNieuweDatasets(dataset), maxNbOfLabels, Path.nbIterations, labelData);
          
            for(Cluster c: subClusters){
                //afdrukken
                                //extra check: geen lege clusters?
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
    
    /**
     * @param indices: de indices die overeen komen met de labels (kolommen) van Wn
     * @param mappings: de mappings tussen de indices en de labels
     * @return  een string die een knoop in een hierarchie voorstelt
     */
    private static String indicesToString(ArrayList<Integer> indices, HashMap<Integer, String> mappings){
         String basicString = "";
            for(int s: indices){
                basicString += mappings.get(s)+"-";
            }
            return basicString.substring(0, basicString.length()-1);
    }

    //het balanced kmeans algoritme. Maakt de eerste initiele cluster aan en split deze recursief
    //op tot een stopconditie bereikt is.
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
              
                while(distances!=null){
                    //label bij de dichtst bijzijnde cluster steken
                    distances = distances.get(0).cluster.addDistance(distances, maxLabels);
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
    
    
    //maak 1 hierarchie per volledige dataset
    public static void makeOneHierarchyForWholeDataset() throws IOException{
        for(String dataset: Path.datasets){
          String fromFile = Path.pathPinac+dataset+"/"+dataset+"train.arff";
          String outputFile = Path.pathPinac+dataset+"/kmeans/hierKMeansFull.txt";
          String fileLabels = Path.pathPinac+dataset+"/"+dataset+"trainFlat.arff";
          System.out.println(dataset);
          makeHierarchy(dataset,  fromFile,  outputFile,  fileLabels);
        }
    }
    
    public static void main(String[] args) throws IOException{

       makeOneHierarchyForWholeDataset();
       
       // makeAllhierarchies();
    }
}
