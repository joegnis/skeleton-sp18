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

    public static void main(String[] args) {
        // Fixed T
        int N = 100;
        int T = 50;
        int initN = 50;
        XYChart chart = new XYChartBuilder()
                .title("Union find in Percolation")
                .width(800).height(600)
                .xAxisTitle(String.format("Grid size (Number of experiments=%d)", N))
                .yAxisTitle("Runtime (seconds)")
                .build();

        List<Integer> tList = IntStream.iterate(T, v -> v).limit(N).boxed().collect(Collectors.toList());

        // QuickUnion with a fixed T
        List<Integer> nList = IntStream.range(initN, initN + N).boxed().collect(Collectors.toList());
        List<Double> runtimes = recordTimes(nList, tList, QuickUnion.class);
        chart.addSeries("QuickFind", nList, runtimes);

        new SwingWrapper<>(chart).displayChart();
    }
}
