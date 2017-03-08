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
    
    
    public static void printAllDatasets() throws FileNotFoundException, IOException, IOException{
        PrintStream stream = new PrintStream(new File(Path.path+"/statistics.txt"));
        for(String dataset: Path.datasets){
            System.out.println(dataset);
            printDataset(stream, Path.path+"/"+dataset+"/settings.test.pred.arff", 
                    Path.path+"/"+dataset+"/settings.out", dataset);
        }
    }
    
    public static void printDataset(PrintStream stream, String pathPred, String pathOut, String dataset) throws IOException{
        boolean hasKey = dataset.equals("genbase");
        double hammingLoss = HammingLoss.getHammingLoss(pathPred, hasKey);
        TupleFloat meanAU = AUGetter.getAUPRCandAUROC(pathOut);
        TupleFloat weightedMeanAU = AUGetter.getWeightedAUPRCandAUROC(pathOut);
        String printString = dataset+"\t hammingLoss: "+hammingLoss+"\t mean auprc: "+meanAU.getFirst()+
                "\t mean auroc: "+meanAU.getSecond()+"\t weighted mean auprc: "+weightedMeanAU.getFirst()+
                "\t weighted mean auroc: "+weightedMeanAU.getSecond();
        stream.println(printString);
    }
    
    public static void main(String[] args) throws IOException{
        printAllDatasets();
    }
     
}
