/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sFiles;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import statics.Path;

/**
 *
 * @author katie
 */
public class SFileMakerOne {
 
       public static void makeAllSFiles() throws FileNotFoundException{
       PrintStream stream;
     for(String d: Path.datasets){
            String train = Path.pathPinac+d+"/"+d+"train.arff";
            String test = Path.pathPinac+d+"/"+d+"test.arff";
            //FLAT 
            stream = new PrintStream(new File(Path.pathPinac+d+"/one/settingsFlatOne.s"));
             makeSFileOne(stream, train, test,
                     Path.pathPinac+d+"/one/hierOneFlat.txt");
             stream.close();
             
             //KMEANS
             stream = new PrintStream(new File(Path.pathPinac+d+"/one/settingsKMeansOne.s"));
             makeSFileOne(stream, train, test, Path.pathPinac+d+"/one/kmeansHierOne.txt");
             stream.close();
             
             //RHam
             for(int i = 0; i<10; i++){
                 stream = new PrintStream(new File(Path.pathPinac+d+"/one/settingsRHamOne"+i+".s"));
                 makeSFileOne(stream, train, test, Path.pathPinac+d+"/one/hierOneRHam"+i);
                 stream.close();
             }
             
         }
     }
   
     private static void makeSFileOne(PrintStream stream, String pathTrain, String pathTest,
             String pathHierarchy){
            stream.println("[General]");
            stream.println("RandomSeed = 0");
            stream.println();
            stream.println("[Data]");
            stream.println("File = " + pathTrain);
            stream.println("TestSet = "+ pathTest);
            stream.println();
            stream.println("[Hierarchical]");
            stream.println("Type = DAG");
            stream.println("HSeparator = /");
            stream.println("SingleLabel = No");
            stream.println("DefinitionFile = "+pathHierarchy);
            stream.println("EmptySetIndicator = None");
            stream.println();
            stream.println("[Output]");
            stream.println("AllFoldErrors = Yes");
            stream.println("WritePredictions = Test");
            stream.println();
            stream.println("[Tree]");
            stream.println("FTest = 0.05");
            stream.println();
            stream.println("[Model]");
            stream.println("MinimalWeight = 5.0");
      }
      
      public static void main(String[] args) throws FileNotFoundException{
        makeAllSFiles();
      }
}
