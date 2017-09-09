/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Irrelevant;

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
 * TODO: * rank mag niet gewoon index zijn, moet index + 1 zijn
 * getest op "1,0,0,1,0,1,1,"
                + "1.0,0.0,0.6666666666666666,1.0,0.3333333333333333,1.0,0.0";
 * 
 */
public class RankingLoss {
        
    public static double rankingLossFile(String path) throws FileNotFoundException, IOException{
        BufferedReader reader = new BufferedReader(new FileReader(path));
        while(!reader.readLine().contains("@DATA")){}
        String line;
        double sum = 0;
        int nbExamples = 0;
        while((line=reader.readLine())!=null && !line.isEmpty()){
            sum+=rankingLossLine(line);
            nbExamples++;
        }
        return (double) sum/nbExamples;
    }
    
    public static double rankingLossLine(String line){
        LinkedList<RankTuple> ones = new LinkedList();
        LinkedList<RankTuple> zeros = new LinkedList();
        divideZerosOnes(line, ones, zeros);
        int Y = ones.size();
        int Ycomp = zeros.size();
        int D = getNbD(ones, zeros);
       /* if(Y==0 || Ycomp==0){
            System.out.println("Y"+Y+" Ycom"+Ycomp);
            System.out.println(line);
            return 0;
        }*/
        
        return (double) D/(Y*Ycomp);
        
    }
    
    //splits de true en false in twee linkedlisten
    //vb: line=0,1,0.0,1.0  ones: 1,1.0 zeros: 0,0.0
    public static void divideZerosOnes(String line, LinkedList<RankTuple> ones, LinkedList<RankTuple> zeros){
         String[] parsed = line.split(",");
        int offset = parsed.length/2;
        for(int i = 0; i<offset; i++){
            if(parsed[i].equals("1")){
                ones.add(new RankTuple(1, Double.parseDouble(parsed[i+offset])));
            } else {
                zeros.add(new RankTuple(0, Double.parseDouble(parsed[i+offset])));
            }
        }
      //  Collections.sort(ones);
        Collections.sort(zeros);
    }
    
    //ones moeten niet gesorteerd zijn, zeros moeten gesorteerd zijn van hoge probabiliteit naar lage probabiliteit
    public static int getNbD(LinkedList<RankTuple> ones, LinkedList<RankTuple> zeros){
        int nbD = 0;
        for(RankTuple tuple: ones){
            for(int i = 0; i<zeros.size(); i++){
                if(tuple.prediction < zeros.get(i).prediction){
                    nbD++;
                } else {
                    break;
                }
            }
        }
        return nbD;
    }
    
    public static void main(String[] args) throws IOException{
        String p = Path.path+"flags/average.test.pred.arff";
        LinkedList<RankTuple> zeros = new LinkedList();
        LinkedList<RankTuple> ones = new LinkedList();
        
        
        String line = "1,0,0,1,0,1,1,"
                + "1.0,0.0,0.6666666666666666,1.0,0.3333333333333333,1.0,0.0";
        divideZerosOnes(line, ones, zeros);
        //System.out.println(ones.size()+" "+zeros.size());
        System.out.println(getNbD(ones, zeros));
           System.out.println((double) 2/12);
           System.out.println(rankingLossLine(line));
        System.out.println(rankingLossFile(p));
    }
}
