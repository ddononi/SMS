<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.util.*" %>	
<%@ page import="kr.go.police.sms.*" %>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	int no = (Integer)request.getAttribute("no");
%>	
<%-- 헤더  --%>
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
		<%-- 상단메뉴  --%>
		<jsp:include page="../modules/admin_topmenu.jsp" />
		<div id="contents">
			<%-- 사이드 메뉴  --%>
			<jsp:include page="../modules/admin_sidebox.jsp" />
     	   <div class="boderWrap">
				<div class=user_name style="text-align:left; font-size:20px; color:#000; font-family:'Nanum'; font-weight:bold;">
					<h3>
						<img src="images/admin/title_stats.gif" alt="통계" />
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
						<option ${year == date?"selected":""} value="${year}">${year}년</option>
						</c:forEach>					
						</select>
					</div>
						<div style="float: right; display: inline-block;">
							<select id=s_type name="s_type">
								<option value="id"  ${s_type == "id"?"selected":""} >ID</option>
								<option value="name" ${s_type == "name"?"selected":""} >이름</option>
							</select>										
							<input title="검색할 단어를 입력하세요." style="margin-bottom: 3px;" value="${search}"  class="search phone" type="text" name="search" id="search" size="20" />
							<a  href="#"  onclick="return false;" id="search_btn"><img style="margin-bottom:5px;margin-right:5px; right;vertical-align: middle;"  src="./images/base/category_btn.gif" /></a>
						</div>												
					</form>					
			
				<!-- 테이블 -->
				<table id="sendResultList"  style="clear: both;" width="100%" border="0" cellpadding="0" cellspacing="0">
					<thead>
						<tr height="34px;">
							<!-- <th>이름</th> -->
							<th>ID</th>													
							<th>전체</th>
							<th>1월</th>							
							<th>2월</th>
							<th>3월</th>
							<th>4월</th>
							<th>5월</th>
							<th>6월</th>
							<th>7월</th>
							<th>8월</th>
							<th>9월</th>
							<th>10월</th>
							<th>11월</th>
							<th>12월</th>
							<th>차트</th>																														
						</tr >
					</thead>
					<c:forEach var="data"  items="${list}" >
						<tr>
							<td class='userName' alt="${data.id}">${data.name}</td>
							<!-- <td>${data.id}</td> -->
							<td class="total">${data.total=="0"?"-":data.total}${data.total=="0"?"":"건"}</td>
							<td class="jan">${data.jan=="0"?"-":data.jan}${data.jan=="0"?"":"건"}</td>
							<td class="feb">${data.feb=="0"?"-":data.feb}${data.feb=="0"?"":"건"}</td>
							<td class="mar">${data.mar=="0"?"-":data.mar}${data.mar=="0"?"":"건"}</td>
							<td class="apr">${data.apr=="0"?"-":data.apr}${data.apr=="0"?"":"건"}</td>
							<td class="may">${data.may=="0"?"-":data.may}${data.may=="0"?"":"건"}</td>
							<td class="jun">${data.jun=="0"?"-":data.jun}${data.jun=="0"?"":"건"}</td>
							<td class="jul">${data.jul=="0"?"-":data.jul}${data.jul=="0"?"":"건"}</td>
							<td class="aug">${data.aug=="0"?"-":data.aug}${data.aug=="0"?"":"건"}</td>
							<td class="sep">${data.sep=="0"?"-":data.sep}${data.sep=="0"?"":"건"}</td>
							<td class="oct">${data.oct=="0"?"-":data.oct}${data.oct=="0"?"":"건"}</td>
							<td class="nov">${data.nov=="0"?"-":data.nov}${data.nov=="0"?"":"건"}</td>
							<td class="dec">${data.dec=="0"?"-":data.dec}${data.dec=="0"?"":"건"}</td>
							<td class="chart_view"><a href="#" onclick="return false;">차트</a></td>
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
	<div id="chart_dlg"  title="차트보기"  >
		<div id="chartdiv" style="width: 800px; height: 400px; margin: 20px;" ></div>
	</div>		
</body>
<script src="./js/amcharts/amcharts.js" type="text/javascript"></script>  
<script type="text/javascript">
<!--
$(function(){
    var chart = null,
    // 바 칼라
    barColor = ["#FF0F00",  "#FF6600", "#FF9E01", "#FCD202", "#F8FF01", "#B0DE09",
                 "#04D215", "#0D8ECF", "#0D52D1",  "#2A0CD0", "#8A0CCF",  "#CD0D74"];
    
    // 차트 생성
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
        valueAxis.title = titleName + " 월별 발송건수";
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
    
    // 전체 차트 생성
    allChartCreate = function(titleName){
    	// 차트 값 설정
    	chartData = [];
    	$(".total").each(function(index){
    		var $this = $(this),
    				title = $this.attr("title"),
   					sendCnt = parseInt($this.text().replace("-", "0").replace("건", "") );
    		if(title == "합계"){
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
    
    
 	// 일일 차트
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
        valueAxis.maximum = maxValue;	// y축 최대값
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
 	// 월 클릭시 일일 차트 보기
 	$(".jan, .feb, .mar, .apr, .may, .jun, .jul, .aug, .sep, .oct, .nov, .dec ").click(function(){
 		var $this = $(this),
 			  userId = $.trim($this.siblings().first().attr('alt')); 		
 		// 발송건수가 있는지 체크
 		if($.trim($this.text()) == '-'){
 			alert("발송건수가 없습니다.");
 			return;
 		}
        // ajax로 날짜별 데이터 가져오기
        $.get("./DaySentDataAction.sm", 
        		{
        			depth : "user",
        			type : $("#type").val(),
        			code : userId,
        			year : $("#date").val(),
        			month : parseInt($this.index()) - 1
        		}, function(result){
        	var days = $.trim(result).split(","),
        		 maxValue = Math.max.apply(Math, days);	//	전송 최대값
      		chartData = [];
	        for (var i = 0, len =days.length ; i < len; i++) {
	            chartData.push({
	                day : i +1 + "일",
	                sendCnt: days[i]
	            });
	        }
	        
            daysChart(maxValue + 50);
        });
 	});
 	
	    
    // 차트 보기 버튼
    $(".chart_view a").button().click(function(){
    	var $tr =$(this).parents("tr"),
    		title = "";
    	
    	// 차트 값 설정
    	chartData = [];
    	$tr.find("td").each(function(index){
    		// 타이틀명 얻기
    		if(index == 0){
    			title = $.trim($(this).text()) ;
    		}
    		
    		if(index >= 2 && index < 14){
    			var sendCnt = parseInt($(this).text().replace("-", "0").replace("건", "") );
    			chartData.push({
    		        month : index - 1 +"월",
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
    
    //  차트 다이얼로그 설정
	$( "#chart_dlg" ).dialog({
            autoOpen: false,
            modal: true,
            width : 920,
            height : 560,
            buttons: {
                "확인": function() {
                    $( this ).dialog( "close" );
                }
            }
    });     
	
//	입력창 에서 엔터 버튼 입력시 폼전송
    $("#search").tooltip().keydown(function(event){
	       if(event.keyCode == 13){
	    	   $("#search_frm").submit();
	       }
    });
    
    $("#type").change(function(){
    	$("#search_frm").submit();
    });
    
 // 검색 버튼    
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