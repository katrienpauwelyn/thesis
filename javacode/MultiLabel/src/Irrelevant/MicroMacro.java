/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Irrelevant;

import combinerenResultaten.Macro;
import combinerenResultaten.MacroOne;
import combinerenResultaten.Micro;
import combinerenResultaten.MicroOne;
import java.io.IOException;

/**
 *
 *maakt de micro en macro files aan
 * Er is een 'micromacro' map nodig in iedere dataset map
 * Hierna nog script runnen om auroc en auprc te bekomen
 */
public class MicroMacro {
    
    //eerst checken of er bij alle datasets een 'micromacro' map is
    public static void makeMicroMacroPinacs() throws IOException{
        System.out.println("micromacro");
        MicroOne.makeMicroFilesForAllDatasets();
        MacroOne.makeMacroFilesForAllDatasets();
    }
    
    public static void main(String[] args) throws IOException{
        makeMicroMacroPinacs();
    }
    
}
