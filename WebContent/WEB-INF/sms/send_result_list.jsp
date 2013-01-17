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
.resultMsg{
	display: inline-block; width: 100px; text-overflow:ellipsis; white-space: nowrap; overflow: hidden;
}
#sendResultList td{
	cursor: pointer;
}
select{width: 100px;}
</style>
<body>
	<div id="wrapper">
		<%-- 상단메뉴  --%>
		<jsp:include page="../modules/topmenu.jsp" />
		<div id="contents">
			<%-- 사이드 메뉴  --%>
			<jsp:include page="../modules/sidebox.jsp" />
     	   <div class="boderWrap">
				<h3>
					<img src="images/sms/title_send.gif" alt="전송내역" />
				</h3>
				<%--	검색 처리 --%>
				<%--	검색 처리 --%>
				<form style="clear: both; width: 100%; padding:3px; vertical-align: middle;" id="search_frm" action="./SmsSendResultAction.sm" method="get"  >
					<input value="" name="page" type="hidden" />
					<div style="float: left; display: inline-block;  margin-top:7px; width: 300px; vertical-align: middle;" >
						<select id="mode" name="mode">
							<option value="SMS" ${mode == "SMS"?"selected":""} >SMS</option>
							<option value="LMS" ${mode == "LMS"?"selected":""} >LMS</option>
							<option value="MMS" ${mode == "MMS"?"selected":""} >MMS</option>
						</select>						
						<select id="limit" name="limit" >
							<option ${limit == "10"?"selected":""} value="10">10개</option>
							<option ${limit == "20"?"selected":""} value="20">20개</option>
							<option ${limit == "30"?"selected":""} value="30">30개</option>
							<option ${limit == "40"?"selected":""} value="40">40개</option>
							<option ${limit == "50"?"selected":""} value="50">50개</option>
						</select>	
					</div>
					<div style="float: right; display: inline-block;">
					<select id=type name="type">
						<!-- <option value="from"  ${type == "from"?"selected":""} >보낸이</option> -->
						<option value="message" ${type == "message"?"selected":""} >메세지</option>
						<option value="to" ${type == "to"?"selected":""} >받는번호</option>
					</select>							
						<input title="검색어를 입력하세요" style="margin-bottom: 3px;" value="${search}"  class="search" type="text" name="search" id="search" size="20" />
						<a  href="#"  onclick="return false;" id="search_btn"><img style="margin-bottom:5px;margin-right:5px; right;vertical-align: middle;"  src="./images/base/category_btn.gif" /></a>
					</div>
				</form>
			
				<table id="sendResultList"  style="clear: both;" width="100%" border="0" cellpadding="0" cellspacing="0">
				<!-- 
					<colgroup>
						<col width="10%" />
						<col width="" />
						<col width="18%" />
						<col width="15%" />
						<col width="8%" />
					</colgroup>
				-->	
					<thead>
						<tr height="34px;">
							<th><input id="select_all"  type="checkbox"   /></th>							
							<th>No.</th>
							<th>받는번호</th>							
							<th>메세지</th>
							<th>전송결과</th>
							<th>요청시간</th>
							<th>발송시간</th>																							
						</tr>
					</thead>
					<tbody>
					
					<!--  발송내역이 없는경우 -->
					<c:if test="${empty sendList}">
						<tr>
							<td colspan="7"> 발송내역이 없습니다.</td>
						</tr> 
					</c:if>
										
					<!--  발송내역 리스트  -->
					<c:forEach var="data"  items="${sendList}" >
						<tr>
							<td>					
					   		   <input  type="checkbox" name="del" value="${data.index}"   />
					       </td>							
							<td>					
					   		   <%=no--%>
					       </td>
							<td class="phone">					
					   		   ${data.phone}
					       </td>						       
							<td>					
					   		   <a 	title="${data.msg}"  class="message" href="#" onclick="return false;" >${data.msg}</a>
					       </td>					
							<td>					
					   		<a href="#" onclick="return false" class="resultMsg" title="${data.resultCodeMessage}" >${data.resultCodeMessage}</a>
					       </td>	
							<td>					
					   		${data.senddate}
					       </td>						       
							<td>					
					   		   ${data.realsenddate}
					       </td>	
					     </tr> 
					</c:forEach>
					</tbody>
				</table>
				<%--	삭제 폼 --%>
				<form id="del_frm" action="./ListDeleteAction.sm" method="post" style="float: right;  margin-top: 5px;">
					<input type="hidden" name="token"  id="token"  value="${token}" />
					<input value="" id="indexs" name="indexs" type="hidden" />
					<input value="${mode}"  name="mode" type="hidden" />
					<input value="user"  name="from" type="hidden" />
					<input value="./SmsSendResultAction.sm" name="page" type="hidden" />
					<input value="${page}" name="page" type="hidden" />				
					<!-- <a href="#" onclick="return false;" id="del_btn">삭제</a> -->
				</form>
				<div style="clear: both;"></div>				
				<c:if test="${(empty sendList) == false}">
					${pagiNation}
				</c:if>	
			</div>
		</div>
	</div>
	<jsp:include page="../modules/footer.jspf" />	
	<!-- modal content -->
	<div id="message_dlg" style="text-align: center;">
		<ul class="mymessage" style="heighe:107px; display:block; text-align: center; background: url('./images/sms/sms_back.gif') no-repeat center top;">						
			<li>									
				<textarea  id="msg" style="width:134px; height: 108px;  
				padding:2px; margin-top:21.5px; " readonly="readonly" ></textarea>								
			</li>								
		</ul>
	</div>	
	
	<!-- chart content -->
	<div id="chart_dlg"  title="차트보기"  >
		<div id="chartdiv" style="width: 800px; height: 400px; margin: 20px;" ></div>
	</div>	
		
