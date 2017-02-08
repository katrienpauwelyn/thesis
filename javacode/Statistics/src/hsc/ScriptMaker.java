/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hsc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import staticData.Path;

/**
 *
 * @author katie
 */
public class ScriptMaker {
    
    /**
     * Vooraleer dit script te runnen in de terminal: ga naar de locatie waar dit script staat. (= path)
     * @param path
     * @throws FileNotFoundException 
     */
    
    public static void makeScript(String path) throws FileNotFoundException{
        //run_hsc in iedere dataset map plaatsen
        String run_hsc_path = "/Users/katie/thesisoutput/hsc/run_hsc.pl";
        String perlScript = "run_hsc.pl";
        String name = "/hscScript.sh";
        PrintStream stream = new PrintStream(new File(path+name));
        stream.println("#!/bin/bash");
        String basicString = "perl "+perlScript+" ";//nog s-file aan toevoegen zonder .s
        
       // for(String classifier: Path.classifiers){
       String classifier = "classBalanced";
            stream.println("cd "+classifier);
           
            //for(String dataset: Path.datasets){
            String dataset = Path.datasets[12];//niet: 0,1, (2 & 11(class staat niet laatste?))
            //wel: 3, 4, 5, 6, 7, 8, 9, 10
                stream.println("cd "+dataset+"/asettings");
                stream.println("cp " + run_hsc_path + " " + perlScript);
                for(int seed = 0; seed<Path.nbSeeds; seed++){
                    for(int fold=1; fold<Path.nbFolds+1; fold++){
                        stream.println(basicString+ " S"+seed+"settingsFold"+fold + " || exit 1");
                    }
                }
                stream.println("cd ../..");
            //}
            stream.println("cd ..");
     //   }
        
     stream.close();
    }
    
    
    public static void main(String[] args) throws FileNotFoundException{
        ScriptMaker.makeScript(Path.path);
    }
}
