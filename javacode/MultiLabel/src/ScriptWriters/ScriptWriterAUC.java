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
 * 
 * Maakt een script om het scriptje uit te voeren die de AUCs berekent.
 * 
 */
public class ScriptWriterAUC {
    
      //op pathFilteredMao staat er een file waarin de naam van alle datasets staat met alle 
    //bijhorende labels. De labels die er uit gefilterd zijn (degene die nooit een effectieve 1 hebben
    //in de test set, staan er niet bij)
    public static void makeScript() throws FileNotFoundException, IOException{
        PrintStream stream = new PrintStream(new File(Path.pathToAUScript));
        //java -jar auc.jar testsetlist.txt list
       BufferedReader readerSp = new BufferedReader(new FileReader(Path.pathFilteredMao));
        BufferedReader readerSt = new BufferedReader(new FileReader(Path.pathFilteredAll));
        makePartScript(stream, readerSp);
        makePartScript(stream, readerSt);
        readerSp.close();
        readerSt.close();
        stream.close();
    }
    
    public static void makePartScript(PrintStream stream, BufferedReader reader) throws IOException{
        String line;
        String path;
        
        
        while((line=reader.readLine())!=null && !line.isEmpty()){
            //java -jar auc.jar /export/home1/NoCsBack/thesisdt/s0212310/bibtex/micromacro/microbibtex.txt list > /export/home1/NoCsBack/thesisdt/s0212310/bibtex/micromacro/AUCmicrobibtex.txt
                    //MICRO
            String all = "micro"+line+".txt";
           
            String pathRHam = Path.pathPinac.concat(line).concat("/micromacro/");
            String pathKMeans = Path.pathPinac.concat(line).concat("/kmeans/micromacro/");
            String pathFlat = Path.pathPinac.concat(line).concat("/flat/micromacro/");
            String pathOutputFlat = pathFlat.concat("AUCmicro"+line+"Flat.txt");
            String pathOutputKmeans = pathKMeans.concat("AUCmicro"+line+"KmeansFull.txt");
            String pathOutputRHam = pathRHam.concat("AUCmicro"+line+"RHam.txt");
          //  
             
            stream.println("echo \""+line+" micro\"");
            
            stream.println("java -jar auc.jar "+pathFlat+all+" list > "+pathOutputFlat);
            stream.println("java -jar auc.jar "+pathKMeans+all+" list > "+pathOutputKmeans);
            stream.println("java -jar auc.jar "+pathRHam+all+" list > "+pathOutputRHam);
           
         
            
              
            //java -jar auc.jar /export/home1/NoCsBack/thesisdt/s0212310/bibtex/micromacro/TAG_2005.txt list > /export/home1/NoCsBack/thesisdt/s0212310/bibtex/micromacro/AUCmacroTAG_2005.txt
            //MACRO
           String l =  reader.readLine().replace("(","\\(").replace(")","\\)");
            String[] parsed = l.split(",");
            stream.println("echo \""+line+" macro "+"\"");
            
             for(int i = 0; i<parsed.length; i++){
                 
                 //FLAT
               stream.println("java -jar auc.jar "+pathFlat+parsed[i]+".txt list > "
                        +pathFlat+"AUCmacro"+parsed[i]+"Flat.txt");
               //RHAM
           stream.println("java -jar auc.jar "+pathRHam+parsed[i]+".txt list > "
                        +pathRHam+"AUCmacro"+parsed[i]+"RHam.txt");
           //KMEANS
               stream.println("java -jar auc.jar "+pathKMeans+parsed[i]+".txt list > "
                        +pathKMeans+"AUCmacro"+parsed[i]+"KMeansFull.txt");
               

                
            }
             
        }
        
        }
    
    public static void main(String[] args) throws IOException{
       
 makeScript();
    }
    
    
}
