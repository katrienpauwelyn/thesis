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
public class PrintRankingOne {
    
    //OPGEPAST!!! eerst ordinal / competition ranking aanpassen in multilabeloutput en rankingbasedmeasures
    
    public static void printAllRanking() throws FileNotFoundException, IOException{
        
        String pathFlat;
        String[] pathKMeans;
        String[] pathRHam;
        PrintStream streamFlat = new PrintStream(new File(Path.pathPinac+"rankingFlatOneOrdinal.txt"));
        PrintStream[] streamKMeans = new PrintStream[10];
        for(int i = 0; i<10; i++){
            streamKMeans[i] = new PrintStream(new File(Path.pathPinac+"rankingKMeansOne"+i+".txt"));
        }
        PrintStream[] streamRHam = new PrintStream[10];
        for(int i = 0; i<10; i++){
            streamRHam[i] = new PrintStream(new File(Path.pathPinac+"rankingRHamOne"+i+"Ordinal.txt"));
        }
        
        for(String dataset: Path.datasets){
            pathFlat = Path.pathPinac+dataset+"/one/averageFlatOne.test.pred.arff";
            pathKMeans = new String[10];
            for(int i =0; i<10; i++){
               pathKMeans[i] =  Path.pathPinac+dataset+"/one/averageKMeansOne"+i+".test.pred.arff";
            }
            pathRHam = new String[10];
            for(int i = 0; i<10; i++){
                pathRHam[i] = Path.pathPinac+dataset+"/one/averageRHamOne"+i+".test.pred.arff";
            }
        
            printAll(streamFlat, pathFlat, dataset, "FlatOne");
          for(int i=0; i<10; i++){
              printAll(streamKMeans[i], pathKMeans[i], dataset, "KMeansOne"+i);
          }
            
            for(int i = 0; i<10; i++){
                printAll(streamRHam[i], pathRHam[i], dataset, "RHamOne"+i);
            }
         
        }
       // streamFlat.close();
       for(PrintStream stream: streamKMeans){
           stream.close();
       }
        
     /*   for(PrintStream stream: streamRHam){
            stream.close();
        }*/
    }
    
    public static void printAll(PrintStream stream, String inputFile, String dataset, String classifier) throws IOException{
             int nbInstances = ArffParser.getNbInstances(inputFile);
            int nbLabels = ArffParser.getNbLabels(inputFile);
            MultiLabelOutput[] predictionsHier = new MultiLabelOutput[nbInstances];
            boolean[][] actualHier = ArffParser.getActualValues(inputFile, nbLabels, nbInstances, predictionsHier);
            RankingBasedMeasures rankingHier = new RankingBasedMeasures(predictionsHier, actualHier);
             stream.println(dataset + " "+classifier);
            stream.println(getRankingMeasures(rankingHier));
            stream.println();
    }
  
 
    private static String getRankingMeasures(RankingBasedMeasures ranking) {
      return "oneError: "+ranking.getOneError()+" -- coverage: "+ranking.getCoverage()+" -- rankingLoss: "+
               ranking.getRankingLoss()+" -- avgPrecision: "+ranking.getAvgPrecision();
    }
    
    public static void main(String[] args) throws IOException{
        printAllRanking();
    
    }
    
}
