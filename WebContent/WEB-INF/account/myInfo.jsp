<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.util.*"%>
<%@ page import="kr.go.police.account.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	// requset �� ���� ���� �����͸� �����´�.
	UserBean user = (UserBean) request.getAttribute("userData");
%>
<c:set var="user" value="<%=user%>" />
<%-- ���  --%>
<jsp:include page="../modules/header.jsp" />
<script>
$(function() {
	//  ���� ȿ�� �ֱ� 
	$("#pwd").tooltip();
	
	// ���� Ȯ�� ��ư
	$("#modifyBtn").click(function() {
		if (confirm("����������� ���� �Ͻðڽ��ϱ�?\n�����Ŀ��� ������ ������ �̿��� �����մϴ�.")) {
			
			// �������� �Է°���
			if ($("#psname").val().length <= 0) {
				alert("���������� �Է��ϼ���");
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
			if ($("#deptName").val().length <= 0) {
				alert("�μ����� ��Ȯ�� �Է��ϼ���");
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

			// ��� �Է� ����
			var regEmailExp = /^([0-9a-zA-Z_-]+)@([0-9a-zA-Z_-]+)(\.[0-9a-zA-Z_-]+){1,2}$/;
			if ($("#email").val().length <= 0
					|| regEmailExp.test($(
							"#email").val()) === false) {
				alert("�̸����� ��Ȯ�� �Է��ϼ���");
				$("#email").trigger("focus");
				var $imgs = $("#email").parent(
						'td').siblings().eq(1);
				$imgs.children(":first").show();
				$imgs.children(":last").hide();
				return;
			} else {
				var $imgs = $("#email").parent(
						'td').siblings().eq(1);
				$imgs.children(":first").show();
				$imgs.children(":last").hide();
			}

			//  ��й�ȣ ����
			if ($("#pwd").val().length <= 0) {
				alert("��й�ȣ�� �Է��ϼ���");
				$("#pwd").trigger("focus");
				return;
			} else if (checkPassword($("#id").val(), $("#pwd").val()) === false) {
				$("#pwd").trigger("focus");
				return;
			}

			// ��й�ȣ Ȯ��ó��
			if ($("#rePwd").val() != $("#rePwd")
					.val()) {
				alert("��й�ȣ�� �������� �ʽ��ϴ�.");
				$("#rePwd").trigger("focus");
				return;
			}
			
			$("form").submit();
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
			<div class="boderWrap">
				<h3>
					<img src="./images/boder/tit_member.gif" alt="ȸ����������" />
				</h3>
				<form action="./MyInfoModify.ac" method="post">
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
								<td class="tite"><input type="password" id="pwd" name="pwd" value=""
									title="�ּ� 8�����̻�  ���빮��, ���ҹ���, ����, Ư������ �� 3���� �̻����� �����ؾ� �մϴ�."" class="none" />
								</td>
							</tr>
							<tr >
								<td style="background: #f4f4f4;"><strong>��й�ȣȮ��</strong></td>
								<td class="tite"><input type="password" id="rePwd" name="rePwd" value=""
									title="������ ��й�ȣ�� �Է��ϼ���" class="none" />
								</td>
							</tr>									
							<tr>
								<td style="background: #f4f4f4;"><strong>������</strong></td>
								<td class="tite"><input type="text" value="${user.psName}"
									id="psname" name="psname" title="��Ȯ�� ���������� �Է��ϼ���" class="none" /></td>
							</tr>
							<tr>
								<td style="background: #f4f4f4;"><strong>�μ�</strong></td>
								<td class="tite"><input type="text" id="deptName"
									value="${user.deptName}" name="deptName" title="��Ȯ�� �μ����� �Է��ϼ���"
									class="none" /></td>
							</tr>
							<tr>
								<td style="background: #f4f4f4;"><strong>�޴��ȣ</strong></td>
								<td class="tite"><input value="${user.phoneTop1}" id="phone1"
									name="phone1" type="text"
									style="width: 60px; margin-left: 10px;" class="phone none" /> - <input
									value="${user.phoneMiddle1}" id="phone2" name="phone2" type="text"
									style="width: 60px;" class="phone none" /> - <input
									value="${user.phoneBottom1}" id="phone3" name="phone3" type="text"
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
										value="2">�����������</option>
										<option ${user.userClass == 3  ? ' selected="selected" ' : '' }
										value="3">û�����</option>
										<option ${user.userClass == 0  ? ' selected="selected" ' : '' }
										value="0">������</option>
									</select>
								</td>
							</tr>
						</tbody>
					</table>
				</form>
				<div class="btn">
					<a href="#" id="modifyBtn"><img
						src="./images/notice/register_btn.gif" alt="���" /></a>
						<a href="javascript: history.back(-1);"><img src="./images/notice/cancel_btn.gif" alt="���" /></a>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="../modules/footer.jspf" />	
</body>


</html>