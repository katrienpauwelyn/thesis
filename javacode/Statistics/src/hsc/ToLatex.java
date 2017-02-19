/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hsc;

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
 * PROBLEEM: niet alle datasets zitten er in
 * @author katie
 */
public class ToLatex {
    
        public void convertAllToLatex() throws IOException{
        for(String classifier: Path.classifiers){
            convertOneClassifierToLatex(Path.path+"/"+classifier, classifier);
        }
      //   convertOneClassifierToLatex(Path.path+"/"+"classBalanced", "classBalanced");
    }
    
    // header: accuracy/mean / weighted mean & classifier C4.5 & classifier Logistic & Clus
    
    //path = bv. /Users/katie/NetBeansProjects/weka/trunk/packages/internal/ensemblesOfNestedDichotomies/out/classBalanced
    public void convertOneClassifierToLatex(String path, String classifier) throws FileNotFoundException, IOException{
        toLatex.ToLatex to = new toLatex.ToLatex();
        PrintStream stream = new PrintStream(new File(path+"/"+"alatexHsc.tex"));
       to.printAuprc(classifier, stream, path+"/"+"aStatisticsClus.txt", path+"/"+"aHsc.txt");
        stream.println();
        stream.println();
        to.printAuroc(classifier, stream, path+"/"+"aStatisticsClus.txt", path+"/"+"aHsc.txt");
        stream.println();
        stream.println();
        to.printAccuracy(classifier, stream, path+"/"+"aStatisticsClus.txt", path+"/"+"aHsc.txt");
    }
    
    public static void toLatexHscAccuracy(String path, String classifier){
        
    }
    
    public void printAccuracy(String classifier, PrintStream stream, String pathClus, String pathHSC, String pathND) throws FileNotFoundException, IOException{
        BufferedReader readerClus = new BufferedReader(new FileReader(pathClus));
        BufferedReader readerC45 = new BufferedReader(new FileReader(pathND));
        BufferedReader readerHsc = new BufferedReader(new FileReader(pathHSC));
        
        toLatex.ToLatex to = new toLatex.ToLatex();
        String lineC45;
        
        to.printBeginTabular(stream, classifier, "de nauwkeurigheid");
        stream.println(" Nauwkeurigheid & "+ classifier+" C4.5 &  Clus"+"\\\\ \\hline\\hline");
               
        while((lineC45 = readerC45.readLine())!=null){
            String dataset = getDataset(lineC45);
            
                //new
            String accND = getAccuracy(lineC45, dataset);
            String accClus = getAccuracy(readerClus.readLine(), dataset);
            String auHsc = 
            String acc = compare(accND, accClus);
            //enew
            
            String printString = dataset + " & " + acc+" \\\\ \\hline";
            stream.println(printString);
        }
        to.printEndTabular(stream);
    }
    
      /**
     * 
     * Vergelijkt 3 gemiddelden$pm$standaarddeviaties met elkaar.
     * Als first groter is dan second, is first beter en komt er een bol naast.
     * als second groter is dan first, is second beter en komt er een bol naast second.
     * als third best is komt er een bol naast third
     * converteert naar gedeeltelijke latex code
     */
    public String compareThree(String first, String second, String third){
        String newString;
        String[] f = first.split("pm");
        String[] s = second.split("pm");
        String[] t = third.split("pm");
        
          double firstMean =  Double.parseDouble(f[0])*100;
        double secondMean = Double.parseDouble(s[0])*100;
        double thirdMean = Double.parseDouble(t[0])*100;
        double firstSD = Double.parseDouble(f[1])*100;
        double secondSD = Double.parseDouble(s[1])*100;
        double thirdSD = Double.parseDouble(s[1])*100;
        

          NumberFormat formatter = new DecimalFormat("#0.000");
         
        String best = "$\\bigodot$";
        String pm = "$\\pm$";
        if(firstMean>secondMean && firstMean > thirdMean){
            newString = formatter.format(firstMean)+pm+formatter.format(firstSD)+best+ "&" + 
                    formatter.format(secondMean)+pm+formatter.format(secondSD) + "&"+
                    formatter.format(thirdMean)+pm+formatter.format(thirdSD);
        } else if(secondMean>firstMean && secondMean > thirdMean){
            newString = formatter.format(firstMean)+pm+formatter.format(firstSD)+ "&" + 
                    formatter.format(secondMean)+pm+formatter.format(secondSD)+best+"&"+
                    formatter.format(thirdMean)+pm+formatter.format(thirdSD);
        } else {
            newString = formatter.format(firstMean)+pm+formatter.format(firstSD)+ "&" + 
                    formatter.format(secondMean)+pm+formatter.format(secondSD)+"&"+
                    formatter.format(thirdMean)+pm+formatter.format(thirdSD)+best;
        }
        return newString;
    }
    
    public static void main(String[] args) throws IOException{
        ToLatex to = new ToLatex();
        to.convertAllToLatex();
    }
    
    
}
