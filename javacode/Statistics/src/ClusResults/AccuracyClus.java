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
 *Berekent de accuracy van de output van Clus.
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
    public TupleInt getNbPosNegAllFolds(String path, int seed, String achtervoegsel) throws IOException{
        String newPath;
        TupleInt tuple = new TupleInt();
        
        for(int i = 0; i<Path.nbFolds; i++){
            
            newPath = path+"/asettings/S"+seed+"settingsFold"+Integer.toString(i+1)+achtervoegsel;
            tuple.addTuple(getNbPosNegFile(newPath));
        }
        return tuple;
    }
    
    
        //berekent het aantal correct en incorrect voorspelde instances
    //van alle folds van 1 dataset samen
    //methode die gebruikt wordt voor hsc. Indien er 'hsc' in de naam moet staan is hsc true
    public TupleInt getNbPosNegAllFoldsWithHsc(String path, int seed, String achtervoegsel, boolean hsc) throws IOException{
        String newPath;
        TupleInt tuple = new TupleInt();
        
        for(int i = 0; i<Path.nbFolds; i++){
            if(hsc){
                newPath = path+"/asettings/hscS"+seed+"settingsFold"+Integer.toString(i+1)+achtervoegsel;
            } else {
                newPath = path+"/asettings/S"+seed+"settingsFold"+Integer.toString(i+1)+achtervoegsel;
            }
            
            tuple.addTuple(getNbPosNegFile(newPath));
        }
        return tuple;
    }
    
    //tussenvoegsel en hsc zijn toegevoegd voor hsc redenen.
    //tussenvoegsel is ".hsc.combined" indien het voor een HSC dataset gerund wordt, "" als het een niet HSC-dataset is
    //hsc is true als de dataset "letterRecognition" of "segmentation" is (deze hebben een licht andere naam)
    public double getAccuracy(String path, int seed, String tussenvoegsel, boolean hsc) throws IOException{
        TupleInt posNeg;
        if(hsc){
            posNeg = getNbPosNegAllFoldsWithHsc(path, seed, tussenvoegsel+".test.pred.arff", hsc);
        } else {
            posNeg = getNbPosNegAllFolds(path, seed, tussenvoegsel+".test.pred.arff");
        }
        return posNeg.getAccuracy();
    }
  
}
