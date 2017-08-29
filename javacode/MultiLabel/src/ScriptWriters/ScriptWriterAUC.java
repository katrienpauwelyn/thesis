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
public class ScriptWriterAUC {
    
    //op pathFilteredMao staat er een file waarin de naam van alle datasets staat met alle 
    //bijhorende labels. De labels die er uit gefilterd zijn (degene die nooit een effectieve 1 hebben
    //in de test set, staan er niet bij)
    public static void makeScript() throws FileNotFoundException, IOException{
        PrintStream stream = new PrintStream(new File(Path.pathToAUScript));
        //java -jar auc.jar testsetlist.txt list
        BufferedReader readerSp = new BufferedReader(new FileReader(Path.pathFilteredMao));
        BufferedReader readerSt = new BufferedReader(new FileReader(Path.pathStandardMap));
        makePartScript(stream, readerSp);
        makePartScript(stream, readerSt);
        readerSp.close();
        readerSt.close();
        stream.close();
    }
    
    public static void makePartScript(PrintStream stream, BufferedReader reader) throws IOException{
        String line;
        String path;
        String pathOutput;
        
        while((line=reader.readLine())!=null && !line.isEmpty()){
            String all = "micro"+line;
           path = Path.pathPinac.concat(line).concat("/kmeans/micromacro/");
            pathOutput = path.concat("AUCmicro"+line+".txt");
            stream.println("echo \""+line+" micro\"");
            stream.println("java -jar auc.jar "+path+all+".txt list > "+pathOutput);
            System.out.println(line);
            reader.readLine();
            String[] parsed = reader.readLine().split(",");
            System.out.println(parsed.length);
          //  System.out.println(line);
            System.out.println();
             for(int i = 0; i<parsed.length; i++){
                 stream.println("echo \""+line+" macro "+parsed[i]+"\"");
                stream.println("java -jar auc.jar "+path+parsed[i]+".txt list > "+path+"AUCmacro"+parsed[i]+".txt");
             }  
         
        }
    }
    // > path/inputfileAUC.txt of zo?
    public static void main(String[] args) throws IOException{
        makeScript();
    }
    
}
