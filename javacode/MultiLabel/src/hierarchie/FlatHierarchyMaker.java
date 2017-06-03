/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hierarchie;

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
 */
public class FlatHierarchyMaker {
    
        public static void makeAllHierarchies() throws FileNotFoundException, IOException{
            HashMap<String, String> normalMap = new HashMap();
            HashMap<String, String> sparseMap = new HashMap();
            String path = Path.pathPinac;
            BufferedReader readerNormal = new BufferedReader(new FileReader(path+"normalMap.txt"));
            BufferedReader readerSparse = new BufferedReader(new FileReader(path+"sparseMap.txt"));
            String lineN;
            while((lineN = readerNormal.readLine())!=null && !lineN.isEmpty()){
               readerNormal.readLine();
                normalMap.put(lineN, readerNormal.readLine());
            }
            while((lineN=readerSparse.readLine())!=null && !lineN.isEmpty()){
               readerSparse.readLine();
                sparseMap.put(lineN, readerSparse.readLine());
            }
            
            PrintStream timePrinter = new PrintStream(new File(Path.pathPinac+"timeWithoutHierMakingHier.txt"));
            timePrinter.println("tijd nodig om een vlakke hierarchie te maken");
            makeAllHierarchies(normalMap, timePrinter);
            makeAllHierarchies(sparseMap, timePrinter);
            timePrinter.close();
    }
        
        public static void makeAllHierarchies(HashMap<String, String> map, PrintStream timePrinter) throws FileNotFoundException{
            String path = Path.pathPinac;
            for(String dataset: map.keySet()){
                
                long startTime = System.nanoTime();
                
                String finalPath = path+dataset+"/flat/flatHier.txt";
                PrintStream stream = new PrintStream(new File(finalPath));
                
                String all = map.get(dataset);
                
                
                String[] parsed = all.split(",");
                all = all.replace(",", "-")+",";
                for(String cl: parsed){
                    stream.println(all+cl);
                }
                long endTime = System.nanoTime();
                long duration = (endTime - startTime)/1000000; //(ms)
                timePrinter.println("hierarchie "+dataset+" "+duration+"ms");
            }
        }  
        
        public static void main(String[] args) throws IOException{
            makeAllHierarchies();
        }
}
