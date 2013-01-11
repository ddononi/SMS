package kr.go.police.sms;

/**
 * LMS, MMS bean
 * 추후 mms, lms, sms 공통 변수는 인터페이스 처리 
 */
public class LGMMSBean {
	private long index; // 인덱스
	private String userId; // 유저 아이디
	private int userIndex; // 유저 인덱스
	private String subject; // 제목
	private String phone; // 수신할 전화번호
	private String callback; // 송신자 전화번호
	private String sendstat; // 발송상태
	private String senddate; // 발송시간(예약시간)
	private String msg; // 메세지
	private int fileCnt; // 전송 파일 갯수
	private int fileCntReal; // 에이전트가 실제로 체크한 전송파일갯수
	private String filePath1; // 전송파일1 위치
	private int file1Size; // 파일1 사이즈
	private String filePath2; // 전송파일2 위치
	private int file2Size; // 파일2 사이즈
	private String filePath3; // 전송파일3 위치
	private int file3Size; // 파일3 사이즈
	private String filePath4; // 전송파일4 위치
	private int file4Size; // 파일4 사이즈
	private String filePath5; // 전송파일5 위치
	private int file5Size; // 파일5 사이즈
	private String realsenddate; // 송신완료시간
	private String rsltdate; // 에이전트가 수신받은시간
	private String reportdate; // 결과 수신 받은 시간
	private String termniateddate; // 메세지 처리가 완료된 시간
	private String rsltstat; // 결과값
	private String type; // mms,ul, html
	private String telcoinfo; // 이통사 구분코드

	public long getIndex() {
		return index;
	}

	public void setIndex(long index) {
		this.index = index;
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

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
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

	public String getSendstat() {
		return sendstat;
	}

	public void setSendstat(String sendstat) {
		this.sendstat = sendstat;
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

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getFileCnt() {
		return fileCnt;
	}

	public void setFileCnt(int fileCnt) {
		this.fileCnt = fileCnt;
	}

	public int getFileCntReal() {
		return fileCntReal;
	}

	public void setFileCntReal(int fileCntReal) {
		this.fileCntReal = fileCntReal;
	}

	public String getFilePath1() {
		return filePath1;
	}

	public void setFilePath1(String filePath1) {
		this.filePath1 = filePath1;
	}

	public int getFile1Size() {
		return file1Size;
	}

	public void setFile1Size(int file1Size) {
		this.file1Size = file1Size;
	}

	public String getFilePath2() {
		return filePath2;
	}

	public void setFilePath2(String filePath2) {
		this.filePath2 = filePath2;
	}

	public int getFile2Size() {
		return file2Size;
	}

	public void setFile2Size(int file2Size) {
		this.file2Size = file2Size;
	}

	public String getFilePath3() {
		return filePath3;
	}

	public void setFilePath3(String filePath3) {
		this.filePath3 = filePath3;
	}

	public int getFile3Size() {
		return file3Size;
	}

	public void setFile3Size(int file3Size) {
		this.file3Size = file3Size;
	}

	public String getFilePath4() {
		return filePath4;
	}

	public void setFilePath4(String filePath4) {
		this.filePath4 = filePath4;
	}

	public int getFile4Size() {
		return file4Size;
	}

	public void setFile4Size(int file4Size) {
		this.file4Size = file4Size;
	}

	public String getFilePath5() {
		return filePath5;
	}

	public void setFilePath5(String filePath5) {
		this.filePath5 = filePath5;
	}

	public int getFile5Size() {
		return file5Size;
	}

	public void setFile5Size(int file5Size) {
		this.file5Size = file5Size;
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

	public String getRsltdate() {
		if(rsltdate != null){
			return rsltdate.substring(5, 16);
		}		
		return rsltdate;
	}

	public void setRsltdate(String rsltdate) {
		this.rsltdate = rsltdate;
	}

	public String getReportdate() {
		return reportdate;
	}

	public void setReportdate(String reportdate) {
		this.reportdate = reportdate;
	}

	public String getTermniateddate() {
		return termniateddate;
	}

	public void setTermniateddate(String termniateddate) {
		this.termniateddate = termniateddate;
	}


	public String getRsltstat() {
		return rsltstat;
	}

	public void setRsltstat(String rsltstat) {
		this.rsltstat = rsltstat;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTelcoinfo() {
		return telcoinfo;
	}

	public void setTelcoinfo(String telcoinfo) {
		this.telcoinfo = telcoinfo;
	}
	
	/**
	 * 결과코드값 메시지 출력
	 * @return
	 */
	public String getResultCodeMessage(){
		try{
			return AgentResultMsg.getMmsMsg(Integer.valueOf(rsltstat));
		}catch(Exception e){}
		
		return "";
	}	

}
