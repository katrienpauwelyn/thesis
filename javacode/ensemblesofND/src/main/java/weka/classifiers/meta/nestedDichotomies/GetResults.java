package weka.classifiers.meta.nestedDichotomies;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import staticData.Path;

/**
 * Runs alle ND programma's op alle train en test files. 
 * Slaat de resultaten en runtimes op.
 * @author katie
 */
public class GetResults {
    
    public static String logisticRegression = "weka.classifiers.functions.Logistic";
    
    //logistic is true: logisticregression as base classifier. Logistic is false: C4.5 as base classifier
    //dataset: the name of the dataset. 
    //trainPath: the path of the training data
    //testPath: the path of the testing data
    public String[] getArgs(boolean logistic, String dataset, String trainPath, String testPath, String seed){
        if(logistic){
            if(Path.getIndexOfClassAttribute(dataset)<0){
                return new String[] {"-t", trainPath, "-T", testPath, "-W", logisticRegression, "-s", seed, "-S", seed};
            } else {
                return new String[] {"-t", testPath, "-T", trainPath, "-W", logisticRegression, "-c", "1", "-s", seed, "-S", seed};
            }
        } else {
            if(Path.getIndexOfClassAttribute(dataset)<0){
                return new String[] {"-t", trainPath, "-T", testPath, "-s", seed, "-S", seed};
            } else {
                return new String[] {"-t", trainPath, "-T", testPath, "-c", "1", "-s", seed, "-S", seed};
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
            PrintStream timeStream = new PrintStream(new File(
            path+"/nd/"+s+"/aOutputND/NDaTime.txt"));
            for(int seed= 0; seed<Path.nbSeeds; seed++){//for all seeds
                for(int i = 1; i<11; i++){//for all folds
                   String trainPath = path+"/nd/"+s+"/NDS"+seed+"fold"+Integer.toString(i)+".arff";
                   String testPath = path+"/nd/"+s+"/NDS"+seed+"test"+Integer.toString(i)+".arff";
                  
                   PrintStream outStream = new PrintStream(new FileOutputStream(
                           path+"/nd/"+s+"/aOutputND/NDS"+seed+"outputC45"+Integer.toString(i)));
                   System.setOut(outStream);
                   
                   long startTime = System.nanoTime();
                   ND.main(getArgs(false, s, trainPath, testPath, Integer.toString(seed)));
                   long endTime = System.nanoTime();
                   long durationMiliSeconds = (endTime - startTime)/1000000;
                   timeStream.println(durationMiliSeconds);
                   
                   outStream.close();
                }
            }
            timeStream.close();
        }     
    }
    
    public void runAllRandomPair(String path) throws FileNotFoundException{
        for(String s: Path.datasets){//for all datasets
            PrintStream timeStream = new PrintStream(new File(
               path+"/randomPair/"+s+"/aOutputND/NDaTime.txt"));

           for(int seed = 0; seed<Path.nbSeeds; seed++){
               for(int i = 1; i<11; i++){//for all folds
                   String trainPath = path+"/randomPair/"+s+"/NDS"+seed+"fold"+Integer.toString(i)+".arff";
                   String testPath = path+"/randomPair/"+s+"/NDS"+seed+"test"+Integer.toString(i)+".arff"; 

                   PrintStream outStream = new PrintStream(new FileOutputStream(path+"/randomPair/"+s+
                           "/aOutputND/NDS"+seed+"outputC45"+Integer.toString(i)));
                   System.setOut(outStream);
                   
                   long startTime = System.nanoTime();
                   RandomPairND.main(getArgs(false, s, trainPath, testPath, Integer.toString(seed)));
                   long endTime = System.nanoTime();
                   long durationMiliSeconds = (endTime - startTime)/1000000;
                   timeStream.println(durationMiliSeconds);
                   
                   outStream.close();
                }
            }
           timeStream.close();
        }
    }
    
    
    public void runAllFurthestCentroid(String path) throws FileNotFoundException{
        for(String s: Path.datasets){//for all datasets
             PrintStream timeStream = new PrintStream(new File(
               path+"/furthestCentroid/"+s+"/aOutputND/NDaTime.txt"));

            for(int seed= 0; seed<Path.nbSeeds; seed++){//for all seeds
                  for(int i = 1; i<11; i++){//for all folds
                        String trainPath = path+"/furthestCentroid/"+s+"/NDS"+seed+"fold"+Integer.toString(i)+".arff";
                        String testPath = path+"/furthestCentroid/"+s+"/NDS"+seed+"test"+Integer.toString(i)+".arff"; 
                   
                        PrintStream outStream = new PrintStream(
                                new FileOutputStream(path+"/furthestCentroid/"+s+"/aOutputND/NDS"+seed+"outputC45"+Integer.toString(i)));
                        System.setOut(outStream);
                         long startTime = System.nanoTime();
                        FurthestCentroidND.main(getArgs(false, s, trainPath, testPath, Integer.toString(seed)));
                        long endTime = System.nanoTime();
                          long durationMiliSeconds = (endTime - startTime)/1000000;
                        timeStream.println(durationMiliSeconds);
                   
                        outStream.close();                
            }
            }
          timeStream.close();
        }
    }
    
    
    
    public void runAllClassBalanced(String path) throws FileNotFoundException{
        for(String s: Path.datasets){//for all datasets
             PrintStream timeStream = new PrintStream(new File(
               path+"/classBalanced/"+s+"/aOutputND/NDaTime.txt"));

            for(int seed = 0; seed < Path.nbSeeds; seed++){
                for(int i = 1; i<11; i++){
                    String trainPath = path+"/classBalanced/"+s+"/NDS"+seed+"fold"+Integer.toString(i)+".arff";
                    String testPath = path+"/classBalanced/"+s+"/NDS"+seed+"test"+Integer.toString(i)+".arff"; 
                
                    PrintStream outStream = new PrintStream(
                            new FileOutputStream(path+"/classBalanced/"+s+"/aOutputND/NDS"+seed+"outputC45"+Integer.toString(i)));
                    System.setOut(outStream);
                     long startTime = System.nanoTime();
                    ClassBalancedND.main(getArgs(false, s, trainPath, testPath, Integer.toString(seed)));
                     long endTime = System.nanoTime();
                          long durationMiliSeconds = (endTime - startTime)/1000000;
                        timeStream.println(durationMiliSeconds);
                   
                    outStream.close();
            }
            }
            timeStream.close();
        }
    }
   
    public static void main(String[] args) throws FileNotFoundException{
        GetResults getIt = new GetResults();
        getIt.runAllClassBalanced(Path.path);
        
    }
    
}