</body>
<link rel="stylesheet" type="text/css"  href="./css/simplemodal-basic.css" />        
<script type="text/javascript" src="./js/plug-in/jquery.simplemodal.js"></script>
<script src="./js/amcharts/amcharts.js" type="text/javascript"></script>        
<script type="text/javascript">
<!--
$(function(){
	
    // odd td colume stand out
    $("#sendResultList tbody tr").each(function(i){
       if(i % 2 == 0){
        $(this).children('td').css('background', '#efe');
       } 
    });
 
    // 테이블 현재 열 강조 효과
    $("#sendResultList td").hover(
      function () {
        $(this).siblings().andSelf().addClass("hover");
      },
      function () {
        $(this).siblings().andSelf().removeClass("hover");
      }
    );   
    
    // 메시지를 볼수 있도록 툴팁처리
    $(".message").click(function(){
    	var msg = $.trim($(this).text());
    	$("#msg").val(msg);
    	$('#message_dlg').modal();
    });
    
    $(".resultMsg").tooltip();
    
	//	입력창 에서 엔터 버튼 입력시 폼전송
    $("#search").tooltip().keydown(function(event){
	       if(event.keyCode == 13){
	    	   $("#search_frm").submit();
	       }
    });	     
	// 검색 버튼    
    $("#search_btn").click(function(){
    	$("#search_frm").submit();
    });  
	
	// 전화번호 하이픈 넣기
	$(".phone").addHyphen();
	
	// 삭제처리
    $("#del_btn").button().click(function(){
    	var delArr = [];
    	$("input:checked").not("#select_all").each(function(){
    		delArr.push($(this).val());
    	});
    	
    	if(delArr.length <=0){
    		alert("선택된 항목이 없습니다.");
    		return;
    	}    	
    	
    	$("#del_frm #indexs").val(delArr);
    	if(confirm("선택한 내역을 삭제하시겠습니까?")){
    		$("#del_frm").submit();
    	}
    	    	
    });
    
    $("#select_all").change(function(){
    	// 이요소가 체크되어 있다면 다른 체크박스들도 전부 체크를 활성화시킨다.
    	if(this.checked){
    		$("input:checkbox").attr("checked", "checked");
    	}else{
    		$("input:checkbox").removeAttr("checked");
    	}
    });
    
    // 테이블 td를 클릭하면 해당 row의 chekcbox상태 변경
    $("#sendResultList tbody td").click(function(){
    	var $check = $(this).siblings().first().children("input:checkbox");
    	if($check.attr("checked") == "checked"){
    		$check.removeAttr("checked");    		
    	}else{
    		$check.attr("checked", "checked");
    	}
    });    
    
    $("#limit, #mode").change(function(){
    	$("#search_frm").submit();
    });

    $(".gnb_sub1").show();
	$("#top_menu1").attr("data-on", "on");
	$("#top_menu1 > img").attr("src", "./images/top/menu01_on.gif");
	$("#send_result_list_menu > img").attr("src", "./images/top/menu_sub03_on.gif");
	$("#send_result_list_menu").attr("data-on", "on");

});	
//-->
</script>
</html>