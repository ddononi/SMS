<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.util.*" %>	
<%@ page import="kr.go.police.account.*" %>	
<%@ page import="java.util.*" %>	    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%-- 헤더  --%>
<jsp:include page="../modules/header.jsp" />
<body>
	<div id="wrapper">
		<%-- 상단메뉴  --%>
		<jsp:include page="../modules/topmenu.jsp" />
		<div id="contents">
			<%-- 사이드 메뉴  --%>
			<jsp:include page="../modules/sidebox.jsp" />
	        <div class="boderWrap">
	        	<h3><img src="images/notice/title_inquiry.gif" alt="문의하기" /></h3>
	          	  <!--게시판-->
	          	  	<form id="frm" action="./BoardWriteAction.bo" method="post"  >
	          	  		<input type="hidden" name="token"  id="token"  value="${token}" />
		            	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		                    <thead>
		                        <tr height="34px;">
		                            <th colspan="2">문의 하기</th>
		                      </tr>
		                    </thead>
		                    <tbody>
		                    	  <tr>
		                              <td width="100" ><strong>작성자</strong></td>
		                              <td class="tite"><input  disabled="disabled" value="${sessionScope.id}" type="text" class="none"/></td>
		                          </tr>
		                          <tr>
		                              <td><strong>비밀번호</strong></td>
		                              <td class="tite"><input id="board_pwd" name="board_pwd" type="password" class="none"  title="수정 및 삭제시 확인할 비밀번호를 입력하세요" /></td>
		                          </tr>
		                          <tr>
		                              <td><strong>제목</strong></td>
		                              <td class="tite"><input id="title" name="title" type="text" class="none" style="width:385px" title="제목을 입력하세요" /></td>
		                    	  </tr>
		                          <tr style="display: none;">
		                              <td><strong>첨부파일</strong></td>
		                              <td class="tite">
		                              <!-- <a href=""><img src="images/notice/file_btn.gif" alt="파일추가"  style="margin:5px 0 0 10px;" /></a><br /> -->
		                             <!-- <input type="text"  class="none" style="width:293px; vertical-align:middle;"> -->
		                              <input  title="첨부할 파일을 선택하세요"  src="images/notice/btn_found.gif"   type="file" id="attach_file" name="attach_file" style="padding-left:5px; border:0; vertical-align:middle; margin-bottom:1px;" alt="파일첨부" ></td>  
		                          </tr>
		                   		  <tr>
		                   		  		<td colspan="2" >
		                    	      	  	<textarea name="content" id="content" rows="10" cols="100" style="width:95%; height:400px; display:none;">${data.content}</textarea>		                    	  	
		                				</td>
		                			</tr>
			                  </tbody>                
			          	</table>
		          	</form>
	         	<div class="btn">
	         		<a href="#"  id="reg_btn" onclick="return false"><img src="images/notice/register_btn.gif" alt="등록"/></a>
	         		<a href="javascript:history.go(-1);"  ><img src="images/notice/cancel_btn.gif"  alt="취소"/></a>
	         		<a href="./BoardListAction.bo"  ><img src="images/notice/list_btn.gif" alt="목록"/></a>
	         	</div>
	   		</div>
		</div>
	</div>
	<jsp:include page="../modules/footer.jspf" />	
</body>
<script type="text/javascript" src="./se/js/HuskyEZCreator.js" charset="utf-8"></script>
<script type="text/javascript">
<!--
$(function(){
	$("#top_menu4").attr("data-on", "on");
	$("#top_menu4 > img").attr("src", "./images/top/menu04_on.gif");
	$("#board_write_menu > img").attr("src", "./images/top/menu_sub08_on.gif");
	$("#board_write_menu").attr("data-on", "on");
	$("#top_menu4").trigger("mouseover");

	var oEditors = [];
	nhn.husky.EZCreator.createInIFrame({
		oAppRef: oEditors,
		elPlaceHolder: "content",
		sSkinURI: "se/SmartEditor2Skin.html",
		fCreator: "createSEditor2"
	});
	
	// 등록검증 및 confirm 처리
	$("#reg_btn").click(function(){
		

		// 에디터의 내용을 textarea 적용한다.
		oEditors[0].exec("UPDATE_CONTENTS_FIELD", []); 
		
		// 비밀번호 입력 확인
		if(!$("#board_pwd").val().length){
			alert("비밀번호를 입력하세요");
			$("#board_pwd").focus();
			return false;
		}
		
		// 제목 입력 확인
		if(!$("#title").val().length){
			alert("제목을 입력하세요");
			$("#title").focus();
			return false;
		}
		
		// 내용 입력 확인
		if(!$("#content").val().length){
			alert("내용을 입력하세요");
			$("#content").focus();
			return false;
		}
		
		$("#frm").submit();
	});

});	
//-->
</script>
</html>