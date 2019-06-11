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
    
    /*
     * Create / Delete Account tests
     */
    
    @Test
    public void createDeleteAccountSuccessTest() {
    	int acct_id = -1;
    	assertTrue(Banking.create_acct(1, "Test"));
    	assertTrue((acct_id = Banking.test_get_account_by_name_and_id(1, "Test")) != -1);
    	assertTrue(Banking.deleteAccount(acct_id));
    }
    
    @Test
    public void userNonExistingTest() {
    	assertFalse(Banking.create_acct(-1, "Fail"));
    }
    
    @Test
    public void accountNameTooLongTest() {
    	assertFalse(Banking.create_acct(1, "1234567891113151719212325272931"));
    }
    
    /*
     * Deposit/Withdraw/Transfer/Delete Tests
     */
    
    @Test
    public void successfullDepositWithdrawTransferDeleteTest() {
    	int user_id = 0;
    	int acct_id = 0;
    	int other_acct = 0;
    	assertTrue(Banking.create_user("Test", "Test"));
    	assertTrue((user_id = Banking.log_in("Test", "Test")) != -1);
    	assertTrue(Banking.create_acct(user_id, "Test"));
    	assertTrue((acct_id = Banking.test_get_account_by_name_and_id(user_id, "Test")) != -1);
    	assertTrue(Banking.create_acct(user_id, "Test2"));
    	assertTrue((other_acct = Banking.test_get_account_by_name_and_id(user_id, "Test2")) != -1);
    	Account a = new Account(acct_id, user_id, 100, "Test");
    	Account o = new Account(other_acct, user_id, 100, "Test2");
    	assertTrue(Banking.deposit(a, 500));
    	assertTrue(Banking.withdraw(a, 250));
    	assertTrue(Banking.transfer(a, o, 250));
    	assertTrue(Banking.deleteAccount(other_acct));
    	Banking.delete_user(user_id);
    }
    
    @Test
    public void withdrawIntoNegatives() {
    	Account a = new Account(1, 1, 100, "unnamed");
    	assertFalse(Banking.withdraw(a, 500));
    }
    
    @Test
    public void transferIntoNegatives() {
    	Account a = new Account(1, 1, 100, "unnamed");
    	Account o = new Account(1, 1, 100, "unnamed");
    	assertFalse(Banking.transfer(a, o, 500));
    }
    
    @Test
    public void deleteNonExistingAccountTest() {
    	assertFalse(Banking.deleteAccount(-1));
    }
}
