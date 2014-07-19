package com.semicolok.config.spring.security.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.semicolok.support.utils.AjaxUtils;
import com.semicolok.web.entity.User;
import com.semicolok.web.repository.UserRepository;

public class TestAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Inject private UserRepository userRepository;
	private String authenticationFailureUrl;
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		if(AjaxUtils.isAjaxRequest(request)){
			String inputEmail = request.getParameter("j_username");
			
			String msg = null;
			User user = userRepository.findByEmailAndEnabled(inputEmail, true);
			if(user != null){
				msg = String.format("{\"fail\" : {\"msg\" : \"%s\"}}", "패스워드가 일치하지 않습니다.");
			}else{
				msg = String.format("{\"fail\" : {\"msg\" : \"%s\"}}", "사용자 email이 존재하지 않습니다.");
			}
			
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			out.print(msg);
			out.flush();
		}else{
			response.sendRedirect(authenticationFailureUrl);
		}
	}

	public void setAuthenticationFailureUrl(String authenticationFailureUrl) {
		this.authenticationFailureUrl = authenticationFailureUrl;
	}
}
