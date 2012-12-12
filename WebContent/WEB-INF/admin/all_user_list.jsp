<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.util.*" %>	
<%@ page import="kr.go.police.account.*" %>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	//	����Ʈ ��ȣ
	int no = (Integer)request.getAttribute("no");	
%>	
<%-- ���  --%>
<jsp:include page="../modules/header.jsp" />
<body>
	<div id="wrapper">
		<%-- ��ܸ޴�  --%>
		<jsp:include page="../modules/admin_topmenu.jsp" />
		<div id="contents">
			<%-- ���̵� �޴�  --%>
			<jsp:include page="../modules/admin_sidebox.jsp" />
     	   <div class="boderWrap">
				<h3>
					<img src="images/admin/title_memberlist.gif" alt="��������" />
				</h3>
				<!--�Խ���-->
				<table id="usersList" width="100%" border="0" cellpadding="0" cellspacing="0">
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
							<th>No.</th>						
							<th>�̸�</th>
							<th>���̵�</th>							
							<th>������</th>
							<th>�μ�</th>
							<th>���</th>							
							<th>��ȭ��ȣ</th>
						</tr>
					</thead>
					<tbody>
					<!--  ȸ�� ����Ʈ -->
					<c:forEach var="user"  items="${userList}"  >
						<tr>
							<td>					
					   		   <%=no--%>
					       </td>						
							<td>					
					   		   <a href="./UserDetailAction.ac?index=${user.index}" >${user.name}</a>
					       </td>
							<td>					
					   		   <a href="./UserDetailAction.ac?index=${user.index}" >${user.id}</a>
					       </td>						       
							<td>					
					   		   <a href="./UserDetailAction.ac?index=${user.index}" >${user.psName}</a>
					       </td>					
							<td>					
					   		   <a href="./UserDetailAction.ac?index=${user.index}" >${user.deptName}</a>
					       </td>
							<td>					
					   		   <a href="./UserDetailAction.ac?index=${user.index}" >${user.grade}</a>
					       </td>					       	
							<td>					
					   		   <a  class="phone" href="./UserDetailAction.ac?index=${user.index}" >${user.phone1}</a>
					       </td>	
					     </tr> 
					</c:forEach>
					</tbody>
				</table>
				<c:if test="${(empty userList) == false}">
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
    $("#usersList tbody tr").each(function(i){
       if(i % 2 == 0){
        $(this).children('td').css('background', '#efe');
       } 
    });
 
    // ���̺� ���� �� ���� ȿ��
    $("#usersList tr").bind('mouseover', function(){
    	var $this = $(this);
    	$this.find("td").addClass("hover");
    	//$this.find("a").spectrum();     
    }).bind('mouseout', function(){
    	var $this = $(this);    	
    	$this.find("td").removeClass("hover");
    	//$this.find("a").css("color", "#8b8b8b"); 
    });

    $(".phone").addHyphen();
    
	$("#top_menu1").attr("data-on", "on");
	$("#top_menu1 > img").attr("src", "./images/admin/menu01_admin_on.gif");
	$("#user_list_sub_menu > img").attr("src", "./images/admin/menu_admin_sub05_on.gif");
	$("#user_list_sub_menu").attr("data-on", "on");
	$("#top_menu1").trigger("mouseover");

});	
//-->
</script>
</html>