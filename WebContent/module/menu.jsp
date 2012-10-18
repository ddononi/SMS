<%@ page contentType="text/html; charset=euc-kr" pageEncoding="EUC-KR"%>
<%@ page import="java.util.*"%>
<link type="text/css" href="./css/sms.css" rel="stylesheet" />
<script type="text/javascript" src="./js/plug-in/jquery.simplemodal.js"></script>
<!-- ���̾�α�  �÷�����-->
<style type="text/css">
#subnav1,#subnav2,#subnav3,#subnav4,#subnav5,#subnav6,#subnav7 {
	display: none;
}

#subnav2 {
	margin-left: 80px;
}

#subnav3 {
	margin-left: 155px;
}

#subnav4 {
	margin-left: 315px;
}

#subnav5 {
	margin-left: 390px;
}

#subnav6 {
	margin-left: 370px;
}

#deptname {
	margin-left: 5px;
	font-size: 90%;
	color: #FF8040;
}

#f_id,#freeSendCount span {
	color: #4481d6;
	font-weight: bold;
}

#freeSendCount {
	font-size: 75%;
}

#freeSendCount span {
	font-size: 16px;
	margin-left: 5px;
	margin-right: 5px;
}

.sms_list a {
	color: #4481d6;
}

.sms_list a {
	border-bottom: none;
}

#noticeBox {
	display: none;
	height: 300px;
	width: 575px;
	padding: 10px;
	background: #fff;
	border: 3px solid #B0CDEA;
}

#quickLogout {
	position: relative;
	right: 0;
}

#f_id {
	width: 110px;
}

.dear {
	color: #666;
}

.gonji_date {
	margin-left: 8px;
	font-size: 9px;
	color: #666;
}

.free {
	border: none;
}
</style>
<!-- 
	��� �޴�
 -->
<div id="header" style="width:1024px;">
	<h1>
		<a href="../sms_send/hp.jsp">
			<!--<img src="../images/SMS.jpg" alt="logo" />-->
			<img src="./images/main-title.gif" alt="���ûSMS" />
		</a>
	</h1>
	<!-- <h2><img src="../images/login_top.gif" alt="SMS" /></h2> -->
	<ul id="nav">
		<li><a href="../sms_send/hp.jsp" class="parent" id="smstransMenu">SMS
				����</a></li>
		<li><a href="../sms_send/message.jsp" class="parent">�޽�������</a></li>
		<li><a href="../Address/Address_mgr.jsp">�ּҷ�</a></li>

		<li><a href="../enrollUser/modifyUser.jsp">���������</a></li>
		<li><a href="../QnA/index_qna.jsp">�����ϱ�</a></li>
		<c:set var="f_class" value="" />
		<c:if test="${f_class=='3' || f_class=='2'}">
			<li id="adminMenu"><a href="../manageUser/ListUser.jsp">�����ڸ��</a></li>
		</c:if>
		<li><a href="../logout.jsp"><span>�α׾ƿ�</span></a></li>
	</ul>
	<ul id="subnav">
		<span id="subnav1">
			<li><a href="../sms_send/hp.jsp"><span>���ں�����</span></a></li>
			<li><a href="../sms_send/send_info.jsp"><span>���ฮ��Ʈ</span></a></li>
			<li><a href="../sms_send/result_info.jsp"><span>���۰��</span></a></li>
		</span>
		<span id="subnav2">
			<li><a href="../sms_send/message.jsp"><span>���Ǹ޽���</span></a></li>
			<li><a href="../sms_send/message1.jsp"><span>����޽���</span></a></li>
		</span>
		<span id="subnav3">

			<li><a href="../Address/Address_mgr.jsp"><span>�׷���</span></a></li> <!-- <li><a href="../Address/Addr_input.jsp"><span>�׷�װ����ּҷϵ��</span></a></li> -->
			<li><a href="../Address/Addr_excel_input.jsp"><span>����(����)���</span></a></li>
			<li><a href="../Address/search_user.jsp"><span>�ּҷϰ˻�</span></a></li>

		</span>
		<span id="subnav4">
			<li><a href="../enrollUser/modifyUser.jsp"><span>��������</span></a></li>
		</span>
		<span id="subnav5">
			<li><a href="../QnA/index_qna.jsp"><span>���Ǹ��</span></a></li>
			<li><a href="../QnA/index_qna_write.jsp"><span>�����ϱ�</span></a></li>
		</span>
		<span id="subnav6">

			<li><a href="../Statistic/Send_statistic.jsp"><span>��
						��</span></a></li>
			<li><a href="../controlDept/controlDept.jsp"><span>�μ�����</span></a></li>
			<c:if test="${f_class=='3'}">
				<!--   ����û �����ڸ� -->
				<li><a href="../manageUser/restrict.jsp"><span>�׷캰���Ѽ���</span></a></li>
			</c:if>
			<li><a href="../manageUser/spamLetter.jsp"><span>��Ģ���</span></a></li>
			<!--  <li><a href="../manageUser/enrollUser.jsp"><span>ȸ�����</span></a></li> -->
			<c:if test="${f_class=='3'}">
				<!--   ����û �����ڸ� -->
				<li><a href="../manageUser/smsLog.jsp"><span>SMS�α�</span></a></li>
			</c:if>
		</span>
		<span id="subnav7">
			<li><a href="../logout.jsp"><span>�α׾ƿ�</span></a></li>
		</span>
	</ul>
