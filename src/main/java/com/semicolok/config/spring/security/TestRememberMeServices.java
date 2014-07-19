package com.semicolok.config.spring.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.util.CookieGenerator;

public class TestRememberMeServices extends PersistentTokenBasedRememberMeServices {
	private static final String COOKIE_DOMAIN = ".localhost";
	private CookieGenerator cookieGenerator = new CookieGenerator();
	
	public TestRememberMeServices(String key, UserDetailsService userDetailsService, PersistentTokenRepository tokenRepository) {
		super(key, userDetailsService, tokenRepository);
		this.setParameter("semiuser");
		this.setAlwaysRemember(true);
		this.setCookieName("semiboard");;
	}

	protected void setCookie(String[] tokens, int maxAge, HttpServletRequest request, HttpServletResponse response) {
		this.cookieGenerator.setCookieName(getCookieName());
		this.cookieGenerator.setCookiePath("/");
		this.cookieGenerator.setCookieMaxAge(maxAge);
		this.cookieGenerator.setCookieDomain(COOKIE_DOMAIN);
		this.cookieGenerator.addCookie(response, encodeCookie(tokens));
	}

	protected void cancelCookie(HttpServletRequest request, HttpServletResponse response) {
		logger.debug("Cancelling cookie : " + getCookieName());
		this.cookieGenerator.setCookieName(getCookieName());
		this.cookieGenerator.setCookiePath("/");
		this.cookieGenerator.setCookieMaxAge(0);
		this.cookieGenerator.setCookieDomain(COOKIE_DOMAIN);
		this.cookieGenerator.addCookie(response, "");
	}
}
