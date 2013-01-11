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
	display: inline-block; width: 100px; text-overflow:ellipsis; white-space: nowrap; overflow: hidden;
}
#sendResultList td{
	cursor: pointer;
}
select{width: 100px;}
.resultMsg{
	display: inline-block; width: 100px; text-overflow:ellipsis; white-space: nowrap; overflow: hidden;
}
</style>
<body>
	<div id="wrapper">
		<%-- 상단메뉴  --%>
		<jsp:include page="../modules/admin_topmenu.jsp" />
		<div id="contents">
			<%-- 사이드 메뉴  --%>
			<jsp:include page="../modules/admin_sidebox.jsp" />
     	   <div class="boderWrap">
				<h3>
					<img src="images/admin/title_send_list.gif" alt="발송내역" />
				</h3>
				<%--	검색 처리 --%>
				<form style="clear: both; width: 100%; padding:3px; vertical-align: middle;" id="search_frm" action="./AllSendListAction.sm" method="get"  >
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
						<option value="from"  ${type == "from"?"selected":""} >보낸이</option>
						<option value="message" ${type == "message"?"selected":""} >메세지</option>
						<option value="to" ${type == "to"?"selected":""} >받는번호</option>
					</select>							
						<input title="검색어를 입력하세요" style="margin-bottom: 3px;" value="${search}"  class="search phone" type="text" name="search" id="search" size="20" />
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
							<th>보낸이</th>
							<th>받는번호</th>							
							<th>메세지</th>
							<th>전송결과</th>
							<th>전송타입</th>
							<th>발송시간</th>																							
						</tr>
					</thead>
					<tbody>
					
					<!--  발송내역이 없는경우 -->
					<c:if test="${empty sendList}">
						<tr>
							<td colspan="8"> 발송내역이 없습니다.</td>
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
					       <td>					
					   		   <a href="./UserSendListAction.sm?index=${data.userIndex}&userid=${data.userId}">${data.userId}</a>
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
					   		    ${mode}
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
					<input value="admin"  name="from" type="hidden" />
					<input value="${mode}"  name="mode" type="hidden" />
					<a href="#" onclick="return false;" id="del_btn">삭제</a>
				</form>
				<div style="clear: both;"></div>				
				<c:if test="${(empty sendList) == false}">
					${pagiNation}
				</c:if>	
				<!-- 
				<div class="page">
					<a href="#"><img src="images/notice/page_prev_btn.gif" /></a><a
						href="#"><span>1</span></a><a href="#">2</a><a href="#"><img
						src="images/notice/page_next_btn.gif" /></a>
				</div>
				-->
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
</body>
<link rel="stylesheet" type="text/css"  href="./css/simplemodal-basic.css" />        
<script type="text/javascript" src="./js/plug-in/jquery.simplemodal.js"></script>
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
    
    $(".resultMsg").tooltip();
    // 메시지를 볼수 있도록 툴팁처리
    $(".message").click(function(){
    	var msg = $.trim($(this).text());
    	$("#msg").val(msg);
    	$('#message_dlg').modal();
    });
    
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
    
    $("#limit, #mode").change(function(){
    	$("#search_frm").submit();
    });

	$("#top_menu2").attr("data-on", "on");
	$("#top_menu2 > img").attr("src", "./images/admin/menu02_admin_on.gif");
	$("#all_send_result_list_sub_menu > img").attr("src", "./images/admin/menu_admin_sub08_on.gif");
	$("#all_send_result_list_sub_menu").attr("data-on", "on");
    $(".gnb_sub2").show();	
});	
//-->
</script>
</html>