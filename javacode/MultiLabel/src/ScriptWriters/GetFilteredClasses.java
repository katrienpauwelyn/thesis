/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ScriptWriters;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import statics.Path;

/**
 *
 * @author katie
 */
public class GetFilteredClasses {
    
    public static void getUnfilteredClasses(String path, PrintStream stream) throws FileNotFoundException, IOException{
        
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;
            String all = "";
            while(!(line=reader.readLine()).contains("class-a")){}
            String[] split = line.split("class-a-");
            all = all + split[1];
            while((line=reader.readLine()).contains("class-a")){
                String[] split2 = line.split("class-a-");
                all = all +","+ split2[1];
            }
            stream.println(all);
        }
    
    
    public static void main(String[] args) throws IOException{
        String corel = "/Users/katie/Downloads/corelaverage.test.pred.arff";
        String delicious = "/Users/katie/Downloads/deliciousaverage.test.pred.arff";
        String medical = "/Users/katie/Downloads/medicalaverage.test.pred.arff";
        
        String to = "/Users/katie/Downloads/a.txt";
        PrintStream stream = new PrintStream(new File(to));
        stream.println("corel");
        getUnfilteredClasses(corel, stream);
        stream.println();
        
        stream.println("delicious");
        getUnfilteredClasses(delicious, stream);
        stream.println();
        
        stream.println("medical");
        getUnfilteredClasses(medical, stream);
        
    }
    
}


