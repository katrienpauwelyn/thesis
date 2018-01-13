/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Irrelevant;

/**
 *
 * @author katie
 */
public class parseResultsR {
    
    
    public static void parse(String from, String name){
        String[] output = new String[4];
        output[0]=name+"ClusHSC <- c(";
         output[1] = name+"ClusHMC <- c(";
         output[2] = name+"ClusHSCGew <- c(";
         output[3] = name+"ClusHMCGew <- c(";
        String[] lines = from.split("\n");
        for(int i = 0; i<lines.length; i++){
            String line = lines[i];
            line = line.replace(" ", "");
            String[] linePM = line.split("\\\\pm");
            for(int j= 0; j<linePM.length-1; j++){
                String[] ampersant = linePM[j].split("&");
                output[j]+=ampersant[ampersant.length-1].replace(",", ".")+",";
            }
        }
        for(String s: output){
            System.out.println(s.substring(0, s.length()-1).replace("$", "")+")");
        }
    }
    
    public static void parseNauwkeurigheid(String from, String naam){
        String[] output = new String[2];
        output[0] = "nauwkeurigheid"+naam+"C45 <- c(";
        output[1] = "nauwkeurigheid"+naam+"Clus <- c(";
        String[] lines = from.split("\n");
        for(int i = 0; i<lines.length; i++){
            
            String line = lines[i];
            if(line.contains("NA")){
                continue;
            }
            line = line.replace(" ", "");
            String[] linePM = line.split("\\\\pm");
            for(int j= 0; j<linePM.length-1; j++){
                String[] ampersant = linePM[j].split("&");
                output[j]+=ampersant[ampersant.length-1].replace(",", ".")+",";
            }
        }
        for(String s: output){
            System.out.println(s.substring(0, s.length()-1).replace("$", "")+")");
        }
        
    }
    
public static void main(String[] arg){

        
        
String fromNND = "audiology & NA&64,867$\\pm$2,813$\\bigodot$ \\\\ \\hline\n" +
"krkopt & NA&70,701$\\pm$0,527$\\bigodot$ \\\\ \\hline\n" +
"letterRecognition & NA&85,541$\\pm$0,284$\\bigodot$ \\\\ \\hline\n" +
"mfeatFac & 77,779$\\pm$0,972&78,024$\\pm$0,566$\\bigodot$ \\\\ \\hline\n" +
"mfeatFou & 61,680$\\pm$0,821&63,945$\\pm$0,730$\\bigodot$ \\\\ \\hline\n" +
"mfeatKar & 70,795$\\pm$0,917&71,890$\\pm$1,030$\\bigodot$ \\\\ \\hline\n" +
"mfeatMor & 60,775$\\pm$0,498&61,485$\\pm$0,643$\\bigodot$ \\\\ \\hline\n" +
"mfeatPix & 76,285$\\pm$0,792&77,075$\\pm$0,930$\\bigodot$ \\\\ \\hline\n" +
"optdigits & 79,088$\\pm$0,396&80,008$\\pm$0,454$\\bigodot$ \\\\ \\hline\n" +
"pageBlocks & 96,404$\\pm$0,160&96,427$\\pm$0,152$\\bigodot$ \\\\ \\hline\n" +
"pendigits & 84,677$\\pm$0,237&85,514$\\pm$0,246$\\bigodot$ \\\\ \\hline\n" +
"segmentation & 95,493$\\pm$0,273&96,103$\\pm$0,245$\\bigodot$ \\\\ \\hline\n" +
"shuttle & 99,974$\\pm$0,004$\\bigodot$&99,968$\\pm$0,003 \\\\ \\hline\n" +
"vowel & NA&70,939$\\pm$1,383$\\bigodot$ \\\\ \\hline\n" +
"yeast & 46,987$\\pm$1,387&57,675$\\pm$0,643$\\bigodot$ \\\\ \\hline\n" +
"zoo & 75,049$\\pm$2,535&88,019$\\pm$2,673$\\bigodot$ \\\\ \\hline";
parseNauwkeurigheid(fromNND, "NDHsc");
        
String fromNauwkeurigheid = "audiology & NA&67,477$\\pm$2,779$\\bigodot$ \\\\ \\hline\n" +
"krkopt & NA&73,140$\\pm$0,326$\\bigodot$ \\\\ \\hline\n" +
"letterRecognition & NA&86,149$\\pm$0,195$\\bigodot$ \\\\ \\hline\n" +
"mfeatFac & 78,600$\\pm$0,501$\\bigodot$&78,390$\\pm$0,386 \\\\ \\hline\n" +
"mfeatFou & 63,179$\\pm$0,678&63,680$\\pm$0,879$\\bigodot$ \\\\ \\hline\n" +
"mfeatKar & 72,485$\\pm$0,656&72,605$\\pm$0,673$\\bigodot$ \\\\ \\hline\n" +
"mfeatMor & 60,820$\\pm$0,400&61,749$\\pm$0,428$\\bigodot$ \\\\ \\hline\n" +
"mfeatPix & 78,820$\\pm$0,814$\\bigodot$&78,309$\\pm$0,505 \\\\ \\hline\n" +
"optdigits & 80,078$\\pm$0,286$\\bigodot$&79,925$\\pm$0,373 \\\\ \\hline\n" +
"pageBlocks & 96,458$\\pm$0,120&96,495$\\pm$0,140$\\bigodot$ \\\\ \\hline\n" +
"pendigits & 85,048$\\pm$0,197&85,594$\\pm$0,172$\\bigodot$ \\\\ \\hline\n" +
"segmentation & 95,900$\\pm$0,303&96,151$\\pm$0,314$\\bigodot$ \\\\ \\hline\n" +
"shuttle & 99,979$\\pm$0,004$\\bigodot$&99,971$\\pm$0,005 \\\\ \\hline\n" +
"vowel & 70,646$\\pm$1,033&71,787$\\pm$0,977$\\bigodot$ \\\\ \\hline\n" +
"yeast & 45,970$\\pm$1,977&55,491$\\pm$1,059$\\bigodot$ \\\\ \\hline\n" +
"zoo & 77,623$\\pm$5,167&85,841$\\pm$3,101$\\bigodot$ \\\\ \\hline";
parseNauwkeurigheid(fromNauwkeurigheid, "RandomPairHsc");
}
}

