var ENTER_KEY = 13; // Enter keycode ��

jQuery.fn.util = {
		selectAll : function(check){
			var flag = check;
			$("input:checkbox").each(function(){
				this.checked = flag;
			});
		},
		spectrum : function(){  
		   return $(this).css('rgb(' + (Math.floor(Math.random() * 256)) + ',' + (Math.floor(Math.random() * 256)) + ',' + (Math.floor(Math.random() * 256)) + ')');  
		 }
};

// ����Ʈ�� ȿ���ֱ�
$.fn.spectrum = function(){
	return this.each(function(){
		$(this).css('color', 'rgb(' + (Math.floor(Math.random() * 256)) + ',' + (Math.floor(Math.random() * 256)) + ',' + (Math.floor(Math.random() * 256)) + ')');  
	});
};

// ��ü üũ Ȱ��ȭ/ ��Ȱ��ȭ
jQuery.fn.selectAll = function(check){
	var flag = check;	
	return this.each(function(){
		this.checked = flag;
	});
};

// �ֿ� ��ƿ��Ƽ �Լ�
jQuery.myUtil = {

	CurrenttTime : null, // ���� �ð� ����

	// ���� �ð�
	clock : function() {
		var d = new Date();
		$('#currentTime').text("����ð� : " + d.toLocaleTimeString());
		CurrenttTime = setTimeout("$.myUtil.clock()", 1000);
	},

	/**
	 * �����â alert�� ���ŷο��� ���ֱ� ���� ���� div�� �����Ͽ� ���� ���ڸ� �޾� ȭ�� �ϴܿ� �� �ش� ������� ������
	 * 10.27 ����� â �ݱ� ��ư ����
	 */
	debug : function() {
		var divHtml = "<div style='width:100%;height=100px baccolor:4f70ca;"
				+ "background-color: #f7f7f7; border: 3px solid red;"
				+ "position:absolute; left:0px; bottom: 10px;padding:30px;' >"
				+ "<h4>�����â<a href='#' id='debugClose'>(___â�ݱ�___)</a></h4><br/><br/>";
		for ( var i = 0; i < $.myUtil.debug.arguments.length; i++)
			divHtml += " <strong>��[" + (i + 1) + "] :</strong>  "
					+ $.myUtil.debug.arguments[i] + "<br/>";

		divHtml += "</div>";
		$("body").append(divHtml);
		$("#debugClose").live("click", function() {
			$(this).parent().parent().hide("500");
		});
	},

	/**
	 * �޼��� ���� ���� ��� �˸�â �޼����� ���� ����� �޾� �ش� ���۰���� ���� �ϴ� �˸�â���� �˷��� �������� �� Ŭ���̾�Ʈ�� ����
	 * ��ũ��Ʈ ó���� �����ϱ� ���� ���� �ð����� Ÿ�̸� ������ ����.
	 */
	smsResult : function(setTime) {
		$.messager.show({
			title : '�޼��� ���� ���',
			msg : '�޼����� ���������� ���۵Ǿ����ϴ�.',
			timeout : 3000,
			showType : 'slide'
		});
		setTimeout("$.myUtil.smsResult(" + setTime + ")", setTime);
	},

	/**
	 * �ڵ��α׾ƿ� Ÿ�̸Ӹ� �̿��� ī���͸� �߻����� ���� ī��Ʈ�� �Ǹ� �α׾ƿ� �������� �̵� ���� ���콺 �̵��̳� Ű �̺�Ʈ��
	 * �߻��Ұ��� ī���ʹ� �ٽ� �ʱ�ȭ �ȴ�.
	 */
	t : null, // Ÿ���� ��ü
	logoutCount : 0, // �α׾ƿ� ī����
	logoutTime : 60, // �α׾ƿ� �� 6 = 1min ,6*10min
	autoLogout : function() {
		t = setTimeout("$.myUtil.autoLogout()", 10000); // 10�ʸ��� üũ
		$.myUtil.logoutCount++;
		if ($.myUtil.logoutCount == $.myUtil.logoutTime) {
			window.location.href = "./login/autologoutInfo.jsp"
		}
	},
	logoutCountReset : function() { // ���콺,Ű �̺�Ʈ ������ ī���� ����
		$("*").mousemove(function(e) {
			$.myUtil.logoutCount = 0;
		});

		$("*").keypress(function(e) {
			$.myUtil.logoutCount = 0;
		});
	},
	// ����Ű �Է½� �� ���� ������ ����
	doNotEnterKey : function() {
		$(document).keydown(function(e) {
			if (e.keyCode == 13) {
				return false;
			}
		});
	}
}

