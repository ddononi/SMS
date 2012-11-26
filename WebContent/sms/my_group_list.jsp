<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.util.*" %>	
<%@ page import="kr.go.police.sms.*" %>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	// requset �� ���� �׷츮��Ʈ�� �����´�.
	int count =0;
	List<Group> groupList = (List<Group>)request.getAttribute("groups");
%>	
<c:set var="list"  value ="<%=groupList %>" />
<c:set var="size"  value ="<%=groupList.size() %>" />
<%-- ���  --%>
<jsp:include page="../modules/header.jsp" />
<body>
	<div id="dialog-modify-form" title="�׷�� ����">
		<form method="post" id="modifyFrm" action="./GroupModifyAction.sm" style="margin-top: 20px">
		    <fieldset>
				<input type="hidden" id="groupIndex" value=""  name="groupIndex"  />		    
		        <input type="text" size="30" name="groupName" id="groupName" class="text ui-widget-content ui-corner-all" />
		    </fieldset>
	    </form>
	</div>
	<div id="dialog-add-form" title="�׷� �߰�">
		<form method="post" id="addFrm" action="./GroupAddAction.sm" style="margin-top: 20px">
		    <fieldset>
		        <input type="text" size="30" name="groupName" id="groupName" class="text ui-widget-content ui-corner-all" />
		    </fieldset>
	    </form>
	</div>	
	<div id="wrapper">
		<%-- ��ܸ޴�  --%>
		<jsp:include page="../modules/topmenu.jsp" />
		<div id="contents">
			<%-- ���̵� �޴�  --%>
			<jsp:include page="../modules/sidebox.jsp" />
     	   <div class="boderWrap">
				<h3>
					<img src="images/notice/title_group.gif" alt="�׷����" />
				</h3>
				<form id="delForm" action="./GroupDelAction.sm">
					<input value="" id="groupIndex" name="groupIndex" type="hidden" />
				</form>				
				<%--
					�׷��� 10�� �̳��϶��� ���� �����ϰ� �Ѵ�.
				 --%>
				<div></div>		
					<p style="float: left;display: inline-block;font-size: 1.2em; margin-top: 10px;font-weight: bold;"  >�׷� : ${size}��</p>
					<c:if test="${size < 10}">
						<div style="float: right;display: inline-block;margin-bottom: 5px;">
							<a href="#"  id="add_btn">�߰�</a>
						</div>
					</c:if>
				<table style="float: left;" id="groupList" width="100%" border="0" cellpadding="0" cellspacing="0">
					<colgroup>
						<col width="15%" />
						<col width="70%" />
						<col width="15%" />						
					</colgroup>
					<thead>
						<tr height="34px;">
							<th>No.</th>
							<th>�׷��</th>				
							<th>����</th>										
						</tr>
					</thead>
					<tbody>
					<!--  ���׷� ����Ʈ -->
					<c:forEach var="user"  items="${list}" >
						<tr>
							<td>					
					   		   <%=++count%>
					       </td>
							<td>					
					   		   <a ${user.group != '�⺻�׷�'?'class="group_td"':''}  data-index="${user.index}"  href="#" onclick="javascript:return false;" >${user.group}</a>
					       </td>	
							<td>		
								<%--
									�⺻�׷��� ����ó���� �Ҽ� ����.
								 --%>
								<c:if test="${user.group != '�⺻�׷�'}">		
					   		   		<a class="group_del" data-index="${user.index}"  href="#" onclick="javascript:return false;" ><img src="./images/sms/bt_categoryAll_close.gif" alt="����" /></a>
					   		   	</c:if>	
					       </td>					       		
					     </tr> 
					</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
<jsp:include page="../modules/footer.jspf" />
</body>
<script type="text/javascript">
<!--
$(function(){
	
	// �޴� ó��
	$("#top_menu2").attr("data-on", "on");
	$("#top_menu2 > img").attr("src", "./images/top/menu02_on.gif");
	$("#my_group_menu > img").attr("src", "./images/top/menu_sub10_on.gif");
	$("#my_group_menu").attr("data-on", "on");
	$("#top_menu2").trigger("mouseover");
	$(".gnb_sub2").show();
	
	// buttons ui
	$("#add_btn").button();
	
    // odd td colume stand out
    $("#groupList tbody tr").each(function(i){
       if(i % 2 == 0){
        $(this).children('td').css('background', '#efe');
       } 
    });
 
    // ���̺� ���� �� ���� ȿ��
    $("#groupList td").hover(
      function () {
        $(this).siblings().andSelf().addClass("hover");
      },
      function () {
        $(this).siblings().andSelf().removeClass("hover");
      }
    );   
    
    // ���� ���̾�α� ����
	$( "#dialog-modify-form" ).dialog({
            autoOpen: false,
            modal: true,
            width : 250,
            buttons: {
                "����": function() {
                	var groupVal = $("#modifyFrm #groupName").val();
                	if(groupVal.length <= 0){
                		alert("�׷���� �Է��ϼ���");
                		return;
                	}                	
                	$("#modifyFrm").submit();           	
                },
                "���": function() {
                    $( this ).dialog( "close" );
                }
            }
    }); 
    
    //  �߰� ���̾�α� ����
	$( "#dialog-add-form" ).dialog({
            autoOpen: false,
            modal: true,
            width : 250,
            buttons: {
            	// �׷� �߰� ó��
                "�߰�": function() {
                	var groupVal = $("#addFrm #groupName").val();
                	if(groupVal.length <= 0){
                		alert("�׷���� �Է��ϼ���");
                		return;
                	}     
                	
                	//	������ �׷���� �ִ��� üũ
                	var existCheck = true;
                	$(".group_td").each(function(){
                		var existedGroup = $.trim($(this).text());
                		if(existedGroup == $.trim(groupVal)){
                			alert(existedGroup.replace("�׷�", "") +"�׷��� �̹� �����մϴ�.");
                			existCheck = false;
                			return false;
                		}
               		});
                	
                	if(existCheck == false){
                		return;
                	}
                	
                	$("#addFrm").submit();                        	
                },
                "���": function() {
                	$( "#addFrm #groupName").val("");    	                	
                    $( this ).dialog( "close" );
                }
            }
    });     
    
    // �׷��߰���ư
    $("#add_btn").click(function(){
    	$( "#addFrm #groupName").val("");    	
    	$( "#dialog-add-form" ).dialog( "open" );
    });
    
    //  �׷�� ���� ó��
    $(".group_td").click(function(){
    	$( "#dialog-modify-form" ).dialog( "open" );
    	var groupName = $(this).text();
    	$("#modifyFrm #groupName").val(groupName);
		var index = $(this).attr("data-index");    	
    	$("#modifyFrm #groupIndex").val(index);    	
    });
    
    // �׷� ���� ó��
	$(".group_del").click(function(){
		// �׷�� ���
		var group = $(this).parent("td").siblings().first().next().children().text();
		if(confirm(group+ "�׷��� ���� �Ͻðڽ��ϱ�?")){
			var index = $(this).attr("data-index");
			// ���� �� ����
			$("#delForm > input:first").val(index);
			$("#delForm").submit();
		}
	});  

});	
//-->
</script>
</html>