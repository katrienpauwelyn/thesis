/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extractRankingMeasures;

import com.evaluation.RankingBasedMeasures;
import static com.multilabel.Main.NUMBER_OF_LABELS;
import com.output.MultiLabelOutput;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import weka.core.Instance;

/**
 *
 * @author katie
 */
public class ArffParser {
    
    /**
     * Returnt hoe veel instances er in de gegeven file zijn.
     */
    public static int getNbInstances(String path) throws FileNotFoundException, IOException{
        int nbInstances = 0;
        BufferedReader reader = new BufferedReader(new FileReader(path));
        while(!reader.readLine().contains("@DATA")){}
        String line;
        while((line=reader.readLine())!=null && !line.isEmpty()){
            nbInstances++;
        }
        reader.close();
        return nbInstances;
    }
    
    /**
     * Returnt hoe veel labels er in de gegeven file zijn.
     */
    public static int getNbLabels(String path) throws FileNotFoundException, IOException{
        int nbLabels = 0;
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String line;
        while(!(line = reader.readLine()).contains("@DATA")){
            if(line.contains("@ATTRIBUTE")){
                nbLabels++;
            }
        }
           nbLabels /= 2;
           reader.close();
           return nbLabels;
    }
    
    /**
     * 
     * @param path: path naar de average.arff file
     * @param nbLabels: aantal labels
     * @param nbInstances: aantal instances
     * @param predictions: lege array, wordt gevuld met de predictions
     * @return de actual values, per instance een array van booleans (1 = true; 0=false)
     */
    public static boolean[][] getActualValues(String path, int nbLabels, int nbInstances, MultiLabelOutput[] predictions) throws FileNotFoundException, IOException{
        boolean[][] realPred = new boolean[nbInstances][nbLabels];
        BufferedReader reader = new BufferedReader(new FileReader(path));
        while(!reader.readLine().contains("@DATA")){}
        String line;
        String[] parsed;
        int instanceNb=0;
        
        
        while((line=reader.readLine())!=null && !line.isEmpty()){
            parsed = line.split(",");
            //----true labels----
            for(int i = 0; i<nbLabels; i++){ 
                if(parsed[i].equals("1")){
                    realPred[instanceNb][i] = true;
                } else {
                    realPred[instanceNb][i] = false;
                }
            }
            //----predictions----
            double[] predLine = new double[nbLabels];
            for(int i = 0; i<nbLabels; i++){
                predLine[i] = Double.parseDouble(parsed[i+nbLabels]);
            }
            predictions[instanceNb] = new MultiLabelOutput(predLine, 0.0);
            
            instanceNb++;
        }
        
        reader.close();
        return realPred;
    }

    public static String printArrayBooleans(boolean[] array){
        String out = "";
        for(int i = 0; i<array.length; i++){
            out += array[i]+" ";
        }
        return out;
    }
    
    public static void main(String[] args) throws IOException{
        
        String p = "/Users/katie/Desktop/argh/average.test.pred.arff";
      /*  String path = "/Users/katie/Desktop/temp/average.test.pred.arff";
        int nbInstances = getNbInstances(path);
        int nbLabels = getNbLabels(path);
        MultiLabelOutput[] predictions = new MultiLabelOutput[nbInstances];
        boolean[][] actual = getActualValues(path, nbLabels, nbInstances, predictions);
       /* System.out.println("predictions");
        for(int i = 0; i<nbInstances; i++){
            System.out.println(predictions[i].confidencesToString());
        }
        System.out.println();
        System.out.println("actual");
        for(int i = 0; i<nbInstances; i++){
            System.out.println(printArrayBooleans(actual[i]));
        }*/
        //getActualValues(String path, int nbLabels, int nbInstances, MultiLabelOutput[] predictions)
        
       /* RankingBasedMeasures ranking = new RankingBasedMeasures(predictions, actual);
        System.out.println(ranking.getOneError());
        System.out.println(ranking.getCoverage());
        System.out.println(ranking.getRankingLoss());
        System.out.println(ranking.getAvgPrecision());*/
    }
    
}
