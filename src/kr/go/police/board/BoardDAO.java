package kr.go.police.board;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

import kr.go.police.CommonCon;
import kr.go.police.address.AddressBean;
import kr.go.police.aria.Aria;


/**
 * ��������, �Խ��� ����
 */
public class BoardDAO extends CommonCon {
	DataSource dataSource;
	public ResultSet rs;
	public PreparedStatement pstmt;
	public Connection conn;
	
	public BoardDAO(){
		dataSource = getDataSource();
		if(dataSource == null){
			return;
		}
	}

	/**
	 * �������� �ۼ�
	 * @param search 
	 * 	�˻���
	 * @return
	 */
	public int getNoticeListCount(String search){
		int count = 0;
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement("SELECT count(*) FROM board  WHERE " +
					" f_notice = 'y'  AND f_title like ? ");
			pstmt.setString(1, "%" + search + "%");
			rs = pstmt.executeQuery();
			if(rs.next()){
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getNoticeListCount ���� : " +  e.getMessage());
		}finally{
			connClose();
		}
		return count;
	}
	
	/**
	 *  �Խù� �ۼ�
	 * @return
	 */
	public int getBoardCount(String search){
		int count = 0;
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement("SELECT count(*) FROM board  WHERE" +
					" f_notice = 'n'  AND  (f_title like ? OR f_reg_user like ?) "); 
			pstmt.setString(1, "%" + search + "%");
			pstmt.setString(2, "%" + search + "%");				
			rs = pstmt.executeQuery();
			if(rs.next()){
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getBoardCount ���� : " +  e.getMessage());
		}finally{
			connClose();
		}
		return count;
	}	

	/**
	 * �Խù� �ڼ��� ����
	 * @return
	 */
	public BoardBean getDetail(int index){
		BoardBean data = null;		
		try {
			conn = dataSource.getConnection();
			// �ֱ� �������
			pstmt = conn.prepareStatement("SELECT * FROM board WHERE f_index = " + index);
			rs = pstmt.executeQuery();
			
			if(rs.next())	{
			    data = new BoardBean();	
			    data.setIndex(rs.getInt("f_index"));
				data.setTitle(rs.getString("f_title"));
				data.setContent(rs.getString("f_content"));
			    data.setNotice(rs.getBoolean("f_notice"));
			    data.setViewCount(rs.getInt("f_view_count"));
			    data.setParentIndex(rs.getInt("f_parent_index"));
				data.setFilename(rs.getString("f_filename"));
				data. setRegisterName(rs.getString("f_reg_user"));
				data.setRegDate(rs.getString("f_reg_date"));
				data.setModiDate(rs.getString("f_modi_date"));
				data.setRegUserIndex(rs.getInt("f_reg_user_index"));
				System.out.println(data.getRegisterName());
  			}		

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getDetail ���� : " + e.getMessage());
		}finally{
			connClose();
		}
		return data;
	}
	
	
	
