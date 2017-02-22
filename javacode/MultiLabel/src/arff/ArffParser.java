/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arff;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author katie
 * 
 *     
       
 */
public class ArffParser {
    
    //returnt een hashset met als string values de namen van de klassen 
    public static HashSet<String> getClassNames(String pathToXml) throws FileNotFoundException, IOException{
         BufferedReader reader =  new BufferedReader(new FileReader(pathToXml));
         String line;
         while(!(line = reader.readLine()).contains("name")){}
         HashSet<String> map = new HashSet<>();
         while(line.contains("name")){
             String[] parsed = line.split("\"");
             map.add(parsed[1]);
             line = reader.readLine();
         }
         return map;
    }
    
    //verandert de header (roept methode aan die de instances verandert)
    public static void changeArff(String fromPath, String toPath, HashSet<String> classes) throws FileNotFoundException, IOException{
        BufferedReader reader = new BufferedReader(new FileReader(fromPath));
        PrintStream writer = new PrintStream(new File(toPath));
        String line;
        HashMap<Integer, String>  classMap = new HashMap<Integer, String>();
        
        while(!(line=reader.readLine()).contains("@attribute")){
            writer.println(line);
        }
        int index = 0;
        String classString = "";
        while(line.contains("@attribute")){
            String[] split = line.split(" ");
            if(classes.contains(split[1])){
                classMap.put(index, split[1]);
               classString= classString.concat(split[1]+",");
            } else {
                writer.println(line);
            }
            line = reader.readLine();
            index++;
        }
        if(classes.size()!=classMap.size()){
            throw new Error("er zijn niet evenveel klassen in de arff file als in de xml file!");
        }
        
        writer.println("@attribute class hierarchical "+classString.substring(0, classString.length()-1));
        writer.println(line);
        while(!(line=reader.readLine()).contains("@data")){
            writer.println(line);
        }
        writer.println(line);//@data
        
        printInstances(reader, writer, classMap);
    }
    
    //verandert de isntances
    public static void printInstances(BufferedReader reader,PrintStream stream, HashMap<Integer, String> map) throws IOException{
        String line;
        
        while((line = reader.readLine())!=null && !line.isEmpty()){
            String[] parsed = line.split(",");
              String at = "";
            for(int index: map.keySet()){
                String c = map.get(index);
                if(Integer.parseInt(parsed[index])==1){
                    at = at.concat(c+"@");
                }
                parsed[index] = "magweg";
                
            }
            String toPrint = "";
            for(String s: parsed){
                if(!s.equals("magweg")){
                    toPrint = toPrint.concat(s+",");
                }
            }
            stream.println(toPrint.concat(at.substring(0, at.length()-1)));
        }
    }
    
    
    public static void main(String[] args) throws IOException{
        String path = "/Users/katie/Desktop/flags";
        HashSet<String> classes = getClassNames(path+"/flags.xml");
        
        changeArff(path+"/flags.arff", path+"/newflags.txt",classes);
        changeArff(path+"/flags-train.arff", path+"/newflags-train.txt",classes);
        changeArff(path+"/flags-test.arff", path+"/newflags-test.txt",classes);
    }
    
}
