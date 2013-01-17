<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.util.*" %>	
<%@ page import="kr.go.police.sms.*" %>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	int no = (Integer)request.getAttribute("no");
%>	
<%-- ���  --%>
<jsp:include page="../modules/header.jsp" />
<style>
.message{
	display: inline-block; width: 200px; text-overflow:ellipsis; white-space: nowrap; overflow: hidden;
}
#sendResultList td{
	cursor: pointer;
	height : 35px;	
}
select{width:80px !important;}
.userName{ background:#f4f4f4; border-bottom:#d0d0d0 1px solid; color:#258abf;}
.user_name{font-size:20px; font-weight:bold; text-align:center;}</style>
<body>
	<div id="wrapper">
		<%-- ��ܸ޴�  --%>
		<jsp:include page="../modules/admin_topmenu.jsp" />
		<div id="contents">
			<%-- ���̵� �޴�  --%>
			<jsp:include page="../modules/admin_sidebox.jsp" />
     	   <div class="boderWrap">
				<div class=user_name style="text-align:left; font-size:20px; color:#000; font-family:'Nanum'; font-weight:bold;">
					<h3>
						<img src="images/admin/title_stats.gif" alt="���" />
						<!-- <span style="font-size: 18px; font-weight: normal;">(${userid})</span> -->
					</h3>
				</div>
				
				<form style="clear: both; width: 100%; padding:3px; vertical-align: middle;" id="search_frm" action="./DeptStatisticsAction.sm" method="get"  >
					<input type="hidden"  name="deptcode" value="${deptcode}" />				
					<div style="float: left; display: inline-block;  margin:7px 0; width:300px; vertical-align: middle;" >					
						<select id="type" name="type">
							<option ${type == "SMS"?"selected":""} value="SMS">SMS</option>
							<option ${type == "LMS"?"selected":""} value="LMS">LMS</option>
							<option ${type == "MMS"?"selected":""} value="MMS">MMS</option>
						</select>
						
						<select id="date" name="date">					
						<c:forEach var="year" begin="${mind}" end="${maxd}" >						
						<option ${year == date?"selected":""} value="${year}">${year}��</option>
						</c:forEach>					
						</select>
					</div>
						<div style="float: right; display: inline-block;">
							<select id=s_type name="s_type">
								<option value="id"  ${s_type == "id"?"selected":""} >ID</option>
								<option value="name" ${s_type == "name"?"selected":""} >�̸�</option>
							</select>										
							<input title="�˻��� �ܾ �Է��ϼ���." style="margin-bottom: 3px;" value="${search}"  class="search phone" type="text" name="search" id="search" size="20" />
							<a  href="#"  onclick="return false;" id="search_btn"><img style="margin-bottom:5px;margin-right:5px; right;vertical-align: middle;"  src="./images/base/category_btn.gif" /></a>
						</div>												
					</form>					
			
				<!-- ���̺� -->
				<table id="sendResultList"  style="clear: both;" width="100%" border="0" cellpadding="0" cellspacing="0">
					<thead>
						<tr height="34px;">
							<!-- <th>�̸�</th> -->
							<th>ID</th>													
							<th>��ü</th>
							<th>1��</th>							
							<th>2��</th>
							<th>3��</th>
							<th>4��</th>
							<th>5��</th>
							<th>6��</th>
							<th>7��</th>
							<th>8��</th>
							<th>9��</th>
							<th>10��</th>
							<th>11��</th>
							<th>12��</th>
							<th>��Ʈ</th>																														
						</tr >
					</thead>
					<c:forEach var="data"  items="${list}" >
						<tr>
							<td class='userName' alt="${data.id}">${data.name}</td>
							<!-- <td>${data.id}</td> -->
							<td class="total">${data.total=="0"?"-":data.total}${data.total=="0"?"":"��"}</td>
							<td class="jan">${data.jan=="0"?"-":data.jan}${data.jan=="0"?"":"��"}</td>
							<td class="feb">${data.feb=="0"?"-":data.feb}${data.feb=="0"?"":"��"}</td>
							<td class="mar">${data.mar=="0"?"-":data.mar}${data.mar=="0"?"":"��"}</td>
							<td class="apr">${data.apr=="0"?"-":data.apr}${data.apr=="0"?"":"��"}</td>
							<td class="may">${data.may=="0"?"-":data.may}${data.may=="0"?"":"��"}</td>
							<td class="jun">${data.jun=="0"?"-":data.jun}${data.jun=="0"?"":"��"}</td>
							<td class="jul">${data.jul=="0"?"-":data.jul}${data.jul=="0"?"":"��"}</td>
							<td class="aug">${data.aug=="0"?"-":data.aug}${data.aug=="0"?"":"��"}</td>
							<td class="sep">${data.sep=="0"?"-":data.sep}${data.sep=="0"?"":"��"}</td>
							<td class="oct">${data.oct=="0"?"-":data.oct}${data.oct=="0"?"":"��"}</td>
							<td class="nov">${data.nov=="0"?"-":data.nov}${data.nov=="0"?"":"��"}</td>
							<td class="dec">${data.dec=="0"?"-":data.dec}${data.dec=="0"?"":"��"}</td>
							<td class="chart_view"><a href="#" onclick="return false;">��Ʈ</a></td>
						</tr>
						</c:forEach>
					</tbody>				
				</table>							
				<c:if test="${(empty list) == false}">
					${pagiNation}
				</c:if>	
			</div>
		</div>
	</div>
	<jsp:include page="../modules/footer.jspf" />	
	<!-- chart content -->
	<div id="chart_dlg"  title="��Ʈ����"  >
		<div id="chartdiv" style="width: 800px; height: 400px; margin: 20px;" ></div>
	</div>		
</body>
<script src="./js/amcharts/amcharts.js" type="text/javascript"></script>  
<script type="text/javascript">
<!--
$(function(){
    var chart = null,
    // �� Į��
    barColor = ["#FF0F00",  "#FF6600", "#FF9E01", "#FCD202", "#F8FF01", "#B0DE09",
                 "#04D215", "#0D8ECF", "#0D52D1",  "#2A0CD0", "#8A0CCF",  "#CD0D74"];
    
    // ��Ʈ ����
    chartCreate = function(titleName){
        // SERIAL CHART
        chart = new AmCharts.AmSerialChart();
        chart.dataProvider = chartData;
        chart.categoryField = "month";
        chart.startDuration = 1;
        // AXES
        // category
        var categoryAxis = chart.categoryAxis;
       // categoryAxis.labelRotation = 45; // this line makes category values to be rotated
        categoryAxis.gridAlpha = 0;
        categoryAxis.fillAlpha = 1;
        categoryAxis.fillColor = "#FAFAFA";
        categoryAxis.gridPosition = "start";

        // value
        var valueAxis = new AmCharts.ValueAxis();
        valueAxis.dashLength = 5;
        valueAxis.title = titleName + " ���� �߼۰Ǽ�";
        valueAxis.axisAlpha = 0;
        chart.addValueAxis(valueAxis);

        // GRAPH
        var graph = new AmCharts.AmGraph();
        graph.valueField = "sendCnt";
        graph.colorField = "color";
        graph.balloonText = "[[category]]: [[value]]";
        graph.type = "column";
        graph.lineAlpha = 0;
        graph.fillAlphas = 1;
        chart.addGraph(graph);    	
    };
    
    // ��ü ��Ʈ ����
    allChartCreate = function(titleName){
    	// ��Ʈ �� ����
    	chartData = [];
    	$(".total").each(function(index){
    		var $this = $(this),
    				title = $this.attr("title"),
   					sendCnt = parseInt($this.text().replace("-", "0").replace("��", "") );
    		if(title == "�հ�"){
    			return;
    		}
   			chartData.push({
   		        part : title,
   		        sendCnt: sendCnt
   		    });
    	});
        
        // PIE CHART
        chart = new AmCharts.AmPieChart();
        chart.dataProvider = chartData;
        chart.titleField = "part";
        chart.valueField = "sendCnt";
        chart.outlineColor = "#FFFFFF";
        chart.outlineAlpha = 0.8;
        chart.outlineThickness = 2;
        // this makes the chart 3D
        chart.depth3D = 15;
        chart.angle = 30;

    };    
    
    
 	// ���� ��Ʈ
 	daysChart = function(maxValue){
        // SERIAL CHART
        chart = new AmCharts.AmSerialChart();
        chart.pathToImages = "./js/amcharts/images/";
        chart.marginLeft = 50;
        chart.marginRight = 50;
        chart.marginTop = 0;
        chart.columnWidth = 1;
        chart.columnSpacing = 7;
        chart.dataProvider = chartData;
        chart.categoryField = "day";
        // AXES
        // category
        var categoryAxis = chart.categoryAxis;
        categoryAxis.parseDates = false; // as our data is date-based, we set parseDates to true
        //categoryAxis.minPeriod = "DD"; // our data is daily, so we set minPeriod to DD
        // value axis
        var valueAxis = new AmCharts.ValueAxis();
        valueAxis.inside = true;
        valueAxis.tickLength = 0;
        valueAxis.axisAlpha = 0;
        valueAxis.minimum = 0;
        valueAxis.maximum = maxValue;	// y�� �ִ밪
        chart.addValueAxis(valueAxis);

        // GRAPH
        var graph = new AmCharts.AmGraph();
        graph.dashLength = 3;
        graph.lineColor = "#7717D7";
        graph.valueField = "sendCnt";
        graph.bullet = "round";
        chart.addGraph(graph);

        // CURSOR
        var chartCursor = new AmCharts.ChartCursor();
        chartCursor.cursorAlpha = 0;
        chart.addChartCursor(chartCursor);

        // GUIDES are used to create horizontal range fills
        var guide = new AmCharts.Guide();
        guide.value = 0;
        guide.toValue = maxValue + 50;
        guide.fillColor = "#FF0000";
        guide.fillAlpha = 0.2;
        guide.lineAlpha = 0;
        valueAxis.addGuide(guide);

   		$( "#chart_dlg" ).dialog( "open" );
        chart.write("chartdiv");
 	}
 	// �� Ŭ���� ���� ��Ʈ ����
 	$(".jan, .feb, .mar, .apr, .may, .jun, .jul, .aug, .sep, .oct, .nov, .dec ").click(function(){
 		var $this = $(this),
 			  userId = $.trim($this.siblings().first().attr('alt')); 		
 		// �߼۰Ǽ��� �ִ��� üũ
 		if($.trim($this.text()) == '-'){
 			alert("�߼۰Ǽ��� �����ϴ�.");
 			return;
 		}
        // ajax�� ��¥�� ������ ��������
        $.get("./DaySentDataAction.sm", 
        		{
        			depth : "user",
        			type : $("#type").val(),
        			code : userId,
        			year : $("#date").val(),
        			month : parseInt($this.index()) - 1
        		}, function(result){
        	var days = $.trim(result).split(","),
        		 maxValue = Math.max.apply(Math, days);	//	���� �ִ밪
      		chartData = [];
	        for (var i = 0, len =days.length ; i < len; i++) {
	            chartData.push({
	                day : i +1 + "��",
	                sendCnt: days[i]
	            });
	        }
	        
            daysChart(maxValue + 50);
        });
 	});
 	
	    
    // ��Ʈ ���� ��ư
    $(".chart_view a").button().click(function(){
    	var $tr =$(this).parents("tr"),
    		title = "";
    	
    	// ��Ʈ �� ����
    	chartData = [];
    	$tr.find("td").each(function(index){
    		// Ÿ��Ʋ�� ���
    		if(index == 0){
    			title = $.trim($(this).text()) ;
    		}
    		
    		if(index >= 2 && index < 14){
    			var sendCnt = parseInt($(this).text().replace("-", "0").replace("��", "") );
    			chartData.push({
    		        month : index - 1 +"��",
    		        sendCnt: sendCnt,
    		        color: barColor[(index - 2) % 12]
    		    });
    		}
    	});
    	chartCreate(title);
        if(chart != null){
    		$( "#chart_dlg" ).dialog( "open" );
        	chart.write("chartdiv");
        }
    });	    
    
    // odd td colume stand out
    $("tbody tr").each(function(i){
       if(i % 2 == 0){
        $(this).children('td').not(":first").css('background', '#efe');
       } 
    });    
    
    //  ��Ʈ ���̾�α� ����
	$( "#chart_dlg" ).dialog({
            autoOpen: false,
            modal: true,
            width : 920,
            height : 560,
            buttons: {
                "Ȯ��": function() {
                    $( this ).dialog( "close" );
                }
            }
    });     
	
//	�Է�â ���� ���� ��ư �Է½� ������
    $("#search").tooltip().keydown(function(event){
	       if(event.keyCode == 13){
	    	   $("#search_frm").submit();
	       }
    });
    
    $("#type").change(function(){
    	$("#search_frm").submit();
    });
    
 // �˻� ��ư    
    $("#search_btn").click(function(){
    	$("#search_frm").submit();
    }); 
 
    $("#date").change(function(){
    	$("#search_frm").submit();
    });
 
});	
//-->
</script>
</html>