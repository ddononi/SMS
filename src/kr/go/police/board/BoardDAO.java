package kr.go.police.board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


/**
 * ��������, �Խ��� ����
 */
public class BoardDAO {
	
	DataSource dataSource;
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs, rs1;
	
	public BoardDAO(){
		try{
			Context initCtx = new InitialContext();
			Context envCtx=(Context)initCtx.lookup("java:comp/env");
			dataSource = (DataSource)envCtx.lookup("jdbc/smsConn");
		}catch(Exception ex){
			System.out.println("DB ���� ���� : " + ex);
			return;
		}
	}

	/**
	 * �Խ��� �ۼ�
	 * @return
	 */
	public int getNoticeListCount(){
		int count = 0;
		
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement("SELECT count(*) FROM board ");
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
			System.out.println("getNoticeList ���� : " + e.getMessage());
		}finally{
			connClose();
		}
		return data;
	}
	
	/**
	 * �Խù� ��� ����
	 * @return
	 */
	public List<BoardBean> getBoardList(int page, int limit){
		List<BoardBean> list = new ArrayList<BoardBean>();
		BoardBean data = null;		
		int startRow = (page -1 ) * 10 +1;		// ���� ��ȣ
		int endRow = startRow + limit -1;		// �� ��ȣ
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM board LIMIT ?, ? ORDER BY f_notice, f_index DESC");
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);			
			rs = pstmt.executeQuery();
			
			while(rs.next())	{
			    data = new BoardBean();	
			    data.setIndex(rs.getInt("f_index"));
				data.setTitle(rs.getString("f_title"));
			    data.setNotice(rs.getBoolean("f_notice"));
			    data.setViewCount(rs.getInt("f_view_count"));
			    data.setParentIndex(rs.getInt("f_parent_index"));
				data.setFilename(rs.getString("f_filename"));
				data. setRegisterName(rs.getString("f_reg_user"));
				data.setRegDate(rs.getString("f_reg_date"));
				data.setModiDate(rs.getString("f_modi_date"));
				data.setRegUserIndex(rs.getInt("f_reg_user_index"));
				
				System.out.println(data.getRegisterName());
				list.add(data);
  			}		
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getBoardList ���� : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}

	}	
	
	/**
	 * �������� ��� ����
	 * @return
	 */
	public List<BoardBean> getNoticeList(){
		List<BoardBean> list = new ArrayList<BoardBean>();
		BoardBean data = null;		

		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM board WHERE f_notice = 'y' ORDER BY f_index DESC");
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
				
				System.out.println(data.getRegisterName());
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
				
				System.out.println(data.getRegisterName());
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
	 * �Խ��� �� ���
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
			pstmt.setString(2, data.getTitle());			
			pstmt.setString(3, data.getContent());	
			pstmt.setBoolean(4, data.isNotice());	
			pstmt.setString(5, data.getRegisterName());	
			pstmt.setInt(6, 0);	
			pstmt.setString(7, data.getRegDate());	
			pstmt.setInt(8, data.getRegUserIndex());	
			pstmt.setString(9, data.getPwd());	
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
			}

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
	 * 	��ȸ�� ������Ʈ
	 * @param index
	 * 		�Խù� �ε���
	 */
	public void updateReadCount(int index){
		String sql = "UPDATE board set f_view_count = f_view_count + 1 WHERE f_index =  + index";
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
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
			pstmt.setString(2, "");								// ��ۿ� ������ ������
			pstmt.setString(3, data.getContent());	
			pstmt.setBoolean(4, false);	
			pstmt.setString(5, data.getRegisterName());	
			pstmt.setString(6, "");								// ����̹Ƿ� ������ ������	
			pstmt.setInt(7, data.getParentIndex());	
			pstmt.setString(8, data.getRegDate());	
			pstmt.setInt(9, data.getRegUserIndex());	
			pstmt.setString(10, data.getPwd());	
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
	 * ���ҽ� ��ȯ
	 * ��ȯ ������� �ݾ��ش�.
	 */
	private void connClose() {
		if(rs != null){
			try{
				rs.close();
			}catch(SQLException e){}
		}
		
		if(pstmt != null){
			try{
				pstmt.close();
			}catch(SQLException e){}
		}
		
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {}
		}
		
	} 
	
}
