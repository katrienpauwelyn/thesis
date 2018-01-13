/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clus;

/**
 t
 * @author katie
 */
public class SupsampleGetter {
    
    ///Users/katie/thesiscode/datasets/multilabelUpload
    //path build
    
    public static void sparse(){
        for(String dataset: Path.multilabelSparseDatasets){
             String[] arg = {"-forest", Path.pathPinac+dataset+"/settings.s"};
          //  String[] arg = {"-forest","/Users/katie/Desktop/temp/bookmarks/settings.s"};
            Clus.main(arg);
        }
    }
    //TODO CAL500, 
    //Error: ,  eurlex-ev, , , nuscVLADplus
    //ok birds, corel16k, flags, rcv1subset1, tmc2007, Computers, eurlex-dc, genbase, eurlex-sm
    //nu: 
    public static void main(String[] args){
         // String[] arg = {"-forest", "/Users/katie/thesiscode/datasets/multilabelUpload/medical/settings.s"};
         // String[] arg = {"-forest", Path.pathPinac+"settings.s"};
         // Clus.main(arg);
      //  String path = "/Users/katie/Desktop/nieuweDatasets/";
           String[] arg = {"-forest", Path.pathPinac+"tmc2007"+"/settings.s"};
         //  String[] arg = {path+"CAL500"+"/settings.s"};
            Clus.main(arg);
        // sparse();
    }
    //TODO kijk of dat het helpt met in de sparse map alle - te vervangen door :
}
