package hw2;

import edu.princeton.cs.algs4.Stopwatch;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RuntimeAnalysis {
    private static <U extends IUnionFind> List<Double> recordTimes(List<Integer> nList, List<Integer> tList, Class<U> unionFindClass) {
        PercolationFactory pf = new PercolationFactory();
        List<Double> times = new ArrayList<>(nList.size());
        for (int i = 0; i < nList.size(); i++) {
            int N = nList.get(i);
            int T = tList.get(i);

            Stopwatch watch = new Stopwatch();
            new PercolationStats(N, T, pf, unionFindClass);
            times.add(watch.elapsedTime());
        }

        return times;
    }

    private static void showChartFixedNumExperiments(int numExperiments) {
        int gridSize = 200;
        final int initGridSize = 50;
        XYChart chart = new XYChartBuilder()
                .title("Analyzing union find runtime by performing Percolation")
                .width(800).height(600)
                .xAxisTitle(String.format("Grid size (w/ fixed number of experiments: %d)", numExperiments))
                .yAxisTitle("Runtime (seconds)")
                .build();

        List<Integer> tList = IntStream.iterate(numExperiments, v -> v).limit(gridSize).boxed().collect(Collectors.toList());

        List<Integer> nList = IntStream.range(initGridSize, initGridSize + gridSize).boxed().collect(Collectors.toList());
        chart.addSeries("QuickUnion", nList, recordTimes(nList, tList, QuickUnion.class));
        chart.addSeries("WeightedQuickUnion", nList, recordTimes(nList, tList, WeightedQuickUnion.class));
        chart.addSeries("WeightedQuickUnion with Path Compression", nList, recordTimes(nList, tList, WeightedQuickUnionWithPathCompression.class));
        chart.addSeries("QuickUnion with Path Compression", nList, recordTimes(nList, tList, QuickUnionWithPathCompression.class));

        new SwingWrapper<>(chart).displayChart();
    }

    private static void showChartFixedGridSize(int gridSize) {
        int numExperiments = 200;
        int initNumExperiments = 50;
        XYChart chart = new XYChartBuilder()
                .title("Analyzing union find runtime by performing Percolation")
                .width(800).height(600)
                .xAxisTitle(String.format("Number of experiments (w/ fixed grid size: %d)", gridSize))
                .yAxisTitle("Runtime (seconds)")
                .build();

        List<Integer> nList = IntStream.iterate(gridSize, v -> v).limit(numExperiments).boxed().collect(Collectors.toList());

        List<Integer> tList = IntStream.range(initNumExperiments, initNumExperiments + numExperiments).boxed().collect(Collectors.toList());
        chart.addSeries("QuickUnion", tList, recordTimes(nList, tList, QuickUnion.class));
        chart.addSeries("WeightedQuickUnion", tList, recordTimes(nList, tList, WeightedQuickUnion.class));
        chart.addSeries("WeightedQuickUnion with Path Compression", tList, recordTimes(nList, tList, WeightedQuickUnionWithPathCompression.class));
        chart.addSeries("QuickUnion with Path Compression", tList, recordTimes(nList, tList, QuickUnionWithPathCompression.class));

        new SwingWrapper<>(chart).displayChart();
    }

    public static void main(String[] args) {
        showChartFixedNumExperiments(50);
        showChartFixedGridSize(50);
    }
}
