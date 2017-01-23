/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClusResults;

import dataTypes.TupleFloat;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import staticData.Path;

/**
 *
 * @author katie
 * Deze klasse schrijft alle statistieken van CLUS uit in files (1 file per classifier).
 */
public class WriteAllStatistics {
    
    //iterate over all classifiers
    public void printStatisticsOfAllClassifiersToFile(String path) throws IOException{
        for(String s: Path.classifiers){
            printStatisticsOfAllDatasets(path+"/"+s, s+"Statistics");
        }
    }
    
    //iterate over all datasets
    private void printStatisticsOfAllDatasets(String path, String name) throws IOException{
        AUClus auClus = new AUClus();
        AccuracyClus accCl = new AccuracyClus();
        TupleFloat mean;
        TupleFloat weighted;
        double acc; 
        for(String s: Path.datasets){ //voor iedere dataset evenveel resultaten files als er seeds zijn
            for(int seed = 0; seed<Path.nbSeeds; seed++){
                 PrintStream stream = new PrintStream(new File(path+"/S"+seed+name));
                 mean = auClus.getMeanAUForAllFolds(path+"/"+s, seed);
                weighted = auClus.getWeightedAUForAllFolds(path+"/"+s, seed);
                acc = accCl.getAccuracy(path+"/"+s, seed);
                stream.println(s+"\t\t"+"Mean auprc: "+Float.toString(mean.getFirst())+
                        "\t\t Mean auroc: "+Float.toString(mean.getSecond())+
                        "\t\t"+"Weighted mean auprc: "+Float.toString(weighted.getFirst())+
                        "\t\t"+"Weighted mean auroc: "+Float.toString(weighted.getSecond())+
                "\t\t"+"accuracy: "+Double.toString(acc));
                stream.close();
            }
            
           
           
        }
        
    }
    
    //print de statistieken uit van de 4 test datasets
 /*   private void printStatisticsOfFourDatasets() throws FileNotFoundException, IOException{
        String[] datasets = {"audiology","krkopt","mfeatFac","pageBlocks"};
        for(String classifier: Path.classifiers){
            String path = Path.path+"/"+classifier;
            File file = new File(path+"/StatisticsFour");
            PrintStream stream = new PrintStream(file);
            AUClus auprcClus = new AUClus();
            AccuracyClus accCl = new AccuracyClus();
            TupleFloat mean;
            TupleFloat weighted;
            double acc; 
            
            for(String s : datasets){        
                mean = auprcClus.getMeanAUForAllFoldsEnsemble(path+"/"+s);
                weighted = auprcClus.getWeightedAUForAllFoldsEnsemble(path+"/"+s);
                acc = accCl.getAccuracyEnsemble(path+"/"+s);
                stream.println(s+"\t\t"+"Mean auprc: "+Float.toString(mean)+"\t\t"+"Weighted mean auprc: "+Float.toString(weighted)+
                "\t\t"+"accuracy: "+Double.toString(acc));
            }
        }
        
    }*/
    
    public static void main(String[] args) throws IOException{
      WriteAllStatistics write = new WriteAllStatistics();
      write.printStatisticsOfAllClassifiersToFile(Path.path);
 //      write.printStatisticsOfFourDatasets();
    }    
}
