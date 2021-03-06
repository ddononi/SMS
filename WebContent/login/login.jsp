<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title><%=getServletContext().getInitParameter("title")%></title>
<link rel="shortcut icon" href="../images/base/police.ico" type="image/ico" />
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<link rel="stylesheet" type="text/css" href="../css/style.css"/>
<script type="text/javascript" src="../js/jquery-1.8.3.min.js"></script>
<script src="../js/cookie.js"></script>
<script type="text/javascript">
<!--
$(function(){
	//	아이디기억이 체크 되어 있으면.	
    if( $.cookie('id_fill') == 'true' ){
    	$("#id_fill").attr("checked",true);    	
   		$("form #id").val($.cookie('id'));
    }		
	
	// 아이디 체크 확인
	$("#id_fill").click(function(){
		if(this.checked){
			$.cookie('id_fill' , 'true', { expires : 365 });
		}else{
			$.cookie('id_fill' , 'false', { expires : 365 });
		}
	});		
	//	비밀번호에서 엔터키를 입력하면 로그인 버튼 트리거처리
    $("form #pwd").keydown(function(event){
       if(event.keyCode == 13){
    	  // $("body").trigger("focusout");
            $("#login_btn").focus().trigger("click");
       }
     });	
	
	$("#pwd").keypress(function(event){
	    if ( isCapslock(event) ){
	        $("#notice_caps").css("visibility", "visible");
	    }else{
	    	$("#notice_caps").css("visibility", "hidden");
	    }
	});
	
	//  입력 검증 처리	
	$("form").submit(function(){
		
		if ($("form #id").val().length <= 0) {
			alert("아이디를 입력하세요");
			$("form #id").focus();
			return false;
		}
		
		if ($("form #pwd").val().length <= 0) {
			alert("비밀번호를 입력하세요");
			$("form #pwd").focus();
			return false;
		}
		// 아이디 저장이면
	    if( $.cookie('id_fill') == 'true' ){
			// 아이디값 쿠기에 저장
			$.cookie('id', $("form #id").val(),  { expires : 365 }); 
	    }else{
	    	$.removeCookie('id');
	    }			
		
		return true;
	});
	
	function isCapslock(e){

	    e = (e) ? e : window.event;

	    var charCode = false;
	    if (e.which) {
	        charCode = e.which;
	    } else if (e.keyCode) {
	        charCode = e.keyCode;
	    }

	    var shifton = false;
	    if (e.shiftKey) {
	        shifton = e.shiftKey;
	    } else if (e.modifiers) {
	        shifton = !!(e.modifiers & 4);
	    }

	    if (charCode >= 97 && charCode <= 122 && shifton) {
	        return true;
	    }

	    if (charCode >= 65 && charCode <= 90 && !shifton) {
	        return true;
	    }

	    return false;

	}
	
});

//-->
</script>
</head>
<body>
	<div id="login_wrapper">
		<!-- login -->
		<h1 class="logo">
			<img src="../images/top/logo.gif" alt="강원청SMS" border="0" /></a>
		</h1>
		<div class="login_box">
			<h3>
				<img src="../images/login/txt_login.gif" />
			</h3>
			<form action="../LoginAction.ac" method="post">
				<fieldset>
					<legend>로그인정보입력</legend>
					<p class="id">
						<label for="id"><img src="../images/login/txt_id.gif"
							alt="아이디" /></label> <input type="text" id="id" name="id" value="" />
					</p>
					<p class="pw">
						<label for="pwd"><img src="../images/login/txt_ow.gif"
							alt="비밀번호" /></label> <input title="" type="password" id="pwd" name="pwd" value="" />
					</p>
					<p class="btnLogin">
						<input id="login_btn" type="image" src="../images/login/btn_login.gif" alt="로그인" />
						<p id="notice_caps" style="text-align:center; margin-top:3px; color:RED; font-weight:bold; visibility: hidden;">Caps Lock이 켜져 있습니다.</p> 
					</p>
					<p class="id_send" style="margin-top: -3px;" >
						<input  type="checkbox" id="id_fill" name="id_fill" />아이디 저장
					</p>
				</fieldset>
			</form>
			<p class="memberinfo">
				<a href="#" style="visibility: hidden;">
					<img src="../images/login/btn_find.gif" alt="아이디비번찾기" />
				</a>

				<a 	href="./join.jsp" >
					<img src="../images/login/btn_join.gif" alt="회원가입" />
				</a>
			</p>
		</div>
	</div>
	<jsp:include page="../include/footer.jspf" />	
</body>
</html>
