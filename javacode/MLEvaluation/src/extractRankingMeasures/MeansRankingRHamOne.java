/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extractRankingMeasures;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import statics.Path;

/**
 *
 * @author katie
 */
public class MeansRankingRHamOne {
    
    
  
    
    public static void main(String[] args) throws FileNotFoundException, IOException{
        BufferedReader[] readers = new BufferedReader[10];
        for(int i = 0; i<10; i++){
            readers[i] = new BufferedReader(new FileReader(Path.pathPinac+"rankingKMeansOne"+i+".txt"));
        }
         PrintStream stream = new PrintStream(new File(Path.pathPinac+"rankingKMeansOneGemiddelde.txt"));
        
        for(int  dataset=0; dataset<10; dataset++){
            double oneError = 0.0;
            double coverage = 0.0;
            double rankingLoss = 0.0;
            double avgPrecision = 0.0;
            //naam dataset afdrukken
            stream.println(readers[0].readLine());
            for(int i = 1; i<10; i++){
                readers[i].readLine();
            }
            
            //optellen
            for(int i = 0; i<10; i++){
                String line = readers[i].readLine();
                String[] perRanking = line.split(" -- ");
                oneError += Double.parseDouble(perRanking[0].split(": ")[1]);
                coverage += Double.parseDouble(perRanking[1].split(": ")[1]);
                rankingLoss += Double.parseDouble(perRanking[2].split(": ")[1]);
                avgPrecision += Double.parseDouble(perRanking[3].split(": ")[1]);
            }
            String printLine =  "oneError: "+oneError/10+" -- coverage: "+coverage/10+" -- rankingLoss: "+
            rankingLoss/10+" -- avgPrecision: "+avgPrecision/10;
            stream.println(printLine);
            stream.println();
            
            for(BufferedReader r: readers){
                r.readLine();
            }
        }
    }
    
}
