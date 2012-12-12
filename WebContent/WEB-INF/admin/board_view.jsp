<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.util.*" %>	
<%@ page import="kr.go.police.board.*" %>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%-- ���  --%>
<jsp:include page="../modules/header.jsp" />
<body>
	<div id="wrapper">
		<%-- ��ܸ޴�  --%>
		<jsp:include page="../modules/admin_topmenu.jsp" />
		<div id="contents">
			<%-- ���̵� �޴�  --%>
			<jsp:include page="../modules/admin_sidebox.jsp" />
     	   <div class="boderWrap">
        	<h3><img src="images/notice/title_inquiryview.gif" alt="���Ǻ���" /></h3>
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
	                        <td colspan="2">
	                        		<form id="reply_frm" action="./AdminBoardReplyWriteAction.bo?parentIndex=${data.index}" method="post">
										<input type="hidden" name="token"  id="token"  value="${token}" />	                        		
	                        			<textarea id="reply_content" name="reply_content" style="width:580px; height:50px; margin:0 0; padding: 0 0;"></textarea>
	                        		</form>
	                        </td>
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
	          		<a href="./AdminBoardListAction.bo"><img src="images/notice/list_btn.gif" alt="���"/></a>
						<a href="#"  id="delete_btn" onClick="return false;"><img src="images/notice/delet_btn.gif" alt="����"/></a>
						<form id="del_form" action="./AdminBoardDeleteAction.bo" method="post">
							<input type="hidden" name="token"  id="token"  value="${token}" />
							<input type="hidden" value="${data.index}" id="del_index" name="del_index"  />
						</form> 	          			       				
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
			
			$("#reply_frm").submit();
		}
	});
	
	// �����ϰ�� ���� �̺�Ʈ ó��
	$("#delete_btn").click(function(){
		if(confirm("���Ǹ� �����Ͻðڽ��ϱ�?")){
			$("#del_form").submit();
		}
	});	
});	
//-->
</script>
</html>