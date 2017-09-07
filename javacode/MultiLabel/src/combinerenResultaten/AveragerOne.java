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
 * neemt het gemiddelde van de output files en filtert de interne knopen er uit. Voor one moet er niet gecombineerd worden,
 * enkel de interne knopen worden gefilterd. Dus enkel de leaf nodes blijven nog over.
 */
public class AveragerOne {
    
    
    public static void makeAllAverageArff() throws FileNotFoundException, IOException{
        String path=Path.pathPinac;
        for(String dataset: Path.datasets){
            System.out.println(dataset);
            String output;
            String input;
            
                    //FLAT
                    System.out.println("flat");
                    output=path+dataset+"/one/averageFlatOne.test.pred.arff";
                    input=path+dataset+"/one/settingsFlatOne.test.pred.arff";
                    makeAverageArff(input, output, dataset);
                    
                    //KMEANS
                    System.out.println("kmeans");
                    output = path+dataset+"/one/averageKMeansOne.test.pred.arff";
                    input = path+dataset+"/one/settingsKMeansOne.test.pred.arff";
                    makeAverageArff(input, output, dataset);
                    //RHAM
                    System.out.println("rham");
                    for(int i = 0; i<10; i++){
                        System.out.println(i);
                        output = path+dataset+"/one/averageRHamOne"+i+".test.pred.arff";
                        input = path+dataset+"/one/settingsRHamOne"+i+".test.pred.arff";
                        makeAverageArff(input, output, dataset);
                    }
                    System.out.println("");
        }
    }
    
      
    //alle indexen hebben @ATTRIBUTE
    //Original-p- => begin van de voorspellingen => index nodig 
    //maar: andere hierarchieen in andere bags. Waarschijnlijk enkel de leaf nodes nodig?
    //de indices nodig van de actual leafs, indices van predicted leafs
    
    //black@red@white@yellow,1,1,0,0,1,1,1,1,0,1,1.0,1.0,0.0,0.0,1.0,1.0,1.0,1.0,1.0,0.0,"0"
    // settingsBag3.test.pred.arff
    public static void makeAverageArff(String inputFile,
             String output, 
            String dataset) throws FileNotFoundException, IOException{
        PrintStream stream = new PrintStream(new File(output));
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        MapIndices mapInds = getIndicesLeaves(reader);
       
        String line;
        HashMap<String, Double> sumPrediction = new HashMap();      
        String[] volgordeLeafs = new String[mapInds.mapActual.size()];
        int in = 0;
        for(String s: mapInds.mapActual.keySet()){
            volgordeLeafs[in++] = s;
            sumPrediction.put(s, 0.0);
        }    
        printHeader(stream, volgordeLeafs, dataset );
        
        while((line=reader.readLine())!=null && !line.isEmpty()){
            HashMap<String, Double> sumPredCopy = (HashMap<String, Double>) sumPrediction.clone();
            addToSum(volgordeLeafs, line, sumPredCopy, mapInds);
            String printLine = makeStringLine(sumPredCopy, volgordeLeafs, mapInds.mapActual, line);//notsure
            stream.println(printLine);
            
        }
        
        
        
        
    }
    
    //maak een map met <naamLeafKlasse, index> (aparte map voor actual values en predicted values)
    //reader wordt geplaatst op de eerste instance
    public static MapIndices getIndicesLeaves(BufferedReader reader) throws IOException{
        String line;
       MapIndices mI = new MapIndices();
        while(!reader.readLine().contains("@ATTRIBUTE")){} //class-a
        String leaf;
        int index = 0;
        while(!(line=reader.readLine()).contains("@ATTRIBUTE Original-p-")){ //actuals
            index++;
            leaf = isLeaf(line);
          //  System.out.println(line);
          //  System.out.println(leaf);
            if(leaf!=null){
               mI.addActual(leaf, index);
            }
        }
        index++;
        while((line=reader.readLine()).contains("@ATTRIBUTE Original-p-")){//de eerste -p- heeft alle klassen moet dus niet gecheckt worden
              leaf = isLeaf(line);
              index++;
            if(leaf!=null){
               mI.addPredicted(leaf, index);
            }
        }
        while(!reader.readLine().contains("@DATA")){}
        return mI;
    }

    /**
     *check of line een leaf node bevat en returnt de leaf 
     * @ATTRIBUTE Original-p-black-orange => null
     * @ATTRIBUTE Original-p-blue  => blue
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
            splitp = splitp[1].split("-");
            if(splitp.length==1){
                return splitp[0].split(" ")[0];
            }
            
        }
        return null;
    }
    
    /**
     * Voegt een lijn toe aan de (voorlopige) som van de predicties
     * @param volgordeLeafs: bv [blue, red, white]: blue heeft index 0, red 1, white 2
     * @param line: de instances toe te voegen
     * @param sumPrediction : som van de predicties tot nu toe
     * @param indices: de mapping tussen de leaf names en hun index in de line
     * @return de geupdate sumprediction
     */
    public static HashMap<String, Double> addToSum(String[] volgordeLeafs, String line, HashMap<String, 
            Double> sumPrediction, MapIndices indices){
   //     System.out.println(line);
        String[] split = line.split(",");
        HashMap<String, Integer> predictions = indices.mapPredicted;
        for(int i = 0; i<volgordeLeafs.length; i++){
            String currentLeaf = volgordeLeafs[i];
            int index = predictions.get(currentLeaf);
            String value = split[index];
           // System.out.println(value);
            double current = sumPrediction.get(currentLeaf);
          //  System.out.println(current);
            sumPrediction.put(currentLeaf, current+Double.parseDouble(value));
        }
        return sumPrediction;
    }

    /**
     * returnt een string met de nieuwe arff waarden 
     * eerst worden de actual values afgedrukt van de leafs, erna het gemiddelde van de predicted values
     * @param sumPredCopy: de predictes values voor alle leafs
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
            double d = sumPredCopy.get(volgordeLeafs[i]);
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
        //TODO moet class-a hier nog bij?
        for(int i = 0; i<volgordeLeafs.length; i++){
            stream.println("@ATTRIBUTE class-a-"+volgordeLeafs[i]);
        }
        for(int i = 0; i<volgordeLeafs.length; i++){
            stream.println("@ATTRIBUTE Original-p-"+volgordeLeafs[i]);
        }
        stream.println("@DATA");
    }
}
