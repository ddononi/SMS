<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.util.*"%>
<%@ page import="kr.go.police.account.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- ���  --%>
<jsp:include page="../modules/header.jsp" />
<style>
	select{margin-left: 10px; width: 130px;}
	.user_btn{ margin-top: 10px; text-align: right; }
	#confirm_pwd{ text-indent: 10px;}
</style>
<script>
$(function() {
	//  ���� ȿ�� �ֱ� 
	//$("#pwd").tooltip();
	$(".user_btn a").button();
	
	// ������ ����� �μ� ����� �����´�.
	$("#psname").change(function(){
		var $this = $(this);
		// ����Ʈ�ϰ��
		if($this.val() == "no"){
			return;
		}
		$.get("./DeptListAction.ac", {code : $this.val().split(",")[0]},
				function(result){
					$("#deptName").empty().append($.trim(result));
		});
		
	});
	
	// ȸ�� Ż�� ��й�ȣ Ȯ��
	$("#drop_out_dlg").dialog({
		resizable : false,
		height : 160,
		modal : true,
		autoOpen : false,
		buttons : {
			"Ż��" : function() {
				var $pwd = $("#confirm_pwd");
				if($pwd.val().length <= 0){
					alert("��й�ȣ�� �Է��ϼ���.");
					return;
				}
				
				if($pwd.val().length < 8){
					alert("��й�ȣ�� ��Ȯ�� �Է��ϼ���.");
					return;
				}
				// Ż�� ó��
				$("#dropout_frm").submit();
			},
			"���" : function() {
				$(this).dialog("close");
			}
		}
	});	
	
	
	// ���� Ȯ�� ��ư
	$("#modifyBtn").click(function() {
		if (confirm("����������� ���� �Ͻðڽ��ϱ�?\n�����Ŀ��� ������ ������ �̿��� �����մϴ�.")) {
			
			// �������� �Է°���
			if ($("#psname").val() == 'no') {
				alert("�������� �����ϼ���");
				$("#psname").trigger("focus");
				var $imgs = $("#psname")
						.parent('td')
						.siblings().eq(1);
				$imgs.children(":last").show();
				$imgs.children(":first").hide();
				return;
			} else {
				var $imgs = $("#psname")
						.parent('td')
						.siblings().eq(1);
				$imgs.children(":first").show();
				$imgs.children(":last").hide();
			}

			// �μ� �Է� ����
			if ($("#deptName").val() == 'no') {
				alert("�μ��� �����ϼ���");
				$("#deptName").trigger("focus");
				var $imgs = $("#deptName")
						.parent('td')
						.siblings().eq(1);
				$imgs.children(":first").show();
				$imgs.children(":last").hide();
				return;
			} else {
				var $imgs = $("#deptName")
						.parent('td')
						.siblings().eq(1);
				$imgs.children(":first").show();
				$imgs.children(":last").hide();
			}

			// ��� �Է� ����
			if ($("#grade").val().length <= 0) {
				alert("��޸��� ��Ȯ�� �Է��ϼ���");
				$("#grade").trigger("focus");
				var $imgs = $("#grade").parent(
						'td').siblings().eq(1);
				$imgs.children(":first").show();
				$imgs.children(":last").hide();
				return;
			} else {
				var $imgs = $("#grade").parent(
						'td').siblings().eq(1);
				$imgs.children(":first").show();
				$imgs.children(":last").hide();
			}

			// �޴��ȣ ����
			var phone = $("#phone1").val() + $("#phone2").val() + $("#phone3").val();
			if(!phone.isMobile()){
				alert("�ùٸ� �޴��ȣ�� �Է��ϼ���");
				$("#phone2").focus(); 
				return;
			}
			// �̸���
			var $email = $("#email");
			if(!$email.val().isEmail()){
				alert("�̸����� ��Ȯ�� �Է��ϼ���");
				$email.focus(); 
				return;
			}	
			
			$("#info_modify").submit();
		}
				
	});
	
	// ȸ�� Ż�� ó��
	$("#dropout_btn").click(function(){
		if (confirm("ȸ�� Ż�� �Ͻðڽ��ϱ�?")) {
			$("#drop_out_dlg").dialog("open");
		}
	});
	
	// ��й�ȣ ���� ��ư
	$("#pwd_change_Btn").click(function(){  
    	$("#pwd_change").submit();
	});    	
	
	// ���ڸ� �Է����
	$("#phone2, #phone3").inputNumber();	
	
});
</script>
<body>
	<div id="wrapper">
		<%-- ��ܸ޴�  --%>
		<jsp:include page="../modules/topmenu.jsp" />

		<div id="contents">
			<%-- ���̵� �޴�  --%>
			<jsp:include page="../modules/sidebox.jsp" />
			<div class="boderWrap">
				<h3>
					<img src="./images/boder/tit_member.gif" alt="ȸ����������" />
				</h3>
				<form action="./MyInfoModify.ac" method="post" id="info_modify">
					<input type="hidden" name="token"  id="token"  value="${token}" />		
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tbody>
							<tr style="border-top: #258abf 2px solid;">
								<td style="background: #f4f4f4;"><strong>�̸�</strong></td>
								<td class="tite"><input type="text" id="name" name="name" value="${user.name}"
									title="��Ȯ�� �̸��� �Է��ϼ���" class="none" />
								</td>
							</tr>
							<tr>
								<td style="background: #f4f4f4;"><strong>���̵�</strong></td>
								<td class="tite"><input type="text" id="id" name="id"
									value="${user.id}" disabled="disabled" class="none" /></td>
							</tr>
							<tr >
								<td style="background: #f4f4f4;"><strong>��й�ȣ</strong></td>
								<td class="tite"><a style="margin-left: 10px;" href="#" id="pwd_change_Btn" ><img src="./images/notice/modify_btn.gif" alt="�н��������" /></a>								
							</tr>
							<tr>
								<td style="background: #f4f4f4;"><strong>������</strong></td>
								<td class="tite">
									<select  id="psname" name="psname" title="�������� �����ϼ���." >
										<option value="no" >--����--</option>
										<c:forEach var="police"  items="${psList}" >
											<option ${user.psName == police.name?"selected='selected'":""} value="${police.code},${police.name}" >${police.name}</option>
										</c:forEach> 	
									</select>
								</td>
							</tr>
							<tr>
								<td style="background: #f4f4f4;"><strong>�μ�</strong></td>
								<td class="tite">
									<select id="deptName" name="deptName"  title="�μ��� �����ϼ���" >
										<c:forEach var="dept"  items="${deptList}" >
											<option ${dept.name == user.deptName?"selected='selected'":""} value="${dept.deptCode},${dept.name}" >${dept.name}</option>
										</c:forEach> 										
									</select>
								</td>
							</tr>
							<tr>
								<td style="background: #f4f4f4;"><strong>�޴��ȣ</strong></td>
								<td class="tite">
									<select id="phone1" name="phone1" style="width: 60px; margin-left: 10px;" >
										<option ${userData.phoneTop1 == "010"?"selected='selected'":""}  value="010">010</option>
					 					<option ${userData.phoneTop1 == "011"?"selected='selected'":""}  value="011">011</option>
										<option ${userData.phoneTop1 == "016"?"selected='selected'":""}  value="016">016</option>
										<option ${userData.phoneTop1 == "018"?"selected='selected'":""}  value="018">018</option>
										<option ${userData.phoneTop1 == "019"?"selected='selected'":""}  value="019">019</option>
									</select> - <input
									value="${user.phoneMiddle1}" id="phone2" name="phone2" type="text" maxlength="4"
									style="width: 60px;" class="phone none" /> - <input
									value="${user.phoneBottom1}" id="phone3" name="phone3" type="text" maxlength="4"
									style="width: 60px;" class="phone none" /></td>
							</tr>
							<tr>
								<td style="background: #f4f4f4;"><strong>���</strong></td>
								<td class="tite"><input id="grade" name="grade"  value="${user.grade}"
									title="����� �Է��ϼ���" type="text" class="none" /></td>
							</tr>
							<tr>
								<td style="background: #f4f4f4;"><strong>�̸���</strong></td>
								<td class="tite"><input id="email" name="email"
									value="${user.email}" title="�̸����� �Է��ϼ���" type="text"
									class="none" /></td>
							</tr>
							<tr>
								<td style="background: #f4f4f4;"><strong>���</strong></td>
								<td class="tite">
									<select style="height: 20px; margin-left: 10px; width: 133px;" id="userClass" name="userClass">
										<option ${user.userClass == 1  ? ' selected="selected" ' : '' }
										value="1">�Ϲݻ����</option>
										<option ${user.userClass == 2  ? ' selected="selected" ' : '' }
										value="2">������������</option>
										<option ${user.userClass == 3  ? ' selected="selected" ' : '' }
										value="3">����û������</option>										
									</select>
								</td>
							</tr>
						</tbody>
					</table>
				</form>
				<div class="user_btn">
					<a style="color: RED; float: left;" href="#" id="dropout_btn">ȸ��Ż��<!-- <img  src="./images/sms/tit_leave_off.gif" alt="ȸ��Ż��" />--></a>
					<a style="float: right;" href="javascript: history.back(-1);">���<!-- <img src="./images/notice/cancel_btn.gif" alt="���" />--></a>
					<a  href="#" id="modifyBtn">����<!-- <img src="./images/notice/register_btn.gif" alt="���" />--></a>					
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="../modules/footer.jspf" />	

	<%-- ȸ�� Ż�� ���̾�α� --%>
	<div id="drop_out_dlg" title="ȸ�� Ż�� ó��">
		<form method="post" id="dropout_frm" action="./DropoutAction.ac" style="margin-top: 20px">
		    <fieldset>
				<label>��й�ȣ : </label>	
		        <input type="password" size="30"  class="search" name="confirm_pwd"  id="confirm_pwd" title="��й�ȣ�� �Է��ϼ���" class="text ui-widget-content ui-corner-all" /><br/>
		    </fieldset>
	    </form>
	</div>	
	
	<%-- ��������� --%>	
	<form method="post" action="./PwdChangePageAction.ac" id="pwd_change" name="pwd_change">
		<input type="hidden" name="index" value="${index}">
	</form>		
</body>



</html>