/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 *
 * @author katie
 */
public class RunClusMultilabel {
    
    public static void runAllDatasetsWithoutHier() throws FileNotFoundException{
        String time = Path.pathPinac+"timeWithoutHierarchyDeel3.txt";
        PrintStream stream = new PrintStream(new File(time));
        stream.println("time clus without hierarchy");
        
        for(String dataset: Path.multilabelDatasets){
            System.out.println(dataset);
            String filePath = Path.pathPinac+dataset+"/settings.s";
            String[] arg = {"-forest", filePath};
            long startTime = System.nanoTime();
            
            Clus.main(arg);

            long endTime = System.nanoTime();
            long durationMs = (endTime - startTime)/1000000;  
            stream.println(dataset+ " "+durationMs+"ms");
        }
    }
    
    public static void runAllDatasetsWithHier() throws FileNotFoundException{
        String time = Path.pathPinac+"timeClusWithHierarchy.txt";
        PrintStream stream = new PrintStream(new File(time));
        stream.println("time clus with hierarchy");
        
        for(String dataset: Path.multilabelDatasets){
            System.out.println(dataset);
            stream.println(dataset);
            long totalDuration = 0;
            
                for(int i = 0; i<Path.nbBags; i++){
                System.out.println(i);
                String filePath = Path.pathPinac+dataset+"/settingsBag"+i+".s";
                String[] arg = {filePath};
              
                long startTime = System.nanoTime();
                Clus.main(arg);
                long endTime = System.nanoTime();
                long durationMs = (endTime - startTime)/1000000;
                stream.println("tijd bag "+i+" "+durationMs+"ms");
                totalDuration += durationMs;
                
                
            }
                long durationBag = totalDuration/Path.nbBags;
                stream.println("complete runtijd: "+ totalDuration+"ms -- gemiddelde runtijd per bag: "+durationBag+"ms");
            
        }
    }
    
    public static void runClusFlatHierarchy() throws FileNotFoundException{
         String time = Path.pathPinac+"timeClusFlatHierarchyEurlex.txt";
        PrintStream stream = new PrintStream(new File(time));
        stream.println("time clus with flat hierarchy");
        
        for(String dataset: Path.tempTodo){
            System.out.println(dataset);
            stream.println(dataset);
            long totalDuration = 0;
            
                for(int i = 0; i<Path.nbBags; i++){
                System.out.println(i);
                String filePath = Path.pathPinac+dataset+"/flat/settingsFlat"+i+".s";
                String[] arg = {filePath};
              
                long startTime = System.nanoTime();
                Clus.main(arg);
                long endTime = System.nanoTime();
                long durationMs = (endTime - startTime)/1000000;
                stream.println("tijd bag "+i+" "+durationMs+"ms");
                totalDuration += durationMs;
                
                
            }
                long durationBag = totalDuration/Path.nbBags;
                stream.println("complete runtijd: "+ totalDuration+"ms -- gemiddelde runtijd per bag: "+durationBag+"ms");
            
        }
        stream.close();
    }
    
        public static void runClusKMeans() throws FileNotFoundException{
         String time = Path.pathPinac+"timeClusKMeansEurlex.txt";
        PrintStream stream = new PrintStream(new File(time));
        stream.println("runtime clus with one Full kmeans hierarchy");
        
     //   for(String dataset: Path.multilabelDatasets){
     String dataset = "eurlex-dc";
            System.out.println(dataset);
            stream.println(dataset);
            long totalDuration = 0;
            
              //  for(int i = 0; i<Path.nbBags; i++){
                for(int i = 0; i<22; i++){
                System.out.println(i);
                String filePath = Path.pathPinac+dataset+"/kmeans/settingsBag"+i+"Full.s";
                String[] arg = {filePath};
              
                long startTime = System.nanoTime();
                Clus.main(arg);
                long endTime = System.nanoTime();
                long durationMs = (endTime - startTime)/1000000;
                totalDuration += durationMs;
                stream.println("tijd bag "+i+" "+durationMs+"ms");
                
                
                
            }
                long durationBag = totalDuration/Path.nbBags;
                if(dataset.equals("eurlex-dc")){
                    stream.println("pas op: eurlex-dc is nog niet correct");
                }
                stream.println("complete runtijd: "+ totalDuration+"ms -- gemiddelde runtijd per bag: "+durationBag+"ms");
            
      //  }
        stream.close();
    }
    
    public static void test(){
        String[] flat = {"/Users/katie/Downloads/bookmarks/settingsFlat0.s"};
        String[] hier = {"/Users/katie/Downloads/bookmarks/settingsBag0.s"};
        Clus.main(flat);
        Clus.main(hier);
        
        
    }
    
    public static void main(String[] args) throws FileNotFoundException{
          //  String[] arg = {"-forest", "/Users/katie/Desktop/test/test/settingsFlat0.s"};
          //  Clus.main(arg);
        //  runClusFlatHierarchy();
        // String[] argTest = {"/Users/katie/Downloads/example4/S0settingsFold1.s"};
        // String[] arg = {"/Users/katie/Desktop/temp/emo/settingsBag0.s"};
         //test();
      //  runClusFlatHierarchy();
     runClusKMeans();
   //  runAllDatasetsWithHier();
    }
    
}
