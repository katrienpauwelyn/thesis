/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statistics;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import statics.Path;

/**
 *
 * @author katie
 */
public class StatisticsPrinter {
    
    
    public static void printAUAllDatasetsWithoutHier() throws FileNotFoundException, IOException, IOException{
        String p = "/Users/katie/thesisoutput/multilabel/ClusWithoutHier/outputPinac/pred/";
        
        PrintStream stream = new PrintStream(new File("/Users/katie/thesisoutput/multilabel/ClusWithoutHier/AUWithoutHierarchy.txt"));
        for(String dataset: Path.datasets){
            printAUDataset(stream, 
                    p+"settings"+dataset+".out", dataset);
        }
    }
    
    public static void printAUDataset(PrintStream stream, String pathOut, String dataset) throws IOException{
        TupleFloat meanAU = AUGetter.getAUPRCandAUROC(pathOut);
        TupleFloat weightedMeanAU = AUGetter.getWeightedAUPRCandAUROC(pathOut);
        String printString = dataset+"\t mean auprc: "+meanAU.getFirst()+
                "\t mean auroc: "+meanAU.getSecond()+"\t weighted mean auprc: "+weightedMeanAU.getFirst()+
                "\t weighted mean auroc: "+weightedMeanAU.getSecond();
        stream.println(printString);
    }
    
    public static void main(String[] args) throws IOException{
        printAUAllDatasetsWithoutHier();
    }
     
}
