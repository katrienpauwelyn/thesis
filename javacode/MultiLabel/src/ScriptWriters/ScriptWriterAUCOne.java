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
 * maakt een script om de AUCROC en AUCPR te berekenen (vertrekkende van micro en macro average)
 */
public class ScriptWriterAUCOne {
    
    //op pathFilteredMao staat er een file waarin de naam van alle datasets staat met alle 
    //bijhorende labels. De labels die er uit gefilterd zijn (degene die nooit een effectieve 1 hebben
    //in de test set, staan er niet bij)
    public static void makeScript() throws FileNotFoundException, IOException{
        PrintStream stream = new PrintStream(new File(Path.pathToAUScript));
        //java -jar auc.jar testsetlist.txt list
        BufferedReader readerSp = new BufferedReader(new FileReader(Path.pathFilteredAll));
       // BufferedReader readerSt = new BufferedReader(new FileReader(Path.pathStandardMap));
        makePartScript(stream, readerSp);
       // makePartScript(stream, readerSt);
        readerSp.close();
       // readerSt.close();
        stream.close();
    }
    
    public static void makePartScript(PrintStream stream, BufferedReader reader) throws IOException{
        String line;
        String path;
        
        
        while((line=reader.readLine())!=null && !line.isEmpty()){
                    //MICRO
            String allFlat = "micro"+line+"FlatOne.txt";
           
             path = Path.pathPinac.concat(line).concat("/one/micromacro/");
            String pathOutputFlat = path.concat("AUCmicro"+line+"FlatOne.txt");
          //  
             
           // stream.println("echo \""+line+" micro\"");
            stream.println("echo \""+line+" micro Flat\"");
            
            stream.println("java -jar auc.jar "+path+allFlat+" list > "+pathOutputFlat);
           
           // stream.println("echo \""+line+" micro RHam\"");
            for(int i = 0; i<10; i++){
                
            stream.println("echo \""+line+" micro KMeans\"");
            String pathOutputKMeans = path.concat("AUCmicro"+line+"KMeansOne"+i+".txt");
            String allKMeans = "micro"+line+"KMeansOne"+i+".txt";
            stream.println("java -jar auc.jar "+path+allKMeans+" list > "+pathOutputKMeans);
                
                
                stream.println("echo \""+line+" micro RHam\"");
               
                 String pathOutputRHam = path.concat("AUCmicro"+line+"RHamOne"+i+".txt");
                 String allRHam = "micro"+line+"RHamOne"+i+".txt";
                 stream.println("java -jar auc.jar "+path+allRHam+" list > "+pathOutputRHam);
            }
              
            
            //MACRO
           // System.out.println(line);
         //   reader.readLine();
         String l = reader.readLine().replace("(","\\(").replace(")","\\)");
            String[] parsed = l.split(",");
          //  System.out.println(parsed.length);
          //  System.out.println(line);
            System.out.println();
             for(int i = 0; i<parsed.length; i++){
                 stream.println("echo \""+line+" macro "+parsed[i]+"\"");
                stream.println("java -jar auc.jar "+path+parsed[i]+"-"+line+"FlatOne.txt list > "
                        +path+"AUCmacro"+parsed[i]+"FlatOne.txt");
               
                for(int ham = 0; ham<10; ham++){
                    stream.println("echo \""+line+" macro RHam\"");
                    stream.println("java -jar auc.jar "+path+parsed[i]+"-"+line+"RHamOne"+ham+".txt list > "
                            +path+"AUCmacro"+parsed[i]+"RHamOne"+ham+".txt");
                }
           
                
                  for(int k = 0; k<10; k++){
                    stream.println("echo \""+line+" macro KMeans\"");
                    stream.println("java -jar auc.jar "+path+parsed[i]+"-"+line+"KMeansOne"+k+".txt list > "
                            +path+"AUCmacro"+parsed[i]+"KMeansOne"+k+".txt");
                }
                
            }
             
         
        
        }
    }
    // > path/inputfileAUC.txt of zo?
    public static void main(String[] args) throws IOException{
        makeScript();
    }
    
}
