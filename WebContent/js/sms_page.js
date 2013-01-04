/**
 * 	sms 
 *  ���� ���� �߼� ������ 
 *  main.jsp
 */
var phoneReg =  /^01([0|1|6|7|8|9]?)-?([0-9]{3,4})-?([0-9]{4})$/;	// �ڵ��� ���Խ�
// �� ���Խ� �� �޼��� ����
jQuery.phone = {
	smsMode : true,	// sms �������		
	hasFile : false,		// ���� ÷�� ����
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
		var that = $("#message");
		var textThat = $("#textByte");
		var buf = that.val() || that.text();
		var encodedStr = $.phone.checkEnter(buf);
		var l = 0;

		// sms ����̸�
		if($.phone.smsMode && !$.phone.hasFile){
			if ( encodedStr > 80) {
				if(event.keyCode == 8 || event.keyCode ==46){
					return false;
				}
				
		        for (var i=0; i< buf.length; i++) { 
	                l += (buf.charCodeAt(i) > 128) ? 2 : 1; //  �ѱ� ó�� 
	                if (l > 80){
	                	// sms ����ϰ�� mms ���� ��ȯ�Ѵ�.
	                	if($.phone.smsMode){
	    	                $("#sms_sep_icon").attr("src", "./images/lettersend/icon_mms.gif");
		                	$.phone.smsMode = false;
	    	                $("[name='send_type']").first().removeAttr('checked')
	    	                	.andSelf().last().attr('checked', 'chekced');			                	
	                	}
	                }else{
	                	if(!$.phone.smsMode){
		                	$.phone.smsMode = true;
	    	                $("#sms_sep_icon").attr("src", "./images/lettersend/icon_sms.gif");
	    	                $("[name='send_type']").last().removeAttr('checked')
	    	                	.andSelf().first().attr('checked', 'chekced');	
	                	}
	                }
		        }
			}else{
				textThat.text( encodedStr + "/80Bytes" );
			}
		// mms ����̸�	
		}else{
            if (encodedStr > 2000){
            	$.phone.smsMode = false;
                alert( "���̻� �Է��Ҽ� �����ϴ�.");
                that.val(  stringCut2(buf, 2000) );
                return false;
            }		
            
			if ( encodedStr > 2000) {
				if(event.keyCode == 8 || event.keyCode ==46){
					return false;
				}
			}else{
				
		        for (var i=0; i< buf.length; i++) { 
	                l += (buf.charCodeAt(i) > 128) ? 2 : 1; //  �ѱ� ó�� 
	                if (l > 2000){
	                	$.phone.smsMode = false;
		                alert( "���̻� �Է��Ҽ� �����ϴ�.");
		                that.val( stringCut2(buf, 2000) );
		                return false;
	                }
		        }				
		        
            	
		        if(!$.phone.hasFile && l <= 80){	// 80Byte �����̸�
                	$.phone.smsMode = true;				        	
	                $("#sms_sep_icon").attr("src", "./images/lettersend/icon_sms.gif");
	                $("[name='send_type']").last().removeAttr('checked')
                		.andSelf().first().attr('checked', 'chekced');	          	
                }
		        
				textThat.text( encodedStr + "/2000Bytes" );
			}
		}
		
	},
	
	/*
	 * split �� ���� , �� �ٿ����� �Է°� üũ�� �ð��� �־����� ����
	 */
	send : function(){	// ���� ����
		/*
		var remainder = parseInt( $("#freeSendCount span").text() );	// ���� �޽��� �Ǽ�
		if( remainder == 0){	// ���� �������� ������
			//alert("���� �޼��� �Ǽ��� �����ϴ�.");
			//return false;			
		}
		*/

		var phoneNumbers = [];
		var senderCount = 0; //  ������ ��� ī��Ʈ
		var firstSender = "";
		
		// �Է� ��ȭ��ȣ ��������
		$("#pone_list ul").each(function(){
			var $input = $(this).find("li:odd input");
			if( $input.val() != "" ){
				if( senderCount == 0 ){
					firstSender =$input.val();
				}
				senderCount++;
				phoneNumbers.push($input.val());
			}
		});

		

		// ���� �޼��� �Ǽ� �� ������ �޽��� ������ �� ó��
		/*
		if(  remainder < parseInt( senderCount ) ){
			alert("���� �޼��� �Ǽ��� Ȯ���ϼ���");
			return false;			
		}
		*/
				
		//phoneNumbers = phoneNumbers.substr(0,(phoneNumbers.length-1) );
		// �Էµ� ��ȭ���� �������  false ó��
		if( phoneNumbers.length <= 0 ){
			alert("�Էµ� ��ȭ��ȣ�� �����ϴ�.");
			return false;
		}
		
		// �Էµ� ������ ������ false ó��
		if( isDefault == true || $("#message").val().length == 0 ){
			alert("�Էµȳ����� �����ϴ�.");
			$("#message").focus();
			return false;
		}
		
		var callback_num = $("#my_phone_num").val();
		if( callback_num.length == 0 /* || !phoneReg.test(callback_num) */ ){
			alert("�� ��ȣ�� ��Ȯ�� �Է��ϼ���.");
			$("#my_phone_num").focus();
			return false;			
		}

		if( $.phone.spam() != true ){
			return false;
		}

		//$.phone.doNotDuplicate();
		if( $.phone.check() != true  ){
			return false;
		}
			// ��� ����
		$.phone.doNotDuplicate();	// ���� ��ȭ��ȣ ���� ó��
		// �޴� ��� ��ȭ��ȣ�� �־��ش�.
		$("#call_to_nums").val(phoneNumbers.join(","));
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

		// ���� �߼� ���� ��
		$("#send_frm #call_to_nums").val(phoneNumbers.join(","));		// �޴� ��ȭ��ȣ��(�޸��� ����)
		$("#send_frm #message").val($("#message").val());				// �޼���
		$("#send_frm #my_phone_num").val($("#my_phone_num").val());			// �� ��ȭ��ȣ
		$("#send_frm #flag").val($("[name='send_type']:checked").val());		// ���� Ÿ��
		$("#send_frm #reserved").val($("#reserved_datetime").val().length>0?"true":"false");		// 	���� ����
		$("#send_frm #reserved_datetime").val($("#reserved_datetime").val());			// 	���� ��¥
		/*
		 *	ajax �� sms �߼�ó��
		 */			
		if( confirm("���ڸ޼����� �߼� �Ͻðڽ��ϱ�?") != true){
			return false;
		}
		// ���� �߼�
		$("#send_frm").submit();
		
	},
	/*
	 * �Էµ� ��ȭ��ȣ üũ
	 * �� input�� ��ȸ�ϸ�  ���Խ� ��ȿ�� �� üũ
	 * ���� ������� ������ ��ȿ�� �˻��� Ʋ�� �׸��� �ش� �ε�����ȣ�� �˷���
	 */
 	check : function() {	// �Էµ� ��ȭ��ȣ üũ
		var flag = true;
		var str = "";
		var invaildInput = null;
		$("#pone_list ul").each(function(index){
			var that = $(this).find("li:odd input");		
			var pNum = that.val();
			/*that.css("background", "none");*/
			if( pNum != "" ){
				if( !phoneReg.test(pNum) ){
					// �߸��� ��ȭ��ȣ�߿� �� ù��°�� ���� 
					if(invaildInput == null){
						invaildInput = that;
					}
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
			// �߸� �Էµ� ��ȭ��ȣ�� ù��°�� ��Ŀ��
			if(invaildInput != null){
				invaildInput.focus();
			}
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
		/*
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
		*/
		
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
				/*
                <li><span>05</span><input name="recvName1" id="recvName1" type="text" class="inp" /></li>
                <li><input name="recvPhone1" id="recvPhone1" type="text" class="inp" value="" style="width:150px;" /></li>
                <li class="bt"><img src="./images/sms/btn_close2.gif" alt="�ݱ�" /></li>
                */
				html = "<li><span>" + count + "</span><input readonly='readonly' name='recvName"+ count + "' id='recvName"+ count + "' type=text' class='inp'>"
								+ "<li><input name='recvPhone" + count + "' id='recvPhone" + count + "' type='Text' class='inp'></li>"
								+ "<li class='bt'><img src='./images/sms/btn_close2.gif' alt='�ݱ�'></li>";
			 
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
					var message = $("#message");
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
	
    //  ���� ÷�� ���̾�α� ����
	$( "#send_form_dlg" ).dialog({
            autoOpen: false,
            modal: true,
            width : 400,
            height : 300,
            buttons: {
            	// �ּҷ� �߰� ó��
                "Ȯ��": function() {
                	 $( this ).dialog( "close" );
                	 // �����Է�â�� ��Ŀ��
                	 var $message =$("textarea#message");
                	 $message.focus();  
             		 var len = $message.val().length;
             		 setSelectionRange($message[0], len, len);             		
                },
                "����": function() {
                	// ���ϸ� ���� ���� �ٽ� �����Ѵ�.
                    $("[type='file']" ).each(function(index){
                    	index += 1;
                    	var html ='<input id="file' +index + '" name="file' +index +
                    	'" value="" type="file" style="display: block; margin: 5px;"  />';
                    	$("#file" +index).remove();
                    	$("#send_frm").append(html);
                    });
                    $( this ).dialog( "close" );
               	 	// �����Է�â�� ��Ŀ��
                    var $message =$("textarea#message");
               	 	$message.focus();  
            		 var len = $message.val().length;
            		 setSelectionRange($message[0], len, len);                        
                }
            }
    });     
	

	/*
	 *	���ڸ� �Է� ���-- ��ȭ��ȣ �Է¶��� ����
	 */
	$("#pone_list ul li input:odd").inputNumber().live('focusout', function(){
		$(this).addHyphen();
	});

	/*
	 *	�ʱ� �޽��������� �޼��� notice������ �Է� �Ǿ��ֱ� ������ 
	 *  ����ڰ� ó�� ��Ŀ���� �ٶ� �⺻ �޽���â�� �Էµ� ���� ��������
	 */
	$("#message").one('click',function(){ // ����Ʈ �޼��� ����
		if( isDefault == true ){ // �⺻ ���� ���� Ȯ��
			$(this).empty();
		}
	});
	
	$("#resetTextBtn").click(function(){ // �ٽ� ���� ��ư
		$("#message").val("");
		$("#send_frm")[0].reset();
		// sms ���� ����
		$.phone.hasFile = false;
    	$.phone.smsMode = true;		
        $("#sms_sep_icon").attr("src", "./images/lettersend/icon_sms.gif");
        $("[name='send_type']").last().removeAttr('checked').andSelf().first().attr('checked', 'chekced');				
		$("#textByte").text("0/80Bytes");
		return false;
	});

	$("#message").bind('keypress keydown keyup change focus', function(e) { // �޼��� Ű �Է½� ���� Ű�� üũ
		isDefault = false;
		$.phone.checkLength(e);		
	});	

	$("#reserveBtn").click(function(){ //���� ��ư Ŭ���� ����â modal
		$('#dateTimePicker').modal(options);
	});

	/*
	 *	��ȭ��ȣ �Է¶��� 10���̱� ������ ����ڰ� �߰� ��ư�� ������
	 *  ��ȭ��ȣ �Է¶� ������ üũ�� �������� ���� �±׸� ������ ��Ŀ���� ��
	 *  ������ �±״� ���� �̺�Ʈ �Ҵ�
	 */
	$("#listPlus").click(function(){
		var index = $("#pone_list ul").size();

		var html = "<ul><li><span>" + (++index) + "</span><input disabled='disabled' readonly='readonly' name='recvName"+ index + "' id='recvName"+ index + "' type=text' class='inp'>"
					+ " <li><input name='recvPhone" + index + "' id='recvPhone" + index + "' type='Text' class='inp hyphen rt' style=\"width:150px;\"></li>"
					+ " <li class='bt'><img src='./images/sms/btn_close2.gif' alt='�ݱ�'></li></ul>";
			 
	   $("#pone_list ul:last").after(html);
		var $lastInput = $("#recvPhone" + index);
		$lastInput.inputNumber().focus();				
		currentPhoneInput = $lastInput;
	});


	/*  
	 *  ��ȭ��ȣ �Է°����� �ʱ⿡�� 10�������� ���� �߰� �ɼ� �ֱ� ������
	 *  �������� ������ html���� event�� �ɾ������
	 *
	 */
	$(".bt").live("click", function(){
		$(this).parents("ul")
			.children("li")
			.children("input").css("background-image", "none").val("");
	});
	
	// ���� ��Ŀ���� ���� input ��ü ����
	$("#pone_list ul li input:odd").live("focusin", function(){
		currentPhoneInput = $(this);
	});
	
	$("#special td").click(function(e){
		$.phone.checkLength(e);		
		var $message = $("#message");
		$message.val($message.val() + $(this).text());
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
	/**
	 * �ּҷ� ���ý�
	 * �ּҷ� ������ â�� ����ش�.
	 */
	$("#address_book_btn").click(function(){		// �ּҷ� ��ư
		var url = "./AddressBookWindow.ad";
		var title = "addressWindow";
		var status = "toolbar=no, scrollbars=no, status=no, menubar=no, width=700, height=600, top=100, left=200";
		if( addressWindow != null ) { 	// ��â�� �θ�â ���� �����ֱ� ����
			addressWindow.close();
		}
		addressWindow = window.open(url, title, status);
		addressWindow.focus();			

	});
	
	$("#send_btn").click(function(){	// ������ ��ư
		$.phone.send();
	});

	$("#reset").click(function(){ // �ʱ�ȭ
		$("#pone_list ul")
			.children("li")
				.children("input").css("background-image", "none").val("");
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
    	$(this).attr("src", "./images/lettersend/tab01_on.gif");
    	$("#specailCharBox").attr("src", "./images/lettersend/tab02_off.gif");    	
    	$(".my02").hide();    	
    	$(".my01").show();
    });
    
    // sms mode or mms mode ��ȯ
    $("[name='send_type']").click(function(event){
    	$.phone.smsMode =$("[name='send_type']:first").is(this);
    });
    
	// ��ȭ��ȣ ������ �ֱ�
	$(".hyphen").addHyphen();
    
    $("#specailCharBox").click(function(){
    	$(this).attr("src", "./images/lettersend/tab02_on.gif");
    	$("#myMessageBox").attr("src", "./images/lettersend/tab01_off.gif");    	    	
    	$(".my01").hide();    	
    	$(".my02").show();
    });
    
    // ���� ��¥
    $("#reserved_btn").click(function(){
    	$("#reserved_datetime").datetimepicker( "show" );
    });
    
    // datetime picker ����ȭó�� �� �ɼ� ����
    // datetime�� show�Ҷ� ���� ��ư�� �������Ƿ� ���� ��ư�� �����ְ�
    // �ٽ� close�Ҷ� �����Ѵ�.
	$("#reserved_datetime").datetimepicker({
		hideIfNoPrevNextType : true,
		dateFormat : "yy-mm-dd",
		monthNames: [ "1��", "2��", "3��", "4��", "5��", "6��", "7��", "8��", "9��", "10��", "11��", "12��" ],
		dayNamesType : ["�Ͽ���", "������", "ȭ����", "������", "�����", "�ݿ���", "�����"  ],
		dayNamesMin : ["��", "��", "ȭ", "��", "��", "��", "��"  ],		
		prevText: "����",
		nextText: "����",
		closeText: "Ȯ��",		
		currentText : "����",
		hourText : "��",
		minuteText : "��",
		timeText : "�ð�",
		gotoCurrent: true,		
		beforeShow : function(){
			$("#toolbar").css("visibility", "hidden");
			$("#reserved_datetime").val("");
			//$("#reserved_datetime, #reserved_label").show();
		},
		onClose : function(dateText, inst ){
			$("#toolbar").css("visibility", "visible");
			if($(this).val().length > 0){
				// ����ð��� ����ð��� ���Ͽ� �ð��� ������ �Ѱ��ش�.
				var reserveDate = parseInt(($(this).val() +"00").replace(/[^0-9]/gi, ""));
				var currentDate =  parseInt(getTimeStamp().replace(/[^0-9]/gi, ""));
				if(currentDate > reserveDate){
					alert(reserveDate);
					alert(currentDate);
					alert("����ð��� ����ð����� Ŀ�� �մϴ�.");
					$(this).val("");
				}
				//$("#reserved_datetime,  #reserved_label").hide();
				
			}
		},
		onSelect : function(){

		}
	});
	/*
	 * Ư������ tab
	 * ���� Ư�����ڵ� ���� �⺻ �ε����� ù��°�� ��
	 */
	$.html.character(0);
	
	// ���� ÷�� ó��
	$("#addFile").click(function(){
		$( "#send_form_dlg" ).dialog("open");
	});
	
	// ÷������ ���ο� ���� ������ ����
	$("#file1, #file2, #file3").bind('change', function(){
		var $this = $(this);
		if($this.val().length <= 0){
			return;
		}
		// ���� Ȯ���� üũ
		var ext = $this.val().substr( $this.val().lastIndexOf(".") + 1, 3).toLowerCase();
		if(ext  != "skm" && ext  != "k3g" && ext  != "jpg" && ext  != "jpeg" && ext  != "sis" ){
			alert("���� ������ �ùٸ��� �ʽ��ϴ�.");
        	var file_id = $this.attr('id') ;
        	var html ='<input id="' + file_id + '" name="' +file_id +
        	'" value="" type="file" style="display: block; margin: 5px;"  />';
        	$this.remove();
        	$("#send_frm").append(html);			
			return;
		}
		
		$.phone.hasFile = true;
    	$.phone.smsMode = false;		
        $("#sms_sep_icon").attr("src", "./images/lettersend/icon_mms.gif");
        $("[name='send_type']").first().removeAttr('checked');		                 
        $("[name='send_type']").last().attr('checked', 'chekced');					
		/*
		if( $this.attr("id") == 'file1' ){
			// ������ �����̸�
			if(ext  == "skm" || ext  == "k3g"){
				$("#movie_icon1").show();
			}else{
				$("#image_file_icon1").show();
			}
		}else if( $this.attr("id") == 'file2' ){
			if(ext  == "skm" || ext  == "k3g"){
				$("#movie_icon1").show();
			}else{
				$("#image_file_icon2").show();
			}
		}else if( $this.attr("id") == 'file3'){
			if(ext  == "skm" || ext  == "k3g"){
				$("#movie_icon1").show();
			}else{
				$("#image_file_icon3").show();
			}
		}	
		*/	
	});
});

/*******************************************************************************
* ����Ʈ üũ 80bytes 
*******************************************************************************/
function checkBytes2(max_length)
{
	var page = 0;
	var body_length = 0;
	var msgVal = $("#message").val();
	var $textByte = $("#textByte");
	if(max_length>80)
	{
		body_length = getLength2(msgVal);
	}
	else
	{
		body_length = getLength(msgVal);
	}
	
	if(body_length > max_length)
	{
		alert(max_length + "bytes �̻� �Է��� �� �����ϴ�.");
		$("#message").val(stringCut(msgVal, max_length));

		if(max_length>80)
		{
			body_length = getLength2(msgVal);
		}
		else
		{
			body_length = getLength(msgVal);
		}
	}

	if(body_length)
		page = parseInt(body_length / 80);

	if(body_length % 80)
		page += 1;

	/*
	if(typeof(form.page) != "undefined")
		form.page.value = page;
	 */
	
	$textByte.val(body_length);
}

/*******************************************************************************
* ���ڿ��ڸ���
*******************************************************************************/

function stringCut2(str, MAX_LEN)
{
	var len = 0;
	var temp;
	var count = 0;
	len = str.length;
	for (var k=0 ; k<len ; k++)
	{
		temp = str.charAt(k);
	
		if (escape(temp).length > 4) {
			count += 2;
		}
		else
			if(escape(temp) != "%0D")
				count++;

		if(count > MAX_LEN)
		{
			if(escape(temp) == "%0A")
				k--;
			break;
		}
		
		
	}
	
	return str.substring(0, k);
}

/*******************************************************************************
* ���ڿ� ���̸� �����Ѵ�. 
*******************************************************************************/
function getLength(str) 
{
	var length = 0;

	for(var i = 0; i < str.length; i++)
	{
		if(escape(str.charAt(i)).length >= 4 || escape(str.charAt(i)) == "%A7"){
			//�ѱ��� ��� 79 byte���� �ԷµǸ� length ++ add by ljj -2009-03-11
			legnth2=length+1;
			if(legnth2 % 80==0)
			{
				length++;
			}
			length += 2;
		}else{
			if(escape(str.charAt(i)) != "%0D"){
				length++;
			}
		}

		/*
		if(escape(str.charAt(i)).length >= 4)
			length += 2;
		else if(escape(str.charAt(i)) == "%A7")
			length += 2;
		else
			if(escape(str.charAt(i)) != "%0D")
				length++;
		*/
	}	

	return length;
}

/*******************************************************************************
* 1000�� �̿��ڸ� ���� ���ڿ� ���̸� �����Ѵ�.
*******************************************************************************/
function getLength2(str) 
{
	var length = 0;

	for(var i = 0; i < str.length; i++)
	{

		if(escape(str.charAt(i)).length >= 4)
			length += 2;
		else if(escape(str.charAt(i)) == "%A7")
			length += 2;
		else
			if(escape(str.charAt(i)) != "%0D")
				length++;
	}
	return length;
}