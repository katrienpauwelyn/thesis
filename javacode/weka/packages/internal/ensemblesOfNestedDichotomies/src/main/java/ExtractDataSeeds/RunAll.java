package ExtractDataSeeds;

import java.io.FileNotFoundException;
import java.io.IOException;
import staticData.Path;
import static weka.classifiers.AbstractClassifier.runClassifier;
import weka.classifiers.meta.nestedDichotomies.ClassBalancedPlus;
import weka.classifiers.meta.nestedDichotomies.FurthestCentroidND;
import weka.classifiers.meta.nestedDichotomies.ND;
import weka.classifiers.meta.nestedDichotomies.RandomPairND;

/**
 *
 * @author katie
 */
public class RunAll {

    
    //outputi.txt
    //outputDatai.txt kunnen afgedrukt worden
    
    
    /**
     * 
     * @param isStandard false: letterRecognition en multipleFeatures. True: all others
     *  true: het classe attribuut is het laatste attribuut
     * @param path absoluut pad naar de data file
     * @param seed1 seed voor random
     * @param seed2 seed voor random
     * @param classInd als isStandard true is hoort het -1 te zijn. Deze waarde wordt genegeerd als isSTandard true is.
     * Als het false is, heeft dit de index van het klasse attribuut
     * @return de argumenten voor de classifiers
     */
    public static String[] getArgs(boolean isStandard, String path, int seed1, int seed2, int classInd) {

        if (isStandard) {
            String[] argOthers = new String[6];
            argOthers[1] = path;
            argOthers[0] = "-t";
            argOthers[2] = "-s";
            argOthers[3] = Integer.toString(seed1);
            argOthers[4] = "-S";
            argOthers[5] = Integer.toString(seed2);
            return argOthers;
        } else {
            String[] args = new String[8];
            args[0] = "-t";
            args[1] = path;
            args[2] = "-s";
            args[3] = Integer.toString(seed1);
            args[4] = "-S";
            args[5] = Integer.toString(seed2);
            args[6] = "-c";
            args[7] = Integer.toString(classInd);
            return args;
        }
    }

    public static void main(String[] argv) throws FileNotFoundException, IOException {
        String[] allDataSets = Path.allDataSetFiles;
        String[] dataNames = Path.datasets;
        String[] classifierNames =Path.classifiers;
        String path = Path.path;
      
        MakeHierarchyAndFolds testmain = new MakeHierarchyAndFolds();

        //testmain.go("out/" + classifierNames[0] + "/" + "pageBlocks");
        
        //Random pair
            
                      for (int i = 0; i < 1; i++) {
                 for(int seed = 0; seed<Path.nbSeeds; seed++){
                    // for (int i = 0; i < dataNames.length; i++) {
       
                if (dataNames[i].equals("segmentation")
                        || dataNames[i].equals("letterRecognition")) {
                    runClassifier(new RandomPairND(path+ "/"+ classifierNames[3] + "/" + dataNames[i], Integer.toString(seed)),
                            RunAll.getArgs(false, allDataSets[i], seed, seed, 1));          
                } else {
                    runClassifier(new RandomPairND(path+ "/" + classifierNames[3] + "/"
                            + dataNames[i], Integer.toString(seed)), RunAll.getArgs(true, allDataSets[i], seed, seed, -1));
                }
           //     testmain.go("out/" + classifierNames[3] + "/" + dataNames[i], Integer.toString(seed));


            }
        }
     

        //class balanced
     /*   for(int seed = 0; seed<Path.nbSeeds; seed++){
            for (int i = 0; i < dataNames.length; i++) {
              if (dataNames[i].equals("segmentation")
                      || dataNames[i].equals("letterRecognition")) {
                  runClassifier(new ClassBalancedPlus("/"+path + "/"+ classifierNames[0] + "/" + dataNames[i], 
                          Integer.toString(seed)), 
                          RunAll.getArgs(false, allDataSets[i], seed, seed, 1));
              } else {
                  runClassifier(new ClassBalancedPlus("/"+path+ "/" + classifierNames[0] + "/" + dataNames[i], 
                          Integer.toString(seed)),
                          RunAll.getArgs(true, allDataSets[i], seed, seed, -1));
              }
         //     testmain.go("out/" + classifierNames[0] + "/" + dataNames[i], Integer.toString(seed));
            }
        }
        
     

        //furthest centroid
        for(int seed = 0; seed<Path.nbSeeds; seed++){
             for (int i = 0; i < dataNames.length; i++) {
                if (dataNames[i].equals("segmentation")
                        || dataNames[i].equals("letterRecognition")) {
                    runClassifier(new FurthestCentroidND("/"+path+ "/"
                            + classifierNames[1] + "/" + dataNames[i], Integer.toString(seed)), RunAll.getArgs(false,allDataSets[i] ,seed, seed, 1));
                } else {
                    runClassifier(new FurthestCentroidND("/"+path+ "/" + classifierNames[1] + "/" + dataNames[i], 
                            Integer.toString(seed)), RunAll.getArgs(true, allDataSets[i], seed, seed, -1));
                }
         //       testmain.go("out/" + classifierNames[1] + "/" + dataNames[i], Integer.toString(seed));
            }
        }
       

        //random  (nd)
        for(int seed = 0; seed<Path.nbSeeds; seed++){
            for (int i = 0; i < dataNames.length; i++) {
           //       for (int i = 0; i < 1; i++) {
                if (dataNames[i].equals("segmentation")
                        || dataNames[i].equals("letterRecognition")) {

                    runClassifier(new ND("/"+path+ "/" + classifierNames[2]
                            + "/" + dataNames[i], Integer.toString(seed)), RunAll.getArgs(false, allDataSets[i], seed, seed, 1));
                } else {
                    runClassifier(new ND("/"+path + "/"+ classifierNames[2]
                            + "/" + dataNames[i], Integer.toString(seed)), RunAll.getArgs(true, allDataSets[i], seed, seed, -1));
                }
         //      testmain.go("out/" + classifierNames[2] + "/" + dataNames[i], Integer.toString(seed));
           }
        } */
            
    }
    

}
