<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.util.*" %>	
<%@ page import="kr.go.police.*"  %>			
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- 헤더  --%>
<jsp:include page="../modules/header.jsp" />
<link rel="stylesheet" type="text/css" href="./css/sms.css"/>  
<style>
#myMessageBox, #specailCharBox{cursor: pointer;}
#send_result_dialog{ display: none; }
 #message{background-color: #FEF6D0; font-size: 16px;}

/* css for timepicker */
.ui-timepicker-div .ui-widget-header { margin-bottom: 8px; }
.ui-timepicker-div dl { text-align: left; }
.ui-timepicker-div dl dt { height: 25px; margin-bottom: -25px; }
.ui-timepicker-div dl dd { margin: 0 10px 10px 65px; }
.ui-timepicker-div td { font-size: 90%; }
.ui-tpicker-grid-label { background: none; border: none; margin: 0; padding: 0; }

.ui-timepicker-rtl{ direction: rtl; }
.ui-timepicker-rtl dl { text-align: right; }
.ui-timepicker-rtl dl dd { margin: 0 65px 10px 10px; }
#send_btn{ cursor: pointer;}
.attach_file{display: inline; margin-right:40px;; float: right; }
.attach_file * {display: none; cursor: pointer;}
</style>
<script type="text/javascript" src="./js/sms_page.js"></script>
<script type="text/javascript" src="./js/timepicker.js"></script>
<body>
	<div id="wrapper">
		<%-- 상단메뉴  --%>
		<jsp:include page="../modules/topmenu.jsp" />

		<div id="contents">
			<%-- 사이드 메뉴  --%>
			<jsp:include page="../modules/sidebox.jsp" />
        <div id="contentsWrap">
        	<h3><img src="./images/lettersend/title.gif" alt="문자보내기" /></h3>
            <div class="phone">
						<ul class="choice" >
							<li ><input  disabled="disabled" readonly="readonly"  type="radio" name="send_type" alt="SMS" value="s" checked="checked" /><span>SMS</span>
								<input disabled="disabled"  readonly="readonly"  type="radio"  name="send_type" alt="MMS" value="m" /><strong style="color: #FF2200;">MMS</strong>
							</li>
						</ul>
						<p class="disk_icon" style="visibility:hidden;">
							<img src="././images/lettersend/icon_disk2.gif" />
						</p>
						<p>
							<textarea style="height:252px; white-space:pre-wrap;" 
							 id="message"  name="message" cols="" rows="" class="txt"></textarea>
						</p>
						<ul class="txt_01">
							<li><img  id="sms_sep_icon" src="./images/lettersend/icon_sms.gif" /></li>
							<li id="textByte" class="bytes">0/80Bytes</li>
						</ul>
						<ul class="choice_icon">
							<li><img src="././images/lettersend/icon_disk.gif"  alt="파일첨부"
								id="addFile" border="0" /></li>
							<li><img src="././images/lettersend/icon_write.gif"  alt="새로쓰기" 
								id="resetTextBtn" border="0" /></li>
							<li>
								<ul class="attach_file" >
								<li><img id="movie_icon1"  src="././images/sms/avi_icon.gif"  alt="동영상파일1"  /></li>
								<li><img id="image_file_icon1" src="././images/sms/imageicon.jpg"  alt="이미지파일1"  /></li>
								<li><img id="image_file_icon2" src="././images/sms/imageicon.jpg"  alt="이미지파일2"  /></li>		
								<li><img id="image_file_icon3" src="././images/sms/imageicon.jpg"  alt="이미지파일3"  /></li>
								</ul>
							</li>										
						</ul>
				</div>
          <!-- 전화번호 입력 박스 -->
				<div id="pone_list">
					<div class="bg">
						<div class="srh">
							<!-- <input id="ajaxSearch" size="15" type="Text" value="이름 검색"/>&nbsp;<a href="#" onclick="return false;">
						<img src="./images/btn_srh.gif" alt="검"> <img id="addList" src="./images/btn_addre.gif" alt="주소록" /></a>-->
							<div id="slist" class="srh_box" style="display: none;"></div>
						</div>
						<div id="testTemp" style="position: absolute; left: 42px; top: 20px; display: none;"></div>
						<!--
					<div id="receiver">
					<img id="listPlus" src="./images/iconbtn_plus.gif" alt="추가" />항목 추가
					</div>
					-->	
                   <div class="list_box">
					<c:forEach var="index"  begin="1" end="10" step="1">
                            <ul>
                                <li><span>${index != "10"?"0":""}${index}</span><input disabled="disabled" readonly="readonly" name="recvName${index}" id="recvName${index}"  maxlength="5"  type="text" class="inp nameInp" /></li>
                                <li><input name="recvPhone${index}" id="recvPhone${index}" type="text" class="inp hyphen rt" maxlength="11" value="" style="width:150px;" /></li>
                                <li class="bt"><img src="./images/sms/btn_close2.gif" alt="닫기" /></li>
                            </ul>
					</c:forEach>

                   </div>
						<!--div class="nsent"-->
						<div class="nsent" id="JsNewNum" style="margin-top: 10px;text-align: center;">
							<span id="toolbar" >
						    	<a id="address_book_btn" href="#" onClick="return false;">주소록</a>
                                <a style="display: none;"id="addExcel" href="#" onClick="return false;">엑셀</a> 							
								<a href="#"  onClick="return false;" id="vknPad">숫자패드</a> 
                            	<a href="#" onClick="return false;" id="reset">초기화</a>
                      	   		<a href="#"  id="listPlus" onClick="return false;">추가</a>  
                      	   		<a href="#"  id="reserved_btn" onClick="return false;">예약</a>                       	   		
							</span>
						</div>
						<!--임시 예약 필드 yyyymmddhhmi <input type="text" name="reserve" value=""-->
						<div id="reservationTime">
						<!-- 	<strong>예약일시</strong> :<span></span> -->
						</div>
                        <div class="to" style="font-size: 12px; font-weight: bold;text-align: left; width: 100%;">
							내 번호 :<input class="hyphen"  type="text" name="my_phone_num" id="my_phone_num"
								value="${sessionScope.phone}" style="width: 131px; margin-left: 5px;" class="inp" />
								<label id="reserved_label">예약 :</label><input type="text" id="reserved_datetime" name="reserved_datetime"  disabled="disabled" class="inp" style="width: 115px; margin-left: 5px;font-size: 12px;"   />								
						</div>
						<div class="btn" >
							<a id="send_btn" href="#send_btn" onclick="return false;">
								<img src="./images/sms/btn_send.gif" alt="보내기" />
							</a>
						</div>
					</div>
				</div>
				<!-- //전화번호 입력 박스 -->

				<div class="tab_box">
					<h4 class="tab01">
						<img src="././images/lettersend/tab01_off.gif" id="myMessageBox" alt="내문자" border="0" />
						<div class="my01" style="left:0px;">
							<iframe src="./MyMessageListAction.sm" width="700px"  height="450px"></iframe>						 						
						</div>
					</h4>
					<h4 class="tab02">
						<img src="././images/lettersend/tab02_on.gif"  id="specailCharBox" alt="특수문자" border="0" />
	                    <div class="my02">
						<jsp:include page="../modules/specialBox.jspf" />
                    </div>
                </h4>
                <h4 class="tab03"><img src="./images/lettersend/tab_line.gif" /></h4>
            </div>
        </div>
    </div>
