/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package combinerenResultaten;

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
        System.out.println(file);
        String line;

        while(!(line=reader.readLine()).contains("Writing ROC file")){}
        
        return new PrRocTuple( getPrOrRocFromLine(reader.readLine()), 
                getPrOrRocFromLine(reader.readLine()) );
    }
    
    public static double getPrOrRocFromLine(String line){
        String[] parsed = line.split(" ");
        String l = parsed[parsed.length-1];
        return Double.parseDouble(l);
    }
    
    //returnt de gemiddelde aus van een dataset
    public static PrRocTuple getMeanMacroAUsDataset(String dataset, String unparsedClasses) throws IOException{
        String path = Path.pathPinac+dataset+"/micromacro/";
                //"/Users/katie/Downloads/micromacro/";
        PrRocTuple microAU = getPrRocFromFile(path+"AUCmicro"+dataset+".txt");
        String[] parsed = unparsedClasses.split(",");
        PrRocTuple macroAU = new PrRocTuple(0, 0);
        for(String cl: parsed){
            String p = path+"AUCmacro"+cl+".txt";
            macroAU.add(getPrRocFromFile(p));
        }
        macroAU.divide(parsed.length);
        return macroAU;
    }
    
    public static PrRocTuple getMicroAuDataset(String dataset) throws IOException{
        String path = Path.pathPinac+dataset+"/micromacro/";
                //"/Users/katie/Downloads/micromacro/";
        PrRocTuple microAU = getPrRocFromFile(path+"AUCmicro"+dataset+".txt");
        return microAU;
    }
    
    public static void printAUs(String pathPrint) throws FileNotFoundException, IOException{
        PrintStream stream = new PrintStream(new File(pathPrint));
        String pathStandard = Path.pathStandardMap;
        String pathSparse = Path.pathSparseMap;
        printAUsPerMap(pathStandard, stream);
        printAUsPerMap(pathSparse, stream);
        stream.close();
    }
    
    public static void printAUsPerMap(String pathMap, PrintStream stream) throws FileNotFoundException, IOException{
        String line;
        BufferedReader reader = new BufferedReader(new FileReader(pathMap));
        while((line=reader.readLine())!=null && !line.isEmpty()){
            reader.readLine();
            String classes = reader.readLine();
            PrRocTuple macro = getMeanMacroAUsDataset(line, classes);
            PrRocTuple micro = getMicroAuDataset(line);
            
            String print = line+"  Micro: (PR-"+micro.pr+") (ROC-"+micro.roc+")"
                    + "  Macro: (MeanPR-"+macro.pr+") (MeanROC-"+macro.roc+")";
            stream.println(print);
        }
    }
    
    
    
    //Area Under the Curve for Precision - Recall is 0.11937984496124028
//Area Under the Curve for ROC is 0.4999999999999999
    public static void main(String[] args) throws IOException{
        printAUs(Path.pathPinac+"AUs.txt");
                //"/Users/katie/Downloads/micromacro/aaaa.txt");
    }
    
}
