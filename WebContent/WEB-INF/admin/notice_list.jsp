<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.util.*" %>	
<%@ page import="kr.go.police.account.*" %>	
<%@ page import="java.util.*" %>	    
<%@ page import="kr.go.police.board.*" %>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%
	//	����Ʈ ��ȣ
	int no = (Integer)request.getAttribute("no");		
%>
<%-- ���  --%>
<jsp:include page="../modules/header.jsp" />
<body>
	<div id="wrapper">
		<%-- ��ܸ޴�  --%>
		<jsp:include page="../modules/admin_topmenu.jsp" />
		<div id="contents">
			<%-- ���̵� �޴�  --%>
			<jsp:include page="../modules/admin_sidebox.jsp" />
     	   <div class="boderWrap">
				<h3>
					<img src="images/notice/title_notice.gif" alt="��������" />
				</h3>
				<%--	���� �� --%>
				<form id="del_frm" action="./NoticeDeleteAction.bo" method="post" >
					<input type="hidden" name="token"  id="token"  value="${token}" />				
					<input id="del_index" name="del_index" type="hidden" />				
				</form>				
				<%--	�˻� ó�� --%>
				<form style="clear: both; width: 100%; padding:3px; vertical-align: middle;" id="search_frm" action="./AdminNoticeListAction.bo" method="get"  >
					<input value="" name="page" type="hidden" />
					<select id="limit" name="limit" style="float: left; display: inline-block;">
						<option ${limit == "10"?"selected":""} value="10">10��</option>
						<option ${limit == "20"?"selected":""} value="20">20��</option>
						<option ${limit == "30"?"selected":""} value="30">30��</option>
						<option ${limit == "40"?"selected":""} value="40">40��</option>
						<option ${limit == "50"?"selected":""} value="50">50��</option>
					</select>	
					<div style="float: right; display: inline-block;">
						<input title="�˻��� �ۼ��� Ȥ�� ������ �Է��ϼ���" style="margin-bottom: 3px;" value="${search}"  class="search" type="text" name="search" id="search" size="20" />
						<a  href="#"  onclick="return false;" id="search_btn"><img style="margin-bottom:5px;margin-right:5px; right;vertical-align: middle;"  src="./images/base/category_btn.gif" /></a>
					</div>
				</form>
				<!--�Խ���-->
				<table id="board_table" width="100%" border="0" cellpadding="0" cellspacing="0">
					<colgroup>
						<col width="5%" />
						<col width="8%" />
						<col width="" />
						<col width="18%" />
						<col width="8%" />
					</colgroup>
					<thead>
						<tr height="34px;">
							<th><input id="select_all"  type="checkbox"   /></th>							
							<th>NO.</th>
							<th>����</th>
							<th>�����</th>
							<th>��ȸ</th>
						</tr>
					</thead>
					<tbody>
					<!--  ���������� ���°�� -->
					<c:if test="${empty list}">
						<tr>
							<td colspan="5"> ���������� �����ϴ�.</td>
						</tr> 
					</c:if>
										
					<!-- �������� -->
					<c:forEach var="data"  items="${list}" >
						<tr>
							<td>					
					   		   <input  type="checkbox" name="del" value="${data.index}"   />
					       </td>							
							<td class="num"><%=no--%></td>
							<td class="tite">
									${data.hasFile?'<img src="images/notice/icon_disk.gif" />':''}
									${data.newIcon?'<img src="images/notice/icon_n.gif" />':''}							
									<a class="subject"  href="./NoticeModifyView.bo?index=${data.index}">${data.notice?'[����]':''}
									${data.title}</a>
							</td>
							<td class="day">${data.regDate}</td>
							<td class="hit">${data.viewCount}</td>
						</tr>
					</c:forEach>		
					</tbody>
				</table>
				<div id="buttons" style="float: right;  margin-top: 5px;">
					<a href="./AdminNoticeWriteView.bo"  id="add_btn">���</a>
					<a href="#" onclick="return false" id="del_btn">����</a>
				</div>		
				<div style="clear: both;"></div>						
				<c:if test="${(empty list) == false}">
					${pagiNation}
				</c:if>
			</div>
		</div>
	</div>
<jsp:include page="../modules/footer.jspf" />	
</body>
<script type="text/javascript">
<!--
$(function(){
	
    // odd td colume stand out
    $("#board_table tbody tr").each(function(i){
       if(i % 2 == 0){
        $(this).children('td').css('background', '#efe');
       } 
    });
 
    // ���̺� ���� �� ���� ȿ��
    $("#board_table td").hover(
      function () {
        $(this).siblings().andSelf().addClass("hover");
      },
      function () {
        $(this).siblings().andSelf().removeClass("hover");
      }
    ); 
    
	//	�Է�â ���� ���� ��ư �Է½� ������
    $("#search").tooltip().keydown(function(event){
	       if(event.keyCode == 13){
	    	   $("#search_frm").submit();
	       }
    });	 
	
    $("#select_all").change(function(){
    	// �̿�Ұ� üũ�Ǿ� �ִٸ� �ٸ� üũ�ڽ��鵵 ���� üũ�� Ȱ��ȭ��Ų��.
    	if(this.checked){
    		$("input:checkbox").attr("checked", "checked");
    	}else{
    		$("input:checkbox").removeAttr("checked");
    	}
    });	
	
    // ��ư  ui ó��
    $("#add_btn, #del_btn").button();	
	
	// �˻� ��ư    
    $("#search_btn").click(function(){
    	$("#search_frm").submit();
    });  	
    
    // ����ó��
    // �ش��ε�����ȣ�� (,)��ūȭ
    $("#del_btn").button().click(function(){
    	var delArr = [];
    	$("input:checked").not("#select_all").each(function(){
    		delArr.push($(this).val());
    	});
    	if(delArr.length <=0){
    		alert("���õ� �׸��� �����ϴ�.");
    		return;
    	}
    	// ������ ����
    	$("#del_index").val(delArr.join(","));
    	$("#del_frm").submit();
    });
    
	// �޴� ó��
	$("#top_menu4").attr("data-on", "on");
	$("#top_menu4 > img").attr("src", "./images/top/menu04_on.gif");
	$("#notice_sub_menu > img").attr("src",  "./images/admin/menu_admin_sub03_on.gif");
	$("#notice_sub_menu").attr("data-on", "on");
	$("#top_menu4").trigger("mouseover");
	$(".gnb_sub4").show();
	
});	
//-->
</script>
</html>