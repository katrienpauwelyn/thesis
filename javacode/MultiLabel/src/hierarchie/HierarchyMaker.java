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
 *
 * @author katie
 */
public class HierarchyMaker {
    public static void makeAllHierarchies(HashMap<String, String> normalMap,
            HashMap<String, String> sparseMap, HashMap<String, Integer> intMap) throws IOException{
        String path = Path.path;
 /*       for(String dataset: Path.standardDatasets){
    // String dataset = "flags";
               String unparsedClasses = normalMap.get(dataset);
              String[] parsed = unparsedClasses.split(",");
              ArrayList<String> list = new ArrayList();
              for(String s: parsed){
                   list.add(s.replaceAll("/",":"));
              }
            for(int i = 0; i<Path.nbBags; i++){
                String train = path+"/"+dataset+"/"+dataset+"train.arff";
                String output = path+"/"+dataset+"/"+"temphier"+dataset+i;
                 String finalOutput = path+"/"+dataset+"/"+"hier"+dataset+i;
                System.out.println(dataset);
                makeHierarchy(train, output, false, list, intMap.get(dataset));
                ParseHierarchy.parseHierarchy(output, finalOutput);
            }
            
        }*/

           for(String dataset: Path.sparseDatasets){
             String unparsedClasses = sparseMap.get(dataset);
              String[] parsed = unparsedClasses.split(",");
              ArrayList<String> list = new ArrayList();
              for(String s: parsed){
                   list.add(s.replaceAll("/", ":"));
              }
            for(int i = 0; i<Path.nbBags; i++){
                String train = path+"/"+dataset+"/"+dataset+"train.arff";
                String output = path+"/"+dataset+"/"+"temphier"+dataset+i;
                 String finalOutput = path+"/"+dataset+"/"+"hier"+dataset+i;
               
                System.out.println(dataset);
                makeHierarchy(train, output, true,list, intMap.get(dataset));
                ParseHierarchy.parseHierarchy(output, finalOutput);
            }
            
            
        }
        
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
            ArrayList<String> classes, PrintStream stream, HashMap<String, DistanceKeeper> distances, boolean sparse){
        if(!hierMap.isEmpty()){
            Node node = hierMap.remove(0);  //de te behandelen knoop
            int nbClassesToDo = node.nbClasses();
            int maximum = Math.min(nbClassesToDo, 5);
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
    
   //returnt alle klassen uit een file
   /*public static ArrayList<String> getClasses(String path) throws FileNotFoundException, IOException{
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String line;
        while(!(line = reader.readLine()).contains("@attribute class hierarchical")){}
        String[] spatie = line.split(" ");
        String[] classes = spatie[spatie.length-1].split(",");
        ArrayList<String> map = new ArrayList();
        map.addAll(Arrays.asList(classes));
        return map;
    }*/
    
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
        while(!reader.readLine().contains("@data")){}
        String line;
        while((line = reader.readLine())!=null && !line.isEmpty()){
        //    System.out.println(line);
            if((sparse && !line.endsWith(" None}")) || (!sparse && !line.endsWith("None"))){
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
}
