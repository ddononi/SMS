<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.util.*"%>
<%@ page import="kr.go.police.account.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 헤더  --%>
<jsp:include page="../modules/header.jsp" />
<style>
	select{margin-left: 10px; width: 130px;}
</style>
<script>
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
	
	$("input").keypress(function(event){
		var $this = $(this);
	    if ( isCapslock(event) ){
	        $this.attr("title", "Caps Lock이 켜져 있습니다.")
	    }else{
	    	  $this.removeAttr("title").tooltip( "destroy" );
	    }
	});
});
</script>
<body>
	<div id="wrapper">
		<%-- 상단메뉴  --%>
		<jsp:include page="../modules/topmenu.jsp" />

		<div id="contents">
			<%-- 사이드 메뉴  --%>
			<jsp:include page="../modules/sidebox.jsp" />
			<div class="boderWrap">
				<h3>
					<img src="./images/boder/tit_member.gif" alt="회원정보변경" />
				</h3>
				<form action="./PwdChangeAction.ac" method="post">
						<input type="hidden" id="id" name="id" value="${user.id}" />
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tbody>
							<tr style="border-top: #258abf 2px solid;">								
								<td style="background: #f4f4f4;"><strong>현재 비밀번호</strong></td>
								<td class="tite"><input type="password" id="pwd" name="pwd" value=""
									 class="none" />
								</td>
							</tr>
							<tr >
								<td style="background: #f4f4f4;"><strong>새로운 비밀번호</strong></td>
								<td class="tite"><input type="password" id="new_pwd" name="new_pwd" value=""
									class="none" />
								</td>
							</tr>
							<tr>
								<td style="background: #f4f4f4;"><strong>비밀번호확인</strong></td>
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
						src="./images/notice/register_btn.gif" alt="등록" /></a>
						<a href="javascript: history.back(-1);"><img src="./images/notice/cancel_btn.gif" alt="취소" /></a>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="../modules/footer.jspf" />	
</body>


</html>