<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.util.*" %>	
<%@ page import="kr.go.police.account.*" %>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	// requset �� ���� ���� ����Ʈ�� �����´�.
	List<UserBean> list = (List<UserBean>)request.getAttribute("userList");
%>	
<c:set var="list"  value ="<%=list %>" />
<%-- ���  --%>
<jsp:include page="../modules/header.jsp" />
<link rel="stylesheet" type="text/css" href="./css/sms.css"/>
<script type="text/javascript" src="./js/sms_page.js"></script>
<body>
	<div id="wrapper">
		<%-- ��ܸ޴�  --%>
		<jsp:include page="../modules/topmenu.jsp" />

		<div id="contents">
			<%-- ���̵� �޴�  --%>
			<jsp:include page="../modules/sidebox.jsp" />

			<div id="contentsWrap">
				<h3>
					<img src="./images/lettersend/title.gif" alt="���ں�����" />
				</h3>
				<div>
					<!--  ���� ����Ʈ �κ� -->
					<table>
					<c:forEach var="user"  items="${list}" >
						<tr>
							<td>					
					   		   <a href="./UserDetailAction.ac?index=${user.index}" >${user.name}</a>
					       </td>
							<td>					
					   		   <a href="./UserDetailAction.ac?index=${user.index}" >${user.psName}</a>
					       </td>					
							<td>					
					   		   <a href="./UserDetailActionac?index=${user.index}" >${user.deptName}</a>
					       </td>	
							<td>					
					   		   <a href="./UserDetailAction.ac?index=${user.index}" >${user.id}</a>
					       </td>						       					              
					     </tr> 
					</c:forEach>
					</table>
				</div>
			</div>
		</div>
		<div id="footer">Ǫ�Ϳ���</div>
	</div>
</body>


</html>