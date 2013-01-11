<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.util.*" %>	
<%@ page import="kr.go.police.sms.*" %>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	int no = (Integer)request.getAttribute("no");
%>	
<%-- ���  --%>
<jsp:include page="../modules/header.jsp" />
<style>
.message{
	display: inline-block; width: 100px; text-overflow:ellipsis; white-space: nowrap; overflow: hidden;
}
#sendResultList td{
	cursor: pointer;
}
select{width: 100px;}
.resultMsg{
	display: inline-block; width: 100px; text-overflow:ellipsis; white-space: nowrap; overflow: hidden;
}
</style>
<body>
	<div id="wrapper">
		<%-- ��ܸ޴�  --%>
		<jsp:include page="../modules/admin_topmenu.jsp" />
		<div id="contents">
			<%-- ���̵� �޴�  --%>
			<jsp:include page="../modules/admin_sidebox.jsp" />
     	   <div class="boderWrap">
				<h3>
					<img src="images/admin/title_send_list.gif" alt="�߼۳���" />
				</h3>
				<%--	�˻� ó�� --%>
				<form style="clear: both; width: 100%; padding:3px; vertical-align: middle;" id="search_frm" action="./AllSendListAction.sm" method="get"  >
					<input value="" name="page" type="hidden" />
					<div style="float: left; display: inline-block;  margin-top:7px; width: 300px; vertical-align: middle;" >
						<select id="mode" name="mode">
							<option value="SMS" ${mode == "SMS"?"selected":""} >SMS</option>
							<option value="LMS" ${mode == "LMS"?"selected":""} >LMS</option>
							<option value="MMS" ${mode == "MMS"?"selected":""} >MMS</option>
						</select>						
						<select id="limit" name="limit" >
							<option ${limit == "10"?"selected":""} value="10">10��</option>
							<option ${limit == "20"?"selected":""} value="20">20��</option>
							<option ${limit == "30"?"selected":""} value="30">30��</option>
							<option ${limit == "40"?"selected":""} value="40">40��</option>
							<option ${limit == "50"?"selected":""} value="50">50��</option>
						</select>	
					</div>	
					<div style="float: right; display: inline-block;">
					<select id=type name="type">
						<option value="from"  ${type == "from"?"selected":""} >������</option>
						<option value="message" ${type == "message"?"selected":""} >�޼���</option>
						<option value="to" ${type == "to"?"selected":""} >�޴¹�ȣ</option>
					</select>							
						<input title="�˻�� �Է��ϼ���" style="margin-bottom: 3px;" value="${search}"  class="search phone" type="text" name="search" id="search" size="20" />
						<a  href="#"  onclick="return false;" id="search_btn"><img style="margin-bottom:5px;margin-right:5px; right;vertical-align: middle;"  src="./images/base/category_btn.gif" /></a>
					</div>
				</form>
			
				<table id="sendResultList"  style="clear: both;" width="100%" border="0" cellpadding="0" cellspacing="0">
				<!-- 
					<colgroup>
						<col width="10%" />
						<col width="" />
						<col width="18%" />
						<col width="15%" />
						<col width="8%" />
					</colgroup>
				-->	
					<thead>
						<tr height="34px;">
							<th><input id="select_all"  type="checkbox"   /></th>							
							<th>No.</th>
							<th>������</th>
							<th>�޴¹�ȣ</th>							
							<th>�޼���</th>
							<th>���۰��</th>
							<th>����Ÿ��</th>
							<th>�߼۽ð�</th>																							
						</tr>
					</thead>
					<tbody>
					
					<!--  �߼۳����� ���°�� -->
					<c:if test="${empty sendList}">
						<tr>
							<td colspan="8"> �߼۳����� �����ϴ�.</td>
						</tr> 
					</c:if>
										
					<!--  �߼۳��� ����Ʈ  -->
					<c:forEach var="data"  items="${sendList}" >
						<tr>
							<td>					
					   		   <input  type="checkbox" name="del" value="${data.index}"   />
					       </td>							
							<td>					
					   		   <%=no--%>
					       </td>
					       <td>					
					   		   <a href="./UserSendListAction.sm?index=${data.userIndex}&userid=${data.userId}">${data.userId}</a>
					       </td>
							<td class="phone">					
					   		   ${data.phone}
					       </td>						       
							<td>					
					   		   <a 	title="${data.msg}"  class="message" href="#" onclick="return false;" >${data.msg}</a>
					       </td>					
							<td>					
					   			<a href="#" onclick="return false" class="resultMsg" title="${data.resultCodeMessage}" >${data.resultCodeMessage}</a>
					       </td>	
							<td>					
					   		    ${mode}
					       </td>						       
							<td>					
					   		   ${data.realsenddate}
					       </td>	
					     </tr> 
					</c:forEach>
					</tbody>
				</table>
				<%--	���� �� --%>
				<form id="del_frm" action="./ListDeleteAction.sm" method="post" style="float: right;  margin-top: 5px;">
					<input type="hidden" name="token"  id="token"  value="${token}" />
					<input value="" id="indexs" name="indexs" type="hidden" />
					<input value="admin"  name="from" type="hidden" />
					<input value="${mode}"  name="mode" type="hidden" />
					<a href="#" onclick="return false;" id="del_btn">����</a>
				</form>
				<div style="clear: both;"></div>				
				<c:if test="${(empty sendList) == false}">
					${pagiNation}
				</c:if>	
				<!-- 
				<div class="page">
					<a href="#"><img src="images/notice/page_prev_btn.gif" /></a><a
						href="#"><span>1</span></a><a href="#">2</a><a href="#"><img
						src="images/notice/page_next_btn.gif" /></a>
				</div>
				-->
			</div>
		</div>
	</div>
	<jsp:include page="../modules/footer.jspf" />	
	<!-- modal content -->
	<div id="message_dlg" style="text-align: center;">
		<ul class="mymessage" style="heighe:107px; display:block; text-align: center; background: url('./images/sms/sms_back.gif') no-repeat center top;">						
			<li>									
				<textarea  id="msg" style="width:134px; height: 108px;  
				padding:2px; margin-top:21.5px; " readonly="readonly" ></textarea>								
			</li>								
		</ul>
	</div>		
