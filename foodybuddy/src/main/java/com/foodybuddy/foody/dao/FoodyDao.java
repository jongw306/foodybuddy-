package com.foodybuddy.foody.dao;


import static com.foodybuddy.common.sql.JDBCTemplate.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.foodybuddy.foody.vo.Foody;
import com.foodybuddy.user.vo.User;
import com.foodybuddy.common.sql.Paging;

public class FoodyDao {
  
  public int createBoard(Foody f, Connection conn) {
      PreparedStatement pstmt = null;
      int result = 0;
      try {
          String sql = "INSERT INTO foody_create (foody_title, foody_name, foody_taste, foody_clean, "
                  + "foody_parking, foody_main, foody_address, reg_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
          pstmt = conn.prepareStatement(sql);
          pstmt.setString(1, f.getFoody_title());
          pstmt.setString(2, f.getFoody_name());
          pstmt.setInt(3, f.getFoody_taste());
          pstmt.setInt(4, f.getFoody_clean());
          pstmt.setString(5, f.getFoody_parking());
          pstmt.setString(6, f.getFoody_delivery());
          pstmt.setString(7, f.getFoody_address());
          pstmt.setTimestamp(8, Timestamp.valueOf(f.getReg_date()));
          
          result = pstmt.executeUpdate();
          
      } catch (Exception e) {
          e.printStackTrace();
      } finally {
          close(pstmt); // Close statement
      }
      return result;
  }
  
  
  public int selectBoardCount(Foody option , Connection conn) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT COUNT(*) AS cnt FROM foody_create ";
			if(option.getFoody_title() != null) {
				sql += " WHERE foody_title LIKE CONCAT('%',"+option.getFoody_title()+",'%')";
			}
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt("cnt");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return result;
		
	}
	
	
	public List<Foody> selectBoardList(Foody option,Connection conn){
		List<Foody> list = new ArrayList<Foody>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			// 검색 조건
			// X : SELECT * FROM board
			// O : SELECT * FROM board WHERE board_title LIKE CONCAT('%',board_title,'%')
			String sql = "SELECT * FROM foody_create ORDER BY reg_date DESC";
			if(option.getFoody_title() != null) {
				sql = " SELECT * FROM foody_create WHERE foody_title LIKE CONCAT('%',"+option.getFoody_title()+",'%') "
						+ " ORDER BY reg_date DESC ";
			}
			sql += " LIMIT "+option.getLimitPageNo()+", "+option.getNumPerPage();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Foody resultVo = new Foody(rs.getInt("foody_no"),
						rs.getInt("user_no"),
						rs.getInt("report_no"),
						rs.getString("foody_title"),
						rs.getString("foody_name"),
						rs.getInt("foody_taste"),
						rs.getInt("foody_clean"),
						rs.getString("foody_parking"),
						rs.getString("foody_delivery"),
						rs.getString("foody_main"),
						rs.getTimestamp("reg_date").toLocalDateTime(),
						rs.getTimestamp("mod_date").toLocalDateTime(),
						rs.getString("foody_address"),
						rs.getInt("foody_click"),
						rs.getInt("foody_good"),
						rs.getString("ori_picture"),
						rs.getString("new_picture"));
				
				list.add(resultVo);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return list;
	}

	
	public List<Foody> selectBoardTopList(Foody option, Connection conn) {
	    List<Foody> list = new ArrayList<>();
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        String sql = "SELECT * FROM foody_create WHERE MONTH(reg_date) = ? - 1 ORDER BY foody_good DESC LIMIT 3";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, LocalDateTime.now().getMonthValue()); // 현재 달의 값으로 설정

	        rs = pstmt.executeQuery();
	        while (rs.next()) {
	            Foody resultVo = new Foody(
	                rs.getInt("foody_no"),
	                rs.getInt("user_no"),
	                rs.getInt("report_no"),
	                rs.getString("foody_title"),
	                rs.getString("foody_name"),
	                rs.getInt("foody_taste"),
	                rs.getInt("foody_clean"),
	                rs.getString("foody_parking"),
	                rs.getString("foody_delivery"),
	                rs.getString("foody_main"),
	                rs.getTimestamp("reg_date").toLocalDateTime(),
	                rs.getTimestamp("mod_date").toLocalDateTime(),
	                rs.getString("foody_address"),
	                rs.getInt("foody_click"),
	                rs.getInt("foody_good"),
	                rs.getString("ori_picture"),
	                rs.getString("new_picture")
	            );

	            list.add(resultVo);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        close(rs);
	        close(pstmt);
	    }
	    return list;
	}

	
	
	public List<Foody> viewFoody(int no,Connection conn){
		List<Foody> list = new ArrayList<Foody>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			
			String sql = "SELECT * FROM foody_create WHERE foody_no = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Foody resultVo = new Foody(rs.getInt("foody_no"),
						rs.getInt("user_no"),
						rs.getInt("report_no"),
						rs.getString("foody_title"),
						rs.getString("foody_name"),
						rs.getInt("foody_taste"),
						rs.getInt("foody_clean"),
						rs.getString("foody_parking"),
						rs.getString("foody_delivery"),
						rs.getString("foody_main"),
						rs.getTimestamp("reg_date").toLocalDateTime(),
						rs.getTimestamp("mod_date").toLocalDateTime(),
						rs.getString("foody_address"),
						rs.getInt("foody_click"),
						rs.getInt("foody_good"),
						rs.getString("ori_picture"),
						rs.getString("new_picture"));
				
				list.add(resultVo);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return list;
	}	
}



