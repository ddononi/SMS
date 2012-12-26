// -----------------------------------------------------------------------------
// 주민번호 체크 - arguments[0] : 주민번호 구분자
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
	// 생년월일 체크
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
// 이메일의 유효성을 체크
// @return : boolean
// -----------------------------------------------------------------------------
String.prototype.isEmail = function() {
	return (/\w+([-+.]\w+)*@\w+([-.]\w+)*\.[a-zA-Z]{2,4}$/).test(this.trim());
};
// -----------------------------------------------------------------------------
// 전화번호 체크 - arguments[0] : 전화번호 구분자
// @return : boolean
// -----------------------------------------------------------------------------
String.prototype.isPhone = function() {
	var arg = arguments[0] ? arguments[0] : "";
	return eval("(/(02|0[3-9]{1}[0-9]{1})" + arg + "[1-9]{1}[0-9]{2,3}" + arg
			+ "[0-9]{4}$/).test(this)");
};
// -----------------------------------------------------------------------------
// 핸드폰번호 체크 - arguments[0] : 핸드폰 구분자
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

// 스펙트럼 효과주기
$.fn.spectrum = function() {
	return this.each(function() {
		$(this).css(
				'color',
				'rgb(' + (Math.floor(Math.random() * 256)) + ','
						+ (Math.floor(Math.random() * 256)) + ','
						+ (Math.floor(Math.random() * 256)) + ')');
	});
};

