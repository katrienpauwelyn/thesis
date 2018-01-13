/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ScriptWriters;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import statics.Path;
import static statics.Path.path;

/**
 *
 * @author katie
 * maakt een script om de bags gegenereerd door Clus uniform te maken:
 * //sed -i "s/firstword//g; s/secondword//g" inputFileName
 *  1) none te vervangen door None 's/none/None/g'
 *  2) de klasse labels weg te doen uit de "@ATTRIBUTE class  
 * uit de invoer bags. 
 * hierarchical "
 * s/@ATTRIBUTE class * hierarchical .* /@ATTRIBUTE class hierarchical/g
 */
public class PostProcessingBag {
    
    public static void makeBashScript(String path) throws FileNotFoundException{
        String s= "sed -i 's/@ATTRIBUTE class-1 * hierarchical.*/@ATTRIBUTE class hierarchical/g;"
                + "s/none/None/g; s/Nonetheless/nonetheless/g' 'settings-bag-";
        PrintStream stream = new PrintStream(new File(path));
        //for(String dataset: Path.datasets){
        String[] d = {"eurlex-sm", "eurlex-dc"};
        for(String dataset: d){
              stream.println("cd "+dataset+"/");
            for(int bag = 1; bag<=50; bag++){
                String newS = s+bag+".arff'";
                stream.println(newS);
            }
            stream.println("cd ..");
        }
              
       // }
    }
    
    public static void makeBibtexScript(String p) throws FileNotFoundException{
        String s= "sed -i 's/@ATTRIBUTE class-1 * hierarchical.*/@ATTRIBUTE class hierarchical/g;" //ook nog class doen
                + "s/Nonequilibrium/nonequilibrium/g' 'settings-bag-";
         PrintStream stream = new PrintStream(new File(p));
        stream.println("cd bibtex/kmeans");
            for(int bag = 1; bag<=50; bag++){
                String newS = s+bag+".arff'";
                stream.println(newS);
            }
            stream.println("cd ..");
    }
    
    public static void main(String[] args) throws FileNotFoundException{
        makeBashScript("/Users/katie/Desktop/test/bash.sh");
       // makeBibtexScript("/Users/katie/Desktop/temp/bashBibtex.sh");
    }
    
}
