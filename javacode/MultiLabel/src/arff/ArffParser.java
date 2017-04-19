package arff;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import statics.Path;

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
         while(!line.contains("</labels>")){
             if(line.contains("name")){
                 String[] parsed = line.split("\"");
             map.add(parsed[1]);
             }
             line = reader.readLine();
         }
         return map;
    }
    
    //verandert de header (roept methode aan die de instances verandert)
    //standard is true als de instances standaard zijn. False als de instances zo zijn: {1,22 2,33 ...}
    public static  StringInt changeArff(HashSet<String> classes,
            BufferedReader reader, PrintStream writer, HashMap<Integer, String> classMap) throws FileNotFoundException, IOException{
        String line;
        
        while(!(line=reader.readLine()).contains("@attribute")){
            writer.println(line);
        }
        int index = 0;
        int nbAttributes = 0;
        String classString = "";
        while(line.contains("@attribute")){
            String[] split = line.split(" ");
            if(classes.contains(split[1])){
                classMap.put(index, split[1]);
               classString= classString.concat(split[1]+",");
            } else {
                nbAttributes++;
                writer.println(line);
            }
            line = reader.readLine();
            index++;
        }
        if(classes.size()!=classMap.size()){
            for(String xml: classes){
                boolean ok = false;
                for(String s: classMap.values() ){
                    if(s.equals(xml)){
                        ok=true; 
                        break;
                    }
                }
                if(!ok){
                    System.out.println("wel in xml, niet in arff "+xml);
                }
            }
           for(String xml: classMap.values()){
                boolean ok = false;
                for(String s: classes ){
                    if(s.equals(xml)){
                        ok=true; 
                        break;
                    }
                }
                if(!ok){
                    System.out.println("wel in arff, niet in xml "+xml);
                }
            }            
            
            
          //  throw new Error("er zijn niet evenveel klassen in de arff file als in de xml file!");
        }
        
        writer.println("@attribute class hierarchical ");//+classString.substring(0, classString.length()-1).replace("/", ":"));
        writer.println(line);
        while(!(line=reader.readLine()).contains("@data")){
            writer.println(line);
        }
        writer.println(line);//@data
       return new StringInt(classString, nbAttributes);
    }
    
    //verandert de isntances
    public static void printInstances(BufferedReader reader,PrintStream stream, HashMap<Integer, String> map) throws IOException{
        String line;
        int geenKlassenToegewezen = 0;
        
        while((line = reader.readLine())!=null && !line.isEmpty()){
            String[] parsed = line.split(",");
              String at = "";
              
            for(int index: map.keySet()){
                String c = map.get(index);
                if(Integer.parseInt(parsed[index])==1)
               {
                    at = at.concat(c+"@").replace("-", ":");
                }
                parsed[index] = "magweg";
                
            }
            String toPrint = "";
            for(String s: parsed){
                if(!s.equals("magweg")){
                    toPrint = toPrint.concat(s+",");
                }
            }
            if(at.length()==0){
                geenKlassenToegewezen++;
                stream.println(toPrint+"None");
            } else {
                toPrint = toPrint.concat(at.substring(0, at.length()-1)).replace("/", ":");
                stream.println(toPrint);
            }
            
        }
        System.out.println("geen klassen toegewezen voor "+geenKlassenToegewezen+" instances"); //TODO
    }
    
    //parset een dataset
    public static StringInt parseArff(String pathToXml, String pathToTest, 
        String newPathToTest, String pathToTrain, String newPathToTrain) throws IOException{
        HashSet<String> classes = getClassNames(pathToXml);
        
        BufferedReader reader = new BufferedReader(new FileReader(pathToTest));
        PrintStream writer = new PrintStream(new File(newPathToTest));
        HashMap<Integer, String> classMap1 = new HashMap<Integer, String>();
        StringInt c1 = changeArff(classes, reader, writer, classMap1);
        int classIndex1 = c1.intPart;
        printInstances(reader, writer, classMap1); 
            
        reader = new BufferedReader(new FileReader(pathToTrain));
        writer = new PrintStream(new File(newPathToTrain));
        HashMap<Integer, String> classMap2 = new HashMap<Integer, String>();
         StringInt c2 = changeArff(classes, reader, writer, classMap2);
        int classIndex2 = c2.intPart;
        printInstances(reader, writer, classMap2); 
        return c1;
    }
    
    //parset alle datasets
    public static void parseAllStandardArffs() throws IOException{
        String path = Path.path+"/";
        HashMap<String, StringInt> map = new HashMap();
        for(String dataset: Path.standardDatasets){
            System.out.println(dataset);
            map.put(dataset, parseArff(path+dataset+"/"+dataset+".xml", path+dataset+"/"+dataset+"-test.arff",path+dataset+"/"+dataset+"test.arff",
                    path+dataset+"/"+dataset+"-train.arff", path+dataset+"/"+dataset+"train.arff"));
        }
        printMap(map, Path.pathStandardMap);
    }
    
        
    public static void printMap(HashMap<String, StringInt> map, String path) throws FileNotFoundException{
        PrintStream stream = new PrintStream(new File(path)); 
        for(String s: map.keySet()){
           stream.println(s);
           StringInt i = map.get(s);
           stream.println(i.intPart);
           stream.println(i.stringPart);
       }
    }
    
        
    public static void main(String[] args) throws IOException{
     // parseAllStandardArffs();
     String pathToXml = "/Users/katie/thesiscode/datasets/multilabel/scene/scene.xml";
     String pathToTest="/Users/katie/thesiscode/datasets/multilabel/scene/scene-test.arff"; 
     String newPathToTest="/Users/katie/thesiscode/datasets/multilabel/scene/scenetest.arff";
     String pathToTrain="/Users/katie/thesiscode/datasets/multilabel/scene/scene-train.arff";
     String newPathToTrain="/Users/katie/thesiscode/datasets/multilabel/scene/scenetrain.arff";
     parseArff(pathToXml, pathToTest, newPathToTest, pathToTrain, newPathToTrain);
       
        
        
      
       // changeArff(path+"/tmc2007-train.arff", path+"/newtmc2007-train.txt",classes);
       // changeArff(path+"/tmc2007-test.arff", path+"/newtmc2007-test.txt",classes);
      // public static String[] nonStandardDatasets = { "bibtex","delicious","","tmc2007"};  
     
    }
    /**
     * enron: spatie en komma als komma
     * bibtex delicious
     * rcv1subset1 iets raars
     */
    
}
