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
public class SFileMakerMakeFolds {
    
    
    public static void makeAllSFiles() throws FileNotFoundException{
        String path = Path.pathPinac;
        //String path = "/Users/katie/Desktop/nieuweDatasets/";
        for(String dataset: Path.datasets){
                String pathSFile = path+dataset+"/settings.s";
                String pathTrain = path+dataset+"/"+dataset+"train.arff";
                String pathTest = path+dataset+"/"+dataset+"test.arff";
                makeOneSFile(pathSFile, pathTrain, pathTest, dataset);
        }
        
    }
    
    //.arff begint vanaf 1, hier vanaf 0, settings vanaf 0
    public static void makeOneSFile(String pathSFile, String pathTrain, String pathTest,
            String dataset
            ) throws FileNotFoundException{
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
            stream.println("SingleLabel = No");
            stream.println("DefinitionFile = /export/home1/NoCsBack/thesisdt/s0212310/nieuweDatasets/"+dataset+"/flat/flatHier.txt");
            stream.println("EmptySetIndicator = None");
            stream.println();
            stream.println("[Output]");
            stream.println("AllFoldErrors = Yes");
            stream.println("WritePredictions = Test");
             stream.println();
              stream.println("[Ensemble]");
             stream.println("Iterations = 50");
             stream.println("EnsembleMethod = Bagging");
             stream.println("WriteBagTrainingFiles = Yes");
             stream.println();
            stream.println("[Model]");
            stream.println("MinimalWeight = 391");
    }
    
    
    public static void main(String[] args) throws FileNotFoundException{
        makeAllSFiles();
    }
    
}
