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
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import statics.Path;

/**
 *
 * @author katie
 */
public class HierarchyMaker {
    
    
    public static void makeHierarchy(String pathTest, String pathTrain, boolean key,
            String fileOut) throws FileNotFoundException, IOException{
        //TODO check of dat er evenveel klassen in de test en train file zitten
        
        
       ArrayList<String> classes = getClasses(pathTrain);
       ArrayList<Node> hierMap = new ArrayList();
       String root = "";
       for(String cl : classes){
            root = root.concat(cl).concat("-");
       }
       Node n = new Node(root.substring(0, root.length()-1), classes);
       hierMap.add(n);
       HashSet<String> hierDone = new HashSet();//de hierarchieen die tot in een knoop lopen
       Random rand = new Random();
        int nbAttributes = getNbAttributes(pathTest);
        HashMap<String, Double>[] types = getTypes(pathTrain, nbAttributes, key);
        
        HashMap<String, String[]> classesAndTheirMean = new HashMap();
        for(String c: n.getClasses()){
            classesAndTheirMean.put(c, getMeanInstance(c, pathTrain, nbAttributes, types, key));
        }
        PrintStream stream = new PrintStream(new File(fileOut));
        makeHierarchyRecursive(hierMap, hierDone, rand, pathTrain, nbAttributes, types, key, classesAndTheirMean, stream);
        stream.close();
        
        
        //de klassen en instances veranderen met de nieuwe hierarchie (test en train)
    }
    
    
    public static void makeHierarchyRecursive(ArrayList<Node> hierMap, HashSet<String> hierDone, Random random, String filePath,
            int nbAttributes, HashMap<String, Double>[] types, boolean key, HashMap<String, String[]> classesAndTheirMean,
            PrintStream stream) throws FileNotFoundException, IOException{
        if(!hierMap.isEmpty()){//stopconditie
            Node node = hierMap.remove(0);  //de te behandelen knoop
            int nbClassesToDo = node.nbClasses();
            int maximum = Math.min(nbClassesToDo, 5);
            int nbSplitsingen = random.nextInt((maximum - 2) + 1) + 2; //node zal zo veel takken hebben

            HashMap<Node, String[]> nodesMean = new HashMap();//nieuwe node - classmean van de basis
            
            for(int splits=0; splits<nbSplitsingen; splits++){
                int indClass = random.nextInt(nbClassesToDo);
                String className = node.getAndRemoveClass(indClass);    //basis voor een tak
                ArrayList<String> list = new ArrayList();
                list.add(className);
                nodesMean.put(new Node(node.getHierarchy(), list), classesAndTheirMean.get(className));
                nbClassesToDo--;
            }
            
            for(String cl: node.getClasses()){
                checkAndAddClosest(nodesMean, cl, classesAndTheirMean.get(cl), types);
                //kijk waar het dichtst bij en voeg toe. Node is leeg
            }
            
            for(Node n: nodesMean.keySet()){
                String cla = "/";
                   for(String s: n.getClasses()){
                       cla = cla.concat(s+"-");
                   }
                   cla = cla.substring(0, cla.length()-1);
               if(n.nbClasses()==1){
                   stream.println(n.getHierarchy()+cla);
               } else {
                   n.appendToHier(cla);
                   hierMap.add(n);
                   //TODO
                   //hierarchie aanpassen
                   //toevoegen aan hiermap 
                   //zz oproepen
               }
            }
            makeHierarchyRecursive(hierMap, hierDone, random, filePath, nbAttributes, types, key, classesAndTheirMean, stream);
            //vul de hierarchieen aan abc/ab/a
            //indien er maar een klasse meer in de node zit, voeg deze toe aan hierDone
            //indien er meerdere zitten, voeg de nodes toe aan hierMap en roep recursief op
        }
    }

