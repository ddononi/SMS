<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.util.*" %>	
<%@ page import="kr.go.police.account.*" %>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../modules/header.jsp" />
<style>
.check_yes img {
	display: none;
}

input, select {
	margin-left: 10px;
}
#deptName, #psname, #level{width: 150px;}
</style>
<script type="text/javascript">
$(function() {	
	// ���� Ȯ�� ��ư
	$("#modifyBtn").click(function() {
		if (confirm("��й�ȣ�� �����Ͻðڽ��ϱ�?")) {

			//  ��й�ȣ ����
			if ($("#new_pwd").val().length <= 0) {
				alert("��й�ȣ�� �Է��ϼ���");
				$("#new_pwd").trigger("focus");
				return;
			} else if (checkPassword($("#id").val(), $("#new_pwd").val()) === false) {
				$("#new_pwd").trigger("focus");
				return;
			}

			// ��й�ȣ Ȯ��ó��
			if ($("#new_pwd").val() != $("#rePwd")
					.val()) {
				alert("��й�ȣ�� �������� �ʽ��ϴ�.");
				$("#rePwd").trigger("focus");
				return;
			}
			
			if ($("#new_pwd").val() == $("#pwd")
					.val()) {
				alert("���� ��й�ȣ�� �ٸ� ��й�ȣ�� �Է��ϼ���.");
				$("#new_pwd").trigger("focus");
				return;
			}			
			$("form").submit();
		}
	});
});
</script>
<body>
	<div id="join_wrapper">
		<!-- join -->
		<h1 class="logo">
			<img src="../SMS/images/top/logo.gif" alt="����ûSMS" border="0" />
		</h1>
		<div class="join_box">
			<h3>
				<img src="../SMS/images/join/txt_join.gif" alt="ȸ������" />
			</h3>
				<form action="./PwdChangeAction.ac" method="post">
						<input type="hidden" id="id" name="id" value="${id}" />
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tbody>
							<tr style="border-top: #258abf 2px solid;">								
								<td style="background: #f4f4f4;"><strong>���� ��й�ȣ</strong></td>
								<td class="tite"><input type="password" id="pwd" name="pwd" value=""
									title="���� ��й�ȣ�� �Է��� �ּ���." class="none" />
								</td>
							</tr>
							<tr >
								<td style="background: #f4f4f4;"><strong>���ο� ��й�ȣ</strong></td>
								<td class="tite"><input type="password" id="new_pwd" name="new_pwd" value=""
									title="�ּ� 8�����̻�  ���빮��, ���ҹ���, ����, Ư������ �� 3���� �̻����� �����ؾ� �մϴ�." class="none" />
								</td>
							</tr>
							<tr>
								<td style="background: #f4f4f4;"><strong>��й�ȣȮ��</strong></td>
								<td class="tite"><input type="password" id="rePwd" name="rePwd" value=""
									title="������ ��й�ȣ�� �Է��ϼ���" class="none" />
								</td>
							</tr>
						</tbody>
					</table>
					<input type="hidden" id="index" name="index" value="${index}" />
					<input type="hidden" id="Url" name="url" value="login/login.jsp" />
				</form>
			<p class="join_info">
				<a href="#" id="modifyBtn"><img
						src="../SMS/images/notice/register_btn.gif" alt="���" /></a>
						<a href="javascript: history.back(-1);"><img src="../SMS/images/notice/cancel_btn.gif" alt="���" /></a>
			</p>
		</div>
	</div>

</body>

</html>
