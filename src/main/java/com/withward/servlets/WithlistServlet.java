package com.withward.servlets;
import org.apache.log4j.Logger;


import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.withward.model.Withlist;
import com.withward.service.WithlistService;

/**
 * Servlet implementation class WithlistServlet
 */
public class WithlistServlet extends HttpServlet {
	
	private static Logger logger = Logger.getLogger(WithlistServlet.class);
	
	private ObjectMapper objectMapper = new ObjectMapper();
	private WithlistService withlistService = new WithlistService();

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WithlistServlet() {
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
//		response.getWriter().append(request.getPathInfo());
		
		String action = "";
		if(request.getPathInfo() != null) {
			action = request.getPathInfo();
		}

		switch (action) {
		case "/new":
			insertWithlist(request, response);
			logger.debug("POST request made to " + request.getRequestURI());
			break;
		case "/edit":
			editWithlist(request, response);
			logger.debug("PUT request made to " + request.getRequestURI());

			break;
		case "/delete":
			deleteWithlist(request, response);
			logger.debug("DELETE request made to " + request.getRequestURI());

			break;
		case "/all":
			listAllWithlists(request, response);
			logger.debug("GET request made to " + request.getRequestURI());

			break;
		default:
			listWithlist(request, response);
			logger.debug("GET request made to " + request.getRequestURI());

			break;
		}
	}

	private void insertWithlist(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BufferedReader reader = request.getReader();
		StringBuilder sb = new StringBuilder();
		String line;

		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}

		String jsonString = sb.toString();

		try {
			Withlist withlistData = objectMapper.readValue(jsonString, Withlist.class);
			Withlist withlist = withlistService.createWithlist(withlistData);
		    String insertedUserJSON = objectMapper.writeValueAsString(withlist);

			response.getWriter().append(insertedUserJSON);
			response.setContentType("application/json");
			response.setStatus(201);

		} catch (JsonProcessingException e) {
			response.setStatus(400);
			e.printStackTrace();
		}
	}

	private void editWithlist(HttpServletRequest request, HttpServletResponse response)
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
			Withlist withlistData = objectMapper.readValue(jsonString, Withlist.class);
			Withlist withlist = withlistService.updateWithlist(withlistData);
			String insertedUserJSON = objectMapper.writeValueAsString(withlist);

			response.getWriter().append(insertedUserJSON);
			response.setContentType("application/json");
			response.setStatus(201);

		} catch (JsonProcessingException e) {
			response.setStatus(400);
			e.printStackTrace();
		}
	}

	private void deleteWithlist(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		response.getWriter().append("DELETE USER");
		Integer withlistId = 0;
		if (request.getParameter("id") != null) {
			withlistId = Integer.parseInt(request.getParameter("id"));
			withlistService.deleteWithlist(withlistId);
		}

	}

	private void listAllWithlists(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Integer userId = 0;
		if (request.getParameter("id") != null) {
			userId = Integer.parseInt(request.getParameter("id"));
		}
		try {
			ArrayList<Withlist> withlists = withlistService.getAllWithlists(userId);
			
			objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
			String json = objectMapper.writeValueAsString(withlists);

			response.getWriter().append(json);
			response.setContentType("application/json");
			response.setStatus(200);

		} catch (JsonProcessingException e) {
			response.setStatus(400);
			e.printStackTrace();
		}
	}

	private void listWithlist(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Integer withlistId = 0;
		if (request.getParameter("id") != null) {
			withlistId = Integer.parseInt(request.getParameter("id"));
		}
		try {
			
			Withlist user = withlistService.getOneWithlist(withlistId);
			
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
