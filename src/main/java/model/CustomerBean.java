package model;

import java.util.Arrays;

public class CustomerBean {
	private String custid;
	private byte password[];
	private String email;
	private java.util.Date birth;

	public CustomerBean() {}
	public CustomerBean(String custid,byte password[],String email,java.sql.Date birth){
		this.setCustid(custid);
		this.setPassword(password);
		this.setEmail(email);
		this.setBirth(birth);
		}
	
	@Override
	public String toString() {
		return "CustomerBean [custid=" + custid + ", password=" + Arrays.toString(password) + ", email=" + email
				+ ", birth=" + birth + "]";
	}

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public byte[] getPassword() {
		return password;
	}

	public void setPassword(byte[] password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public java.util.Date getBirth() {
		return birth;
	}

	public void setBirth(java.sql.Date birth) {
		this.birth = birth;
	}

}
