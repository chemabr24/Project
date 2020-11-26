package com.ae5.sige.interceptor;


import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ae5.sige.security.Rbac;
import com.ae5.sige.security.Token;


@Component
public class Interceptor implements HandlerInterceptor {
   @Override
   public boolean preHandle
      (HttpServletRequest request, HttpServletResponse response, Object handler) 
      throws Exception {

		   Enumeration<String> aux1 = request.getHeaders("authorization");
		   while(aux1.hasMoreElements()) {
			   JSONArray token = Token.header(aux1.nextElement());
				 if(token==null) {
					 response.getWriter().write("{ \"error_description\": \"Invalid Value\"}");
					 response.setContentType("application/json");
					 response.setCharacterEncoding("UTF-8");
					 response.setStatus(401);
					 return false;
				 }
				 String url = request.getRequestURL().toString();
				 if(!Rbac.hasperm(token,url)) {
					 response.getWriter().write("{ \"error_description\": \"Invalid Value\"}");
					 response.setContentType("application/json");
					 response.setCharacterEncoding("UTF-8");
					 response.setStatus(401);
					 return false;
				 }
		   }

      return true;
   }
}