</body>
<link rel="stylesheet" type="text/css"  href="./css/simplemodal-basic.css" />        
<script type="text/javascript" src="./js/plug-in/jquery.simplemodal.js"></script>
<script type="text/javascript">
<!--
$(function(){
	
    // odd td colume stand out
    $("#sendResultList tbody tr").each(function(i){
       if(i % 2 == 0){
        $(this).children('td').css('background', '#efe');
       } 
    });
 
    // ���̺� ���� �� ���� ȿ��
    $("#sendResultList td").hover(
      function () {
        $(this).siblings().andSelf().addClass("hover");
      },
      function () {
        $(this).siblings().andSelf().removeClass("hover");
      }
    );   
    
    $(".resultMsg").tooltip();
    // �޽����� ���� �ֵ��� ����ó��
    $(".message").click(function(){
    	var msg = $.trim($(this).text());
    	$("#msg").val(msg);
    	$('#message_dlg').modal();
    });
    
	//	�Է�â ���� ���� ��ư �Է½� ������
    $("#search").tooltip().keydown(function(event){
	       if(event.keyCode == 13){
	    	   $("#search_frm").submit();
	       }
    });	     
	// �˻� ��ư    
    $("#search_btn").click(function(){
    	$("#search_frm").submit();
    });  
	
	// ��ȭ��ȣ ������ �ֱ�
	$(".phone").addHyphen();
	
	// ����ó��
    $("#del_btn").button().click(function(){
    	var delArr = [];
    	$("input:checked").not("#select_all").each(function(){
    		delArr.push($(this).val());
    	});
    	
    	if(delArr.length <=0){
    		alert("���õ� �׸��� �����ϴ�.");
    		return;
    	}    	
    	
    	$("#del_frm #indexs").val(delArr);
    	if(confirm("������ ������ �����Ͻðڽ��ϱ�?")){
    		$("#del_frm").submit();
    	}

    	    	
    });
    
    $("#select_all").change(function(){
    	// �̿�Ұ� üũ�Ǿ� �ִٸ� �ٸ� üũ�ڽ��鵵 ���� üũ�� Ȱ��ȭ��Ų��.
    	if(this.checked){
    		$("input:checkbox").attr("checked", "checked");
    	}else{
    		$("input:checkbox").removeAttr("checked");
    	}
    });
    
    $("#limit, #mode").change(function(){
    	$("#search_frm").submit();
    });

	$("#top_menu2").attr("data-on", "on");
	$("#top_menu2 > img").attr("src", "./images/admin/menu02_admin_on.gif");
	$("#all_send_result_list_sub_menu > img").attr("src", "./images/admin/menu_admin_sub08_on.gif");
	$("#all_send_result_list_sub_menu").attr("data-on", "on");
    $(".gnb_sub2").show();	
});	
//-->
</script>
</html>