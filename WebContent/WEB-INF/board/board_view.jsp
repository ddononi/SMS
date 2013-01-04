<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.util.*" %>	
<%@ page import="kr.go.police.board.*" %>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%-- 헤더  --%>
<jsp:include page="../modules/header.jsp" />
<style type="text/css">
#wrapper #contents .boderWrap table tbody ul li{list-style-type:disc;}
#wrapper #contents .boderWrap table tbody ol li{list-style-type:decimal;}
#wrapper #contents .boderWrap table #con{line-height:1}
</style>
<body>
	<div id="wrapper">
		<%-- 상단메뉴  --%>
		<jsp:include page="../modules/topmenu.jsp" />
		<div id="contents">
			<%-- 사이드 메뉴  --%>
			<jsp:include page="../modules/sidebox.jsp" />
     	   <div class="boderWrap">
        	<h3><img src="images/notice/title_inquiryview.gif" alt="문의보기" /></h3>
	            <!--게시판-->
	            	<table width="100%" border="0" cellpadding="0" cellspacing="0">
	                   <colgroup>
	                        <col width="20%"/>
	                        <col width="50%" />
	                        <col width="10%"/>
	                        <col width="20%"/>	                        
	                   </colgroup>
	                    <thead>
	                        <tr height="34px;">
	                            <th colspan="4">${data.title}</th>
	                      </tr>
	                    </thead>
	                    <tbody>
	                    	  <tr>
	                              <td colspan="2"  style="text-align: left; padding-left: 10px;"><strong>작성자</strong> | ${data.registerName}</td>
	                              <td colspan="2" style="text-align: right; padding-right: 10px;">${data.regDate} <strong>조회</strong> : ${data.viewCount}</td>	                              
	                          </tr>
	                          <tr>
	                               <td colspan="4" class="view" height="300" id="con">
	                               	 ${data.content}
	                               </td>
	                          </tr>
	                      <tr class="re">
	                               <td><%=session.getAttribute("id")%> |</td>
	                        <td colspan="2">
	                        		<form id="reply_frm" action="./BoardReplyWriteAction.bo?parentIndex=${data.index}" method="post">
										<input type="hidden" name="token"  id="token"  value="${token}" />	                        		
	                        			<textarea id="reply_content" name="reply_content" style="width:580px; height:50px; margin:0 0; padding: 0 0;"></textarea>
	                        		</form>
	                        </td>
	                        <td><a href="#" id="reply_btn"><img src="images/notice/btn_re.gif" alt="댓글등록" /></a></td>
	                     </tr>
							<!-- 댓글목록 -->
							<c:forEach var="data"  items="${replyList}" >
		                          <tr>
		                                <td><img src="images/notice/icon_re.gif" /><strong> ${data.registerName}</strong></td>
		                                <td colspan="2"  class="tite" >${data.content}</td>
		                                <td  style="text-align: right;" >| ${data.regDate}</td>
		                          </tr>
							</c:forEach>		                          
	                  </tbody>                
	          </table>
	          <div class="btn">
	          		<a href="./BoardListAction.bo"><img src="images/notice/list_btn.gif" alt="목록"/></a>
	          		<% 
	          			// 내 글일경우 수정 및 삭제처리를 할수 있도록 한다.
						BoardBean data = (BoardBean)request.getAttribute("data");	          			
	          			if(data.getRegUserIndex() == Integer.valueOf(session.getAttribute("index").toString()) ){ %>
	          			<a href="./BoardModifyViewAction.bo?index=${data.index}"><img src="images/notice/modify_btn.gif" alt="수정"/></a>
						<a href="#"  id="delete_btn" onClick="return false;"><img src="images/notice/delet_btn.gif" alt="삭제"/></a>
						<form id="del_form" action="./BoardDeleteAction.bo" method="post">
							<input type="hidden" name="token"  id="token"  value="${token}" />
							<input type="hidden" value="${data.index}" id="index" name="index"  />
						</form> 	          			       				
	          		<% } %>
	         </div>
			</div>
		</div>
	</div>
	<jsp:include page="../modules/footer.jspf" />	
</body>
<script type="text/javascript">
<!--
$(function(){
	$("#top_menu4").attr("data-on", "on");
	$("#top_menu4 > img").attr("src", "./images/top/menu04_on.gif");
	$("#board_view_top_menu > img").attr("src", "./images/top/menu_sub07_on.gif");
	$("#board_view_top_menu").attr("data-on", "on");
	$("#top_menu4").trigger("mouseover");
	
	// 댓글 입력 검증 처리 및 전송
	$("#reply_btn").click(function(){
		if(confirm("댓글을 등록하시겠습니까?")){
			var replyContent = $("#reply_content").val();
			if(replyContent.length <= 0){
				alert("댓글 내용이 없습니다.");
				return;
			}
			
			$("#reply_frm").submit();
		}
	});
	
	// 내글일경우 삭제 이벤트 처리
	$("#delete_btn").click(function(){
		if(confirm("문의를 삭제하시겠습니까?")){
			$("#del_form").submit();
		}
	});	
	
});	
//-->
</script>
</html>