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
 * print de ranking based measures af. (one error, coverage, ... en ...)
 * om te switchen tussen ordinal en competitive ranking moet zowel in RankingBasedMeasures code aangepast worden
 * als in MultiLabelOutput (staat in commentaar)
 */
public class PrintRanking {
    
    public static void printAllRanking(String outputPathKMeansDiffHiers)//String outputPathFlat, String outputPathKMeansDiffHiers, String outputPathRHam )
            throws FileNotFoundException, IOException{
      //  String pathRHam ;
      //  String pathFlat;
        String pathKMeansDiffHiers;
     //   PrintStream streamFlat = new PrintStream(new File(outputPathFlat));
        PrintStream streamKMeansDiffHiers = new PrintStream(new File(outputPathKMeansDiffHiers));
     //   PrintStream streamRHam = new PrintStream(new File(outputPathRHam));
        
        
        for(String dataset: Path.datasets){
            pathKMeansDiffHiers = Path.pathPinac+dataset+"/kmeans/averageKMeansFull.test.pred.arff";
          //  pathFlat = Path.pathPinac+dataset+"/flat/average.test.pred.arff";
          //  pathRHam = Path.pathPinac+dataset+"/averageRHam.test.pred.arff";
            
            int nbInstances = ArffParser.getNbInstances(pathKMeansDiffHiers);
            int nbLabels = ArffParser.getNbLabels(pathKMeansDiffHiers);
          /*  MultiLabelOutput[] predictionsRHam = new MultiLabelOutput[nbInstances];
            MultiLabelOutput[] predictionsFlat = new MultiLabelOutput[nbInstances];*/
            MultiLabelOutput[] predictionsKMeansDiffHiers = new MultiLabelOutput[nbInstances];
         
          //  boolean[][] actualRHam = ArffParser.getActualValues(pathRHam, nbLabels, nbInstances, predictionsRHam);
          //  boolean[][] actualFlat = ArffParser.getActualValues(pathFlat, nbLabels, nbInstances, predictionsFlat);
            boolean[][] actualKMeansDiffHiers = ArffParser.getActualValues(pathKMeansDiffHiers, nbLabels, nbInstances, predictionsKMeansDiffHiers);
            
          /*  RankingBasedMeasures rankingRHam = new RankingBasedMeasures(predictionsRHam, actualRHam);
            RankingBasedMeasures rankingFlat = new RankingBasedMeasures(predictionsFlat, actualFlat);*/
            RankingBasedMeasures rankingKMeansDiffHiers = new RankingBasedMeasures(predictionsKMeansDiffHiers, actualKMeansDiffHiers);
            
           /* streamFlat.println(dataset+" Flat:");
            streamFlat.println(getRankingMeasures(rankingFlat));
            streamFlat.println();*/
            streamKMeansDiffHiers.println(dataset +" kmeansDiffHiers:");
            streamKMeansDiffHiers.println(getRankingMeasures(rankingKMeansDiffHiers));
            streamKMeansDiffHiers.println();
          /*  streamRHam.println(dataset+" RHam:");
            streamRHam.println(getRankingMeasures(rankingRHam));*/
        }
      //  streamFlat.close();
        streamKMeansDiffHiers.close();
        //streamRHam.close();
    }
    
     
      /*  public static void printUnfilteredRanking(String outputPath) throws FileNotFoundException, IOException{
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
    }*/

    private static String getRankingMeasures(RankingBasedMeasures ranking) {
      return "oneError: "+ranking.getOneError()+" -- coverage: "+ranking.getCoverage()+" -- rankingLoss: "+
               ranking.getRankingLoss()+" -- avgPrecision: "+ranking.getAvgPrecision();
    }
    
    public static void main(String[] args) throws IOException{
      //  String outputFlat = Path.pathPinac+"outputRankingFlat.txt";
      //  String outputRHam = Path.pathPinac+"outputRankingRHam.txt";
        String outputKMeansDiffHiers = Path.pathPinac+"outputRankingKMeansFull.txt";
                
      //   String    pathHier = "/Users/katie/Desktop/temp/averageFlatOne.test.pred.arff";
      //    String    pathHier2 = "/Users/katie/Desktop/temp/average.test.pred.arff";  
      
        printAllRanking(outputKMeansDiffHiers);
       // printOne(input, input, output2);
        //printUnfilteredRanking(output);
    }
    
}
