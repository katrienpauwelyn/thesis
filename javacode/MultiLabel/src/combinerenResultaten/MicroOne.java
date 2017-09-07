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
public class MicroOne {
    
     public static void makeMicroFilesForAllDatasets() throws IOException{
        String basic;
       for(String dataset: Path.datasets){
            System.out.println("micro one "+dataset);
            basic = Path.pathPinac+dataset+"/one";
            //FLAT
            String output = basic+"/micromacro/micro"+dataset+"FlatOne.txt";
            makeMicroFiles(basic, basic+"/averageFlatOne.test.pred.arff", dataset, output);
            //KMeans
            output = basic+"/micromacro/micro"+dataset+"KMeansOne.txt";
            makeMicroFiles(basic, basic+"/averageKMeansOne.test.pred.arff", dataset, output);
            //RHam
            for(int i = 0; i<10; i++){
                output = basic+"/micromacro/micro"+dataset+"RHamOne"+i+".txt";
                makeMicroFiles(basic, basic+"/averageRHamOne"+i+".test.pred.arff", dataset, output);
            }
        }
    }

    public static void makeMicroFiles(String basic, String fromFile, String dataset, String output) throws IOException {
         int nbClasses = getNbClasses(fromFile);
         BufferedReader reader = new BufferedReader(new FileReader(fromFile));
         PrintStream stream = new PrintStream(new File(output));
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
