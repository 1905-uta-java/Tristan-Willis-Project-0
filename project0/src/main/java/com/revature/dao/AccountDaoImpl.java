package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.model.Account;
import com.revature.util.ConnectionUtil;

public class AccountDaoImpl implements AccountDao {

	@Override
	public List<Account> getAccounts() {
		List<Account> accounts = new ArrayList<>();
		
		String sql = "SELECT * FROM bankaccount";
		
		try( 
			Connection con = ConnectionUtil.getConnection();
				Statement s = con.createStatement();
				ResultSet rs = s.executeQuery(sql);
			){
			while(rs.next()) {
				int accountid = rs.getInt("AccountID");
				int userId = rs.getInt("UserID");
				int balance = rs.getInt("Accountbalance");
				String acct_name = rs.getString("AccountName");
				accounts.add(new Account(accountid, userId, balance, acct_name));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return accounts;
	}

	@Override
	public List<Account> getAccountByUserID(int id) {
		List<Account> accounts = new ArrayList<>();
		
		String sql = "SELECT * FROM bankaccount WHERE UserID = " + id;
		
		try( 
			Connection con = ConnectionUtil.getConnection();
				Statement s = con.createStatement();
				ResultSet rs = s.executeQuery(sql);
			){
			while(rs.next()) {
				int accountid = rs.getInt("AccountID");
				int userId = rs.getInt("UserID");
				int balance = rs.getInt("Accountbalance");
				String acct_name = rs.getString("AccountName");
				accounts.add(new Account(accountid, userId, balance, acct_name));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return accounts;
	}
	
	@Override
	public Account getAccountByID(int id) {
		Account wanted = new Account();
		String sql = "SELECT * FROM bankaccount WHERE AccountID = " + id;
		
		try(
				Connection con = ConnectionUtil.getConnection();
				Statement s = con.createStatement();
				ResultSet rs = s.executeQuery(sql);
				){
			rs.next();
			wanted.setAcct_id(id);
			wanted.setUser_id(rs.getInt("UserID"));
			wanted.setBalance(rs.getInt("Accountbalance"));
			wanted.setAcct_name(rs.getString("AccountName"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return wanted;
	}

	@Override
	public int createAccount(Account a) {
		String sql = "INSERT INTO bankaccount(UserID, AccountName) VALUES (?, ?)";
		
		try(
				Connection con = ConnectionUtil.getConnection();
				PreparedStatement ps = con.prepareStatement(sql);
				){
			ps.setInt(1, a.getUser_id());
			ps.setString(2, a.getAcct_name());
			ps.execute();
			return ps.getUpdateCount();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public int updateAccount(Account a) {
		String sql = "UPDATE bankaccount SET UserID = ?, AccountBalance = ?, AccountName = ? WHERE AccountID = ?";
		
		try( 
				Connection con = ConnectionUtil.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)
						){
			ps.setInt(1, a.getUser_id());
			ps.setInt(2, a.getBalance());
			ps.setString(3, a.getAcct_name());
			ps.setInt(4, a.getAcct_id());
			ps.execute();
			return ps.getUpdateCount();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public int deleteAccount(int id) {
		String sql = "DELETE FROM bankaccount WHERE AccountID = " + id;
		
		try(
				Connection con = ConnectionUtil.getConnection();
				Statement s = con.createStatement()){
			s.execute(sql);
			return s.getUpdateCount();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

}
