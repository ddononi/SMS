<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.util.*"%>
<%@ page import="kr.go.police.account.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- ���  --%>
<jsp:include page="../modules/header.jsp" />
<style>
	select{margin-left: 10px; width: 130px;}
</style>
<script>
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
	
	$("input").keypress(function(event){
		var $this = $(this);
	    if ( isCapslock(event) ){
	        $this.attr("title", "Caps Lock�� ���� �ֽ��ϴ�.")
	    }else{
	    	  $this.removeAttr("title").tooltip( "destroy" );
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
				<form action="./PwdChangeAction.ac" method="post">
						<input type="hidden" id="id" name="id" value="${user.id}" />
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tbody>
							<tr style="border-top: #258abf 2px solid;">								
								<td style="background: #f4f4f4;"><strong>���� ��й�ȣ</strong></td>
								<td class="tite"><input type="password" id="pwd" name="pwd" value=""
									 class="none" />
								</td>
							</tr>
							<tr >
								<td style="background: #f4f4f4;"><strong>���ο� ��й�ȣ</strong></td>
								<td class="tite"><input type="password" id="new_pwd" name="new_pwd" value=""
									class="none" />
								</td>
							</tr>
							<tr>
								<td style="background: #f4f4f4;"><strong>��й�ȣȮ��</strong></td>
								<td class="tite"><input type="password" id="rePwd" name="rePwd" value=""
									 class="none" />
								</td>
							</tr>
						</tbody>
					</table>
					<input type="hidden" id="index" name="index" value="${index}" />
					<input type="hidden" id="Url" name="url" value="MyInfoAction.ac" />
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