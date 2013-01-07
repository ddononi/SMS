package kr.go.police.sms;

public interface iSmsResultConstant {
	
	// 발송 상태
	public final static int SENDSTAT_READY_NUM = 0;		// 발송대기
	public final static int SENDSTAT_COMPLETE_NUM = 1;	// 전송완료
	public final static int SENDSTAT_RECIVE_NUM = 2;		//  결과수신완료
	
	// 발송 결과
	public final static String E_OK = "00";						//  결과수신완료
	public final static String E_SYSFAIL = "01";				//  시스템 장애
	public final static String E_AUTH_FAIL = "02";			//  인증실패,
	public final static String E_SENT = "06";					//  전송성공
	
}
