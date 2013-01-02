<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.util.*"%>
<%@ page import="kr.go.police.sms.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 헤더  --%>
<jsp:include page="../modules/header.jsp" />
<style>
#letterbox_m{text-align: center; height: 600px; display: block;}
.box{ display:inline-block;  margin: 0px 10px 20px 10px;
 text-align: center; background: url('./images/sms/sms_back.gif') no-repeat center top;}
 .box textarea{background-color: #FEF6D0;}
 select{width:100px;]}
</style>
<script>
	$(function() {

		$("#group_select").change(
				function() {
					var groupIndex = $(this).children("option:selected").val();
					window.location.href = "./MyMessageAction.sm?groupIndex="
							+ groupIndex;
		});
		
		$("#add_btn").button();
		// 메뉴 처리
		$("#top_menu2").attr("data-on", "on");
		$("#top_menu2 > img").attr("src", "./images/top/menu02_on.gif");
		$("#sms_manage_menu > img").attr("src",
				"./images/top/menu_sub09_on.gif");
		$("#sms_manage_menu").attr("data-on", "on");
		$("#top_menu2").trigger("mouseover");
		$(".gnb_sub2").show();

	});
</script>
<body>
	<div id="wrapper">
		<%-- 상단메뉴  --%>
		<jsp:include page="../modules/topmenu.jsp" />

		<div id="contents">
			<%-- 사이드 메뉴  --%>
			<jsp:include page="../modules/sidebox.jsp" />


			<div id="contentsWrap_m">
				<h3>
					<img src="./images/lettersend/title_manage.gif" alt="내문자함" />
				</h3>
				<p class="choice_gruop" style="vertical-align: middle;margin-top: 10px;">
					<select id="group_select">
						<c:forEach var="group" items="${groups}">
							<option ${groupIndex ==group.index?'selected':''}
								value="${group.index}">${group.group}</option>
						</c:forEach>
					</select> <a href="./AddMyMessageView.sm" id="add_btn">문자함추가</a>
				</p>
				<p class="top_bg"></p>
				<div class="letterbox_m">
					<!--  주소록이 없는경우 -->
					<c:if test="${empty messages}">
						<h3 style="text-align: center;">문자함이 없습니다.</h3>
					</c:if>
					<c:forEach var="msg" items="${messages}">
						<ul class="box">
							<li ><a href="./MyMessageView.sm?index=${msg.index}" ><textarea
									style="width: 135px; height: 108px; margin-left:1px; margin-top: 21.5px; cursor: pointer;"
									name="${msg.index}">${msg.message}</textarea></a></li>
							<li><a href="./MyMessageView.sm?index=${msg.index}" >${msg.title}</a></li>
						</ul>
					</c:forEach>
					<div style="display:block; width: 600px; height:140px; padding: 20px 0px 5px 0px;"
						class="boderWrap">
						<c:if test="${(empty messages) == false}">
							${pagiNation}
					</c:if>
					</div>
				</div>						
				<p class="bottom_bg"></p>			
			</div>
		</div>
	</div>
	<jsp:include page="../modules/footer.jspf" />
</body>

</html>