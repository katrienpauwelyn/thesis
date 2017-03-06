/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statistics;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author katie
 */
public class AUGetter {
    
      /**
     * Berekent de AUPRC van de gegeven file. (getest)
     * Neemt het gemiddelde van alle AUPRCs van alle leaf nodes (niet de internal nodes)
     */
    public static TupleFloat getAUPRCandAUROC(String path) throws FileNotFoundException, IOException{
        TupleFloat tupleOut = new TupleFloat(); //first is prc, second is roc
        BufferedReader in = new BufferedReader(new FileReader(path));
        String line;
        while(true){
            line = in.readLine();
            if(line.contains("Testing error")){
                while(true){
                        line = in.readLine();
                        if(line.contains("Pooled")) {break;}
                }
                break;
            }
        }
        line = in.readLine();
        float nbLeafNodes = 0;
        while(!line.isEmpty()){
            if(checkLeafNode(line)){
                tupleOut.incrementFirstWith(getAuprcFromLine(line));
                tupleOut.incrementSecondWith(getAurocFromLine(line));
                nbLeafNodes++;
            }
            line = in.readLine();
        }
        tupleOut.divideBothWith(nbLeafNodes);
       return tupleOut;
    }
    
        /**
     * Berekent de gewogen AUPRC van de gegeven file. (getest)
     * Neemt het gewogen gemiddelde van alle AUPRCs van alle leaf nodes (niet de internal nodes)
     */
    public static TupleFloat getWeightedAUPRCandAUROC(String path) throws FileNotFoundException, IOException{
        TupleFloat outTuple = new TupleFloat();
        BufferedReader in = new BufferedReader(new FileReader(path));
        String line;
        while(true){
            line = in.readLine();
            if(line.contains("Testing error")){
                while(true){
                   line = in.readLine();
                    if(line.contains("Pooled")){ break;}
                }
                break;
            }
        }
     
        line = in.readLine();
        while(!line.isEmpty()){
            if(checkLeafNode(line)){
                outTuple.incrementFirstWith(getWeightedAuprcFromLine(line));
                outTuple.incrementSecondWith(getWeightedAurocFromLine(line));
            }
            line = in.readLine();
        }
       return outTuple;
    }
    
    
       /**
     * returnt de AUPRC uit de string (getest)
     */
    private static float getAuprcFromLine(String line){
        String[] spatie = line.split(" ");
        for(int i = 0; i<spatie.length; i++){
            if(spatie[i].contains("AUPRC:")){
            return Float.parseFloat(spatie[i+1].substring(0, spatie[i+1].length()-1));
        }
        }
       throw new Error("geen auprc beschikbaar in deze lijn: "+line);
    }
    
     /**
     * returnt de AUROC uit de string 
     */
    private static float getAurocFromLine(String line){
        String[] spatie = line.split(" ");
        for(int i = 0; i<spatie.length; i++){
            if(spatie[i].contains("AUROC:")){
            return Float.parseFloat(spatie[i+1].substring(0, spatie[i+1].length()-1));
        }
        }
       throw new Error("geen auroc beschikbaar in deze lijn: "+line);
    }
    
        /**
     * returnt de AUPRC uit de string vermenigvuldigd met zijn frequentie
     */
    private static float getWeightedAuprcFromLine(String line){
        String[] spatie = line.split(" ");
       return getAuprcFromLine(line) * Float.parseFloat(spatie[spatie.length-1]);
    }
    
     /**
     * returnt de AUROC uit de string vermenigvuldigd met zijn frequentie
     */
    private static float getWeightedAurocFromLine(String line){
        String[] spatie = line.split(" ");
       return getAurocFromLine(line) * Float.parseFloat(spatie[spatie.length-1]);
    }
    
    //TODO implementeren indien voor hierarchische nodig
    public static boolean checkLeafNode(String line){
        return true;
    }
    
    
}
