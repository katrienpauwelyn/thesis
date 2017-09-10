/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hierarchie;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import statics.Path;

/**
 * Dezelfde code als Hierarchymaker maar dan voor 'one' ipv de ensembles
 * @author katie
 * maak een hierarchie, volgens RHam voor de volledige dataset
 */
public class OneHierMaker {
    public static void makeAllHierarchies(HashMap<String, String> normalMap,
            HashMap<String, String> sparseMap, HashMap<String, Integer> intMap) throws IOException{
        String path = Path.pathPinac;
         PrintStream streamTimeHier = new PrintStream(new File(path+"timeHierOne.txt"));
        for(String dataset: Path.standardDatasets){
            long totalTime = 0;
            for(int i = 0; i<10; i++){
             System.out.println(dataset);
               String unparsedClasses = normalMap.get(dataset);
              String[] parsed = unparsedClasses.split(",");
              ArrayList<String> list = new ArrayList();
              for(String s: parsed){
                   list.add(s.replaceAll("/",":").replace("-",":"));
              }
              
              streamTimeHier.println(dataset);
              
                String train = path+dataset+"/"+dataset+"train.arff";
                String output = path+dataset+"/one/temphierOne";
                 String finalOutput = path+dataset+"/one/hierOneRHam"+i;
                 long startTime = System.nanoTime();
                makeHierarchy(train, output, false, list, intMap.get(dataset));
                ParseHierarchy.parseHierarchy(output, finalOutput);
                 long endTime = System.nanoTime();
                long duration = (endTime - startTime)/1000000; 
                totalTime+=duration;
            
            }
               streamTimeHier.println("time one hierarchy rHam "+dataset+": "+totalTime/10+"ms");
            streamTimeHier.println(); 
        }
        
          for(String dataset: Path.sparseDatasets){ 
               long totalTime = 0;
              for(int i =0; i<10; i++){
                   String unparsedClasses = sparseMap.get(dataset);
              String[] parsed = unparsedClasses.split(",");
              ArrayList<String> list = new ArrayList();
               
              for(String s: parsed){
                
                   list.add(s.replaceAll("/", ":").replace("-",":"));
              }
             
              streamTimeHier.println(dataset);
               System.out.println(dataset);
               
               String train = path+dataset+"/"+dataset+"train.arff";
                String output = path+dataset+"/one/temphierOne";
                 String finalOutput = path+dataset+"/one/hierOneRHam"+i;
               
                long startTime = System.nanoTime();
                makeHierarchy(train, output, true,list, intMap.get(dataset));
                ParseHierarchy.parseHierarchy(output, finalOutput);
                long endTime = System.nanoTime();
                long duration = (endTime - startTime)/1000000; 
                totalTime+=duration;
                  
              }
            
             streamTimeHier.println("time one hierarchy rHam "+dataset+": "+totalTime/10+"ms");
            streamTimeHier.println();
        } 
           streamTimeHier.close();
    }
    
    
    
    public static void makeHierarchy(String pathToTrain, String pathOutput, boolean sparse,
           ArrayList<String> classes, int indexClass) throws IOException{
        String root = "";
        for(String cl : classes){
            root = root.concat(cl).concat("-");
       }
        Node node = new Node(root.substring(0, root.length()-1), new ArrayList(classes));
        ArrayList<Node> nodes = new ArrayList();
        nodes.add(node);
        PrintStream stream = new PrintStream(new File(pathOutput));
        HashMap<String, DistanceKeeper> distances = getDistancesBetweenClasses(pathToTrain, classes, sparse, indexClass);
       
        
        makeHierarchyRecursive(new Random(), nodes, classes, stream, distances, sparse);
        stream.close();
    }
    
    public static void makeHierarchyRecursive(Random random, ArrayList<Node> hierMap, 
            ArrayList<String> classes, PrintStream stream, HashMap<String, DistanceKeeper> distances, 
            boolean sparse){
        if(!hierMap.isEmpty()){
            Node node = hierMap.remove(0);  //de te behandelen knoop
            int nbClassesToDo = node.nbClasses();
            int maximum = Math.max(2, nbClassesToDo/2);
            //int maximum = Math.min(nbClassesToDo, 5);
            int nbSplitsingen = random.nextInt((maximum - 2) + 1) + 2; //node zal zo veel takken hebben
            ArrayList<Node> newNodes = new ArrayList();
            for(int splits=0; splits<nbSplitsingen; splits++){
                int indClass = random.nextInt(nbClassesToDo);
                String className = node.getAndRemoveClass(indClass);    //basis voor een tak
                ArrayList<String> list = new ArrayList();
                list.add(className);
                newNodes.add(new Node(node.getHierarchy(), list));
                nbClassesToDo--;
            }
            divideAndPrintOtherClasses(newNodes, node, distances, stream);
               for(Node n: newNodes){
                   if(n.nbClasses()!=1){
                       hierMap.add(n);
                   }
               }
           makeHierarchyRecursive(random, hierMap, classes, stream, distances, sparse); 
        }
    }
    
