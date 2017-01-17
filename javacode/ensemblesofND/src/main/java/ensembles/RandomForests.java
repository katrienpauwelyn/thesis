/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ensembles;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.meta.Bagging;
import weka.classifiers.meta.nestedDichotomies.ClassBalancedND;

/**
 *
 * @author katie
 */
public class RandomForests {
    
    public void runClassBalanced(){
        ClassBalancedND classBalanced = new ClassBalancedND();
        Bagging f = new Bagging();
        f.setClassifier(classBalanced);
             String[] arg = new String[2];
      arg[0] = "-t"; //training
      arg[1] = "/Users/katie/NetBeansProjects/weka/trunk/packages/internal/ensemblesOfNestedDichotomies/out/nd/mfeatFac/NDfold1.arff";
    //  arg[2] = "-T";
    //  arg[3] = "/Users/katie/NetBeansProjects/weka/trunk/packages/internal/ensemblesOfNestedDichotomies/out/nd/mfeatFac/NDtest1.arff";
   //   arg[2] = "-W"; //full name of the base classifier
   //   arg[3] = "weka.classifiers.functions.Logistic";
     AbstractClassifier.runClassifier(f,arg);
    }
    
    public static void main(String[] args){
        RandomForests r = new RandomForests();
        r.runClassBalanced();
    }
    
}
