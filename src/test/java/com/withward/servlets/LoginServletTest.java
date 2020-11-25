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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.withward.repository.UserDAO;
import com.withward.service.UserService;
import com.withward.util.SHA;

public class LoginServletTest {
//	@Mock
//    HttpServletRequest request;
// 
//    @Mock
//    HttpServletResponse response;
//    
//    @Mock
//    UserService userService;
//    
//    @InjectMocks
//    LoginServlet loginServlet;
//    
//    @Before
//    public void setUp() throws Exception {
//        MockitoAnnotations.initMocks(this);
//    }
//    
//    @Test
//    public void testLoginForCorrectLogin() throws IOException, ServletException {
//    	
//        when(request.getParameter("username")).thenReturn("usernametest");
//        when(request.getParameter("password")).thenReturn("1234");
//        
//        StringWriter sw = new StringWriter();
//        PrintWriter pw = new PrintWriter(sw);
//        when(response.getWriter()).thenReturn(pw);
//        
//        loginServlet = new LoginServlet();
//        loginServlet.doGet(request, response);
//        String result = sw.getBuffer().toString().trim();
//        System.out.println(result);
//        assertEquals(result, new String("YOU LOGGED IN"));
//    }
//    
//    @Test
//    public void testLoginForInvalidLogin() throws IOException, ServletException {
//
//        when(request.getParameter("username")).thenReturn("usernametest");
//        when(request.getParameter("password")).thenReturn("1111");
//
//        StringWriter sw = new StringWriter();
//        PrintWriter pw = new PrintWriter(sw);
//        when(response.getWriter()).thenReturn(pw);
//        
//        loginServlet = new LoginServlet();
//        loginServlet.doGet(request, response);
//        String result = sw.getBuffer().toString().trim();
//        System.out.println(result);
//        assertEquals(result, new String("INCORRECT LOGIN"));
//    }
//    
}