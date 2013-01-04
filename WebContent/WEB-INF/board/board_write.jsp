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
	        	<h3><img src="images/notice/title_inquiry.gif" alt="�����ϱ�" /></h3>
	          	  <!--�Խ���-->
	          	  	<form id="frm" action="./BoardWriteAction.bo" method="post"  >
	          	  		<input type="hidden" name="token"  id="token"  value="${token}" />
		            	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		                    <thead>
		                        <tr height="34px;">
		                            <th colspan="2">���� �ϱ�</th>
		                      </tr>
		                    </thead>
		                    <tbody>
		                    	  <tr>
		                              <td width="100" ><strong>�ۼ���</strong></td>
		                              <td class="tite"><input  disabled="disabled" value="${sessionScope.id}" type="text" class="none"/></td>
		                          </tr>
		                          <tr>
		                              <td><strong>��й�ȣ</strong></td>
		                              <td class="tite"><input id="board_pwd" name="board_pwd" type="password" class="none"  title="���� �� ������ Ȯ���� ��й�ȣ�� �Է��ϼ���" /></td>
		                          </tr>
		                          <tr>
		                              <td><strong>����</strong></td>
		                              <td class="tite"><input id="title" name="title" type="text" class="none" style="width:385px" title="������ �Է��ϼ���" /></td>
		                    	  </tr>
		                          <tr style="display: none;">
		                              <td><strong>÷������</strong></td>
		                              <td class="tite">
		                              <!-- <a href=""><img src="images/notice/file_btn.gif" alt="�����߰�"  style="margin:5px 0 0 10px;" /></a><br /> -->
		                             <!-- <input type="text"  class="none" style="width:293px; vertical-align:middle;"> -->
		                              <input  title="÷���� ������ �����ϼ���"  src="images/notice/btn_found.gif"   type="file" id="attach_file" name="attach_file" style="padding-left:5px; border:0; vertical-align:middle; margin-bottom:1px;" alt="����÷��" ></td>  
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
	         		<a href="#"  id="reg_btn" onclick="return false"><img src="images/notice/register_btn.gif" alt="���"/></a>
	         		<a href="javascript:history.go(-1);"  ><img src="images/notice/cancel_btn.gif"  alt="���"/></a>
	         		<a href="./BoardListAction.bo"  ><img src="images/notice/list_btn.gif" alt="���"/></a>
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
	
	// ��ϰ��� �� confirm ó��
	$("#reg_btn").click(function(){
		

		// �������� ������ textarea �����Ѵ�.
		oEditors[0].exec("UPDATE_CONTENTS_FIELD", []); 
		
		// ��й�ȣ �Է� Ȯ��
		if(!$("#board_pwd").val().length){
			alert("��й�ȣ�� �Է��ϼ���");
			$("#board_pwd").focus();
			return false;
		}
		
		// ���� �Է� Ȯ��
		if(!$("#title").val().length){
			alert("������ �Է��ϼ���");
			$("#title").focus();
			return false;
		}
		
		// ���� �Է� Ȯ��
		if(!$("#content").val().length){
			alert("������ �Է��ϼ���");
			$("#content").focus();
			return false;
		}
		
		$("#frm").submit();
	});

});	
//-->
</script>
</html>