<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.util.*" %>	
<%@ page import="kr.go.police.sms.*" %>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	//	리스트 번호
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
}</style>
<body>
	<div id="wrapper">
		<%-- 상단메뉴  --%>
		<jsp:include page="../modules/topmenu.jsp" />
		<div id="contents">
			<%-- 사이드 메뉴  --%>
			<jsp:include page="../modules/sidebox.jsp" />
     	   <div class="boderWrap">
				<h3>
					<img src="images/sms/title_reservlist.gif" alt="예약내역" />
				</h3>
				<%--	검색 처리 --%>
				<form style="clear: both; width: 100%; padding:3px; vertical-align: middle;" id="search_frm" action="./ReservedListAction.sm" method="get"  >
					<input value="" name="page" type="hidden" />
					<select id="limit" name="limit" style="float: left; display: inline-block;  margin-top:7px; width: 80px; vertical-align: middle;" >
						<option ${limit == "10"?"selected":""} value="10">10개</option>
						<option ${limit == "20"?"selected":""} value="20">20개</option>
						<option ${limit == "30"?"selected":""} value="30">30개</option>
						<option ${limit == "40"?"selected":""} value="40">40개</option>
						<option ${limit == "50"?"selected":""} value="50">50개</option>
					</select>	
					<div style="float: right; display: inline-block;">
					<select id=type name="type">
						<!-- <option value="from"  ${type == "from"?"selected":""} >보낸이</option> -->
						<option value="message" ${type == "message"?"selected":""} >메세지</option>
						<option value="to" ${type == "to"?"selected":""} >받는번호</option>
					</select>							
						<input title="검색어를 입력하세요" style="margin-bottom: 3px;" value="${search}"  class="search phone" type="text" name="search" id="search" size="20" />
						<a  href="#"  onclick="return false;" id="search_btn"><img style="margin-bottom:5px;margin-right:5px; right;vertical-align: middle;"  src="./images/base/category_btn.gif" /></a>
					</div>
				</form>
				
				<!--예약내역 게시판-->
				<table id="list"  style="clear: both;" width="100%" border="0" cellpadding="0" cellspacing="0">
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
							<th>전송타입</th>
							<th>예약시간</th>																							
						</tr>
					</thead>
					<tbody>
					
					<!--  발송내역이 없는경우 -->
					<c:if test="${empty list}">
						<tr>
							<td colspan="8"> 발송내역이 없습니다.</td>
						</tr> 
					</c:if>
										
					<!--  발송내역 리스트  -->
					<c:forEach var="data"  items="${list}" >
						<tr>
							<td>					
					   		   <input  type="checkbox" name="del" value="${data.index}"   />
					       </td>							
							<td>					
					   		   <%=no--%>
					       </td>
					       <td>					
					   		   <a href="./UserReserveListAction.sm?index=${data.userIndex}&userid=${data.id}">${data.id}</a>
					       </td>
							<td class="phone">					
					   		   ${data.toPhone}
					       </td>						       
							<td>					
					   		   <a 	title="${data.message}"  class="message" href="#" onclick="return false;" >${data.message}</a>
					       </td>					
							<td>					
					   		   ${data.flag}
					       </td>						       
							<td>					
					   		   ${data.reserveDate}
					       </td>	
					     </tr> 
					</c:forEach>
					</tbody>
				</table>
				<%--	삭제 폼 --%>
				<form id="del_frm" action="./ListDeleteAction.sm" method="post" style="float: right;  margin-top: 5px;">
					<input type="hidden" name="token"  id="token"  value="${token}" />
					<input value="" id="indexs" name="indexs" type="hidden" />
					<input value="./ReservedListAction.sm" name="page" type="hidden" />
					<a href="#" onclick="return false;" id="del_btn">삭제</a>
				</form>
				<div style="clear: both;"></div>				
				<c:if test="${(empty list) == false}">
					${pagiNation}
				</c:if>	
			</div>
		</div>
	</div>
	<jsp:include page="../modules/footer.jspf" />	
</body>
<script type="text/javascript">
<!--
$(function(){
    // odd td colume stand out
    $("#list tbody tr").each(function(i){
       if(i % 2 == 0){
        	$(this).children('td').css('background', '#efe');
       } 
    });
 
    // 테이블 현재 열 강조 효과
    $("#list td").hover(
	      function () {
	        $(this).siblings().andSelf().addClass("hover");
	      },
	      function () {
	        $(this).siblings().andSelf().removeClass("hover");
	      }
    );   
    
    // 메시지를 볼수 있도록 툴팁처리
    $(".message").tooltip();
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
	
    $("#limit").change(function(){
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
    
    $(".gnb_sub1").show();
	$("#top_menu1").attr("data-on", "on");
	$("#top_menu1 > img").attr("src", "./images/top/menu01_on.gif");
	$("#reservered_send_top_menu > img").attr("src", "./images/top/menu_sub02_on.gif");
	$("#reservered_send_top_menu").attr("data-on", "on");
});	

//-->
</script>
</html>