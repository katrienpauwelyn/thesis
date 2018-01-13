/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statics;

/**
 *in "delicious" is TAG_m\'usica handmatig aangepast naar TAG_m'usica
 * @author katie
 */
public class Path {
    
    public static String path = "/Users/katie/thesiscode/datasets/multilabel/";
            //"/Users/katie/Desktop/nieuweDatasets";
    /*public static String[] datasets = {"bibtex",
        "corel5k","delicious",
        "emotions",
        "enron","eurlex","flags",
        "genbase",
        "mediamill","yeast"
    };*/
    
    


 
    
    public static String[] standardDatasets = {"birds",//"CAL500",
        "Corel16k","flags","genbase","nuscVLADplus"};
    public static String[] sparseDatasets = {"Computers",
        "eurlex-dc",//"eurlex-ev",
        "eurlex-sm","rcv1subset1","tmc2007"};
    
    public static String[] datasets = {"birds",//"CAL500",
        "Computers","Corel16k","eurlex-dc",//"eurlex-ev",
        "eurlex-sm","flags","genbase","nuscVLADplus","rcv1subset1","tmc2007"};
    
   /* public static String[] standardDatasets = {"corel5k",
        "emotions",//"flags","genbase",
        "mediamill","scene",
        "yeast"
    };*/
  /*  public static String[] sparseDatasets = { "bibtex",//"bookmarks",
        "delicious",
        "enron",
        "medical"
    };*/
    
  /*  public static String[] datasets = {"bibtex",
       // "bookmarks",
        "corel5k","delicious","emotions",
    "enron", "mediamill","medical","scene",//"tmc2007",
    "yeast"
    };*/
    
   /* public static String[] toParse = {"bookmarks","medical","scene","tmc2007"};
    
    public static String[] toParseSparse = {"bookmarks","medical","tmc2007"};
    public static String[] toParseStandard={"scene"};*/
    
    //hier worden de naam van de dataset + aantal attributen + namen van de klassen opgeslaan
   
    
      public static String pathPinac = "/export/home1/NoCsBack/thesisdt/s0212310/nieuweDatasets/";
      
       public static String pathStandardMap = pathPinac + "normalMapZonder.txt";
            //"/Users/katie/thesiscode/datasets/multilabelUpload/"+"normalMapNew2.txt";
    public static String pathSparseMap = path + "/sparseMap.txt";
            //"/Users/katie/thesiscode/datasets/multilabelUpload/"+"sparseMapBibtex.txt";
    public static String pathFilteredMao = pathPinac + "sparseMapZonder.txt";
    
    public static String pathFilteredAll = pathPinac+"filteredClasses.txt";
    
    
    
      public static String pathTest = "/Users/katie/Downloads/";
    
    //hier wordt de tijd die nodig is om hierarchieen te maken opgeslaan
    public static String pathTimeHierStandard = pathPinac+"timeHierNormal.txt";
    public static String pathTimeHierSparse = pathPinac+"timeHierSparse.txt";
    
    //hier wordt het scriptje opgeslaan om alle AU te berekenen
    public static String pathToAUScript = pathPinac+"scriptAU.sh";
    
    //mediamill, eurlex, delicious
    public static String[] dataset = {"flags"};
    
    public static int nbBags = 50;
    
  public static String[] postAverageDatasets= {"corel5k", 
      "delicious", "medical"};
    
    public static boolean isSparse(String dataset){
        for(String s: sparseDatasets){
            if(s.equals(dataset)){
                return true;
            }
        }
        return false;
    }
    
    public static int getNbClusterCentra(String dataset){
        switch (dataset){
            case "bibtex": case "bookmarks": case "corel5k": case "mediamill":
            case "medical": case "scene":
                return 3;
            case "emotions":
                return 2;
            case "yeast":
                return 5;
            case "enron": case "delicious":
                return 4;
        }
        throw new Error("Foutieve dataset");
    }
    
        public static int getNbClusterCentraNieuweDatasets(String dataset){
        switch (dataset){
            //public static String[] datasets = {"birds","CAL500","Computers","Corel16k","eurlex-dc",//"eurlex-ev",
      //  "eurlex-sm","flags","genbase","nuscVLADplus","rcv1subset1","tmc2007"};
            case "birds": case "CAL500": case "Computers": case "Corel16k": case "eurlex-dc": 
            case "eurlex-sm": case "flags": case "genbase": case "nuscVLADplus": case "rcv1subset1": case "tmc2007":
                return 3;
        }
        throw new Error("Foutieve dataset");
    }

    public static int nbIterations = 500;
     
}