</div>
<%--  문자 전송 폼--%>		  
<div id="send_form_dlg" style="margin-top: 5px; vertical-align:middle;" title="파일첨부">      
	<p style="line-height: 22px; margin-bottom: 10px" >
		첨부파일은 이미지 100Kb(jpg형식, 176 * 144 크기) 이내<br/>
		동영상파일은 300Kb이내로 첨부해야 합니다.<br/>
		(동영상 첨부시 반드시 skm, k3g형식 모두 첨부)
	</p>   
	<form style="text-align: right;" id="send_frm"   action="./SmsSendAction.sm" method="post" enctype="multipart/form-data" >
		<input value="" type="hidden" name="call_to_nums"  id="call_to_nums" />
		<input value="" type="hidden" name="my_phone_num"  id="my_phone_num" />
		<input value="" type="hidden" name="message"  id="message" />
		<input value="" type="hidden" name="flag"  id="flag" />
		<input value="" type="hidden" name="reserved"  id="reserved" />
		<input value="" type="hidden" name="reserved_datetime"  id="reserved_datetime" />
		<input id="file1" name="file1" value="" type="file" style="display: block; margin: 5px;"  />
		<input id="file2" name="file2" value="" type="file" 	style="display: block; margin: 5px;" />
		<input id="file3" name="file3" value="" type="file" style="display: block; margin: 5px;"	 />
	</form>
</div>
<div id="send_result_dialog" style="margin-top: 5px;vertical-align:middle;" title="발송추가완료">
    <p>
        <span id="send_count"></span>건을 발송내역에 추가하였습니다.
    </p>
</div>

	<!-- Numeric Key Pad -->
	<ol id="controls">
		<li id="keyPadClose"><a href="#">Close</a></li>
		<li><a href="#">1</a></li>
		<li><a href="#">2</a></li>
		<li><a href="#">3</a></li>
		<li><a href="#">4</a></li>
		<li><a href="#">5</a></li>
		<li><a href="#">6</a></li>
		<li><a href="#">7</a></li>
		<li><a href="#">8</a></li>
		<li><a href="#">9</a></li>
		<li><a href="#">0</a></li>
		<li><a href="#" id="backSpace">BackSpace</a></li>
	</ol>
	<!-- //Numeric Key Pad -->
	<jsp:include page="../modules/footer.jspf" />	
</body>
<script type="text/javascript">
<!--
	// 버튼 ui 처리
	//$(".srh a").button();
	$( "#toolbar" ).buttonset();

	// 메뉴 효과 처리
	$("#top_menu1").attr("data-on", "on");
	$("#top_menu1 > img").attr("src", "././images/top/menu01_on.gif");
	$("#send_top_menu > img").attr("src", "././images/top/menu_sub01_on.gif");
	$("#send_top_menu").attr("data-on", "on");
	$(".gnb_sub1").show();
//-->
</script>
</html>
