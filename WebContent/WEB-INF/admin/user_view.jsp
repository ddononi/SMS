<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.util.*"%>
<%@ page import="kr.go.police.account.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 헤더  --%>
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
	
    //  추가 다이얼로그 설정
	$( "#new_pwd_dlg" ).dialog({
            autoOpen: false,
            modal: true,
            width : 300,
            buttons: {
                "확인": function() {
                    $( this ).dialog( "close" );
                }
            }
    });      
    
	// 승인 미승인 ui 설정후 승인이나 미승인을 누르면 확인 다이얼로그를 띄워준다.
	$("#radio").children("[type=radio]").click(function(event) {
		//  승인 버튼을 눌렀을경우
		if ($(this).attr("id") == "approve") {
			$("#approveConfirm").dialog("open");
		} else {
			$("#refuseConfirm").dialog("open");
		}
	});
	
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
	
	// 승인 처리 확인 다이얼로그
	$("#approveConfirm").dialog({
		resizable : false,
		height : 160,
		modal : true,
		autoOpen : false,
		buttons : {
			"확인" : function() {
				$("[name='approve']:first").attr('checked', 'checked');
				$("[name='approve']:last").removeAttr('checked');					
				$(this).dialog("close");
			},
			"취소" : function() {
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
			"확인" : function() {
				$("[name='approve']:first").removeAttr('checked');
				$("[name='approve']:last").attr('checked', 'checked');
				$(this).dialog("close");
			},
			"취소" : function() {
				$("[name='approve']:first").attr('checked', 'checked');
				$("[name='approve']:last").removeAttr('checked');		
				$(this).dialog("close");
			}
		}
	});

	// 수정 확인 버튼
	$("#modifyBtn").click(function() {
		if (confirm("사용자정보를 수정 하시겠습니까?")) {
			// 검증 처리
			var phone = $("#phone1").val() + $("#phone2").val() + $("#phone3").val();
			if(!phone.isMobile()){
				alert("올바른 휴대번호를 입력하세요");
				$("#phone2").focus(); 
				return;
			}
			
			var $email = $("#email");
			if(!$email.val().isEmail()){
				alert("이메일을 정확히 입력하세요");
				$email.focus(); 
				return;
			}			
			
			$("#info").submit();
		}
	});
	
	// 비밀번호 초기화 버튼
	$("#pwd_reset_btn").button().click(function() {
		if (confirm("비밀번호를 초기화 하시겠습니까?")) {
			$("#pwd_reset").submit();
			$.post(
				"./PwdResetAction.ac",
				{index : $("#index").val() },
				function(result){
					var result = $.trim(result);
					if(result != "fail"){
						$("#new_pwd").val(result);
						$( "#new_pwd_dlg" ).dialog("open");
						// 클립보드에 복사
						$("#copy_btn").button().click(function(){
							clipboard(result);
						});
					}
			});
	
		}
	});
	
	// 숫자만 입력허용
	$("#phone2, #phone3").inputNumber();

});
</script>
<body>
	<div id="wrapper">
		<%-- 상단메뉴  --%>
		<jsp:include page="../modules/admin_topmenu.jsp" />

		<div id="contents">
			<%-- 사이드 메뉴  --%>
			<jsp:include page="../modules/admin_sidebox.jsp" />
			<div class="boderWrap">
				<h3>
					<img src="./images/boder/tit_member.gif" alt="회원정보변경" />
				</h3>
				<form action="./AdminModifyUserAction.ac" method="post" id="info">
					<input type="hidden" name="token"  id="token"  value="${token}" />				
					<input value="${userData.index}" id="index"
						name="index" type="hidden" />
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tbody>
							<tr style="border-top: #258abf 2px solid;">
								<td style="background: #f4f4f4;"><strong>이름</strong></td>
								<td class="tite"><input type="text" id="name" name="name" value="${userData.name}"
									title="정확한 이름을 입력하세요" class="none" />
								</td>
							</tr>
							<tr>
								<td style="background: #f4f4f4;"><strong>아이디</strong></td>
								<td class="tite"><input type="text" id="id" name="id"
									value="${userData.id}" disabled="disabled" class="none" /></td>
							</tr>
							<tr>
								<td style="background: #f4f4f4;"><strong>비밀번호 초기화</strong></td>
								<td class="tite" style="vertical-align: middle;">				
									<!-- 					
										<input type="text" id="password" name="password" 
													value="${userData.pwdreset}" disabled="disabled" class="none" />									
										-->
										<h4>
											<a href="#" id="pwd_reset_btn" onclick="return false;">비밀번호 초기화</a>
											(초기화시 8자리의 임시 비밀번호가 발급됩니다.)
										</h4>										
								</td>									
							</tr>
							<tr>
								<td style="background: #f4f4f4;"><strong>경찰서</strong></td>
								<td class="tite">
									<select  id="psname" name="psname" title="경찰서를 선택하세요." >
										<option value="no" >--선택--</option>
										<c:forEach var="police"  items="${psList}" >
											<option ${userData.psName == police.name?"selected='selected'":""} value="${police.code},${police.name}" >${police.name}</option>
										</c:forEach> 	
									</select>
							</tr>
							<tr>
								<td style="background: #f4f4f4;"><strong>부서</strong></td>
								<td class="tite">
									<select id="deptName" name="deptName"  title="부서를 선택하세요" >
										<c:forEach var="dept"  items="${deptList}" >
											<option ${dept.name == userData.deptName?"selected='selected'":""} value="${dept.deptCode},${dept.name}" >${dept.name}</option>
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
									</select>
									 - <input
									value="${userData.phoneMiddle1}" id="phone2" name="phone2" type="text"
									style="width: 60px;" class="phone none" maxlength="4" /> - <input
									value="${userData.phoneBottom1}" id="phone3" name="phone3" type="text"
									style="width: 60px;" class="phone none" maxlength="4"  /></td>
							</tr>
							<tr>
								<td style="background: #f4f4f4;"><strong>계급</strong></td>
								<td class="tite"><input id="grade" name="grade" value="${userData.grade}"
									title="계급을 입력하세요" type="text" class="none" /></td>
							</tr>
							<tr>
								<td style="background: #f4f4f4;"><strong>이메일</strong></td>
								<td class="tite"><input id="email" name="email"
									value="${userData.email}" title="이메일을 입력하세요" type="text"
									class="none" /></td>
							</tr>
							<tr>
								<td style="background: #f4f4f4;"><strong>등급</strong></td>
								<td class="tite">
									<select style="height: 20px; margin-left: 10px; width: 133px;" id="userClass" name="userClass">
										<option ${userData.userClass == 1  ? ' selected="selected" ' : '' }
										value="1">일반사용자</option>
										<option ${userData.userClass == 2  ? ' selected="selected" ' : '' }
										value="2">경찰서관리자</option>
										<option ${userData.userClass == 3  ? ' selected="selected" ' : '' }
										value="3">지방청관리자</option>
									</select>
								</td>
							</tr>
							<tr class="end">
								<td style="background: #f4f4f4;"><strong>승인여부</strong></td>
								<td class="tite"  id="radio" ><input style="margin-left: 10px"
									${userData.approve ? 'checked="checked"':'' } value="y"
									type="radio" id="approve" name="approve" /><label
									for="approve">승인</label> <input class="none" 
									${userData.approve ? '' : 'checked="checked"' } value="n"
									type="radio" id="refuse" name="approve" /><label
									for="refuse"">미승인</label></td>
								</td>
							</tr>
						</tbody>
					</table>
				</form>
				<div class="btn">
					<a href="#" id="modifyBtn"><img
						src="./images/notice/register_btn.gif" alt="등록" /></a>
						<a href="javascript:history.go(-1);"><img src="./images/notice/cancel_btn.gif" alt="취소" /></a>
				</div>
			</div>
		</div>
	</div>
	<!--  다이얼 로그 -->
	<div id="approveConfirm" title="승인처리 확인">
		<p>
			<span class="ui-icon ui-icon-alert"
				style="float: left; margin: 0 7px 20px 0;"></span>사용자를 승인처리 하시겠습니까?
		</p>
	</div>
	<div id="refuseConfirm" title="미승인처리 확인">
		<p>
			<span class="ui-icon ui-icon-alert"
				style="float: left; margin: 0 7px 20px 0;"></span>사용자를 미승인처리 하시겠습니까?
		</p>
	</div>	
	
	<div id="new_pwd_dlg" title="임시 비밀번호 발급">
		<input size="30" class="inp"  id="new_pwd"  type="text" name="new_pwd"  readonly="readonly" />
		<a href="#" id="copy_btn">클립보드 복사</a>
	</div>	
	<jsp:include page="../modules/footer.jspf" />	
</body>
</html>