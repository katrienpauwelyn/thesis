/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arff;

import static arff.ArffParser.changeArff;
import static arff.ArffParser.getClassNames;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import statics.Path;

/**
 * TODO: de indexen beginnen vanaf 0 en zouden vanaf 1 moeten beginnen
 * @author katie
 */
public class SparseArffParser {
    
    public static StringInt parseArff(String pathToXml, String pathToTest, 
        String newPathToTest, String pathToTrain, String newPathToTrain) throws IOException{
        HashSet<String> classes = getClassNames(pathToXml);
        BufferedReader reader = new BufferedReader(new FileReader(pathToTest));
        PrintStream writer = new PrintStream(new File(newPathToTest));
        HashMap<Integer, String> classMap1 = new HashMap();
        StringInt c1 = changeArff(classes, reader, writer, classMap1);
        int index1 = c1.intPart;
        printInstances(reader, writer, classMap1, index1); 
            
        reader = new BufferedReader(new FileReader(pathToTrain));
        writer = new PrintStream(new File(newPathToTrain));
        HashMap<Integer, String> classMap2 = new HashMap();
        int index2 = changeArff(classes, reader, writer, classMap2).intPart;
        printInstances(reader, writer, classMap2, index2); 
        System.out.println(c1.intPart);
     return c1;  
    }
    
    //classMap: index en klasse naam
    //indexClass: de index waarop de nieuwe klasse zal staan
    public static void printInstances(BufferedReader reader, PrintStream stream, HashMap<Integer, 
            String> classMap, int indexClass) throws IOException{
         String line;
        
        while((line = reader.readLine())!=null && !line.isEmpty()){
            line = line.substring(1,line.length()-1);//{} weg
            String[] parsed = line.split(",");
            String finalString = "{";
            int indexClassPlusEen = indexClass+1;
            String classString = indexClassPlusEen+" ";
            for(int i = 0; i<parsed.length; i++){
                String[] tuple = parsed[i].split(" "); //index - waarde
                if(!classMap.containsKey(Integer.parseInt(tuple[0]))){ //geen klasse attribuut
                    int index = Integer.parseInt(tuple[0])+1;
                    finalString = finalString.concat(index+" "+tuple[1]+",");
                } else { //wel klasse attribuut
                    classString = classString.concat(classMap.get(Integer.parseInt(tuple[0]))+"@");
                }
            }
           // if(!classString.equals(indexClassPlusEen+" ")){ //TODO geen klasse: lijn wordt niet afgedrukt
                finalString = finalString.concat(classString);
                finalString = finalString.substring(0,finalString.length()-1).concat("}").replace("/", ":");// / weg doen (stelt hierarchisch voor en dat mag niet)
                stream.println(finalString);
           // }
       
            
        }
    }
    
    //parset alle sparse datasets
    public static HashMap<String, StringInt> parseAllSparseArffs() throws IOException{
        String path = Path.path+"/";
        HashMap<String, StringInt> map = new HashMap();
        for(String dataset: Path.sparseDatasets){
            System.out.println(dataset);
            map.put(dataset, parseArff(path+dataset+"/"+dataset+".xml", path+dataset+"/"+dataset+"-test.arff",path+dataset+"/"+dataset+"test.arff",
                    path+dataset+"/"+dataset+"-train.arff", path+dataset+"/"+dataset+"train.arff"));
        }
        return map;
    }
    
    
    public static void main(String[] args) throws IOException{
       parseAllSparseArffs();
    }

    
}
