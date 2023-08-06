package org.app.chartCreator;

import org.app.oilData.CommoditiesValueMap;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.pdf.PDFDocument;
import org.jfree.pdf.PDFGraphics2D;
import org.jfree.pdf.PDFHints;
import org.jfree.pdf.Page;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChartCreator {
    List<CommoditiesValueMap> values;
    String userChoice;
    public ChartCreator(List<CommoditiesValueMap> list,String choice) {
        values = list;
        userChoice = choice;
    }

    private JFreeChart createChart(XYDataset dataset) {
        JFreeChart chart = ChartFactory.createTimeSeriesChart("Resource Prices", null, userChoice, dataset);
        String fontName = "Palatino";
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setDomainPannable(false);
        plot.setRangePannable(true);
        plot.getDomainAxis().setLowerMargin(0.0);
        XYItemRenderer r = plot.getRenderer();
        if (r instanceof XYLineAndShapeRenderer) {
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
            renderer.setDefaultShapesVisible(false);
            renderer.setDrawSeriesLineAsPath(true);
            renderer.setAutoPopulateSeriesStroke(false);
            renderer.setDefaultStroke(new BasicStroke(3.0f, BasicStroke.CAP_ROUND,
                    BasicStroke.JOIN_BEVEL), false);
        }
        return chart;
    }

    private List<Integer> getCorrectDateFormat(CommoditiesValueMap data){
        List<String>dateStr = Arrays.stream(data.getDate().split("-")).toList();
        List<Integer>correctDate = new ArrayList<>();
        correctDate.add(Integer.parseInt(dateStr.get(0)));
        correctDate.add(Integer.parseInt(dateStr.get(1)));
        return correctDate;
    }
    private XYDataset createDataset(){
        TimeSeries s1 = new TimeSeries("Indicator price");
        for(CommoditiesValueMap map: values){
            List<Integer>yearMonth = getCorrectDateFormat(map);
            s1.add(new Month(yearMonth.get(1),yearMonth.get(0)),map.getValue());
        }

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(s1);
        return dataset;
    }
    public void createPDFfile(){
        PDFDocument pdf = new PDFDocument();
        pdf.setTitle("PDF");
        pdf.setAuthor("Nikos");
        Page page = pdf.createPage(new Rectangle(612,468));
        PDFGraphics2D g2 = page.getGraphics2D();
        g2.setRenderingHint(PDFHints.KEY_DRAW_STRING_TYPE,
                PDFHints.VALUE_DRAW_STRING_TYPE_VECTOR);

        JFreeChart chart = createChart(createDataset());
        chart.draw(g2, new Rectangle(0, 0, 612, 468));
        File f = new File("PDFTimeSeriesChartDemo1.pdf");
        pdf.writeToFile(f);
    }

}
