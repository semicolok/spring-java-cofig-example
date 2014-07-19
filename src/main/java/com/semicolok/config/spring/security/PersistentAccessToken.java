package com.semicolok.config.spring.security;

import java.io.Serializable;
import java.util.Date;

import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;

public class PersistentAccessToken extends PersistentRememberMeToken implements Serializable {
	private static final long serialVersionUID = -629351082186098283L;
	
	public PersistentAccessToken(PersistentRememberMeToken persistentRememberMeToken) {
		super(persistentRememberMeToken.getUsername(), persistentRememberMeToken.getSeries(), persistentRememberMeToken.getTokenValue(), persistentRememberMeToken.getDate());
	}
	
	public PersistentAccessToken(String username, String series, String tokenValue, Date date) {
		super(username, series, tokenValue, date);
	}
}
