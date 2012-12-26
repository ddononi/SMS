<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.util.*"%>
<%@ page import="kr.go.police.account.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- ���  --%>
<jsp:include page="../modules/header.jsp" />
<style>
.check_yes img {
	display: none;
}
select, #pwd_reset_btn{margin-left: 10px; width: 130px;}
#pwd_reset_btn{ vertical-align: middle; color: RED;  margin-bottom: 3px;}
#new_pwd{ width: 150px; height: 30px; vertical-align: middle; line-height: 30px; text-indent: 10px;}
</style>
<script>
$(function() {
	
    //  �߰� ���̾�α� ����
	$( "#new_pwd_dlg" ).dialog({
            autoOpen: false,
            modal: true,
            width : 300,
            buttons: {
                "Ȯ��": function() {
                    $( this ).dialog( "close" );
                }
            }
    });      
    
	// ���� �̽��� ui ������ �����̳� �̽����� ������ Ȯ�� ���̾�α׸� ����ش�.
	$("#radio").children("[type=radio]").click(function(event) {
		//  ���� ��ư�� ���������
		if ($(this).attr("id") == "approve") {
			$("#approveConfirm").dialog("open");
		} else {
			$("#refuseConfirm").dialog("open");
		}
	});
	
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
	
	// ���� ó�� Ȯ�� ���̾�α�
	$("#approveConfirm").dialog({
		resizable : false,
		height : 160,
		modal : true,
		autoOpen : false,
		buttons : {
			"Ȯ��" : function() {
				$("[name='approve']:first").attr('checked', 'checked');
				$("[name='approve']:last").removeAttr('checked');					
				$(this).dialog("close");
			},
			"���" : function() {
				$("[name='approve']:first").removeAttr('checked');
				$("[name='approve']:last").attr('checked', 'checked');
				$(this).dialog("close");
			}
		}
	});

	$("#refuseConfirm").dialog({
		resizable : false,
		height : 160,
		modal : true,
		autoOpen : false,
		buttons : {
			"Ȯ��" : function() {
				$("[name='approve']:first").removeAttr('checked');
				$("[name='approve']:last").attr('checked', 'checked');
				$(this).dialog("close");
			},
			"���" : function() {
				$("[name='approve']:first").attr('checked', 'checked');
				$("[name='approve']:last").removeAttr('checked');		
				$(this).dialog("close");
			}
		}
	});

	// ���� Ȯ�� ��ư
	$("#modifyBtn").click(function() {
		if (confirm("����������� ���� �Ͻðڽ��ϱ�?")) {
			// ���� ó��
			var phone = $("#phone1").val() + $("#phone2").val() + $("#phone3").val();
			if(!phone.isMobile()){
				alert("�ùٸ� �޴��ȣ�� �Է��ϼ���");
				$("#phone2").focus(); 
				return;
			}
			
			var $email = $("#email");
			if(!$email.val().isEmail()){
				alert("�̸����� ��Ȯ�� �Է��ϼ���");
				$email.focus(); 
				return;
			}			
			
			$("#info").submit();
		}
	});
	
	// ��й�ȣ �ʱ�ȭ ��ư
	$("#pwd_reset_btn").button().click(function() {
		if (confirm("��й�ȣ�� �ʱ�ȭ �Ͻðڽ��ϱ�?")) {
			$("#pwd_reset").submit();
			$.post(
				"./PwdResetAction.ac",
				{index : $("#index").val() },
				function(result){
					var result = $.trim(result);
					if(result != "fail"){
						$("#new_pwd").val(result);
						$( "#new_pwd_dlg" ).dialog("open");
						// Ŭ�����忡 ����
						$("#copy_btn").button().click(function(){
							clipboard(result);
						});
					}
			});
	
		}
	});
	
	// ���ڸ� �Է����
	$("#phone2, #phone3").inputNumber();

});
</script>
<body>
	<div id="wrapper">
		<%-- ��ܸ޴�  --%>
		<jsp:include page="../modules/admin_topmenu.jsp" />

		<div id="contents">
			<%-- ���̵� �޴�  --%>
			<jsp:include page="../modules/admin_sidebox.jsp" />
			<div class="boderWrap">
				<h3>
					<img src="./images/boder/tit_member.gif" alt="ȸ����������" />
				</h3>
				<form action="./AdminModifyUserAction.ac" method="post" id="info">
					<input type="hidden" name="token"  id="token"  value="${token}" />				
					<input value="${userData.index}" id="index"
						name="index" type="hidden" />
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tbody>
							<tr style="border-top: #258abf 2px solid;">
								<td style="background: #f4f4f4;"><strong>�̸�</strong></td>
								<td class="tite"><input type="text" id="name" name="name" value="${userData.name}"
									title="��Ȯ�� �̸��� �Է��ϼ���" class="none" />
								</td>
							</tr>
							<tr>
								<td style="background: #f4f4f4;"><strong>���̵�</strong></td>
								<td class="tite"><input type="text" id="id" name="id"
									value="${userData.id}" disabled="disabled" class="none" /></td>
							</tr>
							<tr>
								<td style="background: #f4f4f4;"><strong>��й�ȣ �ʱ�ȭ</strong></td>
								<td class="tite" style="vertical-align: middle;">				
									<!-- 					
										<input type="text" id="password" name="password" 
													value="${userData.pwdreset}" disabled="disabled" class="none" />									
										-->
										<h4>
											<a href="#" id="pwd_reset_btn" onclick="return false;">��й�ȣ �ʱ�ȭ</a>
											(�ʱ�ȭ�� 8�ڸ��� �ӽ� ��й�ȣ�� �߱޵˴ϴ�.)
										</h4>										
								</td>									
							</tr>
							<tr>
								<td style="background: #f4f4f4;"><strong>������</strong></td>
								<td class="tite">
									<select  id="psname" name="psname" title="�������� �����ϼ���." >
										<option value="no" >--����--</option>
										<c:forEach var="police"  items="${psList}" >
											<option ${userData.psName == police.name?"selected='selected'":""} value="${police.code},${police.name}" >${police.name}</option>
										</c:forEach> 	
									</select>
							</tr>
							<tr>
								<td style="background: #f4f4f4;"><strong>�μ�</strong></td>
								<td class="tite">
									<select id="deptName" name="deptName"  title="�μ��� �����ϼ���" >
										<c:forEach var="dept"  items="${deptList}" >
											<option ${dept.name == userData.deptName?"selected='selected'":""} value="${dept.deptCode},${dept.name}" >${dept.name}</option>
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
									</select>
									 - <input
									value="${userData.phoneMiddle1}" id="phone2" name="phone2" type="text"
									style="width: 60px;" class="phone none" maxlength="4" /> - <input
									value="${userData.phoneBottom1}" id="phone3" name="phone3" type="text"
									style="width: 60px;" class="phone none" maxlength="4"  /></td>
							</tr>
							<tr>
								<td style="background: #f4f4f4;"><strong>���</strong></td>
								<td class="tite"><input id="grade" name="grade" value="${userData.grade}"
									title="����� �Է��ϼ���" type="text" class="none" /></td>
							</tr>
							<tr>
								<td style="background: #f4f4f4;"><strong>�̸���</strong></td>
								<td class="tite"><input id="email" name="email"
									value="${userData.email}" title="�̸����� �Է��ϼ���" type="text"
									class="none" /></td>
							</tr>
							<tr>
								<td style="background: #f4f4f4;"><strong>���</strong></td>
								<td class="tite">
									<select style="height: 20px; margin-left: 10px; width: 133px;" id="userClass" name="userClass">
										<option ${userData.userClass == 1  ? ' selected="selected" ' : '' }
										value="1">�Ϲݻ����</option>
										<option ${userData.userClass == 2  ? ' selected="selected" ' : '' }
										value="2">������������</option>
										<option ${userData.userClass == 3  ? ' selected="selected" ' : '' }
										value="3">����û������</option>
									</select>
								</td>
							</tr>
							<tr class="end">
								<td style="background: #f4f4f4;"><strong>���ο���</strong></td>
								<td class="tite"  id="radio" ><input style="margin-left: 10px"
									${userData.approve ? 'checked="checked"':'' } value="y"
									type="radio" id="approve" name="approve" /><label
									for="approve">����</label> <input class="none" 
									${userData.approve ? '' : 'checked="checked"' } value="n"
									type="radio" id="refuse" name="approve" /><label
									for="refuse"">�̽���</label></td>
								</td>
							</tr>
						</tbody>
					</table>
				</form>
				<div class="btn">
					<a href="#" id="modifyBtn"><img
						src="./images/notice/register_btn.gif" alt="���" /></a>
						<a href="javascript:history.go(-1);"><img src="./images/notice/cancel_btn.gif" alt="���" /></a>
				</div>
			</div>
		</div>
	</div>
	<!--  ���̾� �α� -->
	<div id="approveConfirm" title="����ó�� Ȯ��">
		<p>
			<span class="ui-icon ui-icon-alert"
				style="float: left; margin: 0 7px 20px 0;"></span>����ڸ� ����ó�� �Ͻðڽ��ϱ�?
		</p>
	</div>
	<div id="refuseConfirm" title="�̽���ó�� Ȯ��">
		<p>
			<span class="ui-icon ui-icon-alert"
				style="float: left; margin: 0 7px 20px 0;"></span>����ڸ� �̽���ó�� �Ͻðڽ��ϱ�?
		</p>
	</div>	
	
	<div id="new_pwd_dlg" title="�ӽ� ��й�ȣ �߱�">
		<input size="30" class="inp"  id="new_pwd"  type="text" name="new_pwd"  readonly="readonly" />
		<a href="#" id="copy_btn">Ŭ������ ����</a>
	</div>	
	<jsp:include page="../modules/footer.jspf" />	
</body>
</html>