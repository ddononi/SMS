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
}
.user_name{font-size:20px; font-weight:bold; text-align:center;}
#wrapper #contents .boderWrap #month a{margin:10px 5px; font-size:15px; color:#00F;}
</style>
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
					</h3>
					
					<form style="clear: both; width: 100%; padding:3px; vertical-align: middle;" id="search_frm" action="./StatisticsSearchAction.sm" method="post"  >
						<input type="hidden" name="s_date" value="${s_date}">
						<input type="hidden" name="c_date" value="${c_date}">
						<select id="type" name="type">
							<option ${type == "SMS"?"selected":""} value="SMS">SMS</option>
							<option ${type == "LMS"?"selected":""} value="LMS">LMS</option>
							<option ${type == "MMS"?"selected":""} value="MMS">MMS</option>
						</select>
						<div style="float: right; display: inline-block;">
						${s_date}부터 ${c_date}까지의 검색
						</div>										
					</form>					
				</div>				
				<!-- 테이블 -->
				<table id="sendResultList"  style="clear: both;" width="100%" border="0" cellpadding="0" cellspacing="0">
					<thead>
						<tr height="34px;">
							<th>이름</th>													
							<th>아이디</th>
							<th>경찰서</th>							
							<th>부서</th>
							<th>발송건수</th>																																					
						</tr>
					</thead>
					<tbody>
					
					<c:if test="${empty list}">
						<tr>
							<td colspan="5"> 발송내역이 없습니다.</td>
						</tr> 
					</c:if>
					
					<c:forEach var="data"  items="${list}" >
						<tr>
							<td>${data.name}</td>
							<td>${data.id}</td>
							<td><a href="./PoliceStatisticsAction.sm?pscode=${data.pscode}&type=${type}">${data.psname}</a></td>
							<td><a href="./DeptStatisticsAction.sm?deptcode=${data.deptcode}&type=${type}">${data.deptname}</a></td>
							<td>${data.jan}건</td>		
						</tr>
						</c:forEach>
					</tbody>				
				</table>
				
			</div>
		</div>
	</div>
	<jsp:include page="../modules/footer.jspf" />	
</body>
<script type="text/javascript">
<!--
$(function(){
    $("#type").change(function(){
    	$("#search_frm").submit();
    });
});	
//-->
</script>
</html>