package com.revature.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.model.User;
import com.revature.util.ConnectionUtil;

public class UserDaoImpl implements UserDao {

	@Override
	public List<User> getUsers() {
		// TODO Auto-generated method stub
		List<User> users = new ArrayList<>();
		
		String sql = "SELECT * FROM bankuser";
		
		try( 
			Connection con = ConnectionUtil.getConnection();
				Statement s = con.createStatement();
				ResultSet rs = s.executeQuery(sql);
			){
			while(rs.next()) {
				int userId = rs.getInt("UserID");
				String login = rs.getString("UserName");
				String pass = rs.getString("UserPass");
				users.add(new User(userId, login, pass));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return users;
	}

	@Override
	public User getUserByID(int id) {
		// TODO Auto-generated method stub
		User wanted = new User();
		String sql = "SELECT * FROM bankuser WHERE UserID = " + id;
		
		try(
				Connection con = ConnectionUtil.getConnection();
				Statement s = con.createStatement();
				ResultSet rs = s.executeQuery(sql);
				){
			rs.next();
			wanted.setId(id);
			wanted.setLogin(rs.getString("UserName"));
			wanted.setPassword(rs.getString("UserPass"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return wanted;
	}

	@Override
	public int createUser(User u) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO bankuser(UserName, UserPass) VALUES (?, ?)";
		
		try(
				Connection con = ConnectionUtil.getConnection();
				PreparedStatement ps = con.prepareStatement(sql);
				){
			ps.setString(1, u.getLogin());
			ps.setString(2, u.getPassword());
			ps.execute();
			return ps.getUpdateCount();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public int updateUser(User u) {
		// TODO Auto-generated method stub
		String sql = "UPDATE bankuser SET UserName = ?, UserPass = ? WHERE UserID = ?";
		
		try( 
				Connection con = ConnectionUtil.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)
						){
			ps.setString(1, u.getLogin());
			ps.setString(2, u.getPassword());
			ps.setInt(3, u.getId());
			ps.execute();
			return ps.getUpdateCount();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public int deleteUser(int id) {
		// TODO Auto-generated method stub
		String sql = "{call DELETE_USER (?)}";
		
		try(
				Connection con = ConnectionUtil.getConnection();
				CallableStatement cs = con.prepareCall(sql);){
			cs.setInt(1, id);
			cs.execute();
			return cs.getUpdateCount();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

}
