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
public class RunOne {
    
      public static void main(String[] args) throws FileNotFoundException{
       runFlat();
   //    runKMeans();
  //     runRHam();
    }
      
      public static void runFlat() throws FileNotFoundException{
          System.out.println("flat");
          PrintStream stream = new PrintStream(new File(Path.pathPinac+"timeClusOneFlat.txt"));
           stream.println("time 1x clus one flat") ;
           
          for(String dataset: Path.flatTodo){
              System.out.println(dataset);
              String filePath = Path.pathPinac+dataset+"/one/settingsFlatOne.s";
              String[] arg = {filePath};
              long startTime = System.nanoTime();
              
              Clus.main(arg);
              
              long endTime = System.nanoTime();
               long durationMs = (endTime - startTime)/1000000;  
             stream.println(dataset+ " "+durationMs+"ms");
          }
          
      }
      
      public static void runRHam() throws FileNotFoundException{
             System.out.println("RHam");
              
          PrintStream stream = new PrintStream(new File(Path.pathPinac+"timeClusOneRHam.txt"));
          stream.println("time 1x clus one rham") ;
          
          for(String dataset: Path.multilabelDatasets){
              System.out.println(dataset);
              long totalTime = 0;
              for(int i = 0; i<10; i++){
                  String filePath = Path.pathPinac+dataset+"/one/settingsRHamOne"+i+".s";
              String[] arg = {filePath};
              long startTime = System.nanoTime();
              
              Clus.main(arg);
              
              long endTime = System.nanoTime();
              totalTime += (endTime - startTime)/1000000;  
              }
              
            
             stream.println(dataset+ " gemiddelde "+totalTime/10+"ms");
          }
       }
      
      public static void runKMeans() throws FileNotFoundException{
              System.out.println("kmeans");
              
          PrintStream stream = new PrintStream(new File(Path.pathPinac+"timeClusOneKmeans10.txt"));
          stream.println("time 1x clus one kmeans") ;
          
          for(String dataset: Path.multilabelDatasets){
              System.out.println(dataset);
              long totalTime = 0;
              for(int i = 0; i<10; i++){
              String filePath = Path.pathPinac+dataset+"/one/settingsKMeansOne"+i+".s";
              String[] arg = {filePath};
              long startTime = System.nanoTime();
              
              Clus.main(arg);
              
              long endTime = System.nanoTime();
              totalTime += (endTime-startTime)/1000000;    
              }
          
             stream.println(dataset+ " gemiddelde "+totalTime/10+"ms");
          }
          
      }
    
}
