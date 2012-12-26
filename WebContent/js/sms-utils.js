// -----------------------------------------------------------------------------
// �ֹι�ȣ üũ - arguments[0] : �ֹι�ȣ ������
// XXXXXX-XXXXXXX
// @return : boolean
// -----------------------------------------------------------------------------
String.prototype.isJumin = function() {
	var arg = arguments[0] ? arguments[0] : "";
	var jumin = eval("this.match(/[0-9]{2}[01]{1}[0-9]{1}[0123]{1}[0-9]{1}"
			+ arg + "[1234]{1}[0-9]{6}$/)");
	if (jumin == null) {
		return false;
	} else {
		jumin = jumin.toString().num().toString();
	}
	// ������� üũ
	var birthYY = (parseInt(jumin.charAt(6)) == (1 || 2)) ? "19" : "20";
	birthYY += jumin.substr(0, 2);
	var birthMM = jumin.substr(2, 2) - 1;
	var birthDD = jumin.substr(4, 2);
	var birthDay = new Date(birthYY, birthMM, birthDD);
	if (birthDay.getYear() % 100 != jumin.substr(0, 2)
			|| birthDay.getMonth() != birthMM || birthDay.getDate() != birthDD) {
		return false;
	}
	var sum = 0;
	var num = [ 2, 3, 4, 5, 6, 7, 8, 9, 2, 3, 4, 5 ];
	var last = parseInt(jumin.charAt(12));
	for ( var i = 0; i < 12; i++) {
		sum += parseInt(jumin.charAt(i)) * num[i];
	}
	return ((11 - sum % 11) % 10 == last) ? true : false;
};

// -----------------------------------------------------------------------------
// �̸����� ��ȿ���� üũ
// @return : boolean
// -----------------------------------------------------------------------------
String.prototype.isEmail = function() {
	return (/\w+([-+.]\w+)*@\w+([-.]\w+)*\.[a-zA-Z]{2,4}$/).test(this.trim());
};
// -----------------------------------------------------------------------------
// ��ȭ��ȣ üũ - arguments[0] : ��ȭ��ȣ ������
// @return : boolean
// -----------------------------------------------------------------------------
String.prototype.isPhone = function() {
	var arg = arguments[0] ? arguments[0] : "";
	return eval("(/(02|0[3-9]{1}[0-9]{1})" + arg + "[1-9]{1}[0-9]{2,3}" + arg
			+ "[0-9]{4}$/).test(this)");
};
// -----------------------------------------------------------------------------
// �ڵ�����ȣ üũ - arguments[0] : �ڵ��� ������
// @return : boolean
// -----------------------------------------------------------------------------
String.prototype.isMobile = function() {
	var arg = arguments[0] ? arguments[0] : "";
	return eval("(/01[016789]" + arg + "[1-9]{1}[0-9]{2,3}" + arg
			+ "[0-9]{4}$/).test(this)");
};

jQuery.fn.util = {
	selectAll : function(check) {
		var flag = check;
		$("input:checkbox").each(function() {
			this.checked = flag;
		});
	},
	spectrum : function() {
		return $(this).css(
				'rgb(' + (Math.floor(Math.random() * 256)) + ','
						+ (Math.floor(Math.random() * 256)) + ','
						+ (Math.floor(Math.random() * 256)) + ')');
	}
};

// ����Ʈ�� ȿ���ֱ�
$.fn.spectrum = function() {
	return this.each(function() {
		$(this).css(
				'color',
				'rgb(' + (Math.floor(Math.random() * 256)) + ','
						+ (Math.floor(Math.random() * 256)) + ','
						+ (Math.floor(Math.random() * 256)) + ')');
	});
};

// �޴��� ��ȣ�� (-)������ �ֱ�
$.fn.addHyphen = function() {

	return this.each(function() {
		var phone;
		if ($(this).is("input")) {
			phone = $.trim($(this).val());
		} else {
			phone = $.trim($(this).text());
		}

		// �������� ������ ���� ����
		phone = phone.split("-").join("");
		;
		if (phone.length < 11 && phone.length > 7) {
			phone = phone.substr(0, 3) + "-" + phone.substr(3, 3) + "-"
					+ phone.substr(6);
		} else if (phone.length >= 11) {
			phone = phone.substr(0, 3) + "-" + phone.substr(3, 4) + "-"
					+ phone.substr(7);
		}

		$(this).val(phone);
		if ($(this).is("input")) {
			$(this).val(phone);
		} else {
			$(this).text(phone);
		}
	});
};

// ��ü üũ Ȱ��ȭ/ ��Ȱ��ȭ
jQuery.fn.selectAll = function(check) {
	var flag = check;
	return this.each(function() {
		this.checked = flag;
	});
};

