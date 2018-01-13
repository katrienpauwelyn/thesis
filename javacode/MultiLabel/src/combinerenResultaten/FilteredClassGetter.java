/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package combinerenResultaten;

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
 * Returnt alle klasse namen die minstens 1 maal effectieve waarde 1 hebben
 */
public class FilteredClassGetter {
    
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
    
    public static String getFilteredClasses(String pathFrom) throws IOException{
          ArrayList<Integer> list = getIndicesNeverActual(pathFrom);
        BufferedReader reader = new BufferedReader(new FileReader(pathFrom));
        
        int nbAttributes = 1;
        while(!reader.readLine().contains("@ATTRIBUTE")){}
        while(reader.readLine().contains("@ATTRIBUTE")){nbAttributes++;}
        reader.close();
        
        reader = new BufferedReader(new FileReader(pathFrom));
        String allTheFilteredClasses = "";
        String line;
        while(!(line=reader.readLine()).contains("@ATTRIBUTE")){}
        for(int i = 0; i<nbAttributes/2; i++){
            if(!list.contains(i)){
                allTheFilteredClasses+=getAttributeFromline(line)+",";
            }
            line = reader.readLine();
        }
        return allTheFilteredClasses;
    }
    
    
      
    //@ATTRIBUTE class-a-red
    public static String getAttributeFromline(String line){
        return line.replace("@ATTRIBUTE class-a-", "");
    }
    
    
    public static void printAllTheFilteredClasses() throws IOException{
        PrintStream stream = new PrintStream(new File(Path.pathPinac+"filteredClasses.txt"));
        for(String dataset: Path.datasets){
            String p = Path.pathPinac+dataset+"/averageRHam.test.pred.arff";
            stream.println(dataset);
            stream.println(getFilteredClasses(p));
        }
    }
    
    public static void main(String[] args) throws IOException{
        printAllTheFilteredClasses();
    }
    
}
