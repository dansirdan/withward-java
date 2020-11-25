package com.withward.repository;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import com.withward.model.User;
import com.withward.util.JDBC;
import com.withward.util.SHA;

public class UserDAO {
	private SHA sha = new SHA();
	public ArrayList<User> getAll() {

		ArrayList<User> users = new ArrayList<User>();
		String sql = "SELECT * " + "FROM users";

		try (Connection connection = JDBC.getConnection()) {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Integer id = rs.getInt("user_id");
				String username = rs.getString("username");
				String photo = rs.getString("user_photo");
				User user = new User(id, username, photo);
				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return users;
	}
	
	public boolean authenticateUser(String username, String password) {
		String hashedPassword;
		byte[] salt;
		String sql = "SELECT user_salt, user_password from users where username = ?";
		
		try(Connection connection = JDBC.getConnection()){
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, username);
			ResultSet resultSet = pstmt.executeQuery();
			resultSet.next();
			salt = resultSet.getBytes("user_salt");
			hashedPassword = resultSet.getString("user_password");
			
			if(hashedPassword.equals(sha.hashingMethod(password, salt))) {
				return true;
			} else {
				return false;
			}
			
			
		} catch ( SQLException ex) {
			return false;
		}
	}
	
//	public ArrayList<User> getAllWithQuery(String column, String value) {
//
//		ArrayList<User> users = new ArrayList<User>();
//		String sql = "SELECT * " + "FROM user"
//				+ " WHERE ? = ?";
//
//		try (Connection connection = JDBC.getConnection()) {
//			PreparedStatement pstmt = connection.prepareStatement(sql);
//			pstmt.setString(1, column);
//			pstmt.setString()
//			ResultSet rs = stmt.executeQuery(sql);
//			while (rs.next()) {
//				Integer id = rs.getInt("id");
//				String username = rs.getString("username");
//				String email = rs.getString("email");
//				String password = rs.getString("password");
//				String homeLocation = rs.getString("homeLocation");
//				User user = new User(id, username, email, password, homeLocation);
//				users.add(user);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//			System.err.println(e.getClass().getName() + ": " + e.getMessage());
//			System.exit(0);
//		}
//		return users;
//	}
	

	public User getUser(Integer userId) {

		User user = null;
		String sql = "SELECT * " + "FROM users " + "WHERE users.user_id = ?";
		try (Connection connection = JDBC.getConnection()) {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, userId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Integer id = rs.getInt("user_id");
				String username = rs.getString("username");
				String photo = rs.getString("user_photo");
				user = new User(id, username, photo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return user;
	}
	
	public User insertUser(User user) {
		String sql = "INSERT INTO users "
				+ "(username, user_email, user_salt, user_password, user_photo) " + "VALUES "
				+ "(?,?,?,?,?)";

		byte[] salt = sha.getSalt();
		String hashedPassword = sha.hashingMethod(user.getPassword(), salt);
		
		try (Connection connection = JDBC.getConnection()) {
			connection.setAutoCommit(false);
			PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getEmail());
			pstmt.setBytes(3, salt);
			pstmt.setString(4, hashedPassword);
			pstmt.setString(5, user.getPhoto());

			if (pstmt.executeUpdate() != 1) {
				throw new SQLException("Inserting user failed, no rows were affected");
			}

			int autoId = 0;
			ResultSet generatedKeys = pstmt.getGeneratedKeys();
			if (generatedKeys.next()) {
				autoId = generatedKeys.getInt(1);
			} else {
				throw new SQLException("Inserting user failed, no ID generated.");
			}

			connection.commit();
			return new User(autoId, user.getUsername(),user.getEmail(),user.getPassword(),user.getPhoto());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public User updateUser(User user) {
		String sql = "UPDATE users " 
				+ "SET username = ?, " 
				+ "user_email = ?, "
				+ "user_password = ?, " 
				+ "user_photo = ? " 
				+ "WHERE user_id = ?";
		
		try (Connection connection = JDBC.getConnection()) {
			connection.setAutoCommit(false);
			PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getEmail());
			pstmt.setString(3, user.getPassword());
			pstmt.setString(4, user.getPhoto());
			pstmt.setInt(5,  user.getId());

			if (pstmt.executeUpdate() != 1) {
				throw new SQLException("Inserting destination failed, no rows were affected");
			}

			int autoId = 0;
			ResultSet generatedKeys = pstmt.getGeneratedKeys();
			if (generatedKeys.next()) {
				autoId = generatedKeys.getInt(1);
			} else {
				throw new SQLException("Inserting destination failed, no ID generated.");
			}
			connection.commit();
			
			return new User(autoId, user.getUsername(),user.getEmail(),user.getPassword(),user.getPhoto());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}
	
	public void deleteOne(Integer user_id) {
		
		String sql = "DELETE FROM users WHERE user_id = ?";
		
		try (Connection connection = JDBC.getConnection()) {
			
			connection.setAutoCommit(false);
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, user_id);
			
			pstmt.execute();
			connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
