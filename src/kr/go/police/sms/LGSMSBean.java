package kr.go.police.sms;

/**
 * 문자 전송 dto(LG U+ 용)
 * 추후 mms, lms, sms 공통 변수는 인터페이스 처리 
 */
public class LGSMSBean  {
	private long index; // 메세지 고유 번호
	private String senddate; // 메시지 전송할 시간, 미래시간을 넣으면 예약 발송
	private int serialnum; // 시리얼넘버(사용안함)
	private String sendstate; // 발송상태
	private String msgtype; // 문자전송형태
	private String rsltstat;	// 발송 결곽 수신값
	private String phone; // 수신할 핸드폰 번호
	private String callback; // 송신자 전화번호
	private String rsltdate; // 이동통신사로부터 결과를 통보 받은 시간
	private String realsenddate;	//  실제 발송시간	
	private String msg; // 전송할 메세지
	private String net; // 전송완료후 최종 이동통신사 정보
	private String userId; // 유저 아이디
	private int userIndex; // 유저 인덱스
	
	public long getIndex() {
		return index;
	}

	public void setIndex(long index) {
		this.index = index;
	}

	public String getSenddate() {
		if(senddate != null && senddate.length() >= 16){
			return senddate.substring(5, 16);
		}				
		return senddate;
	}

	public void setSenddate(String senddate) {
		this.senddate = senddate;
	}

	public int getSerialnum() {
		return serialnum;
	}

	public void setSerialnum(int serialnum) {
		this.serialnum = serialnum;
	}

	public String getSendstate() {
		return sendstate;
	}

	public void setSendstate(String sendstate) {
		this.sendstate = sendstate;
	}

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCallback() {
		return callback;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}

	public String getRsltdate() {
		if(rsltdate != null && rsltdate.length() >= 16){
			return rsltdate.substring(5, 16);
		}
		
		return rsltdate;
	}

	public void setRsltdate(String rsltdate) {
		this.rsltdate = rsltdate;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getNet() {
		return net;
	}

	public void setNet(String net) {
		this.net = net;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getUserIndex() {
		return userIndex;
	}

	public void setUserIndex(int userIndex) {
		this.userIndex = userIndex;
	}

	public String getRealsenddate() {
		if(realsenddate != null && realsenddate.length() >= 16){
			return realsenddate.substring(5, 16);
		}		
		return realsenddate;
	}

	public void setRealsenddate(String realsenddate) {
		this.realsenddate = realsenddate;
	}

	public String getRsltstat() {
		return rsltstat;
	}

	public void setRsltstat(String rsltstat) {
		this.rsltstat = rsltstat;
	}
	
	/**
	 * 결과코드값 메시지 출력
	 * @return
	 */
	public String getResultCodeMessage(){
		try{
			return AgentResultMsg.getSmsMsg(Integer.valueOf(rsltstat));
		}catch(Exception e){}
		
		return "";
	}
}
