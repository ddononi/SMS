package kr.go.police.sms;

/**
 * 문자 전송 dto
 */
public class SMSBean {
	private long index; // 인덱스
	private int userIndex;
	private String id; // 아이디
	private String deptCode; // 부서코드
	private String toPhone;	// 받는 전화번호 
	private String fromPhone;  // 보내는 전화번호
	private String message; // 메시지
	private boolean sendState; // 전송상태
	private boolean sendResult; // 전송결과
	private String reserveDate; // 예약시간
	private String callback; // 콜백
	private String deptName; // 부서명
	private boolean mms; // mms 여부
	private String regDate;		// 발송등록 시간

	public long getIndex() {
		return index;
	}

	public void setIndex(long index) {
		this.index = index;
	}
	
	public int getUserIndex() {
		return userIndex;
	}

	public void setUserIndex(int userIndex) {
		this.userIndex = userIndex;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getToPhone() {
		return toPhone;
	}

	public void setToPhone(String toPhone) {
		this.toPhone = toPhone;
	}

	public String getFromPhone() {
		return fromPhone;
	}

	public void setFromPhone(String fromPhone) {
		this.fromPhone = fromPhone;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSendState() {
		return sendState;
	}

	public void setSendState(boolean sendState) {
		this.sendState = sendState;
	}

	public boolean isSendResult() {
		return sendResult;
	}

	public void setSendResult(boolean sendResult) {
		this.sendResult = sendResult;
	}

	public String getReserveDate() {
		return reserveDate;
	}

	public void setReserveDate(String reserveDate) {
		this.reserveDate = reserveDate;
	}

	public String getCallback() {
		return callback;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public boolean isMms() {
		return mms;
	}

	public void setMms(boolean mms) {
		this.mms = mms;
	}

	public String getRegDate() {
		if(regDate != null){
			return regDate.substring(5, 16);
		}
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	
	

}
