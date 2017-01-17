/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExtractData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import staticData.Path;

/**
 *
 * @author katie
 */
public class MakeNDFolds {
    
    public void writeAllNDFolds() throws IOException{
        writeTrainingNDAllClassifiers(Path.path);
    }
    
    
    //for each classifier
    public void writeTrainingNDAllClassifiers(String path) throws IOException{
        for(String cl: Path.classifiers){
            writeTrainingNDAllDatasets(path+"/"+cl);
        }
    }
    
    //for each dataset
    public void writeTrainingNDAllDatasets(String path) throws IOException{
        for(String dataset: Path.datasets){
            writeTrainingND(path+"/"+dataset);
        }
    }

    //write the training data folds for one dataset
    public void writeTrainingND(String path) throws FileNotFoundException, IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path + "/outputData.txt"));
        PrintStream stream = new PrintStream(new File(path + "/NDfoldAll.arff"));
     
        String line;
        boolean data = false;
        ArrayList<String> listOfAllLines = new ArrayList<String>();
        ArrayList<String> header = new ArrayList<String>();
        
        while(true){
            line = bufferedReader.readLine();
            if(data){
                if(line.isEmpty()){ //de volledige data is over, nu volgen de folds
                    makeFolds(bufferedReader, path, listOfAllLines, header);
                    break;
                }
                listOfAllLines.add(line);
                
            } else {
                if(line.contains("@data")){//@ data behoort tot de header
                    data= true;
                }
                header.add(line);
            }
            
            stream.println(line);
        }
    }
    
    //make the 10 folds (not the 'fold' that contains all data) (and the corresponding test files)
    private void makeFolds(BufferedReader reader, String path, ArrayList<String> allData,
            ArrayList<String> header) throws IOException{
       
        String line; 
        
        for(int i = 1; i<Path.nbFolds+1; i++){
            String newPath = path+"/NDfold"+Integer.toString(i)+".arff";
            PrintStream print = new PrintStream(new File(newPath));
            ArrayList<String> foldData = new ArrayList<String>();
             boolean data = false;
            
            while((line = reader.readLine())!=null){
               
                if(data){
                    if(line.isEmpty()){//tijd voor de volgende fold
                        writeTestND(path+"/NDtest"+Integer.toString(i)+".arff", allData, foldData, header); //make the test file corresponding the previous fold
                        break;
                    }
                    foldData.add(line);
                }
               if(line.contains("@data")){//@data behoort niet tot de fold data
                    data = true;
                }
                
                print.println(line);
            }
        }
    }

    //write a test file (the difference between ListOfAllLines and listOfFoldLines)
    //path is the path and the complete name of the file
    public void writeTestND(String path, ArrayList<String> listOfAll, ArrayList<String> listOfFoldLines,
            ArrayList<String> header) throws FileNotFoundException {
        ArrayList<String> listOfAllLines = (ArrayList<String>) listOfAll.clone();
        PrintStream print = new PrintStream(new File(path));
        for(String s: header){
            print.println(s);
        }    
        
        for(String s: listOfFoldLines){
            listOfAllLines.remove(s);
        }
        for(String s: listOfAllLines){
            print.println(s);
        }
    }
    
    
    public static void main(String[] args) throws IOException{
        MakeNDFolds f = new MakeNDFolds();
        f.writeAllNDFolds();
    }

}