    public static void print(ArrayList<Node> hierMap){
        for(Node n: hierMap){
            System.out.println(n.toString());
        }
    }
    
    public static HashMap<String, DistanceKeeper> getDistancesBetweenClasses(String file, 
            ArrayList<String> classes, boolean sparse, int indexClass) throws IOException{
        HashMap<String, DistanceKeeper> distances = new HashMap();
        for(String s: classes){
            distances.put(s, getDistancesBetweenClassesSimple(file, classes, s, sparse, indexClass));
        }
        return distances;
    }
    
    //returnt een overzicth van afstanden tussen klassen. De afstanden van klasse A tot BCDE en F
    public static DistanceKeeper getDistancesBetweenClassesSimple(String file, ArrayList<String> classes,
            String current, boolean sparse, int indexClass) throws FileNotFoundException, IOException{
       // System.out.println(indexClass);
        DistanceKeeper keeper = new DistanceKeeper(current, classes);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();
        while(!line.contains("@DATA")&&!line.contains("@data")){line = reader.readLine();}
        while((line = reader.readLine())!=null && !line.isEmpty()){
            
             if((sparse && !line.endsWith(" None}") && !line.endsWith(" none}")) ||
                     (!sparse && !line.endsWith("None") && !line.endsWith("none"))){
                      String[] cl;
            if(!sparse){
                String[] parsed = line.split(",");
                cl = parsed[parsed.length-1].split("@");
            } else {
                String[] parsed = line.split(" ");
                String s = parsed[parsed.length-1];
                s = s.substring(0, s.length()-1);
                cl=s.split("@");
              }
            if(arrayContainsElement(current, cl)){ //class is 1, alle klassen die ook in de array zitten, hiervan is de distance 0
                ArrayList<String> others = getOthers(cl, classes);
                for(String not: others){
                    keeper.incrementClass(not);
                }
            } else {
                for(String not: cl){//class is 0, alle klassen uit de array is distance 1
                    keeper.incrementClass(not);
                }
            }
            }
        }
        reader.close();
        return keeper;
    }
    
    
    public static ArrayList<String> getOthers(String[] yes, ArrayList<String> classes){
        ArrayList<String> other = new ArrayList(classes);
        for(String s: yes){
            other.remove(s);
        }
        return other;
    }
    
    
    public static boolean arrayContainsElement(String element, String[] list){
        for(String s: list){
            if(element.equals(s)){
                return true;
            }
        }
        return false;
    }
    
    
    /**
     * 
     * @param newNodes: de knopen waar er enkel de startklasse in zit
     * @param oldNode: de oude klassen die nog moeten verdeeld worden
     * @param distances: de afstanden tussen de verschillende klassen
     * als er maar 1 klasse meer in een node zit, wordt de hierarchy afgedrukt
     */
    private static void divideAndPrintOtherClasses(ArrayList<Node> newNodes, Node oldNode, HashMap<String, 
            DistanceKeeper> distances, PrintStream stream) {
        for(String old: oldNode.getClasses()){ //voor iedere oude knoop kijken waar ie het dichtst bij zit
            DistanceKeeper keeper = distances.get(old);
            Node closest = keeper.closestNode(newNodes);
            closest.addClass(old);
        }
        for(Node n: newNodes){
            n.updateAndPrintHierarchy(stream);
        }
    }
    
    
    public static void makeAllHierarchies() throws FileNotFoundException, IOException{
            HashMap<String, String> normalMap = new HashMap();
            HashMap<String, String> sparseMap = new HashMap();
            HashMap<String, Integer> intMap = new HashMap();
            String path = Path.pathPinac;
            BufferedReader readerNormal = new BufferedReader(new FileReader(Path.pathStandardMap));
            BufferedReader readerSparse = new BufferedReader(new FileReader(Path.pathSparseMap));
            String lineN;
            while((lineN = readerNormal.readLine())!=null && !lineN.isEmpty()){
                intMap.put(lineN, Integer.parseInt(readerNormal.readLine())+1);
                normalMap.put(lineN, readerNormal.readLine());
            }
            while((lineN=readerSparse.readLine())!=null && !lineN.isEmpty()){
                intMap.put(lineN, Integer.parseInt(readerSparse.readLine())+1);
                sparseMap.put(lineN, readerSparse.readLine());
            }
            makeAllHierarchies(normalMap, sparseMap, intMap);
            
           /* for(String s: standard.keySet()){
            
                normalMap.put(s, standard.get(s).stringPart);
                intMap.put(s, standard.get(s).intPart+1);
            }
            
            for(String s: sparse.keySet()){
                sparseMap.put(s, sparse.get(s).stringPart);
                 intMap.put(s, sparse.get(s).intPart+1);
            }*/
    }
    
    public static void main(String[] args) throws IOException{
        makeAllHierarchies();
    }
}
