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
     */
    public static ArrayList<int[]> getLabelData(String dataset) throws IOException{
      //  String fromFile = Path.pathPinac+dataset+"/"+dataset+"trainFlat.arff";//TODO weer uit commentaar zetten
      String fromFile = "/Users/katie/Desktop/temp/emotionsShort.arff";
        int nbInstances = getNbInstances(fromFile);
        BufferedReader reader = new BufferedReader(new FileReader(fromFile));
        String line;
        while(!(line=reader.readLine()).contains("@attribute class hierarchical")){}
        int nbLabels = line.split(",").length;
        if(Path.isSparse(dataset)){
            return getLabelsData(fromFile, nbInstances, nbLabels, true);
        } else {
           return getLabelsData(fromFile, nbInstances, nbLabels, false);
        }
    }
    
    
    //getest standard en sparse
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
        while(!reader.readLine().contains("@data")){}
       
        while((line = reader.readLine())!=null && !line.isEmpty()){
            String[] parsed;
            if(sparse){
                parsed = line.split("}")[0].split(" ");
            } else {
                parsed = line.split(",");
            }
            String[] actualLabels = parsed[parsed.length-1].split("@");//alle labels toegewezen aan deze instance
            int[] instance = new int[nbLabels];
            for(String actual : actualLabels){
                instance[indexLabel.get(actual)] = 1;
            }
            labelData.add(instance);
        }
        return labelData;
    }
     
    private static int getNbInstances(String file) throws FileNotFoundException, IOException{
        BufferedReader reader = new BufferedReader(new FileReader(file));
        while(!reader.readLine().contains("@data")){        }
        int nbInstances = 0;
        while(reader.readLine()!=null && !reader.readLine().isEmpty()){
            nbInstances++;
        }
        return nbInstances;
    }
    
    
    public static void printIntInt(int[][] toPrint){
        for(int x=0; x<toPrint.length; x++){
            String out = "";
            for(int y = 0; y<toPrint[0].length; y++){
                out += toPrint[x][y]+" ";
            }
            System.out.println(out);
        }
    }
}
