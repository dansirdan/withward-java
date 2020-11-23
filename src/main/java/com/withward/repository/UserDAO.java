package com.withward.repository;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import com.withward.model.User;
import com.withward.util.JDBC;

public class UserDAO {
	public ArrayList<User> getAll() {

		ArrayList<User> users = new ArrayList<User>();
		String sql = "SELECT * " + "FROM users";

		try (Connection connection = JDBC.getConnection()) {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Integer id = rs.getInt("id");
				String username = rs.getString("username");
				String email = rs.getString("email");
				String password = rs.getString("password");
				String photo = rs.getString("photo");
				User user = new User(id, username, email, password, photo);
				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return users;
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
		String sql = "SELECT * " + "FROM users " + "WHERE id = ?";
		try (Connection connection = JDBC.getConnection()) {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, userId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Integer id = rs.getInt("id");
				String username = rs.getString("username");
				String email = rs.getString("email");
				String password = rs.getString("password");
				String photo = rs.getString("photo");
				user = new User(id, username, email, password, photo);
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
				+ "(username, email, password, photo) " + "VALUES "
				+ "(?,?,?,?)";

		try (Connection connection = JDBC.getConnection()) {
			connection.setAutoCommit(false);
			PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getEmail());
			pstmt.setString(3, user.getPassword());
			pstmt.setString(4, user.getPhoto());

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
				+ "email = ?, "
				+ "password = ?, " 
				+ "photo = ? " 
				+ "WHERE id = ?";
		
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
		
		String sql = "DELETE FROM users WHERE ID = ?";
		
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
