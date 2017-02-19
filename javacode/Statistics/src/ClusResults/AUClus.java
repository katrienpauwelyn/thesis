package ClusResults;

import dataTypes.TupleFloat;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import staticData.Path;

/**
 *
 * @author katie
 */
public class AUClus {
    
    /**
     * Berekent de AUPRC van de gegeven file. (getest)
     * Neemt het gemiddelde van alle AUPRCs van alle leaf nodes (niet de internal nodes)
     */
    public TupleFloat getAUPRCandAUROC(String path) throws FileNotFoundException, IOException{
        TupleFloat tupleOut = new TupleFloat(); //first is prc, second is roc
        BufferedReader in = new BufferedReader(new FileReader(path));
        boolean testError = false;
        boolean startNodes = false;
        String line;
        while(!testError){
            line = in.readLine();
            if(line.contains("Testing error")){
                testError=true;
            }
        }
        while(!startNodes){
            line = in.readLine();
            if(line.contains("Pooled")){
                startNodes=true;
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
    
    
 /*   public static void main(String[] args) throws IOException{
        String audiology = "/Users/katie/thesisoutput/out/classBalanced/audiology/asettings/S0settingsFold1.hsc.combined.out";
        String krkopt = "/Users/katie/thesisoutput/out/classBalanced/krkopt/asettings/S0settingsFold1.hsc.combined.out";
        AUClus c = new AUClus();
        TupleFloat audF = c.getWeightedAUPRCandAUROC(audiology);
        System.out.println("aud");
        System.out.println(audF.print());
        TupleFloat krkF = c.getWeightedAUPRCandAUROC(krkopt);
        System.out.println("krk");
        System.out.println(krkF.print());
        
        
    }*/
    
        /**
     * Berekent de gewogen AUPRC van de gegeven file. (getest)
     * Neemt het gewogen gemiddelde van alle AUPRCs van alle leaf nodes (niet de internal nodes)
     */
    public TupleFloat getWeightedAUPRCandAUROC(String path) throws FileNotFoundException, IOException{
        TupleFloat outTuple = new TupleFloat();
        BufferedReader in = new BufferedReader(new FileReader(path));
        boolean testError = false;
        boolean startNodes = false;
        String line;
        while(!testError){
            line = in.readLine();
            if(line.contains("Testing error")){
                testError=true;
            }
        }
        while(!startNodes){
            line = in.readLine();
            if(line.contains("Pooled")){
                startNodes=true;
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
    
    //berekent voor iedere fold het gemiddelde auprc en auroc
    public TupleFloat[] getAUsForAllFolds(String path, int seed) throws IOException{
        TupleFloat[] outAU = new TupleFloat[Path.nbFolds];
        String newPath ;
        for(int i = 0; i<Path.nbFolds; i++){
            newPath = path+"/asettings/S"+seed+"settingsFold"+Integer.toString(i+1)+".out";
            outAU[i] = getAUPRCandAUROC(newPath);
        }
        return outAU;
    }
    
    //berekent voor iedere fold het gewogen gemiddelde auprc en auroc
    public TupleFloat[] getAUsWeightedForAllFolds(String path, int seed) throws IOException{
        TupleFloat[] outAU = new TupleFloat[Path.nbFolds];
        String newPath ;
        for(int i = 0; i<Path.nbFolds; i++){
            newPath = path+"/asettings/S"+seed+"settingsFold"+Integer.toString(i+1)+".out";
            outAU[i] = getWeightedAUPRCandAUROC(newPath);
        }
        return outAU;
    }
    
     //berekent voor iedere fold het gemiddelde auprc (voor ensembles)
 /*   public float[] getAUPRCsForAllFoldsEnsembles(String path) throws IOException{
        float[] outAU = new float[Path.nbFolds];
        String newPath ;
        for(int i = 0; i<Path.nbFolds; i++){
            newPath = path+"/settingsEnsemble"+Integer.toString(i+1)+".out";
            outAU[i] = getAUPRC(newPath);
        }
        return outAU;
    }*/
    
    //berekent voor iedere fold het gewogen gemiddelde auprc (voor ensembles)
  /*  public float[] getAUPRCsWeightedForAllFoldsEnsembles(String path) throws IOException{
                float[] outAU = new float[Path.nbFolds];
        String newPath ;
        for(int i = 0; i<Path.nbFolds; i++){
            newPath = path+"/settingsEnsemble"+Integer.toString(i+1)+".out";
            outAU[i] = getWeightedAUPRC(newPath);
        }
        return outAU;
    }*/
    
    /**
     * Checks if the given String contains a leaf node or an internal node. Return
     * true if it is a leaf node. (getest)
     * @param line
     * @return 
     */
    public boolean checkLeafNode(String line){
    
        String[] spatie = line.split(" ");
        String node = "";//bevat een node
        for(int i = 0; i<spatie.length; i++){
            if(!spatie[i].isEmpty()){
                node = spatie[i+1];
                break;
            }
        }
        String[] slash = node.split("/");
        String last = slash[slash.length-1];
        if(!line.contains("-")){
            return checkLeafNodeWithoutHierarchy(last);
        }
        return !last.contains("-");
    }
    
    //te gebruiken bij hsc waar de namen van de de klassen korter zijn en de "-" verwijderd zijn uit de namen
    private boolean checkLeafNodeWithoutHierarchy(String element){
        element=element.replace(",", "");
        return element.length()==1;
    }
    
    /**
     * returnt de AUPRC uit de string (getest)
     */
    private float getAuprcFromLine(String line){
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
    private float getAurocFromLine(String line){
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
    private float getWeightedAuprcFromLine(String line){
        String[] spatie = line.split(" ");
       return getAuprcFromLine(line) * Float.parseFloat(spatie[spatie.length-1]);
    }
    
     /**
     * returnt de AUROC uit de string vermenigvuldigd met zijn frequentie
     */
    private float getWeightedAurocFromLine(String line){
        String[] spatie = line.split(" ");
       return getAurocFromLine(line) * Float.parseFloat(spatie[spatie.length-1]);
    }
    
    //berekent het gemiddelde van een array
    public TupleFloat getMeanOfArray(TupleFloat[] means){
       TupleFloat outTuple = new TupleFloat(); 
       for(int i = 0; i<means.length; i++){
            outTuple.incrementFirstWith(means[i].getFirst());
            outTuple.incrementSecondWith(means[i].getSecond());
        }
        outTuple.divideBothWith(means.length);
        return outTuple;
    }

    
    //returnt het gemiddelde over alle folds van de gewogen gemiddelde auprc & auroc
    //per dataset
    public TupleFloat getWeightedAUForAllFolds(String path, int seed) throws IOException{
        TupleFloat[] allWeightedAUs = getAUsWeightedForAllFolds(path, seed);
        return getMeanOfArray(allWeightedAUs);
    }
    
    //returnt het gemiddelde over alle folds van de gemiddelde auprc & auroc
    //per dataset
    public TupleFloat getMeanAUForAllFolds(String path, int seed) throws IOException{
        TupleFloat[] allMeanAUPRCs = getAUsForAllFolds(path, seed);
        return getMeanOfArray(allMeanAUPRCs);
    }
    
       //returnt het gemiddelde over alle folds van de gewogen gemiddelde auprc (voor ensembles)
    //per dataset
 /*   public float getWeightedAUForAllFoldsEnsemble(String path) throws IOException{
        float[] allWeightedAUPRCs = getAUPRCsWeightedForAllFoldsEnsembles(path);
        return getMeanOfArray(allWeightedAUPRCs);
    }*/
    
    //returnt het gemiddelde over alle folds van de gemiddelde auprc (voor ensembles)
    //per dataset
 /*   public float getMeanAUPRCForAllFoldsEnsemble(String path) throws IOException{
        float[] allMeanAUPRCs = getAUPRCsForAllFoldsEnsembles(path);
        return getMeanOfArray(allMeanAUPRCs);
    }
*/
    
 /**   public void test(String path) throws IOException{
        TupleFloat[] mean = getAUsForAllFolds(path);
        TupleFloat[] weighted = getAUsWeightedForAllFolds(path);
        System.out.println("mean:");
        for(int i = 0; i<mean.length; i++){
            System.out.println("auprc: "+mean[i].getFirst()+"   auroc: "+mean[i].getSecond());
        }
        System.out.println("mean all folds prc: "+getMeanOfArray(mean).getFirst() + " roc: "+getMeanOfArray(mean).getSecond());
        System.out.println();
        System.out.println("weighted mean");
        for(int i = 0; i<weighted.length; i++){
            System.out.println("auprc: "+weighted[i].getFirst()+"   auroc: "+weighted[i].getSecond());
        }
        System.out.println("mean all weighted folds prc: "+getMeanOfArray(weighted).getFirst() +" roc: "+getMeanOfArray(weighted).getSecond());
    }
    */
    
  
}
