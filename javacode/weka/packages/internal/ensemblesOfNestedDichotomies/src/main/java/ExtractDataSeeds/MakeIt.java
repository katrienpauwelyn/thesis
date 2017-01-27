package ExtractDataSeeds;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import staticData.Path;

/**
 *[DEPRECATED]
 * @author katie
 * Wordt niet meer gebruikt???
 * maakt de test en train sets aan zonder hierarchie
 */
public class MakeIt {

    
    public void go(String path) throws FileNotFoundException, IOException{
        /**
       * De hierarchie in aparte files zetten.
       * TODO hierarchie hoeft eigenlijk niet in files te staan, de hashtable alleen is voldoende.
      */
          PrintStream[] streamsh = new PrintStream[11];
          streamsh[0] = new PrintStream(new File(path+"/hAll.txt"));
              NewElementHierarchy[] listNewElementsHier = new NewElementHierarchy[11];//per fold een newelementhierarchy
              //als er klassen in de test staan die niet in de train staan, dan komt het element daar bij te staan
              listNewElementsHier[0] = new NewElementHierarchy();
          for(int i = 1; i<Path.nbFolds+1; i++){
              streamsh[i] = new PrintStream(new File(path+"/h"+Integer.toString(i)+".txt"));
              listNewElementsHier[i] = new NewElementHierarchy();
          }
          
          ArrayList<HashMap<String, String>> lijstHash = new ArrayList();
          
              BufferedReader outHier = new BufferedReader(new FileReader(path+"/output.txt"));
              String lineh;
                  int ih = -1;
                  while((lineh = outHier.readLine()) != null)
                  {
                      if("".equals(lineh)){
                          ih++;
                          lijstHash.add(new HashMap<String, String>()); 
                      } else{
                          //hierarchie formateren: [] vervangen door /; spaties weg; [ en ] weg
                         lineh = lineh.replaceAll("\\]\\[", "/");
                         lineh = lineh.replaceAll(", ", "-");
                         lineh = lineh.replaceAll("\\[","").replaceAll("\\]","");
                         
                           
                           //maak een hashmap met als key de klasse waartoe een instance geklassificeerd wordt
                           //en als value deze klasse met de voorouders er bij
                         String[] split = lineh.split("/");
                         String key = split[split.length-1];
                         lijstHash.get(ih).put(key, lineh);
                         streamsh[ih].println(lineh);
                        }
                  }
          outHier.close();
          
          /**
           * De data van de folds in aparte files zetten.
           * Klassen direct omzetten naar hierarchische klassen.
           * FoldAll is de volledige dataset.
           */
          PrintStream[] streams = new PrintStream[11];
          streams[0] = new PrintStream(new File(path+"/foldAll.arff"));
             for(int i = 1; i<Path.nbFolds+1; i++){
              streams[i] = new PrintStream(new File(path+"/fold"+Integer.toString(i)+".arff"));
          }
          
              BufferedReader bufferedReader = new BufferedReader(new FileReader(path+"/outputData.txt"));
              String line;
              String[] classNames = null;
                  int i = -1;
                  boolean data = false;
                  int indexClass = 0;
                  int nbAttributes = 0;
                  ArrayList<String> listOfAllLines = new ArrayList<String>();
                  ArrayList<ArrayList<String>> arffHeader = new ArrayList<ArrayList<String>>();
                  ArrayList<ArrayList<String>> listOfAllFoldLines = new ArrayList<ArrayList<String>>();
                  for(int count = 0; count<10; count++){//tODO 10
                      listOfAllFoldLines.add(new ArrayList<String>());
                      arffHeader.add(new ArrayList<String>());
                  }
                  
                  while((line = bufferedReader.readLine()) != null){
                      //begin van een nieuwe fold
                      if(line.contains("@relation")){
                          i++;
                          nbAttributes = 0;
                          data=false;
                          if(i>0){
                              arffHeader.get(i-1).add(line);
                          }
                              
                          
                      } else if(line.contains("@attribute")){ //tel het aantal attributen
                          nbAttributes++;
                                  if(i>0){
                              arffHeader.get(i-1).add(line);
                          }
                          //nu wordt er verondersteld dat de klasse aangeduid wordt door @attribute class
                        if(line.contains("@attribute class")){
                            indexClass = nbAttributes;
                            if(classNames == null){
                                  classNames = line.split("\\{")[1].split("\\}")[0].split(","); //array met enkel alle klassen 
                                  //is voor alle folds gelijk
                            }
                       } 
                      } else     if(line.contains("@data")){ //kijken of we vanaf nu effectieve data hebben
                            data=true;
                      } else {
                             if(i==0){
                                 if(!line.isEmpty()){
                         listOfAllLines.add(line);
                                 }
                     } else {
                         if(!line.isEmpty()){
                            listOfAllFoldLines.get(i-1).add(line);
                         }
                     }
                      //effectieve data: Splitten op ",". Bijgehouden op welke index de klasse zit.
                      //het klasse elementje er uit halen. Converteren naar de hierarchische vorm.
                      //Gesplitte array terug converteren naar de gepaste string.
                  
                    }
                          streams[i].println(line);
                   
              } 
                      
            bufferedReader.close();
            makeTestFiles(listOfAllLines, listOfAllFoldLines, path, indexClass, 
                    lijstHash, arffHeader, classNames, listNewElementsHier);
       /*   for(NewElementHierarchy h: listNewElementsHier){
              if(h.hasNewElements()){
               //    streamsh[i] = new PrintStream(new File(path+"/h"+Integer.toString(i)+".txt"));
               //   changeHierarchy(h, path+"/fold"+Integer.toString(i));
                 System.out.println(Integer.toString(h.getIndex()));
                  for(String el: h.getElements()){
                      System.out.println(el);
                  }
                  System.out.println("next");
              }
          }*/
          
          
    }
    
