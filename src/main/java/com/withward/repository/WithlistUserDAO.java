package com.withward.repository;
import java.sql.Connection;
//import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
//import java.sql.Statement;
import java.util.ArrayList;

import com.withward.model.User;
import com.withward.model.WithlistUser;
import com.withward.util.JDBC;

public class WithlistUserDAO {
	// FOR RETRIEVING ALL users that belong to a withlist
	public ArrayList<User> getAllWithlistUsers(Integer withlist_id) {

		ArrayList<User> withlist_users = new ArrayList<User>();
		String sql = "SELECT *  " + "FROM withlist_user "
					+ "RIGHT JOIN users "
					+ "ON withlist_user.user_id = users.id"
					+ "WHERE withlist_user.withlist_id = ?";

		try (Connection connection = JDBC.getConnection()) {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, withlist_id);
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Integer id = rs.getInt("id");
				String username = rs.getString("username");
				String email = rs.getString("email");
				String password = rs.getString("password");
				String homeLocation = rs.getString("homeLocation");
				User user = new User(id, username, email, password, homeLocation);
				withlist_users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return withlist_users;
	}
	
	public void createWithlistUser(WithlistUser wluser) {
		String sql = "INSERT INTO withlist_user "
				+ "(withlist_id, user_id) " + "VALUES "
				+ "(?,?)";

		try (Connection connection = JDBC.getConnection()) {
			connection.setAutoCommit(false);
			PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			pstmt.setInt(1, wluser.getWithlist_id());
			pstmt.setInt(2, wluser.getUser_id());

			if (pstmt.executeUpdate() != 1) {
				throw new SQLException("Inserting user failed, no rows were affected");
			}

//			int autoId = 0;
//			ResultSet generatedKeys = pstmt.getGeneratedKeys();
//			if (generatedKeys.next()) {
//				autoId = generatedKeys.getInt(1);
//			} else {
//				throw new SQLException("Inserting user failed, no ID generated.");
//			}

			connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();
		}
//		String sql = "INSERT INTO user (username, email, password
		
	}
}
