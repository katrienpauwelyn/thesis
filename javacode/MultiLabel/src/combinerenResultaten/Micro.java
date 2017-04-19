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
 *alle voorspellingen per koppel onder elkaar
 * @author katie
 */
public class Micro {
    
     public static void makeMicroFilesForAllDatasets() throws IOException{
        String basic;
        for(String dataset: Path.datasets){
            basic = Path.path.concat(dataset);
            makeMicroFiles(basic, basic+"/average.test.pred.arff", dataset);
        }
    }

    private static void makeMicroFiles(String basic, String fromFile, String dataset) throws IOException {
         int nbClasses = getNbClasses(fromFile);
         BufferedReader reader = new BufferedReader(new FileReader(fromFile));
         PrintStream stream = new PrintStream(new File(basic+"/micromacro/micro"+dataset+".txt"));
         while(!reader.readLine().contains("@DATA")){}
         String line;
         //1,0,0,1,0,1,1,1.0,0.0,0.6666666666666666,1.0,0.3333333333333333,1.0,0.0

         while((line=reader.readLine())!=null && !line.isEmpty()){
             String[] split = line.split(",");
             for(int i = 0; i<nbClasses; i++){
                 
                 stream.println(split[i+nbClasses]+" "+split[i]);
             }
         }
    }
    
    public static void main(String[] args) throws IOException{
        String basic = Path.path;
        String fromFile = basic+"flags/average.test.pred.arff";
        String dataset = "flags";
        makeMicroFiles(basic+"flags", fromFile, dataset);
    }
}
