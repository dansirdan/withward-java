package com.withward.servlets;

import java.util.ArrayList;
import java.io.BufferedReader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.withward.service.UserService;
import com.withward.model.User;

/**
 * Servlet implementation class UserServlet
 */
public class UserServlet extends HttpServlet {
	private static Logger logger = Logger.getLogger(WithlistServlet.class);

	private ObjectMapper objectMapper = new ObjectMapper();
	private UserService userService = new UserService();

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
//		String action = request.getParameter("action");
		String action = "";
		if (request.getPathInfo() != null) {
			action = request.getPathInfo();
		}
//		response.getWriter().append(request.getPathInfo());

		switch (action) {
		case "/new":
			insertUser(request, response);
			logger.debug("POST request made to " + request.getRequestURI());

			break;
		case "/edit":
			editUser(request, response);
			logger.debug("PUT request made to " + request.getRequestURI());

			break;
		case "/delete":
			deleteUser(request, response);
			logger.debug("DELETE request made to " + request.getRequestURI());

			break;
		case "/all":
			listAllUsers(request, response);
			logger.debug("GET request made to " + request.getRequestURI());

			break;
		default:
			listUser(request, response);
			logger.debug("GET request made to " + request.getRequestURI());

			break;
		}
	}
	
	private void insertUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BufferedReader reader = request.getReader();
		StringBuilder sb = new StringBuilder();
		String line;

		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}

		String jsonString = sb.toString();

		try {
			User userdata = objectMapper.readValue(jsonString, User.class);
			User user = userService.createUser(userdata);
			String insertedUserJSON = objectMapper.writeValueAsString(user);

			response.getWriter().append(insertedUserJSON);
			response.setContentType("application/json");
			response.setStatus(201);

		} catch (JsonProcessingException e) {
			response.setStatus(400);
			e.printStackTrace();
		}
	}

	private void editUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		response.getWriter().append("EDIT USER");

		BufferedReader reader = request.getReader();
		StringBuilder sb = new StringBuilder();
		String line;

		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}

		String jsonString = sb.toString();

		try {
			User userdata = objectMapper.readValue(jsonString, User.class);
			User user = userService.updateUser(userdata);
			String insertedUserJSON = objectMapper.writeValueAsString(user);

			response.getWriter().append(insertedUserJSON);
			response.setContentType("application/json");
			response.setStatus(201);

		} catch (JsonProcessingException e) {
			response.setStatus(400);
			e.printStackTrace();
		}
	}

	private void deleteUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		response.getWriter().append("DELETE USER");
		Integer user_id = 0;
		if (request.getParameter("id") != null) {			
			user_id = Integer.parseInt(request.getParameter("id"));
		}
		userService.deleteUser(user_id);
	}

	private void listAllUsers(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			ArrayList<User> users = userService.getAllUsers();

			objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
			String json = objectMapper.writeValueAsString(users);

			response.getWriter().append(json);
			response.setContentType("application/json");
			response.setStatus(200);

		} catch (JsonProcessingException e) {
			response.setStatus(400);
			e.printStackTrace();
		}
	}

	private void listUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		response.getWriter().append("LIST ONE");
		Integer user_id = 0;
		if (request.getParameter("id") != null) {			
			user_id = Integer.parseInt(request.getParameter("id"));
		}

		try {

			User user = userService.getOneUser(user_id);
			
			objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
			String json = objectMapper.writeValueAsString(user);

			response.getWriter().append(json);
			response.setContentType("application/json");
			response.setStatus(200);

		} catch (JsonProcessingException e) {
			response.setStatus(400);
			e.printStackTrace();
		}
	}
}
