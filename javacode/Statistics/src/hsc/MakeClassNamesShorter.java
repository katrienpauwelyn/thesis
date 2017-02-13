/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hsc;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import staticData.Path;

/**
 *maakt de klasse namen korter van de files die errors geven. Gebruikt het alfabet.
 * @author katie
 * 
 * PAS OP bij het opnieuw runnen (niet de aller eerste keer): de  if (!newfile.exists()){
                           originalfile.renameTo(newfile);
                       }
                       * 
                       * hierin moet de if-test weg anders wordt het niet verplaatst
                       * (enkel als er nieuwe test en train files gemaakt zijn!!!)
                       * bij de eerste keer runnen hoeft dit niet aangepast te worden
 * 
 * makkelijker om hier te doen dan met script
 */
public class MakeClassNamesShorter {
    
    public static void makeScriptAllShorter() throws IOException{
        
        PrintStream stream = new PrintStream(new File(Path.path+"/shorteningScript.sh"));
        stream.println("#!/bin/bash");
        
        for(String classifier: Path.classifiers){
            int index = 0;
          //  stream.println("cd classifier");
           for(String dataset: Path.datasetShortening){
            //   stream.println("cd dataset");
               String path = Path.path+"/"+classifier+"/"+dataset+"/";
               for(int seed = 0; seed<Path.nbSeeds; seed++){
                   for(int fold = 1; fold<Path.nbFolds+1; fold++){
                        File originalfile = new File(path+"S"+seed+"fold"+fold+".arff");
                       File newfile = new File(path+"S"+seed+"fold"+fold+"old.arff");
                       /**
                        * TODO: bij een nieuwe sessie moet deze code aangepast worden. Enkel als er nieuwe train en test 
                        * files gemaakt zijn!!!
                        */
                       if (!newfile.exists()){
                           originalfile.renameTo(newfile);
                       }
                        stream.println(makeSedCommand(index, originalfile.getAbsolutePath(), newfile.getAbsolutePath()));

                       originalfile = new File(path+"S"+seed+"test"+fold+".arff");
                       newfile = new File(path+"S"+seed+"test"+fold+"old.arff");
                       if (!newfile.exists()){
                           originalfile.renameTo(newfile);
                       }
                       
                       stream.println(makeSedCommand(index, originalfile.getAbsolutePath(), newfile.getAbsolutePath()));
                   }
               }
               index++;
           }
        }
        
    }
 
    private static String makeSedCommand(int index, String to, String from){
        String out = "sed '";
        
        for(int i = 0; i<Path.shorteningClasses[index].length; i++){
            out = out.concat(" s/"+Path.shorteningClasses[index][i]+"/"+Path.alfabet[i]+"/g;");
        }
        out = out.substring(0, out.length()-1);
        out = out.concat("' "+from+" > "+to);
        return out;
    }
    
    
    public static void main(String[] args) throws IOException{
        makeScriptAllShorter();
    }
    
}
