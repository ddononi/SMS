/**
 * 	sms 
 *  ���� ���� �߼� ������ 
 *  main.jsp
 */

var phoneReg =  /^01([0|1|6|7|8|9]?)-?([0-9]{3,4})-?([0-9]{4})$/;	// �ڵ��� ���Խ�
// �� ���Խ� �� �޼��� ����
jQuery.phone = {
	smsMode : true,	// sms �������		
	/*
 	 *	���Ͱ� üũ
	 */
	checkEnter : function(buf){	// enter üũ
		var n = 0
		var Is_one_char = "";
		for(var i=0; i<buf.length; i++){
			//encoding
			var hex = escape(buf.substring(i,i + 2));
			Is_one_char = buf.charAt(i);
			
			if(escape(Is_one_char).length > 4)// enter�̸�
			{
				n += 2;
			}else{// enter �ƴϸ�
				n++;
			}
		}
		return n;	
	},
	/*
	 * ���� �޼������� �� üũ 
	 * Ű���� �޾� �ڵ尪�� �ѱ��� ��� ���ڰ� byte�� �Ǳ� ������ 2���ڷ� ó�� 
	 */
	checkLength : function(event){	// ���� �޽��� 80�� ����
		var that = $("#f_message");
		var textThat = $("#textByte");
		var buf = that.val();
		var encodedStr = $.phone.checkEnter(buf);
		var l = 0;

		// sms ����̸�
		if($.phone.smsMode){
			if ( encodedStr > 80) {
				if(event.keyCode == 8 || event.keyCode ==46){
					return false;
				}
				
		        for (var i=0; i< buf.length; i++) { 
	                l += (buf.charCodeAt(i) > 128) ? 2 : 1; //  �ѱ� ó�� 
	                if (l > 80){
	                	$.phone.smsMode = false;
		                alert( "80�� �̻� �Է½� MMS�� ��ȯ �˴ϴ�..");
		                 that.val( buf.substring(0,80) );
		                 $(".choice li input").first().attr('checked', '');		                 
		                 $(".choice li input").last().attr('checked', 'chekced');
	                }
		        }
			}else{
				textThat.text( encodedStr + "/80Bytes" );
			}
		// mms ����̸�	
		}else{
			if ( encodedStr > 2000) {
				if(event.keyCode == 8 || event.keyCode ==46){
					return false;
				}
				
		        for (var i=0; i< buf.length; i++) { 
	                l += (buf.charCodeAt(i) > 128) ? 2 : 1; //  �ѱ� ó�� 
	                if (l > 80){
	                	$.phone.smsMode = false;
		                alert( "���̻� �Է��Ҽ� �����ϴ�.");
		                that.val( buf.substring(0,80) );
		                // return false;
	                }
		        }
			}else{
				textThat.text( encodedStr + "/2000Bytes" );
			}
		}
		
		
	},
	/*
	 * split �� ���� , �� �ٿ����� �Է°� üũ�� �ð��� �־����� ����
	 */
	send : function(){	// ���� ����
		var remainder = parseInt( $("#freeSendCount span").text() );	// ���� �޽��� �Ǽ�
		if( remainder == 0){	// ���� �������� ������
			alert("���� �޼��� �Ǽ��� �����ϴ�.");
			return false;			
		}
		
		var phoneNumbers = "";
		var senderCount = 0; //  ������ ��� ī��Ʈ
		var firstSender = "";
		$("#pone_list li").each(function(){
			if( $(this).find("input:last").val() != "" ){
				if( senderCount == 0 ){
					firstSender = $(this).find("input:last").val();
				}
				senderCount++;
				phoneNumbers += $(this).find("input:last").val() + ",";
			}
		});

		

		// ���� �޼��� �Ǽ� �� ������ �޽��� ������ �� ó��
		if(  remainder < parseInt( senderCount ) ){
			alert("���� �޼��� �Ǽ��� Ȯ���ϼ���");
			return false;			
		}
				
		phoneNumbers = phoneNumbers.substr(0,(phoneNumbers.length-1) );
		// �Էµ� ��ȭ���� �������  false ó��
		if( phoneNumbers == "" ){
			alert("�Էµ� ��ȭ��ȣ�� �����ϴ�.");
			return false;
		}
		
		// �Էµ� ������ ������ false ó��
		if( isDefault == true || $("#f_message").val().length == 0 ){
			alert("�Էµȳ����� �����ϴ�.");
			return false;
		}
		
		var callback_num = $("#f_callback").val();
		if( callback_num.length == 0 /* || !phoneReg.test(callback_num) */ ){
			alert("�� ��ȣ�� ��Ȯ�� �Է��ϼ���.");
			return false;			
		}

		if( $.phone.spam() != true ){
			return false;
		}

		//$.phone.doNotDuplicate();
		if( $.phone.check() == true  ){
				// ��� ����
			$.phone.doNotDuplicate();	// ���� ��ȭ��ȣ ���� ó��
			$("#f_to_telnum1").val(phoneNumbers);
			if( $("#reservationTime span").text() == "" ){
				$("#f_reserve_date").val( $.date.getNow() ); 
				$("#f_reserve_time").val( $.date.getTime() ); 
			}else{
				// ���� ���۽� ���Ŀ� �°� ���ڸ� �ڸ��� ��¥ ���۰��� �־���
				var str = $("#reservationTime span").text();
				var date = str.substring(0,4)+ str.substring(5,7) + str.substring(8,10);
				var time = str.substring(12,14)+ str.substring(15,17);
				$("#f_reserve_date").val( date ); 
				$("#f_reserve_time").val( time );
			}
			var data = {
					f_id : $("#chk_ps").next().val(),
					f_to_telnum1 : $.trim($("#f_to_telnum1").val()),
					f_reserve_date : $("#f_reserve_date").val(),
					f_reserve_time : $("#f_reserve_time").val(),
					f_from_telnum : $("#f_from_telnum").val(),
					f_send_state : $("#f_send_state").val(),
					f_deptcode: $("#f_deptcode").val(),
					f_message : encodeURIComponent( $("#f_message").val() ),
					f_callback : $("#f_callback").val(),
					f_count : senderCount
				};
			/*
			 *	ajax �� sms �߼�ó��
			 */			
			if( confirm("���ڸ޼����� �߼� �Ͻðڽ��ϱ�?") == true){
				var str = (senderCount == 1)?(firstSender + "���� ���� �߼�"):
											(firstSender + "�� " + (senderCount-1) + "�� ���� �߼�");
				$.post("./ajax/send_proc.jsp", data, function(result){
					if( $.trim(result) == "true" ){
						// ���۽� ���� �ϴܿ� �˸�â�� ���
						$.messager.show({
							title: "���� �˸�â",
							msg: str ,
							timeout:3000,
							showType: "slide"
						});
						$("#freeSendCount span").text( remainder - senderCount );
						$.post("./ajax/writeLog.jsp", {msg :str + " ����", f_callback : callback_num } );	// �α�
					}else{
						alert("�߼۽���");
						$.post("./ajax/writeLog.jsp", {msg : str + "  ����", f_callback : callback_num } ); // �α�
					}
				});
			}
		}
	},
	/*
	 * �Էµ� ��ȭ��ȣ üũ
	 * �� input�� ��ȸ�ϸ�  ���Խ� ��ȿ�� �� üũ
	 * ���� ������� ������ ��ȿ�� �˻��� Ʋ�� �׸��� �ش� �ε�����ȣ�� �˷���
	 */
 	check : function() {	// �Էµ� ��ȭ��ȣ üũ
		var flag = true;
		var str = "";
		$("#pone_list li").each(function(index){
			var that = $(this).children(".rt").find(":input");
			var pNum = that.val();
			/*that.css("background", "none");*/
			if( pNum != "" ){
				if( !phoneReg.test(pNum) ){
					str += (index + 1) + "�� ";
					that.css({"background-image" : "url(./images/invalid.gif)",
							  "background-position" : "left top",
							  "background-repeat" : "no-repeat"
						});
					flag = false;
				}
			}
		});
		if( str != "" ){
			alert( str + "��ȭ��ȣ�� ��Ȯ�� �Է��ϼ���" );
		}
		return flag;
	},
	/*
	 *	��Ģ�� ó�� 
	 *	spamList���� ��Ģ����� ��ȯ�ϸ鼭 �Է��� �ؽ�Ʈ�� ��Ī���� 
	 *	��Ī���� �����ϸ� false ó��
	 */
	spam : function(){
		var str = $("#f_message").text();
		var flag = true; // ��Ģ�� ����
		$("#spamList a").each(function(){
			var spamStr = $(this).text(); // �� ��Ģ���
			if ( str.match( spamStr ) != null){ // ��Ī���� ��Ģ� ������
				alert( spamStr + "(��)�� ��Ģ���Դϴ�.");
				flag = false; 
			}
		});
		return flag;
	},
	/*
	 *
	 *
	 */
	doNotDuplicate : function(){
		var arr = new Array();
		$("#pone_list li").each(function(index){
			var that = $(this).children(".rt").find(":input");
			var pNum = that.val();
			//that.css("background", "none");
					arr.push(pNum);
		});
		var len = arr.length;
		var flag = true;
		for(var i=0; i < len -1; i++  ){
			if( arr[i] == "") continue;
			
			for(var j=i+1; j<len; j++){
				if( arr[i] == arr[j] ){
					$("#pone_list li:eq(" + i + ")").val("");
					flag = false;
					//alert(i+1 +"��°�� "+ parseInt(j+1) + "��° ��ȭ��ȣ�� �����ϴ�." );
					//return false;
				}
			} 
		}
		if( flag == false){
			alert("�ߺ���ȭ��ȣ�� �ڵ����ŵ˴ϴ�.");
		}
		
	},
	/*
	 *	excel_proc.jsp �� ���� Excel ������ �����͸� ���ڿ��� �����
	 *	insertExcel �Լ��� ���� ������ �̸��� ��ȭ��ȣ�� �־��ش�.
	 *	���� ��ȭ��ȣ�� (-)������ ���ְ� �Է� ������ �����Ѱ�� <li> ���� ������ �־��ش�.
	 */
	insertExcel : function( str ){
		str = str.substr( 0, str.length - 1 );	// ������ ���� (,)�� ����
		var arr = str.split(",");
		var len = arr.length;

		var phoneName = null;
		var phoneNum = null;
		var html = null;
		var size = $("#pone_list li").size(); // ��ȭ ��ȣ �Է� ������
		var count = 0;
		// �ش� input�ȿ� ��ȭ��ȣ�� �Է� �Ǿ����� ��� ��� ���������� ī���� ���� ��Ŵ
		
		$("input[name^='recvPhone']").each(function(index){
			if( $(this).val() != ''){
				 count = index + 1;
			}
		});

		for( var i=0; i<len; i+=2){	// �̸��� ��ȭ��ȣ �ҷ�����
			count++;
			phoneName = arr[i].toString();
			phoneNum = arr[i+1].toString().replace(/-/g,"");	// ��ȭ��ȣ ������ ����

			// Excel �ҷ������ Excel�� ���Ŀ� ���� �� ���� ���� 0�� ������ �ֱ� ������ üũ�Ͽ� 0�� �־��� 
			if( phoneNum.charAt(0) != "0" ){
				phoneNum = "0".concat(phoneNum);
			}

			if( count > size ){	// input �Է°����� ������ ���
				//count++;
				html = "<li><div class='lt'><input name='recvName"+ count + "' id='recvName"+ count + "' type='Text' class='inp'>"
								+ "<div class='nt'>" + count + "</div></div><div class='rt'>"
								+ "<input name='recvPhone" + count + "' id='recvPhone" + count + "' type='Text' class='inp'>"
								+ "<div class='bt'><img src='./images/btn_close2.gif' alt='�ݱ�'></div></div></li>";
			 
				$("#pone_list li:last").after(html);	// ���� ����	
				size++;			
			}
				$("#recvName"+count).val( phoneName );	// �̸� �ֱ�
				$("#recvPhone"+count).val( phoneNum );	// ��ȭ��ȣ �ֱ�
		}
	}	
	
}

