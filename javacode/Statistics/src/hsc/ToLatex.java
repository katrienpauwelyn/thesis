/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hsc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import staticData.Path;

/**
 *
 * @author katie
 */
public class ToLatex {
    
        public void convertAllToLatex() throws IOException{
        for(String classifier: Path.classifiers){
            convertOneClassifierToLatex(Path.path+"/"+classifier, classifier);
        }
    }
    
    // header: accuracy/mean / weighted mean & classifier C4.5 & classifier Logistic & Clus
    
    //path = bv. /Users/katie/NetBeansProjects/weka/trunk/packages/internal/ensemblesOfNestedDichotomies/out/classBalanced
    public void convertOneClassifierToLatex(String path, String classifier) throws FileNotFoundException, IOException{
        toLatex.ToLatex to = new toLatex.ToLatex();
        PrintStream stream = new PrintStream(new File(path+"/"+"alatexHsc.txt"));
       to.printAuprc(classifier, stream, path+"/"+"aStatisticsClus.txt", path+"/"+"aHsc");
        stream.println();
        stream.println();
        to.printAuroc(classifier, stream, path+"/"+"aStatisticsClus.txt", path+"/"+"aHsc");
        stream.println();
        stream.println();
        to.printAccuracy(classifier, stream, path+"/"+"aStatisticsClus.txt", path+"/"+"aHsc");
    }
    
    
    public static void main(String[] args) throws IOException{
        ToLatex to = new ToLatex();
        to.convertAllToLatex();
    }
}
