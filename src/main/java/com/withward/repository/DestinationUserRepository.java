package com.withward.repository;
import java.sql.Connection;
//import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.withward.model.DestinationUser;
import com.withward.model.User;
import com.withward.util.JDBC;

public class DestinationUserRepository {
	public ArrayList<DestinationUser> getAll() {

		ArrayList<DestinationUser> destination_users = new ArrayList<DestinationUser>();
		String sql = "SELECT * " + "FROM destination_user";

		try (Connection connection = JDBC.getConnection()) {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Integer id = rs.getInt("id");
				Integer destinationId = rs.getInt("destination_id");
				Integer userId = rs.getInt("user_id");
				Float rating = rs.getFloat("rating");
				DestinationUser destination_user = new DestinationUser(id, destinationId, userId, rating);
				destination_users.add(destination_user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return destination_users;
	}

	public DestinationUser getDestUser(Integer destinationUserId) {

		DestinationUser destination_user = null;
		String sql = "SELECT * " + "FROM destination_user " + "WHERE id = ?";
		try (Connection connection = JDBC.getConnection()) {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, destinationUserId);
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Integer id = rs.getInt("id");
				Integer destinationId = rs.getInt("destination_id");
				Integer userId = rs.getInt("user_id");
				Float rating = rs.getFloat("rating");
				destination_user = new DestinationUser(id, destinationId, userId, rating);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return destination_user;
	}
//	
//	public void insertDestUser(DestinationUser destination_user) {
//		String sql = "INSERT INTO user "
//				+ "(username, email, password, photo) " + "VALUES "
//				+ "(?,?,?,?)";
//
//		try (Connection connection = JDBC.getConnection()) {
//			connection.setAutoCommit(false);
//			PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//
//			pstmt.setString(1, user.getUsername());
//			pstmt.setString(2, user.getEmail());
//			pstmt.setString(3, user.getPassword());
//			pstmt.setString(4, user.getPhoto());
//
//			if (pstmt.executeUpdate() != 1) {
//				throw new SQLException("Inserting user failed, no rows were affected");
//			}
//
////			int autoId = 0;
////			ResultSet generatedKeys = pstmt.getGeneratedKeys();
////			if (generatedKeys.next()) {
////				autoId = generatedKeys.getInt(1);
////			} else {
////				throw new SQLException("Inserting user failed, no ID generated.");
////			}
//
//			connection.commit();
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
////		String sql = "INSERT INTO user (username, email, password
//	}
//	
//	public void updateUser(User user) {
//		String sql = "UPDATE user " 
//				+ "SET username = ?, " 
//				+ "email = ?, "
//				+ "password = ?, " 
//				+ "photo = ?, " 
//				+ "WHERE id = ?";
//		
//		try (Connection connection = JDBC.getConnection()) {
//			connection.setAutoCommit(false);
//			PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//
//			pstmt.setString(1, user.getUsername());
//			pstmt.setString(2, user.getEmail());
//			pstmt.setString(3, user.getPassword());
//			pstmt.setString(4, user.getPhoto());
//			pstmt.setInt(5,  user.getId());
//
//			if (pstmt.executeUpdate() != 1) {
//				throw new SQLException("Inserting destination failed, no rows were affected");
//			}
//
////			int autoId = 0;
////			ResultSet generatedKeys = pstmt.getGeneratedKeys();
////			if (generatedKeys.next()) {
////				autoId = generatedKeys.getInt(1);
////			} else {
////				throw new SQLException("Inserting destination failed, no ID generated.");
////			}
//
//			connection.commit();
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public void deleteOne(Integer user_id) {
//		
//		String sql = "DELETE FROM user WHERE ID = ?";
//		
//		try (Connection connection = JDBC.getConnection()) {
//			
//			connection.setAutoCommit(false);
//			PreparedStatement pstmt = connection.prepareStatement(sql);
//			pstmt.setInt(1, user_id);
//			connection.commit();
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
}
