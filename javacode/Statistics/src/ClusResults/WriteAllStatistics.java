/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClusResults;

import dataTypes.TupleFloat;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import staticData.Path;
import statistics.CollectionMeanStdev;
import toLatex.ToLatex;

/**
 *
 * @author katie
 * Deze klasse schrijft alle statistieken van CLUS uit in files (1 file per classifier).
 * 
 * eerst printStatisticsOfAllClassifiersToFile om per seed de resultaten te halen
 * dan combineMultipleSeedsAllClassifiers om het gemiddelde en standaard deviatie van de seeds samen te combineren
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
                 PrintStream stream = new PrintStream(new File(path+"/"+s+"/asettings/S"+seed+name));
                 mean = auClus.getMeanAUForAllFolds(path+"/"+s, seed);
                weighted = auClus.getWeightedAUForAllFolds(path+"/"+s, seed);
                acc = accCl.getAccuracy(path+"/"+s, seed);
                stream.println(s+"\t\t"+"Mean auprc: "+Double.toString(mean.getFirst())+
                        "\t\t Mean auroc: "+Double.toString(mean.getSecond())+
                        "\t\t"+"Weighted mean auprc: "+Double.toString(weighted.getFirst())+
                        "\t\t"+"Weighted mean auroc: "+Double.toString(weighted.getSecond())+
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
    
    public static void combineMultipleSeedsAllClassifiers(String path) throws IOException{
        for(String classifier: Path.classifiers){
            combineMultipleSeedsAllDatasets(path+"/"+classifier, classifier);
        }
    }
    
    
       //voorbeeld van een pad
    ///Users/katie/thesisoutput/out/nd
    public static void combineMultipleSeedsAllDatasets(String path, String classifier) throws FileNotFoundException, IOException{
        PrintStream stream = new PrintStream(new File(path+"/aStatisticsClus.txt"));
        for(String dataset: Path.datasets){
            combineMultipleSeeds(path, dataset, stream, classifier);
        }
        stream.close();
    }
    
    //voorbeeld van een pad
    ///Users/katie/thesisoutput/out/nd
    // /audiology/asettings
    //combineer alle seeds tot 1 enkele file. Combineer de verschillende resultaten van de
    //verschillende seeds tot 1 resultaat (gemiddelde en standaard deviatie)
    public static void combineMultipleSeeds(String path, String dataset, 
            PrintStream stream, String classifier) throws FileNotFoundException, IOException{
        CollectionMeanStdev meanAuprc = new CollectionMeanStdev();
        CollectionMeanStdev meanAuroc = new CollectionMeanStdev();
        CollectionMeanStdev weightedMeanAuprc = new CollectionMeanStdev();
        CollectionMeanStdev weightedMeanAuroc = new CollectionMeanStdev();
        CollectionMeanStdev acc = new CollectionMeanStdev();
        ToLatex parser = new ToLatex();
   
        for(int seed = 0; seed<Path.nbSeeds; seed++){//S0ndStatistics
             BufferedReader readerClus = new BufferedReader(new FileReader(path+"/"+dataset+"/asettings/S"+seed+classifier+"Statistics"));
             String line = readerClus.readLine();
             meanAuprc.addNumber(Double.parseDouble(parser.getMean(line, dataset, true)));
             meanAuroc.addNumber(Double.parseDouble(parser.getMean(line, dataset, false)));
             weightedMeanAuprc.addNumber(Double.parseDouble(parser.getWeightedMean(line, dataset, true)));
             weightedMeanAuroc.addNumber(Double.parseDouble(parser.getWeightedMean(line, dataset, false)));
             acc.addNumber(Double.parseDouble(parser.getAccuracy(line, dataset)));
        }
        stream.println(dataset+"\t\t"+"Mean auprc: "+Double.toString(meanAuprc.getMean())+"pm"+
                        Double.toString(meanAuprc.getStandardDeviation())+
                       "\t\t Mean auroc: "+Double.toString(meanAuroc.getMean())+"pm"+
                         Double.toString(meanAuroc.getStandardDeviation())+
                       "\t\t"+"Weighted mean auprc: "+Double.toString(weightedMeanAuprc.getMean())+
                        "pm"+Double.toString(weightedMeanAuprc.getStandardDeviation())+
                       "\t\t"+"Weighted mean auroc: "+Double.toString(weightedMeanAuroc.getMean())+
                        "pm"+Double.toString(weightedMeanAuroc.getStandardDeviation())+
                       "\t\t"+"accuracy: "+Double.toString(acc.getMean())+
                        "pm"+Double.toString(acc.getStandardDeviation()));
    }
    
    
    
    public static void main(String[] args) throws IOException{
        WriteAllStatistics write = new WriteAllStatistics();
        write.printStatisticsOfAllClassifiersToFile(Path.path);
        write.combineMultipleSeedsAllClassifiers(Path.path);
    }    
}
