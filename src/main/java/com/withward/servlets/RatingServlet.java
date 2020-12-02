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
import com.withward.model.DestinationRating;
import com.withward.service.RatingService;

/**
 * Servlet implementation class RatingServlet
 */
public class RatingServlet extends HttpServlet {
	private static Logger logger = Logger.getLogger(DestinationServlet.class);
	private ObjectMapper objectMapper = new ObjectMapper();
	private RatingService ratingService = new RatingService();

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RatingServlet() {
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
					DestinationRating rating = ratingService.getOneDestinationRating(id);
					
					objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
					String json = objectMapper.writeValueAsString(rating);
					
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
					if (req.getParameter("destination-id") != null) {
						Integer destinationId = Integer.parseInt(req.getParameter("destination-id"));
						
						ArrayList<DestinationRating> ratings = ratingService.getAllRatings(destinationId);
						
						objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
						String json = objectMapper.writeValueAsString(ratings);
						
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
				DestinationRating ratingData = objectMapper.readValue(jsonString, DestinationRating.class);
				DestinationRating rating = ratingService.createDestinationRating(ratingData);
				String insertedUserJSON = objectMapper.writeValueAsString(rating);
				
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
					
					DestinationRating ratingData = objectMapper.readValue(jsonString, DestinationRating.class);
					DestinationRating rating = ratingService.updateDestinationRating(ratingData,
							Integer.parseInt(params[1]));
					String insertedUserJSON = objectMapper.writeValueAsString(rating);
					
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
}
