/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toLatex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import staticData.Path;


//DEPRECATED

/**
 *Converteer de resultaten die in files geschreven zijn naar output formaat dat in latex kan geïmporteerd worden.
 * @author katie
 * 
 * Getest op furthestcentroid
 */
public class ConvertToLatexFormat {
    
    
    public void convertAllToLatex() throws IOException{
        for(String classifier: Path.classifiers){
            convertOneClassifierToLatex(Path.path+"/"+classifier, classifier);
        }
    }
    
    // header: accuracy/mean / weighted mean & classifier C4.5 & classifier Logistic & Clus
    
    //path = bv. /Users/katie/NetBeansProjects/weka/trunk/packages/internal/ensemblesOfNestedDichotomies/out/classBalanced
    public void convertOneClassifierToLatex(String path, String classifier) throws FileNotFoundException, IOException{
        PrintStream stream = new PrintStream(new File(path+"/"+classifier+"latex"));
        printMean(path, classifier, stream);
        stream.println();
        stream.println();
        printWeightedMean(path, classifier, stream);
        stream.println();
        stream.println();
        printAccuracy(path, classifier, stream);
    }
    
    //clus: direct data
    //nd: Statistics of C45:
    //Statistics of Logistics:
    
    //names: classBalancedStatistics and classBalancedStatisticsND
    public void printMean(String path, String classifier, PrintStream stream) throws FileNotFoundException, IOException{
        BufferedReader readerClus = new BufferedReader(new FileReader(path+"/"+classifier+"Statistics"));
        BufferedReader readerC45 = new BufferedReader(new FileReader(path+"/"+classifier+"StatisticsND"));
         BufferedReader readerLogistics = new BufferedReader(new FileReader(path+"/"+classifier+"StatisticsND"));
       
         String lineC45;
   
        while(!readerC45.readLine().contains("Statistics of C45:")){ }
        while(!readerLogistics.readLine().contains("Statistics of Logistics:")){ }
 
        printBeginTabular(stream, classifier, "het gemiddelde van de AUPRC");
        stream.println(" Gemiddelde auprc & "+ classifier+" C4.5 & "+classifier+" Logistic & Clus"+"\\\\ \\hline\\hline");
               
        while(!(lineC45 = readerC45.readLine()).isEmpty()){
            String dataset = getDataset(lineC45);
            String printString = dataset + " & " + getMean(lineC45, dataset)+" & "+
                    getMean(readerLogistics.readLine(), dataset)+" & "+
                    getMean(readerClus.readLine(), dataset)+" \\\\ \\hline";
            stream.println(printString);
        }
        printEndTabular(stream);
    }
    
    public void printWeightedMean(String path, String classifier, PrintStream stream) throws FileNotFoundException, IOException{
        BufferedReader readerClus = new BufferedReader(new FileReader(path+"/"+classifier+"Statistics"));
        BufferedReader readerC45 = new BufferedReader(new FileReader(path+"/"+classifier+"StatisticsND"));
         BufferedReader readerLogistics = new BufferedReader(new FileReader(path+"/"+classifier+"StatisticsND"));
         
        String lineC45;
        while(!readerC45.readLine().contains("Statistics of C45:")){ }
        while(!readerLogistics.readLine().contains("Statistics of Logistics:")){}
      
        printBeginTabular(stream, classifier, "het gewogen gemiddelde van de AUPRC");
        stream.println(" Gewogen gemiddelde auprc & "+ classifier+" C4.5 & "+classifier+" Logistic & Clus"+"\\\\ \\hline\\hline");
               
        
        while(!(lineC45 = readerC45.readLine()).isEmpty()){
            String dataset = getDataset(lineC45);
            String printString = dataset + " & " + getWeightedMean(lineC45, dataset)+" & "+
                    getWeightedMean(readerLogistics.readLine(), dataset)+" & "+
                    getWeightedMean(readerClus.readLine(), dataset)+" \\\\ \\hline";
            stream.println(printString);
        }
        printEndTabular(stream);
    }
    
