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
public class RunClusMultilabel {
    
    public static void runAllDatasets(){
        for(String dataset: Path.multilabelDatasets){
            System.out.println(dataset);
            String filePath = Path.multilabelPath+dataset+"/settings.s";
            String[] arg = {"-forest", filePath};
            Clus.main(arg);
        }
    }
    
    public static void main(String[] args){
        runAllDatasets();
    }
    
}
