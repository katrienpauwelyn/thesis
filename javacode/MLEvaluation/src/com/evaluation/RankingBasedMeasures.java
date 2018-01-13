/*
 *    This program is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 2 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program; if not, write to the Free Software
 *    Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

/*
 *    RankingBasedMeasures.java
 *    Copyright (C) 2009 Aristotle University of Thessaloniki, Thessaloniki, Greece
 *
 */

package com.evaluation;

import java.util.ArrayList;
import com.output.MultiLabelOutput;
import java.util.Arrays;


public class RankingBasedMeasures {

	protected double oneError;
	protected double coverage;
	protected double rankingLoss;
	protected double avgPrecision;
	
	public RankingBasedMeasures(MultiLabelOutput[] output, boolean[][] trueLabels) {
		computeMeasures(output, trueLabels);
	}

    RankingBasedMeasures(RankingBasedMeasures[] arrayOfMeasures) {
		oneError = 0;
		coverage = 0;
		rankingLoss = 0;
		avgPrecision = 0;

        for (RankingBasedMeasures measures : arrayOfMeasures) {
            oneError += measures.getOneError();
            coverage += measures.getCoverage();
            rankingLoss += measures.getRankingLoss();
            avgPrecision += measures.getAvgPrecision();
        }

        int arraySize = arrayOfMeasures.length;
        oneError /= arraySize;
        coverage /= arraySize;
        rankingLoss /= arraySize;
        avgPrecision /= arraySize;
    }
//output: voor iedere instance een MultiLabelOutput
    private void computeMeasures(MultiLabelOutput[] output, boolean[][] trueLabels) {
		oneError = 0;
		coverage = 0;
		rankingLoss = 0;
		avgPrecision = 0;

        int numLabels = trueLabels[0].length;
        int examplesToIgnoreRankingLoss = 0;
        int examplesToIgnoreAvgPrecision = 0;
        int numInstances = output.length;
        System.out.println("Numinstances"+numInstances);
        for (int instanceIndex=0; instanceIndex<numInstances; instanceIndex++) {

            int[] ranks = output[instanceIndex].getRanking();

            //======one error related============
            //---begin ordinal ranking ---
	/*		int topRated;
            for (topRated=0; topRated<numLabels; topRated++)    //index van de top rated zoeken (dus duidelijk de eerste top rated.)
                if (ranks[topRated] == 1)
                    break;
            if (!trueLabels[instanceIndex][topRated]) //checken of de toprated een effectieve 1 of 0 is. Als het een 0 is: oneError++
				oneError++;*/
            //---einde ordinal ranking ---
            
            int topRated;
            int nbFirstRanked = 0;
            int firstRankedNul = 0;
            for (topRated=0; topRated<numLabels; topRated++) {   //index van de top rated zoeken (dus duidelijk de eerste top rated.)
                if (ranks[topRated] == 1){
                    nbFirstRanked++;
                    if (!trueLabels[instanceIndex][topRated]){ //checken of de toprated een effectieve 1 of 0 is. Als het een 0 is: oneError++
			firstRankedNul++;	
                    }   
                }
            }
            oneError+=(firstRankedNul / nbFirstRanked);
            
            
            
            
            
            
			//======coverage related=============
            int howDeep = 0;
            //---begin foutieve code---
           /* for (int rank = numLabels; rank >= 1; rank--) {//hoogste rank van iets dat effectief true is, beginnen bij hoogste rank getal
                int indexOfRank;
                for (indexOfRank=0; indexOfRank<numLabels; indexOfRank++){//de index zoeken van de voorspelling/echte getal die bij deze rank hoort
                    if (ranks[indexOfRank] == rank)
                        break;
                    if (trueLabels[instanceIndex][indexOfRank]) {//als deze index een echt label is: dit is de waarde die we nodig hebben
			howDeep = rank-1;
                    	break;
                    }
                   
                }
            }*/
           //---eind foutieve code---
           
           //---begin ordinal ranking ---
         /* for (int rank = numLabels; rank >= 1; rank--) {//hoogste rank van iets dat effectief true is, beginnen bij hoogste rank getal
                int indexOfRank;
                for (indexOfRank=0; indexOfRank<numLabels; indexOfRank++){//de index zoeken van de voorspelling/echte getal die bij deze rank hoort
                    if (ranks[indexOfRank] == rank)
                        break;
                }
                if (trueLabels[instanceIndex][indexOfRank]) {//als deze index een echt label is: dit is de waarde die we nodig hebben
			howDeep = rank-1;
                  	break;
                    }
           }
            coverage += howDeep;*/
        //---einde ordinal ranking ---
        //oude implementatie werkt niet omdat er voor sommige ranks geen  if (ranks[indexOfRank] == rank) true is. Dus gaat out of 
        //range exception (geeft dezelfde coverage bij ordinal als oude code (getest))
        //BEGIN COMPETITION RANKING
        int highestRank = 0;
        for(int indexOfRank = 0; indexOfRank<numLabels; indexOfRank++){
            if(ranks[indexOfRank]>highestRank){
                if(trueLabels[instanceIndex][indexOfRank]){
                    highestRank=ranks[indexOfRank]-1;
                }
            }
        }
        coverage += highestRank;
          //EINDE COMPETITION RANKING  


//-----ranking loss: voorbereiding
			// gather indexes of true and false labels
			// indexes of true and false labels
			ArrayList<Integer> trueIndexes = new ArrayList<Integer>();
			ArrayList<Integer> falseIndexes = new ArrayList<Integer>();
			for (int labelIndex = 0; labelIndex < numLabels; labelIndex++) {
				if (trueLabels[instanceIndex][labelIndex]) {
					trueIndexes.add(labelIndex);
				} else {
					falseIndexes.add(labelIndex);
				}
			}

            //======ranking loss related=============
             //---begin ordinal ranking ---
           /* if (trueIndexes.size() == 0 || falseIndexes.size() == 0)
                examplesToIgnoreRankingLoss++;
            else {
                int rolp = 0; // reversed ordered label pairs
                for (int k : trueIndexes)
                    for (int l : falseIndexes)
                        if (ranks[k] > ranks[l]) //voorsp(Xi, truei) <= voorsp(Xj, falsej) <=> rank(truei) > rank(falsej)
    //					if (output[instanceIndex].getConfidences()[trueIndexes.get(k)] <= output[instanceIndex].getConfidences()[falseIndexes.get(l)])
                            rolp++;
                rankingLoss += (double) rolp / (trueIndexes.size() * falseIndexes.size());
            }*/
             //---einde ordinal ranking ---
            
            if (trueIndexes.size() == 0 || falseIndexes.size() == 0)
                examplesToIgnoreRankingLoss++;
            else {
                int rolp = 0; // reversed ordered label pairs
                for (int k : trueIndexes)
                    for (int l : falseIndexes)
                        if (ranks[k] >= ranks[l]) //voorsp(Xi, truei) <= voorsp(Xj, falsej) <=> rank(truei) > rank(falsej)
    //					if (output[instanceIndex].getConfidences()[trueIndexes.get(k)] <= output[instanceIndex].getConfidences()[falseIndexes.get(l)])
                            rolp++;
                rankingLoss += (double) rolp / (trueIndexes.size() * falseIndexes.size());
            }
           

            //======average precision related =============
             //---begin ordinal ranking ---
           /* if (trueIndexes.size() == 0)
                examplesToIgnoreAvgPrecision++;
            else {
                double sum=0;
                for (int j : trueIndexes) {
                    double rankedAbove=0;
                    for (int k : trueIndexes)
                        if (ranks[k] <= ranks[j])
                            rankedAbove++;

                    sum += (rankedAbove / ranks[j]);
                }
                avgPrecision += (sum / trueIndexes.size());
            }
		}*/
           //---einde ordinal ranking ---
            if (trueIndexes.size() == 0){
                examplesToIgnoreAvgPrecision++;
            } else {
                double sum=0;
                for (int j : trueIndexes) {
                    double rankedAbove=0;
                    for (int k : trueIndexes){
                        if (ranks[k] <= ranks[j]){
                            rankedAbove++;
                        }
                    }
                  
                    sum += (rankedAbove / ranks[j]);
                }
                 avgPrecision += (sum / trueIndexes.size());
            }
        }
        oneError /= numInstances;
       	coverage /= numInstances;
       	rankingLoss /= (numInstances - examplesToIgnoreRankingLoss);
		avgPrecision /= (numInstances - examplesToIgnoreAvgPrecision);
    }
    

	
	
	public double getAvgPrecision(){
		return avgPrecision;
	}
	
	public double getRankingLoss(){
        return rankingLoss;
    }
	
	public double getCoverage(){
        return coverage;
    }
	
	public double getOneError(){
        return oneError;
    }
	
    @Override
	public String toString(){
        return "not implemented yet!";
	}

}
