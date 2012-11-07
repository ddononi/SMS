<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.util.*" %>		
<%@ page import="kr.go.police.sms.*" %>		
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	// requset �� ���� �� ���� ������ �����´�.
	List<Message> list = (List<Message>)request.getAttribute("messages");
%>	
<c:set var="messages"  value ="<%=list %>" />
<%-- ���  --%>
<jsp:include page="../modules/header.jsp" />
  <style>
    .column { width: 170px; float: left; padding-bottom: 100px; }
    .portlet { margin: 0 1em 1em 0; }
    .portlet-header { margin: 0.3em; padding-bottom: 4px; padding-left: 0.2em; }
    .portlet-header .ui-icon { float: right; }
    .portlet-content { padding: 0.4em; }
    .ui-sortable-placeholder { border: 1px dotted black; visibility: visible !important; height: 50px !important; }
    .ui-sortable-placeholder * { visibility: hidden; }
</style>
<script type="text/javascript" src="../js/sms_page.js"></script>
<script>
$(function() {
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
});
</script>
<body>
	<div id="wrapper">
		<%-- ��ܸ޴�  --%>
		<jsp:include page="../modules/topmenu.jsp" />

		<div id="contents">
			<%-- ���̵� �޴�  --%>
			<jsp:include page="../modules/sidebox.jsp" />

			<div id="contentsWrap">
				<h3>
					<img src="../images/lettersend/title.gif" alt="��������" />
				</h3>
				
					<c:forEach var="msg"  items="${messages}" >
									<div class="column">
								    <div class="portlet">
								        <div class="portlet-header">${msg.title}</div>
								        <div class="portlet-content">${msg.message}</div>
								    </div>					
						 </div>
					</c:forEach>				
				
				<div class="column">
				 
				    <div class="portlet">
				        <div class="portlet-header">Feeds</div>
				        <div class="portlet-content">Lorem ipsum dolor sit amet, consectetuer adipiscing elit</div>
				    </div>
				    <div class="portlet">
				        <div class="portlet-header">News</div>
				        <div class="portlet-content">Lorem ipsum dolor sit amet, consectetuer adipiscing elit</div>
				    </div>
				</div>
				 
				<div class="column">
				    <div class="portlet">
				        <div class="portlet-header">Shopping</div>
				        <div class="portlet-content">Lorem ipsum dolor sit amet, consectetuer adipiscing elit</div>
				    </div>
				 
				</div>
				 
				<div class="column">
				 
				    <div class="portlet">
				        <div class="portlet-header">Links</div>
				        <div class="portlet-content">Lorem ipsum dolor sit amet, consectetuer adipiscing elit</div>
				    </div>
				 
				    <div class="portlet">
				        <div class="portlet-header">Images</div>
				        <div class="portlet-content">Lorem ipsum dolor sit amet, consectetuer adipiscing elit</div>
				    </div>
				 
				</div>		
				
				
				<div class="column">
				 
				    <div class="portlet">
				        <div class="portlet-header">Links</div>
				        <div class="portlet-content"><a href="#">���� �հ��� 2���� ���� ��� ���� ���� û�ʹ� ������ ��������. Ư���� �������� 6��� ��� ������ �� �� ������, ����, û�ʹ� ��ȣó ���� ���� ���� ������ ������ ���� �ִ�.</a></div>
				    </div>
				 
				    <div class="portlet">
				        <div class="portlet-header">Images</div>
				        <div class="portlet-content">Lorem ipsum dolor sit amet, consectetuer adipiscing elit</div>
				    </div>
				 
				</div>						
				
			</div>
		<div id="footer">Ǫ�Ϳ���</div>
	</div>

</body>


</html>