var ENTER_KEY = 13; // Enter keycode ��
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
	if (!/([a-zA-Z0-9].*[!,@,#,$,%,^,&,*,?,_,~])|([!,@,#,$,%,^,&,*,?,_,~].*[a-zA-Z0-9]){8,20}$/
			.test(upw)) {
		alert('��й�ȣ�� ������, ����, Ư�������� ������ 8~20�ڸ��� ����ؾ� �մϴ�.');
		return false;
	}

	var chk_num = upw.search(/[0-9]/g);
	var chk_eng = upw.search(/[a-z]/ig);
	var chk_spe = upw.search(/[~!@\#$%^&*\()\=+_']/ig);

	if (chk_num < 0 || chk_eng < 0 || chk_spe < 0) {
		alert('��й�ȣ�� ����, ����, Ư�������� ������ ȥ���Ͽ��� �մϴ�.');
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

/**
 * ���� ��¥/�ð� YYYY-MM-DD hh:mm:ss ����
 * 
 * @returns {String}
 */
function getTimeStamp() {
	var d = new Date();

	var s = leadingZeros(d.getFullYear(), 4) + '-'
			+ leadingZeros(d.getMonth() + 1, 2) + '-'
			+ leadingZeros(d.getDate(), 2) + ' ' +

			leadingZeros(d.getHours(), 2) + ':'
			+ leadingZeros(d.getMinutes(), 2) + ':'
			+ leadingZeros(d.getSeconds(), 2);

	return s;
}

function leadingZeros(n, digits) {
	var zero = '';
	n = n.toString();

	if (n.length < digits) {
		for ( var i = 0; i < digits - n.length; i++)
			zero += '0';
	}
	return zero + n;
}

/**
 * CAPS LOCKŰ �����ִ��� üũ
 * 
 * @param e
 */
function checkCapsLock(e) {
	var myKeyCode = 0;
	var myShiftKey = false;
	var myMsg = 'Caps Lock�� �����ֽ��ϴ�. Ȯ���ϼ���!';

	// Internet Explorer 4+
	if (document.all) {
		myKeyCode = e.keyCode;
		myShiftKey = e.shiftKey;

		// Netscape 4
	} else if (document.layers) {
		myKeyCode = e.which;
		myShiftKey = (myKeyCode == 16) ? true : false;

		// Netscape 6
	} else if (document.getElementById) {
		myKeyCode = e.which;
		myShiftKey = (myKeyCode == 16) ? true : false;

	}

	// Upper case letters are seen without depressing the Shift key, therefore
	// Caps Lock is on
	if ((myKeyCode >= 65 && myKeyCode <= 90) && !myShiftKey) {
		alert(myMsg);

		// Lower case letters are seen while depressing the Shift key, therefore
		// Caps Lock is on
	} else if ((myKeyCode >= 97 && myKeyCode <= 122) && myShiftKey) {
		alert(myMsg);

	}
}

$.fn.setCursorPosition = function(pos) {
	this.each(function(index, elem) {
		if (elem.setSelectionRange) {
			elem.setSelectionRange(pos, pos);
		} else if (elem.createTextRange) {
			var range = elem.createTextRange();
			range.collapse(true);
			range.moveEnd('character', pos);
			range.moveStart('character', pos);
			range.select();
		}
	});
	return this;
};

// Ŀ�� ��ġ ���� �޼ҵ�
function setSelectionRange(input, selectionStart, selectionEnd) {
	if (input.setSelectionRange) {
		input.focus();
		input.setSelectionRange(selectionStart, selectionEnd);
	} else if (input.createTextRange) {
		var range = input.createTextRange();
		range.collapse(true);
		range.moveEnd('character', selectionEnd);
		range.moveStart('character', selectionStart);
		range.select();
	}
}

function getCursorPos(input) {
	if ("selectionStart" in input && document.activeElement == input) {
		return {
			start : input.selectionStart,
			end : input.selectionEnd
		};
	} else if (input.createTextRange) {
		var sel = document.selection.createRange();
		if (sel.parentElement() === input) {
			var rng = input.createTextRange();
			rng.moveToBookmark(sel.getBookmark());
			for ( var len = 0; rng.compareEndPoints("EndToStart", rng) > 0; rng
					.moveEnd("character", -1)) {
				len++;
			}
			rng.setEndPoint("StartToStart", input.createTextRange());
			for ( var pos = {
				start : 0,
				end : len
			}; rng.compareEndPoints("EndToStart", rng) > 0; rng.moveEnd(
					"character", -1)) {
				pos.start++;
				pos.end++;
			}
			return pos;
		}
	}
	return -1;
}

// Ŭ�����忡 ����
function clipboard(str) {
	window.clipboardData.setData('Text', str);
	alert("Ŭ�����忡 ����Ǿ����ϴ�.");
}

$(document).ready(function() {
	$.myUtil.clock();
	$("input, select").inputOver(); // focus ȿ��
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
