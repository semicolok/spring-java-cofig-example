package com.semicolok.config.spring.redis;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.AnnotationCacheOperationSource;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.BeanFactoryCacheOperationSourceAdvisor;
import org.springframework.cache.interceptor.CacheOperationSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import redis.clients.jedis.JedisPoolConfig;

import com.google.common.collect.Maps;

@Configuration
@EnableCaching
public class RedisConfiguration {
    private static final String PROPERTY_NAME_REDIS_HOST = "redis.host";
    private static final String PROPERTY_NAME_REDIS_PORT = "redis.port";
    private static final String PROPERTY_NAME_REDIS_PASS = "redis.pass";
    
    @Autowired private Environment env;
    
    @Bean
    public RedisConnectionFactory jedisConnectionFactory(){
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
        connectionFactory.setHostName(env.getRequiredProperty(PROPERTY_NAME_REDIS_HOST));
        connectionFactory.setPort(Integer.parseInt(env.getRequiredProperty(PROPERTY_NAME_REDIS_PORT)));
        connectionFactory.setPassword(env.getRequiredProperty(PROPERTY_NAME_REDIS_PASS));
        connectionFactory.setUsePool(true);
        connectionFactory.setPoolConfig(jedisPoolConfig());
        return connectionFactory;
    }
    
    private JedisPoolConfig jedisPoolConfig(){
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(10);
        poolConfig.setMaxTotal(10);
        poolConfig.setMinIdle(10);
        poolConfig.setMaxWaitMillis(-1);
        return poolConfig;
    }
    
    @Bean
    @Autowired
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory jedisConnectionFactory){
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
//        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        return redisTemplate;
    }
    
    @Bean
    @Autowired
    public CacheManager cacheManager(RedisTemplate<String, Object> redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        cacheManager.setDefaultExpiration(30);
        cacheManager.setExpires(expires());
        cacheManager.setUsePrefix(true);
        return cacheManager;
    }
    
    private Map<String, Long> expires() {
        Map<String, Long> expires = Maps.newConcurrentMap();
        expires.put("cache:semi:users", 0l);
        return expires;
    }
    
    @Bean(name="annotationCacheOperationSource")
    public CacheOperationSource annotationCacheOperationSource(){
        return new AnnotationCacheOperationSource();
    }
    
    @Bean(name="cacheInterceptor")
    @Autowired
    public org.springframework.cache.interceptor.CacheInterceptor cacheInterceptor(CacheManager cacheManager, CacheOperationSource annotationCacheOperationSource) {
        org.springframework.cache.interceptor.CacheInterceptor cacheInterceptor = new CacheInterceptor();
        cacheInterceptor.setCacheOperationSources(annotationCacheOperationSource);
        cacheInterceptor.setCacheManager(cacheManager);
        return cacheInterceptor;
    }
    
    @Bean(name="cacheAdvisor")
    @Autowired
    public BeanFactoryCacheOperationSourceAdvisor beanFactoryCacheOperationSourceAdvisor(org.springframework.cache.interceptor.CacheInterceptor cacheInterceptor, CacheOperationSource annotationCacheOperationSource){
        BeanFactoryCacheOperationSourceAdvisor cacheOperationSourceAdvisor = new BeanFactoryCacheOperationSourceAdvisor();
        cacheOperationSourceAdvisor.setCacheOperationSource(annotationCacheOperationSource);
        cacheOperationSourceAdvisor.setAdvice(cacheInterceptor);
        return cacheOperationSourceAdvisor;
    }
}
