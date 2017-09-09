/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package combinerenResultaten;

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
 * voor iedere klasse een file
 * bevat van alle instances enkel de waarde die overeenkomt met die klasse (zowel predicted als actual)
 * predicted - actual
 */
public class Macro {
    
    public static void makeMacroFilesForAllDatasets() throws IOException{
        String basic;
        for(String dataset: Path.sparseDatasets){
            basic = Path.pathPinac+dataset;
            makeMacroFiles(basic, basic+"/average.test.pred.arff");
        }
        for(String dataset: Path.datasets){
            basic = Path.pathPinac+dataset+"/kmeans";
            makeMacroFiles(basic, basic+"/averageKMeans.test.pred.arff");
        }
    }
    
    public static void makeMacroFiles(String path, String fromFile) throws IOException{
        int nbClasses = getNbClasses(fromFile);
        String file;
        BufferedReader reader = new BufferedReader(new FileReader(fromFile));
        PrintStream[] streams = new PrintStream[nbClasses];
        String line;
        while(!(line=reader.readLine()).contains("@ATTRIBUTE class-a-")){}
        for(int i = 0; i<nbClasses; i++){
            streams[i] = new PrintStream(new File(path+"/micromacro/"+getClassFromLine(line)+".txt"));
            line = reader.readLine();
        }
        while(!reader.readLine().contains("@DATA")){}
        while((line=reader.readLine())!=null && !line.isEmpty()){
            for(int i = 0; i<nbClasses; i++){
                streams[i].println(getValue(line, i+nbClasses)+" "+getValue(line, i));
            }
        }
        
    }
    
    //returnt hoe veel klassen er in de file zijn
    public static int getNbClasses(String file) throws FileNotFoundException, IOException{
        BufferedReader reader = new BufferedReader(new FileReader(new File(file)));
        int nbClasses = 1;
        while(!reader.readLine().contains("@ATTRIBUTE class-a-")){}
        while(reader.readLine().contains("@ATTRIBUTE class-a-")){
            nbClasses++;
        }
        reader.close();
        return nbClasses;
    }
    
    //vb: @ATTRIBUTE class-a-orange return orange
    public static String getClassFromLine(String line){
        String[] parsed = line.split("-");
        return parsed[parsed.length-1];
    }
    
    public static String getValue(String line, int index){
        String[] split = line.split(",");
        return split[index];
    }
}
