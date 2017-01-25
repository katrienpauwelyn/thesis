/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NDResults;

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
 * Schrijft alle statistieken van alle classifiers, datasets en folds naar files.
 * Bekomen via nested dichotomies.
 * 1 outputfile per classifier. (path/classifierStatisticsND)
 * 
 * eerst writeAllNDResultsToFiles om per seed een resultaat te hebben, 
 * dan combineMultipleSeedsAllClassifiers om de resultaten van alle seeds te combineren.
 * 
 */
public class WriteAllStatistics {
    
    //Schrijf alle resultaten van alle classifiers naar files
    public void writeAllNDResultsToFiles() throws FileNotFoundException, IOException{
        for(String classifier: Path.classifiers){
            writeNdResultsClassifier(Path.path+"/"+classifier, classifier);
        }
    }
    
    //Schrijf de resultaten van alle datasets (en hun folds) van een classifier naar een file
    public void writeNdResultsClassifier(String pathForFile, String classifier) throws FileNotFoundException, IOException{
       
       // stream.println("Statistics of C45:");
        for(String dataset: Path.datasets){
            for(int seed = 0; seed<Path.nbSeeds; seed++){
                 PrintStream stream = new PrintStream(new File(pathForFile+"/"+dataset+"/aOutputND/S"+seed+classifier+"StatisticsND"));
                writeNdResultsFold(stream, pathForFile+"/"+dataset+"/aOutputND/NDS"+seed+"outputC45", dataset);
                 stream.close();
            }
            
        }
     /*   stream.println();
        stream.println();
        stream.println("Statistics of Logistics:");
         for(String dataset: Path.datasets){
            writeNdResultsFold(stream, pathForFile+"/"+dataset+"NDoutputLogistic", dataset);
        }*/
       
    }
    
    //Schrijf de resultaten van 10 folds samen naar een file.
    public void writeNdResultsFold(PrintStream stream, String pathFold, String dataset) throws IOException{
        TupleInt posNeg = new TupleInt();
        TupleDouble meansPrc = new TupleDouble();
        TupleDouble meansRoc = new TupleDouble();
        calculateStatisticsFold(pathFold, posNeg, meansPrc, meansRoc);
        
        stream.println(dataset + "\t"+ "Mean auprc: "+meansPrc.getMean()/10+ "\t Weighted mean auprc: "+
                meansPrc.getWeighted()/10+ "\t Mean auroc: "+meansRoc.getMean()/10 + "\t Weighted mean auroc: "+
                meansRoc.getWeighted()/10+"\t accuracy: "+posNeg.getAccuracy());
    }
    
   
    //Bereken de gemiddelden en de nauwkeurigheid voor 10 folds samen.
    private void calculateStatisticsFold(String path, TupleInt posNeg, TupleDouble meansPrc, TupleDouble meansRoc)
            throws IOException{
        ParseDataND parseData = new ParseDataND();
        for(int i = 1; i<11; i++){
            parseData.parseFold(path+Integer.toString(i), posNeg, meansPrc, meansRoc);
        }
    }
    
    
      
    public static void combineMultipleSeedsAllClassifiers(String path) throws IOException{
        for(String classifier: Path.classifiers){
            combineMultipleSeedsAllDatasets(path+"/"+classifier, classifier);
        }
    }
    
    
       //voorbeeld van een pad
    ///Users/katie/thesisoutput/out/nd
    public static void combineMultipleSeedsAllDatasets(String path, String classifier) throws FileNotFoundException, IOException{
        PrintStream stream = new PrintStream(new File(path+"/aStatisticsND.txt"));
        for(String dataset: Path.datasets){
            combineMultipleSeeds(path, dataset, stream, classifier);
        }
        stream.close();
    }
    
    //voorbeeld van een pad
    ///Users/katie/thesisoutput/out/nd
    // /audiology/aOutputND
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
   
        for(int seed = 0; seed<Path.nbSeeds; seed++){//"/aOutputND/S"+seed+classifier+"StatisticsND"
             BufferedReader readerClus = new BufferedReader(new FileReader(path+"/"+dataset+"/aOutputND/S"+seed+classifier+"StatisticsND"));
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
    
    
    
    
    
    
    public static void main(String[] args) throws FileNotFoundException, IOException{
        WriteAllStatistics write = new WriteAllStatistics();
        write.writeAllNDResultsToFiles();
       write.combineMultipleSeedsAllClassifiers(Path.path);
    }
}