/*
 * jQuery.fn.tictoc = function() { var d = new Date(); var that = this;
 * CurrenttTime = setTimeout("tictoc()", 1000); return this.text( "����ð� : " +
 * d.toLocaleTimeString()); };
 */

/*
 * ����� ���� �Լ��ν� ���ڸ� �Է� ��� ���� �̿��� Ű���� false ó��
 */
jQuery.fn.inputNumber = function() {
	return this.each(function() {
		jQuery(this).keydown(
				function(e) {
					/*
					 * ����Ű: 37~40, ���ڿ� 0 ~ 9 : 48 ~ 57, Ű�е� 0 ~ 9 : 96 ~ 105 , 8 :
					 * backspace, 9 : tab, 46 : delete
					 */
					if (e.keyCode >= 37 && e.keyCode <= 40 || e.keyCode >= 48
							&& e.keyCode <= 57 || e.keyCode >= 96
							&& e.keyCode <= 105 || e.keyCode == 8
							|| e.keyCode == 9 || e.keyCode == 46) {
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
	return this.each(function() {
		var that = $(this);
		that.focusin(function() {
			that.addClass("inputFocus");
		}).focusout(function() {
			that.removeClass("inputFocus");
		});
		// css( { "border":'2px solid #38A8E7', "background": "#EDFAFE"});
	});
}

jQuery.fn.border = function() { // to make border
	return this.each(function() {
		$(this).css({
			"border" : '1px solid red'
		});
	});
}
// table mouse hover effect
jQuery.fn.tableHover = function() {
	return this.each(function() {
		$(this).find("tbody tr").mouseover(function() {// ���콺������ ȿ�� ����
			$(this).children('td').addClass("over");
			// $(this).children('td').children('img').addClass("over");
		}).mouseout(function() {
			$(this).children('td').removeClass("over");
			// $(this).children('td').children('img').removeClass("over");
		});
	});
},

// to locate to center
jQuery.fn.toCenter = function() {
	return this.attr("align", "center");
};

// ��й�ȣ üũ
function checkPassword(uid, upw) {
	if (!/^[a-zA-Z0-9]{8,20}$/.test(upw)) {
		alert('��й�ȣ�� ���ڿ� ������ �������� 8~20�ڸ��� ����ؾ� �մϴ�.');
		return false;
	}

	var chk_num = upw.search(/[0-9]/g);
	var chk_eng = upw.search(/[a-z]/ig);
	if (chk_num < 0 || chk_eng < 0) {
		alert('��й�ȣ�� ���ڿ� �����ڸ� ȥ���Ͽ��� �մϴ�.');
		return false;
	}

	if (/(\w)\1\1\1/.test(upw)) {
		alert('��й�ȣ�� ���� ���ڸ� 4�� �̻� ����Ͻ� �� �����ϴ�.');
		return false;
	}
	if (upw.search(uid) > -1) {
		alert('ID�� ���Ե� ��й�ȣ�� ����Ͻ� �� �����ϴ�.');
		return false;
	}

	return true;
}

// ��ȭ��ȣ �ڵ� ������
function checkHyphen(obj) {
	obj.value = obj.value.replace(/\-/g, "");
	obj.select();

	if (!obj.onblur) {
		obj.onblur = function() {
			this.value = this.value.replace(/\-/g, "");
			var str = this.value;
			var l = str.length;
			if (l > 2) {
				if (l > 7)
					str = str.substr(0,
							(k = (str.substr(0, 2) == '02') ? 2 : 3))
							+ '-'
							+ str.substr(k, l - 4 - k)
							+ '-'
							+ str.substr(l - 4);
				else
					str = str.substr(0, 3) + '-' + str.substr(3);
			}
			this.value = str;
		};
	}
}

$(document).ready(function() {
	var logoutCount = 0; // �α׾ƿ� ī��Ʈ
	$.myUtil.clock();
	$("input, textarea, select").inputOver(); // focus ȿ��
	$.myUtil.doNotEnterKey();
	$("table.board tbody tr:odd").addClass("alt");

	$("body").css("min-width", "800px");
	$.myUtil.autoLogout(); // �ڵ��α׾ƿ�
	$.myUtil.logoutCountReset();

	// ����ڰ� ���������� ���� �������
	// $(window).onBeforeUnload(function() {
	// $.post("../logout.jsp");
	// });
	// ���� �޼��� ���� ��� �Լ� -- ���� ���� ���� ��ũ��Ʈ ó���� ���� �ӵ� ���Ͻ� ���
	// setTimeout("$.myUtil.smsResult(" + 10000 + ")", 5000); // 5�� �Ŀ�
});
