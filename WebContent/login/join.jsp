<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<jsp:include page="../modules/header.jsp" />

<style>
.check_yes img {
	display: none;
}

input {
	margin-left: 10px;
}
</style>
<script type="text/javascript">
<!--
$(document).ready(function() {
	// ��� input�� ���� ȿ�� �ֱ� 
	$("input").tooltip();
	
 	 // ���̵� �Է���Ŀ���� ������� ���̵� �ߺ� üũ
 	var duple_check_id = false; 
    $("#id").focusout(function(){
    	if($("#id").val().length <= 0)
    		return;
    	
        $.ajax({
          url: '../IdCheckAction.ac',
          data: { id : $(this).val() },
          success: function(result){
              if($.trim(result) == 'true'){
                  alert("��밡���� ���̵� �Դϴ�.");
                  duple_check_id = true;
              }else{
            	  alert("�̹� ������� ���̵� �Դϴ�.");
                  duple_check_id = false;
              }
          },
        });  
    });	 
 	 

	// ������ �α����ʵ� ���� ó��
	$("#joinBtn").click(function() {
		
		// �̸� �Է� ����
		if ($("#name").val().length <= 0) {
			alert("�̸��� ��Ȯ�� �Է��ϼ���");
			$("#name").trigger("focus");
			var $imgs = $("#name").parent('td').siblings().eq(1);
			$imgs.children(":last").show();
			$imgs.children(":first").hide();		
			return;
		}else{
			var $imgs = $("#name").parent('td').siblings().eq(1);
			$imgs.children(":first").show();
			$imgs.children(":last").hide();		
		}	
		
		// ���̵� ���Խ�
		var regIdExp = /^[a-z0-9_]{4,20}$/;		
		//�Է������ߴٸ�
		if ($("#id").val().length <= 0) {
			alert("���̵� �Է��ϼ���");
			$("#id").trigger("focus");
			var $imgs = $("#id").parent('td').siblings().eq(1);
			$imgs.children(":last").show();
			$imgs.children(":first").hide();				
			return;
		}

		//  ���̵� �Է� ���� üũ
		if (regIdExp.test($("#id").val()) === false) {
			alert("��Ȯ�� ���̵� �Է��ϼ���");
			$("#id").trigger("focus");
			var $imgs = $("#id").parent('td').siblings().eq(1);
			$imgs.children(":last").show();
			$imgs.children(":first").hide();				
			return;
		}else{
			var $imgs = $("#id").parent('td').siblings().eq(1);
			$imgs.children(":first").show();
			$imgs.children(":last").hide();				
		}
		
		
		// �������� �Է°���
		if ($("#psname").val().length <= 0) {
			alert("���������� �Է��ϼ���");
			$("#psname").trigger("focus");
			var $imgs = $("#psname").parent('td').siblings().eq(1);
			$imgs.children(":last").show();
			$imgs.children(":first").hide();	
			return;
		}else{
			var $imgs = $("#psname").parent('td').siblings().eq(1);
			$imgs.children(":first").show();
			$imgs.children(":last").hide();	
		}
		
		// �μ� �Է� ����
		if ($("#deptName").val().length <= 0) {
			alert("�μ����� ��Ȯ�� �Է��ϼ���");
			$("#deptName").trigger("focus");
			var $imgs = $("#deptName").parent('td').siblings().eq(1);
			$imgs.children(":first").show();
			$imgs.children(":last").hide();				
			return;
		}else{
			var $imgs = $("#deptName").parent('td').siblings().eq(1);
			$imgs.children(":first").show();
			$imgs.children(":last").hide();				
		}	
		
		// ��� �Է� ����
		if ($("#grade").val().length <= 0) {
			alert("��޸��� ��Ȯ�� �Է��ϼ���");
			$("#grade").trigger("focus");
			var $imgs = $("#grade").parent('td').siblings().eq(1);
			$imgs.children(":first").show();
			$imgs.children(":last").hide();				
			return;
		}else{
			var $imgs = $("#grade").parent('td').siblings().eq(1);
			$imgs.children(":first").show();
			$imgs.children(":last").hide();				
		}			
		
		// ��� �Է� ����
		var regEmailExp = /^([0-9a-zA-Z_-]+)@([0-9a-zA-Z_-]+)(\.[0-9a-zA-Z_-]+){1,2}$/;
		if ($("#email").val().length <= 0 || regEmailExp.test($("#email").val()) === false) {
			alert("�̸����� ��Ȯ�� �Է��ϼ���");
			$("#email").trigger("focus");
			var $imgs = $("#email").parent('td').siblings().eq(1);
			$imgs.children(":first").show();
			$imgs.children(":last").hide();				
			return;
		}else{
			var $imgs = $("#email").parent('td').siblings().eq(1);
			$imgs.children(":first").show();
			$imgs.children(":last").hide();				
		}			
		
		//  ��й�ȣ ����
		if ($("#pwd").val().length <= 0) {
			alert("��й�ȣ�� �Է��ϼ���");
			$("#pwd").trigger("focus");
			return;
		} else if (checkPassword($("#pwd").val()) === false) {
			alert("��й�ȣ�� ��Ȯ�� �Է��ϼ���");
			$("#pwd").trigger("focus");
			return;
		}

		// ��й�ȣ Ȯ��ó��
		if( $("#rePwd").val() != $("#rePwd").val()) {
			alert("��й�ȣ�� �������� �ʽ��ϴ�.");
			$("#rePwd").trigger("focus");
			return;
		}
		
		if(duple_check_id == false){
			alert('�ߺ��� ���̵� �Դϴ�.');
			return;
		}
		
		$("form").submit();
	});
	
	//$.myUtil.debug($("#id").val(), 'awef', 'awefawef', 'awefawef');

	// ��й�ȣâ�� ����Ű�� ������ �α��ι�ư Ʈ����
	/*
	$("#user_pwd").keydown(function(event){
	   if(event.keyCode == ENTER_KEY){
	       $("#login_btn").click();
	   }
	});
	 */

	// ���̵� �ʵ忡 ��Ŀ��
	//$("#id").focus();
});
//-->
</script>
<body>
	<div id="join_wrapper">
		<!-- join -->
		<h1 class="logo">
			<img src="../images/top/logo.gif" alt="����ûSMS" border="0" /></a>
		</h1>
		<div class="join_box">
			<h3>
				<img src="../images/join/txt_join.gif" alt="ȸ������" />
			</h3>
			<form action="../JoinAction.ac" method="post">
				<fieldset>
					<legend>ȸ������ �����Է�</legend>
					<table width="547" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<th width="146">�̸�</th>
							<td class="check_yes"><img
								src="../images/join/check_yes.gif" /> <img
								src="../images/join/check_no.gif" /></td>
							<td><input type="text" id="name" name="name"
								title="��Ȯ�� �̸��� �Է��ϼ���" /></td>
						</tr>
						<tr>
							<th width="146">���̵�</th>
							<td class="check_yes"><img
								src="../images/join/check_yes.gif" /> <img
								src="../images/join/check_no.gif" /></td>
							<td><input type="text" id="id" name="id"
								title="�������ڷθ� �Է��ϼ���" /></td>
						</tr>
						<tr>
							<th width="146">������</th>
							<td class="check_yes"><img
								src="../images/join/check_yes.gif" /> <img
								src="../images/join/check_no.gif" /></td>
							<td><input type="text" id="psname" name="psname" title="��Ȯ�� ���������� �Է��ϼ���" /></td>
						</tr>
						<tr>
							<th width="146">�μ�</th>
							<td class="check_yes"><img
								src="../images/join/check_yes.gif" /> <img
								src="../images/join/check_no.gif" /></td>
							<td><input type="text" id="deptName" name="deptName" title="��Ȯ�� �μ����� �Է��ϼ���" /></td>
						</tr>
						<tr>
							<th width="146">�޴��ȣ</th>
							<td class="check_yes"><img
								src="../images/join/check_yes.gif" /> <img
								src="../images/join/check_no.gif" /></td>
							<td>
								<input type="text"  class="phone" id="phone1" name="phone1" /> - <input type="text"
								class="phone" id="phone2" name="phone2"  /> - <input class="phone" type="text" id="phone3" name="phone3"  />
							</td>
						</tr>
						<tr>
							<th width="146">���</th>
							<td class="check_yes"><img
								src="../images/join/check_yes.gif" /> <img
								src="../images/join/check_no.gif" /></td>
							<td><input type="text" id="grade" name="grade" title="����� �Է��ϼ���" /></td>
						</tr>
						<tr>
							<th width="146">�̸���</th>
							<td class="check_yes"><img
								src="../images/join/check_yes.gif" /> <img
								src="../images/join/check_no.gif" /></td>
							<td><input type="text" id="email" name="email" title="�̸����� �Է��ϼ���" /></td>
						</tr>
						<tr>
							<th width="146">��й�ȣ</th>
							<td class="check_yes"><img
								src="../images/join/check_yes.gif" /> <img
								src="../images/join/check_no.gif" /></td>
							<td><input type="password" id="pwd" name="pwd"
								title="�ּ� 6���� �̻� �Է��ϼ���" /></td>
						</tr>
						<tr>
							<th width="146">��й�ȣ Ȯ��</th>
							<td class="check_yes"><img
								src="../images/join/check_yes.gif" /> <img
								src="../images/join/check_no.gif" /></td>
							<td><input type="password" id="rePwd" name="rePwd"
								title="������ ��й�ȣ�� �Է��ϼ���" /></td>
						</tr>
					</table>
				</fieldset>
			</form>
			<p class="join_info">
				<a href="#" id="joinBtn"><img src="../images/join/btn_join.gif"
					alt="ȸ������" /></a><a href=""><img
					src="../images/join/btn_cancel.gif" alt="���" /></a>
			</p>
		</div>
	</div>
	<div id="footer_wrap">
		<div class="footer">Ǫ�Ϳ���</div>
	</div>

</body>


</html>
