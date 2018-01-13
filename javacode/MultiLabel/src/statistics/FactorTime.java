/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statistics;

/**
 *
 * @author katie
 * a = Factor x b
 * berekent Factor, waarin a en b de looptijden zijn van twee verschillende classifiers
 */
public class FactorTime {
    
    
    public static double getFactorTime(String data){
        double factor = 0.0;
        int nbDataset = 0;
        data = data.replace(" ", "").replace("pm","").replace("{","").replace("}","").replace("\n","").replace("\\","").replace("$", "");
        String[] matrix = data.split("hline");
        
        for(String line: matrix){
            String[] elementen = line.split("&");
            double one = Double.parseDouble(elementen[3]);
            double two = Double.parseDouble(elementen[2]);
            factor += two/one;
            nbDataset++;
        }
        
        
        return (double) factor/nbDataset;
    }
    
    public static void main(String[] args){
        String a = "bibtex & 1&3462  & 242 \\\\ \\hline\n" +
"corel5k& 10& 21678 & 642  \\\\ \\hline\n" +
"delicious & 34& 230213  & 38251 \\\\ \\hline\n" +
"emotions&0 & 10 & 1  \\\\ \\hline\n" +
"enron & 0&322 & 16   \\\\ \\hline\n" +
"mediamill & 4 & 21439 & 1801  \\\\ \\hline\n" +
"medical & 0& 39 &  3 \\\\ \\hline\n" +
"scene &0 &78  &  17 \\\\ \\hline\n" +
"yeast &0 & 127  &11 \\\\ \\hline\n" +
"birds &0 & 42  & 5 \\\\ \\hline\n" +
"computers &0 & 1437& 98 \\\\ \\hline\n" +
"corel16k &4 &11000& 298 \\\\ \\hline\n" +
"eurlex-dc &5 & 92799  & 6950 \\\\ \\hline\n" +
"eurlex-sm &2 & 46840 & 2396 \\\\ \\hline\n" +
"flags &0 & 1  & 1 \\\\ \\hline\n" +
"genbase & 0&382  & 16 \\\\ \\hline\n" +
"nuscVLADplus &2 &82779 & 8762 \\\\ \\hline\n" +
"rcv1subset1&0 & 2409 & 107 \\\\ \\hline\n" +
"tmc2007 & 0& 7698& 97 \\\\ \\hline";
        
        System.out.println(getFactorTime(a));
    }
    
}
