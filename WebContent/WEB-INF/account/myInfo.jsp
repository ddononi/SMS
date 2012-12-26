<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.util.*"%>
<%@ page import="kr.go.police.account.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 헤더  --%>
<jsp:include page="../modules/header.jsp" />
<style>
	select{margin-left: 10px; width: 130px;}
	.user_btn{ margin-top: 10px; text-align: right; }
	#confirm_pwd{ text-indent: 10px;}
</style>
<script>
$(function() {
	//  툴팁 효과 주기 
	//$("#pwd").tooltip();
	$(".user_btn a").button();
	
	// 경찰서 변경시 부서 목록을 가져온다.
	$("#psname").change(function(){
		var $this = $(this);
		// 디폴트일경우
		if($this.val() == "no"){
			return;
		}
		$.get("./DeptListAction.ac", {code : $this.val().split(",")[0]},
				function(result){
					$("#deptName").empty().append($.trim(result));
		});
		
	});
	
	// 회원 탈퇴 비밀번호 확인
	$("#drop_out_dlg").dialog({
		resizable : false,
		height : 160,
		modal : true,
		autoOpen : false,
		buttons : {
			"탈퇴" : function() {
				var $pwd = $("#confirm_pwd");
				if($pwd.val().length <= 0){
					alert("비밀번호를 입력하세요.");
					return;
				}
				
				if($pwd.val().length < 8){
					alert("비밀번호를 정확히 입력하세요.");
					return;
				}
				// 탈퇴 처리
				$("#dropout_frm").submit();
			},
			"취소" : function() {
				$(this).dialog("close");
			}
		}
	});	
	
	
	// 수정 확인 버튼
	$("#modifyBtn").click(function() {
		if (confirm("사용자정보를 수정 하시겠습니까?\n수정후에는 관리자 승인후 이용이 가능합니다.")) {
			
			// 경찰서명 입력검증
			if ($("#psname").val() == 'no') {
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
			if ($("#deptName").val() == 'no') {
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

			// 휴대번호 검증
			var phone = $("#phone1").val() + $("#phone2").val() + $("#phone3").val();
			if(!phone.isMobile()){
				alert("올바른 휴대번호를 입력하세요");
				$("#phone2").focus(); 
				return;
			}
			// 이메일
			var $email = $("#email");
			if(!$email.val().isEmail()){
				alert("이메일을 정확히 입력하세요");
				$email.focus(); 
				return;
			}	
			
			$("#info_modify").submit();
		}
				
	});
	
	// 회원 탈퇴 처리
	$("#dropout_btn").click(function(){
		if (confirm("회원 탈퇴 하시겠습니까?")) {
			$("#drop_out_dlg").dialog("open");
		}
	});
	
	// 비밀번호 변경 버튼
	$("#pwd_change_Btn").click(function(){  
    	$("#pwd_change").submit();
	});    	
	
	// 숫자만 입력허용
	$("#phone2, #phone3").inputNumber();	
	
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
				<form action="./MyInfoModify.ac" method="post" id="info_modify">
					<input type="hidden" name="token"  id="token"  value="${token}" />		
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tbody>
							<tr style="border-top: #258abf 2px solid;">
								<td style="background: #f4f4f4;"><strong>이름</strong></td>
								<td class="tite"><input type="text" id="name" name="name" value="${user.name}"
									title="정확한 이름을 입력하세요" class="none" />
								</td>
							</tr>
							<tr>
								<td style="background: #f4f4f4;"><strong>아이디</strong></td>
								<td class="tite"><input type="text" id="id" name="id"
									value="${user.id}" disabled="disabled" class="none" /></td>
							</tr>
							<tr >
								<td style="background: #f4f4f4;"><strong>비밀번호</strong></td>
								<td class="tite"><a style="margin-left: 10px;" href="#" id="pwd_change_Btn" ><img src="./images/notice/modify_btn.gif" alt="패스워드수정" /></a>								
							</tr>
							<tr>
								<td style="background: #f4f4f4;"><strong>경찰서</strong></td>
								<td class="tite">
									<select  id="psname" name="psname" title="경찰서를 선택하세요." >
										<option value="no" >--선택--</option>
										<c:forEach var="police"  items="${psList}" >
											<option ${user.psName == police.name?"selected='selected'":""} value="${police.code},${police.name}" >${police.name}</option>
										</c:forEach> 	
									</select>
								</td>
							</tr>
							<tr>
								<td style="background: #f4f4f4;"><strong>부서</strong></td>
								<td class="tite">
									<select id="deptName" name="deptName"  title="부서를 선택하세요" >
										<c:forEach var="dept"  items="${deptList}" >
											<option ${dept.name == user.deptName?"selected='selected'":""} value="${dept.deptCode},${dept.name}" >${dept.name}</option>
										</c:forEach> 										
									</select>
								</td>
							</tr>
							<tr>
								<td style="background: #f4f4f4;"><strong>휴대번호</strong></td>
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
								<td style="background: #f4f4f4;"><strong>계급</strong></td>
								<td class="tite"><input id="grade" name="grade"  value="${user.grade}"
									title="계급을 입력하세요" type="text" class="none" /></td>
							</tr>
							<tr>
								<td style="background: #f4f4f4;"><strong>이메일</strong></td>
								<td class="tite"><input id="email" name="email"
									value="${user.email}" title="이메일을 입력하세요" type="text"
									class="none" /></td>
							</tr>
							<tr>
								<td style="background: #f4f4f4;"><strong>등급</strong></td>
								<td class="tite">
									<select style="height: 20px; margin-left: 10px; width: 133px;" id="userClass" name="userClass">
										<option ${user.userClass == 1  ? ' selected="selected" ' : '' }
										value="1">일반사용자</option>
										<option ${user.userClass == 2  ? ' selected="selected" ' : '' }
										value="2">경찰서관리자</option>
										<option ${user.userClass == 3  ? ' selected="selected" ' : '' }
										value="3">지방청관리자</option>										
									</select>
								</td>
							</tr>
						</tbody>
					</table>
				</form>
				<div class="user_btn">
					<a style="color: RED; float: left;" href="#" id="dropout_btn">회원탈퇴<!-- <img  src="./images/sms/tit_leave_off.gif" alt="회원탈퇴" />--></a>
					<a style="float: right;" href="javascript: history.back(-1);">취소<!-- <img src="./images/notice/cancel_btn.gif" alt="취소" />--></a>
					<a  href="#" id="modifyBtn">수정<!-- <img src="./images/notice/register_btn.gif" alt="등록" />--></a>					
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="../modules/footer.jspf" />	

	<%-- 회원 탈퇴 다이얼로그 --%>
	<div id="drop_out_dlg" title="회원 탈퇴 처리">
		<form method="post" id="dropout_frm" action="./DropoutAction.ac" style="margin-top: 20px">
		    <fieldset>
				<label>비밀번호 : </label>	
		        <input type="password" size="30"  class="search" name="confirm_pwd"  id="confirm_pwd" title="비밀번호를 입력하세요" class="text ui-widget-content ui-corner-all" /><br/>
		    </fieldset>
	    </form>
	</div>	
	
	<%-- 비번변경폼 --%>	
	<form method="post" action="./PwdChangePageAction.ac" id="pwd_change" name="pwd_change">
		<input type="hidden" name="index" value="${index}">
	</form>		
</body>



</html>