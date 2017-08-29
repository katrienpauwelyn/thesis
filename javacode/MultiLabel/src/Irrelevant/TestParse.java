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

/**
 *
 * @author katie
 */
public class TestParse {
    
    public static void split(String from, String to) throws FileNotFoundException, IOException{
        BufferedReader reader = new BufferedReader(new FileReader(from));
        PrintStream stream = new PrintStream(new File(to));
        String line;
        while(!(line=reader.readLine()).contains("@DATA")){
            stream.println(line);
        }
        while((line=reader.readLine())!=null && !line.isEmpty()){
            String[] parsed = line.split(",");
            String toprint = parsed[1];
            for(int i = 2; i<parsed.length-1; i++){
                toprint += ","+parsed[i];
            }
            stream.println(toprint);
                
        }
        
        reader.close();
        stream.close();
    }
    
    public static void main(String[] args) throws IOException{
        String to = "/Users/katie/Desktop/temp/test.txt";
        String from = "/Users/katie/Desktop/temp/settings2.test.pred.arff";
        split(from, to);
    }
    
}
