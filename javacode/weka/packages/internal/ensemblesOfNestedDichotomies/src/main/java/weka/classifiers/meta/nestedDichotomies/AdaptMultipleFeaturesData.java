/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weka.classifiers.meta.nestedDichotomies;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

public class AdaptMultipleFeaturesData {

    public static void main(String[] argv) throws FileNotFoundException, IOException {

        PrintStream[] prints = new PrintStream[5];
        prints[0] = new PrintStream(new File("/Users/katie/Dropbox/thesis/data/multipleFeatures/mfeatFac.arff"));
        prints[1] = new PrintStream(new File("/Users/katie/Dropbox/thesis/data/multipleFeatures/mfeatFou.arff"));
        prints[2] = new PrintStream(new File("/Users/katie/Dropbox/thesis/data/multipleFeatures/mfeatKar.arff"));
        prints[3] = new PrintStream(new File("/Users/katie/Dropbox/thesis/data/multipleFeatures/mfeatMor.arff"));
        prints[4] = new PrintStream(new File("/Users/katie/Dropbox/thesis/data/multipleFeatures/mfeatPix.arff"));
        BufferedReader[] readers = new BufferedReader[5];
        readers[0] = new BufferedReader(new FileReader("/Users/katie/Dropbox/thesis/data/multipleFeatures/origineleData/mfeat-fac.arff"));
        readers[1] = new BufferedReader(new FileReader("/Users/katie/Dropbox/thesis/data/multipleFeatures/origineleData/mfeat-fou.arff"));
        readers[2] = new BufferedReader(new FileReader("/Users/katie/Dropbox/thesis/data/multipleFeatures/origineleData/mfeat-kar.arff"));
        readers[3] = new BufferedReader(new FileReader("/Users/katie/Dropbox/thesis/data/multipleFeatures/origineleData/mfeat-mor.arff"));
        readers[4] = new BufferedReader(new FileReader("/Users/katie/Dropbox/thesis/data/multipleFeatures/origineleData/mfeat-pix.arff"));
        String classString = "@attribute class {0,1,2,3,4,5,6,7,8,9}";
        String line;

        for (int i = 0; i < readers.length; i++) {
            line = readers[i].readLine();
            while (!line.contains("@attribute")) {
                prints[i].println(line);
                line = readers[i].readLine();
            }
            while (line.contains("@attribute")) {
                prints[i].println(line);
                line = readers[i].readLine();
            }
            prints[i].println(classString);
            prints[i].println(line);
            prints[i].println(readers[i].readLine()); //@data

            line = readers[i].readLine();

            for (int x = 0; x < 10; x++) {
                for (int y = 0; y < 200; y++) {
                        line = line.concat("," + x);
                        prints[i].println(line);
                        line = readers[i].readLine();
                }
            }
        }
    }
}
