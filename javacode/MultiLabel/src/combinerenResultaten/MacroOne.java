/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package combinerenResultaten;

import static combinerenResultaten.MicroOne.makeMicroFiles;
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
 * alle instances met enkel die klasse
 * predicted - actual
 */
public class MacroOne {
    
    public static void makeMacroFilesForAllDatasets() throws IOException{
        String basic;
        for(String dataset: Path.datasets){
            System.out.println("macro "+dataset);
            basic = Path.pathPinac+dataset+"/one";
            //FLAT
            System.out.println("flat");
            String classifier = "-"+dataset+"FlatOne";
            makeMacroFiles(basic, basic+"/averageFlatOne.test.pred.arff", classifier);
            //KMeans
            System.out.println("kmeans");
            classifier = "-"+dataset+"KMeansOne";
            makeMacroFiles(basic, basic+"/averageKMeansOne.test.pred.arff", classifier);
            //RHam
            System.out.println("rham");
            for(int i = 0; i<10; i++){
                classifier ="-"+dataset+"RHamOne"+i;
                makeMacroFiles(basic, basic+"/averageRHamOne"+i+".test.pred.arff", classifier);
            }
        }
    }
    
    public static void makeMacroFiles(String path, String fromFile, String classifier) throws IOException{
        int nbClasses = getNbClasses(fromFile);
        String file;
        BufferedReader reader = new BufferedReader(new FileReader(fromFile));
        PrintStream[] streams = new PrintStream[nbClasses];
        String line;
        while(!(line=reader.readLine()).contains("@ATTRIBUTE class-a-")){}
        for(int i = 0; i<nbClasses; i++){
            streams[i] = new PrintStream(new File(path+"/micromacro/"+getClassFromLine(line)+classifier+".txt"));
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
