<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>

<script type="text/javascript">
<!--
	$(function(){
		//	메뉴 처리
		// 상위 메뉴
		$("#top_menu1").mouseover(function(){
			$(this).children("img").attr("src", "./images/top/menu01_on.gif");
			$(".gnb_sub1").show();
			$(".gnb_sub2, .gnb_sub3, .gnb_sub4").hide();
		}).mouseout(function(){
			if($(this).attr("data-on") != "on"){			
				$(this).children("img").attr("src", "./images/top/menu01_off.gif");
			}
		});
		
		$("#top_menu2").mouseover(function(){
			$(this).children("img").attr("src", "./images/top/menu02_on.gif");
			$(".gnb_sub2").show();
			$(".gnb_sub1, .gnb_sub3, .gnb_sub4").hide();			
		}).mouseout(function(){
			if($(this).attr("data-on") != "on"){			
				$(this).children("img").attr("src", "./images/top/menu02_off.gif");
			}
		});

		$("#top_menu3").mouseover(function(){
			$(this).children("img").attr("src", "./images/top/menu03_on.gif");
			$(".gnb_sub3").show();
			$(".gnb_sub1, .gnb_sub2, .gnb_sub4").hide();						
		}).mouseout(function(){
			if($(this).attr("data-on") != "on"){			
				$(this).children("img").attr("src", "./images/top/menu03_off.gif");
			}
		});		

		$("#top_menu4").mouseover(function(){
			$(this).children("img").attr("src", "./images/top/menu04_on.gif");
			$(".gnb_sub4").show();
			$(".gnb_sub1, .gnb_sub2, .gnb_sub3").hide();					
		}).mouseout(function(){
			if($(this).attr("data-on") != "on"){			
				$(this).children("img").attr("src", "./images/top/menu04_off.gif");
			}
		});			
		
		// 회원관리
		$("#user_list_menu").mouseover(function(){
			$(this).children("img").attr("src", "./images/top/menu_sub01_on.gif");
		}).mouseout(function(){
			if($(this).attr("data-on") != "on"){					
				$(this).children("img").attr("src", "./images/top/menu_sub01_off.gif");
			}
		});
		 
		$("#reservered_send_top_menu").mouseover(function(){
			$(this).children("img").attr("src", "./images/top/menu_sub02_on.gif");
		}).mouseout(function(){
			if($(this).attr("data-on") != "on"){					
				$(this).children("img").attr("src", "./images/top/menu_sub02_off.gif");
			}
		});
		
		// 문자관리 하위메뉴
		$("#result_top_menu").mouseover(function(){
			$(this).children("img").attr("src", "./images/top/menu_sub03_on.gif");
		}).mouseout(function(){
			$(this).children("img").attr("src", "./images/top/menu_sub03_off.gif");
		});
		
		$("#sms_manage_menu").mouseover(function(){
			$(this).children("img").attr("src", "./images/top/menu_sub04_on.gif");
		}).mouseout(function(){
			$(this).children("img").attr("src", "./images/top/menu_sub04_off.gif");
		});		
		
		// 주소록 하위메뉴
		$("#my_address_book_top_menu").mouseover(function(){
			$(this).children("img").attr("src", "./images/top/menu_sub05_on.gif");
		}).mouseout(function(){
			$(this).children("img").attr("src", "./images/top/menu_sub05_off.gif");
		});		
		
		$("#add_my_address_book_top_menu").mouseover(function(){
			$(this).children("img").attr("src", "./images/top/menu_sub06_on.gif");
		}).mouseout(function(){
			$(this).children("img").attr("src", "./images/top/menu_sub06_off.gif");
		});	
		
		// 문의 하위메뉴
		$("#board_view_top_menu").mouseover(function(){
			$(this).children("img").attr("src", "./images/top/menu_sub07_on.gif");
		}).mouseout(function(){
			if($(this).attr("data-on") != "on"){
				$(this).children("img").attr("src", "./images/top/menu_sub07_off.gif");
			}
		});	
		
		$("#board_write_top_menu").mouseover(function(){
			$(this).children("img").attr("src", "./images/top/menu_sub08_on.gif");
		}).mouseout(function(){
			$(this).children("img").attr("src", "./images/top/menu_sub08_off.gif");
		});			
		
	});
//-->
</script>    
	<!-- 헤더영역 -->
	<div id="header">
    	<h1 class="logo"><a href=""><img src="./images/top/logo.gif"  alt="강원청SMS" border="0" /></a></h1>
        <ul class="gnb">
        	<li style="visibility: hidden;" class="admin_btn"><a href=""><img src="./images/top/btn_manager.gif" alt="관리자 모드"  border="0" /></a></li>
        	<li class="gnb_sub">
            	<a href="#" id="top_menu1"><img src="./images/top/menu01_off.gif" alt="문자발송"  border="0" /></a>
                <ul class="gnb_sub1">
                	<li><a href="./UserListAction.ac"  id="user_list_menu"><img src="./images/top/menu_sub01_off.gif"  alt="회원관리"  border="0" /></a></li>
                    <li><a href="./ReservedListAction.sm" id="reservered_send_top_menu"><img src="./images/top/menu_sub02_off.gif"  alt="예약내역"  border="0" /></a></li>
                    <li><a href="./ReservedListAction.sm" id="result_top_menu"><img src="./images/top/menu_sub03_off.gif"  alt="전송결과"  border="0" /></a></li>
            	</ul>
            </li>    
            <li class="gnb_sub">
            	<a href="#" id="top_menu2"><img src="./images/top/menu02_off.gif" alt="문자관리" border="0"/></a>
                <ul class="gnb_sub2">
                    <li><a href="./MyMessageAction.sm"  id="sms_manage_menu"><img src="./images/top/menu_sub04_off.gif"  alt="문자함 추가" border="0"/></a></li>
                </ul>
            </li>
            <li class="gnb_sub">
            	<a href="#"  id="top_menu3"><img src="./images/top/menu03_off.gif" alt="주소록" border="0"/></a>
                <ul class="gnb_sub3">
                	<li><a href="#"  id="my_address_book_top_menu"><img src="./images/top/menu_sub05_off.gif"  alt="내 주소록" border="0"/></a></li>
                    <li><a href="#"  id="add_my_address_book_top_menu"><img src="./images/top/menu_sub06_off.gif"  alt="주소록 추가" border="0"/></a></li>
                </ul>
            </li>
            <li class="gnb_sub">
            	<a href="#" id="top_menu4"><img src="./images/top/menu04_off.gif" alt="문의/제안" border="0"/></a>
            	<ul class="gnb_sub4">
                	<li><a href="#"  id="board_view_top_menu"><img  src="./images/top/menu_sub07_off.gif"  alt="문의보기" border="0"/></a></li>
                    <li><a href="#"  id="board_write_top_menu"><img src="./images/top/menu_sub08_off.gif"  alt="문의하기" border="0"/></a></li>
                </ul>
            </li>
        </ul>
    </div>