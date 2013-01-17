package kr.go.police.sms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import kr.go.police.CommonCon;
import kr.go.police.aria.Aria;
import kr.go.police.board.BoardBean;

/**
 * ���� ����, ���� ����
 * ������ ���� Dao
 */
public class SmsDAO extends CommonCon {
	DataSource dataSource;
	public ResultSet rs;
	public PreparedStatement pstmt;
	public Connection conn;
	
	public SmsDAO(){
		dataSource = getDataSource();
		if(dataSource == null){
			return;
		}
	}
	
	/**
	 * �� ������ ���
	 * @param userIndex
	 * 	���� �ε���
	 * @param groupIndex
	 * 	�׷� �ε���
	 * @return
	 * �޽��� ����Ʈ
	 */
	protected List<Message> getMyMessages(int userIndex, int groupIndex){
		List<Message> list = new ArrayList<Message>();
		Message data = null;		
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM message WHERE" +
					" f_user_index =?  AND f_group_index = ? ");
			pstmt.setInt(1, userIndex);
			pstmt.setInt(2, groupIndex);
			rs = pstmt.executeQuery();
			
			while(rs.next())	{
				// �ε���, ��Ͼ��̵�, ����, ����, �׷��ε���, �׷��
			    data = new Message();	
			    data.setIndex(rs.getInt("f_index"));
			    data.setId(rs.getString("f_id"));	  		
			    data.setTitle(rs.getString("f_message_title"));			    
			    data.setMessage(rs.getString("f_message_text"));
			    data.setGroupIndex(rs.getInt("f_group_index"));
			    data.setGroup(rs.getString("f_message_group"));
	
				list.add(data);
			}		
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getMyMessages ���� : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}
	}

	/**
	 * 	������ ������ �޼��� ����
	 * @param index
	 * 		�޽��� �ε���
	 * @return
	 */
	protected Message getMyMessage(int index){
		Message data = null;		
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM message WHERE" +
					" f_index =?  ");
			pstmt.setInt(1, index);
			rs = pstmt.executeQuery();
			while(rs.next())	{
				// �ε���, ��Ͼ��̵�, ����, ����, �׷��ε���, �׷��
			    data = new Message();	
			    data.setIndex(rs.getInt("f_index"));
			    data.setId(rs.getString("f_id"));	  		
			    data.setTitle(rs.getString("f_message_title"));			    
			    data.setMessage(rs.getString("f_message_text"));
			    data.setGroupIndex(rs.getInt("f_group_index"));
			    data.setGroup(rs.getString("f_message_group"));
			}		
			return data;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getMyMessage ���� : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}
	}	
	
	/**
	 * �� ������ ���
	 * @param userIndex
	 * 	���� �ε���
	 * @param groupIndex
	 * 	�׷� �ε���
	 * @return
	 * �޽��� ����Ʈ
	 */
	/*
	public List<Message> getMessageGroupList(int userIndex){
		List<Message> list = new ArrayList<Message>();
		Message data = null;		
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM message_group WHERE" +
					" f_user_index =? ORDER BY f_index ASC ");
			pstmt.setInt(1, userIndex);
			rs = pstmt.executeQuery();
			
			while(rs.next())	{
				// �׷��ε���, �׷��
			    data = new Message();	
			    data.setGroupIndex(rs.getInt("f_index"));
			    data.setGroup(rs.getString("f_group"));
				list.add(data);
			}		
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getMessageGroupList ���� : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}
	}	
	*/
	

	/**
	 * ���� ���� ����Ʈ ��������
	 * @param userIndex
	 * 	���� �ε���
	 * @param pae
	 * 	������
	 * @param limit
	 *  ����������
	 * @return
	 */
	protected List<SMSBean> getReservedList(int userIndex, int page, int limit){
		List<SMSBean> list = new ArrayList<SMSBean>();
		SMSBean data = null;		
		int startRow = (page -1 ) * 10 +1;		// ���� ��ȣ
		int endRow = startRow + limit -1;		// �� ��ȣ
		try {
			conn = dataSource.getConnection();
			// �ش� ������ ����� ���ڳ����� �����´�.
			pstmt = conn.prepareStatement("SELECT * FROM send_sms_info " +
					"WHERE f_reserve_date != null " +
					"AND f_user_index = ? " +
					"ORDER BY f_index DESC " +
					"LIMIT ?, ?");
			pstmt.setInt(1, userIndex);			
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);			
			rs = pstmt.executeQuery();
			
			while(rs.next())	{
			    data = new SMSBean();	
			    data.setId(rs.getString("f_id"));
			    data.setMessage(rs.getString("f_message"));
			    data.setToPhone(rs.getString("f_to_phone"));
			    data.setFromPhone(rs.getString("f_from_phone"));			    
			    data.setSendDate(rs.getString("f_reserve_date"));	
			    data.setUserIndex(rs.getInt("f_user_index"));	
			    
				list.add(data);
  			}		
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getReservedList ���� : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}

	}		

	/**
	 *  ���� ��� ���� ����Ʈ
	 * @param userIndex
	 * 		���� �ε���
	 * @param start
	 * @param end
	 * @param search
	 * @return	
	 */
	public List<LGSMSBean> getSendResultList(
			int userIndex, int start, int end, String type, String search) {
		List<LGSMSBean> list = new ArrayList<LGSMSBean>();
		LGSMSBean data = null;
		String sql = "";
		try {
			conn = dataSource.getConnection();
			/*
			pstmt = conn.prepareStatement("SELECT * FROM sms_send_info WHERE" +
					" f_callto like ? AND f_user_index = ? AND f_is_del = 'n'  " +
					" ORDER BY f_index DESC LIMIT ?, ? ");
			pstmt.setString(1, "%" + search + "%");	
			pstmt.setInt(2, userIndex);				
			pstmt.setInt(3, start -1);
			pstmt.setInt(4, end);				
			rs = pstmt.executeQuery();
			Aria aria = Aria.getInstance();	
			while(rs.next())	{
				// ���� ������ ��´�.
			    data = new SMSBean();	
			    data.setIndex(rs.getLong("f_index"));
			    data.setToPhone(rs.getString("f_callto"));			    
			    data.setFromPhone(rs.getString("f_callfrom"));
			    data.setMessage(rs.getString("f_message"));
			    data.setSendState(rs.getInt("f_send_state")  == 0);
			    data.setRequestResult(rs.getInt("f_request_result_code"));
			    data.setResponseResult(rs.getInt("f_response_result_code"));			    
			    data.setRegDate(rs.getString("f_reg_date"));   
			    data.setCallback(rs.getString("f_callback"));
			    data.setFlag(rs.getString("f_flag"));	    
			    data.setResultMsg(rs.getString("f_result_msg"));					    
				list.add(data);
  			}
  			*/
		//	Calendar cal = Calendar.getInstance();
		//	String logTable = "";
			//while(true){
			/*
				//  �ش� �� ���̺��� �ִ� �˻�
				pstmt = conn.prepareStatement("SHOW TABLES LIKE ? ");
				String month = String.format("%02d", Integer.valueOf(cal.get(Calendar.MONTH) + 1));
				logTable =  "sc_log_" + cal.get(Calendar.YEAR) + "" + month;
				
				pstmt.setString(1, logTable);
				rs = pstmt.executeQuery();
				rs.last();
				//	�ش� ���̺��� ������
				if( rs.getRow() <= 0 ){
					System.out.println("e : " + logTable);					
					break;
				}
				// �����޷� �̵�
				cal.add(Calendar.MONTH, -1);
			*/
			sql = "SELECT * FROM sc_log WHERE ";
			if(type.equalsIgnoreCase("to")){			// �޴� ��ȣ�� �˻�
				sql += " tr_phone like ? ";
			}else if(type.equalsIgnoreCase("message")){		// �޼����� �˻�
				sql += "  tr_msg like ? ";
			}
			
			sql +=" AND tr_etc2 = ? AND tr_etc3 = 'n'  " +
				" ORDER BY tr_num DESC LIMIT ?, ? ";			
			
			pstmt = conn.prepareStatement(sql);			
			pstmt.setString(1, "%" + search + "%");	
			pstmt.setString(2, ""+ userIndex);				
			pstmt.setInt(3, start -1);
			pstmt.setInt(4, end);				
			rs = pstmt.executeQuery();
			while(rs.next())	{
				// ���� ������ ��´�.
			    data = new LGSMSBean();	
			    data.setIndex(rs.getLong("tr_num"));
			    data.setSenddate(rs.getString("tr_senddate"));				    
			    data.setPhone(rs.getString("tr_phone"));			    
			    data.setCallback(rs.getString("tr_callback"));
			    data.setMsg(rs.getString("tr_msg"));
			    data.setRsltstat(rs.getString("tr_rsltstat"));
			    data.setRealsenddate(rs.getString("tr_realsenddate"));			    
			    data.setRsltdate(rs.getString("tr_rsltdate"));   
			    data.setSendstate(rs.getString("tr_sendstat"));					    
				list.add(data);
  			}
		//	}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getSendResultList ���� : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}
	}
	
	
	/**
	 * �� ������ �׷츮��Ʈ
	 * @param userIndex
	 * 	���� �ε���
	 * @return
	 */
	public List<Group> getMyGroupList(int userIndex) {
		List<Group> list = new ArrayList<Group>();
		Group data = null;		
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM message_group WHERE" +
					" f_user_index = ? ORDER BY f_index ASC ");
			pstmt.setInt(1, userIndex);	
			rs = pstmt.executeQuery();
			while(rs.next()){
				// ������ �׷�
			    data = new Group();	
			    data.setIndex(rs.getInt("f_index"));
			    data.setGroup(rs.getString("f_group"));
				list.add(data);
  			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getMyGroupList ���� : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}
	}	
	
	/**
	 * �� ������ �߰�
	 * @param msg
	 * 	message dto
	 */
	public boolean addMyMessage(Message msg) {
		int result = 0;
		try {
			conn = dataSource.getConnection();
			String sql = "INSERT INTO message ( f_id, f_group_index, f_message_group, f_message_title," +
					" f_message_text, f_user_index) VALUES (?, ?, ?, ?, ?, ?) ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, msg.getId());					// ���̵�
			pstmt.setInt(2, msg.getGroupIndex());			// �׷� �ε���
			pstmt.setString(3, msg.getGroup());				// �׷��
			pstmt.setString(4, msg.getTitle());				// ����	
			pstmt.setString(5, msg.getMessage());			// �޼���	
			pstmt.setInt(6, msg.getUserIndex());			// ���� �ε���	
			// update
			result = pstmt.executeUpdate();
			return result > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("addMyMessage ���� : " + e.getMessage());
			return false;
		}finally{
			connClose();
		}
	}
	
	/**
	 * 	������ ����
	 * @param msg
	 * 	�޽��� ��ü
	 * @return
	 */
	public boolean modifyMyMessage(Message msg) {
		int result = 0;
		try {
			conn = dataSource.getConnection();
			String sql = "UPDATE message  SET   f_message_title = ?, f_message_text = ? " +
					"WHERE f_index = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, msg.getTitle());				// ����	
			pstmt.setString(2, msg.getMessage());			// �޼���	
			pstmt.setInt(3, msg.getIndex());					//  �ε���	
			// update
			result = pstmt.executeUpdate();
			return result > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("modifyMyMessage ���� : " + e.getMessage());
			return false;
		}finally{
			connClose();
		}
	}	

	/**
	 * 	�޼��� ����
	 * @param index
	 * 	�޼��� �ε���
	 * @return
	 */
	public boolean delMessage(int index) {
		int result = 0;
		try {
			conn = dataSource.getConnection();
			String sql = "DELETE FROM message WHERE 1 =1 AND f_index = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, index);						// �޼��� �ε���
			// update
			result = pstmt.executeUpdate();
			return result > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("delMessage ���� : " + e.getMessage());
			return false;
		}finally{
			connClose();
		}
	}		

	/**
	 * 	������ �׷� �߰�
	 * @param userIndex
	 * 		���� �ε���
	 * @param groupName
	 * 		�׷��
	 * @return
	 */
	public boolean addGroup(int userIndex, String groupName) {
		int result = 0;
		try {
			conn = dataSource.getConnection();
			String sql = "INSERT INTO message_group ( f_user_index, f_group) VALUES (?, ?) ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userIndex);							// ���� �ε���
			pstmt.setString(2, groupName);					// �׷� �ε���		

			// update
			result = pstmt.executeUpdate();
			return result > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("addGroup ���� : " + e.getMessage());
			return false;
		}finally{
			connClose();
		}
	}

	/**
	 *	�׷� ����
	 * @param userIndex
	 * 		���� �ε���
	 * @param groupIndex
	 * 		�׷� �ε���
	 * @return
	 */
	public boolean delGroup(int userIndex, int groupIndex) {
		int result = 0;
		try {
			conn = dataSource.getConnection();
			String sql = "DELETE FROM message_group WHERE 1 =1 AND" +
					" f_user_index = ? AND f_index = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userIndex);						// ���� �ε���
			pstmt.setInt(2, groupIndex);					// �׷� �ε���			

			// update
			result = pstmt.executeUpdate();
			return result > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("delGroup ���� : " + e.getMessage());
			return false;
		}finally{
			connClose();
		}
	}

	/**
	 * 	�׷� ����
	 * @param userIndex
	 * 		���� �ε���
	 * @param groupIndex
	 * 		�׷� �ε���
	 * @param groupName
	 * 		�׷��
	 * @return
	 */
	public boolean modifyGroup(int userIndex, int groupIndex, String groupName) {
		int result = 0;
		try {
			conn = dataSource.getConnection();
			String sql = "UPDATE message_group SET f_group = ? WHERE 1 =1 AND" +
					" f_user_index = ? AND f_index = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, groupName);					// �׷��			
			pstmt.setInt(2, userIndex);						// ���� �ε���
			pstmt.setInt(3, groupIndex);					// �׷� �ε���			

			// update
			result = pstmt.executeUpdate();
			return result > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("modifyGroup ���� : " + e.getMessage());
			return false;
		}finally{
			connClose();
		}
	}	
	
	/**
	 * ���ҽ� ��ȯ ��ȯ ������� �ݾ��ش�.
	 */
	public void connClose() {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				System.out.println("rs ����" + e.getMessage());				
			}
		}

		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				System.out.println("pstmt ����" + e.getMessage());
			}
		}

		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("conn ����" + e.getMessage());								
			}
		}
	}

	/**
	 * �� �⺻ �׷� �ε��� ��������
	 * @param userIndex
	 * 		���� �ε���
	 * @return
	 */
	public int getMyBaseGroup(int userIndex) {
		int groupIndex = 0;
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM message_group WHERE" +
					" f_user_index = ? ORDER BY f_index ASC LIMIT 1 ");
			pstmt.setInt(1, userIndex);	
			rs = pstmt.executeQuery();
			if(rs.next()){
			    groupIndex = rs.getInt("f_index");
  			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getMyBaseGroup ���� : " + e.getMessage());
		}finally{
			connClose();
		}
		
		return groupIndex;
	}

	/**
	 * �߼��� ���������߰� �ϱ�
	 * @param list
	 * 	�߼� �������� ����Ʈ
	 * @return
	 * 	�߼� ��� �߰� ����
	 */
	public int addSendList(ArrayList<SMSBean> list) {
		int resultCount = 0;
		try {
			conn = dataSource.getConnection();
			String sql;
			String title = "";
			for(SMSBean data : list){
				/*
				sql = "INSERT INTO sms_send_info ( f_index, f_user_id, f_user_index, f_callto, f_callfrom, f_message, " +
						"f_send_count, f_send_state, f_reserved, f_reserve_date, f_callback," +
						" f_nameto, f_flag, f_reg_date, f_result_msg, f_file1, f_file2, f_file3 )" +
						" VALUES (?, ?, ?, ?, ?, ?, ?, 0, ?, ?, ?, '',  ?, now(), '������' , ?, ?, ?) ";
				*/
				//	80����Ʈ �̻� Ȥ�� ÷�������� ���� ��� LMS, MMS  ����  �ƴϸ� SMS ����
				if(data.getMessage().getBytes().length > 80 ||
						(data.getFile1() != null && !data.getFile1().isEmpty()) ||
						(data.getFile2() != null && !data.getFile2().isEmpty()) ||
						(data.getFile3() != null && !data.getFile3().isEmpty()) ){		// LMS or MMS	
					sql = "INSERT INTO mms_msg " +
							"( subject, reqdate, etc1, etc2, phone, callback, msg, " +
							"etc3, file_path1, file_path2, file_path3, file_cnt) " +
							" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
					pstmt = conn.prepareStatement(sql);
					try{
							// ������ 10���� �̻��ϰ��
						 if(data.getMessage().length() > 10){
							 title = data.getMessage().substring(0, 10);
						 }else{
							 title = data.getMessage();
						 }
					}catch(Exception e){ 
						title = "";
					}
					pstmt.setString(1,  title);							//	����					
					pstmt.setString(2, data.getSendDate());		// �߼۽ð�					
					pstmt.setString(3, data.getId());					// ���� ���̵�
					pstmt.setString(4, ""+data.getUserIndex());	// ����  �ε���				
					pstmt.setString(5, data.getToPhone());			// �޴� ��ȭ��ȣ
					pstmt.setString(6, data.getFromPhone());		// ������ ��ȭ��ȣ				
					pstmt.setString(7, data.getMessage());			// �޼���
					pstmt.setString(8, "n");								// Ȯ�ο���				
					pstmt.setString(9, data.getFile1());				// ����1
					pstmt.setString(10, data.getFile2());				// ����2	
					pstmt.setString(11, data.getFile3());				// ����3
					// ���� ���� ���� üũ
					int fileCount = 0;
					if(data.getFile1() != null && !data.getFile1().isEmpty()) fileCount += 1;
					if(data.getFile2() != null && !data.getFile2().isEmpty()) fileCount += 1;
					if(data.getFile3() != null && !data.getFile3().isEmpty()) fileCount += 1;
					pstmt.setInt(12, fileCount);				// ���� ���� ����					
					System.out.println("insert ~~~~~~~~~~~~~~~");
					resultCount +=  pstmt.executeUpdate();
				}else{			// SMS
					sql = "INSERT INTO sc_tran ( tr_senddate, tr_etc1, tr_etc2, " +
							"tr_phone, tr_callback, tr_msg, tr_etc3) " +
							" VALUES (?, ?, ?, ?, ?, ?, ?) ";
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, data.getSendDate());		// �߼۽ð�					
					pstmt.setString(2, data.getId());					// ���� ���̵�
					pstmt.setString(3, ""+data.getUserIndex());	// ����  �ε���				
					pstmt.setString(4, data.getToPhone());			// �޴� ��ȭ��ȣ
					pstmt.setString(5, data.getFromPhone());		// ������ ��ȭ��ȣ				
					pstmt.setString(6, data.getMessage());			// �޼���
					pstmt.setString(7, "n");			// Ȯ�ο���				
					resultCount +=  pstmt.executeUpdate();
				}
			}
			return resultCount;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("addSmsSendList ���� : " + e.getMessage());
			return 0;
		}finally{
			connClose();
		}
		
	}

	/**
	 * �߼� ���� ���� 
	 * @param userIndex
	 * 		�˻��� ���� �ε���
	 * @param search 
	 * 		�˻���
	 * @param type 
	 * @return
	 */
	public int getSMSSendResultCount(int userIndex, String type, String search) {
		int result = 0;
		String sql = "";
		try {
			conn = dataSource.getConnection();
			//Calendar cal = Calendar.getInstance();
			//String logTable = "";
		//	while(true){
			/*
				//  �ش� �� ���̺��� �ִ� �˻�
				pstmt = conn.prepareStatement("SHOW TABLES LIKE ? ");
				String month = String.format("%02d", Integer.valueOf(cal.get(Calendar.MONTH) + 1));
				logTable =  "sc_log_" + cal.get(Calendar.YEAR) + "" + month;
				
				pstmt.setString(1, logTable);
				rs = pstmt.executeQuery();
				rs.last();
				//	�ش� ���̺��� ������
				if( rs.getRow() <= 0 ){
					System.out.println("e : " + logTable);					
					break;
				}
				// �����޷� �̵�
				cal.add(Calendar.MONTH, -1);
			*/	
			sql ="SELECT  count(*) FROM sc_log WHERE ";
			if(type.equalsIgnoreCase("to")){			// �޴� ��ȣ�� �˻�
				sql += " tr_phone like ? ";
			}else if(type.equalsIgnoreCase("message")){		// �޼����� �˻�
				sql += "  tr_msg like ? ";
			}
			
			sql +=" AND tr_etc2 = ? AND tr_etc3 = 'n'  ";			
			pstmt = conn.prepareStatement(sql);	
			pstmt.setString(1, "%" + search + "%");
			pstmt.setString(2, "" + userIndex);				
			rs = pstmt.executeQuery();
			if(rs.next()){
				result +=  rs.getInt(1);
  			}
		//	}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getSendResultCount ���� : " + e.getMessage());
		}finally{
			connClose();
		}
		return result;
	}

	/**
	 *	 ���� �߼۰��� ���ϱ�
	 * @param search
	 * 		�˻���
	 * @param type
	 * 		�˻� ����
	 * @param psCode 
	 * 		������ �ڵ�
	 * @return
	 */
	public int getReserveAllListCount(String search, String type, int psCode) {
		int result = 0;
		try {
			conn = dataSource.getConnection();
			
			String sql = " SELECT count(s.f_index) FROM sms_send_info s, user_info u " + 
					" WHERE  u.f_index = s.f_user_index AND " +
					" f_reserved = 'y'  AND f_reserve_date > now() AND ";
			if(type.equalsIgnoreCase("from")){	// ������ȭ��ȣ�� �˻�
				sql += "  s.f_user_id like ? ";
			}else if(type.equalsIgnoreCase("message")){		// �޼����� �˻�
				sql += " s.f_message like ? ";
			}else{			// �޴� ��ȭ��ȣ�� �˻�
				sql += " s.f_callto like ? ";
			}
			if(psCode != 100){
				sql += " AND u.f_pscode = " + psCode;
			}
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + search + "%");				
			rs = pstmt.executeQuery();
			if(rs.next()){
				result =  rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getReserveListCount ���� : " + e.getMessage());
		}finally{
			connClose();
		}
		return result;
	}

	
	/**
	 * ��� ���� ���� ����Ʈ
	 * @param start
	 * @param end
	 * @param search
	 * 		�˻���
	 * @param type
	 *		�˻� ����
	 * @param psCode 
	 * 		������ �ڵ�
	 * @return
	 */
	public List<SMSBean> getReserveAllList(int start, int end, String search, String type, int psCode) {
		List<SMSBean> list = new ArrayList<SMSBean>();
		SMSBean data = null;		
		try {
			conn = dataSource.getConnection();
			
			String sql = " SELECT s.* FROM sms_send_info s, user_info u " + 
					" WHERE  u.f_index = s.f_user_index AND " +
					" f_reserved = 'y'  AND f_reserve_date > now() AND ";
			if(type.equalsIgnoreCase("from")){
				sql += " s.f_user_id like ? ";
			}else if(type.equalsIgnoreCase("message")){
				sql += " s.f_message like ? ";				
			}else{
				sql += " s.f_callto like ? ";		
			}
			
			if(psCode != 100){
				sql += " AND u.f_pscode = " + psCode;
			}
			
			sql += " ORDER BY s.f_index DESC LIMIT ?, ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + search + "%");							
			pstmt.setInt(2, start -1);
			pstmt.setInt(3, end);				
			rs = pstmt.executeQuery();
			while(rs.next())	{
				// ���� ������ ��´�.
			    data = new SMSBean();	
			    data.setIndex(rs.getLong("f_index"));
			    data.setId(rs.getString("f_user_id"));
			    data.setUserIndex(rs.getInt("f_user_index"));
			    data.setToPhone(rs.getString("f_callto"));			    
			    data.setFromPhone(rs.getString("f_callfrom"));
			    data.setMessage(rs.getString("f_message"));
			    data.setRequestResult(rs.getInt("f_request_result_code"));
			    data.setResponseResult(rs.getInt("f_response_result_code"));		
			    data.setFlag(rs.getString("f_flag").equalsIgnoreCase("s")?"SMS":"MMS");
			    data.setSendDate(rs.getString("f_reserve_date"));			    
			    data.setRegDate(rs.getString("f_reg_date"));   
			    data.setCallback(rs.getString("f_callback"));
			    
				list.add(data);
			}
			
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getReserveList ���� : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}
	}	
	
	/**
	 * 	���� ���� ����
	 * �����ڰ� ���� �ֵ��� ���������ʵ尪�� ������Ʈó�� �Ѵ�.
	 * @param mode 
	 * 		�߼۸��(sms, lms,mms)
	 * @param index
	 * 	�޼��� �ε���
	 * @return
	 */
	public boolean delSendMessage(String mode, long index) {
		int result = 0;
		try {
			conn = dataSource.getConnection();
			String sql ="";
			if(mode.equals("SMS")){	// SMS ����̸�
				sql = "UPDATE sc_log SET tr_etc3 = 'y'  WHERE tr_num = ? ";
			}else{ 	// LMS, MMS ���
				sql = "UPDATE mms_log SET etc3 = 'y'  WHERE msgkey = ? ";
			}
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, index);						// �޼��� �ε���
			// update
			result = pstmt.executeUpdate();
			return result > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("delSendMessage ���� : " + e.getMessage());
			return false;
		}finally{
			connClose();
		}
	}	
	

	/**
	 *	��� ���� ��� ���� ����Ʈ
	 * @param start
	 * @param end
	 * @param search
	 * 		�˻���
	 * @param type
	 * 		�˻�����
	 * @param psCode 
	 * @return
	 */
	public List<LGSMSBean> getSmsSendAllList(int start, int end, String search, String type, int psCode) {
		/*
		List<SMSBean> list = new ArrayList<SMSBean>();
		SMSBean data = null;		
		try {
			conn = dataSource.getConnection();
			
			String sql = "SELECT s.* FROM sms_send_info s, user_info u " + 
					" WHERE u.f_index = s.f_user_index AND ";
			if(type.equalsIgnoreCase("from")){	// ������ȭ��ȣ�� �˻�
				sql += "  s.f_user_id like ? ";
			}else if(type.equalsIgnoreCase("message")){		// �޼����� �˻�
				sql += " s.f_message like ? ";
			}else{			// �޴� ��ȭ��ȣ�� �˻�
				sql += " f_callto like ? ";
			}
			if(psCode != 100){
				sql += " AND u.f_pscode = " + psCode;
			}
			sql +=  " ORDER BY s.f_index DESC LIMIT ?, ? ";
			
			pstmt = conn.prepareStatement(sql);		
			pstmt.setString(1, "%" + search + "%");	
			pstmt.setInt(2, start -1);
			pstmt.setInt(3, end);				
			rs = pstmt.executeQuery();
			Aria aria = Aria.getInstance();	
			while(rs.next())	{
				// ���� ������ ��´�.
			    data = new SMSBean();	
			    data.setIndex(rs.getLong("f_index"));
			    data.setId(rs.getString("f_user_id"));
			    data.setUserIndex(rs.getInt("f_user_index"));
			    data.setToPhone(rs.getString("f_callto"));			    
			    data.setFromPhone(rs.getString("f_callfrom"));
			    data.setMessage(rs.getString("f_message"));
			    data.setRequestResult(rs.getInt("f_request_result_code"));
			    data.setResponseResult(rs.getInt("f_response_result_code"));		
			    data.setFlag(rs.getString("f_flag").equalsIgnoreCase("s")?"SMS":"MMS");
			    data.setRegDate(rs.getString("f_reg_date"));   
			    data.setCallback(rs.getString("f_callback"));
			    data.setResultMsg(rs.getString("f_result_msg"));			    
				list.add(data);
			}
			*/
		
		List<LGSMSBean> list = new ArrayList<LGSMSBean>();
		LGSMSBean data = null;		
		
		try {
			conn = dataSource.getConnection();
			Calendar cal = Calendar.getInstance();
			String logTable = "";
			String sql;
		//	while(true){
			/*
				//  �ش� �� ���̺��� �ִ� �˻�
				pstmt = conn.prepareStatement("SHOW TABLES LIKE ? ");
				String month = String.format("%02d", Integer.valueOf(cal.get(Calendar.MONTH) + 1));
				logTable =  "sc_log_" + cal.get(Calendar.YEAR) + "" + month;
				pstmt.setString(1, logTable);
				rs = pstmt.executeQuery();
				rs.last();
				//	�ش� ���̺��� ������
				if( rs.getRow() <= 0 ){
					System.out.println("e : " + logTable);					
					break;
				}
				// �����޷� �̵�
				cal.add(Calendar.MONTH, -1);
			*/	
			sql = "SELECT * FROM sc_log s, user_info u " + 
					" WHERE  u.f_index = s.tr_etc2 AND ";				
			if(type.equalsIgnoreCase("from")){	// ������ȭ��ȣ�� �˻�
				sql += "  s.tr_etc1 like ? ";
			}else if(type.equalsIgnoreCase("message")){		// �޼����� �˻�
				sql += " s.tr_msg like ? ";
			}else{			// �޴� ��ȭ��ȣ�� �˻�
				sql += " tr_phone like ? ";
				search = search.replace("-", "");		// ������ ����					
			}
			if(psCode != 100){
				sql += " AND u.f_pscode = " + psCode;
			}
			//  ����û �����ڰ� �ƴϸ� �ش� ���������Ϻμ��� ���̵���
			if(psCode != 100){
				sql += " AND u.f_pscode = " + psCode;
			}					
			sql +=  " ORDER BY s.TR_SENDDATE DESC LIMIT ?, ? ";
			
			pstmt = conn.prepareStatement(sql);		
			pstmt.setString(1, "%" + search + "%");	
			pstmt.setInt(2, start -1);
			pstmt.setInt(3, end);				
			rs = pstmt.executeQuery();
			while(rs.next())	{
				// ���� ������ ��´�.
			    data = new LGSMSBean();	
			    data.setIndex(rs.getLong("tr_num"));
			    data.setUserId(rs.getString("tr_etc1"));
			    data.setUserIndex(rs.getInt("tr_etc2"));
			    data.setCallback(rs.getString("tr_callback"));			    
			    data.setPhone(rs.getString("tr_phone"));
			    data.setMsg(rs.getString("tr_msg"));
			    data.setRealsenddate(rs.getString("tr_realsenddate"));
			    data.setSenddate(rs.getString("tr_senddate"));					    
			    data.setSendstate(rs.getString("tr_sendstat"));		
			    data.setRsltstat(rs.getString("tr_rsltstat"));
			    data.setMsgtype(rs.getString("tr_msgtype"));   
				list.add(data);
			}			
		//	}
		
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getSendList ���� : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}
	}	

	/**
	 * SMS ��ü �����߼۰��� ���ϱ�
	 * @param search
	 * 		�˻���
	 * @param type
	 * 		�˻�����
	 * @param psCode 
	 * @return
	 */
	public int getSmsSendAllListCount(String search, String type, int psCode) {
		int result = 0;
		try {
			conn = dataSource.getConnection();
			Calendar cal = Calendar.getInstance();
			String logTable = "";
			String sql;
	//		while(true){
			/*
			//  �ش� �� ���̺��� �ִ� �˻�
			pstmt = conn.prepareStatement("SHOW TABLES LIKE ? ");
			String month = String.format("%02d", Integer.valueOf(cal.get(Calendar.MONTH) + 1));
			logTable =  "sc_log_" + cal.get(Calendar.YEAR) + "" + month;
			
			pstmt.setString(1, logTable);
			rs = pstmt.executeQuery();
			rs.last();
			//	�ش� ���̺��� ������
			if( rs.getRow() <= 0 ){
				System.out.println("e : " + logTable);					
				break;
			}
			// �����޷� �̵�
			cal.add(Calendar.MONTH, -1);
			*/
			sql = "SELECT count(s.TR_SENDDATE) FROM sc_log s, user_info u " + 
						" WHERE  u.f_index = s.tr_etc2 AND ";
			if(type.equalsIgnoreCase("from")){			// ������ ��ȣ�� �˻�
				sql += " tr_etc1 like ? ";
			}else if(type.equalsIgnoreCase("message")){		// �޼����� �˻�
				sql += "  tr_msg like ? ";
			}else{			// �޴� ��ȭ��ȣ�� �˻�
				sql += " tr_phone like ? ";
				search = search.replace("-", "");		// ������ ����		
			}
			//  ����û �����ڰ� �ƴϸ� �ش� ���������Ϻμ��� ���̵���
			if(psCode != 100){
				sql += " AND u.f_pscode = " + psCode;
			}	
			pstmt = conn.prepareStatement(sql);			
			pstmt.setString(1, "%" + search + "%");
			rs = pstmt.executeQuery();
			if(rs.next()){
				result +=  rs.getInt(1);
			}				
		//	}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getSendListCount ���� : " + e.getMessage());
		}finally{
			connClose();
		}
		return result;
	}
	
	/**
	 * Ư�� ���� ���� ���� ���� 
	 * @param userIndex
	 * 		�˻��� ���� �ε���
	 * @param search 
	 * 		�˻���
	 * @return
	 */
	public int getUserReserveCount(int userIndex, String search, String type) {
		int result = 0;
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT count(*) FROM sms_send_info " +
					" WHERE  f_user_index = ? AND" +
					" f_reserved = 'y' AND f_reserve_date > now() AND ";
			if(type.equalsIgnoreCase("from")){	// �޼����� �˻�
				sql += "  f_message like ? ";
			}else if(type.equalsIgnoreCase("message")){		// �޴� ��ȭ��ȣ�� �˻�
				sql += " f_callto like ? ";
				search = search.replace("-", "");		// ������ ����		
			}
			
			pstmt = conn.prepareStatement(sql +" ORDER BY f_index DESC ");		
			pstmt.setInt(1, userIndex);	
			pstmt.setString(2, "%" + search + "%");				
			rs = pstmt.executeQuery();
			if(rs.next()){
				result =  rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getUserReserveCount ���� : " + e.getMessage());
		}finally{
			connClose();
		}
		return result;
	}	
	


	/**
	 * ���� ���� ���� ���� ����Ʈ
	 * @param userIndex
	 * @param start
	 * @param end
	 * @param search
	 * @param type
	 * @return
	 */
	public List<SMSBean> getUserReserveList(int userIndex, int start, int end, String search, String type) {
		List<SMSBean> list = new ArrayList<SMSBean>();
		SMSBean data = null;		
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT * FROM sms_send_info " +
					" WHERE  f_user_index = ? AND" +
					" f_reserved = 'y' AND f_reserve_date > now() AND ";
			if(type.equalsIgnoreCase("message")){	// �޼����� �˻�
				sql += "  f_message like ? ";
			}else if(type.equalsIgnoreCase("from")){		// �޴� ��ȭ��ȣ�� �˻�
				sql += " f_callto like ? ";
				search = search.replace("-", "");		// ������ ����		
			}
			sql += " ORDER BY f_index DESC LIMIT ?, ? ";
			pstmt = conn.prepareStatement(sql);		
			pstmt.setInt(1, userIndex);
			pstmt.setString(2, "%" + search + "%");				
			pstmt.setInt(3, start -1);
			pstmt.setInt(4, end);				
			rs = pstmt.executeQuery();
			while(rs.next())	{
				// ���� ������ ��´�.
			    data = new SMSBean();	
			    data.setIndex(rs.getLong("f_index"));
			    data.setId(rs.getString("f_user_id"));
			    data.setUserIndex(rs.getInt("f_user_index"));
			    data.setToPhone(rs.getString("f_callto"));			    
			    data.setFromPhone(rs.getString("f_callfrom"));
			    data.setMessage(rs.getString("f_message"));
			    data.setRequestResult(rs.getInt("f_request_result_code"));
			    data.setResponseResult(rs.getInt("f_response_result_code"));			
			    data.setFlag(rs.getString("f_flag").equalsIgnoreCase("s")?"SMS":"MMS");			    
			    data.setRegDate(rs.getString("f_reg_date"));   
			    data.setSendDate(rs.getString("f_reserve_date"));		    
			    data.setCallback(rs.getString("f_callback"));
			    
				list.add(data);
  			}
			
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getReserveResultList ���� : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}
	}	
	
	/**
	 * ���� ���� �߼� ���� ���� 
	 * @param userIndex
	 * 		�˻��� ���� �ε���
	 * @param search 
	 * 		�˻���
	 * @return
	 */
	public int getUserSentCount(int userIndex, String search, String type) {
		int result = 0;
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT count(*) FROM sms_send_info " +
					" WHERE  f_user_index = ? AND" +
					" f_send_state <> 0 AND ";
			if(type.equalsIgnoreCase("message")){	// �޼����� �˻�
				sql += "  f_message like ? ";
			}else if(type.equalsIgnoreCase("to")){		// �޴� ��ȭ��ȣ�� �˻�
				sql += " f_callto like ? ";
				search = search.replace("-", "");		// ������ ����		
			}
			
			pstmt = conn.prepareStatement(sql);		
			pstmt.setInt(1, userIndex);	
			pstmt.setString(2, "%" + search + "%");				
			rs = pstmt.executeQuery();
			if(rs.next()){
				result =  rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getUserReserveCount ���� : " + e.getMessage());
		}finally{
			connClose();
		}
		return result;
	}	
	
	/**
	 *  Sms ���� �߼� ���� ���� 
	 * @param userIndex
	 * 		�˻��� ���� �ε���
	 * @param search 
	 * 		�˻���
	 * @return
	 */
	public int getUserSmsSentCount(int userIndex, String search, String type) {
		int result = 0;
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT count(s.TR_SENDDATE) FROM sc_log s, user_info u " + 
						" WHERE s.tr_etc3 = 'n' AND  u.f_index = s.tr_etc2" +
						" AND u.f_index =? AND ";
			if(type.equalsIgnoreCase("to")){			//	�޴� ��ȣ �˻�
				sql += " tr_phone like ? ";
				search = search.replace("-", "");		// ������ ����	
			}else if(type.equalsIgnoreCase("message")){		// �޼����� �˻�
				sql += "  tr_msg like ? ";
			}
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userIndex);				
			pstmt.setString(2, "%" + search + "%");
			rs = pstmt.executeQuery();
			if(rs.next()){
				result +=  rs.getInt(1);
			}				
		//	}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getUserSmsSentCount ���� : " + e.getMessage());
		}finally{
			connClose();
		}
		return result;
	}	
	
	/**
	 * Sms ���� �߼� ���� ����(������ ��� ) 
	 * @param userIndex
	 * 		�˻��� ���� �ε���
	 * @param search 
	 * 		�˻���
	 * @return
	 */
	public int getUserSmsSentAdminCount(int userIndex, String search, String type) {
		int result = 0;
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT count(s.TR_SENDDATE) FROM sc_log s, user_info u " + 
						" WHERE  u.f_index = s.tr_etc2" +
						" AND u.f_index =? AND ";
			if(type.equalsIgnoreCase("to")){			//	�޴� ��ȣ �˻�
				sql += " tr_phone like ? ";
				search = search.replace("-", "");		// ������ ����	
			}else if(type.equalsIgnoreCase("message")){		// �޼����� �˻�
				sql += "  tr_msg like ? ";
			}
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userIndex);				
			pstmt.setString(2, "%" + search + "%");
			rs = pstmt.executeQuery();
			if(rs.next()){
				result +=  rs.getInt(1);
			}				
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getUserSmsSentCount ���� : " + e.getMessage());
		}finally{
			connClose();
		}
		return result;
	}		

	
	/**
	 * LMS ��ü ���� ���� ���ϱ�
	 * @param search
	 * 		�˻���
	 * @param type
	 * 		Ÿ��
	 * @param psCode
	 * 		������ �ڵ�
	 * @return
	 * 		���� �� ����
	 */
	public int getUserLmsSentCount(int userIndex, String search, String type) {
		int result = 0;
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT count(s.REQDATE) FROM mms_log s, user_info u " + 
						" WHERE s.etc3 = 'n' AND u.f_index = s.etc2 " +
						" AND u.f_index =? AND file_cnt = 0 AND ";
			if(type.equalsIgnoreCase("message")){		// �޼����� �˻�
				sql += " s.msg like ? ";
			}else{			// �޴� ��ȭ��ȣ�� �˻�
				sql += " s.phone like ? ";
				search = search.replace("-", "");		// ������ ����	
			}
			
			pstmt = conn.prepareStatement(sql);			
			pstmt.setInt(1, userIndex);							
			pstmt.setString(2, "%" + search + "%");
			rs = pstmt.executeQuery();
			if(rs.next()){
				result +=  rs.getInt(1);
			}				
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getUserLmsSentCount ���� : " + e.getMessage());
		}finally{
			connClose();
		}
		return result;
	}
	
	
	/**
	 * LMS ��ü ���� ���� ���ϱ�(�����ڸ��)
	 * @param search
	 * 		�˻���
	 * @param type
	 * 		Ÿ��
	 * @param psCode
	 * 		������ �ڵ�
	 * @return
	 * 		���� �� ����
	 */
	public int getUserLmsSentAdminCount(int userIndex, String search, String type) {
		int result = 0;
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT count(s.REQDATE) FROM mms_log s, user_info u " + 
						" WHERE  u.f_index = s.etc2 " +
						" AND u.f_index =? AND file_cnt = 0 AND ";
			if(type.equalsIgnoreCase("message")){		// �޼����� �˻�
				sql += " s.msg like ? ";
			}else{			// �޴� ��ȭ��ȣ�� �˻�
				sql += " s.phone like ? ";
				search = search.replace("-", "");		// ������ ����	
			}
			
			pstmt = conn.prepareStatement(sql);			
			pstmt.setInt(1, userIndex);							
			pstmt.setString(2, "%" + search + "%");
			rs = pstmt.executeQuery();
			if(rs.next()){
				result +=  rs.getInt(1);
			}				
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getUserLmsSentCount ���� : " + e.getMessage());
		}finally{
			connClose();
		}
		return result;
	}
	
	
	/**
	 * MMS ��ü ���� ���� ���ϱ�
	 * @param search
	 * 		�˻���
	 * @param type
	 * 		Ÿ��
	 * @param psCode
	 * 		������ �ڵ�
	 * @return
	 * 		���� �� ����
	 */
	public int getUserMmsSentCount(int userIndex, String search, String type) {
		int result = 0;
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT count(s.REQDATE) FROM mms_log s, user_info u " + 
						" WHERE  s.etc3 = 'n' AND u.f_index = s.etc2 " +
						" AND u.f_index = ? AND file_cnt > 0 AND ";
			if(type.equalsIgnoreCase("message")){		// �޼����� �˻�
				sql += " s.msg like ? ";
			}else{			// �޴� ��ȭ��ȣ�� �˻�
				sql += " s.phone like ? ";
				search = search.replace("-", "");		// ������ ����	
			}
			
			pstmt = conn.prepareStatement(sql);			
			pstmt.setInt(1, userIndex);							
			pstmt.setString(2, "%" + search + "%");
			rs = pstmt.executeQuery();
			if(rs.next()){
				result +=  rs.getInt(1);
			}				
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getUserMmsSentCount ���� : " + e.getMessage());
		}finally{
			connClose();
		}
		return result;
	}	
	
	/**
	 * MMS ��ü ���� ���� ���ϱ�
	 * @param search
	 * 		�˻���
	 * @param type
	 * 		Ÿ��
	 * @param psCode
	 * 		������ �ڵ�
	 * @return
	 * 		���� �� ����
	 */
	public int getUserMmsSentAdminCount(int userIndex, String search, String type) {
		int result = 0;
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT count(s.REQDATE) FROM mms_log s, user_info u " + 
						" WHERE  u.f_index = s.etc2 " +
						" AND u.f_index = ? AND file_cnt > 0 AND ";
			if(type.equalsIgnoreCase("message")){		// �޼����� �˻�
				sql += " s.msg like ? ";
			}else{			// �޴� ��ȭ��ȣ�� �˻�
				sql += " s.phone like ? ";
				search = search.replace("-", "");		// ������ ����	
			}
			
			pstmt = conn.prepareStatement(sql);			
			pstmt.setInt(1, userIndex);							
			pstmt.setString(2, "%" + search + "%");
			rs = pstmt.executeQuery();
			if(rs.next()){
				result +=  rs.getInt(1);
			}				
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getUserMmsSentCount ���� : " + e.getMessage());
		}finally{
			connClose();
		}
		return result;
	}	
	

	/**
	 * ���� ���� �߼� ���� ����Ʈ
	 * @param userIndex
	 * @param start
	 * @param end
	 * @param search
	 * @param type
	 * @return
	 */
	public List<SMSBean> getUserSentList(int userIndex, int start, int end, String search, String type) {
		List<SMSBean> list = new ArrayList<SMSBean>();
		SMSBean data = null;		
		try {
			
			conn = dataSource.getConnection();
			String sql = "SELECT * FROM sms_send_info " +
					" WHERE  f_user_index = ? AND" +
					"  f_send_state <> 0 AND ";
			if(type.equalsIgnoreCase("message")){	// �޼����� �˻�
				sql += "  f_message like ? ";
			}else if(type.equalsIgnoreCase("to")){		// �޴� ��ȭ��ȣ�� �˻�
				sql += " f_callto like ? ";
			}
			sql += " ORDER BY f_index DESC LIMIT ?, ? ";
			pstmt = conn.prepareStatement(sql);		
			pstmt.setInt(1, userIndex);
			pstmt.setString(2, "%" + search + "%");				
			pstmt.setInt(3, start -1);
			pstmt.setInt(4, end);				
			rs = pstmt.executeQuery();
			while(rs.next())	{
				// ���� ������ ��´�.
			    data = new SMSBean();	
			    data.setIndex(rs.getLong("f_index"));
			    data.setId(rs.getString("f_user_id"));
			    data.setUserIndex(rs.getInt("f_user_index"));
			    data.setToPhone(rs.getString("f_callto"));			    
			    data.setFromPhone(rs.getString("f_callfrom"));
			    data.setMessage(rs.getString("f_message"));
			    data.setRequestResult(rs.getInt("f_request_result_code"));
			    data.setResponseResult(rs.getInt("f_response_result_code"));			
			    data.setFlag(rs.getString("f_flag").equalsIgnoreCase("s")?"SMS":"MMS");			    
			    data.setRegDate(rs.getString("f_reg_date"));   
			    data.setCallback(rs.getString("f_callback"));
			    data.setResultMsg(rs.getString("f_result_msg"));
				list.add(data);
  			}
			
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getReserveResultList ���� : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}
	}
	
	/**
	 * ���� ���� �߼� ���� ����Ʈ
	 * @param userIndex
	 * 		���� �ε���
	 * @param start
	 * 		���� ��ȣ
	 * @param end
	 * 		������ ����ȣ
	 * @param search
	 * 		�˻���
	 * @param type
	 * 		�˻� ����
	 * @return
	 * 	�ش� ���� �߼� ���� ����Ʈ
	 */
	public List<LGSMSBean> getUserSmsSentList(int userIndex, int start, int end, String search, String type) {
		List<LGSMSBean> list = new ArrayList<LGSMSBean>();
		LGSMSBean data = null;		
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT * FROM sc_log s, user_info u " + 
					" WHERE s.tr_etc3 = 'n' AND u.f_index = s.tr_etc2 " +
					" AND u.f_index =? AND ";
			if(type.equalsIgnoreCase("message")){		// �޼����� �˻�
				sql += " s.tr_msg like ? ";
			}else{			// �޴� ��ȭ��ȣ�� �˻�
				sql += " tr_phone like ? ";
				search = search.replace("-", "");		// ������ ����					
			}
			
			sql +=  " ORDER BY s.TR_SENDDATE DESC LIMIT ?, ? ";
			pstmt = conn.prepareStatement(sql);		
			pstmt.setInt(1, userIndex);
			pstmt.setString(2, "%" + search + "%");				
			pstmt.setInt(3, start -1);
			pstmt.setInt(4, end);				
			rs = pstmt.executeQuery();
			while(rs.next())	{
				// ���� ������ ��´�.
			    data = new LGSMSBean();	
			    data.setIndex(rs.getLong("tr_num"));
			    data.setUserId(rs.getString("tr_etc1"));
			    data.setUserIndex(rs.getInt("tr_etc2"));
			    data.setCallback(rs.getString("tr_callback"));			    
			    data.setPhone(rs.getString("tr_phone"));
			    data.setMsg(rs.getString("tr_msg"));
			    data.setRealsenddate(rs.getString("tr_realsenddate"));
			    data.setSenddate(rs.getString("tr_senddate"));					    
			    data.setSendstate(rs.getString("tr_sendstat"));		
			    data.setRsltstat(rs.getString("tr_rsltstat"));
			    data.setMsgtype(rs.getString("tr_msgtype"));   
				list.add(data);
			}			
		
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getUserSmsSentList ���� : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}
	}
	
	
	/**
	 * ���� ���� �߼� ���� ����Ʈ (�����ڸ��)
	 * @param userIndex
	 * 		���� �ε���
	 * @param start
	 * 		���� ��ȣ
	 * @param end
	 * 		������ ����ȣ
	 * @param search
	 * 		�˻���
	 * @param type
	 * 		�˻� ����
	 * @return
	 * 	�ش� ���� �߼� ���� ����Ʈ
	 */
	public List<LGSMSBean> getUserSmsSentAdminList(int userIndex, int start, int end, String search, String type) {
		List<LGSMSBean> list = new ArrayList<LGSMSBean>();
		LGSMSBean data = null;		
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT * FROM sc_log s, user_info u " + 
					" WHERE u.f_index = s.tr_etc2 " +
					" AND u.f_index =? AND ";
			if(type.equalsIgnoreCase("message")){		// �޼����� �˻�
				sql += " s.tr_msg like ? ";
			}else{			// �޴� ��ȭ��ȣ�� �˻�
				sql += " tr_phone like ? ";
				search = search.replace("-", "");		// ������ ����					
			}
			
			sql +=  " ORDER BY s.TR_SENDDATE DESC LIMIT ?, ? ";
			pstmt = conn.prepareStatement(sql);		
			pstmt.setInt(1, userIndex);
			pstmt.setString(2, "%" + search + "%");				
			pstmt.setInt(3, start -1);
			pstmt.setInt(4, end);				
			rs = pstmt.executeQuery();
			while(rs.next())	{
				// ���� ������ ��´�.
			    data = new LGSMSBean();	
			    data.setIndex(rs.getLong("tr_num"));
			    data.setUserId(rs.getString("tr_etc1"));
			    data.setUserIndex(rs.getInt("tr_etc2"));
			    data.setCallback(rs.getString("tr_callback"));			    
			    data.setPhone(rs.getString("tr_phone"));
			    data.setMsg(rs.getString("tr_msg"));
			    data.setRealsenddate(rs.getString("tr_realsenddate"));
			    data.setSenddate(rs.getString("tr_senddate"));					    
			    data.setSendstate(rs.getString("tr_sendstat"));		
			    data.setRsltstat(rs.getString("tr_rsltstat"));
			    data.setMsgtype(rs.getString("tr_msgtype"));   
				list.add(data);
			}			
		
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getUserSmsSentList ���� : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}
	}	
	
	
	/**
	 * LMS ���� �߼� ���� ����Ʈ
	 * @param userIndex
	 * 		���� �ε���
	 * @param start
	 * 		���� ��ȣ
	 * @param end
	 * 		������ ����ȣ
	 * @param search
	 * 		�˻���
	 * @param type
	 * 		�˻� ����
	 * @return
	 * 	�ش� ���� �߼� ���� ����Ʈ
	 */
	public List<LGMMSBean> getUserLmsSentList(int userIndex, int start, int end, String search, String type) {
		List<LGMMSBean> list = new ArrayList<LGMMSBean>();
		LGMMSBean data = null;		
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT * FROM mms_log s, user_info u " + 
					" WHERE  s.etc3 = 'n' AND u.f_index = s.etc2" +
					" AND u.f_index = ? AND file_cnt = 0 AND ";				
			if(type.equalsIgnoreCase("message")){		// �޼����� �˻�
				sql += " s.msg like ? ";
			}else{			// �޴� ��ȭ��ȣ�� �˻�
				sql += " s.phone like ? ";
				search = search.replace("-", "");		// ������ ����	
			}
			sql +=  " ORDER BY s.REQDATE DESC LIMIT ?, ? ";
			
			pstmt = conn.prepareStatement(sql);		
			pstmt.setInt(1, userIndex);			
			pstmt.setString(2, "%" + search + "%");	
			pstmt.setInt(3, start -1);
			pstmt.setInt(4, end);				
			rs = pstmt.executeQuery();
			while(rs.next())	{
				// mms ������ ��´�.
			    data = new LGMMSBean();	
			    data.setIndex(rs.getLong("msgkey"));
			    data.setUserId(rs.getString("etc1"));
			    data.setUserIndex(rs.getInt("etc2"));
			    data.setCallback(rs.getString("callback"));			    
			    data.setPhone(rs.getString("phone"));
			    data.setMsg(rs.getString("msg"));
			    data.setSubject(rs.getString("subject"));
			    data.setTelcoinfo(rs.getString("telcoinfo"));
			    data.setRealsenddate(rs.getString("sentdate"));
			    data.setSenddate(rs.getString("reqdate"));		
			    data.setRsltstat(rs.getString("rslt"));
			    data.setType(rs.getString("type"));   
				list.add(data);
			}			
		
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getUserLmsSentList ���� : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}
	}
	
	/**
	 * LMS ���� �߼� ���� ����Ʈ(�����ڸ��)
	 * @param userIndex
	 * 		���� �ε���
	 * @param start
	 * 		���� ��ȣ
	 * @param end
	 * 		������ ����ȣ
	 * @param search
	 * 		�˻���
	 * @param type
	 * 		�˻� ����
	 * @return
	 * 	�ش� ���� �߼� ���� ����Ʈ
	 */
	public List<LGMMSBean> getUserLmsSentAdminList(int userIndex, int start, int end, String search, String type) {
		List<LGMMSBean> list = new ArrayList<LGMMSBean>();
		LGMMSBean data = null;		
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT * FROM mms_log s, user_info u " + 
					" WHERE  u.f_index = s.etc2" +
					" AND u.f_index = ? AND file_cnt = 0 AND ";				
			if(type.equalsIgnoreCase("message")){		// �޼����� �˻�
				sql += " s.msg like ? ";
			}else{			// �޴� ��ȭ��ȣ�� �˻�
				sql += " s.phone like ? ";
				search = search.replace("-", "");		// ������ ����	
			}
			sql +=  " ORDER BY s.REQDATE DESC LIMIT ?, ? ";
			
			pstmt = conn.prepareStatement(sql);		
			pstmt.setInt(1, userIndex);			
			pstmt.setString(2, "%" + search + "%");	
			pstmt.setInt(3, start -1);
			pstmt.setInt(4, end);				
			rs = pstmt.executeQuery();
			while(rs.next())	{
				// mms ������ ��´�.
			    data = new LGMMSBean();	
			    data.setIndex(rs.getLong("msgkey"));
			    data.setUserId(rs.getString("etc1"));
			    data.setUserIndex(rs.getInt("etc2"));
			    data.setCallback(rs.getString("callback"));			    
			    data.setPhone(rs.getString("phone"));
			    data.setMsg(rs.getString("msg"));
			    data.setSubject(rs.getString("subject"));
			    data.setTelcoinfo(rs.getString("telcoinfo"));
			    data.setRealsenddate(rs.getString("sentdate"));
			    data.setSenddate(rs.getString("reqdate"));		
			    data.setRsltstat(rs.getString("rslt"));
			    data.setType(rs.getString("type"));   
				list.add(data);
			}			
		
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getUserLmsSentList ���� : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}
	}	
	
	
	/**
	 * MMS ���� �߼� ���� ����Ʈ
	 * @param userIndex
	 * 		���� �ε���
	 * @param start
	 * 		���� ��ȣ
	 * @param end
	 * 		������ ����ȣ
	 * @param search
	 * 		�˻���
	 * @param type
	 * 		�˻� ����
	 * @return
	 * 	�ش� ���� �߼� ���� ����Ʈ
	 */
	public List<LGMMSBean> getUserMmsSentList(int userIndex, int start, int end, String search, String type) {
		List<LGMMSBean> list = new ArrayList<LGMMSBean>();
		LGMMSBean data = null;		
		try {
			conn = dataSource.getConnection();
			// ���� ���� �߼� ������ 1�� �̻��϶� MMS�� �Ǻ�
			String sql = "SELECT * FROM mms_log s, user_info u " + 
					" WHERE s.etc3 = 'n' AND  u.f_index = s.etc2" +
					" AND u.f_index = ? AND file_cnt > 0 AND ";				
			if(type.equalsIgnoreCase("message")){		// �޼����� �˻�
				sql += " s.msg like ? ";
			}else{			// �޴� ��ȭ��ȣ�� �˻�
				sql += " s.phone like ? ";
				search = search.replace("-", "");		// ������ ����	
			}
			sql +=  " ORDER BY s.REQDATE DESC LIMIT ?, ? ";
			
			pstmt = conn.prepareStatement(sql);		
			pstmt.setInt(1, userIndex);			
			pstmt.setString(2, "%" + search + "%");	
			pstmt.setInt(3, start -1);
			pstmt.setInt(4, end);				
			rs = pstmt.executeQuery();
			while(rs.next())	{
				// mms ������ ��´�.
			    data = new LGMMSBean();	
			    data.setIndex(rs.getLong("msgkey"));
			    data.setUserId(rs.getString("etc1"));
			    data.setUserIndex(rs.getInt("etc2"));
			    data.setCallback(rs.getString("callback"));			    
			    data.setPhone(rs.getString("phone"));
			    data.setMsg(rs.getString("msg"));
			    data.setSubject(rs.getString("subject"));
			    data.setTelcoinfo(rs.getString("telcoinfo"));
			    data.setRealsenddate(rs.getString("sentdate"));
			    data.setSenddate(rs.getString("reqdate"));		
			    data.setRsltstat(rs.getString("rslt"));
			    data.setType(rs.getString("type"));   
				list.add(data);
			}			
		
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getUserMmsSentList ���� : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}
	}	
	
	
	/**
	 * MMS ���� �߼� ���� ����Ʈ(�����ڸ��)
	 * @param userIndex
	 * 		���� �ε���
	 * @param start
	 * 		���� ��ȣ
	 * @param end
	 * 		������ ����ȣ
	 * @param search
	 * 		�˻���
	 * @param type
	 * 		�˻� ����
	 * @return
	 * 	�ش� ���� �߼� ���� ����Ʈ
	 */
	public List<LGMMSBean> getUserMmsSentAdminList(int userIndex, int start, int end, String search, String type) {
		List<LGMMSBean> list = new ArrayList<LGMMSBean>();
		LGMMSBean data = null;		
		try {
			conn = dataSource.getConnection();
			// ���� ���� �߼� ������ 1�� �̻��϶� MMS�� �Ǻ�
			String sql = "SELECT * FROM mms_log s, user_info u " + 
					" WHERE u.f_index = s.etc2" +
					" AND u.f_index = ? AND file_cnt > 0 AND ";				
			if(type.equalsIgnoreCase("message")){		// �޼����� �˻�
				sql += " s.msg like ? ";
			}else{			// �޴� ��ȭ��ȣ�� �˻�
				sql += " s.phone like ? ";
				search = search.replace("-", "");		// ������ ����	
			}
			sql +=  " ORDER BY s.REQDATE DESC LIMIT ?, ? ";
			
			pstmt = conn.prepareStatement(sql);		
			pstmt.setInt(1, userIndex);			
			pstmt.setString(2, "%" + search + "%");	
			pstmt.setInt(3, start -1);
			pstmt.setInt(4, end);				
			rs = pstmt.executeQuery();
			while(rs.next())	{
				// mms ������ ��´�.
			    data = new LGMMSBean();	
			    data.setIndex(rs.getLong("msgkey"));
			    data.setUserId(rs.getString("etc1"));
			    data.setUserIndex(rs.getInt("etc2"));
			    data.setCallback(rs.getString("callback"));			    
			    data.setPhone(rs.getString("phone"));
			    data.setMsg(rs.getString("msg"));
			    data.setSubject(rs.getString("subject"));
			    data.setTelcoinfo(rs.getString("telcoinfo"));
			    data.setRealsenddate(rs.getString("sentdate"));
			    data.setSenddate(rs.getString("reqdate"));		
			    data.setRsltstat(rs.getString("rslt"));
			    data.setType(rs.getString("type"));   
				list.add(data);
			}			
		
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getUserMmsSentList ���� : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}
	}	
	

	
	public List<Message> getMessagesList(int userIndex, int groupIndex, int start, int end) {
		List<Message> list = new ArrayList<Message>();
		Message data = null;		
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM message WHERE" +
					" f_user_index =?  AND f_group_index = ? ORDER BY f_index DESC LIMIT ?, ?");
			pstmt.setInt(1, userIndex);
			pstmt.setInt(2, groupIndex);
			pstmt.setInt(3, start-1);
			pstmt.setInt(4, end);
			rs = pstmt.executeQuery();
			
			while(rs.next())	{
				// �ε���, ��Ͼ��̵�, ����, ����, �׷��ε���, �׷��
			    data = new Message();	
			    data.setIndex(rs.getInt("f_index"));
			    data.setId(rs.getString("f_id"));	  		
			    data.setTitle(rs.getString("f_message_title"));			    
			    data.setMessage(rs.getString("f_message_text"));
			    data.setGroupIndex(rs.getInt("f_group_index"));
			    data.setGroup(rs.getString("f_message_group"));
	
				list.add(data);
			}		
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getMyMessages ���� : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}
	}

	
	public int getMessageListCount(int groupIndex) {
		int result = 0;
		try {
			conn = dataSource.getConnection();					
			String sql = "SELECT count(*) FROM message WHERE  " +
					" f_group_index = ? ";
			pstmt = conn.prepareStatement(sql +" ORDER BY f_index DESC ");
			pstmt.setInt(1, groupIndex);				
			rs = pstmt.executeQuery();
			if(rs.next()){
				result =  rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getReserveListCount ���� : " + e.getMessage());
		}finally{
			connClose();
		}
		return result;
	}

	/**
	 * LMS ��ü ���� ���� ���ϱ�
	 * @param search
	 * 		�˻���
	 * @param type
	 * 		Ÿ��
	 * @param psCode
	 * 		������ �ڵ�
	 * @return
	 * 		���� �� ����
	 */
	public int getLmsSendAllListCount(String search, String type, int psCode) {
		int result = 0;
		try {
			conn = dataSource.getConnection();
			Calendar cal = Calendar.getInstance();
			String logTable = "";
			String sql;
			
			/*
			// ������Ʈ ���� ������ �ش� ���̺� ���� �˻�
			//  �ش� �� ���̺��� �ִ� �˻�
			pstmt = conn.prepareStatement("SHOW TABLES LIKE ? ");
			String month = String.format("%02d", Integer.valueOf(cal.get(Calendar.MONTH) + 1));
			logTable =  "mms_log_" + cal.get(Calendar.YEAR) + "" + month;
			pstmt.setString(1, logTable);
			rs = pstmt.executeQuery();
			rs.last();
			//	�ش� ���̺��� ������
			if( rs.getRow() <= 0 ){
				System.out.println("e : " + logTable);					
				break;
			}
			// �����޷� �̵�
			cal.add(Calendar.MONTH, -1);
			*/
			sql = "SELECT count(s.REQDATE) FROM mms_log s, user_info u " + 
						" WHERE  u.f_index = s.etc2 AND file_cnt = 0 AND ";
			if(type.equalsIgnoreCase("from")){	// ������ȭ��ȣ�� �˻�
				sql += "  s.etc1 like ? ";
			}else if(type.equalsIgnoreCase("message")){		// �޼����� �˻�
				sql += " s.msg like ? ";
			}else{			// �޴� ��ȭ��ȣ�� �˻�
				sql += " s.phone like ? ";
				search = search.replace("-", "");		// ������ ����	
			}
			
			//  ����û �����ڰ� �ƴϸ� �ش� ���������Ϻμ��� ���̵���
			if(psCode != 100){
				sql += " AND u.f_pscode = " + psCode;
			}
			
			pstmt = conn.prepareStatement(sql);			
			pstmt.setString(1, "%" + search + "%");
			rs = pstmt.executeQuery();
			if(rs.next()){
				result +=  rs.getInt(1);
			}				
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getLmsSendAllListCount ���� : " + e.getMessage());
		}finally{
			connClose();
		}
		return result;
	}
	
	/**
	 * MMS ��ü ���� ���� ���ϱ�
	 * @param search
	 * 		�˻���
	 * @param type
	 * 		Ÿ��
	 * @param psCode
	 * 		������ �ڵ�
	 * @return
	 * 		���� �� ����
	 */
	public int getMmsSendAllListCount(String search, String type, int psCode) {
		int result = 0;
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT count(s.REQDATE) FROM mms_log s, user_info u " + 
						" WHERE  u.f_index = s.etc2 AND file_cnt > 0 AND ";
			if(type.equalsIgnoreCase("from")){			// �޴� ���̵� �˻�
				sql += " etc1 like ? ";
			}else if(type.equalsIgnoreCase("message")){		// �޼����� �˻�
				sql += "  msg like ? ";
			}else{			// �޴� ��ȭ��ȣ�� �˻�
				sql += " phone like ? ";
				search = search.replace("-", "");		// ������ ����	
			}
			//  ����û �����ڰ� �ƴϸ� �ش� ���������Ϻμ��� ���̵���
			if(psCode != 100){
				sql += " AND u.f_pscode = " + psCode;
			}	
			pstmt = conn.prepareStatement(sql);			
			pstmt.setString(1, "%" + search + "%");
			rs = pstmt.executeQuery();
			if(rs.next()){
				result +=  rs.getInt(1);
			}				
			//}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getMmsSendAllListCount ���� : " + e.getMessage());
		}finally{
			connClose();
		}
		return result;
	}

	
	
	/**
	 * LMS	��ü �߼� ���� ���ϱ�
	 * @param start
	 * 		���� ��ȣ
	 * @param end
	 * 		������ ����ȣ
	 * @param search
	 * 		�˻���
	 * @param type
	 * 		�˻�����
	 * @param psCode
	 * 		������ �ڵ�
	 * @return
	 * 	LMS List
	 */
	public List<LGMMSBean> getLmsSendAllList(int start, int end,
			String search, String type, int psCode) {
		List<LGMMSBean> list = new ArrayList<LGMMSBean>();
		LGMMSBean data = null;		
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT * FROM mms_log s, user_info u " + 
						" WHERE  u.f_index = s.etc2 AND file_cnt = 0 AND ";				
				if(type.equalsIgnoreCase("from")){	// ������ȭ��ȣ�� �˻�
					sql += "  s.etc1 like ? ";
				}else if(type.equalsIgnoreCase("message")){		// �޼����� �˻�
					sql += " s.msg like ? ";
				}else{			// �޴� ��ȭ��ȣ�� �˻�
					sql += " s.phone like ? ";
					search = search.replace("-", "");		// ������ ����	
				}
				
				//  ����û �����ڰ� �ƴϸ� �ش� ���������Ϻμ��� ���̵���
				if(psCode != 100){
					sql += " AND u.f_pscode = " + psCode;
				}					
				sql +=  " ORDER BY s.REQDATE DESC LIMIT ?, ? ";
				
				pstmt = conn.prepareStatement(sql);		
				pstmt.setString(1, "%" + search + "%");	
				pstmt.setInt(2, start -1);
				pstmt.setInt(3, end);				
				rs = pstmt.executeQuery();
				while(rs.next())	{
					// mms ������ ��´�.
				    data = new LGMMSBean();	
				    data.setIndex(rs.getLong("msgkey"));
				    data.setUserId(rs.getString("etc1"));
				    data.setUserIndex(rs.getInt("etc2"));
				    data.setCallback(rs.getString("callback"));			    
				    data.setPhone(rs.getString("phone"));
				    data.setMsg(rs.getString("msg"));
				    data.setSubject(rs.getString("subject"));
				    data.setTelcoinfo(rs.getString("telcoinfo"));
				    data.setRealsenddate(rs.getString("sentdate"));
				    data.setSenddate(rs.getString("reqdate"));		
				    data.setRsltstat(rs.getString("rslt"));
				    data.setType(rs.getString("type"));   
					list.add(data);
				}			
		//	}
		
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getLmsSendAllList ���� : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}
	}

	/**
	 * MMS	��ü �߼� ���� ���ϱ�
	 * @param start
	 * 		���� ��ȣ
	 * @param end
	 * 		������ ����ȣ
	 * @param search
	 * 		�˻���
	 * @param type
	 * 		�˻�����
	 * @param psCode
	 * 		������ �ڵ�
	 * @return
	 * 	MMS List
	 */
	public List<LGMMSBean> getMmsSendAllList(int start, int end,
			String search, String type, int psCode) {
		List<LGMMSBean> list = new ArrayList<LGMMSBean>();
		LGMMSBean data = null;		
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT * FROM mms_log s, user_info u " + 
					" WHERE  u.f_index = s.etc2 AND file_cnt > 0 AND ";	
			if(type.equalsIgnoreCase("from")){	// ������ȭ��ȣ�� �˻�
				sql += "  s.etc1 like ? ";
			}else if(type.equalsIgnoreCase("message")){		// �޼����� �˻�
				sql += " s.msg like ? ";
			}else{			// �޴� ��ȭ��ȣ�� �˻�
				sql += " s.phone like ? ";
				search = search.replace("-", "");		// ������ ����					
			}
			
			//  ����û �����ڰ� �ƴϸ� �ش� ���������Ϻμ��� ���̵���
			if(psCode != 100){
				sql += " AND u.f_pscode = " + psCode;
			}					
			sql +=  " ORDER BY s.REQDATE DESC LIMIT ?, ? ";
			
			pstmt = conn.prepareStatement(sql);		
			pstmt.setString(1, "%" + search + "%");	
			pstmt.setInt(2, start -1);
			pstmt.setInt(3, end);				
			rs = pstmt.executeQuery();
			while(rs.next())	{
				// mms ������ ��´�.
			    data = new LGMMSBean();	
			    data.setIndex(rs.getLong("msgkey"));
			    data.setUserId(rs.getString("etc1"));
			    data.setUserIndex(rs.getInt("etc2"));
			    data.setCallback(rs.getString("callback"));			    
			    data.setPhone(rs.getString("phone"));
			    data.setMsg(rs.getString("msg"));
			    data.setSubject(rs.getString("subject"));
			    data.setTelcoinfo(rs.getString("telcoinfo"));
			    data.setRealsenddate(rs.getString("sentdate"));
			    data.setSenddate(rs.getString("reqdate"));		
			    data.setRsltstat(rs.getString("rslt"));
			    data.setType(rs.getString("type"));   
				list.add(data);
			}			
			//}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getMmsSendAllList ���� : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}
	}

	/**
	 * SMS ���� Ư�� �����߼۰��� ���ϱ�
	 * @param search
	 * 		�˻���
	 * @param type
	 * 		�˻�����
	 * @param psCode 
	 * @return
	 */
	public int getUserReserveSmsSendListCount(int userIndex, String search, String type){
		int result = 0;
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT count(s.TR_SENDDATE) FROM sc_tran s, user_info u " + 
						" WHERE  u.f_index = s.tr_etc2 AND s.tr_senddate > now() " +
						" AND u.f_index =? AND ";
			if(type.equalsIgnoreCase("to")){			// �޴� ��ȣ�� �˻�
				sql += " tr_phone like ? ";
				search = search.replace("-", "");		// ������ ����
			}else if(type.equalsIgnoreCase("message")){		// �޼����� �˻�
				sql += "  tr_msg like ? ";
			}
			
			pstmt = conn.prepareStatement(sql);			
			pstmt.setInt(1, userIndex);			
			pstmt.setString(2, "%" + search + "%");
			rs = pstmt.executeQuery();
			if(rs.next()){
				result =  rs.getInt(1);
			}				
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getUserReserveSmsSendListCount ���� : " + e.getMessage());
		}finally{
			connClose();
		}
		return result;
	}
	
	/**
	 * LMS ���� ��ü ���� ���� ���ϱ�
	 * @param search
	 * 		�˻���
	 * @param type
	 * 		Ÿ��
	 * @param psCode
	 * 		������ �ڵ�
	 * @return
	 * 		���� �� ����
	 */
	public int getUserReserveLmsSendListCount(int userIndex, String search, String type){
		int result = 0;
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT count(s.REQDATE) FROM mms_msg s, user_info u " + 
						" WHERE  u.f_index = s.etc2 AND u.f_index = ?  " +
						" AND file_cnt = 0 AND s.reqdate > now() AND ";
			if(type.equalsIgnoreCase("to")){			// �޴� ��ȣ�� �˻�
				sql += " s.phone like ? ";
				search = search.replace("-", "");		// ������ ����				
			}else if(type.equalsIgnoreCase("message")){		// �޼����� �˻�
				sql += "  s.msg like ? ";
			}
			
			pstmt = conn.prepareStatement(sql);	
			pstmt.setInt(1, userIndex);			
			pstmt.setString(2, "%" + search + "%");
			rs = pstmt.executeQuery();
			if(rs.next()){
				result +=  rs.getInt(1);
			}				
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getUserReserveLmsSendListCount ���� : " + e.getMessage());
		}finally{
			connClose();
		}
		return result;
	}
	
	/**
	 * MMS ���� ��ü ���� ���� ���ϱ�
	 * @param search
	 * 		�˻���
	 * @param type
	 * 		Ÿ��
	 * @param psCode
	 * 		������ �ڵ�
	 * @return
	 * 		���� �� ����
	 */
	public int getUserReserveMmsSendListCount(int userIndex, String search, String type){
		int result = 0;
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT count(s.REQDATE) FROM mms_msg s, user_info u " + 
					" WHERE  u.f_index = s.etc2 AND u.f_index = ?  " +
					" AND file_cnt > 0 AND s.reqdate > now() AND ";
			if(type.equalsIgnoreCase("to")){			// �޴� ��ȣ�� �˻�
				sql += " s.phone like ? ";
				search = search.replace("-", "");		// ������ ����				
			}else if(type.equalsIgnoreCase("message")){		// �޼����� �˻�
				sql += "  s.msg like ? ";
			}
			
			pstmt = conn.prepareStatement(sql);			
			pstmt.setInt(1, userIndex);
			pstmt.setString(2, "%" + search + "%");			
			rs = pstmt.executeQuery();
			if(rs.next()){
				result +=  rs.getInt(1);
			}				
			//}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getReserveMmsSendAllListCount ���� : " + e.getMessage());
		}finally{
			connClose();
		}
		return result;
	}
	
	/**
	 *	SMS Ư�� ���� ���� ���� ��� ���� ����Ʈ
	 * @param start
	 * @param end
	 * @param search
	 * 		�˻���
	 * @param type
	 * 		�˻�����
	 * @return
	 */
	public List<LGSMSBean> getUserReserveSmsSendList(
			int userIndex, int start, int end, String search, String type){
		
		List<LGSMSBean> list = new ArrayList<LGSMSBean>();
		LGSMSBean data = null;		
		
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT * FROM sc_tran s, user_info u " + 
					" WHERE  u.f_index = s.tr_etc2 AND" +
					" s.tr_senddate > now() AND u.f_index = ? AND ";				
			if(type.equalsIgnoreCase("to")){			// �޴� ��ȣ�� �˻�
				sql += " tr_phone like ? ";
				search = search.replace("-", "");		// ������ ����				
			}else if(type.equalsIgnoreCase("message")){		// �޼����� �˻�
				sql += "  tr_msg like ? ";
			}
			
			sql +=  " ORDER BY s.TR_SENDDATE DESC LIMIT ?, ? ";
			
			pstmt = conn.prepareStatement(sql);		
			pstmt.setInt(1, userIndex);				
			pstmt.setString(2, "%" + search + "%");	
			pstmt.setInt(3, start -1);
			pstmt.setInt(4, end);				
			rs = pstmt.executeQuery();
			while(rs.next())	{
				// ���� ������ ��´�.
			    data = new LGSMSBean();	
			    data.setIndex(rs.getLong("tr_num"));
			    data.setUserId(rs.getString("tr_etc1"));
			    data.setUserIndex(rs.getInt("tr_etc2"));
			    data.setCallback(rs.getString("tr_callback"));		
			    data.setSenddate(rs.getString("tr_senddate"));					    
			    data.setPhone(rs.getString("tr_phone"));
			    data.setMsg(rs.getString("tr_msg"));
			    data.setRealsenddate(rs.getString("tr_realsenddate"));
			    data.setSendstate(rs.getString("tr_sendstat"));		
			    data.setRsltstat(rs.getString("tr_rsltstat"));
			    data.setMsgtype(rs.getString("tr_msgtype"));   
				list.add(data);
			}			
		//	}
		
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getUserReserveSmsSendList ���� : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}
	}		
	
	/**
	 * LMS Ư������ ���� ��ü �߼� ����Ʈ
	 * @param start
	 * 		���� ��ȣ
	 * @param end
	 * 		������ ����ȣ
	 * @param search
	 * 		�˻���
	 * @param type
	 * 		�˻�����
	 * @param psCode
	 * 		������ �ڵ�
	 * @return
	 * 	LMS List
	 */
	public List<LGMMSBean> getUserReserveLmsSendList(int userIndex, int start, int end, String search, String type){	
		List<LGMMSBean> list = new ArrayList<LGMMSBean>();
		LGMMSBean data = null;		
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT * FROM mms_msg s, user_info u " + 
						" WHERE  u.f_index = s.etc2 AND" +
						" file_cnt = 0 AND s.reqdate > now() " +
						" AND u.f_index = ? AND ";				
			if(type.equalsIgnoreCase("to")){			// �޴� ��ȣ�� �˻�
				sql += " s.phone like ? ";
				search = search.replace("-", "");		// ������ ����						
			}else if(type.equalsIgnoreCase("message")){		// �޼����� �˻�
				sql += "  s.msg like ? ";
			}
			sql +=  " ORDER BY s.REQDATE DESC LIMIT ?, ? ";
			
			pstmt = conn.prepareStatement(sql);		
			pstmt.setInt(1, userIndex);					
			pstmt.setString(2, "%" + search + "%");	
			pstmt.setInt(3, start -1);
			pstmt.setInt(4, end);				
			rs = pstmt.executeQuery();
			while(rs.next())	{
				// mms ������ ��´�.
			    data = new LGMMSBean();	
			    data.setIndex(rs.getLong("msgkey"));
			    data.setUserId(rs.getString("etc1"));
			    data.setUserIndex(rs.getInt("etc2"));
			    data.setCallback(rs.getString("callback"));			    
			    data.setPhone(rs.getString("phone"));
			    data.setMsg(rs.getString("msg"));
			    data.setSubject(rs.getString("subject"));
			    data.setTelcoinfo(rs.getString("telcoinfo"));
			    data.setRealsenddate(rs.getString("sentdate"));
			    data.setSenddate(rs.getString("reqdate"));		
			    data.setRsltstat(rs.getString("rslt"));
			    data.setType(rs.getString("type"));   
				list.add(data);
			}			
		
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getUserReserveLmsSendList ���� : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}
	}

	/**
	 * MMS	���� ��ü �߼� ����Ʈ
	 * @param start
	 * 		���� ��ȣ
	 * @param end
	 * 		������ ����ȣ
	 * @param search
	 * 		�˻���
	 * @param type
	 * 		�˻�����
	 * @return
	 * 	MMS List
	 */
	public List<LGMMSBean> getUserReserveMmsSendList(int userIndex, int start, int end, String search, String type){	
		List<LGMMSBean> list = new ArrayList<LGMMSBean>();
		LGMMSBean data = null;		
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT * FROM mms_msg s, user_info u " + 
						" WHERE  u.f_index = s.etc2 AND" +
						" file_cnt > 0 AND s.reqdate > now() " +
						" AND u.f_index = ? AND ";				
			if(type.equalsIgnoreCase("to")){			// �޴� ��ȣ�� �˻�
				sql += " s.phone like ? ";
				search = search.replace("-", "");		// ������ ����						
			}else if(type.equalsIgnoreCase("message")){		// �޼����� �˻�
				sql += " s.msg like ? ";
			}
			sql +=  " ORDER BY s.REQDATE DESC LIMIT ?, ? ";
			
			pstmt = conn.prepareStatement(sql);		
			pstmt.setInt(1, userIndex);					
			pstmt.setString(2, "%" + search + "%");	
			pstmt.setInt(3, start -1);
			pstmt.setInt(4, end);				
			rs = pstmt.executeQuery();
			while(rs.next())	{
				// mms ������ ��´�.
			    data = new LGMMSBean();	
			    data.setIndex(rs.getLong("msgkey"));
			    data.setUserId(rs.getString("etc1"));
			    data.setUserIndex(rs.getInt("etc2"));
			    data.setCallback(rs.getString("callback"));			    
			    data.setPhone(rs.getString("phone"));
			    data.setMsg(rs.getString("msg"));
			    data.setSubject(rs.getString("subject"));
			    data.setTelcoinfo(rs.getString("telcoinfo"));
			    data.setRealsenddate(rs.getString("sentdate"));
			    data.setSenddate(rs.getString("reqdate"));		
			    data.setRsltstat(rs.getString("rslt"));
			    data.setType(rs.getString("type"));   
				list.add(data);
			}			
		
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getUserReserveMmsSendList ���� : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}
	}	

	/**
	 * SMS ���� ��ü �����߼۰��� ���ϱ�
	 * @param search
	 * 		�˻���
	 * @param type
	 * 		�˻�����
	 * @param psCode 
	 * @return
	 */
	public int getReserveSmsSendAllListCount(String search, String type, int psCode) {
		int result = 0;
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT count(s.TR_SENDDATE) FROM sc_tran s, user_info u " + 
						" WHERE  u.f_index = s.tr_etc2 AND s.tr_senddate > now()  AND ";
			if(type.equalsIgnoreCase("to")){			// �޴� ��ȣ�� �˻�
				sql += " tr_phone like ? ";
				search = search.replace("-", "");		// ������ ����						
			}else if(type.equalsIgnoreCase("message")){		// �޼����� �˻�
				sql += "  tr_msg like ? ";
			}else{			// �޴� ��ȭ��ȣ�� �˻�
				sql += " tr_phone like ? ";
				search = search.replace("-", "");		// ������ ����	
			}
			//  ����û �����ڰ� �ƴϸ� �ش� ���������Ϻμ��� ���̵���
			if(psCode != 100){
				sql += " AND u.f_pscode = " + psCode;
			}	
			
			pstmt = conn.prepareStatement(sql);			
			pstmt.setString(1, "%" + search + "%");
			rs = pstmt.executeQuery();
			if(rs.next()){
				result +=  rs.getInt(1);
			}				
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getReserveSmsSendAllListCount ���� : " + e.getMessage());
		}finally{
			connClose();
		}
		return result;
	}
	
	/**
	 * LMS ���� ��ü ���� ���� ���ϱ�
	 * @param search
	 * 		�˻���
	 * @param type
	 * 		Ÿ��
	 * @param psCode
	 * 		������ �ڵ�
	 * @return
	 * 		���� �� ����
	 */
	public int getReserveLmsSendAllListCount(String search, String type, int psCode) {
		int result = 0;
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT count(s.REQDATE) FROM mms_msg s, user_info u " + 
						" WHERE  u.f_index = s.etc2 AND" +
						" file_cnt = 0 AND s.reqdate > now() AND ";
			if(type.equalsIgnoreCase("from")){			// �������̵�� �˻�
				sql += "  s.etc1 like ? ";
			}else if(type.equalsIgnoreCase("message")){		// �޼����� �˻�
				sql += " s.msg like ? ";
			}else{												// �޴� ��ȭ��ȣ�� �˻�
				sql += " phone like ? ";
				search = search.replace("-", "");		// ������ ����						
			}
			
			//  ����û �����ڰ� �ƴϸ� �ش� ���������Ϻμ��� ���̵���
			if(psCode != 100){
				sql += " AND u.f_pscode = " + psCode;
			}
			
			pstmt = conn.prepareStatement(sql);			
			pstmt.setString(1, "%" + search + "%");
			rs = pstmt.executeQuery();
			if(rs.next()){
				result +=  rs.getInt(1);
			}				
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getReserveLmsSendAllListCount ���� : " + e.getMessage());
		}finally{
			connClose();
		}
		return result;
	}
	
	/**
	 * MMS ���� ��ü ���� ���� ���ϱ�
	 * @param search
	 * 		�˻���
	 * @param type
	 * 		Ÿ��
	 * @param psCode
	 * 		������ �ڵ�
	 * @return
	 * 		���� �� ����
	 */
	public int getReserveMmsSendAllListCount(String search, String type, int psCode) {
		int result = 0;
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT count(s.REQDATE) FROM mms_msg s, user_info u " + 
						" WHERE  u.f_index = s.etc2 AND " +
						" file_cnt > 0 AND s.reqdate > now() AND ";
			if(type.equalsIgnoreCase("from")){			// �޴� ���̵� �˻�
				sql += " etc1 like ? ";
			}else if(type.equalsIgnoreCase("message")){		// �޼����� �˻�
				sql += "  msg like ? ";
			}else{			// �޴� ��ȭ��ȣ�� �˻�
				sql += " s.phone like ? ";
				search = search.replace("-", "");		// ������ ����						
			}
			//  ����û �����ڰ� �ƴϸ� �ش� ���������Ϻμ��� ���̵���
			if(psCode != 100){
				sql += " AND u.f_pscode = " + psCode;
			}	
			pstmt = conn.prepareStatement(sql);			
			pstmt.setString(1, "%" + search + "%");
			rs = pstmt.executeQuery();
			if(rs.next()){
				result +=  rs.getInt(1);
			}				
			//}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getReserveMmsSendAllListCount ���� : " + e.getMessage());
		}finally{
			connClose();
		}
		return result;
	}
	
	/**
	 *	SMS ���� ���� ��� ���� ����Ʈ
	 * @param start
	 * @param end
	 * @param search
	 * 		�˻���
	 * @param type
	 * 		�˻�����
	 * @param psCode 
	 * @return
	 */
	public List<LGSMSBean> getReserveSmsSendAllList(int start, int end, String search, String type, int psCode) {
		
		List<LGSMSBean> list = new ArrayList<LGSMSBean>();
		LGSMSBean data = null;		
		
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT * FROM sc_tran s, user_info u " + 
					" WHERE  u.f_index = s.tr_etc2 AND" +
					" s.tr_senddate > now() AND ";				
			if(type.equalsIgnoreCase("from")){	// ������ȭ��ȣ�� �˻�
				sql += "  s.tr_callback like ? ";
				search = search.replace("-", "");		// ������ ����						
			}else if(type.equalsIgnoreCase("message")){		// �޼����� �˻�
				sql += " s.tr_msg like ? ";
			}else{			// �޴� ��ȭ��ȣ�� �˻�
				sql += " s.tr_phone like ? ";
				search = search.replace("-", "");		// ������ ����						
			}
			if(psCode != 100){
				sql += " AND u.f_pscode = " + psCode;
			}
			//  ����û �����ڰ� �ƴϸ� �ش� ���������Ϻμ��� ���̵���
			if(psCode != 100){
				sql += " AND u.f_pscode = " + psCode;
			}					
			sql +=  " ORDER BY s.TR_SENDDATE DESC LIMIT ?, ? ";
			
			pstmt = conn.prepareStatement(sql);		
			pstmt.setString(1, "%" + search + "%");	
			pstmt.setInt(2, start -1);
			pstmt.setInt(3, end);				
			rs = pstmt.executeQuery();
			while(rs.next())	{
				// ���� ������ ��´�.
			    data = new LGSMSBean();	
			    data.setIndex(rs.getLong("tr_num"));
			    data.setUserId(rs.getString("tr_etc1"));
			    data.setUserIndex(rs.getInt("tr_etc2"));
			    data.setCallback(rs.getString("tr_callback"));		
			    data.setPhone(rs.getString("tr_phone"));
			    data.setMsg(rs.getString("tr_msg"));
			    data.setRealsenddate(rs.getString("tr_realsenddate"));
			    data.setSenddate(rs.getString("tr_senddate"));		
			    data.setRsltstat(rs.getString("tr_rsltstat"));
			    data.setMsgtype(rs.getString("tr_msgtype"));   
				list.add(data);
			}			
		//	}
		
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getSendList ���� : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}
	}		
	
	/**
	 * LMS����	��ü �߼� ����Ʈ
	 * @param start
	 * 		���� ��ȣ
	 * @param end
	 * 		������ ����ȣ
	 * @param search
	 * 		�˻���
	 * @param type
	 * 		�˻�����
	 * @param psCode
	 * 		������ �ڵ�
	 * @return
	 * 	LMS List
	 */
	public List<LGMMSBean> getReserveLmsSendAllList(int start, int end, String search, String type, int psCode) {
		List<LGMMSBean> list = new ArrayList<LGMMSBean>();
		LGMMSBean data = null;		
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT * FROM mms_msg s, user_info u " + 
						" WHERE  u.f_index = s.etc2 AND" +
						" file_cnt = 0 AND s.reqdate > now() AND ";				
				if(type.equalsIgnoreCase("from")){	// ������ȭ��ȣ�� �˻�
					sql += "  s.etc1 like ? ";
				}else if(type.equalsIgnoreCase("message")){		// �޼����� �˻�
					sql += " s.msg like ? ";
				}else{			// �޴� ��ȭ��ȣ�� �˻�
					sql += " s.phone like ? ";
					search = search.replace("-", "");		// ������ ����						
				}
				
				//  ����û �����ڰ� �ƴϸ� �ش� ���������Ϻμ��� ���̵���
				if(psCode != 100){
					sql += " AND u.f_pscode = " + psCode;
				}					
				sql +=  " ORDER BY s.REQDATE DESC LIMIT ?, ? ";
				
				pstmt = conn.prepareStatement(sql);		
				pstmt.setString(1, "%" + search + "%");	
				pstmt.setInt(2, start -1);
				pstmt.setInt(3, end);				
				rs = pstmt.executeQuery();
				while(rs.next())	{
					// mms ������ ��´�.
				    data = new LGMMSBean();	
				    data.setIndex(rs.getLong("msgkey"));
				    data.setUserId(rs.getString("etc1"));
				    data.setUserIndex(rs.getInt("etc2"));
				    data.setCallback(rs.getString("callback"));			    
				    data.setPhone(rs.getString("phone"));
				    data.setMsg(rs.getString("msg"));
				    data.setSubject(rs.getString("subject"));
				    data.setTelcoinfo(rs.getString("telcoinfo"));
				    data.setRealsenddate(rs.getString("sentdate"));
				    data.setSenddate(rs.getString("reqdate"));		
				    data.setRsltstat(rs.getString("rslt"));
				    data.setType(rs.getString("type"));   
					list.add(data);
				}			
		//	}
		
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getReserveLmsSendAllList ���� : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}
	}

	/**
	 * MMS	���� ��ü �߼� ����Ʈ
	 * @param start
	 * 		���� ��ȣ
	 * @param end
	 * 		������ ����ȣ
	 * @param search
	 * 		�˻���
	 * @param type
	 * 		�˻�����
	 * @param psCode
	 * 		������ �ڵ�
	 * @return
	 * 	MMS List
	 */
	public List<LGMMSBean> getReserveMmsSendAllList(int start, int end,
			String search, String type, int psCode) {
		List<LGMMSBean> list = new ArrayList<LGMMSBean>();
		LGMMSBean data = null;		
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT * FROM mms_msg s, user_info u " + 
					" WHERE  u.f_index = s.etc2 AND " +
					" file_cnt > 0 AND s.reqdate > now() AND ";							
			if(type.equalsIgnoreCase("from")){	// ���� ���̵� �˻�
				sql += "  s.etc1 like ? ";
			}else if(type.equalsIgnoreCase("message")){		// �޼����� �˻�
				sql += " s.msg like ? ";
			}else{			// �޴� ��ȭ��ȣ�� �˻�
				sql += " s.callback like ? ";
				search = search.replace("-", "");		// ������ ����					
			}
			
			//  ����û �����ڰ� �ƴϸ� �ش� ���������Ϻμ��� ���̵���
			if(psCode != 100){
				sql += " AND u.f_pscode = " + psCode;
			}					
			sql +=  " ORDER BY s.REQDATE DESC LIMIT ?, ? ";
			
			pstmt = conn.prepareStatement(sql);		
			pstmt.setString(1, "%" + search + "%");	
			pstmt.setInt(2, start -1);
			pstmt.setInt(3, end);				
			rs = pstmt.executeQuery();
			while(rs.next())	{
				// mms ������ ��´�.
			    data = new LGMMSBean();	
			    data.setIndex(rs.getLong("msgkey"));
			    data.setUserId(rs.getString("etc1"));
			    data.setUserIndex(rs.getInt("etc2"));
			    data.setCallback(rs.getString("callback"));			    
			    data.setPhone(rs.getString("phone"));
			    data.setMsg(rs.getString("msg"));
			    data.setSubject(rs.getString("subject"));
			    data.setTelcoinfo(rs.getString("telcoinfo"));
			    data.setRealsenddate(rs.getString("sentdate"));
			    data.setSenddate(rs.getString("reqdate"));		
			    data.setRsltstat(rs.getString("rslt"));
			    data.setType(rs.getString("type"));   
				list.add(data);
			}			
			//}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getReserveMmsSendAllList ���� : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}
	}

	/**
	 * �����ڸ�忡�� �߼۳��� ���� (���� ����ó��)
	 * @param mode
	 * 	���۸��
	 * @param parseLong
	 * 	�߼��ε���
	 */
	public boolean realDelSendMessage(String mode, long index) {
		int result = 0;
		try {
			conn = dataSource.getConnection();
			String sql ="";
			if(mode.equals("SMS")){	// SMS ����̸�
				sql = "DELETE FROM sc_log WHERE tr_num = ? ";
			}else{ 	// LMS, MMS ���
				sql = "DELETE FROM mms_log WHERE msgkey = ? ";
			}
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, index);						// �޼��� �ε���
			// update
			result = pstmt.executeUpdate();
			return result > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("realDelSendMessage ���� : " + e.getMessage());
			return false;
		}finally{
			connClose();
		}
	}

	/**
	 * ���� ���� ����
	 * @param mode
	 *		�߼� ���
	 * @param index
	 * 		���� �ε���
	 * @return
	 */
	public boolean delReserveMessage(String mode, long index) {
		int result = 0;
		try {
			conn = dataSource.getConnection();
			String sql ="";
			if(mode.equals("SMS")){	// SMS ����̸�
				sql = "DELETE FROM sc_tran WHERE tr_num = ? ";
			}else{ 	// LMS, MMS ���
				sql = "DELETE FROM mms_msg WHERE msgkey = ? ";
			}
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, index);						// �޼��� �ε���
			// update
			result = pstmt.executeUpdate();
			return result > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("delReserveMessage ���� : " + e.getMessage());
			return false;
		}finally{
			connClose();
		}
	}	
	
	
	/**
	 * ���� ������ �߼��� ���ϱ�
	 * @param type
	 * 	����Ÿ��
	 * @return
	 */
	public String Mindate(String type) {
		String date = "";
		String sql = "";
		if(type.equalsIgnoreCase("SMS")){
			 sql = "select min(TR_SENDDATE) as mindate from sc_log where TR_SENDSTAT=2";
		}else if(type.equalsIgnoreCase("MMS")){
			 sql = "select min(REQDATE) as mindate from mms_log where STATUS=3 AND FILE_CNT!=0";
		}else {
			 sql = "select min(REQDATE) as mindate from mms_log where STATUS=3 AND FILE_CNT=0";
		}
		try {
			conn = dataSource.getConnection();						
			pstmt = conn.prepareStatement(sql);			
			rs = pstmt.executeQuery();
			if(rs.next()){
				date =  rs.getString("mindate");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Mindate ���� : " + e.getMessage());
		}finally{
			connClose();
		}
		return date;
	}
	
	/**
	 * ���� �ֱ� �߼��� ������
	 * @param type
	 * 	����Ÿ��
	 * @return
	 */
	public String Maxdate(String type) {
		String date = "";
		String sql = "";
		if(type.equalsIgnoreCase("SMS")){
			 sql = "select max(TR_SENDDATE) as maxdate from sc_log where TR_SENDSTAT=2";
		}else if(type.equalsIgnoreCase("MMS")){
			 sql = "select max(REQDATE) as maxdate from mms_log where STATUS=3 AND FILE_CNT!=0";
		}else {
			 sql = "select max(REQDATE) as maxdate from mms_log where STATUS=3 AND FILE_CNT=0";
		}
		try {
			conn = dataSource.getConnection();						
			pstmt = conn.prepareStatement(sql);			
			rs = pstmt.executeQuery();
			if(rs.next()){
				date =  rs.getString("maxdate");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Maxdate ���� : " + e.getMessage());
		}finally{
			connClose();
		}
		return date;
	}
	

	/**
	 * �������� �� �߼� Ƚ�� ��ȸ
	 * @param userIndex
	 * 	���� �ε���
	 * @return
	 */
	public List<Statistics> getPsStatisticsList(String date, String type) {
		List<Statistics> list = new ArrayList<Statistics>();
		Statistics data = null;
		int a_jan = 0, a_feb = 0, a_mar =0, a_apr = 0, a_may = 0, a_jun=0, a_jul=0, a_aug=0, a_sep=0, a_oct=0, a_nov=0, a_dec=0;
		String sql = "SELECT  A.f_psname, A.f_pscode, A.f_deptcode,";
		if(type.equalsIgnoreCase("SMS")){
			for(int i=1;i<=11;i++){
					sql+="sum(case when B.TR_SENDDATE between '"+date+"-"+i+"-01'  and '"+date+"-"+i+"-31' then 1 else 0 end) as "+i+"m,";
				}
			sql+="sum(case when B.TR_SENDDATE between '"+date+"-12-01'  and '"+date+"-12-31' then 1 else 0 end) as 12m";
			sql+=" FROM user_info A LEFT outer JOIN (SELECT * from sc_log where TR_SENDSTAT=2) B ON A.f_index = B.TR_ETC2 ";
			sql+="group BY A.f_psname order by count(A.f_psname) desc";		
		}else if(type.equalsIgnoreCase("MMS")){
			for(int i=1;i<=11;i++){
				sql+="sum(case when B.REQDATE between '"+date+"-"+i+"-01'  and '"+date+"-"+i+"-31' then 1 else 0 end) as "+i+"m,";
			}
				sql+="sum(case when B.REQDATE between '"+date+"-12-01'  and '"+date+"-12-31' then 1 else 0 end) as 12m";
				sql+=" FROM user_info A LEFT outer JOIN (SELECT * from mms_log where STATUS=3 AND FILE_CNT!=0) B ON A.f_index = B.ETC2 ";
				sql+="group BY A.f_psname order by count(A.f_psname) desc";	
		}else {
			for(int i=1;i<=11;i++){
				sql+="sum(case when B.REQDATE between '"+date+"-"+i+"-01'  and '"+date+"-"+i+"-31' then 1 else 0 end) as "+i+"m,";
			}
				sql+="sum(case when B.REQDATE between '"+date+"-12-01'  and '"+date+"-12-31' then 1 else 0 end) as 12m";
				sql+=" FROM user_info A LEFT outer JOIN (SELECT * from mms_log where STATUS=3 AND FILE_CNT=0) B ON A.f_index = B.ETC2 ";
				sql+="group BY A.f_psname order by count(A.f_psname) desc";	
		}		
		
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				// ������ �׷�
			    data = new Statistics();	
			    data.setName(rs.getString("f_psname"));
			    data.setJan(rs.getInt("1m"));
			    a_jan += rs.getInt("1m");
			    data.setFeb(rs.getInt("2m"));
			    a_feb += rs.getInt("2m");
			    data.setMar(rs.getInt("3m"));
			    a_mar += rs.getInt("3m");
			    data.setApr(rs.getInt("4m"));
			    a_apr += rs.getInt("4m");
			    data.setMay(rs.getInt("5m"));
			    a_may += rs.getInt("5m");
			    data.setJun(rs.getInt("6m"));
			    a_jun += rs.getInt("6m");
			    data.setJul(rs.getInt("7m"));
			    a_jul += rs.getInt("7m");
			    data.setAug(rs.getInt("8m"));
			    a_aug += rs.getInt("8m");
			    data.setSep(rs.getInt("9m"));
			    a_sep += rs.getInt("9m");
			    data.setOct(rs.getInt("10m"));
			    a_oct += rs.getInt("10m");
			    data.setNov(rs.getInt("11m"));
			    a_nov += rs.getInt("11m");
			    data.setDec(rs.getInt("12m"));
			    a_dec += rs.getInt("12m");
			    data.setPscode(rs.getInt("f_pscode"));
			    data.setDeptcode(rs.getInt("f_deptcode"));
				list.add(data);
  			}
			
			data = new Statistics();	
			data.setName("�հ�");
			data.setId("");
		    data.setJan(a_jan);
		    data.setFeb(a_feb);
		    data.setMar(a_mar);
		    data.setApr(a_apr);
		    data.setMay(a_may);
		    data.setJun(a_jun);
		    data.setJul(a_jul);
		    data.setAug(a_aug);
		    data.setSep(a_sep);
		    data.setOct(a_oct);
		    data.setNov(a_nov);
		    data.setDec(a_dec);		    
		    list.add(data);
		    
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getPsStatisticsList ���� : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}
	}
	
	/**
	 * �μ��� �� �߼� Ƚ�� ��ȸ
	 * @param userIndex
	 * 	���� �ε���
	 * @return
	 */
	public List<Statistics> getDeptStatisticsList(String date, String type, int pscode) {
		List<Statistics> list = new ArrayList<Statistics>();
		Statistics data = null;
		String sql = "SELECT  A.f_deptname,  A.f_deptcode,";
		if(type.equalsIgnoreCase("SMS")){
			for(int i=1;i<=11;i++){
					sql+="sum(case when B.TR_SENDDATE between '"+date+"-"+i+"-01'  and '"+date+"-"+i+"-31' then 1 else 0 end) as "+i+"m,";
				}
			sql+="sum(case when B.TR_SENDDATE between '"+date+"-12-01'  and '"+date+"-12-31' then 1 else 0 end) as 12m";
			sql+=" FROM ( SELECT * from user_info where f_pscode="+pscode+") A LEFT outer JOIN (SELECT * from sc_log where TR_SENDSTAT=2) B ON A.f_index = B.TR_ETC2 ";
			sql+="group BY A.f_deptname order by count(A.f_deptname) desc";		
		}else if(type.equalsIgnoreCase("MMS")){
			for(int i=1;i<=11;i++){
				sql+="sum(case when B.REQDATE between '"+date+"-"+i+"-01'  and '"+date+"-"+i+"-31' then 1 else 0 end) as "+i+"m,";
			}
				sql+="sum(case when B.REQDATE between '"+date+"-12-01'  and '"+date+"-12-31' then 1 else 0 end) as 12m";
				sql+=" FROM ( SELECT * from user_info where f_pscode="+pscode+") A LEFT outer JOIN (SELECT * from mms_log where STATUS=3 AND FILE_CNT!=0) B ON A.f_index = B.ETC2 ";
				sql+="group BY A.f_deptname order by count(A.f_deptname) desc";	
		}else {
			for(int i=1;i<=11;i++){
				sql+="sum(case when B.REQDATE between '"+date+"-"+i+"-01'  and '"+date+"-"+i+"-31' then 1 else 0 end) as "+i+"m,";
			}
				sql+="sum(case when B.REQDATE between '"+date+"-12-01'  and '"+date+"-12-31' then 1 else 0 end) as 12m";
				sql+=" FROM ( SELECT * from user_info where f_pscode="+pscode+") A LEFT outer JOIN (SELECT * from mms_log where STATUS=3 AND FILE_CNT=0) B ON A.f_index = B.ETC2 ";
				sql+="group BY A.f_deptname order by count(A.f_deptname) desc";	
		}
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				// ������ �׷�
			    data = new Statistics();	
			    data.setName(rs.getString("f_deptname"));
			    data.setJan(rs.getInt("1m"));
			    data.setFeb(rs.getInt("2m"));
			    data.setMar(rs.getInt("3m"));
			    data.setApr(rs.getInt("4m"));
			    data.setMay(rs.getInt("5m"));
			    data.setJun(rs.getInt("6m"));
			    data.setJul(rs.getInt("7m"));
			    data.setAug(rs.getInt("8m"));
			    data.setSep(rs.getInt("9m"));
			    data.setOct(rs.getInt("10m"));
			    data.setNov(rs.getInt("11m"));
			    data.setDec(rs.getInt("12m"));
			    data.setDeptcode(rs.getInt("f_deptcode"));
				list.add(data);
  			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getDeptStatisticsList ���� : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}
	}
	
	/**
	 * ���� �� �߼� Ƚ�� ��ȸ
	 * @param userIndex
	 *  @param date
	 * 	���� �ε���
	 * @return
	 */
	public List<Statistics> getUserStatisticsList(String date, String type, int deptcode, int start, int end, String search, String s_type) {
		List<Statistics> list = new ArrayList<Statistics>();
		Statistics data = null;
		start -= 1;
		Aria aria = Aria.getInstance();	
		
		String sql = "SELECT  A.f_name, A.f_id, ";
		if(type.equalsIgnoreCase("SMS")){			
					sql+="sum(case when B.TR_SENDDATE between '"+date+"-01-01'  and '"+date+"-01-31' then 1 else 0 end) as 1m,"+
							"sum(case when B.TR_SENDDATE between '"+date+"-02-01'  and '"+date+"-02-31' then 1 else 0 end) as 2m,"+
							"sum(case when B.TR_SENDDATE between '"+date+"-03-01'  and '"+date+"-03-31' then 1 else 0 end) as 3m,"+
							"sum(case when B.TR_SENDDATE between '"+date+"-04-01'  and '"+date+"-04-31' then 1 else 0 end) as 4m,"+
							"sum(case when B.TR_SENDDATE between '"+date+"-05-01'  and '"+date+"-05-31' then 1 else 0 end) as 5m,"+
							"sum(case when B.TR_SENDDATE between '"+date+"-06-01'  and '"+date+"-06-31' then 1 else 0 end) as 6m,"+
							"sum(case when B.TR_SENDDATE between '"+date+"-07-01'  and '"+date+"-07-31' then 1 else 0 end) as 7m,"+
							"sum(case when B.TR_SENDDATE between '"+date+"-08-01'  and '"+date+"-08-31' then 1 else 0 end) as 8m,"+
							"sum(case when B.TR_SENDDATE between '"+date+"-09-01'  and '"+date+"-09-31' then 1 else 0 end) as 9m,"+
							"sum(case when B.TR_SENDDATE between '"+date+"-10-01'  and '"+date+"-10-31' then 1 else 0 end) as 10m,"+
							"sum(case when B.TR_SENDDATE between '"+date+"-11-01'  and '"+date+"-11-31' then 1 else 0 end) as 11m,"+			
							"sum(case when B.TR_SENDDATE between '"+date+"-12-01'  and '"+date+"-12-31' then 1 else 0 end) as 12m"+
							" FROM ( SELECT * from user_info where f_deptcode="+deptcode+" AND ";
					if(s_type.equalsIgnoreCase("id")){	// ID�� �˻�
						sql += "  f_id like ? ";
					}else {																// �̸����� �˻�
						sql += " f_name like ? ";
						search=aria.encryptByte2HexStr(search);	
					}
					sql+=") A LEFT outer JOIN (SELECT * from sc_log where TR_SENDSTAT=2) B ON A.f_index = B.TR_ETC2 "+
							"group BY A.f_name, A.f_index order by count(A.f_name) desc LIMIT "+start+", "+end;		
		}else if(type.equalsIgnoreCase("MMS")){		
				sql+="sum(case when B.REQDATE between '"+date+"-01-01'  and '"+date+"-01-31' then 1 else 0 end) as 1m,"+
						"sum(case when B.REQDATE between '"+date+"-02-01'  and '"+date+"-02-31' then 1 else 0 end) as 2m,"+
						"sum(case when B.REQDATE between '"+date+"-03-01'  and '"+date+"-03-31' then 1 else 0 end) as 3m,"+
						"sum(case when B.REQDATE between '"+date+"-04-01'  and '"+date+"-04-31' then 1 else 0 end) as 4m,"+
						"sum(case when B.REQDATE between '"+date+"-05-01'  and '"+date+"-05-31' then 1 else 0 end) as 5m,"+
						"sum(case when B.REQDATE between '"+date+"-06-01'  and '"+date+"-06-31' then 1 else 0 end) as 6m,"+
						"sum(case when B.REQDATE between '"+date+"-07-01'  and '"+date+"-07-31' then 1 else 0 end) as 7m,"+
						"sum(case when B.REQDATE between '"+date+"-08-01'  and '"+date+"-08-31' then 1 else 0 end) as 8m,"+
						"sum(case when B.REQDATE between '"+date+"-09-01'  and '"+date+"-09-31' then 1 else 0 end) as 9m,"+
						"sum(case when B.REQDATE between '"+date+"-10-01'  and '"+date+"-10-31' then 1 else 0 end) as 10m,"+
						"sum(case when B.REQDATE between '"+date+"-11-01'  and '"+date+"-11-31' then 1 else 0 end) as 11m,"+
						"sum(case when B.REQDATE between '"+date+"-12-01'  and '"+date+"-12-31' then 1 else 0 end) as 12m"+
						" FROM ( SELECT * from user_info where f_deptcode="+deptcode+" AND ";
				if(s_type.equalsIgnoreCase("from")){	// ID�� �˻�
					sql += "  f_id like ? ";
				}else {																// �̸����� �˻�
					sql += " f_name like ? ";
					search=aria.encryptByte2HexStr(search);	
				}
				sql+=		") A LEFT outer JOIN (SELECT * from mms_log where STATUS=3 AND FILE_CNT!=0) B ON A.f_index = B.ETC2 "+
						"group BY A.f_name, A.f_index order by count(A.f_name) desc LIMIT "+start+", "+end;	
		}else{
			
				sql+="sum(case when B.REQDATE between '"+date+"-01-01'  and '"+date+"-01-31' then 1 else 0 end) as 1m,"+
						"sum(case when B.REQDATE between '"+date+"-02-01'  and '"+date+"-02-31' then 1 else 0 end) as 2m,"+
						"sum(case when B.REQDATE between '"+date+"-03-01'  and '"+date+"-03-31' then 1 else 0 end) as 3m,"+
						"sum(case when B.REQDATE between '"+date+"-04-01'  and '"+date+"-04-31' then 1 else 0 end) as 4m,"+
						"sum(case when B.REQDATE between '"+date+"-05-01'  and '"+date+"-05-31' then 1 else 0 end) as 5m,"+
						"sum(case when B.REQDATE between '"+date+"-06-01'  and '"+date+"-06-31' then 1 else 0 end) as 6m,"+
						"sum(case when B.REQDATE between '"+date+"-07-01'  and '"+date+"-07-31' then 1 else 0 end) as 7m,"+
						"sum(case when B.REQDATE between '"+date+"-08-01'  and '"+date+"-08-31' then 1 else 0 end) as 8m,"+
						"sum(case when B.REQDATE between '"+date+"-09-01'  and '"+date+"-09-31' then 1 else 0 end) as 9m,"+
						"sum(case when B.REQDATE between '"+date+"-10-01'  and '"+date+"-10-31' then 1 else 0 end) as 10m,"+
						"sum(case when B.REQDATE between '"+date+"-11-01'  and '"+date+"-11-31' then 1 else 0 end) as 11m,"+
						"sum(case when B.REQDATE between '"+date+"-12-01'  and '"+date+"-12-31' then 1 else 0 end) as 12m"+
						" FROM ( SELECT * from user_info where f_deptcode="+deptcode+" AND ";
						if(s_type.equalsIgnoreCase("from")){	// ID�� �˻�
							sql += "  f_id like ? ";
						}else {																// �̸����� �˻�
							sql += " f_name like ? ";
							search=aria.encryptByte2HexStr(search);	
						}
						sql+=") A  LEFT outer JOIN (SELECT * from mms_log where STATUS=3 AND FILE_CNT=0) B ON A.f_index = B.ETC2 "+
						"group BY A.f_name, A.f_index order by count(A.f_name) desc LIMIT "+start+", "+end;	
		}		
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + search + "%");	
			rs = pstmt.executeQuery();
			while(rs.next()){
				// ������ �׷�
			    data = new Statistics();	
			    data.setName(aria.encryptHexStr2DecryptStr(rs.getString("f_name")));
			    data.setId(rs.getString("f_id"));
			    data.setJan(rs.getInt("1m"));
			    data.setFeb(rs.getInt("2m"));
			    data.setMar(rs.getInt("3m"));
			    data.setApr(rs.getInt("4m"));
			    data.setMay(rs.getInt("5m"));
			    data.setJun(rs.getInt("6m"));
			    data.setJul(rs.getInt("7m"));
			    data.setAug(rs.getInt("8m"));
			    data.setSep(rs.getInt("9m"));
			    data.setOct(rs.getInt("10m"));
			    data.setNov(rs.getInt("11m"));
			    data.setDec(rs.getInt("12m"));			    
			    list.add(data);			   
  			}			
		    
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getUserStatisticsList ���� : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}
	}
	
	/**
	 * �μ� ����� �� 
	 * @param 
	 * 		����Ÿ��
	 * @param search 
	 * 		�˻���
	 * @return
	 */
	public int getDeptUserCount(int deptcode, String search, String s_type) {		
		int result = 0;
		Aria aria = Aria.getInstance();	
		
		String sql = "SELECT count(*) FROM user_info where f_deptcode = ? AND ";
		if(s_type.equalsIgnoreCase("id")){	// ID�� �˻�
			sql += "  f_id like ? ";
		}else {																// �̸����� �˻�
			sql += " f_name like ? ";
			search=aria.encryptByte2HexStr(search);	
		}
		
		try {		
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);		
			pstmt.setInt(1, deptcode);
			pstmt.setString(2, "%" + search + "%");	
			rs = pstmt.executeQuery();
			if(rs.next()){
				result =  rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getDeptUserCount ���� : " + e.getMessage());
		}finally{
			connClose();
		}
		return result;
	}	
	
	/**
	 * ��� �Ⱓ�� �˻�
	 * @param userIndex
	 * 	���� �ε���
	 * @return
	 */
	public List<Statistics> getStatisticsSearch(String s_date, String c_date, String type, int start, int end) {
		List<Statistics> list = new ArrayList<Statistics>();
		Statistics data = null;
		Aria aria = Aria.getInstance();	
		String sql = "SELECT   A.f_psname, A.f_pscode, A.f_deptname, A.f_deptcode, A.f_id, A.f_name, A.f_index, ";
		if(type.equalsIgnoreCase("SMS")){			
			sql+="sum(case when B.TR_SENDDATE between '"+s_date+"'  and '"+c_date+"' then 1 else 0 end) as d_count";
			sql+=" FROM user_info A LEFT outer JOIN (SELECT * from sc_log where TR_SENDSTAT=2) B ON A.f_index = B.TR_ETC2 ";
			sql+=" group by A.f_index order by d_count desc ";		
		}else if(type.equalsIgnoreCase("MMS")){			
				sql+="sum(case when B.REQDATE between '"+s_date+"'  and '"+c_date+"' then 1 else 0 end) as d_count";
				sql+=" FROM user_info A LEFT outer JOIN (SELECT * from mms_log where STATUS=3 AND FILE_CNT!=0) B ON A.f_index = B.ETC2 ";
				sql+=" group by A.f_index order by d_count desc";		
		}else {		
				sql+="sum(case when B.REQDATE between '"+s_date+"'  and '"+c_date+"' then 1 else 0 end) as d_count";
				sql+=" FROM user_info A LEFT outer JOIN (SELECT * from mms_log where STATUS=3 AND FILE_CNT=0) B ON A.f_index = B.ETC2 ";
				sql+=" group by A.f_index order by d_count desc";	
		}
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				// ������ �׷�
			    data = new Statistics();	
			    data.setIndex(rs.getInt("f_index"));																							//	�ε���
			    data.setJan(rs.getInt("d_count"));			  																					//	�߼�Ƚ��
			    data.setDeptname(rs.getString("f_deptname"));																	//	�μ��̸�
			    data.setDeptcode(rs.getInt("f_deptcode"));																			//	�μ��ڵ�
			    data.setPsname(rs.getString("f_psname"));																			//	�������̸�
			    data.setPscode(rs.getInt("f_pscode"));																					// ������ �ڵ�
			    data.setId(rs.getString("f_id"));																									// ID
			    data.setName(aria.encryptHexStr2DecryptStr(rs.getString("f_name")));						// �̸�
			    if(data.getJan()>0){
			    list.add(data);
			    }
  			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getStatisticsSearch ���� : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}
	}
	
	/**
	 * �Ⱓ�� ����� �� 
	 * @param 
	 * 		����Ÿ��
	 * @param search 
	 * 		�˻���
	 * @return
	 */
	public int getStatisticsSearchCount(String s_date, String c_date, String type) {		
		int result = 0;
		Aria aria = Aria.getInstance();	
		
		String sql = "SELECT (*) from ";
		if(type.equalsIgnoreCase("SMS")){	// ID�� �˻�
			sql += " sc_log where TR_SENDSTAT=2 AND TR_SENDDATE between ? and ? ";
		}else if(type.equalsIgnoreCase("MMS")) {																// �̸����� �˻�
			sql += " mms_log where STATUS=3 AND FILE_CNT!=0 AND REQDATE between ? and ?";			
		}else{
			sql += " mms_log where STATUS=3 AND FILE_CNT=0 AND REQDATE between ? and ?";
		}
		
		try {		
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);		
			pstmt.setString(1, "'" + s_date + "'");
			pstmt.setString(2, "'" + c_date + "'");	
			rs = pstmt.executeQuery();
			if(rs.next()){
				result =  rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getDeptUserCount ���� : " + e.getMessage());
		}finally{
			connClose();
		}
		return result;
	}
	
	
	/**
	 * �ش� ������ �� ���� ���� ���ϱ�
	 * @param type
	 * 	����Ÿ��
	 * @return
	 */
	public String PsStatisticsCount(String type, int pscode, String year, int month, int day) {
		String count = "";
		String sql = "SELECT ";
		if(type.equalsIgnoreCase("SMS")){			
			sql+="sum(case when B.TR_SENDDATE  between '"+year+"-"+month+"-"+day+" 00:00:00' and '"+year+"-"+month+"-"+(day+1)+" 00:00:00' then 1 else 0 end) as d_count ";
			sql+=" FROM ( SELECT * from user_info where f_pscode="+pscode+") A LEFT outer JOIN (SELECT * from sc_log where TR_SENDSTAT=2) B ON A.f_index = B.TR_ETC2 ";
		}else if(type.equalsIgnoreCase("MMS")){			
				sql+=" sum(case when B.REQDATE  between '"+year+"-"+month+"-"+day+" 00:00:00' and '"+year+"-"+month+"-"+(day+1)+" 00:00:00' then 1 else 0 end) as d_count ";
				sql+=" FROM ( SELECT * from user_info where f_pscode="+pscode+") A LEFT outer JOIN (SELECT * from mms_log where STATUS=3 AND FILE_CNT!=0) B ON A.f_index = B.ETC2 ";
		}else {		
				sql+="sum(case when B.REQDATE  between '"+year+"-"+month+"-"+day+" 00:00:00' and '"+year+"-"+month+"-"+(day+1)+" 00:00:00' then 1 else 0 end) as d_count ";
				sql+=" FROM ( SELECT * from user_info where f_pscode="+pscode+") A LEFT outer JOIN (SELECT * from mms_log where STATUS=3 AND FILE_CNT=0) B ON A.f_index = B.ETC2 ";
		}
		try {
			conn = dataSource.getConnection();						
			pstmt = conn.prepareStatement(sql);			
			rs = pstmt.executeQuery();
			if(rs.next()){
				count =  rs.getString("d_count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("PsStatisticsCount ���� : " + e.getMessage());
		}finally{
			connClose();
		}
		return count;
	}
	
	/**
	 * �ش� �μ� �� ���� ���� ���ϱ�
	 * @param type
	 * 	����Ÿ��
	 * @return
	 */
	public String DeptStatisticsCount(String type, int deptcode, String year, int month, int day) {
		String count = "";
		String sql = "SELECT ";
		if(type.equalsIgnoreCase("SMS")){			
			sql+="sum(case when B.TR_SENDDATE  between '"+year+"-"+month+"-"+day+" 00:00:00' and '"+year+"-"+month+"-"+(day+1)+" 00:00:00' then 1 else 0 end) as d_count ";
			sql+=" FROM ( SELECT * from user_info where f_deptcode="+deptcode+") A LEFT outer JOIN (SELECT * from sc_log where TR_SENDSTAT=2) B ON A.f_index = B.TR_ETC2 ";
		}else if(type.equalsIgnoreCase("MMS")){			
				sql+=" sum(case when B.REQDATE  between '"+year+"-"+month+"-"+day+" 00:00:00' and '"+year+"-"+month+"-"+(day+1)+" 00:00:00' then 1 else 0 end) as d_count ";
				sql+=" FROM ( SELECT * from user_info where f_deptcode="+deptcode+") A LEFT outer JOIN (SELECT * from mms_log where STATUS=3 AND FILE_CNT!=0) B ON A.f_index = B.ETC2 ";
		}else {		
				sql+="sum(case when B.REQDATE  between '"+year+"-"+month+"-"+day+" 00:00:00' and '"+year+"-"+month+"-"+(day+1)+" 00:00:00' then 1 else 0 end) as d_count ";
				sql+=" FROM ( SELECT * from user_info where f_deptcode="+deptcode+") A LEFT outer JOIN (SELECT * from mms_log where STATUS=3 AND FILE_CNT=0) B ON A.f_index = B.ETC2 ";
		}
		try {
			conn = dataSource.getConnection();						
			pstmt = conn.prepareStatement(sql);			
			rs = pstmt.executeQuery();
			if(rs.next()){
				count =  rs.getString("d_count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("DeptStatisticsCount ���� : " + e.getMessage());
		}finally{
			connClose();
		}
		return count;
	}
	
	/**
	 * �ش� ���� �� ���� ���� ���ϱ�
	 * @param type
	 * 	����Ÿ��
	 * @return
	 */
	public String UserStatisticsCount(String type, String UserId, String year, int month, int day) {
		String count = "";
		String sql = "SELECT ";
		if(type.equalsIgnoreCase("SMS")){			
			sql+="sum(case when B.TR_SENDDATE  between '"+year+"-"+month+"-"+day+" 00:00:00' and '"+year+"-"+month+"-"+(day+1)+" 00:00:00' then 1 else 0 end) as d_count ";
			sql+=" FROM ( SELECT * from user_info WHERE f_id = '"+ UserId +"' ) A LEFT outer JOIN (SELECT * from sc_log where TR_SENDSTAT=2) B ON A.f_index = B.TR_ETC2 ";
		}else if(type.equalsIgnoreCase("MMS")){			
				sql+=" sum(case when B.REQDATE  between '"+year+"-"+month+"-"+day+" 00:00:00' and '"+year+"-"+month+"-"+(day+1)+" 00:00:00' then 1 else 0 end) as d_count ";
				sql+=" FROM ( SELECT * from user_info WHERE f_id = '"+ UserId +"' ) A LEFT outer JOIN (SELECT * from mms_log where STATUS=3 AND FILE_CNT!=0) B ON A.f_index = B.ETC2 ";
		}else {		
				sql+="sum(case when B.REQDATE  between '"+year+"-"+month+"-"+day+" 00:00:00' and '"+year+"-"+month+"-"+(day+1)+" 00:00:00' then 1 else 0 end) as d_count ";
				sql+=" FROM ( SELECT * from user_info WHERE f_id = '"+ UserId +"' ) A LEFT outer JOIN (SELECT * from mms_log where STATUS=3 AND FILE_CNT=0) B ON A.f_index = B.ETC2 ";
		}
		try {
			conn = dataSource.getConnection();						
			pstmt = conn.prepareStatement(sql);			
			rs = pstmt.executeQuery();
			if(rs.next()){
				count =  rs.getString("d_count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("UserStatisticsCount ���� : " + e.getMessage());
		}finally{
			connClose();
		}
		return count;
	}
	
}
