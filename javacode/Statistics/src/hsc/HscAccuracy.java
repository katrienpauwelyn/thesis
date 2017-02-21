/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hsc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import staticData.Path;

/**
 *[DEPRECATED]
 * @author katie
 */
public class HscAccuracy {

    
    
    //returns the accuracy of 10 folds (1 seed) combined
  /*  public double getNbPosNegAllFoldsWithHsc(String path, int seed, String achtervoegselOut, boolean hsc,
            String achtervoegselPred) throws IOException{
        String newPathOut;
        String newPathPred;
        AccTuple accuracy = new AccTuple();
        
        for(int i = 0; i<Path.nbFolds; i++){
            if(hsc){
                newPathOut = path+"/asettings/hscS"+seed+"settingsFold"+Integer.toString(i+1)+achtervoegselOut;
                newPathPred = path+"/asettings/hscS"+seed+"settingsFold"+Integer.toString(i+1)+achtervoegselPred;
            } else {
                newPathOut = path+"/asettings/S"+seed+"settingsFold"+Integer.toString(i+1)+achtervoegselOut;
                newPathPred = path+"/asettings/S"+seed+"settingsFold"+Integer.toString(i+1)+achtervoegselPred;
            }
            accuracy.add(getAccuracyOneFile(newPathPred, newPathOut));
        }
        return accuracy.meanAccPercent();
    }
    
    
    //return the mean accuracy of one file (getest)
    public static double getAccuracyOneFile(String pathPred, String pathOut) throws FileNotFoundException, IOException{
        int nbLeaves = 0;
        BufferedReader readerPred =  new BufferedReader(new FileReader(pathPred));
        BufferedReader readerOut =  new BufferedReader(new FileReader(pathOut));
        nbLeaves = getReadersReady(readerPred, readerOut);
        String[] nodes = makeNodesSet(nbLeaves, readerOut);
       
        String line, leaf;
        int index;
        double accLine;
        double highestAcc;
        AccTuple accTuple = new AccTuple();
        while((line=readerPred.readLine())!=null){
            leaf = getLeafPred(line);
            index = getIndex(nodes, leaf);
            highestAcc = getHightestAcc(line, nodes);
            accLine = getAccLine(line, index);
            if(accLine==highestAcc){
                accTuple.add(1);
            } else {
                accTuple.add(0);
            }
            
        }
        return accTuple.meanAcc();
    }
   
    

//zet de readers op de juiste positie. De volgende lijn is een predictie voor pred en de nodes voor out
    private static int getReadersReady(BufferedReader pred, BufferedReader out) throws IOException{
      int nbTrees=0;
      String line;
      while(!(line=out.readLine()).contains("Original: Combined model with")){}
      nbTrees = getNbTrees(line);
       while(!out.readLine().contains("Pooled AUPRC: ")){}
        while(!pred.readLine().contains("@DATA")){}
        return nbTrees;
    }
    
    //lijn is bv:      Original: Combined model with 9 trees with 405 nodes
    private static int getNbTrees(String line){
        String[] parsed = line.split(" ");
        for(int i = 0; i<parsed.length; i++){
            if(parsed[i].contains("with")){
                return Integer.parseInt(parsed[i+1]);
            }
        }
        throw new Error("no number of trees was found");
    }
  
    //maakt String[] met de nodes er in.
    private static String[] makeNodesSet(int nbLeaves, BufferedReader readerOut) throws IOException{
        String[] leaves = new String[nbLeaves];
        
        String line;
        int total = 0;
        
        while(!(line=readerOut.readLine()).isEmpty()){
            leaves[getNumberOfNode(line)] = getLeafOut(line);
            total++;
        }
        int index = 0;
       boolean start = true;
        while(total!=nbLeaves){
            if(leaves[index]==null){
                leaves[index] = getNodeNameInTest(readerOut, index, start);
                total++;
                start=false;
            }
            index++;
            
        }
        return leaves;
    }
    
    //Indien een klasse niet in de training set zat, is er een knoop te kort in de training knopen en moet een knoop gezocht worden in de test knopen
     private static String getNodeNameInTest(BufferedReader readerOut, int index, boolean start) throws IOException {
       String line;
       if(start){
        while(!(line = readerOut.readLine()).contains("Pooled AUPRC:")){}
       }
        line = readerOut.readLine();
       while(getNumberOfNode(line)!=index){
         line = readerOut.readLine();
       }
       return getLeafOut(line);
    }


    
    
    //node:  1: wabxcdefghijlmnopqrstuv, AUROC: 0.5, AUPRC: 0.956522, Freq: 0.956522
    //1 wordt gereturned
    private static int getNumberOfNode(String line) {
        String[] parsed = line.split(" ");
     //   System.out.println(line);
        for(int i = 0; i<parsed.length; i++){
            if(parsed[i].contains(":")){
                return Integer.parseInt(parsed[i].replace(":", "" ));
            }
        }
        throw new Error("index van de node kon niet gevonden worden in "+line);
    }

    
    //returnt het blad indien het een blad bevat
    private static String getLeafOut(String line){
       // System.out.println(line);
        String[] parsed = line.split(" ");
        String leaf=null;
        for(int i = 0; i<parsed.length; i++){
            if(parsed[i].contains(":")){
                leaf = parsed[i+1].replace(",", "");
                return leaf;
            }
        }
            throw new Error("no node is found in: "+line);
    }
    
    //returnt het blad uit een lijn uit de predicties
    private static String getLeafPred(String line){
        return line.split(",")[0];
    }
    
    private static int getIndex(String[] nodes, String leaf){
        for(int index = 0; index<nodes.length; index++){
            if(nodes[index].equals(leaf)){
                return index;
            }
        }
        throw new Error("leaf was not in the nodes ");
    }

    private static double getAccLine(String line, int index) {
        String[] parsed = line.split(",");
        return Double.parseDouble(parsed[index+1]);
    }
    
    
    private static double getHightestAcc(String line, String[] nodes) {
       double highest = 0;
       String[] parsed = line.split(",");
       for(int i = 1; i<parsed.length-1; i++){
           if(Double.parseDouble(parsed[i])>highest && isLeafNode(nodes[i-1])){
               highest = Double.parseDouble(parsed[i]);
           }
       }
       return highest;
    }

   
    public static boolean isLeafNode(String node){
    
        String[] slash = node.split("/");
        String last = slash[slash.length-1];
        if(!node.contains("-")){
            return last.length()==1;
        } 
        return !last.contains("-");
    }
   
    */
    
    public static void main(String[] args) throws IOException{
        String pathOut="/Users/katie/Desktop/temp/S0settingsFold1.hsc.combined.out";
        String pathPred="/Users/katie/Desktop/temp/S0settingsFold1.hsc.combined.test.pred.arff";
      //  System.out.println(getAccuracyOneFile(pathPred, pathOut));
    }
    
}
