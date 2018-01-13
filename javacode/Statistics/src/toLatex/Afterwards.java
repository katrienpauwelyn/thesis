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

/**
 *
 * @author katie
 * reeds in latex formaat, pas de symbolen aan
 * + voor degene die een beetje beter is, ++ voor degene die buiten standaarddeviatie beter is
 * 
 * buiten standaarddeviatie = clus+std < c4.5-std
 */
public class Afterwards {
    //audiology om te beginnen, zoo om te eindigen
    
    public static void transformPlus(String pathFile, String pathNewFile) throws FileNotFoundException, IOException{
        PrintStream stream = new PrintStream(new File(pathNewFile));
        BufferedReader reader = new BufferedReader(new FileReader(pathFile));
        transformRecursive(stream, reader);
        
        
    }
    
    public static void transformRecursive(PrintStream stream, BufferedReader reader) throws IOException{
       String line;
            while((line=reader.readLine())!=null && !line.contains("audiology")){
                stream.println(line);
            }
            if(line!=null){
                  while(!line.contains("end{tabular}")){
                    stream.println(transformLine(line));
                    line = reader.readLine();   
            }
            
            transformRecursive(stream, reader);
            }
          
        
      }
    
    
    /**
     *  Nauwkeurigheid & classBalanced C4.5 &  Clus\\ \hline\hline
audiology & 73,849$\pm$1,525$\bigodot$&68,584$\pm$1,998 \\ \hline
     */
    public static String transformLine(String line){
        String[] split = (line.replace("\\\\ \\hline","")).split("&");
      
        //0: dataset    1: C4.5     2:Clus
        String outputLine = split[0];
        for(int i = 1; i<split.length; i=i+2){
            
            if(isFirstOneBigger(split[i], split[i+1])==0){
                outputLine = outputLine.concat(" & "+split[i]+" & "+split[i+1]);
            } else if(isFirstOneBigger(split[i], split[i+1])==1){ //C4.5 is beter
                if(getMin(split[i])>getPlus(split[i+1])){
                    outputLine = outputLine.concat(" & "+split[i]+"++ & "+split[i+1]);
                } else {
                    outputLine = outputLine.concat(" & "+split[i]+"+ & "+split[i+1]);
                }
            } else { //Clus is beter
                if(getMin(split[i+1])>getPlus(split[i])){
                    outputLine = outputLine.concat(" & "+split[i]+" & "+split[i+1]+"++ ");
                } else {
                    outputLine = outputLine.concat(" & "+split[i]+" & "+split[i+1]+"+ ");
                }
            }
        }
        return outputLine.concat(" \\\\ \\hline");
    }
    
    //s =  73,849$\pm$1,525$\bigodot$ => 73,849+1,525
    public static double getPlus(String s){
        s=s.replace(" ", "").replace("$\\bigodot$","").replace("\\","").replace("$","");
        String[] split = s.split("pm");
        double mean = Double.parseDouble(split[0].replace(",","."));
        double pm = Double.parseDouble(split[1].replace(",","."));
        return mean+pm;
    }
    
    public static double getMin(String s){
        s=s.replace(" ", "").replace("$\\bigodot$","").replace("\\","").replace("$","");
        String[] split = s.split("pm");
        double mean = Double.parseDouble(split[0].replace(",","."));
        double pm = Double.parseDouble(split[1].replace(",","."));
        return mean-pm;
    }
    
    //s1 en s2:  73,849$\pm$1,525$\bigodot$
    //als het eerste gemiddelde groter is, return 1. gelijk: 0. Eerste is kleiner: return -1
    public static int isFirstOneBigger(String s1, String s2){
         s1=s1.replace(" ", "").replace("$\\bigodot$","").replace("\\","").replace("$","");
         s2=s2.replace(" ", "").replace("$\\bigodot$","").replace("\\","").replace("$","");
        
        String[] split1 = s1.split("pm");
       
        String[] split2 = s2.split("pm");
        double mean1 = Double.parseDouble(split1[0].replace(",","."));
        double mean2 = Double.parseDouble(split2[0].replace(",","."));
        if(mean1>mean2){
            return 1;
        } 
        if(mean2>mean1){
            return -1;
        }
        return 0;
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException{
        String p = "/Users/katie/Dropbox/thesis/blabla/thesisTekst/input/resultaten/resultatenNd.tex";
        String to = "/Users/katie/Dropbox/thesis/blabla/thesisTekst/input/resultaten/resultatenNdNew.tex";
        transformPlus(p, to);
    }
}