    /**
     * 
     * @param completeData
     * @param allFolds
     * @param path
     * @param indexClass
     * @param lijstHash
     * @param headerArff bevat de header. TODO: enkel bij de eerste adden (methode hier boven)
     * TODO methode extracten om class te veranderen naar hierarchisch
     * TODO plakken aan de file vooraleer de data er in te steken
     * @throws FileNotFoundException 
     * maak alle test files aan (verschil tussen volledige data en de fold)
     */
    private void makeTestFiles(ArrayList<String> completeData, ArrayList<ArrayList<String>> allFolds, 
            String path, int indexClass, ArrayList<HashMap<String, String>> lijstHash, 
            ArrayList<ArrayList<String>> headerArff, String[] classNames, 
            NewElementHierarchy[] newElementsHiers) throws FileNotFoundException{
         PrintStream[] streamsh = new PrintStream[10];
          for(int i = 0; i<10; i++){
              streamsh[i] = new PrintStream(new File(path+"/test"+Integer.toString(i+1)+".arff"));
              
              for(int indHeader = 0; indHeader<headerArff.get(i).size(); indHeader++){
                  streamsh[i].println(headerArff.get(i).get(indHeader));
              }
              streamsh[i].println();
              streamsh[i].println("@data");
          }
            for(int index = 0; index<allFolds.size(); index++){
                ArrayList<String> list = allFolds.get(index);
             ArrayList<String> completeDataCopy = new ArrayList(completeData);
             for(String line: list){
                 completeDataCopy.remove(line);
             }
             for(String line: completeDataCopy){
                      streamsh[index].println(line);
                      
             }
            }
    }
    

    
  /**  private void  changeHierarchy(NewElementHierarchy newEl, String fileName) throws FileNotFoundException, IOException{
        File tempFile = new File(fileName+"temp"+".arff");
         PrintStream stream = new PrintStream(tempFile);
        BufferedReader reader = new BufferedReader(new FileReader(fileName+".arff"));
        String line;
        while((line = reader.readLine()) != null){
            if(line.contains("@attribute class")){
                for(String s: newEl.getElements()){
                    line= line.concat(", ");
                    line= line.concat(s);
                }
            }
               stream.println(line);
        }
        reader.close();
  //  new File(fileName).delete();
  //   File f = new File(fileName);
  //   tempFile.renameTo(f);
    }*/

    
}
