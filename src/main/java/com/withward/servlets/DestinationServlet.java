package com.withward.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.withward.DTO.DestinationDTO;
import com.withward.model.Destination;
import com.withward.service.DestinationService;

/**
 * Servlet implementation class DestinationServlet
 */
public class DestinationServlet extends HttpServlet {
	private static Logger logger = Logger.getLogger(DestinationServlet.class);
	private ObjectMapper objectMapper = new ObjectMapper();
	private DestinationService destinationService = new DestinationService();

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DestinationServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		logger.info("GET request made to " + req.getRequestURI());
		HttpSession session = req.getSession(false);

		if (session != null) {
			if (req.getPathInfo() != null && req.getPathInfo().split("/").length == 2) {
				try {
					Integer id = Integer.parseInt(req.getPathInfo().split("/")[1]);
					DestinationDTO destination = destinationService.getOneDestination(id);
					
					objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
					String json = objectMapper.writeValueAsString(destination);
					
					res.getWriter().append(json);
					res.setContentType("application/json");
					res.setStatus(200);
				} catch (NumberFormatException e) {
					res.setStatus(400);
					e.printStackTrace();
				} catch (SQLException e) {
					res.setStatus(400);
					e.printStackTrace();
				} catch (ArrayIndexOutOfBoundsException e) {
					res.setStatus(400);
					e.printStackTrace();
				} catch (JsonProcessingException e) {
					res.setStatus(400);
					e.printStackTrace();
				}
			} else if (req.getPathInfo() != null && req.getPathInfo().split("/").length > 2) {
				res.setStatus(400);
			} else {
				try {
					if (req.getParameter("withlist-id") != null) {
						Integer withlistId = Integer.parseInt(req.getParameter("withlist-id"));
						
						ArrayList<Destination> users = destinationService.getAllDestinations(withlistId);
						
						objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
						String json = objectMapper.writeValueAsString(users);
						
						res.getWriter().append(json);
						res.setContentType("application/json");
						res.setStatus(200);
					} else {
						res.setStatus(404);
					}
					
				} catch (JsonProcessingException e) {
					res.setStatus(400);
					e.printStackTrace();
				} catch (IOException e) {
					res.setStatus(400);
					e.printStackTrace();
				} catch (SQLException e) {
					res.setStatus(400);
					e.printStackTrace();
				}
			}
		} else {
			logger.info("GET request made without login");
			res.setStatus(401);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		logger.info("POST request made to " + req.getRequestURI());
		HttpSession session = req.getSession(false);

		if (session != null) {
			BufferedReader reader = req.getReader();
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
				
				res.getWriter().append(insertedUserJSON);
				res.setContentType("application/json");
				res.setStatus(201);
				
			} catch (JsonProcessingException e) {
				res.setStatus(400);
				e.printStackTrace();
			} catch (SQLException e) {
				res.setStatus(400);
				e.printStackTrace();
			}
		} else {
			logger.info("GET request made without login");
			res.setStatus(401);
		}
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		logger.info("PUT request made to " + req.getRequestURI());
		HttpSession session = req.getSession(false);

		if (session != null) {
			if (req.getPathInfo() == null || req.getPathInfo().split("/").length != 2) {
				res.setStatus(400);
			} else {
				String[] params = req.getPathInfo().split("/");
				
				try {
					BufferedReader reader = req.getReader();
					StringBuilder sb = new StringBuilder();
					String line;
					
					while ((line = reader.readLine()) != null) {
						sb.append(line);
					}
					
					String jsonString = sb.toString();
					
					Destination destinationData = objectMapper.readValue(jsonString, Destination.class);
					Destination destination = destinationService.updateDestination(destinationData,
							Integer.parseInt(params[1]));
					String insertedUserJSON = objectMapper.writeValueAsString(destination);
					
					res.getWriter().append(insertedUserJSON);
					res.setContentType("application/json");
					res.setStatus(201);
					
				} catch (JsonProcessingException e) {
					res.setStatus(400);
					e.printStackTrace();
				} catch (SQLException e) {
					res.setStatus(400);
					e.printStackTrace();
				}
			}
		} else {
			logger.info("GET request made without login");
			res.setStatus(401);
		}
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		logger.info("DELETE request made to " + req.getRequestURI());
		HttpSession session = req.getSession(false);

		if (session != null) {
			if (req.getPathInfo() == null || req.getPathInfo().split("/").length != 2) {
				res.setStatus(400);
			} else {
				try {
					String[] params = req.getPathInfo().split("/");
					destinationService.deleteDestination(Integer.parseInt(params[1]));
					res.setStatus(204);
				} catch (NumberFormatException e) {
					res.setStatus(400);
					e.printStackTrace();
				} catch (SQLException e) {
					res.setStatus(400);
					e.printStackTrace();
				}
			}
		} else {
			logger.info("GET request made without login");
			res.setStatus(401);
		}
	}
}
