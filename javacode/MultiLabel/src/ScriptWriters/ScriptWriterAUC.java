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
 * TODO moet de "/" terug vervangen worden door "-" in de file names? (: mag niet)
 */
public class ScriptWriterAUC {
    
    public static void makeScript() throws FileNotFoundException, IOException{
        PrintStream stream = new PrintStream(new File(Path.pathToAUScript));
        //java -jar auc.jar testsetlist.txt list
        BufferedReader readerSp = new BufferedReader(new FileReader(Path.pathSparseMap));
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
            if(!line.contains("tmc")){
                String all = "micro"+line;
           
            path = Path.pathPinac.concat(line).concat("/micromacro/");
            pathOutput = path.concat("AUCmicro"+line+".txt");
            stream.println("echo \""+line+" micro\"");
            stream.println("java -jar auc.jar "+path+all+".txt list > "+pathOutput);
            
            reader.readLine();
            String[] parsed = reader.readLine().split(",");
            for(String s: parsed){//kan zijn dat de laatste een error geeft: eindigt op ,
                 stream.println("echo \""+line+" macro "+s+"\"");
                stream.println("java -jar auc.jar "+path+s+".txt list > "+path+"AUCmacro"+s+".txt");
            }
            } else{
                reader.readLine();
                reader.readLine();
            }
            
        }
    }
    // > path/inputfileAUC.txt of zo?
    public static void main(String[] args) throws IOException{
        makeScript();
    }
    
}
