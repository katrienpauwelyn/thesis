/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statistics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import static statistics.AUCResultParser.getAUCline;
import static statistics.AUCResultParser.getStandardDev;

/**
 *
 * @author katie
 *
 * bibtex RHamOne5 oneError: 0.45089463220675946 -- coverage: 21.434990059642146
 * -- rankingLoss: 0.20240557459570435 -- avgPrecision: 0.5147402971000256
 *
 * corel5k RHamOne5 oneError: 0.708 -- coverage: 96.476 -- rankingLoss:
 * 0.17244219524273546 -- avgPrecision: 0.2719940945040716
 *
 * delicious RHamOne5 oneError: 0.39717425431711145 -- coverage:
 * 410.4103610675039 -- rankingLoss: 0.15192480112701082 -- avgPrecision:
 * 0.40460440569478057
 *
 * emotions RHamOne5 oneError: 0.3811881188118812 -- coverage:
 * 1.8267326732673268 -- rankingLoss: 0.2946369636963696 -- avgPrecision:
 * 0.8901127612761277
 *
 * enron RHamOne5 oneError: 0.2556131260794473 -- coverage: 10.67357512953368 --
 * rankingLoss: 0.14230214358321697 -- avgPrecision: 0.6815881145244812
 *
 * mediamill RHamOne5 oneError: 0.21805792163543442 -- coverage:
 * 12.165014712714884 -- rankingLoss: 0.16183095380912463 -- avgPrecision:
 * 0.9408357594659921
 *
 * medical RHamOne5 oneError: 0.24186046511627907 -- coverage:
 * 1.3736434108527131 -- rankingLoss: 0.11040900389196769 -- avgPrecision:
 * 0.8276595820200471
 *
 * scene RHamOne5 oneError: 0.41471571906354515 -- coverage: 0.7232441471571907
 * -- rankingLoss: 0.33624581939799225 -- avgPrecision: 0.7721850613154986
 *
 * yeast RHamOne5 oneError: 0.33805888767720826 -- coverage: 5.863685932388223
 * -- rankingLoss: 0.2903737109941349 -- avgPrecision: 1.1794658489463943
 *
 *Berekent de standaarddeviatie van de ranking based measures
 */
public class RankingResultParser {

    public static void parse(String name) throws FileNotFoundException, IOException {
        String[] datasets = {"bibtex", "corel5k", "delicious", "emotions", "enron", "mediamill", "medical", "scene", "yeast"};
        String path = "/Users/katie/Dropbox/thesis/outputPinac/one/";

        BufferedReader[] reader = new BufferedReader[10];
        PrintStream stream = new PrintStream(new File(path + "std/" + name));

        for (int i = 0; i < 10; i++) {
            reader[i] = new BufferedReader(new FileReader(path + name + i + ".txt"));
        }

        String[] rankingNames = {"oneError", "coverage", "rankingLoss", "avgPrecision"};

        for (String dataset : datasets) {//for each dataset
            stream.println(dataset);

            double[][] ranking = new double[4][10];
            for (int i = 0; i < 10; i++) {//voor iedere run

                String line = reader[i].readLine();
                System.out.println("line " + line);
                if (!line.contains(dataset)) {
                    System.out.println(line + "!!");
                    System.out.println(dataset + "!!");
                    throw new Error();
                }
                line = reader[i].readLine();
                System.out.println("ranking " + line);

                for (int nb = 0; nb < 4; nb++) {//for each ranking
                    ranking[nb][i] = getRanking(nb + 1, line);

                }
                line = reader[i].readLine();
                System.out.println("empty " + line);
            }

            for (int nb = 0; nb < 4; nb++) {
                stream.println(rankingNames[nb] + " " + getStandardDev(ranking[nb]));
            }

        }

    }

    /**
     *
     * @param nb 1: oneError; 2: coverage; 3: rankingLoss; 4: avgPrecision line:
     * oneError: 0.33805888767720826 -- coverage: 5.863685932388223 --
     * rankingLoss: 0.2903737109941349 -- avgPrecision: 1.1794658489463943
     */
    public static double getRanking(int nb, String line) {
        String[] dubbelPunt = line.split(": ");
        String nodig = dubbelPunt[nb].split(" --")[0];
        return Double.parseDouble(nodig);
    }

    public static void main(String[] args) throws IOException {
        parse("rankingKMeansOne");
        parse("rankingRHamOne");
    }

}
