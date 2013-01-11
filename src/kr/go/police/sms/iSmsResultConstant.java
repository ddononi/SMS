package kr.go.police.sms;

public interface iSmsResultConstant {
	
	// �߼� ����
	public final static int SENDSTAT_READY_NUM = 0;		// �߼۴��
	public final static int SENDSTAT_COMPLETSMS_E_NUM = 1;	// ���ۿϷ�
	public final static int SENDSTAT_RECIVSMS_E_NUM = 2;		//  ������ſϷ�
	
	// �߼� ���
	// SMS ��� �ڵ� ��
	public final static int SMS_E_OK = 0;						//  ������ſϷ�
	public final static int SMS_E_SYSFAIL = 1;				//  �ý��� ���
	public final static int SMS_E_AUTH_FAIL = 2;			//  ��������
	public final static int SMS_E_FORMAT_ERR = 3;		// �޽��� ���� ����
	public final static int SMS_E_NOT_BOUND = 4;		//  BIND �ȵ�
	public final static int SMS_E_NO_DESTIN = 5;			// ���Ű����� ����(�̵��)
	public final static int SMS_E_SENT = 6;					//  ���ۼ���
	public final static int SMS_E_INVAILDDST = 7;		//  ������, ���, ��������
	public final static int SMS_E_POWEROFF = 8;			// �ܸ��� Power-off ����
	public final static int SMS_E_HIDDEN = 9;				// ����
	public final static int SMS_E_TERMFULL = 10;			// �ܸ��� �޽��� FULL
	public final static int SMS_E_TIMEOUT = 11;			// Ÿ�Ӿƿ�
	public final static int SMS_E_ETC = 14;					// ����������
	public final static int SMS_E_NO_URLUSER = 17;		// CALLBACKURL ����� �ƴ�
	public final static int SMS_E_DUP_MSG = 18;			// �޽��� �ߺ� �߼�
	public final static int SMS_E_FLOWCONTROL = 19;	// �� �۽� �Ǽ� �ʰ�
	public final static int SMS_E_UNKOWN = 20;			// ��Ÿ����
	public final static int SMS_E_DEST_SIZE = 21;			// ���Ź�ȣ ����(�ڸ�������)
	public final static int SMS_E_DEST_CODE = 22;		// ���Ź�ȣ ����(���� ����)
	public final static int SMS_E_MSG_FMT = 23;			// ���Űź� �޽��� ����
	public final static int SMS_E_ADV21 = 24;				// 21�� ���� ����
	public final static int SMS_E_ADV = 25;					// ���α���, ���Ɽ�� �� ��Ÿ ����
	public final static int SMS_E_DACOM_SPAM = 26;	// ������ ���� ���͸�
	public final static int SMS_E_NIGTHBLOCK = 27;		// �߰��߼�����
	public final static int SMS_E_NO_ALLOW = 40;		// �ܸ������Űź�(���Ե�)
	public final static int SMS_E_FORBINDDEN_TIME = 91;			// �߼� ����� �ð� �� �߼� ���� ó��
	public final static int SMS_E_DUP =99;					// 	�޽��� �ߺ�	
	
	
	// LMS, MMS ��� �ڵ� ��
	public final static int MMS_E_OK = 1000;								//  ����
	public final static int MMS_E_FORMAT_ERR = 2000;					// ���˿���
	public final static int MMS_E_INVALID_PHONE = 2001;				//	�߸��� ��ȣ	
	public final static int MMS_E_CONTENT_ERR = 2002;				//	������ ������ �� ���� �ʰ�
	public final static int MMS_E_INVALID_CONTENTS = 2003;		//	�߸��� ������
	public final static int MMS_E_UNAVAILABLE_PHONE = 3000;		//	����� MMS ������ �ܸ���
	public final static int MMS_E_MSG_FULL = 3001;						// �ܸ��� �޽��� ���尳�� �ʰ�
	public final static int MMS_E_TIMEOUT = 3002;						//	���۽ð� �ʰ�
	public final static int MMS_E_POWER_OFF = 3004;					//	���� ����
	public final static int MMS_E_EXP_SMS = 3005;						//	��������
	public final static int MMS_E_ETC = 3006;								//	��Ÿ
	public final static int MMS_E_SYS_ERR = 4000;							//	���������� ���� ���� ����
	public final static int MMS_E_STOP_PHONE = 4001;					//	�ܸ��� �Ͻ� ���� ����
	public final static int MMS_E_TELECOM_ERR = 4002;				//	��Ż� ���� ����(��������)
	public final static int MMS_E_BUSY_FAIL = 4003;						//	������ �Ͻ����� ����
	public final static int MMS_E_DENY = 4101;							//	���� ����
	public final static int MMS_E_REJECT_IP = 4102;						//	������ ���� IP����
	public final static int MMS_E_INSUFFICIENT = 4104;					//	�Ǽ� ����
	public final static int MMS_E_REJECT_ITER = 4201;					//	���� MMS �߼� ������ ����
	public final static int MMS_E_PORTED = 5000;							//	��ȣ�̵�����
	public final static int MMS_E_NOCDR = 5001;							//	���ҹ߱� �߼۰Ǽ� �ʰ�
	public final static int MMS_E_SPAM = 5003;							//	����
	public final static int MMS_E_DUP_KEY = 5201;						//	�ߺ��� Ű ���� ����
	public final static int MMS_E_DUP_PHONE = 5202;					//	�ߺ��� ���Ź�ȣ ���� ����
	public final static int MMS_E_FORBIDDEN_TIME = 9001;			//	�߼� �����ð�
	public final static int MMS_E_PHONE_NUM = 9002;					//	�� �ѹ� ����
	public final static int MMS_E_SPAM_NUM = 9003;					//	���Թ�ȣ
	public final static int MMS_E_LO_TIMEOUT = 9004;					//	����翡�� �������
	public final static int MMS_E_FILE_SIZE = 9005;						//	����ũ�����
	public final static int MMS_E_FILE_FORMAT = 9006;					//	�������� �ʴ� ����
	public final static int MMS_E_FILE_INVALID = 9007;					//	���Ͽ���
	public final static int MMS_E_MSG_TYPE = 9008;						//	MMS_MSG�� MSG_TYPE�� ����
	public final static int MMS_E_DUP_MSG = 9009;						//	�����ð��̳� �ߺ�
	public final static int MMS_E_EXCEED_RETRY = 9010;				// ������ Ƚ�� �ʰ��� ����
	public final static int MMS_E_OLD_REQ = 9011;						//	�߼��������� ���� ����
}
