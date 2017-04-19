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
    public static String[] datasets = {"bibtex",
        "corel5k","delicious",
        "emotions",
        "enron","eurlex","flags",
        "genbase",
        "mediamill","yeast"
    };
    
    public static String[] standardDatasets = {"corel5k",
        "emotions","flags","genbase",
        "mediamill",
        "yeast"
    };
    public static String[] sparseDatasets = { "bibtex",
        "delicious",
        "enron",
       "eurlex"
    };
    
    public static String[] newDatasets = {"bibtex","bookmarks","corel5k","delicious","emotions",
    "enron", "mediamill","medical","scene","tmc2007","yeast"};
    
    public static String[] toParse = {"bookmarks","medical","scene","tmc2007"};
    
    public static String[] toParseSparse = {"bookmarks","medical","tmc2007"};
    public static String[] toParseStandard={"scene"};
    
    //hier worden de naam van de dataset + aantal attributen + namen van de klassen opgeslaan
    public static String pathStandardMap = path+"normalMap.txt";
    public static String pathSparseMap = path+"sparseMap.txt";
    
    //hier wordt de tijd die nodig is om hierarchieen te maken opgeslaan
    public static String pathTimeHierStandard = path+"timeHierNormal.txt";
    public static String pathTimeHierSparse = path+"timeHierSparse.txt";
    
    //hier wordt het scriptje opgeslaan om alle AU te berekenen
    public static String pathToAUScript = path+"scriptAU";
    
    //mediamill, eurlex, delicious
    public static String[] dataset = {"flags"};
    
    public static int nbBags = 3;
    
    
    
}
