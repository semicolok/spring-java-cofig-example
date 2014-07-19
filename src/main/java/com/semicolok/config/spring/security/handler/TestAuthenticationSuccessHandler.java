package com.semicolok.config.spring.security.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.semicolok.support.utils.AjaxUtils;

public class TestAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private String authenticationSuccessUrl = "localhost";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (AjaxUtils.isAjaxRequest(request)) {
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            String msg = String.format("{\"success\" : {\"redirect\" : \"%s\"}}", authenticationSuccessUrl);
            out.print(msg);
            out.flush();
        } else {
            response.sendRedirect(authenticationSuccessUrl);
        }
    }

    public void setAuthenticationSuccessUrl(String authenticationSuccessUrl) {
        this.authenticationSuccessUrl = authenticationSuccessUrl;
    }
}
