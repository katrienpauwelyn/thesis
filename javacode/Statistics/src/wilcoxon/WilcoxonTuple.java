/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wilcoxon;

/**
 *[DEPRECATED]
 * @author katie
 */
public class WilcoxonTuple {
    double nd;
    double clus;
    
    public WilcoxonTuple(String ndNew, String clusNew){
        nd = removePM(ndNew);
        clus = removePM(clusNew);
    }
    
    private double removePM(String st){
        String[] split = st.split("pm");
        return Double.parseDouble(split[0]);
    }
    
    //nd - clus
    public double getDifference(){
        return nd-clus;
    }
    
    public double getAbsoluteDifference(){
        return Math.abs(nd-clus);
    }
    
    /**
     * @return true als nd>clus, false als clus>=nd
     */
    public boolean ndBetter(){
        if(nd>clus){
            return true;
        }
        return false;
    }
    
    public boolean equals(){
        return nd == clus;
    }
    

}
