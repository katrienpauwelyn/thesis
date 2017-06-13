/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extractRankingMeasures;

import com.evaluation.RankingBasedMeasures;
import com.output.MultiLabelOutput;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import statics.Path;

/**
 *
 * @author katie
 */
public class PrintRanking {
    
    public static void printAllRanking(String outputPath) throws FileNotFoundException, IOException{
        String pathHier ;
        String pathFlat;
        PrintStream stream = new PrintStream(new File(outputPath));
        
        for(String dataset: Path.datasets){
            pathHier = Path.pathPinac+dataset+"/average.test.pred.arff";
            pathFlat = Path.pathPinac+dataset+"/flat/average.test.pred.arff";
            
            int nbInstances = ArffParser.getNbInstances(pathHier);
            int nbLabels = ArffParser.getNbLabels(pathHier);
            MultiLabelOutput[] predictionsHier = new MultiLabelOutput[nbInstances];
            MultiLabelOutput[] predictionsFlat = new MultiLabelOutput[nbInstances];
            boolean[][] actualHier = ArffParser.getActualValues(pathHier, nbLabels, nbInstances, predictionsHier);
            boolean[][] actualFlat = ArffParser.getActualValues(pathFlat, nbLabels, nbInstances, predictionsFlat);
            RankingBasedMeasures rankingHier = new RankingBasedMeasures(predictionsHier, actualHier);
            RankingBasedMeasures rankingFlat = new RankingBasedMeasures(predictionsFlat, actualFlat);
            stream.println(dataset+" Flat:");
            stream.println(getRankingMeasures(rankingFlat));
            stream.println(dataset +" Hier:");
            stream.println(getRankingMeasures(rankingHier));
            stream.println();
        }
        stream.close();
    }
    
        public static void printUnfilteredRanking(String outputPath) throws FileNotFoundException, IOException{
        String pathHier ;
        String pathFlat;
        PrintStream stream = new PrintStream(new File(outputPath));
        
        for(String dataset: Path.postAverageDatasets){
            pathHier = Path.pathPinac+dataset+"/averageUnfiltered.test.pred.arff";
            pathFlat = Path.pathPinac+dataset+"/flat/averageUnfiltered.test.pred.arff";
            
            int nbInstances = ArffParser.getNbInstances(pathHier);
            int nbLabels = ArffParser.getNbLabels(pathHier);
            MultiLabelOutput[] predictionsHier = new MultiLabelOutput[nbInstances];
            MultiLabelOutput[] predictionsFlat = new MultiLabelOutput[nbInstances];
            boolean[][] actualHier = ArffParser.getActualValues(pathHier, nbLabels, nbInstances, predictionsHier);
            boolean[][] actualFlat = ArffParser.getActualValues(pathFlat, nbLabels, nbInstances, predictionsFlat);
            RankingBasedMeasures rankingHier = new RankingBasedMeasures(predictionsHier, actualHier);
            RankingBasedMeasures rankingFlat = new RankingBasedMeasures(predictionsFlat, actualFlat);
            stream.println(dataset+" Flat:");
            stream.println(getRankingMeasures(rankingFlat));
            stream.println(dataset +" Hier:");
            stream.println(getRankingMeasures(rankingHier));
            stream.println();
        }
        stream.close();
    }
    
    public static void printOne(String pathHier, String pathFlat, String outputPath) throws FileNotFoundException, IOException{
          PrintStream stream = new PrintStream(new File(outputPath));
        int nbInstances = ArffParser.getNbInstances(pathHier);
            int nbLabels = ArffParser.getNbLabels(pathHier);
            MultiLabelOutput[] predictionsHier = new MultiLabelOutput[nbInstances];
            MultiLabelOutput[] predictionsFlat = new MultiLabelOutput[nbInstances];
            boolean[][] actualHier = ArffParser.getActualValues(pathHier, nbLabels, nbInstances, predictionsHier);
            boolean[][] actualFlat = ArffParser.getActualValues(pathFlat, nbLabels, nbInstances, predictionsFlat);
            RankingBasedMeasures rankingHier = new RankingBasedMeasures(predictionsHier, actualHier);
            RankingBasedMeasures rankingFlat = new RankingBasedMeasures(predictionsFlat, actualFlat);
            stream.println(" Flat:");
            stream.println(getRankingMeasures(rankingFlat));
            stream.println("Hier:");
            stream.println(getRankingMeasures(rankingHier));
            stream.println();
             stream.close();
    }

    private static String getRankingMeasures(RankingBasedMeasures ranking) {
      return "oneError: "+ranking.getOneError()+" -- coverage: "+ranking.getCoverage()+" -- rankingLoss: "+
               ranking.getRankingLoss()+" -- avgPrecision: "+ranking.getAvgPrecision();
    }
    
    public static void main(String[] args) throws IOException{
        String output = Path.pathPinac+"outputRanking2.txt";
        String pathOne = "/Users/katie/Desktop/temp/averageUnfiltered.test.pred.arff";
        String pathTo = "/Users/katie/Desktop/temp/one.txt";
        
        String input = "/Users/katie/Downloads/average.test.pred.arff";
        String output2 = "/Users/katie/Downloads/aaa.txt";
       // printAllRanking(output);
        printOne(input, input, output2);
        //printUnfilteredRanking(output);
    }
    
}