    private static ArrayList<String> getClasses(String pathTest) throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader(pathTest));
        String line;
        while(!(line=reader.readLine()).contains("class")){}
        String[] spatie = line.split(" ");
        String[] komma = spatie[spatie.length-1].split(",");
        ArrayList<String> arrayList = new ArrayList<>(); 
        Collections.addAll(arrayList, komma); 
        reader.close();
       return arrayList;
    }
    
    //controleert of een instance tot de klasse behoort
    public static boolean hasLabel(String line, String className){
        return line.contains(className);
    }
    
    //returnt het aantal attributen er in een file zijn
    public static int getNbAttributes(String filePath) throws FileNotFoundException, IOException{
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        while(!reader.readLine().contains("@data")){}
        String line = reader.readLine();
        String[] parsed = line.split(",");
        return parsed.length-1;
    }

    //returnt de gemiddelde instance die tot een klasse behoort
    private static String[] getMeanInstance(String className, String filePath, int nbAttributes,
            HashMap<String, Double>[] attrTypes, boolean key) throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        while(!reader.readLine().contains("@data")){}
        String line;
        int plus =0;
        if(key){
            plus++;
        }
        int nbInstances = 0;
        while((line=reader.readLine())!=null && !line.isEmpty()){
            if(hasLabel(line, className)){
                nbInstances++;
                String[] parsed = line.split(",");
                for(int i = 0; i<nbAttributes; i++){
                    increment(parsed[i+plus], attrTypes[i]);
                }
            }
        }
        return getMeanOfMap(attrTypes, nbInstances);
    }
    
    /**
     * 
     * @param attr
     * @param nbInstances: hoe veel instances er tot de klasse behoren die we nu behandelen
     * @return een gemiddelde instance die tot deze klasse behoort
     * GETEST
     */
    public static String[] getMeanOfMap(HashMap<String, Double>[] attr, int nbInstances){
        String[] mean = new String[attr.length];
        for(int i = 0; i<attr.length; i++){
            HashMap<String, Double> map = attr[i];
            if(map.size()==1){
                mean[i] = String.valueOf(map.get("-1")/nbInstances);
            } else {
                String highestKey = null;
                double highest = 0;
                for(String k: map.keySet()){
                    if(highestKey==null){
                        highestKey = k;
                        highest = map.get(k);
                    } else {
                        if(highest<map.get(k)){
                            highestKey = k;
                            highest = map.get(k);
                        }
                    }
                    
                }
                mean[i] = highestKey;
            }
        }
        return mean;
    }
    
    /**
     * voeg de waarde "key" toe bij de hashmap. Als het een numerieke waarde is, wordt deze
     * opgeteld bij de reeds bestaande som. Als het geen numerieke waarde is, wordt
     * de counter met 1 opgehoogd bij deze klasse.
     * @param key
     * @param map 
     */
    public static void increment(String key, HashMap<String, Double> map){
        if(map.size()==1){
            double d = map.remove("-1");
            map.put("-1", d+Double.parseDouble(key));
            
        } else {
            double d = map.remove(key);
            map.put(key, d+1);
        }
        
    }
    
    /**
     * 
     * @param path
     * @param nbAttributes: het aantal attributen
     * @param key: als er een key is: true. Het eerste attribuut wordt dan niet bekeken
     * @return voor iedere attribuut een hashamp. Als het numeric is, is de grootte van de hashmap 1 (met tuple <-1, 0.0>). 
     * Als het niet numeric is, is de grootte meer dan 1. <String, Double>: de waarde die het attribuut kan aannemen en een counter
     * (of de som als het numeriek is) Double is overal geinitialiseerd op 0.0
     * @throws FileNotFoundException
     * @throws IOException 
     * GETEST
     */
    public static HashMap<String, Double>[] getTypes(String path, int nbAttributes, boolean key) throws FileNotFoundException, IOException{
        HashMap<String, Double>[] out = new HashMap[nbAttributes];
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String line;
        while(!(line=reader.readLine()).contains("@attribute")){}
        if(key){
            line = reader.readLine();
        }
        for(int i = 0; i<nbAttributes; i++){
            if(!line.contains("@attribute")){
                throw new Error("er is een attribuut te weinig in "+path);
            }
            if(line.contains("numeric")){
                HashMap<String, Double> map = new HashMap();
                map.put("-1", 0.0);
                out[i]=map;
            } else {
                String[] parsedSpatie = line.split(" ");
                String[] parsedKomma = parsedSpatie[2].substring(1, parsedSpatie[2].length()-1).replace(" ","").split(",");
                HashMap<String, Double> array = new HashMap();
                for(String s: parsedKomma){
                    array.put(s, 0.0);
                }
                out[i]=array;
            }
            line = reader.readLine();
        }
      if(reader.readLine().contains("@attribute")){
          throw new Error("er is een fout met het aantal attributen in "+path);
      }
      return out;  
    }
        
    
    public static void main(String[] args) throws IOException{
     /*   String p="/Users/katie/thesiscode/datasets/multilabel/flags/flagstest.arff";
       HashMap<String, Double>[] list =  getTypes(p, 18, true);
       for(HashMap s: list){
           if(s==null){
               System.out.println("null");
           } else {
               System.out.println(s.toString());
           }
           
       }
       
       
       HashMap<String, Double>[] attr = new HashMap[3];
       HashMap<String, Double> map1 = new HashMap();
       map1.put("-1", 5.0);
       HashMap<String, Double> map2 = new HashMap();
       map2.put("a", 5.0);
       map2.put("b", 3.0);
       map2.put("meeste", 9.0);
       
       HashMap<String, Double> map3 = new HashMap();
       map3.put("10", 7.0);
       map3.put("11", 5.0);
       map3.put("12", 5.0);
       map3.put("13", 6.0);
       map3.put("14", 5.0);
       attr[0] = map1;
       attr[1] = map2;
       attr[2] = map3;
       for(int i = 0; i<attr.length; i++){
           System.out.println(getMeanOfMap(attr, 3)[i]);
       }
       System.out.println(getMeanOfMap(attr, 3).toString());*/
     String p = Path.path+"/flags/"+"flagstest.arff";
     String fileOut = Path.path+"/flags/output.txt";
     makeHierarchy(p, p, false, fileOut);
       
     }

    /**
     * Kijk bij welke node cl het dichtst ligt. Voeg cl toe in deze node.
     * @param nodesMean
     * @param cl
     * @param meanOfCl 
     * TODO numerieke testen
     */
    private static void checkAndAddClosest(HashMap<Node, String[]> nodesMean, String cl, 
            String[] meanOfCl, HashMap<String, Double>[] types) {
        
        int minimumHamming = Integer.MAX_VALUE;
        Node minimumNode=null;
        for(Node n: nodesMean.keySet()){
            int hamming = 0;
            for(int i = 0; i<types.length; i++){
             if(types.length!=1){
                if(nodesMean.get(n)[i]!=meanOfCl[i]){
                    hamming++;
                }
             }
            }
            if(hamming<minimumHamming){
                minimumHamming=hamming;
                minimumNode = n;
            }
        }
        
       minimumNode.addClass(cl);
    }

    private static void printHierarchies(HashSet<String> hierDone, String fileOut) throws FileNotFoundException {
        PrintStream stream = new PrintStream(new File(fileOut));
        for(String s: hierDone){
            stream.print(s);
        }
        stream.close();
    }

}
