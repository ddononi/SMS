<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.util.*" %>	
<%@ page import="kr.go.police.address.*"  %>		
<%@ page import="kr.go.police.sms.Group"  %>			
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<?xml version="1.0" encoding="EUC-KR" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>�ּҷ� ���</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<!-- <link rel="stylesheet" type="text/css" href="../css/address_window_default.css" /> -->
<script type="text/javascript" src="./js/jquery-1.8.2.min.js"></script>
<style>
body{overflow:auto;color:#333;font-size:16px;scrollbar-face-color:#fff;scrollbar-shadow-color:#BEBEBE;scrollbar-3dlight-color:#BEBEBE;scrollbar-arrow-color:#666;scrollbar-darkshadow-color:#FFF;scrollbar-base-color:#F2EDCE;scrollbar-track-color:#F7F7F7;}
a:LINK,a:VISITED{color:#333;text-decoration:none;}
a:HOVER{color:#00b5f5;}
#main{overflow:hidden;}
.ads_tbl{width:100%;text-align:center;}
.ads_tbl thead th{padding: 3px 0px; background-color:#f7f7f7;border-right:1px solid #ccc;border-bottom:1px solid #ccc;color:#333;font-size:12px;}
.ads_tbl tbody tr{border-top:1px solid red;}
.ads_tbl tbody td{font-size:12px;padding:2px 0;}
.odd{background-color:#f7f7f7;border:none;}
#allBtn{cursor: pointer;}
</style>
<script> 
jQuery.js = {
	selectAll : function(check){
		var flag = check;
		$("input:checkbox").each(function(){
			this.checked = flag;
		});
	},
	spectrum : function(){  
	   return 'rgb(' + (Math.floor(Math.random() * 256)) + ',' + (Math.floor(Math.random() * 256)) + ',' + (Math.floor(Math.random() * 256)) + ')';  
	 }
};

//�޴��� ��ȣ�� (-)������ �ֱ�
$.fn.addHyphen = function(){
	
	return this.each(function(){
		var phone;
		if( $(this).is("input")){
			phone = $.trim($(this).val());
		}else{
			phone = $.trim($(this).text());
		}
		
		// �������� ������ ���� ����
		phone = phone.split("-").join("");;
		if(phone.length < 11 && phone.length> 7 ){
			phone = phone.substr(0, 3) + "-" + phone.substr(3, 3) + "-" + phone.substr(6);
		}else if(phone.length >= 11){
			phone= phone.substr(0, 3) + "-" + phone.substr(3, 4) + "-" + phone.substr(7);
		}
		
		$(this).val(phone);
		if( $(this).is("input")){
			$(this).val(phone);
		}else{
			$(this).text(phone);			
		}
	});
};

$(function() {
	 
	 $.getScript('./js/plug-in/jquery.tablesorter.min.js', function() {
		$("#list").tablesorter({
		       headers: { 
		      	 0: { // ù��° th�� ��ü���� ��ư�̱⶧���� �����̶����� disable
		           sorter: false
		      	}}
			}); // ���̺� ����				  
	});			 
	  
	$("#allBtn").click(function(){	// ��ü ���� �� ����
		var all = "./images/sms/btn_tall.gif";
		var clear = "./images/sms/btn_choice_off.gif";
		var $this = $(this);
		if( $this.attr('src')  == clear ){
			$this.attr("src", all);
			$.js.selectAll(false);
		}else{
			$this.attr("src", clear);
			$.js.selectAll(true);
		}
	});

	$("table tbody td").mouseover(function(){
		$(this).siblings().andSelf().css("color", $.js.spectrum());
	}).mouseout(function(){
		$(this).siblings().andSelf().css("color", "#333");
	});
	
	// ��ȭ��ȣ ������ ó��
	$(".phone_num").addHyphen();
});
</script>
</head>
<body>
<div>
<table id="list" class="ads_tbl" cellspacing="0" cellpadding="0" >
	<colgroup>
		<col width="10%" />
		<col width="40%" />
		<col width="50%" />
		<!-- 
		<col width="20%" />
		<col width="30%" />
		
		-->
	</colgroup>
	<thead>
		<tr>
			<th><img id="allBtn" src="./images/sms/btn_tall.gif" alt="��ü ����" /></a></th>
			<th><a href="#">�̸�</a><img id="sortBtn" src="./images/sms/btn_opope.gif" alt="sort" /></th>
			<th><a href="#">�޴��ȣ</a><img src="./images/sms/btn_opope.gif" alt="sort" /></th>
			<!-- 
			<th><a href="#">�����ȭ</a><img src="./images/sms/btn_opope.gif" alt="sort" /></th>
			<th style="border-right: none; border-bottom: 1px solid #ccc;"><a href="#">��å </a><img id="sortBtn" src="./images/sms/btn_opope.gif" alt="sort" /></th>
			-->			
		</tr>
	</thead>
	<tbody>
	
	<c:if test="${empty list}">
		<tr>
			<td style="padding: 20px;font-weight: bold;" colspan="5">�˻���  �ּҷ��� �����ϴ�.</td>
		</tr>
	</c:if>
						
	<c:forEach var="data"  items="${list}" >
		<tr>
			<td>					
	   		   <input type="checkbox" value="" />
	       </td>
			<td>${data.people}</td>
			<td class="phone_num">${data.phone}</td>
			<td></td>
			<td></td>				       		
	     </tr> 
	</c:forEach>
	</tbody>
</table>
</div>
</body>
</html>
