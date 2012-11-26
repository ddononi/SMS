<%@page import="kr.go.police.address.AddressBean"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.util.*" %>	
<%@ page import="kr.go.police.address.*" %>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	// requset �� ���� �ּҷ��� �����´�.
	List<AddressBean> list = (List<AddressBean>)request.getAttribute("list");
	// �׷� �ε����� �����´�.
	String groupIndex = (String)request.getAttribute("groupIndex");
	// ���������̼�
	String pagiNation = (String)request.getAttribute("pagiNation");
	// ����Ʈ ����
	int listSize = (Integer)request.getAttribute("listSize");	
	//	����Ʈ ��ȣ
	int no = (Integer)request.getAttribute("no");		
%>	
<%-- ���  --%>
<jsp:include page="../modules/header.jsp" />
<body>
	<div id="dialog-modify-form" title="�ּҷ� ����">
		<form method="post" id="modifyFrm" action="./AddressModifyAction.ad" style="margin-top: 20px">
		    <fieldset>
				<input type="hidden" id="groupIndex" value="${groupIndex}"  name="groupIndex"  />		    
				<input type="hidden" id="index"  value=""  name="index"  />
				<label>�̸� : </label>	
		        <input type="text" size="30" name="peopleName"  id="peopleName" title="�̸��� �Է��ϼ���" class="text ui-widget-content ui-corner-all" /><br/>
				<label>��ȭ��ȣ : </label>		        						    
		        <input type="text" size="30" name="phoneNum" id="phoneNum" title="��ȭ��ȣ�� �Է��ϼ���"  class="text ui-widget-content ui-corner-all" />
		    </fieldset>
	    </form>
	</div>
	<div id="dialog-add-form" title="�ּҷ� �߰�">
		<form method="post" id="addFrm" action="./AddressAddAction.ad" style="margin-top: 20px">
		    <fieldset>
				<input type="hidden" value="${groupIndex}"  name="groupIndex"  />			    
				<label>�̸� : </label>	
		        <input type="text" size="30" name="peopleName"  id="peopleName" title="�̸��� �Է��ϼ���" class="text ui-widget-content ui-corner-all" /><br/>
				<label>��ȭ��ȣ : </label>		        						    
		        <input type="text" size="30" name="phoneNum" id="phoneNum" title="��ȭ��ȣ�� �Է��ϼ���"  class="text ui-widget-content ui-corner-all" />
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
					<img src="images/lettersend/title_address.gif" alt="�� �ּҷ� " />
				</h3>
				<!--  �ּҷ� ���� -->
				<form method="post"  id="delForm" action="./AddressDelAction.ad">
					<input value="" id="index" name="index" type="hidden" />
					<input type="hidden" value="${groupIndex}"  name="groupIndex"  />							
				</form>				
				<div id="buttons" style="margin-bottom: 5px;">
					<p style="float: left;display: inline-block;font-size: 14px;margin-top: 10px;font-size: 1.2em; font-weight: bold;"  >�ο� : ${listSize}��</p>
					<div style="float: right;display: inline-block;margin-bottom: 5px;" >
						<a   href="#"  id="add_btn">�߰�</a>
						<a   href="./AddressGroupListAction.ad" >�׷���</a>
					</div>						
				</div>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<colgroup>
						<col width="15%" />
						<col width="30%" />
						<col width="40%" />						
						<col width="15%" />						
					</colgroup>
					<thead>
						<tr height="34px;">
							<th>No.</th>
							<th>�̸�</th>				
							<th>��ȭ��ȣ</th>	
							<th>����</th>																	
						</tr>
					</thead>
					<tbody>
					<!--  �ּҷ��� ���°�� -->
					<c:if test="${empty list}">
						<tr>
							<td colspan="4"> �ּҷ��� �����ϴ�.</td>
						</tr> 
					</c:if>
					<!--  �ּҷ� ����Ʈ -->
					<c:forEach var="data"  items="${list}" >
						<tr>
							<td>					
					   		   <%=no--%>
					       </td>
							<td>					
					   		   <a class="group_td"  data-index="${data.index}"  href="#" onclick="javascript:return false;" >${data.people}</a>
					       </td>
							<td>					
					   		   <a class="group_td"  data-index="${data.index}"  href="#" onclick="javascript:return false;" >${data.phone}</a>
					       </td>						       	
							<td>					
					   		   <a class="group_del" data-index="${data.index}"  href="#" onclick="javascript:return false;" ><img src="./images/sms/bt_categoryAll_close.gif" alt="����" /></a>
					       </td>					       		
					     </tr> 
					</c:forEach>
					</tbody>
				</table>
				<c:if test="${(empty list) == false}">
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
    $("table tbody tr").each(function(i){
       if(i % 2 == 0){
       	 $(this).children('td').css('background', '#efe');
       } 
    });
 
    // ���̺� ���� �� ���� ȿ��
    $("table td").hover(
      function () {
        $(this).siblings().andSelf().addClass("hover");
      },
      function () {
        $(this).siblings().andSelf().removeClass("hover");
      }
    );   
    $("#buttons a").button();
    // ���� ���̾�α� ����
	$( "#dialog-modify-form" ).dialog({
            autoOpen: false,
            modal: true,
            width : 250,
            buttons: {
                "����": function() {
                	var groupVal = $("#modifyFrm #peopleName").val();
                	if(groupVal.length <= 0){
                		alert("�̸��� �Է��ϼ���");
                		return;
                	} 
                	
                	var groupVal = $("#modifyFrm #phoneNum").val();
                	if(groupVal.length <= 0){
                		alert("��ȭ��ȣ�� �Է��ϼ���");
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
            	// �ּҷ� �߰� ó��
                "�߰�": function() {
                	var groupVal = $("#addFrm #peopleName").val();
                	if(groupVal.length <= 0){
                		alert("�̸��� �Է��ϼ���");
                		return;
                	} 
                	
                	var phoneVal = $("#addFrm #phoneNum").val();
                	if(phoneVal.length <= 0){
                		alert("��ȭ��ȣ�� �Է��ϼ���");
                		return;
                	}                  	
                	
                	// �޴�����ȣ �Է½� �ùٸ� �޴��� ��ȣ���� üũ   
                	var rgEx = /[01](0|1|6|7|8|9)(\d{4}|\d{3})\d{4}$/g;  
                	var chkFlg = rgEx.test(phoneVal);    
                	if(!chkFlg){ 
                	    alert("�ùٸ� �޴�����ȣ�� �ƴմϴ�.");
                	    $("#addFrm #phoneNum").focus();  
                	    return; 
                	 }
                	 
                	$("#addFrm").submit();                        	
                },
                "���": function() {
                    $( this ).dialog( "close" );
                }
            }
    });     
    
    // 	�ּҷ� �߰� 
    $("#add_btn").click(function(){
    	$( "#dialog-add-form" ).dialog( "open" );
    });
    
    //  �׷�� ���� ó��
    $(".group_td").click(function(){
    	$( "#dialog-modify-form" ).dialog( "open" );
    	// �̸� ��������
    	var peopleName = $.trim($(this).parent('td').siblings().first().next().text());
    	$("#modifyFrm #peopleName").val(peopleName);
    	// ��ȭ��ȣ ��������
    	var phoneNum = $.trim($(this).parent('td').siblings().first().next().next().text());
    	$("#modifyFrm #phoneNum").val(phoneNum);    	
    	// �ش� �ε���
		var index = $(this).attr("data-index");    	
    	$("#modifyFrm #index").val(index);    	
    });
    
    // �׷� ���� ó��
	$(".group_del").click(function(){
		// �׷�� ���
		var group = $(this).parent("td").siblings().first().next().children().text();
		if(confirm("(" + group + ") �ּҷ��� ���� �Ͻðڽ��ϱ�?")){
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