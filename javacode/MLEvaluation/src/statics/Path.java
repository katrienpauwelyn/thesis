/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statics;

/**
 *
 * @author katie
 */
public class Path {
    
       public static String pathPinac = "/export/home1/NoCsBack/thesisdt/s0212310/nieuweDatasets/";
       
         public static String[] standardDatasets = {"corel5k",
        "emotions",
        "mediamill","scene",
        "yeast"
    };
    public static String[] sparseDatasets = { "bibtex","bookmarks",
        "delicious",
        "enron","medical"
    };
    
   /* public static String[] datasets = {"bibtex",//"bookmarks",
        "corel5k","delicious","emotions",
    "enron", "mediamill","medical","scene",
    "yeast"};*/
    
    public static String[] datasets = {"birds",//"CAL500",
        "Computers","Corel16k","eurlex-dc",//"eurlex-ev",
        "eurlex-sm","flags","genbase","nuscVLADplus","rcv1subset1","tmc2007"};
    
     public static String[] postAverageDatasets= {"corel5k", "delicious", "medical"};
}
