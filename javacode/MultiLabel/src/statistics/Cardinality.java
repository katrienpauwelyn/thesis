/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statistics;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author katie
 * Berekent de kardinaliteit van een file.
 */
public class Cardinality {
    
    public static double getCardinality(String file1, String file2) throws FileNotFoundException, IOException{
        BufferedReader reader = new BufferedReader(new FileReader(file1));
        String line;
        double out = 0;
        int nbInstances = 0;
        while(!(line=reader.readLine()).contains("@data") && !line.contains("@DATA")){}
        while((line=reader.readLine())!=null && !line.isEmpty()){
            nbInstances++;
            out += line.split("@").length;
        }
        
          reader = new BufferedReader(new FileReader(file2));
           while(!(line=reader.readLine()).contains("@data") && !line.contains("@DATA")){}
        while((line=reader.readLine())!=null && !line.isEmpty()){
            nbInstances++;
            out += line.split("@").length;
        }
        
        
        return (double) out/nbInstances;
    }
    
    
    public static void main(String[] args) throws IOException{
        System.out.println(
        getCardinality("/Users/katie/Desktop/test/todo/rcv1subset1test.arff", "/Users/katie/Desktop/test/todo/rcv1subset1train.arff"));
    }
}
