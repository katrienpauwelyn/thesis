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

/**
 *
 * @author katie
 */
public class MeanAUC {
    
    /**
     * --- Writing PR file /export/home1/NoCsBack/thesisdt/s0212310/medical/micromacro/micromedical.txt.pr ---
--- Writing standardized PR file /export/home1/NoCsBack/thesisdt/s0212310/medical/micromacro/micromedical.txt.spr ---
--- Writing ROC file /export/home1/NoCsBack/thesisdt/s0212310/medical/micromacro/micromedical.txt.roc ---
Area Under the Curve for Precision - Recall is 0.15751045207745387
Area Under the Curve for ROC is 0.8583492028343667
     */
    //returnt de pr en roc uit een file (getest)
    public static PrRocTuple getPrRocFromFile(String file) throws FileNotFoundException, IOException{
        BufferedReader reader = new BufferedReader(new FileReader(file));
        double pr;
        double roc;
        while(!reader.readLine().contains("Writing ROC file")){}
        
        return new PrRocTuple( getPrOrRocFromLine(reader.readLine()), 
                getPrOrRocFromLine(reader.readLine()) );
    }
    
    public static double getPrOrRocFromLine(String line){
        String[] parsed = line.split(" ");
        String l = parsed[parsed.length-1];
        return Double.parseDouble(l);
    }
    
    //Area Under the Curve for Precision - Recall is 0.11937984496124028
//Area Under the Curve for ROC is 0.4999999999999999
    public static void main(String[] args) throws IOException{
        String path = "/Users/katie/Downloads/medical/micromacro/AUCmacroClass-0-593_70.txt";
        getPrRocFromFile(path).print();
    }
    
}
