package com.multilabel;

import com.evaluation.*;
import com.output.MultiLabelOutput;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

public class Main {

    public static int NUMBER_OF_LABELS = 10;

    public static void main(String[] args) throws Exception {


        Evaluation evaluation = new Evaluation();

        ConverterUtils.DataSource outputSource = new ConverterUtils.DataSource("path to arff file with predictions");

        Instances output = new Instances(outputSource.getDataSet());
        MultiLabelOutput[] predictions = new MultiLabelOutput[output.numInstances()];
        double threshold = 0.5;
        for(int i = 0; i<output.numInstances(); ++i) {
            Instance inst = output.instance(i);
            double[] predictionsPerSample = new double[NUMBER_OF_LABELS];

            //TODO fill the predictionsPerSample with the loaded output inst

            predictions[i] = new MultiLabelOutput(predictionsPerSample, threshold);
        }


        boolean trueLabels[][] = new boolean[output.numInstances()][NUMBER_OF_LABELS];

        if (predictions[0].hasBipartition()) {

            ExampleBasedMeasures ebm = new ExampleBasedMeasures(predictions, trueLabels);
            LabelBasedMeasures lbm = new LabelBasedMeasures(predictions, trueLabels);

            if (predictions[0].hasRanking()) {
                RankingBasedMeasures rbm = new RankingBasedMeasures(predictions, trueLabels);
                evaluation.setRankingBasedMeasures(rbm);
            }
            if (predictions[0].hasConfidences()) {
                ConfidenceLabelBasedMeasures clbm = new ConfidenceLabelBasedMeasures(predictions, trueLabels);
                evaluation.setConfidenceLabelBasedMeasures(clbm);
            }

            System.out.println(evaluation);

        }
    }
}
