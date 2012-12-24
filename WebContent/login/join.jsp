<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.util.*" %>	
<%@ page import="kr.go.police.account.*" %>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	AccountDAO dao = new AccountDAO();
	ArrayList<PoliceBean> list = (ArrayList<PoliceBean>)dao.getDeptList();
%>
<c:set var="list" value="<%=list%>" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title><%=getServletContext().getInitParameter("title")%></title>
<link rel="shortcut icon" href="../images/base/police.ico" type="image/ico" />
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<link rel="stylesheet" type="text/css" href="../css/style.css"/>
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
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
<!--
$(document).ready(
function() {
	//  툴팁 효과 주기 
	//$("#pwd").tooltip();

	// 아이디 입력포커스를 나갈경우 아이디 중복 체크
	var duple_check_id = false;
	$("#id").focusout(function() {
		if ($("#id").val().length <= 0)
			return;

		$.ajax({
			url : '../IdCheckAction.ac',
			data : {
				id : $(this).val()
			},
			success : function(result) {
				if ($.trim(result) == 'true') {
					//alert("사용가능한 아이디 입니다.");
					duple_check_id = true;
				} else {
					alert("이미 사용중인 아이디 입니다.");
					duple_check_id = false;
				}
			},
		});
	});
	
	// 경찰서 변경시 부서 목록을 가져온다.
	$("#psname").change(function(){
		var $this = $(this);
		// 디폴트일경우
		if($this.val() == "no"){
			return;
		}
		
		$.get("../DeptListAction.ac", {code : $this.val().split(",")[0]},
				function(result){
					$("#deptName").empty().append($.trim(result));
		});
		
	});

	// 전송전 로그인필드 검증 처리
	$("#joinBtn").click(function() {
			// 이름 입력 검증
			if ($("#name").val().length <= 0) {
				alert("이름을 정확히 입력하세요");
				$("#name").trigger("focus");
				var $imgs = $("#name").parent(
						'td').siblings().eq(1);
				$imgs.children(":last").show();
				$imgs.children(":first").hide();
				return;
			} else {
				var $imgs = $("#name").parent(
						'td').siblings().eq(1);
				$imgs.children(":first").show();
				$imgs.children(":last").hide();
			}

			// 아이디 정규식
			var regIdExp = /^[a-z0-9_]{4,20}$/;
			//입력을안했다면
			if ($("#id").val().length <= 0) {
				alert("아이디를 입력하세요");
				$("#id").trigger("focus");
				var $imgs = $("#id").parent(
						'td').siblings().eq(1);
				$imgs.children(":last").show();
				$imgs.children(":first").hide();
				return;
			}

			//  아이디 입력 형식 체크
			if (regIdExp.test($("#id").val()) === false) {
				alert("정확한 아이디를 입력하세요");
				$("#id").trigger("focus");
				var $imgs = $("#id").parent(
						'td').siblings().eq(1);
				$imgs.children(":last").show();
				$imgs.children(":first").hide();
				return;
			} else {
				var $imgs = $("#id").parent(
						'td').siblings().eq(1);
				$imgs.children(":first").show();
				$imgs.children(":last").hide();
			}

			// 경찰서명 입력검증
			if ($("#psname").val() == "no") {
				alert("경찰서를 선택하세요");
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

			// 부서 입력 검증
			if ($("#deptName").val() == "no") {
				alert("부서를 선택하세요");
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
			
			// 등급 입력 검증
			if ($("#userClass").val() == "no") {
				alert("등급을 선택하세요");
				$("#userClass").trigger("focus");
				var $imgs = $("#userClass").parent(
						'td').siblings().eq(1);
				$imgs.children(":first").show();
				$imgs.children(":last").hide();
				return;
			} else {
				var $imgs = $("#userClass").parent(
						'td').siblings().eq(1);
				$imgs.children(":first").show();
				$imgs.children(":last").hide();
			}			

			// 계급 입력 검증
			if ($("#grade").val().length <= 0) {
				alert("계급명을 정확히 입력하세요");
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

			// 계급 입력 검증
			var regEmailExp = /^([0-9a-zA-Z_-]+)@([0-9a-zA-Z_-]+)(\.[0-9a-zA-Z_-]+){1,2}$/;
			if ($("#email").val().length <= 0
					|| regEmailExp.test($(
							"#email").val()) === false) {
				alert("이메일을 정확히 입력하세요");
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

			//  비밀번호 검증
			if ($("#pwd").val().length <= 0) {
				alert("비밀번호를 입력하세요");
				$("#pwd").trigger("focus");
				return;
			} else if (checkPassword($("#id").val(), $("#pwd").val()) === false) {
				$("#pwd").trigger("focus");
				return;
			}

			// 비밀번호 확인처리
			if ($("#rePwd").val() != $("#rePwd")
					.val()) {
				alert("비밀번호가 동일하지 않습니다.");
				$("#rePwd").trigger("focus");
				return;
			}

			if (duple_check_id == false) {
				alert('중복된 아이디 입니다.');
				return;
			}

			$("form").submit();
		});

	//$.myUtil.debug($("#id").val(), 'awef', 'awefawef', 'awefawef');

	// 비밀번호창에 엔터키를 누르면 로그인버튼 트리거
	/*
	$("#user_pwd").keydown(function(event){
	   if(event.keyCode == ENTER_KEY){
	       $("#login_btn").click();
	   }
	});
	 */

	// 아이디 필드에 포커스
	//$("#id").focus();
});
//-->
</script>
</head>
<body>
	<div id="join_wrapper">
		<!-- join -->
		<h1 class="logo">
			<img src="../images/top/logo.gif" alt="강원청SMS" border="0" /></a>
		</h1>
		<div class="join_box">
			<h3>
				<img src="../images/join/txt_join.gif" alt="회원가입" />
			</h3>
			<form action="../JoinAction.ac" method="post">
				<fieldset>
					<legend>회원가입 정보입력</legend>
					<table width="547" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<th width="146">이름</th>
							<td class="check_yes"><img
								src="../images/join/check_yes.gif" /> <img
								src="../images/join/check_no.gif" /></td>
							<td><input type="text" id="name" name="name"
								title="정확한 이름을 입력하세요" /></td>
						</tr>
						<tr>
							<th width="146">아이디</th>
							<td class="check_yes"><img
								src="../images/join/check_yes.gif" /> <img
								src="../images/join/check_no.gif" /></td>
							<td><input type="text" id="id" name="id"
								title="영문숫자로만 입력하세요" /></td>
						</tr>
						<tr>
							<th width="146">비밀번호</th>
							<td class="check_yes"><img
								src="../images/join/check_yes.gif" /> <img
								src="../images/join/check_no.gif" /></td>
							<td><input type="password" id="pwd" name="pwd"
								title="최소 8글자이상  영대문자, 영소문자, 숫자, 특수문자 중 3종류 이상으로 구성해야 합니다." /></td>
						</tr>
						<tr>
							<th width="146">비밀번호 확인</th>
							<td class="check_yes"><img
								src="../images/join/check_yes.gif" /> <img
								src="../images/join/check_no.gif" /></td>
							<td><input type="password" id="rePwd" name="rePwd"
								title="동일한 비밀번호를 입력하세요" /></td>
						</tr>
						<tr>
							<th width="146">경찰서</th>
							<td class="check_yes"><img
								src="../images/join/check_yes.gif" /> <img
								src="../images/join/check_no.gif" /></td>
							<td>
								<select id="psname" name="psname" title="정확한 경찰서명을 입력하세요" >
									<option value="no" >--선택--</option>
									<c:forEach var="police"  items="${list}" >
										<option value="${police.code},${police.name}" >${police.name}</option>
									</c:forEach> 	
								</select>
							</td>
						</tr>
						<tr>
							<th width="146">부서</th>
							<td class="check_yes"><img
								src="../images/join/check_yes.gif" /> <img
								src="../images/join/check_no.gif" /></td>
							<td>
								<select  id="deptName" name="deptName"  title="정확한 부서명을 입력하세요" >
									<option value="no" >--선택--</option>
								</select>
							</td>
						</tr>
					
						<tr>
							<th width="146">등급</th>
							<td class="check_yes"><img
								src="../images/join/check_yes.gif" /> <img
								src="../images/join/check_no.gif" /></td>
							<td>
								<select id="userClass" name="userClass">
									<option value="no" >--선택--</option>
									<option value="1">일반사용자</option>
									<option value="2">경찰서관리자</option>
									<option value="3">지방청관리자</option>
								</select>
							</td>
						</tr>						
						
						<tr>
							<th width="146">휴대번호</th>
							<td class="check_yes"><img
								src="../images/join/check_yes.gif" /> <img
								src="../images/join/check_no.gif" /></td>
							<td>
								<select  id="phone1" name="phone1">
									<option value="010">010</option>
									<option value="011">011</option>
									<option value="016">016</option>
									<option value="018">018</option>
									<option value="019">019</option>
								</select>
								 - <input maxlength="4" type="text" class="phone" id="phone2"
								name="phone2" /> - <input maxlength="4" class="phone" type="text"
								id="phone3" name="phone3" /></td>
						</tr>
						<tr>
							<th width="146">계급</th>
							<td class="check_yes"><img
								src="../images/join/check_yes.gif" /> <img
								src="../images/join/check_no.gif" /></td>
							<td><input type="text" id="grade" name="grade"
								title="계급을 입력하세요" /></td>
						</tr>
						<tr>
							<th width="146">이메일</th>
							<td class="check_yes"><img
								src="../images/join/check_yes.gif" /> <img
								src="../images/join/check_no.gif" /></td>
							<td><input type="text" id="email" name="email"
								title="이메일을 입력하세요" /></td>
						</tr>
					</table>
				</fieldset>
			</form>
			<p class="join_info">
				<a href="#" id="joinBtn"><img src="../images/join/btn_join.gif"
					alt="회원가입" /></a>
					<a href="./login.jsp"><img src="../images/join/btn_cancel.gif" alt="취소" /></a>
			</p>
		</div>
	</div>
<jsp:include page="../include/footer.jspf" />	
</body>

</html>
