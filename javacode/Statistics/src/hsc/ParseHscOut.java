/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hsc;

import ClusResults.AUClus;
import ClusResults.AccuracyClus;
import ClusResults.TupleInt;
import dataTypes.TupleFloat;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import staticData.Path;
import statistics.CollectionMeanStdev;

/**
 *
 * @author katie
 */
public class ParseHscOut {
    /**
     * nog post processing stap nodig: niet alle datasets zitten in de output file.
     * Er handmatig bijvoegen vooraleer ToLatex toe te passen
     * @param args 
     */

    //per classifier output file maken (en dus stream nodig)
    public void parseAllHsc() throws IOException{
        AUClus c = new AUClus();
        AccuracyClus acc = new AccuracyClus();
        String[] base = {"classBalanced", "furthestCentroid"};
        for(String classifier: base){
            PrintStream stream = new PrintStream(new File(Path.path+"/"+classifier+"/aHsc.txt"));
            forAllDatasets(Path.path+"/"+classifier, c, acc, stream, Path.datasets);
            stream.close();
        }
             String classifier = "nd";
             PrintStream stream = new PrintStream(new File(Path.path+"/"+classifier+"/aHsc.txt"));
            forAllDatasets(Path.path+"/"+classifier, c, acc, stream, Path.restrictedND);
            stream.close();
            
            classifier = "randomPair";
            stream = new PrintStream(new File(Path.path+"/"+classifier+"/aHsc.txt"));
            forAllDatasets(Path.path+"/"+classifier, c, acc, stream, Path.restrictedRandomPair);
            stream.close();
    }
    
    public void forAllDatasets(String path, AUClus auclus, AccuracyClus acc, 
            PrintStream stream, String[] datasets) throws IOException{
        for(String dataset: datasets){
            forAllSeeds(path+"/"+dataset, auclus, acc, stream, dataset);
        }
    }
    
    //berekent voor één dataset (alle seeds en alle folds) de auroc, auprc en acc
    public void forAllSeeds(String path, AUClus auclus, AccuracyClus accuracyClus,
            PrintStream stream, String dataset) throws IOException{
        TupleFloat weightedAllSeeds;
        TupleFloat meanAllSeeds;
        CollectionMeanStdev meanAuprc = new CollectionMeanStdev();
        CollectionMeanStdev meanAuroc = new CollectionMeanStdev();
        CollectionMeanStdev weightedMeanAuprc = new CollectionMeanStdev();
        CollectionMeanStdev weightedMeanAuroc = new CollectionMeanStdev();
        CollectionMeanStdev acc = new CollectionMeanStdev();
        
        boolean hsc = (dataset.equals("letterRecognition") || dataset.equals("segmentation"));
            
        
        for(int seed = 0; seed<Path.nbSeeds; seed++){
            
           weightedAllSeeds = getWeightedAUForAllFolds(path, seed, auclus, hsc); //first is prc, second is roc
           meanAllSeeds = getMeanAUForAllFolds(path, seed, auclus, hsc);
           meanAuprc.addNumber(meanAllSeeds.getFirst());
           meanAuroc.addNumber(meanAllSeeds.getSecond());
           weightedMeanAuprc.addNumber(weightedAllSeeds.getFirst());
           weightedMeanAuroc.addNumber(weightedAllSeeds.getSecond());
           acc.addNumber(getAccuracy(path, seed, accuracyClus, hsc));
        }
        stream.println(dataset+"\t Mean auprc: "+meanAuprc.getMean()+"pm"+meanAuprc.getStandardDeviation()+
                "\t Mean auroc: "+meanAuroc.getMean()+"pm"+meanAuroc.getStandardDeviation()+
                "\t Weighted mean auprc: "+weightedMeanAuprc.getMean()+"pm"+weightedMeanAuprc.getStandardDeviation()+
                 "\t Weighted mean auroc: "+weightedMeanAuroc.getMean()+"pm"+weightedMeanAuroc.getStandardDeviation()+
                "\t accuracy: "+acc.getMean()+"pm"+acc.getStandardDeviation());
    }
    
    //returnt de accuracy voor een bepaalde seed (voor alle 10 folds gecombineerd)
        public double getAccuracy(String path, int seed, AccuracyClus acc, boolean hsc) throws IOException{
        TupleInt posNeg = acc.getNbPosNegAllFoldsWithHsc(path, seed, ".hsc.combined.test.pred.arff", hsc );
        return posNeg.getAccuracy();//de accuracy van een seed
    }
 
    
        //returnt het gemiddelde over alle folds van de gewogen gemiddelde auprc & auroc
    //per dataset (path is tot in de dataset)
        //hsc is true if there must be a 'hsc' before S0settingsFold...
    public TupleFloat getWeightedAUForAllFolds(String path, int seed, AUClus auclus, boolean hsc) throws IOException{
        TupleFloat[] allWeightedAUs = new TupleFloat[Path.nbFolds];
        String newPath ;
        for(int i = 0; i<Path.nbFolds; i++){
            //S9settingsFold10.hsc.combined.out
            if(hsc){
                 newPath = path+"/asettings/hscS"+seed+"settingsFold"+Integer.toString(i+1)+".hsc.combined.out";
            } else {
                newPath = path+"/asettings/S"+seed+"settingsFold"+Integer.toString(i+1)+".hsc.combined.out";
            }
            
            allWeightedAUs[i] = auclus.getWeightedAUPRCandAUROC(newPath);
        }
        return auclus.getMeanOfArray(allWeightedAUs);
    }

    //returnt het gemiddelde over alle folds van de gemiddelde auprc & auroc
    //per dataset (path is tot in de dataset)
    public TupleFloat getMeanAUForAllFolds(String path, int seed, AUClus auclus, boolean hsc) throws IOException{
        TupleFloat[] allMeanAUPRCs = new TupleFloat[Path.nbFolds];
        String newPath ;
        for(int i = 0; i<Path.nbFolds; i++){
              if(hsc){
                 newPath = path+"/asettings/hscS"+seed+"settingsFold"+Integer.toString(i+1)+".hsc.combined.out";
            } else {
                newPath = path+"/asettings/S"+seed+"settingsFold"+Integer.toString(i+1)+".hsc.combined.out";
            }
            allMeanAUPRCs[i] = auclus.getAUPRCandAUROC(newPath);
        }
          return auclus.getMeanOfArray(allMeanAUPRCs);
    }
    

    
    public static void main(String[] args) throws IOException{
        ParseHscOut parse = new ParseHscOut();
        parse.parseAllHsc();
    }
/**
 * S0settingsFold1.hsc.combined.test.pred.arff
 * hscS0settingsFold1.hsc.combined.test.pred.arff
 */
    
}
