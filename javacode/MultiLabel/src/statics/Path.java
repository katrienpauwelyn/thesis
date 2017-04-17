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
    public static String[] datasets = {//"bibtex",
        //"corel5k","delicious",
        "emotions"
        //"enron","eurlex","flags",
        //"genbase",
        //"mediamill","yeast"
    };
    
    public static String[] standardDatasets = {/*"corel5k",
        "emotions","flags","genbase",
        "mediamill",
        "yeast"*/
    };
    public static String[] sparseDatasets = { "bibtex",
        "delicious",
        "enron",
       "eurlex"
    };
    
    //mediamill, eurlex, delicious
    public static String[] dataset = {"flags"};
    
    public static int nbBags = 3;
    
    
    
}
