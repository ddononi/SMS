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
	 * �������װԽ��� �ۼ�
	 * @return
	 */
	public int getNoticeListCount(){
		int count = 0;
		
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement("SELECT count(*) FROM board_qna ");
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getNoticeListCount ���� : " +  e.getMessage());
		}finally{
			ConnClose();
			
		}
		return count;
	}

	/**
	 * �������� ��������
	 * @return
	 */
	public List<BoardBean> getNoticeList(){
		List<BoardBean> list = new ArrayList<BoardBean>();
		try {
			conn = dataSource.getConnection();
			// �ֱ� �������
			pstmt = conn.prepareStatement("SELECT * FROM board ORDER BY f_index desc");
			rs = pstmt.executeQuery();
			
			BoardBean data;
			while(rs.next())	{
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
  			}		
			
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getNoticeList ���� : " + e.getMessage());
			return null;
		}finally{
			ConnClose();
		}
		
	}
	
	/**
	 * �Խ��� �� ���
	 * @return
	 * 	��� ����
	 */
	public boolean insertBoard(){
		int result = 0;
		try {
			String sql = "INSERT INTO board (f_title, f_content, f_notice, f_reg_user, f_) VALUES"
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			BoardBean data;
			while(rs.next())	{
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
  			}		
			
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getNoticeList ���� : " + e.getMessage());
			return null;
		}finally{
			ConnClose();
		}
		
	}	

	/**
	 * ���ҽ� ��ȯ
	 * ��ȯ ������� �ݾ��ش�.
	 */
	private void ConnClose() {
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