// ajax �ε� ����
jQuery.html = {
	/*
	 * Ư�� ���� tab ���� �Լ�
	 * ��� �Լ��ν�  �ε� ó���� ���� ajax ó��  
	 * �ε尡 �Ϸ�Ǹ� tabs������ �̺�Ʈ �� ȿ�� ó���� ����
	 */		
	character : function(i){
		i = i + 1;
		var url = "./ajax/specialCharicters/character-" + i + ".html";
		var that = "#specialChar" + i;
		$(that).load(url, function(){
			if( i < 5){
				$.html.character(i);
			}else if( i == 5 ){
				$("#emoticonsTab").show();
				$("#emoticonsTab")
				.tabs("#emoticonsTab div.pane", {tabs: 'h2', effect: 'slide', initialIndex: 0});
				$("#emoticonsTab table td").mouseover(function(){
					$(this).css("color", "red");
				}).mouseout(function(){
					$(this).css("color", "#666");
				}).click(function(e){
					var message = $("#f_message");
					if(isDefault == true){
						 message.empty();
						isDefault = false;
					}
					$.phone.checkLength(e);
					message.text( message.text() + $(this).text() );
				});
			}
		});
	},
	
	/*
	 * ���� ��  ���� �޼��� �ε� �Լ� 
	 * loadBox div �� ���ڰ��� ���� �޼����ε�
	 * callback ó���� scrollable ������ �̺�Ʈ ����
	 */
	messages : function( flag, selected ){
		if( selected == "all") selected  ="";
		var data = { flag_group : flag, sms_group : selected }
		$("#loadBox").load('./ajax/messagesHTML.jsp', data, function(){
			$(this).find("#messageLoadBox").scrollable(); 
			$("#loadingImg").css("disaply","none");
			$("#messageBoxes .bx textarea").click(function(){
				isDefault = false;	// �ʱ�ȭ���� �ƴ϶�� ���� 
				$("#phoneView textarea").text( $(this).text() );//.attr("readonly","readonly");
				$("#f_message_title").val( $(this).next(".title").text() );
			});
		});
	},
	/*
	 *	���� Ű�е� ���� 
	 *	��۷� Ű�е� display ����
	 */
	NumericKeyPad : function(){
		if( NumericKeyPadFlag == true ){
			NumericKeyPadFlag = false;
			$("#controls").css("display", "none");
		}else{
			NumericKeyPadFlag = true;
			$("#controls").css("display", "block");			
		}
	}
	
}

