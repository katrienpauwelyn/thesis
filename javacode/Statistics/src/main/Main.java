/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;


import java.io.IOException;
import Time.CalculateTime;
import toLatex.ToLatex;

/**
 *
 * @author katie
 */
public class Main {
    
    public static void main(String[] args) throws IOException{
        CalculateTime.main(null);
        ClusResults.WriteAllStatistics.main(null);
        NDResults.WriteAllStatistics.main(null);
        ToLatex.main(null);
        
     //   Statistics.Time.CalculateTime (berekent de looptijden en zet de tijden in out/_classifier/_dataset/asettings/CLUSaTime.txt en out/_classifier/_dataset/aOutputND/CLUSaTime.txt en de latex file in out/_classifier/alatexTime.txt)

//Statistics.ClusResults.WriteAllStatistics (schrijft de statistieken van clus naar een mens leesbaar formaat. Te vinden in out/_classifier/_dataset/asettings/S_i_datasetStatistics)

//Statistics.NDResults.WriteAllStatistics (schrijft de statistieken van clus naar een mens leesbaar formaat. Te vinden in out/_classifier/_dataset/asettings/S_i_datasetStatistics)

//Statistics.toLatex.ToLatex (converteert de mens leesbare resultaten naar latex tabellen)
        
    }
    
}
