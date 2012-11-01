<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.util.*" %>	
<%@ page import="kr.go.police.account.*" %>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	// requset �� ���� ���� �����͸� �����´�.
	UserBean user = (UserBean)request.getAttribute("userData");
%>	
<c:set var="user"  value ="<%=user %>" />
<%-- ���  --%>
<jsp:include page="../modules/header.jsp" />
<style>
.check_yes img {
	display: none;
}

input {
	margin-left: 10px;
}
</style>
<script>
$(function() {
	// ���� �̽��� ui ������ �����̳� �̽����� ������ Ȯ�� ���̾�α׸� ����ش�.
    $( "#radio" ).buttonset().children("[type=radio]").click(function(event){
    	//  ���� ��ư�� ���������
    	if( $(this).attr("id") == "approve"){
    		$( "#approveConfirm" ).dialog( "open" );
    	}else{
    		$( "#refuseConfirm" ).dialog( "open" );    		
    	}
	
    });
    
	
	// ���� ó�� Ȯ�� ���̾�α�
    $( "#approveConfirm" ).dialog({
        resizable: false,
        height:160,
        modal: true,
        autoOpen : false,
        buttons: {
            "Ȯ��": function() {
            	$("#refuse").removeAttr('checked');
            		//.andSelf().attr('checked', 'checked');
            	// ���� ��ư ����
            	$( "#radio" ).buttonset("refresh");                   	
                $( this ).dialog( "close" );
            },
            "���": function() {
            	$("#approve").siblings().removeAttr('checked');
            	// ���� ��ư ����
            	$( "#radio" ).buttonset("refresh");                	
                $( this ).dialog( "close" );
            }
        }
    });
	
    $( "#refuseConfirm" ).dialog({
        resizable: false,
        height:160,
        modal: true,
        autoOpen : false,
        buttons: {
            "Ȯ��": function() {
            	$("#approve").removeAttr('checked');
            	// ���� ��ư ����
            	$( "#radio" ).buttonset("refresh");                   	
                $( this ).dialog( "close" );
            },
            "���": function() {
            	$("#refuse").siblings().removeAttr('checked');
            	// ���� ��ư ����
            	$( "#radio" ).buttonset("refresh");             	
                $( this ).dialog( "close" );
            }
        }
    });	
    
});
</script>
<body>
	<div id="wrapper">
		<%-- ��ܸ޴�  --%>
		<jsp:include page="../modules/topmenu.jsp" />

		<div id="contents">
			<%-- ���̵� �޴�  --%>
			<jsp:include page="../modules/sidebox.jsp" />

			<div id="contentsWrap">
				<h3>
					<img src="./images/lettersend/title.gif" alt="���ں�����" />
				</h3>
			<form action="./JoinAction.ac" method="post">
				<fieldset>
					<legend>ȸ�� ����</legend>
					<table width="547" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<th width="146">�̸�</th>
							<td class="check_yes"><img
								src="./images/join/check_yes.gif" /> <img
								src="./images/join/check_no.gif" /></td>
							<td><input type="text" id="name" name="name"  value="${user.name}"
								title="��Ȯ�� �̸��� �Է��ϼ���" /></td>
						</tr>
						<tr>
							<th width="146">���̵�</th>
							<td class="check_yes"><img
								src="./images/join/check_yes.gif" /> <img
								src="./images/join/check_no.gif" /></td>
							<td><input type="text" id="id" name="id"  value="${user.id}" disabled="disabled"
								title="�������ڷθ� �Է��ϼ���" /></td>
						</tr>
						<tr>
							<th width="146">������</th>
							<td class="check_yes"><img
								src="./images/join/check_yes.gif" /> <img
								src="./images/join/check_no.gif" /></td>
							<td><input type="text" value="${user.psName}" id="psname" name="psname" title="��Ȯ�� ���������� �Է��ϼ���" /></td>
						</tr>
						<tr>
							<th width="146">�μ�</th>
							<td class="check_yes"><img
								src="./images/join/check_yes.gif" /> <img
								src="./images/join/check_no.gif" /></td>
							<td><input type="text" id="deptName" value="${user.deptName}" name="deptName" title="��Ȯ�� �μ����� �Է��ϼ���" /></td>
						</tr>
						<tr>
							<th width="146">�޴��ȣ</th>
							<td class="check_yes"><img
								src="./images/join/check_yes.gif" /> <img
								src="./images/join/check_no.gif" /></td>
							<td>
								<input type="text"  value="${user.phone1}"  id="phone1" name="phone1" /> - <input type="text"
								id="phone2" name="phone2" value="${user.phone1}"   /> - <input value="${user.phone1}"  type="text" id="phone3" name="phone3"  />
							</td>
						</tr>
						<tr>
							<th width="146">���</th>
							<td class="check_yes"><img
								src="./images/join/check_yes.gif" /> <img
								src="./images/join/check_no.gif" /></td>
							<td><input type="text" id="grade" name="grade" title="����� �Է��ϼ���" /></td>
						</tr>
						<tr>
							<th width="146">�̸���</th>
							<td class="check_yes"><img
								src="./images/join/check_yes.gif" /> <img
								src="./images/join/check_no.gif" /></td>
							<td><input type="text" id="email" name="email" value="${user.email}" title="�̸����� �Է��ϼ���" /></td>
						</tr>
						
						<tr>
							<th width="146">���ο���</th>
							<td class="check_yes"><img
								src="./images/join/check_yes.gif" /> <img
								src="./images/join/check_no.gif" /></td>
							<td><input type="text" id="email" name="email" value="${user.email}" title="�̸����� �Է��ϼ���" /></td>
						</tr>						
	
						<tr>
							<th width="300">���ο���</th>	
								<td id="radio" colspan="2">							
					        		 <input ${user.approve ? 'checked="checked"':'' }  type="radio" id="approve" name="approve" /><label for="approve">����</label>
					       			 <input ${user.approve ? '' : 'checked="checked"' } type="radio" id="refuse" name="refuse"   /><label for="refuse"">�̽���</label>
					 			</td>						
						</tr>	
										
					</table>
				</fieldset>
			</form>
			<p class="join_info">
				<a href="#" id="joinBtn"><img src="./images/join/btn_join.gif"
					alt="ȸ������" /></a><a href=""><img
					src="./images/join/btn_cancel.gif" alt="���" /></a>
			</p>
		</div>
					</table>
				</div>
			</div>
		</div>
		<div id="footer">Ǫ�Ϳ���</div>
	</div>
	
<!--  ���̾� �α� -->	
<div id="approveConfirm" title="����ó�� Ȯ��">
    <p><span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span >����ڸ� ����ó�� �Ͻðڽ��ϱ�?</p>
</div>
	
<div id="refuseConfirm" title="�̽���ó�� Ȯ��">
    <p><span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span >����ڸ� �̽���ó�� �Ͻðڽ��ϱ�?</p>
</div>	
	
</body>


</html>