<%@ page contentType="text/html; charset=euc-kr" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>자동로그아웃 안내</title>
<meta http-equiv="content-type" content="text/html; charset=euc-kr" />
<style type="text/css">
body{background-color: #f4f9ff; }
img{ border:none;}
p { margin: 20px;}
</style>
<link rel="shortcut icon" href="../images/base/police.ico" type="image/ico" />    
<link rel="stylesheet" type="text/css" href="../css/plug-in/fileTree/default/easyui.css" />
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<script src="../js/amcharts/amcharts.js" type="text/javascript"></script>        
<script>
$(function(){
    var chart;

    var chartData = [];

    AmCharts.ready(function () {
        // generate some random data first   
        generateChartData();

        // SERIAL CHART
        chart = new AmCharts.AmSerialChart();
        chart.pathToImages = "./js/amcharts/images/";
        chart.marginLeft = 0;
        chart.marginRight = 0;
        chart.marginTop = 0;
        chart.dataProvider = chartData;
        chart.categoryField = "zzz";

        // AXES
        // category
        var categoryAxis = chart.categoryAxis;
        categoryAxis.parseDates = false; // as our data is date-based, we set parseDates to true
        categoryAxis.minPeriod = "DD"; // our data is daily, so we set minPeriod to DD
        // value axis
        var valueAxis = new AmCharts.ValueAxis();
        valueAxis.inside = true;
        valueAxis.tickLength = 0;
        valueAxis.axisAlpha = 0;
        valueAxis.minimum = 100;
        valueAxis.maximum = 140;
        chart.addValueAxis(valueAxis);

        // GRAPH
        var graph = new AmCharts.AmGraph();
        graph.dashLength = 3;
        graph.lineColor = "#7717D7";
        graph.valueField = "visits";
        graph.dashLength = 3;
        graph.bullet = "round";
        chart.addGraph(graph);

        // CURSOR
        var chartCursor = new AmCharts.ChartCursor();
        chartCursor.cursorAlpha = 0;
        chart.addChartCursor(chartCursor);

        // GUIDES are used to create horizontal range fills
        var guide = new AmCharts.Guide();
        guide.value = 0;
        guide.toValue = 105;
        guide.fillColor = "#CC0000";
        guide.fillAlpha = 0.2;
        guide.lineAlpha = 0;
        valueAxis.addGuide(guide);

        var guide = new AmCharts.Guide();
        guide.value = 105;
        guide.toValue = 140;
        guide.fillColor = "#CC0000";
        guide.fillAlpha = 0.15;
        guide.lineAlpha = 0;
        valueAxis.addGuide(guide);

        var guide = new AmCharts.Guide();
        guide.value = 110;
        guide.toValue = 115;
        guide.fillColor = "#CC0000";
        guide.fillAlpha = 0.1;
        guide.lineAlpha = 0;
        valueAxis.addGuide(guide);

        var guide = new AmCharts.Guide();
        guide.value = 115;
        guide.toValue = 120;
        guide.fillColor = "#CC0000";
        guide.fillAlpha = 0.05;
        guide.lineAlpha = 0;
        valueAxis.addGuide(guide);

        var guide = new AmCharts.Guide();
        guide.value = 120;
        guide.toValue = 125;
        guide.fillColor = "#0000cc";
        guide.fillAlpha = 0.05;
        guide.lineAlpha = 0;
        valueAxis.addGuide(guide);

        var guide = new AmCharts.Guide();
        guide.value = 125;
        guide.toValue = 130;
        guide.fillColor = "#0000cc";
        guide.fillAlpha = 0.1;
        guide.lineAlpha = 0;
        valueAxis.addGuide(guide);

        var guide = new AmCharts.Guide();
        guide.value = 130;
        guide.toValue = 135;
        guide.fillColor = "#0000cc";
        guide.fillAlpha = 0.15;
        guide.lineAlpha = 0;
        valueAxis.addGuide(guide);

        var guide = new AmCharts.Guide();
        guide.value = 135;
        guide.toValue = 140;
        guide.fillColor = "#0000cc";
        guide.fillAlpha = 0.2;
        guide.lineAlpha = 0;
        valueAxis.addGuide(guide);

        // WRITE
        chart.write("chartdiv");
    });

    // generate some random data
    function generateChartData() {
        var firstDate = new Date();
        firstDate.setDate(firstDate.getDate() - 10);

        for (var i = 0; i < 31; i++) {
          //  var newDate = new Date(firstDate);
          //  newDate.setDate(newDate.getDate() + i);
			var newDate = "2013-01-" + i;
            var visits = Math.round(Math.random() * 40) + 100;

            chartData.push({
               // dateX: newDate,
               zzz : newDate,
                visits: visits
            });
        }
    }
});
</script>
</head>
<body>
        <div id="chartdiv" style="width: 100%; height: 400px;"></div>
</body>
</html>