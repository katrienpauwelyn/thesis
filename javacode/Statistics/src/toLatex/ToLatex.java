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

/**
 *Converteer de resultaten die in files geschreven zijn naar output formaat dat in latex kan geïmporteerd worden.
 * TODO tijd incorporeren
 * @author katie
 * 
 * Getest op furthestcentroid
 */
public class ToLatex {
    
    
    public void convertAllToLatex() throws IOException{
        for(String classifier: Path.classifiers){
            convertOneClassifierToLatex(Path.path+"/"+classifier, classifier);
        }
    }
    
    // header: accuracy/mean / weighted mean & classifier C4.5 & classifier Logistic & Clus
    
    //path = bv. /Users/katie/NetBeansProjects/weka/trunk/packages/internal/ensemblesOfNestedDichotomies/out/classBalanced
    public void convertOneClassifierToLatex(String path, String classifier) throws FileNotFoundException, IOException{
        PrintStream stream = new PrintStream(new File(path+"/"+classifier+"latex"));
       printAuprc(path, classifier, stream);
        stream.println();
        stream.println();
        printAuroc(path, classifier, stream);
        stream.println();
        stream.println();
        printAccuracy(path, classifier, stream);
    }
    
    public void printAuprc(String path, String classifier, PrintStream stream) throws FileNotFoundException, IOException{
        BufferedReader readerClus = new BufferedReader(new FileReader(path+"/"+classifier+"Statistics"));
        BufferedReader readerC45 = new BufferedReader(new FileReader(path+"/"+classifier+"StatisticsND"));
       
         String lineC45;
         String lineClus;
   
        while(!readerC45.readLine().contains("Statistics of C45:")){ }
  
        printBeginTabular(stream, classifier, "AUPRC");
        stream.println(" auprc "+  classifier+"& gem C4.5 & gem Clus & gew gem C4.5 & gew gem Clus"+"\\\\ \\hline\\hline");
               
        while((lineC45 = readerC45.readLine())!=null){
            
            lineClus = readerClus.readLine();
            String dataset = getDataset(lineC45);
            String printString = dataset + " & " + getMean(lineC45, dataset, true)+" & "+
                    getMean(lineClus, dataset, true)+" & "+
                    getWeightedMean(lineC45, dataset, true)+ " & "+
                    getWeightedMean(lineClus, dataset, true)+" \\\\ \\hline";
            stream.println(printString);
        }
        printEndTabular(stream);
    }
    
     public void printAuroc(String path, String classifier, PrintStream stream) throws FileNotFoundException, IOException{
        BufferedReader readerClus = new BufferedReader(new FileReader(path+"/"+classifier+"Statistics"));
        BufferedReader readerC45 = new BufferedReader(new FileReader(path+"/"+classifier+"StatisticsND"));
       
         String lineC45;
         String lineClus;
   
        while(!readerC45.readLine().contains("Statistics of C45:")){ }
    
        printBeginTabular(stream, classifier, "AUROC");
        stream.println(" auroc "+  classifier+"& gem C4.5 & gem Clus & gew gem C4.5 & gew gem Clus"+"\\\\ \\hline\\hline");
               
        while((lineC45 = readerC45.readLine())!=null){
            lineClus = readerClus.readLine();
            String dataset = getDataset(lineC45);
            String printString = dataset + " & " + getMean(lineC45, dataset, false)+" & "+
                    getMean(lineClus, dataset, false)+" & "+
                    getWeightedMean(lineC45, dataset, false)+ " & "+
                    getWeightedMean(lineClus, dataset, false)+" \\\\ \\hline";
            stream.println(printString);
        }
        printEndTabular(stream);
    }
      
    public void printAccuracy(String path, String classifier, PrintStream stream) throws FileNotFoundException, IOException{
        BufferedReader readerClus = new BufferedReader(new FileReader(path+"/"+classifier+"Statistics"));
        BufferedReader readerC45 = new BufferedReader(new FileReader(path+"/"+classifier+"StatisticsND"));
       
        String lineC45;
        while(!readerC45.readLine().contains("Statistics of C45:")){ }
        
        printBeginTabular(stream, classifier, "de nauwkeurigheid");
        stream.println(" Nauwkeurigheid & "+ classifier+" C4.5 &  Clus"+"\\\\ \\hline\\hline");
               
        while((lineC45 = readerC45.readLine())!=null){
            String dataset = getDataset(lineC45);
            String printString = dataset + " & " + getAccuracy(lineC45, dataset)+" & "+
                    getAccuracy(readerClus.readLine(), dataset)+" \\\\ \\hline";
            stream.println(printString);
        }
        printEndTabular(stream);
    }


    
        //als het auprc is, is auprc true. als het auroc is, is auprc false
    public String getMean(String line, String dataset, boolean auprc){
        if(!line.contains(dataset)){
            throw new Error(line +" is niet van dataset "+dataset);
        }
        String[] splitTab = line.split("\t");
        for(String s: splitTab){
            if(auprc && s.contains("Mean auprc:")){
               String[] splitSpace = s.split(" ");
               return splitSpace[splitSpace.length-1];
            } else if(!auprc && s.contains("Mean auroc:")){
               String[] splitSpace = s.split(" ");
               return splitSpace[splitSpace.length-1];
            }
        }
        throw new Error("getMean is niet gelukt voor "+line +" en prc (true)/roc(false): " +auprc);
    }
    
    //als het auprc is, is auprc true. als het auroc is, is auprc false
        public String getWeightedMean(String line, String dataset, boolean auprc){
        if(!line.contains(dataset)){
            throw new Error(line +" is niet van dataset "+dataset);
        }
                String[] splitTab = line.split("\t");
        for(String s: splitTab){
            if(auprc && s.contains("Weighted mean auprc:")){
               String[] splitSpace = s.split(" ");
               return splitSpace[splitSpace.length-1];
            } else if(!auprc && s.contains("Weighted mean auroc:")){
                 String[] splitSpace = s.split(" ");
               return splitSpace[splitSpace.length-1];
            }
        }
        throw new Error("getWeightedMean is niet gelukt voor "+line+" en prc (true)/roc(false): " +auprc);
        
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
        stream.println("\\begin{tabular}{| l || l | l | l | l |}");
        stream.println("\\hline");
    }
    
    public void printEndTabular(PrintStream stream){
        stream.println("    \\end{tabular}");
        stream.println("\\end{center}");
        stream.println("\\end{table}");
    }
    
    public static void main(String[] args) throws IOException{
        ToLatex c = new ToLatex();
        c.convertAllToLatex();
    }
    
    
    
}
