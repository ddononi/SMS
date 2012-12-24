<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.util.*" %>		
<%@ page import="kr.go.police.sms.*" %>		
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- 헤더  --%>
<jsp:include page="../modules/header.jsp" />
  <style>
    .column { width: 170px; float: left; padding-bottom: 100px; }
    .portlet { cursor: pointer; }
    .portlet-header { margin: 0.3em;  padding-left: 0.2em; padding-top: 8px;}
    .portlet-header .ui-icon { float: right; }
    .portlet-content { padding: 0.4em; }
    .ui-sortable-placeholder { border: 1px dotted black; visibility: visible !important; height: 50px !important; }
    .ui-sortable-placeholder * { visibility: hidden; }
</style>
<script>
$(function() {
	// 메세지 박스 처리
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
    
    // 문자함 드레그 처리
    // 부모 박스안에서만 이동가능 처리
    $(".box").draggable({ containment: "parent", scroll: false });
	$("#add_btn").button();    
	// 메뉴 처리
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
		<%-- 상단메뉴  --%>
		<jsp:include page="../modules/topmenu.jsp" />

		<div id="contents">
			<%-- 사이드 메뉴  --%>
			<jsp:include page="../modules/sidebox.jsp" />


        <div id="contentsWrap_m">
			<h3>
				<img src="./images/lettersend/title_manage.gif" alt="내문자함" />
			</h3>
            <p class="choice_gruop" style="vertical-align: middle;">
					<select id="group_select">
						<c:forEach var="group"  items="${groups}" >
							<option ${groupIndex ==group.index?'selected':''} value="${group.index}">${group.group}</option>
						</c:forEach>							
					</select>
            		<a href="./AddMyMessageView.sm" id="add_btn">문자함추가</a>					
            </p>
            <p class="top_bg"></p>
            <div class="letterbox_m">
				<!--  주소록이 없는경우 -->
				<c:if test="${empty requestScope.messages}">
						<h3 style="text-align: center;">문자함이 없습니다.</h3>
				</c:if>
				<c:forEach var="msg"  items="${messages}" >
	        		<ul class="box">
	        			<li>
						    <div class="portlet"><div class="portlet-header">${msg.title}</div>
						    <a href="./MyMessageView.sm?index=${msg.index}" title="메시지 보기" >						        
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