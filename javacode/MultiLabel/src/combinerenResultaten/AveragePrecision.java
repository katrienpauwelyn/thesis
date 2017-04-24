/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package combinerenResultaten;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import statics.Path;

/**
 *
 * @author katie
 */
public class AveragePrecision {
    
     public static double averagePrecisionFile(String path) throws FileNotFoundException, IOException{
        BufferedReader reader = new BufferedReader(new FileReader(path));
        while(!reader.readLine().contains("@DATA")){}
        String line;
        double sum = 0;
        int nbExamples = 0;
        while((line=reader.readLine())!=null && !line.isEmpty()){
            sum+=averagePrecisionLine(line);
            nbExamples++;
        }
        return (double) sum/nbExamples;
    }
    
    
    public static double averagePrecisionLine(String line){
        LinkedList<RankTuple> ones = new LinkedList();
        selectOnes(line, ones);
        int sizeYi = ones.size();
        if(sizeYi==0){
            return 0;
        }
        double sumLi = 0;
        for(int i = 0; i<sizeYi; i++){
            int Li = sizeYi - i-1; //rechts: zowiezo groter of gelijk aan
            double currentPred = ones.get(i).prediction;
            int y = i-1;
            //alle labels die kleinere of gelijk aan predictie hebben als de huidige
            //links: zolang gelijk (niet gelijk => groter)
            while(y>=0){
                if(ones.get(y).prediction == currentPred){
                    Li++;
                    y--;
                } else {
                    break;
                }
            }
            sumLi+= (double) Li/(i+1); 
        }
    return (double) sumLi/sizeYi;
    }
    
       //haalt enkel de 'ware' attributes er uit, sorteren van hoge naar lage probabiliteit
    public static void selectOnes(String line, LinkedList<RankTuple> ones){
         String[] parsed = line.split(",");
        int offset = parsed.length/2;
        for(int i = 0; i<offset; i++){
            if(parsed[i].equals("1")){
                ones.add(new RankTuple(1, Double.parseDouble(parsed[i+offset])));
            }
        }
      Collections.sort(ones);
    }
    
        public static void main(String[] args) throws IOException{
          String p = Path.path+"flags/average.test.pred.arff";
          System.out.println(averagePrecisionFile(p));
    }
    
}
