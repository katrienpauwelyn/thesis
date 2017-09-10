/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package balancedKMeans;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import statics.Path;

/**
 *
 * @author katie
 */
public class LabelDataReader {
    /**
     * maakt de Wn aan, per lijn alle label data van een instance
     * per instance : per label een 0 als false, 1 als true (actual data)
     */
    public static ArrayList<int[]> getLabelData(String dataset, String fromFile) throws IOException{
        int nbInstances = getNbInstances(fromFile);
        BufferedReader reader = new BufferedReader(new FileReader(fromFile));
        String line;
        while(!(line=reader.readLine()).contains("@attribute class hierarchical")){}
        int nbLabels = line.split(",").length;
        reader.close();
        if(Path.isSparse(dataset)){
            return getLabelsData(fromFile, nbInstances, nbLabels, true);
        } else {
           return getLabelsData(fromFile, nbInstances, nbLabels, false);
        }
    }
    
    //getest standard en sparse
    /**
     * 
     * @param fromFile: de labeldata moet van deze file genomen worden
     * @param nbInstances: het aantal instances dat fromFile heeft
     * @param nbLabels: het aantal labels dat fromFile heeft
     * @param sparse: true: is een sparse dataset. False: is een standard dataset (niet sparse)
     * @return de label data (Wn)
     */
    private static ArrayList<int[]> getLabelsData(String fromFile, int nbInstances, 
            int nbLabels, boolean sparse) throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader(fromFile));
        ArrayList<int[]> labelData = new ArrayList();
        String line;
        while(!(line=reader.readLine()).contains("@attribute class hierarchical")){}
        String[] s = line.split("hierarchical ");
        String[] labels = s[s.length-1].split(",");//alle labels
        HashMap<String, Integer> indexLabel = new HashMap(); //indexen toewijzen aan labels
        int index = 0;
        for(String l: labels){
            indexLabel.put(l, index);
            index++;
        }
        while(!(line=reader.readLine()).contains("@data") && !(line.contains("@DATA"))){}
       
        while((line = reader.readLine())!=null && !line.isEmpty()){
            String[] parsed;
            if(sparse){
                parsed = line.split("}")[0].split(" ");
            } else {
                parsed = line.split(",");
            }
            String[] actualLabels = parsed[parsed.length-1].split("@");//alle labels toegewezen aan deze instance
            if(actualLabels.length!=1 || !actualLabels[0].equals("None")){
                int[] instance = new int[nbLabels];
                for(String actual : actualLabels){
                    instance[indexLabel.get(actual)] = 1;
                 }
                 labelData.add(instance);
            }
            
        }
        reader.close();
        return labelData;
    }
   /*
    returnt hoe veel insances er in de gegeven file zitten.
    */
    private static int getNbInstances(String file) throws FileNotFoundException, IOException{
        BufferedReader reader = new BufferedReader(new FileReader(file));
          String line;
        while(!(line=reader.readLine()).contains("@data") && !(line.contains("@DATA"))){        }
        int nbInstances = 0;
      
        while((line=reader.readLine())!=null && !line.isEmpty()){
            nbInstances++;
        }
        reader.close();
        return nbInstances;
    }
    
    public static void printIntInt(ArrayList<int[]> toPrint){
        for(int x=0; x<toPrint.size(); x++){
            String out = "";
            for(int y = 0; y<toPrint.get(0).length; y++){
                out += toPrint.get(x)[y]+" ";
            }
            System.out.println(out);
        }
    }
   
    /**
     * return de mappings tussen de index van het label en het label zelf
     * vb: @attribute class hierarchical a,b,c
     * => 0 - a; 1 - b; 2 - c
     */
    public static HashMap<Integer, String> getIndicesLabel(String file) throws FileNotFoundException, IOException{
        HashMap<Integer, String> mapping = new HashMap();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while(!(line=reader.readLine()).contains("@attribute class hierarchical")){}
        line = line.replace("@attribute class hierarchical ", "");
        String[] split = line.split(",");
        for(int i = 0; i<split.length; i++){
            mapping.put(i, split[i]);
        }
        reader.close();
        return mapping;
    }
}
