package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.CustomerBean;
import model.CustomerDAO;

public class CustomerDAOJdbc implements CustomerDAO {

	private final String URL = "jdbc:sqlserver://localhost:1433;databaseName=java";
	private final String USERNAME = "sa";
	private final String PASSWORD = "sa123456";

	public static void main(String[] args) {
		// 測試程式
		System.out.println(new CustomerDAOJdbc().select("Alex"));
		System.out.println(
		new CustomerDAOJdbc().UPDATE("Ellen", "E".getBytes(), "ny88619@gmail.com", new java.util.Date(0)));
	}

	private static final String SELECT_BY_CUSTID = "SELECT * FROM customer where custid=?";

	@Override
	public CustomerBean select(String custid) {
		CustomerBean result = null;
		ResultSet rs = null;
		try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement pstmt = conn.prepareStatement(SELECT_BY_CUSTID);) {
			pstmt.setString(1, custid);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = new CustomerBean();
				result.setCustid(rs.getString("custid"));
				result.setPassword(rs.getBytes("password"));
				result.setEmail(rs.getString("email"));
				result.setBirth(rs.getDate("birth"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	private static final String UPDATE = "UPDATE customer set password=?,email=?,birth=? where custid=?";

	@Override
	public boolean UPDATE(String custid, byte password[], String email, java.util.Date birth) {

		int update;
		PreparedStatement pstmt = null;
		try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);) {
			pstmt = conn.prepareStatement(UPDATE);
			pstmt.setBytes(1, password);
			pstmt.setString(2, email);
			pstmt.setDate(3, new java.sql.Date(birth.getTime()));
			pstmt.setString(4, custid);
			update = pstmt.executeUpdate();
			if (update == 1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
			}
		}
		return false;
	}

}
