<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.util.*" %>	
<%@ page import="kr.go.police.board.*" %>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%
	BoardBean data = (BoardBean)request.getAttribute("data");
	List<BoardBean> replyList = (List<BoardBean>)request.getAttribute("replyList");
%>  
<%-- ���  --%>
<jsp:include page="../modules/header.jsp" />
<body>
	<div id="wrapper">
		<%-- ��ܸ޴�  --%>
		<jsp:include page="../modules/topmenu.jsp" />
		<div id="contents">
			<%-- ���̵� �޴�  --%>
			<jsp:include page="../modules/sidebox.jsp" />
     	   <div class="boderWrap">
        	<h3><img src="images/notice/title_notice.gif" alt="��������" /></h3>
	            <!--�Խ���-->
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
	                              <td><strong>�ۼ���</strong> |</td>
	                              <td style="text-align: left;">${data.registerName}</td>
	                              <td>${data.regDate}</td>	                              
	                              <td>| ��ȸ : ${data.viewCount}</td>
	                          </tr>
	                          <tr>
	                               <td colspan="4" class="view" height="300">
	                               	 ${data.content}
	                               </td>
	                          </tr>
	                      <tr class="re">
	                               <td><%=session.getAttribute("id")%> |</td>
	                        <td colspan="2"><form id="frm" action="./BoardReplyAction.bo?parentIndex=${data.index}" method="post"><textarea id="reply_content" name="reply_content" style="width:580px; height:50px; margin:0 0; padding: 0 0;"></textarea></form></td>
	                               <td><a href="#" id="reply_btn"><img src="images/notice/btn_re.gif" alt="��۵��" /></a></td>
	                          </tr>
							<!-- ��۸�� -->
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
	          		<a href="./BoardListAction.bo"><img src="images/notice/list_btn.gif" alt="���"/></a>
	          		<% 
	          			// �� ���ϰ�� ���� �� ����ó���� �Ҽ� �ֵ��� �Ѵ�.
	          			if(data.getRegUserIndex() == Integer.valueOf(session.getAttribute("index").toString()) ){ %>
	          			<a href="./BoardModifyViewAction.bo?index=${data.index}"><img src="images/notice/modify_btn.gif" alt="����"/></a>
						<a href="./BoardDeleteAction.bo?index=${data.index}" id="delete_btn" onClick="return false;"><img src="images/notice/delet_btn.gif" alt="����"/></a> 	          			       				
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
	
	// ��� �Է� ���� ó�� �� ����
	$("#reply_btn").click(function(){
		if(confirm("����� ����Ͻðڽ��ϱ�?")){
			var replyContent = $("#reply_content").val();
			if(replyContent.length <= 0){
				alert("��� ������ �����ϴ�.");
				return;
			}
			
			$("form").submit();
		}
	});
	
	// �����ϰ�� ���� �̺�Ʈ ó��
	$("#delete_btn").click(function(){
		if(confirm("���Ǹ� �����Ͻðڽ��ϱ�?")){
			var href = $(this).attr("href");
			window.location.href = href;
		}
	});	
});	
//-->
</script>
</html>