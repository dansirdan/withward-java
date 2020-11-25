package com.withward.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
//import java.sql.Statement;
import java.util.ArrayList;

import com.withward.model.Destination;
import com.withward.util.JDBC;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DestinationDAO {
	public ArrayList<Destination> getAll(Integer withlist_id) {

		ArrayList<Destination> destinations = new ArrayList<Destination>();
		String sql = "SELECT * " + "FROM destinations " + "WHERE withlist_id = ?";

		try (Connection connection = JDBC.getConnection()) {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, withlist_id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Integer id = rs.getInt("dest_id");
				Integer withlistId = rs.getInt("withlist_id");
				String name = rs.getString("dest_name");
				String description = rs.getString("dest_description");
				String photo = rs.getString("dest_photo");
				boolean completed = rs.getBoolean("dest_completed");
				Float averageRating = rs.getFloat("dest_averageRating");
				Destination destination = new Destination(id, withlistId, name, description, photo, completed,
						averageRating);
				destinations.add(destination);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return destinations;
	}

	public Destination getDestination(Integer destinationId) {

		Destination destination = null;
		String sql = "SELECT * " + "FROM destinations " + "WHERE dest_id = ?";
		try (Connection connection = JDBC.getConnection()) {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, destinationId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Integer id = rs.getInt("dest_id");
				Integer withlistId = rs.getInt("withlist_id");
				String name = rs.getString("dest_name");
				String description = rs.getString("dest_description");
				String photo = rs.getString("dest_photo");
				boolean completed = rs.getBoolean("dest_completed");
				Float averageRating = rs.getFloat("dest_averageRating");
				destination = new Destination(id, withlistId, name, description, photo, completed, averageRating);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return destination;
	}

	public Destination insertDestination(Destination destination) {
		String sql = "INSERT INTO destinations "
				+ "(withlist_id, dest_name, dest_description, dest_photo, dest_completed, dest_averageRating) " + "VALUES "
				+ "(?,?,?,?,?,?)";

		try (Connection connection = JDBC.getConnection()) {
			connection.setAutoCommit(false);
			PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			pstmt.setInt(1, destination.getWithlist_id());
			pstmt.setString(2, destination.getName());
			pstmt.setString(3, destination.getDescription());
			pstmt.setString(4, destination.getPhoto());
			pstmt.setBoolean(5, false);
			pstmt.setFloat(6, destination.getAverageRating());

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
			Destination newDest = this.getDestination(autoId);
			return newDest;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Destination updateDestination(Destination destination) {
		String sql = "UPDATE destinations " 
				+ "SET dest_name = ?, " 
				+ "dest_description = ?, "
				+ "dest_photo = ?, " 
				+ "dest_completed = ?, " 
				+ "dest_averageRating = ? " 
				+ "WHERE dest_id = ?";
		
		try (Connection connection = JDBC.getConnection()) {
			connection.setAutoCommit(false);
			PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			pstmt.setString(1, destination.getName());
			pstmt.setString(2, destination.getDescription());
			pstmt.setString(3, destination.getPhoto());
			pstmt.setBoolean(4, destination.isCompleted());
			pstmt.setFloat(5, destination.getAverageRating());
			pstmt.setInt(6, destination.getId());

			if (pstmt.executeUpdate() != 1) {
				throw new SQLException("Inserting destination failed, no rows were affected");
			}

			Integer autoId = 0;
			ResultSet generatedKeys = pstmt.getGeneratedKeys();
			if (generatedKeys.next()) {
				autoId = generatedKeys.getInt(1);
			} else {
				throw new SQLException("Inserting destination failed, no ID generated.");
			}

			connection.commit();
			Destination newDest = this.getDestination(autoId);
			return newDest;		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void deleteOne(Integer destination_id) {
		
		String sql = "DELETE FROM destinations WHERE dest_id = ?";
		
		try (Connection connection = JDBC.getConnection()) {
			
			connection.setAutoCommit(false);
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, destination_id);
			pstmt.execute();

			connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
