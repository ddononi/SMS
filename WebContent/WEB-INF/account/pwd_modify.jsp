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
	// 수정 확인 버튼
	$("#modifyBtn").click(function() {
		if (confirm("비밀번호를 변경하시겠습니까?")) {

			//  비밀번호 검증
			if ($("#new_pwd").val().length <= 0) {
				alert("비밀번호를 입력하세요");
				$("#new_pwd").trigger("focus");
				return;
			} else if (checkPassword($("#id").val(), $("#new_pwd").val()) === false) {
				$("#new_pwd").trigger("focus");
				return;
			}

			// 비밀번호 확인처리
			if ($("#new_pwd").val() != $("#rePwd")
					.val()) {
				alert("비밀번호가 동일하지 않습니다.");
				$("#rePwd").trigger("focus");
				return;
			}
			
			if ($("#new_pwd").val() == $("#pwd")
					.val()) {
				alert("현재 비밀번호와 다른 비밀번호를 입력하세요.");
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
			<img src="../SMS/images/top/logo.gif" alt="강원청SMS" border="0" />
		</h1>
		<div class="join_box">
			<h3>
				<img src="../SMS/images/join/txt_join.gif" alt="회원가입" />
			</h3>
				<form action="./PwdChangeAction.ac" method="post">
						<input type="hidden" id="id" name="id" value="${id}" />
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tbody>
							<tr style="border-top: #258abf 2px solid;">								
								<td style="background: #f4f4f4;"><strong>현재 비밀번호</strong></td>
								<td class="tite"><input type="password" id="pwd" name="pwd" value=""
									title="현재 비밀번호를 입력해 주세요." class="none" />
								</td>
							</tr>
							<tr >
								<td style="background: #f4f4f4;"><strong>새로운 비밀번호</strong></td>
								<td class="tite"><input type="password" id="new_pwd" name="new_pwd" value=""
									title="최소 8글자이상  영대문자, 영소문자, 숫자, 특수문자 중 3종류 이상으로 구성해야 합니다." class="none" />
								</td>
							</tr>
							<tr>
								<td style="background: #f4f4f4;"><strong>비밀번호확인</strong></td>
								<td class="tite"><input type="password" id="rePwd" name="rePwd" value=""
									title="동일한 비밀번호를 입력하세요" class="none" />
								</td>
							</tr>
						</tbody>
					</table>
					<input type="hidden" id="index" name="index" value="${index}" />
					<input type="hidden" id="Url" name="url" value="login/login.jsp" />
				</form>
			<p class="join_info">
				<a href="#" id="modifyBtn"><img
						src="../SMS/images/notice/register_btn.gif" alt="등록" /></a>
						<a href="javascript: history.back(-1);"><img src="../SMS/images/notice/cancel_btn.gif" alt="취소" /></a>
			</p>
		</div>
	</div>

</body>

</html>
