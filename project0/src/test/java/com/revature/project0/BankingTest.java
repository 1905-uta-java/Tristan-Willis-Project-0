package com.revature.project0;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import static org.junit.Assert.assertTrue;

import org.junit.Before;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.revature.model.Account;
import com.revature.model.User;

/**
 * Unit test for simple App.
 */
public class BankingTest 
{
	/*
	 * Init
	 */
	
	@Before
	public void init() {
		Banking.test_init();
	}
	
	/*
	 * Basic User Tests
	 */
	
    @Test
    public void userSetGetID() {
    	User u = new User(1, "lin", "pss");
    	assertEquals(u.getId(), 1);
    	u.setId(2);
        assertEquals(u.getId(), 2);
    }
    
    @Test
    public void userSetGetLogin() {
    	User u = new User(1, "lin", "pss");
    	assertEquals("lin", u.getLogin());
    	u.setLogin("lin2");
    	assertEquals("lin2", u.getLogin());
    }
    
    @Test
    public void userSetGetPass() {
    	User u = new User(1, "lin", "pss");
    	assertEquals("pss", u.getPassword());
    	u.setPassword("pss2");
    	assertEquals("pss2", u.getPassword());
    }
    
    /*
     * Basic Account Tests
     */
    
    @Test
    public void accountSetGetID() {
    	Account a = new Account(1, 1, 100, "acct");
    	assertEquals(1, a.getAcct_id());
    	a.setAcct_id(2);
    	assertEquals(2, a.getAcct_id());
    }
    
    @Test
    public void accountSetGetUserID() {
    	Account a = new Account(1, 1, 100, "acct");
    	assertEquals(1, a.getUser_id());
    	a.setUser_id(2);
    	assertEquals(2, a.getUser_id());
    }
    
    @Test
    public void accountSetGetBalance() {
    	Account a = new Account(1, 1, 100, "acct");
    	assertEquals(100, a.getBalance());
    	a.setBalance(200);
    	assertEquals(200, a.getBalance());
    }
    
    @Test
    public void accountSetGetName() {
    	Account a = new Account(1, 1, 100, "acct");
    	assertEquals("acct", a.getAcct_name());
    	a.setAcct_name("acct2");
    	assertEquals("acct2", a.getAcct_name());
    }
    
    /*
     * Log in tests
     */
    
    @Test
    public void loginSuccessTest() {
    	assertTrue(Banking.log_in("DEF", "PASS2") != -1);
    }
    
    @Test
    public void loginFailTest() {
    	assertFalse(Banking.log_in("DEF", "A") != -1);
    }
    
    /*
     * Create / Delete user tests
     */
    
    @Test
    public void createDeleteUserSuccessTest() {
    	int user_id = -1;
    	assertTrue(Banking.create_user("AZ", "ZA"));
    	assertTrue((user_id = Banking.log_in("AZ", "ZA")) != -1);
    	Banking.delete_user(user_id);
    }
    
    @Test
    public void UserNameTooLongTest() {
    	assertFalse(Banking.create_user("123456789111315171921", "A"));
    }
    
    @Test
    public void PasswordTooLongTest() {
    	assertFalse(Banking.create_user("A", "1234567891113151719212325272931"));
    }
    
    @Test
    public void DeleteNonExistingUserTest() {
    	assertFalse(Banking.delete_user(-1));
    }
}
