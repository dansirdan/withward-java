package com.withward.repository;
import java.sql.Connection;
//import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.withward.model.DestinationRating;
import com.withward.util.JDBC;

public class DestinationRatingDAO {
	public ArrayList<DestinationRating> getAll(Integer destination_id) {

		ArrayList<DestinationRating> destination_ratings = new ArrayList<DestinationRating>();
		String sql = "SELECT * " + "FROM ratings"
				+ "WHERE destination_id = ?";

		try (Connection connection = JDBC.getConnection()) {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, destination_id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Integer id = rs.getInt("rating_id");
				Integer destinationId = rs.getInt("destination_id");
				Integer userId = rs.getInt("user_id");
				Float rating = rs.getFloat("rating_value");
				DestinationRating destination_user = new DestinationRating(id, destinationId, userId, rating);
				destination_ratings.add(destination_user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return destination_ratings;
	}

	public DestinationRating getDestinationRating(Integer destinationRatingId) {

		DestinationRating destination_user = null;
		String sql = "SELECT * " + "FROM ratings " + "WHERE rating_id = ?";
		try (Connection connection = JDBC.getConnection()) {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, destinationRatingId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Integer id = rs.getInt("rating_id");
				Integer destinationId = rs.getInt("destination_id");
				Integer userId = rs.getInt("user_id");
				Float rating = rs.getFloat("rating_value");
				destination_user = new DestinationRating(id, destinationId, userId, rating);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return destination_user;
	}
	
	public DestinationRating insertDestRating(DestinationRating destination_rating) {
		String sql = "INSERT INTO ratings "
				+ "(destination_id, user_id, rating_value) " + "VALUES "
				+ "(?,?,?)";

		try (Connection connection = JDBC.getConnection()) {
			connection.setAutoCommit(false);
			PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			pstmt.setInt(1, destination_rating.getDestination_id());
			pstmt.setInt(2, destination_rating.getUser_id());
			pstmt.setFloat(3, destination_rating.getRating());

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
			DestinationRating newRating = this.getDestinationRating(autoId);
			return newRating;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public DestinationRating updateDestinationRating(DestinationRating destination_rating) {
		String sql = "UPDATE ratings " 
				+ "SET rating_value = ? "  
				+ "WHERE rating_id = ?";
		
		try (Connection connection = JDBC.getConnection()) {
			connection.setAutoCommit(false);
			PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			pstmt.setFloat(1, destination_rating.getRating());
			pstmt.setInt(2, destination_rating.getId());

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
			DestinationRating newRating = this.getDestinationRating(autoId);
			return newRating;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
