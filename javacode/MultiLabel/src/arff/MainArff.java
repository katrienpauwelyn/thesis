/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arff;

import java.io.IOException;

/**
 * Deze klasse parset alle datasets (zowel sparse als niet sparse)
 *
 * @author katie
 */
public class MainArff {
    
    public static void main(String[] args) throws IOException{
        ArffParser.parseAllStandardArffs();
        SparseArffParser.parseAllSparseArffs();
                
    }
    
}
