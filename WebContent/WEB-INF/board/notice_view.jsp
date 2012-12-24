<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.util.*" %>	
<%@ page import="kr.go.police.board.*" %>	
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
        	<h3><img src="images/notice/title_notice.gif" alt="공지사항" /></h3>
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
	                            <th colspan="4">${data.title} </th>
	                      </tr>
	                    </thead>
	                    <tbody>
	                    	  <tr>
	                              <td colspan="2"  style="text-align: left; padding-left: 10px;"><strong>작성자</strong> | ${data.registerName}
										<c:if test="${!empty data.filename}">
											<img  style="vertical-align: middle;margin : 0px 0px 0px 10px;" src="./images/notice/icon_disk.gif" alt="첨부파일" />
											<a style="color :#005EB3" href="./FileDownAction.bo?file=${data.filename}">${data.filename}</a>
										</c:if>		                              
	                              </td>
	                              <td colspan="2" style="text-align: right; padding-right: 10px;">${data.regDate} <strong>조회</strong> : ${data.viewCount}</td>	                    	  
	                          </tr>
	                          <tr>
	                               <td colspan="4" class="view" height="300">
	                               		<pre  style="width:600px;display: block;word-wrap: break-word; " >${data.content}</pre>
	                               </td>
	                          </tr>
	                      <tr class="re">
	                               <td><%=session.getAttribute("id")%> |</td>
	                        <td colspan="2">
	                        		<form id="reply_frm" action="./BoardReplyWriteAction.bo?parentIndex=${data.index}" method="post">
	                        			<input type="hidden" name="token"  id="token"  value="${token}" />
	                        			<textarea id="reply_content" name="reply_content" style="width:580px; height:50px; margin:0 0; padding: 0 0;"></textarea>
	                        		</form></td>
	                               <td><a href="#" id="reply_btn"><img src="images/notice/btn_re.gif" alt="댓글등록" /></a></td>
	                          </tr>
							<!-- 댓글목록 -->
							<c:forEach var="data"  items="${replyList}" >
		                          <tr>
		                                <td><img src="images/notice/icon_re.gif" /><strong> ${data.registerName}</strong></td>
		                                <td class="tite" >${data.content}</td>
		                                <td colspan="2" style="text-align: right;" >| ${data.regDate}</td>
		                          </tr>
							</c:forEach>		                          
	                  </tbody>                
	          </table>
	          <div class="btn">
	          		<a href="./NoticeListAction.bo"><img src="images/notice/list_btn.gif" alt="목록"/></a>
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
	$("#notice_view_menu > img").attr("src", "./images/top/menu_admin_sub03_on.gif");
	$("#notice_view_menu").attr("data-on", "on");
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

});	
//-->
</script>
</html>