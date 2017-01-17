/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NDResults;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author katie
 */
public class ParseDataND {
    
    //adds the number of positive and negative examples this fold contains to posNeg
    //means: contains after execution the mean and weighted mean of this fold
    public void parseFold(String path, TupleInt posNeg, TupleDouble meansAuprc, TupleDouble meansAuroc) throws FileNotFoundException, IOException{
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String line;
        
        //go to the correct data segment
        while((line = reader.readLine())!= null){
            if(line.contains("Error on test data")){
                break;
            }
        }
        
        //accuracy
        while((line = reader.readLine())!=null){
            if(line.contains("Correctly Classified Instances")){
                posNeg.incrementPositive(getSecondArgument(line));
                line = reader.readLine();
                posNeg.incrementNegative(getSecondArgument(line));
                break;
            }
        }
        
        //auprc
        while((line=reader.readLine())!=null){
            if(line.contains("Detailed Accuracy By Class")){
                reader.readLine();
                parseResultMatrix(reader, meansAuprc, meansAuroc);
            }
        }
        
        
        
        
    }
    
    //returnt het tweede argument uit een String. Negeert alle whitespaces tussen de argumenten.
    //bv: Correctly Classified Instances          16               69.5652 %
    //returnt '16'
    //getest op 1 file
    private int getSecondArgument(String line){
        ArrayList<String> p =removeWhitespaces(line);
        return Integer.parseInt(p.get(1).replaceAll(" ", ""));
    }
    
    
    //parse de matrix waar oa tp, fp, prcarea in staan. berektn de mean auprc en de weighted mean auprc
    //de volgende lijn dat de reader leest zijn de headers
    //soms zijn er '?' in de resultaten van auprc per klasse. Dit waarschijnlijk omdat er geen
    //instances van deze klasse in de test file zitten. Deze worden genegeerd: het gewogen gemiddelde
    //blijft behouden van de file zelf, het gemiddelde wordt berekend met de resultaten die er wel zijn
    //opgeteld en gedeeld door het aantal klassen die er wel zijn
    //means: kan al gemiddelden bevatten. Het gemiddelde van deze fold wordt er bij geteld.
    private void parseResultMatrix(BufferedReader reader, TupleDouble meansPrc, TupleDouble meansRoc) throws IOException{
        String headers = reader.readLine();
        int indexAUPRC = getIndex(headers, "PRC Area");
        int indexROC = getIndex(headers, "ROC Area");
        String line;
        int nbClassesPrc = 0;
        double sumOfAUPRCs = 0; 
        double sumOfAUROCs = 0;
        int nbClassesRoc = 0;
        while(!(line = reader.readLine()).contains("Weighted Avg.")){
            ArrayList<String> parsed = removeWhitespaces(line);
            String prc = parsed.get(indexAUPRC);
            String roc = parsed.get(indexROC);
            if(!prc.contains("?")){
                 sumOfAUPRCs += Double.parseDouble(prc);
                 nbClassesPrc++;
            }
            if(!roc.contains("?")){
                 sumOfAUROCs += Double.parseDouble(roc);
                 nbClassesRoc++;
            }           
        }
        meansPrc.incrementMean(sumOfAUPRCs/nbClassesPrc);
        meansPrc.incrementWeighted(getWeightedAverageAUPRC(line, true));       
        meansRoc.incrementMean(sumOfAUROCs/nbClassesRoc);
        meansRoc.incrementWeighted(getWeightedAverageAUPRC(line, false));       
    }
    
    //returnt de index van de auprc of auroc indien de string geparsed wordt op spaties (dubbele)x.
    //getest op 1 file
    private int getIndex(String headers, String searchFor){
        ArrayList<String> parsed = removeWhitespaces(headers);
        for(int i = 0; i<parsed.size(); i++){
            if(parsed.get(i).contains(searchFor)){
                return i;
            }
        }
        throw new Error("get index of "+searchFor+" in matrix was not successful");
    }

    
    //returnt het laatste element, dit bevat de auprc
    //voorlaatste element: auroc
    //als 'auprc' true is, return auprc. Als 'auprc' false is, return roc
    private double getWeightedAverageAUPRC(String weighted, boolean auprc){
        ArrayList<String> parsed = removeWhitespaces(weighted);
        if(auprc){
            return Double.parseDouble(parsed.get(parsed.size()-1));
        } else {
            return Double.parseDouble(parsed.get(parsed.size()-2));
        }
    }
    
    //parse a string by whitespaces, return all the elements without whitespaces
    private ArrayList<String> removeWhitespaces(String line){
        ArrayList<String> out = new ArrayList<String>();
        String[] parsed = line.split("  ");
        for(String s: parsed){
            String temp = s.replaceAll(" ", "");
            if(!temp.isEmpty()){
                s=s.replaceAll(",",".");
                out.add(s);
            }
        }
        return out;
    }
    
    public void  printStatistics(String path) throws IOException{
        TupleInt posNeg = new TupleInt();
        TupleDouble meansAuprc = new TupleDouble();
        TupleDouble meansAuroc = new TupleDouble();
        parseFold(path, posNeg, meansAuprc, meansAuroc);
        System.out.println(posNeg.getPositive()+" positive");
        System.out.println(posNeg.getNegative()+" negative");
        System.out.println(meansAuprc.getMean()+" meanprc");
        System.out.println(meansAuprc.getWeighted()+" weightedprc");
        System.out.println(meansAuroc.getMean()+" meanroc");
        System.out.println(meansAuroc.getWeighted()+" weightedroc");
        
    }
    
    
    public static void main(String[] args) throws IOException{
        String path = "/Users/katie/NetBeansProjects/weka/trunk/packages/internal/ensemblesOfNestedDichotomies/out/nd/pageBlocksNDoutputC451";
        TupleInt tuple = new TupleInt();
        double mean = 0;
        double weightedMean = 0;
        ParseDataND parseDataND = new ParseDataND();
        
       // parseDataND.parseFold(path, tuple, mean, weightedMean);
       parseDataND.printStatistics(path);
    }
}
