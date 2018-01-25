package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.ProductBean;
import model.ProductDAO;

public class ProductDAOJdbc implements ProductDAO {
	private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=java";
	private static final String USERNAME = "sa";
	private static final String PASSWORD = "sa123456";

	public static void main(String[] args) throws SQLException, ParseException {
		model.ProductDAO bean = new ProductDAOJdbc();
		// 測試select bt id
		// System.out.println(new ProductDAOJdbc().select(1));

		// 測試select all
		// List<ProductBean> list = new ProductDAOJdbc().select();
		// Iterator<ProductBean> product = list.iterator();
		// while (product.hasNext()) {
		// System.out.println(product.next());
		// }

		// 測試insert
		// ProductBean bean = new ProductBean(11,"Coca Cola",20.0,new
		// SimpleDateFormat("yyyy-MM-dd").parse("2017-01-11"),365);
		// ProductBean result=new ProductDAOJdbc().insert(bean);
		// if(result!=null) {
		// System.out.println(result);
		// }

		// 測試update
//		ProductBean rsbean = bean.update("Milk", 15.5, new SimpleDateFormat("yyyy-MM-dd").parse("2017-01-12"), 10, 11);
//		System.out.println(rsbean);
		//測試delete
		System.out.println(bean.delete(11));
	}

	private static final String SELECT_BY_ID = "select * from product where id=?";

	@Override
	public ProductBean select(int id)  {
		ProductBean result = null;
		if (id!=0) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				pstmt = conn.prepareStatement(SELECT_BY_ID);
				pstmt.setInt(1, id);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					result = new ProductBean(rs.getInt("id"), rs.getString("name"), rs.getDouble("price"),
							rs.getDate("make"), rs.getInt("expire"));
				}
			} catch (SQLException e) {
			} finally {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} 
		}
		return result;
	}

	private static final String SELECT_ALL = "select * from product";

	@Override
	public List<ProductBean> select() throws SQLException {
		List<ProductBean> result = new ArrayList<ProductBean>();
		ResultSet rs = null;
		try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL);) {
			rs = pstmt.executeQuery();
			while (rs.next()) {
				result.add(new ProductBean(rs.getInt("id"), rs.getString("name"), rs.getDouble("price"),
						rs.getDate("make"), rs.getInt("expire")));
			}

		} catch (SQLException e) {
		} finally {
			if (rs != null)
				rs.close();
		}
		return result;
	}

	private static final String INSERT = "insert into product (id, name, price, make, expire) values (?, ?, ?, ?, ?)";

	@Override
	public ProductBean insert(ProductBean bean) {
		ProductBean result = null;
		if (bean!=null) {
			try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
					PreparedStatement pstmt = conn.prepareStatement(INSERT);) {
				pstmt.setInt(1, bean.getId());
				pstmt.setString(2, bean.getName());
				pstmt.setDouble(3, bean.getPrice());
				pstmt.setDate(4, new java.sql.Date(bean.getMake().getTime()));
				pstmt.setInt(5, bean.getExpire());
				int success = pstmt.executeUpdate();
				if (success == 1) {
					result = bean;
					return result;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		}
		return result;
	}

	private static final String UPDATE = "update product set name=?, price=?, make=?, expire=? where id=?";

	@Override
	public ProductBean update(String name, double price, java.util.Date make, int expire, int id) {
		ProductBean result = null;
		if (name!=null) {
			try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
					PreparedStatement pstmt = conn.prepareStatement(UPDATE);) {
				pstmt.setString(1, name);
				pstmt.setDouble(2, price);
				pstmt.setDate(3, new java.sql.Date(make.getTime()));
				pstmt.setInt(4, expire);
				pstmt.setInt(5, id);
				int success = pstmt.executeUpdate();
				if (success == 1) {
					result = new ProductBean(id, name, price, make, expire);
					return result;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		}
		return result;
	}

	private static final String DELETE = "delete from product where id=?";
	@Override
	public boolean delete(int id) {
		try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement pstmt = conn.prepareStatement(DELETE);) {
			pstmt.setInt(1,id);
			int delsuccess=pstmt.executeUpdate();
			if(delsuccess==1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