jQuery.date = { // ���� �ð� �� ��¥ ��������
	/*
	 * ���� �ð� �������� yyymmdd
	 */
	getNow : function(){
		var d =  new Date();
		var dateStr = d.getFullYear() + getFormatMon( d.getMonth() ) + getFormatDay( d.getDate() );
		/* 
		 * ��¥ ������ 0���� �̱⶧���� 1�� ��������
		 * ���ڸ��ϰ�� ���˿� �°� 0�� �ٿ���
		 */
		function getFormatMon(m){
			m = parseInt(m)+ 1;
			if( m < 10 ){
				m = "0" + m;
			}
			return m.toString();
		};
		/* 
		 * 
		 * getFormatMon ����������  ���ڸ��� ��� 0�� �ٿ���
		 */	
		function getFormatDay(dd){
			if( dd < 10 ){
				dd = "0" + dd;
			}
			return dd.toString();
		};
		return dateStr;
		
	},
	getTime : function(){
		var d= new Date();
		var timeStr =  getFormatHour( d.getHours() ).toString() + 
						 getFormatMinutes( d.getMinutes() ).toString();

		function getFormatHour(hh){
			if( hh < 10 ){
				hh = "0" + hh;
			}
			return hh;
		};

		function getFormatMinutes(tt){
			if( tt < 10 ){
				tt = "0" + tt;
			}
			return tt;
		};
		return timeStr;
	}
}

