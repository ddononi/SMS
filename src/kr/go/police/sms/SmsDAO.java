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
 * 문자 전송, 내역 관리
 * 문자함 관련 Dao
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
	 * 내 문자함 목록
	 * @param userIndex
	 * 	유저 인덱스
	 * @param groupIndex
	 * 	그룹 인덱스
	 * @return
	 * 메시지 리스트
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
				// 인덱스, 등록아이디, 제목, 내용, 그룹인덱스, 그룹명
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
			System.out.println("getMyMessages 에러 : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}
	}

	/**
	 * 	선택한 문자함 메세지 보기
	 * @param index
	 * 		메시지 인덱스
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
				// 인덱스, 등록아이디, 제목, 내용, 그룹인덱스, 그룹명
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
			System.out.println("getMyMessage 에러 : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}
	}	
	
	/**
	 * 내 문자함 목록
	 * @param userIndex
	 * 	유저 인덱스
	 * @param groupIndex
	 * 	그룹 인덱스
	 * @return
	 * 메시지 리스트
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
				// 그룹인덱스, 그룹명
			    data = new Message();	
			    data.setGroupIndex(rs.getInt("f_index"));
			    data.setGroup(rs.getString("f_group"));
				list.add(data);
			}		
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getMessageGroupList 에러 : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}
	}	
	*/
	

	/**
	 * 예약 내역 리스트 가져오기
	 * @param userIndex
	 * 	유저 인덱스
	 * @param pae
	 * 	페이지
	 * @param limit
	 *  한페이지수
	 * @return
	 */
	protected List<SMSBean> getReservedList(int userIndex, int page, int limit){
		List<SMSBean> list = new ArrayList<SMSBean>();
		SMSBean data = null;		
		int startRow = (page -1 ) * 10 +1;		// 시작 번호
		int endRow = startRow + limit -1;		// 끝 번호
		try {
			conn = dataSource.getConnection();
			// 해당 유저의 예약된 문자내역만 가져온다.
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
			System.out.println("getReservedList 에러 : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}

	}		

	/**
	 *  전송 결과 내역 리스트
	 * @param userIndex
	 * 		유저 인덱스
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
				// 문자 내역을 담는다.
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
			System.out.println("getSendResultList 에러 : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}
	}
	
	
	/**
	 * 내 문자함 그룹리스트
	 * @param userIndex
	 * 	유저 인덱스
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
				// 문자함 그룹
			    data = new Group();	
			    data.setIndex(rs.getInt("f_index"));
			    data.setGroup(rs.getString("f_group"));
				list.add(data);
  			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getMyGroupList 에러 : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}
	}	
	
	/**
	 * 내 문자함 추가
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
			pstmt.setString(1, msg.getId());					// 아이디
			pstmt.setInt(2, msg.getGroupIndex());			// 그룹 인덱스
			pstmt.setString(3, msg.getGroup());				// 그룹명
			pstmt.setString(4, msg.getTitle());				// 제목	
			pstmt.setString(5, msg.getMessage());			// 메세지	
			pstmt.setInt(6, msg.getUserIndex());			// 유저 인덱스	
			// update
			result = pstmt.executeUpdate();
			return result > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("addMyMessage 에러 : " + e.getMessage());
			return false;
		}finally{
			connClose();
		}
	}
	
	/**
	 * 	문자함 수정
	 * @param msg
	 * 	메시지 객체
	 * @return
	 */
	public boolean modifyMyMessage(Message msg) {
		int result = 0;
		try {
			conn = dataSource.getConnection();
			String sql = "UPDATE message  SET   f_message_title = ?, f_message_text = ? " +
					"WHERE f_index = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, msg.getTitle());				// 제목	
			pstmt.setString(2, msg.getMessage());			// 메세지	
			pstmt.setInt(3, msg.getIndex());					//  인덱스	
			// update
			result = pstmt.executeUpdate();
			return result > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("modifyMyMessage 에러 : " + e.getMessage());
			return false;
		}finally{
			connClose();
		}
	}	

	/**
	 * 	메세지 삭제
	 * @param index
	 * 	메세지 인덱스
	 * @return
	 */
	public boolean delMessage(int index) {
		int result = 0;
		try {
			conn = dataSource.getConnection();
			String sql = "DELETE FROM message WHERE 1 =1 AND f_index = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, index);						// 메세지 인덱스
			// update
			result = pstmt.executeUpdate();
			return result > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("delMessage 에러 : " + e.getMessage());
			return false;
		}finally{
			connClose();
		}
	}		

	/**
	 * 	문자함 그룹 추가
	 * @param userIndex
	 * 		유저 인덱스
	 * @param groupName
	 * 		그룹명
	 * @return
	 */
	public boolean addGroup(int userIndex, String groupName) {
		int result = 0;
		try {
			conn = dataSource.getConnection();
			String sql = "INSERT INTO message_group ( f_user_index, f_group) VALUES (?, ?) ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userIndex);							// 유저 인덱스
			pstmt.setString(2, groupName);					// 그룹 인덱스		

			// update
			result = pstmt.executeUpdate();
			return result > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("addGroup 에러 : " + e.getMessage());
			return false;
		}finally{
			connClose();
		}
	}

	/**
	 *	그룹 삭제
	 * @param userIndex
	 * 		유저 인덱스
	 * @param groupIndex
	 * 		그룹 인덱스
	 * @return
	 */
	public boolean delGroup(int userIndex, int groupIndex) {
		int result = 0;
		try {
			conn = dataSource.getConnection();
			String sql = "DELETE FROM message_group WHERE 1 =1 AND" +
					" f_user_index = ? AND f_index = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userIndex);						// 유저 인덱스
			pstmt.setInt(2, groupIndex);					// 그룹 인덱스			

			// update
			result = pstmt.executeUpdate();
			return result > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("delGroup 에러 : " + e.getMessage());
			return false;
		}finally{
			connClose();
		}
	}

	/**
	 * 	그룹 수정
	 * @param userIndex
	 * 		유저 인덱스
	 * @param groupIndex
	 * 		그룹 인덱스
	 * @param groupName
	 * 		그룹명
	 * @return
	 */
	public boolean modifyGroup(int userIndex, int groupIndex, String groupName) {
		int result = 0;
		try {
			conn = dataSource.getConnection();
			String sql = "UPDATE message_group SET f_group = ? WHERE 1 =1 AND" +
					" f_user_index = ? AND f_index = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, groupName);					// 그룹명			
			pstmt.setInt(2, userIndex);						// 유저 인덱스
			pstmt.setInt(3, groupIndex);					// 그룹 인덱스			

			// update
			result = pstmt.executeUpdate();
			return result > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("modifyGroup 에러 : " + e.getMessage());
			return false;
		}finally{
			connClose();
		}
	}	
	
	/**
	 * 리소스 반환 반환 순서대로 닫아준다.
	 */
	public void connClose() {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				System.out.println("rs 에러" + e.getMessage());				
			}
		}

		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				System.out.println("pstmt 에러" + e.getMessage());
			}
		}

		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("conn 에러" + e.getMessage());								
			}
		}
	}

	/**
	 * 내 기본 그룹 인덱스 가져오기
	 * @param userIndex
	 * 		유저 인덱스
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
			System.out.println("getMyBaseGroup 에러 : " + e.getMessage());
		}finally{
			connClose();
		}
		
		return groupIndex;
	}

	/**
	 * 발송할 문자정보추가 하기
	 * @param list
	 * 	발송 문자정보 리스트
	 * @return
	 * 	발송 대기 추가 갯수
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
						" VALUES (?, ?, ?, ?, ?, ?, ?, 0, ?, ?, ?, '',  ?, now(), '전송중' , ?, ?, ?) ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setLong(1, data.getIndex());				// 고유 시퀸스 번호				
				pstmt.setString(2, data.getId());					// 유저 아이디
				pstmt.setInt(3, data.getUserIndex());			// 유저 인덱스
				pstmt.setString(4, data.getToPhone());			// 받는 전화번호
				pstmt.setString(5, data.getFromPhone());		// 보내는 전화번호				
				pstmt.setString(6, data.getMessage());			// 메세지
				pstmt.setInt(7, list.size());							// 해당 문자 전송 갯수	
				pstmt.setString(8, data.isResreved()?"y":"n");	// 예약여부
				pstmt.setString(9, data.getReserveDate());	// 예약일				
				pstmt.setString(10, data.getCallback());			// 발송 수신 전화번호
				pstmt.setString(11, data.getFlag());				//	전송타입	
				pstmt.setString(12, data.getFile1());				//	파일1
				pstmt.setString(13, data.getFile2());				//	파일2
				pstmt.setString(14, data.getFile3());				//	파일3				
				resultCount +=  pstmt.executeUpdate();
			}
			return resultCount;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("addSmsSendList 에러 : " + e.getMessage());
			return 0;
		}finally{
			connClose();
		}
		
	}

	/**
	 * 발송 내역 갯수 
	 * @param userIndex
	 * 		검색할 유저 인덱스
	 * @param search 
	 * 		검색어
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
			System.out.println("getSendResultCount 에러 : " + e.getMessage());
		}finally{
			connClose();
		}
		return result;
	}

	/**
	 *	 예약 발송갯수 구하기
	 * @param search
	 * 		검색어
	 * @param type
	 * 		검색 종류
	 * @param psCode 
	 * 		경찰서 코드
	 * @return
	 */
	public int getReserveAllListCount(String search, String type, int psCode) {
		int result = 0;
		try {
			conn = dataSource.getConnection();
			
			String sql = " SELECT count(s.f_index) FROM sms_send_info s, user_info u " + 
					" WHERE  u.f_index = s.f_user_index AND " +
					" f_reserved = 'y'  AND f_reserve_date > now() AND ";
			if(type.equalsIgnoreCase("from")){	// 보낸전화번호로 검색
				sql += "  s.f_user_id like ? ";
			}else if(type.equalsIgnoreCase("message")){		// 메세지로 검색
				sql += " s.f_message like ? ";
			}else{			// 받는 전화번호로 검색
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
			System.out.println("getReserveListCount 에러 : " + e.getMessage());
		}finally{
			connClose();
		}
		return result;
	}

	
	/**
	 * 모든 예약 내역 리스트
	 * @param start
	 * @param end
	 * @param search
	 * 		검색어
	 * @param type
	 *		검색 종류
	 * @param psCode 
	 * 		경찰서 코드
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
				// 문자 내역을 담는다.
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
			System.out.println("getReserveList 에러 : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}
	}	
	
	/**
	 * 	전송 내역 삭제
	 * 관리자가 볼수 있도록 f_is_del필드값만 업데이트처리 한다.
	 * @param index
	 * 	메세지 인덱스
	 * @return
	 */
	public boolean delSendMessage(long index) {
		int result = 0;
		try {
			conn = dataSource.getConnection();
			String sql = "UPDATE sms_send_info set f_is_del = 'y'  WHERE f_index = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, index);						// 메세지 인덱스
			// update
			result = pstmt.executeUpdate();
			return result > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("delSendMessage 에러 : " + e.getMessage());
			return false;
		}finally{
			connClose();
		}
	}	
	

	/**
	 *	모든 전송 결과 내역 리스트
	 * @param start
	 * @param end
	 * @param search
	 * 		검색어
	 * @param type
	 * 		검색종류
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
			if(type.equalsIgnoreCase("from")){	// 보낸전화번호로 검색
				sql += "  s.f_user_id like ? ";
			}else if(type.equalsIgnoreCase("message")){		// 메세지로 검색
				sql += " s.f_message like ? ";
			}else{			// 받는 전화번호로 검색
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
				// 문자 내역을 담는다.
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
			System.out.println("getSendList 에러 : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}
	}	

	/**
	 * 유저발송갯수 구하기
	 * @param search
	 * 		검색어
	 * @param type
	 * 		검색종류
	 * @param psCode 
	 * @return
	 */
	public int getSendAllListCount(String search, String type, int psCode) {
		int result = 0;
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT count(s.f_index) FROM sms_send_info s, user_info u " + 
					" WHERE  u.f_index = s.f_user_index AND ";
			if(type.equalsIgnoreCase("from")){	// 보낸전화번호로 검색
				sql += "  s.f_user_id like ? ";
			}else if(type.equalsIgnoreCase("message")){		// 메세지로 검색
				sql += " s.f_message like ? ";
			}else{			// 받는 전화번호로 검색
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
			System.out.println("getSendListCount 에러 : " + e.getMessage());
		}finally{
			connClose();
		}
		return result;
	}
	
	/**
	 * 특정 유저 예약 내역 갯수 
	 * @param userIndex
	 * 		검색할 유저 인덱스
	 * @param search 
	 * 		검색어
	 * @return
	 */
	public int getUserReserveCount(int userIndex, String search, String type) {
		int result = 0;
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT count(*) FROM sms_send_info " +
					" WHERE  f_user_index = ? AND" +
					" f_reserved = 'y' AND f_reserve_date > now() AND ";
			if(type.equalsIgnoreCase("from")){	// 메세지로 검색
				sql += "  f_message like ? ";
			}else if(type.equalsIgnoreCase("message")){		// 받는 전화번호로 검색
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
			System.out.println("getUserReserveCount 에러 : " + e.getMessage());
		}finally{
			connClose();
		}
		return result;
	}	
	


	/**
	 * 선택 유저 예약 내역 리스트
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
			if(type.equalsIgnoreCase("from")){	// 메세지로 검색
				sql += "  f_message like ? ";
			}else if(type.equalsIgnoreCase("message")){		// 받는 전화번호로 검색
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
				// 문자 내역을 담는다.
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
			System.out.println("getReserveResultList 에러 : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}
	}	
	
	/**
	 * 선택 유저 발송 내역 갯수 
	 * @param userIndex
	 * 		검색할 유저 인덱스
	 * @param search 
	 * 		검색어
	 * @return
	 */
	public int getUserSentCount(int userIndex, String search, String type) {
		int result = 0;
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT count(*) FROM sms_send_info " +
					" WHERE  f_user_index = ? AND" +
					" f_send_state <> 0 AND ";
			if(type.equalsIgnoreCase("message")){	// 메세지로 검색
				sql += "  f_message like ? ";
			}else if(type.equalsIgnoreCase("to")){		// 받는 전화번호로 검색
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
			System.out.println("getUserReserveCount 에러 : " + e.getMessage());
		}finally{
			connClose();
		}
		return result;
	}	
	


	/**
	 * 선택 유저 발송 내역 리스트
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
			if(type.equalsIgnoreCase("message")){	// 메세지로 검색
				sql += "  f_message like ? ";
			}else if(type.equalsIgnoreCase("to")){		// 받는 전화번호로 검색
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
				// 문자 내역을 담는다.
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
			System.out.println("getReserveResultList 에러 : " + e.getMessage());
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
				// 인덱스, 등록아이디, 제목, 내용, 그룹인덱스, 그룹명
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
			System.out.println("getMyMessages 에러 : " + e.getMessage());
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
			System.out.println("getReserveListCount 에러 : " + e.getMessage());
		}finally{
			connClose();
		}
		return result;
	}

}
