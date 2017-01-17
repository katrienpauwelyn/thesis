/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package katie;

import clus.error.BinaryPredictionList;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 *
 * @author katie
 */
public class CombinePredictions {
    
    public BinaryPredictionList makeList(String path) throws FileNotFoundException{
             BufferedReader predReader = new BufferedReader(new FileReader(path+"/test.test.pred.arff"));
             
             return null;
    }

     
    public static void main(String[] args){
        String path = "/Users/katie/NetBeansProjects/weka/trunk/packages/internal/ensemblesOfNestedDichotomies/katie";
    }
    
    
}
