/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Irrelevant;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import statics.Path;

/**
 *
 * @author katie
 */
public class SamePrediction {
    
    //return true if there are two the same predictions
    public static boolean checkPredictionLine(String line){
        String[] split = line.split(",");
        int offset = split.length/2;
        Set<String> set = new HashSet<String>();
        for(int i = offset; i<split.length; i++){
            if(!split[i].equals("0.0")){
                if(set.contains(split[i])){
                System.out.println(line);
                System.out.println(split[i]);
                return true;
            }
            set.add(split[i]);          
            }
            
        }
        return false;
    }
    
    //return true als er twee gelijke predicties in een instance in deze file staan
    public static boolean checkPrediction(String path) throws FileNotFoundException, IOException{
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String line;
        while(!reader.readLine().contains("@DATA")){}
        while((line=reader.readLine())!=null && !line.isEmpty()){
            if(checkPredictionLine(line)){
                return true;
            }
        }
        return false;
    }
    
    
    public static void main(String[] args) throws IOException{
       String path = Path.pathPinac;
        for(String dataset: Path.datasets){
          System.out.println(dataset+" "+checkPrediction(path+dataset+"/average.test.pred.arff"));
      }
    }
            
}
