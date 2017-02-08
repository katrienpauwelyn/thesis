/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package time;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import staticData.Path;

/**
 *
 * @author katie
 */
public class CalculateTime {
    
    
    //maak een file per classifier aan die het gemiddelde en standaarddeviatie van alle tijden berekent
    public static void printTimeAllClassifiers() throws FileNotFoundException, IOException{
        for(String classifier: Path.classifiers){
            PrintStream streamCLUS = new PrintStream(new File(Path.path+"/"+classifier+"/"+"aTimeCLUS.txt"));
            PrintStream streamND = new PrintStream(new File(Path.path+"/"+classifier+"/"+"aTimeND.txt"));
            printTimeAllDatasets(Path.path+"/"+classifier, streamCLUS, "asettings/CLUSaTime.txt");
            printTimeAllDatasets(Path.path+"/"+classifier, streamND, "aOutputND/NDaTime.txt");
        }
    }

    //pathAfterDataset: aOutputND/NDaTime.txt voor nd
    //asettings/CLUSaTime.txt voor CLUS
    private static void printTimeAllDatasets(String path, PrintStream stream, String pathAfterDataset) throws FileNotFoundException, IOException {
        for(String dataset: Path.datasets){
            int nbTimes = 0;
            int totalTime = 0;
            BufferedReader reader = new BufferedReader(new FileReader(path+"/"+dataset+"/"+pathAfterDataset));
            String line;
            while((line = reader.readLine())!=null){
                nbTimes++;
                totalTime+=Integer.parseInt(line);
            }
             stream.println(dataset+" "+Double.toString(totalTime/nbTimes));
        }
       
    }
    
    //transformeert de twee aparte tijds files (clus en nd) tot 1 file per classifier in latex formaat
    public static void toLatex() throws FileNotFoundException, IOException{
        for(String classifier: Path.classifiers){
            PrintStream streamLatex = new PrintStream(new File(Path.path+"/"+classifier+"/"+"alatexTime.txt"));
            printBeginTabular(streamLatex, classifier);
            toLatexAllDatasets(streamLatex, Path.path+"/"+classifier);
            printEndTabular(streamLatex);
        }
    }
    
    private static void toLatexAllDatasets(PrintStream printStream, String path) throws FileNotFoundException, IOException{
        BufferedReader readerClus = new BufferedReader(new FileReader(path+"/aTimeCLUS.txt"));
        BufferedReader readerND = new BufferedReader(new FileReader(path+"/aTimeND.txt"));
        String lineClus;
        String lineND;
        while((lineClus=readerClus.readLine())!=null){
            lineND = readerND.readLine();
            String[] splitClus = lineClus.split(" ");
            String[] splitND = lineND.split(" ");
            if(Double.parseDouble(splitND[1])<Double.parseDouble(splitClus[1])){
                printStream.println(splitClus[0]+" & "+ splitND[1] +"$\\bigodot$ & "+splitClus[1] + "\\\\ \\hline");
            } else if(Double.parseDouble(splitND[1])>Double.parseDouble(splitClus[1])){
                printStream.println(splitClus[0]+" & "+ splitND[1]+" & "+splitClus[1] + "$\\bigodot$ \\\\ \\hline");
            } else {
                printStream.println(splitClus[0]+" & "+ splitND[1]+" & "+splitClus[1] + "\\hline");
            }
            
        }
        
    }
    
    
        private static void printBeginTabular(PrintStream stream, String classifier){
        stream.println("\\begin {table}[H]");
        String caption = "Vergelijking van de looptijd tussen Clus op basis van een hi\\\"erarchie "
                + "gemaakt door "+classifier+" en de resultaten bekomen door "+classifier+".";
        stream.println("\\caption {"+caption+"} \\label{tab:tijd"+classifier+"} ");
        stream.println(" \\begin{center}");
        stream.println("\\begin{tabular}{| l || l | l |}");
        stream.println("\\hline");
        stream.println(" Tijd (ms) & "+ classifier+" C4.5 &  Clus"+"\\\\ \\hline\\hline");
    }
    
    private static void printEndTabular(PrintStream stream){
        stream.println("    \\end{tabular}");
        stream.println("\\end{center}");
        stream.println("\\end{table}");
    }
    
    
    public static void main(String[] args) throws IOException{
       // CalculateTime.printTimeAllClassifiers();
        CalculateTime.toLatex();
    }
    
}
