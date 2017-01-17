/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statistics;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author katie
 */
public class Statistics {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        String path = "/Users/katie/NetBeansProjects/weka/trunk/packages"+
                "/internal/ensemblesOfNestedDichotomies/katie/test.test.pred.arff";
        Statistics statistics = new Statistics();
        statistics.printAllStatistics(path);
    }
    
    
    //original is voorspelde, class-a effectieve klasse
    //berekent de accuracy van alle gegeven data (is de bedoeling dat het gebruikt wordt 
    //met alle data van de folds samen
    public float calculateAccuracy(String path) throws FileNotFoundException, IOException{
         BufferedReader in = new BufferedReader(new FileReader(path));
         String line;
         boolean data = false;
         String[] splitLine;
         float correctlyClassified = 0;
         float faultyClassified = 0;
         
         while((line=in.readLine())!=null){
             if(data){
                 splitLine = line.split(",");
                 if(splitLine[0].equals(splitLine[1])){
                     correctlyClassified++;
                 } else {
                     faultyClassified++;
                 }
             } else if(line.contains("@DATA")){
                 data = true;
             }
         }
         return correctlyClassified / (correctlyClassified+faultyClassified);
    }
    
    public float getIncorrectlyClassified(String path) throws IOException{
        return (float) (1.0-calculateAccuracy(path));
    }
    
    
    //tp/(tp+fp)
    public float[] calculatePrecision(int[][] confusion, ArrayList<String> classes) throws FileNotFoundException, IOException{
         float[] out = new float[classes.size()];
         for(int i = 0; i<classes.size(); i++){
             out[i] = getTP(confusion, i)/(getTP(confusion, i)+getFP(confusion, i));
         }
        return out;
    }
    
     public float[] calculatePrecision(String path, ArrayList<String> classes) throws FileNotFoundException, IOException{
         return calculatePrecision(confusionMatrix(path, classes), classes);
     }
    
    //tp/(tp+fn)
    public float[] calculateRecall(String path, ArrayList<String> classes) throws FileNotFoundException, IOException{
        return calculateRecall(confusionMatrix(path, classes), classes);
    }
    
    public float[] calculateRecall(int[][] confusion, ArrayList<String> classes) throws IOException{
         float[] out = new float[classes.size()];
         for(int i = 0; i<classes.size(); i++){
             out[i] = getTP(confusion, i)/(getTP(confusion, i)+getFN(confusion, i));
         }
        return out;
    }
    

    //returnt een arraylist van alle klassen
    private ArrayList<String> getListAllClasses(String path) throws FileNotFoundException, IOException{
         BufferedReader in = new BufferedReader(new FileReader(path));
         String line= in.readLine();
         boolean data = false;
         String[] splitLine;
         ArrayList<String> classes = new ArrayList<String>();
             while(!line.contains("@ATTRIBUTE Original-class")){
                 line = in.readLine();
         }
            splitLine = line.split(" ");
            String subString = splitLine[splitLine.length-1];
            subString = subString.substring(1, subString.length()-1);
            splitLine = subString.split(",");
            classes.addAll(Arrays.asList(splitLine));
         return classes;
    }
    
    //geef een matrix terug met de consufion matrix. Kijk naar 'classes' om de index
    //van de overeenkomende klasse te weten
    public int[][] confusionMatrix(String path, ArrayList<String> classes) throws IOException{
          BufferedReader in = new BufferedReader(new FileReader(path));
          String line;
         boolean data = false;
         String[] splitLine;
         int[][] matrix = new int[classes.size()][classes.size()];
         int indAct;
         int indPred;
            while((line=in.readLine())!=null){
             if(data){
                 splitLine = line.split(",");
                 indAct = classes.indexOf(splitLine[0]);
                 indPred = classes.indexOf(splitLine[1]);
                 matrix[indAct][indPred]++;
             } else if(line.contains("@DATA")){
                 data = true;
             }
         }
        return matrix;
    }
    
    //index is de index van de klasse (kolom)
    private int getTP(int[][] matrix, int index){
        return matrix[index][index];
    }
    
    private int getFP(int[][] matrix, int index){
        int out = 0;
        for(int i = 0; i< matrix.length; i++){
            if(i!=index){
                out+=matrix[i][index];
            }
        }
        return out;
    }
    
    private int getFN(int[][] matrix, int index){
        int out = 0;
        for(int i = 0; i<matrix.length; i++){
            if(i!=index){
                out+=matrix[index][i];
            }
        }
        return out;
    }
    
    private void printMatrix(int[][] matrix){
        for(int j = 0; j<matrix. length; j++){
            String s = "";
            for(int i = 0; i<matrix[0].length; i++){
            
            s = s+Integer.toString(matrix[j][i])+"\t";
        }
            System.out.println(s);
        }
    }
    
    private void printConfusionMatrix(int[][] matrix, ArrayList<String> classes){
        System.out.println("CONFUSION MATRIX");
        String cla = "";
        for(int x=0; x<classes.size(); x++){
            cla+=classes.get(x)+"\t";
        }
        System.out.println(cla);
        for(int j = 0; j<matrix.length; j++){   
            String s = "";
            for(int i = 0; i<matrix[0].length; i++){
            
            s = s+Integer.toString(matrix[j][i])+"\t\t\t";
        }
            s+=classes.get(j);
            System.out.println(s);
        }
    }
    
    private void printAccuracy(float accuracy){
        System.out.println("CORRECTLY CLASSIFIED INSTANCES "+Float.toString(accuracy));
        System.out.println("INCORRECTLY CLASSIFIED INSTANCES "+Float.toString((float)1.0-accuracy));
    }
    
    private void printPrecisionRecall(float[] precision, float[] recall, ArrayList<String> classes){
        System.out.println("PRECISION \t  RECALL \t CLASS");
        for(int i = 0; i<classes.size(); i++){
            System.out.println(Float.toString(precision[i])+"\t\t"+Float.toString(recall[i])+"\t\t"+classes.get(i));
        }
    }
    
    private void printAllStatistics(String path) throws IOException{
        printAccuracy(calculateAccuracy(path));
        ArrayList<String> classes = getListAllClasses(path);
        printPrecisionRecall(calculatePrecision(path, classes), calculateRecall(path, classes), classes);
        int[][] confusion = confusionMatrix(path, classes);
        printConfusionMatrix(confusion, classes);
    }
    
    private void printAllStatisticsWithoutHierarchy(String path) throws IOException{
        printAccuracy(calculateAccuracy(path));
        ArrayList<String> classes = getListAllClasses(path);
        ArrayList<String> classesWithoutHier = new ArrayList<String>();
        for(String c: classes){
            String[] line = c.split("/");
            classesWithoutHier.add(line[line.length-1]);
        }
        printPrecisionRecall(calculatePrecision(path, classes), calculateRecall(path, classes), classesWithoutHier);
        int[][] confusion = confusionMatrix(path, classes);
        printConfusionMatrix(confusion, classesWithoutHier);
    }
}
