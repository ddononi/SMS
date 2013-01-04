<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.util.*" %>	
<%@ page import="kr.go.police.board.*" %>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%-- ���  --%>
<jsp:include page="../modules/header.jsp" />
<style type="text/css">
#wrapper #contents .boderWrap table tbody ul li{list-style-type:disc;}
#wrapper #contents .boderWrap table tbody ol li{list-style-type:decimal;}
#wrapper #contents .boderWrap table #con{line-height:1}
</style>
<body>
	<div id="wrapper">
		<%-- ��ܸ޴�  --%>
		<jsp:include page="../modules/topmenu.jsp" />
		<div id="contents">
			<%-- ���̵� �޴�  --%>
			<jsp:include page="../modules/sidebox.jsp" />
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
	                              <td colspan="2"  style="text-align: left; padding-left: 10px;"><strong>�ۼ���</strong> | ${data.registerName}</td>
	                              <td colspan="2" style="text-align: right; padding-right: 10px;">${data.regDate} <strong>��ȸ</strong> : ${data.viewCount}</td>	                              
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
	                        <td><a href="#" id="reply_btn"><img src="images/notice/btn_re.gif" alt="��۵��" /></a></td>
	                     </tr>
							<!-- ��۸�� -->
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
	          		<a href="./BoardListAction.bo"><img src="images/notice/list_btn.gif" alt="���"/></a>
	          		<% 
	          			// �� ���ϰ�� ���� �� ����ó���� �Ҽ� �ֵ��� �Ѵ�.
						BoardBean data = (BoardBean)request.getAttribute("data");	          			
	          			if(data.getRegUserIndex() == Integer.valueOf(session.getAttribute("index").toString()) ){ %>
	          			<a href="./BoardModifyViewAction.bo?index=${data.index}"><img src="images/notice/modify_btn.gif" alt="����"/></a>
						<a href="#"  id="delete_btn" onClick="return false;"><img src="images/notice/delet_btn.gif" alt="����"/></a>
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