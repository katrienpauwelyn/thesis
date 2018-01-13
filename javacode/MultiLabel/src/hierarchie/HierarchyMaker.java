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
 * 
 * Maakt de RHam hierarchieen. 
 */
public class HierarchyMaker {
    
    /**
     * Maakt alle RHam hierarchieën aan (sparse en normal, voor alle bags (ensemble))
     * @param normalMap: naam normal dataset - naam van de klassen 
     * @param sparseMap: naam sparse dataset - naam van de klassen 
     * @throws IOException 
     */
    public static void makeAllHierarchies(HashMap<String, String> normalMap,
            HashMap<String, String> sparseMap) throws IOException{
        String path = Path.pathPinac;
        
        //STANDARD
         PrintStream streamTimeHier = new PrintStream(new File(Path.pathTimeHierStandard));
        for(String dataset: Path.standardDatasets){
             System.out.println(dataset);
               String unparsedClasses = normalMap.get(dataset);
              String[] parsed = unparsedClasses.split(",");
              ArrayList<String> list = new ArrayList();
              for(String s: parsed){
                   list.add(s.replaceAll("/",":").replace("-",":"));
              }
              long totalTime = 0;
              streamTimeHier.println(dataset);
              
            for(int i = 0; i<Path.nbBags; i++){
                System.out.println(i);
                int index = i+1;
                String train = path+dataset+"/settings-bag-"+index+".arff";
                String output = path+dataset+"/"+"temphier"+dataset+i;
                 String finalOutput = path+dataset+"/"+"hier"+dataset+i;
                 long startTime = System.nanoTime();
                makeHierarchy(train, output, false, list);
                ParseHierarchy.parseHierarchy(output, finalOutput);
                 long endTime = System.nanoTime();
                long duration = (endTime - startTime)/1000000; 
                totalTime+=duration;
                streamTimeHier.println("bag "+index+": "+duration+"ms");
            }
            streamTimeHier.println("total time: "+totalTime+"ms");
            streamTimeHier.println("mean time per bag: "+totalTime/Path.nbBags+"ms");
            streamTimeHier.println();
        }
        
        //SPARSE
         streamTimeHier = new PrintStream(new File(Path.pathTimeHierSparse));
           for(String dataset: Path.sparseDatasets){ 
             String unparsedClasses = sparseMap.get(dataset);
              String[] parsed = unparsedClasses.split(",");
              ArrayList<String> list = new ArrayList();
              for(String s: parsed){
                   list.add(s.replaceAll("/", ":").replace("-",":"));
              }
              long totalTime = 0;
              streamTimeHier.println(dataset);
               System.out.println(dataset);
               
            for(int i = 0; i<Path.nbBags; i++){
                System.out.println(i);
               int index = i+1;
               String train = path+dataset+"/settings-bag-"+index+".arff";
                String output = path+dataset+"/"+"temphier"+dataset+i;
                 String finalOutput = path+dataset+"/"+"hier"+dataset+i;
               
                long startTime = System.nanoTime();
                makeHierarchy(train, output, true,list);
                ParseHierarchy.parseHierarchy(output, finalOutput);
                long endTime = System.nanoTime();
                long duration = (endTime - startTime)/1000000; 
                totalTime+=duration;
                streamTimeHier.println("bag "+index+": "+duration+"ms");
            }
            streamTimeHier.println("total time: "+totalTime+"ms");
            streamTimeHier.println("mean time per bag: "+totalTime/Path.nbBags+"ms");
            streamTimeHier.println();
            
        } 
           streamTimeHier.close();
    }
    
    
   
