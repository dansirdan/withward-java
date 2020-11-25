package com.withward.servlets;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
 
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
 
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.withward.service.DestinationService;

public class DestinationServletTest {
	@Mock
    HttpServletRequest request;
 
    @Mock
    HttpServletResponse response;
    
    @Mock
    DestinationService destinationService;
    
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void testGetOneDestination() throws IOException, ServletException {
        when(request.getParameter("id")).thenReturn("1");
        
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);
        
        DestinationServlet destinationServlet = new DestinationServlet();
        destinationServlet.doGet(request, response);
        String result = sw.getBuffer().toString().trim();
//        System.out.println(request);
        assertEquals(result, new String("{\r\n"
        		+ "  \"id\" : 1,\r\n"
        		+ "  \"withlist_id\" : 1,\r\n"
        		+ "  \"name\" : \"Trailhead1\",\r\n"
        		+ "  \"description\" : \"hike to the midpoint\",\r\n"
        		+ "  \"photo\" : \"placeholder\",\r\n"
        		+ "  \"completed\" : false,\r\n"
        		+ "  \"averageRating\" : 0.0\r\n"
        		+ "}"));
    }
    @Test
    public void testGetAllDestinations() throws IOException, ServletException {
        when(request.getParameter("id")).thenReturn("1");
        when(request.getPathInfo()).thenReturn("/all");
        
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);
        
        DestinationServlet destinationServlet = new DestinationServlet();
        destinationServlet.doGet(request, response);
        String result = sw.getBuffer().toString().trim();
//        System.out.println(result);
        assertEquals(result, new String("[ {\r\n"
        		+ "  \"id\" : 1,\r\n"
        		+ "  \"withlist_id\" : 1,\r\n"
        		+ "  \"name\" : \"Trailhead1\",\r\n"
        		+ "  \"description\" : \"hike to the midpoint\",\r\n"
        		+ "  \"photo\" : \"placeholder\",\r\n"
        		+ "  \"completed\" : false,\r\n"
        		+ "  \"averageRating\" : 0.0\r\n"
        		+ "}, {\r\n"
        		+ "  \"id\" : 2,\r\n"
        		+ "  \"withlist_id\" : 1,\r\n"
        		+ "  \"name\" : \"Northshore\",\r\n"
        		+ "  \"description\" : \"hike to the beach\",\r\n"
        		+ "  \"photo\" : \"placeholder\",\r\n"
        		+ "  \"completed\" : false,\r\n"
        		+ "  \"averageRating\" : 0.0\r\n"
        		+ "} ]"));
    }
}