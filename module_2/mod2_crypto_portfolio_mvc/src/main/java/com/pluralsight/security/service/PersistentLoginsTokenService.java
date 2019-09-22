package com.pluralsight.security.service;

import java.util.Date;

import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;

import com.pluralsight.security.entity.PersistentLoginToken;
import com.pluralsight.security.repository.PersistentLoginsTokenRespository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PersistentLoginsTokenService implements PersistentTokenRepository {

	private final PersistentLoginsTokenRespository tokenRepository;
	
	@Override
	public void createNewToken(PersistentRememberMeToken token) {
		this.tokenRepository.save(new PersistentLoginToken(token.getUsername(), 
														   token.getSeries(), 
														   token.getTokenValue(), 
														   token.getDate()));
		
	}

	@Override
	public void updateToken(String series, String tokenValue, Date lastUsed) {
		PersistentLoginToken currentToken = tokenRepository.findBySeries(series);
		tokenRepository.save(new PersistentLoginToken(currentToken.getId(),
													  currentToken.getUsername(),
													  series,
													  tokenValue,
													  lastUsed));
	}

	@Override
	public PersistentRememberMeToken getTokenForSeries(String seriesId) {
		PersistentLoginToken currentToken = tokenRepository.findBySeries(seriesId);
		return new PersistentRememberMeToken(currentToken.getUsername(),
											 currentToken.getSeries(),
											 currentToken.getTokenValue(),
											 currentToken.getDate());
	}

	@Override
	public void removeUserTokens(String username) {
		PersistentLoginToken currentToken = tokenRepository.findByUsername(username);
		if( currentToken != null) {
			tokenRepository.delete(currentToken);
		}
	}

}
