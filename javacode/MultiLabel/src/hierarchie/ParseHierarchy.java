/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hierarchie;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

/**
 *
 * @author katie
 */
public class ParseHierarchy {
    //tot nu toe staat in de hierarchy iedere leaf node apart
    //er moet iedere ouder-kind relatie alleen komen te staan
    public static void parseHierarchy(String pathIn, String pathOut) throws FileNotFoundException, IOException{
        BufferedReader reader = new BufferedReader(new FileReader(pathIn));
        PrintStream stream = new PrintStream(new File(pathOut));
        String line;
        ArrayList<String> added = new ArrayList();
        while((line=reader.readLine())!=null && !line.isEmpty()){
            String[] split = line.split(",");
            for(int i = 0; i<split.length-1; i++){
                String d = split[i]+","+split[i+1];
                if(!added.contains(d)){
                    stream.println(d);
                    added.add(d);
                }
            }
        }
    }
 
    
    public static void main(String[] args) throws IOException{
        String p = "/Users/katie/Desktop/example-2/hierflags1";
        String p2 = "/Users/katie/Desktop/example-2/hierflags1New";
        parseHierarchy(p, p2);
    }
}
