/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClusResults;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import staticData.Path;

/**
 *
 * @author katie
 */
public class AccuracyClus {
    
    //Berekent het aantal correct voorspelde en incorrect voorspelde instances van een file
    public TupleInt getNbPosNegFile(String path) throws FileNotFoundException, IOException{
        BufferedReader reader = new BufferedReader(new FileReader(path));
        TupleInt posNeg = new TupleInt();
        String line;
        
          while(true){
           line = reader.readLine();
           if(line.contains("@DATA")){
               break;
           }
       }
       String[] parsedKomma;
       while((line = reader.readLine())!=null && !line.isEmpty() ){
           parsedKomma = line.split(",");
           if(parsedKomma[0].equals(parsedKomma[1])){ //0 is de effectieve klasse, 1 de voorspelde
               posNeg.incrementPositiveOne();
           } else {
               posNeg.incrementNegativeOne();
           }
       }
        
        return posNeg;
    }
    
    
    //berekent het aantal correct en incorrect voorspelde instances
    //van alle folds van 1 dataset samen
    public TupleInt getNbPosNegAllFolds(String path) throws IOException{
        String newPath;
        TupleInt tuple = new TupleInt();
        for(int i = 0; i<Path.nbFolds; i++){
            newPath = path+"/settings"+Integer.toString(i+1)+".test.pred.arff";
            tuple.addTuple(getNbPosNegFile(newPath));
        }
        return tuple;
    }
    
    public double getAccuracy(String path) throws IOException{
        TupleInt posNeg = getNbPosNegAllFolds(path);
        return posNeg.getAccuracy();
    }
    
        //berekent het aantal correct en incorrect voorspelde instances (voor ensembles)
    //van alle folds van 1 dataset samen
    public TupleInt getNbPosNegAllFoldsEnsemble(String path) throws IOException{
        String newPath;
        TupleInt tuple = new TupleInt();
        for(int i = 0; i<Path.nbFolds; i++){
            newPath = path+"/settingsEnsemble"+Integer.toString(i+1)+".test.pred.arff";
            tuple.addTuple(getNbPosNegFile(newPath));
        }
        return tuple;
    }
    
    //(voor ensembles)
    public double getAccuracyEnsemble(String path) throws IOException{
        TupleInt posNeg = getNbPosNegAllFoldsEnsemble(path);
        return posNeg.getAccuracy();
    }
    
}
