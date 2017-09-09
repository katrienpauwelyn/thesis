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
import statics.Path;

/**
 *
 * @author katie
 * 
 */
public class OneError {
    
    //??? als er meerdere zijn met dezelfde hoogste kans, moet je al deze checken? of een random?
    
    public static double oneError(String file) throws FileNotFoundException, IOException{
        double out = 0.0;
        int nbTest = 0;
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while(!reader.readLine().contains("@DATA")){}
        while((line=reader.readLine())!=null && !line.isEmpty()){
            nbTest++;
            String[] split = line.split(",");
            out+=oneErrorLineSum(line);
        }
        return (double) out/nbTest;
    }
    
    //(aantal hoogste voorspellingen die geen labels zijn) / (aantal hoogste voorspellingen)
    public static double oneErrorLineSum(String line){
        String[] split = line.split(",");
        int offset = split.length/2;
        int nbHighest = 0;
        int nbHighestNotLabel = 0;
        double highest = 0.0;
        for(int i = offset; i<split.length; i++){
            if(Double.parseDouble(split[i])>highest){
                highest = Double.parseDouble(split[i]);
            }
        }
        
        for(int i = 0; i<offset; i++){
            if(Double.parseDouble(split[i+offset])==highest){
                nbHighest++;
                if(Integer.parseInt(split[i])!=1){
                    nbHighestNotLabel++;
                }
            }
        }
        return (double) nbHighestNotLabel/nbHighest;
    }
    
    
    //1 als er een van de hoogste voorspellingen niet in het label zit
 /*   public static int oneErrorLineAllLabels(String line){
        String[] split = line.split(",");
        int offset = split.length/2;
        double highest = 0.0;
        for(int i = offset; i<split.length; i++){
            if(Double.parseDouble(split[i])>highest){
                highest = Double.parseDouble(split[i]);
            }
        }
        for(int i = 0; i<offset; i++){
            if(Double.parseDouble(split[i+offset])==highest){
                if(Integer.parseInt(split[i])!=1){
                    return 1;
                }
            }
        }
        return 0;
    }*/
    
    public static void main(String[] args) throws IOException{
        String a = "1,0,0,1,0,1,1 ,1.0,0.0,0.6666666666666666,1.0,0.3333333333333333,1.0,0.0";
        String b = "0,0,0,0,1,1,1, 0.3333333333333333,0.6666666666666666,0.3333333333333333,1.0,0.3333333333333333,0.0,0.3333333333333333";
        String c = "1,0,1,1,0,1,0 ,1.0,0.0,0.0,1.0,1.0,1.0,0.0";
        String d = "0,1,1.0,1.0";
        String p = Path.path+"flags/average.test.pred.arff";
        System.out.println(oneErrorLineSum(a));
        System.out.println(oneErrorLineSum(b));
        System.out.println(oneErrorLineSum(c));
        System.out.println(oneError(p));
    }
    
}
