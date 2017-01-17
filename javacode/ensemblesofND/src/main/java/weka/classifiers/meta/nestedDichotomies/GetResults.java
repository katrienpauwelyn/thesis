/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weka.classifiers.meta.nestedDichotomies;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import staticData.Path;
import weka.classifiers.meta.nestedDichotomies.ClassBalancedND;
import weka.classifiers.meta.nestedDichotomies.FurthestCentroidND;
import weka.classifiers.meta.nestedDichotomies.ND;
import weka.classifiers.meta.nestedDichotomies.RandomPairND;

/**
 *
 * @author katie
 */
public class GetResults {
    
    public static String logisticRegression = "weka.classifiers.functions.Logistic";
    
    //logistic is true: logisticregression as base classifier. Logistic is false: C4.5 as base classifier
    //dataset: the name of the dataset. 
    //trainPath: the path of the training data
    //testPath: the path of the testing data
    public String[] getArgs(boolean logistic, String dataset, String trainPath, String testPath){
        if(logistic){
            if(Path.getIndexOfClassAttribute(dataset)<0){
                return new String[] {"-t", trainPath, "-T", testPath, "-W", logisticRegression};
            } else {
                return new String[] {"-t", testPath, "-T", trainPath, "-W", logisticRegression, "-c", "1"};
            }
        } else {
            if(Path.getIndexOfClassAttribute(dataset)<0){
                return new String[] {"-t", trainPath, "-T", testPath};
            } else {
                return new String[] {"-t", trainPath, "-T", testPath, "-c", "1"};
            }
        }
    }
    
    //run all the classifiers
    public void runAll() throws FileNotFoundException{
       String basicPath = Path.path;
       runAllND(basicPath+"/nd");
       runAllRandomPair(basicPath+"/randomPair");
       runAllFurthestCentroid(basicPath+"/furthestCentroid");
       runAllClassBalanced(basicPath+"/classBalanced");
    }
    
 
    
    public void runAllND(String path) throws FileNotFoundException{
        for(String s: Path.datasets){//for all datasets
            for(int i = 1; i<11; i++){//for all folds
                String trainPath = path+"/"+s+"/NDfold"+Integer.toString(i)+".arff";
                String testPath = path+"/"+s+"/NDtest"+Integer.toString(i)+".arff"; 
                PrintStream outTrue = new PrintStream(new FileOutputStream(path+"/"+s+"NDoutputLogistic"+Integer.toString(i)));
                System.setOut(outTrue);
                
                ND.main(getArgs(true, s, trainPath, testPath));
                outTrue.close();
               
                PrintStream outFalse = new PrintStream(new FileOutputStream(path+"/"+s+"NDoutputC45"+Integer.toString(i)));
                System.setOut(outFalse);
               
                ND.main(getArgs(false, s, trainPath, testPath));
                outFalse.close();
            }
        }
        
    
        
    }
    public void runRandomPair(String path, String dataset) throws FileNotFoundException{
          for(int i = 1; i<11; i++){
                String trainPath = path+"/NDfold"+Integer.toString(i)+".arff";
                String testPath = path+"/NDtest"+Integer.toString(i)+".arff"; 
                PrintStream outTrue = new PrintStream(new FileOutputStream(path+"NDoutputLogistic"+Integer.toString(i)));
               System.setOut(outTrue);
                RandomPairND.main(getArgs(true, dataset, trainPath, testPath));
                outTrue.close();
               
                 PrintStream outFalse = new PrintStream(new FileOutputStream(path+"NDoutputC45"+Integer.toString(i)));
                System.setOut(outFalse);
                RandomPairND.main(getArgs(false, dataset, trainPath, testPath));
                outFalse.close();
            }
    }
    
    public void runAllRandomPair(String path) throws FileNotFoundException{
        for(String s: Path.datasets){
            
          runRandomPair(path+"/"+s, s);
        }
    }
    
    public void runAllFurthestCentroid(String path) throws FileNotFoundException{
        for(String s: Path.datasets){
            for(int i = 1; i<11; i++){
                String trainPath = path+"/"+s+"/NDfold"+Integer.toString(i)+".arff";
                String testPath = path+"/"+s+"/NDtest"+Integer.toString(i)+".arff"; 
                PrintStream outTrue = new PrintStream(new FileOutputStream(path+"/"+s+"NDoutputLogistic"+Integer.toString(i)));
                System.setOut(outTrue);
                FurthestCentroidND.main(getArgs(true, s, trainPath, testPath));
                outTrue.close();
                
                PrintStream outFalse = new PrintStream(new FileOutputStream(path+"/"+s+"NDoutputC45"+Integer.toString(i)));
                System.setOut(outFalse);
                FurthestCentroidND.main(getArgs(false, s, trainPath, testPath));
                outFalse.close();                
            }
        }
    }
    
    
    
    public void runAllClassBalanced(String path) throws FileNotFoundException{
        for(String s: Path.datasets){
            for(int i = 1; i<11; i++){
                String trainPath = path+"/"+s+"/NDfold"+Integer.toString(i)+".arff";
                String testPath = path+"/"+s+"/NDtest"+Integer.toString(i)+".arff"; 
                PrintStream outTrue = new PrintStream(new FileOutputStream(path+"/"+s+"NDoutputLogistic"+Integer.toString(i)));
                System.setOut(outTrue);
                ClassBalancedND.main(getArgs(true, s, trainPath, testPath));
                outTrue.close();
                
                PrintStream outFalse = new PrintStream(new FileOutputStream(path+"/"+s+"NDoutputC45"+Integer.toString(i)));
                System.setOut(outFalse);
                ClassBalancedND.main(getArgs(false, s, trainPath, testPath));
                outFalse.close();
            }
        }
    }
    
    
    
    
    
    public static void main(String[] args) throws FileNotFoundException{
        GetResults getIt = new GetResults();
        String basicPath = Path.path;
        
       //getIt.runAllND(basicPath+"/nd");

       int size = Path.datasets.length;
    //   getIt.runRandomPair(basicPath+"/randomPair/"+Path.datasets[size-2], Path.datasets[size-2]);
      //  getIt.runAllRandomPair(basicPath+"/randomPair");
       
     //  getIt.runAllClassBalanced(basicPath+"/classBalanced");
     //  getIt.runAllFurthestCentroid(basicPath+"/furthestCentroid");
        
    }
    
}
