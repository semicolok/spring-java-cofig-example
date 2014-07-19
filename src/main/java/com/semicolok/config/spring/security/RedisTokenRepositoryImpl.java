package com.semicolok.config.spring.security;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

public class RedisTokenRepositoryImpl implements PersistentTokenRepository {

    private RedisTemplate<String, Object> redisTemplate;

    public RedisTokenRepositoryImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("accessToken:series:" + token.getSeries(), token.getUsername());
        map.put("accessToken:users:" + token.getUsername(), token.getSeries());
        map.put("accessToken:tokens:" + token.getSeries(), new PersistentAccessToken(token));
        redisTemplate.opsForValue().multiSet(map);
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
        String username = (String) opsForValue.get("accessToken:series:" + series);
        PersistentAccessToken token = new PersistentAccessToken(username, series, tokenValue, lastUsed);
        opsForValue.set("accessToken:tokens:" + series, new PersistentAccessToken(token));
    }

    @SuppressWarnings("unchecked")
    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        List<Object> result = redisTemplate.opsForValue().multiGet(Arrays.asList(new String[] { "accessToken:series:" + seriesId, "accessToken:tokens:" + seriesId }));
        Map<String, Object> map = (Map<String, Object>) result.get(1);

        PersistentRememberMeToken token = null;
        try {
            token = new PersistentRememberMeToken((String) result.get(0), (String) map.get("series"), (String) map.get("tokenValue"), new Date((Long) map.get("date")));
        } catch (Exception e) {
            return null;
        }
        return token;
    }

    @Override
    public void removeUserTokens(String username) {
        String series = (String) redisTemplate.opsForValue().get("accessToken:users:" + username);
        redisTemplate.delete(Arrays.asList(new String[] { "accessToken:series:" + series, "accessToken:users:" + username, "accessToken:tokens:" + series }));
    }
}
