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
 * berekent de micro en macro gemiddelde van de AUPRC en AUROC van alle datasets en print
 * het uit naar een gegeven file
 */
public class MeanAUC {
    
    /**
     * input file bevat: 
     * 
     * --- Writing PR input /export/home1/NoCsBack/thesisdt/s0212310/medical/micromacro/micromedical.txt.pr ---
--- Writing standardized PR input /export/home1/NoCsBack/thesisdt/s0212310/medical/micromacro/micromedical.txt.spr ---
--- Writing ROC input /export/home1/NoCsBack/thesisdt/s0212310/medical/micromacro/micromedical.txt.roc ---
Area Under the Curve for Precision - Recall is 0.15751045207745387
Area Under the Curve for ROC is 0.8583492028343667
     */
    //returnt de pr en roc uit een file (getest)
    public static PrRocTuple getPrRocFromFile(String input) throws FileNotFoundException, IOException{
        BufferedReader reader = new BufferedReader(new FileReader(input));
        double pr;
        double roc;
        String line;
        
        while(!(line=reader.readLine()).contains("Writing ROC file")){}
        
        return new PrRocTuple( getPrOrRocFromLine(reader.readLine()), 
                getPrOrRocFromLine(reader.readLine()) );
    }
    
    /**
     * Returnt de PR of ROC (al naar gelang wat er in de lijn staat) uit de gegeven lijn. 
     */
    public static double getPrOrRocFromLine(String line){
        String[] parsed = line.split(" ");
        String l = parsed[parsed.length-1];
        return Double.parseDouble(l);
    }
    
      //returnt de gemiddelde macro PR en ROC van een dataset
    public static PrRocTuple getMeanMacroAUsDataset(String dataset, String unparsedClasses) throws IOException{
       // String path = Path.pathPinac+dataset+"/kmeans/micromacro/";
        //String path = Path.pathPinac+dataset+"/flat/micromacro/";
        String path = Path.pathPinac+dataset+"/kmeans/micromacro/";
        String[] classes = unparsedClasses.split(",");
        PrRocTuple macroAU = new PrRocTuple(0, 0);
        for(String cl: classes){
            String p = path+"AUCmacro"+cl+"KMeansFull.txt";
            macroAU.add(getPrRocFromFile(p));
        }
        macroAU.divide(classes.length);
        return macroAU;
    }
    
    public static PrRocTuple getMicroAuDataset(String dataset) throws IOException{
        //String path = Path.pathPinac+dataset+"/kmeans/micromacro/";
        //String path = Path.pathPinac+dataset+"/flat/micromacro/";
        String path = Path.pathPinac+dataset+"/kmeans/micromacro/";
        PrRocTuple microAU = getPrRocFromFile(path+"AUCmicro"+dataset+"KmeansFull.txt");
        return microAU;
    }
    
    public static void printAUs(String pathPrint) throws FileNotFoundException, IOException{
        PrintStream stream = new PrintStream(new File(pathPrint));
       // String pathStandard = Path.pathStandardMap;
        String pathAll = Path.pathFilteredAll;
       // printAUsPerMap(pathStandard, stream);
        printAUsPerMap(pathAll, stream);
        stream.close();
    }
    
    /**
     * 
     * @param pathMap: pad naar een file die per dataset de naam, aantal attributen en de naam van de klassen bevat.
     * vb:
     *  emotions
        72
        amazed:suprised,angry:aggresive,happy:pleased,quiet:still,relaxing:calm,sad:lonely
      */
    public static void printAUsPerMap(String pathMap, PrintStream stream) throws FileNotFoundException, IOException{
        String line;
        BufferedReader reader = new BufferedReader(new FileReader(pathMap));
        while((line=reader.readLine())!=null && !line.isEmpty()){
           // reader.readLine();
            String classes = reader.readLine();
            PrRocTuple macro = getMeanMacroAUsDataset(line, classes);
            PrRocTuple micro = getMicroAuDataset(line);
            
            String print = line+"  MicroPR: "+micro.pr+"\n MicroROC: "+micro.roc+"\n"
                    + "  MacroPR: "+macro.pr+"\n MacroROC: "+macro.roc;
            stream.println(print);
            stream.println();
        }
    }
    
    
    
    //Area Under the Curve for Precision - Recall is 0.11937984496124028
//Area Under the Curve for ROC is 0.4999999999999999
    public static void main(String[] args) throws IOException{
        printAUs(Path.pathPinac+"AUKmeansFull.txt");
                //"/Users/katie/Downloads/micromacro/aaaa.txt");
    }
    
}
