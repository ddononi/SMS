package kr.go.police.sms;

public interface iSmsResultConstant {
	
	// �߼� ����
	public final static int SENDSTAT_READY_NUM = 0;		// �߼۴��
	public final static int SENDSTAT_COMPLETE_NUM = 1;	// ���ۿϷ�
	public final static int SENDSTAT_RECIVE_NUM = 2;		//  ������ſϷ�
	
	// �߼� ���
	public final static String E_OK = "00";						//  ������ſϷ�
	public final static String E_SYSFAIL = "01";				//  �ý��� ���
	public final static String E_AUTH_FAIL = "02";			//  ��������,
	public final static String E_SENT = "06";					//  ���ۼ���
	
}
