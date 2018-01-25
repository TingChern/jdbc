package model;

import java.sql.SQLException;
import java.util.List;

public interface ProductDAO {

	ProductBean select(int id);

	List<ProductBean> select() throws SQLException;

	ProductBean insert(ProductBean bean);

	ProductBean update(String name, double price, java.util.Date make, int expire, int id);

	boolean delete(int id);

}