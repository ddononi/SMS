<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title><%=getServletContext().getInitParameter("title")%></title>
<link rel="shortcut icon" href="../images/base/police.ico" type="image/ico" />
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<link rel="stylesheet" type="text/css" href="../css/style.css"/>
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<script src="../js/cookie.js"></script>
<script type="text/javascript">
<!--
$(function(){
	//	���̵����� üũ �Ǿ� ������.	
    if( $.cookie('id_fill') == 'true' ){
    	$("#id_fill").attr("checked",true);    	
   		$("form #id").val($.cookie('id'));
    }		
	
	// ���̵� üũ Ȯ��
	$("#id_fill").click(function(){
		if(this.checked){
			$.cookie('id_fill' , 'true', { expires : 365 });
		}else{
			$.cookie('id_fill' , 'false', { expires : 365 });
		}
	});		
	//	��й�ȣ���� ����Ű�� �Է��ϸ� �α��� ��ư Ʈ����ó��
    $("form #pwd").keydown(function(event){
       if(event.keyCode == 13){
    	  // $("body").trigger("focusout");
            $("#login_btn").focus().trigger("click");
       }
     });	
	
	//  �Է� ���� ó��	
	$("form").submit(function(){
		
		if ($("form #id").val().length <= 0) {
			alert("���̵� �Է��ϼ���");
			$("form #id").focus();
			return false;
		}
		
		if ($("form #pwd").val().length <= 0) {
			alert("��й�ȣ�� �Է��ϼ���");
			$("form #pwd").focus();
			return false;
		}
		// ���̵� �����̸�
	    if( $.cookie('id_fill') == 'true' ){
			// ���̵� ��⿡ ����
			$.cookie('id', $("form #id").val(),  { expires : 365 }); 
	    }else{
	    	$.removeCookie('id');
	    }			
		
		return true;
	});
});
//-->
</script>
</head>
<body>
	<div id="login_wrapper">
		<!-- login -->
		<h1 class="logo">
			<img src="../images/top/logo.gif" alt="���ûSMS" border="0" /></a>
		</h1>
		<div class="login_box">
			<h3>
				<img src="../images/login/txt_login.gif" />
			</h3>
			<form action="../LoginAction.ac" method="post">
				<fieldset>
					<legend>�α��������Է�</legend>
					<p class="id">
						<label for="id"><img src="../images/login/txt_id.gif"
							alt="���̵�" /></label> <input type="text" id="id" name="id" value="" />
					</p>
					<p class="pw">
						<label for="pwd"><img src="../images/login/txt_ow.gif"
							alt="��й�ȣ" /></label> <input type="password" id="pwd" name="pwd" value="goqkfkrl01" />
					</p>
					<p class="btnLogin">
						<input id="login_btn" type="image" src="../images/login/btn_login.gif" alt="�α���" />
					</p>
					<p class="id_send">
						<input type="checkbox" id="id_fill" name="id_fill" />���̵� ����
					</p>
				</fieldset>
			</form>
			<p class="memberinfo">
				<a href="#" style="visibility: hidden;">
					<img src="../images/login/btn_find.gif" alt="���̵���ã��" />
				</a>

				<a 	href="./join.jsp" >
					<img src="../images/login/btn_join.gif" alt="ȸ������" />
				</a>
			</p>
		</div>
	</div>
	<jsp:include page="../include/footer.jspf" />	
</body>
</html>
