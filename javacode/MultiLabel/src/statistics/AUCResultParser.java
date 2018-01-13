/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statistics;

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
 * berekent de standaarddeviatie van de AUCs
 */
public class AUCResultParser {
    
    //String name = "aucsKMeansOne" of "aucsRHam";
    public static void parse(String name) throws FileNotFoundException, IOException{
        String[] datasets = {"corel5k", "emotions", "mediamill", "scene", "yeast", "bibtex", "delicious", "enron", "medical"};
        String path = "/Users/katie/Dropbox/thesis/outputPinac/one/";
        
        BufferedReader[] reader = new BufferedReader[10];
        PrintStream stream = new PrintStream(new File(path+"std/aucsKMeans"));
        
        for(int i  = 0; i<10; i++){
            reader[i] = new BufferedReader(new FileReader(path+name+i+".txt"));
        }
        for(BufferedReader r: reader){
            r.readLine();
        }
        
        String[] aucsNames = {"microPR", "microROC", "macroPR", "macroROC"};

        for (String dataset : datasets) {//for each dataset
            stream.println(dataset);
         
            
             double[][] auc = new double[4][10];
                for (int i = 0; i < 10; i++) {//voor iedere run
                    
                   
               
           
                    
                    
                    
                    String line = reader[i].readLine();
                      for (int nb = 0; nb < 4; nb++) {//for each auc
                    if (!line.contains(dataset)) {
                        System.out.println(line+"!!");
                        System.out.println(dataset+"!!");
                        throw new Error();
                    }
                    auc[nb][i] = getAUCline(nb+1, line);
                }
                
            }
                for(int nb=0; nb<4; nb++){
                    stream.println(aucsNames[nb]+" "+getStandardDev(auc[nb]));
                }

        }

        
        
    }

    //nb: 1 is microPR, 2 is microROC, 3 is macroPR, 4 is macroROC
public static double getAUCline(int nb, String line){
        String[] parsed = line.split("\\(");
        String rightParse = parsed[nb];//bv "PR-0.1470192903750701) "
        String ab = rightParse.split("\\)")[0];
        String a = ab.split("-")[1];
        return Double.parseDouble(a);
}

public static double getMean(double[] a){
    double out = 0;
    for(double aa: a){
        out+=aa;
    }
    return out/(a.length);
}

public static double getStandardDev(double[] a){
    double mean = getMean(a);
    double sum = 0;
    for(double aa: a){
        sum+=(aa-mean)*(aa-mean);
    }
    sum/=a.length-1;
    return Math.sqrt(sum);
}

public static void main(String[] args) throws IOException{
   
parse("aucsKMeansOne");
    
}

}
/**
 * AUCs van KMean One set 4
corel5k  Micro: (PR-0.1470192903750701) (ROC-0.8625001569711106)  Macro: (MeanPR-0.04124454390592116) (MeanROC-0.6123372832028405)
emotions  Micro: (PR-0.5666589706107067) (ROC-0.7456356142508793)  Macro: (MeanPR-0.5648184898601801) (MeanROC-0.7381028804032087)
mediamill  Micro: (PR-0.5115884234510659) (ROC-0.8845910481165125)  Macro: (MeanPR-0.1066065126815758) (MeanROC-0.6373618789320893)
scene  Micro: (PR-0.5638655023578608) (ROC-0.7955719920678241)  Macro: (MeanPR-0.5709278790909208) (MeanROC-0.7939757878762923)
yeast  Micro: (PR-0.6204291408280858) (ROC-0.7735081044729342)  Macro: (MeanPR-0.4149353064243338) (MeanROC-0.6115985950510565)
bibtex  Micro: (PR-0.380666284893012) (ROC-0.8730102966375299)  Macro: (MeanPR-0.22162519960120108) (MeanROC-0.8320728382070044)
delicious  Micro: (PR-0.28807769315301723) (ROC-0.8814609875692598)  Macro: (MeanPR-0.093429352149364) (MeanROC-0.7280303819160251)
enron  Micro: (PR-0.5239001058045933) (ROC-0.8847536616653067)  Macro: (MeanPR-0.14044761951839496) (MeanROC-0.6474812339218999)
medical  Micro: (PR-0.7352932614178247) (ROC-0.9368885518157661)  Macro: (MeanPR-0.2758253960553656) (MeanROC-0.7875178570883693)

 */
