package com.withward.servlets;
import java.io.BufferedReader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.withward.service.UserService;
import com.withward.model.User;
/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static Logger logger = Logger.getLogger(WithlistServlet.class);
	
	private ObjectMapper objectMapper = new ObjectMapper();
	private UserService userService = new UserService();

	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		BufferedReader reader = request.getReader();
		StringBuilder sb = new StringBuilder();
		String line;

		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}

		String jsonString = sb.toString();
		HttpSession session = request.getSession();

		try {
			User loginData = objectMapper.readValue(jsonString, User.class);
			
			String username;
			String password;
			boolean isAuthenticated = false;
			if (loginData.getUsername() != null & loginData.getPassword() != null) {
				username = loginData.getUsername();
				password = loginData.getPassword();				
				isAuthenticated = userService.isAuthenticated(username, password);
				session.setAttribute("username", username);
				session.setAttribute("password", password);
			} else {
				try {
					username = (String) session.getAttribute("username");
					password = (String) session.getAttribute("password");
					isAuthenticated = userService.isAuthenticated(username, password);
					logger.debug("LOGIN SESSION ATTEMPT made at " + request.getRequestURI());

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			logger.debug("LOGIN ATTEMPT made at " + request.getRequestURI());

			if (isAuthenticated) {
				
				
				
				response.getWriter().append("YOU LOGGED IN");
				response.setStatus(200);
			} else {
				response.getWriter().append("INCORRECT LOGIN");
				response.setStatus(401);
			}
			
		} catch (JsonProcessingException e) {
			response.setStatus(400);
			e.printStackTrace();
		}		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
