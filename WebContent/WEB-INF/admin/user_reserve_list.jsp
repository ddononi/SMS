<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.util.*" %>	
<%@ page import="kr.go.police.sms.*" %>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	//	����Ʈ ��ȣ
	int no = (Integer)request.getAttribute("no");
%>	
<%-- ���  --%>
<jsp:include page="../modules/header.jsp" />
<style>
.message{
	display: inline-block; width: 200px; text-overflow:ellipsis; white-space: nowrap; overflow: hidden;
}
#sendResultList td{
	cursor: pointer;
}
.user_name{font-size:20px; font-weight:bold; text-align:center;}</style>
<body>
	<div id="wrapper">
		<%-- ��ܸ޴�  --%>
		<jsp:include page="../modules/admin_topmenu.jsp" />
		<div id="contents">
			<%-- ���̵� �޴�  --%>
			<jsp:include page="../modules/admin_sidebox.jsp" />
     	   <div class="boderWrap">
				<div class=user_name style="text-align:left; font-size:20px; color:#000; font-family:'Nanum'; font-weight:bold;">
					<h3>
						<img src="images/admin/title_reservlist.gif" alt="���೻��" />
						<span style="font-size: 18px; font-weight: normal;">(${userid})</span>
					</h3>
				</div>
				<%--	�˻� ó�� --%>
				 <form style="clear: both; width: 100%; padding:3px; vertical-align: middle;" id="search_frm" action="./UserReserveListAction.sm" method="get"  >
					<input value="" name="page" type="hidden" />
					<select id="limit" name="limit" style="float: left; display: inline-block;">
						<option ${limit == "10"?"selected":""} value="10">10��</option>
						<option ${limit == "20"?"selected":""} value="20">20��</option>
						<option ${limit == "30"?"selected":""} value="30">30��</option>
						<option ${limit == "40"?"selected":""} value="40">40��</option>
						<option ${limit == "50"?"selected":""} value="50">50��</option>
					</select> 
				<input type="hidden" name="userIndex" value="${userIndex}">
				<input type="hidden" name="index" value="${index}">
					<div style="float: right; display: inline-block;">
						<select id=type name="type">
							<option value="message" ${type == "message"?"selected":""} >�޼���</option>
							<option value="to" ${type == "to"?"selected":""} >�޴¹�ȣ</option>
						</select>						
						<input title="�˻�� �Է��ϼ���" style="margin-bottom: 3px;" value="${search}"  class="search phone" type="text" name="search" id="search" size="20" />
						<a  href="#"  onclick="return false;" id="search_btn"><img style="margin-bottom:5px;margin-right:5px; right;vertical-align: middle;"  src="./images/base/category_btn.gif" /></a>
					</div>
				</form>	
			
				<table id="sendResultList"  style="clear: both;" width="100%" border="0" cellpadding="0" cellspacing="0">
					<thead>
						<tr height="34px;">
							<th><input id="select_all"  type="checkbox"   /></th>							
							<th>No.</th>
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
					       	<td class="phone">					
					   		   ${data.toPhone}
					       </td>						       
							<td>					
					   		   <a 	title="${data.message}"  class="message" href="#" onclick="return false;" >${data.message}</a>
					       </td>					
							<td>					
					   		 ${data.requestResult}
					       </td>	
							<td>					
					   		   ${data.flag}
					       </td>							       
							<td>					
					   		   ${data.regDate}
					       </td>	
					     </tr> 
					</c:forEach>
					</tbody>
				</table>
				<%--	���� �� --%>
				<form id="del_frm" action="./ListDeleteAction.sm" method="post" style="float: right;  margin-top: 5px;">
					<input type="hidden" name="token"  id="token"  value="${token}" />
					<input value="" id="indexs" name="indexs" type="hidden" />
					<input value="./AllReserveListAction.sm" name="page" type="hidden" />
					<a href="#" onclick="return false;" id="del_btn">����</a>
				</form>
				<div style="clear: both;"></div>				
				<c:if test="${(empty sendList) == false}">
					${pagiNation}					
				</c:if>	
			</div>
		</div>
	</div>
	<jsp:include page="../modules/footer.jspf" />	
</body>
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
    
    // �޽����� ���� �ֵ��� ����ó��
    $(".message").tooltip();
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
	
    $("#limit").change(function(){
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
    	
    	$("#del_frm input:first").val(delArr);
    	if(confirm("������ ������ �����Ͻðڽ��ϱ�?")){
    		$("#del_frm").submit();
    	}    	    	
    });
  //��ư�ٹ̱�
	$("#back_list").button().click(function(){		
	});
    
    $("#select_all").change(function(){
    	// �̿�Ұ� üũ�Ǿ� �ִٸ� �ٸ� üũ�ڽ��鵵 ���� üũ�� Ȱ��ȭ��Ų��.
    	if(this.checked){
    		$("input:checkbox").attr("checked", "checked");
    	}else{
    		$("input:checkbox").removeAttr("checked");
    	}
    });
    
    $("#limit").change(function(){
    	$("#frm").submit();
    });


	$("#top_menu2").attr("data-on", "on");
	$("#top_menu2 > img").attr("src", "./images/admin/menu02_admin_on.gif");
	$("#all_reserved_list_sub_menu > img").attr("src", "./images/admin/menu_admin_sub09_on.gif");
	$("#all_reserved_list_sub_menu").attr("data-on", "on");
    $(".gnb_sub2").show();	
});	
//-->
</script>
</html>