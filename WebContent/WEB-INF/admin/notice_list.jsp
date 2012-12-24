<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.util.*" %>	
<%@ page import="kr.go.police.account.*" %>	
<%@ page import="java.util.*" %>	    
<%@ page import="kr.go.police.board.*" %>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%
	//	리스트 번호
	int no = (Integer)request.getAttribute("no");		
%>
<%-- 헤더  --%>
<jsp:include page="../modules/header.jsp" />
<body>
	<div id="wrapper">
		<%-- 상단메뉴  --%>
		<jsp:include page="../modules/admin_topmenu.jsp" />
		<div id="contents">
			<%-- 사이드 메뉴  --%>
			<jsp:include page="../modules/admin_sidebox.jsp" />
     	   <div class="boderWrap">
				<h3>
					<img src="images/notice/title_notice.gif" alt="공지사항" />
				</h3>
				<%--	삭제 폼 --%>
				<form id="del_frm" action="./NoticeDeleteAction.bo" method="post" >
					<input type="hidden" name="token"  id="token"  value="${token}" />				
					<input id="del_index" name="del_index" type="hidden" />				
				</form>				
				<%--	검색 처리 --%>
				<form style="clear: both; width: 100%; padding:3px; vertical-align: middle;" id="search_frm" action="./AdminNoticeListAction.bo" method="get"  >
					<input value="" name="page" type="hidden" />
					<select id="limit" name="limit" style="float: left; display: inline-block;">
						<option ${limit == "10"?"selected":""} value="10">10개</option>
						<option ${limit == "20"?"selected":""} value="20">20개</option>
						<option ${limit == "30"?"selected":""} value="30">30개</option>
						<option ${limit == "40"?"selected":""} value="40">40개</option>
						<option ${limit == "50"?"selected":""} value="50">50개</option>
					</select>	
					<div style="float: right; display: inline-block;">
						<input title="검색할 작성자 혹은 제목을 입력하세요" style="margin-bottom: 3px;" value="${search}"  class="search" type="text" name="search" id="search" size="20" />
						<a  href="#"  onclick="return false;" id="search_btn"><img style="margin-bottom:5px;margin-right:5px; right;vertical-align: middle;"  src="./images/base/category_btn.gif" /></a>
					</div>
				</form>
				<!--게시판-->
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
							<th>제목</th>
							<th>등록일</th>
							<th>조회</th>
						</tr>
					</thead>
					<tbody>
					<!--  공지사항이 없는경우 -->
					<c:if test="${empty list}">
						<tr>
							<td colspan="5"> 공지사항이 없습니다.</td>
						</tr> 
					</c:if>
										
					<!-- 공지사항 -->
					<c:forEach var="data"  items="${list}" >
						<tr>
							<td>					
					   		   <input  type="checkbox" name="del" value="${data.index}"   />
					       </td>							
							<td class="num"><%=no--%></td>
							<td class="tite">
									${data.hasFile?'<img src="images/notice/icon_disk.gif" />':''}
									${data.newIcon?'<img src="images/notice/icon_n.gif" />':''}							
									<a class="subject"  href="./NoticeModifyView.bo?index=${data.index}">${data.notice?'[공지]':''}
									${data.title}</a>
							</td>
							<td class="day">${data.regDate}</td>
							<td class="hit">${data.viewCount}</td>
						</tr>
					</c:forEach>		
					</tbody>
				</table>
				<div id="buttons" style="float: right;  margin-top: 5px;">
					<a href="./AdminNoticeWriteView.bo"  id="add_btn">등록</a>
					<a href="#" onclick="return false" id="del_btn">삭제</a>
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
 
    // 테이블 현재 열 강조 효과
    $("#board_table td").hover(
      function () {
        $(this).siblings().andSelf().addClass("hover");
      },
      function () {
        $(this).siblings().andSelf().removeClass("hover");
      }
    ); 
    
	//	입력창 에서 엔터 버튼 입력시 폼전송
    $("#search").tooltip().keydown(function(event){
	       if(event.keyCode == 13){
	    	   $("#search_frm").submit();
	       }
    });	 
	
    $("#select_all").change(function(){
    	// 이요소가 체크되어 있다면 다른 체크박스들도 전부 체크를 활성화시킨다.
    	if(this.checked){
    		$("input:checkbox").attr("checked", "checked");
    	}else{
    		$("input:checkbox").removeAttr("checked");
    	}
    });	
	
    // 버튼  ui 처리
    $("#add_btn, #del_btn").button();	
	
	// 검색 버튼    
    $("#search_btn").click(function(){
    	$("#search_frm").submit();
    });  	
    
    // 삭제처리
    // 해당인덱스번호를 (,)토큰화
    $("#del_btn").button().click(function(){
    	var delArr = [];
    	$("input:checked").not("#select_all").each(function(){
    		delArr.push($(this).val());
    	});
    	if(delArr.length <=0){
    		alert("선택된 항목이 없습니다.");
    		return;
    	}
    	// 삭제폼 전송
    	$("#del_index").val(delArr.join(","));
    	$("#del_frm").submit();
    });
    
	// 메뉴 처리
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