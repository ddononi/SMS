<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
    
<%--	������ �޴� ó�� --%>
<script type="text/javascript">
<!--
	$(function(){
		//	�޴� ó��
		// ���� �޴�
		$("#top_menu1").mouseover(function(){
			$(this).children("img").attr("src", "./images/admin/menu01_admin_on.gif");
			$(".gnb_sub1").show();
			$(".gnb_sub2, .gnb_sub3, .gnb_sub4").hide();
		}).mouseout(function(){
			if($(this).attr("data-on") != "on"){			
				$(this).children("img").attr("src", "./images/admin/menu01_admin_off.gif");
			}
		});
		
		$("#top_menu2").mouseover(function(){
			$(this).children("img").attr("src", "./images/admin/menu02_admin_on.gif");
			$(".gnb_sub2").show();
			$(".gnb_sub1, .gnb_sub3, .gnb_sub4").hide();			
		}).mouseout(function(){
			if($(this).attr("data-on") != "on"){			
				$(this).children("img").attr("src", "./images/admin/menu02_admin_off.gif");
			}
		});

		$("#top_menu3").mouseover(function(){
			$(this).children("img").attr("src", "./images/admin/menu03_admin_on.gif");
			$(".gnb_sub3").show();
			$(".gnb_sub1, .gnb_sub2, .gnb_sub4").hide();						
		}).mouseout(function(){
			if($(this).attr("data-on") != "on"){			
				$(this).children("img").attr("src", "./images/admin/menu03_admin_off.gif");
			}
		});		

		$("#top_menu4").mouseover(function(){
			$(this).children("img").attr("src", "./images/admin/menu04_admin_on.gif");
			$(".gnb_sub4").show();
			$(".gnb_sub1, .gnb_sub2, .gnb_sub3").hide();					
		}).mouseout(function(){
			if($(this).attr("data-on") != "on"){			
				$(this).children("img").attr("src", "./images/admin/menu04_admin_off.gif");
			}
		});			
		
		// ------------�����޴�---------
		// ȸ������
		$("#user_list_sub_menu").mouseover(function(){
			$(this).children("img").attr("src", "./images/admin/menu_admin_sub05_on.gif");
		}).mouseout(function(){
			if($(this).attr("data-on") != "on"){					
				$(this).children("img").attr("src", "./images/admin/menu_admin_sub05_off.gif");
			}
		});
		// ���δ��
		$("#wait_user_list_sub_menu").mouseover(function(){
			$(this).children("img").attr("src", "./images/admin/menu_admin_sub07_on.gif");
		}).mouseout(function(){
			if($(this).attr("data-on") != "on"){					
				$(this).children("img").attr("src", "./images/admin/menu_admin_sub07_off.gif");
			}
		});
		//  �̻�� ȸ��
		$("#no_visit_user_list_sub_menu").mouseover(function(){
			$(this).children("img").attr("src", "./images/admin/menu_admin_sub06_on.gif");
		}).mouseout(function(){
			if($(this).attr("data-on") != "on"){					
				$(this).children("img").attr("src", "./images/admin/menu_admin_sub06_off.gif");
			}
		});
		
		//	�߼۳���
		$("#all_send_result_list_sub_menu").mouseover(function(){
			$(this).children("img").attr("src", "./images/admin/menu_admin_sub08_on.gif");
		}).mouseout(function(){
			if($(this).attr("data-on") != "on"){					
				$(this).children("img").attr("src", "./images/admin/menu_admin_sub08_off.gif");
			}
		});	
		
		//	���೻��
		$("#all_reserved_list_sub_menu").mouseover(function(){
			$(this).children("img").attr("src", "./images/admin/menu_admin_sub09_on.gif");
		}).mouseout(function(){
			if($(this).attr("data-on") != "on"){					
				$(this).children("img").attr("src", "./images/admin/menu_admin_sub09_off.gif");
			}
		});
		
		// ���
		$("#stats_sub_menu").mouseover(function(){
			$(this).children("img").attr("src", "./images/admin/menu_admin_sub10_on.gif");
		}).mouseout(function(){
			if($(this).attr("data-on") != "on"){					
				$(this).children("img").attr("src", "./images/admin/menu_admin_sub10_off.gif");
			}
		});
		
		// �μ����� �����޴�
		$("#dept_sub_menu").mouseover(function(){
			$(this).children("img").attr("src", "./images/admin/menu_admin_sub11_on.gif");
		}).mouseout(function(){
			if($(this).attr("data-on") != "on"){					
				$(this).children("img").attr("src", "./images/admin/menu_admin_sub11_off.gif");
			}
		});
		
		// ����
		$("#notice_sub_menu").mouseover(function(){
			$(this).children("img").attr("src", "./images/admin/menu_admin_sub03_on.gif");
		}).mouseout(function(){
			if($(this).attr("data-on") != "on"){					
				$(this).children("img").attr("src", "./images/admin/menu_admin_sub03_off.gif");
			}
		});
		// ����
		$("#board_sub_menu").mouseover(function(){
			$(this).children("img").attr("src", "./images/admin/menu_admin_sub04_on.gif");
		}).mouseout(function(){
			if($(this).attr("data-on") != "on"){					
				$(this).children("img").attr("src", "./images/admin/menu_admin_sub04_off.gif");
			}
		});		
		
	});
