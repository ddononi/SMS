<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.util.*" %>	
<%@ page import="kr.go.police.account.*" %>	
<%@ page import="java.util.*" %>	    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
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
	                    <thead>
	                        <tr height="34px;">
	                            <th colspan="2">�۾���</th>
	                      </tr>
	                    </thead>
	                    <tbody>
	                    	  <tr>
	                              <td width="100" ><strong>�ۼ���</strong></td>
	                              <td class="tite"><input type="text" class="none"/></td>
	                          </tr>
	                          <tr>
	                              <td><strong>��й�ȣ</strong></td>
	                              <td class="tite"><input type="password" class="none"/></td>
	                          </tr>
	                          <tr>
	                              <td><strong>����</strong></td>
	                              <td class="tite"><input type="text" class="none" style="width:385px"/></td>
	                    	  </tr>
	                          <tr>
	                              <td><strong>÷������</strong></td>
	                              <td class="tite"><a href=""><img src="images/notice/file_btn.gif" alt="�����߰�"  style="margin:5px 0 0 10px;" /></a><br />
	                              <input type="text"  class="none" style="width:293px; vertical-align:middle;"><input type="image" src="images/notice/btn_found.gif" style="padding-left:5px; border:0; vertical-align:middle; margin-bottom:1px;" ></td>  
	                          </tr>
	                   		  <tr  class="end">
	                      		  <td><strong>����</strong></td>
	                              <td colspan="2"><textarea></textarea></td>
	                    	  </tr>
	                  </tbody>                
	          	</table>
	         	<div class="btn">
	         		<a href="#"><img src="images/notice/register_btn.gif" alt="���"/></a>
	         		<a href="#"><img src="images/notice/cancel_btn.gif"  alt="���"/></a>
	         		<a href="#"><img src="images/notice/list_btn.gif" alt="���"/></a>
	         	</div>
	   		</div>
		</div>
	</div>
	<jsp:include page="../include/footer.jspf" />	
</body>
<script type="text/javascript">
<!--
$(function(){
	$("#top_menu4").attr("data-on", "on");
	$("#top_menu4 > img").attr("src", "./images/top/menu04_on.gif");
	$("#board_view_top_menu > img").attr("src", "./images/top/menu_sub07_on.gif");
	$("#board_view_top_menu").attr("data-on", "on");
	$("#top_menu4").trigger("mouseover");
});	

$(function(){
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
});	
//-->
</script>
</html>