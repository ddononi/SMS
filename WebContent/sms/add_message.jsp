<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.util.*" %>		
<%@ page import="kr.go.police.sms.*" %>		
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	int userIndex = Integer.valueOf(session.getAttribute("index").toString());
	// �� �׷� ��� ��������
	SmsDAO dao = new SmsDAO();	
	List<Group> groupList = dao.getMyGroupList(userIndex);		
%>	
<c:set var="groups"  value ="<%=groupList %>" />
<%-- ���  --%>
<jsp:include page="../include/header.jsp" />
  <style>
    .column { width: 170px; float: left; padding-bottom: 100px; }
    .portlet { margin: 0 1em 1em 0; cursor: pointer; }
    .portlet-header { margin: 0.3em; padding-bottom: 4px; padding-left: 0.2em; }
    .portlet-header .ui-icon { float: right; }
    .portlet-content { padding: 0.4em; }
    .ui-sortable-placeholder { border: 1px dotted black; visibility: visible !important; height: 50px !important; }
    .ui-sortable-placeholder * { visibility: hidden; }
</style>
<script>
$(function() {
	// �޼��� �ڽ� ó��
    $( ".column" ).sortable({
        connectWith: ".column"
    });
    $( ".portlet" ).addClass( "ui-widget ui-widget-content ui-helper-clearfix ui-corner-all" )
        .find( ".portlet-header" )
            .addClass( "ui-widget-header ui-corner-all" )
            .prepend( "<span class='ui-icon ui-icon-minusthick'></span>")
            .end()
        .find( ".portlet-content" );

    $( ".portlet-header .ui-icon" ).click(function() {
        $( this ).toggleClass( "ui-icon-minusthick" ).toggleClass( "ui-icon-plusthick" );
        $( this ).parents( ".portlet:first" ).find( ".portlet-content" ).toggle();
    });

    $( ".column" ).disableSelection();
    
    // ������ �߰�
    $("#add_btn").click(function(){
    	// �׷� �ε����� 
    	$("#groupIndex").val($("#group_select option:selected").val());
    	// �׷��
    	$("#groupName").val($("#group_select option:selected").text());    	
    	alert($("#groupName").val());
    	var title = $("#title").val();
    	var message = $("#message").val();    	
    	if(title.length <= 0){
    		alert("������ �Է��ϼ���");
    		return;
    	}
    	
    	if(message.length <= 0){
    		alert("������ �Է��ϼ���");
    		return;
    	}    	
    	
    	$("#add_message_form").submit();
    });
});
</script>
<body>
	<div id="wrapper">
		<%-- ��ܸ޴�  --%>
		<jsp:include page="../include/topmenu.jsp" />

		<div id="contents">
			<%-- ���̵� �޴�  --%>
			<jsp:include page="../include/sidebox.jsp" />
			<div id="contentsWrap">
				<h3>
					<img src="../images/lettersend/title.gif" alt="��������" />
				</h3>
				<form style="float: right;">
					<select id="group_select">
						<option value="0">�⺻�׷�</option>
						<c:forEach var="group"  items="${groups}" >
							<option value="${group.index}">${group.group}</option>
						</c:forEach>							
					</select>
				</form>
				<div>
					<!--  ������ �߰� �� -->
					<form id="add_message_form" method="post" action="../MyMessageAddAction.sm">
							<input value=""	type="hidden" id="groupIndex" name="groupIndex" />	
							<input value=""	type="hidden" id="groupName" name="groupName" />							
							<input value="" title="������ �Է��ϼ���"	type="text"	 id="title" name="title" /><br/>
							<textarea rows="5" cols="50" id="message" name="message"></textarea>
							<a href="#" id="add_btn">�߰�</a>
					</form>
				</div>
			</div>
		<div id="footer">Ǫ�Ϳ���</div>
	</div>
</body>

</html>