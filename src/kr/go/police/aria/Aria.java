package kr.go.police.aria;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;

import kr.go.police.SMSUtil;

import org.apache.http.util.ByteArrayBuffer;

public class Aria {
	/* aria */
    public static final String MASTER_KEY = "everontech";	// ������ Ű	
    public static final short MASTER_KEY_LENGTH = 32;		// ������ Ű ����Ʈ��    
    public static final short MASTER_KEY_BIT_LENGTH = 192;
    public static final int ENCRPTY_LENGTH = 128;
    private static Aria instance;
	private byte[] masterkey;	// DEBUG��
	ARIAEngine ariaEngine;
	
	private Aria(){
		// TODO Auto-generated constructor stub
		this.masterkey = setMasterKey(MASTER_KEY);
		//Log.i(BaseActivity.DEBUG_TAG, "master key1 : " + masterkey.toString() );
	    try {
			this.ariaEngine = new ARIAEngine(MASTER_KEY_BIT_LENGTH);
		    this.ariaEngine.setKey(this.masterkey);
		    this.ariaEngine.setupRoundKeys();			
		} catch (InvalidKeyException e) {}			
	}
	
	/**
	 * �̱��ϰ�ü
	 * @return
	 */
	public static Aria getInstance(){
		if(instance == null)
			instance = new Aria();
		return instance;
	}

	private byte[] setMasterKey(String key){
		ByteBuffer bytebuffer = ByteBuffer.allocate(MASTER_KEY_LENGTH);	
		bytebuffer.put(key.getBytes());		
		return bytebuffer.array();	  
	}
	
	/**
	 * ��ȣȭ�� ����Ʈ�� ��� ��Ʈ������ ��ȯ
	 * @return
	 */
	public String encryptByte2HexStr(String str){
		if(str == null || str.length() <= 0){
			return null;
		}
		ByteBuffer bytebuffer = ByteBuffer.allocate(ENCRPTY_LENGTH);	
		bytebuffer.put(str.getBytes());			
		byte[] enBtes = this.encryptPacket(bytebuffer.array(), ENCRPTY_LENGTH);
		return SMSUtil.byteArrayToHex(enBtes);
	}
	
	/**
	 * ��ȣȭ�� ��� ��Ʈ���� ��ȣȭ
	 * @return
	 */
	public String encryptHexStr2DecryptStr(String str){
		if(str == null || str.length() <= 0){
			return null;
		}
		
		ByteBuffer bytebuffer = ByteBuffer.allocate(ENCRPTY_LENGTH);	
		bytebuffer.put(SMSUtil.hexToByteArray(str));						
		byte[] bytes = this.decryptPacket(bytebuffer.array(), ENCRPTY_LENGTH);
		return new String(bytes);		
	}	
	
	
	public byte[] encryptPacket(byte[] plain, int encryptSize ){
		byte[] encrypted = new byte[16];	// ��ȣ�� ����  byte
        byte[] encrypt = new byte[16];		
		ByteArrayBuffer byteArrayBuffer = new ByteArrayBuffer(encryptSize); //��ȣȭ�� �����ŭ �ʱ�ȭ 
		//byteArrayBuffer.clear();	// ����Ʈ�迭�� �����
        int nPlainBytes = plain.length;	// plain ���ڿ� ���̰�
        //  16�� ������ �߶� �� ����
        //  16���� ���� �������� ������ �� ũŰ +1 �߰� 
        int nBlockNum = (nPlainBytes % 16 == 0) ? (nPlainBytes / 16) : (nPlainBytes / 16 + 1);
        int cnt = 0;
		try {
			// �� ũ�⸸ŭ 16����Ʈ�� �߶� ��ȣȭ ��Ŵ			
	        for(int i = 0; i < nBlockNum ; i++) {
	            for(int j =i*16; j < ((i+1)*16); j++)
	            	encrypt[cnt++] = plain[j];	
	            cnt = 0;
	            ariaEngine.encrypt(encrypt, 0, encrypted, 0); // ��ȣ����
	            //Log.i(BaseActivity.DEBUG_TAG, "encrypting-" + i );
	        	byteArrayBuffer.append( encrypted, 0, encrypted.length );	//	����Ʈ �迭�� �߰� 
	        }				
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			//Log.i(BaseActivity.DEBUG_TAG, "error_encryptPacket : " + ARIAEngine.bytesToHex(byteArrayBuffer.toByteArray()) );
			return null;
		}
		//Log.i(BaseActivity.DEBUG_TAG, "encrypted" );
		//Log.i(BaseActivity.DEBUG_TAG, "encrypt-byteToHex : " + ARIAEngine.bytesToHex(byteArrayBuffer.toByteArray()) );
		return byteArrayBuffer.toByteArray();
		
	}
	
	public byte[] decryptPacket(byte[] encrypted, int decryptSize ){
		byte[] plain = new byte[16];	// ��ȣ�� ���� byte
		byte[] temp = new byte[16];	
		ByteArrayBuffer byteArrayBuffer = new ByteArrayBuffer(decryptSize); //��ȣȭ�� �����ŭ �ʱ�ȭ 
        int nEncryptBytes = encrypted.length;	// plain ���ڿ� ���̰�
        //  16�� ������ �߶� �� ����
        //  16���� ���� �������� ������ �� ũŰ +1 �߰� 
        int nBlockNum = (nEncryptBytes % 16 == 0) ? (nEncryptBytes / 16) : (nEncryptBytes / 16 + 1);
        int cnt = 0;
		try {
			// �� ũ�⸸ŭ 16����Ʈ�� �߶� ��ȣȭ ��Ŵ
	        for (int i = 0; i < nBlockNum ; i++) {	
	            for(int j =i*16; j < ((i+1)*16); j++)
	            	plain[cnt++] = encrypted[j];
	            cnt = 0;
	        	ariaEngine.decrypt(plain, 0, temp, 0);
	        //	Log.i(BaseActivity.DEBUG_TAG, "decrypting-" + i );
	        	byteArrayBuffer.append( temp, 0, temp.length );
	        }			
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
		//	Log.i(BaseActivity.DEBUG_TAG, "decryptPacket-error : " + e.getMessage() );
			return null;
		}		
		//Log.i(BaseActivity.DEBUG_TAG, "decrypted" );
		//Log.i(BaseActivity.DEBUG_TAG, "decrypt : " + (new String(byteArrayBuffer.toByteArray())).trim() );
		return byteArrayBuffer.toByteArray();	
		
	}
	
}
