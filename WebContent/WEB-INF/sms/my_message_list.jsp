<%@ page language="java" contentType="text/html; charset=EUC-KR"	pageEncoding="EUC-KR"%>
<%@ page import="java.util.*" %>	
<%@ page import="kr.go.police.*"  %>			
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title><%=getServletContext().getInitParameter("title")%></title>
<link rel="shortcut icon" href="./images/base/police.ico" type="image/ico" />    
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<link rel="stylesheet" type="text/css" href="./css/style.css"/>
<link rel="stylesheet" href="./css/cupertino/jquery-ui-1.9.2.custom.css" />
<link rel="stylesheet" type="text/css" href="./css/sms.css" />  	
<script type="text/javascript" src="./js/jquery-1.8.3.min.js"></script>
<script src="./js/jquery-ui-1.9.1.custom.min.js"></script>        
<style type="text/css">
*{min-width:0px;}
#myMessageBox, #specailCharBox{cursor: pointer;}
.tab_box {top:-80px; height: 295px;}
.tab_box .tab01 .my01{height: 220px;}
.tab_box .tab01 .my01 .mymessage{width:136px; display:block; float:left; padding:2px; height: 90px;  }
.tab_box .tab01 .my01 .mymessage textarea {width:120px; height: 70px;}
.tab_box .tab01 .my01 .mymessage ul{text-align: center;}
body{background:none;}
.mymessage * { cursor: pointer;}
h3{text-align: center; margin-top: 60px;}
select{width: 130px;}
</style>
</head>
	<body>						
				<div style="display: block; float: right;">				
				<form  id="search_frm" action="./MyMessageListAction.sm" method="post"  >
					<select id="groupIndex" name="groupIndex" >									
						<c:forEach var="gl"  items="${groupList}">
							<option ${groupIndex == gl.index?"selected":""} value="${gl.index }">${gl.group}</option>
							</c:forEach>
							</form>								
								</select>
						</div>
							<div style="display: block; clear: both; padding:5px 0px 5px 0px;">
								<c:if test="${empty list}">
									<ul>
										<li colspan="7"><h3>등록된 문자가 없습니다.</h3></li>
									</ul> 
								</c:if>
								<c:forEach var="mdate" items="${list}">
									<ul class="mymessage" style="heighe:107px; display:block; float:left; margin:5px 10px 0px 10px; text-align: center; background: url('./images/sms/sms_back.gif') no-repeat center top;">						
										<li style="width:150px; height: 145px;">									
											<textarea  style="width:135px; height: 108px; padding:2px; margin-top:21.5px; cursor:pointer;" name="${mdate.index }" >${mdate.message}</textarea>								
										</li>								
										<li>${mdate.title}</li>							
									</ul>									
								</c:forEach>		
							</div>							
							 <br />
							 <div style="margin-bottom: 0px; display: block; width:600px; float:left; padding:0px 0px 5px 0px;"" class="boderWrap">
								<c:if test="${(empty list) == false}">
										${pagiNation}
								</c:if>	
							</div>
			</body>
</html>
<script type="text/javascript">
$(function(){
	
	// 내문자 그룹 변경
	 $("#groupIndex").change(function(){
	    	$("#search_frm").submit();
	 });
	 
	 // 내문자함 선택시 문자창에 내용을 복사한후 커서를 문자내용마지막으로 이동처리한다.
	 $(".mymessage > li").live('click', function(e){
		 var $message = window.parent.$("#message");
		 var msg = $(this).parent("ul").children("li").find("textarea").val();
		 $message.val(msg).focus();
		 var len = $message.val().length;
		 setSelectionRange($message[0], len, len);
		 //$message[0].setSelectionRange(len, len);			
	});


	// 커서 위치 설정 메소드
	function setSelectionRange(input, selectionStart, selectionEnd){
		if (input.setSelectionRange) {
			input.focus();
			input.setSelectionRange(selectionStart, selectionEnd);
		} else if (input.createTextRange) {
			var range = input.createTextRange();
			range.collapse(true);
			range.moveEnd('character', selectionEnd);
			range.moveStart('character', selectionStart);
			range.select();
		}
	}
	
});	 

</script>