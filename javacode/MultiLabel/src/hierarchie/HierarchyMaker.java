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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author katie
 */
public class HierarchyMaker {
    public static void makeHierarchy(String pathToTrain, String pathOutput) throws IOException{
        ArrayList<String> classes = getClasses(pathToTrain);
        String root = "";
        for(String cl : classes){
            root = root.concat(cl).concat("-");
       }
        Node node = new Node(root, new ArrayList(classes));//TODO klopt dit om een nieuwe arraylist te maken die lales kopieert?
        ArrayList<Node> nodes = new ArrayList();
        nodes.add(node);
        PrintStream stream = new PrintStream(new File(pathOutput));
        HashMap<String, DistanceKeeper> distances = getDistancesBetweenClasses(pathToTrain, classes);
        makeHierarchyRecursive(new Random(), nodes, classes, stream, distances);
        stream.close();
    }
    
    public static void makeHierarchyRecursive(Random random, ArrayList<Node> hierMap, 
            ArrayList<String> classes, PrintStream stream, HashMap<String, DistanceKeeper> distances){
        if(!hierMap.isEmpty()){
            Node node = hierMap.remove(0);  //de te behandelen knoop
            int nbClassesToDo = node.nbClasses();
            int maximum = Math.min(nbClassesToDo, 5);
            int nbSplitsingen = random.nextInt((maximum - 2) + 1) + 2; //node zal zo veel takken hebben
            ArrayList<Node> newNodes = new ArrayList();
            ArrayList<String> classesNode = node.getClasses();
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
           makeHierarchyRecursive(random, hierMap, classes, stream, distances); 
        }
    }
    
    public static void print(ArrayList<Node> hierMap){
        for(Node n: hierMap){
            System.out.println(n.toString());
        }
    }
    
   //returnt alle klassen uit een file
    public static ArrayList<String> getClasses(String path) throws FileNotFoundException, IOException{
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String line;
        while(!(line = reader.readLine()).contains("@attribute class")){}
        String[] spatie = line.split(" ");
        String[] classes = spatie[spatie.length-1].split(",");
        ArrayList<String> map = new ArrayList();
        map.addAll(Arrays.asList(classes));
        return map;
    }
    
    public static HashMap<String, DistanceKeeper> getDistancesBetweenClasses(String file, 
            ArrayList<String> classes) throws IOException{
        HashMap<String, DistanceKeeper> distances = new HashMap();
        for(String s: classes){
            distances.put(s, getDistancesBetweenClassesSimple(file, classes, s));
        }
        return distances;
    }
    
    //returnt een overzicth van afstanden tussen klassen. De afstanden van klasse A tot BCDEF
    public static DistanceKeeper getDistancesBetweenClassesSimple(String file, ArrayList<String> classes,
            String current) throws FileNotFoundException, IOException{
        DistanceKeeper keeper = new DistanceKeeper(current, classes);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        while(!reader.readLine().contains("@data")){}
        String line;
        while((line = reader.readLine())!=null && !line.isEmpty()){
            String[] parsed = line.split(",");
            String[] cl = parsed[parsed.length-1].split("@");
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
    
    
    
    
    
    
    public static void main(String[] args) throws IOException{
       String path = "/Users/katie/Desktop/demo/voorbeeld/flagstrain.arff";
       String file = "/Users/katie/Desktop/demo/voorbeeld/output.txt";
       makeHierarchy(path, file);
    }

    
}
