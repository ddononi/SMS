var ENTER_KEY = 13;   // Enter  keycode ��

// �ֿ� ��ƿ��Ƽ �Լ�
jQuery.myUtil = {
		
	CurrenttTime : null, // ���� �ð� ����
	
	// ���� �ð�
	clock : function() {
		var d = new Date();
		$('#currentTime').text("����ð� : " + d.toLocaleTimeString() );
		CurrenttTime = setTimeout("$.myUtil.clock()", 1000);
	},
	
	/**
	 *	 �����â alert�� ���ŷο��� ���ֱ� ���� 
	 *	���� div�� �����Ͽ� ���� ���ڸ� �޾�  ȭ�� �ϴܿ� �� �ش� ������� ������
	 *	10.27 ����� â �ݱ� ��ư ����
	 */
	debug : function() {
		var divHtml = "<div style='width:100%;height=100px baccolor:4f70ca;" +
					"background-color: #f7f7f7; border: 3px solid red;" +
					"position:absolute; left:0px; bottom: 10px;padding:30px;' >" +
					"<h4>�����â<a href='#' id='debugClose'>(___â�ݱ�___)</a></h4><br/><br/>";		
	    for(var i=0;i< $.myUtil.debug.arguments.length;i++)
	    	 divHtml += " <strong>��[" + (i+1) + "] :</strong>  " + $.myUtil.debug.arguments[i] +"<br/>";
	    	 
	    divHtml += "</div>";
		$("body").append(divHtml);
		$("#debugClose").live("click", function(){
			$(this).parent().parent().hide("500");
		});
	},
	
	/**
	 * �޼��� ���� ���� ��� �˸�â 
	 * �޼����� ���� ����� �޾� �ش� ���۰���� ���� �ϴ� �˸�â���� �˷���
	 * �������� �� Ŭ���̾�Ʈ�� ���� ��ũ��Ʈ ó���� �����ϱ� ���� ���� �ð�����
	 * Ÿ�̸� ������ ����.
	 */
	smsResult : function( setTime ) {
		$.messager.show({
			title:'�޼��� ���� ���',
			msg:'�޼����� ���������� ���۵Ǿ����ϴ�.',
			timeout:3000,
			showType:'slide'
		});
		setTimeout("$.myUtil.smsResult(" + setTime + ")", setTime);
	},
	
	
	/**
	 * �ڵ��α׾ƿ� 
	 * Ÿ�̸Ӹ� �̿��� ī���͸� �߻����� ���� ī��Ʈ�� �Ǹ� �α׾ƿ� �������� �̵�
	 * ���� ���콺 �̵��̳� Ű �̺�Ʈ�� �߻��Ұ��� ī���ʹ� �ٽ� �ʱ�ȭ �ȴ�.
	 */
	t : null,	//Ÿ���� ��ü
	logoutCount : 0, // �α׾ƿ� ī����
	logoutTime : 60,	// �α׾ƿ� ��	 6 = 1min ,6*10min
	autoLogout : function(){
		t =setTimeout("$.myUtil.autoLogout()",10000);	// 10�ʸ��� üũ
		$.myUtil.logoutCount++;
		if($.myUtil.logoutCount == $.myUtil.logoutTime){
			window.location.href="./login/autologoutInfo.jsp"
		}
	},
	logoutCountReset : function(){	// ���콺,Ű �̺�Ʈ ������ ī���� ����
		$("*").mousemove(function(e){
			$.myUtil.logoutCount = 0;
		});
		
		$("*").keypress(function(e){
			$.myUtil.logoutCount = 0;
		});		
	},
	// ����Ű �Է½� �� ���� ������ ����
	doNotEnterKey : function(){
		$(document).keydown(function(e) {
			if( e.keyCode == 13 ){
				return false;
			}
		});
	}
}

/*
jQuery.fn.tictoc = function() {
	var d = new Date();
	var that = this;
	CurrenttTime = setTimeout("tictoc()", 1000);
	return this.text( "����ð� : " + d.toLocaleTimeString());
};
*/

/*
 * ����� ���� �Լ��ν� ���ڸ� �Է� ���   
 * ���� �̿��� Ű���� false ó��
 */
jQuery.fn.inputNumber = function() {
	return this.each(function(){
		jQuery(this).keydown(function(e) {
			/* ����Ű: 37~40, ���ڿ� 0 ~ 9 : 48 ~ 57, Ű�е� 0 ~ 9 : 96 ~ 105 ,
			 * 8 : backspace, 9 : tab, 46 : delete
			 */
			if (
					e.keyCode >= 37 && e.keyCode <= 40 ||
					e.keyCode >= 48 && e.keyCode <= 57 ||
					e.keyCode >= 96 && e.keyCode <= 105 ||
					e.keyCode == 8 ||
					e.keyCode == 9 ||
					e.keyCode == 46
			) {
				return true;
			} else {
				if (jQuery.browser.msie) {
					return false;
				} else {
					e.preventDefault();
					return false;
				}
			}
		});
	});	
};

// input mouse hover effect
jQuery.fn.inputOver = function() {
	return this.each(function(){
		var that = $(this);
		that.focusin(function() {
			that.addClass("inputFocus");
	    }).focusout(function() {
	    	that.removeClass("inputFocus");
	    });
		//css( { "border":'2px solid #38A8E7', "background": "#EDFAFE"});
	});
}

jQuery.fn.border = function() {	//   to make border
	return this.each(function(){
	  $(this).css( { "border":'1px solid red'});
	});
}
//table mouse hover effect
jQuery.fn.tableHover = function() {
	return this.each(function(){
		$(this).find("tbody tr").mouseover(function(){// ���콺������ ȿ�� ����
				$(this).children('td').addClass("over");
					//$(this).children('td').children('img').addClass("over");
				}).mouseout(function(){
					$(this).children('td').removeClass("over");
					//$(this).children('td').children('img').removeClass("over");
			});
		});
},

// to locate to center
jQuery.fn.toCenter = function(){
	return this.attr("align", "center");
};

// ��й�ȣ üũ
function checkPassword(pwd){
    reg1 = /^[a-z\d]{6,12}$/i;  //a-z�� 0-9�̿��� ���ڰ� �ִ��� Ȯ��
    reg2 = /[a-z]/i;  //��� �Ѱ��� a-z Ȯ��
    reg3 = /\d/;  //��� �Ѱ��� 0-9 Ȯ��
    return reg1.test(pwd) && reg2.test(pwd) && reg3.test(pwd);
} 


$(document).ready(function(){
	var logoutCount = 0;	//  �α׾ƿ� ī��Ʈ
	$.myUtil.clock();
	$("input, textarea, select").inputOver(); // focus ȿ��
	$.myUtil.doNotEnterKey();
	$("table.board tbody tr:odd").addClass("alt");
	
	$("body").css("min-width","800px");
	$.myUtil.autoLogout();	// �ڵ��α׾ƿ�
	$.myUtil.logoutCountReset();
	
	// ����ڰ� ���������� ���� �������
	//$(window).onBeforeUnload(function() {
	//	  $.post("../logout.jsp");
	//});
	// ���� �޼��� ���� ��� �Լ� -- ���� ���� ���� ��ũ��Ʈ ó���� ���� �ӵ� ���Ͻ� ���
	//setTimeout("$.myUtil.smsResult(" + 10000 + ")", 5000); // 5�� �Ŀ�
});

