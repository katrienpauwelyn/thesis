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
import java.util.HashMap;
import statics.Path;

/**
 *
 * @author katie
 * Bij de ensembles hebben we 50 bags en 50 uitvoer files. We combineren deze 50 
 * uitvoer files in 1 gemiddelde (average) file. We behouden enkel de leaf nodes en filteren
 * de interne nodes eruit. 
 */
public class Averager {
    
    
    public static void makeAllAverageArff() throws FileNotFoundException, IOException{
        for(String dataset: Path.datasets){
            System.out.println(dataset);
        //    String outputFlat = Path.pathPinac+dataset+"/flat/average.test.pred.arff";
            String outputKmeans = Path.pathPinac+dataset+"/kmeans/averageKMeansFull.test.pred.arff";
         //   String outputRHam = Path.pathPinac+dataset+"/averageRHam.test.pred.arff";
         //   makeAverageArff(Path.pathPinac+dataset+"/flat/settingsFlat", ".test.pred.arff", outputFlat, dataset);
            makeAverageArff(Path.pathPinac+dataset+"/kmeans/settingsBag","Full.test.pred.arff",outputKmeans,dataset);
         //   makeAverageArff(Path.pathPinac+dataset+"/settingsBag",".test.pred.arff",outputRHam,dataset);
        }
    }
    
    public static void test() throws IOException{
        for(String dataset: Path.postAverageDatasets){
                 String outputHier = Path.pathPinac+dataset+"/unfilteredAverage.test.pred.arff";
                 String outputFlat = Path.pathPinac+dataset+"/flat/unfilteredAverage.test.pred.arff";
                  makeAverageArff(Path.pathPinac+dataset+"/settingsBag",".test.pred.arff",outputHier,dataset);
                  makeAverageArff(Path.pathPinac+dataset+"/flat/settingsFlat",".test.pred.arff",outputFlat,dataset);
        }
     
    }

    //alle indexen hebben @ATTRIBUTE
    //Original-p- => begin van de voorspellingen => index nodig 
    //maar: andere hierarchieen in andere bags. Waarschijnlijk enkel de leaf nodes nodig?
    //de indices nodig van de actual leafs, indices van predicted leafs
    
    //black@red@white@yellow,1,1,0,0,1,1,1,1,0,1,1.0,1.0,0.0,0.0,1.0,1.0,1.0,1.0,1.0,0.0,"0"
    // settingsBag3.test.pred.arff
    /**
     * Maakt een file aan die de gemiddelde voorspellingen bevat van alle bags.
     * Filtert direct de innerlijke knopen er uit en laat alleen de leaf nodes over.
     * 
     * @param firstHalf: het pad en eerste deel van de naam van de input file
     * @param secondHalf: de tweede helft van de naam van de input file. 
     * Tussen de eerste en tweede helft komt het nummer van de bag.
     * @param output: het pad en naam van de output file
     * @param dataset: de naam van de dataset
     */
    public static void makeAverageArff(String firstHalf, String secondHalf, String output, 
            String dataset) throws FileNotFoundException, IOException{
        PrintStream stream = new PrintStream(new File(output));
        BufferedReader[] readers = new BufferedReader[Path.nbBags];
        MapIndices[] mapInds = new MapIndices[Path.nbBags];
        for(int i = 0; i<Path.nbBags; i++){
            readers[i]=new BufferedReader(new FileReader(firstHalf+i+secondHalf));;
            mapInds[i] = getIndicesLeaves(readers[i]);
        }
        String line;
        HashMap<String, Double> sumPrediction = new HashMap();      
        String[] volgordeLeafs = new String[mapInds[0].mapActual.size()];//opsomming van alle leafs
        int in = 0;
        for(String s: mapInds[0].mapActual.keySet()){
            volgordeLeafs[in++] = s.replace("{1,0}","");
            sumPrediction.put(s, 0.0);
        }    
        printHeader(stream, volgordeLeafs, dataset );
        
        while((line=readers[0].readLine())!=null && !line.isEmpty()){
            HashMap<String, Double> sumPredCopy = (HashMap<String, Double>) sumPrediction.clone();
            addToSum(volgordeLeafs, line, sumPredCopy, mapInds[0]);
            for(int i = 1; i<Path.nbBags; i++){
                line = readers[i].readLine();
                //System.out.println("BAg " +i);
                addToSum(volgordeLeafs, line, sumPredCopy, mapInds[i]);
            }
            String printLine = makeStringLine(sumPredCopy, volgordeLeafs, mapInds[Path.nbBags-1].mapActual, line);
            stream.println(printLine);
        }
    }
    
