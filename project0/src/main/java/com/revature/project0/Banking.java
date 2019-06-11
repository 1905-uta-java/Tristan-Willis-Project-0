package com.revature.project0;


import java.util.List;
import java.util.Scanner;

import com.revature.dao.AccountDao;
import com.revature.dao.AccountDaoImpl;
import com.revature.dao.UserDao;
import com.revature.dao.UserDaoImpl;
import com.revature.model.Account;
import com.revature.model.User;

public class Banking 
{
	private static Scanner scan;
	private static UserDao uu;
	private static AccountDao aa;
    public static void main( String[] args )
    {
    	uu = new UserDaoImpl();
    	aa = new AccountDaoImpl();
    	scan = new Scanner(System.in);
    	boolean cont = false;
    	boolean correct = false;
    	String input = "";
    	String input2 = "";
		ask_create_user();
    	int lid = -1;
    	while(!cont) {
    		System.out.println("--------------------------");
    		System.out.println("Please enter your username: ");
    		input = scan.next();
    		System.out.println("Please enter the password for "+ input);
    		System.out.println("Or enter q to quit: ");
    		input2 = scan.next();
    		if((lid = log_in(input, input2)) != -1) {
    			cont = true;
    			correct = true;
    		}
    		if(input2.equals("q")) {
    			cont = true;
    		}
    		if(!cont) {
    			System.out.println("Incorrect username/password.");
    		}
    	}
    	if(correct) {
    		logged_in(lid);
    	}
    	else {
    		System.out.println("Bye.");
    	}
    }
    
    public static void test_init() {
    	uu = new UserDaoImpl();
    	aa = new AccountDaoImpl();
    }
    
    public static void ask_create_user() {
    	boolean create = true;
    	String input;
    	String input2;
    	while(create) {
    		System.out.println("--------------------------");
			System.out.println("Welcome! Are you a new user? (yes/no)");
			input = scan.next();
			if(input.equals("yes")) {
				System.out.println("Please enter the username you wish to use: ");
				input = scan.next();
				System.out.println("Please enter the password you wish to use: ");
				input2 = scan.next();
				create = !create_user(input, input2);
			}
			if(input.equals("no")) {
				create = false;
			}
			if(create) {
				System.out.println("Only enter yes or no");
			}
		}
    }
    
    public static boolean create_user(String username, String password) {
    	if(username.length() > 20 || password.length() > 30) {
    		System.out.println("Username must be 20 or less characters and password must be 30 or less characters.");
    		return false;
    	}
    	User n = new User(0, username, password);
		if(uu.createUser(n) == 1) {
			System.out.println("Created account successfully!");
			return true;
		}
		else
			System.out.println("Failed to create account.");
		return false;
    }
    
    public static boolean delete_user(int user_id) {
    	int del = uu.deleteUser(user_id);
    	System.out.println("del: " + del);
    	return (del != 1);
    }
    
    public static int log_in(String username, String password) {
    	List<User> users = uu.getUsers();
    	for(User u : users) {
			if(username.equals(u.getLogin()) && password.equals(u.getPassword())) {
				return u.getId();
			}
		}
    	return -1;
    }
    
    public static void logged_in(int user_log) {
    	boolean quit = false;
    	String input = "";
    	List<Account> accts;
    	while(!quit) {
    		accts = aa.getAccountByUserID(user_log);

    		System.out.println("--------------------------");
    		System.out.println("Please select an account.");
    		for(Account acct : accts) {
    			System.out.println(acct.getAcct_id() + ": " + acct.getAcct_name());
    		}
    		System.out.println("Or select 0 to create a new account.");
    		System.out.println("Or select -1 to log out.");
    		
    		input = scan.next();
    		if(input.equals("-1")){
    			System.out.println("Bye.");
    			return;
    		}
    		if(input.equals("0")) {
    			String name;
    	    	System.out.println("Please enter a name for this account. (30 character max)");
    	    	name = scan.next();
    			create_acct(user_log, name);
    		}
    		else {
    			try {
    				int acctid = Integer.parseInt(input);
    				boolean success = false;
    				for(Account acct : accts) {
    					if(acctid == acct.getAcct_id()) {
    						selected_acct(user_log, acctid);
    						success = true;
    						break;
    					}
    				}
    				if(!success) {
    					System.out.println("Enter a valid account id for an account you own.");
    				}
    			}
    			catch (NumberFormatException e) {
    				System.out.println("Enter a valid account id for an account you own.");
    			}
    		}
    	}
    }
    
    public static boolean create_acct(int user_log, String name) {
    	if(name.length() > 30) {
    		System.out.println("Must be 30 characters or less.");
    	}
    	else {
    		if(aa.createAccount(new Account(0, user_log, 100, name)) != -1) {
    			System.out.println("Successfully created account!");
    			return true;
    		}
    		else
    			System.out.println("Failed to create account.");
    	}
    	return false;
    }
    
