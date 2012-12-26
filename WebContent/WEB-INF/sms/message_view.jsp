<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.util.*" %>		
<%@ page import="kr.go.police.sms.*" %>		
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
		int groupIndex = 0;
%>	
<%-- 헤더  --%>
<jsp:include page="../modules/header.jsp" />
  <style>
    .column { width: 170px; float: left; padding-bottom: 100px; }
    .portlet { margin: 0 1em 1em 0; cursor: pointer; }
    .portlet-header { margin: 0.3em; padding-bottom: 4px; padding-left: 0.2em; }
    .portlet-header .ui-icon { float: right; }
    .portlet-content { padding: 0.4em; }
    .ui-sortable-placeholder { border: 1px dotted black; visibility: visible !important; height: 50px !important; }
    .ui-sortable-placeholder * { visibility: hidden; }
   .selectedGroup {color : #FF4800 !important; font-weight : bold; } 
	textarea{background-color: #FEF6D0;}   
</style>
<script>

$(function() {
	// 메뉴 처리
	$("#top_menu2").attr("data-on", "on");
	$("#top_menu2 > img").attr("src", "./images/top/menu02_on.gif");
	$("#sms_manage_menu > img").attr("src", "./images/top/menu_sub09_on.gif");
	$("#sms_manage_menu").attr("data-on", "on");
	$("#top_menu2").trigger("mouseover");
	$(".gnb_sub2").show();
    

    // odd td colume stand out
    $("#group_list tbody tr:odd").each(function(i){
        $(this).children('td').css('background', '#efe');
    });
 
    // 테이블 현재 열 강조 효과
    $("#group_list td").hover(
      function () {
        $(this).siblings().andSelf().addClass("hover");
      },
      function () {
        $(this).siblings().andSelf().removeClass("hover");
      }
    ); 
    
    // 문자함 수정
    $("#add_btn").click(function(){
    	var $title = $("#title");
    	var $message = $("#message");    	
    	if(!$title.val().length){
    		$title.focus();
    		alert("제목을 입력하세요");
    		return;
    	}
    	
    	if(!$message.val().length){
    		$message.focus();
    		alert("내용을 입력하세요");
    		return;
    	}    	

    	$("#frm").submit();
    });
    
    // 삭제 처리
    $("#delete_btn").click(function(){
    	if(confirm(" 문자함을 삭제하시겠습니까?")){
    		$("#delete_frm").submit();
    	}
    });
});
</script>
<body>
	<div id="wrapper">
		<%-- 상단메뉴  --%>
		<jsp:include page="../modules/topmenu.jsp" />

		<div id="contents">
			<%-- 사이드 메뉴  --%>
			<jsp:include page="../modules/sidebox.jsp" />
  	      <div id="contentsWrap">
        	<h3><img src="images/lettersend/title_manage_e.gif" alt="문자함등록" /></h3>
            <div class="phone">   
            	<form id="delete_frm" action="./MyMessageDeleteAction.sm" method="post">
                		<input value="${message.index}" id="index" name="index" type="hidden" />
                		<input value="${message.groupIndex}" id="groupIndex" name="groupIndex"  type="hidden" />                		
                </form>
                             
            	<form id="frm" action="./MyMessageModifyAction.sm" method="post">
                	<fieldset>       
                		<input value="${message.index}" id="index" name="index" type="hidden" />
                		<input value="${message.groupIndex}" id="groupIndex" name="groupIndex"  type="hidden" />
                        <p><label>제목</label><input type="text"  value="${message.title}" class="title" id="title" name="title" /></p>
                        <p style=" padding-top:5px;margin-right:30px; text-align:center; background:#f0f0f0; width:236px; height:20px;">
                        <label>내용</label></p><textarea name="message" id="message" cols="" rows="" class="txt">${message.message}</textarea>
                	</fieldset>
                </form>
                <ul class="txt_01">
                    <li><img src="images/lettersend/icon_sms.gif" /></li>
                    <li class="bytes">0/80Bytes</li>
                </ul>
                <ul class="btn">
               	    <li><a href="#" onclick="return false" id="add_btn"><img src="./images/lettersend/btn_en.gif"  border="0" alt="등록"  /></a></li>
               	    <li><a href="#" onclick="return false" id="delete_btn"><img src="./images/lettersend/btn_de.gif"  border="0" alt="삭제" /></a></li>
                    <li><a href="javascript:history.go(-1);"  id="cancel_btn"><img src="./images/lettersend/btn_cn.gif" border="0" alt="취소"  /></a></li>
                </ul>
            </div>
				
			<div id="group_list" class="boderWrap">
	            	<table width="100%" border="0" cellpadding="0" cellspacing="0">
	            		<colgroup>
	            			<col width="20%" />
	            			<col width="70%" />
	            			<col width="10%" />
	            		</colgroup>
	                    <thead>
	                        <tr height="34px;">
	                        	<th>No.</th>
	                            <th>그룹목록</th>
	                            <th></th>
	                      </tr>
	                    </thead>
						<tbody>
							<c:forEach var="group"  items="${groups}" >
								<tr>
	                        		<td><%=++groupIndex%></td>								
									<td ${group.index == data.groupIndex ?"class='selectedGroup' ":"" }>${group.group}</td>
									<td></td>
								</tr>
							</c:forEach>		
						</tbody>	                    
	                 </table>
			</div>
			
			</div>
		</div>
	</div>
	<jsp:include page="../modules/footer.jspf" />		
</body>

</html>