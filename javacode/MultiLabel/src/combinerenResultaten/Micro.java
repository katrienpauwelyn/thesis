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
 * wordt gebruikt door het AUC script.
 * @author katie
 */
public class Micro {
    
     public static void makeMicroFilesForAllDatasets() throws IOException{
        String basic;
        System.out.println("MICRO");
        for(String dataset: Path.datasets){
            System.out.println(dataset);
            //KMEANS
            System.out.println("kmeans");
            basic = Path.pathPinac+dataset+"/kmeans";
            makeMicroFiles(basic, basic+"/averageKMeansFull.test.pred.arff", dataset);
            //RHAM
          /*  System.out.println("rham");
            basic = Path.pathPinac+dataset;
             makeMicroFiles(basic, basic+"/averageRHam.test.pred.arff", dataset);
            //FLAT
            System.out.println("flat");
             basic = Path.pathPinac+dataset+"/flat";
             makeMicroFiles(basic, basic+"/average.test.pred.arff", dataset);
           */
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