//-->
</script>    
	<!-- ������� -->
	<div id="header" class="admin_header">
    	<h1 class="logo"><a href="./SmsSendViewAction.sm"><img src="./images/top/logo.gif"  alt="����ûSMS" border="0" /></a></h1>
        <ul class="gnb">
        	<li  class="admin_btn"><a href="./SmsSendViewAction.sm"><img src="./images/admin/btn_user.gif" alt="����� ���"  border="0" /></a></li>
        	<li class="gnb_sub">
            	<a href="./UserListAction.ac" id="top_menu1"><img src="./images/admin/menu01_admin_off.gif" alt="ȸ������"  border="0" /></a>
                <ul class="gnb_sub1">
                	<li><a href="./UserListAction.ac"  id="user_list_sub_menu"><img src="./images/admin/menu_admin_sub05_off.gif"  alt="ȸ������"  border="0" /></a></li>
                    <li><a href="./UserNoApprovalListAction.ac" id="wait_user_list_sub_menu"><img src="./images/admin/menu_admin_sub07_off.gif"  alt="���δ��"  border="0" /></a></li>
                    <li><a href="./QuiescenceListAction.ac" id="no_visit_user_list_sub_menu"><img src="./images/admin/menu_admin_sub06_off.gif"  alt="�̻��ȸ��"  border="0" /></a></li>
            	</ul>
            </li>    
            <li class="gnb_sub">
            	<a href="./AllSendListAction.sm" id="top_menu2"><img src="./images/admin/menu02_admin_off.gif" alt="��������" border="0"/></a>
                <ul class="gnb_sub2">
                    <li><a href="./AllSendListAction.sm"  id="all_send_result_list_sub_menu"><img src="./images/admin/menu_admin_sub08_off.gif"  alt="�߼۳���" border="0"/></a></li>
                    <li><a href="./AllReserveListAction.sm"  id="all_reserved_list_sub_menu"><img src="./images/admin/menu_admin_sub09_off.gif"  alt="���೻��" border="0"/></a></li>
                    <li><a href="./AllStatisticsAction.sm"  id="stats_sub_menu"><img src="./images/admin/menu_admin_sub10_off.gif"  alt="���" border="0"/></a></li>                    
                </ul>
            </li>
            <li class="gnb_sub">
            	<a href="./DeptManageViewAction.ac"  id="top_menu3"><img src="./images/admin/menu03_admin_off.gif" alt="�μ�����" border="0"/></a>
                <ul class="gnb_sub3">
                	<li><a href="./DeptManageViewAction.ac"  id="dept_sub_menu"><img src="./images/admin/menu_admin_sub11_off.gif"  alt="�μ�����" border="0"/></a></li>
                </ul>
            </li>
            <li class="gnb_sub">
            	<a href="./AdminNoticeListAction.bo" id="top_menu4"><img src="./images/admin/menu04_admin_off.gif" alt="��������" border="0"/></a>
            	<ul class="gnb_sub4">
                	<li><a href="./AdminNoticeListAction.bo"  id="notice_sub_menu"><img  src="./images/admin/menu_admin_sub03_off.gif"  alt="��������" border="0"/></a></li>                    	
                	<li><a href="./AdminBoardListAction.bo"  id="board_sub_menu"><img  src="./images/admin/menu_admin_sub04_off.gif"  alt="����" border="0"/></a></li>
                </ul>            	
            </li>
        </ul>
    </div>