/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Irrelevant;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import statics.Path;

/**
 *
 * @author katie
 * 
 * filtert de klassen er uit die nooit een 1 hebben als effectieve voorspelling
 */
public class PostAverager {
    
    //returnt de indices van de klassen die nooit effectief 1 zijn (getest op emotions)
    public static ArrayList<Integer> getIndicesNeverActual(String pathFrom) throws FileNotFoundException, IOException{
        ArrayList<Integer> list = new ArrayList();
        BufferedReader reader = new BufferedReader(new FileReader(pathFrom));
        
        int nbAttributes = 1;
        while(!reader.readLine().contains("@ATTRIBUTE")){}
        while(reader.readLine().contains("@ATTRIBUTE")){nbAttributes++;}
        for(int i = 0; i<nbAttributes/2; i++){
            list.add(i);
        }
        
        String line;
        while((line=reader.readLine())!=null && !line.isEmpty()){
            if(list.isEmpty()){
                break;
            }
            String[] split = line.split(",");
            ArrayList<Integer> toRemove = new ArrayList();
            for(int index: list){
                if(split[index].equals("1")){
                    toRemove.add(index);
                }
            }
            list.removeAll(toRemove);
        }
         
        reader.close();
        return list;
    }
      
    public static void filterAverage(String pathFrom, String pathTo) throws IOException{
        ArrayList<Integer> list = getIndicesNeverActual(pathFrom);
        BufferedReader reader = new BufferedReader(new FileReader(pathFrom));
        PrintStream stream = new PrintStream(new File(pathTo));
        String line;
        int nbAttributes = 1;
        int current = 0;
        while(!(line=reader.readLine()).contains("@ATTRIBUTE")){
           stream.println(line); 
        }
        if(!list.contains(current)){
            stream.println(line);
            
        }
        current++;
        while((line=reader.readLine()).contains("@ATTRIBUTE")){
            if(!list.contains(current)){
             stream.println(line);   
            }
            nbAttributes++;
            current++;
            
        }
        nbAttributes/=2;
        ArrayList<Integer> augmentedList = new ArrayList(list); //de indexen van de actual values en van de voorspellingen
        for(int i: list){
            augmentedList.add(i+nbAttributes);
        }
        
        
        stream.println(line);
        while((line=reader.readLine())!=null && !line.isEmpty()){
            stream.println(filterLine(line, augmentedList, nbAttributes));
        }
        
        
        reader.close();
        stream.close();
    }
    
    //getest
      private static String filterLine(String line, ArrayList<Integer> list, int nbAttributes) {
          String[] split = line.split(",");
          String out = "";
          for(int i = 0; i<split.length; i++){
              if(!list.contains(i)){
                  out = out+","+split[i];
              }
          }
          
          return out.substring(1);
    }
      
      //dit nog achteraf doen: maakt dat de attribuut namen juist zijn (vroeger waren er te veel Original-p's)
      public static void adaptPredictionAttributes(String input, String output) throws FileNotFoundException, IOException{
          BufferedReader reader = new BufferedReader(new FileReader(input));
          PrintStream stream = new PrintStream(new File(output));
          ArrayList<String> labels = new ArrayList();
          String line;
          while(!(line=reader.readLine()).contains("@ATTRIBUTE")){
              stream.println(line);
          }
          while(line.contains(("@ATTRIBUTE class-a-"))){
              labels.add(line.replace("class-a", "Original-p"));
              stream.println(line);
              line = reader.readLine();
          }
          while(!line.contains("@DATA")){
              line = reader.readLine();
          }
          for(String l: labels){
              stream.println(l);
          }
          stream.println(line);
          while((line=reader.readLine())!=null && !line.isEmpty()){
              stream.println(line);
          }
          
          
          stream.close();
          reader.close();
      }
    
    public static void main(String[] args) throws IOException{
        
         String fileName = "average.test.pred.arff";
         String toFileName = "average2.test.pred.arff";
           
        for(String dataset: Path.postAverageDatasets){
            System.out.println(dataset);
            String path1 = Path.pathPinac+dataset+"/kmeans/";
         //   String path2 = Path.pathPinac+dataset+"/";
             adaptPredictionAttributes(path1+fileName, path1+toFileName);
       //      adaptPredictionAttributes(path2+fileName, path2+toFileName);
           
        }
        
    }

       
}
