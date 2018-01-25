package model;

public interface CustomerDAO {

	CustomerBean select(String custid);

	boolean UPDATE(String custid, byte password[], String email, java.util.Date birth);

}