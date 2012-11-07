<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.util.*" %>	
<%@ page import="kr.go.police.sms.*" %>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	// requset �� ���� ���� ���� ����Ʈ�� �����´�.
	List<SMS> list = (List<SMS>)request.getAttribute("list");
%>	
<c:set var="list"  value ="<%=list %>" />
<%-- ���  --%>
<jsp:include page="../modules/header.jsp" />
<body>
	<div id="wrapper">
		<%-- ��ܸ޴�  --%>
		<jsp:include page="../modules/topmenu.jsp" />
		<div id="contents">
			<%-- ���̵� �޴�  --%>
			<jsp:include page="../modules/sidebox.jsp" />
     	   <div class="boderWrap">
				<h3>
					<img src="images/notice/title_notice.gif" alt="���೻��" />
				</h3>
				<!--���೻�� �Խ���-->
				<table id="reservedList" width="100%" border="0" cellpadding="0" cellspacing="0">
					<colgroup>
						<col width="8%" />
						<col width="" />
						<col width="18%" />
						<col width="18%" />
						<col width="8%" />
					</colgroup>

					<thead>
						<tr height="34px;">
							<th>No.</th>
							<th>����</th>							
							<th>����ð�</th>
							<th>��ȭ��ȣ</th>
							<th>����</th>							
						</tr>
					</thead>
					<tbody>
					<!--  ȸ�� ����Ʈ -->
					<c:forEach var="data"  items="${list}" >
						<tr>
							<td>					
					   		   <a href="./UserDetailAction.ac?index=${data.index}" >${data.index}</a>
					       </td>
							<td>					
					   		   <a href="./UserDetailAction.ac?index=${data.index}" >${data.message}</a>
					       </td>						       
							<td>					
					   		   <a href="./UserDetailActionac?index=${data.index}" >${data.reserveDate}</a>
					       </td>	
							<td>					
					   		   <a href="./UserDetailActionac?index=${data.index}" >${data.toPhone}</a>
					       </td>	
							<td>					
					   		   <a href="./UserDetailActionac?index=${user.index}" >${user.sendResult}</a>
					       </td>						       					       
					     </tr> 
					</c:forEach>
					</tbody>
				</table>
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
		<div id="footer">Ǫ�Ϳ���</div>
	</div>
</body>
<script type="text/javascript">
<!--
$(function(){
    // odd td colume stand out
    $("#reservedList tbody tr").each(function(i){
       if(i % 2 == 0){
        	$(this).children('td').css('background', '#efe');
       } 
    });
 
    // ���̺� ���� �� ���� ȿ��
    $("#reservedList td").hover(
	      function () {
	        $(this).siblings().andSelf().addClass("hover");
	      },
	      function () {
	        $(this).siblings().andSelf().removeClass("hover");
	      }
    );   
    
    $(".gnb_sub1").show();
	$("#top_menu1").attr("data-on", "on");
	$("#top_menu1 > img").attr("src", "./images/top/menu01_on.gif");
	$("#reservered_send_top_menu > img").attr("src", "./images/top/menu_sub02_on.gif");
	$("#reservered_send_top_menu").attr("data-on", "on");
});	

//-->
</script>
</html>