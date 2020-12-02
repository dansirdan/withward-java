package com.withward.repository;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import com.withward.DTO.UserDTO;
import com.withward.model.User;
import com.withward.util.JDBC;
import com.withward.util.SHA;

public class UserDAO {
	private SHA sha = new SHA();

	/**
	 * Method to interact with the database to get all user records.
	 * @return ArrayList<User> of all user records. 
	 * @throws SQLException
	 */
	public ArrayList<UserDTO> getAll() throws SQLException {

		ArrayList<UserDTO> users = new ArrayList<UserDTO>();
		String sql = "SELECT * " + "FROM users";

		Connection connection = JDBC.getConnection();
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			Integer id = rs.getInt("user_id");
			String username = rs.getString("username");
			String photo = rs.getString("user_photo");
			UserDTO user = new UserDTO(id, username, photo);
			users.add(user);
		}

		stmt.close();
		connection.close();
		return users;
	}

	/**
	 * Method to authenticate user based on username and password.
	 * @param username String, username in database
	 * @param password String, password in database
	 * @return boolean if authenticated. 
	 * @throws SQLException
	 */
	public boolean authenticateUser(String username, String password) throws SQLException {
		String hashedPassword;
		byte[] salt;
		String sql = "SELECT user_salt, user_password from users where username = ?";

		Connection connection = JDBC.getConnection();

		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setString(1, username);
		ResultSet resultSet = pstmt.executeQuery();
		resultSet.next();
		salt = resultSet.getBytes("user_salt");
		hashedPassword = resultSet.getString("user_password");
		pstmt.close();
		connection.close();
		if (hashedPassword.equals(sha.hashingMethod(password, salt))) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Method to interact with the database to get one user record.
	 * @param userId id value of user
	 * @return User object of found user record. 
	 * @throws SQLException
	 */
	public UserDTO getUser(Integer userId) throws SQLException {

		UserDTO user = null;
		String sql = "SELECT * " + "FROM users " + "WHERE users.user_id = ?";
		Connection connection = JDBC.getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, userId);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			Integer id = rs.getInt("user_id");
			String username = rs.getString("username");
			String photo = rs.getString("user_photo");
			user = new UserDTO(id, username, photo);
		}
		pstmt.close();
		connection.close();
		return user;
	}
	
	/**
	 * Method to interact with the database to get one user record.
	 * @param userId id value of user
	 * @return User object of found user record. 
	 * @throws SQLException
	 */
	public UserDTO getUserByUsername(String queryUsername) throws SQLException {

		UserDTO user = null;
		String sql = "SELECT * " + "FROM users " + "WHERE users.username = ?";
		Connection connection = JDBC.getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setString(1, queryUsername);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			Integer id = rs.getInt("user_id");
			String username = rs.getString("username");
			String photo = rs.getString("user_photo");
			user = new UserDTO(id, username, photo);
		}
		pstmt.close();
		connection.close();
		return user;
	}

	/**
	 * Method to interact with the database to insert one user record.
	 * @param User object
	 * @return User object of inserted user record. 
	 * @throws SQLException
	 */
	public UserDTO insertUser(User user) throws SQLException {
		String sql = "INSERT INTO users " + "(username, user_email, user_salt, user_password, user_photo) " + "VALUES "
				+ "(?,?,?,?,?)";

		byte[] salt = sha.getSalt();
		String hashedPassword = sha.hashingMethod(user.getPassword(), salt);

		Connection connection = JDBC.getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

		pstmt.setString(1, user.getUsername());
		pstmt.setString(2, user.getEmail());
		pstmt.setBytes(3, salt);
		pstmt.setString(4, hashedPassword);
		pstmt.setString(5, user.getPhoto());

		if (pstmt.executeUpdate() != 1) {
			throw new SQLException("No Rows Affected");
		}

		int autoId = 0;
		ResultSet generatedKeys = pstmt.getGeneratedKeys();
		if (generatedKeys.next()) {
			autoId = generatedKeys.getInt(1);
		} else {
			throw new SQLException("ID generation failed");
		}

		pstmt.close();
		connection.close();
		return new UserDTO(autoId, user.getUsername(), user.getPhoto());

	}

	/**
	 * Method to interact with the database to update one user record.
	 * @param User object
	 * @param id user id that will be updated
	 * @return User object of updated destination record. 
	 * @throws SQLException
	 */
	public UserDTO updateUser(User user, Integer id) throws SQLException {
		String sql = "UPDATE users " + "SET username = ?, " + "user_email = ?, " + "user_password = ?, "
				+ "user_photo = ? " + "WHERE user_id = ?";

		Connection connection = JDBC.getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);

		pstmt.setString(1, user.getUsername());
		pstmt.setString(2, user.getEmail());
		pstmt.setString(3, user.getPassword());
		pstmt.setString(4, user.getPhoto());
		pstmt.setInt(5, id);

		if (pstmt.executeUpdate() != 1) {
			throw new SQLException("No Rows Affected");
		}

		pstmt.close();
		connection.close();

		return new UserDTO(id, user.getUsername(), user.getPhoto());
	}

	/**
	 * Method to interact with the database to delete one user record.
	 * @param id user id that will be deleted
	 * @return boolean if record was deleted 
	 * @throws SQLException
	 */
	public boolean deleteOne(Integer user_id) throws SQLException {

		String sql = "DELETE FROM users WHERE user_id = ?";

		Connection connection = JDBC.getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, user_id);

		if (pstmt.executeUpdate() == 1) {
			pstmt.close();
			connection.close();
			return true;
		} else {
			pstmt.close();
			connection.close();
			throw new SQLException("No Rows Affected");
		}
	}
}
