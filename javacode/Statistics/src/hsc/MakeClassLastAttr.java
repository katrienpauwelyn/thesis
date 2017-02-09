/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hsc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import staticData.Path;
import static staticData.Path.path;

/**
 *
 * @author katie
 */
public class MakeClassLastAttr {
    
    
    public static void putAllClassAttrLast() throws FileNotFoundException, IOException{
        for(String classifier: Path.classifiers){
           for(String dataset: Path.datasetsNotFirst){
               String path = Path.path+"/"+classifier+"/"+dataset+"/";
               for(int seed = 0; seed<Path.nbSeeds; seed++){
                   for(int fold = 1; fold<Path.nbFolds+1; fold++){
                       
                       //de originele files verplaatsen zodat de aangepaste files op de originele plaats staan
                       //de oude files worden zo niet verwijderd
                       File originalfile = new File(path+"S"+seed+"fold"+fold+".arff");
                       File newfile = new File(path+"S"+seed+"fold"+fold+"old.arff");
                       if (newfile.exists()){
                           throw new java.io.IOException("file exists");
                       }
                       originalfile.renameTo(newfile);

                       originalfile = new File(path+"S"+seed+"test"+fold+".arff");
                       newfile = new File(path+"S"+seed+"test"+fold+"old.arff");
                       if (newfile.exists()){
                           throw new java.io.IOException("file exists");
                       }
                       originalfile.renameTo(newfile);


                       putClassAttrLast(path+"S"+seed+"fold"+fold+"old.arff", path+"S"+seed+"fold"+fold+".arff");
                       putClassAttrLast(path+"S"+seed+"test"+fold+"old.arff", path+"S"+seed+"test"+fold+".arff");
                   }
               }
           }
        }

}
            
    private static void putClassAttrLast(String filePath, String newFilePath) throws FileNotFoundException, IOException{
           BufferedReader reader =  new BufferedReader(new FileReader(filePath));
           PrintStream writer = new PrintStream(new File(newFilePath));
           String line;
           String classLine = null;
           while((line = reader.readLine()) != null ){
               if(line.contains("@attribute class")){
                   classLine = line;
               } else {
                   if(null!=classLine && line.isEmpty()){
                       writer.println(classLine);
                       classLine = null;
                   }
                   writer.println(line);
               }
           }
           reader.close();
           writer.close();
    }
    
    
    public static void main(String[] args) throws IOException, IOException{
        putAllClassAttrLast();
    }
}
