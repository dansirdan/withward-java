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

import com.withward.service.WithlistService;

public class WithlistServletTest {
	@Mock
    HttpServletRequest request;
 
    @Mock
    HttpServletResponse response;
    
    @Mock
    WithlistService withlistService;
    
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void testGetWithlist() throws IOException, ServletException {
        when(request.getParameter("id")).thenReturn("1");
        
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);
        
        WithlistServlet withlistServlet = new WithlistServlet();
        withlistServlet.doGet(request, response);
        String result = sw.getBuffer().toString().trim();
//        System.out.println(result);
        assertEquals(result, new String("{\r\n"
        		+ "  \"id\" : 1,\r\n"
        		+ "  \"ownerId\" : 1,\r\n"
        		+ "  \"title\" : \"Hiking\",\r\n"
        		+ "  \"description\" : \"Hiking list to track hiking destinations.\"\r\n"
        		+ "}"));
    }
    
    @Test
    public void testGetAllWithlists() throws IOException, ServletException {
    	when(request.getPathInfo()).thenReturn("/all");
    	when(request.getParameter("id")).thenReturn("1");
        
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);
        
        WithlistServlet withlistServlet = new WithlistServlet();
        withlistServlet.doGet(request, response);
        String result = sw.getBuffer().toString().trim();
//        System.out.println(result);
        assertEquals(result, new String("[ {\r\n"
        		+ "  \"id\" : 1,\r\n"
        		+ "  \"ownerId\" : 1,\r\n"
        		+ "  \"title\" : \"Hiking\",\r\n"
        		+ "  \"description\" : \"Hiking list to track hiking destinations.\"\r\n"
        		+ "}, {\r\n"
        		+ "  \"id\" : 3,\r\n"
        		+ "  \"ownerId\" : 1,\r\n"
        		+ "  \"title\" : \"Roadtrip 2020\",\r\n"
        		+ "  \"description\" : \"Roadtrip list to track roadtrip destinations.\"\r\n"
        		+ "} ]"));
    }
    
}
