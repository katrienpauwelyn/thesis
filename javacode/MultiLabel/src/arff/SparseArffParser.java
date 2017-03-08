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
    
    public static void parseArff(String pathToXml, String pathToTest, 
        String newPathToTest, String pathToTrain, String newPathToTrain) throws IOException{
        HashSet<String> classes = getClassNames(pathToXml);
        BufferedReader reader = new BufferedReader(new FileReader(pathToTest));
        PrintStream writer = new PrintStream(new File(newPathToTest));
        HashMap<Integer, String> classMap1 = new HashMap();
        
        int index1 = changeArff(classes, reader, writer, classMap1);
        printInstances(reader, writer, classMap1, index1); 
            
        reader = new BufferedReader(new FileReader(pathToTrain));
        writer = new PrintStream(new File(newPathToTrain));
        HashMap<Integer, String> classMap2 = new HashMap();
        int index2 = changeArff(classes, reader, writer, classMap2);
        printInstances(reader, writer, classMap2, index2); 
           
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
            if(!classString.equals(indexClassPlusEen+" ")){ //TODO geen klasse: lijn wordt niet afgedrukt
                finalString = finalString.concat(classString);
                finalString = finalString.substring(0,finalString.length()-1).concat("}").replace("/", ":");// / weg doen (stelt hierarchisch voor en dat mag niet)
                stream.println(finalString);
            }
       
            
        }
    }
    
    //parset alle sparse datasets
    public static void parseAllSparseArffs() throws IOException{
        String path = Path.path+"/";
        for(String dataset: Path.sparseDatasets){
            parseArff(path+dataset+"/"+dataset+".xml", path+dataset+"/"+dataset+"-test.arff",path+dataset+"/"+dataset+"test.arff",
                    path+dataset+"/"+dataset+"-train.arff", path+dataset+"/"+dataset+"train.arff");
        }
    }
    
    
    public static void main(String[] args) throws IOException{
       parseAllSparseArffs();
    }

    
}