    /**
     * maak een map met <naamLeafKlasse, index> (aparte map voor actual values en predicted values)
     * @param reader: de reader verwijst naar een file met voorspellingen en actual values.
     * @post reader staat gepositioneerd op de eerste lijn met predicties
     */
    public static MapIndices getIndicesLeaves(BufferedReader reader) throws IOException{
        String line;
       MapIndices mI = new MapIndices();
        while(!reader.readLine().contains("@ATTRIBUTE")){} //class-a
        String leaf;
        int index = 0;      //op index 0 staan de labels
        while(!(line=reader.readLine()).contains("@ATTRIBUTE Original-p-")){ //actuals
            index++;
            leaf = isLeaf(line);
            if(leaf!=null){
                leaf = leaf.replace("numeric","").replace("{1,0}","");
               mI.addActual(leaf, index);
            }
        }
        index++;
        while((line=reader.readLine()).contains("@ATTRIBUTE Original-p-")){//de eerste -p- heeft alle klassen moet dus niet gecheckt worden
              leaf = isLeaf(line);
              index++;
            if(leaf!=null){
                leaf = leaf.replace("numeric","").replace("{1,0}","");
               mI.addPredicted(leaf, index);
            }
        }
        while(!reader.readLine().contains("@DATA")){}
        return mI;
    }

    /**
     *check of line een leaf node bevat. Zo ja, return de naam van de leaf node.
     * Zo nee, return null.
     *voorbeeld: als line = 'Original-p-black-orange' => return null
     *           als line = 'Original-p-blue'  => return blue
     */
    private static String isLeaf(String line) {
        String[] splita = line.split("-a-");
        String[] splitp = line.split("-p-");
        if(splita.length==2){
            splita = splita[1].split(("-"));
            if(splita.length==1){
                return splita[0].split(" ")[0];
            }
            
        } else if(splitp.length==2){
            splitp = splitp[1].split("-");      //splitp = splitp[1].split(",");
            if(splitp.length==1){
                return splitp[0].split(" ")[0];
            }
        }
        return null;
    }
    
    /**
     * Voegt de predicties van een lijn toe aan de (voorlopige) som van de predicties
     * @param volgordeLeafs: bv [blue, red, white]: blue heeft index 0, red 1, white 2
     * @param line: de instance (met actual en predicties), toe te voegen
     * @param sumPrediction : som van de predicties tot nu toe
     * @param indices: de mapping tussen de leaf names en hun index in de line
     * @return de geupdate sumprediction
     */
    public static HashMap<String, Double> addToSum(String[] volgordeLeafs, String line, HashMap<String, 
            Double> sumPrediction, MapIndices indices){
        String[] split = line.split(",");
        HashMap<String, Integer> predictions = indices.mapPredicted;
        for(int i = 0; i<volgordeLeafs.length; i++){
            String currentLeaf = volgordeLeafs[i];
            int index = 0;
            try{
             index = predictions.get(currentLeaf);
            }catch(Exception e){
                System.out.println("leaf= "+currentLeaf);
                System.out.println("line= "+line);
                System.out.println("predictions");
                for(String s: predictions.keySet()){
                    System.out.println(s);
                }
            }
            String value = split[index];
            double current = sumPrediction.get(currentLeaf);
            sumPrediction.put(currentLeaf, current+Double.parseDouble(value));
        }
        return sumPrediction;
    }

    /**
     * returnt een string met de nieuwe arff waarden 
     * eerst worden de actual values afgedrukt van de leafs, erna het gemiddelde van de predicted values
     * @param sumPredCopy: de som van de predicted values voor alle leafs
     * @param volgordeLeafs: de volgorde waarin de leafs worden afgedrukt
     * @param mapActual: de actual values voor alle leafs
     */
    private static String makeStringLine(HashMap<String, Double> sumPredCopy, 
            String[] volgordeLeafs, HashMap<String, Integer> mapActual, String line) {
        String outputLine = "";
        String[] split = line.split(",");
        for(int i = 0; i<volgordeLeafs.length; i++){    //eerst de actual values afdrukken
            outputLine = outputLine.concat(split[(mapActual.get(volgordeLeafs[i]))].toString()); 
            outputLine = outputLine.concat(",");
        }
        for(int i = 0; i<volgordeLeafs.length; i++){
            double d = sumPredCopy.get(volgordeLeafs[i]) / Path.nbBags;
            outputLine = outputLine.concat(Double.toString(d));
            outputLine = outputLine.concat(",");
        }
        return outputLine.substring(0, outputLine.length()-1);
    }
    
    
    public static void main(String[] args) throws IOException{
       makeAllAverageArff();
    }

    private static void printHeader(PrintStream stream, String[] volgordeLeafs, String relation) {
        stream.println("@RELATION '"+relation+"'");
        stream.println();
        for(int i = 0; i<volgordeLeafs.length; i++){
            stream.println("@ATTRIBUTE class-a-"+volgordeLeafs[i]);
        }
        for(int i = 0; i<volgordeLeafs.length; i++){
            stream.println("@ATTRIBUTE Original-p-"+volgordeLeafs[i]);
        }
        stream.println("@DATA");
    }
}
