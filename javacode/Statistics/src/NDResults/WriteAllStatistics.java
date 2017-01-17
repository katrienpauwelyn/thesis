/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NDResults;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import staticData.Path;

/**
 *
 * Schrijft alle statistieken van alle classifiers, datasets en folds naar files.
 * Bekomen via nested dichotomies.
 * 1 outputfile per classifier. (path/classifierStatisticsND)
 * 
 * Getest op pageBlocksNDoutputC45.
 */
public class WriteAllStatistics {
    
    //Schrijf alle resultaten van alle classifiers naar files
    public void writeAllNDResultsToFiles() throws FileNotFoundException, IOException{
        for(String classifier: Path.classifiers){
            writeNdResultsClassifier(Path.path+"/"+classifier, classifier);
        }
    }
    
    //Schrijf de resultaten van alle datasets (en hun folds) van een classifier naar een file
    public void writeNdResultsClassifier(String pathForFile, String classifier) throws FileNotFoundException, IOException{
        PrintStream stream = new PrintStream(new File(pathForFile+"/"+classifier+"StatisticsND"));
        stream.println("Statistics of C45:");
        for(String dataset: Path.datasets){
            writeNdResultsFold(stream, pathForFile+"/"+dataset+"NDoutputC45", dataset);
        }
     /*   stream.println();
        stream.println();
        stream.println("Statistics of Logistics:");
         for(String dataset: Path.datasets){
            writeNdResultsFold(stream, pathForFile+"/"+dataset+"NDoutputLogistic", dataset);
        }*/
        stream.close();
    }
    
    //Schrijf de resultaten van 10 folds samen naar een file.
    public void writeNdResultsFold(PrintStream stream, String pathFold, String dataset) throws IOException{
        TupleInt posNeg = new TupleInt();
        TupleDouble meansPrc = new TupleDouble();
        TupleDouble meansRoc = new TupleDouble();
        calculateStatisticsFold(pathFold, posNeg, meansPrc, meansRoc);
        
        stream.println(dataset + "\t"+ "Mean auprc: "+meansPrc.getMean()/10+ "\t Weighted mean auprc: "+
                meansPrc.getWeighted()/10+ "\t Mean auroc: "+meansRoc.getMean()/10 + "\t Weighted mean auroc: "+
                meansRoc.getWeighted()/10+"\t accuracy: "+posNeg.getAccuracy());
    }
    
   
    //Bereken de gemiddelden en de nauwkeurigheid voor 10 folds samen.
    private void calculateStatisticsFold(String path, TupleInt posNeg, TupleDouble meansPrc, TupleDouble meansRoc)
            throws IOException{
        ParseDataND parseData = new ParseDataND();
        for(int i = 1; i<11; i++){
            parseData.parseFold(path+Integer.toString(i), posNeg, meansPrc, meansRoc);
        }
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException{
        WriteAllStatistics write = new WriteAllStatistics();
       // String path = "/Users/katie/NetBeansProjects/weka/trunk/packages/internal/ensemblesOfNestedDichotomies/out/nd/pageBlocksNDoutputC45";
       // PrintStream print = new PrintStream(new File("/Users/katie/NetBeansProjects/weka/trunk/packages/internal/temp"));
        //write.writeNdResultsFold(print, path, "pageBlocks");
        write.writeAllNDResultsToFiles();
    }
}
