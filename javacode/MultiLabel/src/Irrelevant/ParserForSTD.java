/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Irrelevant;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

/**
 *
 * @author katie
 */
public class ParserForSTD {
    
    //parse ranking
    public static void parseRanking(String path) throws FileNotFoundException, IOException{
        BufferedReader[] readers = new BufferedReader[10];
        PrintStream streamOneError = new PrintStream(new File(path+"oneErrorKMeans.txt"));//TODO
        PrintStream streamCoverage = new PrintStream(new File(path+"coverageKMeans.txt"));
        PrintStream streamRankingLoss = new PrintStream(new File(path+"rankingLossKMeans.txt"));
        
        for(int i = 0; i<10; i++){
            String totalPath = path+"rankingKMeansOne"+i+".txt";
            readers[i] = new BufferedReader(new FileReader(totalPath));
        }
        
        for(int dataset = 0; dataset<10; dataset++){
            String printOneError = "";
            String printCoverage = "";
            String printRankingLoss = "";
            for(int r = 0; r<10; r++){
                String dat = readers[r].readLine().split(" ")[0];
                if(r==0){
                    printOneError = dat+"<-c(";
                    printCoverage = dat+"<-c(";
                    printRankingLoss = dat+"<-c(";
                }
                String[] data = readers[r].readLine().split(" -- ");
                printOneError += data[0].split(" ")[1]+", ";
                printCoverage += data[1].split(" ")[1]+", ";
                printRankingLoss += data[2].split(" ")[1]+", ";
                readers[r].readLine();
            }
            streamOneError.println(printOneError.substring(0,printOneError.length()-2)+")");
            streamCoverage.println(printCoverage.substring(0,printCoverage.length()-2)+")");
            streamRankingLoss.println(printRankingLoss.substring(0,printRankingLoss.length()-2)+")");
        }
        
    }
    
    
    public static void parseAU(String path) throws FileNotFoundException, IOException{
        //
         BufferedReader[] readers = new BufferedReader[10];
        PrintStream streamMicroROC = new PrintStream(new File(path+"microROCKmeans.txt"));//TODO
        PrintStream streamMacroROC = new PrintStream(new File(path+"macroROCKmeans.txt"));
        PrintStream streamMicroPR = new PrintStream(new File(path+"microPRKmeans.txt"));
        PrintStream streamMacroPR = new PrintStream(new File(path+"macroPRKmeans.txt"));
       
         for(int i = 0; i<10; i++){
            String totalPath = path+"aucsKMeansOne"+i+".txt";
            readers[i] = new BufferedReader(new FileReader(totalPath));
            readers[i].readLine();
        }
         
           for(int dataset = 0; dataset<10; dataset++){
                  String microroc = "";
                 String macroroc = "";
                 String micropr = "";
                 String macropr = "";
                 for(int i = 0; i<10; i++){
                     String l = readers[i].readLine();
                   //  System.out.println(l);
                   String[] line = l.split("Micro");
                   
                    if(i==0){
                    microroc = line[0]+"<-c(";
                    macroroc = line[0]+"<-c(";
                    micropr = line[0]+"<-c(";
                    macropr = line[0]+"<-c(";
                }//: (PR-0.33018684881591576) (ROC-0.8086863216655549)  Macro: (MeanPR-0.23136970435689133) (MeanROC-0.7663623916594432)
                String[] data = line[1].split("-");
               
                micropr += data[1].split("\\)")[0]+", ";
                microroc += data[2].split("\\)")[0]+", ";
                 macropr+= data[3].split("\\)")[0]+", ";
                 macroroc+= data[4].split("\\)")[0]+", ";
                 }
               /*  System.out.println(microroc);
                 System.out.println(micropr);
                 System.out.println(macroroc);
                 System.out.println(macropr);
                 System.out.println();*/
                  streamMicroROC.println(microroc.substring(0,microroc.length()-2)+")");
                  streamMacroROC.println(macroroc.substring(0,macroroc.length()-2)+")");
                  streamMicroPR.println(micropr.substring(0,micropr.length()-2)+")");
                  streamMacroPR.println(macropr.substring(0,macropr.length()-2)+")");
                  
           }
        
    }
    
    public static void main(String[] args) throws IOException{
        parseAU("/Users/katie/Dropbox/thesis/outputPinac/nieuweDatasets/ones/");
    }
    
}