    /**
     * Maakt een hierarchie. Roept recursieve methode op.
     * @param sparse true als dataset sparse is
     * @param classes al de klasse namen
     * @throws IOException 
     */
    public static void makeHierarchy(String pathToTrain, String pathOutput, boolean sparse,
           ArrayList<String> classes) throws IOException{
        String root = "";
        for(String cl : classes){
            root = root.concat(cl).concat("-");
       }
        Node node = new Node(root.substring(0, root.length()-1), new ArrayList(classes));
        ArrayList<Node> nodes = new ArrayList();
        nodes.add(node);
        PrintStream stream = new PrintStream(new File(pathOutput));
        HashMap<String, DistanceKeeper> distances = getDistancesBetweenClasses(pathToTrain, classes, sparse);
        makeHierarchyRecursive(new Random(), nodes, classes, stream, distances, sparse);
        stream.close();
    }
    
    //effectief algoritme, maat de hierarchie (recursief) en print de hierarchie af (het volledige
    //pad van de leaf nodes)
    public static void makeHierarchyRecursive(Random random, ArrayList<Node> nodes, 
            ArrayList<String> classes, PrintStream stream, HashMap<String, DistanceKeeper> distances, 
            boolean sparse){
        if(!nodes.isEmpty()){
            Node node = nodes.remove(0);  //de te behandelen knoop
            int nbClassesToDo = node.nbClasses();
            int maximum = Math.max(2, nbClassesToDo/2);
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
                    nodes.add(n);       //deze met 1 node zijn afgedrukt (leaf nodes), de andere nog verder opsplitsen
                }
            }
           makeHierarchyRecursive(random, nodes, classes, stream, distances, sparse); 
        }
    }
   
    
    public static void print(ArrayList<Node> hierMap){
        for(Node n: hierMap){
            System.out.println(n.toString());
        }
    }
    //returnt een hashmap met per klasse de afstanden naar de andere klassen 
    public static HashMap<String, DistanceKeeper> getDistancesBetweenClasses(String file, 
            ArrayList<String> classes, boolean sparse) throws IOException{
        HashMap<String, DistanceKeeper> distances = new HashMap();
        for(String s: classes){
            distances.put(s, getDistancesBetweenClassesSimple(file, classes, s, sparse));
        }
        return distances;
    }
    
    //returnt een overzicth van afstanden tussen klassen. De afstanden van klasse A tot BCDE en F
    public static DistanceKeeper getDistancesBetweenClassesSimple(String file, ArrayList<String> classes,
            String currentClass, boolean sparse) throws FileNotFoundException, IOException{
        DistanceKeeper keeper = new DistanceKeeper(currentClass, classes);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        while(!reader.readLine().contains("@DATA")){}
        String line;
        while((line = reader.readLine())!=null && !line.isEmpty()){
            if((sparse && !line.endsWith(" None}") && !line.endsWith(" none}")) ||      //als er een toegewezen klasse is
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
                if(arrayContainsElement(currentClass, cl)){ //class is 1, alle klassen die ook in de array zitten, hiervan is de distance 0
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
    
    //returnt de klassen die niet in yes zitten maar wel in classes
    public static ArrayList<String> getOthers(String[] yes, ArrayList<String> classes){
        ArrayList<String> other = new ArrayList(classes);
        for(String s: yes){
            other.remove(s);
        }
        return other;
    }
    
    //return true als element in list zit
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
     * @param newNodes: de nieuwe (kinder)knopen die enkel de startklasse van die knoop bevatten
     * @param oldNode: bevat de oude klassen die nog moeten verdeeld worden
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
            String path = Path.pathPinac;
            BufferedReader readerNormal = new BufferedReader(new FileReader(path+"normalMapZonder.txt"));
            BufferedReader readerSparse = new BufferedReader(new FileReader(path+"sparseMapZonder.txt"));
            String lineN;
            while((lineN = readerNormal.readLine())!=null && !lineN.isEmpty()){
                normalMap.put(lineN, readerNormal.readLine());
            }
            while((lineN=readerSparse.readLine())!=null && !lineN.isEmpty()){
                sparseMap.put(lineN, readerSparse.readLine());
            }
            makeAllHierarchies(normalMap, sparseMap);
    }
   
    
    public static void main(String[] args) throws IOException{
        makeAllHierarchies();
    }
}
