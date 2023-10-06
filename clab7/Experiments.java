import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hug.
 */
public class Experiments {
    public static void experiment1() {
        BST<Integer> bst = new BST<>();
        List<Double> avgDepthValues = new ArrayList<>();
        List<Double> avgOptDepthValues = new ArrayList<>();
        List<Integer> xValues = new ArrayList<>();

        for (int x = 0; x <= 5000; x += 1) {
            int randomItem = RandomGenerator.getRandomInt(5000);
            if (bst.contains(randomItem)) {
                continue;
            }
            bst.add(randomItem);
            double thisY = bst.averageDepth();
            xValues.add(x);
            avgDepthValues.add(thisY);
            avgOptDepthValues.add(ExperimentHelper.optimalAverageDepth(x));
        }

        XYChart chart = new XYChartBuilder()
                            .width(800)
                            .height(600)
                            .xAxisTitle("x label")
                            .yAxisTitle("y label")
                            .build();
        chart.addSeries("Optimal Average Depth Value", xValues, avgOptDepthValues);
        chart.addSeries("Random Average Depth Value", xValues, avgDepthValues);

        new SwingWrapper(chart).displayChart();
    }

    public static void experiment2() {
        BST<Integer> bst = new BST<>();
        List<Double> avgDepthValues = new ArrayList<>();
        List<Double> initialDepthValues = new ArrayList<>();
        List<Integer> xValues = new ArrayList<>();

        for (int i = 0; i <= 2000; i++) {
            int randomItem = RandomGenerator.getRandomInt(5000);
            if (bst.contains(randomItem)) {
                continue;
            }
            bst.add(randomItem);
        }
        double initialDepthValue = bst.averageDepth();

        for (int x = 0; x <= 100000; x++) {
            ExperimentHelper.asymmetricDeletionAndInsertion(bst);
            double avgDepths = bst.averageDepth();
            xValues.add(x);
            avgDepthValues.add(avgDepths);
            initialDepthValues.add(initialDepthValue);
        }


        XYChart chart = new XYChartBuilder()
                .width(800)
                .height(600)
                .xAxisTitle("x label")
                .yAxisTitle("y label")
                .build();
        chart.addSeries("Average Depth Value", xValues, avgDepthValues);
        chart.addSeries("Initial Depth Value", xValues, initialDepthValues);

        new SwingWrapper(chart).displayChart();
    }

    public static void experiment3() {
        BST<Integer> bst = new BST<>();
        List<Double> avgDepthValues = new ArrayList<>();
        List<Double> initialDepthValues = new ArrayList<>();
        List<Integer> xValues = new ArrayList<>();

        for (int i = 0; i <= 2000; i++) {
            int randomItem = RandomGenerator.getRandomInt(5000);
            if (bst.contains(randomItem)) {
                continue;
            }
            bst.add(randomItem);
        }
        double initialDepthValue = bst.averageDepth();

        for (int x = 0; x <= 100000; x++) {
            ExperimentHelper.symmetricDeletionAndInsertion(bst);
            double avgDepths = bst.averageDepth();
            xValues.add(x);
            avgDepthValues.add(avgDepths);
            initialDepthValues.add(initialDepthValue);
        }


        XYChart chart = new XYChartBuilder()
                .width(800)
                .height(600)
                .xAxisTitle("x label")
                .yAxisTitle("y label")
                .build();
        chart.addSeries("Average Depth Value", xValues, avgDepthValues);
        chart.addSeries("Initial Depth Value", xValues, initialDepthValues);

        new SwingWrapper(chart).displayChart();
    }

    public static void main(String[] args) {
//        int optIPL = ExperimentHelper.optimalIPL(8);
//        System.out.println(optIPL);
//        double optAvgDepth = ExperimentHelper.optimalAverageDepth(8);
//        System.out.println(optAvgDepth);
        experiment1();
        experiment2();
        experiment3();
    }
}
