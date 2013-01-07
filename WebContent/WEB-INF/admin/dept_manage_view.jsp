<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.util.*" %>	
<%@ page import="kr.go.police.sms.*" %>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- 헤더  --%>
<jsp:include page="../modules/header.jsp" />
<style>
.message{
	display: inline-block; width: 100px; text-overflow:ellipsis; white-space: nowrap; overflow: hidden;
}
#sendResultList td{
	cursor: pointer;
}
#tree, #dept_table{
	width : 250px;
	height: 400px;
	overflow: hidden;
}
#dept_table table{margin-top: 3px;}
#tree *{
 border: none;
}
#tree{ float: left; }
#dept_table{ float: right; width: 450px; }
</style>
<body>
	<div id="wrapper">
		<%-- 상단메뉴  --%>
		<jsp:include page="../modules/admin_topmenu.jsp" />
		<div id="contents">
			<%-- 사이드 메뉴  --%>
			<jsp:include page="../modules/admin_sidebox.jsp" />
     	   <div class="boderWrap">
				<h3>
					<img src="images/admin/title_department.gif" alt="부서관리" />
				</h3>
				<!--  부서관리 트리 -->
			    <div id="tree"  style="border:  2px solid #ccc;">
			        <ul>
						<c:forEach var="police"  items="${list}" >
							<li id="${police.code},0" class="folder">${police.name}
								 <ul>
									<c:forEach var="dept"  items="${police.list}" >
										<li id="${police.code},${dept.deptCode}" >${dept.name}</li>	
									</c:forEach> 	
				                </ul>
			                </li>																
						</c:forEach> 		
			        </ul>
			    </div>
			    <div id="dept_table" style="border:  2px solid #ccc;" >
			    </div>
				<form id="del_frm" action="./ListDeleteAction.sm" method="post" style="float: right;  margin-top: 5px;">
					<input type="hidden" name="token"  id="token"  value="${token}" />
					<input value="" id="indexs" name="indexs" type="hidden" />
					<input value="./AllSendListAction.sm" name="page" type="hidden" />
					<a href="#" onclick="return false;" id="del_btn">삭제</a>
				</form>
			</div>
		</div>
	</div>
	<jsp:include page="../modules/footer.jspf" />	
</body>
<!--  tree plugin -->
<link rel='stylesheet' type='text/css' href='./js/plug-in/dynatree-1.2.2/skin-vista/ui.dynatree.css' >
<script src='./js/plug-in/dynatree-1.2.2/jquery.dynatree.js' type="text/javascript"></script>
<script type="text/javascript">
<!--
$(function(){
    $("#tree").dynatree({
    	checkbox: true,
        selectMode: 2,
        onSelect: function(select, node) {
        	node.expand(false);
       /*
          var selNodes = node.tree.getSelectedNodes();
          var selKeys = $.map(selNodes, function(node){
               return "[" + node.data.key + "]: '" + node.data.title + "'";
          });
          */
         // $("#echoSelection2").text(selKeys.join(", "));
        },
        
        onClick: function(node, event) {
        	node.expand(false);
          // We should not toggle, if target was "checkbox", because this
          // would result in double-toggle (i.e. no toggle)
          /*
          if( node.getEventTargetType(event) == "title" )
            node.toggleSelect();
          */
        },
        
        onKeydown: function(node, event) {
        	/*
          if( event.which == 32 ) {
            node.toggleSelect();
            return false;
          }
        	*/
        },
        
        onFocus: function(node) {
           //alert(node.data.title);
        },
        onBlur: function(node) {
           // alert(node.data.title);
        },
        
        // The following options are only required, if we have more than one tree on one page:
        cookieId: "dynatree-Cb2",
        idPrefix: "dynatree-Cb2-",
        
        
        // 아이템 선택시 해당 부서의 유저를 가져온다.
        onActivate: function(node) {
           $.get("./DeptUserTableAction.ac", { deptcode : node.data.key },
           	 function(result){
        	   $("#dept_table").empty().append($.trim(result));
        	    // odd td colume stand out
        	    $("table:last tbody tr").each(function(i){
        	       if(i % 2 == 0){
        	        $(this).children('td').css('background', '#efe');
        	       } 
        	    });
        	 
        	    // 테이블 현재 열 강조 효과
        	    $("table:last td").hover(
        	      function () {
        	        $(this).siblings().andSelf().addClass("hover");
        	      },
        	      function () {
        	        $(this).siblings().andSelf().removeClass("hover");
        	      }
        	    );            	   
           });
        }
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
    	
    	$("#del_frm #indexs").val(delArr);
    	if(confirm("선택한 내역을 삭제하시겠습니까?")){
    		$("#del_frm").submit();
    	}

    	    	
    });
    
    $("#select_all").live('change', function(){
    	// 이요소가 체크되어 있다면 다른 체크박스들도 전부 체크를 활성화시킨다.
    	if(this.checked){
    		$("input:checkbox").attr("checked", "checked");
    	}else{
    		$("input:checkbox").removeAttr("checked");
    	}
    });
    

    //	
	$("#top_menu3").attr("data-on", "on");
	$("#top_menu3 > img").attr("src", "./images/admin/menu03_admin_on.gif");
	$("#dept_sub_menu > img").attr("src", "./images/admin/menu_admin_sub11_on.gif");
	$("#dept_sub_menu").attr("data-on", "on");
    $(".gnb_sub3").show();	
    
});	
//-->
</script>
</html>