var isDefault = true;
var sec = 500;
var NumericKeyPadFlag = false;	// Ű�е� flag
var currentPhoneInput = null;	// ���� input  ��ü��
$(document).ready(function(){

	/*
	 *	���ڸ� �Է� ���-- ��ȭ��ȣ �Է¶��� ����
	 */
	$(".rt input, #f_callback").inputNumber();

	/*
	 *	�ʱ� �޽��������� �޼��� notice������ �Է� �Ǿ��ֱ� ������ 
	 *  ����ڰ� ó�� ��Ŀ���� �ٶ� �⺻ �޽���â�� �Էµ� ���� ��������
	 */
	$("#f_message").one('click',function(){ // ����Ʈ �޼��� ����
		if( isDefault == true ){ // �⺻ ���� ���� Ȯ��
			$(this).empty();
		}
	});
	
	$("#resetTextBtn").click(function(){ // �ٽ� ���� ��ư
		$("#f_message").val("");
		$("#textByte").val("0/0Bytes");
		return false;
	});

	$("#f_message").keydown(function(e) { // �޼��� Ű �Է½� ���� Ű�� üũ
		isDefault = false;
		$.phone.checkLength(e);
	});	

	//$(".rt input.inp").JQFNumKeypad({clearText: 'Clean'});
	//

	$("#reserveBtn").click(function(){ //���� ��ư Ŭ���� ����â modal
		$('#dateTimePicker').modal(options);
	});

	 /*
	$("#ajaxSearch").one('click',function(){ // ����Ʈ �޼��� ����
			$(this).val("");
	});
	*/

	/*
	 *	��ȭ��ȣ �Է¶��� 10���̱� ������ ����ڰ� �߰� ��ư�� ������
	 *  ��ȭ��ȣ �Է¶� ������ üũ�� �������� ���� �±׸� ������ ��Ŀ���� ��
	 *  ������ �±״� ���� �̺�Ʈ �Ҵ�
	 */
	$("#listPlus").click(function(){
		var index = $("#pone_list li").size();
		index++;
		var html = "<li><div class='lt'><input name='recvName"+ index + "' id='recvName"+ index + "' type='Text' class='inp'>"
					+ "<div class='nt'>" + index + "</div></div><div class='rt'>"
					+ "<input name='recvPhone" + index + "' id='recvPhone" + index + "' type='text' class='inp'>"
					+ "<div class='bt'><img src='../images/sms/btn_close2.gif' alt='�ݱ�'></div></div></li>";
			 
		$("#pone_list li:last").after(html);
		var lastInput = $("#pone_list li:last input:last");
		currentPhoneInput = lastInput;
		lastInput.inputNumber().focus();

		//alert($("#pone_list").html());
	});


	/*  
	 *  ��ȭ��ȣ �Է°����� �ʱ⿡�� 10�������� ���� �߰� �ɼ� �ֱ� ������
	 *  �������� ������ html���� event�� �ɾ������
	 *
	 */
	$(".bt").live("click", function(){
		$(this).parents("li").children("div").
		children("input").css("background-image", "none").val("");
	});
	
	$(".rt :input").live("focusin", function(){
		//	$(this).children("input").css("background", "none");
		currentPhoneInput = $(this);
	});

	/*  
	 *  �ϴ� �޼�������Ʈ �ڽ����� select �±��� change �̺�Ʈ�� �߻��Ұ��
	 *  �ٲ� ���� ���� ajax�� �޼����� ������
	 */
	$("#messageBoxes select").change(function(){
		var val = $(this).val();
		if( $(this).is("#myGroup") == true ){ // ���õ� �׷��� ���� �޽��� �׷��� ���
			flag_group = "my";
		}else{
			flag_group = "public";
		}
			
		if( val != ""){
			//$("#messageLoadBox").empty();
			$.html.messages( flag_group, val );
		}
		//alert( $(this).val() );
		/*
		if( $("#messageBoxes select:first option").val() == "1" ){
			messagesHTML("public", ""); // ���� �޽����� ������
		}else{
			//var selected = $("#myGroup option:selected").val();
			alert( $("#myGroup option").val() );
			messagesHTML("my", selected );
		}
		
		*/
		//myMessages
		//messagesHTML();
	});
	
	var addressWindow = null;
	$("#addList").click(function(){	// �ּҷ� ��ư
		/****************  ���� �ּҷ� **************/
		//document.all.f_id.value = "<%= f_id%>";  
		//var url = "../Address/Read_address.jsp";
		//var title = "read_addr";
		//var status = "toolbar=no, scrollbars=no, status=no, menubar=no, width=500, height=500, top=100, left=200";
		//if(!chkwindow || chkwindow.closed) { 	// ��â�� �θ�â ���� �����ֱ� ����
		//	chkwindow = window.open(url, title, status);
		//} else {
		//	chkwindow.window.close();
		//	chkwindow = window.open(url, title, status);
		//}

		//document.form1.target = title;
		//document.form1.action = url;
		//document.form1.method="post";
		//document.form1.submit();
		/****************  ���ο� �ּҷ� **************/
		var url = "./addressWindow.html";
		var title = "addressWindow";
		var status = "toolbar=no, scrollbars=no, status=no, menubar=no, width=900, height=750, top=100, left=200";
		if( addressWindow != null ) { 	// ��â�� �θ�â ���� �����ֱ� ����
			addressWindow.close();
		}
		addressWindow = window.open(url, title, status);
		addressWindow.focus();			

	});
	
	$("#finalSendBtn").click(function(){	// ������ ��ư
		$.phone.send();
	});

	$("#reset").click(function(){ // �ʱ�ȭ
		$("#pone_list li").each(function(){
			$(this).children("div").children("input").css("background-image", "none").val("");
		});
	});
	
	$("#vknPad").click(function(){ // Virtual Numeric Keypad
	//	alert("Virtual Numeric Keypad �� ���� ���� ���� �ʽ��ϴ�.(���� ���� ����)");
		if( currentPhoneInput == null){
			currentPhoneInput = $("#recvPhone1");
		}
		$.html.NumericKeyPad();
	});

	/*
	 *	Numeric KeyPad Event function
	 *	���� Ű�е� Ŭ���� �ش� ���ڰ��� ���� ����� input ��ü���� �־��ش�.
	 *	BackSpace Ŭ���� Ű����� ���� ȿ���� �ѱ��ڸ� �߶󳽴�
	 *	��  �ڵ��� �����ڸ����� ���ų� ������쿡�� ó���Ѵ�.
	 */
	$("ol#controls li a").click(function(){
		var k = $(this).text();
		var v = currentPhoneInput.val();
		var len = v.length;
		if(k == "Close"){
			$.html.NumericKeyPad();
			return false;
		}else if(k == "BackSpace"){
			currentPhoneInput
			.val( v.substr(0, (len-1) ) ).focus();				
		}else{
			if(len < 12 ){	//  �ڵ��� �ڸ����� ���� ��������
				currentPhoneInput
				.val( v.concat( $(this).text() ) ).focus();
			}
		}
		return false;
	});	

	/*
	 * ���� �߰� ���̾�α�
	 * Ȯ���ڰ� xls �̳� xlsx ��츸 true
	 */
	var excelWindow = null;
	$("#addExcel").click(function(){
		var url = "./excel.jsp";
		var title = "excel";
		var status = "toolbar=no, scrollbars=no, status=no, menubar=no, width=525, height=60, top=100, left=200";
		if( excelWindow != null ) { 	// ��â�� �θ�â ���� �����ֱ� ����
			excelWindow.close();
		}
		excelWindow = window.open(url, title, status);
		excelWindow.focus();	
	});
	
	
	/**
	 * sms or mms üũ�ڽ� ó��
	 */
    $(".choice li input").click(function(){
    	var $this = $(this);
    	$this.siblings();
    	// ù��° Ŭ�� �� sms �����̸�
    	// �޽����� ������ �����ش�.
    	if($this.index() == 0){
        	$.phone.smsMode = true;
        	$("#f_message").val("");	
        	$("#textByte").text("0/80Bytes" );        	
    	}else{	// mms �̸�
        	$.phone.smsMode = false;
        	$("#f_message").val("");	
        	$("#textByte").text("0/2000Bytes" );
    	}
    });
    
    /**
     * �� ���� Ȥ�� Ư�� ���� ���� ����ó��
     */
    $("#myMessageBox").click(function(){
    	$(this).attr("src", "../images/lettersend/tab01_on.gif");
    	$("#specailCharBox").attr("src", "../images/lettersend/tab02_off.gif");    	
    	$(".my02").hide();    	
    	$(".my01").show();
    });
    
    $("#specailCharBox").click(function(){
    	$(this).attr("src", "../images/lettersend/tab02_on.gif");
    	$("#myMessageBox").attr("src", "../images/lettersend/tab01_off.gif");    	    	
    	$(".my01").hide();    	
    	$(".my02").show();
    });
    
    
    
	/*
	 * Ư������ tab
	 * ���� Ư�����ڵ� ���� �⺻ �ε����� ù��°�� ��
	 */
	$.html.character(0);
	
	setTimeout(function(){ // ��ũ��Ʈ ���� �ε�
		$.html.messages("public", "all");	// ���� ��ũ��Ʈ-1 ( ���� �޽���  )
		setTimeout(function(){
			$('#dateTimePicker').load("./ajax/calendar/calendar.html", function(){// ���� ��ũ��Ʈ-2
				Defaults();
				setTimeout(function(){ $('.miniload').hide() }, 50);
			});
		},50);
	},50);
	
	
	

});