// 휴대폰 번호에 (-)하이픈 넣기
$.fn.addHyphen = function() {

	return this.each(function() {
		var phone;
		if ($(this).is("input")) {
			phone = $.trim($(this).val());
		} else {
			phone = $.trim($(this).text());
		}

		// 하이픈이 있으면 먼저 제거
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

// 전체 체크 활성화/ 비활성화
jQuery.fn.selectAll = function(check) {
	var flag = check;
	return this.each(function() {
		this.checked = flag;
	});
};

var ENTER_KEY = 13; // Enter keycode 값
// 주완 유틸리티 함수
jQuery.myUtil = {

	CurrenttTime : null, // 현재 시간 변수

	// 현재 시각
	clock : function() {
		var d = new Date();
		$('#currentTime').text("현재시각 : " + d.toLocaleTimeString());
		CurrenttTime = setTimeout("$.myUtil.clock()", 1000);
	},

	/**
	 * 디버그창 alert의 번거로움을 없애기 위해 동적 div를 생성하여 가변 인자를 받아 화면 하단에 각 해당 결과값을 보여줌
	 * 10.27 디버그 창 닫기 버튼 생성
	 */
	debug : function() {
		var divHtml = "<div style='width:100%;height=100px baccolor:4f70ca;"
				+ "background-color: #f7f7f7; border: 3px solid red;"
				+ "position:absolute; left:0px; bottom: 10px;padding:30px;' >"
				+ "<h4>디버그창<a href='#' id='debugClose'>(___창닫기___)</a></h4><br/><br/>";
		for ( var i = 0; i < $.myUtil.debug.arguments.length; i++)
			divHtml += " <strong>값[" + (i + 1) + "] :</strong>  "
					+ $.myUtil.debug.arguments[i] + "<br/>";

		divHtml += "</div>";
		$("body").append(divHtml);
		$("#debugClose").live("click", function() {
			$(this).parent().parent().hide("500");
		});
	},

	/**
	 * 메세지 예약 전송 결과 알림창 메세지가 전송 결과를 받아 해당 전송결과를 우측 하단 알림창으로 알려줌 서버부하 및 클라이언트의 과다
	 * 스크립트 처리를 방지하기 위해 적정 시간으로 타이머 설정을 해줌.
	 */
	smsResult : function(setTime) {
		$.messager.show({
			title : '메세지 전송 결과',
			msg : '메세지가 정상적으로 전송되었습니다.',
			timeout : 3000,
			showType : 'slide'
		});
		setTimeout("$.myUtil.smsResult(" + setTime + ")", setTime);
	},

	/**
	 * 자동로그아웃 타이머를 이용해 카운터를 발생한후 일정 카운트가 되면 로그아웃 페이지로 이동 만약 마우스 이동이나 키 이벤트가
	 * 발생할경우는 카운터는 다시 초기화 된다.
	 */
	t : null, // 타이터 객체
	logoutCount : 0, // 로그아웃 카운터
	logoutTime : 60, // 로그아웃 초 6 = 1min ,6*10min
	autoLogout : function() {
		t = setTimeout("$.myUtil.autoLogout()", 10000); // 10초마다 체크
		$.myUtil.logoutCount++;
		if ($.myUtil.logoutCount == $.myUtil.logoutTime) {
			window.location.href = "./login/autologoutInfo.jsp"
		}
	},
	logoutCountReset : function() { // 마우스,키 이벤트 감지후 카운터 리셋
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
 * CurrenttTime = setTimeout("tictoc()", 1000); return this.text( "현재시간 : " +
 * d.toLocaleTimeString()); };
 */

/*
 * 사용자 정의 함수로써 숫자만 입력 허용 숫자 이외의 키값은 false 처리
 */
jQuery.fn.inputNumber = function() {
	return this.each(function() {
		jQuery(this).keydown(
				function(e) {
					/*
					 * 방향키: 37~40, 숫자열 0 ~ 9 : 48 ~ 57, 키패드 0 ~ 9 : 96 ~ 105 , 8 :
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
		$(this).find("tbody tr").mouseover(function() {// 마우스오버시 효과 설정
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

// 비밀번호 체크
function checkPassword(uid, upw) {
	if (!/([a-zA-Z0-9].*[!,@,#,$,%,^,&,*,?,_,~])|([!,@,#,$,%,^,&,*,?,_,~].*[a-zA-Z0-9]){8,20}$/
			.test(upw)) {
		alert('비밀번호는 숫문자, 숫자, 특수문자의 조합을 8~20자리를 사용해야 합니다.');
		return false;
	}

	var chk_num = upw.search(/[0-9]/g);
	var chk_eng = upw.search(/[a-z]/ig);
	var chk_spe = upw.search(/[~!@\#$%^&*\()\=+_']/ig);

	if (chk_num < 0 || chk_eng < 0 || chk_spe < 0) {
		alert('비밀번호는 문자, 숫자, 특수문자의 조합을 혼용하여야 합니다.');
		return false;
	}

	if (/(\w)\1\1\1/.test(upw)) {
		alert('비밀번호에 같은 문자를 4번 이상 사용하실 수 없습니다.');
		return false;
	}

	if (upw.search(uid) > -1) {
		alert('ID가 포함된 비밀번호는 사용하실 수 없습니다.');
		return false;
	}

	return true;
}

// 전화번호 자동 하이픈
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
 * 현재 날짜/시간 YYYY-MM-DD hh:mm:ss 포맷
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
 * CAPS LOCK키 켜지있는지 체크
 * 
 * @param e
 */
function checkCapsLock(e) {
	var myKeyCode = 0;
	var myShiftKey = false;
	var myMsg = 'Caps Lock이 켜져있습니다. 확인하세요!';

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

// 커서 위치 설정 메소드
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

// 클립보드에 복사
function clipboard(str) {
	window.clipboardData.setData('Text', str);
	alert("클립보드에 복사되었습니다.");
}

$(document).ready(function() {
	$.myUtil.clock();
	$("input, select").inputOver(); // focus 효과
	$("table.board tbody tr:odd").addClass("alt");

	$("body").css("min-width", "800px");
	$.myUtil.autoLogout(); // 자동로그아웃
	$.myUtil.logoutCountReset();

	// 사용자가 웹브라우저를 종료 했을경우
	// $(window).onBeforeUnload(function() {
	// $.post("../logout.jsp");
	// });
	// 예약 메세지 전송 결과 함수 -- 추후 구현 예정 스크립트 처리로 인해 속도 저하시 폐기
	// setTimeout("$.myUtil.smsResult(" + 10000 + ")", 5000); // 5초 후에
});