</div>
<div id="sideMenu" style="width:300px; margin-left: 50px;">
	<div id="sidebar">
		<h2>�α��� ����</h2>
		<p id="idInfo">
		<div>
			<span id="f_id"><span class="dear"> ��</span></span> <span
				id="quickLogout"><a href="../logout.jsp"><img
					src="./images/logout.gif" alt="�α׾ƿ�" /></a></span>
		</div>
		</p>
		<p>�ູ�� �Ϸ� �Ǽ���!</p>
		<p>
			<span id="deptname">()</span>
		</p>
		<p id="freeSendCount">
			�̴� ���� �Ǽ� :<span></span>��
		</p>
		<p id="currentTime"></p>
	</div>
	<div class="leftmenu">
		<dl class="sms_list">
			<dt>
				<div class="send">
					<a href="../sms_send/hp.jsp"><strong>���ں�����</strong></a>
					<!--(<em>6</em>)-->
				</div>
			</dt>
			<dt>
				<div class="save">
					<a href="../sms_send/send_info.jsp"><strong>���ฮ��Ʈ</strong></a>
				</div>
			</dt>
			<dt>
				<div class="save">
					<a href="../sms_send/result_info.jsp"><strong>���۰��</strong></a>
					<!-- (<em>6</em>) -->
				</div>
			</dt>
			<dt>
				<div>
					<a href="../sms_send/message.jsp"><strong>���Ǹ޽���</strong></a>
				</div>
			</dt>
			<dt>
				<div>
					<a href="../Address/Address_mgr.jsp"><strong>�ּҷ�</strong></a>
				</div>
			</dt>
		</dl>
		<div class="bgbottom"></div>
	</div>
	<div class="motli">
		<div class="mtit">
			<h2>
				��������<img src="./images/ico_marr2.gif" />
			</h2>
		</div>
		<ul>

		</ul>
	</div>
</div>
<!--  /���̵� �޴� -->

<!-- chart  -->
<div id="noticeBox"></div>
<!-- /cahrt -->
<script type="text/javascript">
	var selectedSub = null; // ���� ���õ� �޴�
	var sub = []; // �޴� ��ü �迭
	var adminFlag = false;
	// �����ڸ�� ������ ���� ���� �޴� ��ġ ����
	var options = { // ���̾�α� �ɼ�
		close : true,
		closeHTML : "�ݱ�",
		closeClass : "close",
		onClose : function(dialog) {
			$.modal.close(); // Must be Call
			$("#noticeBox").hide(); // ��Ʈ�� ������ �ϴܿ� ��Ʈ�� ���̱� ������ ������
			$("#noticeBox").html(""); // ��Ʈ�� ��� 
		}
	}
	$(function() {
		if ($("#nav").children("li").is("#adminMenu") === true) {
			$("#subnav7").css("margin-left", "625px");
			adminFlag = true;
		} else {
			$("#subnav7").css("margin-left", "510px");
		}
		// ����û �����ڰ� �ƴҰ��� li������ ��ȭ�� subnav6�� ��ġ ������
		if ($("#subnav6 li:last a").is("[href='../manageUser/smsLog.jsp']") != true) {
			$("#subnav6").css("margin-left", "440px");
		}

		/*
		 *	���� �޴� li ��  ��ȸ�ϸ鼭
		 *	�Ϲ� ����� �޴��� ������ �� �޴��� �����ϰ� ���콺 ������ ȿ�� ����
		 *	li 3��°�� �ּҷϵǾ� ���� ������  �����ڷν� �Ϲݻ���ڿʹ� �ٸ���
		 *  ���еǾ����� �޴� �������� margin-left�� ������ 70��ŭ �� ����� 
		 */

		$("#nav li").each(
				function(index) {
					if (index == 5) {
						if (!adminFlag) {
							index = 6;
						}
					}
					if ($("#nav li").eq(2).children("a").text() != "�ּҷ�") {
						if (index < 2) {
							sub[index] = "#subnav".concat(index + 1);
						} else {
							sub[index] = "#subnav".concat(index + 2);
							var moveToleft = (parseInt($(sub[index]).css(
									"margin-left")) - 80).toString().concat(
									"px");
							//alert(  moveToleft );
							$(sub[index]).css("margin-left", moveToleft);
						}
					} else {
						sub[index] = "#subnav".concat(index + 1);
					}
					$(this).mouseover(function() {
						$(selectedSub).hide();
						$(sub[index]).show();
						selectedSub = sub[index];
					});
				});

		$(".motli a").click(function() {
			var arg = $(this).attr("alt");
			$.get("../sms_send/ajax/main_board_view.jsp", {
				num : arg
			}, function(html) {
				$("#noticeBox").html(html);
				$("#noticeBox").modal(options);
			});
			return false;
		});
		/*	
		 * �������� ��� subnav3�� li ������ �ϳ� �̱⶧����
		 * �ּҷϰ� ���� �޴��� �ּҸ� �����ڿ����� �ٲ���
		 */
		if ($("#subnav3 li").size() == 1) {
			$("ul#nav li:eq(2) a").attr("href", "../Address/Addr_adm.jsp");
			$(".sms_list dt:last div a")
					.attr("href", "../Address/Addr_adm.jsp");
		}

		/*
		$(".date").each(function(){ // �������� year�� �߶����
			$(this).text( "[".concat( $(this).text().slice(5)).concat("]") );
		});*/
	});
</script>