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
public class MeanAUCOne {
    
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
    
    
    
    public static void printAUs() throws FileNotFoundException, IOException{
        String pathStandard = Path.pathFilteredAll;
        PrintStream[] streams = new PrintStream[23];
        streams[0] = new PrintStream(new File(Path.pathPinac+"aucsFlatOne.txt"));
        streams[0].println("AUCs van Flat One");

        for(int i = 0; i<10; i++){
            streams[i+1] = new PrintStream(new File(Path.pathPinac+"aucsKMeansOne"+i+".txt"));
            streams[i+1].println("AUCs van KMean One set "+i);
        }
        streams[11] = new PrintStream(new File(Path.pathPinac+"aucsKMeansOneGemiddelde.txt"));
        streams[11].println("AUCs van KMeans One gemiddelde");
        
        for( int i =0; i<10; i++){
            streams[i+12] = new PrintStream(new File(Path.pathPinac+"aucsRHam"+i+".txt"));
            streams[i+12].println("AUCs van RHam One set "+i);
        }
          streams[22] = new PrintStream(new File(Path.pathPinac+"aucRHamOneGemiddelde.txt"));
        streams[22].println("gemiddelde AUCs van RHam  One");
        
        printAUsPerMap(pathStandard, streams);
        for(PrintStream stream: streams){
            stream.close();
        }
    }
    
    public static void printAUsPerMap(String pathMap, PrintStream[] streams) throws FileNotFoundException, IOException{
        String line;
        BufferedReader reader = new BufferedReader(new FileReader(pathMap));
        
        //per classifier een stream
       
        
        
        
        while((line=reader.readLine())!=null && !line.isEmpty()){
          //  reader.readLine();
            String classes = reader.readLine();
            PrRocTuple macro;
            PrRocTuple micro;
            String print;
            
            //Flat
            micro = getMicroAuDataset(line, "FlatOne");
            macro = getMeanMacroAUsDataset(line, classes, "FlatOne");
             print = line+"  Micro: (PR-"+micro.pr+") (ROC-"+micro.roc+")"
                    + "  Macro: (MeanPR-"+macro.pr+") (MeanROC-"+macro.roc+")";
             streams[0].println(print);
           
         double microPr = 0.0;
          double microRoc = 0.0;
          double macroPr = 0.0;
          double macroRoc = 0.0;
          //KMeans
          for(int i = 0; i<10; i++){
                  micro = getMicroAuDataset(line, "KMeansOne"+i);
            macro = getMeanMacroAUsDataset(line, classes, "KMeansOne"+i);
            microPr += micro.pr;
            macroPr += macro.pr;
            microRoc += micro.roc;
            macroRoc += macro.roc;
             print = line+"  Micro: (PR-"+micro.pr+") (ROC-"+micro.roc+")"
                    + "  Macro: (MeanPR-"+macro.pr+") (MeanROC-"+macro.roc+")";
             streams[i+1].println(print);
        
          }
          
           print = line+"  Micro: (PR-"+microPr/10+") (ROC-"+microRoc/10+")"
                    + "  Macro: (MeanPR-"+macroPr/10+") (MeanROC-"+macroRoc/10+")";
            streams[11].println(print);
            
            
            
              microPr = 0.0;
           microRoc = 0.0;
          macroPr = 0.0;
          macroRoc = 0.0;
             //RHam
            for(int i = 0; i<10; i++){
                
                micro = getMicroAuDataset(line, "RHamOne"+i);
                macro = getMeanMacroAUsDataset(line, classes, "RHamOne"+i);
                microPr += micro.pr;
                macroPr += macro.pr;
                microRoc += micro.roc;
                macroRoc += macro.roc;
                 print = line+"  Micro: (PR-"+micro.pr+") (ROC-"+micro.roc+")"
                    + "  Macro: (MeanPR-"+macro.pr+") (MeanROC-"+macro.roc+")";
                 streams[i+12].println(print);
            }
            print = line+"  Micro: (PR-"+microPr/10+") (ROC-"+microRoc/10+")"
                    + "  Macro: (MeanPR-"+macroPr/10+") (MeanROC-"+macroRoc/10+")";
            streams[22].println(print);
          }
    }
    
        //MICRO
    //AUCmicro"+dataset+"FlatOne.txt
    //AUCmicro"+dataset+"KMeansOne.txt
    //AUCmicro"+dataset+"RHamOne"+i+".txt
    
      public static PrRocTuple getMicroAuDataset(String dataset, String classifier) throws IOException{
        String path = Path.pathPinac+dataset+"/one/micromacro/";
        PrRocTuple microAU;
        microAU = getPrRocFromFile(path+"AUCmicro"+dataset+classifier+".txt");
        return microAU;
    }
         
        //MACRO
    //"AUCmacro"+parsed[i]+"FlatOne.txt"
    //"AUCmacro"+parsed[i]+"KMeansOne.txt"
    //"AUCmacro"+parsed[i]+"RHamOne"+ham+".txt"
 
      
      //returnt de gemiddelde aus van een dataset
      //ERROR: File /export/home1/NoCsBack/thesisdt/s0212310/delicious/one/micromacro/TAG_3vraag-deliciousKMeansOne0.txt not found - exiting...
// File /export/home1/NoCsBack/thesisdt/s0212310/delicious/one/micromacro/TAG_muusica-deliciousKMeansOne9.txt not found - exiting...
// File /export/home1/NoCsBack/thesisdt/s0212310/delicious/one/micromacro/TAG_musica-deliciousKMeansOne3.txt not found - exiting...

        
    public static PrRocTuple getMeanMacroAUsDataset(String dataset, String unparsedClasses, String classifier) throws IOException{
        String path = Path.pathPinac+dataset+"/one/micromacro/";
        String[] parsed = unparsedClasses.split(",");
        PrRocTuple macroAU = new PrRocTuple(0, 0);
         int nb = parsed.length;
        for(String cl: parsed){
                String p = path+"AUCmacro"+cl+classifier+".txt";
            macroAU.add(getPrRocFromFile(p));
            
        }
       
        
        macroAU.divide(nb);
        return macroAU;
    }
    
  
    
    
    //Area Under the Curve for Precision - Recall is 0.11937984496124028
//Area Under the Curve for ROC is 0.4999999999999999
    public static void main(String[] args) throws IOException{
        printAUs();
             
    }
    
}
