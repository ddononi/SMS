package kr.go.police.sms;

public interface iSmsResultConstant {
	
	// 발송 상태
	public final static int SENDSTAT_READY_NUM = 0;		// 발송대기
	public final static int SENDSTAT_COMPLETSMS_E_NUM = 1;	// 전송완료
	public final static int SENDSTAT_RECIVSMS_E_NUM = 2;		//  결과수신완료
	
	// 발송 결과
	// SMS 결과 코드 값
	public final static int SMS_E_OK = 0;						//  결과수신완료
	public final static int SMS_E_SYSFAIL = 1;				//  시스템 장애
	public final static int SMS_E_AUTH_FAIL = 2;			//  인증실패
	public final static int SMS_E_FORMAT_ERR = 3;		// 메시지 형식 오류
	public final static int SMS_E_NOT_BOUND = 4;		//  BIND 안됨
	public final static int SMS_E_NO_DESTIN = 5;			// 착신가입자 없음(미등록)
	public final static int SMS_E_SENT = 6;					//  전송성공
	public final static int SMS_E_INVAILDDST = 7;		//  비가입자, 결번, 서비스정지
	public final static int SMS_E_POWEROFF = 8;			// 단말기 Power-off 상태
	public final static int SMS_E_HIDDEN = 9;				// 음영
	public final static int SMS_E_TERMFULL = 10;			// 단말기 메시지 FULL
	public final static int SMS_E_TIMEOUT = 11;			// 타임아웃
	public final static int SMS_E_ETC = 14;					// 무선망에러
	public final static int SMS_E_NO_URLUSER = 17;		// CALLBACKURL 사용자 아님
	public final static int SMS_E_DUP_MSG = 18;			// 메시지 중복 발송
	public final static int SMS_E_FLOWCONTROL = 19;	// 월 송신 건수 초과
	public final static int SMS_E_UNKOWN = 20;			// 기타에러
	public final static int SMS_E_DEST_SIZE = 21;			// 찬신번호 에러(자리수에러)
	public final static int SMS_E_DEST_CODE = 22;		// 착신번호 에러(없는 국번)
	public final static int SMS_E_MSG_FMT = 23;			// 수신거부 메시지 없음
	public final static int SMS_E_ADV21 = 24;				// 21시 이후 광고
	public final static int SMS_E_ADV = 25;					// 성인광고, 대출광고 등 기타 제한
	public final static int SMS_E_DACOM_SPAM = 26;	// 데이콤 스팸 필터링
	public final static int SMS_E_NIGTHBLOCK = 27;		// 야간발송차단
	public final static int SMS_E_NO_ALLOW = 40;		// 단말기착신거부(스팸등)
	public final static int SMS_E_FORBINDDEN_TIME = 91;			// 발송 미허용 시간 때 발송 실패 처리
	public final static int SMS_E_DUP =99;					// 	메시지 중복	
	
	
	// LMS, MMS 결과 코드 값
	public final static int MMS_E_OK = 1000;								//  성공
	public final static int MMS_E_FORMAT_ERR = 2000;					// 포맷에러
	public final static int MMS_E_INVALID_PHONE = 2001;				//	잘못된 번호	
	public final static int MMS_E_CONTENT_ERR = 2002;				//	컨텐츠 사이즈 및 개수 초과
	public final static int MMS_E_INVALID_CONTENTS = 2003;		//	잘못된 컨텐츠
	public final static int MMS_E_UNAVAILABLE_PHONE = 3000;		//	기업형 MMS 미지원 단말기
	public final static int MMS_E_MSG_FULL = 3001;						// 단말기 메시지 저장개수 초과
	public final static int MMS_E_TIMEOUT = 3002;						//	전송시간 초과
	public final static int MMS_E_POWER_OFF = 3004;					//	전원 꺼짐
	public final static int MMS_E_EXP_SMS = 3005;						//	음영지역
	public final static int MMS_E_ETC = 3006;								//	기타
	public final static int MMS_E_SYS_ERR = 4000;							//	서버문제로 인한 접수 실패
	public final static int MMS_E_STOP_PHONE = 4001;					//	단말기 일시 서비스 정지
	public final static int MMS_E_TELECOM_ERR = 4002;				//	통신사 내부 실패(무선망단)
	public final static int MMS_E_BUSY_FAIL = 4003;						//	서비스의 일시적인 에러
	public final static int MMS_E_DENY = 4101;							//	계정 차단
	public final static int MMS_E_REJECT_IP = 4102;						//	허용되지 않은 IP접근
	public final static int MMS_E_INSUFFICIENT = 4104;					//	건수 부족
	public final static int MMS_E_REJECT_ITER = 4201;					//	국제 MMS 발송 권한이 없음
	public final static int MMS_E_PORTED = 5000;							//	번호이동에러
	public final static int MMS_E_NOCDR = 5001;							//	선불발급 발송건수 초과
	public final static int MMS_E_SPAM = 5003;							//	스팸
	public final static int MMS_E_DUP_KEY = 5201;						//	중복된 키 접수 차단
	public final static int MMS_E_DUP_PHONE = 5202;					//	중복된 수신번호 접수 차단
	public final static int MMS_E_FORBIDDEN_TIME = 9001;			//	발송 미허용시간
	public final static int MMS_E_PHONE_NUM = 9002;					//	폰 넘버 에러
	public final static int MMS_E_SPAM_NUM = 9003;					//	스팸번호
	public final static int MMS_E_LO_TIMEOUT = 9004;					//	이통사에서 응답없음
	public final static int MMS_E_FILE_SIZE = 9005;						//	파일크기오류
	public final static int MMS_E_FILE_FORMAT = 9006;					//	지원되지 않는 파일
	public final static int MMS_E_FILE_INVALID = 9007;					//	파일오류
	public final static int MMS_E_MSG_TYPE = 9008;						//	MMS_MSG의 MSG_TYPE값 에러
	public final static int MMS_E_DUP_MSG = 9009;						//	설정시간이내 중복
	public final static int MMS_E_EXCEED_RETRY = 9010;				// 재전송 횟수 초과로 실패
	public final static int MMS_E_OLD_REQ = 9011;						//	발송지연으로 인한 실패
}
