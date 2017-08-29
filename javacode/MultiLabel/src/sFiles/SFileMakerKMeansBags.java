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
public class SFileMakerKMeansBags {
       public static void makeAllSFiles() throws FileNotFoundException{
        String path = Path.pathPinac;
        for(String dataset: Path.datasets){
            for(int i = 0; i<Path.nbBags; i++){
                int index = i+1;
                String pathSFile = path+dataset+"/kmeans/settingsKBag"+i+".s";
                String pathTrain = path+dataset+"/settings-bag-"+index+".arff";
                String pathTest = path+dataset+"/"+dataset+"test.arff";
                String pathHierarchy = path+dataset+"/kmeansHierBag+"+i+".txt";
                makeOneSFile(pathSFile, pathTrain, pathTest, pathHierarchy);
            }
            
        }
        
    }
    
    //.arff begint vanaf 1, hier vanaf 0, settings vanaf 0
    public static void makeOneSFile(String pathSFile, String pathTrain, String pathTest, 
            String pathHierarchy) throws FileNotFoundException{
        PrintStream stream = new PrintStream(new File(pathSFile));
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
            stream.println("DefinitionFile = "+pathHierarchy);
            stream.println("EmptySetIndicator = None");
            stream.println();
            stream.println("[Output]");
            stream.println("AllFoldErrors = Yes");
            stream.println("WritePredictions = Test");
    }
    
    
    public static void main(String[] args) throws FileNotFoundException{
        makeAllSFiles();
    }
}
