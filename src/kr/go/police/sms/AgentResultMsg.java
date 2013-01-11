package kr.go.police.sms;

/**
 *  LG ���÷��� ��� �ڵ�
 * ���� ��忡 ���� �ڵ尪 �޽����� ��Ī ó�� Ŭ����
 * @author juwan
 *
 */
public class AgentResultMsg implements iSmsResultConstant{

	/**
	 * SMS ��� ���Ű� ��Ī
	 * @param resultCode
	 * @return
	 */
	public static String getSmsMsg(int resultCode) {
		switch(resultCode){
		case SMS_E_SENT :
			return "����";		
		case SMS_E_SYSFAIL :
			return "�ý��� ���";
		case SMS_E_AUTH_FAIL :
			return "��������, ���� ������ ����";
		case SMS_E_FORMAT_ERR :
			return "�޽��� ���� ����";
		case SMS_E_NOT_BOUND :
			return "BIND �ȵ�";
		case SMS_E_NO_DESTIN :
			return "���Ű����� ����(�̵��)";
		case SMS_E_INVAILDDST :
			return "������, ���, ��������";
		case SMS_E_POWEROFF :
			return "�ܸ��� Power-off ����";
		case SMS_E_HIDDEN :
			return "����";
		case SMS_E_TERMFULL :
			return "�ܸ��� �޽��� FULL";
		case SMS_E_TIMEOUT :
			return "Ÿ�Ӿƿ�";
		case SMS_E_ETC :
			return "����������";
		case SMS_E_NO_URLUSER :
			return "CALLBACKURL ����� �ƴ�";
		case SMS_E_DUP_MSG :
			return "�޽��� �ߺ� �߼�";
		case SMS_E_FLOWCONTROL :
			return "�� �۽� �Ǽ� �ʰ�";
		case SMS_E_UNKOWN :
			return "��Ÿ����";			
		case SMS_E_DEST_SIZE :
			return "���Ź�ȣ ����(�ڸ�������)";				
		case SMS_E_DEST_CODE :
			return "���Ź�ȣ ����(���� ����)";					
		case SMS_E_MSG_FMT :
			return "���Űź� �޽��� ����";				
		case SMS_E_ADV21 :
			return "21�� ���� ����";	
		case SMS_E_ADV :
			return "���α���, ���Ɽ�� �� ��Ÿ ����";	
		case SMS_E_DACOM_SPAM :
			return "������ ���� ���͸�";	
		case SMS_E_NIGTHBLOCK :
			return "�߰��߼�����";			
		case SMS_E_NO_ALLOW :
			return "�ܸ������Űź�(���Ե�)";				
		case SMS_E_FORBINDDEN_TIME :
			return "�߼� ����� �ð� �� �߼� ���� ó��";				
		case SMS_E_DUP :
			return "�޽��� �ߺ�";	
			
		}
		return  "������� ����";
	}
	
	
	/**
	 * MMS ��� ���Ű� ��Ī
	 * @param resultCode
	 * @return
	 */
	public static String getMmsMsg(int resultCode) {	
		switch(resultCode){
		case MMS_E_OK :
			return "����";		
		case MMS_E_FORMAT_ERR :
			return "���˿���";
		case MMS_E_INVALID_PHONE :
			return "�߸��� ��ȣ";
		case MMS_E_CONTENT_ERR :
			return "������ ������ �� ���� �ʰ�";	
		case MMS_E_INVALID_CONTENTS :
			return "�߸��� ������";	
		case MMS_E_UNAVAILABLE_PHONE :
			return "����� MMS ������ �ܸ���";		
		case MMS_E_MSG_FULL :
			return "�ܸ��� �޽��� ���尳�� �ʰ�";			
		case MMS_E_TIMEOUT :
			return "���۽ð� �ʰ�";	
		case MMS_E_POWER_OFF :
			return "���� ����";		
		case MMS_E_EXP_SMS :
			return "��������";		
		case MMS_E_ETC :
			return "��Ÿ";			
		case MMS_E_SYS_ERR :
			return "���������� ���� ���� ����";		
		case MMS_E_STOP_PHONE :
			return "�ܸ��� �Ͻ� ���� ����";					
		case MMS_E_TELECOM_ERR :
			return "��Ż� ���� ����(��������)";				
		case MMS_E_BUSY_FAIL :
			return "������ �Ͻ����� ����";				
		case MMS_E_DENY :
			return "���� ����";	
		case MMS_E_REJECT_IP :
			return "������ ���� IP����";			
		case MMS_E_INSUFFICIENT :
			return "�Ǽ� ����";			
		case MMS_E_REJECT_ITER :
			return "���� MMS �߼� ������ ����";				
		case MMS_E_PORTED :
			return "��ȣ�̵�����";			
		case MMS_E_NOCDR :
			return "���ҹ߱� �߼۰Ǽ� �ʰ�";				
		case MMS_E_SPAM :
			return "����";			
		case MMS_E_DUP_KEY :
			return "�ߺ��� Ű ���� ����";			
		case MMS_E_DUP_PHONE :
			return "�ߺ��� ���Ź�ȣ ���� ����";				
		case MMS_E_FORBIDDEN_TIME :
			return "�߼� �����ð�";		
		case MMS_E_PHONE_NUM :
			return "�� �ѹ� ����";			
		case MMS_E_SPAM_NUM :
			return "���Թ�ȣ";		
		case MMS_E_LO_TIMEOUT :
			return "����翡�� �������";		
		case MMS_E_FILE_SIZE :
			return "����ũ�����";		
		case MMS_E_FILE_FORMAT :
			return "�������� �ʴ� ����";			
		case MMS_E_FILE_INVALID :
			return "���Ͽ���";			
		case MMS_E_MSG_TYPE :
			return "MMS_MSG�� MSG_TYPE�� ����";	
		case MMS_E_DUP_MSG :
			return "�����ð��̳� �ߺ�";			
		case MMS_E_EXCEED_RETRY :
			return "������ Ƚ�� �ʰ��� ����";			
		case MMS_E_OLD_REQ :
			return "�߼��������� ���� ����";				
		}
		return  "������� ����";
	}
}
