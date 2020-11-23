package com.withward.repository;
import java.sql.Connection;
//import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.withward.model.Withlist;
import com.withward.util.JDBC;

public class WithlistDAO {
	public ArrayList<Withlist> getAll(Integer user_id) {

		ArrayList<Withlist> withlists = new ArrayList<Withlist>();
		String sql = "SELECT * " + "FROM withlist " + "WHERE ownerId = ?";

		try (Connection connection = JDBC.getConnection()) {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, user_id);
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Integer id = rs.getInt("id");
				Integer ownerId = rs.getInt("ownerId");
				String title = rs.getString("title");
				String description = rs.getString("description");
				Withlist withlist = new Withlist(id, ownerId, title, description);
				withlists.add(withlist);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return withlists;
	}
	
	public boolean isAdmin(Integer user_id, Integer withlist_id) {
		String sql = "SELECT owner_id " + "FROM withlist " + "WHERE withlist_id = ?";

		try (Connection connection = JDBC.getConnection()) {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, withlist_id);
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Integer ownerId = rs.getInt("ownerId");
				if (ownerId == user_id) {
					return true;
				} 
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return false;
	}
	
	public Withlist getWithlist(Integer withlist_id) {

		Withlist withlist = null;
		String sql = "SELECT * " + "FROM withlist " + "WHERE id = ?";
		try (Connection connection = JDBC.getConnection()) {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, withlist_id);
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Integer id = rs.getInt("id");
				Integer ownerId = rs.getInt("ownerId");
				String title = rs.getString("title");
				String description = rs.getString("description");
				withlist = new Withlist(id, ownerId, title, description);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return withlist;
	}
	
	public void insertWithlist(Withlist withlist) {
		String sql = "INSERT INTO withlist "
				+ "(owner_id, title, description) " + "VALUES "
				+ "(?,?,?)";

		try (Connection connection = JDBC.getConnection()) {
			connection.setAutoCommit(false);
			PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			pstmt.setInt(1, withlist.getOwnerId());
			pstmt.setString(2, withlist.getTitle());
			pstmt.setString(3, withlist.getDescription());

			if (pstmt.executeUpdate() != 1) {
				throw new SQLException("Inserting destination failed, no rows were affected");
			}

//			int autoId = 0;
//			ResultSet generatedKeys = pstmt.getGeneratedKeys();
//			if (generatedKeys.next()) {
//				autoId = generatedKeys.getInt(1);
//			} else {
//				throw new SQLException("Inserting destination failed, no ID generated.");
//			}

			connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateWithlist(Withlist withlist) {
		String sql = "UPDATE withlist " 
				+ "SET title = ?, "
				+ "description = ? "
				+ "WHERE id = ?";
		
		try (Connection connection = JDBC.getConnection()) {
			connection.setAutoCommit(false);
			PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			pstmt.setString(1, withlist.getTitle());
			pstmt.setString(2, withlist.getDescription());
			pstmt.setInt(6, withlist.getId());

			if (pstmt.executeUpdate() != 1) {
				throw new SQLException("Inserting destination failed, no rows were affected");
			}

//			int autoId = 0;
//			ResultSet generatedKeys = pstmt.getGeneratedKeys();
//			if (generatedKeys.next()) {
//				autoId = generatedKeys.getInt(1);
//			} else {
//				throw new SQLException("Inserting destination failed, no ID generated.");
//			}

			connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteOne(Integer withlist_id) {
		
		String sql = "DELETE FROM withlist WHERE ID = ?";
		
		try (Connection connection = JDBC.getConnection()) {
			
			connection.setAutoCommit(false);
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, withlist_id);
			connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
