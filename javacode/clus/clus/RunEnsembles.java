/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clus;

/**
 *
 * @author katie
 */
public class RunEnsembles {
    
    public void runAllEnsembles(){
        runAllClassifiersEnsembles(Path.path);
    }
    
   private void runAllClassifiersEnsembles(String path){
        for(String s: Path.classifiers){
            runAllDatasetsEnsembles(path+"/"+s);
        }
    }
   
   private void runAllDatasetsEnsembles(String path){
       for(String s: Path.datasets){
            for(int i = 1; i<11; i++){//for each fold
                 String finalPath = path+"/"+s+"/settingsEnsemble"+i+".s";
                 runEnsemble(finalPath);
               }
           
       }
          
   }
   
   public void runEnsemble(String filePath){
         String[] arg = {"-forest", filePath};
         Clus.main(arg);
   }
   
   
   public void runFourTestDatabases(){
       String path = Path.path;
        String[] datasets = {"audiology","krkopt","mfeatFac","pageBlocks"};
       
       for(String s: Path.classifiers){
           String newPath = path +"/"+s;
           for(String data: datasets){
             //  System.out.println(data);
                for(int i = 1; i<11; i++){//for each fold
                 String finalPath = newPath+"/"+data+"/settingsEnsemble"+i+".s";
                 String[] args = {"-forest", finalPath};
                 Clus.main(args);
               }
           }
   }
   }
   
   
    
    
    public static void main(String[] args){
        RunEnsembles runEns = new RunEnsembles();
        //runEns.runFourTestDatabases();
        runEns.runAllEnsembles();
    }
    
}
