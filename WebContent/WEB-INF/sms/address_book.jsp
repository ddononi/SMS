<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.util.*" %>	
<%@ page import="kr.go.police.address.*"  %>		
<%@ page import="kr.go.police.sms.Group"  %>			
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	// requset �� ���� �׷츮��Ʈ�� �����´�.
	int count =0;
	List<Group> groupList = (List<Group>)request.getAttribute("groups");
%>	
<c:set var="list"  value ="<%=groupList %>" />
<c:set var="size"  value ="<%=groupList.size() %>" />
<?xml version="1.0" encoding="EUC-KR" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR" />
<title>�ּҷ�</title>
<link rel="stylesheet" type="text/css" href="./css/plug-in/fileTree/icon.css" />
<link rel="stylesheet" type="text/css" href="./css/plug-in/fileTree/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="./css/formTable.css" />
<script type="text/javascript" src="./js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="./js/plug-in/jquery.tablesorter.min.js"></script>
<script type="text/javascript" src="./js/plug-in/fileTree/jquery.easyui.min.js"></script>
<script type="text/javascript" src="./js/plug-in/jquery-tools-1.2.4.min.js"></script>
<style>
img{vertical-align:middle;margin:0;padding:0; border: none;}
.myTabs{list-style:none;height:30px;border-bottom:1px solid #666;margin:0 !important;padding:0;}
.myTabs li{float:left;font-weight:500;text-indent:0;list-style-image:none !important;margin:0 !important;padding:0;}
.myTabs a{background:url(./images/sms/tabs.png) no-repeat -652px 0;font-size:11px;display:block;height:30px;line-height:30px;width:111px;text-align:center;text-decoration:none;color:#000;position:relative;top:1px;margin:0;padding:0;}
.myTabs a:active{outline:none;}
.myTabs a:hover{background-position:-652px -31px;color:#fff;}
.myTabs .current,.myTabs .current:hover,.myTabs li.current a{background-position:-652px -62px;cursor:default !important;color:#000 !important;}
.myTabs .w1{background-position:-519px 0;width:134px;}
.myTabs .w1:hover{background-position:-519px -31px;}
.myTabs .w1.current{background-position:-519px -62px;}
.myTabs .w2{background-position:-366px 0;width:154px;}
.myTabs .w2:hover{background-position:-366px -31px;}
.myTabs .w2.current{background-position:-366px -62px;}
.myTabs .w3{background-position:-193px 0;width:174px;}
.myTabs .w3:hover{background-position:-193px -31px;}
.myTabs .w3.current{background-position:-193px -62px;}
.myTabs .w4{background-position:0 0;width:194px;}
.myTabs .w4:hover{background-position:0 -31px;}
.myTabs .w4.current{background-position:0 -62px;}
.panes .pane{display:none;}
iframe{width:100%;height:100%;}
.scrollable{overflow:auto;color:#333;background-color:#f7f7f7;border:1px solid #ccc;font-size:16px;scrollbar-face-color:#fff;scrollbar-shadow-color:#BEBEBE;scrollbar-3dlight-color:#BEBEBE;scrollbar-arrow-color:#666;scrollbar-darkshadow-color:#FFF;scrollbar-base-color:#F2EDCE;scrollbar-track-color:#F7F7F7;}
#header{width:100%;background:#f7f7f7;height:36px;border-bottom:1px solid #ebebeb;font-size:17px;color:#222;font-weight:700;padding:11px 0 0 11px;}
#header img{margin:0 10px;}
#sreachBar{width:100%;height:30px;text-align:right;border-bottom:1px solid #ccc;margin:10px 0;}
#sreachBar label{font-size:12px;color:#333;font-weight:700;margin-right:10px;}
#sreachBar select{color:#666;}
#sreachBar img{margin-left:5px;margin-right:15px;cursor:pointer;_margin-bottom:6px;}
#searchedList{width:66%;height:200px;float:left;border:1px solid #ccc;overflow:hidden;margin:10px 0 10px 10px;}
#selectableList{_margin-left:5px;float:left;width:30%;height:200px;margin:10px 0 10px 10px;}
#inout{clear:both;text-align:center;}
#inout img{margin-left:5px;cursor:pointer;}
#selectedList{background-color:#fff;height:200px;margin:10px;}
#tree{padding:20px;}
#buttons{text-align:center;margin-top:15px;}
.ads_tbl{width:100%;text-align:center;}
.ads_tbl thead th{background-color:#f7f7f7;border-right:1px solid #ccc;border-bottom:1px solid #ccc;color:#333;font-size:12px;padding:3px 0;}
.ads_tbl tbody tr{border-top:1px solid red;}
.ads_tbl tbody td{font-size:12px;padding:4px 0;}
#main{margin:0;padding:0;}
#loadingBar{background:url(../images/sms/bg_mini300.gif) no-repeat;width:300px;height:100px;position:absolute;left:50%;top:50%;margin-left:-150px;margin-top:-50px;}
#loadingBar .minile{float:left;padding:33px 0 0 38px;}
#loadingBar .minire{float:left;text-align:left;color:#666;padding:40px 0 0 13px;}
#loadingBar .minire strong{color:#f66828;font-size:14px;line-height:22px;}
.odd{background-color:#f3f3f3;border:none;}
.myList{border-bottom:1px solid #ccc;}
.myList tbody td{cursor:pointer;padding-top:10px;}
.checkedTr{background-color:#e9e9e9;}
#infoBox{margin-top:-18px;color:#333;text-align:right;font-size:12px;font-weight:700;margin-right:15px;}
#infoBox strong{font-size:14px;color:#fd6a24;margin-right:3px;}
#addressList{background-color:#fff;}
#selectedTbl a:LINK,a:VISITED{color:#333;text-decoration:none;}
#selectedTbl a:HOVER{color:#00b5f5;}
#allBtn{border: none;}
</style>
<script> 
jQuery.js = {
	selectAll : function(check){
		var flag = check;
		$("input:checkbox").each(function(){
			this.checked = flag;
		});
	},
	doCheck : function(that){
		that.each(function(){
			this.checked = !this.checked;
		});
		$.js.highlight( that );
	},	
	highlight : function(that){
		//var that = $(this);
		var count = 0;
		
		if( that.is(':checked') ){	// üũ�� ��常
		 	that.parents("tr").addClass("checkedTr");
		 	count++;
		}else{
			that.parents("tr").removeClass("checkedTr");
			count--;
		}
		$("#checkedNum").text( parseInt($("#checkedNum").text())+count);		
	},
	/*
	 *	 �ߺ� ��ȭ��ȣ ���߰� ����
 	 */
	checkDuplicate : function(phone){
		var count = selectedPhoneArr.length;
		//alert( count );
		while(count){
			// ���� ��ȭ��ȣ�� ������..
			if( selectedPhoneArr[--count] == phone ){
				return false;
			}
		}
		return true;
	},

	throwPhoneNum : function(phone){
		 if( !confirm("����Ʈ�� �߰��Ͻðڽ��ϱ�?") ){
			return false;
		 }
		 try{
			 if( $("input:checked").size() == 0){
				 throw "���õ� �ּҷ��� �����ϴ�.";
			 }	
		 }catch(e){
			 alert(e);
			 return false;
		 }
		var index = 0;	// �θ� �������� ��ȭ��ȣ ����Ʈ �ε���			
		var addedCount = 0;	// �߰��� �ο�
		var name= null;	// üũ�� �̸� 
		var phone = null;	// üũ�� ��ȭ��ȣ
		var errMassage = "";	// ���� �޽��� 
		var phoneArr = [];	// �ߺ��˻��� �θ� ��ȭ��ȣ ����Ʈ �迭
		var dupleFlag;	// �ߺ�üũ	

		// ��ȭ��ȣ ����Ʈ �迭 �����
	    window.parent.opener.$(".rt :input").each(function(){
	    	if( $(this).val() != "" ){	// �� ���� �ƴϸ�
	    		phoneArr.push( $(this).val() );
			}
		});

		$("#selectedTbl input:checked").each(function(i){
			// ��ȭ��ȣ ����Ʈ ������ �˾ƿ���
			var  wListSize = window.parent.opener.$("#pone_list ul").size();
			dupleFlag = false;	// �ߺ�üũ			
			name =  $(this).parents("tr").children("td:eq(1)").text();	// �̸� ������
			phone =  $(this).parents("tr").children("td:eq(2)").text(); // ��ȭ��ȣ ������
			// �θ� �������� ��ȭ��ȣ ����Ʈ �׸��� üũ�Ͽ� �ߺ��� ��ȭ��ȣ�� ������� �˷���
			$.each( phoneArr, function(idx, value) { 
				if( phone == value){
					errMassage += "HP :" +  phone + "\n";
					dupleFlag = true;
				}
			});	
			index = index + 1;	
			if( dupleFlag != true ){	// �ߺ��� �ȵ� ��ȭ��ȣ �ϰ�츸
				if( index <= wListSize ){
					for(var i=index; i<=wListSize; i++){
						// ������� ��� �ش� ��ȣ�� index �� �־���
						if( window.parent.opener.$(".rt :input:eq(" + i + ")").val() == "" ){
							index = i + 1;	
							break;
						}
					}
				}else{	// ��ȭ��ȣ ����Ʈ ������� Ŭ ��� ���� html ������ ��ȣ�� �־���
					
					var html = "<ul><li><span>" + index + "</span><input disabled='disabled' readonly='readonly' name='recvName"+ index + "' id='recvName"+ index + "' type=text' class='inp'>"
					+ " <li><input name='recvPhone" + index + "' id='recvPhone" + index + "' type='Text' class='inp hyphen rt' style=\"width:150px;\"></li>"
					+ " <li class='bt'><img src='./images/sms/btn_close2.gif' alt='�ݱ�'></li></ul>";
 					window.parent.opener.$("#pone_list ul:last").after(html);						
				}
				window.parent.opener.$("#recvName" + index).val( name );
				window.parent.opener.$("#recvPhone" + index).val( phone );
				var $lastInput = $("#recvPhone" + index);
				$lastInput.focus();					
				addedCount++;					
			}
		});

		// �ߺ��� ��ȭ��ȣ ���
		if( errMassage != "" ){
			alert("�ߺ��� ��ȭ��ȣ�� ���Ե��� �ʽ��ϴ�\n" + errMassage);
		}
		
		// �߰� �ο� ���
		/*
		if( addedCount > 0 ){
			alert("�޴»���� " + addedCount + "���� �߰��Ͽ����ϴ�...");
		} 
		*/
		window.close();
	},

	/*
	 *	�ּҷ� �˻�
	 */
	search : function(){
		 var slt = $("#select").val();
		 var input = $("#searchInput").val();		 
		 
		 var telReg = /^([0-9]{3,8})$/; // ��ȭ��ȣ
		 var groupNameReg = /^[\uac00-\ud7a3]{1,20}$/; //�ѱ�

		 if( (slt == "f_enuser")  && !groupNameReg.test(input) ){
				alert("�ѱ۸� �Է��ϼ���");
				return false;
		 }
		 if( (slt == "f_enphone")  && !telReg.test(input) ){
				alert("���ڸ� �Է��ϼ���.");
				return false;
		 }	
		 if( (slt == "f_deptcode")  && !groupNameReg.test(input) ){
				alert("�ѱ۸�  �Է��ϼ���");
				return false;
		 }			 


		 var url = "addressList.jsp?search=" + slt;
 		 $("#f_search").val( input );		 
		 $("#frm").attr("action", url);
	 	 $("#frm").submit();
	}
	
};

var selectedNodeId = null;
var selectedPhoneArr = [];	// ��ȭ��ȣ�� ���� �迭
var checkedCount = 0;
$(function() {

	//var obj =  window.parent.opener.$(".lt :input").val();
	// ��ȭ��ȣ ����Ʈ�� �迭�� ������ �� �׸��� ����
	//arr = $.grep( $.makeArray(obj), function(a) return a != "" );
		
	// setup ul.tabs to work as tabs for each div directly under div.panes
	$("ul.myTabs").tabs("#selectableList > div");

	/*
	 *	 ù��° �μ��׸񿡼��� Ʈ���� �ε��� ��
	 * 	  �ݹ����� �̺�Ʈ���� �ɾ��ش�.
	 */

// 	$("#tree").load('../controlDept/ajax/DeptTreeList.jsp', function(){
// 		$('#tt').tree({
// 			/*checkbox: true,*/
// 			onClick:function(node){
// 				//$(this).tree('toggle', node.target);
// 				//$("#treeBox table input").val(node.text);	
// 				//$(this).tree('pop');
// 			},
// 			onLoadSuccess : function(){
// 				$(this).tree('collapseAll');
// 				$("#main").show();
// 				$("#loadingBar").hide();
// 			},
// 			/*
// 			 *	Ʈ�� ���ý� �ش� �ڵ带 �޾� iframe�� �ش� �μ��� ������
// 			 */
// 			onSelect : function(){
// 				var code = $(this).tree('getSelected').id;	// get deptcode 
// 				var selectedName = $(this).tree('getSelected').text;

// 				selectedNodeId = $(this).tree('getSelected').id;

// 				if( parseInt(code) < 9999 ){	// �������ڵ��
// 				//	$("#f_pscode").val(code);
// 					$("#f_psname").val( selectedName );
// 				}else{							// �μ��ڵ��
// 				//	$("#f_deptcode").val(code);
// 					var target = $(this).tree('getSelected').target;
// 					$("#f_psname").val( $(this).tree('getParent', target).text );	// ��������
// 					$("#f_dname").val( selectedName );
// 				}
// 				$("#f_deptcode").val(code);
				
// 				$("#frm").attr("action", "addressList.jsp");
// 				$("#frm").submit();

// 			}					
// 		});
// 	});	// tree - load


	/*
	 * ���� �׷� �ε� �� callback �Լ��� �̺�Ʈ ó��
	 */
	$("#addressList table").find("tbody tr").each(function(index){
			if( index % 2 == 0 ){
				// odd background
				$(this).css("background-color", "#fff");
			}else{
				$(this).css("background-color", "#f1f1f1");
			}
		}).find('td:even').click(function(){
			// �׷���� �����ϸ�
			var index = $.trim($(this).children("span").text());
			// ������ �׷� �ּҷ� ����Ʈ�� ���ü� �ֵ���
			// iframe�� action ������ ����ó��
			$("#frm").attr("action", "./AddressListTableAction.ad?groupIndex=" +index);
			$("#frm").submit();			
		});
		

	/*
	 *	��ü ���� �� ���� 
	 */
	$("#allBtn").click(function(){	// ��ü ���� �� ����
		var all = "./images/sms/btn_tall.gif";
		var clear = "./images/sms/btn_choice_off.gif";
		var src = $(this).children("img").attr("src");
		if( src == clear ){
			$(this).children("img").attr("src", all);
			$(".ads_tbl tbody tr").removeClass("checkedTr");
			$.js.selectAll(false);
			
		}else{
			$(this).children("img").attr("src", clear);
			$(".ads_tbl tbody tr").addClass("checkedTr");
			$.js.selectAll(true);
			
		}
		$("#checkedNum").text( $("input:checked").size() );
	});

	/*
	 *	���̺� �� ���ڿ� ���콺 ȿ��
	 */
	$("#ads_tbl tbody td").live("mouseover", function(){
		$(this).siblings().andSelf().css("color", "#00b5f5");
	})
	.live("mouseout", function(){
		$(this).siblings().andSelf().css("color", "#333");
	 }).live("click", function(event){
		 // event target�� input �̸� �׳� �ѱ�
		 if( $(event.target).is("input") ){
			 return;
		 }
		 var that = $(this).parent("tr").find(":first-child");
		 $.js.doCheck( that );
	 });

	 $("#addBtn").click(function(){
		 var table = $("#selectedTbl tbody:last");
		// var obj = $("#selectedTbl tbody tr td:eq(2)").text();
		 var html = null;
		 var count = 0;
		 var phone;
		 var tempArr = new Array();	// ���õ� ��ȭ��ȣ�� �߰��� �ӽ� �迭
		 $("#deptFrame").contents().find("input:checked")
		 	.parents("tr").each(function(){
		 		phone = $(this).children("td:eq(2)").text();
			 	if( $.js.checkDuplicate(phone) == true  ){
					html = ((count++) % 2 == 0)?"<tr class='odd'>":"<tr>";
					html += "<td><input type='checkbox' value=''/></td>"
						 /* �̸� */
						 + "<td>"+ $(this).children("td:eq(1)").text() + "</td>"
						 /* �޴��ȣ */
						 + "<td>"+ phone + "</td>"
						 /* ��å */
						 + "<td>"+ $(this).children("td:eq(3)").text() + "</td>"
						 /* ����ȣ */
						 + "<td>"+ $(this).children("td:eq(4)").text() + "</td>"
						 + "</tr>";
					table.append(html);
					tempArr.push( phone );	// �˻��� �迭�� �ֱ�
				}
		 });
		 if( count != 0 ){
			 // �迭 ����
		 	 selectedPhoneArr = $.merge( selectedPhoneArr, tempArr );
		 }
		 // �߰��� �ο� 
		 $("#addNum").text( parseInt($("#addNum").text() ) + parseInt(count) );
	 });
	

	 /*
	  * ���� ��ư
	 */
	 $("#removeBtn").click(function(){
		 // üũ�� ��带 ������
		 var count = 0;
		 $("#selectedTbl tbody tr td input:checked").each(function(index){
			 // �迭���� ������ ��ȭ��ȣ
			count--;
			var removedPhone = $(this).parent().siblings(":eq(1)").text();
			$(this).parents("tr").remove();	// ���õ� ��� ����
			// �迭���� ������ ���� �迭 ����
			selectedPhoneArr = $.grep(selectedPhoneArr,
					 function(arr){ return arr != removedPhone; });
		 });

		 $("#addNum").text( parseInt($("#addNum").text() ) + parseInt(count) );
		 $("#checkedNum").text( $("input:checked").size() );
	 });

	/*
	 * ���̶���Ʈ �߰�
	 */
	 $("input:checkbox").live("click", function(){
		$.js.highlight( $(this) );
	 });

	 $("#ok").click(function(){
		 $.js.throwPhoneNum();
	 });

	 /*
	  *	�ּҷ� �˻����� ����Ű�� �Է� �������
	  */
	 $('#main').keydown(function(e) {
		  if( e.keyCode == 13){	// ����Ű�� �Է��������
			 $.js.search();
		  }
		});

	 /*
	  *	�ּҷ� �˻� ��ư
	  */
	 $("#searchBtn").click(function(){
			$.js.search();	
	 });

	$("#cancel").click(function(){
		window.close();
	});
				
	
	 // table sorter 
	// $.getScript('./js/plug-in/jquery.tablesorter.min.js', function() {
		 $("#selectedTbl").tablesorter({
		       headers: { 
			      	 0: { // ù��° th�� ��ü���� ��ư�̱⶧���� �����̶����� disable
			           sorter: false
			    	}}
		}); // ���̺� ����
	// });
	 
});
</script>
</head>
<body>
<div id="main">
<!-- the tabs -->
<h2 id="header"><img src="./images/sms/bg_title.gif" alt="" />SMS �ּҷ�
</h2>
<div id="sreachBar" style="display: none;">
	<img style="margin:0" src="./images/sms/ico_marr2.gif" /><label>�ּҷ� �˻� : </label>
	<select id="select">
		<option value="f_enuser">�̸�</option>
		<option value="f_enphone">��ȭ��ȣ</option>
		<option value="f_deptcode">��å</option>
	</select>
	<input class="inp" type="text" id="searchInput" name="searchInput" size="40" maxlength="40" value="" />
	<img id="searchBtn"  src="./images/sms/btn_search3.gif" alt="�˻���ư" />
</div>
<ul class="myTabs">
	<li><a href="#second">���� �ּҷ�</a></li>
	<!--  �μ� �ּҷ��� ���� ���� ó�� -->		
	<!-- <li><a href="#first">�μ� �ּҷ�</a></li> -->
</ul>

<div id="box"><!-- tab "panes" -->
	<div class="scrollable" id="selectableList">
		<div id="addressList">
			<table class="ads_tbl myList" cellpadding="0" cellspacing="0">
				<colgroup>
					<col width="80%" />
					<col width="20%" />
				</colgroup>
				<thead>
					<tr>
						<th>�׷��</th>
						<th style="border-right: none;">�ο�</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach var="data"  items="${list}" >
					<tr>
						<td>${data.group}<span style="display: none;">${data.index}</span></td>
						<td>${data.count}</td>						
				   </tr> 
				</c:forEach>				
				</tbody>
			</table>
		</div>
		<div id="tree"></div>
	</div>
	<div id="searchedList"><!--  iframe ���ۿ� -->
	<form name="frm" id="frm" action="" target="deptFrame" method="post">
		<input type="hidden" name="groupIndex" id="f_deptcode" value="" />
		<!-- 
		<input type="hidden" name="f_deptcode" id="f_deptcode" value="" />
		<input type="hidden" name="f_psname" id="f_psname" value="" />
		<input type="hidden" name="f_pscode" id="f_pscode" value="" />
		<input type="hidden" name="f_dname" id="f_dname" value="" />
		<input type="hidden" name="f_search" id="f_search" value="" />
		-->
		
	</form>
	<!--// iframe ���ۿ� -->
	<iframe style="overflow:hidden;" src="" id="deptFrame" name="deptFrame"
		frameborder="0"></iframe>
		
	</div>
	<div id="inout"><img id="addBtn" src="./images/sms/btn_opaddition.gif" alt="�߰�" />
					<img id="removeBtn" src="./images/sms/btn_opspdelete.gif" alt="����" />
	</div>
	<div id="infoBox">
		<span>�߰��� �ο� : <strong id="addNum">0</strong>��
		���õ� �ο� : <strong id="checkedNum">0</strong>��</span>
	</div>
	<div class="scrollable" id="selectedList">
	<table class="ads_tbl" id="selectedTbl" cellpadding="0" cellspacing="0">
		<colgroup>
			<col width="10%" />
			<col width="40%" />
			<col width="60%" />
			<!-- 
			<col width="20%" />
			<col width="30%" />
			-->
		</colgroup>
		<thead>
			<tr>
				<th><a id="allBtn" href="#" onclick="return false;"><img src="./images/sms/btn_tall.gif" alt="��ü ����" /></a></th>
				<th><a href="#">�̸�</a><img id="sortBtn" src="./images/sms/btn_opope.gif" alt="sort" /></th>
				<th><a href="#">�޴��ȣ</a><img id="sortBtn" src="./images/sms/btn_opope.gif" alt="sort" /></th>
				<!-- 
				<th><a href="#">�����ȭ</a><img id="sortBtn" src="./images/sms/btn_opope.gif" alt="sort" /></th>
				<th style="border-right: none; border-bottom: 1px solid #ccc;">
				<a href="#">��å</a><img id="sortBtn" src="./images/sms/btn_opope.gif" alt="sort" /></th>
				-->
			</tr>
		</thead>
		<tbody>
		
		</tbody>
		<tfoot>
		</tfoot>
	</table>
	</div>
</div>
<div id="buttons"><a href="#" id="ok" onclick="return false"><img
	src="./images/sms/btn_opchange.gif" alt="Ȯ��" /></a> <a href="#" id="cancel"
	onclick="return false"><img src="./images/sms/btn_opcancel.gif"
	alt="���" /></a>
</div>
</div><!-- //main -->
</body>
</html>