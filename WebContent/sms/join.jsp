<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%-- ���  --%>
<jsp:include page="../modules/header.jsp" />

<body>

<script type="text/javascript">
$(function(){
	  $("input").tooltip();
});
</script>
<form action="../JoinAction.ac" method="post" >
	<label>���̵�</label><input value="" name="id" id="id" type="text" title="���̵� �Է��ϼ���.." /><br/>
	<label>���</label><input value="" name="pwd" id="pwd" type="password" ><br/>
	<label>���</label><input value="" name="grade" id="grade" type="text" ><br/>
	<label>�̸�</label><input value="" name="name" id="name" type="text" /><br/>
	<label>�μ�</label><input value="" name="deptname" id="deptname" type="text" ><br/>
	<label>������</label><input value="" name="police_name" id="police_name" type="text" /><br/>
	<label>�޴��ȣ</label><input value="" name="phone" id="phone" type="text" ><br/>
	<label>�̸���</label><input value="" name="email" id="email" type="text" ><br/>	
	<button type="submit">����</button>
</body>
</html>