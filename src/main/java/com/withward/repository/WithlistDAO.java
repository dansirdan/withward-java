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
		String sql = "SELECT * " + "FROM withlists " + "WHERE owner_id = ?";

		try (Connection connection = JDBC.getConnection()) {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, user_id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Integer id = rs.getInt("withlist_id");
				Integer ownerId = rs.getInt("owner_id");
				String title = rs.getString("withlist_title");
				String description = rs.getString("withlist_description");
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
		String sql = "SELECT owner_id " + "FROM withlists " + "WHERE withlist_id = ?";

		try (Connection connection = JDBC.getConnection()) {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, withlist_id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Integer ownerId = rs.getInt("owner_id");
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
		String sql = "SELECT * " + "FROM withlists " + "WHERE withlist_id = ?";
		try (Connection connection = JDBC.getConnection()) {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, withlist_id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Integer id = rs.getInt("withlist_id");
				Integer ownerId = rs.getInt("owner_id");
				String title = rs.getString("withlist_title");
				String description = rs.getString("withlist_description");
				withlist = new Withlist(id, ownerId, title, description);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return withlist;
	}
	
	public Withlist insertWithlist(Withlist withlist) {
		String sql = "INSERT INTO withlists "
				+ "(owner_id, withlist_title, withlist_description) " + "VALUES "
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

			int autoId = 0;
			ResultSet generatedKeys = pstmt.getGeneratedKeys();
			if (generatedKeys.next()) {
				autoId = generatedKeys.getInt(1);
			} else {
				throw new SQLException("Inserting destination failed, no ID generated.");
			}

			connection.commit();
			Withlist newWithlist = this.getWithlist(autoId);
			return newWithlist;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Withlist updateWithlist(Withlist withlist) {
		String sql = "UPDATE withlists " 
				+ "SET withlist_title = ?, "
				+ "withlist_description = ? "
				+ "WHERE withlist_id = ?";
		
		try (Connection connection = JDBC.getConnection()) {
			connection.setAutoCommit(false);
			PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			pstmt.setString(1, withlist.getTitle());
			pstmt.setString(2, withlist.getDescription());
			pstmt.setInt(6, withlist.getId());

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
			Withlist newWithlist = this.getWithlist(autoId);
			return newWithlist;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void deleteOne(Integer withlist_id) {
		
		String sql = "DELETE FROM withlists WHERE withlist_id = ?";
		
		try (Connection connection = JDBC.getConnection()) {
			
			connection.setAutoCommit(false);
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, withlist_id);
			pstmt.execute();
			connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
