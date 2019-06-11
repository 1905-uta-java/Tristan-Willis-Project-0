package com.revature.model;

import java.io.Serializable;

public class Account implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7356758073368256484L;

	private int acct_id;
	private int user_id;
	private int balance;
	private String acct_name;
	
	public Account() {
		
	}
	
	public Account(int acct_id, int user_id, int balance, String acct_name) {
		this.setAcct_id(acct_id);
		this.setUser_id(user_id);
		this.setBalance(balance);
		this.setAcct_name(acct_name);
	}

	public int getAcct_id() {
		return acct_id;
	}

	public void setAcct_id(int acct_id) {
		this.acct_id = acct_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public String getAcct_name() {
		return acct_name;
	}

	public void setAcct_name(String acct_name) {
		this.acct_name = acct_name;
	}
	
}
