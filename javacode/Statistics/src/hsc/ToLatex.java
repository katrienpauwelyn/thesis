/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hsc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import staticData.Path;

/**
 * PROBLEEM: niet alle datasets zitten er in
 * @author katie
 */
public class ToLatex {
    
        public void convertAllToLatex() throws IOException{
        for(String classifier: Path.classifiers){
            convertOneClassifierToLatex(Path.path+"/"+classifier, classifier);
        }
         //convertOneClassifierToLatex(Path.path+"/"+"classBalanced", "classBalanced");
         //convertOneClassifierToLatex(Path.path+"/"+"furthestCentroid", "furthestCentroid");
         //convertOneClassifierToLatex(Path.path+"/"+"randomPair", "randomPair");
    }
    
    // header: accuracy/mean / weighted mean & classifier C4.5 & classifier Logistic & Clus
    
    //path = bv. /Users/katie/NetBeansProjects/weka/trunk/packages/internal/ensemblesOfNestedDichotomies/out/classBalanced
    public void convertOneClassifierToLatex(String path, String classifier) throws FileNotFoundException, IOException{
        toLatex.ToLatex to = new toLatex.ToLatex();
        PrintStream stream = new PrintStream(new File(path+"/"+"alatexHsc.tex"));
       to.printAuprc(classifier, stream, path+"/"+"aStatisticsClus.txt", path+"/"+"aHsc.txt");
        stream.println();
        stream.println();
        to.printAuroc(classifier, stream, path+"/"+"aStatisticsClus.txt", path+"/"+"aHsc.txt");
        stream.println();
        stream.println();
        to.printAccuracy(classifier, stream, path+"/"+"aStatisticsClus.txt", path+"/"+"aHsc.txt");
    }
    
  
    public static void main(String[] args) throws IOException{
        ToLatex to = new ToLatex();
        to.convertAllToLatex();
    }
    
    
}
