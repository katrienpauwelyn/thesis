/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multilabel;

import combinerenResultaten.Averager;
import combinerenResultaten.Macro;
import combinerenResultaten.Micro;
import java.io.IOException;
import statics.Path;

/**
 *
 * maakt een average file aan van alle bags op de pinac, en maakt dan ook de micro en macro
 * files aan aan de hand van deze average files.
 * Er is een 'micromacro' map nodig in iedere dataset map
 * Hierna nog script runnen om auroc en auprc te bekomen
 */
public class MicroMacro {
    
    
    
    public static void makeAllAverages() throws IOException{
        String basic = Path.pathPinac;
        System.out.println("averages");
        for(String dataset: Path.datasets){
            System.out.println(dataset);
            String output = basic+dataset+"/average.test.pred.arff";
            Averager.makeAverageArff(basic+dataset+"/settingsBag", ".test.pred.arff", output, dataset);
        }
    }
    
    
    //eerst checken of er bij alle datasets een 'micromacro' map is
    public static void makeMicroMacroPinacs() throws IOException{
        String basic;
        System.out.println("micromacro");
        for(String dataset: Path.datasets){
            System.out.println(dataset);
            basic = Path.pathPinac.concat(dataset);
            Macro.makeMacroFiles(basic, basic+"/average.test.pred.arff");
            Micro.makeMicroFiles(basic, basic+"/average.test.pred.arff", dataset);
        }    
    }
    
    public static void main(String[] args) throws IOException{
       // makeAllAverages();
        makeMicroMacroPinacs();
    }
    
}
