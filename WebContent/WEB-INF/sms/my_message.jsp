<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.util.*" %>		
<%@ page import="kr.go.police.sms.*" %>		
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- ���  --%>
<jsp:include page="../modules/header.jsp" />
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
    
    $("#group_select").change(function () {
    	var groupIndex = $(this).children("option:selected").val();
    	window.location.href= "./MyMessageAction.sm?groupIndex=" +  groupIndex;
    });
    
    // ������ �巹�� ó��
    // �θ� �ڽ��ȿ����� �̵����� ó��
    $(".box").draggable({ containment: "parent", scroll: false });
	$("#add_btn").button();    
	// �޴� ó��
	$("#top_menu2").attr("data-on", "on");
	$("#top_menu2 > img").attr("src", "./images/top/menu02_on.gif");
	$("#sms_manage_menu > img").attr("src", "./images/top/menu_sub09_on.gif");
	$("#sms_manage_menu").attr("data-on", "on");
	$("#top_menu2").trigger("mouseover");
	$(".gnb_sub2").show();
	
});
</script>
<body>
	<div id="wrapper">
		<%-- ��ܸ޴�  --%>
		<jsp:include page="../modules/topmenu.jsp" />

		<div id="contents">
			<%-- ���̵� �޴�  --%>
			<jsp:include page="../modules/sidebox.jsp" />


        <div id="contentsWrap_m">
			<h3>
				<img src="./images/lettersend/title_manage.gif" alt="��������" />
			</h3>
            <p class="choice_gruop" style="vertical-align: middle;">
					<select id="group_select">
						<c:forEach var="group"  items="${groups}" >
							<option ${groupIndex ==group.index?'selected':''} value="${group.index}">${group.group}</option>
						</c:forEach>							
					</select>
            		<a href="./AddMyMessageView.sm" id="add_btn">�������߰�</a>					
            </p>
            <p class="top_bg"></p>
            <div class="letterbox_m">
				<!--  �ּҷ��� ���°�� -->
				<c:if test="${empty requestScope.messages}">
						<h3 style="text-align: center;">�������� �����ϴ�.</h3>
				</c:if>
				<c:forEach var="msg"  items="${messages}" >
	        		<ul class="box">
	        			<li>
						    <div class="portlet"><a href="./MyMessageView.sm?index=${msg.index}" title="�޽��� ����" >
						        <div class="portlet-header">${msg.title}</div>
						        <div class="portlet-content">${msg.message}</div>
						        </a>
						    </div>	
						   </li> 
	                </ul>
				</c:forEach>	
            </div>   
            <p class="bottom_bg"></p>     
        </div> 
	</div>
</div>
<jsp:include page="../modules/footer.jspf" />	
</body>

</html>