<%@ page contentType="text/html; charset=euc-kr" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>자동로그아웃 안내</title>
<meta http-equiv="content-type" content="text/html; charset=euc-kr" />
<style type="text/css">
body{background-color: #f4f9ff; }
img{ border:none;}
p { margin: 20px;}
</style>
<link rel="shortcut icon" href="../images/base/police.ico" type="image/ico" />    
<link rel="stylesheet" type="text/css" href="../css/plug-in/fileTree/default/easyui.css" />
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="../js/plug-in/fileTree/jquery.easyui.min.js"></script>
<script>
$(function(){
	$('#win').window({
	    collapsible:false,
	    minimizable:false,
	    maximizable:false,
	    closable:false,
	    buttons:[{
	        text:'확인',
	        iconCls:'icon-ok',
	        handler:function(){
	            alert('ok');
	        }
	    }]	    
	});
});
</script>
</head>
<body>

<div id="win" class="easyui-window" title="자동로그아웃 안내" style="width:330px;height:180px;">
		<p>사용자의 안전한 정보보호를 위해  로그인 후 <br/>
		       10분 동안 서비스 이용이 없어 자동 로그아웃 되었습니다.
   		       확인 버튼을 누르시면 초기 로그인 화면으로 이동합니다.
        </p>
        <div style="padding:5px;text-align:center;">
			<a href="../login/login.jsp"><img src="../images/login/bt_confirm.gif" alt="확인" /></a>
        </div>
</div>
</body>
</html>
<%session.invalidate();%>