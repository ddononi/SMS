<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.util.*" %>	
<%@ page import="kr.go.police.account.*" %>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	//	����Ʈ ��ȣ
	int no = (Integer)request.getAttribute("no");	
%>	
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
					<img src="images/admin/title_approve.gif" alt="���δ��" />
				</h3>
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
							<th>�����</th>
						</tr>
					</thead>
					<tbody>
					<!--  ȸ������� ���°�� -->
					<c:if test="${empty userList}">
						<tr>
							<td colspan="6"> �̽��� ȸ���� �����ϴ�.</td>
						</tr> 
					</c:if>
										
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
					   		   <a href="./UserDetailAction.ac?index=${user.index}" >${user.regDate}</a>
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
    $("table:last tbody tr").each(function(i){
       if(i % 2 == 1){
        $(this).children('td').css('background', '#efe');
       } 
    });
 
    // ���̺� ���� �� ���� ȿ��
    $("table:last  tr").bind('mouseover', function(){
    	var $this = $(this);
    	$this.find("td").addClass("hover");
    	//$this.find("a").spectrum();     
    }).bind('mouseout', function(){
    	var $this = $(this);    	
    	$this.find("td").removeClass("hover");
    	//$this.find("a").css("color", "#8b8b8b"); 
    });
    
    /*
    $("#select_all").change(function(){
    	// �̿�Ұ� üũ�Ǿ� �ִٸ� �ٸ� üũ�ڽ��鵵 ���� üũ�� Ȱ��ȭ��Ų��.
    	if(this.checked){
    		$("input:checkbox").attr("checked", "checked");
    	}else{
    		$("input:checkbox").removeAttr("checked");
    	}
    });
    
    // ���̺� td�� Ŭ���ϸ� �ش� row�� chekcbox���� ����
    $("#sendResultList tbody td").click(function(){
    	var $check = $(this).siblings().first().children("input:checkbox");
    	if($check.attr("checked") == "checked"){
    		$check.removeAttr("checked");    		
    	}else{
    		$check.attr("checked", "checked");
    	}
    });    
    */

	$("#top_menu1").attr("data-on", "on");
	$("#top_menu1 > img").attr("src", "./images/admin/menu01_admin_on.gif");
	$("#wait_user_list_sub_menu > img").attr("src",  "./images/admin/menu_admin_sub07_on.gif");
	$("#wait_user_list_sub_menu").attr("data-on", "on");
	$("#top_menu1").trigger("mouseover");    


});	
//-->
</script>
</html>