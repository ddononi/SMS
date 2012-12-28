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
<body>
	<div id="dialog-modify-form" title="�׷�� ����">
		<form method="post" id="modifyFrm" action="./GroupModifyAction.ad" style="margin-top: 20px">
		    <fieldset>
				<input type="hidden" id="groupIndex" value=""  name="groupIndex"  />		    
		        <input type="text" size="30" name="groupName" id="groupName" class="text ui-widget-content ui-corner-all" />
		    </fieldset>
	    </form>
	</div>
	<div id="dialog-add-form" title="�׷� �߰�">
		<form method="post" id="addFrm" action="./GroupAddAction.ad" style="margin-top: 20px">
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
			<form id="delForm" action="./GroupDelAction.ad">
				<input value="" id="groupIndex" name="groupIndex" type="hidden" />
			</form>			
     	   <div class="boderWrap">
				<h3>
					<img src="images/lettersend/title_address.gif" alt="���׷�" />
				</h3>
				<table id="groupList" width="100%" border="0" cellpadding="0" cellspacing="0">
					<colgroup>
						<col width="15%" />
						<col width="50%" />
						<col width="20%" />						
						<col width="15%" />						
					</colgroup>
					<thead>
						<tr height="34px;">
							<th>No.</th>
							<th>�׷��</th>	
							<th>�ο�</th>										
							<th>����</th>										
						</tr>
					</thead>
					<tbody>
						<!--  �⺻ �׷� -->
						<!-- 
						<tr>
							<td>1</td>
							<td>					
					   		   <a href="#" onclick="javascript:return false;" >�⺻�׷�</a>
					       </td>	
					       <td>${data.group}</td>					       
					       <td></td>
				       </tr>
				       -->
					<!--  ���׷� ����Ʈ -->
					<c:forEach var="data"  items="${groups}" >
						<tr>
							<td>
					   		   <%=no--%>
					       </td>
							<td>					
					   		   <a class="group_td"  data-index="${data.index}"  href="./AddressListAction.ad?groupIndex=${data.index}"  >${data.group}</a>
					       </td>	
							<td>					
					   		    ${data.count}
					       </td>					       
							<td>
								<%--
									�⺻�׷��� ����ó���� �Ҽ� ����.
								 --%>
								<c:if test="${data.group != '�⺻�׷�'}">														
					   		   		<a class="group_del" data-index="${data.index}"  href="#" onclick="javascript:return false;" ><img src="./images/sms/bt_categoryAll_close.gif" alt="����" /></a>
								</c:if>						   		   	
					       </td>					       		
					     </tr> 
					</c:forEach>
					</tbody>
				</table>
				<div id="buttons" style="float: right;  margin-top: 5px;">
						<a  href="#"  id="add_btn" onclick="return false;">�߰�</a>
				</div>		
				<div style="clear: both;"></div>				
				<c:if test="${(empty groups) == false}">
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
    
    $("#add_btn").button();
    
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
                	$("#addFrm").submit();                        	
                },
                "���": function() {
                    $( this ).dialog( "close" );
                }
            }
    });     
    
    // �׷��߰���ư
    $("#add_btn").click(function(){
    	$( "#dialog-add-form" ).dialog( "open" );
    });

    
    // �׷� ���� ó��
	$(".group_del").click(function(){
		// �׷�� ���
		var group = $(this).parent("td").siblings().first().next().children().text();
		if(confirm( "(" + group + ")�׷��� ���� �Ͻðڽ��ϱ�?")){
			var index = $(this).attr("data-index");
			// ���� �� ����
			$("#delForm > input:first").val(index);
			$("#delForm").submit();
		}
	});  

	// �޴� ó��
	$("#top_menu3").attr("data-on", "on");
	$("#top_menu3 > img").attr("src", "./images/top/menu03_on.gif");
	$("#address_book_menu > img").attr("src", "./images/top/menu_sub05_on.gif");
	$("#address_book_menu").attr("data-on", "on");
	$("#top_menu3").trigger("mouseover");
	$(".gnb_sub3").show();
});	
//-->
</script>
</html>