package com.withward.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.withward.model.Destination;
import com.withward.service.DestinationService;

/**
 * Servlet implementation class DestinationServlet
 */
public class DestinationServlet extends HttpServlet {
	private static Logger logger = Logger.getLogger(WithlistServlet.class);
	private ObjectMapper objectMapper = new ObjectMapper();
	private DestinationService destinationService = new DestinationService();

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DestinationServlet() {
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
			insertDestination(request, response);
			logger.debug("POST request made to " + request.getRequestURI());

			break;
		case "/edit":
			editDestination(request, response);
			logger.debug("PUT request made to " + request.getRequestURI());

			break;
		case "/delete":
			deleteDestination(request, response);
			logger.debug("DELETE request made to " + request.getRequestURI());

			break;
		case "/all":
			listAllDestinations(request, response);
			logger.debug("GET request made to " + request.getRequestURI());

			break;
		default:
			listDestination(request, response);
			logger.debug("GET request made to " + request.getRequestURI());

			break;
		}
	}

	private void insertDestination(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BufferedReader reader = request.getReader();
		StringBuilder sb = new StringBuilder();
		String line;

		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}

		String jsonString = sb.toString();

		try {
			Destination destinationData = objectMapper.readValue(jsonString, Destination.class);
			Destination destination = destinationService.createDestination(destinationData);
			String insertedUserJSON = objectMapper.writeValueAsString(destination);

			response.getWriter().append(insertedUserJSON);
			response.setContentType("application/json");
			response.setStatus(201);

		} catch (JsonProcessingException e) {
			response.setStatus(400);
			e.printStackTrace();
		}
	}

	private void editDestination(HttpServletRequest request, HttpServletResponse response)
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
			Destination destinationData = objectMapper.readValue(jsonString, Destination.class);
			Destination destination = destinationService.updateDestination(destinationData);
			String insertedUserJSON = objectMapper.writeValueAsString(destination);

			response.getWriter().append(insertedUserJSON);
			response.setContentType("application/json");
			response.setStatus(201);

		} catch (JsonProcessingException e) {
			response.setStatus(400);
			e.printStackTrace();
		}
	}

	private void deleteDestination(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Integer destinationId = 0;
		if (request.getParameter("id") != null) {
			destinationId = Integer.parseInt(request.getParameter("id"));	
			destinationService.deleteDestination(destinationId);
//			response.getWriter().append("DELETED DESTINATION");
		}
	}

	private void listAllDestinations(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Integer withlistId = 0;
		if(request.getParameter("id") != null) {
			withlistId = Integer.parseInt(request.getParameter("id"));
		}

		try {
			ArrayList<Destination> users = destinationService.getAllDestinations(withlistId);
			
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

	private void listDestination(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		response.getWriter().append("LIST ONE");
		Integer destinationId = 0;
		if (request.getParameter("id") != null) {
			destinationId = Integer.parseInt(request.getParameter("id"));	
		}

		try {
			
			Destination user = destinationService.getOneDestination(destinationId);
			
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
