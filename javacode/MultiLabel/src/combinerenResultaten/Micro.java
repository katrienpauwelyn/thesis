/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package combinerenResultaten;

import static combinerenResultaten.Macro.getNbClasses;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import statics.Path;

/**
 * maakt de micro files aan (van de average file)
 *alle voorspellingen per koppel onder elkaar: a1, a2, a3, p1, p2, p3 =>
 * a1 p1
 * a2 p2
 * a3 p3
 * @author katie
 */
public class Micro {
    
     public static void makeMicroFilesForAllDatasets() throws IOException{
        String basic;
        for(String dataset: Path.datasets){
            basic = Path.pathPinac+dataset+"/kmeans";
            makeMicroFiles(basic, basic+"/averageKMeans.test.pred.arff", dataset);
        }
    }

    public static void makeMicroFiles(String basic, String fromFile, String dataset) throws IOException {
         int nbClasses = getNbClasses(fromFile);
         BufferedReader reader = new BufferedReader(new FileReader(fromFile));
         PrintStream stream = new PrintStream(new File(basic+"/micromacro/micro"+dataset+".txt"));
         while(!reader.readLine().contains("@DATA")){}
         String line;
       System.out.println(nbClasses);
         while((line=reader.readLine())!=null && !line.isEmpty()){
             String[] split = line.split(",");
             for(int i = 0; i<nbClasses; i++){
                 stream.println(split[i+nbClasses]+" "+split[i]);
             }
         }
    }
    
}
