package kr.go.police.sms;

/**
 *  LG 유플러스 결과 코드
 * 전송 모드에 따라 코드값 메시지값 매칭 처리 클래스
 * @author juwan
 *
 */
public class AgentResultMsg implements iSmsResultConstant{

	/**
	 * SMS 결과 수신값 매칭
	 * @param resultCode
	 * @return
	 */
	public static String getSmsMsg(int resultCode) {
		switch(resultCode){
		case SMS_E_SENT :
			return "성공";		
		case SMS_E_SYSFAIL :
			return "시스템 장애";
		case SMS_E_AUTH_FAIL :
			return "인증실패, 직후 연결을 끊음";
		case SMS_E_FORMAT_ERR :
			return "메시지 형식 오류";
		case SMS_E_NOT_BOUND :
			return "BIND 안됨";
		case SMS_E_NO_DESTIN :
			return "착신가입자 없음(미등록)";
		case SMS_E_INVAILDDST :
			return "비가입자, 결번, 서비스정지";
		case SMS_E_POWEROFF :
			return "단말기 Power-off 상태";
		case SMS_E_HIDDEN :
			return "음영";
		case SMS_E_TERMFULL :
			return "단말기 메시지 FULL";
		case SMS_E_TIMEOUT :
			return "타임아웃";
		case SMS_E_ETC :
			return "무선망에러";
		case SMS_E_NO_URLUSER :
			return "CALLBACKURL 사용자 아님";
		case SMS_E_DUP_MSG :
			return "메시지 중복 발송";
		case SMS_E_FLOWCONTROL :
			return "월 송신 건수 초과";
		case SMS_E_UNKOWN :
			return "기타에러";			
		case SMS_E_DEST_SIZE :
			return "착신번호 에러(자리수에러)";				
		case SMS_E_DEST_CODE :
			return "착신번호 에러(없는 국번)";					
		case SMS_E_MSG_FMT :
			return "수신거부 메시지 없음";				
		case SMS_E_ADV21 :
			return "21시 이후 광고";	
		case SMS_E_ADV :
			return "성인광고, 대출광고 등 기타 제한";	
		case SMS_E_DACOM_SPAM :
			return "데이콤 스팸 필터링";	
		case SMS_E_NIGTHBLOCK :
			return "야간발송차단";			
		case SMS_E_NO_ALLOW :
			return "단말기착신거부(스팸등)";				
		case SMS_E_FORBINDDEN_TIME :
			return "발송 미허용 시간 때 발송 실패 처리";				
		case SMS_E_DUP :
			return "메시지 중복";	
			
		}
		return  "결과정보 없음";
	}
	
	
	/**
	 * MMS 결과 수신값 매칭
	 * @param resultCode
	 * @return
	 */
	public static String getMmsMsg(int resultCode) {	
		switch(resultCode){
		case MMS_E_OK :
			return "성공";		
		case MMS_E_FORMAT_ERR :
			return "포맷에러";
		case MMS_E_INVALID_PHONE :
			return "잘못된 번호";
		case MMS_E_CONTENT_ERR :
			return "컨텐츠 사이즈 및 개수 초과";	
		case MMS_E_INVALID_CONTENTS :
			return "잘못된 컨텐츠";	
		case MMS_E_UNAVAILABLE_PHONE :
			return "기업형 MMS 미지원 단말기";		
		case MMS_E_MSG_FULL :
			return "단말기 메시지 저장개수 초과";			
		case MMS_E_TIMEOUT :
			return "전송시간 초과";	
		case MMS_E_POWER_OFF :
			return "전원 꺼짐";		
		case MMS_E_EXP_SMS :
			return "음영지역";		
		case MMS_E_ETC :
			return "기타";			
		case MMS_E_SYS_ERR :
			return "서버문제로 인한 접수 실패";		
		case MMS_E_STOP_PHONE :
			return "단말기 일시 서비스 정지";					
		case MMS_E_TELECOM_ERR :
			return "통신사 내부 실패(무선망단)";				
		case MMS_E_BUSY_FAIL :
			return "서비스의 일시적인 에러";				
		case MMS_E_DENY :
			return "계정 차단";	
		case MMS_E_REJECT_IP :
			return "허용되지 않은 IP접근";			
		case MMS_E_INSUFFICIENT :
			return "건수 부족";			
		case MMS_E_REJECT_ITER :
			return "국제 MMS 발송 권한이 없음";				
		case MMS_E_PORTED :
			return "번호이동에러";			
		case MMS_E_NOCDR :
			return "선불발급 발송건수 초과";				
		case MMS_E_SPAM :
			return "스팸";			
		case MMS_E_DUP_KEY :
			return "중복된 키 접수 차단";			
		case MMS_E_DUP_PHONE :
			return "중복된 수신번호 접수 차단";				
		case MMS_E_FORBIDDEN_TIME :
			return "발송 미허용시간";		
		case MMS_E_PHONE_NUM :
			return "폰 넘버 에러";			
		case MMS_E_SPAM_NUM :
			return "스팸번호";		
		case MMS_E_LO_TIMEOUT :
			return "이통사에서 응답없음";		
		case MMS_E_FILE_SIZE :
			return "파일크기오류";		
		case MMS_E_FILE_FORMAT :
			return "지원되지 않는 파일";			
		case MMS_E_FILE_INVALID :
			return "파일오류";			
		case MMS_E_MSG_TYPE :
			return "MMS_MSG의 MSG_TYPE값 에러";	
		case MMS_E_DUP_MSG :
			return "설정시간이내 중복";			
		case MMS_E_EXCEED_RETRY :
			return "재전송 횟수 초과로 실패";			
		case MMS_E_OLD_REQ :
			return "발송지연으로 인한 실패";				
		}
		return  "결과정보 없음";
	}
}
