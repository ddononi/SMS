<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.util.*" %>	
<%@ page import="kr.go.police.account.*" %>	
<%@ page import="java.util.*" %>	    
<%@ page import="kr.go.police.board.*" %>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%
	List<BoardBean> list = (List<BoardBean>)request.getAttribute("list");
	// ���������̼�
	String pagiNation = (String)request.getAttribute("pagiNation");
	// ����Ʈ ����
	int listSize = (Integer)request.getAttribute("listSize");	
	//	����Ʈ ��ȣ
	int no = (Integer)request.getAttribute("no");		
%>
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
					<img src="images/notice/title_notice.gif" alt="���Ǻ���" />
				</h3>
				<!--�Խ���-->
				<table id="board_table" width="100%" border="0" cellpadding="0" cellspacing="0">
					<colgroup>
						<col width="10%" />
						<col width="" />
						<col width="18%" />
						<col width="15%" />
						<col width="8%" />
					</colgroup>
					<thead>
						<tr height="34px;">
							<th>NO.</th>
							<th>����</th>
							<th>�ۼ���</th>
							<th>�����</th>
							<th>��ȸ</th>
						</tr>
					</thead>
					<tbody>
					<!--  ���������� ���°�� -->
					<c:if test="${empty list}">
						<tr>
							<td colspan="5"> ���ǻ����� �����ϴ�.</td>
						</tr> 
					</c:if>
										
					<!-- ���Ǹ�� -->
					<c:forEach var="data"  items="${list}" >
						<tr>
							<td class="num"><%=no--%></td>
							<td class="tite">
									${data.newIcon?'<img src="images/notice/icon_n.gif" />':''}							
									<a href="./BoardDetailAction.bo?index=${data.index}">${data.notice?'[����]':''}
									${data.title}</a>
							</td>
							<td class="writer">${data.registerName}</td>
							<td class="day">${data.regDate}</td>
							<td class="hit">${data.viewCount}</td>
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
</body>
<script type="text/javascript">
<!--
$(function(){

	
    // odd td colume stand out
    $("#board_table tbody tr").each(function(i){
       if(i % 2 == 0){
        $(this).children('td').css('background', '#efe');
       } 
    });
 
    // ���̺� ���� �� ���� ȿ��
    $("#board_table td").hover(
      function () {
        $(this).siblings().andSelf().addClass("hover");
      },
      function () {
        $(this).siblings().andSelf().removeClass("hover");
      }
    ); 
    
    
	// �޴� ó��
	$("#top_menu4").attr("data-on", "on");
	$("#top_menu4 > img").attr("src", "./images/top/menu04_on.gif");
	$("#board_view_menu > img").attr("src", "./images/top/menu_sub07_on.gif");
	$("#board_view_menu").attr("data-on", "on");
	$("#top_menu4").trigger("mouseover");
	$(".gnb_sub4").show();
	
});	
//-->
</script>
</html>