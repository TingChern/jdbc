package model;

import java.util.Arrays;

import model.dao.CustomerDAOJdbc;

public class CustomerService {
	private model.CustomerDAO CustomerDAO = new CustomerDAOJdbc();

	public static void main(String[] args) {
		CustomerBean bean = new CustomerService().Login("Alex", "A");
		System.out.println(bean);
		
//		boolean changers = new CustomerService().changePassword("Alex", "AA", "A");
//		System.out.println(changers);

	}

	public CustomerBean Login(String username, String password) {

		CustomerBean bean = null;
		bean = CustomerDAO.select(username);
		if (bean != null) {
			if (password != null && password.trim().length() != 0) {
				byte[] pass = password.getBytes();
				byte[] temp = bean.getPassword();
				if (Arrays.equals(pass, temp)) {
					return bean;
				}
			}
		}

		return null;
	}
	
	public boolean changePassword(String username,String oldpassword,String newpassword) {
		CustomerBean bean =this.Login(username, oldpassword);

		if(bean!=null) {
			if(newpassword!=null&&newpassword.trim().length()!=0) {
				byte[] temp=newpassword.getBytes();
				return CustomerDAO.UPDATE(bean.getCustid(), temp, bean.getEmail(), bean.getBirth());
			}
		}
		return false;
	}
}