    public void printAccuracy(String path, String classifier, PrintStream stream) throws FileNotFoundException, IOException{
        BufferedReader readerClus = new BufferedReader(new FileReader(path+"/"+classifier+"Statistics"));
        BufferedReader readerC45 = new BufferedReader(new FileReader(path+"/"+classifier+"StatisticsND"));
        BufferedReader readerLogistics = new BufferedReader(new FileReader(path+"/"+classifier+"StatisticsND"));
      
        String lineC45;
        while(!readerC45.readLine().contains("Statistics of C45:")){ }
        while(!readerLogistics.readLine().contains("Statistics of Logistics:")){}
        
        printBeginTabular(stream, classifier, "de nauwkeurigheid");
        stream.println(" Nauwkeurigheid & "+ classifier+" C4.5 & "+classifier+" Logistic & Clus"+"\\\\ \\hline\\hline");
               
        while(!(lineC45 = readerC45.readLine()).isEmpty()){
            String dataset = getDataset(lineC45);
            String printString = dataset + " & " + getAccuracy(lineC45, dataset)+" & "+
                    getAccuracy(readerLogistics.readLine(), dataset)+" & "+
                    getAccuracy(readerClus.readLine(), dataset)+" \\\\ \\hline";
            stream.println(printString);
        }
        printEndTabular(stream);
    }

    public String getMean(String line, String dataset){
        if(!line.contains(dataset)){
            throw new Error(line +" is niet van dataset "+dataset);
        }
        String[] splitTab = line.split("\t");
        for(String s: splitTab){
            if(s.contains("Mean auprc:")){
               String[] splitSpace = s.split(" ");
               return splitSpace[splitSpace.length-1];
            }
        }
        throw new Error("getMean is niet gelukt voor "+line);
    }
    
    public String getWeightedMean(String line, String dataset){
        if(!line.contains(dataset)){
            throw new Error(line +" is niet van dataset "+dataset);
        }
                String[] splitTab = line.split("\t");
        for(String s: splitTab){
            if(s.contains("Weighted mean auprc:")){
               String[] splitSpace = s.split(" ");
               return splitSpace[splitSpace.length-1];
            }
        }
        throw new Error("getWeightedMean is niet gelukt voor "+line);
        
    }
    
    public String getAccuracy(String line, String dataset){
        if(!line.contains(dataset)){
            throw new Error(line +" is niet van dataset "+dataset);
        }
                String[] splitTab = line.split("\t");
        for(String s: splitTab){
            if(s.contains("accuracy:")){
               String[] splitSpace = s.split(" ");
               return splitSpace[splitSpace.length-1];
            }
        }
        throw new Error("getAccuracy is niet gelukt voor "+line);
        
    }
    
    public String getDataset(String line){
        String[] splitTab = line.split("\t");
        return splitTab[0];
    }

    public void printBeginTabular(PrintStream stream, String classifier, String wma){
        stream.println("\\begin {table}[H]");
        String caption = "Vergelijking van " + wma +" tussen Clus op basis van een hi\\\"erarchie "
                + "gemaakt door "+classifier+" en de resultaten bekomen door "+classifier+".";
        stream.println("\\caption {"+caption+"} \\label{tab:"+wma+classifier+"} ");
        stream.println(" \\begin{center}");
        stream.println("\\begin{tabular}{| l || l | l | l |}");
        stream.println("\\hline");
    }
    
    public void printEndTabular(PrintStream stream){
        stream.println("    \\end{tabular}");
        stream.println("\\end{center}");
        stream.println("\\end{table}");
    }
    
    public static void main(String[] args) throws IOException{
        ConvertToLatexFormat c = new ConvertToLatexFormat();
        c.convertAllToLatex();
    }
    
    
    
}
