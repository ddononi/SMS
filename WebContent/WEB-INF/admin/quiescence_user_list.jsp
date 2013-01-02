<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.util.*" %>	
<%@ page import="kr.go.police.account.*" %>	
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
					<img src="images/admin/title_virgin.gif" alt="미사용회원" />
				</h3>
				<%--	검색 처리 --%>
				<form style="clear: both; width: 100%; padding:3px; vertical-align: middle;" id="search_frm" action="./QuiescenceListAction.ac" method="get"  >
					<select id="limit" name="limit">
						<option ${limit == "10"?"selected":""} value="10">10개</option>
						<option ${limit == "20"?"selected":""} value="20">20개</option>
						<option ${limit == "30"?"selected":""} value="30">30개</option>
						<option ${limit == "40"?"selected":""} value="40">40개</option>
						<option ${limit == "50"?"selected":""} value="50">50개</option>
					</select>
					<div style="float: right; display: inline-block;">	
					<select id=type name="type">
						<option value="id" ${type == "id"?"selected":""} >아이디</option>
						<option value="name"  ${type == "name"?"selected":""} >이름</option>					
						<option value="police" ${type == "police"?"selected":""} >경찰서</option>
						<option value="department" ${type == "department"?"selected":""} >부서</option>
			    <!-- <option value="grade" ${type == "grade"?"selected":""} >계급</option> -->
						<option value="phone" ${type == "phone"?"selected":""} >전화번호</option>
					</select>										
						<input title="검색어를 입력하세요" style="margin-bottom: 3px;" value="${search}"  class="search" type="text" name="search" id="search" size="20" />
						<a  href="#"  onclick="return false;" id="search_btn"><img style="margin-bottom:5px;margin-right:5px; right;vertical-align: middle;"  src="./images/base/category_btn.gif" /></a>
					</div>
				</form>	
				<!--게시판-->
				<form method=post action="#">	</form>			
				<table id="usersList" width="100%" border="0" cellpadding="0" cellspacing="0">
					<thead>
						<tr height="34px;">
							<th><input type="checkbox" id="select_all"></th>
							<th>No.</th>						
							<th>이름</th>
							<th>아이디</th>							
							<th>경찰서</th>
							<th>부서</th>
							<th>전화번호</th>							
							<th>접속시간</th>														
						</tr>
					</thead>
					<tbody>
					<!--  공지사항이 없는경우 -->
					<c:if test="${empty userList}">
						<tr>
							<td colspan="8">미사용회원이 없습니다.</td>
						</tr> 
					</c:if>
										
					  <!--회원 리스트 -->
					<c:forEach var="user"  items="${userList}"  >
						<tr>
							<td>					
					   		   <input type="checkbox" name="del" value="${user.index}" >
					       </td>
							<td>					
					   		   <%=no--%>
					       </td>						
							<td>					
					   		   ${user.name}
					       </td>
							<td>					
					   		  ${user.id}
					       </td>						       
							<td>					
					   		   ${user.psName}
					       </td>					
							<td>					
					   		   ${user.deptName}
					       </td>	
							<td class="phone">					
					   		   ${user.phone1}
					       </td>				
							<td>					
					   		  ${user.visitDate}
					       </td>						       			       					       
					     </tr> 
					</c:forEach> 
					</tbody>
				</table>
				
				<form id="del_frm" action="./UserDeleteAction.ac" method="post" style="float: right;  margin-top: 5px;">
					<input value=""  name="del_index"  id="del_index" type="hidden" />
					<a href="#" onclick="return false;" id="del_btn">유저탈퇴</a>
				</form>
				<div style="clear: both;"></div>
				 <c:if test="${(empty userList) == false}">
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
    $("#usersList tbody tr").each(function(i){
       if(i % 2 == 0){
        $(this).children('td').css('background', '#efe');
       } 
    });
 
    // 테이블 현재 열 강조 효과
    $("#usersList tr").bind('mouseover', function(){
    	var $this = $(this);
    	$this.find("td").addClass("hover");
    	//$this.find("a").spectrum();     
    }).bind('mouseout', function(){
    	var $this = $(this);    	
    	$this.find("td").removeClass("hover");
    	//$this.find("a").css("color", "#8b8b8b"); 
    });
    

 // 삭제처리
    $("#del_btn").button().click(function(){
    	var delArr = [];
    	$("input:checked").not("#select_all").each(function(){
    		delArr.push($(this).val());
    	});
    	
    	if(delArr.length <=0){
    		alert("선택된 항목이 없습니다.");
    		return;
    	}
    	if(confirm("선택한 유저를 탈퇴시키겠습니까?")){
        	$("#del_index").val(delArr.join(","));
    		$("#del_frm").submit();
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

  // 테이블 td를 클릭하면 해당 row의 chekcbox상태 변경
  $("#usersList tbody td").click(function(){
  	var $check = $(this).siblings().first().children("input:checkbox");
  	if($check.attr("checked") == "checked"){
  		$check.removeAttr("checked");    		
  	}else{
  		$check.attr("checked", "checked");
  	}
  });    

  $("#limit").change(function(){
  	$("#frm").submit();
  });
  
//검색 버튼    
  $("#search_btn").click(function(){
  	$("#search_frm").submit();
  });  
	
  $("#limit").change(function(){
  	$("#search_frm").submit();
  });
  
  $(".phone").addHyphen();
  
  	//window.location.href="SmsSendResultAction.sm?limit=" + $(this).val();



  $("#top_menu1").attr("data-on", "on");
  $("#top_menu1 > img").attr("src", "./images/admin/menu01_admin_on.gif");
  $("#no_visit_user_list_sub_menu > img").attr("src", "./images/admin/menu_admin_sub06_on.gif");
  $("#no_visit_user_list_sub_menu").attr("data-on", "on");
  $(".gnb_sub1").show();	
    

});	

//-->
</script>
</html>