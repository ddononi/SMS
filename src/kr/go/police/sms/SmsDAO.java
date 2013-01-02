package kr.go.police.sms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
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
			    data.setReserveDate(rs.getString("f_reserve_date"));	
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
	public List<SMSBean> getSendResultList(int userIndex, int start, int end, String search) {
		List<SMSBean> list = new ArrayList<SMSBean>();
		SMSBean data = null;		
		try {
			conn = dataSource.getConnection();
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
	public int addSmsSendList(ArrayList<SMSBean> list) {
		int resultCount = 0;
		try {
			conn = dataSource.getConnection();
			String sql;
			for(SMSBean data : list){
				sql = "INSERT INTO sms_send_info ( f_index, f_user_id, f_user_index, f_callto, f_callfrom, f_message, " +
						"f_send_count, f_send_state, f_reserved, f_reserve_date, f_callback," +
						" f_nameto, f_flag, f_reg_date, f_result_msg, f_file1, f_file2, f_file3 )" +
						" VALUES (?, ?, ?, ?, ?, ?, ?, 0, ?, ?, ?, '',  ?, now(), '������' , ?, ?, ?) ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setLong(1, data.getIndex());				// ���� ������ ��ȣ				
				pstmt.setString(2, data.getId());					// ���� ���̵�
				pstmt.setInt(3, data.getUserIndex());			// ���� �ε���
				pstmt.setString(4, data.getToPhone());			// �޴� ��ȭ��ȣ
				pstmt.setString(5, data.getFromPhone());		// ������ ��ȭ��ȣ				
				pstmt.setString(6, data.getMessage());			// �޼���
				pstmt.setInt(7, list.size());							// �ش� ���� ���� ����	
				pstmt.setString(8, data.isResreved()?"y":"n");	// ���࿩��
				pstmt.setString(9, data.getReserveDate());	// ������				
				pstmt.setString(10, data.getCallback());			// �߼� ���� ��ȭ��ȣ
				pstmt.setString(11, data.getFlag());				//	����Ÿ��	
				pstmt.setString(12, data.getFile1());				//	����1
				pstmt.setString(13, data.getFile2());				//	����2
				pstmt.setString(14, data.getFile3());				//	����3				
				resultCount +=  pstmt.executeUpdate();
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
	 * @return
	 */
	public int getSendResultCount(int userIndex, String search) {
		int result = 0;
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement("SELECT count(*) FROM sms_send_info WHERE" +
					" f_user_index = ? AND " +
					" f_callto like ?  AND f_is_del = 'n'  ORDER BY f_index DESC ");
			pstmt.setInt(1, userIndex);	
			pstmt.setString(2, "%" + search + "%");				
			rs = pstmt.executeQuery();
			if(rs.next()){
				result =  rs.getInt(1);
  			}
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
			    data.setReserveDate(rs.getString("f_reserve_date"));			    
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
	 * �����ڰ� ���� �ֵ��� f_is_del�ʵ尪�� ������Ʈó�� �Ѵ�.
	 * @param index
	 * 	�޼��� �ε���
	 * @return
	 */
	public boolean delSendMessage(long index) {
		int result = 0;
		try {
			conn = dataSource.getConnection();
			String sql = "UPDATE sms_send_info set f_is_del = 'y'  WHERE f_index = ? ";
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
	public List<SMSBean> getSendAllList(int start, int end, String search, String type, int psCode) {
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
	 * �����߼۰��� ���ϱ�
	 * @param search
	 * 		�˻���
	 * @param type
	 * 		�˻�����
	 * @param psCode 
	 * @return
	 */
	public int getSendAllListCount(String search, String type, int psCode) {
		int result = 0;
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT count(s.f_index) FROM sms_send_info s, user_info u " + 
					" WHERE  u.f_index = s.f_user_index AND ";
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
			pstmt = conn.prepareStatement(sql);			
			pstmt.setString(1, "%" + search + "%");
			rs = pstmt.executeQuery();
			if(rs.next()){
				result =  rs.getInt(1);
			}
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
			if(type.equalsIgnoreCase("from")){	// �޼����� �˻�
				sql += "  f_message like ? ";
			}else if(type.equalsIgnoreCase("message")){		// �޴� ��ȭ��ȣ�� �˻�
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
			    data.setReserveDate(rs.getString("f_reserve_date"));		    
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

}
