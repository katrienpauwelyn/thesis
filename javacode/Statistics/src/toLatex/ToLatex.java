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
import java.text.DecimalFormat;
import java.text.NumberFormat;
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
        PrintStream stream = new PrintStream(new File(path+"/"+"alatex.txt"));
       printAuprc(classifier, stream,path+"/"+"aStatisticsClus.txt", path+"/"+"aStatisticsND.txt");
        stream.println();
        stream.println();
        printAuroc(classifier, stream, path+"/"+"aStatisticsClus.txt",path+"/"+"aStatisticsND.txt" );
        stream.println();
        stream.println();
        printAccuracy(classifier, stream, path+"/"+"aStatisticsClus.txt", path+"/"+"aStatisticsND.txt");
    }
    
    public void printAuprc(String classifier, PrintStream stream, String pathClus, String pathND) throws FileNotFoundException, IOException{
        BufferedReader readerClus = new BufferedReader(new FileReader(pathClus));
        BufferedReader readerC45 = new BufferedReader(new FileReader(pathND));
       
         String lineC45;
         String lineClus;
   
      //  while(!readerC45.readLine().contains("Statistics of C45:")){ }
  
        printBeginTabular(stream, classifier, "AUPRC");
        stream.println(" auprc "+  classifier+"& gem C4.5 & gem Clus & gew gem C4.5 & gew gem Clus"+"\\\\ \\hline\\hline");
               
        while((lineC45 = readerC45.readLine())!=null){
            
            lineClus = readerClus.readLine();
            String dataset = getDataset(lineC45);
            
            //new
            String meanND = getMean(lineC45, dataset, true);
            String meanClus = getMean(lineClus, dataset, true);
            String mean = compare(meanND, meanClus);
            String weightedMeanND = getWeightedMean(lineC45, dataset, true);
            String weightedMeanClus = getWeightedMean(lineClus, dataset, true);
            String weightedMean = compare(weightedMeanND, weightedMeanClus);
            //enew
            String printString = dataset + " & " + mean +" & "+
                    weightedMean+" \\\\ \\hline";
            stream.println(printString);
        }
        printEndTabular(stream);
    }
    
     public void printAuroc(String classifier, PrintStream stream, String pathClus, String pathND) throws FileNotFoundException, IOException{
        BufferedReader readerClus = new BufferedReader(new FileReader(pathClus));
        BufferedReader readerC45 = new BufferedReader(new FileReader(pathND));
       
         String lineC45;
         String lineClus;
   
    //    while(!readerC45.readLine().contains("Statistics of C45:")){ }
    
        printBeginTabular(stream, classifier, "AUROC");
        stream.println(" auroc "+  classifier+"& gem C4.5 & gem Clus & gew gem C4.5 & gew gem Clus"+"\\\\ \\hline\\hline");
               
        while((lineC45 = readerC45.readLine())!=null){
            lineClus = readerClus.readLine();
            String dataset = getDataset(lineC45);
            
                //new
            String meanND = getMean(lineC45, dataset, false);
            String meanClus = getMean(lineClus, dataset, false);
            String mean = compare(meanND, meanClus);
            String weightedMeanND = getWeightedMean(lineC45, dataset, false);
            String weightedMeanClus = getWeightedMean(lineClus, dataset, false);
            String weightedMean = compare(weightedMeanND, weightedMeanClus);
            //enew
            
            String printString = dataset + " & " + mean+" & "+
                    weightedMean+" \\\\ \\hline";
            stream.println(printString);
        }
        printEndTabular(stream);
    }
      
    public void printAccuracy(String classifier, PrintStream stream, String pathClus, String pathND) throws FileNotFoundException, IOException{
        BufferedReader readerClus = new BufferedReader(new FileReader(pathClus));
        BufferedReader readerC45 = new BufferedReader(new FileReader(pathND));
       
        String lineC45;
     //   while(!readerC45.readLine().contains("Statistics of C45:")){ }
        
        printBeginTabular(stream, classifier, "de nauwkeurigheid");
        stream.println(" Nauwkeurigheid & "+ classifier+" C4.5 &  Clus"+"\\\\ \\hline\\hline");
               
        while((lineC45 = readerC45.readLine())!=null){
            String dataset = getDataset(lineC45);
            
                //new
            String accND = getAccuracy(lineC45, dataset);
            String accClus = getAccuracy(readerClus.readLine(), dataset);
            String acc = compare(accND, accClus);
            //enew
            
            String printString = dataset + " & " + acc+" \\\\ \\hline";
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
        throw new Error(line+"getMean is niet gelukt voor "+line +" en prc (true)/roc(false): " +auprc);
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
    
    
    /**
     * 
     * Vergelijkt 2 gemiddelden$pm$standaarddeviaties met elkaar.
     * Als first groter is dan second, is first beter en komt er een bol naast.
     * als second groter is dan first, is second beter en komt er een bol naast second.
     * converteert naar gedeeltelijke latex code
     */
    public String compare(String first, String second){
        String newString;
        String[] f = first.split("pm");
        String[] s = second.split("pm");
        
          double firstMean =  Double.parseDouble(f[0])*100;
        double secondMean = Double.parseDouble(s[0])*100;
        double firstSD = Double.parseDouble(f[1])*100;
        double secondSD = Double.parseDouble(s[1])*100;
        

          NumberFormat formatter = new DecimalFormat("#0.000");
         
        
      
        String best = "$\\bigodot$";
        String pm = "$\\pm$";
        if(firstMean>secondMean){
            newString = formatter.format(firstMean)+pm+formatter.format(firstSD)+best+ "&" + 
                    formatter.format(secondMean)+pm+formatter.format(secondSD);
        } else if(secondMean>firstMean){
            newString = formatter.format(firstMean)+pm+formatter.format(firstSD)+ "&" + 
                    formatter.format(secondMean)+pm+formatter.format(secondSD)+best;
        } else {
            newString = formatter.format(firstMean)+pm+formatter.format(firstSD)+ "&" + 
                    formatter.format(secondMean)+pm+formatter.format(secondSD);
        }
        return newString;
    }

    
    public static void main(String[] args) throws IOException{
        ToLatex c = new ToLatex();
        c.convertAllToLatex();
     
    }
    
    
    
}
