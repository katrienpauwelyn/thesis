/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExtractDataSeeds;

import ExtractData.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import staticData.Path;

/**
 *[DEPRECATED] is niet meer aangepast naar de verscihllende seeds
 * @author katie
 */
public class Test {
    
    //test if the test and training data together are the full data
    public boolean test(String testFile, String trainFile, String allFile) throws FileNotFoundException, IOException
    { BufferedReader readerTest = new BufferedReader(new FileReader(testFile));
        BufferedReader readerTrain = new BufferedReader(new FileReader(trainFile));
        BufferedReader readerAll = new BufferedReader(new FileReader(allFile));
        ArrayList<String> list = new ArrayList<String>();
        String te;
        boolean b = false;
        while( (te = readerTest.readLine()) != null){
        if(b){
            list.add(te);
        } else if(te.contains("@data")){
            b = true;
        }
    }
        b=false;
        while( (te = readerTrain.readLine()) != null){
        if(b){
            list.add(te);
        } else if(te.contains("@data")){
            b = true;
        }
    }
        b=false;
        while( (te = readerAll.readLine()) != null){
        if(b){
            if(!list.contains(te)){
                System.out.println("fout");
            }
            list.remove(te);
        } else if(te.contains("@data")){
            b = true;
        }
    }
        return list.isEmpty();
    }  
    
    
     public void testAllNDFolds() throws IOException{
        TestAllNDClass(Path.path);
    }
    
    
    //for each classifier
    public void TestAllNDClass(String path) throws IOException{
        for(String cl: Path.classifiers){
            testAllNDDatasets(path+"/"+cl);
        }
    }
    
    //for each dataset
    public void testAllNDDatasets(String path) throws IOException{
        for(String dataset: Path.datasets){
            testAllFolds(path+"/"+dataset);
        }
    }
    
    public void testAllFolds(String path) throws IOException{
        for(int i = 1; i<11; i++){
           System.out.println(test(path+"/NDtest"+Integer.toString(i)+".arff",path+"/NDfold"+Integer.toString(i)+".arff", path+"/NDfoldAll.arff"));
        }
    }
    
    public void testIt(String train, String test, String all) throws FileNotFoundException, IOException{
        System.out.println(all);
        BufferedReader readerTrain = new BufferedReader(new FileReader(train));
        BufferedReader readerTest = new BufferedReader(new FileReader(test));
        BufferedReader readerAll = new BufferedReader(new FileReader(all));
        ArrayList<String> listTrain = getListAfterData(readerTrain);
        ArrayList<String> listTest = getListAfterData(readerTest);
        ArrayList<String> listAll = getListAfterData(readerAll);
        for(String s: listTest){
            if(!listAll.contains(s)){
                System.out.println(s);
            }
            listAll.remove(s);
        }
        for(String s: listAll){
            if(!listTrain.contains(s)){
                System.out.println("train te kort"+s);
            }
            listTrain.remove(s);
        }
        if(!listTrain.isEmpty()){
            System.out.println("listTrain te groot"+listTrain.size());
        }
    }
    
    
    public ArrayList<String> getListAfterData(BufferedReader reader) throws IOException{
        ArrayList<String> out = new ArrayList<String>();
        boolean data=false;
        String line;
        while(( line = reader.readLine())!=null){
            if(data && !line.isEmpty()){
                out.add(line);
            } else {
                if(line.contains("@data")){
                    data = true;
                }
            }
    }
        return out;
    }
    
    
    public static void main(String[] args) throws FileNotFoundException, IOException{
        Test test = new Test();
       /* for(int i = 1; i<11; i++){
            System.out.println(test.test("/Users/katie/Dropbox/thesis/data/pageBlocks/pageBlocks/fold"+Integer.toString(i)+".arff", 
                "/Users/katie/Dropbox/thesis/data/pageBlocks/pageBlocks/test"+Integer.toString(i)+".arff", 
                "/Users/katie/Dropbox/thesis/data/pageBlocks/pageBlocks/foldAll.arff"));
        }*/
      
       test.testAllNDFolds();
        
        
        
    }

}
