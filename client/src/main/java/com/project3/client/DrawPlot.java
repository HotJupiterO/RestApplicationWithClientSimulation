package com.project3.client;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.Styler;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DrawPlot {
    public static void main(String[] args) {
        List<Double> yData = Client.getAllMeasurements();
        List<Double> xData = IntStream.rangeClosed(1,yData.size())
                .mapToObj(Double::valueOf)
                .collect(Collectors.toList());
        System.out.println(xData);

        XYChart chart = new XYChartBuilder()
                .title("Temperature plot for 1000 days")
                .xAxisTitle("X")
                .yAxisTitle("Y")
                .width(800)
                .height(600)
                .theme(Styler.ChartTheme.GGPlot2)
                .build();
        chart.getStyler().setMarkerSize(4);

        XYSeries series = chart.addSeries("Y vs X", xData, yData);
        series.setLineWidth(2);

        new SwingWrapper<>(chart).displayChart();
    }
}
