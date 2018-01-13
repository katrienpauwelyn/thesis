/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wilcoxon;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import staticData.Path;

/**
 *[DEPRECATED]
 * @author katie
 */
public class CalculateWilcoxon {
    
    /**
     * vertrekken van de latex file: die heeft tenminste al alles naast elkaar
     * 
     */
    
    public static void doAll() throws IOException{
        for(String classifier: Path.classifiers){
            WilcoxonMap[] coxMap = new WilcoxonMap[5];
            for(int i = 0; i<5; i++){
                coxMap[i] = new WilcoxonMap();
            }
            String path = "/Users/katie/thesisoutput/out/"+classifier+"/alatex.txt";
            convertFileToTuples(path, coxMap);
            printResults("/Users/katie/thesisoutput/out/"+classifier, coxMap);
        }
    }
    
    
    /**
     * Converteer een latex file (resultaten van een classifier) naar wilcoxonTuples
     * @param path: vb is /Users/katie/thesisoutput/out/randomPair/alatex.txt 
     * de latex file die de resultaten bevat
     * @param tuples: hierin komen de tuples. Lengte 5.
     * [0]: gem auprc; [1]: gew gem auprc; [2]: gem auroc; [3]: gew gem auroc; [4]: acc
     * Double: de absolute waarde van het verschil (nd-clus) (om op te sorteren)
     * @param classifier
     * @throws FileNotFoundException 
     */
    public static void convertFileToTuples(String path, WilcoxonMap[] tuples) throws FileNotFoundException, IOException{
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String line;
        
        while(!reader.readLine().contains("auprc")){}
        while(!(line=reader.readLine()).contains("\\end")){
           doAu(tuples[0], tuples[1], line);
        }
        
        while(!reader.readLine().contains("auroc")){}
        while(!(line=reader.readLine()).contains("\\end")){
           doAu(tuples[2], tuples[3], line); 
        }
        
        while(!reader.readLine().contains("Nauwkeurigheid")){}
        while(!(line=reader.readLine()).contains("\\end")){
           doAcc(tuples[4], line);
        }
    }
    
    /**
     * 
     * @param average: voeg hier het gemiddelde aan toe
     * @param weightedAverage: voeg hier het gewogen gemiddelde aan toe
     * @param line : hieruit worden de gegevens gehaald
     */
    private static void doAu(WilcoxonMap average, WilcoxonMap weightedAverage, String line){
         String[] split = line.split(" ");
         WilcoxonTuple wAverage = new WilcoxonTuple(split[2], split[4]);
         average.put(wAverage.getAbsoluteDifference(), wAverage);
         WilcoxonTuple wWeightedAverage = new WilcoxonTuple(split[6], split[8]);
         weightedAverage.put(wWeightedAverage.getAbsoluteDifference(), wWeightedAverage);
    }
    
    private static void doAcc(WilcoxonMap accuracy, String line){
        String[] split = line.split(" ");
        WilcoxonTuple w = new WilcoxonTuple(split[2], split[4]);
        accuracy.put(w.getAbsoluteDifference(), w);
    }
    
    // [0]: gem auprc; [1]: gew gem auprc; [2]: gem auroc; [3]: gew gem auroc; [4]: acc
    /**
     * Druk de wilcoxon resultaten af op een file.
     * @param path
     * @param tuples
     * @throws FileNotFoundException 
     */
    private static void printResults(String path, WilcoxonMap[] tuples) throws FileNotFoundException{
        PrintStream stream = new PrintStream(new File(path+"/aWilcoxon.txt"));
        stream.println("gem auprc nd score: "+tuples[0].calculateWilcoxonScore()+ " size: "+tuples[0].getSize());
        stream.println("gew gem auprc nd score: "+tuples[1].calculateWilcoxonScore()+ " size: "+tuples[1].getSize());
        stream.println("gem auroc nd score: "+tuples[2].calculateWilcoxonScore()+ " size: "+tuples[2].getSize());
        stream.println("gew gem auroc nd score: "+tuples[3].calculateWilcoxonScore()+ " size: "+tuples[3].getSize());
        stream.println("gem acc nd score: "+tuples[4].calculateWilcoxonScore()+ " size: "+tuples[4].getSize());
        stream.close();
        
    }
    
     
    
    public static void main(String[] args) throws IOException{
      doAll();
    }
}
