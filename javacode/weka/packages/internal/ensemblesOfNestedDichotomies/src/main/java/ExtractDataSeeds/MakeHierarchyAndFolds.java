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
 *
 * @author katie
 * 
 * Steekt de hierarchieen in aparte files. Maakt de test- en train files aan.
 * De test en train files zijn ook direct hierarchisch.
 *   * maakt de test en train sets aan met hierarchie?
 */
public class MakeHierarchyAndFolds {
    
    public void go(String path) throws FileNotFoundException, IOException{
        /**
       * De hierarchie in aparte files zetten.
      */
          PrintStream[] streamsh = new PrintStream[Path.nbFolds+1];
          streamsh[0] = new PrintStream(new File(path+"/hAll.txt"));
              NewElementHierarchy[] listNewElementsHier = new NewElementHierarchy[Path.nbFolds+1];//per fold een newelementhierarchy
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
          PrintStream[] streams = new PrintStream[Path.nbFolds+1];
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
                          line = convertClassLineToHier(classNames,line, lijstHash.get(i));
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
                      if(data && line.length()>0){
                         line = convertLineToHier(line, indexClass, lijstHash.get(i), listNewElementsHier[0]);
                      }
                    }
                    streams[i].println(line);
              } 
                      
            bufferedReader.close();
            makeTestFiles(listOfAllLines, listOfAllFoldLines, path, indexClass, 
                    lijstHash, arffHeader, classNames, listNewElementsHier);
        /*  for(NewElementHierarchy h: listNewElementsHier){
              if(h.hasNewElements()){
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
          for(int i = 0; i<Path.nbFolds; i++){
              streamsh[i] = new PrintStream(new File(path+"/test"+Integer.toString(i+1)+".arff"));
              
              if(indexClass!=1){//als de class op de laatste index staat
                   for(int indHeader = 0; indHeader<headerArff.get(i).size()-1; indHeader++){
                  streamsh[i].println(headerArff.get(i).get(indHeader));
              }
              streamsh[i].println(convertClassLineToHier(classNames, path, lijstHash.get(i+1)));//ndexClass
            
              } else {//als de class als eerste attribuut staat
                  streamsh[i].println(headerArff.get(i).get(0));
                  streamsh[i].println(convertClassLineToHier(classNames, path, lijstHash.get(i+1)));//ndexClass
                  
                   for(int indHeader = 2; indHeader<headerArff.get(i).size(); indHeader++){
                  streamsh[i].println(headerArff.get(i).get(indHeader));
              }
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
                  String line2 = convertLineToHier(line, indexClass, 
                              lijstHash.get(index+1), newElementsHiers[index+1]);
                      if(newElementsHiers[index+1].hasNewElements()){
                          newElementsHiers[index+1].setIndex(index+1);//1tem10
                      }
                      streamsh[index].println(line2);
                      
             }
            }
    }
    
    /**
     * 
     * @param line: de lijn uit de originele dataset die hierarchisch moet worden
     * @param indexClass: de index van het klasse attribuut
     * @param lijstHier: conversie van originele klasse naar de klasse die hierarchisch is
     * @return : dezelfde lijn maar met hierarchische klasse
     */
    private String convertLineToHier(String line, int indexClass, HashMap<String, String> lijstHier, 
            NewElementHierarchy newHierValues){
                          String[] changeClass = line.split(",");
                    final String oldClass = changeClass[indexClass-1];
                       if(lijstHier.get(oldClass)!=null){
                           changeClass[indexClass-1] = lijstHier.get(oldClass);
                       } else {
                           newHierValues.addNewElement(oldClass);
                       }
                         
                       String lineTo = changeClass[0];
                         for(int indTo = 1; indTo<changeClass.length; indTo++){
                            lineTo =  lineTo.concat(",").concat(changeClass[indTo]);
                         }
                         return lineTo;
    }
    
    private String convertClassLineToHier(String[] classNames, String line, HashMap<String, String> map){
                            
                           line = "@attribute class ".concat(" hierarchical "); //array aanmaken met alle hierarchische klassen er in
                            for (String workLine1 : classNames) {
                                String get = map.get(workLine1);
                                if (get!=null) { //kan null zijn omdat er een klasse niet voorkomt in een fold 
                                    //en dus ook niet in de hierarchie voorkomt
                                    line = line.concat(map.get(workLine1));
                                    line = line.concat(", ");
                                } else {
                                    line = line.concat(workLine1);//staat als enkele knoop in de hierarchie van de test files
                                    line = line.concat(", ");
                                }
                            }
                         return line.substring(0, line.length()-2);
    }
}