    public static void selected_acct(int user_log, int acct_num) {
    	Account acct;
    	List<Account> connected_accts;
    	while(true) {
    		acct = aa.getAccountByID(acct_num);
	    	String input;
    		System.out.println("--------------------------");
	    	System.out.println("Your current balance is $" + acct.getBalance());
	    	System.out.println("What would you like to do?");
	    	System.out.println("0: Change account / Log out.");
	    	System.out.println("1: Deposit money.");
	    	System.out.println("2: Withdraw money.");
	    	System.out.println("3: Transfer money.");
	    	System.out.println("4: Delete account.");
	    	input = scan.next();
	    	switch(input) {
	    	case "0":
	    		return;
	    		
	    	case "1":
	    		System.out.println("How much would you like to deposit?");
	    		input = scan.next();
	    		try {
    				int ammt = Integer.parseInt(input);
    				deposit(acct, ammt);
    			}
    			catch (NumberFormatException e) {
    				System.out.println("Enter a valid amount.");
    			}
	    		break;
	    		
	    	case "2":
	    		System.out.println("How much would you like to withdraw?");
	    		input = scan.next();
	    		try {
    				int ammt = Integer.parseInt(input);
    				withdraw(acct, ammt);
    			}
    			catch (NumberFormatException e) {
    				System.out.println("Enter a valid amount.");
    			}
	    		break;
	    		
	    	case "3":
	    		System.out.println("Which account would you like to transfer money to.");
	    		connected_accts = aa.getAccountByUserID(user_log);
	    		for(Account cnct : connected_accts) {
	    			System.out.println(cnct.getAcct_id() + ": " + cnct.getAcct_name());
	    		}
	    		input = scan.next();
	    		int acctid = -1;
	    		boolean success = false;
	    		try {
    				acctid = Integer.parseInt(input);
    				
    				for(Account cnct : connected_accts) {
    					if(acctid == cnct.getAcct_id()) {
    						success = true;
    						break;
    					}
    				}
    			}
    			catch (NumberFormatException e) {
    				System.out.println("Enter a valid account id for an account you own.");
    			}

				if(!success) {
					System.out.println("Enter a valid account id for an account you own.");
				}
				else {
					System.out.println("How much would you like to transfer?");
					Account other = aa.getAccountByID(acctid);
					String input2 = scan.next();
					try {
	    				int ammt = Integer.parseInt(input2);
	    				transfer(acct, other, ammt);
	    			}
	    			catch (NumberFormatException e) {
	    				System.out.println("Enter a valid amount.");
	    			}
				}
	    		break;
	    		
	    	case "4":
	    		System.out.println("ARE YOU SURE? Type " + acct.getAcct_name() + " to confirm.");
	    		input = scan.next();
	    		if(input.equals(acct.getAcct_name())) {
	    			if(deleteAccount(acct_num))
	    				return;
	    		}
	    		break;
	    	default:
	    		System.out.println("Select one of the given options.");
	    		break;
	    	}
    	}
    }
    
    public static boolean deposit(Account acct, int ammt) {
    	acct.setBalance(acct.getBalance() + ammt);
		if(aa.updateAccount(acct) == 1) {
			System.out.println("Deposit successful!");
			return true;
		}
		else
			System.out.println("Failed to deposit.");
		return false;
    }
    
    public static boolean withdraw(Account acct, int ammt) {
    	if(acct.getBalance() - ammt > 0) {
			acct.setBalance(acct.getBalance() - ammt);
			if(aa.updateAccount(acct) == 1) {
				System.out.println("Withdraw successful!");
				return true;
			}
			else
				System.out.println("Withdraw failed.");
		}
		else {
			System.out.println("Balance cannot go negative.");
		}
    	return false;
    }
    
    public static boolean transfer(Account acct, Account other, int ammt) {
    	if(acct.getBalance() - ammt > 0) {
			acct.setBalance(acct.getBalance() - ammt);
			other.setBalance(other.getBalance() + ammt);
			if(aa.updateAccount(acct) == 1)
				if(aa.updateAccount(other) == 1) {
					System.out.println("Transfer successful!");
					return true;
				}
				else
					System.out.println("Transfer failed.");
			else
				System.out.println("Transfer Failed.");
		}
		else {
			System.out.println("Balance cannot go negative.");
		}
    	return false;
    }
    
    public static boolean deleteAccount(int acct_num) {
    	if(aa.deleteAccount(acct_num) == 1) {
			System.out.println("Account deleted.");
			System.out.println("Returning to account selection.");
			return true;
		}
		else {
			System.out.println("Failed to delete account.");
		}
    	return false;
    }
}