	/**
	 * �� �ּҷ� ����Ʈ
	 * @param userIndex
	 * 		���� �ε���
	 * @param groupndex
	 * 		�׷� �ε���
	 * @param start
	 * 		���۹�ȣ
	 * @param end
	 * 		����ȣ
	 * @return
	 */
	public List<AddressBean> getAddressList(int userIndex, int groupIndex, int start, int end) {
		List<AddressBean> list = new ArrayList<AddressBean>();
		AddressBean data = null;		
		try {
			
			Aria aria = Aria.getInstance();				
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM address_book WHERE" +
					" f_user_index = ? AND f_group_index = ? ORDER BY f_index DESC LIMIT ?, ? ");
			pstmt.setInt(1, userIndex);	
			pstmt.setInt(2, groupIndex);
			pstmt.setInt(3, start -1);	
			pstmt.setInt(4, end);						
			rs = pstmt.executeQuery();
			while(rs.next()){
				// ������ �׷�
			    data = new AddressBean();	
			    data.setIndex(rs.getInt("f_index"));
			    data.setPeople(aria.encryptHexStr2DecryptStr(rs.getString("f_people")));
			    data.setPhone(aria.encryptHexStr2DecryptStr(rs.getString("f_phone")));		
			    data.setGroupIndex(rs.getInt("f_group_index"));
				list.add(data);
  			}
			
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getAddressList ���� : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}
	}
	
	/**
	 * �������� ��� ����
	 * @return
	 */
	public List<BoardBean> getNoticeList(int start, int end, String search) {
		List<BoardBean> list = new ArrayList<BoardBean>();
		BoardBean data = null;		
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM board WHERE f_notice = 'y' " +
					" AND f_title like ? ORDER BY f_index DESC LIMIT ?, ? ");
			pstmt.setString(1, "%" + search + "%");
			pstmt.setInt(2, start -1);
			pstmt.setInt(3, end);						
			rs = pstmt.executeQuery();
			
			while(rs.next())	{
			    data = new BoardBean();	
			    data.setIndex(rs.getInt("f_index"));
				data.setTitle(rs.getString("f_title"));
			    data.setViewCount(rs.getInt("f_view_count"));
			    data.setParentIndex(rs.getInt("f_parent_index"));
				data.setNotice(rs.getBoolean("f_notice"));			    
				data.setFilename(rs.getString("f_filename"));
				data. setRegisterName(rs.getString("f_reg_user"));
				data.setRegDate(rs.getString("f_reg_date"));
				data.setModiDate(rs.getString("f_modi_date"));
				data.setRegUserIndex(rs.getInt("f_reg_user_index"));
				list.add(data);
  			}		
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getNoticeList ���� : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}
	}	
	
	/**
	 * �Խù� ��� ����
	 * @return
	 */
	public List<BoardBean> getBoardList(int start, int end, String search) {
		List<BoardBean> list = new ArrayList<BoardBean>();
		BoardBean data = null;		
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM board WHERE " +
					" (f_title like ? OR f_reg_user like ?) AND " +
					"f_notice = 'n' ORDER BY f_index DESC LIMIT ?, ? ");
			pstmt.setString(1, "%" + search + "%");
			pstmt.setString(2, "%" + search + "%");	
			pstmt.setInt(3, start -1);
			pstmt.setInt(4, end);						
			rs = pstmt.executeQuery();
			
			while(rs.next())	{
			    data = new BoardBean();	
			    data.setIndex(rs.getInt("f_index"));
				data.setTitle(rs.getString("f_title"));
			    data.setViewCount(rs.getInt("f_view_count"));
			    data.setParentIndex(rs.getInt("f_parent_index"));
				data.setFilename(rs.getString("f_filename"));
				data. setRegisterName(rs.getString("f_reg_user"));
				data.setRegDate(rs.getString("f_reg_date"));
				data.setModiDate(rs.getString("f_modi_date"));
				data.setRegUserIndex(rs.getInt("f_reg_user_index"));
				list.add(data);
  			}		
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getNoticeList ���� : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}
	}	
	
	/**
	 * �ֱ� �������� ��� ����
	 * @return
	 */
	public List<BoardBean> getRecentNoticeList(int limit){
		List<BoardBean> list = new ArrayList<BoardBean>();
		BoardBean data = null;		
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM board WHERE f_notice = 'y' ORDER BY f_index DESC LIMIT ? ");
			pstmt.setInt(1, limit);
			rs = pstmt.executeQuery();
			while(rs.next())	{
			    data = new BoardBean();	
			    data.setIndex(rs.getInt("f_index"));
				data.setTitle(rs.getString("f_title"));
			    data.setViewCount(rs.getInt("f_view_count"));
			    data.setParentIndex(rs.getInt("f_parent_index"));
				data.setNotice(rs.getBoolean("f_notice"));			    
				data.setFilename(rs.getString("f_filename"));
				data. setRegisterName(rs.getString("f_reg_user"));
				data.setRegDate(rs.getString("f_reg_date"));
				data.setModiDate(rs.getString("f_modi_date"));
				data.setRegUserIndex(rs.getInt("f_reg_user_index"));
				list.add(data);
  			}		
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getRecentNoticeList ���� : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}
	}		

	/**
	 *	�Խù� ��� ��� ����
	 * @param parentIndex
	 * 	�θ� �Խù� �ε���
	 * @return
	 */
	public List<BoardBean> getReplyList(int parentIndex){
		List<BoardBean> list = new ArrayList<BoardBean>();
		BoardBean data = null;		
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM board WHERE f_parent_index = ? ORDER BY f_index DESC");
			pstmt.setInt(1, parentIndex);
			rs = pstmt.executeQuery();
			
			while(rs.next())	{
			    data = new BoardBean();	
			    data.setIndex(rs.getInt("f_index"));
				data.setContent(rs.getString("f_content"));
			    data.setParentIndex(rs.getInt("f_parent_index"));
				data. setRegisterName(rs.getString("f_reg_user"));
				data.setRegDate(rs.getString("f_reg_date"));
				data.setRegUserIndex(rs.getInt("f_reg_user_index"));
				list.add(data);
  			}		
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getNoticeList ���� : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}

	}		
	
	/**
	 * ���� �Խ��� �� ���
	 * @return
	 * 	��� ����
	 */
	public boolean insertBoard(BoardBean data){
		int result = 0;
		
		try {
			conn = dataSource.getConnection();
			String sql = "INSERT INTO board ( f_title, f_content, f_notice, f_reg_user, f_filename, f_parent_index, " +
					"f_reg_date, f_reg_user_index, f_password) VALUES (?, ?, ?, ?, ?, ?, now(), ?, password(?) )";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, data.getTitle());			
			pstmt.setString(2, data.getContent());	
			pstmt.setString(3, data.isNotice()?"y":"n");	
			pstmt.setString(4, data.getRegisterName());	
			pstmt.setString(5, data.getFilename());				
			pstmt.setInt(6, 0);	
			pstmt.setInt(7, data.getRegUserIndex());	
			pstmt.setString(8, data.getPwd());	
			// update
			result = pstmt.executeUpdate();
			return result > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("insertBoard ���� : " + e.getMessage());
			return false;
		}finally{
			connClose();
		}
	}
	
	/**
	 * ���� or �������� �Խù� ����
	 * @return
	 * 	��� ����
	 */
	public boolean modifyBoard(BoardBean data){
		int result = 0;
		try {
			conn = dataSource.getConnection();
			String sql = "UPDATE board SET f_title = ?, f_content = ?, f_notice = ?, " +
					" f_password = ?, f_modi_date = now(), f_filename = ? WHERE f_index = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, data.getTitle());			
			pstmt.setString(2, data.getContent());	
			pstmt.setString(3, data.isNotice()?"y":"n");	
			pstmt.setString(4, data.getPwd());
			pstmt.setString(5, data.getFilename());			
			pstmt.setInt(6, data.getIndex());	
			// update
			result = pstmt.executeUpdate();
			return result > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("modifyBoard ���� : " + e.getMessage());
			return false;
		}finally{
			connClose();
		}
	}	
	
	/**
	 * �Խ��� �� ����
	 * @return
	 * 	���� ����
	 */
	public boolean deleteBoard(int index, String password){
		int result = 0;
		
		try {
			conn = dataSource.getConnection();
			// �� ����
			String sql = "DELETE  FROM board WHERE  f_index =  ? AND f_pwd = password(?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, index);
			result = pstmt.executeUpdate();			
			
			// ������ �Ǿ�����  �θ���ϰ�� ����� ������ ��۵� ����
			if(result > 0){
				sql = "DELETE  FROM board WHERE  f_parent_index = ? ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, index);
				pstmt.setString(2, password);			
				pstmt.executeUpdate();
				
				// ��ȸ�� ���̺����� �ش� �Խù� �� ����
				sql = "DELETE  FROM view_check WHERE  f_board_index = ? ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, index);
				pstmt.executeUpdate();				
			}

			result = pstmt.executeUpdate();
			return result > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("deleteBoard ���� : " + e.getMessage());
			return false;
		}finally{
			connClose();
		}
	}	
	
	/**
	 * �Խ��� �� ����
	 * @return
	 * 	���� ����
	 */
	public boolean deleteBoard(int index){
		int result = 0;
		
		try {
			conn = dataSource.getConnection();
			// �� ����
			String sql = "DELETE  FROM board WHERE  f_index =  ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, index);
			result = pstmt.executeUpdate();			
			
			// ������ �Ǿ�����  �θ���ϰ�� ����� ������ ��۵� ����
			if(result > 0){
				sql = "DELETE  FROM board WHERE  f_parent_index = ? ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, index);
				pstmt.executeUpdate();
				
				// ��ȸ�� ���̺����� �ش� �Խù� �� ����
				sql = "DELETE  FROM view_check WHERE  f_board_index = ? ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, index);
				pstmt.executeUpdate();				
			}

			return result > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("deleteBoard ���� : " + e.getMessage());
			return false;
		}finally{
			connClose();
		}
	}		

	/**
	 * 	��ȸ�� ������Ʈ
	 * @param index
	 * 		�Խù� �ε���
	 */
	public void updateReadCount(int boardIndex, int userIndex){
		//  ���� ������ ��ȸ�� �ߴ��� üũ�Ѵ�.
		String sql = "SELECT count(*) as cnt FROM view_check WHERE f_board_index = ? AND f_user_index = ? ";
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardIndex);
			pstmt.setInt(2, userIndex);
			rs = pstmt.executeQuery();
			rs.next();
			if(rs.getInt("cnt") > 0){
				return;
			}
			// ��ȸ ���̺� ���			
			sql = "INSERT INTO view_check(f_board_index, f_user_index) VALUES (? , ?) ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardIndex);
			pstmt.setInt(2, userIndex);					
			pstmt.executeUpdate();		
			// ��ȸ�� ����			
			sql = "UPDATE board set f_view_count = f_view_count + 1 WHERE f_index = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardIndex);			
			pstmt.executeUpdate();			
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("updateReadCount ���� : " + e.getMessage());			
		}finally{
			connClose();
		}
	}
	
	/**
	 * �۾��� üũ
	 * @return
	 */
	public boolean checkWriteUser(int index, String regUser){
		String sql = "SELECT count(*) FROM board WHERE f_index = ? AND f_reg_user = ?";
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, index);
			pstmt.setString(2, regUser);
			rs = pstmt.executeQuery();			
			rs.next();
			if(rs.getInt(1) == 1){
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("updateReadCount ���� : " + e.getMessage());		
		}finally{
			connClose();
		}
		
		return false;
	}

	/**
	 *  ��� �ޱ�
	 * @param data
	 * @param parentIndex
	 * 	�θ� �Խù� �ε���
	 * @return
	 * 	��� ����
	 */
	public boolean replyBoard(BoardBean data, int parentIndex){
		int result = 0;
		
		try {
			conn = dataSource.getConnection();
			String sql = "INSERT INTO board ( f_title, f_content, f_notice, f_reg_user, f_filename, f_parent_index, " +
					"f_reg_date, f_reg_user_index, f_password) VALUES (?, ?, ?, ?, ?, ?, now(), ?, password(?) )";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "");								// ��ۿ� ������ ������
			pstmt.setString(2, data.getContent());	
			pstmt.setBoolean(3, false);	
			pstmt.setString(4, data.getRegisterName());	
			pstmt.setString(5, "");								// ����̹Ƿ� ������ ������	
			pstmt.setInt(6, data.getParentIndex());	
			pstmt.setInt(7, data.getRegUserIndex());	
			pstmt.setString(8, "");	
			// update
			result = pstmt.executeUpdate();
			return result > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("insertBoard ���� : " + e.getMessage());
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
			}
		}

		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
			}
		}

		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
	}		


	
}
