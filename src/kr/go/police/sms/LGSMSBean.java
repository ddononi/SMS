package kr.go.police.sms;

/**
 * ���� ���� dto(LG U+ ��)
 * ���� mms, lms, sms ���� ������ �������̽� ó�� 
 */
public class LGSMSBean  {
	private long index; // �޼��� ���� ��ȣ
	private String senddate; // �޽��� ������ �ð�, �̷��ð��� ������ ���� �߼�
	private int serialnum; // �ø���ѹ�(������)
	private String sendstate; // �߼ۻ���
	private String msgtype; // ������������
	private String rsltstat;	// �߼� ��� ���Ű�
	private String phone; // ������ �ڵ��� ��ȣ
	private String callback; // �۽��� ��ȭ��ȣ
	private String rsltdate; // �̵���Ż�κ��� ����� �뺸 ���� �ð�
	private String realsenddate;	//  ���� �߼۽ð�	
	private String msg; // ������ �޼���
	private String net; // ���ۿϷ��� ���� �̵���Ż� ����
	private String userId; // ���� ���̵�
	private int userIndex; // ���� �ε���
	
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
	 * ����ڵ尪 �޽��� ���
	 * @return
	 */
	public String getResultCodeMessage(){
		try{
			return AgentResultMsg.getSmsMsg(Integer.valueOf(rsltstat));
		}catch(Exception e){}
		
		return "";
	}
}
