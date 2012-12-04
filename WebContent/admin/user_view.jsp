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
</style>
<script>
	$(function() {
		// ���� �̽��� ui ������ �����̳� �̽����� ������ Ȯ�� ���̾�α׸� ����ش�.
		$("#radio").children("[type=radio]").click(function(event) {
			//  ���� ��ư�� ���������
			if ($(this).attr("id") == "approve") {
				$("#approveConfirm").dialog("open");
			} else {
				$("#refuseConfirm").dialog("open");
			}

		});

		// ���� ó�� Ȯ�� ���̾�α�
		$("#approveConfirm").dialog({
			resizable : false,
			height : 160,
			modal : true,
			autoOpen : false,
			buttons : {
				"Ȯ��" : function() {
					$("#refuse").removeAttr('checked');
					$(this).dialog("close");
				},
				"���" : function() {
					$("#approve").siblings().removeAttr('checked');
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
					$("#approve").removeAttr('checked');
					$(this).dialog("close");
				},
				"���" : function() {
					$("#refuse").siblings().removeAttr('checked');
					$(this).dialog("close");
				}
			}
		});

		// ���� Ȯ�� ��ư
		$("#modifyBtn").click(function() {
			if (confirm("����������� ���� �Ͻðڽ��ϱ�?")) {
				$("form").submit();
			}
		});

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
				<form action="./AdminModifyUserAction.ac" method="post">
					<input value="${index}" id="index"
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
								<td style="background: #f4f4f4;"><strong>������</strong></td>
								<td class="tite"><input type="text" value="${userData.psName}"
									id="psname" name="psname" title="��Ȯ�� ���������� �Է��ϼ���" class="none" /></td>
							</tr>
							<tr>
								<td style="background: #f4f4f4;"><strong>�μ�</strong></td>
								<td class="tite"><input type="text" id="deptName"
									value="${userData.deptName}" name="deptName" title="��Ȯ�� �μ����� �Է��ϼ���"
									class="none" /></td>
							</tr>
							<tr>
								<td style="background: #f4f4f4;"><strong>�޴��ȣ</strong></td>
								<td class="tite"><input value="${userData.phoneTop1}" id="phone1"
									name="phone1" type="text"
									style="width: 60px; margin-left: 10px;" class="phone none" /> - <input
									value="${userData.phoneMiddle1}" id="phone2" name="phone2" type="text"
									style="width: 60px;" class="phone none" /> - <input
									value="${userData.phoneBottom1}" id="phone3" name="phone3" type="text"
									style="width: 60px;" class="phone none" /></td>
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
										value="2">�����������</option>
										<option ${userData.userClass == 3  ? ' selected="selected" ' : '' }
										value="3">û�����</option>
										<option ${userData.userClass == 0  ? ' selected="selected" ' : '' }
										value="0">������</option>
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
	<jsp:include page="../modules/footer.jspf" />	
</body>
</html>