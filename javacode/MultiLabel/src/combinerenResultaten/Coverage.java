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
 * Nog niet getest 
 * wordt correct gesorteerd van grote predictie naar kleine predictie
 */
public class Coverage {
    
    
    public static double coverageFile(String path) throws FileNotFoundException, IOException{
        BufferedReader reader = new BufferedReader(new FileReader(path));
        while(!reader.readLine().contains("@DATA")){}
        String line;
        double sum = 0;
        int nbExamples = 0;
        while((line=reader.readLine())!=null && !line.isEmpty()){
            sum+=coverageLine(line);
            nbExamples++;
        }
        return (double) sum/nbExamples;
    }
    
    public static double coverageLine(String line){
        LinkedList<RankTuple> listRank = convertLineToList(line);
        for(int i = listRank.size()-1; i>=0; i--){
            if(listRank.get(i).label==1){
                return (double) i/listRank.size();
            }
        }
        System.out.println("coverage: there is no real label in "+line);
        return 0;
    }
    
    
    public static LinkedList<RankTuple> convertLineToList(String line){
        String[] parsed = line.split(",");
        int offset = parsed.length/2;
        LinkedList<RankTuple> listRank = new LinkedList<RankTuple>();
        for(int i = 0; i<offset; i++){
           listRank.add(new RankTuple(Integer.parseInt(parsed[i]), Double.parseDouble(parsed[i+offset])));
        }
        Collections.sort(listRank);
        return listRank;
    }
    
    public static void main(String[] args) throws IOException{
          String p = Path.path+"flags/average.test.pred.arff";
          System.out.println(coverageFile(p));
    }
    
}
