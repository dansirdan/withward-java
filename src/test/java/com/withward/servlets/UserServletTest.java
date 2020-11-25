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

import com.withward.service.UserService;
 
public class UserServletTest {
 
    @Mock
    HttpServletRequest request;
 
    @Mock
    HttpServletResponse response;
    
    @Mock
    UserService userService;
 
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }
 
    @Test
    public void testGetUser() throws IOException, ServletException {
 
        when(request.getParameter("id")).thenReturn("4");
 
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
         
        when(response.getWriter()).thenReturn(pw);
 
        UserServlet userServlet = new UserServlet();
        
        userServlet.doGet(request, response);
        String result = sw.getBuffer().toString().trim();
//        System.out.println(result);
        assertEquals(result, new String("{\r\n"
        		+ "  \"id\" : 4,\r\n"
        		+ "  \"username\" : \"usernametest\",\r\n"
        		+ "  \"email\" : null,\r\n"
        		+ "  \"password\" : null,\r\n"
        		+ "  \"photo\" : \"photoplaceholder\"\r\n"
        		+ "}"));
 
    }
    @Test
    public void testGetAllUsers() throws IOException, ServletException {
    	when(request.getPathInfo()).thenReturn("/all");
        
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);
        
        UserServlet userServlet = new UserServlet();
        userServlet.doGet(request, response);
        String result = sw.getBuffer().toString().trim();
//        System.out.println(result);
        assertEquals(result, new String("[ {\r\n"
        		+ "  \"id\" : 1,\r\n"
        		+ "  \"username\" : \"user1\",\r\n"
        		+ "  \"email\" : null,\r\n"
        		+ "  \"password\" : null,\r\n"
        		+ "  \"photo\" : \"photoplaceholder\"\r\n"
        		+ "}, {\r\n"
        		+ "  \"id\" : 2,\r\n"
        		+ "  \"username\" : \"user2\",\r\n"
        		+ "  \"email\" : null,\r\n"
        		+ "  \"password\" : null,\r\n"
        		+ "  \"photo\" : \"photoplaceholder\"\r\n"
        		+ "}, {\r\n"
        		+ "  \"id\" : 3,\r\n"
        		+ "  \"username\" : \"user3\",\r\n"
        		+ "  \"email\" : null,\r\n"
        		+ "  \"password\" : null,\r\n"
        		+ "  \"photo\" : \"photoplaceholder\"\r\n"
        		+ "}, {\r\n"
        		+ "  \"id\" : 4,\r\n"
        		+ "  \"username\" : \"usernametest\",\r\n"
        		+ "  \"email\" : null,\r\n"
        		+ "  \"password\" : null,\r\n"
        		+ "  \"photo\" : \"photoplaceholder\"\r\n"
        		+ "} ]"));
    }
}
