/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Irrelevant;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 *
 * @author katie
 */
public class CsvParser {

    public static void parseEnsemble() throws FileNotFoundException {
        String path = "/Users/katie/Dropbox/thesis/archief/presentatie3/csv/";
        //oneError
        print(EnsembleData.oneErrorFlat, EnsembleData.oneErrorHier, EnsembleData.oneErrorKMeans, EnsembleData.oneErrorKMeansDiffHiers,
               new PrintStream(new File(path+"oneError.csv")) );
     
        //coverage
         print(EnsembleData.coverageFlat, EnsembleData.coverageHier, EnsembleData.coverageKMeans, EnsembleData.coverageKMeansDiffHiers,
               new PrintStream(new File(path+"coverage.csv")) );
         
     //rankingloss
       print(EnsembleData.rankingLossFlat, EnsembleData.rankingLossHier, EnsembleData.rankingLossKMeans, EnsembleData.rankingLossKMeansDiffHiers,
               new PrintStream(new File(path+"rankingLoss.csv")) );
       
       //microPR
         print(EnsembleData.microPRFlat, EnsembleData.microPRHier, EnsembleData.microPRKMeans, EnsembleData.microPRKMeansDiffHiers,
               new PrintStream(new File(path+"microPR.csv")) );
       
         //macroPR
           print(EnsembleData.macroPRFlat, EnsembleData.macroPRHier, EnsembleData.macroPRKMeans, EnsembleData.macroPRKMeansDiffHiers,
               new PrintStream(new File(path+"macroPR.csv")) );
           
           //microROC 
             print(EnsembleData.microROCFlat, EnsembleData.microROCHier, EnsembleData.microROCKMeans, EnsembleData.microROCKMeansDiffHiers,
               new PrintStream(new File(path+"microROC.csv")) );
       //macroROC
               print(EnsembleData.macroROCFlat, EnsembleData.macroROCHier, EnsembleData.macroROCKMeans, EnsembleData.macroROCKMeansDiffHiers,
               new PrintStream(new File(path+"macroROC.csv")) );
    }
    
    
    public static void parseOne() throws FileNotFoundException{
         String path = "/Users/katie/Dropbox/thesis/archief/presentatie3/csv/";
        //oneError
        print(OneData.oneErrorFlatOne, OneData.oneErrorRHamOne, OneData.oneErrorKMeansOne,
               new PrintStream(new File(path+"oneErrorOne.csv")) );
     
        //coverage
         print(OneData.coverageFlatOne, OneData.coverageRHamOne, OneData.coverageKMeansOne, 
               new PrintStream(new File(path+"coverageOne.csv")) );
         
     //rankingloss
       print(OneData.rankingLossFlatOne, OneData.rankingLossRHamOne, OneData.rankingLossKMeansOne, 
               new PrintStream(new File(path+"rankingLossOne.csv")) );
       
       //microPR
         print(OneData.microPrFlatOne, OneData.microPrRHamOne, OneData.microPrKMeansOne,
               new PrintStream(new File(path+"microPROne.csv")) );
       
         //macroPR
           print(OneData.macroPrFlatOne, OneData.macroPrRHamOne, OneData.macroPrKMeansOne, 
               new PrintStream(new File(path+"macroPROne.csv")) );
           
           //microROC 
             print(OneData.microRocFlatOne, OneData.microRocRHamOne, OneData.microRocKMeansOne,
               new PrintStream(new File(path+"microROCOne.csv")) );
       //macroROC
               print(OneData.macroRocFlatOne, OneData.macroRocRHamOne, OneData.macroRocKMeansOne, 
               new PrintStream(new File(path+"macroROCOne.csv")) );
        
    }

    public static void print(double[] flat, double[] hier, double[] kmeans, double[] kmeansDiffHiers, PrintStream stream){
                stream.println("Flat; RHamDiffHiers; KMeans; KMeansDiffHiers");
        for(int i = 0; i<9; i++){
            String print = "";
            print+=Double.toString(flat[i]).replace(".", ",")+";";
            print+=Double.toString(hier[i]).replace(".", ",")+";";
            print+=Double.toString(kmeans[i]).replace(".", ",")+";";
            print+=Double.toString(kmeansDiffHiers[i]).replace(".", ",");
            stream.println(print);
        }
        stream.close();
        
    }

        public static void print(double[] flat, double[] hier, double[] kmeans, PrintStream stream){
                stream.println("FlatOne; RHamOne; KMeansOne");
        for(int i = 0; i<9; i++){
            String print = "";
            print+=Double.toString(flat[i]).replace(".", ",")+";";
            print+=Double.toString(hier[i]).replace(".", ",")+";";
            print+=Double.toString(kmeans[i]).replace(".", ",");
            stream.println(print);
        }
        stream.close();
        
    }
        
        public static void main(String[] args) throws FileNotFoundException{
            parseEnsemble();
            parseOne();
        }
}
